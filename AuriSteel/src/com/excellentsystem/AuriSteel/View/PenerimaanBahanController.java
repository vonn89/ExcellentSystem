/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.ImageDatabaseDAO;
import com.excellentsystem.AuriSteel.DAO.PenerimaanBahanDAO;
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
import com.excellentsystem.AuriSteel.Model.ImageDatabase;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailImageController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenerimaanBahanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
 * @author excellent
 */
public class PenerimaanBahanController {

    @FXML
    private TableView<PenerimaanBahan> penerimaanTable;
    @FXML
    private TableColumn<PenerimaanBahan, String> noPenerimaanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> tglPenerimaanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeGudangColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeBahanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> namaBahanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> keteranganColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratTimbanganColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratKotorColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratBersihColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> panjangColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeUserColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> statusColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglMulaiPicker;
    @FXML
    private DatePicker tglAkhirPicker;
    @FXML
    private ComboBox<String> groupByCombo;

    private ObservableList<PenerimaanBahan> allPenerimaan = FXCollections.observableArrayList();
    private ObservableList<PenerimaanBahan> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPenerimaanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenerimaanProperty());
        noPenerimaanColumn.setCellFactory(col -> Function.getWrapTableCell(noPenerimaanColumn));

        tglPenerimaanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenerimaan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenerimaanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPenerimaanColumn));
        tglPenerimaanColumn.setComparator(Function.sortDate(tglLengkap));

        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeGudangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeGudangColumn));
        
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBahanColumn));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTableCell(namaBahanColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        beratTimbanganColumn.setCellValueFactory(celldata -> celldata.getValue().beratTimbanganProperty());
        beratTimbanganColumn.setCellFactory(col -> Function.getTableCell());

        beratKotorColumn.setCellValueFactory(celldata -> celldata.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        
        beratBersihColumn.setCellValueFactory(celldata -> celldata.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        
        panjangColumn.setCellValueFactory(celldata -> celldata.getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        
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


        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Penerimaan");
        addNew.setOnAction((ActionEvent e) -> {
            newPenerimaan();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e) -> {
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPenerimaan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Penerimaan Bahan") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
            if (o.getJenis().equals("Export Excel") && o.isStatus()) {
                rm.getItems().add(export);
            }
        }
        rm.getItems().addAll(refresh);
        penerimaanTable.setContextMenu(rm);
        penerimaanTable.setRowFactory((TableView<PenerimaanBahan> tableView) -> {
            final TableRow<PenerimaanBahan> row = new TableRow<PenerimaanBahan>() {
                @Override
                public void updateItem(PenerimaanBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Penerimaan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenerimaan();
                        });
                        MenuItem batal = new MenuItem("Batal Penerimaan");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPenerimaan(item);
                        });
                        MenuItem foto = new MenuItem("Lihat Bukti Foto Penerimaan");
                        foto.setOnAction((ActionEvent e) -> {
                            lihatBuktiFoto(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e) -> {
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenerimaan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Penerimaan Bahan") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Batal Penerimaan Bahan") && o.isStatus() && item.getStatus().equals("open")) {
                                rm.getItems().add(batal);
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
            return row;
        });
        allPenerimaan.addListener((ListChangeListener.Change<? extends PenerimaanBahan> change) -> {
            searchPenerimaan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenerimaan();
                });
        filterData.addAll(allPenerimaan);
        penerimaanTable.setItems(filterData);
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
        getPenerimaan();
    }

    @FXML
    private void getPenerimaan() {
        Task<List<PenerimaanBahan>> task = new Task<List<PenerimaanBahan>>() {
            @Override
            public List<PenerimaanBahan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Done")) {
                        status = "true";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")) {
                        status = "open";
                    } else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Cancel")) {
                        status = "false";
                    }
                    List<PenerimaanBahan> allPenerimaan = PenerimaanBahanDAO.getAllByDateAndStatus(con,
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    return allPenerimaan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPenerimaan.clear();
            allPenerimaan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            task.getException().printStackTrace();
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

    private void searchPenerimaan() {
        try {
            filterData.clear();
            for (PenerimaanBahan temp : allPenerimaan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenerimaan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenerimaan())))
                            || checkColumn(temp.getKodeBahan())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getNamaBahan())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeUser())
                            || checkColumn(df.format(temp.getBeratKotor()))
                            || checkColumn(df.format(temp.getBeratBersih()))
                            || checkColumn(df.format(temp.getPanjang()))
                            || checkColumn(df.format(temp.getBeratTimbangan()))) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }

    private void newPenerimaan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenerimaanBahan.fxml");
        NewPenerimaanBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if (controller.kodeBahanField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan masih kosong");
            } else if (controller.gudangCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
            } else if (controller.kategoriCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori bahan belum dipilih");
            } else if (Double.parseDouble(controller.beratKotorField.getText().replaceAll(",", ""))==0) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat kotor masih kosong");
            } else if (Double.parseDouble(controller.beratBersihField.getText().replaceAll(",", ""))==0) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih masih kosong");
            } else if (Double.parseDouble(controller.beratTimbanganField.getText().replaceAll(",", ""))==0) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat timbangan masih kosong");
            } else if (Double.parseDouble(controller.beratKotorField.getText().replaceAll(",", ""))<
                    Double.parseDouble(controller.beratBersihField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih tidak boleh lebih besar dari berat kotor");
            } else if (Double.parseDouble(controller.beratTimbanganField.getText().replaceAll(",", ""))<
                    Double.parseDouble(controller.beratBersihField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Berat timbangan tidak boleh lebih kecil dari berat bersih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PenerimaanBahan penerimaan = new PenerimaanBahan();
                            penerimaan.setNoPenerimaan("");
                            penerimaan.setTglPenerimaan("2000-01-01 00:00:00");
                            penerimaan.setKodeGudang(controller.gudangCombo.getSelectionModel().getSelectedItem());
                            penerimaan.setKodeBahan(controller.kodeBahanField.getText());
                            penerimaan.setKodeKategori(controller.kategoriCombo.getSelectionModel().getSelectedItem());
                            penerimaan.setNamaBahan(controller.kategoriCombo.getSelectionModel().getSelectedItem());
                            penerimaan.setKeterangan(controller.keteranganField.getText());
                            penerimaan.setBeratTimbangan(Double.parseDouble(controller.beratTimbanganField.getText().replaceAll(",", "")));
                            penerimaan.setBeratKotor(Double.parseDouble(controller.beratKotorField.getText().replaceAll(",", "")));
                            penerimaan.setBeratBersih(Double.parseDouble(controller.beratBersihField.getText().replaceAll(",", "")));
                            penerimaan.setPanjang(Double.parseDouble(controller.panjangField.getText().replaceAll(",", "")));
                            penerimaan.setKodeUser(sistem.getUser().getKodeUser());
                            penerimaan.setTglBatal("2000-01-01 00:00:00");
                            penerimaan.setUserBatal("");
                            penerimaan.setStatus("open");
                            return Service.newPenerimaanBahan(con, penerimaan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPenerimaan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data penerimaan bahan berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    task.getException().printStackTrace();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }


    private void batalPenerimaan(PenerimaanBahan penerimaan) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal penerimaan bahan " + penerimaan.getNoPenerimaan()+ " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalPenerimaanBahan(con, penerimaan);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPenerimaan();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data penerimaan bahan berhasil dibatal");
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

    private void lihatBuktiFoto(PenerimaanBahan p) {
        ObservableList<ImageView> listImage = FXCollections.observableArrayList();
        try (Connection con = Koneksi.getConnection()) {
            List<ImageDatabase> listImageDb = ImageDatabaseDAO.getAllByNoTransaksi(con, p.getNoPenerimaan());
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
                Sheet sheet = workbook.createSheet("Data Penerimaan Barang");
                int rc = 0;
                int c = 12;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "
                        + tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString())) + "-"
                        + tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : " + searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penerimaan");
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penerimaan");
                sheet.getRow(rc).getCell(2).setCellValue("Kode Gudang");
                sheet.getRow(rc).getCell(3).setCellValue("Kategori Bahan");
                sheet.getRow(rc).getCell(4).setCellValue("Kode Bahan");
                sheet.getRow(rc).getCell(5).setCellValue("Keterangan");
                sheet.getRow(rc).getCell(6).setCellValue("Berat Timbangan");
                sheet.getRow(rc).getCell(7).setCellValue("Berat Kotor");
                sheet.getRow(rc).getCell(8).setCellValue("Berat Bersih");
                sheet.getRow(rc).getCell(9).setCellValue("Panjang");
                sheet.getRow(rc).getCell(10).setCellValue("Kode User");
                rc++;
                for (PenerimaanBahan b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPenerimaan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPenerimaan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKodeKategori());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getKodeBahan());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getKeterangan());
                    sheet.getRow(rc).getCell(6).setCellValue(df.format(b.getBeratTimbangan()));
                    sheet.getRow(rc).getCell(7).setCellValue(df.format(b.getBeratKotor()));
                    sheet.getRow(rc).getCell(8).setCellValue(df.format(b.getBeratBersih()));
                    sheet.getRow(rc).getCell(9).setCellValue(df.format(b.getPanjang()));
                    sheet.getRow(rc).getCell(10).setCellValue(b.getKodeUser());
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
