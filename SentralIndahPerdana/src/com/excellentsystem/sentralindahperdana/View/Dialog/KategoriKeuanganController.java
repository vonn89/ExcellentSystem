/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriKeuanganDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriKeuangan;
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
public class KategoriKeuanganController {


    @FXML private TableView<KategoriKeuangan> tipeKeuanganTable;
    @FXML private TableColumn<KategoriKeuangan, String> kodeKategoriKeuanganColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private TextField kodeKategoriKeuanganField;
    private ObservableList<KategoriKeuangan> allKategoriKeuangan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKeuanganProperty());
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKategoriKeuangan();
        });
        menu.getItems().addAll(refresh);
        tipeKeuanganTable.setContextMenu(menu);
        tipeKeuanganTable.setRowFactory(table -> {
            TableRow<KategoriKeuangan> row = new TableRow<KategoriKeuangan>(){
                @Override
                public void updateItem(KategoriKeuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Tipe Keuangan");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriKeuangan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriKeuangan();
                        });
                        rowMenu.getItems().addAll(hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeKategoriKeuanganField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveKategoriKeuangan();
        });
    }    
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<KategoriKeuangan, Boolean>(){
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
                        KategoriKeuangan t = (KategoriKeuangan)getTableView().getItems().get( getIndex() );
                        deleteKategoriKeuangan(t);
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
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tipeKeuanganTable.setItems(allKategoriKeuangan);
        getKategoriKeuangan();
    }
    private void getKategoriKeuangan(){
        try{
            allKategoriKeuangan.clear();
            Task<List<KategoriKeuangan>> task = new Task<List<KategoriKeuangan>>() {
                @Override 
                public List<KategoriKeuangan> call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return KategoriKeuanganDAO.getAll(con);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allKategoriKeuangan.addAll(task.getValue());
                tipeKeuanganTable.refresh();
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
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    private void deleteKategoriKeuangan(KategoriKeuangan temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Tipe Keuangan "+temp.getKodeKeuangan()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            try{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            return Service.deleteKategoriKeuangan(con, temp);
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
                        getKategoriKeuangan();
                        mainApp.showMessage(Modality.NONE, "Success", "Tipe Keuangan berhasil dihapus");
                        kodeKategoriKeuanganField.setText("");
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
    private void saveKategoriKeuangan(){
        if(kodeKategoriKeuanganField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode keuangan masih kosong");
        else{
            Boolean s = true;
            for(KategoriKeuangan k : allKategoriKeuangan){
                if(k.getKodeKeuangan().equals(kodeKategoriKeuanganField.getText()))
                    s = false;
            }
            if(kodeKategoriKeuanganField.getText().toUpperCase().equals("Hutang")||
                    kodeKategoriKeuanganField.getText().toUpperCase().equals("Piutang")||
                    kodeKategoriKeuanganField.getText().toUpperCase().equals("Stok Barang")||
                    kodeKategoriKeuanganField.getText().toUpperCase().equals("Aset Tetap")||
                    kodeKategoriKeuanganField.getText().toUpperCase().equals("Modal")||
                    kodeKategoriKeuanganField.getText().toUpperCase().equals("Untung/Rugi")
                    )
                s =false;
            if(s){
                try{
                    KategoriKeuangan k = new KategoriKeuangan();
                    k.setKodeKeuangan(kodeKategoriKeuanganField.getText());
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                return Service.saveNewKategoriKeuangan(con, k);
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
                            getKategoriKeuangan();
                            mainApp.showMessage(Modality.NONE, "Success", "Tipe Keuangan berhasil disimpan");
                            kodeKategoriKeuanganField.setText("");
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
                mainApp.showMessage(Modality.NONE, "Warning", "Kode tipe keuangan sudah terdaftar/tidak dapat digunakan");
            }
        }
    }  
    
}
