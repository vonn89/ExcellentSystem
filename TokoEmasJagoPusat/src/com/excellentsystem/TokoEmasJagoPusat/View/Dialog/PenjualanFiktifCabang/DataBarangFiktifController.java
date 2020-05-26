/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.KategoriFiktif;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataBarangFiktifController  {

    @FXML private TableView<KategoriFiktif> kategoriTable;
    @FXML private TableColumn<KategoriFiktif, String> kodeKategoriColumn;
    @FXML private TableColumn<KategoriFiktif, Number> hargaMinColumn;
    @FXML private TableColumn<KategoriFiktif, Number> hargaMaxColumn;
    
    @FXML private TableView<BarangFiktif> barangTable;
    @FXML private TableColumn<BarangFiktif, String> namaBarangColumn;
    @FXML private TableColumn<BarangFiktif, String> kadarColumn;
    @FXML private TableColumn<BarangFiktif, String> kodeInternColumn;
    
    @FXML private TextField kodeKategoriField;
    @FXML private TextField hargaMinField;
    @FXML private TextField hargaMaxField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private ComboBox<String> kodeKategoriCombo;
    @FXML private TextField namaBarangField;
    @FXML private TextField kadarField;
    @FXML private TextField kodeInternField;
    
    @FXML private Label totalBarangLabel;
    private String status = "";
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private ObservableList<BarangFiktif> allBarang = FXCollections.observableArrayList();
    public ObservableList<KategoriFiktif> allKategori = FXCollections.observableArrayList();
    public List<BarangFiktif> listBarang = new ArrayList<>();
    private ObservableList<String> listKategori = FXCollections.observableArrayList();
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        hargaMinColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualMinProperty());
        hargaMinColumn.setCellFactory(col -> getTableCell(rp));
        hargaMaxColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualMaxProperty());
        hargaMaxColumn.setCellFactory(col -> getTableCell(rp));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        
        
        Function.setNumberField(hargaMinField, rp);
        Function.setNumberField(hargaMaxField, rp);
        
        ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Kategori Baru");
        addNew.setOnAction((ActionEvent) -> {
            newKategori();
        });
        menu.getItems().addAll(addNew);
        kategoriTable.setContextMenu(menu);
        kategoriTable.setRowFactory(t -> {
            TableRow<KategoriFiktif> row = new TableRow<KategoriFiktif>(){
                @Override
                public void updateItem(KategoriFiktif item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        MenuItem addNew = new MenuItem("Tambah Kategori Baru");
                        addNew.setOnAction((ActionEvent) -> {
                            newKategori();
                        });
                        MenuItem hapus = new MenuItem("Hapus Kategori");
                        hapus.setOnAction((ActionEvent) -> {
                            deleteKategori(item);
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNew,hapus);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kategoriTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectKategori(newValue));
        
        barangTable.setRowFactory(t -> {
            TableRow<BarangFiktif> row = new TableRow<BarangFiktif>(){
                @Override
                public void updateItem(BarangFiktif item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent) -> {
                            hapusBarang(item);
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(hapus);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }  
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        kategoriTable.setItems(allKategori);
        barangTable.setItems(allBarang);
        kodeKategoriCombo.setItems(listKategori);
    }  
    public void setBarang(List<BarangFiktif> x, List<KategoriFiktif> y){
        listBarang.clear();
        listBarang.addAll(x);
        
        allKategori.clear();
        allKategori.addAll(y);
        
        listKategori.clear();
        for(KategoriFiktif k : y){
            listKategori.add(k.getKodeKategori());
        }
        allBarang.clear();
        barangTable.refresh();
        totalBarangLabel.setText("");
    }
    private void newKategori(){
        kodeKategoriField.setDisable(false);
        hargaMinField.setDisable(false);
        hargaMaxField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        kodeKategoriField.setText("");
        hargaMinField.setText("");
        hargaMaxField.setText("");
        status = "New";
    }
    @FXML
    private void reset(){
        kodeKategoriField.setDisable(true);
        hargaMinField.setDisable(true);
        hargaMaxField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        kodeKategoriField.setText("");
        hargaMinField.setText("");
        hargaMaxField.setText("");
        status = "";
    }
    private void selectKategori(KategoriFiktif k){
        if(k!=null){
            kodeKategoriField.setDisable(true);
            hargaMinField.setDisable(false);
            hargaMaxField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            kodeKategoriField.setText(k.getKodeKategori());
            hargaMinField.setText(rp.format(k.getHargaJualMin()));
            hargaMaxField.setText(rp.format(k.getHargaJualMax()));
            status = "Update";
            showKategori(k.getKodeKategori());
        }
    }
    private void showKategori(String kodeKategori){
        allBarang.clear();
        for(BarangFiktif b : listBarang){
            if(b.getKodeKategori().equals(kodeKategori))
                allBarang.add(b);
        }
        totalBarangLabel.setText(kodeKategori+" : "+rp.format(allBarang.size())+" Barang");
        barangTable.refresh();
    }
    private void deleteKategori(KategoriFiktif k){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus kategori "+k.getKodeKategori()+" akan menghapus semua barang dengan kode kategori tersebut ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        BarangFiktifDAO.deleteAllByKategori(con, k.getKodeKategori());
                        KategoriFiktifDAO.delete(con, k);
                        return "true";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String msg = task.getValue();
                if(msg.equals("true")){
                    allKategori.remove(k);
                    kategoriTable.refresh();
                    listKategori.remove(k.getKodeKategori());
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori barang berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", msg);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    @FXML
    private void saveKategori(){
        if(kodeKategoriField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else if(hargaMinField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Harga minimum masih kosong");
        else if(hargaMaxField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Harga maximum masih kosong");
        else if(Double.parseDouble(hargaMinField.getText().replaceAll(",", ""))>
                Double.parseDouble(hargaMaxField.getText().replaceAll(",", "")))
            mainApp.showMessage(Modality.NONE, "Warning", "Harga maximum harus lebih besar dari harga minimum");
        else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        if(status.equals("New")){
                            String status = "true";
                            for(KategoriFiktif k : allKategori){
                                if(k.getKodeKategori().toUpperCase().equals(kodeKategoriField.getText().toUpperCase()))
                                    status = "Kode kategori sudah terdaftar";
                            }
                            if(status.equals("true")){
                                KategoriFiktif k = new KategoriFiktif();
                                k.setKodeKategori(kodeKategoriField.getText().toUpperCase());
                                k.setHargaJualMin(Double.parseDouble(hargaMinField.getText().replaceAll(",", "")));
                                k.setHargaJualMax(Double.parseDouble(hargaMaxField.getText().replaceAll(",", "")));
                                KategoriFiktifDAO.insert(con, k);
                                allKategori.add(k);
                                listKategori.add(k.getKodeKategori());
                                kategoriTable.refresh();
                            }
                            return status;
                        }else if(status.equals("Update")){
                            KategoriFiktif k = null;
                            for(KategoriFiktif x : allKategori){
                                if(x.getKodeKategori().toUpperCase().equals(kodeKategoriField.getText().toUpperCase()))
                                    k = x;
                            }
                            if(k==null){
                                return "Kategori tidak ditemukan";
                            }else{
                                k.setKodeKategori(kodeKategoriField.getText().toUpperCase());
                                k.setHargaJualMin(Double.parseDouble(hargaMinField.getText().replaceAll(",", "")));
                                k.setHargaJualMax(Double.parseDouble(hargaMaxField.getText().replaceAll(",", "")));
                                KategoriFiktifDAO.update(con, k);
                                kategoriTable.refresh();
                                return "true";
                            }
                        }else
                            return "false";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String msg = task.getValue();
                if(msg.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori Barang berhasil disimpan");
                else
                    mainApp.showMessage(Modality.NONE, "Failed", msg);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void saveBarang(){
        if(namaBarangField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        else if(kodeKategoriCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "kode kategori belum dipilih");
        else{
            Task<BarangFiktif> task = new Task<BarangFiktif>() {
                @Override 
                public BarangFiktif call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        BarangFiktif b = new BarangFiktif();
                        b.setKodeKategori(kodeKategoriCombo.getSelectionModel().getSelectedItem());
                        b.setNamaBarang(namaBarangField.getText());
                        b.setKadar(kadarField.getText());
                        b.setKodeIntern(kodeInternField.getText());
                        BarangFiktifDAO.insert(con, b);
                        return b;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                listBarang.add(task.getValue());
                showKategori(kodeKategoriCombo.getSelectionModel().getSelectedItem());
                mainApp.showMessage(Modality.NONE, "Success", "barang berhasil disimpan");
                kodeKategoriCombo.getSelectionModel().clearSelection();
                namaBarangField.setText("");
                kadarField.setText("");
                kodeInternField.setText("");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void hapusBarang(BarangFiktif b){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus barang "+b.getNamaBarang()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        BarangFiktifDAO.delete(con, b);
                        return "true";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                listBarang.remove(b);
                showKategori(b.getKodeKategori());
                mainApp.showMessage(Modality.NONE, "Success", "Barang berhasil dihapus");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
