/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.PrintOut.Report;
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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
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
 * @author Xtreme
 */
public class LaporanKartuStokBahanController {

    @FXML private TableView<StokBahan> bahanTable;
    @FXML private TableColumn<StokBahan, String> kodeKategoriColumn;
    @FXML private TableColumn<StokBahan, String> kodeBahanColumn;
    @FXML private TableColumn<StokBahan, String> namaBahanColumn;
    @FXML private TableColumn<StokBahan, Number> beratKotorColumn;
    @FXML private TableColumn<StokBahan, Number> beratBersihColumn;
    @FXML private TableColumn<StokBahan, Number> panjangColumn;
    @FXML private TableColumn<StokBahan, String> tanggalColumn;
    
    @FXML private TableColumn<StokBahan, Number> beratAwalColumn;
    @FXML private TableColumn<StokBahan, Number> beratInColumn;
    @FXML private TableColumn<StokBahan, Number> beratOutColumn;
    @FXML private TableColumn<StokBahan, Number> beratAkhirColumn;
    
    @FXML private Label kodeBahanLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    private ObservableList<StokBahan> allBahan = FXCollections.observableArrayList();
    private StokBahan stokBahan;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().kodeKategoriProperty());
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().namaBahanProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tgl.format(tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tgl));
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty()); 
        beratAwalColumn.setCellFactory(col -> Function.getTableCell());
        beratInColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty()); 
        beratInColumn.setCellFactory(col -> Function.getTableCell());
        beratOutColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        beratOutColumn.setCellFactory(col -> Function.getTableCell());
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> Function.getTableCell());
        
        
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
            getBahan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        bahanTable.setContextMenu(rm);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
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
    public void setBahan(StokBahan s){
        stokBahan = s;
        kodeBahanLabel.setText(s.getKodeBahan());
        getBahan();
    }
    @FXML
    private void getBahan(){
        Task<List<StokBahan>> task = new Task<List<StokBahan>>() {
            @Override 
            public List<StokBahan> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<StokBahan> temp = StokBahanDAO.getAllByTanggalAndBahanAndGudang(con, 
                        tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        stokBahan.getKodeBahan(), stokBahan.getKodeGudang());
                    for(StokBahan stok : temp){
                        stok.setBahan(stokBahan.getBahan());
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allBahan.clear();
            allBahan.addAll(task.getValue());
            allBahan.sort(Comparator.comparing(StokBahan::getTanggal));
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void print(){
        try{
            List<StokBahan> listStokBahan = new ArrayList<>();
            for(StokBahan d : allBahan){
                listStokBahan.add(d);
            }
            Report report = new Report();
            report.printLaporanKartuStokBahan(listStokBahan);
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
                Sheet sheet = workbook.createSheet("Laporan Kartu Stok Bahan");
                int rc = 0;
                int c = 5;
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
                sheet.getRow(rc).getCell(1).setCellValue("Stok Awal");  
                sheet.getRow(rc).getCell(2).setCellValue("Stok Masuk");  
                sheet.getRow(rc).getCell(3).setCellValue("Stok Keluar");  
                sheet.getRow(rc).getCell(4).setCellValue("Stok Akhir");  
                rc++;
                
                for(StokBahan s: allBahan){
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(tgl.format(tglBarang.parse(s.getTanggal())));
                    sheet.getRow(rc).getCell(1).setCellValue(s.getStokAwal());
                    sheet.getRow(rc).getCell(2).setCellValue(s.getStokMasuk());
                    sheet.getRow(rc).getCell(3).setCellValue(s.getStokKeluar());
                    sheet.getRow(rc).getCell(4).setCellValue(s.getStokAkhir());
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
