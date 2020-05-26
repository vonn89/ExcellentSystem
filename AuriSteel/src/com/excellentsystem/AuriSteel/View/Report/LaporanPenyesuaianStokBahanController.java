/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;


import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.PenyesuaianStokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import static com.excellentsystem.AuriSteel.Function.getTableCell;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBahan;
import com.excellentsystem.AuriSteel.View.Dialog.PenyesuaianStokController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
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
public class LaporanPenyesuaianStokBahanController {
    @FXML private TableView<PenyesuaianStokBahan> penyesuaianStokTable;
    @FXML private TableColumn<PenyesuaianStokBahan, String> noPenyesuaianColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> tglPenyesuaianColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> kodeBahanColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> kodeGudangColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> namaBahanColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> statusColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, Number> qtyColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> catatanColumn;
    @FXML private TableColumn<PenyesuaianStokBahan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private DatePicker tglMulaiPenyesuaianPicker;
    @FXML private DatePicker tglAkhirPenyesuaianPicker;
    private ObservableList<PenyesuaianStokBahan> allPenyesuaianStok = FXCollections.observableArrayList();
    private ObservableList<PenyesuaianStokBahan> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    public void initialize() {
        noPenyesuaianColumn.setCellValueFactory(cellData -> cellData.getValue().noPenyesuaianProperty());
        tglPenyesuaianColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenyesuaian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenyesuaianColumn.setComparator(Function.sortDate(tglLengkap));
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getBahan().namaBahanProperty());
        statusColumn.setCellValueFactory(cellData -> {
            double qty = cellData.getValue().getQty();
            if(qty<0)
                return new SimpleStringProperty("Pengurangan Stok");
            else
                return new SimpleStringProperty("Penambahan Stok");
        });
        qtyColumn.setCellValueFactory(cellData -> {
            double qty = cellData.getValue().getQty();
            if(qty<0)
                qty = qty * -1;
            return new SimpleDoubleProperty(qty);
        });
        qtyColumn.setCellFactory((c) -> getTableCell());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
        tglMulaiPenyesuaianPicker.setConverter(Function.getTglConverter());
        tglMulaiPenyesuaianPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPenyesuaianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPenyesuaianPicker));
        tglAkhirPenyesuaianPicker.setConverter(Function.getTglConverter());
        tglAkhirPenyesuaianPicker.setValue(LocalDate.now());
        tglAkhirPenyesuaianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPenyesuaianPicker));
        allPenyesuaianStok.addListener((ListChangeListener.Change<? extends PenyesuaianStokBahan> change) -> {
            searchPenyesuaianStok();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenyesuaianStok();
        });
        filterData.addAll(allPenyesuaianStok);
        penyesuaianStokTable.setItems(filterData);
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
        refresh.setOnAction((ActionEvent event) -> {
            getPenyesuaianStok();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
//            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
//                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        penyesuaianStokTable.setContextMenu(rm);
        penyesuaianStokTable.setRowFactory(ttv -> {
            TableRow<PenyesuaianStokBahan> row = new TableRow<PenyesuaianStokBahan>() {
                @Override
                public void updateItem(PenyesuaianStokBahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        ContextMenu rm = new ContextMenu();
                        MenuItem lihat = new MenuItem("Lihat Detail Penyesuaian Stok");
                        lihat.setOnAction((ActionEvent event) -> {
                            showDetailPenyesuaianStok(item);
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
                        refresh.setOnAction((ActionEvent event) -> {
                            getPenyesuaianStok();
                        });
                        rm.getItems().addAll(lihat);
                        for(Otoritas o : sistem.getUser().getOtoritas()){
//                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
//                                rm.getItems().addAll(print);
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
                        showDetailPenyesuaianStok(row.getItem());
                }
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenyesuaianStok();
    }
    @FXML
    private void getPenyesuaianStok() {
        Task<List<PenyesuaianStokBahan>> task = new Task<List<PenyesuaianStokBahan>>() {
            @Override 
            public List<PenyesuaianStokBahan> call()throws Exception{ 
                try(Connection con = Koneksi.getConnection()){
                    List<Bahan> listBahan = BahanDAO.getAllByStatus(con,"%");
                    List<PenyesuaianStokBahan> listHead = PenyesuaianStokBahanDAO.getAllByDateAndBahanAndGudang(con,
                            tglMulaiPenyesuaianPicker.getValue().toString(), tglAkhirPenyesuaianPicker.getValue().toString(),"%", "%");
                    for (PenyesuaianStokBahan d : listHead) {
                        for(Bahan b : listBahan){
                            if(d.getKodeBahan().equals(b.getKodeBahan()))
                                d.setBahan(b);
                        }
                    }
                    return listHead;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPenyesuaianStok.clear();
            allPenyesuaianStok.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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
    private void searchPenyesuaianStok() {
        try {
            filterData.clear();
            for (PenyesuaianStokBahan temp : allPenyesuaianStok) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenyesuaian())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenyesuaian())))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getKodeUser())
                            || checkColumn(temp.getKodeBahan())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getBahan().getNamaBahan())
                            || checkColumn(temp.getBahan().getKodeKategori())
                            || checkColumn(temp.getBahan().getSpesifikasi())
                            || checkColumn(df.format(temp.getQty()))) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void showDetailPenyesuaianStok(PenyesuaianStokBahan p ){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child ,"View/Dialog/Child/PenyesuaianStok.fxml");
        PenyesuaianStokController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setPenyesuaianStokBahan(p.getNoPenyesuaian());
    }
    private void hitungTotal() {
        double totalQty = 0;
        for (PenyesuaianStokBahan temp : filterData) {
            totalQty = totalQty + temp.getQty();
        }
        totalQtyField.setText(df.format(totalQty));
    }
    private void print(){
        try{
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void exportExcel() {
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
                Sheet sheet = workbook.createSheet("Laporan Penyesuaian Stok Bahan");
                int rc = 0;
                int c = 8;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tgl Penyesuaian Stok : "
                        +tgl.format(tglBarang.parse(tglMulaiPenyesuaianPicker.getValue().toString()))+" - "
                        +tgl.format(tglBarang.parse(tglAkhirPenyesuaianPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penyesuaian"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penyesuaian"); 
                sheet.getRow(rc).getCell(2).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Nama Bahan"); 
                sheet.getRow(rc).getCell(4).setCellValue("Status"); 
                sheet.getRow(rc).getCell(5).setCellValue("Qty"); 
                sheet.getRow(rc).getCell(6).setCellValue("Catatan"); 
                sheet.getRow(rc).getCell(7).setCellValue("Kode User"); 
                rc++;
                double totalPenambahan = 0;
                double totalPengurangan = 0;
                for (PenyesuaianStokBahan s : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(s.getNoPenyesuaian());
                    sheet.getRow(rc).getCell(1).setCellValue(s.getTglPenyesuaian());
                    sheet.getRow(rc).getCell(2).setCellValue(s.getKodeBahan());
                    sheet.getRow(rc).getCell(3).setCellValue(s.getBahan().getNamaBahan());
                    if(s.getQty()<0){
                        sheet.getRow(rc).getCell(4).setCellValue("Pengurangan Stok");
                        sheet.getRow(rc).getCell(5).setCellValue(s.getQty()*-1);
                        totalPengurangan = totalPengurangan + (s.getQty()*-1);
                    }else{
                        sheet.getRow(rc).getCell(4).setCellValue("Penambahan Stok");
                        sheet.getRow(rc).getCell(5).setCellValue(s.getQty());
                        totalPenambahan = totalPenambahan + s.getQty();
                    }
                    sheet.getRow(rc).getCell(6).setCellValue(s.getCatatan());
                    sheet.getRow(rc).getCell(7).setCellValue(s.getKodeUser());
                    rc++;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(1).setCellValue("Total Penambahan Stok");
                sheet.getRow(rc).getCell(5).setCellValue(totalPenambahan);
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(1).setCellValue("Total Pengurangan Stok");
                sheet.getRow(rc).getCell(5).setCellValue(totalPengurangan);
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
