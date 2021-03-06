/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class UntungRugiPenjualanCoilController  {

    
    @FXML private TreeTableView<PenjualanBahanHead> penjualanTable;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> namaSalesColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> totalPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, String> kursColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, Number> totalPenjualanRpColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, Number> pembayaranColumn;
    @FXML private TreeTableColumn<PenjualanBahanHead, Number> sisaPembayaranColumn;
    
    @FXML private Label totalPenjualanField;
    @FXML private Label totalPembayaranField;
    @FXML private Label sisaPembayaranField;
    
    private String tglAwal;
    private String tglAkhir;
    final TreeItem<PenjualanBahanHead> root = new TreeItem<>();
    private ObservableList<PenjualanBahanHead> allPenjualan = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCustomer().namaProperty());
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getSales().namaProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        kursColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getValue().getKurs()));
        });
        kursColumn.setComparator(Function.sortString());
        totalPenjualanColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getValue().getTotalPenjualan()/celldata.getValue().getValue().getKurs()));
        });
        totalPenjualanRpColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalPenjualanProperty());
        totalPenjualanRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan(tglAwal, tglAkhir);
        });
        rm.getItems().addAll(print, export, refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TreeTableView<PenjualanBahanHead> tableView) -> {
            final TreeTableRow<PenjualanBahanHead> row = new TreeTableRow<PenjualanBahanHead>(){
                @Override
                public void updateItem(PenjualanBahanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan Coil");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent event) -> {
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPenjualan(tglAwal, tglAkhir);
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus()
                                    &&item.getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan Coil")&&o.isStatus()
                                    &&item.getPembayaran()>0&&item.getStatus()!=null)
                                rm.getItems().add(pembayaran);
                        }
                        rm.getItems().addAll(print, export, refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getPenjualan(String tglMulai, String tglAkhir){
        Task<List<PenjualanBahanHead>> task = new Task<List<PenjualanBahanHead>>() {
            @Override 
            public List<PenjualanBahanHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanBahanHead> allPenjualan = PenjualanBahanHeadDAO.getAllByDateAndStatus(con, tglMulai, tglAkhir,"true");
                    List<PenjualanBahanDetail> allDetail = PenjualanBahanDetailDAO.getAllByDateAndStatus(con, tglMulai, tglAkhir, "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PenjualanBahanHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        for(Pegawai s : allSales){
                            if(p.getKodeSales().equals(s.getKodePegawai()))
                                p.setSales(s);
                        }
                        List<PenjualanBahanDetail> detail = new ArrayList<>();
                        for(PenjualanBahanDetail d: allDetail){
                            if(p.getNoPenjualan().equals(d.getNoPenjualan()))
                                detail.add(d);
                        }
                        p.setListPenjualanBahanDetail(detail);
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                this.tglAwal = tglMulai;
                this.tglAkhir = tglAkhir;
                allPenjualan.clear();
                allPenjualan.addAll(task.getValue());
                setTable();
                hitungTotal();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private void setTable()throws Exception{
        if(penjualanTable.getRoot()!=null)
            penjualanTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PenjualanBahanHead temp : allPenjualan){
            if(!groupBy.contains(temp.getCustomer().getNama()))
                groupBy.add(temp.getCustomer().getNama());
        }
        for(String temp : groupBy){
            PenjualanBahanHead head = new PenjualanBahanHead();
            head.setNoPenjualan(temp);
            head.setCustomer(new Customer());
            head.setSales(new Pegawai());
            TreeItem<PenjualanBahanHead> parent = new TreeItem<>(head);
            double totalPenjualan = 0;
            double totalPembayaran = 0;
            double sisaPembayaran = 0;
            for(PenjualanBahanHead pj: allPenjualan){
                if(temp.equals(pj.getCustomer().getNama())){
                    totalPenjualan = totalPenjualan + pj.getTotalPenjualan();
                    totalPembayaran = totalPembayaran + pj.getPembayaran();
                    sisaPembayaran = sisaPembayaran + pj.getSisaPembayaran();
                    parent.getChildren().addAll(new TreeItem<>(pj));
                }
            }
            parent.getValue().setKurs(1);
            parent.getValue().setTotalPenjualan(totalPenjualan);
            parent.getValue().setPembayaran(totalPembayaran);
            parent.getValue().setSisaPembayaran(sisaPembayaran);
            root.getChildren().add(parent);
        }
        penjualanTable.setRoot(root);
    }   
    private void hitungTotal(){
        double totalPenjualan=0;
        double totalPembayaran=0;
        double sisaPembayaran=0;
        for(PenjualanBahanHead temp : allPenjualan){
            totalPenjualan = totalPenjualan + temp.getTotalPenjualan();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(totalPenjualan));
        totalPembayaranField.setText(df.format(totalPembayaran));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
    }
    private void lihatDetailPenjualan(PenjualanBahanHead p){
        if(p.getKurs()!=1){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoil.fxml");
            NewPenjualanCoilController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setDetailPenjualan(p.getNoPenjualan());
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoilRp.fxml");
            NewPenjualanCoilRpController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setDetailPenjualan(p.getNoPenjualan());
        }
    }
    private void showDetailPiutang(PenjualanBahanHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setDetailPenjualanCoil(p);
    }
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanPenjualanCoil(allPenjualan, tglAwal,
                    tglAkhir, "Customer");
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void exportExcel(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Sheet sheet = workbook.createSheet("Laporan Untung Rugi - Penjualan Coil");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penjualan");  
                sheet.getRow(rc).getCell(2).setCellValue("Customer"); 
                sheet.getRow(rc).getCell(3).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(4).setCellValue("Total Penjualan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kurs"); 
                sheet.getRow(rc).getCell(6).setCellValue("Total Penjualan Rp"); 
                sheet.getRow(rc).getCell(7).setCellValue("Pembayaran"); 
                sheet.getRow(rc).getCell(8).setCellValue("Sisa Pembayaran"); 
                sheet.getRow(rc).getCell(9).setCellValue("Catatan"); 
                sheet.getRow(rc).getCell(10).setCellValue("Kode User"); 
                rc++;
                
                List<String> groupBy = new ArrayList<>();
                for(PenjualanBahanHead temp : allPenjualan){
                    if(!groupBy.contains(temp.getCustomer().getNama()))
                        groupBy.add(temp.getCustomer().getNama());
                }
                double grandtotalPenjualanRp = 0;
                double grandtotalPembayaran = 0;
                double grandsisaPembayaran = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);  
                    rc++;
                    double totalPenjualanRp = 0;
                    double totalPembayaran = 0;
                    double sisaPembayaran = 0;
                    for(PenjualanBahanHead p: allPenjualan){
                        if(temp.equals(p.getCustomer().getNama())){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(p.getNoPenjualan());  
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));  
                            sheet.getRow(rc).getCell(2).setCellValue(p.getCustomer().getNama());  
                            sheet.getRow(rc).getCell(3).setCellValue(p.getSales().getNama()); 
                            if(p.getKurs()==1){
                                sheet.getRow(rc).getCell(4).setCellValue("-"); 
                                sheet.getRow(rc).getCell(5).setCellValue("-"); 
                            }else{
                                sheet.getRow(rc).getCell(4).setCellValue(p.getTotalPenjualan()/p.getKurs()); 
                                sheet.getRow(rc).getCell(5).setCellValue(p.getKurs()); 
                            }
                            sheet.getRow(rc).getCell(6).setCellValue(p.getTotalPenjualan()); 
                            sheet.getRow(rc).getCell(7).setCellValue(p.getPembayaran()); 
                            sheet.getRow(rc).getCell(8).setCellValue(p.getSisaPembayaran()); 
                            sheet.getRow(rc).getCell(9).setCellValue(p.getCatatan()); 
                            sheet.getRow(rc).getCell(10).setCellValue(p.getKodeUser()); 
                            rc++;
                            totalPenjualanRp = totalPenjualanRp + p.getTotalPenjualan();
                            totalPembayaran = totalPembayaran + p.getPembayaran();
                            sisaPembayaran = sisaPembayaran + p.getSisaPembayaran();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);  
                    sheet.getRow(rc).getCell(6).setCellValue(totalPenjualanRp); 
                    sheet.getRow(rc).getCell(7).setCellValue(totalPembayaran); 
                    sheet.getRow(rc).getCell(8).setCellValue(sisaPembayaran); 
                    rc++;
                    grandtotalPenjualanRp = grandtotalPenjualanRp + totalPenjualanRp;
                    grandtotalPembayaran = grandtotalPembayaran + totalPembayaran;
                    grandsisaPembayaran = grandsisaPembayaran + sisaPembayaran;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalPenjualanRp); 
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalPembayaran); 
                sheet.getRow(rc).getCell(8).setCellValue(grandsisaPembayaran); 
                rc++;
                for(int i=0 ; i<c ; i++){ sheet.autoSizeColumn(i);}
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
}
