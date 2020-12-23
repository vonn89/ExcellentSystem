/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.KeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewModalController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
public class ModalController {

    @FXML
    private TableView<Keuangan> modalTable;
    @FXML
    private TableColumn<Keuangan, String> noPerubahanModalColumn;
    @FXML
    private TableColumn<Keuangan, String> tglPerubahanModalColumn;
    @FXML
    private TableColumn<Keuangan, String> kategoriColumn;
    @FXML
    private TableColumn<Keuangan, String> deskripsiColumn;
    @FXML
    private TableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML
    private TableColumn<Keuangan, String> kodeUserColumn;
    @FXML
    private TableColumn<Keuangan, String> tglInputColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label modalAwalField;
    @FXML
    private Label modalAkhirField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private double modalAwal = 0;
    private double modalAkhir = 0;
    private Main mainApp;
    private ObservableList<Keuangan> allModal = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noPerubahanModalColumn.setCellValueFactory(cellData -> cellData.getValue().noKeuanganProperty());
        noPerubahanModalColumn.setCellFactory(col -> Function.getWrapTableCell(noPerubahanModalColumn));

        tglPerubahanModalColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tgl.format(tglBarang.parse(cellData.getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPerubahanModalColumn.setCellFactory(col -> Function.getWrapTableCell(tglPerubahanModalColumn));
        tglPerubahanModalColumn.setComparator(Function.sortDate(tglLengkap));

        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));

        deskripsiColumn.setCellValueFactory(cellData -> cellData.getValue().deskripsiProperty());
        deskripsiColumn.setCellFactory(col -> Function.getWrapTableCell(deskripsiColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        tglInputColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglInput())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglInputColumn.setCellFactory(col -> Function.getWrapTableCell(tglInputColumn));
        tglInputColumn.setComparator(Function.sortDate(tglLengkap));

        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem tambah = new MenuItem("Tambah Modal");
        tambah.setOnAction((ActionEvent e) -> {
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
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Tambah Modal") && o.isStatus()) {
                rowMenu.getItems().add(tambah);
            }
            if (o.getJenis().equals("Ambil Modal") && o.isStatus()) {
                rowMenu.getItems().add(ambil);
            }
        }
        rowMenu.getItems().addAll(refresh);
        modalTable.setContextMenu(rowMenu);
        allModal.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchModal();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchModal();
                });
        filterData.addAll(allModal);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getModal();
        modalTable.setItems(filterData);
    }

    @FXML
    private void getModal() {
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override
            public List<Keuangan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    modalAwal = KeuanganDAO.getSaldoAkhirByDateAndTipeKeuangan(con, tglAwalPicker.getValue().toString(), "MODAL");
                    modalAkhir = KeuanganDAO.getSaldoAkhirByDateAndTipeKeuangan(con, tglAkhirPicker.getValue().toString(), "MODAL");
                    return KeuanganDAO.getAllByTipeKeuanganAndDate(
                            con, "MODAL", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allModal.clear();
                allModal.addAll(task.getValue());
                modalAwalField.setText(rp.format(modalAwal));
                modalAkhirField.setText(rp.format(modalAkhir));
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchModal() {
        try {
            filterData.clear();
            for (Keuangan temp : allModal) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoKeuangan())
                            || checkColumn(tgl.format(tglBarang.parse(temp.getTglKeuangan())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglInput())))
                            || checkColumn(temp.getKategori())
                            || checkColumn(temp.getDeskripsi())
                            || checkColumn(rp.format(temp.getJumlahRp()))
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void showTambahModal() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewModal.fxml");
        NewModalController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setModal("Tambah Modal");
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahRpField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Keuangan modal = new Keuangan();
                            modal.setTglKeuangan(tglBarang.format(new Date()));
                            modal.setTipeKeuangan("MODAL");
                            modal.setKodeProperty("");
                            modal.setKategori("Tambah Modal");
                            modal.setDeskripsi(x.keteranganField.getText());
                            modal.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            modal.setTotalImage(0);
                            modal.setKodeUser(sistem.getUser().getUsername());
                            modal.setTglInput(tglSql.format(new Date()));
                            modal.setStatus("true");
                            modal.setTglBatal("2000-01-01 00:00:00");
                            modal.setUserBatal("");

                            Keuangan keu = new Keuangan();
                            keu.setTglKeuangan(tglBarang.format(new Date()));
                            keu.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            keu.setKodeProperty("");
                            keu.setKategori("Tambah Modal");
                            keu.setDeskripsi(x.keteranganField.getText());
                            keu.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            keu.setTotalImage(0);
                            keu.setKodeUser(sistem.getUser().getUsername());
                            keu.setTglInput(tglSql.format(new Date()));
                            keu.setStatus("true");
                            keu.setTglBatal("2000-01-01 00:00:00");
                            keu.setUserBatal("");

                            List<Keuangan> listKeuangan = new ArrayList<>();
                            listKeuangan.add(modal);
                            listKeuangan.add(keu);
                            return Service.saveAllTransaksiKeuangan(con, listKeuangan);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        getModal();
                        if (task.getValue().equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Penambahan modal berhasil disimpan");
                        } else {
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
                    } catch (Exception ex) {
                        mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }

    private void showAmbilModal() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewModal.fxml");
        NewModalController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setModal("Ambil Modal");
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahRpField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Keuangan modal = new Keuangan();
                            modal.setTglKeuangan(tglBarang.format(new Date()));
                            modal.setTipeKeuangan("MODAL");
                            modal.setKodeProperty("");
                            modal.setKategori("Ambil Modal");
                            modal.setDeskripsi(x.keteranganField.getText());
                            modal.setJumlahRp(-Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            modal.setTotalImage(0);
                            modal.setKodeUser(sistem.getUser().getUsername());
                            modal.setTglInput(tglSql.format(new Date()));
                            modal.setStatus("true");
                            modal.setTglBatal("2000-01-01 00:00:00");
                            modal.setUserBatal("");

                            Keuangan keu = new Keuangan();
                            keu.setTglKeuangan(tglBarang.format(new Date()));
                            keu.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            keu.setKodeProperty("");
                            keu.setKategori("Ambil Modal");
                            keu.setDeskripsi(x.keteranganField.getText());
                            keu.setJumlahRp(-Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            keu.setTotalImage(0);
                            keu.setKodeUser(sistem.getUser().getUsername());
                            keu.setTglInput(tglSql.format(new Date()));
                            keu.setStatus("true");
                            keu.setTglBatal("2000-01-01 00:00:00");
                            keu.setUserBatal("");

                            List<Keuangan> listKeuangan = new ArrayList<>();
                            listKeuangan.add(modal);
                            listKeuangan.add(keu);
                            return Service.saveAllTransaksiKeuangan(con, listKeuangan);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        getModal();
                        if (task.getValue().equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Pengambilan modal berhasil disimpan");
                        } else {
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
                    } catch (Exception ex) {
                        mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
}
