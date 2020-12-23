/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
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
public class TerimaDownPaymentController {

    @FXML
    private TableView<SDP> SDPTable;
    @FXML
    private TableColumn<SDP, String> noSDPColumn;
    @FXML
    private TableColumn<SDP, String> tglSDPColumn;
    @FXML
    private TableColumn<SDP, Number> TahapDPColumn;
    @FXML
    private TableColumn<SDP, Number> jumlahDPColumn;
    @FXML
    private TableColumn<SDP, String> tipeKeuanganDPColumn;
    @FXML
    private TableColumn<SDP, String> kodeKategoriColumn;
    @FXML
    private TableColumn<SDP, String> namaPropertyColumn;
    @FXML
    private TableColumn<SDP, Number> hargaJualColumn;
    @FXML
    private TableColumn<SDP, Number> diskonColumn;
    @FXML
    private TableColumn<SDP, Number> addendumColumn;
    @FXML
    private TableColumn<SDP, String> namaCustomerColumn;
    @FXML
    private TableColumn<SDP, String> namaSalesColumn;
    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private Main mainApp;
    private ObservableList<SDP> allSDP = FXCollections.observableArrayList();
    private ObservableList<SDP> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noSDPColumn.setCellValueFactory(cellData -> cellData.getValue().noSDPProperty());
        noSDPColumn.setCellFactory(col -> Function.getWrapTableCell(noSDPColumn));

        tglSDPColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSDP())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSDPColumn.setCellFactory(col -> Function.getWrapTableCell(tglSDPColumn));
        tglSDPColumn.setComparator(Function.sortDate(tglLengkap));

        jumlahDPColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahDPColumn.setCellFactory(col -> getTableCell(rp));

        TahapDPColumn.setCellValueFactory(cellData -> cellData.getValue().tahapProperty());

        tipeKeuanganDPColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganDPColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganDPColumn));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        diskonColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));

        addendumColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().addendumProperty());
        addendumColumn.setCellFactory(col -> getTableCell(rp));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Terima Down Payment");
        addNew.setOnAction((ActionEvent e) -> {
            addNewTerimaDownPayment();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getSDP();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Terima Down Payment") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        SDPTable.setContextMenu(rowMenu);
        SDPTable.setRowFactory(tv -> {
            TableRow<SDP> row = new TableRow<SDP>() {
                @Override
                public void updateItem(SDP item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Terima Down Payment");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewTerimaDownPayment();
                        });
                        MenuItem print = new MenuItem("Print Surat Down Payment");
                        print.setOnAction((ActionEvent e) -> {
                            printSDP(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Down Payment");
                        batal.setOnAction((ActionEvent event) -> {
                            batalTerimaDownPayment(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailSales = new MenuItem("Detail Sales");
                        detailSales.setOnAction((ActionEvent event) -> {
                            detailSales(item.getSales());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem detailTerimaDP = new MenuItem("Detail Terima Down Payment");
                        detailTerimaDP.setOnAction((ActionEvent event) -> {
                            detailTerimaDownPayment(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSDP();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Terima Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Terima Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(detailTerimaDP);
                            }
                            if (o.getJenis().equals("Detail Customer") && o.isStatus()) {
                                rowMenu.getItems().add(detailCustomer);
                            }
                            if (o.getJenis().equals("Detail Karyawan") && o.isStatus()) {
                                rowMenu.getItems().add(detailSales);
                            }
                            if (o.getJenis().equals("Detail Property") && o.isStatus()) {
                                rowMenu.getItems().add(detailProperty);
                            }
                            if (o.getJenis().equals("Batal Terima Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(print);
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
                        if (o.getJenis().equals("Detail Terima Down Payment") && o.isStatus()) {
                            detailTerimaDownPayment(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allSDP.addListener((ListChangeListener.Change<? extends SDP> change) -> {
            searchSDP();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchSDP();
                });
        filterData.addAll(allSDP);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getSDP();
        SDPTable.setItems(filterData);
    }

    @FXML
    private void getSDP() {
        Task<List<SDP>> task = new Task<List<SDP>>() {
            @Override
            public List<SDP> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<Karyawan> listKaryawan = KaryawanDAO.getAllByStatus(con, "true");
                    List<SDP> listSDP = SDPDAO.getAllByDateAndStatus(
                            con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for (SDP sdp : listSDP) {
                        for (Customer c : listCustomer) {
                            if (sdp.getKodeCustomer().equals(c.getKodeCustomer())) {
                                sdp.setCustomer(c);
                            }
                        }
                        for (Property p : listProperty) {
                            if (sdp.getKodeProperty().equals(p.getKodeProperty())) {
                                sdp.setProperty(p);
                            }
                        }
                        for (Karyawan k : listKaryawan) {
                            if (sdp.getKodeSales().equals(k.getKodeKaryawan())) {
                                sdp.setSales(k);
                            }
                        }
                    }
                    return listSDP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allSDP.clear();
                allSDP.addAll(task.getValue());
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

    private void searchSDP() {
        filterData.clear();
        for (SDP temp : allSDP) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoSDP())
                        || checkColumn(temp.getTglSDP())
                        || checkColumn(temp.getTipeKeuangan())
                        || checkColumn(qty.format(temp.getTahap()))
                        || checkColumn(rp.format(temp.getJumlahRp()))
                        || checkColumn(temp.getCustomer().getKodeCustomer())
                        || checkColumn(temp.getCustomer().getNama())
                        || checkColumn(temp.getCustomer().getJenisKelamin())
                        || checkColumn(temp.getCustomer().getAlamat())
                        || checkColumn(temp.getCustomer().getNoTelp())
                        || checkColumn(temp.getCustomer().getNoHandphone())
                        || checkColumn(temp.getCustomer().getEmail())
                        || checkColumn(temp.getCustomer().getStatusNikah())
                        || checkColumn(temp.getCustomer().getAgama())
                        || checkColumn(temp.getCustomer().getPekerjaan())
                        || checkColumn(temp.getCustomer().getNoKTP())
                        || checkColumn(temp.getCustomer().getNoNPWP())
                        || checkColumn(temp.getCustomer().getNoSPTPPH())
                        || checkColumn(temp.getProperty().getKodeProperty())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(temp.getProperty().getTipe())
                        || checkColumn(temp.getProperty().getAlamat())
                        || checkColumn(temp.getProperty().getSpesifikasi())
                        || checkColumn(qty.format(temp.getProperty().getLuasTanah()))
                        || checkColumn(qty.format(temp.getProperty().getLuasBangunan()))
                        || checkColumn(rp.format(temp.getProperty().getHargaJual()))
                        || checkColumn(rp.format(temp.getProperty().getDiskon()))
                        || checkColumn(rp.format(temp.getProperty().getAddendum()))
                        || checkColumn(temp.getProperty().getKeterangan())
                        || checkColumn(temp.getSales().getKodeKaryawan())
                        || checkColumn(temp.getSales().getNama())
                        || checkColumn(temp.getKodeUser())) {
                    filterData.add(temp);
                }
            }
        }
    }

    private void addNewTerimaDownPayment() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaDownPayment.fxml");
        DetailTerimaDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewSDP();
        x.saveButton.setOnAction((event) -> {
            if (x.sdp.getProperty() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            } else if ("".equals(x.jumlahDPField.getText()) || "0".equals(x.jumlahDPField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warnign", "Jumlah Pembayaran masih kosong");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            x.sdp.setJumlahRp(Double.parseDouble(x.jumlahDPField.getText().replaceAll(",", "")));
                            x.sdp.setJatuhTempo(tgl.format(tglBarang.parse(x.tglJatuhTempo.getValue().toString())));
                            x.sdp.setJumlahTagihan(Double.parseDouble(x.jumlahTagihanField.getText().replaceAll(",", "")));
                            x.sdp.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            x.sdp.setKodeUser(sistem.getUser().getUsername());
                            x.sdp.setStatus("true");
                            x.sdp.setTglBatal("2000-01-01 00:00:00");
                            x.sdp.setUserBatal("");
                            return Service.saveTerimaDownPayment(con, x.sdp);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Terima down payment berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            getSDP();
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

    private void detailTerimaDownPayment(SDP sdp) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaDownPayment.fxml");
        DetailTerimaDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSDP(sdp);
    }

    private void printSDP(SDP sdp) {
        try {
            PrintOut print = new PrintOut();
            print.printSuratDownPayment(sdp);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void batalTerimaDownPayment(SDP sdp) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal terima down payment " + sdp.getNoSDP() + ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        List<SDP> all = SDPDAO.getAllByKodeProperty(con, sdp.getKodeProperty(), "true");
                        boolean statusTahap = true;
                        for (SDP s : all) {
                            if (s.getTahap() > sdp.getTahap()) {
                                statusTahap = false;
                            }
                        }
                        if (SKLHeadDAO.getByKodeProperty(con, sdp.getKodeProperty(), "true") != null) {
                            return "Terima down payment tidak dapat dibatalkan, karena sudah ada pelunasan DP";
                        } else if (statusTahap) {
                            sdp.setTglBatal(tglSql.format(new Date()));
                            sdp.setUserBatal(sistem.getUser().getUsername());
                            sdp.setStatus("false");
                            return Service.batalTerimaDownPayment(con, sdp);
                        } else {
                            return "Terima down payment tidak dapat dibatalkan, karena sudah ada pembayaran DP tahap selanjutnya";
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try {
                    mainApp.closeLoading();
                    getSDP();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data terima down payment berhasil dibatal");
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
        });
    }

    private void detailCustomer(Customer c) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomer(c);
        x.setViewOnly();
    }

    private void detailSales(Karyawan s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKaryawan.fxml");
        DetailKaryawanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKaryawan(s);
        x.setViewOnly();
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
