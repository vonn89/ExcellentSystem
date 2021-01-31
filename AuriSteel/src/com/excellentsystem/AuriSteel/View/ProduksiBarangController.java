/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.ImageDatabaseDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBahanDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiOperatorDAO;
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
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.ImageDatabase;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.Model.ProduksiOperator;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailImageController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewProduksiBarangController;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class ProduksiBarangController {

    @FXML
    private TableView<ProduksiHead> produksiHeadTable;
    @FXML
    private TableColumn<ProduksiHead, String> kodeProduksiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> tglMulaiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> tglSelesaiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> tglVerifikasiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> gudangColumn;
    @FXML
    private TableColumn<ProduksiHead, String> mesinColumn;
    @FXML
    private TableColumn<ProduksiHead, String> listOperatorColumn;
    @FXML
    private TableColumn<ProduksiHead, String> listBahanColumn;
    @FXML
    private TableColumn<ProduksiHead, Number> totalBeratBahanColumn;
    @FXML
    private TableColumn<ProduksiHead, String> listBarangColumn;
    @FXML
    private TableColumn<ProduksiHead, Number> totalBeratBarangColumn;
    @FXML
    private TableColumn<ProduksiHead, Number> beratColumn;
    @FXML
    private TableColumn<ProduksiHead, Number> bebanMaterialColumn;
    @FXML
    private TableColumn<ProduksiHead, Number> bebanProduksiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> catatanColumn;
    @FXML
    private TableColumn<ProduksiHead, String> kodeUserColumn;
    @FXML
    private TableColumn<ProduksiHead, String> userMulaiColumn;
    @FXML
    private TableColumn<ProduksiHead, String> userSelesaiColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> groupByCombo;
    @FXML
    private ComboBox<String> statusCombo;
    private ObservableList<ProduksiHead> allProduksi = FXCollections.observableArrayList();
    private ObservableList<ProduksiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        kodeProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeProduksiProperty());
        kodeProduksiColumn.setCellFactory(col -> Function.getWrapTableCell(kodeProduksiColumn));

        tglMulaiColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglMulai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglMulaiColumn.setCellFactory(col -> Function.getWrapTableCell(tglMulaiColumn));
        tglMulaiColumn.setComparator(Function.sortDate(tglLengkap));

        tglSelesaiColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSelesai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSelesaiColumn.setCellFactory(col -> Function.getWrapTableCell(tglSelesaiColumn));
        tglSelesaiColumn.setComparator(Function.sortDate(tglLengkap));

        tglVerifikasiColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglProduksi())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglVerifikasiColumn.setCellFactory(col -> Function.getWrapTableCell(tglVerifikasiColumn));
        tglVerifikasiColumn.setComparator(Function.sortDate(tglLengkap));

        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));

        mesinColumn.setCellValueFactory(cellData -> cellData.getValue().kodeMesinProperty());
        mesinColumn.setCellFactory(col -> Function.getWrapTableCell(mesinColumn));
        listOperatorColumn.setCellValueFactory(cellData -> {
            String x = "";
            for (ProduksiOperator d : cellData.getValue().getListProduksiOperator()) {
                x = x + d.getNamaOperator();
                if (cellData.getValue().getListProduksiOperator().indexOf(d)
                        < cellData.getValue().getListProduksiOperator().size() - 1) {
                    x = x + "\n";
                }
            }
            return new SimpleStringProperty(x);
        });
        listOperatorColumn.setCellFactory(col -> Function.getWrapTableCell(listOperatorColumn));

        listBahanColumn.setCellValueFactory(cellData -> {
            String x = "";
            for (ProduksiDetailBahan d : cellData.getValue().getListProduksiDetailBahan()) {
                x = x + d.getKodeBarang();
                if (cellData.getValue().getListProduksiDetailBahan().indexOf(d)
                        < cellData.getValue().getListProduksiDetailBahan().size() - 1) {
                    x = x + "\n";
                }
            }
            return new SimpleStringProperty(x);
        });
        listBahanColumn.setCellFactory(col -> Function.getWrapTableCell(listBahanColumn));

        totalBeratBahanColumn.setCellValueFactory(cellData -> {
            double qty = 0;
            for (ProduksiDetailBahan d : cellData.getValue().getListProduksiDetailBahan()) {
                if (d.getBarang() != null) {
                    qty = qty + (d.getQty() * d.getBarang().getBerat());
                } else {
                    qty = qty + d.getQty();
                }
            }
            return new SimpleDoubleProperty(qty);
        });
        totalBeratBahanColumn.setCellFactory(col -> Function.getTableCell());

        listBarangColumn.setCellValueFactory(cellData -> {
            String x = "";
            for (ProduksiDetailBarang d : cellData.getValue().getListProduksiDetailBarang()) {
                x = x + d.getKodeBarang();
                if (cellData.getValue().getListProduksiDetailBarang().indexOf(d)
                        < cellData.getValue().getListProduksiDetailBarang().size() - 1) {
                    x = x + "\n";
                }
            }
            return new SimpleStringProperty(x);
        });
        listBarangColumn.setCellFactory(col -> Function.getWrapTableCell(listBarangColumn));

        totalBeratBarangColumn.setCellValueFactory(cellData -> {
            double qty = 0;
            for (ProduksiDetailBarang d : cellData.getValue().getListProduksiDetailBarang()) {
                qty = qty + d.getQty() * d.getBarang().getBerat();
            }
            return new SimpleDoubleProperty(qty);
        });
        totalBeratBarangColumn.setCellFactory(col -> Function.getTableCell());

        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));

        beratColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getJenisProduksi().equals("Bahan - Barang")) {
                double qty = 0;
                for (ProduksiDetailBarang d : cellData.getValue().getListProduksiDetailBarang()) {
                    qty = qty + d.getQty();
                }
                double berat = 0;
                for (ProduksiDetailBahan d : cellData.getValue().getListProduksiDetailBahan()) {
                    berat = berat + d.getQty();
                }
                return new SimpleDoubleProperty(berat / qty);
            } else if (cellData.getValue().getJenisProduksi().equals("Barang - Barang")) {
                double qty = 0;
                double berat = 0;
                for (ProduksiDetailBarang d : cellData.getValue().getListProduksiDetailBarang()) {
                    qty = qty + d.getQty();
                    berat = berat + (d.getQty() * d.getBarang().getBerat());
                }
                return new SimpleDoubleProperty(berat / qty);
            } else {
                return new SimpleDoubleProperty(0);
            }
        });
        beratColumn.setCellFactory(col -> Function.getTableCell());

        bebanMaterialColumn.setCellValueFactory(cellData -> cellData.getValue().materialCostProperty());
        bebanMaterialColumn.setCellFactory(col -> Function.getTableCell());

        bebanProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().biayaProduksiProperty());
        bebanProduksiColumn.setCellFactory(col -> Function.getTableCell());

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        userMulaiColumn.setCellValueFactory(cellData -> cellData.getValue().userMulaiProperty());
        userMulaiColumn.setCellFactory(col -> Function.getWrapTableCell(userMulaiColumn));

        userSelesaiColumn.setCellValueFactory(cellData -> cellData.getValue().userSelesaiProperty());
        userSelesaiColumn.setCellFactory(col -> Function.getWrapTableCell(userSelesaiColumn));

        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Produksi");
        addNew.setOnAction((ActionEvent e) -> {
            showNewProduksiBarang();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getProduksi();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Produksi") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        produksiHeadTable.setContextMenu(rm);
        produksiHeadTable.setRowFactory((TableView<ProduksiHead> tableView) -> {
            final TableRow<ProduksiHead> row = new TableRow<ProduksiHead>() {
                @Override
                public void updateItem(ProduksiHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Produksi");
                        addNew.setOnAction((ActionEvent e) -> {
                            showNewProduksiBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Produksi");
                        detail.setOnAction((ActionEvent e) -> {
                            lihatDetailProduksi(item);
                        });
                        MenuItem foto = new MenuItem("Lihat Bukti Foto Produksi");
                        foto.setOnAction((ActionEvent e) -> {
                            lihatBuktiFoto(item);
                        });
                        MenuItem batal = new MenuItem("Batal Produksi");
                        batal.setOnAction((ActionEvent e) -> {
                            batalProduksi(item);
                        });
                        MenuItem verifikasi = new MenuItem("Verifikasi Produksi");
                        verifikasi.setOnAction((ActionEvent e) -> {
                            verifikasiProduksi(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getProduksi();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Produksi") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Produksi") && o.isStatus()) {
                                rm.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Batal Produksi") && o.isStatus()) {
                                rm.getItems().add(batal);
                            }
                            if (o.getJenis().equals("Verifikasi Produksi") && o.isStatus()) {
                                rm.getItems().add(verifikasi);
                            }
                            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                                rm.getItems().add(export);
                            }
                        }
                        rm.getItems().addAll(foto);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Produksi") && o.isStatus()) {
                                lihatDetailProduksi(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allProduksi.addListener((ListChangeListener.Change<? extends ProduksiHead> change) -> {
            searchProduksi();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchProduksi();
                });
        filterData.addAll(allProduksi);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Bahan - Bahan");
        groupBy.add("Bahan - Barang");
        groupBy.add("Barang - Barang");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Bahan - Barang");
        ObservableList<String> status = FXCollections.observableArrayList();
        status.clear();
        status.add("Wait");
        status.add("Done");
        status.add("Cancel");
        status.add("Semua");
        statusCombo.setItems(status);
        statusCombo.getSelectionModel().select("Wait");
        getProduksi();
        produksiHeadTable.setItems(filterData);
    }

    @FXML
    private void getProduksi() {
        Task<List<ProduksiHead>> task = new Task<List<ProduksiHead>>() {
            @Override
            public List<ProduksiHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (statusCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "true";
                    } else if (statusCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "close";
                    } else if (statusCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    }
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<ProduksiHead> listProduksi = ProduksiHeadDAO.getAllByTglMulaiAndJenisProduksiAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                            groupByCombo.getSelectionModel().getSelectedItem(), status);
                    List<ProduksiDetailBahan> listProduksiBahan = ProduksiDetailBahanDAO.getAllByTglMulaiAndJenisProduksiAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                            groupByCombo.getSelectionModel().getSelectedItem(), status);
                    List<ProduksiDetailBarang> listProduksiBarang = ProduksiDetailBarangDAO.getAllByTglMulaiAndJenisProduksiAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                            groupByCombo.getSelectionModel().getSelectedItem(), status);
                    List<ProduksiOperator> listProduksiOperator = ProduksiOperatorDAO.getAllByTglMulaiAndJenisProduksiAndStatus(
                            con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                            groupByCombo.getSelectionModel().getSelectedItem(), status);
                    for (ProduksiHead p : listProduksi) {
                        List<ProduksiDetailBahan> detailBahan = new ArrayList<>();
                        for (ProduksiDetailBahan d : listProduksiBahan) {
                            if (p.getKodeProduksi().equals(d.getKodeProduksi())) {
                                if (p.getJenisProduksi().equals("Barang - Barang")) {
                                    for (Barang x : listBarang) {
                                        if (d.getKodeBarang().equals(x.getKodeBarang())) {
                                            d.setBarang(x);
                                        }
                                    }
                                }
                                detailBahan.add(d);
                            }
                        }
                        p.setListProduksiDetailBahan(detailBahan);

                        List<ProduksiDetailBarang> detailBarang = new ArrayList<>();
                        for (ProduksiDetailBarang d : listProduksiBarang) {
                            if (p.getKodeProduksi().equals(d.getKodeProduksi())) {
                                for (Barang x : listBarang) {
                                    if (d.getKodeBarang().equals(x.getKodeBarang())) {
                                        d.setBarang(x);
                                    }
                                }
                                detailBarang.add(d);
                            }
                        }
                        p.setListProduksiDetailBarang(detailBarang);

                        List<ProduksiOperator> listOperator = new ArrayList<>();
                        for (ProduksiOperator d : listProduksiOperator) {
                            if (p.getKodeProduksi().equals(d.getKodeProduksi())) {
                                listOperator.add(d);
                            }
                        }
                        p.setListProduksiOperator(listOperator);
                    }
                    return listProduksi;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allProduksi.clear();
            allProduksi.addAll(task.getValue());
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

    private void searchProduksi() {
        try {
            filterData.clear();
            for (ProduksiHead temp : allProduksi) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getKodeProduksi())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeMesin())
                            || checkColumn(df.format(temp.getMaterialCost()))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglProduksi())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglMulai())))
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglSelesai())))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getUserMulai())
                            || checkColumn(temp.getUserSelesai())
                            || checkColumn(temp.getKodeUser())) {
                        filterData.add(temp);
                    } else {
                        boolean status = false;
                        for (ProduksiDetailBahan d : temp.getListProduksiDetailBahan()) {
                            if (checkColumn(d.getKodeBarang())) {
                                status = true;
                            }
                        }
                        for (ProduksiDetailBarang d : temp.getListProduksiDetailBarang()) {
                            if (checkColumn(d.getKodeBarang())) {
                                status = true;
                            }
                        }
                        for (ProduksiOperator d : temp.getListProduksiOperator()) {
                            if (checkColumn(d.getNamaOperator())) {
                                status = true;
                            }
                        }
                        if (status) {
                            filterData.add(temp);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void showNewProduksiBarang() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewProduksiBarang.fxml");
        NewProduksiBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewProduksi();
        controller.saveButton.setOnAction((event) -> {
            double sisa = (Double.parseDouble(controller.totalBeratBahanLabel.getText().replaceAll(",", ""))
                    - Double.parseDouble(controller.totalBeratBarangLabel.getText().replaceAll(",", "")))
                    / Double.parseDouble(controller.totalBeratBahanLabel.getText().replaceAll(",", "")) * 100;
            if (controller.listBahanDiproduksi.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Bahan baku masih kosong");
            } else if (controller.listBarangProduksi.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Barang jadi masih kosong");
            } else if (controller.jenisCombo.getSelectionModel().getSelectedItem().equals("Bahan - Barang") && (sisa > 10 || sisa < -10)) {
                mainApp.showMessage(Modality.NONE, "Warning", "Selisih berat lebih dari 10 persen");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            ProduksiHead produksi = new ProduksiHead();
                            produksi.setUserMulai(sistem.getUser().getKodeUser());
                            produksi.setUserSelesai(sistem.getUser().getKodeUser());
                            produksi.setKodeGudang(controller.gudangCombo.getSelectionModel().getSelectedItem());
                            produksi.setJenisProduksi(controller.jenisCombo.getSelectionModel().getSelectedItem());
                            produksi.setKodeMesin(controller.mesinCombo.getSelectionModel().getSelectedItem());
                            produksi.setBiayaProduksi(0);
                            produksi.setCatatan(controller.catatanField.getText());
                            produksi.setKodeUser(sistem.getUser().getKodeUser());
                            produksi.setTglBatal("2000-01-01 00:00:00");
                            produksi.setUserBatal("");
                            produksi.setStatus("true");
                            produksi.setListProduksiDetailBahan(controller.listBahanDiproduksi);
                            produksi.setListProduksiDetailBarang(controller.listBarangProduksi);

                            List<ProduksiOperator> listProduksiOperator = new ArrayList<>();
                            for (Pegawai p : controller.listOperator) {
                                ProduksiOperator op = new ProduksiOperator();
                                op.setKodeOperator(p.getKodePegawai());
                                op.setNamaOperator(p.getNama());
                                listProduksiOperator.add(op);
                            }
                            produksi.setListProduksiOperator(listProduksiOperator);

                            return Service.newProduksiBarang(con, produksi);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getProduksi();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data produksi barang berhasil disimpan");
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

    private void lihatDetailProduksi(ProduksiHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewProduksiBarang.fxml");
        NewProduksiBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setDetailProduksi(p.getKodeProduksi());
    }

    private void lihatBuktiFoto(ProduksiHead p) {
        ObservableList<ImageView> listImage = FXCollections.observableArrayList();
        try (Connection con = Koneksi.getConnection()) {
            List<ImageDatabase> listImageDb = ImageDatabaseDAO.getAllByNoTransaksi(con, p.getKodeProduksi());
            for (ImageDatabase i : listImageDb) {
                Image image = new Image(i.getDownloadUrl());
                ImageView img = new ImageView(image);
                listImage.add(img);
            }
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/DetailImage.fxml");
            DetailImageController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, child);
            x.setImage(listImage, true);
            x.closeButton.setOnAction((event) -> {
                mainApp.closeDialog(mainApp.MainStage, child);
            });
        } catch (Exception e) {
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    private void verifikasiProduksi(ProduksiHead produksi) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Verifikasi produksi barang " + produksi.getKodeProduksi() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.verifikasiProduksiBarang(con, produksi);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getProduksi();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data produksi barang berhasil diverifikasi");
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

    private void batalProduksi(ProduksiHead produksi) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal produksi barang " + produksi.getKodeProduksi() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        if (produksi.getStatus().equals("true")) {
                            return Service.batalProduksiBarang(con, produksi);
                        } else if (produksi.getStatus().equals("close")) {
                            return Service.batalProduksiBarangApp(con, produksi);
                        } else {
                            return null;
                        }
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getProduksi();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data produksi barang berhasil dibatal");
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
                Font bold = workbook.createFont();
                bold.setBold(true);

                CellStyle H1 = workbook.createCellStyle();
                H1.setFont(bold);
                CellStyle H2 = workbook.createCellStyle();
                H2.setFont(bold);
                H2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                H2.setFillPattern(CellStyle.SOLID_FOREGROUND);

                Sheet sheet = workbook.createSheet("Data Produksi");
                int rc = 0;
                int c = 10;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Produksi");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Produksi");
                sheet.getRow(rc).getCell(2).setCellValue("Gudang");
                sheet.getRow(rc).getCell(3).setCellValue("Bahan");
                sheet.getRow(rc).getCell(4).setCellValue("Total Berat Bahan");
                sheet.getRow(rc).getCell(5).setCellValue("Barang");
                sheet.getRow(rc).getCell(6).setCellValue("Total Berat Barang");
                sheet.getRow(rc).getCell(7).setCellValue("Berat Jadi");
                sheet.getRow(rc).getCell(8).setCellValue("Catatan");
                sheet.getRow(rc).getCell(9).setCellValue("Kode User");
                rc++;
                for (ProduksiHead p : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(p.getKodeProduksi());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglProduksi())));
                    sheet.getRow(rc).getCell(2).setCellValue(p.getKodeGudang());
                    String listBahan = "";
                    double totalBeratBahan = 0;
                    for (ProduksiDetailBahan d : p.getListProduksiDetailBahan()) {
                        listBahan = listBahan + d.getKodeBarang();
                        if (p.getListProduksiDetailBahan().indexOf(d) < p.getListProduksiDetailBahan().size() - 1) {
                            listBahan = listBahan + ", ";
                        }
                        if (d.getBarang() != null) {
                            totalBeratBahan = totalBeratBahan + (d.getQty() * d.getBarang().getBerat());
                        } else {
                            totalBeratBahan = totalBeratBahan + d.getQty();
                        }
                    }
                    sheet.getRow(rc).getCell(3).setCellValue(listBahan);
                    sheet.getRow(rc).getCell(4).setCellValue(totalBeratBahan);
                    String listBarang = "";
                    double totalBeratBarang = 0;
                    for (ProduksiDetailBarang d : p.getListProduksiDetailBarang()) {
                        listBarang = listBarang + d.getKodeBarang();
                        if (p.getListProduksiDetailBarang().indexOf(d) < p.getListProduksiDetailBarang().size() - 1) {
                            listBarang = listBarang + ", ";
                        }
                        totalBeratBarang = totalBeratBarang + d.getQty() * d.getBarang().getBerat();
                    }
                    sheet.getRow(rc).getCell(5).setCellValue(listBarang);
                    sheet.getRow(rc).getCell(6).setCellValue(totalBeratBarang);
                    double beratJadi = 0;
                    if (p.getJenisProduksi().equals("Bahan - Barang")) {
                        double qty = 0;
                        for (ProduksiDetailBarang d : p.getListProduksiDetailBarang()) {
                            qty = qty + d.getQty();
                        }
                        double berat = 0;
                        for (ProduksiDetailBahan d : p.getListProduksiDetailBahan()) {
                            berat = berat + d.getQty();
                        }
                        beratJadi = berat / qty;
                    } else if (p.getJenisProduksi().equals("Barang - Barang")) {
                        double qty = 0;
                        double berat = 0;
                        for (ProduksiDetailBarang d : p.getListProduksiDetailBarang()) {
                            qty = qty + d.getQty();
                            berat = berat + (d.getQty() * d.getBarang().getBerat());
                        }
                        beratJadi = berat / qty;
                    }
                    sheet.getRow(rc).getCell(7).setCellValue(beratJadi);
                    sheet.getRow(rc).getCell(8).setCellValue(p.getCatatan());
                    sheet.getRow(rc).getCell(9).setCellValue(p.getKodeUser());
                    rc++;
                }
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
