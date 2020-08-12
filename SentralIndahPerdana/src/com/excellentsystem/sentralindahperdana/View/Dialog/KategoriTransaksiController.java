/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriTransaksiDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import com.excellentsystem.sentralindahperdana.Service.Service;
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
 * @author Yunaz
 */
public class KategoriTransaksiController {

    @FXML private TableView<KategoriTransaksi> kategoriTable;
    @FXML private TableColumn<KategoriTransaksi, String> kodeKategoriTransaksiColumn;
    @FXML private TableColumn<KategoriTransaksi, String> jenisKategoriTransaksiColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private TextField kodeKategoriTransaksiField;
    @FXML private ComboBox<String> jenisKategoriTransaksiCombo;
    private ObservableList<KategoriTransaksi> allKategoriTransaksi = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriTransaksiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        jenisKategoriTransaksiColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getJenisTransaksi().equals("D"))
                return new SimpleStringProperty("Debit");
            else
                return new SimpleStringProperty("Kredit");
        });
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        kodeKategoriTransaksiField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                jenisKategoriTransaksiCombo.requestFocus();
            }
        });
        jenisKategoriTransaksiCombo.setOnKeyPressed((KeyEvent keyEvent) ->{
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveKategoriTransaksi();
            }
        });
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKategoriTransaksi();
        });

        menu.getItems().addAll(refresh);
        kategoriTable.setContextMenu(menu);
        kategoriTable.setRowFactory(table -> {
            TableRow<KategoriTransaksi> row = new TableRow<KategoriTransaksi>(){
                @Override
                public void updateItem(KategoriTransaksi item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Transaksi");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriTransaksi(item);
                        });
                        
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriTransaksi();
                        });
                        rowMenu.getItems().addAll(hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }    
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<KategoriTransaksi, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
                    btn.setGraphic(new ImageView(imageDecline));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        KategoriTransaksi k = (KategoriTransaksi)getTableView().getItems().get( getIndex() );
                        deleteKategoriTransaksi(k);
                    });
                    setGraphic( btn );
                }
            }
        };
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        kategoriTable.setItems(allKategoriTransaksi);
        ObservableList<String> allJenis = FXCollections.observableArrayList();
        allJenis.add("Debit");
        allJenis.add("Kredit");
        jenisKategoriTransaksiCombo.setItems(allJenis);
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
                    Thread.sleep(timeout);
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
                kategoriTable.refresh();
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
    private void deleteKategoriTransaksi(KategoriTransaksi temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Transaksi "+temp.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            try{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            return Service.deleteKategoriTransaksi(con, temp);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    String status = task.getValue();
                    if(status.equals("true")){
                        getKategoriTransaksi();
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Transaksi berhasil dihapus");
                        kodeKategoriTransaksiField.setText("");
                        jenisKategoriTransaksiCombo.getSelectionModel().select(null);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
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
        if(kodeKategoriTransaksiField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else if(jenisKategoriTransaksiCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Jenis Transaksi belum dipilih");
        else{
            Boolean s = true;
            for(KategoriTransaksi k : allKategoriTransaksi){
                if(k.getKodeKategori().equals(kodeKategoriTransaksiField.getText()))
                    s = false;
            }
            if(s){
                try{
                    KategoriTransaksi k = new KategoriTransaksi();
                    k.setKodeKategori(kodeKategoriTransaksiField.getText());
                    k.setJenisTransaksi("D");
                    if(jenisKategoriTransaksiCombo.getSelectionModel().getSelectedItem().equals("Kredit"))
                        k.setJenisTransaksi("K");
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                return Service.saveNewKategoriTransaksi(con, k);
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((e) -> {
                        mainApp.closeLoading();
                        String status = task.getValue();
                        if(status.equals("true")){
                            getKategoriTransaksi();
                            mainApp.showMessage(Modality.NONE, "Success", "Kategori Transaksi berhasil disimpan");
                            kodeKategoriTransaksiField.setText("");
                            jenisKategoriTransaksiCombo.getSelectionModel().select(null);
                        }else
                            mainApp.showMessage(Modality.NONE, "Failed", status);
                    });
                    task.setOnFailed((e) -> {
                        mainApp.closeLoading();
                        mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    });
                    new Thread(task).start();
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            }else{
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Kategori Transaksi sudah terdaftar");
            }
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
