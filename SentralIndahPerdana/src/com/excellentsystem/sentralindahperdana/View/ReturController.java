/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanMaterialDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturHead;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewReturController;
import java.sql.Connection;
import java.time.LocalDate;
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
public class ReturController  {

    @FXML private TableView<ReturHead> returTable;
    @FXML private TableColumn<ReturHead, String> noReturColumn;
    @FXML private TableColumn<ReturHead, String> tglReturColumn;
    @FXML private TableColumn<ReturHead, String> kodeCustomerColumn;
    @FXML private TableColumn<ReturHead, String> namaCustomerColumn;
    @FXML private TableColumn<ReturHead, String> alamatCustomerColumn;
    @FXML private TableColumn<ReturHead, String> namaProyekColumn;
    @FXML private TableColumn<ReturHead, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<ReturHead, String> penerimaColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<ReturHead> allRetur = FXCollections.observableArrayList();
    private ObservableList<ReturHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noReturColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        kodeCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().kodeCustomerProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().getCustomer().namaProperty());
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().getCustomer().alamatProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().lokasiPengerjaanProperty());
        penerimaColumn.setCellValueFactory(cellData -> cellData.getValue().penerimaProperty());
        tglReturColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRetur())));
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
        MenuItem addNew = new MenuItem("Add New Retur");
        addNew.setOnAction((ActionEvent e)->{
            newRetur();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getRetur();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Retur")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        returTable.setContextMenu(rm);
        returTable.setRowFactory((TableView<ReturHead> tableView) -> {
            final TableRow<ReturHead> row = new TableRow<ReturHead>(){
                @Override
                public void updateItem(ReturHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Retur");
                        addNew.setOnAction((ActionEvent e)->{
                            newRetur();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Retur");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailRetur(item);
                        });
                        MenuItem batal = new MenuItem("Batal Retur");
                        batal.setOnAction((ActionEvent e)->{
                            batalRetur(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getRetur();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Retur")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Retur")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Retur")&&o.isStatus())
                                rm.getItems().add(batal);
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
                            if(o.getJenis().equals("Lihat Detail Retur")&&o.isStatus())
                                lihatDetailRetur(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allRetur.addListener((ListChangeListener.Change<? extends ReturHead> change) -> {
            searchRetur();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchRetur();
        });
        filterData.addAll(allRetur);
        returTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getRetur();
    }
    @FXML
    private void getRetur(){
        Task<List<ReturHead>> task = new Task<List<ReturHead>>() {
            @Override 
            public List<ReturHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanHead> listPenjualan = PenjualanHeadDAO.getAll(con);
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<ReturHead> allRetur = ReturHeadDAO.getAllByTanggalAndStatus(con, 
                        tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for(ReturHead p : allRetur){
                        for(PenjualanHead pj : listPenjualan){
                            if(p.getNoPenjualan().equals(pj.getNoPenjualan()))
                                p.setPenjualanHead(pj);
                        }
                        for(Customer c: allCustomer){
                            if(p.getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                p.getPenjualanHead().setCustomer(c);
                        }
                    }
                    return allRetur;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allRetur.clear();
                allRetur.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
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
    private void searchRetur() {
        try{
            filterData.clear();
            for (ReturHead temp : allRetur) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRetur())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglRetur())))||
                        checkColumn(temp.getPenjualanHead().getKodeCustomer())||
                        checkColumn(temp.getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanHead().getCustomer().getAlamat())||
                        checkColumn(temp.getPenjualanHead().getNamaProyek())||
                        checkColumn(temp.getPenjualanHead().getLokasiPengerjaan())||
                        checkColumn(temp.getPenerima())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void newRetur(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewRetur.fxml");
        NewReturController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewRetur();
    }
    private void lihatDetailRetur(ReturHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewRetur.fxml");
        NewReturController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailRetur(p.getNoRetur());
    }
    private void batalRetur(ReturHead r){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal Retur "+r.getNoRetur()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        if(r.getPenjualanHead().getStatus().equals("open")){
                        List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                                con, r.getNoPenjualan(), "%", "true");
                        List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                                con, r.getNoPenjualan(), "%", "true");
                        List<RencanaAnggaranBebanMaterial> rab = RencanaAnggaranBebanMaterialDAO.getAllByNoPenjualanAndNoUrut(
                                con, r.getNoPenjualan(), "%");
                        List<ReturDetail> allDetail = ReturDetailDAO.getAllByNoRetur(con, r.getNoRetur());
                        r.setAllDetail(allDetail);
                        String status = "true";
                        for(ReturDetail d : r.getAllDetail()){
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
                            qty = qty + d.getQty();
                            double qtyAnggaran = 0;
                            for(RencanaAnggaranBebanMaterial r : rab){
                                if(d.getNoUrutPenjualan().equals(r.getNoUrut())&&
                                    d.getKodeBarang().equals(r.getKodeBarang())){
                                    qtyAnggaran = qtyAnggaran + r.getQty();
                                }
                            }
                            if(qty>qtyAnggaran)
                                status = "Qty "+d.getNamaBarang()+" yang akan dibatal melebihi anggaran belanja";
                        }
                        if(status.equals("true")){
                            r.setStatus("false");
                            r.setUserBatal(sistem.getUser().getUsername());
                            r.setTglBatal(tglSql.format(new Date()));
                            return Service.saveBatalRetur(con, r);
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
                    mainApp.showMessage(Modality.NONE, "Success", "Retur berhasil dibatalkan");
                    getRetur();
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
}
