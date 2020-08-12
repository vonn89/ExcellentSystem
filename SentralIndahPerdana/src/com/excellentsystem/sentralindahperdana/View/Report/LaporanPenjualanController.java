/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
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
public class LaporanPenjualanController  {

    @FXML private TableView<PenjualanHead> penjualanTable;
    @FXML private TableColumn<PenjualanHead, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> namaProyekColumn;
    @FXML private TableColumn<PenjualanHead, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PenjualanHead, String> tglMulaiColumn;
    @FXML private TableColumn<PenjualanHead, String> tglSelesaiColumn;
    @FXML private TableColumn<PenjualanHead, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, Number> pembayaranColumn;
    @FXML private TableColumn<PenjualanHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PenjualanHead, String> catatanColumn;
    @FXML private TableColumn<PenjualanHead, String> kodeUserColumn;
    
    @FXML private TableView<PenjualanDetail> detailTable;
    @FXML private TableColumn<PenjualanDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanDetail, String> satuanColumn;
    @FXML private TableColumn<PenjualanDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML private TableColumn<PenjualanDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPenjualanField;
    @FXML private Label totalPembayaranField;
    @FXML private Label sisaPembayaranField;
    @FXML private DatePicker tglMulaiPenjualanPicker;
    @FXML private DatePicker tglAkhirPenjualanPicker;
    
    private ObservableList<PenjualanHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanHead> filterData = FXCollections.observableArrayList();
    private ObservableList<PenjualanDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().lokasiPengerjaanProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglMulaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglMulai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSelesaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglSelesai())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getPekerjaan().satuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        totalColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPenjualanPicker.setConverter(Function.getTglConverter());
        tglMulaiPenjualanPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPenjualanPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPenjualanPicker));
        tglAkhirPenjualanPicker.setConverter(Function.getTglConverter());
        tglAkhirPenjualanPicker.setValue(LocalDate.now());
        tglAkhirPenjualanPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPenjualanPicker));
        
        penjualanTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectPenjualan(newValue));
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rowMenu);
        detailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenjualan();
        penjualanTable.setItems(filterData);
        detailTable.setItems(allDetail);
    }
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanHead>> task = new Task<List<PenjualanHead>>() {
            @Override 
            public List<PenjualanHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAllByTglPenjualan(con, 
                            tglMulaiPenjualanPicker.getValue().toString(), tglAkhirPenjualanPicker.getValue().toString());
                    List<PenjualanDetail> allDetail = PenjualanDetailDAO.getAllByTglPenjualan(con, 
                            tglMulaiPenjualanPicker.getValue().toString(), tglAkhirPenjualanPicker.getValue().toString());
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pekerjaan> allPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    for(PenjualanHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        List<PenjualanDetail> detail = new ArrayList<>();
                        for(PenjualanDetail d: allDetail){
                            if(p.getNoPenjualan().equals(d.getNoPenjualan())){
                                for(Pekerjaan pk : allPekerjaan){
                                    if(d.getKodePekerjaan().equals(pk.getKodePekerjaan()))
                                        d.setPekerjaan(pk);
                                }
                                detail.add(d);
                            }
                        }
                        p.setAllDetail(detail);
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allPenjualan.clear();
                allPenjualan.addAll(task.get());
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getNamaProyek())||
                        checkColumn(temp.getLokasiPengerjaan())||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getTglMulai())||
                        checkColumn(temp.getTglSelesai())||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getTotalPenjualan()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                    else{
                        Boolean status = false;
                        for(PenjualanDetail detail : temp.getAllDetail()){
                            if(checkColumn(detail.getKodePekerjaan())||
                                checkColumn(detail.getNamaPekerjaan())||
                                checkColumn(detail.getKeterangan())||
                                checkColumn(detail.getPekerjaan().getSatuan())||
                                checkColumn(df.format(detail.getQty()))||
                                checkColumn(df.format(detail.getHarga()))||
                                checkColumn(df.format(detail.getTotal())))
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
    private void selectPenjualan(PenjualanHead value){
        allDetail.clear();
        if(value!=null)
            allDetail.addAll(value.getAllDetail());
    }  
    private void hitungTotal(){
        double totalPenjualan=0;
        double totalPembayaran=0;
        double sisaPembayaran=0;
        for(PenjualanHead temp : filterData){
            totalPenjualan = totalPenjualan + temp.getTotalPenjualan();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(totalPenjualan));
        totalPembayaranField.setText(df.format(totalPembayaran));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
    }
    private void print(){
        try{
            List<PenjualanDetail> listPenjualanDetail = new ArrayList<>();
            for(PenjualanHead p : allPenjualan){
                for(PenjualanDetail d : p.getAllDetail()){
                    d.setPenjualanHead(p);
                    listPenjualanDetail.add(d);
                }
            }
            Report report = new Report();
            report.printLaporanPenjualan(listPenjualanDetail, tglMulaiPenjualanPicker.getValue().toString(),
                    tglAkhirPenjualanPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
