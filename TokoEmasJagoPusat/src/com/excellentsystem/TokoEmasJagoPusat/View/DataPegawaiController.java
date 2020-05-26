/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.PegawaiDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.Pegawai;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPegawaiController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class DataPegawaiController  {

    @FXML private TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> kodePegawaiColumn;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    @FXML private TableColumn<Pegawai, String> jenisKelaminColumn;
    @FXML private TableColumn<Pegawai, String> alamatColumn;
    @FXML private TableColumn<Pegawai, String> noTelpColumn;
    @FXML private TableColumn<Pegawai, String> jabatanColumn;
    @FXML private TableColumn<Pegawai, String> tanggalMasukColumn;
    @FXML private TableColumn<Pegawai, String> tanggalKeluarColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPegawaiField;
    private ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private ObservableList<Pegawai> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    public void initialize() {
        kodePegawaiColumn.setCellValueFactory(cellData -> cellData.getValue().kodePegawaiProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        jenisKelaminColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKelaminProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        jabatanColumn.setCellValueFactory(cellData -> cellData.getValue().jabatanProperty());
        tanggalMasukColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggalMasuk())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalMasukColumn.setComparator((String t, String t1) -> {
            try{
                return Long.compare(tglNormal.parse(t).getTime(),tglNormal.parse(t1).getTime());
            }catch(ParseException e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            return -1;
        });
        tanggalKeluarColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggalKeluar())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalKeluarColumn.setComparator((String t, String t1) -> {
            try{
                return Long.compare(tglNormal.parse(t).getTime(),tglNormal.parse(t1).getTime());
            }catch(ParseException e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            return -1;
        });
        final ContextMenu menu = new ContextMenu();
        MenuItem tambah = new MenuItem("Tambah Pegawai Baru");
        tambah.setOnAction((ActionEvent event) -> {
            addNewPegawai();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPegawai();
        });
        menu.getItems().addAll(tambah, refresh);
        pegawaiTable.setContextMenu(menu);
        pegawaiTable.setRowFactory(table -> {
            TableRow<Pegawai> row = new TableRow<Pegawai>(){
                @Override
                public void updateItem(Pegawai item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem tambah = new MenuItem("Tambah Pegawai Baru");
                        tambah.setOnAction((ActionEvent event) -> {
                            addNewPegawai();
                        });
                        MenuItem ubah = new MenuItem("Ubah Pegawai");
                        ubah.setOnAction((ActionEvent event) -> {
                            editPegawai(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Pegawai");
                        hapus.setOnAction((ActionEvent event) -> {
                            deletePegawai(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getPegawai();
                        });
                        rowMenu.getItems().addAll(tambah,ubah,hapus,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
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
        pegawaiTable.setItems(filterData);
    }    
    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPegawai();
    }
    private void getPegawai(){
        Task<List<Pegawai>> task = new Task<List<Pegawai>>() {
            @Override 
            public List<Pegawai> call() throws Exception{
                try(Connection con = KoneksiPusat.getConnection()){
                    return PegawaiDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPegawai.clear();
            allPegawai.addAll(task.getValue());
            pegawaiTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
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
        try{
            filterData.clear();
            for (Pegawai temp : allPegawai) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodePegawai())||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getTanggalMasuk())))||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getTanggalKeluar())))||
                        checkColumn(temp.getNama())||
                        checkColumn(temp.getAlamat())||
                        checkColumn(temp.getNoTelp())||
                        checkColumn(temp.getJenisKelamin())||
                        checkColumn(temp.getJabatan()))
                        filterData.add(temp);
                }
            }
            totalPegawaiField.setText(rp.format(filterData.size()));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void deletePegawai(Pegawai p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus pegawai "+p.getKodePegawai()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = KoneksiPusat.getConnection()){
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
                    mainApp.showMessage(Modality.NONE, "Success", "Pegawai berhasil dihapus");
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
    private void addNewPegawai(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodePegawaiField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode pegawai masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = KoneksiPusat.getConnection()){
                            Pegawai p = new Pegawai();
                            p.setKodePegawai(controller.kodePegawaiField.getText());
                            p.setNama(controller.namaField.getText());
                            p.setAlamat(controller.alamatField.getText());
                            p.setNoTelp(controller.noTelpField.getText());
                            p.setJenisKelamin(controller.jenisKelaminCombo.getSelectionModel().getSelectedItem());
                            p.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                            p.setTanggalMasuk(controller.tglMasukPicker.getValue().toString());
                            p.setTanggalKeluar("2000-01-01");
                            p.setStatus("true");
                            return Service.saveNewPegawai(con, p);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Pegawai berhasil disimpan");
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
    private void editPegawai(Pegawai p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPegawai.fxml");
        DetailPegawaiController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPegawai(p);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodePegawaiField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Kode pegawai masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection con = KoneksiPusat.getConnection()){
                            p.setNama(controller.namaField.getText());
                            p.setAlamat(controller.alamatField.getText());
                            p.setNoTelp(controller.noTelpField.getText());
                            p.setJenisKelamin(controller.jenisKelaminCombo.getSelectionModel().getSelectedItem());
                            p.setJabatan(controller.jabatanCombo.getSelectionModel().getSelectedItem());
                            p.setTanggalMasuk(controller.tglMasukPicker.getValue().toString());
                            p.setTanggalKeluar("2000-01-01");
                            p.setStatus("true");
                            return Service.saveUpdatePegawai(con, p);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Pegawai berhasil disimpan");
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
}
