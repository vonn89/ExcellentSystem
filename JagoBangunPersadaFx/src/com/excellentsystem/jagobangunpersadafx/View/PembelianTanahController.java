/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.PembelianTanahDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PembelianTanah;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembelianTanahController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
public class PembelianTanahController {

    @FXML
    private TableView<PembelianTanah> pembelianTanahTable;
    @FXML
    private TableColumn<PembelianTanah, String> noPembelianColumn;
    @FXML
    private TableColumn<PembelianTanah, String> tglPembelianColumn;
    @FXML
    private TableColumn<PembelianTanah, String> kodePropertyColumn;
    @FXML
    private TableColumn<PembelianTanah, String> namaPropertyColumn;
    @FXML
    private TableColumn<PembelianTanah, String> alamatColumn;
    @FXML
    private TableColumn<PembelianTanah, String> keteranganColumn;
    @FXML
    private TableColumn<PembelianTanah, Number> luasTanahColumn;
    @FXML
    private TableColumn<PembelianTanah, Number> hargaBeliColumn;
    @FXML
    private TableColumn<PembelianTanah, Number> hargaBeliPerMeterColumn;
    @FXML
    private TableColumn<PembelianTanah, String> statusColumn;
    @FXML
    private TableColumn<PembelianTanah, String> kodeUserColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPembelian;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private Main mainApp;
    private ObservableList<PembelianTanah> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianTanah> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        noPembelianColumn.setCellFactory(col -> Function.getWrapTableCell(noPembelianColumn));

        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembelianColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembelianColumn));
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));

        kodePropertyColumn.setCellValueFactory(cellData -> cellData.getValue().kodePropertyProperty());
        kodePropertyColumn.setCellFactory(col -> Function.getWrapTableCell(kodePropertyColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        statusColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().statusProperty());
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        luasTanahColumn.setCellValueFactory(cellData -> cellData.getValue().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));

        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> getTableCell(rp));

        hargaBeliPerMeterColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHargaBeli() / cellData.getValue().getLuasTanah());
        });
        hargaBeliPerMeterColumn.setCellFactory(col -> getTableCell(rp));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));

        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pembelian Tanah");
        addNew.setOnAction((ActionEvent e) -> {
            addNewPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPembelian();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pembelian Tanah") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        pembelianTanahTable.setContextMenu(rowMenu);
        pembelianTanahTable.setRowFactory(tv -> {
            TableRow<PembelianTanah> row = new TableRow<PembelianTanah>() {
                @Override
                public void updateItem(PembelianTanah item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pembelian Tanah");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewPembelian();
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian Tanah");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePembelian(item);
                        });
                        MenuItem detailPembelian = new MenuItem("Detail Pembelian Tanah");
                        detailPembelian.setOnAction((ActionEvent event) -> {
                            detailPembelian(item);
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pembelian Tanah") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pembelian Tanah") && o.isStatus()) {
                                rowMenu.getItems().add(detailPembelian);
                            }
                            if (o.getJenis().equals("Detail Property") && o.isStatus()) {
                                rowMenu.getItems().add(detailProperty);
                            }
                            if (o.getJenis().equals("Batal Pembelian Tanah") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
                            }
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    for (Otoritas o : sistem.getUser().getOtoritas()) {
                        if (o.getJenis().equals("Detail Pembelian Tanah") && o.isStatus()) {
                            detailPembelian(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianTanah> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelian();
                });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        pembelianTanahTable.setItems(filterData);
        getPembelian();
    }

    @FXML
    private void getPembelian() {
        Task<List<PembelianTanah>> task = new Task<List<PembelianTanah>>() {
            @Override
            public List<PembelianTanah> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PembelianTanah> listPembelian = PembelianTanahDAO.getAllByDateAndStatus(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    ArrayList<String> status = new ArrayList<>();
                    status.add("Available");
                    status.add("Combined");
                    status.add("Mapped");
                    status.add("Reserved");
                    status.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, status);
                    for (PembelianTanah beli : listPembelian) {
                        for (Property p : listProperty) {
                            if (beli.getKodeProperty().equals(p.getKodeProperty())) {
                                beli.setProperty(p);
                            }
                        }
                    }
                    return listPembelian;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allPembelian.clear();
                allPembelian.addAll(task.getValue());
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

    private void searchPembelian() {
        filterData.clear();
        for (PembelianTanah temp : allPembelian) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoPembelian())
                        || checkColumn(temp.getTglPembelian())
                        || checkColumn(temp.getKodeProperty())
                        || checkColumn(temp.getKeterangan())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getAlamat())
                        || checkColumn(qty.format(temp.getLuasTanah()))
                        || checkColumn(rp.format(temp.getHargaBeli()))
                        || checkColumn(temp.getKeterangan())
                        || checkColumn(temp.getKodeUser())
                        || checkColumn(temp.getProperty().getStatus())) {
                    filterData.add(temp);
                }
            }
        }
        hitungTotal();
    }

    private void hitungTotal() {
        double total = 0;
        for (PembelianTanah pt : filterData) {
            total = total + pt.getHargaBeli();
        }
        totalPembelian.setText(rp.format(total));
    }

    private void addNewPembelian() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembelianTanah.fxml");
        DetailPembelianTanahController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewPembelian();
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if ("".equals(x.luasTanahField.getText()) || "0".equals(x.luasTanahField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah masih kosong");
            } else if ("".equals(x.hargaBeliField.getText()) || "0".equals(x.hargaBeliField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Harga tanah masih kosong");
            } else if ("".equals(x.jumlahBayarField.getText()) || "0".equals(x.jumlahBayarField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah bayar masih kosong");
            } else if (Double.parseDouble(x.hargaBeliField.getText().replaceAll(",", ""))
                    < Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah bayar melebihi harga tanah");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe Keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PembelianTanah beli = new PembelianTanah();
                            beli.setNoPembelian(x.noPembelianField.getText());
                            beli.setTglPembelian(tglSql.format(new Date()));
                            beli.setKodeProperty(x.kodePropertyField.getText());
                            beli.setKeterangan(x.keteranganField.getText());
                            beli.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                            beli.setHargaBeli(Double.parseDouble(x.hargaBeliField.getText().replaceAll(",", "")));
                            beli.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            beli.setKodeUser(sistem.getUser().getUsername());
                            beli.setStatus("true");
                            beli.setTglBatal("2000-01-01 00:00:00");
                            beli.setUserBatal("");
                            Property prop = new Property();
                            prop.setKodeProperty(x.kodePropertyField.getText());
                            prop.setKodeKategori("");
                            prop.setNamaProperty(x.namaPropertyField.getText());
                            prop.setAlamat(x.alamatField.getText());
                            prop.setTipe("");
                            prop.setSpesifikasi("");
                            prop.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                            prop.setLuasBangunan(0);
                            prop.setNilaiProperty(Double.parseDouble(x.hargaBeliField.getText().replaceAll(",", "")));
                            prop.setHargaJual(0);
                            prop.setDiskon(0);
                            prop.setKeterangan(x.keteranganField.getText());
                            prop.setStatus("Available");
                            beli.setProperty(prop);
                            return Service.savePembelianTanah(con, beli,
                                    Double.parseDouble(x.jumlahBayarField.getText().replaceAll(",", "")));
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian tanah berhasil disimpan");
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

    private void detailPembelian(PembelianTanah p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembelianTanah.fxml");
        DetailPembelianTanahController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setPembelian(p);
    }

    private void deletePembelian(PembelianTanah pembelian) {
        if (!pembelian.getProperty().getStatus().equals("Available")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pembelian tanah tidak dapat dibatalkan, "
                    + "karena status tanah terjual/sudah dipecah/sudah digabung");
        } else {
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Batal pembelian tanah " + pembelian.getNoPembelian() + ", anda yakin ?");
            controller.OK.setOnAction((ActionEvent ev) -> {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            pembelian.setTglBatal(tglSql.format(new Date()));
                            pembelian.setUserBatal(sistem.getUser().getUsername());
                            pembelian.setStatus("false");
                            return Service.batalPembelianTanah(con, pembelian);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data Pembelian berhasil dibatal");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            });
        }
    }

    private void detailProperty(Property p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setProperty(p);
        x.viewOnly();
    }
}
