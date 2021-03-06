/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Pegawai;
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
public class AddCustomerController  {

    @FXML public TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> kodeCustomerColumn;
    @FXML private TableColumn<Customer, String> namaColumn;
    @FXML private TableColumn<Customer, String> alamatColumn;
    @FXML private TableColumn<Customer, String> kotaColumn;
    @FXML private TableColumn<Customer, String> negaraColumn;
    @FXML private TableColumn<Customer, String> kodePosColumn;
    @FXML private TableColumn<Customer, String> emailColumn;
    @FXML private TableColumn<Customer, String> kontakPersonColumn;
    @FXML private TableColumn<Customer, String> noTelpColumn;
    @FXML private TableColumn<Customer, String> noHandphoneColumn;
    @FXML private TableColumn<Customer, String> kodeSalesColumn;
    @FXML private TableColumn<Customer, String> namaSalesColumn;
    @FXML private TableColumn<Customer, String> bankColumn;
    @FXML private TableColumn<Customer, String> atasNamaRekeningColumn;
    @FXML private TableColumn<Customer, String> noRekeningColumn;
    @FXML private TableColumn<Customer, Number> limitHutangColumn;
    @FXML private TableColumn<Customer, Number> hutangColumn;
    @FXML private TableColumn<Customer, Number> depositColumn;
    @FXML private TextField searchField;
    private ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
    private ObservableList<Customer> filterData = FXCollections.observableArrayList();
    private Main mainApp; 
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        negaraColumn.setCellValueFactory(cellData -> cellData.getValue().negaraProperty());
        kodePosColumn.setCellValueFactory(cellData -> cellData.getValue().kodePosProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        kontakPersonColumn.setCellValueFactory(cellData ->cellData.getValue().kontakPersonProperty());
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        kodeSalesColumn.setCellValueFactory(cellData ->cellData.getValue().kodeSalesProperty());
        namaSalesColumn.setCellValueFactory(cellData ->cellData.getValue().getSales().namaProperty());
        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        atasNamaRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().atasNamaRekeningProperty());
        noRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().noRekeningProperty());
        limitHutangColumn.setCellValueFactory(cellData -> cellData.getValue().limitHutangProperty());
        limitHutangColumn.setCellFactory(col -> Function.getTableCell());
        hutangColumn.setCellValueFactory(cellData -> cellData.getValue().hutangProperty());
        hutangColumn.setCellFactory(col -> Function.getTableCell());
        depositColumn.setCellValueFactory(cellData -> cellData.getValue().depositProperty());
        depositColumn.setCellFactory(col -> Function.getTableCell());
        allCustomer.addListener((ListChangeListener.Change<? extends Customer> change) -> {
            searchCustomer();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchCustomer();
        });
        filterData.addAll(allCustomer);
        customerTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getCustomer();
    }
    private void getCustomer(){
        Task<List<Customer>> task = new Task<List<Customer>>() {
            @Override 
            public List<Customer> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "true");
                    for(Customer c : allCustomer){
                        for(Pegawai s : allSales){
                            if(c.getKodeSales().equals(s.getKodePegawai()))
                                c.setSales(s);
                        }
                    }
                    return allCustomer;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allCustomer.clear();
            allCustomer.addAll(task.getValue());
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
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getKodePos())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getKontakPerson())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getKodeSales())||
                    checkColumn(temp.getSales().getNama())||
                    checkColumn(temp.getBank())||
                    checkColumn(temp.getAtasNamaRekening())||
                    checkColumn(temp.getNoRekening())||
                    checkColumn(df.format(temp.getLimitHutang()))||
                    checkColumn(df.format(temp.getHutang()))||
                    checkColumn(df.format(temp.getDeposit()))
                        )
                    filterData.add(temp);
            }
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
