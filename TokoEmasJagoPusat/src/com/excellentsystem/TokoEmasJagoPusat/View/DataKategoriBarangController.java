/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.AddNewJenisBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.AddNewKategoriBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.AddNewSubKategoriBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yunaz
 */
public class DataKategoriBarangController  {
    
    @FXML private TableView<Kategori> kategoriTable;
    @FXML private TableColumn<Kategori, String> kodeKategoriColumn;
    @FXML private TableColumn<Kategori, String> namaKategoriColumn;
    @FXML private TableColumn<Kategori, Number> beratPersenColumn;
    @FXML private TableColumn<Kategori, Number> hargaJualColumn;
    @FXML private TableColumn<Kategori, String> kadarColumn;
    
    @FXML private TableView<SubKategori> subKategoriTable;
    @FXML private TableColumn<SubKategori, String> kodeSubKategoriColumn;
    @FXML private TableColumn<SubKategori, String> namaSubKategoriColumn;
    
    @FXML private TableView<Jenis> jenisTable;
    @FXML private TableColumn<Jenis, String> kodeJenisColumn;
    @FXML private TableColumn<Jenis, String> namaJenisColumn;
    
    private ObservableList<Kategori> allKategori = FXCollections.observableArrayList();
    private ObservableList<Jenis> allJenis = FXCollections.observableArrayList();
    private ObservableList<SubKategori> allSubKategori = FXCollections.observableArrayList();
    private List<Jenis> listJenis = new ArrayList<>();
    private List<SubKategori> listSubKategori = new ArrayList<>();
    private Main mainApp;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().namaKategoriProperty());
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseEmasProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaPersenJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        
        kodeSubKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSubKategoriProperty());
        namaSubKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().namaSubKategoriProperty());
        
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        namaJenisColumn.setCellValueFactory(cellData -> cellData.getValue().namaJenisProperty());
        
        kategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectKategori(newValue));
        
        subKategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectSubKategori(newValue));
        
        ContextMenu kategoriMenu = new ContextMenu();
        MenuItem addNewKategori = new MenuItem("Tambah Kategori Baru");
        addNewKategori.setOnAction((ActionEvent) -> {
            newKategori();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            getKategori();
        });
        kategoriMenu.getItems().addAll(addNewKategori,refresh);
        kategoriTable.setContextMenu(kategoriMenu);
        kategoriTable.setRowFactory(t -> {
            TableRow<Kategori> row = new TableRow<Kategori>(){
                @Override
                public void updateItem(Kategori item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(kategoriMenu);
                    }else{
                        MenuItem editKategori = new MenuItem("Ubah Kategori");
                        editKategori.setOnAction((ActionEvent) -> {
                            editKategori(item);
                        });
                        MenuItem deleteKategori = new MenuItem("Hapus Kategori");
                        deleteKategori.setOnAction((ActionEvent) -> {
                            deleteKategori(item);
                        });
                        MenuItem addNewKategori = new MenuItem("Tambah Kategori Baru");
                        addNewKategori.setOnAction((ActionEvent) -> {
                            newKategori();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            getKategori();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNewKategori, editKategori, deleteKategori, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        ContextMenu jenisMenu = new ContextMenu();
        MenuItem addNewJenis = new MenuItem("Tambah Jenis Baru");
        addNewJenis.setOnAction((ActionEvent event) -> {
            newJenis();
        });
        jenisMenu.getItems().addAll(addNewJenis, refresh);
        jenisTable.setContextMenu(jenisMenu);
        jenisTable.setRowFactory(table -> {
            TableRow<Jenis> row = new TableRow<Jenis>(){
                @Override
                public void updateItem(Jenis item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(jenisMenu);
                    }else{
                        MenuItem editJenis = new MenuItem("Ubah Jenis");
                        editJenis.setOnAction((ActionEvent event) -> {
                            editJenis(item);
                        });
                        MenuItem deleteJenis = new MenuItem("Hapus Jenis");
                        deleteJenis.setOnAction((ActionEvent event) -> {
                            deleteJenis(item);
                        });
                        MenuItem addNewJenis = new MenuItem("Tambah Jenis Baru");
                        addNewJenis.setOnAction((ActionEvent event) -> {
                            newJenis();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            getKategori();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNewJenis, editJenis, deleteJenis, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        ContextMenu subKategoriMenu = new ContextMenu();
        MenuItem addNewSubkategori = new MenuItem("Tambah Sub-Kategori Baru");
        addNewSubkategori.setOnAction((ActionEvent event) -> {
            newSubKategori();
        });
        subKategoriMenu.getItems().addAll(addNewSubkategori, refresh);
        subKategoriTable.setContextMenu(subKategoriMenu);
        subKategoriTable.setRowFactory(table -> {
            TableRow<SubKategori> row = new TableRow<SubKategori>(){
                @Override
                public void updateItem(SubKategori item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(subKategoriMenu);
                    }else{
                        MenuItem editSubKategori = new MenuItem("Ubah Sub-Kategori");
                        editSubKategori.setOnAction((ActionEvent event) -> {
                            editSubKategori(item);
                        });
                        MenuItem deleteSubKategori = new MenuItem("Hapus Sub-Kategori");
                        deleteSubKategori.setOnAction((ActionEvent event) -> {
                            deleteSubKategori(item);
                        });
                        MenuItem addNewSubkategori = new MenuItem("Tambah Sub-Kategori Baru");
                        addNewSubkategori.setOnAction((ActionEvent event) -> {
                            newSubKategori();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            getKategori();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNewSubkategori, editSubKategori, deleteSubKategori, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        kategoriTable.setItems(allKategori);
        subKategoriTable.setItems(allSubKategori);
        jenisTable.setItems(allJenis);
        getKategori();
    }
    private void getKategori(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    allKategori.clear();
                    allKategori.addAll(KategoriDAO.getAll(con));
                    listSubKategori.clear();
                    listSubKategori.addAll(SubKategoriDAO.getAll(con));
                    listJenis.clear();
                    listJenis.addAll(JenisDAO.getAll(con));
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            kategoriTable.refresh();
            subKategoriTable.refresh();
            jenisTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void selectKategori(Kategori k){
        allSubKategori.clear();
        if(k!=null){
            for(SubKategori s : listSubKategori){
                if(s.getKodeKategori().equals(k.getKodeKategori()))
                    allSubKategori.add(s);
            }
        }
        subKategoriTable.refresh();
        allJenis.clear();
        jenisTable.refresh();
    }
    private void selectSubKategori(SubKategori s){
        allJenis.clear();
        if(s!=null){
            for(Jenis j : listJenis){
                if(j.getKodeSubKategori().equals(s.getKodeSubKategori()))
                    allJenis.add(j);
            }
        }
        jenisTable.refresh();
    }
    private void newKategori(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewKategoriBarang.fxml");
        AddNewKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
            else if(controller.namaKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama Kategori masih kosong");
            else if(controller.beratPersenField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else if(controller.hargaPersenJualField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga persen jual masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            Kategori k = new Kategori();
                            k.setKodeKategori(controller.kodeKategoriField.getText().toUpperCase());
                            k.setNamaKategori(controller.namaKategoriField.getText());
                            k.setPersentaseEmas(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", "")));
                            k.setHargaPersenJual(Double.parseDouble(controller.hargaPersenJualField.getText().replaceAll(",", "")));
                            k.setKadar(controller.kadarField.getText());
                            return Service.saveNewKategoriBarang(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori barang berhasil disimpan");
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
    private void editKategori(Kategori k){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewKategoriBarang.fxml");
        AddNewKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategoriBarang(k);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
            else if(controller.namaKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama kategori masih kosong");
            else if(controller.beratPersenField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else if(controller.hargaPersenJualField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga persen jual masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            k.setNamaKategori(controller.namaKategoriField.getText());
                            k.setPersentaseEmas(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", "")));
                            k.setHargaPersenJual(Double.parseDouble(controller.hargaPersenJualField.getText().replaceAll(",", "")));
                            k.setKadar(controller.kadarField.getText());
                            return Service.saveUpdateKategoriBarang(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Barang berhasil disimpan");
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
    private void deleteKategori(Kategori k){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus kategori "+k.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        return Service.deleteKategoriBarang(con, k);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategori();
                String status = task.getValue();
                if(status.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori barang berhasil dihapus");
                else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void newSubKategori(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewSubKategoriBarang.fxml");
        AddNewSubKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategori(allKategori);
        controller.SaveButton.setOnAction((event) -> {
            if(controller.kodeSubKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sub-kategori masih kosong");
            else if(controller.namaSubKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama sub-kategori masih kosong");
            else if(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            SubKategori s = new SubKategori();
                            s.setKodeSubKategori(controller.kodeSubKategoriField.getText().toUpperCase());
                            s.setNamaSubKategori(controller.namaSubKategoriField.getText());
                            s.setKodeKategori(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            return Service.saveNewSubKategoriBarang(con, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Sub-kategori barang berhasil disimpan");
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
    private void editSubKategori(SubKategori s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewSubKategoriBarang.fxml");
        AddNewSubKategoriBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategori(allKategori);
        controller.setSubKategoriBarang(s);
        controller.SaveButton.setOnAction((event) -> {
            if(controller.kodeSubKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sub-kategori masih kosong");
            else if(controller.namaSubKategoriField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama sub-kategori masih kosong");
            else if(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            s.setKodeSubKategori(controller.kodeSubKategoriField.getText().toUpperCase());
                            s.setNamaSubKategori(controller.namaSubKategoriField.getText());
                            s.setKodeKategori(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            return Service.saveUpdateSubKategoriBarang(con, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Sub-kategori barang berhasil disimpan");
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
    private void deleteSubKategori(SubKategori s){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus sub-kategori "+s.getKodeSubKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        return Service.deleteSubKategoriBarang(con, s);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategori();
                String status = task.getValue();
                if(status.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Sub-kategori barang berhasil dihapus");
                else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    
    private void newJenis(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewJenisBarang.fxml");
        AddNewJenisBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategori(allKategori, listSubKategori);
        controller.SaveButton.setOnAction((event) -> {
            if(controller.kodeJenisField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis masih kosong");
            else if(controller.namaJenisField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama jenis masih kosong");
            else if(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            else if(controller.subKategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sub-kategori belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            Jenis j = new Jenis();
                            j.setKodeJenis(controller.kodeJenisField.getText().toUpperCase());
                            j.setNamaJenis(controller.namaJenisField.getText());
                            j.setKodeKategori(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            j.setKodeSubKategori(controller.subKategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            return Service.saveNewJenisBarang(con, j);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true"))
                        mainApp.showMessage(Modality.NONE, "Success", "jenis barang berhasil disimpan");
                    else
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
    private void editJenis(Jenis j){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/AddNewJenisBarang.fxml");
        AddNewJenisBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setKategori(allKategori, listSubKategori);
        controller.setJenis(j);
        controller.SaveButton.setOnAction((event) -> {
            if(controller.kodeJenisField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis masih kosong");
            else if(controller.namaJenisField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama jenis masih kosong");
            else if(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            else if(controller.subKategoriBarangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode sub-kategori belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = KoneksiPusat.getConnection()){
                            j.setKodeJenis(controller.kodeJenisField.getText().toUpperCase());
                            j.setNamaJenis(controller.namaJenisField.getText());
                            j.setKodeKategori(controller.kategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            j.setKodeSubKategori(controller.subKategoriBarangCombo.getSelectionModel().getSelectedItem().toUpperCase());
                            return Service.saveUpdateJenisBarang(con, j);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    getKategori();
                    String status = task.getValue();
                    if(status.equals("true"))
                        mainApp.showMessage(Modality.NONE, "Success", "Jenis barang berhasil disimpan");
                    else
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
    private void deleteJenis(Jenis j){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus jenis "+j.getKodeJenis()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        return Service.deleteJenisBarang(con, j);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKategori();
                String status = task.getValue();
                if(status.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Jenis barang berhasil dihapus");
                else
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
