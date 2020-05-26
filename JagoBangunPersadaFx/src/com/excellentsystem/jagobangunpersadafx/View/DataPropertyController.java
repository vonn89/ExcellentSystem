/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.PemecahanPropertyHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PenggabunganPropertyHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyHead;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyHead;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.PemecahanPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.PenggabunganPropertyController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataPropertyController  {
    @FXML private TableView<Property> propertyTable;
    @FXML private TableColumn<Property, String> kodePropertyColumn;
    @FXML private TableColumn<Property, String> kodeKategoriColumn;
    @FXML private TableColumn<Property, String> namaPropertyColumn;
    @FXML private TableColumn<Property, String> alamatColumn;
    @FXML private TableColumn<Property, String> tipeColumn;
    @FXML private TableColumn<Property, String> spesifikasiColumn;
    @FXML private TableColumn<Property, Number> luasTanahColumn;
    @FXML private TableColumn<Property, Number> luasBangunanColumn;
    @FXML private TableColumn<Property, Number> nilaiPropertyColumn;
    @FXML private TableColumn<Property, Number> hargaJualColumn;
    @FXML private TableColumn<Property, String> keteranganColumn;
    @FXML private TableColumn<Property, String> statusColumn;
    
    @FXML private ComboBox<String> statusCombo;
    @FXML private TextField searchField;
    @FXML private HBox hbox;
    
    @FXML private Label totalLuasTanahLabel;
    @FXML private Label totalLuasBangunanLabel;
    @FXML private Label nilaiPropertyLabel;
    @FXML private Label totalNilaiPropertyLabel;
    @FXML private Label totalHargaJualLabel;
    
    private Main mainApp;   
    private ObservableList<Property> allProperty = FXCollections.observableArrayList();
    private ObservableList<Property> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodePropertyColumn.setCellValueFactory(cellData -> cellData.getValue().kodePropertyProperty());
        kodePropertyColumn.setCellFactory(col -> Function.getWrapTableCell(kodePropertyColumn));
        
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        tipeColumn.setCellValueFactory(cellData -> cellData.getValue().tipeProperty());
        tipeColumn.setCellFactory(col -> Function.getWrapTableCell(tipeColumn));
        
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        spesifikasiColumn.setCellValueFactory(cellData ->cellData.getValue().spesifikasiProperty());
        spesifikasiColumn.setCellFactory(col -> Function.getWrapTableCell(spesifikasiColumn));
        
        luasTanahColumn.setCellValueFactory(cellData ->cellData.getValue().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));
        
        luasBangunanColumn.setCellValueFactory(cellData ->cellData.getValue().luasBangunanProperty());
        luasBangunanColumn.setCellFactory(col -> getTableCell(qty));
        
        nilaiPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().nilaiPropertyProperty());
        nilaiPropertyColumn.setCellFactory(col -> getTableCell(rp));
        
        hargaJualColumn.setCellValueFactory(cellData ->cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        keteranganColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        statusColumn.setCellValueFactory(cellData ->cellData.getValue().statusProperty());
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));
        
        
        final ContextMenu rm = new ContextMenu();
        MenuItem pemecahan = new MenuItem("Pemecahan Property");
        pemecahan.setOnAction((ActionEvent e)->{
            showPemecahanProperty();
        });
        MenuItem penggabungan = new MenuItem("Penggabungan Property");
        penggabungan.setOnAction((ActionEvent e)->{
            showPenggabunganProperty();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getProperty();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Pemecahan Property")&&o.isStatus())
                rm.getItems().add(pemecahan);
            if(o.getJenis().equals("Penggabungan Property")&&o.isStatus())
                rm.getItems().add(penggabungan);
        }
        rm.getItems().add(refresh);
        propertyTable.setContextMenu(rm);
        propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<Property>(){
                @Override
                public void updateItem(Property item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Property");
                        detail.setOnAction((ActionEvent e)->{
                            showDetailProperty(item);
                        });
                        MenuItem pemecahan = new MenuItem("Pemecahan Property");
                        pemecahan.setOnAction((ActionEvent e)->{
                            showPemecahanProperty();
                        });
                        MenuItem penggabungan = new MenuItem("Penggabungan Property");
                        penggabungan.setOnAction((ActionEvent e)->{
                            showPenggabunganProperty();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getProperty();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Property")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Pemecahan Property")&&o.isStatus())
                                rm.getItems().add(pemecahan);
                            if(o.getJenis().equals("Penggabungan Property")&&o.isStatus())
                                rm.getItems().add(penggabungan);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Detail Property")&&o.isStatus())
                            showDetailProperty(row.getItem());
                    }
            });
            return row;
        });
        allProperty.addListener((ListChangeListener.Change<? extends Property> change) -> {
            searchProperty();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchProperty();
        });
        filterData.addAll(allProperty);
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        for(Node n : hbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        propertyTable.setItems(filterData);
        if(!Main.sistem.getUser().getLevel().equals("Manager")){
            kodePropertyColumn.setVisible(false);
            kodeKategoriColumn.setVisible(true);
            namaPropertyColumn.setVisible(true);
            tipeColumn.setVisible(true);
            luasTanahColumn.setVisible(true);
            luasBangunanColumn.setVisible(true);
            nilaiPropertyColumn.setVisible(false);
            hargaJualColumn.setVisible(true);
            keteranganColumn.setVisible(true);
            statusColumn.setVisible(false);
            propertyTable.setTableMenuButtonVisible(false);
            
            nilaiPropertyLabel.setVisible(false);
            totalNilaiPropertyLabel.setVisible(false);
        }
        ObservableList<String> status = FXCollections.observableArrayList();
        status.addAll("Available","Reserved","Sold","Sold - Full Paid");
        statusCombo.setItems(status);
        statusCombo.getSelectionModel().select("Available");
        getProperty();
    }
    @FXML
    private void getProperty(){
        Task<List<Property>> task = new Task<List<Property>>() {
            @Override 
            public List<Property> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<String> status = new ArrayList<>();
                    status.add(statusCombo.getSelectionModel().getSelectedItem());
                    return PropertyDAO.getAllByStatus(con,status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allProperty.clear();
            allProperty.addAll(task.getValue());
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
    private void searchProperty() {
        filterData.clear();
        for (Property temp : allProperty) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeProperty())||
                    checkColumn(temp.getKodeKategori())||
                    checkColumn(temp.getNamaProperty())||
                    checkColumn(temp.getTipe())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getSpesifikasi())||
                    checkColumn(qty.format(temp.getLuasTanah()))||
                    checkColumn(qty.format(temp.getLuasBangunan()))||
                    checkColumn(rp.format(temp.getNilaiProperty()))||
                    checkColumn(rp.format(temp.getHargaJual()))||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(temp.getStatus()))
                    filterData.add(temp);
            }
        }
        hitungTotal();
    }
    private void hitungTotal(){
        double totalLuasTanah = 0;
        double totalLuasBangunan = 0;
        double totalNilaiProperty = 0;
        double totalHargaJual = 0;
        for(Property p : filterData){
            totalLuasTanah = totalLuasTanah + p.getLuasTanah();
            totalLuasBangunan = totalLuasBangunan + p.getLuasBangunan();
            totalNilaiProperty = totalNilaiProperty + p.getNilaiProperty();
            totalHargaJual = totalHargaJual + p.getHargaJual();
        }
        totalLuasTanahLabel.setText(qty.format(totalLuasTanah));
        totalLuasBangunanLabel.setText(qty.format(totalLuasBangunan));
        totalNilaiPropertyLabel.setText(rp.format(totalNilaiProperty));
        totalHargaJualLabel.setText(rp.format(totalHargaJual));
    }
    private void showDetailProperty(Property p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setProperty(p);
        x.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        p.setKodeProperty(x.kodePropertyField.getText());
                        p.setKodeKategori(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                        p.setNamaProperty(x.namaPropertyField.getText());
                        p.setTipe(x.tipeField.getText());
                        p.setAlamat(x.alamatField.getText());
                        p.setSpesifikasi(x.spesifikasiField.getText());
                        p.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                        p.setLuasBangunan(Double.parseDouble(x.luasBangunanField.getText().replaceAll(",", "")));
                        p.setNilaiProperty(Double.parseDouble(x.nilaiPropertyField.getText().replaceAll(",", "")));
                        p.setHargaJual(Double.parseDouble(x.hargaJualField.getText().replaceAll(",", "")));
                        p.setDiskon(0);
                        p.setKeterangan(x.keteranganField.getText());
                        p.setStatus(x.statusField.getText());
                        return Service.updateProperty(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                getProperty();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data property berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void showPemecahanProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PemecahanProperty.fxml");
        PemecahanPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((event) -> {
            if(x.kodePropertyField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Property yang akan dipecah, belum dipilih");
            }else if(x.allDetail.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Detail property akhir masih kosong");
            }else if(Double.parseDouble(x.totalLuasTersisaLabel.getText().replaceAll(",", ""))<0){
                mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah yang akan dipecah melebihi total luas tanah akhir");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            PemecahanPropertyHead head = new PemecahanPropertyHead();
                            head.setNoPemecahan(PemecahanPropertyHeadDAO.getId(con));
                            head.setTglPemecahan(tglSql.format(new Date()));
                            head.setKodeProperty(x.kodePropertyField.getText());
                            head.setNamaProperty(x.namaField.getText());
                            head.setTotalProperty(Double.parseDouble(x.totalPropertyLabel.getText().replaceAll(",", "")));
                            head.setTotalLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                            head.setTotalLuasEfektif(Double.parseDouble(x.totalLuasEfektifLabel.getText().replaceAll(",", "")));
                            head.setTotalLuasTersisa(Double.parseDouble(x.totalLuasTersisaLabel.getText().replaceAll(",", "")));
                            head.setNilaiProperty(Double.parseDouble(x.nilaiPropertyField.getText().replaceAll(",", "")));
                            head.setNilaiPropertyPerMeter(Double.parseDouble(x.nilaiPropertyPerMeterAkhirLabel.getText().replaceAll(",", "")));
                            head.setKodeUser(sistem.getUser().getUsername());
                            head.setStatus("true");
                            for(PemecahanPropertyDetail d : x.allDetail){
                                d.setNoPemecahan(head.getNoPemecahan());
                            }
                            head.setAllDetail(x.allDetail);
                            head.setProperty(x.property);
                            return Service.savePemecahanProperty(con, head);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    try{
                        mainApp.closeLoading();
                        getProperty();
                        if("true".equals(task.getValue())){
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Pemecahan Property berhasil disimpan");
                        }else{
                            mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                        }
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
        });
    }
    private void showPenggabunganProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PenggabunganProperty.fxml");
        PenggabunganPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((event) -> {
            if(x.allDetail.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Detail property masih kosong");
            }else if(Double.parseDouble(x.totalLuasTanahLabel.getText().replaceAll(",", ""))<=0){
                mainApp.showMessage(Modality.NONE, "Warning", "Total luas tanah kurang dari 0");
            }else{
                try{
                    Stage child = new Stage();
                    FXMLLoader childLoader = mainApp.showDialog(stage, child, "View/Dialog/DetailProperty.fxml");
                    DetailPropertyController y = childLoader.getController();
                    y.setMainApp(mainApp, stage, child);
                    y.setPenggabunganProperty(x.allDetail);
                    y.saveButton.setOnAction((ActionEvent ev)->{
                        Task<String> task = new Task<String>() {
                            @Override 
                            public String call() throws Exception{
                                try (Connection con = Koneksi.getConnection()) {
                                    PenggabunganPropertyHead head = new PenggabunganPropertyHead();
                                    head.setNoPenggabungan(PenggabunganPropertyHeadDAO.getId(con));
                                    head.setTglPenggabungan(tglSql.format(new Date()));
                                    head.setKodeProperty(y.kodePropertyField.getText());
                                    head.setTotalProperty(Double.parseDouble(x.totalPropertyLabel.getText().replaceAll(",", "")));
                                    head.setTotalLuasTanah(Double.parseDouble(x.totalLuasTanahLabel.getText().replaceAll(",", "")));
                                    head.setLuasAkhir(Double.parseDouble(y.luasTanahField.getText().replaceAll(",", "")));
                                    head.setNilaiTanah(Double.parseDouble(x.totalNilaiPropertyLabel.getText().replaceAll(",", "")));
                                    head.setNilaiTanahPerMeter(Double.parseDouble(y.nilaiPropertyPerMeterField.getText().replaceAll(",", "")));
                                    head.setKodeUser(sistem.getUser().getUsername());
                                    head.setStatus("true");

                                    Property p = new Property();
                                    p.setKodeKategori(y.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                                    p.setNamaProperty(y.namaPropertyField.getText());
                                    p.setAlamat(y.alamatField.getText());
                                    p.setTipe(y.tipeField.getText());
                                    p.setSpesifikasi(y.spesifikasiField.getText());
                                    p.setLuasTanah(Double.parseDouble(y.luasTanahField.getText().replaceAll(",", "")));
                                    p.setLuasBangunan(0);
                                    p.setNilaiProperty(Double.parseDouble(y.nilaiPropertyField.getText().replaceAll(",", "")));
                                    p.setHargaJual(Double.parseDouble(y.hargaJualField.getText().replaceAll(",", "")));
                                    p.setDiskon(0);
                                    p.setKeterangan(y.keteranganField.getText());
                                    p.setStatus("Available");

                                    head.setProperty(p);
                                    for(PenggabunganPropertyDetail d: x.allDetail){
                                        d.setNoPenggabungan(head.getNoPenggabungan());
                                    }
                                    head.setAllDetail(x.allDetail);

                                    return Service.savePenggabunganProperty(con, head);
                                }
                            }
                        };
                        task.setOnRunning((e) -> {
                            mainApp.showLoadingScreen();
                        });
                        task.setOnSucceeded((WorkerStateEvent e) -> {
                            try{
                                mainApp.closeLoading();
                                if("true".equals(task.getValue())){
                                    mainApp.closeDialog(stage, child);
                                    mainApp.closeDialog(mainApp.MainStage, stage);
                                    mainApp.showMessage(Modality.NONE, "Success", "Penggabungan Property berhasil disimpan");
                                    getProperty();
                                }else{
                                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                                }
                            }catch(Exception ex){
                                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                            }
                        });
                        task.setOnFailed((e) -> {
                            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                            mainApp.closeLoading();
                        });
                        new Thread(task).start();
                    });
                }catch(Exception ex){
                    mainApp.showMessage(Modality.NONE, "Error",ex.toString());
                }
            }
        });
    }
}
