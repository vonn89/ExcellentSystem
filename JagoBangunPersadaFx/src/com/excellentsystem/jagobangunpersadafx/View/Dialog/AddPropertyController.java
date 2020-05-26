/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
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
public class AddPropertyController {
    
    @FXML public TableView<Property> propertyTable;
    @FXML private TableColumn<Property, String> kodePropertyColumn;
    @FXML private TableColumn<Property, String> kodeKategoriColumn;
    @FXML private TableColumn<Property, String> namaPropertyColumn;
    @FXML private TableColumn<Property, String> alamatColumn;
    @FXML private TableColumn<Property, String> tipeColumn;
    @FXML private TableColumn<Property, String> spesifikasiColumn;
    @FXML private TableColumn<Property, Number> luasTanahColumn;
    @FXML private TableColumn<Property, Number> luasBangunanColumn;
    @FXML private TableColumn<Property, Number> hargaJualColumn;
    @FXML private TableColumn<Property, String> keteranganColumn;
    @FXML private TableColumn<Property, String> statusColumn;
    
    @FXML private TextField searchField;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
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
        
        hargaJualColumn.setCellValueFactory(cellData ->cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        keteranganColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        statusColumn.setCellValueFactory(cellData ->cellData.getValue().statusProperty());
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));
        
        allProperty.addListener((ListChangeListener.Change<? extends Property> change) -> {
            searchProperty();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchProperty();
        });
        filterData.addAll(allProperty);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        propertyTable.setItems(filterData);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*80/100);
        stage.setWidth(mainApp.screenSize.getWidth()*80/100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getProperty(List<String> status){
        Task<List<Property>> task = new Task<List<Property>>() {
            @Override 
            public List<Property> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PropertyDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allProperty.clear();
                allProperty.addAll(task.getValue());
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
    private void searchProperty() {
        filterData.clear();
        for (Property temp : allProperty) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeProperty())||
                    checkColumn(temp.getKodeKategori())||
                    checkColumn(temp.getNamaProperty())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getTipe())||
                    checkColumn(temp.getSpesifikasi())||
                    checkColumn(qty.format(temp.getLuasTanah()))||
                    checkColumn(qty.format(temp.getLuasBangunan()))||
                    checkColumn(rp.format(temp.getHargaJual()))||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(temp.getStatus()))
                    filterData.add(temp);
            }
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner,stage);
    }
}
