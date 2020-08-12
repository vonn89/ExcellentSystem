/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.StokBarangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class KartuStokBarangController  {

    @FXML private TableView<StokBarang> barangTable;
    @FXML private TableColumn<StokBarang, String> tanggalColumn;
    @FXML private TableColumn<StokBarang, Number> mutasiAwalColumn;
    @FXML private TableColumn<StokBarang, Number> mutasiInColumn;
    @FXML private TableColumn<StokBarang, Number> mutasiOutColumn;
    @FXML private TableColumn<StokBarang, Number> mutasiAkhirColumn;
    
    @FXML private ComboBox<String> periodeCombo;
    @FXML private TextField kodeBarangField;
    @FXML private TextField namaBarangField;
    
    private ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private ObservableList<StokBarang> filterData = FXCollections.observableArrayList();
    private StokBarang barang;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        mutasiAwalColumn.setCellValueFactory( cellData -> cellData.getValue().stokAwalProperty());
        mutasiAwalColumn.setCellFactory(col -> Function.getTableCell());
        mutasiInColumn.setCellValueFactory( cellData -> cellData.getValue().stokMasukProperty());
        mutasiInColumn.setCellFactory(col -> Function.getTableCell());
        mutasiOutColumn.setCellValueFactory( cellData -> cellData.getValue().stokKeluarProperty());
        mutasiOutColumn.setCellFactory(col -> Function.getTableCell());
        mutasiAkhirColumn.setCellValueFactory( cellData -> cellData.getValue().stokAkhirProperty());
        mutasiAkhirColumn.setCellFactory(col -> Function.getTableCell());
        allBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            searchBarang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> periode = FXCollections.observableArrayList();
        periode.addAll("This Month","Last 3 Months","Last 6 Months","This Year","All");
        periodeCombo.setItems(periode);
        periodeCombo.getSelectionModel().select("This Month");
        barangTable.setItems(filterData);
    }
    public void getBarang(StokBarang s){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    barang = s;
                    List<StokBarang> temp = StokBarangDAO.getAllByTanggalAndBarang(con, "",tglBarang.format(new Date()),barang.getKodeBarang());
                    for(StokBarang stok : temp){
                        stok.setBarang(barang.getBarang());
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
                kodeBarangField.setText(barang.getBarang().getKodeBarang());
                namaBarangField.setText(barang.getBarang().getNamaBarang());
                allBarang.clear();
                allBarang.addAll(task.get());
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
    @FXML
    private void searchBarang()  {
        try{
            filterData.clear();
            for (StokBarang temp : allBarang) {
                if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                    DateFormat df = new SimpleDateFormat("yyMM");
                    if(df.format(tglBarang.parse(temp.getTanggal())).equals(df.format(new Date()))){
                            filterData.add(temp);
                    }
                }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 3 Months")){
                    LocalDate localdate = LocalDate.now().minusMonths(3);
                    if(localdate.isBefore(LocalDate.parse(temp.getTanggal()))){
                            filterData.add(temp);
                    }
                }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 6 Months")){
                    LocalDate localdate = LocalDate.now().minusMonths(6);
                    if(localdate.isBefore(LocalDate.parse(temp.getTanggal()))){
                            filterData.add(temp);
                    }
                }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Year")){
                    DateFormat df = new SimpleDateFormat("yy");
                    if(df.format(tglBarang.parse(temp.getTanggal())).equals(df.format(new Date()))){
                            filterData.add(temp);
                    }
                }else{
                        filterData.add(temp);
                }
            }
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
            List<StokBarang> listStokBarang = new ArrayList<>();
            for(StokBarang d : allBarang){
                listStokBarang.add(d);
            }
            Report report = new Report();
            report.printLaporanKartuStokBarang(listStokBarang);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
