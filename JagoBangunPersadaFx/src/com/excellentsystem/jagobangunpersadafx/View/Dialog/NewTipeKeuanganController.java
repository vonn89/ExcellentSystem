/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.TipeKeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.TipeKeuangan;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewTipeKeuanganController  {
    @FXML private TableView<TipeKeuangan> tipeKeuanganTable;
    @FXML private TableColumn<TipeKeuangan, String> kodeKeuanganColumn;
    @FXML private TextField tipeKeuanganField;
    private final ObservableList<TipeKeuangan> allTipeKeuangan= FXCollections.observableArrayList();
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        kodeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKeuanganProperty());

        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTipeKeuangan();
        });
        rm.getItems().addAll(refresh);
        tipeKeuanganTable.setContextMenu(rm);
        tipeKeuanganTable.setRowFactory(table -> {
            TableRow<TipeKeuangan> row = new TableRow<TipeKeuangan>(){
                @Override
                public void updateItem(TipeKeuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Tipe Keuangan");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteTipeKeuangan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getTipeKeuangan();
                        });
                        rm.getItems().addAll(hapus,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        tipeKeuanganTable.setItems(allTipeKeuangan);
        getTipeKeuangan();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getTipeKeuangan(){
        Task<List<TipeKeuangan>> task = new Task<List<TipeKeuangan>>() {
            @Override 
            public List<TipeKeuangan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return TipeKeuanganDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allTipeKeuangan.clear();
            allTipeKeuangan.addAll(task.getValue());
            tipeKeuanganTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void deleteTipeKeuangan(TipeKeuangan t){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return Service.deleteKategoriKeuangan(con, t);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            getTipeKeuangan();
            if(task.getValue().equals("true"))
                mainApp.showMessage(Modality.NONE, "Success", "Tipe keuangan berhasil dihapus");
            else
                mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void saveTipeKeuangan(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    TipeKeuangan t = new TipeKeuangan();
                    t.setKodeKeuangan(tipeKeuanganField.getText());
                    return Service.newKategoriKeuangan(con, t);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            getTipeKeuangan();
            if(task.getValue().equals("true"))
                mainApp.showMessage(Modality.NONE, "Success", "Tambah Tipe Keuangan berhasil disimpan");
            else
                mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }   
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
