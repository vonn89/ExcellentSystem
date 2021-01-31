/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailTerimaPembayaranDownPaymentController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananController;
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
public class PemesananController {

    @FXML
    private TableView<PemesananBarangHead> pemesananTable;
    @FXML
    private TableColumn<PemesananBarangHead, String> noPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> tglPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> alamatCustomerColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaInvoiceColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> paymentTermColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> totalPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> sisaPemesananColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> downPaymentColumn;
    @FXML
    private TableColumn<PemesananBarangHead, Number> sisaDownPaymentColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> statusColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> catatanColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> namaSalesColumn;
    @FXML
    private TableColumn<PemesananBarangHead, String> kodeUserColumn;

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
    private ObservableList<PemesananBarangHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));

        namaInvoiceColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomerInvoice().namaProperty());
        namaInvoiceColumn.setCellFactory(col -> Function.getWrapTableCell(namaInvoiceColumn));

        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));

        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        statusColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStatus().equals("close")) {
                return new SimpleStringProperty("Done");
            } else if (cellData.getValue().getStatus().equals("open")) {
                return new SimpleStringProperty("Wait");
            } else if (cellData.getValue().getStatus().equals("false")) {
                return new SimpleStringProperty("Cancel");
            } else if (cellData.getValue().getStatus().equals("wait")) {
                return new SimpleStringProperty("On Review");
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
            for (PemesananBarangDetail d : celldata.getValue().getListPemesananBarangDetail()) {
                sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
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
        MenuItem addNew = new MenuItem("Add New Pemesanan");
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
            if (o.getJenis().equals("Add New Pemesanan") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        pemesananTable.setContextMenu(rm);
        pemesananTable.setRowFactory((TableView<PemesananBarangHead> tableView) -> {
            final TableRow<PemesananBarangHead> row = new TableRow<PemesananBarangHead>() {
                @Override
                public void updateItem(PemesananBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pemesanan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPemesanan();
                        });
                        MenuItem detail = new MenuItem("Detail Pemesanan");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailPemesanan(item);
                        });
                        MenuItem approve = new MenuItem("Approve Pemesanan");
                        approve.setOnAction((ActionEvent e) -> {
                            approvePemesanan(item);
                        });
                        MenuItem edit = new MenuItem("Edit Pemesanan");
                        edit.setOnAction((ActionEvent e) -> {
                            editPemesanan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pemesanan");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPemesanan(item);
                        });
                        MenuItem selesai = new MenuItem("Pemesanan Selesai");
                        selesai.setOnAction((ActionEvent e) -> {
                            selesaiPemesanan(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran DP");
                        bayar.setOnAction((ActionEvent e) -> {
                            terimaPembayaranDownPayment(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Terima Pembayaran DP");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            lihatTerimaPembayaranDownPayment(item);
                        });
                        MenuItem invoice = new MenuItem("Print Order Confirmation");
                        invoice.setOnAction((ActionEvent e) -> {
                            printProformaInvoice(item);
                        });
                        MenuItem invoiceSoftcopy = new MenuItem("Print Order Confirmation Softcopy");
                        invoiceSoftcopy.setOnAction((ActionEvent e) -> {
                            printProformaInvoiceSoftcopy(item);
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
                            if (o.getJenis().equals("Add New Pemesanan") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pemesanan") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Edit Pemesanan") && o.isStatus()) {
                                rm.getItems().add(edit);
                            }
                            if (o.getJenis().equals("Approve Pemesanan") && o.isStatus()
                                    && item.getStatus().equals("wait")) {
                                rm.getItems().add(approve);
                            }
                            if (o.getJenis().equals("Batal Pemesanan") && o.isStatus()
                                    && (item.getStatus().equals("open") || item.getStatus().equals("wait")) && item.getDownPayment() == 0) {
                                rm.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Pemesanan Selesai") && o.isStatus()
                                    && item.getStatus().equals("open") && item.getSisaDownPayment() == 0) {
                                rm.getItems().add(selesai);
                            }
                            if (o.getJenis().equals("Terima Pembayaran DP") && o.isStatus()
                                    && item.getStatus().equals("open")) {
                                rm.getItems().add(bayar);
                            }
                            if (o.getJenis().equals("Detail Terima Pembayaran DP") && o.isStatus()) {
                                rm.getItems().add(detailBayar);
                            }
                            if (o.getJenis().equals("Print Order Confirmation") && o.isStatus()
                                    && item.getStatus().equals("open")) {
                                rm.getItems().addAll(invoice, invoiceSoftcopy);
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
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    double hutang = newValue.getCustomer().getHutang();
                    double limitHutang = newValue.getCustomer().getLimitHutang();
                    double sisaPemesanan = 0;
                    double dp = newValue.getSisaDownPayment();
                    for (PemesananBarangDetail d : newValue.getListPemesananBarangDetail()) {
                        sisaPemesanan = sisaPemesanan + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
                    }
                    if (limitHutang - hutang - sisaPemesanan + dp < 0) {
                        row.setStyle("-fx-background-color: #FFD8D1");//red
                    } else {
                        row.setStyle("");
                    }
                } else {
                    row.setStyle("");
                }
            });

            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Pemesanan") && o.isStatus()) {
                                lihatDetailPemesanan(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananBarangHead> change) -> {
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
        groupBy.add("On Review");
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
        Task<List<PemesananBarangHead>> task = new Task<List<PemesananBarangHead>>() {
            @Override
            public List<PemesananBarangHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "close";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "open";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("On Review")) {
                        status = "wait";
                    }
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<PemesananBarangHead> allPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    List<PemesananBarangDetail> listPemesananDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    for (PemesananBarangHead p : allPemesanan) {
                        for (Customer c : allCustomer) {
                            if (p.getKodeCustomer().equals(c.getKodeCustomer())) {
                                p.setCustomer(c);
                            }
                        }
                        for (Customer c : allCustomer) {
                            if (p.getKodeCustomerInvoice().equals(c.getKodeCustomer())) {
                                p.setCustomerInvoice(c);
                            }
                        }
                        for (Pegawai s : allSales) {
                            if (p.getKodeSales().equals(s.getKodePegawai())) {
                                p.setSales(s);
                            }
                        }
                        List<PemesananBarangDetail> detail = new ArrayList<>();
                        for (PemesananBarangDetail d : listPemesananDetail) {
                            if (p.getNoPemesanan().equals(d.getNoPemesanan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPemesananBarangDetail(detail);
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
            for (PemesananBarangHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))
                            || checkColumn(temp.getCustomerInvoice().getNama())
                            || checkColumn(temp.getCustomerInvoice().getAlamat())
                            || checkColumn(temp.getCustomer().getNama())
                            || checkColumn(temp.getCustomer().getAlamat())
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(df.format(temp.getTotalPemesanan()))
                            || checkColumn(df.format(temp.getDownPayment()))
                            || checkColumn(df.format(temp.getSisaDownPayment()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getSales().getNama())
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
        for (PemesananBarangHead temp : filterData) {
            total = total + temp.getTotalPemesanan();
            for (PemesananBarangDetail d : temp.getListPemesananBarangDetail()) {
                sisa = sisa + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
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
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewPemesanan();
        controller.saveButton.setOnAction((event) -> {
            if (controller.customer == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
            } else if (controller.customer.getLimitHutang() < controller.customer.getHutang()
                    + Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan melebihi limit hutang customer");
            } else if (controller.paymentTermCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Payment term belum dipilih");
            } else if (controller.allPemesananDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PemesananBarangHead pemesanan = new PemesananBarangHead();
                            pemesanan.setKodeCustomer(controller.customer.getKodeCustomer());
                            pemesanan.setKodeCustomerInvoice(controller.customerInvoice.getKodeCustomer());
                            pemesanan.setPaymentTerm(controller.paymentTermCombo.getSelectionModel().getSelectedItem());
                            pemesanan.setTotalPemesanan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            pemesanan.setDownPayment(0);
                            pemesanan.setSisaDownPayment(0);
                            pemesanan.setCatatan(controller.catatanField.getText());
                            pemesanan.setKodeSales(controller.customer.getKodeSales());
                            pemesanan.setKodeUser(sistem.getUser().getKodeUser());
                            pemesanan.setTglBatal("2000-01-01 00:00:00");
                            pemesanan.setUserBatal("");
                            pemesanan.setStatus("open");
                            pemesanan.setListPemesananBarangDetail(controller.allPemesananDetail);
                            return Service.newPemesanan(con, pemesanan);
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

    private void lihatDetailPemesanan(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p.getNoPemesanan());
    }

    private void editPemesanan(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.editPemesanan(p.getNoPemesanan());
        controller.saveButton.setOnAction((event) -> {
            if (controller.customer == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
            } else if (controller.paymentTermCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Payment term belum dipilih");
            } else if (controller.allPemesananDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
            } else if (Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")) < p.getSisaDownPayment()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat disimpan karena jumlah dp lebih besar dari total penjualan");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            p.setKodeCustomer(controller.customer.getKodeCustomer());
                            p.setKodeCustomerInvoice(controller.customerInvoice.getKodeCustomer());
                            p.setPaymentTerm(controller.paymentTermCombo.getSelectionModel().getSelectedItem());
                            p.setTotalPemesanan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            p.setCatatan(controller.catatanField.getText());
                            p.setKodeSales(controller.customer.getKodeSales());
                            p.setKodeUser(sistem.getUser().getKodeUser());
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            p.setStatus("open");
                            int noUrut = 1;
                            for (PemesananBarangDetail temp : controller.allPemesananDetail) {
                                temp.setNoPemesanan(p.getNoPemesanan());
                                temp.setNoUrut(noUrut);
                                noUrut = noUrut + 1;
                            }
                            p.setListPemesananBarangDetail(controller.allPemesananDetail);
                            return Service.editPemesanan(con, p);
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

    private void batalPemesanan(PemesananBarangHead p) {
        boolean statusBarang = false;
        for (PemesananBarangDetail d : p.getListPemesananBarangDetail()) {
            if (d.getQtyTerkirim() > 0) {
                statusBarang = true;
            }
        }
        if (statusBarang) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan tidak dapat dibatalkan, karena sudah ada pengiriman barang");
        } else {
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Batal pemesanan " + p.getNoPemesanan() + " , anda yakin ?");
            controller.OK.setOnAction((ActionEvent evt) -> {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            return Service.batalPemesanan(con, p);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil dibatal");
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

    private void selesaiPemesanan(PemesananBarangHead p) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Pemesanan " + p.getNoPemesanan() + " telah selesai , anda yakin ?");
        controller.OK.setOnAction((ActionEvent evt) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        p.setStatus("close");
                        return Service.selesaiApprovePemesanan(con, p);
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

    private void approvePemesanan(PemesananBarangHead p) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Menyetujui pemesanan " + p.getNoPemesanan() + " , anda yakin ?");
        controller.OK.setOnAction((ActionEvent evt) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        p.setStatus("open");
                        return Service.selesaiApprovePemesanan(con, p);
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

    private void terimaPembayaranDownPayment(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setTerimaPembayaranDownPayment(p);
        controller.saveButton.setOnAction((event) -> {
            if (controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            p.setListPemesananBarangDetail(PemesananBarangDetailDAO.getAllByNoPemesanan(con, p.getNoPemesanan()));
                            double totalDikirim = 0;
                            for (PemesananBarangDetail d : p.getListPemesananBarangDetail()) {
                                totalDikirim = totalDikirim + (d.getQtyTerkirim() * d.getHargaJual());
                            }
                            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
                            double sisaPembayaran = p.getTotalPemesanan() - totalDikirim - p.getSisaDownPayment();
                            if (jumlahBayar > sisaPembayaran) {
                                return "Jumlah yang dibayar melebihi dari sisa pembayaran";
                            } else {
                                p.setDownPayment(p.getDownPayment() + jumlahBayar);
                                p.setSisaDownPayment(p.getSisaDownPayment() + jumlahBayar);
                                return Service.newTerimaDownPayment(con, p, jumlahBayar,
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
                        mainApp.showMessage(Modality.NONE, "Success", "Terima pembayaran DP berhasil disimpan");
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

    private void lihatTerimaPembayaranDownPayment(PemesananBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaPembayaranDownPayment.fxml");
        DetailTerimaPembayaranDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPemesanan(p);
        controller.hutangTable.setRowFactory((TableView<Hutang> tv) -> {
            final TableRow<Hutang> row = new TableRow<Hutang>() {
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Terima Pembayaran DP");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembayaran(p, item, stage);
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Batal Terima Pembayaran DP") && o.isStatus()
                                    && item.getStatus().equals("open") && p.getSisaDownPayment() >= item.getJumlahHutang()) {
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

    private void batalPembayaran(PemesananBarangHead p, Hutang h, Stage stage) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembayaran " + h.getNoHutang() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalTerimaDownPayment(con, h);
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

    private void printProformaInvoice(PemesananBarangHead p) {
        try {
            List<PemesananBarangDetail> listPemesanan = p.getListPemesananBarangDetail();
            for (PemesananBarangDetail d : listPemesanan) {
                d.setPemesananBarangHead(p);
            }
            Report report = new Report();
            report.printProformaInvoice(listPemesanan, p.getTotalPemesanan());
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void printProformaInvoiceSoftcopy(PemesananBarangHead p) {
        try {
            List<PemesananBarangDetail> listPemesanan = p.getListPemesananBarangDetail();
            for (PemesananBarangDetail d : listPemesanan) {
                d.setPemesananBarangHead(p);
            }
            Report report = new Report();
            report.printProformaInvoiceSoftcopy(listPemesanan, p.getTotalPemesanan());
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
                Sheet sheet = workbook.createSheet("Data Pemesanan");
                int rc = 0;
                int c = 8;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Gudang : " + groupByCombo.getSelectionModel().getSelectedItem());
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
                sheet.getRow(rc).getCell(2).setCellValue("Customer");
                sheet.getRow(rc).getCell(3).setCellValue("Sales");
                sheet.getRow(rc).getCell(4).setCellValue("Total Pemesanan");
                sheet.getRow(rc).getCell(5).setCellValue("Sisa Pemesanan");
                sheet.getRow(rc).getCell(6).setCellValue("Pembayaran Down Payment");
                sheet.getRow(rc).getCell(7).setCellValue("Sisa Pembayaran Down Payment");
                rc++;
                double pemesanan = 0;
                double sisaPemesanan = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PemesananBarangHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPemesanan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getSales().getNama());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getTotalPemesanan());
                    double x = 0;
                    for (PemesananBarangDetail d : b.getListPemesananBarangDetail()) {
                        x = x + ((d.getQty() - d.getQtyTerkirim()) * d.getHargaJual());
                    }
                    sheet.getRow(rc).getCell(5).setCellValue(x);
                    sheet.getRow(rc).getCell(6).setCellValue(b.getDownPayment());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getSisaDownPayment());
                    rc++;
                    pemesanan = pemesanan + b.getTotalPemesanan();
                    sisaPemesanan = sisaPemesanan + x;
                    pembayaran = pembayaran + b.getDownPayment();
                    sisaPembayaran = sisaPembayaran + b.getSisaDownPayment();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(5).setCellValue(pemesanan);
                sheet.getRow(rc).getCell(6).setCellValue(sisaPemesanan);
                sheet.getRow(rc).getCell(7).setCellValue(pembayaran);
                sheet.getRow(rc).getCell(8).setCellValue(sisaPembayaran);
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
