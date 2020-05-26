/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
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
 * @author excellent
 */
public class DataCabangController  {

    @FXML private TableView<Cabang> cabangTable;
    @FXML private TableColumn<Cabang, String> kodeCabangColumn;
    @FXML private TableColumn<Cabang, String> namaCabangColumn;
    @FXML private TableColumn<Cabang, String> ipServerColumn;
    @FXML private TextField kodeCabangField;
    @FXML private TextField namaCabangField;
    @FXML private TextField ipServerField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private String status = "";
    private ObservableList<Cabang> allCabang = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        namaCabangColumn.setCellValueFactory(cellData -> cellData.getValue().namaCabangProperty());
        ipServerColumn.setCellValueFactory(cellData -> cellData.getValue().ipServerProperty());
        
        cabangTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectCabang(newValue));
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah Cabang Baru");
        addNew.setOnAction((ActionEvent) -> {
            newCabang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            getCabang();
        });
        rowMenu.getItems().addAll(addNew,refresh);
        cabangTable.setContextMenu(rowMenu);
        cabangTable.setRowFactory(t -> {
            TableRow<Cabang> row = new TableRow<Cabang>(){
                @Override
                public void updateItem(Cabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        MenuItem deleteCabang = new MenuItem("Hapus Cabang");
                        deleteCabang.setOnAction((ActionEvent) -> {
                            deleteCabang(item);
                        });
                        MenuItem addNewCabang = new MenuItem("Tambah Cabang Baru");
                        addNewCabang.setOnAction((ActionEvent) -> {
                            newCabang();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            getCabang();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(addNewCabang, deleteCabang, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        kodeCabangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                namaCabangField.requestFocus();
        });
        namaCabangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                ipServerField.requestFocus();
        });
        ipServerField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveCabang();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        cabangTable.setItems(allCabang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        getCabang();
    }
    private void getCabang(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    return CabangDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allCabang.clear();
            allCabang.addAll(task.getValue());
            reset();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void reset(){
        status="";
        kodeCabangField.setText("");
        namaCabangField.setText("");
        ipServerField.setText("");
        kodeCabangField.setDisable(true);
        namaCabangField.setDisable(true);
        ipServerField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
    }
    private void selectCabang(Cabang c){
        if(c!=null){
            status = "update";
            kodeCabangField.setDisable(true);
            namaCabangField.setDisable(false);
            ipServerField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            kodeCabangField.setText(c.getKodeCabang());
            namaCabangField.setText(c.getNamaCabang());
            ipServerField.setText(c.getIpServer());
        }
    }
    private void newCabang(){
        status = "new";
        kodeCabangField.setDisable(false);
        namaCabangField.setDisable(false);
        ipServerField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        kodeCabangField.setText("");
        namaCabangField.setText("");
        ipServerField.setText("");
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void saveCabang(){
        if(kodeCabangField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Cabang belum dipilih");
        }else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        Cabang c = new Cabang();
                        c.setKodeCabang(kodeCabangField.getText());
                        c.setNamaCabang(namaCabangField.getText());
                        c.setIpServer(ipServerField.getText());
                        if(status.equals("update"))
                            return Service.saveUpdateCabang(con, c);
                        else if(status.equals("new"))
                            return Service.saveNewCabang(con, c);
                        else
                            return "false";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCabang();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data cabang berhasil disimpan");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", message);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void deleteCabang(Cabang c){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete Cabang "+c.getKodeCabang()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        return Service.deleteCabang(con, c);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getCabang();
                String message = task.getValue();
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data cabang berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", message);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
