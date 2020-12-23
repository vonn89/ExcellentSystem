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
import com.excellentsystem.jagobangunpersadafx.DAO.STJDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.STJDetail;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaTandaJadiController;
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
public class TerimaTandaJadiController {

    @FXML
    private TableView<STJHead> STJTable;
    @FXML
    private TableColumn<STJHead, String> noSTJColumn;
    @FXML
    private TableColumn<STJHead, String> tglSTJColumn;
    @FXML
    private TableColumn<STJHead, String> kodeKategoriColumn;
    @FXML
    private TableColumn<STJHead, String> namaPropertyColumn;
    @FXML
    private TableColumn<STJHead, Number> hargaJualColumn;
    @FXML
    private TableColumn<STJHead, Number> diskonColumn;
    @FXML
    private TableColumn<STJHead, String> namaCustomerColumn;
    @FXML
    private TableColumn<STJHead, Number> jumlahRpColumn;
    @FXML
    private TableColumn<STJHead, String> tipeKeuanganColumn;
    @FXML
    private TableColumn<STJHead, String> namaSalesColumn;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<STJHead> allSTJ = FXCollections.observableArrayList();
    private ObservableList<STJHead> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noSTJColumn.setCellValueFactory(cellData -> cellData.getValue().noSTJProperty());
        noSTJColumn.setCellFactory(col -> Function.getWrapTableCell(noSTJColumn));

        tglSTJColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSTJ())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSTJColumn.setCellFactory(col -> Function.getWrapTableCell(tglSTJColumn));
        tglSTJColumn.setComparator(Function.sortDate(tglLengkap));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        diskonColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));

        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganColumn));

        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));

        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));

        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Terima Tanda Jadi");
        addNew.setOnAction((ActionEvent e) -> {
            addNewTerimaTandaJadi();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getSTJ();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Terima Tanda Jadi") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        STJTable.setContextMenu(rowMenu);
        STJTable.setRowFactory(tv -> {
            TableRow<STJHead> row = new TableRow<STJHead>() {
                @Override
                public void updateItem(STJHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Terima Tanda Jadi");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewTerimaTandaJadi();
                        });
                        MenuItem print = new MenuItem("Print Surat Tanda Jadi");
                        print.setOnAction((ActionEvent e) -> {
                            printSTJ(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Tanda Jadi");
                        batal.setOnAction((ActionEvent event) -> {
                            batalTerimaTandaJadi(item);
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
                        MenuItem detailTerimaTandaJadi = new MenuItem("Detail Terima Tanda Jadi");
                        detailTerimaTandaJadi.setOnAction((ActionEvent event) -> {
                            detailTerimaTandaJadi(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSTJ();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Terima Tanda Jadi") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Terima Tanda Jadi") && o.isStatus()) {
                                rowMenu.getItems().add(detailTerimaTandaJadi);
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
                            if (o.getJenis().equals("Batal Terima Tanda Jadi") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Tanda Jadi") && o.isStatus()) {
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
                        if (o.getJenis().equals("Detail Terima Tanda Jadi") && o.isStatus()) {
                            detailTerimaTandaJadi(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allSTJ.addListener((ListChangeListener.Change<? extends STJHead> change) -> {
            searchSTJ();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchSTJ();
                });
        filterData.addAll(allSTJ);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getSTJ();
        STJTable.setItems(filterData);
    }

    @FXML
    private void getSTJ() {
        Task<List<STJHead>> task = new Task<List<STJHead>>() {
            @Override
            public List<STJHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<Karyawan> listKaryawan = KaryawanDAO.getAllByStatus(con, "true");
                    List<STJDetail> listSTJDetail = STJDetailDAO.getAllByDateAndStatus(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<STJHead> listSTJ = STJHeadDAO.getAllByDateAndStatus(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for (STJHead stj : listSTJ) {
                        List<STJDetail> stjDetail = new ArrayList<>();
                        for (STJDetail d : listSTJDetail) {
                            if (stj.getNoSTJ().equals(d.getNoSTJ())) {
                                stjDetail.add(d);
                            }
                        }
                        stj.setAllDetail(stjDetail);
                        for (Customer c : listCustomer) {
                            if (stj.getKodeCustomer().equals(c.getKodeCustomer())) {
                                stj.setCustomer(c);
                            }
                        }
                        for (Property p : listProperty) {
                            if (stj.getKodeProperty().equals(p.getKodeProperty())) {
                                stj.setProperty(p);
                            }
                        }
                        for (Karyawan k : listKaryawan) {
                            if (stj.getKodeSales().equals(k.getKodeKaryawan())) {
                                stj.setSales(k);
                            }
                        }
                    }
                    return listSTJ;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allSTJ.clear();
                allSTJ.addAll(task.getValue());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
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

    private void searchSTJ() {
        filterData.clear();
        for (STJHead temp : allSTJ) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoSTJ())
                        || checkColumn(temp.getTglSTJ())
                        || checkColumn(temp.getCustomer().getNama())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(rp.format(temp.getProperty().getHargaJual()))
                        || checkColumn(rp.format(temp.getProperty().getDiskon()))
                        || checkColumn(temp.getTipeKeuangan())
                        || checkColumn(rp.format(temp.getJumlahRp()))
                        || checkColumn(temp.getSales().getNama())) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void addNewTerimaTandaJadi() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaTandaJadi.fxml");
        DetailTerimaTandaJadiController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewSTJ();
        x.saveButton.setOnAction((event) -> {
            if ("".equals(x.namaCustomerField.getText()) || x.stj.getCustomer() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
            } else if ("".equals(x.kodePropertyField.getText()) || x.stj.getProperty() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            } else if ("".equals(x.jumlahPembayaranField.getText()) || "0".equals(x.jumlahPembayaranField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran masih kosong");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else if (x.allDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana pembayaran dp masih kosong");
            } else if ("".equals(x.salesField.getText()) || x.stj.getSales() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "sales belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            if (x.stj.getProperty() != null) {
                                x.stj.getProperty().setDiskon(Double.parseDouble(x.diskonField.getText().replaceAll(",", "")));
                            }
                            x.stj.setKeterangan(x.catatanField.getText());
                            x.stj.setJumlahRp(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", "")));
                            x.stj.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            x.stj.setKodeUser(sistem.getUser().getUsername());
                            x.stj.setStatus("true");
                            x.stj.setTglBatal("2000-01-01 00:00:00");
                            x.stj.setUserBatal("");
                            return Service.saveTerimaTandaJadi(con, x.stj);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        getSTJ();
                        mainApp.closeLoading();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Terima tanda jadi berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
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

    private void detailTerimaTandaJadi(STJHead stj) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaTandaJadi.fxml");
        DetailTerimaTandaJadiController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSTJ(stj);
    }

    private void printSTJ(STJHead stj) {
        try {
            PrintOut print = new PrintOut();
            print.printSuratTandaJadi(stj);
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void batalTerimaTandaJadi(STJHead stj) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal terima tanda jadi " + stj.getNoSTJ() + ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        if (!SDPDAO.getAllByKodeProperty(con, stj.getKodeProperty(), "true").isEmpty()) {
                            return "Terima tanda jadi tidak dapat dibatalkan, karena sudah ada pembayaran DP";
                        } else {
                            stj.setTglBatal(tglSql.format(new Date()));
                            stj.setUserBatal(sistem.getUser().getUsername());
                            stj.setStatus("false");
                            return Service.batalTerimaTandaJadi(con, stj);
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
                    getSTJ();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data terima tanda jadi berhasil dibatal");
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
