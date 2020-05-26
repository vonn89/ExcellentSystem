/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
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
public class AddCustomerController  {

    public TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> kodeCustomerColumn;
    @FXML private TableColumn<Customer, String> namaCustomerColumn;
    @FXML private TableColumn<Customer, String> jenisKelaminColumn;
    @FXML private TableColumn<Customer, String> alamatColumn;
    @FXML private TableColumn<Customer, String> noTelpColumn;
    @FXML private TableColumn<Customer, String> noHandphoneColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private TableColumn<Customer, String> statusNikahColumn;
    @FXML private TableColumn<Customer, String> agamaColumn;
    @FXML private TableColumn<Customer, String> pekerjaanColumn;
    @FXML private TableColumn<Customer, String> noKTPColumn;
    @FXML private TableColumn<Customer, String> noNPWPColumn;
    @FXML private TableColumn<Customer, String> noSPTPPHColumn;
    
    @FXML private TextField searchField;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    private ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
    private ObservableList<Customer> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        kodeCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(kodeCustomerColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData ->cellData.getValue().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        jenisKelaminColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKelaminProperty());
        jenisKelaminColumn.setCellFactory(col -> Function.getWrapTableCell(jenisKelaminColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));
        
        emailColumn.setCellValueFactory(cellData ->cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));
        
        statusNikahColumn.setCellValueFactory(cellData ->cellData.getValue().statusNikahProperty());
        statusNikahColumn.setCellFactory(col -> Function.getWrapTableCell(statusNikahColumn));
        
        agamaColumn.setCellValueFactory(cellData ->cellData.getValue().agamaProperty());
        agamaColumn.setCellFactory(col -> Function.getWrapTableCell(agamaColumn));
        
        pekerjaanColumn.setCellValueFactory(cellData ->cellData.getValue().pekerjaanProperty());
        pekerjaanColumn.setCellFactory(col -> Function.getWrapTableCell(pekerjaanColumn));
        
        noKTPColumn.setCellValueFactory(cellData ->cellData.getValue().noKTPProperty());
        noKTPColumn.setCellFactory(col -> Function.getWrapTableCell(noKTPColumn));
        
        noNPWPColumn.setCellValueFactory(cellData ->cellData.getValue().noNPWPProperty());
        noNPWPColumn.setCellFactory(col -> Function.getWrapTableCell(noNPWPColumn));
        
        noSPTPPHColumn.setCellValueFactory(cellData ->cellData.getValue().noSPTPPHProperty());
        noSPTPPHColumn.setCellFactory(col -> Function.getWrapTableCell(noSPTPPHColumn));
        
        
        allCustomer.addListener((ListChangeListener.Change<? extends Customer> change) -> {
            searchCustomer();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchCustomer();
        });
        filterData.addAll(allCustomer);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        customerTable.setItems(filterData);
        getCustomer();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*80/100);
        stage.setWidth(mainApp.screenSize.getWidth()*80/100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getCustomer(){
        Task<List<Customer>> task = new Task<List<Customer>>() {
            @Override 
            public List<Customer> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return CustomerDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allCustomer.clear();
                allCustomer.addAll(task.getValue());
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
    private void searchCustomer() {
        filterData.clear();
        for (Customer temp : allCustomer) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeCustomer())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getJenisKelamin())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getStatusNikah())||
                    checkColumn(temp.getAgama())||
                    checkColumn(temp.getPekerjaan())||
                    checkColumn(temp.getNoKTP())||
                    checkColumn(temp.getNoNPWP())||
                    checkColumn(temp.getNoSPTPPH()))
                    filterData.add(temp);
            }
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
