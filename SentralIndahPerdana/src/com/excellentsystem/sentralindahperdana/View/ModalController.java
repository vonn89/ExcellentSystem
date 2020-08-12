/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.KeuanganDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewModalController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class ModalController  {

    @FXML private TableView<Keuangan> modalTable;
    @FXML private TableColumn<Keuangan, String> noPerubahanModalColumn;
    @FXML private TableColumn<Keuangan, String> tglPerubahanModalColumn;
    @FXML private TableColumn<Keuangan, String> kategoriColumn;
    @FXML private TableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TableColumn<Keuangan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label modalAwalField;
    @FXML private Label modalAkhirField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private double modalAwal = 0;
    private double modalAkhir = 0;
    private Main mainApp;   
    private ObservableList<Keuangan> allModal = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPerubahanModalColumn.setCellValueFactory(cellData -> cellData.getValue().noKeuanganProperty());
        tglPerubahanModalColumn.setCellValueFactory(cellData -> cellData.getValue().tglKeuanganProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTableCell());
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem tambah = new MenuItem("Tambah Modal");
        tambah.setOnAction((ActionEvent e)->{
            showTambahModal();
        });
        MenuItem ambil = new MenuItem("Ambil Modal");
        ambil.setOnAction((ActionEvent event) -> {
            showAmbilModal();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getModal();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Tambah Modal")&&o.isStatus())
                rowMenu.getItems().add(tambah);
            if(o.getJenis().equals("Ambil Modal")&&o.isStatus())
                rowMenu.getItems().add(ambil);
        }
        rowMenu.getItems().addAll(refresh);
        modalTable.setContextMenu(rowMenu);
        allModal.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKeuangan();
        });
        filterData.addAll(allModal);
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getModal();
        modalTable.setItems(filterData);
    }
    @FXML
    private void getModal(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    modalAwal = KeuanganDAO.getSaldoAwal(con, tglMulaiPicker.getValue().toString(), "Modal");
                    modalAkhir = KeuanganDAO.getSaldoAkhir(con, tglAkhirPicker.getValue().toString(), "Modal");
                    List<Keuangan> modal =  KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Modal",
                        tglMulaiPicker.getValue().toString(),tglAkhirPicker.getValue().toString());
                    List<Keuangan> keuanganBefore = KeuanganDAO.getAllByTanggal(
                            con, "", tglAkhirPicker.getValue().toString());
                    double ur = 0;
                    for(Keuangan k : keuanganBefore){
                        if(k.getTipeKeuangan().equalsIgnoreCase("Untung/Rugi")){
                            if(k.getKategori().equalsIgnoreCase("Penjualan"))
                                ur = ur + k.getJumlahRp();
                            if(k.getKategori().equalsIgnoreCase("Beban"))
                                ur = ur - k.getJumlahRp();
                            if(k.getKategori().equalsIgnoreCase("Pendapatan"))
                                ur = ur + k.getJumlahRp();
                            if(k.getKategori().equalsIgnoreCase("HPP"))
                                ur = ur - k.getJumlahRp();
                            if(k.getKategori().equalsIgnoreCase("Retur Penjualan"))
                                ur = ur - k.getJumlahRp();
                        }
                    }
                    modalAkhir = modalAkhir + ur;
                    Keuangan k = new Keuangan();
                    k.setNoKeuangan("-");
                    k.setTglKeuangan(tglSql.format(new Date()));
                    k.setTipeKeuangan("Modal");
                    k.setKategori("Untung/Rugi");
                    k.setKeterangan("");
                    k.setJumlahRp(ur);
                    k.setKodeUser("System");
                    modal.add(k);
                    return modal;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allModal.clear();
            allModal.addAll(task.getValue());
            modalAwalField.setText(df.format(modalAwal));
            modalAkhirField.setText(df.format(modalAkhir));
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null)
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        return false;
    }
    private void searchKeuangan() {
        try{
            filterData.clear();
            for (Keuangan temp : allModal) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoKeuangan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(df.format(temp.getJumlahRp()))||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void showTambahModal(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewModal.fxml");
        NewModalController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setTitle("Tambah Modal");
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahRpField.getText().replaceAll(",", "")) || "".equals(x.jumlahRpField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            Keuangan modal = new Keuangan();
                            modal.setNoKeuangan(KeuanganDAO.getId(con));
                            modal.setTglKeuangan(tglSql.format(new Date()));
                            modal.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            modal.setKategori("Penambahan Modal");
                            modal.setKeterangan(x.keteranganField.getText());
                            modal.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            modal.setKodeUser(sistem.getUser().getUsername());
                            return Service.saveNewModal(con, modal);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getModal();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Penambahan modal berhasil disimpan");
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
    private void showAmbilModal(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewModal.fxml");
        NewModalController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setTitle("Ambil Modal");
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))||
                    "".equals(x.jumlahRpField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            Keuangan modal = new Keuangan();
                            modal.setNoKeuangan(KeuanganDAO.getId(con));
                            modal.setTglKeuangan(tglSql.format(new Date()));
                            modal.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            modal.setKategori("Pengambilan Modal");
                            modal.setKeterangan(x.keteranganField.getText());
                            modal.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", ""))*-1);
                            modal.setKodeUser(sistem.getUser().getUsername());
                            return Service.saveNewModal(con, modal);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getModal();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pengambilan modal berhasil disimpan");
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
