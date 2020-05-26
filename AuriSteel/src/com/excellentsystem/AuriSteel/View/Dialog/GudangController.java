/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.GudangDAO;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Services.Service;
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
 * @author Yunaz
 */
public class GudangController  {

    @FXML private TableView<Gudang> gudangTable;
    @FXML private TableColumn<Gudang, String> kodeGudangColumn;
    
    @FXML private TextField kodeGudangField;
    
    private ObservableList<Gudang> allGudang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            gudangTable.refresh();
        });
        rm.getItems().addAll(refresh);
        gudangTable.setContextMenu(rm);
        gudangTable.setRowFactory(table -> {
            TableRow<Gudang> row = new TableRow<Gudang>(){
                @Override
                public void updateItem(Gudang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Gudang");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteGudang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getGudang();
                        });
                        rm.getItems().addAll(hapus,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        kodeGudangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveGudang();
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        gudangTable.setItems(allGudang);
        getGudang();
    }
    private void getGudang(){
        Task<List<Gudang>> task = new Task<List<Gudang>>() {
            @Override 
            public List<Gudang> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return GudangDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allGudang.clear();
            allGudang.addAll(task.getValue());
            gudangTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void deleteGudang(Gudang temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Gudang "+temp.getKodeGudang()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        return Service.deleteGudang(con, temp);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getGudang();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Gudang berhasil dihapus");
                    kodeGudangField.setText("");
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
    private void saveGudang(){
        if(kodeGudangField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang masih kosong");
        else{
            Boolean s = true;
            for(Gudang k : allGudang){
                if(k.getKodeGudang().equals(kodeGudangField.getText()))
                    s = false;
            }
            if(s){
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = Koneksi.getConnection()){
                            Gudang k = new Gudang();
                            k.setKodeGudang(kodeGudangField.getText());
                            return Service.newGudang(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getGudang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Gudang berhasil disimpan");
                        kodeGudangField.setText("");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }else{
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Kategori sudah terdaftar");
            }
        }
    } 
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
