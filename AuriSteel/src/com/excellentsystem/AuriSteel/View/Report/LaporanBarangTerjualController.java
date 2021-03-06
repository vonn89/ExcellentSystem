/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
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
public class LaporanBarangTerjualController  {

    @FXML private TreeTableView<PenjualanBarangDetail> penjualanDetailTable;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> gudangColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> namaSalesColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> kodeBarangColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> namaBarangColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, String> satuanColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> qtyColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> nilaiColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> totalNilaiColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> hargaJualColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> totalColumn;
    @FXML private TreeTableColumn<PenjualanBarangDetail, Number> persentaseColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    @FXML private Label totalPenjualanField;
    @FXML private DatePicker tglPenjualanMulaiPicker;
    @FXML private DatePicker tglPenjualanAkhirPicker;
    
    final TreeItem<PenjualanBarangDetail> root = new TreeItem<>();
    private ObservableList<PenjualanBarangDetail> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanBarangDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        noPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPenjualanColumn));
        
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPenjualanBarangHead().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPenjualanColumn));
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanBarangHead().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(gudangColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanBarangHead().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaCustomerColumn));
        
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanBarangHead().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSalesColumn));
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBarangProperty());
        kodeBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBarangColumn));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBarangProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBarangColumn));
        
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(satuanColumn));
        
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        nilaiColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiProperty());
        nilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        totalNilaiColumn.setCellValueFactory(cellData -> 
                new SimpleDoubleProperty(cellData.getValue().getValue().getNilai()*cellData.getValue().getValue().getQty()));
        totalNilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalProperty());
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        persentaseColumn.setCellValueFactory(cellData -> {
            double untung = cellData.getValue().getValue().getTotal() - (cellData.getValue().getValue().getNilai()*cellData.getValue().getValue().getQty());
            double persentase = untung / cellData.getValue().getValue().getTotal()*100;
            return new SimpleDoubleProperty(persentase);
        });
        persentaseColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tglPenjualanMulaiPicker.setConverter(Function.getTglConverter());
        tglPenjualanMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPenjualanMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPenjualanAkhirPicker));
        tglPenjualanAkhirPicker.setConverter(Function.getTglConverter());
        tglPenjualanAkhirPicker.setValue(LocalDate.now());
        tglPenjualanAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPenjualanMulaiPicker));
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanBarangDetail> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
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
            getPenjualan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
        rm.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(rm);
        penjualanDetailTable.setRowFactory((TreeTableView<PenjualanBarangDetail> tableView) -> {
            final TreeTableRow<PenjualanBarangDetail> row = new TreeTableRow<PenjualanBarangDetail>(){
                @Override
                public void updateItem(PenjualanBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item.getPenjualanBarangHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item.getPenjualanBarangHead());
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
                            getPenjualan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Penjualan")&&o.isStatus()
                                    &&item.getPenjualanBarangHead().getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan")&&o.isStatus()
                                    &&item.getPenjualanBarangHead().getPembayaran()>0
                                    &&item.getPenjualanBarangHead().getStatus()!=null)
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
        groupBy.add("No Penjualan");
        groupBy.add("Gudang");
        groupBy.add("Customer");
        groupBy.add("Sales");
        groupBy.add("Barang");
        groupBy.add("Tanggal");
        groupBy.add("Bulan");
        groupBy.add("Tahun");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("No Penjualan");
        getPenjualan();
    } 
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanBarangDetail>> task = new Task<List<PenjualanBarangDetail>>() {
            @Override 
            public List<PenjualanBarangDetail> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanBarangHead> penjualan = PenjualanBarangHeadDAO.getAllByDateAndStatus(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString(),"true");
                    List<PenjualanBarangDetail> temp = PenjualanBarangDetailDAO.getAllByDateAndStatus(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString(),"true");
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> sales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PenjualanBarangDetail d : temp){
                        for(PenjualanBarangHead h:penjualan){
                            if(d.getNoPenjualan().equals(h.getNoPenjualan()))
                                d.setPenjualanBarangHead(h);
                        }
                        for(Customer c : customer){
                            if(d.getPenjualanBarangHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPenjualanBarangHead().setCustomer(c);
                        }
                        for(Pegawai s : sales){
                            if(d.getPenjualanBarangHead().getKodeSales().equals(s.getKodePegawai()))
                                d.getPenjualanBarangHead().setSales(s);
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
            allPenjualan.clear();
            allPenjualan.addAll(task.getValue());
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
    private void searchPenjualan(){
        try{
            filterData.clear();
            for (PenjualanBarangDetail temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan())))||
                        checkColumn(temp.getPenjualanBarangHead().getKodeGudang())||
                        checkColumn(temp.getPenjualanBarangHead().getKodeCustomer())||
                        checkColumn(temp.getPenjualanBarangHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanBarangHead().getPaymentTerm())||
                        checkColumn(temp.getPenjualanBarangHead().getCatatan())||
                        checkColumn(temp.getPenjualanBarangHead().getKodeSales())||
                        checkColumn(temp.getPenjualanBarangHead().getSales().getNama())||
                        checkColumn(temp.getKodeBarang())||
                        checkColumn(df.format(temp.getHargaJual()))||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(temp.getSatuan())||
                        checkColumn(df.format(temp.getNilai()))||
                        checkColumn(df.format(temp.getTotal()))||
                        checkColumn(df.format(temp.getNilai()*temp.getQty())))
                        filterData.add(temp);
                }
            }
            setTable();
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    private void hitungTotal(){
        double totalNilai=0;
        double totalQty = 0;
        double totalJual = 0;
        for(PenjualanBarangDetail temp : filterData){
            totalNilai = totalNilai + (temp.getNilai()*temp.getQty());
            totalQty = totalQty + temp.getQty();
            totalJual = totalJual + temp.getTotal();
        }
        totalNilaiField.setText(df.format(totalNilai));
        totalQtyField.setText(df.format(totalQty));
        totalPenjualanField.setText(df.format(totalJual));
    }
    private void setTable()throws Exception{
        if(penjualanDetailTable.getRoot()!=null)
            penjualanDetailTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PenjualanBarangDetail temp: filterData){
            String group = "";
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")){
                group = temp.getNoPenjualan();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                group = temp.getPenjualanBarangHead().getKodeGudang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")){
                group = temp.getPenjualanBarangHead().getSales().getNama();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")){
                group = temp.getPenjualanBarangHead().getCustomer().getNama();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                group = temp.getKodeBarang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                group = tgl.format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
            }
            if(!groupBy.contains(group))
                groupBy.add(group);
        }
        for(String temp : groupBy){
            PenjualanBarangDetail head = new PenjualanBarangDetail();
            head.setNoPenjualan(temp);
            head.setPenjualanBarangHead(new PenjualanBarangHead());
            head.getPenjualanBarangHead().setCustomer(new Customer());
            head.getPenjualanBarangHead().setSales(new Pegawai());
            TreeItem<PenjualanBarangDetail> parent = new TreeItem<>(head);
            double totalQty = 0;
            double totalNilai = 0;
            double totalHarga = 0;
            for(PenjualanBarangDetail detail: filterData){
                boolean status = false;
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan") && temp.equals(detail.getNoPenjualan())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&&
                        temp.equals(detail.getPenjualanBarangHead().getKodeGudang())){
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                        temp.equals(tgl.format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                        temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                        temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales") && temp.equals(detail.getPenjualanBarangHead().getSales().getNama())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer") && temp.equals(detail.getPenjualanBarangHead().getCustomer().getNama())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang") && temp.equals(detail.getKodeBarang())){
                    status = true;
                }
                if(status){
                    totalQty = totalQty + detail.getQty();
                    totalNilai = totalNilai + (detail.getNilai()*detail.getQty());
                    totalHarga = totalHarga + detail.getTotal();
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setQty(totalQty);
            parent.getValue().setNilai(totalNilai/totalQty);
            parent.getValue().setHargaJual(totalHarga/totalQty);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        penjualanDetailTable.setRoot(root);
    }   
    private void lihatDetailPenjualan(PenjualanBarangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }
    private void showDetailPiutang(PenjualanBarangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPenjualan(p);
    }
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanBarangTerjual(allPenjualan, tglPenjualanMulaiPicker.getValue().toString(),
                    tglPenjualanAkhirPicker.getValue().toString(),groupByCombo.getSelectionModel().getSelectedItem());
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
                
                Sheet sheet = workbook.createSheet("Laporan Barang Terjual");
                int rc = 0;
                int c = 13;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglPenjualanMulaiPicker.getValue().toString()))+"-"+
                        tgl.format(tglBarang.parse(tglPenjualanAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penjualan"); 
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Customer"); 
                sheet.getRow(rc).getCell(4).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kode Barang"); 
                sheet.getRow(rc).getCell(6).setCellValue("Nama Barang"); 
                sheet.getRow(rc).getCell(7).setCellValue("Satuan"); 
                sheet.getRow(rc).getCell(8).setCellValue("Qty");
                sheet.getRow(rc).getCell(9).setCellValue("Nilai");
                sheet.getRow(rc).getCell(10).setCellValue("Total Nilai");
                sheet.getRow(rc).getCell(11).setCellValue("Harga"); 
                sheet.getRow(rc).getCell(12).setCellValue("Total Harga"); 
                rc++;
                List<String> groupBy = new ArrayList<>();
                for(PenjualanBarangDetail temp: filterData){
                    String group = "";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")){
                        group = temp.getNoPenjualan();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                        group = temp.getPenjualanBarangHead().getKodeGudang();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")){
                        group = temp.getPenjualanBarangHead().getSales().getNama();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")){
                        group = temp.getPenjualanBarangHead().getCustomer().getNama();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                        group = temp.getNamaBarang();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                        group = tgl.format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPenjualanBarangHead().getTglPenjualan()));
                    }
                    if(!groupBy.contains(group))
                        groupBy.add(group);
                }
                double grandtotalQty = 0;
                double grandtotalNilai = 0;
                double grandtotalHarga = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalQty = 0;
                    double totalNilai = 0;
                    double totalHarga = 0;
                    for(PenjualanBarangDetail detail: filterData){
                        boolean status = false;
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")&&
                                temp.equals(detail.getNoPenjualan())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&&
                                temp.equals(detail.getPenjualanBarangHead().getKodeGudang())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                                temp.equals(tgl.format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                                temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                                temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")&&
                                temp.equals(detail.getPenjualanBarangHead().getSales().getNama())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")&&
                                temp.equals(detail.getPenjualanBarangHead().getCustomer().getNama())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")&&
                                temp.equals(detail.getNamaBarang())){
                            status = true;
                        }
                        if(status){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getPenjualanBarangHead().getNoPenjualan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPenjualanBarangHead().getTglPenjualan())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPenjualanBarangHead().getKodeGudang());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPenjualanBarangHead().getCustomer().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getPenjualanBarangHead().getSales().getNama());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getKodeBarang());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getNamaBarang());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getSatuan());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getQty());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getNilai()*detail.getQty());
                            sheet.getRow(rc).getCell(11).setCellValue(detail.getHargaJual());
                            sheet.getRow(rc).getCell(12).setCellValue(detail.getTotal());
                            rc++;
                            
                            totalQty = totalQty + detail.getQty();
                            totalNilai = totalNilai + (detail.getNilai()*detail.getQty());
                            totalHarga = totalHarga + detail.getTotal();
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(8).setCellValue(totalQty);
                    sheet.getRow(rc).getCell(9).setCellValue(totalNilai/totalQty);
                    sheet.getRow(rc).getCell(10).setCellValue(totalNilai);
                    sheet.getRow(rc).getCell(11).setCellValue(totalHarga/totalQty);
                    sheet.getRow(rc).getCell(12).setCellValue(totalHarga);
                    rc++;
                    grandtotalQty = grandtotalQty + totalQty;
                    grandtotalNilai = grandtotalNilai + totalNilai;
                    grandtotalHarga = grandtotalHarga + totalHarga;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(8).setCellValue(grandtotalQty);
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalNilai/grandtotalQty);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalNilai);
                sheet.getRow(rc).getCell(11).setCellValue(grandtotalHarga/grandtotalQty);
                sheet.getRow(rc).getCell(12).setCellValue(grandtotalHarga);
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
