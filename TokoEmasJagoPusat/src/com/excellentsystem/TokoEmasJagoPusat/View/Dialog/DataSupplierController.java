/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SupplierDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import com.excellentsystem.TokoEmasJagoPusat.Model.Supplier;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataSupplierController  {

    @FXML private TableView<Supplier> supplierTable;
    @FXML private TableColumn<Supplier, String> kodeSupplierColumn;
    
    @FXML private TextField kodeSupplierField;
    private ObservableList<Supplier> allSupplier = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSupplierProperty());
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getSupplier();
        });
        menu.getItems().addAll(refresh);
        supplierTable.setContextMenu(menu);
        supplierTable.setRowFactory(table -> {
            TableRow<Supplier> row = new TableRow<Supplier>(){
                @Override
                public void updateItem(Supplier item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Supplier");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteSupplier(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getSupplier();
                        });
                        rowMenu.getItems().addAll(hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeSupplierField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveSupplier();
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        supplierTable.setItems(allSupplier);
        getSupplier();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getSupplier(){
        Task<List<Supplier>> task = new Task<List<Supplier>>() {
            @Override 
            public List<Supplier> call() throws Exception{
                try(Connection con = KoneksiPusat.getConnection()){
                    return SupplierDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allSupplier.clear();
            allSupplier.addAll(task.getValue());
            supplierTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void deleteSupplier(Supplier s){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus Supplier "+s.getKodeSupplier()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = KoneksiPusat.getConnection()){
                        return Service.deleteSupplier(con, s);
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
                    kodeSupplierField.setText("");
                    mainApp.showMessage(Modality.NONE, "Success", "Supplier berhasil dihapus");
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
    @FXML
    private void saveSupplier(){
        if(kodeSupplierField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode supplier masih kosong");
        else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = KoneksiPusat.getConnection()){
                        Supplier s = new Supplier();
                        s.setKodeSupplier(kodeSupplierField.getText());
                        return Service.saveNewSupplier(con, s);
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
                    mainApp.showMessage(Modality.NONE, "Success", "Supplier berhasil disimpan");
                    kodeSupplierField.setText("");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        } 
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
