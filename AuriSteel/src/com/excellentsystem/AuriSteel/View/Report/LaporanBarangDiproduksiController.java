/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

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
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.NewProduksiBarangController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
public class LaporanBarangDiproduksiController  {

    @FXML private TreeTableView<ProduksiDetailBarang> produksiBarangDetailTable;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> kodeProduksiColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> tglProduksiColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> gudangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> listBahanColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> catatanColumn;
    
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> kodeBarangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> namaBarangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, String> satuanBarangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, Number> qtyBarangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, Number> nilaiBarangColumn;
    @FXML private TreeTableColumn<ProduksiDetailBarang, Number> nilaiSatuanBarangColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    @FXML private DatePicker tglProduksiMulaiPicker;
    @FXML private DatePicker tglProduksiAkhirPicker;
    private final TreeItem<ProduksiDetailBarang> root = new TreeItem<>();
    private ObservableList<ProduksiDetailBarang> allProduksi = FXCollections.observableArrayList();
    private ObservableList<ProduksiDetailBarang> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        kodeProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeProduksiProperty());
        kodeProduksiColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeProduksiColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getProduksiHead().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));
        
        tglProduksiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getValue().getProduksiHead().getTglProduksi())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglProduksiColumn.setComparator(Function.sortDate(tglLengkap));
        tglProduksiColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglProduksiColumn));
        
        listBahanColumn.setCellValueFactory(cellData -> {
            String x = "";
            for(ProduksiDetailBahan d : cellData.getValue().getValue().getProduksiHead().getListProduksiDetailBahan()){
                x = x + d.getKodeBarang();
                if(cellData.getValue().getValue().getProduksiHead().getListProduksiDetailBahan().indexOf(d)<
                        cellData.getValue().getValue().getProduksiHead().getListProduksiDetailBahan().size()-1)
                    x = x + ", ";
            }
            return new SimpleStringProperty(x);
        });
        listBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(listBahanColumn));
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getProduksiHead().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(catatanColumn));
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBarangColumn));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBarangColumn));
        
        satuanBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getBarang().satuanProperty());
        satuanBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(satuanBarangColumn));
        
        qtyBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyBarangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        nilaiBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiProperty());
        nilaiBarangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        nilaiSatuanBarangColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue().getNilai()/cellData.getValue().getValue().getQty()));
        nilaiSatuanBarangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tglProduksiMulaiPicker.setConverter(Function.getTglConverter());
        tglProduksiMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglProduksiMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglProduksiAkhirPicker));
        tglProduksiAkhirPicker.setConverter(Function.getTglConverter());
        tglProduksiAkhirPicker.setValue(LocalDate.now());
        tglProduksiAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglProduksiMulaiPicker));
        
        allProduksi.addListener((ListChangeListener.Change<? extends ProduksiDetailBarang> change) -> {
            searchProduksi();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchProduksi();
        });
        filterData.addAll(allProduksi);
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
            getProduksi();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        produksiBarangDetailTable.setContextMenu(rm);
        produksiBarangDetailTable.setRowFactory((TreeTableView<ProduksiDetailBarang> tableView) -> {
            final TreeTableRow<ProduksiDetailBarang> row = new TreeTableRow<ProduksiDetailBarang>(){
                @Override
                public void updateItem(ProduksiDetailBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Produksi");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailProduksi(item.getProduksiHead());
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
                            getProduksi();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Produksi")&&o.isStatus()&&item.getKodeBarang()!=null)
                                rm.getItems().add(detail);
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
        groupBy.add("No Produksi");
        groupBy.add("Gudang");
        groupBy.add("Barang");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Produksi");
        getProduksi();
    } 
    @FXML
    private void getProduksi(){
        Task<List<ProduksiDetailBarang>> task = new Task<List<ProduksiDetailBarang>>() {
            @Override 
            public List<ProduksiDetailBarang> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
//                    List<ProduksiHead> produksiHead = ProduksiHeadDAO.getAllByDateAndJenisProduksiAndStatus(con, 
//                            tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(),"true");
//                    List<ProduksiDetailBarang> allProduksi = ProduksiDetailBarangDAO.getAllByDateAndStatus(con, 
//                            tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(),"true");
//                    List<ProduksiDetailBahan> allProduksiBahan = ProduksiDetailBahanDAO.getAllByDateAndStatus(con, 
//                            tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(),"true");
//                    List<Barang> allbarang = BarangDAO.getAllByStatus(con, "%");
//                    for(ProduksiDetailBarang d : allProduksi){
//                        for(Barang b : allbarang){
//                            if(d.getKodeBarang().equals(b.getKodeBarang()))
//                                d.setBarang(b);
//                        }
//                        for(ProduksiHead p : produksiHead){
//                            if(d.getKodeProduksi().equals(p.getKodeProduksi()))
//                                d.setProduksiHead(p);
//                        }
//                        List<ProduksiDetailBahan> listBahan = new ArrayList<>();
//                        for(ProduksiDetailBahan b : allProduksiBahan){
//                            if(b.getKodeProduksi().equals(d.getKodeProduksi()))
//                                listBahan.add(b);
//                        }
//                        d.getProduksiHead().setListProduksiDetailBahan(listBahan);
//                    }
                    return allProduksi;
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
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchProduksi(){
        try{
            filterData.clear();
            for (ProduksiDetailBarang temp : allProduksi) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodeProduksi())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getProduksiHead().getTglProduksi())))||
                        checkColumn(temp.getProduksiHead().getKodeGudang())||
                        checkColumn(temp.getBarang().getKodeBarang())||
                        checkColumn(temp.getBarang().getNamaBarang())||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(temp.getBarang().getSpesifikasi())||
                        checkColumn(df.format(temp.getBarang().getBerat()))||
                        checkColumn(temp.getBarang().getSatuan())||
                        checkColumn(df.format(temp.getNilai()))||
                        checkColumn(df.format(temp.getNilai()/temp.getQty())))
                        filterData.add(temp);
                    else{
                        boolean status = false;
                        for(ProduksiDetailBahan d : temp.getProduksiHead().getListProduksiDetailBahan()){
                            if(checkColumn(d.getKodeBarang()))
                                status = true;
                        }
                        if(status)
                            filterData.add(temp);
                    }
                }
            }
            setTable();
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    private void setTable()throws Exception{
        if(produksiBarangDetailTable.getRoot()!=null)
            produksiBarangDetailTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(ProduksiDetailBarang temp: filterData){
            String group = "";
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Produksi")){
                group = temp.getKodeProduksi();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                group = temp.getProduksiHead().getKodeGudang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                group = temp.getKodeBarang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                group = tgl.format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
            }
            if(!groupBy.contains(group))
                groupBy.add(group);
        }
        for(String temp : groupBy){
            ProduksiDetailBarang head = new ProduksiDetailBarang();
            head.setKodeProduksi(temp);
            head.setProduksiHead(new ProduksiHead());
            head.getProduksiHead().setListProduksiDetailBahan(new ArrayList<>());
            head.setBarang(new Barang());
            TreeItem<ProduksiDetailBarang> parent = new TreeItem<>(head);
            double totalQty = 0;
            double totalNilai = 0;
            for(ProduksiDetailBarang detail: filterData){
                boolean status = false;
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Produksi") && temp.equals(detail.getKodeProduksi())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang") &&
                        temp.equals(detail.getProduksiHead().getKodeGudang())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal") &&
                        temp.equals(tgl.format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                        temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                        temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang") && temp.equals(detail.getKodeBarang())){
                    status = true;
                }
                if(status){
                    parent.getChildren().addAll(new TreeItem<>(detail));
                    totalQty = totalQty + detail.getQty();
                    totalNilai = totalNilai + detail.getNilai();
                }
            }
            parent.getValue().setQty(totalQty);
            parent.getValue().setNilai(totalNilai);
            root.getChildren().add(parent);
        }
        produksiBarangDetailTable.setRoot(root);
    }    
    private void hitungTotal(){
        double totalNilai=0;
        double totalQty = 0;
        for(ProduksiDetailBarang temp : filterData){
            totalNilai = totalNilai + temp.getNilai();
            totalQty = totalQty + temp.getQty();
        }
        totalNilaiField.setText(df.format(totalNilai));
        totalQtyField.setText(df.format(totalQty));
    }
    private void lihatDetailProduksi(ProduksiHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewProduksiBarang.fxml");
        NewProduksiBarangController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailProduksi(p.getKodeProduksi());
    }
    private void print(){
        try{
            allProduksi.sort(Comparator.comparing(ProduksiDetailBarang::getKodeBarang));
            Report report = new Report();
            report.printLaporanBarangDiproduksi(allProduksi, tglProduksiMulaiPicker.getValue().toString(),
                    tglProduksiAkhirPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem());
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
                
                Sheet sheet = workbook.createSheet("Laporan Barang Diproduksi");
                int rc = 0;
                int c = 11;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglProduksiMulaiPicker.getValue().toString()))+"-"+
                        tgl.format(tglBarang.parse(tglProduksiAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Group By : "+groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Produksi"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Produksi"); 
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Bahan"); 
                sheet.getRow(rc).getCell(4).setCellValue("Catatan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kode Barang"); 
                sheet.getRow(rc).getCell(6).setCellValue("Nama Barang"); 
                sheet.getRow(rc).getCell(7).setCellValue("Satuan"); 
                sheet.getRow(rc).getCell(8).setCellValue("Qty");
                sheet.getRow(rc).getCell(9).setCellValue("Nilai Pokok");
                sheet.getRow(rc).getCell(10).setCellValue("Nilai Satuan"); 
                rc++;
                List<String> groupBy = new ArrayList<>();
                for(ProduksiDetailBarang temp: filterData){
                    String group = "";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Produksi")){
                        group = temp.getKodeProduksi();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                        group = temp.getKodeBarang();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                        group = tgl.format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getProduksiHead().getTglProduksi()));
                    }
                    if(!groupBy.contains(group))
                        groupBy.add(group);
                }
                double grandtotalQty = 0;
                double grandtotalNilai = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalQty = 0;
                    double totalNilai = 0;
                    for(ProduksiDetailBarang detail: filterData){
                        boolean status = false;
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Produksi")&&
                                temp.equals(detail.getKodeProduksi())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                                temp.equals(tgl.format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                                temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                                temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getProduksiHead().getTglProduksi())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")&&
                                temp.equals(detail.getKodeBarang())){
                            status = true;
                        }
                        if(status){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getKodeProduksi());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getProduksiHead().getTglProduksi())));
                            sheet.getRow(rc).getCell(1).setCellValue(detail.getProduksiHead().getKodeGudang());
                            String listBahan = "";
                            for(ProduksiDetailBahan d : detail.getProduksiHead().getListProduksiDetailBahan()){
                                listBahan = listBahan + d.getKodeBarang();
                                if(detail.getProduksiHead().getListProduksiDetailBahan().indexOf(d) < detail.getProduksiHead().getListProduksiDetailBahan().size()-1)
                                    listBahan = listBahan + ", ";
                            }
                            sheet.getRow(rc).getCell(2).setCellValue(listBahan);
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getProduksiHead().getCatatan());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getKodeBarang());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getBarang().getNamaBarang());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getBarang().getSatuan());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getQty());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getNilai()/detail.getQty());
                            rc++;
                            totalQty = totalQty + detail.getQty();
                            totalNilai = totalNilai + detail.getNilai();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(7).setCellValue(totalQty);
                    sheet.getRow(rc).getCell(8).setCellValue(totalNilai);
                    rc++;
                    grandtotalQty = grandtotalQty + totalQty;
                    grandtotalNilai = grandtotalNilai + totalNilai;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalQty);
                sheet.getRow(rc).getCell(8).setCellValue(grandtotalNilai);
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
