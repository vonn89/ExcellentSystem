/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPekerjaanController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
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
 * @author Yunaz
 */
public class DataPekerjaanController  {

    @FXML private TableView<Pekerjaan> barangTable;
    @FXML private TableColumn<Pekerjaan, String> kodeKategoriColumn;
    @FXML private TableColumn<Pekerjaan, String> kodePekerjaanColumn;
    @FXML private TableColumn<Pekerjaan, String> namaPekerjaanColumn;
    @FXML private TableColumn<Pekerjaan, String> spesifikasiColumn;
    @FXML private TableColumn<Pekerjaan, String> keteranganColumn;
    @FXML private TableColumn<Pekerjaan, String> satuanColumn;
    @FXML private TableColumn<Pekerjaan, Number> hargaColumn;
    @FXML private TextField searchField;
    private Main mainApp;
    private final ObservableList<Pekerjaan> allPekerjaan = FXCollections.observableArrayList();
    private final ObservableList<Pekerjaan> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriPekerjaanProperty());
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().labaProperty());
        hargaColumn.setCellFactory((cellData) -> Function.getTableCell());
        allPekerjaan.addListener((ListChangeListener.Change<? extends Pekerjaan> change) -> {
            searchPekerjaan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPekerjaan();
        });
        filterData.addAll(allPekerjaan);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pekerjaan");
        addNew.setOnAction((ActionEvent e)->{
            newPekerjaan();
        });
        MenuItem addNewKategori = new MenuItem("Add New Kategori Pekerjaan");
        addNewKategori.setOnAction((ActionEvent e)->{
            mainApp.showKategoriPekerjaan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPekerjaan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pekerjaan")&&o.isStatus())
                rowMenu.getItems().add(addNew);
            if(o.getJenis().equals("Kategori Pekerjaan")&&o.isStatus())
                rowMenu.getItems().add(addNewKategori);
        }
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<Pekerjaan> row = new TableRow<Pekerjaan>() {
                @Override
                public void updateItem(Pekerjaan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pekerjaan");
                        addNew.setOnAction((ActionEvent e)->{
                            newPekerjaan();
                        });
                        MenuItem addNewKategori = new MenuItem("Add New Kategori Pekerjaan");
                        addNewKategori.setOnAction((ActionEvent e)->{
                            mainApp.showKategoriPekerjaan();
                        });
                        MenuItem edit = new MenuItem("Edit Pekerjaan");
                        edit.setOnAction((ActionEvent e)->{
                            updatePekerjaan(item);
                        });
                        MenuItem delete = new MenuItem("Delete Pekerjaan");
                        delete.setOnAction((ActionEvent e) -> {
                            deletePekerjaan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPekerjaan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pekerjaan")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Kategori Pekerjaan")&&o.isStatus())
                                rowMenu.getItems().add(addNewKategori);
                            if(o.getJenis().equals("Edit Pekerjaan")&&o.isStatus())
                                rowMenu.getItems().add(edit);
                            if(o.getJenis().equals("Delete Pekerjaan")&&o.isStatus())
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
                        if(o.getJenis().equals("Edit Pekerjaan")&&o.isStatus())
                            updatePekerjaan(row.getItem());
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPekerjaan();
        barangTable.setItems(filterData);
    }
    private void getPekerjaan() {
        Task<List<Pekerjaan>> task = new Task<List<Pekerjaan>>() {
            @Override 
            public List<Pekerjaan> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    return PekerjaanDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPekerjaan.clear();
            allPekerjaan.addAll(task.getValue());
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
    private void searchPekerjaan() {
        filterData.clear();
        allPekerjaan.forEach((temp) -> {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodePekerjaan()) ||
                    checkColumn(temp.getKategoriPekerjaan()) ||
                    checkColumn(temp.getNamaPekerjaan()) ||
                    checkColumn(temp.getSpesifikasi()) ||
                    checkColumn(temp.getKeterangan()) ||
                    checkColumn(df.format(temp.getKeterangan())) ||
                    checkColumn(temp.getSatuan())) 
                    filterData.add(temp);
            }
        });
    }
    private void newPekerjaan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPekerjaan.fxml");
        DetailPekerjaanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPekerjaanDetail(null);
        x.saveButton.setOnAction((event) -> {
            if(x.namaPekerjaanField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama pekerjaan masih kosong");
            else if (x.kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("")) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(x.satuanField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan masih kosong");
            else if(x.hargaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Laba % masih kosong");
            else {
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            Pekerjaan p = new Pekerjaan();
                            p.setKategoriPekerjaan(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                            p.setNamaPekerjaan(x.namaPekerjaanField.getText());
                            p.setSpesifikasi(x.spesifikasiField.getText());
                            p.setKeterangan(x.keteranganField.getText());
                            p.setSatuan(x.satuanField.getText());
                            p.setLaba(Double.parseDouble(x.hargaField.getText().replaceAll(",","")));
                            p.setStatus("true");
                            return Service.saveNewPekerjaan(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPekerjaan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pekerjaan berhasil disimpan");
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
    private void updatePekerjaan(Pekerjaan p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPekerjaan.fxml");
        DetailPekerjaanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPekerjaanDetail(p);
        x.saveButton.setOnAction((event) -> {
            if(x.namaPekerjaanField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama Pekerjaan masih kosong");
            else if (x.kodeKategoriCombo.getSelectionModel().getSelectedItem().equals("")) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(x.satuanField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Satuan masih kosong");
            else if(x.hargaField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Laba % masih kosong");
            else {
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            p.setKategoriPekerjaan(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                            p.setNamaPekerjaan(x.namaPekerjaanField.getText());
                            p.setSpesifikasi(x.spesifikasiField.getText());
                            p.setKeterangan(x.keteranganField.getText());
                            p.setSatuan(x.satuanField.getText());
                            p.setLaba(Double.parseDouble(x.hargaField.getText().replaceAll(",","")));
                            p.setStatus("true");
                            return Service.saveUpdatePekerjaan(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPekerjaan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pekerjaan berhasil disimpan");
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
    private void deletePekerjaan(Pekerjaan p) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete pekerjaan " + p.getKodePekerjaan() + "-" + p.getNamaPekerjaan() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return Service.deletePekerjaan(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                getPekerjaan();
                String status = task.getValue();
                if (status.equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data pekerjaan berhasil dihapus");
                } else {
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
