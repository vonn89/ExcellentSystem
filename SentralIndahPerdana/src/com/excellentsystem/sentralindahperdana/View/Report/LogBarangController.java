/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.LogBarangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.LogBarang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class LogBarangController  {

    @FXML private TableView<LogBarang> barangTable;
    @FXML private TableColumn<LogBarang, String> tanggalColumn;
    @FXML private TableColumn<LogBarang, String> kategoriColumn;
    @FXML private TableColumn<LogBarang, String> keteranganColumn;
    @FXML private TableColumn<LogBarang, Number> stokAwalColumn;
    @FXML private TableColumn<LogBarang, Number> stokMasukColumn;
    @FXML private TableColumn<LogBarang, Number> stokKeluarColumn;
    @FXML private TableColumn<LogBarang, Number> stokAkhirColumn;
    @FXML private TableColumn<LogBarang, Number> nilaiAwalColumn;
    @FXML private TableColumn<LogBarang, Number> nilaiMasukColumn;
    @FXML private TableColumn<LogBarang, Number> nilaiKeluarColumn;
    @FXML private TableColumn<LogBarang, Number> nilaiAkhirColumn;
    @FXML private Label kodeBarangLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Stage stage;
    private Main mainApp;   
    private Stage owner;
    private final ObservableList<LogBarang> allBarang = FXCollections.observableArrayList();
    private Barang barang;
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
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
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getLogBarang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        barangTable.setItems(allBarang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    } 
    public void setBarang(Barang b){
        barang = b;
        kodeBarangLabel.setText(b.getNamaBarang());
        getLogBarang();
    }
    @FXML
    private void getLogBarang(){
        try{
            Task<List<LogBarang>> task = new Task<List<LogBarang>>() {
                @Override 
                public List<LogBarang> call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        List<LogBarang> allStok = LogBarangDAO.getAllByTanggalAndBarang(con, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), barang.getKodeBarang());
                        
                        return allStok;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allBarang.clear();
                allBarang.addAll(task.getValue());
                System.out.println("x");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void print(){
        try{
            List<LogBarang> listLogBarang = new ArrayList<>();
            for(LogBarang d : allBarang){
                listLogBarang.add(d);
            }
            Report report = new Report();
            report.printLaporanLogBarang(listLogBarang, tglAwalPicker.getValue().toString(),
                    tglAkhirPicker.getValue().toString(), barang.getNamaBarang());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
