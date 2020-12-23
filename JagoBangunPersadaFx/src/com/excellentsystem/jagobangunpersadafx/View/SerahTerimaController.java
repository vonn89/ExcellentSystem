/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SerahTerima;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailSerahTerimaController;
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
public class SerahTerimaController {

    @FXML
    private TableView<SerahTerima> SerahTerimaTable;
    @FXML
    private TableColumn<SerahTerima, String> noSerahTerimaColumn;
    @FXML
    private TableColumn<SerahTerima, String> tglSerahTerimaColumn;
    @FXML
    private TableColumn<SerahTerima, Number> totalDPColumn;
    @FXML
    private TableColumn<SerahTerima, Number> totalKPRColumn;
    @FXML
    private TableColumn<SerahTerima, String> kodeKategoriColumn;
    @FXML
    private TableColumn<SerahTerima, String> namaPropertyColumn;
    @FXML
    private TableColumn<SerahTerima, Number> hargaJualColumn;
    @FXML
    private TableColumn<SerahTerima, Number> diskonColumn;
    @FXML
    private TableColumn<SerahTerima, Number> addendumColumn;
    @FXML
    private TableColumn<SerahTerima, String> namaCustomerColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;

    private Main mainApp;
    private ObservableList<SerahTerima> allSerahTerima = FXCollections.observableArrayList();
    private ObservableList<SerahTerima> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noSerahTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().noSerahTerimaProperty());
        noSerahTerimaColumn.setCellFactory(col -> Function.getWrapTableCell(noSerahTerimaColumn));

        tglSerahTerimaColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSerahTerima())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSerahTerimaColumn.setCellFactory(col -> Function.getWrapTableCell(tglSerahTerimaColumn));
        tglSerahTerimaColumn.setComparator(Function.sortDate(tglLengkap));

        totalDPColumn.setCellValueFactory(cellData -> cellData.getValue().totalDPProperty());
        totalDPColumn.setCellFactory(col -> getTableCell(rp));

        totalKPRColumn.setCellValueFactory(cellData -> cellData.getValue().totalKPRProperty());
        totalKPRColumn.setCellFactory(col -> getTableCell(rp));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        diskonColumn.setCellValueFactory(cellData -> cellData.getValue().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));

        addendumColumn.setCellValueFactory(cellData -> cellData.getValue().addendumProperty());
        addendumColumn.setCellFactory(col -> getTableCell(rp));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));

        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));

        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Serah Terima");
        addNew.setOnAction((ActionEvent e) -> {
            addNewPelunasan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getSerahTerima();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Serah Terima") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        SerahTerimaTable.setContextMenu(rowMenu);
        SerahTerimaTable.setRowFactory(tv -> {
            TableRow<SerahTerima> row = new TableRow<SerahTerima>() {
                @Override
                public void updateItem(SerahTerima item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Serah Terima");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewPelunasan();
                        });
                        MenuItem print = new MenuItem("Print Surat Serah Terima");
                        print.setOnAction((ActionEvent e) -> {
                            printSPP(item);
                        });
                        MenuItem batal = new MenuItem("Batal Serah Terima");
                        batal.setOnAction((ActionEvent event) -> {
                            batalPelunasan(item);
                        });
                        MenuItem detailPelunasan = new MenuItem("Detail Serah Terima");
                        detailPelunasan.setOnAction((ActionEvent event) -> {
                            detailSerahTerima(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSerahTerima();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Serah Terima") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Serah Terima") && o.isStatus()) {
                                rowMenu.getItems().add(detailPelunasan);
                            }
                            if (o.getJenis().equals("Detail Property") && o.isStatus()) {
                                rowMenu.getItems().add(detailProperty);
                            }
                            if (o.getJenis().equals("Detail Customer") && o.isStatus()) {
                                rowMenu.getItems().add(detailCustomer);
                            }
                            if (o.getJenis().equals("Batal Serah Terima") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Surat Serah Terima") && o.isStatus()) {
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
                        if (o.getJenis().equals("Detail Serah Terima") && o.isStatus()) {
                            detailSerahTerima(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allSerahTerima.addListener((ListChangeListener.Change<? extends SerahTerima> change) -> {
            search();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    search();
                });
        filterData.addAll(allSerahTerima);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        SerahTerimaTable.setItems(filterData);
        getSerahTerima();
    }

    @FXML
    private void getSerahTerima() {
        Task<List<SerahTerima>> task = new Task<List<SerahTerima>>() {
            @Override
            public List<SerahTerima> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<SerahTerima> listSPP = SerahTerimaDAO.getAllByDateAndStatus(con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for (SerahTerima spp : listSPP) {
                        for (Customer c : listCustomer) {
                            if (spp.getKodeCustomer().equals(c.getKodeCustomer())) {
                                spp.setCustomer(c);
                            }
                        }
                        for (Property p : listProperty) {
                            if (spp.getKodeProperty().equals(p.getKodeProperty())) {
                                spp.setProperty(p);
                            }
                        }
                    }
                    return listSPP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allSerahTerima.clear();
                allSerahTerima.addAll(task.getValue());
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

    private void search() {
        filterData.clear();
        for (SerahTerima temp : allSerahTerima) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoSerahTerima())
                        || checkColumn(temp.getTglSerahTerima())
                        || checkColumn(rp.format(temp.getTotalDP()))
                        || checkColumn(rp.format(temp.getTotalKPR()))
                        || checkColumn(temp.getCustomer().getNama())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(rp.format(temp.getHarga()))
                        || checkColumn(rp.format(temp.getAddendum()))
                        || checkColumn(rp.format(temp.getDiskon()))) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void addNewPelunasan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSerahTerima.fxml");
        DetailSerahTerimaController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewSerahTerima();
        controller.saveButton.setOnAction((event) -> {
            if (controller.serahTerima.getProperty() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            } else if (controller.serahTerima.getTotalDP() + controller.serahTerima.getTotalKPR()
                    < controller.serahTerima.getProperty().getHargaJual() - controller.serahTerima.getProperty().getDiskon()
                    + controller.serahTerima.getProperty().getAddendum()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total yang dibayar masih kurang");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            controller.serahTerima.setKodeProperty(controller.serahTerima.getProperty().getKodeProperty());
                            controller.serahTerima.setKodeCustomer(controller.serahTerima.getCustomer().getKodeCustomer());
                            controller.serahTerima.setHarga(controller.serahTerima.getProperty().getHargaJual());
                            controller.serahTerima.setDiskon(controller.serahTerima.getProperty().getDiskon());
                            controller.serahTerima.setAddendum(controller.serahTerima.getProperty().getAddendum());
                            controller.serahTerima.setNoHGB(controller.noHGBField.getText());
                            controller.serahTerima.setNoIMB(controller.noIMBField.getText());
                            controller.serahTerima.setKelengkapan(controller.kelengkapanField.getText());
                            controller.serahTerima.setBonus(controller.bonusField.getText());
                            controller.serahTerima.setKodeUser(sistem.getUser().getUsername());
                            controller.serahTerima.setStatus("true");
                            controller.serahTerima.setTglBatal("2000-01-01 00:00:00");
                            controller.serahTerima.setUserBatal("");
                            return Service.saveSerahTerima(con, controller.serahTerima);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        getSerahTerima();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Serah Terima berhasil disimpan");
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

    private void detailSerahTerima(SerahTerima s) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailSerahTerima.fxml");
        DetailSerahTerimaController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setSerahTerima(s);
    }

    private void batalPelunasan(SerahTerima st) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal serah terima " + st.getNoSerahTerima() + ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        st.setTglBatal(tglSql.format(new Date()));
                        st.setUserBatal(sistem.getUser().getUsername());
                        st.setStatus("false");
                        return Service.batalSerahTerima(con, st);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try {
                    mainApp.closeLoading();
                    getSerahTerima();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Serah terima berhasil dibatal");
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

    private void printSPP(SerahTerima spp) {
        try {
            PrintOut print = new PrintOut();
            print.printSuratSerahTerima(spp);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
}
