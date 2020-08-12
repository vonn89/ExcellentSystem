/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
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
 * @author excellent
 */
public class LaporanBebanPenjualanTerbayarController  {

    @FXML private TableView<BebanPenjualan> bebanPenjualanTable;
    @FXML private TableColumn<BebanPenjualan, String> noBebanPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> tglBebanPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> noPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> tglPenjualanColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaCustomerColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaProyekColumn;
    @FXML private TableColumn<BebanPenjualan, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<BebanPenjualan, String> namaPekerjaanColumn;
    @FXML private TableColumn<BebanPenjualan, String> keteranganPekerjaanColumn;
    @FXML private TableColumn<BebanPenjualan, String> kategoriColumn;
    @FXML private TableColumn<BebanPenjualan, String> keteranganColumn;
    @FXML private TableColumn<BebanPenjualan, Number> jumlahRpColumn;
    @FXML private TableColumn<BebanPenjualan, String> tipeKeuanganColumn;
    @FXML private TableColumn<BebanPenjualan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label totalField;
    @FXML private DatePicker tglPembayaranMulaiPicker;
    @FXML private DatePicker tglPembayaranAkhirPicker;
    private ObservableList<BebanPenjualan> allBebanPenjualan = FXCollections.observableArrayList();
    private ObservableList<BebanPenjualan> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noBebanPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noBebanPenjualanProperty());
        tglBebanPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglBebanPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPenjualanDetail().getPenjualanHead().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().getPenjualanHead().lokasiPengerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().namaPekerjaanProperty());
        keteranganPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().keteranganProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTableCell());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
        tglPembayaranMulaiPicker.setConverter(Function.getTglConverter());
        tglPembayaranMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPembayaranMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPembayaranAkhirPicker));
        tglPembayaranAkhirPicker.setConverter(Function.getTglConverter());
        tglPembayaranAkhirPicker.setValue(LocalDate.now());
        tglPembayaranAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPembayaranMulaiPicker));
        
        allBebanPenjualan.addListener((ListChangeListener.Change<? extends BebanPenjualan> change) -> {
            searchBebanPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBebanPenjualan();
        });
        filterData.addAll(allBebanPenjualan);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBebanPenjualan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        bebanPenjualanTable.setContextMenu(rowMenu);
    }   
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getBebanPenjualan();
        bebanPenjualanTable.setItems(filterData);
    } 
    @FXML
    private void getBebanPenjualan(){
        Task<List<BebanPenjualan>> task = new Task<List<BebanPenjualan>>() {
            @Override 
            public List<BebanPenjualan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAll(con);
                    List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByDateAndStatus(con,
                        tglPembayaranMulaiPicker.getValue().toString(), tglPembayaranAkhirPicker.getValue().toString(), "true");
                    for(BebanPenjualan b : listBebanPenjualan){
                        for(PenjualanDetail d : listPenjualanDetail){
                            if(b.getNoPenjualan().equals(d.getNoPenjualan())&&
                                    b.getNoUrut().equals(d.getNoUrut()))
                                b.setPenjualanDetail(d);
                        }
                        for(PenjualanHead p : allPenjualan){
                            if(b.getNoPenjualan().equals(p.getNoPenjualan()))
                                b.getPenjualanDetail().setPenjualanHead(p);
                        }
                        for(Customer c : allCustomer){
                            if(b.getPenjualanDetail().getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                b.getPenjualanDetail().getPenjualanHead().setCustomer(c);
                        }
                    }
                    return listBebanPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allBebanPenjualan.clear();
                allBebanPenjualan.addAll(task.get());
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
    private void searchBebanPenjualan(){
        try{
            filterData.clear();
            for (BebanPenjualan temp : allBebanPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoBebanPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglBebanPenjualan())))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPenjualanDetail().getPenjualanHead().getTglPenjualan())))||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getNoPenjualan())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getNamaProyek())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getLokasiPengerjaan())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPenjualanDetail().getPenjualanHead().getCatatan())||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(temp.getKodeUser())||
                        checkColumn(temp.getTipeKeuangan())||
                        checkColumn(df.format(temp.getJumlahRp()))
                    )
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
        for(BebanPenjualan temp : filterData){
            total = total + temp.getJumlahRp();
        }
        totalField.setText(df.format(total));
    } 
    
    private void print(){
        try{
            List<BebanPenjualan> listBebanPenjualan = new ArrayList<>();
            for(BebanPenjualan d : allBebanPenjualan){
                listBebanPenjualan.add(d);
            }
            Report report = new Report();
            report.printLaporanBebanPenjualanTerbayar(listBebanPenjualan, tglPembayaranMulaiPicker.getValue().toString(),
                    tglPembayaranAkhirPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
