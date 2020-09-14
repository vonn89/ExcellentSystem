/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.HutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembayaranHutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembelianHeadDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.Hutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranHutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailHutangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPembelianSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PembayaranPembelianController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PembelianSupplierController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
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
 * @author Excellent
 */
public class DataPembelianSupplierController {

    @FXML
    private TableView<PembelianHead> pembelianHeadTable;
    @FXML
    private TableColumn<PembelianHead, String> noPembelianColumn;
    @FXML
    private TableColumn<PembelianHead, String> tglPembelianColumn;
    @FXML
    private TableColumn<PembelianHead, String> supplierColumn;
    @FXML
    private TableColumn<PembelianHead, Number> totalBeratColumn;
    @FXML
    private TableColumn<PembelianHead, Number> totalHargaPersenColumn;
    @FXML
    private TableColumn<PembelianHead, Number> hargaEmasColumn;
    @FXML
    private TableColumn<PembelianHead, Number> pembayaranCiokColumn;
    @FXML
    private TableColumn<PembelianHead, Number> sisaPembayaranCiokColumn;
    @FXML
    private TableColumn<PembelianHead, String> kodeUserColumn;

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
    @FXML
    private Label pembayaranGrLabel;
    @FXML
    private Label sisaPembayaranGrLabel;
    private Main mainApp;
    private ObservableList<PembelianHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianHead> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalHargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaEmasColumn.setCellValueFactory(cellData -> cellData.getValue().hargaEmasProperty());
        hargaEmasColumn.setCellFactory(col -> getTableCell(rp));
        pembayaranCiokColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHutang().getTerbayar() / cellData.getValue().getHutang().getKurs());
        });
        pembayaranCiokColumn.setCellFactory(col -> getTableCell(gr));
        sisaPembayaranCiokColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHutang().getSisaHutang()/ cellData.getValue().getHutang().getKurs());
        });
        sisaPembayaranCiokColumn.setCellFactory(col -> getTableCell(gr));
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
        MenuItem addNew = new MenuItem("New Pembelian Supplier");
        addNew.setOnAction((ActionEvent e) -> {
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pembelianHeadTable.setContextMenu(rowMenu);
        pembelianHeadTable.setRowFactory(table -> {
            TableRow<PembelianHead> row = new TableRow<PembelianHead>() {
                @Override
                public void updateItem(PembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pembelian Supplier");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Pembelian Supplier");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPembelianSupplier(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Pembelian Supplier");
                        bayar.setOnAction((ActionEvent e) -> {
                            pembayaranPembelianSupplier(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran Pembelian Supplier");
                        detailBayar.setOnAction((ActionEvent e) -> {
                            detailpembayaranPembelianSupplier(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian Supplier");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembelian(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        if(item.getHutang().getSisaHutang()>0)
                            rowMenu.getItems().add(bayar);
                        if(item.getHutang().getTerbayar()>0)
                            rowMenu.getItems().add(detailBayar);
                        if(item.getHutang().getSisaHutang()>0)
                            rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        detailPembelianSupplier(row.getItem());
                    }
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembelian();
                });
        filterData.addAll(allPembelian);
        pembelianHeadTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembelian();
    }

    @FXML
    private void getPembelian() {
        Task<List<PembelianHead>> task = new Task<List<PembelianHead>>() {
            @Override
            public List<PembelianHead> call() throws Exception {
                try (Connection conPusat = KoneksiPusat.getConnection()) {
                    List<PembelianHead> listPembelian = PembelianHeadDAO.getAllByDateAndSupplierAndStatus(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "true");
                    List<Hutang> listHutang = HutangDAO.getAllByDateAndSupplierAndStatus(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "%");
                    for (PembelianHead p : listPembelian) {
                        for (Hutang h : listHutang) {
                            if (p.getNoPembelian().equals(h.getNoPembelian())) {
                                p.setHutang(h);
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
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
            pembelianHeadTable.refresh();
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
            for (PembelianHead a : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(a);
                } else {

                    if (checkColumn(a.getNoPembelian())
                            || checkColumn(tglLengkap.format(tglSql.parse(a.getTglPembelian())))
                            || checkColumn(a.getSupplier())
                            || checkColumn(a.getKodeUser())
                            || checkColumn(rp.format(a.getTotalBerat()))
                            || checkColumn(rp.format(a.getTotalHargaPersen()))
                            || checkColumn(rp.format(a.getHargaEmas()))
                            || checkColumn(rp.format(a.getTotalPembelian()))) {
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
        double totalPembayaranGr = 0;
        double sisaPembayaranGr = 0;
        for (PembelianHead p : filterData) {
            totalBerat = totalBerat + p.getTotalBerat();
            totalHargaPersen = totalHargaPersen + p.getTotalHargaPersen();
            totalPembayaranGr = totalPembayaranGr + p.getHutang().getTerbayar()/p.getHutang().getKurs();
            sisaPembayaranGr = sisaPembayaranGr + p.getHutang().getSisaHutang()/p.getHutang().getKurs();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
        pembayaranGrLabel.setText(gr.format(totalPembayaranGr));
        sisaPembayaranGrLabel.setText(gr.format(sisaPembayaranGr));
    }

    private void newPembelian() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PembelianSupplier.fxml");
        PembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if (controller.supplierCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
            } else if (controller.listPembelianDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection conPusat = KoneksiPusat.getConnection()) {
                            PembelianHead p = new PembelianHead();
                            p.setNoPembelian(PembelianHeadDAO.getId(conPusat));
                            p.setTglPembelian(Function.getSystemDate());
                            p.setSupplier(controller.supplierCombo.getSelectionModel().getSelectedItem());
                            int i = 1;
                            double totalBerat = 0;
                            double totalHargaPersen = 0;
                            for (PembelianDetail d : controller.listPembelianDetail) {
                                d.setNoPembelian(p.getNoPembelian());
                                d.setNoUrut(i);

                                totalBerat = totalBerat + d.getBerat();
                                totalHargaPersen = totalHargaPersen + d.getTotalHarga();
                                i++;
                            }
                            p.setListPembelianDetail(controller.listPembelianDetail);
                            p.setTotalBerat(totalBerat);
                            p.setTotalHargaPersen(totalHargaPersen);
                            p.setHargaEmas(sistem.getHargaEmas());
                            p.setTotalPembelian(Function.pembulatan(totalHargaPersen * sistem.getHargaEmas()));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    String status = task.getValue();
                    if (status.equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian supplier berhasil disimpan");
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

    private void detailPembelianSupplier(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembelianSupplier.fxml");
        DetailPembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembelian(p);
    }

    private void pembayaranPembelianSupplier(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PembayaranPembelian.fxml");
        PembayaranPembelianController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPembelian(p);
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranRpField.getText().replaceAll(",", ""));
            if(jumlahBayar>p.getHutang().getSisaHutang())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa pembayaran");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = KoneksiPusat.getConnection()) {
                            PembayaranHutang pembayaran = new PembayaranHutang();
                            pembayaran.setNoPembayaran(PembayaranHutangDAO.getId(con));
                            pembayaran.setTglPembayaran(Function.getSystemDate());
                            pembayaran.setNoHutang(p.getHutang().getNoHutang());
                            pembayaran.setJumlahBayar(Double.parseDouble(controller.jumlahPembayaranRpField.getText().replaceAll(",", "")));
                            pembayaran.setKurs(Double.parseDouble(controller.hargaEmasField.getText().replaceAll(",", "")));
                            pembayaran.setTerbayar(Double.parseDouble(controller.jumlahPembayaranGrField.getText().replaceAll(",", ""))*p.getHutang().getKurs());
                            pembayaran.setKodeUser(user.getKodeUser());
                            pembayaran.setStatus("true");
                            pembayaran.setTglBatal("2000-01-01 00:00:00");
                            pembayaran.setUserBatal("");
                            return Service.savePembayaranPembelian(con, pembayaran);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran pembelian berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }

    private void detailpembayaranPembelianSupplier(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailPembelian(p);
        controller.pembayaranHutangTable.setRowFactory((TableView<PembayaranHutang> tableView) -> {
            final TableRow<PembayaranHutang> row = new TableRow<PembayaranHutang>(){
                @Override
                public void updateItem(PembayaranHutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(item, stage);
                        });
                        rm.getItems().add(batal);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    private void batalPembayaran(PembayaranHutang pembayaran, Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = KoneksiPusat.getConnection()) {
                        pembayaran.setStatus("false");
                        pembayaran.setTglBatal(Function.getSystemDate());
                        pembayaran.setUserBatal(user.getKodeUser());
                        return Service.saveBatalPembayaranPembelian(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPembelian();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                }else{
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

    private void batalPembelian(PembelianHead p) {
        if (p.getStatus().equals("false")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pembelian supplier tidak dapat dibatal, karena sudah pernah dibatal");
        } else {
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal pembelian supplier " + p.getNoPembelian() + " ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();

                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection conPusat = KoneksiPusat.getConnection()) {
                            p.setStatus("false");
                            p.setTglBatal(Function.getSystemDate());
                            p.setUserBatal(user.getKodeUser());
                            return Service.saveBatalPembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    String status = task.getValue();
                    if (status.equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian supplier berhasil dibatal");
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
