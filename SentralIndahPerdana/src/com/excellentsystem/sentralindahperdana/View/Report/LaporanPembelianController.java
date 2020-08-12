/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianHeadDAO;
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
import com.excellentsystem.sentralindahperdana.Model.PembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
public class LaporanPembelianController  {

    @FXML private TableView<PembelianHead> pembelianTable;
    @FXML private TableColumn<PembelianHead, String> noPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> namaSupplierColumn;
    @FXML private TableColumn<PembelianHead, Number> totalPembelianColumn;
    @FXML private TableColumn<PembelianHead, Number> pembayaranColumn;
    @FXML private TableColumn<PembelianHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PembelianHead, String> catatanColumn;
    @FXML private TableColumn<PembelianHead, String> kodeUserColumn;
    
    @FXML private TableView<PembelianDetail> detailTable;
    @FXML private TableColumn<PembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> satuanColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPembelianField;
    @FXML private Label totalPembayaranField;
    @FXML private Label sisaPembayaranField;
    @FXML private DatePicker tglMulaiPembelianPicker;
    @FXML private DatePicker tglAkhirPembelianPicker;
    
    private ObservableList<PembelianHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianHead> filterData = FXCollections.observableArrayList();
    private ObservableList<PembelianDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
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
        hargaColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getQty()* cellData.getValue().getHarga());
        });
        totalColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPembelianPicker.setConverter(Function.getTglConverter());
        tglMulaiPembelianPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPembelianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPembelianPicker));
        tglAkhirPembelianPicker.setConverter(Function.getTglConverter());
        tglAkhirPembelianPicker.setValue(LocalDate.now());
        tglAkhirPembelianPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPembelianPicker));
        
        pembelianTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectPembelian(newValue));
        
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        pembelianTable.setContextMenu(rowMenu);
        detailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembelian();
        pembelianTable.setItems(filterData);
        detailTable.setItems(allDetail);
    }
    @FXML
    private void getPembelian(){
        Task<List<PembelianHead>> task = new Task<List<PembelianHead>>() {
            @Override 
            public List<PembelianHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    String tglMulai = tglMulaiPembelianPicker.getValue().toString();
                    String tglAkhir = tglAkhirPembelianPicker.getValue().toString();
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianDetail> allDetail = PembelianDetailDAO.getAllByDateAndStatus(
                            con, tglMulai, tglAkhir, "true");
                    List<PembelianHead> temp = PembelianHeadDAO.getAllByDateAndStatus(
                            con, tglMulai, tglAkhir, "true");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(PembelianHead p : temp){
                        for(Supplier s : allSupplier){
                            if(p.getKodeSupplier().equals(s.getKodeSupplier()))
                                p.setSupplier(s);
                        }
                        List<PembelianDetail> detail = new ArrayList<>();
                        for(PembelianDetail d :allDetail){
                            if(p.getNoPembelian().equals(d.getNoPembelian())){
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
                        p.setAllDetail(detail);
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allPembelian.clear();
                allPembelian.addAll(task.get());
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
    private void searchPembelian() {
        try{
            filterData.clear();
            for (PembelianHead temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPembelian())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPembelian())))||
                        checkColumn(temp.getKodeSupplier())||
                        checkColumn(temp.getSupplier().getNama())||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getTotalPembelian()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                    else{
                        Boolean status = false;
                        for(PembelianDetail detail : temp.getAllDetail()){
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
    private void selectPembelian(PembelianHead value){
        allDetail.clear();
        if(value!=null)
            allDetail.addAll(value.getAllDetail());
    }  
    private void hitungTotal(){
        double totalPembelian=0;
        double sisaPembayaran=0;
        double totalPembayaran=0;
        for(PembelianHead temp : filterData){
            totalPembelian = totalPembelian + temp.getTotalPembelian();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
        }
        totalPembelianField.setText(df.format(totalPembelian));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
        totalPembayaranField.setText(df.format(totalPembayaran));
    }
    private void print(){
        try{
            allPembelian.sort(Comparator.comparing(PembelianHead::getKodeSupplier));
            List<PembelianHead> listPembelianHead = new ArrayList<>();
            for(PembelianHead p : allPembelian){
                for(PembelianDetail d : p.getAllDetail()){
                    String satuan = d.getBarang().getSatuanDasar();
                    for(Satuan s : d.getBarang().getAllSatuan()){
                        if(d.getSatuan().equals(s.getKodeSatuan())){
                            d.setQty(d.getQty()/s.getQty());
                            d.setHarga(d.getHarga()*s.getQty());
                            satuan = s.getKodeSatuan();
                        }
                    }
                    d.setSatuan(satuan);
                }
                listPembelianHead.add(p);
            }
            Report report = new Report();
            report.printLaporanPembelian(listPembelianHead, tglMulaiPembelianPicker.getValue().toString(),
                    tglAkhirPembelianPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
