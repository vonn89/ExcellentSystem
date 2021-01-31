/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPembayaranDownPaymentController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananPembelianBahanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PemesananPembelianBahanController {

    @FXML
    private TableView<PemesananPembelianBahanHead> pemesananTable;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> noPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> tglPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> namaSupplierColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> noKontrakColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> paymentTermColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> deliveryTermColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, Number> totalPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, Number> sisaPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, Number> downPaymentColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, Number> sisaDownPaymentColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> statusColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> catatanColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Label totalPemesananField;
    @FXML
    private Label sisaPemesananField;
    @FXML
    private Label totalDownPaymentField;
    @FXML
    private Label totalSisaDownPaymentField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> groupByCombo;
    private ObservableList<PemesananPembelianBahanHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananPembelianBahanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTableCell(namaSupplierColumn));

        noKontrakColumn.setCellValueFactory(cellData -> cellData.getValue().noKontrakProperty());
        noKontrakColumn.setCellFactory(col -> Function.getWrapTableCell(noKontrakColumn));

        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));

        deliveryTermColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTermProperty());
        deliveryTermColumn.setCellFactory(col -> Function.getWrapTableCell(deliveryTermColumn));

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        statusColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStatus().equals("close")) {
                return new SimpleStringProperty("Done");
            } else if (cellData.getValue().getStatus().equals("open")) {
                return new SimpleStringProperty("Wait");
            } else if (cellData.getValue().getStatus().equals("false")) {
                return new SimpleStringProperty("Cancel");
            } else {
                return null;
            }
        });
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));

        tglPemesananColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));

        totalPemesananColumn.setCellValueFactory(celldata -> celldata.getValue().totalPemesananProperty());
        totalPemesananColumn.setCellFactory(col -> Function.getTableCell());

        sisaPemesananColumn.setCellValueFactory(celldata -> {
            double sisaPemesanan = 0;
            for (PemesananPembelianBahanDetail d : celldata.getValue().getListPemesananPembelianBahanDetail()) {
                sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyDiterima()) * d.getHarga());
            }
            return new SimpleDoubleProperty(sisaPemesanan);
        });
        sisaPemesananColumn.setCellFactory(col -> Function.getTableCell());

        downPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().downPaymentProperty());
        downPaymentColumn.setCellFactory(col -> Function.getTableCell());

        sisaDownPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().sisaDownPaymentProperty());
        sisaDownPaymentColumn.setCellFactory(col -> Function.getTableCell());

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusYears(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pemesanan Pembelian");
        addNew.setOnAction((ActionEvent e) -> {
            newPemesanan();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPemesanan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pemesanan Pembelian Bahan") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        pemesananTable.setContextMenu(rm);
        pemesananTable.setRowFactory((TableView<PemesananPembelianBahanHead> tableView) -> {
            final TableRow<PemesananPembelianBahanHead> row = new TableRow<PemesananPembelianBahanHead>() {
                @Override
                public void updateItem(PemesananPembelianBahanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pemesanan Pembelian");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPemesanan();
                        });
                        MenuItem detail = new MenuItem("Detail Pemesanan Pembelian");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPemesanan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pemesanan Pembelian");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPemesanan(item);
                        });
                        MenuItem selesai = new MenuItem("Pemesanan Pembelian Selesai");
                        selesai.setOnAction((ActionEvent e) -> {
                            selesaiPemesanan(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran DP");
                        bayar.setOnAction((ActionEvent e) -> {
                            pembayaranDownPayment(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran DP");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            lihatPembayaranDownPayment(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPemesanan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pemesanan Pembelian Bahan") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pemesanan Pembelian Bahan") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Batal Pemesanan Pembelian Bahan") && o.isStatus()
                                    && (item.getStatus().equals("open") || item.getStatus().equals("wait")) && item.getDownPayment() == 0) {
                                rm.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Pemesanan Pembelian Bahan Selesai") && o.isStatus()
                                    && item.getStatus().equals("open") && item.getSisaDownPayment() == 0) {
                                rm.getItems().add(selesai);
                            }
                            if (o.getJenis().equals("Pembayaran DP Pembelian Bahan") && o.isStatus()
                                    && item.getStatus().equals("open")) {
                                rm.getItems().add(bayar);
                            }
                            if (o.getJenis().equals("Detail Pembayaran DP Pembelian Bahan") && o.isStatus()) {
                                rm.getItems().add(detailBayar);
                            }
                            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                                rm.getItems().add(export);
                            }
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };

            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Pemesanan Pembelian Bahan") && o.isStatus()) {
                                lihatDetailPemesanan(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananPembelianBahanHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPemesanan();
                });
        filterData.addAll(allPemesanan);
        pemesananTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Wait");
        groupBy.add("Done");
        groupBy.add("Cancel");
        groupBy.add("Semua");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Wait");
        getPemesanan();
    }

    @FXML
    private void getPemesanan() {
        Task<List<PemesananPembelianBahanHead>> task = new Task<List<PemesananPembelianBahanHead>>() {
            @Override
            public List<PemesananPembelianBahanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "close";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "open";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    }
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PemesananPembelianBahanHead> allPemesanan = PemesananPembelianBahanHeadDAO.getAllByDateAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    List<PemesananPembelianBahanDetail> listPemesananDetail = PemesananPembelianBahanDetailDAO.getAllByDateAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    for (PemesananPembelianBahanHead p : allPemesanan) {
                        for (Supplier c : allSupplier) {
                            if (p.getKodeSupplier().equals(c.getKodeSupplier())) {
                                p.setSupplier(c);
                            }
                        }
                        List<PemesananPembelianBahanDetail> detail = new ArrayList<>();
                        for (PemesananPembelianBahanDetail d : listPemesananDetail) {
                            if (p.getNoPemesanan().equals(d.getNoPemesanan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPemesananPembelianBahanDetail(detail);
                    }
                    return allPemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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

    private void searchPemesanan() {
        try {
            filterData.clear();
            for (PemesananPembelianBahanHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))
                            || checkColumn(temp.getNoKontrak())
                            || checkColumn(temp.getSupplier().getNama())
                            || checkColumn(temp.getSupplier().getAlamat())
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(df.format(temp.getTotalPemesanan()))
                            || checkColumn(df.format(temp.getDownPayment()))
                            || checkColumn(df.format(temp.getSisaDownPayment()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getStatus())) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double total = 0;
        double sisa = 0;
        double dp = 0;
        double sisaDp = 0;
        for (PemesananPembelianBahanHead temp : filterData) {
            total = total + temp.getTotalPemesanan();
            for (PemesananPembelianBahanDetail d : temp.getListPemesananPembelianBahanDetail()) {
                sisa = sisa + ((d.getQty() - d.getQtyDiterima()) * d.getHarga());
            }
            dp = dp + temp.getDownPayment();
            sisaDp = sisaDp + temp.getSisaDownPayment();
        }
        totalPemesananField.setText(df.format(total));
        sisaPemesananField.setText(df.format(sisa));
        totalDownPaymentField.setText(df.format(dp));
        totalSisaDownPaymentField.setText(df.format(sisaDp));
    }

    private void newPemesanan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananPembelianBahan.fxml");
        NewPemesananPembelianBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewPemesanan();
        controller.saveButton.setOnAction((event) -> {
            if (controller.supplier == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
            } else if (controller.paymentTermCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Payment term belum dipilih");
            } else if (controller.deliveryTermCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Delivery term belum dipilih");
            } else if (controller.allPemesananDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PemesananPembelianBahanHead pemesanan = new PemesananPembelianBahanHead();
                            pemesanan.setKodeSupplier(controller.supplier.getKodeSupplier());
                            pemesanan.setNoKontrak(controller.noKontrakField.getText());
                            pemesanan.setPaymentTerm(controller.paymentTermCombo.getSelectionModel().getSelectedItem());
                            pemesanan.setDeliveryTerm(controller.deliveryTermCombo.getSelectionModel().getSelectedItem());
                            pemesanan.setTotalPemesanan(Double.parseDouble(controller.totalPemesananField.getText().replaceAll(",", "")));
                            pemesanan.setDownPayment(0);
                            pemesanan.setSisaDownPayment(0);
                            pemesanan.setCatatan(controller.catatanField.getText());
                            pemesanan.setKodeUser(sistem.getUser().getKodeUser());
                            pemesanan.setTglBatal("2000-01-01 00:00:00");
                            pemesanan.setUserBatal("");
                            pemesanan.setStatus("open");
                            pemesanan.setListPemesananPembelianBahanDetail(controller.allPemesananDetail);
                            return Service.newPemesananPembelianBahan(con, pemesanan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }

    private void lihatDetailPemesanan(PemesananPembelianBahanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananPembelianBahan.fxml");
        NewPemesananPembelianBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p.getNoPemesanan());
    }

    private void batalPemesanan(PemesananPembelianBahanHead p) {
        boolean statusBarang = false;
        for (PemesananPembelianBahanDetail d : p.getListPemesananPembelianBahanDetail()) {
            if (d.getQtyDiterima() > 0) {
                statusBarang = true;
            }
        }
        if (statusBarang) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan tidak dapat dibatalkan, karena sudah ada penerimaan barang");
        } else {
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Batal pemesanan " + p.getNoPemesanan() + " , anda yakin ?");
            controller.OK.setOnAction((ActionEvent evt) -> {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            return Service.batalPemesananPembelianBahan(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((we) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan pembelian bahan berhasil dibatal");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
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

    private void selesaiPemesanan(PemesananPembelianBahanHead p) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Pemesanan " + p.getNoPemesanan() + " telah selesai , anda yakin ?");
        controller.OK.setOnAction((ActionEvent evt) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        p.setStatus("close");
                        return Service.selesaiPemesananPembelianBahan(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((we) -> {
                mainApp.closeLoading();
                getPemesanan();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil disimpan");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }

    private void pembayaranDownPayment(PemesananPembelianBahanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranDownPayment(p);
        controller.saveButton.setOnAction((event) -> {
            if (controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            p.setListPemesananPembelianBahanDetail(PemesananPembelianBahanDetailDAO.getAllByNoPemesanan(con, p.getNoPemesanan()));
                            double totalDikirim = 0;
                            for (PemesananPembelianBahanDetail d : p.getListPemesananPembelianBahanDetail()) {
                                totalDikirim = totalDikirim + (d.getQtyDiterima() * d.getHarga());
                            }
                            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
                            double sisaPembayaran = p.getTotalPemesanan() - totalDikirim - p.getSisaDownPayment();
                            if (jumlahBayar > sisaPembayaran) {
                                return "Jumlah yang dibayar melebihi dari sisa pembayaran";
                            } else {
                                p.setDownPayment(p.getDownPayment() + jumlahBayar);
                                p.setSisaDownPayment(p.getSisaDownPayment() + jumlahBayar);
                                return Service.newPembayaranDownPayment(con, p, jumlahBayar,
                                        controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran DP berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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

    private void lihatPembayaranDownPayment(PemesananPembelianBahanHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembayaranDownPayment.fxml");
        DetailPembayaranDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesananPembelianBahan(p);
        controller.piutangTable.setRowFactory((TableView<Piutang> tv) -> {
            final TableRow<Piutang> row = new TableRow<Piutang>() {
                @Override
                public void updateItem(Piutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembayaran(p, item, stage);
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Batal Pembayaran DP Pembelian Bahan") && o.isStatus()
                                    && item.getStatus().equals("open") && p.getSisaDownPayment() >= item.getJumlahPiutang()) {
                                rm.getItems().add(batal);
                            }
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }

    private void batalPembayaran(PemesananPembelianBahanHead p, Piutang pt, Stage stage) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembayaran " + pt.getNoPiutang() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalPembayaranDownPayment(con, pt);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPemesanan();
                if (task.getValue().equals("true")) {
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                } else {
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }

    private void exportExcel() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Sheet sheet = workbook.createSheet("Data Pemesanan Pembelian Bahan");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Status : " + groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pemesanan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pemesanan");
                sheet.getRow(rc).getCell(2).setCellValue("Supplier");
                sheet.getRow(rc).getCell(3).setCellValue("No Kontrak");
                sheet.getRow(rc).getCell(4).setCellValue("Payment Term");
                sheet.getRow(rc).getCell(5).setCellValue("Delivery Term");
                sheet.getRow(rc).getCell(6).setCellValue("Total Pemesanan");
                sheet.getRow(rc).getCell(7).setCellValue("Sisa Pemesanan");
                sheet.getRow(rc).getCell(8).setCellValue("Pembayaran Down Payment");
                sheet.getRow(rc).getCell(9).setCellValue("Sisa Pembayaran Down Payment");
                sheet.getRow(rc).getCell(10).setCellValue("Catatan");
                rc++;
                double pemesanan = 0;
                double sisaPemesanan = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PemesananPembelianBahanHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPemesanan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getSupplier().getNama());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getNoKontrak());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getPaymentTerm());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getDeliveryTerm());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getTotalPemesanan());
                    double x = 0;
                    for (PemesananPembelianBahanDetail d : b.getListPemesananPembelianBahanDetail()) {
                        x = x + ((d.getQty() - d.getQtyDiterima()) * d.getHarga());
                    }
                    sheet.getRow(rc).getCell(7).setCellValue(x);
                    sheet.getRow(rc).getCell(8).setCellValue(b.getDownPayment());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getSisaDownPayment());
                    sheet.getRow(rc).getCell(10).setCellValue(b.getCatatan());
                    rc++;
                    pemesanan = pemesanan + b.getTotalPemesanan();
                    sisaPemesanan = sisaPemesanan + x;
                    pembayaran = pembayaran + b.getDownPayment();
                    sisaPembayaran = sisaPembayaran + b.getSisaDownPayment();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(6).setCellValue(pemesanan);
                sheet.getRow(rc).getCell(7).setCellValue(sisaPemesanan);
                sheet.getRow(rc).getCell(8).setCellValue(pembayaran);
                sheet.getRow(rc).getCell(9).setCellValue(sisaPembayaran);
                for (int i = 0; i < c; i++) {
                    sheet.autoSizeColumn(i);
                }
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
}
