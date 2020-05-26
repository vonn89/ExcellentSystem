/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
public class UntungRugiHPPPenjualanCoilController  {

    @FXML private TreeTableView<PenjualanCoilDetail> penjualanDetailTable;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaSalesColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> kodeBahanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaBahanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> beratKotorColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> beratBersihColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> panjangColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> nilaiColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> totalNilaiColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> hargaJualColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> totalColumn;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    
    private String tglAwal;
    private String tglAkhir;
    final TreeItem<PenjualanCoilDetail> root = new TreeItem<>();
    private ObservableList<PenjualanCoilDetail> allPenjualan = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanCoilHead().getCustomer().namaProperty());
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanCoilHead().getSales().namaProperty());
        
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPenjualanCoilHead().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        nilaiColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiProperty());
        totalNilaiColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue().getNilai()*
                cellData.getValue().getValue().getBeratBersih()));
        hargaJualColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue().getValue().getHargaJual()*cellData.getValue().getValue().getPenjualanCoilHead().getKurs()));
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue().getValue().getTotal()*cellData.getValue().getValue().getPenjualanCoilHead().getKurs()));
        nilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        totalNilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        hargaJualColumn.setCellFactory(col -> Function.getTreeTableCell());
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());
        
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
        penjualanDetailTable.setContextMenu(rm);
        penjualanDetailTable.setRowFactory((TreeTableView<PenjualanCoilDetail> tableView) -> {
            final TreeTableRow<PenjualanCoilDetail> row = new TreeTableRow<PenjualanCoilDetail>(){
                @Override
                public void updateItem(PenjualanCoilDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item.getPenjualanCoilHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan Coil");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item.getPenjualanCoilHead());
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
                                    &&item.getPenjualanCoilHead().getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan Coil")&&o.isStatus()
                                    &&item.getPenjualanCoilHead().getPembayaran()>0
                                    &&item.getPenjualanCoilHead().getStatus()!=null)
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
        try{
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void getPenjualan(String tglAwal, String tglAkhir){
        Task<List<PenjualanCoilDetail>> task = new Task<List<PenjualanCoilDetail>>() {
            @Override 
            public List<PenjualanCoilDetail> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanCoilHead> penjualan = PenjualanCoilHeadDAO.getAllByDateAndStatus(con, tglAwal,tglAkhir, "true");
                    List<PenjualanCoilDetail> temp = PenjualanCoilDetailDAO.getAllByDateAndStatus(con, tglAwal,tglAkhir, "true");
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> sales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PenjualanCoilDetail d : temp){
                        for(PenjualanCoilHead h:penjualan){
                            if(d.getNoPenjualan().equals(h.getNoPenjualan()))
                                d.setPenjualanCoilHead(h);
                        }
                        for(Customer c : customer){
                            if(d.getPenjualanCoilHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPenjualanCoilHead().setCustomer(c);
                        }
                        for(Pegawai s : sales){
                            if(d.getPenjualanCoilHead().getKodeSales().equals(s.getKodePegawai()))
                                d.getPenjualanCoilHead().setSales(s);
                        }
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                this.tglAwal = tglAwal;
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
    private void hitungTotal(){
        double totalNilai=0;
        double totalQty = 0;
        for(PenjualanCoilDetail temp : allPenjualan){
            totalNilai = totalNilai + (temp.getNilai()*temp.getBeratBersih());
            totalQty = totalQty + temp.getBeratBersih();
        }
        totalNilaiField.setText(df.format(totalNilai));
        totalQtyField.setText(df.format(totalQty));
    }
    private void setTable()throws Exception{
        if(penjualanDetailTable.getRoot()!=null)
            penjualanDetailTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PenjualanCoilDetail temp: allPenjualan){
            if(!groupBy.contains(temp.getPenjualanCoilHead().getCustomer().getNama()))
                groupBy.add(temp.getPenjualanCoilHead().getCustomer().getNama());
        }
        for(String temp : groupBy){
            PenjualanCoilDetail head = new PenjualanCoilDetail();
            head.setNoPenjualan(temp);
            head.setPenjualanCoilHead(new PenjualanCoilHead());
            head.getPenjualanCoilHead().setCustomer(new Customer());
            head.getPenjualanCoilHead().setSales(new Pegawai());
            TreeItem<PenjualanCoilDetail> parent = new TreeItem<>(head);
            double totalBeratBersih = 0;
            double totalBeratKotor = 0;
            double totalPanjang = 0;
            double totalNilai = 0;
            double totalHarga = 0;
            for(PenjualanCoilDetail detail: allPenjualan){
                if(temp.equals(detail.getPenjualanCoilHead().getCustomer().getNama())){
                    totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                    totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                    totalPanjang = totalPanjang + detail.getPanjang();
                    totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                    totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setBeratBersih(totalBeratBersih);
            parent.getValue().setBeratKotor(totalBeratKotor);
            parent.getValue().setPanjang(totalPanjang);
            parent.getValue().setNilai(totalNilai/totalBeratBersih);
            parent.getValue().getPenjualanCoilHead().setKurs(1);
            parent.getValue().setHargaJual(totalHarga/totalBeratBersih);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        penjualanDetailTable.setRoot(root);
    }   
    private void lihatDetailPenjualan(PenjualanCoilHead p){
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
    private void showDetailPiutang(PenjualanCoilHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetailPenjualanCoil(p);
    }
    
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanCoilTerjual(allPenjualan, tglAwal, tglAkhir, "Customer");
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
                
                Sheet sheet = workbook.createSheet("Laporan Untung Rugi - HPP Penjualan Coil");
                int rc = 0;
                int c = 13;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penjualan"); 
                sheet.getRow(rc).getCell(2).setCellValue("Customer"); 
                sheet.getRow(rc).getCell(3).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(4).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Nama Bahan"); 
                sheet.getRow(rc).getCell(6).setCellValue("Berat Kotor"); 
                sheet.getRow(rc).getCell(7).setCellValue("Berat Bersih"); 
                sheet.getRow(rc).getCell(8).setCellValue("Panjang"); 
                sheet.getRow(rc).getCell(9).setCellValue("Nilai");
                sheet.getRow(rc).getCell(10).setCellValue("Total Nilai");
                sheet.getRow(rc).getCell(11).setCellValue("Harga"); 
                sheet.getRow(rc).getCell(12).setCellValue("Total Harga"); 
                rc++;
                List<String> groupBy = new ArrayList<>();
                for(PenjualanCoilDetail temp: allPenjualan){
                    if(!groupBy.contains(temp.getPenjualanCoilHead().getCustomer().getNama()))
                        groupBy.add(temp.getPenjualanCoilHead().getCustomer().getNama());
                }
                double grandtotalBeratBersih = 0;
                double grandtotalBeratKotor = 0;
                double grandtotalPanjang = 0;
                double grandtotalNilai = 0;
                double grandtotalHarga = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalBeratBersih = 0;
                    double totalBeratKotor = 0;
                    double totalPanjang = 0;
                    double totalNilai = 0;
                    double totalHarga = 0;
                    for(PenjualanCoilDetail detail: allPenjualan){
                        if(temp.equals(detail.getPenjualanCoilHead().getCustomer().getNama())){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getPenjualanCoilHead().getNoPenjualan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPenjualanCoilHead().getCustomer().getNama());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPenjualanCoilHead().getSales().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeBahan());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getNamaBahan());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getBeratKotor());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getBeratBersih());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getPanjang());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getNilai()*detail.getBeratBersih());
                            sheet.getRow(rc).getCell(11).setCellValue(detail.getHargaJual()*detail.getPenjualanCoilHead().getKurs());
                            sheet.getRow(rc).getCell(12).setCellValue(detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                            rc++;
                            
                            totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                            totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                            totalPanjang = totalPanjang + detail.getPanjang();
                            totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                            totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(6).setCellValue(totalBeratKotor);
                    sheet.getRow(rc).getCell(7).setCellValue(totalBeratBersih);
                    sheet.getRow(rc).getCell(8).setCellValue(totalPanjang);
                    sheet.getRow(rc).getCell(9).setCellValue(totalNilai/totalBeratBersih);
                    sheet.getRow(rc).getCell(10).setCellValue(totalNilai);
                    sheet.getRow(rc).getCell(11).setCellValue(totalHarga/totalBeratBersih);
                    sheet.getRow(rc).getCell(12).setCellValue(totalHarga);
                    rc++;
                    
                    grandtotalBeratKotor = grandtotalBeratKotor + totalBeratKotor;
                    grandtotalBeratBersih = grandtotalBeratBersih + totalBeratBersih;
                    grandtotalPanjang = grandtotalPanjang + totalPanjang;
                    grandtotalNilai = grandtotalNilai + totalNilai;
                    grandtotalHarga = grandtotalHarga + totalHarga;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalBeratKotor);
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalBeratBersih);
                sheet.getRow(rc).getCell(8).setCellValue(grandtotalPanjang);
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalNilai/grandtotalBeratBersih);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalNilai);
                sheet.getRow(rc).getCell(11).setCellValue(grandtotalHarga/grandtotalBeratBersih);
                sheet.getRow(rc).getCell(12).setCellValue(grandtotalHarga);
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
