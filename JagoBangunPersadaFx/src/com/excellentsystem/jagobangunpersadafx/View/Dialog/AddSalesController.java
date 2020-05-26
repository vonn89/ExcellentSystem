/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
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
public class AddSalesController {
    @FXML public TableView<Karyawan> karyawanTable;
    @FXML private TableColumn<Karyawan, String> kodeKaryawanColumn;
    @FXML private TableColumn<Karyawan, String> namaColumn;
    @FXML private TableColumn<Karyawan, String> jabatanColumn;
    @FXML private TableColumn<Karyawan, String> alamatColumn;
    @FXML private TableColumn<Karyawan, String> noTelpColumn;
    @FXML private TableColumn<Karyawan, String> noHandphoneColumn;
    @FXML private TableColumn<Karyawan, String> statusNikahColumn;
    @FXML private TableColumn<Karyawan, String> agamaColumn;
    @FXML private TableColumn<Karyawan, String> identitasColumn;
    @FXML private TableColumn<Karyawan, String> noIdentitasColumn;
    @FXML private TextField searchField;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    private ObservableList<Karyawan> allKaryawan = FXCollections.observableArrayList();
    private ObservableList<Karyawan> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeKaryawanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKaryawanProperty());
        kodeKaryawanColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKaryawanColumn));
        
        namaColumn.setCellValueFactory(cellData ->cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        jabatanColumn.setCellValueFactory(cellData -> cellData.getValue().jabatanProperty());
        jabatanColumn.setCellFactory(col -> Function.getWrapTableCell(jabatanColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));
        
        statusNikahColumn.setCellValueFactory(cellData ->cellData.getValue().statusNikahProperty());
        statusNikahColumn.setCellFactory(col -> Function.getWrapTableCell(statusNikahColumn));
        
        agamaColumn.setCellValueFactory(cellData ->cellData.getValue().agamaProperty());
        agamaColumn.setCellFactory(col -> Function.getWrapTableCell(agamaColumn));
        
        identitasColumn.setCellValueFactory(cellData ->cellData.getValue().identitasProperty());
        identitasColumn.setCellFactory(col -> Function.getWrapTableCell(identitasColumn));
        
        noIdentitasColumn.setCellValueFactory(cellData ->cellData.getValue().noIdentitasProperty());
        noIdentitasColumn.setCellFactory(col -> Function.getWrapTableCell(noIdentitasColumn));
        
        allKaryawan.addListener((ListChangeListener.Change<? extends Karyawan> change) -> {
            searchKaryawan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKaryawan();
        });
        filterData.addAll(allKaryawan);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        karyawanTable.setItems(filterData);
        getKaryawan();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*80/100);
        stage.setWidth(mainApp.screenSize.getWidth()*80/100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getKaryawan(){
        Task<List<Karyawan>> task = new Task<List<Karyawan>>() {
            @Override 
            public List<Karyawan> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return KaryawanDAO.getAllByStatus(con,"true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allKaryawan.clear();
                allKaryawan.addAll(task.getValue());
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
    private void searchKaryawan() {
        filterData.clear();
        for (Karyawan temp : allKaryawan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeKaryawan())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getJabatan())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getStatusNikah())||
                    checkColumn(temp.getAgama())||
                    checkColumn(temp.getIdentitas())||
                    checkColumn(temp.getNoIdentitas()))
                    filterData.add(temp);
            }
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
