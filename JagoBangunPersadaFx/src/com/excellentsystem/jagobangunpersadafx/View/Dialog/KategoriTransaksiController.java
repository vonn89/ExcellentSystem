/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KategoriTransaksiDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriTransaksi;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class KategoriTransaksiController  {

    @FXML private TableView<KategoriTransaksi> kategoriTransaksiTable;
    @FXML private TableColumn<KategoriTransaksi, String> kodeKategoriColumn;
    @FXML private TableColumn<KategoriTransaksi, String> jenisTransaksiColumn;
    
    @FXML private TextField kodeKategoriField;
    @FXML private ComboBox<String> jenisTransaksiCombo;
    private ObservableList<KategoriTransaksi> allKategoriTransaksi= FXCollections.observableArrayList();
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        jenisTransaksiColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getJenisTransaksi().equals("D"))
                return new SimpleStringProperty("Debet");
            else if(cellData.getValue().getJenisTransaksi().equals("K"))
                return new SimpleStringProperty("Kredit");
            else 
                return null;
        });
        kodeKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                jenisTransaksiCombo.requestFocus();
            }
        });
        jenisTransaksiCombo.setOnKeyPressed((KeyEvent keyEvent) ->{
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveKategoriTransaksi();
            }
        });
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKategoriTransaksi();
        });
        rm.getItems().addAll(refresh);
        kategoriTransaksiTable.setContextMenu(rm);
        kategoriTransaksiTable.setRowFactory(table -> {
            TableRow<KategoriTransaksi> row = new TableRow<KategoriTransaksi>(){
                @Override
                public void updateItem(KategoriTransaksi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Transaksi");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriTransaksi(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriTransaksi();
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
        kategoriTransaksiTable.setItems(allKategoriTransaksi);
        ObservableList<String> allJenis = FXCollections.observableArrayList();
        allJenis.addAll("Debet","Kredit");
        jenisTransaksiCombo.setItems(allJenis);
        getKategoriTransaksi();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getKategoriTransaksi(){
        try{
            Task<List<KategoriTransaksi>> task = new Task<List<KategoriTransaksi>>() {
                @Override 
                public List<KategoriTransaksi> call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
                        return KategoriTransaksiDAO.getAll(con);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allKategoriTransaksi.clear();
                allKategoriTransaksi.addAll(task.getValue());
                kategoriTransaksiTable.refresh();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void deleteKategoriTransaksi(KategoriTransaksi k){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Transaksi "+k.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            try{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = Koneksi.getConnection()){
                            return Service.deleteKategoriTransaksi(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategoriTransaksi();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Transaksi berhasil dihapus");
                        kodeKategoriField.setText("");
                        jenisTransaksiCombo.getSelectionModel().select(null);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
    }
    @FXML
    private void saveKategoriTransaksi(){
        if(kodeKategoriField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else if(jenisTransaksiCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Jenis Transaksi belum dipilih");
        else{
            Boolean s = true;
            for(KategoriTransaksi k : allKategoriTransaksi){
                if(k.getKodeKategori().equals(kodeKategoriField.getText()))
                    s = false;
            }
            if(s){
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = Koneksi.getConnection()){
                            KategoriTransaksi k = new KategoriTransaksi();
                            k.setKodeKategori(kodeKategoriField.getText());
                            k.setJenisTransaksi("D");
                            if(jenisTransaksiCombo.getSelectionModel().getSelectedItem().equals("Kredit"))
                                k.setJenisTransaksi("K");
                            return Service.newKategoriTransaksi(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategoriTransaksi();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Transaksi berhasil disimpan");
                        kodeKategoriField.setText("");
                        jenisTransaksiCombo.getSelectionModel().select(null);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }else{
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Kategori Transaksi sudah terdaftar");
            }
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
}
