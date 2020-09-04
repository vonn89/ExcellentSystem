/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
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
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.TerimaPembayaran;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.JatuhTempoController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembayaranController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilRpController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
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
public class PenjualanCoilController {

    @FXML private TableView<PenjualanCoilHead> penjualanTable;
    @FXML private TableColumn<PenjualanCoilHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> gudangColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> paymentTermColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> kursColumn;
    @FXML private TableColumn<PenjualanCoilHead, Number> totalPenjualanRpColumn;
    @FXML private TableColumn<PenjualanCoilHead, Number> pembayaranColumn;
    @FXML private TableColumn<PenjualanCoilHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> catatanColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> namaSalesColumn;
    @FXML private TableColumn<PenjualanCoilHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPenjualanField;
    @FXML private Label belumTerbayarField;
    @FXML private Label sudahTerbayarField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> groupByCombo;
    private ObservableList<PenjualanCoilHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanCoilHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        noPenjualanColumn.setCellFactory(col -> Function.getWrapTableCell(noPenjualanColumn));
        
        gudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        gudangColumn.setCellFactory(col -> Function.getWrapTableCell(gudangColumn));
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        alamatCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(alamatCustomerColumn));
        
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));
        
        namaSalesColumn.setCellValueFactory(cellData -> cellData.getValue().getSales().namaProperty());
        namaSalesColumn.setCellFactory(col -> Function.getWrapTableCell(namaSalesColumn));
        
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenjualanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPenjualanColumn));
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        
        kursColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getKurs()));
        });
        kursColumn.setCellFactory(col -> Function.getWrapTableCell(kursColumn));
        kursColumn.setComparator(Function.sortString());
        
        totalPenjualanColumn.setCellValueFactory(celldata -> {
            if(celldata.getValue().getKurs()==1)
                return new SimpleStringProperty("-");
            else
                return new SimpleStringProperty(df.format(celldata.getValue().getTotalPenjualan()/celldata.getValue().getKurs()));
        });
        totalPenjualanColumn.setCellFactory(col -> Function.getWrapTableCell(totalPenjualanColumn));
        
        totalPenjualanRpColumn.setCellValueFactory(celldata -> celldata.getValue().totalPenjualanProperty());
        totalPenjualanRpColumn.setCellFactory(col -> Function.getTableCell());
        
        pembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        sisaPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPenjualan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rm);
        penjualanTable.setRowFactory((TableView<PenjualanCoilHead> tableView) -> {
            final TableRow<PenjualanCoilHead> row = new TableRow<PenjualanCoilHead>(){
                @Override
                public void updateItem(PenjualanCoilHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan Coil");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan Coil");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem detailPemesanan = new MenuItem("Detail Pemesanan Coil");
                        detailPemesanan.setOnAction((ActionEvent e)->{
                            lihatDetailPemesanan(item);
                        });
                        MenuItem invoice = new MenuItem("Print Invoice Coil");
                        invoice.setOnAction((ActionEvent e)->{
                            printInvoice(item);
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran Coil");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo Coil");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
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
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan Coil")&&o.isStatus()
                                    &&item.getPembayaran()>0)
                                rm.getItems().add(pembayaran);
                            if(o.getJenis().equals("Detail Pemesanan Coil")&&o.isStatus())
                                rm.getItems().add(detailPemesanan);
                            if(o.getJenis().equals("Terima Pembayaran Coil")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Set Jatuh Tempo Penjualan Coil")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(tempo);
                            if(o.getJenis().equals("Print Invoice Coil")&&o.isStatus())
                                rm.getItems().add(invoice);
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
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus())
                                lihatDetailPenjualan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanCoilHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
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
        getPenjualan();
        penjualanTable.setItems(filterData);
    }
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanCoilHead>> task = new Task<List<PenjualanCoilHead>>() {
            @Override 
            public List<PenjualanCoilHead> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanCoilDetail> listPenjualanDetail = PenjualanCoilDetailDAO.getAllByDateAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<PenjualanCoilHead> allPenjualan = PenjualanCoilHeadDAO.getAllByDateAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pegawai> allSales = PegawaiDAO.getAllByStatus(con, "%");
                    List<PenjualanCoilHead> listPenjualan = new ArrayList<>();
                    for(PenjualanCoilHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        for(Pegawai s : allSales){
                            if(p.getKodeSales().equals(s.getKodePegawai()))
                                p.setSales(s);
                        }
                        List<PenjualanCoilDetail> detail = new ArrayList<>();
                        for(PenjualanCoilDetail d : listPenjualanDetail){
                            if(p.getNoPenjualan().equals(d.getNoPenjualan()))
                                detail.add(d);
                        }
                        p.setListPenjualanDetail(detail);
                        if(groupByCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            listPenjualan.add(p);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas")&&p.getSisaPembayaran()!=0)
                            listPenjualan.add(p);
                        else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas")&&p.getSisaPembayaran()==0)
                            listPenjualan.add(p);
                        
                    }
                    return listPenjualan;
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanCoilHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getKodeGudang())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getPaymentTerm())||
                        checkColumn(df.format(temp.getTotalPenjualan()))||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getSales().getNama()))
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
        for(PenjualanCoilHead temp : filterData){
            total = total + temp.getTotalPenjualan();
            terbayar = terbayar + temp.getPembayaran();
            belumterbayar = belumterbayar + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(total));
        sudahTerbayarField.setText(df.format(terbayar));
        belumTerbayarField.setText(df.format(belumterbayar));
    }
    private void showPembayaran(PenjualanCoilHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPenjualanCoil(p.getNoPenjualan());
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
                            Piutang pt = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                                    con, "Piutang Penjualan", p.getNoPenjualan(), "%");
                            pt.setPenjualanCoilHead(p);
                            TerimaPembayaran t = new TerimaPembayaran();
                            t.setNoPiutang(pt.getNoPiutang());
                            t.setJumlahPembayaran(Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")));
                            t.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            t.setCatatan("");
                            t.setKodeUser(sistem.getUser().getKodeUser());
                            t.setTglBatal("2000-01-01 00:00:00");
                            t.setUserBatal("");
                            t.setStatus("true");
                            t.setPiutang(pt);
                            return Service.newTerimaPembayaranPiutang(con,t);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran penjualan berhasil disimpan");
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
    private void setJatuhTempo(PenjualanCoilHead p) {
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
                        Piutang piutang = PiutangDAO.getByKategoriAndKeteranganAndStatus(
                                con, "Piutang Penjualan", p.getNoPenjualan(), "%");
                        piutang.setJatuhTempo(jatuhTempo);
                        return Service.setJatuhTempoPiutang(con, piutang);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getPenjualan();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo penjualan berhasil disimpan");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
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
    private void lihatDetailPemesanan(PenjualanCoilHead p){
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
    private void showDetailPiutang(PenjualanCoilHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPenjualanCoil(p);
        x.pembayaranPiutangTable.setRowFactory((TableView<TerimaPembayaran> tableView) -> {
            final TableRow<TerimaPembayaran> row = new TableRow<TerimaPembayaran>(){
                @Override
                public void updateItem(TerimaPembayaran item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Terima Pembayaran Coil");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(item, stage);
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Batal Terima Pembayaran Coil")&&o.isStatus())
                                rm.getItems().add(batal);
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    private void batalPembayaran(TerimaPembayaran pembayaran, Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoTerimaPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.batalTerimaPembayaranPiutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPenjualan();
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
    private void printInvoice(PenjualanCoilHead p){
        try(Connection con = Koneksi.getConnection()){
            List<PenjualanCoilDetail> listPenjualan = PenjualanCoilDetailDAO.getAllPenjualanCoilDetail(con, p.getNoPenjualan());
            for(PenjualanCoilDetail d : listPenjualan){
                d.setPenjualanCoilHead(p);
            }
            Report report = new Report();
            report.printInvoiceCoil(listPenjualan,p.getTotalPenjualan());
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
                Sheet sheet = workbook.createSheet("Data Penjualan Coil");
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
                sheet.getRow(rc).getCell(0).setCellValue("No Penjualan"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tgl Penjualan");  
                sheet.getRow(rc).getCell(2).setCellValue("Gudang"); 
                sheet.getRow(rc).getCell(3).setCellValue("Customer"); 
                sheet.getRow(rc).getCell(4).setCellValue("Sales"); 
                sheet.getRow(rc).getCell(5).setCellValue("Total Penjualan"); 
                sheet.getRow(rc).getCell(6).setCellValue("Kurs Dollar"); 
                sheet.getRow(rc).getCell(7).setCellValue("Total Penjualan Rp"); 
                sheet.getRow(rc).getCell(8).setCellValue("Pembayaran"); 
                sheet.getRow(rc).getCell(9).setCellValue("Sisa Pembayaran"); 
                rc++;
                double penjualanRp = 0;
                double pembayaran = 0;
                double sisaPembayaran = 0;
                for (PenjualanCoilHead b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoPenjualan());
                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(b.getTglPenjualan())));
                    sheet.getRow(rc).getCell(2).setCellValue(b.getKodeGudang());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getCustomer().getNama());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getSales().getNama());
                    if(b.getKurs()==1){
                        sheet.getRow(rc).getCell(5).setCellValue("-");
                        sheet.getRow(rc).getCell(6).setCellValue("-");
                    }else{
                        sheet.getRow(rc).getCell(5).setCellValue(b.getTotalPenjualan()/b.getKurs());
                        sheet.getRow(rc).getCell(6).setCellValue(b.getKurs());
                    }
                    sheet.getRow(rc).getCell(7).setCellValue(b.getTotalPenjualan());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getPembayaran());
                    sheet.getRow(rc).getCell(9).setCellValue(b.getSisaPembayaran());
                    rc++;
                    penjualanRp = penjualanRp + b.getTotalPenjualan();
                    pembayaran = pembayaran + b.getPembayaran();
                    sisaPembayaran = sisaPembayaran + b.getSisaPembayaran();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(7).setCellValue(penjualanRp);
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
