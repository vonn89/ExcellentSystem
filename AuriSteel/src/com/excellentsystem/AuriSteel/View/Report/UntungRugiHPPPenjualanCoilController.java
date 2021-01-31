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

    @FXML private TreeTableView<PenjualanBahanDetail> penjualanDetailTable;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> namaSalesColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> kodeBahanColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, String> namaBahanColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> beratKotorColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> beratBersihColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> panjangColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> nilaiColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> totalNilaiColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> hargaJualColumn;
    @FXML private TreeTableColumn<PenjualanBahanDetail, Number> totalColumn;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    
    private String tglAwal;
    private String tglAkhir;
    final TreeItem<PenjualanBahanDetail> root = new TreeItem<>();
    private ObservableList<PenjualanBahanDetail> allPenjualan = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanBahanHead().getCustomer().namaProperty());
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanBahanHead().getSales().namaProperty());
        
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPenjualanBahanHead().getTglPenjualan())));
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
                cellData.getValue().getValue().getHargaJual()*cellData.getValue().getValue().getPenjualanBahanHead().getKurs()));
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue().getValue().getTotal()*cellData.getValue().getValue().getPenjualanBahanHead().getKurs()));
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
        penjualanDetailTable.setRowFactory((TreeTableView<PenjualanBahanDetail> tableView) -> {
            final TreeTableRow<PenjualanBahanDetail> row = new TreeTableRow<PenjualanBahanDetail>(){
                @Override
                public void updateItem(PenjualanBahanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item.getPenjualanBahanHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan Coil");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item.getPenjualanBahanHead());
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
                                    &&item.getPenjualanBahanHead().getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan Coil")&&o.isStatus()
                                    &&item.getPenjualanBahanHead().getPembayaran()>0
                                    &&item.getPenjualanBahanHead().getStatus()!=null)
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
        Task<List<PenjualanBahanDetail>> task = new Task<List<PenjualanBahanDetail>>() {
            @Override 
            public List<PenjualanBahanDetail> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanBahanHead> penjualan = PenjualanBahanHeadDAO.getAllByDateAndStatus(con, tglAwal,tglAkhir, "true");
                    List<PenjualanBahanDetail> temp = PenjualanBahanDetailDAO.getAllByDateAndStatus(con, tglAwal,tglAkhir, "true");
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> sales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PenjualanBahanDetail d : temp){
                        for(PenjualanBahanHead h:penjualan){
                            if(d.getNoPenjualan().equals(h.getNoPenjualan()))
                                d.setPenjualanBahanHead(h);
                        }
                        for(Customer c : customer){
                            if(d.getPenjualanBahanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPenjualanBahanHead().setCustomer(c);
                        }
                        for(Pegawai s : sales){
                            if(d.getPenjualanBahanHead().getKodeSales().equals(s.getKodePegawai()))
                                d.getPenjualanBahanHead().setSales(s);
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
        for(PenjualanBahanDetail temp : allPenjualan){
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
        for(PenjualanBahanDetail temp: allPenjualan){
            if(!groupBy.contains(temp.getPenjualanBahanHead().getCustomer().getNama()))
                groupBy.add(temp.getPenjualanBahanHead().getCustomer().getNama());
        }
        for(String temp : groupBy){
            PenjualanBahanDetail head = new PenjualanBahanDetail();
            head.setNoPenjualan(temp);
            head.setPenjualanBahanHead(new PenjualanBahanHead());
            head.getPenjualanBahanHead().setCustomer(new Customer());
            head.getPenjualanBahanHead().setSales(new Pegawai());
            TreeItem<PenjualanBahanDetail> parent = new TreeItem<>(head);
            double totalBeratBersih = 0;
            double totalBeratKotor = 0;
            double totalPanjang = 0;
            double totalNilai = 0;
            double totalHarga = 0;
            for(PenjualanBahanDetail detail: allPenjualan){
                if(temp.equals(detail.getPenjualanBahanHead().getCustomer().getNama())){
                    totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                    totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                    totalPanjang = totalPanjang + detail.getPanjang();
                    totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                    totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanBahanHead().getKurs());
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setBeratBersih(totalBeratBersih);
            parent.getValue().setBeratKotor(totalBeratKotor);
            parent.getValue().setPanjang(totalPanjang);
            parent.getValue().setNilai(totalNilai/totalBeratBersih);
            parent.getValue().getPenjualanBahanHead().setKurs(1);
            parent.getValue().setHargaJual(totalHarga/totalBeratBersih);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        penjualanDetailTable.setRoot(root);
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
                for(PenjualanBahanDetail temp: allPenjualan){
                    if(!groupBy.contains(temp.getPenjualanBahanHead().getCustomer().getNama()))
                        groupBy.add(temp.getPenjualanBahanHead().getCustomer().getNama());
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
                    for(PenjualanBahanDetail detail: allPenjualan){
                        if(temp.equals(detail.getPenjualanBahanHead().getCustomer().getNama())){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getPenjualanBahanHead().getNoPenjualan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPenjualanBahanHead().getTglPenjualan())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPenjualanBahanHead().getCustomer().getNama());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPenjualanBahanHead().getSales().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeBahan());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getNamaBahan());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getBeratKotor());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getBeratBersih());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getPanjang());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getNilai()*detail.getBeratBersih());
                            sheet.getRow(rc).getCell(11).setCellValue(detail.getHargaJual()*detail.getPenjualanBahanHead().getKurs());
                            sheet.getRow(rc).getCell(12).setCellValue(detail.getTotal()*detail.getPenjualanBahanHead().getKurs());
                            rc++;
                            
                            totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                            totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                            totalPanjang = totalPanjang + detail.getPanjang();
                            totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                            totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanBahanHead().getKurs());
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
