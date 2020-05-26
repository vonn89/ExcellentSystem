/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.PembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
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
import com.excellentsystem.AuriSteel.Model.BebanPembelian;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pembayaran;
import com.excellentsystem.AuriSteel.Model.PembelianBarangDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.JatuhTempoController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianBarangController;
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
public class PembelianBarangController  {

    @FXML private TableView<PembelianBarangHead> pembelianTable;
    @FXML private TableColumn<PembelianBarangHead, String> noPembelianColumn;
    @FXML private TableColumn<PembelianBarangHead, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianBarangHead, String> gudangColumn;
    @FXML private TableColumn<PembelianBarangHead, String> namaSupplierColumn;
    @FXML private TableColumn<PembelianBarangHead, String> paymentTermColumn;
    @FXML private TableColumn<PembelianBarangHead, Number> totalPembelianColumn;
    @FXML private TableColumn<PembelianBarangHead, Number> totalBebanPembelianColumn;
    @FXML private TableColumn<PembelianBarangHead, Number> grandTotalColumn;
    @FXML private TableColumn<PembelianBarangHead, Number> pembayaranColumn;
    @FXML private TableColumn<PembelianBarangHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PembelianBarangHead, String> catatanColumn;
    @FXML private TableColumn<PembelianBarangHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPembelianField;
    @FXML private Label belumTerbayarField;
    @FXML private Label sudahTerbayarField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> groupByCombo;
    private ObservableList<PembelianBarangHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianBarangHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        noPembelianColumn.setCellFactory(col -> Function.getWrapTableCell(noPembelianColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));
        
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTableCell(namaSupplierColumn));
        
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));
        
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembelianColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembelianColumn));
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        
        totalPembelianColumn.setCellValueFactory(cellData ->cellData.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> Function.getTableCell());
        
        totalBebanPembelianColumn.setCellValueFactory(cellData ->cellData.getValue().totalBebanPembelianProperty());
        totalBebanPembelianColumn.setCellFactory(col -> Function.getTableCell());
        
        grandTotalColumn.setCellValueFactory(cellData ->cellData.getValue().grandtotalProperty());
        grandTotalColumn.setCellFactory(col -> Function.getTableCell());
        
        pembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        sisaPembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pembelian");
        addNew.setOnAction((ActionEvent e)->{
            newPembelian();
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
            if(o.getJenis().equals("Add New Pembelian Barang")&&o.isStatus())
                rm.getItems().add(addNew);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        pembelianTable.setContextMenu(rm);
        pembelianTable.setRowFactory((TableView<PembelianBarangHead> tableView) -> {
            final TableRow<PembelianBarangHead> row = new TableRow<PembelianBarangHead>(){
                @Override
                public void updateItem(PembelianBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pembelian");
                        addNew.setOnAction((ActionEvent e)->{
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Pembelian");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPembelian(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembelian(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Pembelian");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
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
                            if(o.getJenis().equals("Add New Pembelian Barang")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Pembelian Barang")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pembelian Barang")&&o.isStatus())
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Detail Pembayaran Pembelian Barang")&&o.isStatus()&&item.getPembayaran()>0)
                                rm.getItems().add(pembayaran);
                            if(o.getJenis().equals("Pembayaran Pembelian Barang")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Set Jatuh Tempo Pembelian Barang")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(tempo);
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
                            if(o.getJenis().equals("Detail Pembelian Barang")&&o.isStatus())
                                lihatDetailPembelian(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianBarangHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Semua");
        groupBy.add("Belum Lunas");
        groupBy.add("Lunas");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().selectFirst();
        getPembelian();
        pembelianTable.setItems(filterData);
    }
    @FXML
    private void getPembelian(){
        Task<List<PembelianBarangHead>> task = new Task<List<PembelianBarangHead>>() {
            @Override 
            public List<PembelianBarangHead> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianBarangHead> temp = PembelianBarangHeadDAO.getAllByDateAndStatus(con, 
                                    tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<PembelianBarangHead> listPembelian = new ArrayList<>();
                    for(PembelianBarangHead p : temp){
                        for(Supplier s : allSupplier){
                            if(p.getKodeSupplier().equals(s.getKodeSupplier()))
                                p.setSupplier(s);
                        }
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            listPembelian.add(p);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas")&&p.getSisaPembayaran()!=0)
                            listPembelian.add(p);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas")&&p.getSisaPembayaran()==0)
                            listPembelian.add(p);
                    }
                    return listPembelian;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
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
    private void searchPembelian() {
        try{
            filterData.clear();
            for (PembelianBarangHead temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPembelian())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPembelian())))||
                        checkColumn(temp.getKodeSupplier())||
                        checkColumn(temp.getKodeGudang())||
                        checkColumn(temp.getSupplier().getNama())||
                        checkColumn(temp.getSupplier().getAlamat())||
                        checkColumn(temp.getPaymentTerm())||
                        checkColumn(df.format(temp.getTotalPembelian()))||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(df.format(temp.getGrandtotal()))||
                        checkColumn(df.format(temp.getTotalBebanPembelian()))||
                        checkColumn(temp.getCatatan()))
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
        double terbayar = 0;
        double belumterbayar = 0;
        for(PembelianBarangHead temp : filterData){
            total = total + temp.getGrandtotal();
            terbayar = terbayar + temp.getPembayaran();
            belumterbayar = belumterbayar + temp.getSisaPembayaran();
        }
        totalPembelianField.setText(df.format(total));
        sudahTerbayarField.setText(df.format(terbayar));
        belumTerbayarField.setText(df.format(belumterbayar));
    }
    private void newPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelianBarang.fxml");
        NewPembelianBarangController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPembelian();
        controller.saveButton.setOnAction((event) -> {
            if(controller.supplier==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
            }else if(controller.allPembelianBarangDetail.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum diinput");
            }else if(controller.gudangCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noPembelian = PembelianBarangHeadDAO.getId(con);
                            PembelianBarangHead p = new PembelianBarangHead();
                            p.setNoPembelian(noPembelian);
                            p.setTglPembelian(tglSql.format(Function.getServerDate(con)));
                            p.setKodeGudang(controller.gudangCombo.getSelectionModel().getSelectedItem());
                            p.setKodeSupplier(controller.supplier.getKodeSupplier());
                            p.setPaymentTerm("");
                            p.setTotalBebanPembelian(Double.parseDouble(controller.bebanPembelianField.getText().replaceAll(",", "")));
                            p.setTotalPembelian(Double.parseDouble(controller.totalPembelianField.getText().replaceAll(",", "")));
                            p.setGrandtotal(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            p.setPembayaran(0);
                            p.setSisaPembayaran(Double.parseDouble(controller.grandtotalField.getText().replaceAll(",", "")));
                            p.setCatatan(controller.catatanField.getText());
                            p.setKodeUser(sistem.getUser().getKodeUser());
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            p.setStatus("true");
                            for(PembelianBarangDetail temp : controller.allPembelianBarangDetail){
                                temp.setNoPembelian(noPembelian);
                            }
                            for(BebanPembelian temp : controller.allBebanPembelian){
                                temp.setNoPembelian(noPembelian);
                            }
                            p.setListPembelianBarangDetail(controller.allPembelianBarangDetail);
                            p.setListBebanPembelian(controller.allBebanPembelian);
                            return Service.newPembelianBarang(con, p);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    mainApp.closeLoading();    
                    getPembelian();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data pembelian berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
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
    private void batalPembelian(PembelianBarangHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembelian "+p.getNoPembelian()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        p.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        p.setUserBatal(sistem.getUser().getKodeUser());
                        p.setStatus("false");
                        return Service.batalPembelianBarang(con, p);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPembelian();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembelian berhasil dibatal");
                }else{
                    mainApp.showMessage(Modality.NONE, "Warning", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void lihatDetailPembelian(PembelianBarangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelianBarang.fxml");
        NewPembelianBarangController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPembelian(p.getNoPembelian());
    }
    private void showDetailHutang(PembelianBarangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPembelianBarang(p);
        x.pembayaranHutangTable.setRowFactory((TableView<Pembayaran> tableView) -> {
            final TableRow<Pembayaran> row = new TableRow<Pembayaran>(){
                @Override
                public void updateItem(Pembayaran item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran Pembelian");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(item, stage);
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Batal Pembayaran Pembelian Barang")&&o.isStatus())
                                rm.getItems().add(batal);
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    private void batalPembayaran(Pembayaran pembayaran, Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang hutang = HutangDAO.get(con, pembayaran.getNoHutang());
                        pembayaran.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setUserBatal(sistem.getUser().getKodeUser());
                        pembayaran.setStatus("false");
                        pembayaran.setHutang(hutang);
                        return Service.batalPembayaranHutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPembelian();
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
    private void showPembayaran(PembelianBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPembelianBarang(p.getNoPembelian());
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
            if(jumlahBayar>p.getSisaPembayaran())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa pembayaran");
            else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(
                                    con, "Hutang Pembelian", p.getNoPembelian(), "open");
                            h.setPembelianBarangHead(p);
                            Pembayaran pembayaran = new Pembayaran();
                            pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                            pembayaran.setTglPembayaran(tglSql.format(Function.getServerDate(con)));
                            pembayaran.setNoHutang(h.getNoHutang());
                            pembayaran.setJumlahPembayaran(Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")));
                            pembayaran.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            pembayaran.setCatatan("");
                            pembayaran.setKodeUser(sistem.getUser().getKodeUser());
                            pembayaran.setTglBatal("2000-01-01 00:00:00");
                            pembayaran.setUserBatal("");
                            pembayaran.setStatus("true");
                            pembayaran.setHutang(h);
                            return Service.newPembayaranHutang(con, pembayaran);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran pembelian berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void setJatuhTempo(PembelianBarangHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/JatuhTempo.fxml");
        JatuhTempoController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            String jatuhTempo = controller.jatuhTempoPicker.getValue().toString();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(
                                con, "Hutang Pembelian", p.getNoPembelian(), "open");
                        h.setJatuhTempo(jatuhTempo);
                        return Service.setJatuhTempoHutang(con, h);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPembelian();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo pembelian berhasil disimpan");
                }else
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
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
                Sheet sheet = workbook.createSheet("Data Pembelian");
                int rc = 0;
                int c = 10;
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
                sheet.getRow(rc).getCell(0).setCellValue("No Pembelian"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Pembelian");  
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Supplier"); 
                sheet.getRow(rc).getCell(4).setCellValue("Payment Term"); 
                sheet.getRow(rc).getCell(5).setCellValue("Total Pembelian"); 
                sheet.getRow(rc).getCell(6).setCellValue("Total Beban Pembelian"); 
                sheet.getRow(rc).getCell(7).setCellValue("Grandtotal"); 
                sheet.getRow(rc).getCell(8).setCellValue("Pembayaran"); 
                sheet.getRow(rc).getCell(9).setCellValue("Sisa Pembayaran"); 
                rc++;
                double pembelian = 0;
                double bebanPembelian = 0;
                double grandtotal = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PembelianBarangHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPembelian());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPembelian())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getSupplier().getNama());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getPaymentTerm());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getTotalPembelian());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getTotalBebanPembelian());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getGrandtotal());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getPembayaran());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getSisaPembayaran());
                    rc++;
                    pembelian = pembelian + b.getTotalPembelian();
                    bebanPembelian = bebanPembelian + b.getTotalBebanPembelian();
                    grandtotal = grandtotal + b.getGrandtotal();
                    pembayaran = pembayaran + b.getPembayaran();
                    sisaPembayaran = sisaPembayaran + b.getSisaPembayaran();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(5).setCellValue(pembelian);
                sheet.getRow(rc).getCell(6).setCellValue(bebanPembelian);
                sheet.getRow(rc).getCell(7).setCellValue(grandtotal);
                sheet.getRow(rc).getCell(8).setCellValue(pembayaran);
                sheet.getRow(rc).getCell(9).setCellValue(sisaPembayaran);
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
