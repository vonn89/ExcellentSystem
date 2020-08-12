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
import java.util.Comparator;
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
public class LaporanBarangTerjualController  {

    @FXML private TableView<PenjualanDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanDetail, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanDetail, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaProyekColumn;
    @FXML private TableColumn<PenjualanDetail, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> tglMulaiColumn;
    @FXML private TableColumn<PenjualanDetail, String> tglSelesaiColumn;
    @FXML private TableColumn<PenjualanDetail, String> catatanColumn;
    
    @FXML private TableColumn<PenjualanDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> satuanColumn;
    @FXML private TableColumn<PenjualanDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanDetail, Number> hargaJualColumn;
    @FXML private TableColumn<PenjualanDetail, Number> totalColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private Label totalPenjualanField;
    @FXML private DatePicker tglPenjualanMulaiPicker;
    @FXML private DatePicker tglPenjualanAkhirPicker;
    private ObservableList<PenjualanDetail> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().lokasiPengerjaanProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanHead().catatanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPenjualanHead().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglMulaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getPenjualanHead().getTglMulai())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSelesaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getPenjualanHead().getTglSelesai())));
            } catch (Exception ex) {
                return null;
            }
        });
        
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getPekerjaan().satuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellFactory(col -> Function.getTableCell());
        tglPenjualanMulaiPicker.setConverter(Function.getTglConverter());
        tglPenjualanMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPenjualanMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPenjualanAkhirPicker));
        tglPenjualanAkhirPicker.setConverter(Function.getTglConverter());
        tglPenjualanAkhirPicker.setValue(LocalDate.now());
        tglPenjualanAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPenjualanMulaiPicker));
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanDetail> change) -> {
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
        penjualanDetailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPenjualan();
        penjualanDetailTable.setItems(filterData);
    } 
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanDetail>> task = new Task<List<PenjualanDetail>>() {
            @Override 
            public List<PenjualanDetail> call()throws Exception {
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanHead> penjualan = PenjualanHeadDAO.getAllByTglPenjualan(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString());
                    List<PenjualanDetail> temp = PenjualanDetailDAO.getAllByTglPenjualan(con, 
                            tglPenjualanMulaiPicker.getValue().toString(),tglPenjualanAkhirPicker.getValue().toString());
                    List<Customer> customer = CustomerDAO.getAllByStatus(con, "%");
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    for(PenjualanDetail d : temp){
                        for(PenjualanHead h:penjualan){
                            if(d.getNoPenjualan().equals(h.getNoPenjualan()))
                                d.setPenjualanHead(h);
                        }
                        for(Customer c : customer){
                            if(d.getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                d.getPenjualanHead().setCustomer(c);
                        }
                        for(Pekerjaan pk : listPekerjaan){
                            if(d.getKodePekerjaan().equals(pk.getKodePekerjaan()))
                                d.setPekerjaan(pk);
                        }
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
    private void searchPenjualan(){
        try{
            filterData.clear();
            for (PenjualanDetail temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualanHead().getTglPenjualan())))||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getPenjualanHead().getTglMulai())))||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getPenjualanHead().getTglSelesai())))||
                        checkColumn(temp.getPenjualanHead().getKodeCustomer())||
                        checkColumn(temp.getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanHead().getNamaProyek())||
                        checkColumn(temp.getPenjualanHead().getLokasiPengerjaan())||
                        checkColumn(temp.getPenjualanHead().getCatatan())||
                        checkColumn(temp.getKodePekerjaan())||
                        checkColumn(temp.getNamaPekerjaan())||
                        checkColumn(df.format(temp.getHarga()))||
                        checkColumn(df.format(temp.getQty()))||
                        checkColumn(temp.getPekerjaan().getSatuan())||
                        checkColumn(df.format(temp.getTotal())))
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
        double totalJual = 0;
        for(PenjualanDetail temp : filterData){
            totalQty = totalQty + temp.getQty();
            totalJual = totalJual + temp.getTotal();
        }
        totalQtyField.setText(df.format(totalQty));
        totalPenjualanField.setText(df.format(totalJual));
    }
    private void print(){
        try{
            allPenjualan.sort(Comparator.comparing(PenjualanDetail::getNamaPekerjaan));
            List<PenjualanDetail> listPenjualanDetail = new ArrayList<>();
            for(PenjualanDetail d : allPenjualan){
                listPenjualanDetail.add(d);
            }
            Report report = new Report();
            report.printLaporanItemTerjual(listPenjualanDetail, tglPenjualanMulaiPicker.getValue().toString(),
                    tglPenjualanAkhirPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
