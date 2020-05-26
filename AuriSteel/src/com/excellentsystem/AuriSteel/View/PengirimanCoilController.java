/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.Model.PenjualanHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPengirimanCoilController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.time.LocalDate;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
 * @author excellent
 */
public class PengirimanCoilController  {

    
    @FXML private TableView<PenjualanCoilHead> pengirimanTable;
    @FXML private TableColumn<PenjualanCoilHead, String> noPengirimanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> tglPengirimanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> noPemesananColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> gudangColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> tujuanKirimColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> supirColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<PenjualanCoilHead> allPengiriman = FXCollections.observableArrayList();
    private ObservableList<PenjualanCoilHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPengirimanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        noPengirimanColumn.setCellFactory(col -> Function.getWrapTableCell(noPengirimanColumn));
        
        tglPengirimanColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPengirimanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPengirimanColumn));
        tglPengirimanColumn.setComparator(Function.sortDate(tglLengkap));
        
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));
        
        
        tujuanKirimColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKendaraanProperty());
        tujuanKirimColumn.setCellFactory(col -> Function.getWrapTableCell(tujuanKirimColumn));
        
        supirColumn.setCellValueFactory(cellData -> cellData.getValue().supirProperty());
        supirColumn.setCellFactory(col -> Function.getWrapTableCell(supirColumn));
        
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pengiriman Coil");
        addNew.setOnAction((ActionEvent e)->{
            newPengiriman();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPengiriman();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pengiriman Coil")&&o.isStatus())
                rm.getItems().add(addNew);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        pengirimanTable.setContextMenu(rm);
        pengirimanTable.setRowFactory((TableView<PenjualanCoilHead> tableView) -> {
            final TableRow<PenjualanCoilHead> row = new TableRow<PenjualanCoilHead>(){
                @Override
                public void updateItem(PenjualanCoilHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pengiriman Coil");
                        addNew.setOnAction((ActionEvent e)->{
                            newPengiriman();
                        });
                        MenuItem detail = new MenuItem("Detail Pengiriman Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPengiriman(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pengiriman Coil");
                        batal.setOnAction((ActionEvent e)->{
                            batalPengiriman(item);
                        });
                        MenuItem suratJalan = new MenuItem("Print Surat Jalan Coil");
                        suratJalan.setOnAction((ActionEvent e)->{
                            printSuratJalan(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPengiriman();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pengiriman Coil")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Pengiriman Coil")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pengiriman Coil")&&o.isStatus())
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Print Surat Jalan Coil")&&o.isStatus())
                                rm.getItems().add(suratJalan);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().add(export);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Pengiriman Coil")&&o.isStatus())
                                lihatDetailPengiriman(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPengiriman.addListener((ListChangeListener.Change<? extends PenjualanCoilHead> change) -> {
            searchPengiriman();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPengiriman();
        });
        filterData.addAll(allPengiriman);
        pengirimanTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPengiriman();
    }
    @FXML
    private void getPengiriman(){
        Task<List<PenjualanCoilHead>> task = new Task<List<PenjualanCoilHead>>() {
            @Override 
            public List<PenjualanCoilHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PenjualanCoilHead> allPengiriman = PenjualanCoilHeadDAO.getAllByDateAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),"true");
                    for(PenjualanCoilHead h : allPengiriman){
                        for(Customer c : allCustomer){
                            if(h.getKodeCustomer().equals(c.getKodeCustomer()))
                                h.setCustomer(c);
                        }
                    }
                    return allPengiriman;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPengiriman.clear();
            allPengiriman.addAll(task.getValue());
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
    private void searchPengiriman() {
        try{
            filterData.clear();
            for (PenjualanCoilHead temp : allPengiriman) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getNoPemesanan())||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getCustomer().getKota())||
                        checkColumn(temp.getJenisKendaraan())||
                        checkColumn(temp.getNoPolisi())||
                        checkColumn(temp.getSupir())||
                        checkColumn(temp.getCatatan()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void newPengiriman(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengirimanCoil.fxml");
        NewPengirimanCoilController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPengiriman();
        controller.saveButton.setOnAction((event) -> {
            if(!controller.kursField.isVisible())
                controller.kursField.setText("1");
            
            if(controller.pemesanan==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Pemesanan belum dipilih");
            }else if(controller.gudangCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
            }else if(controller.allPenjualanCoilDetail.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            }else if(Double.parseDouble(controller.kursField.getText().replaceAll(",", ""))==0){
                mainApp.showMessage(Modality.NONE, "Warning", "Kurs dollar masih kosong");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call()throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            String noPengiriman = PenjualanCoilHeadDAO.getId(con);
                            PenjualanCoilHead pengiriman = new PenjualanCoilHead();
                            pengiriman.setPemesananCoilHead(controller.pemesanan);
                            pengiriman.setNoPenjualan(noPengiriman);
                            pengiriman.setTglPenjualan(tglSql.format(Function.getServerDate(con)));
                            pengiriman.setNoPemesanan(controller.noPemesananField.getText());
                            pengiriman.setKodeGudang(controller.gudangCombo.getSelectionModel().getSelectedItem());
                            pengiriman.setKodeCustomer(controller.pemesanan.getKodeCustomer());
                            pengiriman.setJenisKendaraan(controller.jenisKendaraanField.getText());
                            pengiriman.setNoPolisi(controller.noPolisiField.getText());
                            pengiriman.setSupir(controller.namaSupirField.getText());
                            pengiriman.setKurs(Double.parseDouble(controller.kursField.getText().replaceAll(",", "")));
                            pengiriman.setPaymentTerm(controller.pemesanan.getPaymentTerm());
                            pengiriman.setCatatan(controller.pemesanan.getCatatan());
                            pengiriman.setKodeSales(controller.pemesanan.getKodeSales());
                            pengiriman.setKodeUser(sistem.getUser().getKodeUser());
                            pengiriman.setTglBatal("2000-01-01 00:00:00");
                            pengiriman.setUserBatal("");
                            pengiriman.setStatus("true");
                            double total = 0;
                            for(PenjualanCoilDetail temp : controller.allPenjualanCoilDetail){
                                temp.setNoPenjualan(noPengiriman);
                                total = total + temp.getTotal();
                            }
                            total = total * Double.parseDouble(controller.kursField.getText().replaceAll(",", "")); 
                            pengiriman.setTotalPenjualan(Math.round(total));
                            double dp = controller.pemesanan.getSisaDownPayment();
                            if(total>=dp)
                                pengiriman.setPembayaran(dp);
                            else if(total<dp)
                                pengiriman.setPembayaran(total);
                            pengiriman.setSisaPembayaran(pengiriman.getTotalPenjualan()-pengiriman.getPembayaran());
                            pengiriman.setListPenjualanDetail(controller.allPenjualanCoilDetail); 
                            if(pengiriman.getPemesananCoilHead().getCustomer().getLimitHutang()<
                                pengiriman.getPemesananCoilHead().getCustomer().getHutang()+pengiriman.getSisaPembayaran())
                                return "Sisa pembayaran melebihi limit hutang customer";
                            else
                                return Service.newPenjualanCoil(con, pengiriman, controller.pemesananSelesai.isSelected());
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPengiriman();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    task.getException().printStackTrace();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void lihatDetailPengiriman(PenjualanCoilHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengirimanCoil.fxml");
        NewPengirimanCoilController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPengiriman(p.getNoPenjualan());
    }
    private void batalPengiriman(PenjualanCoilHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pengiriman barang "+p.getNoPenjualan()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        p.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        p.setUserBatal(sistem.getUser().getKodeUser());
                        p.setStatus("false");
                        return Service.batalPenjualanCoil(con, p);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPengiriman();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pengiriman barang berhasil dibatal");
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void printSuratJalan(PenjualanCoilHead p){
        try(Connection con = Koneksi.getConnection()){
            List<PenjualanCoilDetail> listPenjualan = PenjualanCoilDetailDAO.getAllPenjualanCoilDetail(con, p.getNoPenjualan());
            for(PenjualanCoilDetail d : listPenjualan){
                d.setPenjualanCoilHead(p);
            }
            Report report = new Report();
            report.printSuratJalanCoil(listPenjualan);
        }catch (Exception e){
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
                Sheet sheet = workbook.createSheet("Data Pengiriman Coil");
                int rc = 0;
                int c = 8;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Tanggal : "+
                        tgl.format(tglBarang.parse(tglMulaiPicker.getValue().toString()))+"-"+
                        tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Pengiriman"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pengiriman");  
                sheet.getRow(rc).getCell(2).setCellValue("No Pemesanan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(4).setCellValue("Nama"); 
                sheet.getRow(rc).getCell(5).setCellValue("Alamat"); 
                sheet.getRow(rc).getCell(6).setCellValue("Tujuan Kirim"); 
                sheet.getRow(rc).getCell(7).setCellValue("Supir"); 
                rc++;
                for (PenjualanCoilHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPenjualan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPenjualan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getCustomer().getAlamat());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getJenisKendaraan());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getSupir());
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
