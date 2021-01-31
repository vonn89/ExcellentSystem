/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.LogBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.LogBahan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
import com.excellentsystem.AuriSteel.View.Dialog.NewProduksiBarangController;
import com.excellentsystem.AuriSteel.View.Dialog.PenyesuaianStokController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
 * @author excellent
 */
public class LogBahanController  {

    @FXML private TableView<LogBahan> bahanTable;
    @FXML private TableColumn<LogBahan, String> tanggalColumn;
    @FXML private TableColumn<LogBahan, String> kategoriColumn;
    @FXML private TableColumn<LogBahan, String> keteranganColumn;
    @FXML private TableColumn<LogBahan, Number> stokAwalColumn;
    @FXML private TableColumn<LogBahan, Number> stokMasukColumn;
    @FXML private TableColumn<LogBahan, Number> stokKeluarColumn;
    @FXML private TableColumn<LogBahan, Number> stokAkhirColumn;
    @FXML private TableColumn<LogBahan, Number> nilaiAwalColumn;
    @FXML private TableColumn<LogBahan, Number> nilaiMasukColumn;
    @FXML private TableColumn<LogBahan, Number> nilaiKeluarColumn;
    @FXML private TableColumn<LogBahan, Number> nilaiAkhirColumn;
    
    @FXML private Label kodeBahanLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    private final ObservableList<LogBahan> allBahan = FXCollections.observableArrayList();
    private StokBahan stokBahan;
    private Stage stage;
    private Main mainApp;   
    private Stage owner;
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglLengkap));
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> Function.getTableCell());
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> Function.getTableCell());
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> Function.getTableCell());
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> Function.getTableCell());
        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> Function.getTableCell());
        nilaiMasukColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiMasukProperty());
        nilaiMasukColumn.setCellFactory(col -> Function.getTableCell());
        nilaiKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiKeluarProperty());
        nilaiKeluarColumn.setCellFactory(col -> Function.getTableCell());
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> Function.getTableCell());
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglAwalPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getLogBahan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        bahanTable.setContextMenu(rm);
        bahanTable.setRowFactory(ttv -> {
            TableRow<LogBahan> row = new TableRow<LogBahan>() {
                @Override
                public void updateItem(LogBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        ContextMenu rm = new ContextMenu();
                        MenuItem detailPenjualan = new MenuItem("Detail Penjualan Coil");
                        detailPenjualan.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem detailPembelian = new MenuItem("Detail Pembelian");
                        detailPembelian.setOnAction((ActionEvent e)->{
                            lihatDetailPembelian(item);
                        });
                        MenuItem detailProduksi = new MenuItem("Detail Produksi");
                        detailProduksi.setOnAction((ActionEvent e)->{
                            lihatDetailProduksi(item);
                        });
                        MenuItem detailPenyesuaian = new MenuItem("Detail Penyesuaian Stok");
                        detailPenyesuaian.setOnAction((ActionEvent event) -> {
                            showDetailPenyesuaianStok(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getLogBahan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus()&&
                                (item.getKategori().equals("Penjualan")||item.getKategori().equals("Batal Penjualan")))
                                rm.getItems().add(detailPenjualan);
                            if(o.getJenis().equals("Detail Pembelian")&&o.isStatus()&&
                                (item.getKategori().equals("Pembelian")||item.getKategori().equals("Batal Pembelian")))
                                rm.getItems().add(detailPembelian);
                            if(o.getJenis().equals("Detail Produksi")&&o.isStatus()&&
                                (item.getKategori().equals("Produksi")||item.getKategori().equals("Batal Produksi")))
                                rm.getItems().add(detailProduksi);
                        }
                        if(item.getKategori().equals("Penyesuaian Stok Bahan"))
                            rm.getItems().add(detailPenyesuaian);
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rm.getItems().addAll(print);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().addAll(export);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        bahanTable.setItems(allBahan);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    } 
    public void setBahan(StokBahan b){
        stokBahan = b;
        kodeBahanLabel.setText(b.getKodeBahan());
        getLogBahan();
    }
    @FXML
    private void getLogBahan(){
        Task<List<LogBahan>> task = new Task<List<LogBahan>>() {
            @Override 
            public List<LogBahan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<LogBahan> allStok = LogBahanDAO.getAllByTanggalAndBahanAndGudang(con, 
                        tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        stokBahan.getKodeBahan(), stokBahan.getKodeGudang());
                    return allStok;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBahan.clear();
            allBahan.addAll(task.getValue());
            allBahan.sort(Comparator.comparing(LogBahan::getTanggal));
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void lihatDetailPenjualan(LogBahan log){
        try(Connection con = Koneksi.getConnection()){
            PenjualanBahanHead p = PenjualanBahanHeadDAO.get(con, log.getKeterangan());
            if(p.getKurs()!=1){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoil.fxml");
                NewPenjualanCoilController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPenjualan(log.getKeterangan());
            }else{
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoilRp.fxml");
                NewPenjualanCoilRpController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPenjualan(log.getKeterangan());
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void lihatDetailPembelian(LogBahan log){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPembelian(log.getKeterangan());
    }
    private void lihatDetailProduksi(LogBahan log){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewProduksiBarang.fxml");
        NewProduksiBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailProduksi(log.getKeterangan());
    }
    private void showDetailPenyesuaianStok(LogBahan log){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child ,"View/Dialog/Child/PenyesuaianStok.fxml");
        PenyesuaianStokController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setPenyesuaianStokBahan(log.getKeterangan());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void print(){
        try{
            List<LogBahan> listLogBahan = new ArrayList<>();
            for(LogBahan d : allBahan){
                listLogBahan.add(d);
            }
            Report report = new Report();
            report.printLaporanLogBahan(listLogBahan, tglAwalPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), stokBahan.getKodeBahan());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
                Sheet sheet = workbook.createSheet("Laporan Log Bahan");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Bahan");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getKodeBahan());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Kategori");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getBahan().getKodeKategori());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Nama Bahan");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getBahan().getNamaBahan());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Berat Kotor");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getBahan().getBeratKotor());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Berat Bersih");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getBahan().getBeratBersih());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Panjang");
                sheet.getRow(rc).getCell(1).setCellValue(stokBahan.getBahan().getPanjang());
                rc++;
                
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal"); 
                sheet.getRow(rc).getCell(1).setCellValue("Kategori"); 
                sheet.getRow(rc).getCell(2).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Stok Awal");  
                sheet.getRow(rc).getCell(4).setCellValue("Nilai Awal");  
                sheet.getRow(rc).getCell(5).setCellValue("Stok Masuk");  
                sheet.getRow(rc).getCell(6).setCellValue("Nilai Masuk");  
                sheet.getRow(rc).getCell(7).setCellValue("Stok Keluar");  
                sheet.getRow(rc).getCell(8).setCellValue("Nilai Keluar");  
                sheet.getRow(rc).getCell(9).setCellValue("Stok Akhir");  
                sheet.getRow(rc).getCell(10).setCellValue("Nilai Akhir");  
                rc++;
                
                for(LogBahan s: allBahan){
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(tglLengkap.format(tglSql.parse(s.getTanggal())));
                    sheet.getRow(rc).getCell(1).setCellValue(s.getKategori());
                    sheet.getRow(rc).getCell(2).setCellValue(s.getKeterangan());
                    sheet.getRow(rc).getCell(3).setCellValue(s.getStokAwal());
                    sheet.getRow(rc).getCell(4).setCellValue(s.getNilaiAwal());
                    sheet.getRow(rc).getCell(5).setCellValue(s.getStokMasuk());
                    sheet.getRow(rc).getCell(6).setCellValue(s.getNilaiMasuk());
                    sheet.getRow(rc).getCell(7).setCellValue(s.getStokKeluar());
                    sheet.getRow(rc).getCell(8).setCellValue(s.getNilaiKeluar());
                    sheet.getRow(rc).getCell(9).setCellValue(s.getStokAkhir());
                    sheet.getRow(rc).getCell(10).setCellValue(s.getNilaiAkhir());
                    rc++;
                }
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
