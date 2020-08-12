/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriPekerjaanDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriPekerjaan;
import com.excellentsystem.sentralindahperdana.Service.Service;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class KategoriPekerjaanController  {

    @FXML private TableView<KategoriPekerjaan> kategoriPekerjaanTable;
    @FXML private TableColumn<KategoriPekerjaan, String> kodeKategoriPekerjaanColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private TextField kodeKategoriPekerjaanField;
    private ObservableList<KategoriPekerjaan> allKategoriPekerjaan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            kategoriPekerjaanTable.refresh();
        });
        menu.getItems().addAll(refresh);
        kategoriPekerjaanTable.setContextMenu(menu);
        kategoriPekerjaanTable.setRowFactory(table -> {
            TableRow<KategoriPekerjaan> row = new TableRow<KategoriPekerjaan>(){
                @Override
                public void updateItem(KategoriPekerjaan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Pekerjaan");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriPekerjaan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            kategoriPekerjaanTable.refresh();
                        });
                        rowMenu.getItems().addAll(hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeKategoriPekerjaanField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveKategoriPekerjaan();
        });
    }    
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<KategoriPekerjaan, Boolean>(){
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
                        KategoriPekerjaan k = (KategoriPekerjaan)getTableView().getItems().get( getIndex() );
                        deleteKategoriPekerjaan(k);
                    });
                    setGraphic( btn );
                }
            }
        };
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        kategoriPekerjaanTable.setItems(allKategoriPekerjaan);
        getKategoriPekerjaan();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getKategoriPekerjaan(){
        try{
            Task<List<KategoriPekerjaan>> task = new Task<List<KategoriPekerjaan>>() {
                @Override 
                public List<KategoriPekerjaan> call()throws Exception {
                    try(Connection con = Koneksi.getConnection()){
                        return KategoriPekerjaanDAO.getAll(con);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                allKategoriPekerjaan.clear();
                allKategoriPekerjaan.addAll(task.getValue());
                kategoriPekerjaanTable.refresh();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void deleteKategoriPekerjaan(KategoriPekerjaan temp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Pekerjaan "+temp.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return Service.deleteKategoriPekerjaan(con, temp);
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
                    getKategoriPekerjaan();
                    mainApp.showMessage(Modality.NONE, "Success", "Kategori Pekerjaan berhasil dihapus");
                    kodeKategoriPekerjaanField.setText("");
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
    private void saveKategoriPekerjaan(){
        if(kodeKategoriPekerjaanField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else{
            Boolean s = true;
            for(KategoriPekerjaan k : allKategoriPekerjaan){
                if(k.getKodeKategori().equals(kodeKategoriPekerjaanField.getText()))
                    s = false;
            }
            if(s){
                try{
                    KategoriPekerjaan k = new KategoriPekerjaan();
                    k.setKodeKategori(kodeKategoriPekerjaanField.getText());
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                return Service.saveNewKategoriPekerjaan(con, k);
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
                            getKategoriPekerjaan();
                            mainApp.showMessage(Modality.NONE, "Success", "Kategori Pekerjaan berhasil disimpan");
                            kodeKategoriPekerjaanField.setText("");
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
