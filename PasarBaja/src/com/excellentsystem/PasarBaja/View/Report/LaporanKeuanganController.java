/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.View.Report;

import com.excellentsystem.PasarBaja.DAO.KategoriKeuanganDAO;
import com.excellentsystem.PasarBaja.DAO.KeuanganDAO;
import com.excellentsystem.PasarBaja.Function;
import static com.excellentsystem.PasarBaja.Function.createRow;
import com.excellentsystem.PasarBaja.Koneksi;
import com.excellentsystem.PasarBaja.Main;
import static com.excellentsystem.PasarBaja.Main.df;
import static com.excellentsystem.PasarBaja.Main.sistem;
import static com.excellentsystem.PasarBaja.Main.tglLengkap;
import static com.excellentsystem.PasarBaja.Main.tglSql;
import com.excellentsystem.PasarBaja.Model.KategoriKeuangan;
import com.excellentsystem.PasarBaja.Model.Keuangan;
import com.excellentsystem.PasarBaja.Model.Otoritas;
import com.excellentsystem.PasarBaja.PrintOut.Report;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LaporanKeuanganController  {

    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label saldoAwalField;
    @FXML private Label saldoAkhirField;
    @FXML private DatePicker tglMulai;
    @FXML private DatePicker tglAkhir;
    private double saldoAwal; 
    private double saldoAkhir;
    final TreeItem<Keuangan> root = new TreeItem<>();
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noKeuanganColumn.setCellValueFactory( param -> param.getValue().getValue().noKeuanganProperty());
        tipeKeuanganColumn.setCellValueFactory( param -> param.getValue().getValue().tipeKeuanganProperty());
        deskripsiColumn.setCellValueFactory( param -> param.getValue().getValue().deskripsiProperty() );
        kodeUserColumn.setCellValueFactory( param -> param.getValue().getValue().kodeUserProperty() );
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglKeuanganColumn.setComparator(Function.sortDate(tglLengkap));
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        tglMulai.setConverter(Function.getTglConverter());
        tglMulai.setValue(LocalDate.now().minusMonths(1));
        tglMulai.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhir));
        tglAkhir.setConverter(Function.getTglConverter());
        tglAkhir.setValue(LocalDate.now());
        tglAkhir.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulai));
        
        allKeuangan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKeuangan();
        });
        filterData.addAll(allKeuangan);
        
        
        final ContextMenu rm = new ContextMenu();
        MenuItem printSummary = new MenuItem("Print Laporan Summary");
        printSummary.setOnAction((ActionEvent event) -> {
            print("Summary");
        });
        MenuItem printDetail = new MenuItem("Print Laporan Detail");
        printDetail.setOnAction((ActionEvent event) -> {
            print("Detail");
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(printSummary, printDetail);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rm);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKeuangan();
    }  
    @FXML
    private void getKeuangan(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<Keuangan> listKeuangan = new ArrayList<>();
                    List<KategoriKeuangan> allKategori = sistem.getListKategoriKeuangan();
                    saldoAwal = 0;
                    saldoAkhir = 0;
                    for(KategoriKeuangan k : allKategori){
                        saldoAwal = saldoAwal + KeuanganDAO.getSaldoAwal(
                            con, tglMulai.getValue().toString(),k.getKodeKeuangan());
                        saldoAkhir = saldoAkhir + KeuanganDAO.getSaldoAkhir(
                            con, tglAkhir.getValue().toString(),k.getKodeKeuangan());
                        listKeuangan.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, k.getKodeKeuangan(),
                            tglMulai.getValue().toString(),tglAkhir.getValue().toString()));
                    }
                    return listKeuangan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allKeuangan.clear();
                allKeuangan.addAll(task.getValue());
                allKeuangan.sort(Comparator.comparing(Keuangan::getKategori));
                saldoAwalField.setText(df.format(saldoAwal));
                saldoAkhirField.setText(df.format(saldoAkhir));
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
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void checkSearchField(Keuangan temp)throws Exception{
        if (searchField.getText() == null || searchField.getText().equals(""))
            filterData.add(temp);
        else{
            if(checkColumn(temp.getNoKeuangan())||
                checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))||
                checkColumn(temp.getTipeKeuangan())||
                checkColumn(temp.getKategori())||
                checkColumn(temp.getDeskripsi())||
                checkColumn(df.format(temp.getJumlahRp()))||
                checkColumn(temp.getKodeUser()))
                filterData.add(temp);
        }
    }
    private void searchKeuangan() {
        try{
            filterData.clear();
            for (Keuangan temp : allKeuangan) {
                checkSearchField(temp);
            }
            setTable();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void setTable()throws Exception{
        if(keuanganTable.getRoot()!=null)
            keuanganTable.getRoot().getChildren().clear();
        List<String> kategori = new ArrayList<>();
        for(Keuangan temp: filterData){
            if(!kategori.contains(temp.getKategori()))
                kategori.add(temp.getKategori());
        }
        
        for(String temp : kategori){
            Keuangan head = new Keuangan();
            head.setNoKeuangan(temp);
            TreeItem<Keuangan> parent = new TreeItem<>(head);
            double total = 0;
            for(Keuangan keu : filterData){
                if(keu.getKategori().equals(temp)){
                    total= total +keu.getJumlahRp();
                    TreeItem<Keuangan> child = new TreeItem<>(keu);
                    parent.getChildren().addAll(child);
                }
            }
            parent.getValue().setJumlahRp(total);
            root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    }   
    @FXML
    private void print(String jenisLaporan){
        try{
            allKeuangan.sort(Comparator.comparing(Keuangan::getKategori));
            Report report = new Report();
            report.printLaporanKeuangan(allKeuangan, tglMulai.getValue().toString(), tglAkhir.getValue().toString(),
                    jenisLaporan,saldoAwalField.getText(), saldoAkhirField.getText());
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
                Sheet sheet = workbook.createSheet("Laporan Keuangan");
                int rc = 0;
                int c = 6;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Keuangan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Keuangan");  
                sheet.getRow(rc).getCell(2).setCellValue("Tipe Keuangan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Kategori"); 
                sheet.getRow(rc).getCell(4).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Jumlah Rp"); 
                rc++;
                
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Saldo Awal :");
                sheet.getRow(rc).getCell(5).setCellValue(saldoAwal);
                rc++;
                
                List<String> kategori = new ArrayList<>();
                for(Keuangan temp: filterData){
                    if(!kategori.contains(temp.getKategori()))
                        kategori.add(temp.getKategori());
                }
                for(String temp : kategori){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double total = 0;
                    for(Keuangan temp2: filterData){
                        if(temp.equals(temp2.getKategori())){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(temp2.getNoKeuangan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(temp2.getTglKeuangan())));
                            sheet.getRow(rc).getCell(2).setCellValue(temp2.getTipeKeuangan());
                            sheet.getRow(rc).getCell(3).setCellValue(temp2.getKategori());
                            sheet.getRow(rc).getCell(4).setCellValue(temp2.getDeskripsi());
                            sheet.getRow(rc).getCell(5).setCellValue(temp2.getJumlahRp());
                            rc++;
                            total = total + temp2.getJumlahRp();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(5).setCellValue(total);
                    rc++;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Saldo Akhir :");
                sheet.getRow(rc).getCell(5).setCellValue(saldoAkhir);
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
