/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailSupplierController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import java.sql.Connection;
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
public class DataSupplierController  {
    @FXML private TableView<Supplier> supplierTable;
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
    @FXML private TableColumn<Supplier, Number> hutangColumn;
    @FXML private TextField searchField;
    
    private final ObservableList<Supplier> allSupplier = FXCollections.observableArrayList();
    private final ObservableList<Supplier> filterData = FXCollections.observableArrayList();
    private Main mainApp;      
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
        hutangColumn.setCellValueFactory(cellData -> cellData.getValue().hutangProperty());
        hutangColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Supplier");
        addNew.setOnAction((ActionEvent e)->{
            newSupplier();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getSupplier();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Supplier")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        supplierTable.setContextMenu(rm);
        supplierTable.setRowFactory((TableView<Supplier> tableView) -> {
            final TableRow<Supplier> row = new TableRow<Supplier>(){
                @Override
                public void updateItem(Supplier item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Supplier");
                        addNew.setOnAction((ActionEvent e)->{
                            newSupplier();
                        });
                        MenuItem edit = new MenuItem("Edit Supplier");
                        edit.setOnAction((ActionEvent e)->{
                            editSupplier(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Supplier");
                        hapus.setOnAction((ActionEvent e)->{
                            deleteSupplier(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getSupplier();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Supplier")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Edit Supplier")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Supplier")&&o.isStatus())
                                rm.getItems().add(hapus);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Supplier")&&o.isStatus())
                                editSupplier(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allSupplier.addListener((ListChangeListener.Change<? extends Supplier> change) -> {
            searchSupplier();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchSupplier();
        });
        filterData.addAll(allSupplier);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getSupplier();
        supplierTable.setItems(filterData);
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
        task.setOnSucceeded((WorkerStateEvent e) -> {
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
                    checkColumn(temp.getNoRekening())||
                    checkColumn(df.format(temp.getDeposit()))||
                    checkColumn(df.format(temp.getHutang())))
                    filterData.add(temp);
            }
        }
    }
    private void newSupplier(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSupplier.fxml");
        DetailSupplierController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSupplierDetail(null);
        x.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Supplier supplier = new Supplier();
                        supplier.setKodeSupplier(SupplierDAO.getId(con));
                        supplier.setNama(x.namaField.getText());
                        supplier.setAlamat(x.alamatField.getText());
                        supplier.setKota(x.kotaField.getText());
                        supplier.setNegara(x.negaraField.getText());
                        supplier.setKodePos(x.kodePosField.getText());
                        supplier.setEmail(x.emailField.getText());
                        supplier.setKontakPerson(x.kontakPersonField.getText());
                        supplier.setNoTelp(x.noTelpField.getText());
                        supplier.setNoHandphone(x.noHandphoneField.getText());
                        supplier.setBank(x.bankField.getText());
                        supplier.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                        supplier.setNoRekening(x.noRekeningField.getText());
                        supplier.setDeposit(0);
                        supplier.setHutang(0);
                        supplier.setStatus("true");
                        return Service.saveNewSupplier(con, supplier);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getSupplier();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data supplier berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
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
    private void editSupplier(Supplier supplier){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSupplier.fxml");
        DetailSupplierController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSupplierDetail(supplier);
        x.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        supplier.setNama(x.namaField.getText());
                        supplier.setAlamat(x.alamatField.getText());
                        supplier.setKota(x.kotaField.getText());
                        supplier.setNegara(x.negaraField.getText());
                        supplier.setKodePos(x.kodePosField.getText());
                        supplier.setEmail(x.emailField.getText());
                        supplier.setKontakPerson(x.kontakPersonField.getText());
                        supplier.setNoTelp(x.noTelpField.getText());
                        supplier.setNoHandphone(x.noHandphoneField.getText());
                        supplier.setBank(x.bankField.getText());
                        supplier.setAtasNamaRekening(x.atasNamaRekeningField.getText());
                        supplier.setNoRekening(x.noRekeningField.getText());
                        supplier.setDeposit(Double.parseDouble(x.depositField.getText().replaceAll(",", "")));
                        supplier.setHutang(Double.parseDouble(x.hutangField.getText().replaceAll(",", "")));
                        supplier.setStatus("true");
                        return Service.saveUpdateSupplier(con, supplier);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getSupplier();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data supplier berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage, stage);
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
    private void deleteSupplier(Supplier supplier){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete supplier "+supplier.getKodeSupplier()+"-"+supplier.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ec) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteSupplier(con, supplier);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getSupplier();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data supplier berhasil dihapus");
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
