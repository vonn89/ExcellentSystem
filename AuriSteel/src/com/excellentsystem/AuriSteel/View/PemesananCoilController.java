/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBahanHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBahanHead;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailTerimaPembayaranDownPaymentController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilRpController;
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
public class PemesananCoilController {
    @FXML private TableView<PemesananBahanHead> pemesananTable;
    @FXML private TableColumn<PemesananBahanHead, String> noPemesananColumn;
    @FXML private TableColumn<PemesananBahanHead, String> tglPemesananColumn;
    @FXML private TableColumn<PemesananBahanHead, String> namaCustomerColumn;
    @FXML private TableColumn<PemesananBahanHead, String> paymentTermColumn;
    @FXML private TableColumn<PemesananBahanHead, String> totalPemesananColumn;
    @FXML private TableColumn<PemesananBahanHead, String> kursColumn;
    @FXML private TableColumn<PemesananBahanHead, Number> totalPemesananRpColumn;
    @FXML private TableColumn<PemesananBahanHead, Number> downPaymentColumn;
    @FXML private TableColumn<PemesananBahanHead, Number> sisaDownPaymentColumn;
    @FXML private TableColumn<PemesananBahanHead, String> statusColumn;
    @FXML private TableColumn<PemesananBahanHead, String> catatanColumn;
    @FXML private TableColumn<PemesananBahanHead, String> namaSalesColumn;
    @FXML private TableColumn<PemesananBahanHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPemesananField;
    @FXML private Label totalDownPaymentField;
    @FXML private Label totalSisaDownPaymentField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> groupByCombo;
    private ObservableList<PemesananBahanHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananBahanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));
        
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));
        
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        statusColumn.setCellValueFactory(cellData -> {
            if(cellData.getValue().getStatus().equals("open"))
                return new SimpleStringProperty("Wait");
            else if(cellData.getValue().getStatus().equals("close"))
                return new SimpleStringProperty("Done");
            else if(cellData.getValue().getStatus().equals("false"))
                return new SimpleStringProperty("Cancel");
            else
                return null;
        });
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));
        
        tglPemesananColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));
        
        kursColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getKurs()));
        });
        kursColumn.setCellFactory(col -> Function.getWrapTableCell(kursColumn));
        kursColumn.setComparator(Function.sortString());
        
        totalPemesananColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getTotalPemesanan()/celldata.getValue().getKurs()));
        });
        totalPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(totalPemesananColumn));
        totalPemesananColumn.setComparator(Function.sortString());
        
        totalPemesananRpColumn.setCellValueFactory(celldata -> celldata.getValue().totalPemesananProperty());
        totalPemesananRpColumn.setCellFactory(col -> Function.getTableCell());
        
        downPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().downPaymentProperty());
        downPaymentColumn.setCellFactory(col -> Function.getTableCell());
        
        sisaDownPaymentColumn.setCellValueFactory(celldata -> celldata.getValue().sisaDownPaymentProperty());
        sisaDownPaymentColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pemesanan Coil (USD)");
        addNew.setOnAction((ActionEvent e)->{
            newPemesanan();
        });
        MenuItem addNewRp = new MenuItem("Add New Pemesanan Coil (Rp)");
        addNewRp.setOnAction((ActionEvent e)->{
            newPemesananRp();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPemesanan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pemesanan Coil")&&o.isStatus())
                rm.getItems().addAll(addNew, addNewRp);
        }
        rm.getItems().addAll(export, refresh);
        pemesananTable.setContextMenu(rm);
        pemesananTable.setRowFactory((TableView<PemesananBahanHead> tableView) -> {
            final TableRow<PemesananBahanHead> row = new TableRow<PemesananBahanHead>(){
                @Override
                public void updateItem(PemesananBahanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pemesanan Coil (USD)");
                        addNew.setOnAction((ActionEvent e)->{
                            newPemesanan();
                        });
                        MenuItem addNewRp = new MenuItem("Add New Pemesanan Coil (Rp)");
                        addNewRp.setOnAction((ActionEvent e)->{
                            newPemesananRp();
                        });
                        MenuItem detail = new MenuItem("Detail Pemesanan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPemesanan(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pemesanan Coil");
                        batal.setOnAction((ActionEvent e)->{
                            batalPemesanan(item);
                        });
                        MenuItem selesai = new MenuItem("Pemesanan Coil Selesai");
                        selesai.setOnAction((ActionEvent e)->{
                            selesaiPemesananCoil(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran DP Coil");
                        bayar.setOnAction((ActionEvent e)->{
                            terimaPembayaranDownPayment(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Terima Pembayaran DP Coil");
                        detailBayar.setOnAction((ActionEvent e)->{
                            lihatTerimaPembayaranDownPayment(item);
                        });
                        MenuItem invoice = new MenuItem("Print Order Confirmation Coil");
                        invoice.setOnAction((ActionEvent e)->{
                            printProformaInvoice(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPemesanan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pemesanan Coil")&&o.isStatus())
                                rm.getItems().addAll(addNew, addNewRp);
                            if(o.getJenis().equals("Detail Pemesanan Coil")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pemesanan Coil")&&o.isStatus()&&item.getStatus().equals("open")&&
                                    item.getSisaDownPayment()==0)
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Pemesanan Coil Selesai")&&o.isStatus()&&item.getStatus().equals("open")&&
                                    item.getSisaDownPayment()==0)
                                rm.getItems().add(selesai);
                            if(o.getJenis().equals("Terima Pembayaran DP Coil")&&o.isStatus()&&item.getStatus().equals("open"))
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Detail Terima Pembayaran DP Coil")&&o.isStatus())
                                rm.getItems().add(detailBayar);
                            if(o.getJenis().equals("Print Order Confirmation Coil")&&o.isStatus()&&item.getStatus().equals("open"))
                                rm.getItems().add(invoice);
                        }
                        rm.getItems().addAll(export, refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Pemesanan Coil")&&o.isStatus())
                                lihatDetailPemesanan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananBahanHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPemesanan);
        pemesananTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Wait");
        groupBy.add("Done");
        groupBy.add("Cancel");
        groupBy.add("Semua");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Wait");
        getPemesanan();
    }
    @FXML
    private void getPemesanan(){
        Task<List<PemesananBahanHead>> task = new Task<List<PemesananBahanHead>>() {
            @Override 
            public List<PemesananBahanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    String status = "%";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("Done")){
                        status = "close";
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Wait")){
                        status = "open";
                    }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Cancel")){
                        status = "false";
                    }
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<PemesananBahanHead> allPemesanan = PemesananBahanHeadDAO.getAllByDateAndStatus(
                        con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    List<PemesananBahanDetail> listPemesananCoilDetail = PemesananBahanDetailDAO.getAllByDateAndStatus(
                        con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), status);
                    for(PemesananBahanHead p : allPemesanan){
                        for(Customer c : allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        for(Pegawai s : allSales){
                            if(p.getKodeSales().equals(s.getKodePegawai()))
                                p.setSales(s);
                        }
                        List<PemesananBahanDetail> detail = new ArrayList<>();
                        for(PemesananBahanDetail d : listPemesananCoilDetail){
                            if(p.getNoPemesanan().equals(d.getNoPemesanan()))
                                detail.add(d);
                        }
                        p.setListPemesananBahanDetail(detail);
                    }
                    return allPemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
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
    private void searchPemesanan() {
        try{
            filterData.clear();
            for (PemesananBahanHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPemesanan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getPaymentTerm())||
                        checkColumn(df.format(temp.getTotalPemesanan()))||
                        checkColumn(df.format(temp.getDownPayment()))||
                        checkColumn(df.format(temp.getSisaDownPayment()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeSales())||
                        checkColumn(temp.getSales().getNama())||
                        checkColumn(temp.getStatus())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double total = 0;
        double dp = 0;
        double sisaDp = 0;
        for(PemesananBahanHead temp : filterData){
            total = total + temp.getTotalPemesanan();
            dp = dp + temp.getDownPayment();
            sisaDp = sisaDp + temp.getSisaDownPayment();
        }
        totalPemesananField.setText(df.format(total));
        totalDownPaymentField.setText(df.format(dp));
        totalSisaDownPaymentField.setText(df.format(sisaDp));
    }
    private void newPemesanan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananCoil.fxml");
        NewPemesananCoilController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPemesanan();
        controller.saveButton.setOnAction((event) -> {
            if(controller.customer==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
            else if(controller.allPemesananCoilDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            PemesananBahanHead pemesanan = new PemesananBahanHead();
                            pemesanan.setKodeCustomer(controller.customer.getKodeCustomer());
                            pemesanan.setPaymentTerm(controller.paymentTermField.getText());
                            pemesanan.setKurs(Double.parseDouble(controller.kursField.getText().replaceAll(",", "")));
                            pemesanan.setTotalPemesanan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            pemesanan.setDownPayment(0);
                            pemesanan.setSisaDownPayment(0);
                            pemesanan.setCatatan(controller.catatanField.getText());
                            pemesanan.setKodeSales(controller.customer.getKodeSales());
                            pemesanan.setKodeUser(sistem.getUser().getKodeUser());
                            pemesanan.setTglBatal("2000-01-01 00:00:00");
                            pemesanan.setUserBatal("");
                            pemesanan.setStatus("open");
                            pemesanan.setListPemesananBahanDetail(controller.allPemesananCoilDetail);
                            return Service.newPemesananCoil(con, pemesanan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void newPemesananRp(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananCoilRp.fxml");
        NewPemesananCoilRpController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPemesanan();
        controller.saveButton.setOnAction((event) -> {
            if(controller.customer==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
            else if(controller.allPemesananCoilDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang tidak ada");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            PemesananBahanHead pemesanan = new PemesananBahanHead();
                            pemesanan.setKodeCustomer(controller.customer.getKodeCustomer());
                            pemesanan.setPaymentTerm(controller.paymentTermField.getText());
                            pemesanan.setKurs(1);
                            pemesanan.setTotalPemesanan(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            pemesanan.setDownPayment(0);
                            pemesanan.setSisaDownPayment(0);
                            pemesanan.setCatatan(controller.catatanField.getText());
                            pemesanan.setKodeSales(controller.customer.getKodeSales());
                            pemesanan.setKodeUser(sistem.getUser().getKodeUser());
                            pemesanan.setTglBatal("2000-01-01 00:00:00");
                            pemesanan.setUserBatal("");
                            pemesanan.setStatus("open");
                            pemesanan.setListPemesananBahanDetail(controller.allPemesananCoilDetail);
                            return Service.newPemesananCoil(con, pemesanan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    }
                });
                task.setOnFailed((ex) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }
    private void lihatDetailPemesanan(PemesananBahanHead p){
        if(p.getKurs()!=1){
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananCoil.fxml");
            NewPemesananCoilController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setDetailPemesanan(p.getNoPemesanan());
        }else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPemesananCoilRp.fxml");
            NewPemesananCoilRpController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setDetailPemesanan(p.getNoPemesanan());
        }
    }
    private void batalPemesanan(PemesananBahanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pemesanan "+p.getNoPemesanan()+" , anda yakin ?");
        controller.OK.setOnAction((ActionEvent evt) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalPemesananCoil(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((we) -> {
                mainApp.closeLoading();
                getPemesanan();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil dibatal");
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void selesaiPemesananCoil(PemesananBahanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Pemesanan "+p.getNoPemesanan()+" telah selesai , anda yakin ?");
        controller.OK.setOnAction((ActionEvent evt) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        p.setStatus("close");
                        return Service.selesaiPemesananCoil(con, p);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((we) -> {
                mainApp.closeLoading();
                getPemesanan();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pemesanan berhasil disimpan");
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void terimaPembayaranDownPayment(PemesananBahanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setTerimaPembayaranDownPaymentCoil(p);
        controller.saveButton.setOnAction((event) -> {
            if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            p.setListPemesananBahanDetail(PemesananBahanDetailDAO.getAllByNoPemesanan(con, p.getNoPemesanan()));
                            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
                            double sisaPembayaran = p.getTotalPemesanan()-p.getSisaDownPayment();
                            if(jumlahBayar>sisaPembayaran)
                                return "Jumlah yang dibayar melebihi dari sisa pembayaran";
                            else{
                                p.setDownPayment(p.getDownPayment()+jumlahBayar);
                                p.setSisaDownPayment(p.getSisaDownPayment()+jumlahBayar);
                                return Service.newTerimaDownPaymentCoil(con, p, jumlahBayar,
                                        controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPemesanan();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Terima pembayaran DP berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void lihatTerimaPembayaranDownPayment(PemesananBahanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaPembayaranDownPayment.fxml");
        DetailTerimaPembayaranDownPaymentController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPemesananCoil(p);
        controller.hutangTable.setRowFactory((TableView<Hutang> tv) -> {
            final TableRow<Hutang> row = new TableRow<Hutang>(){
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Terima Pembayaran DP Coil");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(p, item, stage);
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Batal Terima Pembayaran DP Coil")&&o.isStatus()&&
                                    item.getStatus().equals("open")&&p.getSisaDownPayment()>=item.getJumlahHutang())
                                rm.getItems().add(batal);
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    } 
    private void batalPembayaran(PemesananBahanHead p, Hutang h,Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+h.getNoHutang()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalTerimaDownPaymentCoil(con, h);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPemesanan();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
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
    private void printProformaInvoice(PemesananBahanHead p){
        try(Connection con = Koneksi.getConnection()){
            List<KategoriBahan> listKategori = KategoriBahanDAO.getAllByStatus(con, "%");
            List<PemesananBahanDetail> listPemesanan = PemesananBahanDetailDAO.getAllByNoPemesanan(con, p.getNoPemesanan());
            for(PemesananBahanDetail d : listPemesanan){
                d.setPemesananBahanHead(p);
                d.setBahan(new Bahan());
                for(KategoriBahan k : listKategori){
                    if(d.getKategoriBahan().equals(k.getKodeKategori()))
                        d.getBahan().setKategoriBahan(k);
                }
            }
            Report report = new Report();
            report.printProformaInvoiceCoil(listPemesanan,p.getTotalPemesanan());
        }catch (Exception e){
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
                Sheet sheet = workbook.createSheet("Data Pemesanan Coil");
                int rc = 0;
                int c = 9;
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
                sheet.getRow(rc).getCell(3).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(4).setCellValue("Total Pemesanan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Kurs"); 
                sheet.getRow(rc).getCell(6).setCellValue("Total Pemesanan Rp"); 
                sheet.getRow(rc).getCell(7).setCellValue("Pembayaran Down Payment"); 
                sheet.getRow(rc).getCell(8).setCellValue("Sisa Pembayaran Down Payment"); 
                rc++;
                double pemesananRp = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PemesananBahanHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPemesanan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPemesanan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getSales().getNama());
                    if(b.getKurs()!=1){
                        sheet.getRow(rc).getCell(4).setCellValue(b.getTotalPemesanan()/b.getKurs());
                        sheet.getRow(rc).getCell(5).setCellValue(b.getKurs());
                    }else{
                        sheet.getRow(rc).getCell(4).setCellValue("-");
                        sheet.getRow(rc).getCell(5).setCellValue("-");
                    }
                    sheet.getRow(rc).getCell(6).setCellValue(b.getTotalPemesanan());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getDownPayment());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getSisaDownPayment());
                    rc++;
                    pemesananRp = pemesananRp + b.getTotalPemesanan();
                    pembayaran = pembayaran + b.getDownPayment();
                    sisaPembayaran = sisaPembayaran + b.getSisaDownPayment();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(6).setCellValue(pemesananRp);
                sheet.getRow(rc).getCell(7).setCellValue(pembayaran);
                sheet.getRow(rc).getCell(8).setCellValue(sisaPembayaran);
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
