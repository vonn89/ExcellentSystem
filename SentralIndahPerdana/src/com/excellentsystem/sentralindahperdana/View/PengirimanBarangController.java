/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPengirimanController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class PengirimanBarangController  {

    @FXML private TableView<PengirimanHead> pengirimanTable;
    @FXML private TableColumn<PengirimanHead, String> noPengirimanColumn;
    @FXML private TableColumn<PengirimanHead, String> tglPengirimanColumn;
    @FXML private TableColumn<PengirimanHead, String> kodeCustomerColumn;
    @FXML private TableColumn<PengirimanHead, String> namaCustomerColumn;
    @FXML private TableColumn<PengirimanHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PengirimanHead, String> namaProyekColumn;
    @FXML private TableColumn<PengirimanHead, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PengirimanHead, String> jenisKendaraanColumn;
    @FXML private TableColumn<PengirimanHead, String> noPolisiColumn;
    @FXML private TableColumn<PengirimanHead, String> supirColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<PengirimanHead> allPengiriman = FXCollections.observableArrayList();
    private ObservableList<PengirimanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPengirimanColumn.setCellValueFactory(cellData -> cellData.getValue().noPengirimanProperty());
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().kodeCustomerProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().getCustomer().namaProperty());
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().getCustomer().alamatProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().lokasiPengerjaanProperty());
        jenisKendaraanColumn.setCellValueFactory(cellData -> cellData.getValue().jenisKendaraanProperty());
        noPolisiColumn.setCellValueFactory(cellData ->cellData.getValue().noPolisiProperty());
        supirColumn.setCellValueFactory(cellData ->cellData.getValue().supirProperty());
        tglPengirimanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPengiriman())));
            } catch (Exception ex) {
                return null;
            }
        });
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pengiriman");
        addNew.setOnAction((ActionEvent e)->{
            newPengiriman();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPengiriman();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pengiriman")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        pengirimanTable.setContextMenu(rm);
        pengirimanTable.setRowFactory((TableView<PengirimanHead> tableView) -> {
            final TableRow<PengirimanHead> row = new TableRow<PengirimanHead>(){
                @Override
                public void updateItem(PengirimanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pengiriman");
                        addNew.setOnAction((ActionEvent e)->{
                            newPengiriman();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Pengiriman");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPengiriman(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pengiriman");
                        batal.setOnAction((ActionEvent e)->{
                            batalPengiriman(item);
                        });
                        MenuItem suratJalan = new MenuItem("Print Surat Jalan");
                        suratJalan.setOnAction((ActionEvent e)->{
                            printSuratJalan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPengiriman();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pengiriman")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Pengiriman")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pengiriman")&&o.isStatus())
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Print Surat Jalan")&&o.isStatus())
                                rm.getItems().add(suratJalan);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Pengiriman")&&o.isStatus())
                                lihatDetailPengiriman(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPengiriman.addListener((ListChangeListener.Change<? extends PengirimanHead> change) -> {
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
        Task<List<PengirimanHead>> task = new Task<List<PengirimanHead>>() {
            @Override 
            public List<PengirimanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanHead> listPenjualan = PenjualanHeadDAO.getAll(con);
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PengirimanHead> allPengiriman = PengirimanHeadDAO.getAllByDateAndStatus(con, 
                        tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for(PengirimanHead p : allPengiriman){
                        for(PenjualanHead pj : listPenjualan){
                            if(p.getNoPenjualan().equals(pj.getNoPenjualan()))
                                p.setPenjualanHead(pj);
                        }
                        for(Customer c: allCustomer){
                            if(p.getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                p.getPenjualanHead().setCustomer(c);
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
            try{
                mainApp.closeLoading();
                allPengiriman.clear();
                allPengiriman.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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
            for (PengirimanHead temp : allPengiriman) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPengiriman())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPengiriman())))||
                        checkColumn(temp.getPenjualanHead().getKodeCustomer())||
                        checkColumn(temp.getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanHead().getCustomer().getAlamat())||
                        checkColumn(temp.getPenjualanHead().getNamaProyek())||
                        checkColumn(temp.getPenjualanHead().getLokasiPengerjaan())||
                        checkColumn(temp.getJenisKendaraan())||
                        checkColumn(temp.getNoPolisi())||
                        checkColumn(temp.getSupir())||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void newPengiriman(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengiriman.fxml");
        NewPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPengiriman();
    }
    private void lihatDetailPengiriman(PengirimanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPengiriman.fxml");
        NewPengirimanController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPengiriman(p.getNoPengiriman());
    }
    private void batalPengiriman(PengirimanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal Pengiriman "+p.getNoPengiriman()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        if(p.getPenjualanHead().getStatus().equals("open")){
                            List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                                    con, p.getNoPenjualan(), "%", "true");
                            List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                                    con, p.getNoPenjualan(), "%", "true");
                            List<PengirimanDetail> allDetail = PengirimanDetailDAO.getAllByNoPengiriman(con, p.getNoPengiriman());
                            p.setAllDetail(allDetail);
                            String status = "true";
                            for(PengirimanDetail d : p.getAllDetail()){
                                double qty = 0;
                                for(PengirimanDetail detail : listPengiriman){
                                    if(d.getNoUrutPenjualan().equals(detail.getNoUrutPenjualan())&&
                                        d.getKodeBarang().equals(detail.getKodeBarang()))
                                        qty = qty + detail.getQty();
                                }
                                for(ReturDetail detail : listRetur){
                                    if(d.getNoUrutPenjualan().equals(detail.getNoUrutPenjualan())&&
                                        d.getKodeBarang().equals(detail.getKodeBarang()))
                                        qty = qty - detail.getQty();
                                }
                                if(d.getQty()>qty)
                                    status = "Qty "+d.getNamaBarang()+" yang akan dibatal melebihi yang sudah di kirim & retur";
                            }
                            if(status.equals("true")){
                                p.setStatus("false");
                                p.setUserBatal(sistem.getUser().getUsername());
                                p.setTglBatal(tglSql.format(new Date()));
                                return Service.saveBatalPengiriman(con, p);
                            }else
                                return status;
                        }else
                            return "Proyek sudah selesai, tidak dapat dibatalkan";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Pengiriman berhasil dibatalkan");
                    getPengiriman();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void printSuratJalan(PengirimanHead p){
        try(Connection con = Koneksi.getConnection()){
            List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
            List<Satuan> listSatuan = SatuanDAO.getAll(con);
            List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPengiriman(con, p.getNoPengiriman());
            for(PengirimanDetail d : listPengiriman){
                for(Barang b : listBarang){
                    if(d.getKodeBarang().equals(b.getKodeBarang()))
                        d.setBarang(b);
                }
                List<Satuan> satuan = new ArrayList<>();
                for(Satuan s : listSatuan){
                    if(d.getKodeBarang().equals(s.getKodeBarang()))
                        satuan.add(s);
                }
                d.getBarang().setAllSatuan(satuan);
                double qty = d.getQty();
                String sat = d.getBarang().getSatuanDasar();
                for(Satuan s : d.getBarang().getAllSatuan()){
                    if(s.getKodeSatuan().equals(d.getSatuan())){
                        qty = qty / s.getQty();
                        sat = d.getSatuan();
                    }
                }
                d.setSatuan(sat);
                d.setQty(qty);
            }
            p.setAllDetail(listPengiriman);
            Report report = new Report();
            report.printSuratJalan(p);
        }catch (Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
