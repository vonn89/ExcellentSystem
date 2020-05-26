/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
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
public class DataCustomerController {
    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> kodeCustomerColumn;
    @FXML private TableColumn<Customer, String> namaColumn;
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
    private ObservableList<Customer> allCustomer = FXCollections.observableArrayList();
    private ObservableList<Customer> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCustomerProperty());
        kodeCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(kodeCustomerColumn));
        
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        jenisKelaminColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKelaminProperty());
        jenisKelaminColumn.setCellFactory(col -> Function.getWrapTableCell(jenisKelaminColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));
        
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));
        
        statusNikahColumn.setCellValueFactory(cellData ->cellData.getValue().statusNikahProperty());
        statusNikahColumn.setCellFactory(col -> Function.getWrapTableCell(statusNikahColumn));
        
        agamaColumn.setCellValueFactory(cellData ->cellData.getValue().agamaProperty());
        agamaColumn.setCellFactory(col -> Function.getWrapTableCell(agamaColumn));
        
        pekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().pekerjaanProperty());
        pekerjaanColumn.setCellFactory(col -> Function.getWrapTableCell(pekerjaanColumn));
        
        noKTPColumn.setCellValueFactory(cellData -> cellData.getValue().noKTPProperty());
        noKTPColumn.setCellFactory(col -> Function.getWrapTableCell(noKTPColumn));
        
        noNPWPColumn.setCellValueFactory(cellData -> cellData.getValue().noNPWPProperty());
        noNPWPColumn.setCellFactory(col -> Function.getWrapTableCell(noNPWPColumn));
        
        noSPTPPHColumn.setCellValueFactory(cellData -> cellData.getValue().noSPTPPHProperty());
        noSPTPPHColumn.setCellFactory(col -> Function.getWrapTableCell(noSPTPPHColumn));
        
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
        rm.getItems().add(refresh);
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
                        MenuItem edit = new MenuItem("Detail Customer");
                        edit.setOnAction((ActionEvent e)->{
                            updateCustomer(item);
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
                            if(o.getJenis().equals("Detail Customer")&&o.isStatus())
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
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Customer")&&o.isStatus())
                                updateCustomer(row.getItem());
                        }
                }
            });
            return row;
        });
        allCustomer.addListener((ListChangeListener.Change<? extends Customer> change) -> {
            searchCustomer();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
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
                    checkColumn(temp.getJenisKelamin())||
                    checkColumn(temp.getAgama())||
                    checkColumn(temp.getStatusNikah())||
                    checkColumn(temp.getPekerjaan())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getNoKTP())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getNoNPWP())||
                    checkColumn(temp.getNoSPTPPH())||
                    checkColumn(temp.getStatus()))
                    filterData.add(temp);
            }
        }
    }
    private void newCustomer(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.namaField.getText().equals("")||x.namaField.getText()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Customer c = new Customer();
                            c.setKodeCustomer(CustomerDAO.getId(con));
                            c.setAgama(x.agamaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setJenisKelamin(x.jenisKelaminCombo.getSelectionModel().getSelectedItem());
                            c.setNama(x.namaField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setNoKTP(x.noKTPField.getText());
                            c.setNoNPWP(x.noNPWPField.getText());
                            c.setNoSPTPPH(x.noSPTPPHField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setPekerjaan(x.pekerjaanField.getText());
                            c.setStatusNikah(x.statusNikahCombo.getSelectionModel().getSelectedItem());
                            c.setStatus("true");
                            return Service.newCustomer(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage,stage);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void updateCustomer(Customer c){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setCustomer(c);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.namaField.getText().equals("")||x.namaField.getText()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            c.setAgama(x.agamaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setJenisKelamin(x.jenisKelaminCombo.getSelectionModel().getSelectedItem());
                            c.setNama(x.namaField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setNoKTP(x.noKTPField.getText());
                            c.setNoNPWP(x.noNPWPField.getText());
                            c.setNoSPTPPH(x.noSPTPPHField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setPekerjaan(x.pekerjaanField.getText());
                            c.setStatusNikah(x.statusNikahCombo.getSelectionModel().getSelectedItem());
                            c.setStatus("true");
                            return Service.updateCustomer(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getCustomer();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage,stage);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
                        Boolean status = true;
                        for(STJHead head :  STJHeadDAO.getAllByDateAndStatus(con,"2000-01-01", tglBarang.format(new Date()), "true")){
                            if(head.getKodeCustomer().equals(c.getKodeCustomer()))
                                status = false;
                        }
                        if(status){
                            c.setStatus("false");
                            return Service.updateCustomer(con, c);
                        }
                        else
                            return "tidak dapat dihapus, karena masih ada penjualan dengan customer "+c.getNama();
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCustomer();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data customer berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
