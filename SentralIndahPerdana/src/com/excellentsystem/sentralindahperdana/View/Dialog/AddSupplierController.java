/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
public class AddSupplierController  {

    @FXML public TableView<Supplier> supplierTable;
    @FXML private TableColumn<Supplier, String> kodeSupplierColumn;
    @FXML private TableColumn<Supplier, String> namaColumn;
    @FXML private TableColumn<Supplier, String> alamatColumn;
    @FXML private TableColumn<Supplier, String> kotaColumn;
    @FXML private TableColumn<Supplier, String> negaraColumn;
    @FXML private TableColumn<Supplier, String> kodePosColumn;
    @FXML private TableColumn<Supplier, String> emailColumn;
    @FXML private TableColumn<Supplier, String> kontakPersonColumn;
    @FXML private TableColumn<Supplier, String> noTelpColumn;
    @FXML private TableColumn<Supplier, String> noHandphoneColumn;
    @FXML private TableColumn<Supplier, String> bankColumn;
    @FXML private TableColumn<Supplier, String> atasNamaRekeningColumn;
    @FXML private TableColumn<Supplier, String> noRekeningColumn;
    
    @FXML private TextField searchField;
    private ObservableList<Supplier> allSupplier = FXCollections.observableArrayList();
    private ObservableList<Supplier> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSupplierProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        negaraColumn.setCellValueFactory(cellData -> cellData.getValue().negaraProperty());
        kodePosColumn.setCellValueFactory(cellData -> cellData.getValue().kodePosProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        kontakPersonColumn.setCellValueFactory(cellData ->cellData.getValue().kontakPersonProperty());
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        atasNamaRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().atasNamaRekeningProperty());
        noRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().noRekeningProperty());
        
        allSupplier.addListener((ListChangeListener.Change<? extends Supplier> change) -> {
            searchSupplier();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchSupplier();
        });
        filterData.addAll(allSupplier);
        supplierTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getSupplier();
    }
    private void getSupplier(){
        Task<List<Supplier>> task = new Task<List<Supplier>>() {
            @Override 
            public List<Supplier> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return SupplierDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded(( e) -> {
            try{
                mainApp.closeLoading();
                allSupplier.clear();
                allSupplier.addAll(task.get());
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
    private void searchSupplier() {
        filterData.clear();
        for (Supplier temp : allSupplier) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeSupplier())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getKodePos())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getKontakPerson())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getBank())||
                    checkColumn(temp.getAtasNamaRekening())||
                    checkColumn(temp.getNoRekening()))
                    filterData.add(temp);
            }
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
