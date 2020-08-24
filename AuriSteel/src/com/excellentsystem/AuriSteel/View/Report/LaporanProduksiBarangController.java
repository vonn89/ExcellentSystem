/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBahanDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiDetailBarangDAO;
import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBahan;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.NewProduksiBarangController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class LaporanProduksiBarangController  {

    @FXML private TableView<ProduksiHead> produksiHeadTable;
    @FXML private TableColumn<ProduksiHead, String> kodeProduksiColumn;
    @FXML private TableColumn<ProduksiHead, String> tglProduksiColumn;
    @FXML private TableColumn<ProduksiHead, String> gudangColumn;
    @FXML private TableColumn<ProduksiHead, String> listBahanColumn;
    @FXML private TableColumn<ProduksiHead, String> listBarangColumn;
    @FXML private TableColumn<ProduksiHead, Number> materialCostColumn;
    @FXML private TableColumn<ProduksiHead, String> catatanColumn;
    @FXML private TableColumn<ProduksiHead, String> kodeUserColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private DatePicker tglProduksiMulaiPicker;
    @FXML private DatePicker tglProduksiAkhirPicker;
    private ObservableList<ProduksiHead> allProduksi = FXCollections.observableArrayList();
    private ObservableList<ProduksiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        kodeProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeProduksiProperty());
        kodeProduksiColumn.setCellFactory(col -> Function.getWrapTableCell(kodeProduksiColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));
        
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        tglProduksiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglProduksi())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglProduksiColumn.setComparator(Function.sortDate(tglLengkap));
        tglProduksiColumn.setCellFactory(col -> Function.getWrapTableCell(tglProduksiColumn));
        
        listBahanColumn.setCellValueFactory(cellData -> {
            String x = "";
            for(ProduksiDetailBahan d : cellData.getValue().getListProduksiDetailBahan()){
                x = x + d.getKodeBarang();
                if(cellData.getValue().getListProduksiDetailBahan().indexOf(d)<
                        cellData.getValue().getListProduksiDetailBahan().size()-1)
                    x = x + ", ";
            }
            return new SimpleStringProperty(x);
        });
        listBahanColumn.setCellFactory(col -> Function.getWrapTableCell(listBahanColumn));
        
        listBarangColumn.setCellValueFactory(cellData -> {
            String x = "";
            for(ProduksiDetailBarang d : cellData.getValue().getListProduksiDetailBarang()){
                x = x + d.getKodeBarang();
                if(cellData.getValue().getListProduksiDetailBarang().indexOf(d)<
                        cellData.getValue().getListProduksiDetailBarang().size()-1)
                    x = x + ", ";
            }
            return new SimpleStringProperty(x);
        });
        listBarangColumn.setCellFactory(col -> Function.getWrapTableCell(listBarangColumn));
        
        materialCostColumn.setCellValueFactory(cellData -> cellData.getValue().materialCostProperty());
        materialCostColumn.setCellFactory(col -> Function.getTableCell());
        
        tglProduksiMulaiPicker.setConverter(Function.getTglConverter());
        tglProduksiMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglProduksiMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglProduksiAkhirPicker));
        tglProduksiAkhirPicker.setConverter(Function.getTglConverter());
        tglProduksiAkhirPicker.setValue(LocalDate.now());
        tglProduksiAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglProduksiMulaiPicker));
        
        allProduksi.addListener((ListChangeListener.Change<? extends ProduksiHead> change) -> {
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
        produksiHeadTable.setContextMenu(rm);
        produksiHeadTable.setRowFactory((TableView<ProduksiHead> tableView) -> {
            final TableRow<ProduksiHead> row = new TableRow<ProduksiHead>(){
                @Override
                public void updateItem(ProduksiHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Produksi");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailProduksi(item);
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
                            if(o.getJenis().equals("Detail Produksi")&&o.isStatus())
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
        ObservableList<String> kategori = FXCollections.observableArrayList();
        kategori.clear();
        kategori.add("Bahan - Bahan");
        kategori.add("Bahan - Barang");
        kategori.add("Barang - Barang");
        groupByCombo.setItems(kategori);
        groupByCombo.getSelectionModel().select("Bahan - Barang");
        getProduksi();
        produksiHeadTable.setItems(filterData);
    } 
    @FXML
    private void getProduksi(){
        Task<List<ProduksiHead>> task = new Task<List<ProduksiHead>>() {
            @Override 
            public List<ProduksiHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<ProduksiHead> listProduksi = ProduksiHeadDAO.getAllByDateAndJenisProduksiAndStatus(
                            con, tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(), 
                            groupByCombo.getSelectionModel().getSelectedItem(), "true");
                    List<ProduksiDetailBahan> listProduksiBahan = ProduksiDetailBahanDAO.getAllByDateAndJenisProduksiAndStatus(
                            con, tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(),  
                            groupByCombo.getSelectionModel().getSelectedItem(),"true");
                    List<ProduksiDetailBarang> listProduksiBarang = ProduksiDetailBarangDAO.getAllByDateAndJenisProduksiAndStatus(
                            con, tglProduksiMulaiPicker.getValue().toString(), tglProduksiAkhirPicker.getValue().toString(), 
                            groupByCombo.getSelectionModel().getSelectedItem(),"true");
                    for(ProduksiHead p : listProduksi){
                        System.out.println(p.getKodeProduksi());
                        List<ProduksiDetailBahan> listBahan = new ArrayList<>();
                        for(ProduksiDetailBahan b : listProduksiBahan){
                            if(p.getKodeProduksi().equals(b.getKodeProduksi()))
                                listBahan.add(b);
                        }
                        p.setListProduksiDetailBahan(listBahan);
                        List<ProduksiDetailBarang> listBarang = new ArrayList<>();
                        for(ProduksiDetailBarang b : listProduksiBarang){
                            if(p.getKodeProduksi().equals(b.getKodeProduksi()))
                                listBarang.add(b);
                        }
                        p.setListProduksiDetailBarang(listBarang);
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
            for (ProduksiHead temp : allProduksi) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodeProduksi())||
                        checkColumn(df.format(temp.getMaterialCost()))||
                        checkColumn(temp.getKodeGudang())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglProduksi())))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                    else{
                        boolean status = false;
                        for(ProduksiDetailBahan d : temp.getListProduksiDetailBahan()){
                            if(checkColumn(d.getKodeBarang()))
                                status = true;
                        }
                        for(ProduksiDetailBarang d : temp.getListProduksiDetailBarang()){
                            if(checkColumn(d.getKodeBarang()))
                                status = true;
                        }
                        if(status)
                            filterData.add(temp);
                    }
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
            Report report = new Report();
            report.printLaporanProduksi(allProduksi, tglProduksiMulaiPicker.getValue().toString(),
                    tglProduksiAkhirPicker.getValue().toString());
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
                
                Sheet sheet = workbook.createSheet("Laporan Produksi Barang");
                int rc = 0;
                int c = 8;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglProduksiMulaiPicker.getValue().toString()))+"-"+
                        tgl.format(tglBarang.parse(tglProduksiAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Kode Produksi"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Produksi"); 
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Bahan"); 
                sheet.getRow(rc).getCell(4).setCellValue("Barang"); 
                sheet.getRow(rc).getCell(5).setCellValue("Material Cost");
                sheet.getRow(rc).getCell(6).setCellValue("Catatan"); 
                sheet.getRow(rc).getCell(7).setCellValue("Kode User"); 
                rc++;
                for(ProduksiHead p: filterData){
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(p.getKodeProduksi());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(p.getTglProduksi())));
                    sheet.getRow(rc).getCell(2).setCellValue(p.getKodeProduksi());
                    String listBahan = "";
                    for(ProduksiDetailBahan d : p.getListProduksiDetailBahan()){
                        listBahan = listBahan + d.getKodeBarang();
                        if(p.getListProduksiDetailBahan().indexOf(d)<p.getListProduksiDetailBahan().size()-1)
                            listBahan = listBahan + ", ";
                    }
                    String listBarang = "";
                    for(ProduksiDetailBarang d : p.getListProduksiDetailBarang()){
                        listBarang = listBarang + d.getKodeBarang();
                        if(p.getListProduksiDetailBarang().indexOf(d)<p.getListProduksiDetailBarang().size()-1)
                            listBarang = listBarang + ", ";
                    }
                    sheet.getRow(rc).getCell(3).setCellValue(listBahan);
                    sheet.getRow(rc).getCell(4).setCellValue(listBarang);
                    sheet.getRow(rc).getCell(5).setCellValue(p.getMaterialCost());
                    sheet.getRow(rc).getCell(6).setCellValue(p.getCatatan());
                    sheet.getRow(rc).getCell(7).setCellValue(p.getKodeUser());
                    rc++;
                }
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
