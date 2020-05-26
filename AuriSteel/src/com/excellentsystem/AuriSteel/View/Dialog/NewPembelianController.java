/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BebanPembelianDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.PembelianDetail;
import com.excellentsystem.AuriSteel.Model.PembelianHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
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
public class NewPembelianController  {
    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> noKontrakColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeBahanColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBahanColumn;
    @FXML private TableColumn<PembelianDetail, String> spesifikasiColumn;
    @FXML private TableColumn<PembelianDetail, String> keteranganColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PembelianDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PembelianDetail, Number> panjangColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalColumn;
    
    @FXML private GridPane gridPane;
    
    @FXML private Label noPembelianField;
    @FXML private Label tglPembelianField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    
    @FXML public ComboBox<String> gudangCombo;
    @FXML public TextArea catatanField;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalBeratKotorField;
    @FXML private Label totalBeratBersihField;
    @FXML private Label totalPanjangField;
    
    @FXML public TextField bebanPembelianField;
    @FXML public TextField totalPembelianField;
    @FXML public TextField grandtotalField;
    
    @FXML private Button addSupplierButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public Supplier supplier;
    public ObservableList<PembelianDetail> allPembelianDetail = FXCollections.observableArrayList();
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
        beratBersihColumn.setCellValueFactory( cellData -> cellData.getValue().beratBersihProperty());
        panjangColumn.setCellValueFactory( cellData -> cellData.getValue().panjangProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellFactory(col -> Function.getTableCell());
        pembelianDetailTable.setItems(allPembelianDetail);
        
        ContextMenu cm = new ContextMenu();
        MenuItem addBahan = new MenuItem("Add New Bahan");
        addBahan.setOnAction((ActionEvent e)->{
            addBahan();
        });
        MenuItem importExcel = new MenuItem("Import Excel");
        importExcel.setOnAction((ActionEvent e)->{
            importBahan();
        });
        MenuItem download = new MenuItem("Download Format Excel");
        download.setOnAction((ActionEvent e)->{
            downloadFormatExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            pembelianDetailTable.refresh();
        });
        cm.getItems().addAll(addBahan, importExcel, download, refresh);
        pembelianDetailTable.setContextMenu(cm);
        pembelianDetailTable.setRowFactory((TableView<PembelianDetail> tv) -> {
            final TableRow<PembelianDetail> row = new TableRow<PembelianDetail>(){
                @Override
                public void updateItem(PembelianDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBahan = new MenuItem("Add New Bahan");
                        addBahan.setOnAction((ActionEvent e)->{
                            addBahan();
                        });
                        MenuItem importExcel = new MenuItem("Import Excel");
                        importExcel.setOnAction((ActionEvent e)->{
                            importBahan();
                        });
                        MenuItem delete = new MenuItem("Delete Bahan");
                        delete.setOnAction((ActionEvent e)->{
                            allPembelianDetail.remove(item);
                            hitungTotal();
                        });
                        MenuItem download = new MenuItem("Download Format Excel");
                        download.setOnAction((ActionEvent e)->{
                            downloadFormatExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            pembelianDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBahan, delete, importExcel, download);
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
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    @FXML
    private void hitungTotal(){
        double totalQty = 0;
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPanjang = 0;
        double totalPembelian = 0;
        double totalBeban = 0;
        for(PembelianDetail d : allPembelianDetail){
            totalQty = totalQty + 1;
            totalPembelian = totalPembelian + d.getTotal();
            totalBeratKotor = totalBeratKotor + d.getBeratKotor();
            totalBeratBersih = totalBeratBersih + d.getBeratBersih();
            totalPanjang = totalPanjang + d.getPanjang();
        }
        for(BebanPembelian b : allBebanPembelian){
            totalBeban = totalBeban + b.getJumlahRp();
        }
        totalQtyField.setText(df.format(totalQty));
        totalBeratKotorField.setText(df.format(totalBeratKotor));
        totalBeratBersihField.setText(df.format(totalBeratBersih));
        totalPanjangField.setText(df.format(totalPanjang));
        bebanPembelianField.setText(df.format(totalBeban));
        totalPembelianField.setText(df.format(totalPembelian));
        grandtotalField.setText(df.format(totalPembelian+totalBeban));
    }
    public void setNewPembelian(){
        noPembelianField.setText("");
        tglPembelianField.setText("");
        allBebanPembelian = new ArrayList<>();
        ObservableList<String> listGudang = FXCollections.observableArrayList();
        for(Gudang g : sistem.getListGudang()){
            listGudang.add(g.getKodeGudang());
        }
        gudangCombo.setItems(listGudang);
    }
    public void setDetailPembelian(String noPembelian){
        Task<PembelianHead> task = new Task<PembelianHead>() {
            @Override 
            public PembelianHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PembelianHead p = PembelianHeadDAO.get(con, noPembelian);
                    p.setSupplier(SupplierDAO.get(con, p.getKodeSupplier()));
                    p.setListPembelianCoilDetail(PembelianDetailDAO.getAllByNoPembelian(con, noPembelian));
                    p.setListBebanPembelianCoil(BebanPembelianDAO.getAllByNoPembelian(con, noPembelian));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addSupplierButton.setVisible(false);
                catatanField.setDisable(true);
                gudangCombo.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                List<MenuItem> removeMenu = new ArrayList<>();
                for(MenuItem m : pembelianDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add New Bahan") || 
                            m.getText().equals("Import Excel") || 
                            m.getText().equals("Download Format Excel"))
                        removeMenu.add(m);
                }
                pembelianDetailTable.getContextMenu().getItems().removeAll(removeMenu);
                
                PembelianHead p = task.getValue();
                noPembelianField.setText(p.getNoPembelian());
                tglPembelianField.setText(tglLengkap.format(tglSql.parse(p.getTglPembelian())));
                namaField.setText(p.getSupplier().getNama());
                alamatField.setText(p.getSupplier().getAlamat());
                catatanField.setText(p.getCatatan());
                gudangCombo.getSelectionModel().select(p.getKodeGudang());

                allPembelianDetail.addAll(p.getListPembelianCoilDetail());
                allBebanPembelian.addAll(p.getListBebanPembelianCoil());
                totalPembelianField.setText(df.format(p.getTotalPembelian()));
                bebanPembelianField.setText(df.format(p.getTotalBebanPembelian()));
                grandtotalField.setText(df.format(p.getGrandtotal()));
                hitungTotal();
            }catch(Exception e){
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
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void importBahan(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select .xls or .xlsx files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
                @Override 
                public List<PembelianDetail> call() throws Exception{
                    List<PembelianDetail> listImport = new ArrayList<>();
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
                            PembelianDetail detail = new PembelianDetail();
                            for (int i=0; i<row.getLastCellNum(); i++)  {
                                Cell cell = row.getCell(i);
                                if(i==0){
                                    if (cell == null)
                                        detail.setKodeKategori("");
                                    else 
                                        detail.setKodeKategori(cell.getStringCellValue());
                                }else if(i==1){
                                    if (cell == null) 
                                        detail.setNoKontrak("");
                                    else 
                                        detail.setNoKontrak(cell.getStringCellValue());
                                }else if(i==2){
                                    if (cell == null)
                                        detail.setKodeBahan("");
                                    else
                                        detail.setKodeBahan(cell.getStringCellValue());
                                }else if(i==3){
                                    if (cell == null) 
                                        detail.setNamaBahan("");
                                    else
                                        detail.setNamaBahan(cell.getStringCellValue());
                                }else if(i==4){
                                    if (cell == null) 
                                        detail.setSpesifikasi("");
                                    else
                                        detail.setSpesifikasi(cell.getStringCellValue());
                                }else if(i==5){
                                    if (cell == null) 
                                        detail.setKeterangan("");
                                    else
                                        detail.setKeterangan(cell.getStringCellValue());
                                }else if(i==6){
                                    if (cell == null)
                                        detail.setBeratKotor(0);
                                    else
                                        detail.setBeratKotor(cell.getNumericCellValue());
                                }else if(i==7){
                                    if (cell == null)
                                        detail.setBeratBersih(0);
                                    else
                                        detail.setBeratBersih(cell.getNumericCellValue());
                                }else if(i==8){
                                    if (cell == null)
                                        detail.setPanjang(0);
                                    else
                                        detail.setPanjang(cell.getNumericCellValue());
                                }else if(i==9){
                                    if (cell == null)
                                        detail.setTotal(0);
                                    else
                                        detail.setTotal(cell.getNumericCellValue());
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
                try{
                    mainApp.closeLoading();
                    List<PembelianDetail> listImport = task.getValue();
                    String statusKodeBahanTerdaftar = "";
                    for(PembelianDetail d : allPembelianDetail){
                        for(PembelianDetail b : listImport){
                            if(d.getKodeBahan().equalsIgnoreCase(b.getKodeBahan()))
                                statusKodeBahanTerdaftar = statusKodeBahanTerdaftar + d.getKodeBahan() + ", ";
                        }
                    }
                    String statusKodeBahanDouble = "";
                    for(PembelianDetail d : listImport){
                        int i = 0;
                        for(PembelianDetail b : listImport){
                            if(d.getKodeBahan().equalsIgnoreCase(b.getKodeBahan()))
                                i = i +1;
                        }
                        if(i>1)
                            statusKodeBahanDouble = statusKodeBahanDouble + d.getKodeBahan() + ", ";
                    }
                    String statusKodeKategori = "";
                    List<KategoriBahan> listKategori = sistem.getListKategoriBahan();
                    for(PembelianDetail d : listImport){
                        boolean s = false;
                        for(KategoriBahan k : listKategori){
                            if(d.getKodeKategori().equalsIgnoreCase(k.getKodeKategori())){
                                d.setKodeKategori(k.getKodeKategori());
                                s = true;
                            }
                        }
                        if(!s)
                            statusKodeKategori = statusKodeKategori + d.getKodeKategori();
                    }
                    if(!statusKodeBahanTerdaftar.equals("")){
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeBahanTerdaftar + " sudah pernah terdaftar");
                    }else if(!statusKodeBahanDouble.equals("")){
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeBahanDouble + " lebih dari satu");
                    }else if(!statusKodeKategori.equals("")){
                        mainApp.showMessage(Modality.NONE, "Failed", statusKodeKategori + " tidak terdaftar dalam kategori bahan");
                    }else{
                        for(PembelianDetail d : listImport){
                            allPembelianDetail.addAll(d);
                        }
                        hitungTotal();
                    }
                }catch(Exception e){
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
    private void downloadFormatExcel(){
        try{
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
                
                createRow(workbook, sheet, rc, c,"Header");
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
                
                for(int i=0 ; i<c ; i++){ sheet.autoSizeColumn(i);}
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    @FXML
    private void addBahan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddNewBahan.fxml");
        AddNewBahanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.kodeKategoriCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori belum dipilih");
            }else if(controller.kodeBahanField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan masih kosong");
            }else if(controller.beratBersihField.getText().equals("0")||controller.beratBersihField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Berat bersih masih kosong");
            }else if(controller.beratKotorField.getText().equals("0")||controller.beratKotorField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Berat kotor masih kosong");
            }else if(controller.panjangField.getText().equals("0")||controller.panjangField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Panjang masih kosong");
            }else if(controller.hargaBeliField.getText().equals("0")||controller.hargaBeliField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Total masih kosong");
            }else{
                Boolean status = true;
                for(PembelianDetail temp : allPembelianDetail){
                    if(temp.getKodeBahan().equals(controller.kodeBahanField.getText()))
                        status=false;
                }
                if(status){
                    PembelianDetail detail = new PembelianDetail();
                    detail.setKodeBahan(controller.kodeBahanField.getText());
                    detail.setKodeKategori(controller.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                    detail.setNoKontrak(controller.noKontrakField.getText());
                    detail.setNamaBahan(controller.namaBahanField.getText());
                    detail.setSpesifikasi(controller.spesifikasiField.getText());
                    detail.setKeterangan(controller.keteranganField.getText());
                    detail.setBeratKotor(Double.parseDouble(controller.beratKotorField.getText().replaceAll(",", "")));
                    detail.setBeratBersih(Double.parseDouble(controller.beratBersihField.getText().replaceAll(",", "")));
                    detail.setPanjang(Double.parseDouble(controller.panjangField.getText().replaceAll(",", "")));
                    detail.setTotal(Double.parseDouble(controller.hargaBeliField.getText().replaceAll(",", "")));
                    allPembelianDetail.add(detail);
                    hitungTotal();
                    mainApp.closeDialog(stage, child);
                }else{
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan baku sudah diinput");
                }
            }
        });
    }
    @FXML
    private void addBeban(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailBebanPembelianCoil.fxml");
        DetailBebanPembelianCoilController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailBebanPembelianCoil(allBebanPembelian);
        if(!saveButton.isVisible())
            controller.viewBebanPembelianCoil();
        controller.saveAndCloseButton.setOnAction((ActionEvent event) -> {
            allBebanPembelian.clear();
            allBebanPembelian.addAll(controller.allBebanPembelianCoilDetail);
            hitungTotal();
            mainApp.closeDialog(stage, child);
        });
    }
    @FXML
    private void addSupplier(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddSupplier.fxml");
        AddSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.supplierTable.setRowFactory(table -> {
            TableRow<Supplier> row = new TableRow<Supplier>(){};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        mainApp.closeDialog(stage, child);
                        supplier = row.getItem();
                        namaField.setText(supplier.getNama());
                        alamatField.setText(supplier.getAlamat());
                    }
                }
            });
            return row;
        });
    }
}
