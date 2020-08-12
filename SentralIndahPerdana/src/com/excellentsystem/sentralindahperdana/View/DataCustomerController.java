/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailCustomerController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataCustomerController  {
    @FXML private TableView<Customer> customerTable;
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
    @FXML private TableColumn<Customer, String> bankColumn;
    @FXML private TableColumn<Customer, String> atasNamaRekeningColumn;
    @FXML private TableColumn<Customer, String> noRekeningColumn;
    @FXML private TableColumn<Customer, Number> limitHutangColumn;
    @FXML private TableColumn<Customer, Number> hutangColumn;
    @FXML private TableColumn<Customer, Number> depositColumn;
    @FXML private TextField searchField;
    private Main mainApp;  
    private final ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
    private final ObservableList<Customer> filterData = FXCollections.observableArrayList();
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
        bankColumn.setCellValueFactory(cellData -> cellData.getValue().bankProperty());
        atasNamaRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().atasNamaRekeningProperty());
        noRekeningColumn.setCellValueFactory(cellData -> cellData.getValue().noRekeningProperty());
        limitHutangColumn.setCellValueFactory(cellData -> cellData.getValue().limitHutangProperty());
        limitHutangColumn.setCellFactory(col -> Function.getTableCell());
        hutangColumn.setCellValueFactory(cellData -> cellData.getValue().hutangProperty());
        hutangColumn.setCellFactory(col -> Function.getTableCell());
        depositColumn.setCellValueFactory(cellData -> cellData.getValue().depositProperty());
        depositColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Customer");
        addNew.setOnAction((ActionEvent e)->{
            newCustomer();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getCustomer();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Customer")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        customerTable.setContextMenu(rm);
        customerTable.setRowFactory((TableView<Customer> tableView) -> {
            final TableRow<Customer> row = new TableRow<Customer>(){
                @Override
                public void updateItem(Customer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Customer");
                        addNew.setOnAction((ActionEvent e)->{
                            newCustomer();
                        });
                        MenuItem edit = new MenuItem("Edit Customer");
                        edit.setOnAction((ActionEvent e)->{
                            editCustomer(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Customer");
                        hapus.setOnAction((ActionEvent e)->{
                            deleteCustomer(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getCustomer();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Customer")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Edit Customer")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Customer")&&o.isStatus())
                                rm.getItems().add(hapus);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                    mouseEvent.getClickCount() == 2&&
                    row.getItem()!=null)
                    for(Otoritas o : sistem.getUser().getOtoritas())
                        if(o.getJenis().equals("Edit Customer")&&o.isStatus())
                            editCustomer(row.getItem());
            });
            return row;
        });
        allCustomer.addListener((ListChangeListener.Change<? extends Customer> change) -> {
            searchCustomer();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchCustomer();
        });
        filterData.addAll(allCustomer);
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getCustomer();
        customerTable.setItems(filterData);
    }
    private void getCustomer(){
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
                allCustomer.addAll(task.get());
            }catch(InterruptedException | ExecutionException ex){
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
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getNegara())||
                    checkColumn(temp.getKodePos())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getKontakPerson())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getBank())||
                    checkColumn(temp.getAtasNamaRekening())||
                    checkColumn(temp.getNoRekening())||
                    checkColumn(df.format(temp.getLimitHutang()))||
                    checkColumn(df.format(temp.getHutang()))||
                    checkColumn(df.format(temp.getDeposit())))
                    filterData.add(temp);
            }
        }
    }
    private void newCustomer(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomerDetail(null);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.limitHutangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Limit hutang masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Customer customer = new Customer();
                            customer.setKodeCustomer(CustomerDAO.getId(con));
                            customer.setNama(x.namaField.getText());
                            customer.setAlamat(x.alamatField.getText());
                            customer.setKota(x.kotaField.getText());
                            customer.setNegara(x.negaraField.getText());
                            customer.setKodePos(x.kodePosField.getText());
                            customer.setEmail(x.emailField.getText());
                            customer.setKontakPerson(x.kontakPersonField.getText());
                            customer.setNoTelp(x.noTelpField.getText());
                            customer.setNoHandphone(x.noHandphoneField.getText());
                            customer.setBank(x.bankField.getText());
                            customer.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                            customer.setNoRekening(x.noRekeningField.getText());
                            customer.setLimitHutang(Double.parseDouble(x.limitHutangField.getText().replaceAll(",", "")));
                            customer.setHutang(Double.parseDouble(x.hutangField.getText().replaceAll(",", "")));
                            customer.setDeposit(Double.parseDouble(x.depositField.getText().replaceAll(",", "")));
                            customer.setStatus("true");
                            return Service.saveNewCustomer(con, customer);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void editCustomer(Customer c){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setCustomerDetail(c);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.limitHutangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Limit hutang masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            c.setNama(x.namaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setKota(x.kotaField.getText());
                            c.setNegara(x.negaraField.getText());
                            c.setKodePos(x.kodePosField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setKontakPerson(x.kontakPersonField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setBank(x.bankField.getText());
                            c.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                            c.setNoRekening(x.noRekeningField.getText());
                            c.setLimitHutang(Double.parseDouble(x.limitHutangField.getText().replaceAll(",", "")));
                            c.setHutang(Double.parseDouble(x.hutangField.getText().replaceAll(",", "")));
                            c.setDeposit(Double.parseDouble(x.depositField.getText().replaceAll(",", "")));
                            c.setStatus("true");
                            return Service.saveUpdateCustomer(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void deleteCustomer(Customer c){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete customer "+c.getKodeCustomer()+"-"+c.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteCustomer(con, c);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCustomer();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
