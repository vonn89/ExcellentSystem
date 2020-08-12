/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.PegawaiDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Pegawai;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPegawaiController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
public class DataPegawaiController  {
    @FXML private TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> kodePegawaiColumn;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    @FXML private TableColumn<Pegawai, String> jabatanColumn;
    @FXML private TableColumn<Pegawai, String> alamatColumn;
    @FXML private TableColumn<Pegawai, String> kotaColumn;
    @FXML private TableColumn<Pegawai, String> identitasColumn;
    @FXML private TableColumn<Pegawai, String> noIdentitasColumn;
    @FXML private TableColumn<Pegawai, String> emailColumn;
    @FXML private TableColumn<Pegawai, String> noTelpColumn;
    @FXML private TableColumn<Pegawai, String> noHandphoneColumn;
    @FXML private TextField searchField;
    
    private Main mainApp;      
    private final ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private final ObservableList<Pegawai> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodePegawaiColumn.setCellValueFactory(cellData -> cellData.getValue().kodePegawaiProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jabatanColumn.setCellValueFactory(cellData ->cellData.getValue().jabatanProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pegawai");
        addNew.setOnAction((ActionEvent e)->{
            newPegawai();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPegawai();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pegawai")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        pegawaiTable.setContextMenu(rm);
        pegawaiTable.setRowFactory((TableView<Pegawai> tableView) -> {
            final TableRow<Pegawai> row = new TableRow<Pegawai>(){
                @Override
                public void updateItem(Pegawai item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pegawai");
                        addNew.setOnAction((ActionEvent e)->{
                            newPegawai();
                        });
                        MenuItem edit = new MenuItem("Edit Pegawai");
                        edit.setOnAction((ActionEvent e)->{
                            editPegawai(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Pegawai");
                        hapus.setOnAction((ActionEvent e)->{
                            deletePegawai(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPegawai();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pegawai")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Edit Pegawai")&&o.isStatus())
                                rm.getItems().add(edit);
                            if(o.getJenis().equals("Delete Pegawai")&&o.isStatus())
                                rm.getItems().add(hapus);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Pegawai")&&o.isStatus())
                                editPegawai(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPegawai.addListener((ListChangeListener.Change<? extends Pegawai> change) -> {
            searchPegawai();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPegawai();
        });
        filterData.addAll(allPegawai);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPegawai();
        pegawaiTable.setItems(filterData);
    }
    private void getPegawai(){
        Task<List<Pegawai>> task = new Task<List<Pegawai>>() {
            @Override 
            public List<Pegawai> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PegawaiDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allPegawai.clear();
                allPegawai.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
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
    private void searchPegawai() {
        filterData.clear();
        for (Pegawai temp : allPegawai) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodePegawai())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getJabatan())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getIdentitas())||
                    checkColumn(temp.getNoIdentitas()))
                    filterData.add(temp);
            }
        }
    }
    private void newPegawai(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawaiDetail(null);
        controller.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Pegawai pegawai = new Pegawai();
                        pegawai.setKodePegawai(PegawaiDAO.getId(con));
                        pegawai.setNama(controller.namaField.getText());
                        pegawai.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                        pegawai.setAlamat(controller.alamatField.getText());
                        pegawai.setKota(controller.kotaField.getText());
                        pegawai.setIdentitas(controller.identitasField.getText());
                        pegawai.setNoIdentitas(controller.noIdentitasField.getText());
                        pegawai.setEmail(controller.emailField.getText());
                        pegawai.setNoTelp(controller.noTelpField.getText());
                        pegawai.setNoHandphone(controller.noHandphoneField.getText());
                        pegawai.setStatus("true");
                        return Service.saveNewPegawai(con, pegawai);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil disimpan");
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
    private void editPegawai(Pegawai pegawai){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawaiDetail(pegawai);
        controller.saveButton.setOnAction((ActionEvent ev)->{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        pegawai.setNama(controller.namaField.getText());
                        pegawai.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                        pegawai.setAlamat(controller.alamatField.getText());
                        pegawai.setKota(controller.kotaField.getText());
                        pegawai.setIdentitas(controller.identitasField.getText());
                        pegawai.setNoIdentitas(controller.noIdentitasField.getText());
                        pegawai.setEmail(controller.emailField.getText());
                        pegawai.setNoTelp(controller.noTelpField.getText());
                        pegawai.setNoHandphone(controller.noHandphoneField.getText());
                        pegawai.setStatus("true");
                        return Service.saveUpdatePegawai(con, pegawai);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil disimpan");
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
    private void deletePegawai(Pegawai p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete pegawai "+p.getKodePegawai()+"-"+p.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deletePegawai(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPegawai();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pegawai berhasil dihapus");
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
    
}
