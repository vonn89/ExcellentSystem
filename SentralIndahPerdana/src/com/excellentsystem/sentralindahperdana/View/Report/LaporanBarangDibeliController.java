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
public class LaporanBarangDibeliController  {

    @FXML private TableView<PembelianDetail> pembelianDetailTable;
    @FXML private TableColumn<PembelianDetail, String> noPembelianColumn;
    @FXML private TableColumn<PembelianDetail, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianDetail, String> namaSupplierColumn;
    @FXML private TableColumn<PembelianDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> namaBarangColumn;
    @FXML private TableColumn<PembelianDetail, String> satuanColumn;
    @FXML private TableColumn<PembelianDetail, Number> qtyColumn;
    @FXML private TableColumn<PembelianDetail, Number> hargaBeliColumn;
    @FXML private TableColumn<PembelianDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private Label totalPembelianField;
    @FXML private DatePicker tglPembelianMulaiPicker;
    @FXML private DatePicker tglPembelianAkhirPicker;
    private ObservableList<PembelianDetail> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getPembelianHead().getSupplier().namaProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPembelianHead().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
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
        tglPembelianMulaiPicker.setConverter(Function.getTglConverter());
        tglPembelianMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPembelianMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPembelianAkhirPicker));
        tglPembelianAkhirPicker.setConverter(Function.getTglConverter());
        tglPembelianAkhirPicker.setValue(LocalDate.now());
        tglPembelianAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPembelianMulaiPicker));
        
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianDetail> change) -> {
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
        pembelianDetailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembelian();
        pembelianDetailTable.setItems(filterData);
    } 
    @FXML
    private void getPembelian(){
        Task<List<PembelianDetail>> task = new Task<List<PembelianDetail>>() {
            @Override 
            public List<PembelianDetail> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PembelianHead> head = PembelianHeadDAO.getAllByDateAndStatus(con, 
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(),"true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianDetail> temp = PembelianDetailDAO.getAllByDateAndStatus(con, 
                            tglPembelianMulaiPicker.getValue().toString(), tglPembelianAkhirPicker.getValue().toString(),"true");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(PembelianDetail p : temp){
                        for(PembelianHead pb : head){
                            if(pb.getNoPembelian().equals(p.getNoPembelian()))
                                p.setPembelianHead(pb);
                        }
                        for(Supplier s : allSupplier){
                            if(p.getPembelianHead().getKodeSupplier().equals(s.getKodeSupplier()))
                                p.getPembelianHead().setSupplier(s);
                        }
                        for(Barang b : listBarang){
                            if(p.getKodeBarang().equals(b.getKodeBarang()))
                                p.setBarang(b);
                        }
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(p.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        p.getBarang().setAllSatuan(satuan);
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
    private void searchPembelian(){
        try{
            filterData.clear();
            for (PembelianDetail temp : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPembelian())||
                        checkColumn(temp.getSatuan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPembelianHead().getTglPembelian())))||
                        checkColumn(temp.getPembelianHead().getSupplier().getNama())||
                        checkColumn(temp.getPembelianHead().getCatatan())||
                        checkColumn(temp.getKodeBarang())||
                        checkColumn(df.format(temp.getHarga()))||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(df.format(temp.getHarga()*temp.getQty())))
                        filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    private void hitungTotal(){
        double totalQty = 0;
        double totalBeli = 0;
        for(PembelianDetail temp : filterData){
            totalQty = totalQty + temp.getQty();
            totalBeli = totalBeli + (temp.getQty()*temp.getHarga());
        }
        totalQtyField.setText(df.format(totalQty));
        totalPembelianField.setText(df.format(totalBeli));
    }
    private void print(){
        try{
            allPembelian.sort(Comparator.comparing(PembelianDetail::getNamaBarang));
            List<PembelianDetail> listPembelianDetail = new ArrayList<>();
            for(PembelianDetail d : allPembelian){
                String satuan = d.getBarang().getSatuanDasar();
                for(Satuan s : d.getBarang().getAllSatuan()){
                    if(d.getSatuan().equals(s.getKodeSatuan())){
                        d.setQty(d.getQty()/s.getQty());
                        d.setHarga(d.getHarga()*s.getQty());
                        satuan = s.getKodeSatuan();
                    }
                }
                d.setSatuan(satuan);
                listPembelianDetail.add(d);
            }
            Report report = new Report();
            report.printLaporanBarangDibeli(listPembelianDetail, tglPembelianMulaiPicker.getValue().toString(),
                    tglPembelianAkhirPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
