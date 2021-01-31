/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BebanPembelianDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.BebanPembelian;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.PembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPembelianController {

    @FXML
    private TableView<PembelianBahanDetail> pembelianDetailTable;
    @FXML
    private TableColumn<PembelianBahanDetail, String> noKontrakColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, String> kodeBahanColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, String> namaBahanColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, String> spesifikasiColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, Number> beratKotorColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, Number> beratBersihColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, Number> panjangColumn;
    @FXML
    private TableColumn<PembelianBahanDetail, Number> totalColumn;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label noPembelianField;
    @FXML
    private Label tglPembelianField;

    @FXML
    private TextField namaField;
    @FXML
    private TextArea alamatField;

    @FXML
    private TextField noPemesananField;
    @FXML
    private TextField paymentTermField;
    @FXML
    public ComboBox<String> gudangCombo;
    @FXML
    public TextArea catatanField;

    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalBeratKotorField;
    @FXML
    private Label totalBeratBersihField;
    @FXML
    private Label totalPanjangField;

    @FXML
    public TextField bebanPembelianField;
    @FXML
    public TextField totalPembelianField;
    @FXML
    public TextField grandtotalField;

    @FXML
    private Button addPemesananButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    public PemesananPembelianBahanHead pemesanan;
    public ObservableList<PembelianBahanDetail> allPembelianDetail = FXCollections.observableArrayList();
    public List<BebanPembelian> allBebanPembelian = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        noKontrakColumn.setCellValueFactory(cellData -> cellData.getValue().noKontrakProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().panjangProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellFactory(col -> Function.getTableCell());
        pembelianDetailTable.setItems(allPembelianDetail);

        ContextMenu cm = new ContextMenu();
        MenuItem addBahan = new MenuItem("Add Bahan");
        addBahan.setOnAction((ActionEvent e) -> {
            addPemesananBahan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            pembelianDetailTable.refresh();
        });
        cm.getItems().addAll(addBahan, refresh);
        pembelianDetailTable.setContextMenu(cm);
        pembelianDetailTable.setRowFactory((TableView<PembelianBahanDetail> tv) -> {
            final TableRow<PembelianBahanDetail> row = new TableRow<PembelianBahanDetail>() {
                @Override
                public void updateItem(PembelianBahanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBahan = new MenuItem("Add Bahan");
                        addBahan.setOnAction((ActionEvent e) -> {
                            addPemesananBahan();
                        });
                        MenuItem delete = new MenuItem("Delete Bahan");
                        delete.setOnAction((ActionEvent e) -> {
                            allPembelianDetail.remove(item);
                            hitungTotal();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            pembelianDetailTable.refresh();
                        });
                        if (saveButton.isVisible()) {
                            rm.getItems().addAll(addBahan, delete);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    private void hitungTotal() {
        double totalQty = 0;
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPanjang = 0;
        double totalPembelian = 0;
        double totalBeban = 0;
        for (PembelianBahanDetail d : allPembelianDetail) {
            totalQty = totalQty + 1;
            totalPembelian = totalPembelian + d.getTotal();
            totalBeratKotor = totalBeratKotor + d.getBeratKotor();
            totalBeratBersih = totalBeratBersih + d.getBeratBersih();
            totalPanjang = totalPanjang + d.getPanjang();
        }
        for (BebanPembelian b : allBebanPembelian) {
            totalBeban = totalBeban + b.getJumlahRp();
        }
        totalQtyField.setText(df.format(totalQty));
        totalBeratKotorField.setText(df.format(totalBeratKotor));
        totalBeratBersihField.setText(df.format(totalBeratBersih));
        totalPanjangField.setText(df.format(totalPanjang));
        bebanPembelianField.setText(df.format(totalBeban));
        totalPembelianField.setText(df.format(totalPembelian));
        grandtotalField.setText(df.format(totalPembelian + totalBeban));
    }

    public void setNewPembelian() {
        noPembelianField.setText("");
        tglPembelianField.setText("");
        allBebanPembelian = new ArrayList<>();
        ObservableList<String> listGudang = FXCollections.observableArrayList();
        for (Gudang g : sistem.getListGudang()) {
            listGudang.add(g.getKodeGudang());
        }
        gudangCombo.setItems(listGudang);
    }

    public void setDetailPembelian(String noPembelian) {
        Task<PembelianBahanHead> task = new Task<PembelianBahanHead>() {
            @Override
            public PembelianBahanHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    PembelianBahanHead p = PembelianBahanHeadDAO.get(con, noPembelian);
                    p.setSupplier(SupplierDAO.get(con, p.getKodeSupplier()));
                    p.setListPembelianBahanDetail(PembelianBahanDetailDAO.getAllByNoPembelian(con, noPembelian));
                    p.setListBebanPembelian(BebanPembelianDAO.getAllByNoPembelian(con, noPembelian));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                addPemesananButton.setVisible(false);
                catatanField.setDisable(true);
                gudangCombo.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                List<MenuItem> removeMenu = new ArrayList<>();
                for (MenuItem m : pembelianDetailTable.getContextMenu().getItems()) {
                    if (m.getText().equals("Add New Bahan")) {
                        removeMenu.add(m);
                    }
                }
                pembelianDetailTable.getContextMenu().getItems().removeAll(removeMenu);

                PembelianBahanHead p = task.getValue();

                noPembelianField.setText(p.getNoPembelian());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(p.getTglPembelian())));
                noPemesananField.setText(p.getNoPemesanan());
                paymentTermField.setText(p.getPaymentTerm());
                namaField.setText(p.getSupplier().getNama());
                alamatField.setText(p.getSupplier().getAlamat());
                catatanField.setText(p.getCatatan());
                gudangCombo.getSelectionModel().select(p.getKodeGudang());

                allPembelianDetail.addAll(p.getListPembelianBahanDetail());
                allBebanPembelian.addAll(p.getListBebanPembelian());
                totalPembelianField.setText(df.format(p.getTotalPembelian()));
                bebanPembelianField.setText(df.format(p.getTotalBebanPembelian()));
                grandtotalField.setText(df.format(p.getGrandtotal()));
                hitungTotal();
            } catch (Exception e) {
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void importBahan() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .xls or .xlsx files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"), new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Task<List<PembelianBahanDetail>> task = new Task<List<PembelianBahanDetail>>() {
                @Override
                public List<PembelianBahanDetail> call() throws Exception {
                    List<PembelianBahanDetail> listImport = new ArrayList<>();
                    String excelFilePath = selectedFile.getPath();
                    try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                        Workbook workbook;
                        if (excelFilePath.endsWith("xlsx")) {
                            workbook = new XSSFWorkbook(inputStream);
                        } else if (excelFilePath.endsWith("xls")) {
                            workbook = new HSSFWorkbook(inputStream);
                        } else {
                            throw new IllegalArgumentException("The specified file is not Excel file");
                        }
                        Sheet firstSheet = workbook.getSheetAt(0);
                        Iterator<Row> iterator = firstSheet.iterator();
                        iterator.next();
                        while (iterator.hasNext()) {
                            Row row = iterator.next();
                            PembelianBahanDetail detail = new PembelianBahanDetail();
                            for (int i = 0; i < row.getLastCellNum(); i++) {
                                Cell cell = row.getCell(i);
                                if (i == 0) {
                                    if (cell == null) {
                                        detail.setKodeKategori("");
                                    } else {
                                        detail.setKodeKategori(cell.getStringCellValue());
                                    }
                                } else if (i == 1) {
                                    if (cell == null) {
                                        detail.setNoKontrak("");
                                    } else {
                                        detail.setNoKontrak(cell.getStringCellValue());
                                    }
                                } else if (i == 2) {
                                    if (cell == null) {
                                        detail.setKodeBahan("");
                                    } else {
                                        detail.setKodeBahan(cell.getStringCellValue());
                                    }
                                } else if (i == 3) {
                                    if (cell == null) {
                                        detail.setNamaBahan("");
                                    } else {
                                        detail.setNamaBahan(cell.getStringCellValue());
                                    }
                                } else if (i == 4) {
                                    if (cell == null) {
                                        detail.setSpesifikasi("");
                                    } else {
                                        detail.setSpesifikasi(cell.getStringCellValue());
                                    }
                                } else if (i == 5) {
                                    if (cell == null) {
                                        detail.setKeterangan("");
                                    } else {
                                        detail.setKeterangan(cell.getStringCellValue());
                                    }
                                } else if (i == 6) {
                                    if (cell == null) {
                                        detail.setBeratKotor(0);
                                    } else {
                                        detail.setBeratKotor(cell.getNumericCellValue());
                                    }
                                } else if (i == 7) {
                                    if (cell == null) {
                                        detail.setBeratBersih(0);
                                    } else {
                                        detail.setBeratBersih(cell.getNumericCellValue());
                                    }
                                } else if (i == 8) {
                                    if (cell == null) {
                                        detail.setPanjang(0);
                                    } else {
                                        detail.setPanjang(cell.getNumericCellValue());
                                    }
                                } else if (i == 9) {
                                    if (cell == null) {
                                        detail.setTotal(0);
                                    } else {
                                        detail.setTotal(cell.getNumericCellValue());
                                    }
                                }
                            }
                            listImport.add(detail);
                        }
                        workbook.close();
                    }
                    return listImport;
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ev) -> {
                try {
                    mainApp.closeLoading();
                    List<PembelianBahanDetail> listImport = task.getValue();
                    String statusKodeBahanTerdaftar = "";
                    for (PembelianBahanDetail d : allPembelianDetail) {
                        for (PembelianBahanDetail b : listImport) {
                            if (d.getKodeBahan().equalsIgnoreCase(b.getKodeBahan())) {
                                statusKodeBahanTerdaftar = statusKodeBahanTerdaftar + d.getKodeBahan() + ", ";
                            }
                        }
                    }
                    String statusKodeBahanDouble = "";
                    for (PembelianBahanDetail d : listImport) {
                        int i = 0;
                        for (PembelianBahanDetail b : listImport) {
                            if (d.getKodeBahan().equalsIgnoreCase(b.getKodeBahan())) {
                                i = i + 1;
                            }
                        }
                        if (i > 1) {
                            statusKodeBahanDouble = statusKodeBahanDouble + d.getKodeBahan() + ", ";
                        }
                    }
                    String statusKodeKategori = "";
                    List<KategoriBahan> listKategori = sistem.getListKategoriBahan();
                    for (PembelianBahanDetail d : listImport) {
                        boolean s = false;
                        for (KategoriBahan k : listKategori) {
                            if (d.getKodeKategori().equalsIgnoreCase(k.getKodeKategori())) {
                                d.setKodeKategori(k.getKodeKategori());
                                s = true;
                            }
                        }
                        if (!s) {
                            statusKodeKategori = statusKodeKategori + d.getKodeKategori();
                        }
                    }
                    if (!statusKodeBahanTerdaftar.equals("")) {
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeBahanTerdaftar + " sudah pernah terdaftar");
                    } else if (!statusKodeBahanDouble.equals("")) {
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeBahanDouble + " lebih dari satu");
                    } else if (!statusKodeKategori.equals("")) {
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeKategori + " tidak terdaftar dalam kategori bahan");
                    } else {
                        for (PembelianBahanDetail d : listImport) {
                            allPembelianDetail.addAll(d);
                        }
                        hitungTotal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }

    private void downloadFormatExcel() {
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
                Sheet sheet = workbook.createSheet("Format Excel - Data Pembelian Bahan");
                int rc = 0;
                int c = 10;

                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Kategori");
                sheet.getRow(rc).getCell(1).setCellValue("No Kontrak");
                sheet.getRow(rc).getCell(2).setCellValue("Kode Bahan");
                sheet.getRow(rc).getCell(3).setCellValue("Nama Bahan");
                sheet.getRow(rc).getCell(4).setCellValue("Spesifikasi");
                sheet.getRow(rc).getCell(5).setCellValue("Keterangan");
                sheet.getRow(rc).getCell(6).setCellValue("Berat Kotor");
                sheet.getRow(rc).getCell(7).setCellValue("Berat Bersih");
                sheet.getRow(rc).getCell(8).setCellValue("Panjang");
                sheet.getRow(rc).getCell(9).setCellValue("Total");
                rc++;

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

    @FXML
    private void addPemesananBahan() {
        if (pemesanan == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan Pembelian belum dipilih");
        } else if(gudangCombo.getSelectionModel().getSelectedItem().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPemesananPembelianBahan.fxml");
            AddDetailPemesananPembelianBahanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setPemesananDetail(pemesanan.getListPemesananPembelianBahanDetail());
            controller.pemesananDetailTable.setRowFactory(table -> {
                TableRow<PemesananPembelianBahanDetail> row = new TableRow<PemesananPembelianBahanDetail>() {
                };
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                        if (row.getItem() != null) {
                            PemesananPembelianBahanDetail d = row.getItem();
                            mainApp.closeDialog(stage, child);

                            Stage child2 = new Stage();
                            FXMLLoader loader2 = mainApp.showDialog(stage, child2, "View/Dialog/AddPenerimaanBahan.fxml");
                            AddPenerimaanBahanController controller2 = loader2.getController();
                            controller2.setMainApp(mainApp, stage, child2);
                            controller2.setPenerimaan(gudangCombo.getSelectionModel().getSelectedItem());
                            controller2.selectButton.setOnAction((event) -> {
                                mainApp.closeDialog(stage, child2);
                                List<PenerimaanBahan> listBahan = new ArrayList<>();
                                for (PenerimaanBahan pb : controller2.allPenerimaan) {
                                    if (pb.isIsChecked()) {
                                        listBahan.add(pb);
                                    }
                                }
                                String status = "true";
                                for (PenerimaanBahan pb : listBahan) {
                                    for (PembelianBahanDetail temp : allPembelianDetail) {
                                        if (temp.getKodeBahan().equals(pb.getKodeBahan())) {
                                            status = temp.getKodeBahan();
                                        }
                                    }
                                }
                                if (status.equals("true")) {
                                    for (PenerimaanBahan pb : listBahan) {
                                        PembelianBahanDetail detail = new PembelianBahanDetail();
                                        detail.setNoPemesanan(d.getNoPemesanan());
                                        detail.setNoUrut(d.getNoUrut());
                                        detail.setNoPenerimaan(pb.getNoPenerimaan());
                                        detail.setKodeBahan(pb.getKodeBahan());
                                        detail.setKodeKategori(d.getKategoriBahan());
                                        detail.setNoKontrak(pemesanan.getNoKontrak());
                                        detail.setNamaBahan(pb.getNamaBahan());
                                        detail.setSpesifikasi(d.getSpesifikasi());
                                        detail.setKeterangan(d.getKeterangan());
                                        detail.setBeratKotor(pb.getBeratKotor());
                                        detail.setBeratBersih(pb.getBeratBersih());
                                        detail.setPanjang(pb.getPanjang());
                                        detail.setTotal(pb.getBeratBersih() * d.getHarga());
                                        allPembelianDetail.add(detail);
                                        hitungTotal();
                                    }
                                } else {
                                    mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan baku "+status+" sudah diinput");
                                }
                            });
                        }
                    }
                });
                return row;
            });
        }
    }

    @FXML
    private void addBeban() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailBebanPembelianCoil.fxml");
        DetailBebanPembelianCoilController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailBebanPembelianCoil(allBebanPembelian);
        if (!saveButton.isVisible()) {
            controller.viewBebanPembelianCoil();
        }
        controller.saveAndCloseButton.setOnAction((ActionEvent event) -> {
            allBebanPembelian.clear();
            allBebanPembelian.addAll(controller.allBebanPembelianCoilDetail);
            hitungTotal();
            mainApp.closeDialog(stage, child);
        });
    }

    @FXML
    private void addPemesanan() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPemesananPembelianBahan.fxml");
        AddPemesananPembelianBahanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.pemesananHeadTable.setRowFactory(table -> {
            TableRow<PemesananPembelianBahanHead> row = new TableRow<PemesananPembelianBahanHead>() {
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        mainApp.closeDialog(stage, child);
                        pemesanan = row.getItem();
                        noPemesananField.setText(pemesanan.getNoPemesanan());
                        paymentTermField.setText(pemesanan.getPaymentTerm());
                        namaField.setText(pemesanan.getSupplier().getNama());
                        alamatField.setText(pemesanan.getSupplier().getAlamat());
                    }
                }
            });
            return row;
        });
    }
}
