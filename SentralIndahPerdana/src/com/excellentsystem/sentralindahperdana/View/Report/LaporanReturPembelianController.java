/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LaporanReturPembelianController {

    @FXML private TableView<ReturPembelianHead> returPembelianTable;
    @FXML private TableColumn<ReturPembelianHead, String> noReturPembelianColumn;
    @FXML private TableColumn<ReturPembelianHead, String> tglReturColumn;
    @FXML private TableColumn<ReturPembelianHead, String> kodeSupplierColumn;
    @FXML private TableColumn<ReturPembelianHead, String> namaSupplierColumn;
    @FXML private TableColumn<ReturPembelianHead, Number> totalReturColumn;
    @FXML private TableColumn<ReturPembelianHead, String> tipeKeuanganColumn;
    @FXML private TableColumn<ReturPembelianHead, String> catatanColumn;
    @FXML private TableColumn<ReturPembelianHead, String> kodeUserColumn;
    
    @FXML private TableView<ReturPembelianDetail> detailTable;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> satuanColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> hargaBeliColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalReturField;
    @FXML private DatePicker tglMulaiReturPicker;
    @FXML private DatePicker tglAkhirReturPicker;
    
    private ObservableList<ReturPembelianHead> allReturPembelian = FXCollections.observableArrayList();
    private ObservableList<ReturPembelianHead> filterData = FXCollections.observableArrayList();
    private ObservableList<ReturPembelianDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noReturPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        kodeSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSupplierProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglReturColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRetur())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalReturColumn.setCellValueFactory(cellData -> cellData.getValue().totalReturProperty());
        totalReturColumn.setCellFactory(col -> Function.getTableCell());
        
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        qtyColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        hargaBeliColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHarga();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaBeliColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty()* cellData.getValue().getHarga());
        });
        totalColumn.setCellFactory(col -> Function.getTableCell());
        
        
        tglMulaiReturPicker.setConverter(Function.getTglConverter());
        tglMulaiReturPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiReturPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirReturPicker));
        tglAkhirReturPicker.setConverter(Function.getTglConverter());
        tglAkhirReturPicker.setValue(LocalDate.now());
        tglAkhirReturPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiReturPicker));
        
        returPembelianTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectReturPembelian(newValue));
        
        allReturPembelian.addListener((ListChangeListener.Change<? extends ReturPembelianHead> change) -> {
            searchReturPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchReturPembelian();
        });
        filterData.addAll(allReturPembelian);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getReturPembelian();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        returPembelianTable.setContextMenu(rowMenu);
        detailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getReturPembelian();
        returPembelianTable.setItems(filterData);
        detailTable.setItems(allDetail);
    }
    @FXML
    private void getReturPembelian(){
        Task<List<ReturPembelianHead>> task = new Task<List<ReturPembelianHead>>() {
            @Override 
            public List<ReturPembelianHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<ReturPembelianHead> temp = ReturPembelianHeadDAO.getAllByTanggalAndStatus(
                        con, tglMulaiReturPicker.getValue().toString(), tglAkhirReturPicker.getValue().toString(),"true");
                    List<ReturPembelianDetail> allDetail = ReturPembelianDetailDAO.getAllByDateAndStatus(
                        con, tglMulaiReturPicker.getValue().toString(), tglAkhirReturPicker.getValue().toString(),"true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(ReturPembelianHead r : temp){
                        for(Supplier s : allSupplier){
                            if(s.getKodeSupplier().equals(r.getKodeSupplier()))
                                r.setSupplier(s);
                        }
                        List<ReturPembelianDetail> detail = new ArrayList<>();
                        for(ReturPembelianDetail d: allDetail){
                            if(r.getNoRetur().equals(d.getNoRetur())){
                                detail.add(d);
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
                            }
                        }
                        r.setAllDetail(detail);
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
            try{
                allReturPembelian.clear();
                allReturPembelian.addAll(task.get());
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
    private void searchReturPembelian() {
        try{
            filterData.clear();
            for (ReturPembelianHead temp : allReturPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRetur())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglRetur())))||
                        checkColumn(temp.getKodeSupplier())||
                        checkColumn(temp.getSupplier().getNama())||
                        checkColumn(df.format(temp.getTotalRetur()))||
                        checkColumn(temp.getTipeKeuangan())||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                    else{
                        Boolean status = false;
                        for(ReturPembelianDetail detail : temp.getAllDetail()){
                            if(checkColumn(detail.getKodeBarang())||
                                checkColumn(detail.getNamaBarang())||
                                checkColumn(detail.getSatuan())||
                                checkColumn(df.format(detail.getQty()))||
                                checkColumn(df.format(detail.getHarga()))||
                                checkColumn(df.format(detail.getQty()*detail.getHarga())))
                                status=true;
                        }
                        if(status)
                            filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void selectReturPembelian(ReturPembelianHead value){
        if(value!=null){
            try{
                allDetail.clear();
                allDetail.addAll(value.getAllDetail());
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }else{
            allDetail.clear();
        }
    }  
    @FXML
    private void hitungTotal(){
        double totalRetur=0;
        for(ReturPembelianHead temp : filterData){
            totalRetur = totalRetur + temp.getTotalRetur();
        }
        totalReturField.setText(df.format(totalRetur));
    }
    private void print(){
        try{
            List<ReturPembelianDetail> listReturPembelianDetail = new ArrayList<>();
            for(ReturPembelianHead p : allReturPembelian){
                for(ReturPembelianDetail d : p.getAllDetail()){
                    String satuan = d.getBarang().getSatuanDasar();
                    for(Satuan s : d.getBarang().getAllSatuan()){
                        if(d.getSatuan().equals(s.getKodeSatuan())){
                            d.setQty(d.getQty()/s.getQty());
                            d.setHarga(d.getHarga()*s.getQty());
                            satuan = s.getKodeSatuan();
                        }
                    }
                    d.setSatuan(satuan);
                    d.setReturPembelianHead(p);
                    listReturPembelianDetail.add(d);
                }
            }
            Report report = new Report();
            report.printLaporanReturPembelian(listReturPembelianDetail, tglMulaiReturPicker.getValue().toString(),
                    tglAkhirReturPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
