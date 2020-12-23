/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPHeadDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailRencanaAnggaranProyekController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.time.LocalDate;
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
public class RencanaAnggaranProyekController {

    @FXML
    private TableView<RAPHead> proyekTable;
    @FXML
    private TableColumn<RAPHead, String> noRAPColumn;
    @FXML
    private TableColumn<RAPHead, String> tglRAPColumn;
    @FXML
    private TableColumn<RAPHead, String> kategoriColumn;
    @FXML
    private TableColumn<RAPHead, String> keteranganColumn;
    @FXML
    private TableColumn<RAPHead, String> metodeColumn;
    @FXML
    private TableColumn<RAPHead, String> perkiraanWaktuColumn;
    @FXML
    private TableColumn<RAPHead, Number> totalPropertyColumn;
    @FXML
    private TableColumn<RAPHead, Number> totalAnggaranColumn;
    @FXML
    private TableColumn<RAPHead, String> kodeUserColumn;
    @FXML
    private TableColumn<RAPHead, String> statusApprovalColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private Label totalAnggaranLabel;
    @FXML
    private ComboBox<String> statusApprovalCombo;
    private ObservableList<RAPHead> allRAP = FXCollections.observableArrayList();
    private ObservableList<RAPHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noRAPColumn.setCellValueFactory(cellData -> cellData.getValue().noRapProperty());
        noRAPColumn.setCellFactory(col -> Function.getWrapTableCell(noRAPColumn));

        tglRAPColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRap())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglRAPColumn.setCellFactory(col -> Function.getWrapTableCell(tglRAPColumn));
        tglRAPColumn.setComparator(Function.sortDate(tglLengkap));

        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriPembangunanProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        metodeColumn.setCellValueFactory(cellData -> cellData.getValue().metodePembagianProperty());
        metodeColumn.setCellFactory(col -> Function.getWrapTableCell(metodeColumn));

        perkiraanWaktuColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getPerkiraanWaktu() + " hari");
        });
        perkiraanWaktuColumn.setCellFactory(col -> Function.getWrapTableCell(perkiraanWaktuColumn));

        totalPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().totalPropertyProperty());
        totalPropertyColumn.setCellFactory(col -> getTableCell(qty));

        totalAnggaranColumn.setCellValueFactory(cellData -> cellData.getValue().totalAnggaranProperty());
        totalAnggaranColumn.setCellFactory(col -> getTableCell(rp));

        statusApprovalColumn.setCellValueFactory(cellData -> cellData.getValue().statusApprovalProperty());
        statusApprovalColumn.setCellFactory(col -> Function.getWrapTableCell(statusApprovalColumn));

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));

        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));

        allRAP.addListener((ListChangeListener.Change<? extends RAPHead> change) -> {
            searchRAP();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchRAP();
                });
        filterData.addAll(allRAP);
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Rencana Anggaran Proyek");
        addNew.setOnAction((ActionEvent e) -> {
            addNewRAP();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getRAP();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Rencana Anggaran Proyek") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        proyekTable.setContextMenu(rowMenu);
        proyekTable.setRowFactory(tv -> {
            TableRow<RAPHead> row = new TableRow<RAPHead>() {
                @Override
                public void updateItem(RAPHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Rencana Anggaran Proyek");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewRAP();
                        });
                        MenuItem detail = new MenuItem("Detail Rencana Anggaran Proyek");
                        detail.setOnAction((ActionEvent event) -> {
                            detailRAP(item);
                        });
                        MenuItem edit = new MenuItem("Edit Rencana Anggaran Proyek");
                        edit.setOnAction((ActionEvent event) -> {
                            editRAP(item);
                        });
                        MenuItem batal = new MenuItem("Hapus Rencana Anggaran Proyek");
                        batal.setOnAction((ActionEvent event) -> {
                            hapusRAP(item);
                        });
                        MenuItem print = new MenuItem("Print Rencana Anggaran Proyek");
                        print.setOnAction((ActionEvent event) -> {
                            printRAP(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getRAP();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Rencana Anggaran Proyek") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Rencana Anggaran Proyek") && o.isStatus()) {
                                rowMenu.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Edit Rencana Anggaran Proyek") && o.isStatus() && item.getStatusApproval().equals("On Review")) {
                                rowMenu.getItems().add(edit);
                            }
                            if (o.getJenis().equals("Hapus Rencana Anggaran Proyek") && o.isStatus() && item.getStatusApproval().equals("On Review")) {
                                rowMenu.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Print Rencana Anggaran Proyek") && o.isStatus()) {
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
                        if (o.getJenis().equals("Detail Rencana Anggaran Proyek") && o.isStatus()) {
                            detailRAP(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getRAP();
        proyekTable.setItems(filterData);
        ObservableList<String> approval = FXCollections.observableArrayList();
        approval.add("Semua");
        approval.add("On Review");
        approval.add("Approved");
        approval.add("Rejected");
        statusApprovalCombo.setItems(approval);
        statusApprovalCombo.getSelectionModel().select("Semua");
    }

    @FXML
    private void getRAP() {
        Task<List<RAPHead>> task = new Task<List<RAPHead>>() {
            @Override
            public List<RAPHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String statusApproval = "%";
                    if (!statusApprovalCombo.getSelectionModel().getSelectedItem().equals("Semua")) {
                        statusApproval = statusApprovalCombo.getSelectionModel().getSelectedItem();
                    }
                    List<RAPHead> listRAP = RAPHeadDAO.getAllByDateAndStatusApprovalAndStatusSelesaiAndStatusBatal(con,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                            statusApproval, "%", "false");
                    return listRAP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allRAP.clear();
            allRAP.addAll(task.getValue());
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

    private void searchRAP() {
        try {
            filterData.clear();
            for (RAPHead temp : allRAP) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoRap())
                            || checkColumn(temp.getTglRap())
                            || checkColumn(temp.getKategoriPembangunan())
                            || checkColumn(qty.format(temp.getPerkiraanWaktu()))
                            || checkColumn(tgl.format(tglBarang.parse(temp.getTglMulai())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglRap())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglSelesai())))
                            || checkColumn(temp.getMetodePembagian())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(qty.format(temp.getTotalProperty()))
                            || checkColumn(rp.format(temp.getTotalAnggaran()))
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void hitungTotal() {
        double total = 0;
        for (RAPHead pt : filterData) {
            total = total + pt.getTotalAnggaran();
        }
        totalAnggaranLabel.setText(rp.format(total));
    }

    @FXML
    private void addNewRAP() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaAnggaranProyek.fxml");
        DetailRencanaAnggaranProyekController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewRAP();
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if ("".equals(x.totalAnggaranField.getText()) || "0".equals(x.totalAnggaranField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total anggaran masih kosong");
            } else if ("".equals(x.totalPropertyField.getText()) || "0".equals(x.totalPropertyField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total property masih kosong");
            } else if (x.kategoriCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.kategoriCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori proyek belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            x.rapHead.setNoRap(RAPHeadDAO.getId(con));
                            x.rapHead.setTglRap(tglSql.format(new Date()));
                            x.rapHead.setKategoriPembangunan(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            x.rapHead.setKeterangan(x.keteranganField.getText());
                            x.rapHead.setPerkiraanWaktu(Integer.parseInt(x.estimasiField.getText().replaceAll(",", "")));
                            x.rapHead.setTglMulai("2000-01-01");
                            x.rapHead.setTotalProperty(Double.parseDouble(x.totalPropertyField.getText().replaceAll(",", "")));
                            x.rapHead.setTotalAnggaran(Double.parseDouble(x.totalAnggaranField.getText().replaceAll(",", "")));
                            x.rapHead.setKodeUser(sistem.getUser().getUsername());
                            x.rapHead.setStatusApproval("On Review");
                            x.rapHead.setTglApproval("2000-01-01 00:00:00");
                            x.rapHead.setUserApproval("");
                            x.rapHead.setStatusSelesai("false");
                            x.rapHead.setTglSelesai("2000-01-01 00:00:00");
                            x.rapHead.setUserSelesai("");
                            x.rapHead.setStatusBatal("false");
                            x.rapHead.setTglBatal("2000-01-01 00:00:00");
                            x.rapHead.setUserBatal("");
                            return Service.saveRap(con, x.rapHead);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRAP();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Rencana anggaran proyek berhasil disimpan");
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
        x.cancelButton.setOnAction((event) -> {
            mainApp.closeDialog(mainApp.MainStage, stage);
        });
    }

    private void detailRAP(RAPHead r) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaAnggaranProyek.fxml");
        DetailRencanaAnggaranProyekController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setRAP(r);
        x.saveButton.setOnAction((event) -> {
            if (r.getStatusApproval().equals("Approved")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di setujui");
            } else if (r.getStatusApproval().equals("Rejected")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di tolak");
            } else if (r.getStatusBatal().equals("true")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah pernah dihapus");
            } else {
                MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                        "Setujui rencana anggaran proyek " + r.getNoRap() + ", anda yakin ?");
                controller.OK.setOnAction((ActionEvent ev) -> {
                    Task<String> task = new Task<String>() {
                        @Override
                        public String call() throws Exception {
                            try (Connection con = Koneksi.getConnection()) {
                                r.setStatusApproval("Approved");
                                r.setTglApproval(tglSql.format(new Date()));
                                r.setUserApproval(sistem.getUser().getUsername());
                                return Service.saveUpdateRap(con, r);
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((WorkerStateEvent e) -> {
                        mainApp.closeLoading();
                        getRAP();
                        if (task.getValue().equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Rencana anggaran proyek berhasil disetujui");
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
        });
        x.cancelButton.setOnAction((event) -> {
            if (r.getStatusApproval().equals("Approved")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di setujui");
            } else if (r.getStatusApproval().equals("Rejected")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di tolak");
            } else if (r.getStatusBatal().equals("true")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah pernah dihapus");
            } else {
                MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                        "Tolak rencana anggaran proyek " + r.getNoRap() + ", anda yakin ?");
                controller.OK.setOnAction((ActionEvent ev) -> {
                    Task<String> task = new Task<String>() {
                        @Override
                        public String call() throws Exception {
                            try (Connection con = Koneksi.getConnection()) {
                                r.setStatusApproval("Rejected");
                                return Service.saveUpdateRap(con, r);
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((WorkerStateEvent e) -> {
                        mainApp.closeLoading();
                        getRAP();
                        if (task.getValue().equals("true")) {
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Rencana anggaran proyek berhasil ditolak");
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
        });
    }

    private void editRAP(RAPHead r) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailRencanaAnggaranProyek.fxml");
        DetailRencanaAnggaranProyekController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setEditRAP(r);
        x.saveButton.setOnAction((event) -> {
            if (r.getStatusApproval().equals("Approved")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di setujui");
            } else if (r.getStatusBatal().equals("true")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah pernah dihapus");
            } else if ("".equals(x.totalAnggaranField.getText()) || "0".equals(x.totalAnggaranField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total anggaran masih kosong");
            } else if ("".equals(x.totalPropertyField.getText()) || "0".equals(x.totalPropertyField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total property masih kosong");
            } else if (x.kategoriCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.kategoriCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori proyek belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            x.rapHead.setKategoriPembangunan(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            x.rapHead.setKeterangan(x.keteranganField.getText());
                            x.rapHead.setPerkiraanWaktu(Integer.parseInt(x.estimasiField.getText().replaceAll(",", "")));
                            x.rapHead.setTglMulai("2000-01-01");
                            x.rapHead.setTotalProperty(Double.parseDouble(x.totalPropertyField.getText().replaceAll(",", "")));
                            x.rapHead.setTotalAnggaran(Double.parseDouble(x.totalAnggaranField.getText().replaceAll(",", "")));
                            x.rapHead.setKodeUser(sistem.getUser().getUsername());
                            x.rapHead.setStatusApproval("On Review");
                            x.rapHead.setTglApproval("2000-01-01 00:00:00");
                            x.rapHead.setUserApproval("");
                            x.rapHead.setStatusSelesai("false");
                            x.rapHead.setTglSelesai("2000-01-01 00:00:00");
                            x.rapHead.setUserSelesai("");
                            x.rapHead.setStatusBatal("false");
                            x.rapHead.setTglBatal("2000-01-01 00:00:00");
                            x.rapHead.setUserBatal("");
                            return Service.saveEditRap(con, x.rapHead);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRAP();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Rencana anggaran proyek berhasil disimpan");
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
        x.cancelButton.setOnAction((event) -> {
            mainApp.closeDialog(mainApp.MainStage, stage);
        });
    }

    private void hapusRAP(RAPHead r) {
        if (r.getStatusApproval().equals("Approved")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah di setujui");
        } else if (r.getStatusBatal().equals("true")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Rencana anggaran proyek sudah pernah dihapus");
        } else {
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Hapus rencana anggaran proyek " + r.getNoRap() + ", anda yakin ?");
            controller.OK.setOnAction((ActionEvent ev) -> {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            r.setStatusBatal("true");
                            r.setTglBatal(tglSql.format(new Date()));
                            r.setUserBatal(sistem.getUser().getUsername());
                            return Service.saveUpdateRap(con, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getRAP();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data rencana anggaran proyek berhasil dihapus");
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

    private void printRAP(RAPHead rap) {
        try (Connection con = Koneksi.getConnection()) {
            rap.setListRapDetail(RAPDetailDAO.getAllByNoRap(con, rap.getNoRap()));
            PrintOut print = new PrintOut();
            print.printRencanaAnggaranProyek(rap);
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
