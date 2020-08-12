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
public class LaporanBarangDireturBeliController {

    @FXML private TableView<ReturPembelianDetail> returPembelianTable;
    @FXML private TableColumn<ReturPembelianDetail, String> noReturPembelianColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> tglReturColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> namaSupplierColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalReturColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> tipeKeuanganColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> catatanColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeUserColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> satuanColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> hargaBeliColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalField;
    @FXML private DatePicker tglMulaiReturPicker;
    @FXML private DatePicker tglAkhirReturPicker;
    
    private ObservableList<ReturPembelianDetail> allReturPembelian = FXCollections.observableArrayList();
    private ObservableList<ReturPembelianDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noReturPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getReturPembelianHead().getSupplier().namaProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().getReturPembelianHead().catatanProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().getReturPembelianHead().tipeKeuanganProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().getReturPembelianHead().kodeUserProperty());
        tglReturColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(
                        cellData.getValue().getReturPembelianHead().getTglRetur())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalReturColumn.setCellValueFactory(cellData -> cellData.getValue().getReturPembelianHead().totalReturProperty());
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
            return new SimpleDoubleProperty(cellData.getValue().getQty()*cellData.getValue().getHarga());
        });
        totalColumn.setCellFactory(col -> Function.getTableCell());
        tglMulaiReturPicker.setConverter(Function.getTglConverter());
        tglMulaiReturPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiReturPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirReturPicker));
        tglAkhirReturPicker.setConverter(Function.getTglConverter());
        tglAkhirReturPicker.setValue(LocalDate.now());
        tglAkhirReturPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiReturPicker));
        allReturPembelian.addListener((ListChangeListener.Change<? extends ReturPembelianDetail> change) -> {
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
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getReturPembelian();
        returPembelianTable.setItems(filterData);
    }
    @FXML
    private void getReturPembelian(){
        Task<List<ReturPembelianDetail>> task = new Task<List<ReturPembelianDetail>>() {
            @Override 
            public List<ReturPembelianDetail> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<ReturPembelianHead> head = ReturPembelianHeadDAO.getAllByTanggalAndStatus(con, 
                            tglMulaiReturPicker.getValue().toString(), tglAkhirReturPicker.getValue().toString(),"true");
                    List<ReturPembelianDetail> allRetur = ReturPembelianDetailDAO.getAllByDateAndStatus(con, 
                            tglMulaiReturPicker.getValue().toString(), tglAkhirReturPicker.getValue().toString(),"true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(ReturPembelianDetail temp : allRetur){
                        for(ReturPembelianHead r :head){
                            if(r.getNoRetur().equals(temp.getNoRetur()))
                                temp.setReturPembelianHead(r);
                        }
                        for(Supplier s : allSupplier){
                            if(s.getKodeSupplier().equals(temp.getReturPembelianHead().getKodeSupplier()))
                                temp.getReturPembelianHead().setSupplier(s);
                        }
                        for(Barang b : listBarang){
                            if(temp.getKodeBarang().equals(b.getKodeBarang()))
                                temp.setBarang(b);
                        }
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(temp.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        temp.getBarang().setAllSatuan(satuan);
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
            for (ReturPembelianDetail temp : allReturPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRetur())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getReturPembelianHead().getTglRetur())))||
                        checkColumn(temp.getReturPembelianHead().getKodeSupplier())||
                        checkColumn(temp.getReturPembelianHead().getSupplier().getNama())||
                        checkColumn(df.format(temp.getReturPembelianHead().getTotalRetur()))||
                        checkColumn(temp.getReturPembelianHead().getTipeKeuangan())||
                        checkColumn(temp.getReturPembelianHead().getCatatan())||
                        checkColumn(temp.getReturPembelianHead().getKodeUser())||
                        checkColumn(temp.getKodeBarang())||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(temp.getSatuan())||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(df.format(temp.getHarga()))||
                        checkColumn(df.format(temp.getQty()*temp.getHarga())))
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
        for(ReturPembelianDetail temp : filterData){
            total = total + temp.getQty()*temp.getHarga();
        }
        totalField.setText(df.format(total));
    }
    private void print(){
        try{
            allReturPembelian.sort(Comparator.comparing(ReturPembelianDetail::getNamaBarang));
            List<ReturPembelianDetail> listReturPembelianDetail = new ArrayList<>();
            for(ReturPembelianDetail d : allReturPembelian){
                String satuan = d.getBarang().getSatuanDasar();
                for(Satuan s : d.getBarang().getAllSatuan()){
                    if(d.getSatuan().equals(s.getKodeSatuan())){
                        d.setQty(d.getQty()/s.getQty());
                        d.setHarga(d.getHarga()*s.getQty());
                        satuan = s.getKodeSatuan();
                    }
                }
                d.setSatuan(satuan);
                listReturPembelianDetail.add(d);
            }
            Report report = new Report();
            report.printLaporanBarangDiReturBeli(listReturPembelianDetail, tglMulaiReturPicker.getValue().toString(),
                    tglAkhirReturPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
