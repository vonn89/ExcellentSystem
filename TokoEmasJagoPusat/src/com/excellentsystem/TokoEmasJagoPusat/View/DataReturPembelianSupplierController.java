/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.ReturPembelianHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailReturPembelianSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.ReturPembelianSupplierController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
 * @author Excellent
 */
public class DataReturPembelianSupplierController {

    @FXML
    private TableView<ReturPembelianHead> returPembelianHeadTable;
    @FXML
    private TableColumn<ReturPembelianHead, String> noReturColumn;
    @FXML
    private TableColumn<ReturPembelianHead, String> tglReturColumn;
    @FXML
    private TableColumn<ReturPembelianHead, String> supplierColumn;
    @FXML
    private TableColumn<ReturPembelianHead, Number> totalBeratColumn;
    @FXML
    private TableColumn<ReturPembelianHead, Number> totalHargaPersenColumn;
    @FXML
    private TableColumn<ReturPembelianHead, Number> hargaEmasColumn;
    @FXML
    private TableColumn<ReturPembelianHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;

    @FXML
    private Label totalBeratLabel;
    @FXML
    private Label totalHargaPersenLabel;
    private Main mainApp;
    private ObservableList<ReturPembelianHead> allRetur = FXCollections.observableArrayList();
    private ObservableList<ReturPembelianHead> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noReturColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        tglReturColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRetur())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglReturColumn.setComparator(Function.sortDate(tglLengkap));
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalHargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaEmasColumn.setCellValueFactory(cellData -> cellData.getValue().hargaEmasProperty());
        hargaEmasColumn.setCellFactory(col -> getTableCell(rp));
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker)
                -> Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker)
                -> Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));

        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Retur Pembelian Supplier");
        addNew.setOnAction((ActionEvent e) -> {
            newReturPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getRetur();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        returPembelianHeadTable.setContextMenu(rowMenu);
        returPembelianHeadTable.setRowFactory(table -> {
            TableRow<ReturPembelianHead> row = new TableRow<ReturPembelianHead>() {
                @Override
                public void updateItem(ReturPembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Retur Pembelian Supplier");
                        addNew.setOnAction((ActionEvent e) -> {
                            newReturPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Retur Pembelian Supplier");
                        detail.setOnAction((ActionEvent e) -> {
                            detailReturPembelianSupplier(item);
                        });
                        MenuItem batal = new MenuItem("Batal Retur Pembelian Supplier");
                        batal.setOnAction((ActionEvent e) -> {
                            batalRetur(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getRetur();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        detailReturPembelianSupplier(row.getItem());
                    }
                }
            });
            return row;
        });
        allRetur.addListener((ListChangeListener.Change<? extends ReturPembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelian();
                });
        filterData.addAll(allRetur);
        returPembelianHeadTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getRetur();
    }

    @FXML
    private void getRetur() {
        Task<List<ReturPembelianHead>> task = new Task<List<ReturPembelianHead>>() {
            @Override
            public List<ReturPembelianHead> call() throws Exception {
                try (Connection conPusat = KoneksiPusat.getConnection()) {
                    List<ReturPembelianHead> listRetur = ReturPembelianHeadDAO.getAllByDateAndSupplierAndStatus(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "true");
                    return listRetur;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allRetur.clear();
            allRetur.addAll(task.getValue());
            returPembelianHeadTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
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
        try {
            filterData.clear();
            for (ReturPembelianHead a : allRetur) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(a);
                } else {

                    if (checkColumn(a.getNoRetur())
                            || checkColumn(tglLengkap.format(tglSql.parse(a.getTglRetur())))
                            || checkColumn(a.getSupplier())
                            || checkColumn(a.getKodeUser())
                            || checkColumn(rp.format(a.getTotalBerat()))
                            || checkColumn(rp.format(a.getTotalHargaPersen()))
                            || checkColumn(rp.format(a.getHargaEmas()))
                            || checkColumn(rp.format(a.getTotalRetur()))) {
                        filterData.add(a);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double totalBerat = 0;
        double totalHargaPersen = 0;
        for (ReturPembelianHead p : filterData) {
            totalBerat = totalBerat + p.getTotalBerat();
            totalHargaPersen = totalHargaPersen + p.getTotalHargaPersen();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
    }

    private void newReturPembelian() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/ReturPembelianSupplier.fxml");
        ReturPembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if (controller.supplierCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
            } else if (controller.listReturPembelianDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection conPusat = KoneksiPusat.getConnection()) {
                            ReturPembelianHead p = new ReturPembelianHead();
                            p.setNoRetur(ReturPembelianHeadDAO.getId(conPusat));
                            p.setTglRetur(Function.getSystemDate());
                            p.setSupplier(controller.supplierCombo.getSelectionModel().getSelectedItem());
                            int i = 1;
                            double totalBerat = 0;
                            double totalHargaPersen = 0;
                            for (ReturPembelianDetail d : controller.listReturPembelianDetail) {
                                d.setNoRetur(p.getNoRetur());
                                d.setNoUrut(i);

                                totalBerat = totalBerat + d.getBerat();
                                totalHargaPersen = totalHargaPersen + d.getTotalHarga();
                                i++;
                            }
                            p.setListReturPembelianDetail(controller.listReturPembelianDetail);
                            p.setTotalBerat(totalBerat);
                            p.setTotalHargaPersen(totalHargaPersen);
                            p.setHargaEmas(sistem.getHargaEmas());
                            p.setTotalRetur(Function.pembulatan(totalHargaPersen * sistem.getHargaEmas()));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.saveReturPembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRetur();
                    String status = task.getValue();
                    if (status.equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Retur pembelian supplier berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", status);
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

    private void detailReturPembelianSupplier(ReturPembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailReturPembelianSupplier.fxml");
        DetailReturPembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembelian(p);
    }

    private void batalRetur(ReturPembelianHead p) {
        if (p.getStatus().equals("false")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Retur pembelian supplier tidak dapat dibatal, karena sudah pernah dibatal");
        } else {
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal Retur Pembelian supplier " + p.getNoRetur()+ " ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();

                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection conPusat = KoneksiPusat.getConnection()) {
                            p.setStatus("false");
                            p.setTglBatal(Function.getSystemDate());
                            p.setUserBatal(user.getKodeUser());
                            return Service.saveBatalReturPembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRetur();
                    String status = task.getValue();
                    if (status.equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Retur pembelian supplier berhasil dibatal");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            });
        }
    }

}
