/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailBarangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
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
 * @author Yunaz
 */
public class DataBarangController  {

    @FXML private TableView<Barang> barangTable;
    @FXML private TableColumn<Barang, String> kodeKategoriColumn;
    @FXML private TableColumn<Barang, String> kodeBarangColumn;
    @FXML private TableColumn<Barang, String> namaBarangColumn;
    @FXML private TableColumn<Barang, String> keteranganColumn;
    @FXML private TableColumn<Barang, String> satuanDasarColumn;
    @FXML private TableColumn<Barang, Number> labaPersenColumn;
    @FXML private TextField searchField;
    private Main mainApp;
    private final ObservableList<Barang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<Barang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBarangProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanDasarColumn.setCellValueFactory(cellData -> cellData.getValue().satuanDasarProperty());
        labaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().labaProperty());
        labaPersenColumn.setCellFactory((cellData) -> Function.getTableCell());
        
        allBarang.addListener((ListChangeListener.Change<? extends Barang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Barang");
        addNew.setOnAction((ActionEvent e)->{
            newBarang();
        });
        MenuItem addNewKategori = new MenuItem("Add New Kategori Barang");
        addNewKategori.setOnAction((ActionEvent e)->{
            mainApp.showKategoriBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getBarang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Barang")&&o.isStatus())
                rowMenu.getItems().add(addNew);
            if(o.getJenis().equals("Kategori Barang")&&o.isStatus())
                rowMenu.getItems().add(addNewKategori);
        }
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<Barang> row = new TableRow<Barang>() {
                @Override
                public void updateItem(Barang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Barang");
                        addNew.setOnAction((ActionEvent e)->{
                            newBarang();
                        });
                        MenuItem addNewKategori = new MenuItem("Add New Kategori Barang");
                        addNewKategori.setOnAction((ActionEvent e)->{
                            mainApp.showKategoriBarang();
                        });
                        MenuItem edit = new MenuItem("Edit Barang");
                        edit.setOnAction((ActionEvent e)->{
                            updateBarang(item);
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Barang")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Kategori Barang")&&o.isStatus())
                                rowMenu.getItems().add(addNewKategori);
                            if(o.getJenis().equals("Edit Barang")&&o.isStatus())
                                rowMenu.getItems().add(edit);
                            if(o.getJenis().equals("Delete Barang")&&o.isStatus())
                                rowMenu.getItems().add(delete);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2 && row.getItem()!=null)
                    for(Otoritas o : sistem.getUser().getOtoritas())
                        if(o.getJenis().equals("Edit Barang")&&o.isStatus())
                            updateBarang(row.getItem());
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getBarang();
        barangTable.setItems(filterData);
    }
    private void getBarang() {
        Task<List<Barang>> task = new Task<List<Barang>>() {
            @Override 
            public List<Barang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "true");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(Barang b : listBarang){
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(b.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        b.setAllSatuan(satuan);
                    }
                    return listBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBarang.clear();
            allBarang.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column) {
        return column != null && column.toLowerCase().contains(searchField.getText().toLowerCase());
    }
    private void searchBarang() {
        filterData.clear();
        allBarang.forEach((temp) -> {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodeBarang()) ||
                    checkColumn(temp.getKategoriBarang()) ||
                    checkColumn(temp.getNamaBarang()) ||
                    checkColumn(temp.getKeterangan()) ||
                    checkColumn(df.format(temp.getLaba())) ||
                    checkColumn(temp.getSatuanDasar())) 
                    filterData.add(temp);
            }
        });
    }
    private void newBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setBarangDetail(null);
        x.saveButton.setOnAction((event) -> {
            if(x.namaBarangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            else if (x.kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("")) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            else if(x.satuanDasarField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan dasar masih kosong");
            else if("".equals(x.labaPersenField.getText()) || Double.parseDouble(x.labaPersenField.getText().replaceAll(",", "")) <= 0) 
                mainApp.showMessage(Modality.NONE, "Warning", "Laba persen masih kosong");
            else {
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            Barang b = new Barang();
                            b.setKategoriBarang(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                            b.setNamaBarang(x.namaBarangField.getText());
                            b.setKeterangan(x.keteranganField.getText());
                            b.setSatuanDasar(x.satuanDasarField.getText());
                            b.setLaba(Double.parseDouble(x.labaPersenField.getText().replaceAll(",", "")));
                            b.setStatus("true");
                            b.setAllSatuan(x.allSatuan);
                            return Service.saveNewBarang(con, b);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data barang berhasil disimpan");
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
    private void updateBarang(Barang b) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setBarangDetail(b);
        x.saveButton.setOnAction((event) -> {
            if(x.namaBarangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama Barang masih kosong");
            else if (x.kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("")) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kode Kategori belum dipilih");
            else if(x.satuanDasarField.getText()==null||x.satuanDasarField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan dasar masih kosong");
            else if("".equals(x.labaPersenField.getText()) || Double.parseDouble(x.labaPersenField.getText().replaceAll(",", "")) <= 0) 
                mainApp.showMessage(Modality.NONE, "Warning", "Laba persen masih kosong");
            else {
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            b.setKategoriBarang(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                            b.setNamaBarang(x.namaBarangField.getText());
                            b.setKeterangan(x.keteranganField.getText());
                            b.setSatuanDasar(x.satuanDasarField.getText());
                            b.setLaba(Double.parseDouble(x.labaPersenField.getText().replaceAll(",", "")));
                            b.setStatus("true");
                            b.setAllSatuan(x.allSatuan);
                            return Service.saveUpdateBarang(con, b);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data barang berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void deleteBarang(Barang barang) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete barang " + barang.getKodeBarang() + "-" + barang.getNamaBarang() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return Service.deleteBarang(con, barang);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                getBarang();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data barang berhasil dihapus");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", status);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }

}
