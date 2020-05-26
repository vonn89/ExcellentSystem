/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Services.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
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
public class KategoriBahanController  {

    @FXML private TableView<KategoriBahan> kategoriBahanTable;
    @FXML private TableColumn<KategoriBahan, String> kodeKategoriColumn;
    
    @FXML private TextField kodeKategoriBahanField;
    
    private ObservableList<KategoriBahan> allKategoriBahan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            kategoriBahanTable.refresh();
        });
        rm.getItems().addAll(refresh);
        kategoriBahanTable.setContextMenu(rm);
        kategoriBahanTable.setRowFactory(table -> {
            TableRow<KategoriBahan> row = new TableRow<KategoriBahan>(){
                @Override
                public void updateItem(KategoriBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Bahan");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriBahan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriBahan();
                        });
                        rm.getItems().addAll(hapus,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        kodeKategoriBahanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveKategoriBahan();
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        kategoriBahanTable.setItems(allKategoriBahan);
        getKategoriBahan();
    }
    private void getKategoriBahan(){
        Task<List<KategoriBahan>> task = new Task<List<KategoriBahan>>() {
            @Override 
            public List<KategoriBahan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return KategoriBahanDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKategoriBahan.clear();
            allKategoriBahan.addAll(task.getValue());
            kategoriBahanTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void deleteKategoriBahan(KategoriBahan temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Bahan "+temp.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        return Service.deleteKategoriBahan(con, temp);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategoriBahan();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori Bahan berhasil dihapus");
                    kodeKategoriBahanField.setText("");
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
    private void saveKategoriBahan(){
        if(kodeKategoriBahanField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else{
            Boolean s = true;
            for(KategoriBahan k : allKategoriBahan){
                if(k.getKodeKategori().equals(kodeKategoriBahanField.getText()))
                    s = false;
            }
            if(s){
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = Koneksi.getConnection()){
                            KategoriBahan k = new KategoriBahan();
                            k.setKodeKategori(kodeKategoriBahanField.getText());
                            k.setStatus("true");
                            return Service.newKategoriBahan(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategoriBahan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Bahan berhasil disimpan");
                        kodeKategoriBahanField.setText("");
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
