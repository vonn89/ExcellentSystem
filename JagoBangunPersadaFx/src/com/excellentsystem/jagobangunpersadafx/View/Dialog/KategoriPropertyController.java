/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KategoriPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriProperty;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import java.sql.Connection;
import java.util.ArrayList;
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
 * @author Xtreme
 */
public class KategoriPropertyController  {

    @FXML private TableView<KategoriProperty> kategoriPropertyTable;
    @FXML private TableColumn<KategoriProperty, String> kodeKategoriColumn;
    
    @FXML private TextField kodeKategoriField;
    private ObservableList<KategoriProperty> allKategoriProperty= FXCollections.observableArrayList();
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            kategoriPropertyTable.refresh();
        });
        rm.getItems().addAll(refresh);
        kategoriPropertyTable.setContextMenu(rm);
        kategoriPropertyTable.setRowFactory(table -> {
            TableRow<KategoriProperty> row = new TableRow<KategoriProperty>(){
                @Override
                public void updateItem(KategoriProperty item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem hapus = new MenuItem("Delete Kategori Property");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteKategoriProperty(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getKategoriProperty();
                        });
                        rm.getItems().addAll(hapus,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        kodeKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveKategoriProperty();
        });
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        kategoriPropertyTable.setItems(allKategoriProperty);
        getKategoriProperty();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getKategoriProperty(){
        Task<List<KategoriProperty>> task = new Task<List<KategoriProperty>>() {
            @Override 
            public List<KategoriProperty> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return KategoriPropertyDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKategoriProperty.clear();
            allKategoriProperty.addAll(task.getValue());
            kategoriPropertyTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void deleteKategoriProperty(KategoriProperty k){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Kategori Property "+k.getKodeKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String>task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        boolean status = true;
                        List<String> statusProperty = new ArrayList<>();
                        statusProperty.add("Available");
                        statusProperty.add("Reserved");
                        statusProperty.add("Sold");
                        statusProperty.add("Sold - Full Paid");
                        List<Property> allProperty = PropertyDAO.getAllByStatus(con,statusProperty);
                        for(Property p : allProperty){
                            if(p.getKodeKategori().equals(k.getKodeKategori()))
                                status = false;
                        }
                        if(status){
                            return Service.deleteKategoriProperty(con,k);
                        }else{
                            return "Tidak dapat dihapus,karena masih ada property dengan kategori "+k.getKodeKategori();
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    getKategoriProperty();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori Property berhasil dihapus");
                        kodeKategoriField.setText("");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }catch(Exception exc){
                    mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    @FXML
    private void saveKategoriProperty(){
        if(kodeKategoriField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else{
            Boolean s = true;
            for(KategoriProperty k : allKategoriProperty){
                if(k.getKodeKategori().equals(kodeKategoriField.getText()))
                    s = false;
            }
            if(s){
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = Koneksi.getConnection()){
                            KategoriProperty k = new KategoriProperty();
                            k.setKodeKategori(kodeKategoriField.getText());
                            return Service.newKategoriProperty(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKategoriProperty();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Kategori property berhasil disimpan");
                        kodeKategoriField.setText("");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
    public void close(){
        mainApp.closeDialog(owner, stage);
    }  
}
