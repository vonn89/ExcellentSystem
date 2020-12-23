/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SerahTerimaDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPelunasanDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.SKLManualController;
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
public class PelunasanDownPaymentController {

    @FXML
    private TableView<SKLHead> SKLHeadTable;
    @FXML
    private TableColumn<SKLHead, String> noSKLHeadColumn;
    @FXML
    private TableColumn<SKLHead, String> tglSKLHeadColumn;
    @FXML
    private TableColumn<SKLHead, Number> totalPembayaranColumn;
    @FXML
    private TableColumn<SKLHead, String> kodeKategoriColumn;
    @FXML
    private TableColumn<SKLHead, String> namaPropertyColumn;
    @FXML
    private TableColumn<SKLHead, Number> hargaJualColumn;
    @FXML
    private TableColumn<SKLHead, Number> diskonColumn;
    @FXML
    private TableColumn<SKLHead, Number> addendumColumn;
    @FXML
    private TableColumn<SKLHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<SKLHead, String> namaSalesColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    private Main mainApp;
    private ObservableList<SKLHead> allSKLHead = FXCollections.observableArrayList();
    private ObservableList<SKLHead> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noSKLHeadColumn.setCellValueFactory(cellData -> cellData.getValue().noSKLProperty());
        noSKLHeadColumn.setCellFactory(col -> Function.getWrapTableCell(noSKLHeadColumn));

        tglSKLHeadColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSKL())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSKLHeadColumn.setCellFactory(col -> Function.getWrapTableCell(tglSKLHeadColumn));
        tglSKLHeadColumn.setComparator(Function.sortDate(tglLengkap));

        totalPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembayaranProperty());
        totalPembayaranColumn.setCellFactory(col -> getTableCell(rp));

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
        MenuItem addNew = new MenuItem("Add New Pelunasan Down Payment");
        addNew.setOnAction((ActionEvent e) -> {
            addNewPelunasanDownPayment();
        });
        MenuItem buatSKLManual = new MenuItem("Buat Surat Keterangan Lunas Manual");
        buatSKLManual.setOnAction((ActionEvent event) -> {
            createSKLManual();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getSKL();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pelunasan Down Payment") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
            if (o.getJenis().equals("Buat Surat Keterangan Lunas Manual") && o.isStatus()) {
                rowMenu.getItems().add(buatSKLManual);
            }
        }
        rowMenu.getItems().addAll(refresh);
        SKLHeadTable.setContextMenu(rowMenu);
        SKLHeadTable.setRowFactory(tv -> {
            TableRow<SKLHead> row = new TableRow<SKLHead>() {
                @Override
                public void updateItem(SKLHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pelunasan Down Payment");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewPelunasanDownPayment();
                        });
                        MenuItem print = new MenuItem("Print Surat Keterangan Lunas");
                        print.setOnAction((ActionEvent e) -> {
                            printSKL(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pelunasan Down Payment");
                        batal.setOnAction((ActionEvent event) -> {
                            batalPelunasanDownPayment(item);
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
                        MenuItem detailPelunasanDownPayment = new MenuItem("Detail Pelunasan Down Payment");
                        detailPelunasanDownPayment.setOnAction((ActionEvent event) -> {
                            detailPelunasanDownPayment(item);
                        });
                        MenuItem buatSKLManual = new MenuItem("Buat Surat Keterangan Lunas Manual");
                        buatSKLManual.setOnAction((ActionEvent event) -> {
                            createSKLManual();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSKL();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pelunasan Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Pelunasan Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(detailPelunasanDownPayment);
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
                            if (o.getJenis().equals("Batal Pelunasan Down Payment") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Keterangan Lunas") && o.isStatus()) {
                                rowMenu.getItems().add(print);
                            }
                            if (o.getJenis().equals("Buat Surat Keterangan Lunas Manual") && o.isStatus()) {
                                rowMenu.getItems().add(buatSKLManual);
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
                        if (o.getJenis().equals("Detail Pelunasan Down Payment") && o.isStatus()) {
                            detailPelunasanDownPayment(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allSKLHead.addListener((ListChangeListener.Change<? extends SKLHead> change) -> {
            search();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    search();
                });
        filterData.addAll(allSKLHead);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        SKLHeadTable.setItems(filterData);
        getSKL();
    }

    @FXML
    private void getSKL() {
        Task<List<SKLHead>> task = new Task<List<SKLHead>>() {
            @Override
            public List<SKLHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<Karyawan> listKaryawan = KaryawanDAO.getAllByStatus(con, "true");
                    List<SKLDetail> listSKLDetail = SKLDetailDAO.getAllByDateAndStatus(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<SKLHead> listSKL = SKLHeadDAO.getAllByDateAndStatus(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for (SKLHead skl : listSKL) {
                        List<SKLDetail> sklDetail = new ArrayList<>();
                        for (SKLDetail d : listSKLDetail) {
                            if (skl.getNoSKL().equals(d.getNoSKL())) {
                                sklDetail.add(d);
                            }
                        }
                        skl.setAllDetail(sklDetail);
                        for (Customer c : listCustomer) {
                            if (skl.getKodeCustomer().equals(c.getKodeCustomer())) {
                                skl.setCustomer(c);
                            }
                        }
                        for (Property p : listProperty) {
                            if (skl.getKodeProperty().equals(p.getKodeProperty())) {
                                skl.setProperty(p);
                            }
                        }
                        for (Karyawan k : listKaryawan) {
                            if (skl.getKodeSales().equals(k.getKodeKaryawan())) {
                                skl.setSales(k);
                            }
                        }
                        if(skl.getSales()==null)
                            System.out.println(skl.getNoSKL()+skl.getKodeSales());
                    }
                    return listSKL;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allSKLHead.clear();
                allSKLHead.addAll(task.getValue());
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

    private void search() {
        filterData.clear();
        for (SKLHead temp : allSKLHead) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoSKL())
                        || checkColumn(temp.getTglSKL())
                        || checkColumn(rp.format(temp.getTotalPembayaran()))
                        || checkColumn(temp.getCustomer().getNama())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(rp.format(temp.getProperty().getHargaJual()))
                        || checkColumn(rp.format(temp.getProperty().getDiskon()))
                        || checkColumn(rp.format(temp.getProperty().getAddendum()))
                        || checkColumn(temp.getSales().getNama())) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void addNewPelunasanDownPayment() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanDownPayment.fxml");
        DetailPelunasanDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewSKL();
        controller.saveButton.setOnAction((event) -> {
            if (controller.skl.getProperty() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            controller.skl.setKodeUser(sistem.getUser().getUsername());
                            controller.skl.setStatus("true");
                            controller.skl.setTglBatal("2000-01-01 00:00:00");
                            controller.skl.setUserBatal("");
                            controller.skl.setAllDetail(controller.allDetail);
                            return Service.savePelunasanDownPayment(con, controller.skl);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        getSKL();
                        mainApp.closeLoading();
                        if (task.getValue().equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Pelunasan down payment berhasil disimpan");
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

    private void detailPelunasanDownPayment(SKLHead skl) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanDownPayment.fxml");
        DetailPelunasanDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setSKL(skl);
    }

    private void batalPelunasanDownPayment(SKLHead skl) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pelunasan down payment " + skl.getNoSKL() + ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        if (SerahTerimaDAO.getByKodeProperty(con, skl.getKodeProperty(), "true") != null) {
                            return "Pelunasan down payment tidak dapat dibatalkan, karena sudah ada serah terima";
                        } else if (KPRDAO.getByKodeProperty(con, skl.getKodeProperty()) != null) {
                            return "Pelunasan down payment tidak dapat dibatalkan, karena sudah ada pencairan KPR";
                        } else {
                            skl.setTglBatal(tglSql.format(new Date()));
                            skl.setUserBatal(sistem.getUser().getUsername());
                            skl.setStatus("false");
                            return Service.batalPelunasanDownPayment(con, skl);
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
                    getSKL();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data pelunasan down payment berhasil dibatal");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
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

    private void printSKL(SKLHead skl) {
        try {
            PrintOut print = new PrintOut();
            print.printSuratKeteranganLunas(skl);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void createSKLManual() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/SKLManual.fxml");
        SKLManualController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
    }

    private void detailCustomer(Customer c) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomer(c);
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

    private void detailSales(Karyawan k) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKaryawan.fxml");
        DetailKaryawanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKaryawan(k);
        x.setViewOnly();
    }
}
