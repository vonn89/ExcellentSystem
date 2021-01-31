/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.PembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
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
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
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
public class LaporanBahanDibeliController  {

    @FXML private TreeTableView<PembelianBahanDetail> pembelianDetailTable;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> noPembelianColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> tglPembelianColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> gudangColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> namaSupplierColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> kodeKategoriColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> kodeBahanColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, String> namaBahanColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, Number> beratKotorColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, Number> beratBersihColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, Number> panjangColumn;
    @FXML private TreeTableColumn<PembelianBahanDetail, Number> totalColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private Label totalBeratKotorField;
    @FXML private Label totalBeratBersihField;
    @FXML private Label totalPanjangField;
    @FXML private Label totalPembelianField;
    @FXML private DatePicker tglPembelianMulaiPicker;
    @FXML private DatePicker tglPembelianAkhirPicker;
    
    private final TreeItem<PembelianBahanDetail> root = new TreeItem<>();
    private ObservableList<PembelianBahanDetail> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianBahanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPembelianProperty());
        noPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPembelianColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPembelianCoilHead().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));
        
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPembelianCoilHead().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSupplierColumn));
        
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeKategoriColumn));
        
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBahanColumn));
        
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBahanColumn));
        
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPembelianCoilHead().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembelianColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPembelianColumn));
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalProperty());
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tglPembelianMulaiPicker.setConverter(Function.getTglConverter());
        tglPembelianMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPembelianMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPembelianAkhirPicker));
        tglPembelianAkhirPicker.setConverter(Function.getTglConverter());
        tglPembelianAkhirPicker.setValue(LocalDate.now());
        tglPembelianAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPembelianMulaiPicker));
        
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianBahanDetail> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        pembelianDetailTable.setContextMenu(rm);
        pembelianDetailTable.setRowFactory((TreeTableView<PembelianBahanDetail> tableView) -> {
            final TreeTableRow<PembelianBahanDetail> row = new TreeTableRow<PembelianBahanDetail>(){
                @Override
                public void updateItem(PembelianBahanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPembelian(item.getPembelianCoilHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailHutang(item.getPembelianCoilHead());
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent event) -> {
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPembelian();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Pembelian")&&o.isStatus()&&item.getPembelianCoilHead().getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Pembelian")&&o.isStatus()&&
                                    item.getPembelianCoilHead().getPembayaran()>0&&item.getPembelianCoilHead().getStatus()!=null)
                                rm.getItems().add(pembayaran);
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
            return row;
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("No Pembelian");
        groupBy.add("Gudang");
        groupBy.add("Supplier");
        groupBy.add("Kategori Bahan");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Pembelian");
        getPembelian();
    } 
    @FXML
    private void getPembelian(){
        Task<List<PembelianBahanDetail>> task = new Task<List<PembelianBahanDetail>>() {
            @Override 
            public List<PembelianBahanDetail> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PembelianBahanHead> head = PembelianBahanHeadDAO.getAllByDateAndStatus(con, 
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(),"true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianBahanDetail> temp = PembelianBahanDetailDAO.getAllByDateAndStatus(con, 
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(),"true");
                    for(PembelianBahanDetail p : temp){
                        for(PembelianBahanHead pb : head){
                            if(pb.getNoPembelian().equals(p.getNoPembelian()))
                                p.setPembelianCoilHead(pb);
                        }
                        for(Supplier s : allSupplier){
                            if(p.getPembelianCoilHead().getKodeSupplier().equals(s.getKodeSupplier()))
                                p.getPembelianCoilHead().setSupplier(s);
                        }
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPembelian(){
        try{
            filterData.clear();
            for (PembelianBahanDetail temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPembelian())||
                        checkColumn(temp.getKodeKategori())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian())))||
                        checkColumn(temp.getPembelianCoilHead().getSupplier().getNama())||
                        checkColumn(temp.getPembelianCoilHead().getKodeGudang())||
                        checkColumn(temp.getPembelianCoilHead().getCatatan())||
                        checkColumn(temp.getKodeBahan())||
                        checkColumn(temp.getNamaBahan())||
                        checkColumn(df.format(temp.getBeratKotor()))||
                        checkColumn(df.format(temp.getBeratBersih()))||
                        checkColumn(df.format(temp.getPanjang()))||
                        checkColumn(df.format(temp.getTotal())))
                        filterData.add(temp);
                }
            }
            setTable();
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }  
    private void setTable()throws Exception{
        if(pembelianDetailTable.getRoot()!=null)
            pembelianDetailTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PembelianBahanDetail temp: filterData){
            String group = "";
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")){
                group = temp.getNoPembelian();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                group = temp.getPembelianCoilHead().getKodeGudang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")){
                group = temp.getPembelianCoilHead().getSupplier().getNama();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Bahan")){
                group = temp.getKodeKategori();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                group = tgl.format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
            }
            if(!groupBy.contains(group))
                groupBy.add(group);
        }
        for(String temp : groupBy){
            PembelianBahanDetail head = new PembelianBahanDetail();
            head.setNoPembelian(temp);
            head.setPembelianCoilHead(new PembelianBahanHead());
            head.getPembelianCoilHead().setSupplier(new Supplier());
            TreeItem<PembelianBahanDetail> parent = new TreeItem<>(head);
            double totalBeratBersih = 0;
            double totalBeratKotor = 0;
            double totalPanjang = 0;
            double totalHarga = 0;
            for(PembelianBahanDetail detail: filterData){
                boolean status = false;
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")&&
                        temp.equals(detail.getNoPembelian())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                        temp.equals(tgl.format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                        temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                        temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")&&
                        temp.equals(detail.getPembelianCoilHead().getSupplier().getNama())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&&
                        temp.equals(detail.getPembelianCoilHead().getKodeGudang())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Bahan")&&
                        temp.equals(detail.getKodeKategori())){
                    status = true;
                }
                if(status){
                    totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                    totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                    totalPanjang = totalPanjang + detail.getPanjang();
                    totalHarga = totalHarga + detail.getTotal();
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setBeratBersih(totalBeratBersih);
            parent.getValue().setBeratKotor(totalBeratKotor);
            parent.getValue().setPanjang(totalPanjang);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        pembelianDetailTable.setRoot(root);
    }    
    private void hitungTotal(){
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPanjang = 0;
        double totalBeli = 0;
        for(PembelianBahanDetail temp : filterData){
            totalBeratKotor = totalBeratKotor + temp.getBeratKotor();
            totalBeratBersih = totalBeratBersih + temp.getBeratBersih();
            totalPanjang = totalPanjang + temp.getPanjang();
            totalBeli = totalBeli + temp.getTotal();
        }
        totalBeratKotorField.setText(df.format(totalBeratKotor));
        totalBeratBersihField.setText(df.format(totalBeratBersih));
        totalPanjangField.setText(df.format(totalPanjang));
        totalPembelianField.setText(df.format(totalBeli));
    }
    private void lihatDetailPembelian(PembelianBahanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPembelian(p.getNoPembelian());
    }
    private void showDetailHutang(PembelianBahanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPembelian(p);
    }
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanBarangDibeli(allPembelian, tglPembelianMulaiPicker.getValue().toString(),
                    tglPembelianAkhirPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem());
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
                Sheet sheet = workbook.createSheet("Laporan Bahan Dibeli");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglPembelianMulaiPicker.getValue().toString()))+" - "+
                        tgl.format(tglBarang.parse(tglPembelianAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Group By : "+groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pembelian"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pembelian");  
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Supplier"); 
                sheet.getRow(rc).getCell(4).setCellValue("Kode Kategori"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(6).setCellValue("Nama Bahan"); 
                sheet.getRow(rc).getCell(7).setCellValue("Berat Kotor"); 
                sheet.getRow(rc).getCell(8).setCellValue("Berat Bersih"); 
                sheet.getRow(rc).getCell(9).setCellValue("Panjang"); 
                sheet.getRow(rc).getCell(10).setCellValue("Total"); 
                rc++;
                
                List<String> groupBy = new ArrayList<>();
                for(PembelianBahanDetail temp: filterData){
                    String group = "";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")){
                        group = temp.getNoPembelian();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                        group = temp.getPembelianCoilHead().getKodeGudang();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")){
                        group = temp.getPembelianCoilHead().getSupplier().getNama();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Bahan")){
                        group = temp.getKodeKategori();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                        group = tgl.format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPembelianCoilHead().getTglPembelian()));
                    }
                    if(!groupBy.contains(group))
                        groupBy.add(group);
                }
                double grandtotalBeratBersih = 0;
                double grandtotalBeratKotor = 0;
                double grandtotalPanjang = 0;
                double grandtotalHarga = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);  
                    rc++;
                    double totalBeratBersih = 0;
                    double totalBeratKotor = 0;
                    double totalPanjang = 0;
                    double totalHarga = 0;
                    for(PembelianBahanDetail detail: filterData){
                        boolean status = false;
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Pembelian")&&
                                temp.equals(detail.getNoPembelian())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                                temp.equals(tgl.format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                                temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                                temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Supplier")&&
                                temp.equals(detail.getPembelianCoilHead().getSupplier().getNama())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&&
                                temp.equals(detail.getPembelianCoilHead().getKodeGudang())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kategori Bahan")&&
                                temp.equals(detail.getKodeKategori())){
                            status = true;
                        }
                        if(status){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getNoPembelian());  
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPembelianCoilHead().getTglPembelian())));  
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPembelianCoilHead().getKodeGudang()); 
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPembelianCoilHead().getSupplier().getNama()); 
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeKategori()); 
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getKodeBahan()); 
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getNamaBahan()); 
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getBeratKotor()); 
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getBeratBersih()); 
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getPanjang()); 
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getTotal()); 
                            rc++;
                            
                            totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                            totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                            totalPanjang = totalPanjang + detail.getPanjang();
                            totalHarga = totalHarga + detail.getTotal();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);  
                    sheet.getRow(rc).getCell(7).setCellValue(totalBeratKotor); 
                    sheet.getRow(rc).getCell(8).setCellValue(totalBeratBersih); 
                    sheet.getRow(rc).getCell(9).setCellValue(totalPanjang); 
                    sheet.getRow(rc).getCell(10).setCellValue(totalHarga); 
                    rc++;
                    grandtotalBeratKotor = grandtotalBeratKotor + totalBeratKotor;
                    grandtotalBeratBersih = grandtotalBeratBersih + totalBeratBersih;
                    grandtotalPanjang = grandtotalPanjang + totalPanjang;
                    grandtotalHarga = grandtotalHarga + totalHarga;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalBeratKotor); 
                sheet.getRow(rc).getCell(8).setCellValue(grandtotalBeratBersih); 
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalPanjang); 
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalHarga); 
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
