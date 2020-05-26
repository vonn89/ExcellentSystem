/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
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
public class NeracaStokBahanController  {

    @FXML private TreeTableView<StokBahan> bahanTable;
    @FXML private TreeTableColumn<StokBahan, String> kodeBahanColumn;
    @FXML private TreeTableColumn<StokBahan, String> namaBahanColumn;
    @FXML private TreeTableColumn<StokBahan, Number> beratKotorColumn;
    @FXML private TreeTableColumn<StokBahan, Number> beratBersihColumn;
    @FXML private TreeTableColumn<StokBahan, Number> panjangColumn;
    @FXML private TreeTableColumn<StokBahan, Number> hargaBeliColumn;
    @FXML private TreeTableColumn<StokBahan, Number> beratAkhirColumn;
    @FXML private TreeTableColumn<StokBahan, Number> nilaiAkhirColumn;
    
    @FXML private Label totalBeratField;
    @FXML private Label totalNilaiField;
    
    final TreeItem<StokBahan> root = new TreeItem<>();
    private ObservableList<StokBahan> allBahan = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().namaBahanProperty());
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTreeTableCell());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBahan().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().stokAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> Function.getTreeTableCell());
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> Function.getTreeTableCell());
        
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
            setTable();
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
                            setTable();
                        });
                        if(item.getBahan().getNamaBahan()!=null){
                            rm.getItems().add(kartu);
                            rm.getItems().add(logBahan);
                        }
                        for(Otoritas o : sistem.getUser().getOtoritas()){
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
    }
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
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
    public void getBahan(LocalDate tglAkhir, String gudang){
        Task<List<StokBahan>> task = new Task<List<StokBahan>>() {
            @Override 
            public List<StokBahan> call()throws Exception{ 
                try(Connection con = Koneksi.getConnection()){
                    List<Bahan> listBahan  = BahanDAO.getAllByStatus(con, "%");
                    List<StokBahan> listStok = StokBahanDAO.getAllByDateAndGudang(con, tglAkhir.toString(), gudang);
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
            setTable();
            hitungTotal();
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private void setTable(){
        if(bahanTable.getRoot()!=null)
            bahanTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(StokBahan stokBahan : allBahan){
            if(!groupBy.contains(stokBahan.getBahan().getKodeKategori()))
                groupBy.add(stokBahan.getBahan().getKodeKategori());
        }
        for(String temp : groupBy){
            double totalBeratKotor = 0;
            double totalBeratBersih = 0;
            double totalPanjang = 0;
            double hargaBeli= 0;
            double beratAkhir=0;
            double nilaiAkhir= 0;
            StokBahan head = new StokBahan();
            Bahan bahan = new Bahan();
            bahan.setKodeBahan(temp);
            head.setBahan(bahan);
            TreeItem<StokBahan> parent = new TreeItem<>(head);
            for(StokBahan detail: allBahan){
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
        for(StokBahan temp : allBahan){
            totalBerat = totalBerat + temp.getStokAkhir();
            totalNilai = totalNilai + temp.getNilaiAkhir();
        }
        totalBeratField.setText(df.format(totalBerat));
        totalNilaiField.setText(df.format(totalNilai));
    }
    private void showLogBahan(StokBahan s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Report/LogBahan.fxml");
        LogBahanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBahan(s);
    }
    private void showKartuStok(StokBahan b){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Report/LaporanKartuStokBahan.fxml");
        LaporanKartuStokBahanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBahan(b);
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
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
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
                Sheet sheet = workbook.createSheet("Laporan Neraca - Stok Bahan");
                int rc = 0;
                int c = 8;
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
                for(StokBahan temp: allBahan){
                    if(!kategori.contains(temp.getBahan().getKodeKategori()))
                        kategori.add(temp.getBahan().getKodeKategori());
                }
                double grandtotalBeratKotor = 0;
                double grandtotalBeratBersih = 0;
                double grandtotalPanjang = 0;
                double grandtotalhargaBeli = 0;
                double grandtotalberatAkhir = 0;
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
                    double totalberatAkhir = 0;
                    double totalNilaiAkhir = 0;
                    for(StokBahan detail: allBahan){
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
                            totalberatAkhir = totalberatAkhir + detail.getStokAkhir();
                            totalNilaiAkhir = totalNilaiAkhir + detail.getNilaiAkhir();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(2).setCellValue(totalBeratKotor); 
                    sheet.getRow(rc).getCell(3).setCellValue(totalBeratBersih); 
                    sheet.getRow(rc).getCell(4).setCellValue(totalPanjang); 
                    sheet.getRow(rc).getCell(5).setCellValue(totalhargaBeli); 
                    sheet.getRow(rc).getCell(6).setCellValue(totalberatAkhir); 
                    sheet.getRow(rc).getCell(7).setCellValue(totalNilaiAkhir); 
                    rc++;
                    
                    grandtotalBeratKotor = grandtotalBeratKotor + totalBeratKotor;
                    grandtotalBeratBersih = grandtotalBeratBersih + totalBeratBersih;
                    grandtotalPanjang = grandtotalPanjang + totalPanjang;
                    grandtotalhargaBeli = grandtotalhargaBeli + totalhargaBeli;
                    grandtotalberatAkhir = grandtotalberatAkhir + totalberatAkhir;
                    grandtotalNilaiAkhir = grandtotalNilaiAkhir + totalNilaiAkhir;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(2).setCellValue(grandtotalBeratKotor); 
                sheet.getRow(rc).getCell(3).setCellValue(grandtotalBeratBersih); 
                sheet.getRow(rc).getCell(4).setCellValue(grandtotalPanjang); 
                sheet.getRow(rc).getCell(5).setCellValue(grandtotalhargaBeli); 
                sheet.getRow(rc).getCell(6).setCellValue(grandtotalberatAkhir); 
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
