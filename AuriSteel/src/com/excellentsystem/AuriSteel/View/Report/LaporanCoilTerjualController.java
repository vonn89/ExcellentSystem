/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
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
public class LaporanCoilTerjualController  {

    @FXML private TreeTableView<PenjualanCoilDetail> penjualanDetailTable;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaSalesColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> kodeBahanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, String> namaBahanColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> beratKotorColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> beratBersihColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> panjangColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> nilaiColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> totalNilaiColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> hargaJualColumn;
    @FXML private TreeTableColumn<PenjualanCoilDetail, Number> totalColumn;
    
    @FXML private ComboBox<String> groupByCombo;
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    @FXML private Label totalPenjualanField;
    @FXML private DatePicker tglPenjualanMulaiPicker;
    @FXML private DatePicker tglPenjualanAkhirPicker;
    
    final TreeItem<PenjualanCoilDetail> root = new TreeItem<>();
    private ObservableList<PenjualanCoilDetail> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanCoilDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        noPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(noPenjualanColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanCoilHead().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaCustomerColumn));
        
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getPenjualanCoilHead().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaSalesColumn));
        
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBahanColumn));
        
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBahanColumn));
        
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getPenjualanCoilHead().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(tglPenjualanColumn));
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        nilaiColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().nilaiProperty());
        nilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        totalNilaiColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue().getNilai()*
                cellData.getValue().getValue().getBeratBersih()));
        totalNilaiColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        hargaJualColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue().getValue().getHargaJual()*cellData.getValue().getValue().getPenjualanCoilHead().getKurs()));
        hargaJualColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(
                cellData.getValue().getValue().getTotal()*cellData.getValue().getValue().getPenjualanCoilHead().getKurs()));
        totalColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        tglPenjualanMulaiPicker.setConverter(Function.getTglConverter());
        tglPenjualanMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPenjualanMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPenjualanAkhirPicker));
        tglPenjualanAkhirPicker.setConverter(Function.getTglConverter());
        tglPenjualanAkhirPicker.setValue(LocalDate.now());
        tglPenjualanAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPenjualanMulaiPicker));
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanCoilDetail> change) -> {
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
        penjualanDetailTable.setRowFactory((TreeTableView<PenjualanCoilDetail> tableView) -> {
            final TreeTableRow<PenjualanCoilDetail> row = new TreeTableRow<PenjualanCoilDetail>(){
                @Override
                public void updateItem(PenjualanCoilDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item.getPenjualanCoilHead());
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan Coil");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item.getPenjualanCoilHead());
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
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus()
                                    &&item.getPenjualanCoilHead().getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan Coil")&&o.isStatus()
                                    &&item.getPenjualanCoilHead().getPembayaran()>0
                                    &&item.getPenjualanCoilHead().getStatus()!=null)
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
        Task<List<PenjualanCoilDetail>> task = new Task<List<PenjualanCoilDetail>>() {
            @Override 
            public List<PenjualanCoilDetail> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanCoilHead> penjualan = PenjualanCoilHeadDAO.getAllByDateAndStatus(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString(),"true");
                    List<PenjualanCoilDetail> temp = PenjualanCoilDetailDAO.getAllByDateAndStatus(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString(),"true");
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> sales = PegawaiDAO.getAllByStatus(con, "%");
                    for(PenjualanCoilDetail d : temp){
                        for(PenjualanCoilHead h : penjualan){
                            if(d.getNoPenjualan().equals(h.getNoPenjualan()))
                                d.setPenjualanCoilHead(h);
                        }
                        for(Customer c : customer){
                            if(d.getPenjualanCoilHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPenjualanCoilHead().setCustomer(c);
                        }
                        for(Pegawai s : sales){
                            if(d.getPenjualanCoilHead().getKodeSales().equals(s.getKodePegawai()))
                                d.getPenjualanCoilHead().setSales(s);
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
            for (PenjualanCoilDetail temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan())))||
                        checkColumn(temp.getPenjualanCoilHead().getKodeCustomer())||
                        checkColumn(temp.getPenjualanCoilHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanCoilHead().getPaymentTerm())||
                        checkColumn(temp.getPenjualanCoilHead().getKodeGudang())||
                        checkColumn(temp.getPenjualanCoilHead().getCatatan())||
                        checkColumn(temp.getPenjualanCoilHead().getKodeSales())||
                        checkColumn(temp.getPenjualanCoilHead().getSales().getNama())||
                        checkColumn(temp.getKodeBahan())||
                        checkColumn(df.format(temp.getHargaJual()*temp.getPenjualanCoilHead().getKurs()))||
                        checkColumn(df.format(temp.getTotal()*temp.getPenjualanCoilHead().getKurs()))||
                        checkColumn(temp.getNamaBahan())||
                        checkColumn(df.format(temp.getBeratKotor()))||
                        checkColumn(df.format(temp.getBeratBersih()))||
                        checkColumn(df.format(temp.getPanjang()))||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(temp.getSpesifikasi())||
                        checkColumn(df.format(temp.getNilai()))||
                        checkColumn(df.format(temp.getTotal()))||
                        checkColumn(df.format(temp.getNilai()*temp.getBeratBersih())))
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
        for(PenjualanCoilDetail temp : filterData){
            totalNilai = totalNilai + (temp.getNilai()*temp.getBeratBersih());
            totalQty = totalQty + temp.getBeratBersih();
            totalJual = totalJual + temp.getTotal()*temp.getPenjualanCoilHead().getKurs();
        }
        totalNilaiField.setText(df.format(totalNilai));
        totalQtyField.setText(df.format(totalQty));
        totalPenjualanField.setText(df.format(totalJual));
    }
    private void setTable()throws Exception{
        if(penjualanDetailTable.getRoot()!=null)
            penjualanDetailTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PenjualanCoilDetail temp: filterData){
            String group = "";
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")){
                group = temp.getNoPenjualan();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                group = temp.getPenjualanCoilHead().getKodeGudang();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")){
                group = temp.getPenjualanCoilHead().getSales().getNama();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")){
                group = temp.getPenjualanCoilHead().getCustomer().getNama();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                group = temp.getNamaBahan();
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                group = tgl.format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
            }
            if(!groupBy.contains(group))
                groupBy.add(group);
        }
        for(String temp : groupBy){
            PenjualanCoilDetail head = new PenjualanCoilDetail();
            head.setNoPenjualan(temp);
            head.setPenjualanCoilHead(new PenjualanCoilHead());
            head.getPenjualanCoilHead().setCustomer(new Customer());
            head.getPenjualanCoilHead().setSales(new Pegawai());
            TreeItem<PenjualanCoilDetail> parent = new TreeItem<>(head);
            double totalBeratBersih = 0;
            double totalBeratKotor = 0;
            double totalPanjang = 0;
            double totalNilai = 0;
            double totalHarga = 0;
            for(PenjualanCoilDetail detail: filterData){
                boolean status = false;
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan") && temp.equals(detail.getNoPenjualan())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                        temp.equals(tgl.format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                        temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                        temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&& temp.equals(detail.getPenjualanCoilHead().getKodeGudang())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")&& temp.equals(detail.getPenjualanCoilHead().getSales().getNama())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer") && temp.equals(detail.getPenjualanCoilHead().getCustomer().getNama())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang") && temp.equals(detail.getNamaBahan())){
                    status = true;
                }
                if(status){
                    totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                    totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                    totalPanjang = totalPanjang + detail.getPanjang();
                    totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                    totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                    parent.getChildren().addAll(new TreeItem<>(detail));
                }
            }
            parent.getValue().setBeratBersih(totalBeratBersih);
            parent.getValue().setBeratKotor(totalBeratKotor);
            parent.getValue().setPanjang(totalPanjang);
            parent.getValue().setNilai(totalNilai/totalBeratBersih);
            parent.getValue().getPenjualanCoilHead().setKurs(1);
            parent.getValue().setHargaJual(totalHarga/totalBeratBersih);
            parent.getValue().setTotal(totalHarga);
            root.getChildren().add(parent);
        }
        penjualanDetailTable.setRoot(root);
    }   
    private void lihatDetailPenjualan(PenjualanCoilHead p){
        if(p.getKurs()!=1){
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualanCoil.fxml");
            NewPenjualanCoilController controller = loader.getController();
            controller.setMainApp(mainApp,mainApp.MainStage, stage);
            controller.setDetailPenjualan(p.getNoPenjualan());
        }else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPenjualanCoilRp.fxml");
            NewPenjualanCoilRpController controller = loader.getController();
            controller.setMainApp(mainApp,mainApp.MainStage, stage);
            controller.setDetailPenjualan(p.getNoPenjualan());
        }
    }
    private void showDetailPiutang(PenjualanCoilHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPenjualanCoil(p);
    }
    
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanCoilTerjual(allPenjualan, tglPenjualanMulaiPicker.getValue().toString(),
                    tglPenjualanAkhirPicker.getValue().toString(), groupByCombo.getSelectionModel().getSelectedItem());
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
                
                Sheet sheet = workbook.createSheet("Laporan Coil Terjual");
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
                sheet.getRow(rc).getCell(5).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(6).setCellValue("Nama Bahan"); 
                sheet.getRow(rc).getCell(7).setCellValue("Berat Kotor"); 
                sheet.getRow(rc).getCell(8).setCellValue("Berat Bersih"); 
                sheet.getRow(rc).getCell(9).setCellValue("Panjang"); 
                sheet.getRow(rc).getCell(10).setCellValue("Nilai");
                sheet.getRow(rc).getCell(11).setCellValue("Total Nilai");
                sheet.getRow(rc).getCell(12).setCellValue("Harga"); 
                sheet.getRow(rc).getCell(13).setCellValue("Total Harga"); 
                rc++;
                List<String> groupBy = new ArrayList<>();
                for(PenjualanCoilDetail temp: filterData){
                    String group = "";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")){
                        group = temp.getNoPenjualan();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")){
                        group = temp.getPenjualanCoilHead().getKodeGudang();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")){
                        group = temp.getPenjualanCoilHead().getSales().getNama();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")){
                        group = temp.getPenjualanCoilHead().getCustomer().getNama();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")){
                        group = temp.getNamaBahan();
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")){
                        group = tgl.format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")){
                        group = new SimpleDateFormat("MMM yyyy").format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")){
                        group = new SimpleDateFormat("yyyy").format(tglSql.parse(temp.getPenjualanCoilHead().getTglPenjualan()));
                    }
                    if(!groupBy.contains(group))
                        groupBy.add(group);
                }
                double grandtotalBeratBersih = 0;
                double grandtotalBeratKotor = 0;
                double grandtotalPanjang = 0;
                double grandtotalNilai = 0;
                double grandtotalHarga = 0;
                for(String temp : groupBy){
                    rc++;
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue(temp);
                    rc++;
                    double totalBeratBersih = 0;
                    double totalBeratKotor = 0;
                    double totalPanjang = 0;
                    double totalNilai = 0;
                    double totalHarga = 0;
                    for(PenjualanCoilDetail detail: filterData){
                        boolean status = false;
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Penjualan")&&
                                temp.equals(detail.getNoPenjualan())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tanggal")&&
                                temp.equals(tgl.format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Bulan")&&
                                temp.equals(new SimpleDateFormat("MMM yyyy").format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Tahun")&&
                                temp.equals(new SimpleDateFormat("yyyy").format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())))){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Gudang")&&
                                temp.equals(detail.getPenjualanCoilHead().getKodeGudang())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Sales")&&
                                temp.equals(detail.getPenjualanCoilHead().getSales().getNama())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Customer")&&
                                temp.equals(detail.getPenjualanCoilHead().getCustomer().getNama())){
                            status = true;
                        }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Barang")&&
                                temp.equals(detail.getNamaBahan())){
                            status = true;
                        }
                        if(status){
                            createRow(workbook, sheet, rc, c, "Detail");
                            sheet.getRow(rc).getCell(0).setCellValue(detail.getPenjualanCoilHead().getNoPenjualan());
                            sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(detail.getPenjualanCoilHead().getTglPenjualan())));
                            sheet.getRow(rc).getCell(2).setCellValue(detail.getPenjualanCoilHead().getKodeGudang());
                            sheet.getRow(rc).getCell(3).setCellValue(detail.getPenjualanCoilHead().getCustomer().getNama());
                            sheet.getRow(rc).getCell(4).setCellValue(detail.getPenjualanCoilHead().getSales().getNama());
                            sheet.getRow(rc).getCell(5).setCellValue(detail.getKodeBahan());
                            sheet.getRow(rc).getCell(6).setCellValue(detail.getNamaBahan());
                            sheet.getRow(rc).getCell(7).setCellValue(detail.getBeratKotor());
                            sheet.getRow(rc).getCell(8).setCellValue(detail.getBeratBersih());
                            sheet.getRow(rc).getCell(9).setCellValue(detail.getPanjang());
                            sheet.getRow(rc).getCell(10).setCellValue(detail.getNilai());
                            sheet.getRow(rc).getCell(11).setCellValue(detail.getNilai()*detail.getBeratBersih());
                            sheet.getRow(rc).getCell(12).setCellValue(detail.getHargaJual()*detail.getPenjualanCoilHead().getKurs());
                            sheet.getRow(rc).getCell(13).setCellValue(detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                            rc++;
                            
                            totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                            totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                            totalPanjang = totalPanjang + detail.getPanjang();
                            totalNilai = totalNilai + (detail.getNilai()*detail.getBeratBersih());
                            totalHarga = totalHarga + (detail.getTotal()*detail.getPenjualanCoilHead().getKurs());
                        }
                    }
                    createRow(workbook, sheet, rc, c, "SubHeader");
                    sheet.getRow(rc).getCell(0).setCellValue("Total "+temp);
                    sheet.getRow(rc).getCell(7).setCellValue(totalBeratKotor);
                    sheet.getRow(rc).getCell(8).setCellValue(totalBeratBersih);
                    sheet.getRow(rc).getCell(9).setCellValue(totalPanjang);
                    sheet.getRow(rc).getCell(10).setCellValue(totalNilai/totalBeratBersih);
                    sheet.getRow(rc).getCell(11).setCellValue(totalNilai);
                    sheet.getRow(rc).getCell(12).setCellValue(totalHarga/totalBeratBersih);
                    sheet.getRow(rc).getCell(13).setCellValue(totalHarga);
                    rc++;
                    
                    grandtotalBeratKotor = grandtotalBeratKotor + totalBeratKotor;
                    grandtotalBeratBersih = grandtotalBeratBersih + totalBeratBersih;
                    grandtotalPanjang = grandtotalPanjang + totalPanjang;
                    grandtotalNilai = grandtotalNilai + totalNilai;
                    grandtotalHarga = grandtotalHarga + totalHarga;
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(7).setCellValue(grandtotalBeratKotor);
                sheet.getRow(rc).getCell(8).setCellValue(grandtotalBeratBersih);
                sheet.getRow(rc).getCell(9).setCellValue(grandtotalPanjang);
                sheet.getRow(rc).getCell(10).setCellValue(grandtotalNilai/grandtotalBeratBersih);
                sheet.getRow(rc).getCell(11).setCellValue(grandtotalNilai);
                sheet.getRow(rc).getCell(12).setCellValue(grandtotalHarga/grandtotalBeratBersih);
                sheet.getRow(rc).getCell(13).setCellValue(grandtotalHarga);
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
