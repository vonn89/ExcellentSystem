/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBahan;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.PenyesuaianStokController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
 * @author Xtreme
 */
public class LaporanBahanController  {

    @FXML private TreeTableView<StokBahan> bahanTable;
    @FXML private TreeTableColumn<StokBahan, String> kodeBahanColumn;
    @FXML private TreeTableColumn<StokBahan, String> namaBahanColumn;
    @FXML private TreeTableColumn<StokBahan, Number> beratKotorColumn;
    @FXML private TreeTableColumn<StokBahan, Number> beratBersihColumn;
    @FXML private TreeTableColumn<StokBahan, Number> panjangColumn;
    @FXML private TreeTableColumn<StokBahan, Number> hargaBeliColumn;
    @FXML private TreeTableColumn<StokBahan, Number> stokAkhirColumn;
    @FXML private TreeTableColumn<StokBahan, Number> nilaiAkhirColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private DatePicker tglStokPicker;
    @FXML private ComboBox<String> gudangCombo;
    @FXML private Label totalBeratField;
    @FXML private Label totalNilaiField;
    
    final TreeItem<StokBahan> root = new TreeItem<>();
    private ObservableList<StokBahan> allBahan = FXCollections.observableArrayList();
    private ObservableList<StokBahan> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBahanColumn));
        
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBahanColumn));
        
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tglStokPicker.setConverter(Function.getTglConverter());
        tglStokPicker.setValue(LocalDate.now());
        tglStokPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(LocalDate.now()));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getBahan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        bahanTable.setContextMenu(rm);
        bahanTable.setRowFactory((TreeTableView<StokBahan> tableView) -> {
            final TreeTableRow<StokBahan> row = new TreeTableRow<StokBahan>(){
                @Override
                public void updateItem(StokBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem kartu = new MenuItem("Lihat Kartu Stok");
                        kartu.setOnAction((ActionEvent e)->{
                            showKartuStok(item);
                        });
                        MenuItem logBahan = new MenuItem("Lihat Log Bahan");
                        logBahan.setOnAction((ActionEvent e)->{
                            showLogBahan(item);
                        });
                        MenuItem penyesuaian = new MenuItem("Penyesuaian Stok Bahan");
                        penyesuaian.setOnAction((ActionEvent e)->{
                            showPenyesuaianStok(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getBahan();
                        });
                        if(item.getBahan().getNamaBahan()!=null){
                            rm.getItems().add(kartu);
                            rm.getItems().add(logBahan);
                        }
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Penyesuaian Stok Bahan")&&o.isStatus()&&
                                    item.getBahan().getNamaBahan()!=null)
                                rm.getItems().addAll(penyesuaian);
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rm.getItems().addAll(print);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().addAll(export);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        if(row.getItem().getBahan().getNamaBahan()!=null)
                            showLogBahan(row.getItem());
                }
            });
            return row;
        });
        allBahan.addListener((ListChangeListener.Change<? extends StokBahan> change) -> {
            searchBahan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBahan();
        });
        filterData.addAll(allBahan);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> group = FXCollections.observableArrayList();
        group.add("Kategori");
        group.add("No Kontrak");
        groupByCombo.setItems(group);
        groupByCombo.getSelectionModel().select("Kategori");
        ObservableList<String> listGudang = FXCollections.observableArrayList();
        for(Gudang g : sistem.getListGudang()){
            listGudang.add(g.getKodeGudang());
        }
        gudangCombo.setItems(listGudang);
        gudangCombo.getSelectionModel().selectFirst();
        getBahan();
    }
    public void setTanggal(LocalDate tglAkhir){
        tglStokPicker.setValue(tglAkhir);
        getBahan();
    }
    @FXML
    private void getBahan(){
        if(gudangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<StokBahan>> task = new Task<List<StokBahan>>() {
                @Override 
                public List<StokBahan> call()throws Exception{ 
                    try(Connection con = Koneksi.getConnection()){
                        List<Bahan> listBahan  = BahanDAO.getAllByStatus(con, "%");
                        List<StokBahan> listStok = StokBahanDAO.getAllByDateAndGudang(con, 
                                tglStokPicker.getValue().toString(), gudangCombo.getSelectionModel().getSelectedItem());
                        List<StokBahan> allStok = new ArrayList<>();
                        for(StokBahan s : listStok){
                            if(s.getStokAkhir()>0){
                                for(Bahan b : listBahan){
                                    if(s.getKodeBahan().equals(b.getKodeBahan()))
                                        s.setBahan(b);
                                }
                                s.setNilaiAkhir(s.getStokAkhir()*s.getBahan().getHargaBeli()/s.getBahan().getBeratBersih());
                                allStok.add(s);
                            }
                        }
                        return allStok;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                allBahan.clear();
                allBahan.addAll(task.getValue());
            });
            task.setOnFailed((e) -> {
                task.getException().printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchBahan() {
        filterData.clear();
        for (StokBahan temp : allBahan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getBahan().getKodeBahan())||
                    checkColumn(temp.getKodeGudang())||
                    checkColumn(temp.getBahan().getNamaBahan())||
                    checkColumn(temp.getBahan().getNoKontrak())||
                    checkColumn(temp.getBahan().getKodeKategori())||
                    checkColumn(df.format(temp.getBahan().getHargaBeli()))||
                    checkColumn(df.format(temp.getBahan().getBeratKotor()))||
                    checkColumn(df.format(temp.getBahan().getBeratBersih()))||
                    checkColumn(df.format(temp.getBahan().getPanjang()))||
                    checkColumn(df.format(temp.getStokAkhir())))
                    filterData.add(temp);
            }
        }
        setTable();
        hitungTotal();
    }
    private void setTable(){
        if(bahanTable.getRoot()!=null)
            bahanTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(StokBahan stokBahan : filterData){
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")){
                if(!groupBy.contains(stokBahan.getBahan().getKodeKategori()))
                    groupBy.add(stokBahan.getBahan().getKodeKategori());
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Kontrak")){
                if(!groupBy.contains(stokBahan.getBahan().getNoKontrak()))
                    groupBy.add(stokBahan.getBahan().getNoKontrak());
            }
        }
        for(String temp : groupBy){
            double totalBeratKotor = 0;
            double totalBeratBersih = 0;
            double totalPanjang = 0;
            double hargaBeli= 0;
            double beratAkhir=0;
            double nilaiAkhir=0;
            StokBahan head = new StokBahan();
            Bahan bahan = new Bahan();
            bahan.setKodeBahan(temp);
            head.setBahan(bahan);
            TreeItem<StokBahan> parent = new TreeItem<>(head);
            for(StokBahan detail: filterData){
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori")){
                    if(temp.equals(detail.getBahan().getKodeKategori())){
                        TreeItem<StokBahan> child = new TreeItem<>(detail);
                        parent.getChildren().add(child);
                        totalBeratKotor = totalBeratKotor + detail.getBahan().getBeratKotor();
                        totalBeratBersih = totalBeratBersih +detail.getBahan().getBeratBersih();
                        totalPanjang = totalPanjang +detail.getBahan().getPanjang();
                        hargaBeli = hargaBeli + detail.getBahan().getHargaBeli();
                        beratAkhir = beratAkhir + detail.getStokAkhir();
                        nilaiAkhir = nilaiAkhir + detail.getNilaiAkhir();
                    }
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Kontrak")){
                    if(temp.equals(detail.getBahan().getNoKontrak())){
                        TreeItem<StokBahan> child = new TreeItem<>(detail);
                        parent.getChildren().add(child);
                        totalBeratKotor = totalBeratKotor + detail.getBahan().getBeratKotor();
                        totalBeratBersih = totalBeratBersih +detail.getBahan().getBeratBersih();
                        totalPanjang = totalPanjang +detail.getBahan().getPanjang();
                        hargaBeli = hargaBeli + detail.getBahan().getHargaBeli();
                        beratAkhir = beratAkhir + detail.getStokAkhir();
                        nilaiAkhir = nilaiAkhir + detail.getNilaiAkhir();
                    }
                }
            }
            parent.getValue().getBahan().setBeratKotor(totalBeratKotor);
            parent.getValue().getBahan().setBeratBersih(totalBeratBersih);
            parent.getValue().getBahan().setPanjang(totalPanjang);
            parent.getValue().getBahan().setHargaBeli(hargaBeli);
            parent.getValue().setStokAkhir(beratAkhir);
            parent.getValue().setNilaiAkhir(nilaiAkhir);
            root.getChildren().add(parent);
        }
        bahanTable.setRoot(root);
    }
    private void hitungTotal(){
        double totalBerat =0;
        double totalNilai = 0;
        for(StokBahan temp : filterData){
            totalBerat = totalBerat + temp.getStokAkhir();
            totalNilai = totalNilai + temp.getNilaiAkhir();
        }
        totalBeratField.setText(df.format(totalBerat));
        totalNilaiField.setText(df.format(totalNilai));
    }
    private void showLogBahan(StokBahan s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/LogBahan.fxml");
        LogBahanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setBahan(s);
    }
    private void showKartuStok(StokBahan b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Report/LaporanKartuStokBahan.fxml");
        LaporanKartuStokBahanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBahan(b);
    }
    private void showPenyesuaianStok(StokBahan s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PenyesuaianStok.fxml");
        PenyesuaianStokController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setBahan(s.getKodeBahan(), s.getKodeGudang());
        x.saveButton.setOnAction((event) -> {
            if (x.statusCombo.getSelectionModel().getSelectedItem() == null){
                mainApp.showMessage(Modality.NONE, "Warning", "Status penyesuaian stok belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call()throws Exception{ 
                        try(Connection con = Koneksi.getConnection()){
                            double qty = -Double.parseDouble(x.qtyField.getText().replaceAll(",", ""));
                            if(x.statusCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok"))
                                qty = Double.parseDouble(x.qtyField.getText().replaceAll(",", ""));
                            PenyesuaianStokBahan p = new PenyesuaianStokBahan();
                            p.setKodeBahan(s.getKodeBahan());
                            p.setKodeGudang(s.getKodeGudang());
                            p.setQty(qty);
                            p.setNilai(0);
                            p.setCatatan(x.catatanField.getText());
                            p.setKodeUser(sistem.getUser().getKodeUser());
                            p.setStatus("true");
                            return Service.savePenyesuaianStokBahan(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getBahan();
                    String status = task.getValue();
                    if (status.equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Penyesuaian Stok Bahan berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", status);
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
    private void print(){
        try{
            List<StokBahan> listStokBahan = new ArrayList<>();
            for(StokBahan d : allBahan){
                listStokBahan.add(d);
            }
            Report report = new Report();
            report.printLaporanBahan(listStokBahan);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void exportExcel(){
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
                Sheet sheet = workbook.createSheet("Laporan Bahan");
                int rc = 0;
                int c = 8;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglStokPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Nama Bahan");  
                sheet.getRow(rc).getCell(2).setCellValue("Berat Kotor"); 
                sheet.getRow(rc).getCell(3).setCellValue("Berat Bersih"); 
                sheet.getRow(rc).getCell(4).setCellValue("Panjang"); 
                sheet.getRow(rc).getCell(5).setCellValue("Harga Beli"); 
                sheet.getRow(rc).getCell(6).setCellValue("Stok Akhir"); 
                sheet.getRow(rc).getCell(7).setCellValue("Nilai Akhir"); 
                rc++;
                
                List<String> kategori = new ArrayList<>();
                for(StokBahan temp: filterData){
                    if(!kategori.contains(temp.getBahan().getKodeKategori()))
                        kategori.add(temp.getBahan().getKodeKategori());
                }
                double grandtotalBeratKotor = 0;
                double grandtotalBeratBersih = 0;
                double grandtotalPanjang = 0;
                double grandtotalhargaBeli = 0;
                double grandtotalStokAkhir = 0;
                double grandtotalNilaiAkhir = 0;
                for(String temp : kategori){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    
                    double totalBeratKotor = 0;
                    double totalBeratBersih = 0;
                    double totalPanjang = 0;
                    double totalhargaBeli = 0;
                    double totalStokAkhir = 0;
                    double totalNilaiAkhir = 0;
                    for(StokBahan detail: filterData){
                        if(temp.equals(detail.getBahan().getKodeKategori())){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getKodeBahan());
                            sheet.getRow(rc).getCell(1).setCellValue(detail.getBahan().getNamaBahan());
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getBahan().getBeratKotor()); 
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getBahan().getBeratBersih()); 
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getBahan().getPanjang()); 
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getBahan().getHargaBeli()); 
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getStokAkhir()); 
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getNilaiAkhir()); 
                            rc++;
                            
                            totalBeratKotor = totalBeratKotor + detail.getBahan().getBeratKotor();
                            totalBeratBersih = totalBeratBersih +detail.getBahan().getBeratBersih();
                            totalPanjang = totalPanjang +detail.getBahan().getPanjang();
                            totalhargaBeli = totalhargaBeli + detail.getBahan().getHargaBeli();
                            totalStokAkhir = totalStokAkhir + detail.getStokAkhir();
                            totalNilaiAkhir = totalNilaiAkhir + detail.getNilaiAkhir();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(2).setCellValue(totalBeratKotor); 
                    sheet.getRow(rc).getCell(3).setCellValue(totalBeratBersih); 
                    sheet.getRow(rc).getCell(4).setCellValue(totalPanjang); 
                    sheet.getRow(rc).getCell(5).setCellValue(totalhargaBeli); 
                    sheet.getRow(rc).getCell(6).setCellValue(totalStokAkhir); 
                    sheet.getRow(rc).getCell(7).setCellValue(totalNilaiAkhir); 
                    rc++;
                    
                    grandtotalBeratKotor = grandtotalBeratKotor + totalBeratKotor;
                    grandtotalBeratBersih = grandtotalBeratBersih + totalBeratBersih;
                    grandtotalPanjang = grandtotalPanjang + totalPanjang;
                    grandtotalhargaBeli = grandtotalhargaBeli + totalhargaBeli;
                    grandtotalStokAkhir = grandtotalStokAkhir + totalStokAkhir;
                    grandtotalNilaiAkhir = grandtotalNilaiAkhir + totalNilaiAkhir;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(2).setCellValue(grandtotalBeratKotor); 
                sheet.getRow(rc).getCell(3).setCellValue(grandtotalBeratBersih); 
                sheet.getRow(rc).getCell(4).setCellValue(grandtotalPanjang); 
                sheet.getRow(rc).getCell(5).setCellValue(grandtotalhargaBeli); 
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalStokAkhir); 
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalNilaiAkhir); 
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
}
