/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PermintaanBarangController  {

    @FXML private TableView<PemesananBarangDetail> permintaanTable;
    @FXML private TableColumn<PemesananBarangDetail,Boolean> checkColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> noPemesananColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> tglPemesananColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> namaCustomerColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> alamatCustomerColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> keteranganColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> catatanInternColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> satuanColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> qtyColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> qtyTerkirimColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> qtySisaColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> tonaseColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> salesColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalTonaseLabel;
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> groupByCombo;
    private ObservableList<PemesananBarangDetail> allPermintaan = FXCollections.observableArrayList();
    private ObservableList<PemesananBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));
        
        tglPemesananColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPemesananBarangHead().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBarangColumn));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTableCell(namaBarangColumn));
        
        salesColumn.setCellValueFactory(cellData -> cellData.getValue().getPemesananBarangHead().getSales().namaProperty());
        salesColumn.setCellFactory(col -> Function.getWrapTableCell(salesColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanInternProperty());
        catatanInternColumn.setCellFactory(col -> Function.getWrapTableCell(catatanInternColumn));
        
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTableCell(satuanColumn));
        
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        
        qtyTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().qtyTerkirimProperty());
        qtyTerkirimColumn.setCellFactory(col -> Function.getTableCell());
        
        qtySisaColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty()-cellData.getValue().getQtyTerkirim());
        });
        qtySisaColumn.setCellFactory(col -> Function.getTableCell());
        
        tonaseColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty((cellData.getValue().getQty()-cellData.getValue().getQtyTerkirim())*cellData.getValue().getBarang().getBerat());
        });
        tonaseColumn.setCellFactory(col -> Function.getTableCell());
        
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return permintaanTable.getItems().get(v).statusProperty();
        }));
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusYears(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem spk = new MenuItem("Print SPK");
        spk.setOnAction((ActionEvent e)->{
            printSPK();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPermintaan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print SPK")&&o.isStatus())
                rm.getItems().add(spk);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        permintaanTable.setContextMenu(rm);
        permintaanTable.setRowFactory((TableView<PemesananBarangDetail> tableView) -> {
            final TableRow<PemesananBarangDetail> row = new TableRow<PemesananBarangDetail>(){};
            row.itemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue!=null){
                    double hutang = newValue.getPemesananBarangHead().getCustomer().getHutang();
                    double limitHutang = newValue.getPemesananBarangHead().getCustomer().getLimitHutang();
                    double sisaPemesanan = 0;
                    double downpayment = newValue.getPemesananBarangHead().getSisaDownPayment();
                    for(PemesananBarangDetail d : newValue.getPemesananBarangHead().getListPemesananBarangDetail()){
                        sisaPemesanan = sisaPemesanan + ((d.getQty()-d.getQtyTerkirim()) * d.getHargaJual());
                    }
                    if (limitHutang-hutang-sisaPemesanan+downpayment<0)
                        row.setStyle("-fx-background-color: #FFD8D1");//red
                    else
                        row.setStyle("");
                }else
                    row.setStyle("");
            });
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().isStatus())
                            row.getItem().setStatus(false);
                        else
                            row.getItem().setStatus(true);
                        hitungTotal();
                    }
                }
            });
            return row;
        });
        allPermintaan.addListener((ListChangeListener.Change<? extends PemesananBarangDetail> change) -> {
            searchPermintaan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPermintaan();
        });
        filterData.addAll(allPermintaan);
        permintaanTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Wait");
        groupBy.add("Done");
        groupBy.add("Semua");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Wait");
        getPermintaan();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        for(PemesananBarangDetail d : filterData){
            if(d.isStatus()){
                qty = qty + d.getQty()-d.getQtyTerkirim();
                berat = berat + (d.getQty()-d.getQtyTerkirim())*d.getBarang().getBerat();
            }
        }
        totalQtyLabel.setText(df.format(qty));
        totalTonaseLabel.setText(df.format(berat));
    }
    @FXML
    private void getPermintaan(){
        Task<List<PemesananBarangDetail>> task = new Task<List<PemesananBarangDetail>>() {
            @Override 
            public List<PemesananBarangDetail> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allPegawai = PegawaiDAO.getAllByStatus(con, "%");
                    List<Barang> allBarang = BarangDAO.getAllByStatus(con, "%");
                    List<PemesananBarangDetail> allPemesananDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "open");
                    List<PemesananBarangHead> allPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "open");
                    for(PemesananBarangHead p : allPemesanan){
                        List<PemesananBarangDetail> detail = new ArrayList<>();
                        for(PemesananBarangDetail d : allPemesananDetail){
                            if(d.getNoPemesanan().equals(p.getNoPemesanan()))
                                detail.add(d);
                        }
                        p.setListPemesananBarangDetail(detail);
                    }
                    List<PemesananBarangDetail> listPemesananDetail = new ArrayList<>();
                    for(PemesananBarangDetail d : allPemesananDetail){
                        for(PemesananBarangHead h : allPemesanan){
                            if(d.getNoPemesanan().equals(h.getNoPemesanan()))
                                d.setPemesananBarangHead(h);
                        }
                        for(Customer c: allCustomer){
                            if(d.getPemesananBarangHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPemesananBarangHead().setCustomer(c);
                        }
                        for(Pegawai p : allPegawai){
                            if(d.getPemesananBarangHead().getKodeSales().equals(p.getKodePegawai()))
                                d.getPemesananBarangHead().setSales(p);
                        }
                        for(Barang b : allBarang){
                            if(d.getKodeBarang().equals(b.getKodeBarang()))
                                d.setBarang(b);
                        }
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("Done")&&d.getQty()-d.getQtyTerkirim()==0)
                            listPemesananDetail.add(d);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")&&d.getQty()-d.getQtyTerkirim()!=0)
                            listPemesananDetail.add(d);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            listPemesananDetail.add(d);
                    }
                    return listPemesananDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPermintaan.clear();
            allPermintaan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            task.getException().printStackTrace();
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAllHandle(){
        for(PemesananBarangDetail d: allPermintaan){
            d.setStatus(checkAll.isSelected());
        }
        hitungTotal();
        permintaanTable.refresh();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPermintaan() {
        try{
            filterData.clear();
            for (PemesananBarangDetail temp : allPermintaan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPemesanan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPemesananBarangHead().getTglPemesanan())))||
                        checkColumn(temp.getPemesananBarangHead().getKodeCustomer())||
                        checkColumn(temp.getPemesananBarangHead().getCustomer().getNama())||
                        checkColumn(temp.getPemesananBarangHead().getSales().getNama())||
                        checkColumn(temp.getPemesananBarangHead().getCustomer().getAlamat())||
                        checkColumn(temp.getPemesananBarangHead().getPaymentTerm())||
                        checkColumn(df.format(temp.getPemesananBarangHead().getTotalPemesanan()))||
                        checkColumn(df.format(temp.getPemesananBarangHead().getDownPayment()))||
                        checkColumn(temp.getPemesananBarangHead().getCatatan())||
                        checkColumn(temp.getPemesananBarangHead().getKodeSales())||
                        checkColumn(temp.getPemesananBarangHead().getStatus())||
                        checkColumn(temp.getPemesananBarangHead().getKodeUser())||
                        checkColumn(temp.getKodeBarang())||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(temp.getCatatanIntern())||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(df.format(temp.getQtyTerkirim()))||
                        checkColumn(temp.getSatuan()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void printSPK(){
        try{
            List<PemesananBarangDetail> spk = new ArrayList<>();
            for(PemesananBarangDetail b : filterData){
                if(b.isStatus())
                    spk.add(b);
            }
            if(spk.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Data permintaan barang belum di pilih");
            }else{
                Report report = new Report();
                report.printSPK(spk);
            }
        }catch(Exception e){
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
                Sheet sheet = workbook.createSheet("Data Permintaan Barang");
                int rc = 0;
                int c = 13;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString()))+"-"+
                        tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Status : "+groupByCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pemesanan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pemesanan");  
                sheet.getRow(rc).getCell(2).setCellValue("Customer"); 
                sheet.getRow(rc).getCell(3).setCellValue("Kode Barang"); 
                sheet.getRow(rc).getCell(4).setCellValue("Nama Barang"); 
                sheet.getRow(rc).getCell(5).setCellValue("Satuan"); 
                sheet.getRow(rc).getCell(6).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(7).setCellValue("Catatan Intern"); 
                sheet.getRow(rc).getCell(8).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(9).setCellValue("Qty Order"); 
                sheet.getRow(rc).getCell(10).setCellValue("Qty Terkirim"); 
                sheet.getRow(rc).getCell(11).setCellValue("Qty Sisa"); 
                sheet.getRow(rc).getCell(12).setCellValue("Tonase"); 
                rc++;
                double qty = 0;
                double qtyTerkirim = 0;
                double berat = 0;
                for (PemesananBarangDetail b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getPemesananBarangHead().getTglPemesanan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getPemesananBarangHead().getCustomer().getNama());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKodeBarang());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getNamaBarang());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getSatuan());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getKeterangan());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getKeterangan());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getPemesananBarangHead().getSales().getNama());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getQty());
                    sheet.getRow(rc).getCell(10).setCellValue(b.getQtyTerkirim());
                    sheet.getRow(rc).getCell(11).setCellValue(b.getQty()-b.getQtyTerkirim());
                    sheet.getRow(rc).getCell(12).setCellValue((b.getQty()-b.getQtyTerkirim())*b.getBarang().getBerat());
                    rc++;
                    qty = qty + b.getQty();
                    qtyTerkirim = qtyTerkirim + b.getQtyTerkirim();
                    berat = berat + (b.getQty()-b.getQtyTerkirim())*b.getBarang().getBerat();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(9).setCellValue(qty);
                sheet.getRow(rc).getCell(10).setCellValue(qtyTerkirim);
                sheet.getRow(rc).getCell(11).setCellValue(qty-qtyTerkirim);
                sheet.getRow(rc).getCell(12).setCellValue(berat);
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
