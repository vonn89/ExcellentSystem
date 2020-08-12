/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.DAO.StokBarangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class AddBarangPengirimanController  {
    @FXML private TableView<Barang> barangTable;
    @FXML private TableColumn<Barang, String> kodeBarangColumn;
    @FXML private TableColumn<Barang, String> kodeKategoriColumn;
    @FXML private TableColumn<Barang, String> namaBarangColumn;
    @FXML private TableColumn<Barang, String> keteranganColumn;
    @FXML private TableColumn<Barang, String> satuanDasarColumn;
    @FXML private TableColumn<Barang, Number> stokBarangColumn;
    
    @FXML private TextField kodeBarangField;
    @FXML private TextField namaBarangField;
    @FXML public TextField qtyField;
    @FXML public ComboBox<String> satuanCombo;
    @FXML private TextField searchField;
    @FXML public Button addButton;
    public Barang barang;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private final ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<Barang> filterData = FXCollections.observableArrayList();
    private ObservableList<String> allSatuan = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanDasarColumn.setCellValueFactory(cellData -> cellData.getValue().satuanDasarProperty());
        stokBarangColumn.setCellValueFactory(cellData -> {
            double stokAkhir = 0;
            for(StokBarang s : cellData.getValue().getStokAkhir()){
                stokAkhir = stokAkhir + s.getStokAkhir();
            }
            return new SimpleDoubleProperty(stokAkhir);
        });
        stokBarangColumn.setCellFactory((c) -> Function.getTableCell());
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(NumberFormatException e){
                qtyField.undo();
            }
        });
        barangTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectBarang(newValue);
        });
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        getBarang();
        satuanCombo.setItems(allSatuan);
        barangTable.setItems(filterData);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getBarang(){
        Task<List<Barang>> task = new Task<List<Barang>>() {
            @Override 
            public List<Barang> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    List<StokBarang> listStok = StokBarangDAO.getAllByTanggal(
                            con, tglBarang.format(new Date()));
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con,"true");
                    for (Barang b : listBarang) {
                        List<StokBarang> stokAkhir = new ArrayList<>();
                        for(StokBarang s: listStok){
                            if(b.getKodeBarang().equals(s.getKodeBarang()))
                                stokAkhir.add(s);
                        }
                        b.setStokAkhir(stokAkhir);
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(b.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        b.setAllSatuan(satuan);
                    }
                    return listBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                selectBarang(null);
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
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchBarang() {
        filterData.clear();
        for (Barang temp : allBarang) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeBarang())||
                    checkColumn(temp.getNamaBarang())||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(temp.getKategoriBarang()))
                    filterData.add(temp);
            }
        }
    }
    private void selectBarang(Barang value){
        barang = null;
        kodeBarangField.setText("");
        namaBarangField.setText("");
        satuanCombo.getSelectionModel().clearSelection();
        qtyField.setText("0");
        allSatuan.clear();
        if(value!=null){
            barang = value;
            kodeBarangField.setText(value.getKodeBarang());
            namaBarangField.setText(value.getNamaBarang());
            allSatuan.add(value.getSatuanDasar());
            for (Satuan temp : barang.getAllSatuan()) {
                allSatuan.add(temp.getKodeSatuan());
            }
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    } 
    
}
