/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.AsetTetapDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.AsetTetap;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailAsetTetapController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewAsetTetapController;
import java.sql.Connection;
import java.text.ParseException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
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
public class AsetTetapController {

    @FXML
    private TableView<AsetTetap> asetTetapTable;
    @FXML
    private TableColumn<AsetTetap, String> noAsetTetapColumn;
    @FXML
    private TableColumn<AsetTetap, String> namaColumn;
    @FXML
    private TableColumn<AsetTetap, String> kategoriColumn;
    @FXML
    private TableColumn<AsetTetap, String> keteranganColumn;
    @FXML
    private TableColumn<AsetTetap, String> masaPakaiColumn;
    @FXML
    private TableColumn<AsetTetap, Number> nilaiAwalColumn;
    @FXML
    private TableColumn<AsetTetap, Number> penyusutanColumn;
    @FXML
    private TableColumn<AsetTetap, Number> nilaiAkhirColumn;
    @FXML
    private TableColumn<AsetTetap, Number> hargaJualColumn;
    @FXML
    private TableColumn<AsetTetap, String> tglBeliColumn;
    @FXML
    private TableColumn<AsetTetap, String> userBeliColumn;
    @FXML
    private TableColumn<AsetTetap, String> tglJualColumn;
    @FXML
    private TableColumn<AsetTetap, String> userJualColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalAsetTetapField;
    @FXML
    private Label totalPenyusutanField;
    @FXML
    private ComboBox<String> groupByCombo;
    private ObservableList<AsetTetap> allAsetTetap = FXCollections.observableArrayList();
    private ObservableList<AsetTetap> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noAsetTetapColumn.setCellValueFactory(cellData -> cellData.getValue().noAsetProperty());
        noAsetTetapColumn.setCellFactory(col -> Function.getWrapTableCell(noAsetTetapColumn));

        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));

        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        userBeliColumn.setCellValueFactory(cellData -> cellData.getValue().userBeliProperty());
        userBeliColumn.setCellFactory(col -> Function.getWrapTableCell(userBeliColumn));

        userJualColumn.setCellValueFactory(cellData -> cellData.getValue().userJualProperty());
        userJualColumn.setCellFactory(col -> Function.getWrapTableCell(userJualColumn));

        masaPakaiColumn.setCellValueFactory(cellData -> {
            String masaPakai = "-";
            if (cellData.getValue().getMasaPakai() != 0) {
                masaPakai = String.valueOf(cellData.getValue().getMasaPakai()) + " Bulan";
            }
            return new SimpleStringProperty(masaPakai);
        });
        masaPakaiColumn.setCellFactory(col -> Function.getWrapTableCell(masaPakaiColumn));

        tglBeliColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getTglBeli())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglBeliColumn.setCellFactory(col -> Function.getWrapTableCell(tglBeliColumn));
        tglBeliColumn.setComparator(Function.sortDate(tglLengkap));

        tglJualColumn.setCellValueFactory(cellData -> {
            try {
                String tglJual = "-";
                if (cellData.getValue().getStatus().equals("close")) {
                    tglJual = tglLengkap.format(tglSql.parse(cellData.getValue().getTglJual()));
                }
                return new SimpleStringProperty(tglJual);
            } catch (ParseException ex) {
                return null;
            }
        });
        tglJualColumn.setCellFactory(col -> Function.getWrapTableCell(tglJualColumn));

        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> getTableCell(rp));

        penyusutanColumn.setCellValueFactory(cellData -> cellData.getValue().penyusutanProperty());
        penyusutanColumn.setCellFactory(col -> getTableCell(rp));

        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> getTableCell(rp));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        final ContextMenu rm = new ContextMenu();
        MenuItem beli = new MenuItem("Pembelian Aset Tetap");
        beli.setOnAction((ActionEvent e) -> {
            showBeliAsetTetap();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getAsetTetap();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Pembelian Aset Tetap") && o.isStatus()) {
                rm.getItems().add(beli);
            }
        }
        rm.getItems().addAll(refresh);
        asetTetapTable.setContextMenu(rm);
        asetTetapTable.setRowFactory((TableView<AsetTetap> tableView) -> {
            final TableRow<AsetTetap> row = new TableRow<AsetTetap>() {
                @Override
                public void updateItem(AsetTetap item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem beli = new MenuItem("Pembelian Aset Tetap");
                        beli.setOnAction((ActionEvent e) -> {
                            showBeliAsetTetap();
                        });
                        MenuItem jual = new MenuItem("Penjualan Aset Tetap");
                        jual.setOnAction((ActionEvent e) -> {
                            showJualAsetTetap(item);
                        });
                        MenuItem lihat = new MenuItem("Detail Aset Tetap");
                        lihat.setOnAction((ActionEvent e) -> {
                            showDetailAsetTetap(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getAsetTetap();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Pembelian Aset Tetap") && o.isStatus()) {
                                rm.getItems().add(beli);
                            }
                            if (o.getJenis().equals("Penjualan Aset Tetap") && o.isStatus() && item.getStatus().equals("open")) {
                                rm.getItems().add(jual);
                            }
                        }
                        rm.getItems().add(lihat);
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        showDetailAsetTetap(row.getItem());
                    }
                }
            });
            return row;
        });
        allAsetTetap.addListener((ListChangeListener.Change<? extends AsetTetap> change) -> {
            searchAsetTetap();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchAsetTetap();
        });
        filterData.addAll(allAsetTetap);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        asetTetapTable.setItems(filterData);
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Tersedia");
        groupBy.add("Terjual");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Tersedia");
        getAsetTetap();
    }

    @FXML
    private void getAsetTetap() {
        Task<List<AsetTetap>> task = new Task<List<AsetTetap>>() {
            @Override
            public List<AsetTetap> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tersedia")) {
                        status = "open";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Terjual")) {
                        status = "close";
                    }
                    return AsetTetapDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allAsetTetap.clear();
            allAsetTetap.addAll(task.getValue());
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

    private void searchAsetTetap() {
        try {
            filterData.clear();
            for (AsetTetap temp : allAsetTetap) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoAset())
                            || checkColumn(temp.getNama())
                            || checkColumn(temp.getKategori())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(String.valueOf(temp.getMasaPakai()))
                            || checkColumn(rp.format(temp.getNilaiAwal()))
                            || checkColumn(rp.format(temp.getPenyusutan()))
                            || checkColumn(rp.format(temp.getNilaiAkhir()))
                            || checkColumn(rp.format(temp.getHargaJual()))
                            || checkColumn(temp.getUserBeli())
                            || checkColumn(temp.getUserJual())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglBeli())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglJual())))) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (ParseException e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double totalAsetTetap = 0;
        double totalPenyusutan = 0;
        for (AsetTetap temp : filterData) {
            totalAsetTetap = totalAsetTetap + temp.getNilaiAkhir();
            totalPenyusutan = totalPenyusutan + temp.getPenyusutan();
        }
        totalAsetTetapField.setText(rp.format(totalAsetTetap));
        totalPenyusutanField.setText(rp.format(totalPenyusutan));
    }

    private void showBeliAsetTetap() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewAsetTetap.fxml");
        NewAsetTetapController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.hargaField.getText().replaceAll(",", ""))
                    || "".equals(x.hargaField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
            } else if ("0".equals(x.jumlahBayarField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahBayarField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah bayar masih kosong");
            } else if (Double.parseDouble(x.hargaField.getText().replaceAll(",", ""))
                    < Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah bayar melebihi harga beli");
            } else if (x.kategoriCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            AsetTetap asetTetap = new AsetTetap();
                            asetTetap.setNoAset(AsetTetapDAO.getId(con));
                            asetTetap.setNama(x.namaField.getText());
                            asetTetap.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            asetTetap.setKeterangan(x.keteranganField.getText());
                            asetTetap.setMasaPakai((Integer.parseInt(x.tahunField.getText()) * 12) + Integer.parseInt(x.bulanField.getText()));
                            asetTetap.setNilaiAwal(Double.parseDouble(x.hargaField.getText().replaceAll(",", "")));
                            asetTetap.setPenyusutan(0);
                            asetTetap.setNilaiAkhir(Double.parseDouble(x.hargaField.getText().replaceAll(",", "")));
                            asetTetap.setHargaJual(0);
                            asetTetap.setStatus("open");
                            asetTetap.setTglJual("2000-01-01 00:00:00");
                            asetTetap.setUserJual("");
                            asetTetap.setTglBeli(tglSql.format(new Date()));
                            asetTetap.setUserBeli(sistem.getUser().getUsername());
                            return Service.savePembelianAsetTetap(con, asetTetap,
                                    Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", "")),
                                    x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getAsetTetap();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian Aset Tetap berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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

    private void showJualAsetTetap(AsetTetap aset) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewAsetTetap.fxml");
        NewAsetTetapController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPenjualanAset(aset);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.hargaField.getText().replaceAll(",", ""))
                    || "".equals(x.hargaField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual masih kosong");
            } else if ("0".equals(x.jumlahBayarField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahBayarField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Bayar masih kosong");
            } else if (Double.parseDouble(x.hargaField.getText().replaceAll(",", ""))
                    < Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah bayar melebihi harga jual");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            aset.setHargaJual(Double.parseDouble(x.hargaField.getText().replaceAll(",", "")));
                            aset.setStatus("close");
                            aset.setTglJual(tglSql.format(new Date()));
                            aset.setUserJual(sistem.getUser().getUsername());
                            return Service.savePenjualanAsetTetap(con, aset,
                                    Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", "")),
                                    x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getAsetTetap();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan aset tetap berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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

    private void showDetailAsetTetap(AsetTetap aset) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailAsetTetap.fxml");
        DetailAsetTetapController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetail(aset);
    }
}
