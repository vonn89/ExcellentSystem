/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.TukangDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Tukang;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTukangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataTukangController {
    @FXML private TableView<Tukang> tukangTable;
    @FXML private TableColumn<Tukang, String> kodeTukangColumn;
    @FXML private TableColumn<Tukang, String> namaColumn;
    @FXML private TableColumn<Tukang, String> alamatColumn;
    @FXML private TableColumn<Tukang, String> noTelpColumn;
    @FXML private TableColumn<Tukang, Number> gajiPerHariColumn;
    @FXML private TextField searchField;
    private Main mainApp;   
    private ObservableList<Tukang> allTukang = FXCollections.observableArrayList();
    private ObservableList<Tukang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeTukangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeTukangProperty());
        kodeTukangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeTukangColumn));
        
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));
        
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));
        
        gajiPerHariColumn.setCellValueFactory(cellData ->cellData.getValue().gajiPerHariProperty());
        gajiPerHariColumn.setCellFactory(col -> getTableCell(rp));
        
        allTukang.addListener((ListChangeListener.Change<? extends Tukang> change) -> {
            searchTukang();
        });
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Tukang");
        addNew.setOnAction((ActionEvent e)->{
            newTukang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getTukang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Tukang")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        tukangTable.setContextMenu(rm);
        tukangTable.setRowFactory((TableView<Tukang> tableView) -> {
            final TableRow<Tukang> row = new TableRow<Tukang>(){
                @Override
                public void updateItem(Tukang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Tukang");
                        addNew.setOnAction((ActionEvent e)->{
                            newTukang();
                        });
                        MenuItem edit = new MenuItem("Update Tukang");
                        edit.setOnAction((ActionEvent e)->{
                            updateTukang(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Tukang");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteTukang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getTukang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Tukang")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Update Tukang")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Tukang")&&o.isStatus())
                                rm.getItems().add(hapus);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Update Tukang")&&o.isStatus())
                                updateTukang(row.getItem());
                        }
                }
            });
            return row;
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchTukang();
        });
        filterData.addAll(allTukang);
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getTukang();
        tukangTable.setItems(filterData);
    }
    private void getTukang(){
        Task<List<Tukang>> task = new Task<List<Tukang>>() {
            @Override 
            public List<Tukang> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return TukangDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allTukang.clear();
            allTukang.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchTukang() {
        filterData.clear();
        for (Tukang temp : allTukang) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeTukang())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(rp.format(temp.getGajiPerHari())))
                    filterData.add(temp);
            }
        }
    }
    @FXML
    private void newTukang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTukang.fxml");
        DetailTukangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.namaField.getText().equals("")||x.namaField.getText()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Nama masih kosong");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Tukang t = new Tukang();
                            t.setKodeTukang(TukangDAO.getId(con));
                            t.setAlamat(x.alamatField.getText());
                            t.setGajiPerHari(Double.parseDouble(x.gajiPerHariField.getText().replaceAll(",", "")));
                            t.setNama(x.namaField.getText());
                            t.setNoTelp(x.noTelpField.getText());
                            t.setStatus("true");
                            return Service.newTukang(con, t);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getTukang();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data tukang berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage,stage);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void updateTukang(Tukang t){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTukang.fxml");
        DetailTukangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setTukang(t);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.namaField.getText().equals("")||x.namaField.getText()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Nama masih kosong");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            t.setAlamat(x.alamatField.getText());
                            t.setGajiPerHari(Double.parseDouble(x.gajiPerHariField.getText().replaceAll(",", "")));
                            t.setNama(x.namaField.getText());
                            t.setNoTelp(x.noTelpField.getText());
                            t.setStatus("true");
                            return Service.updateTukang(con, t);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getTukang();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data tukang berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage,stage);
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void deleteTukang(Tukang t){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete tukang "+t.getKodeTukang()+"-"+t.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        t.setStatus("false");
                        return Service.updateTukang(con, t);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getTukang();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data tukang berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
