/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriPiutangDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriPiutang;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
public class KategoriPiutangController {


    @FXML private TableView<KategoriPiutang> kategoriPiutangTable;
    @FXML private TableColumn<KategoriPiutang, String> kodeKategoriPiutangColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private TextField kodeKategoriPiutangField;
    private ObservableList<KategoriPiutang> allKategoriPiutang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            kategoriPiutangTable.refresh();
        });
        menu.getItems().addAll(refresh);
        kategoriPiutangTable.setContextMenu(menu);
        kategoriPiutangTable.setRowFactory(table -> {
            TableRow<KategoriPiutang> row = new TableRow<KategoriPiutang>(){
                @Override
                public void updateItem(KategoriPiutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Piutang");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriPiutang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriPiutang();
                        });
                        rowMenu.getItems().addAll(hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeKategoriPiutangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                saveKategoriPiutang();
            }
        });
    }    
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<KategoriPiutang, Boolean>(){
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
                        KategoriPiutang k = (KategoriPiutang)getTableView().getItems().get( getIndex() );
                        deleteKategoriPiutang(k);
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
        kategoriPiutangTable.setItems(allKategoriPiutang);
        getKategoriPiutang();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getKategoriPiutang(){
        try{
            Task<List<KategoriPiutang>> task = new Task<List<KategoriPiutang>>() {
                @Override 
                public List<KategoriPiutang> call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return KategoriPiutangDAO.getAll(con);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allKategoriPiutang.clear();
                allKategoriPiutang.addAll(task.getValue());
                kategoriPiutangTable.refresh();
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
    private void deleteKategoriPiutang(KategoriPiutang temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Piutang "+temp.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            try{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            return Service.deleteKategoriPiutang(con, temp);
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
                        getKategoriPiutang();
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Piutang berhasil dihapus");
                        kodeKategoriPiutangField.setText("");
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
    private void saveKategoriPiutang(){
        if(kodeKategoriPiutangField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else{
            Boolean s = true;
            for(KategoriPiutang k : allKategoriPiutang){
                if(k.getKodeKategori().equals(kodeKategoriPiutangField.getText()))
                    s = false;
            }
            if(kodeKategoriPiutangField.getText().equals("Piutang Penjualan"))
                s = false;
            if(s){
                try{
                    KategoriPiutang k = new KategoriPiutang();
                    k.setKodeKategori(kodeKategoriPiutangField.getText());
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                return Service.saveNewKategoriPiutang(con, k);
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
                            getKategoriPiutang();
                            mainApp.showMessage(Modality.NONE, "Success", "Kategori Piutang berhasil disimpan");
                            kodeKategoriPiutangField.setText("");
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
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Kategori sudah terdaftar");
            }
        }
    }  
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
