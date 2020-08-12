/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
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
public class LaporanBarangDikirimController  {

    @FXML private TableView<PengirimanDetail> pengirimanDetailTable;
    @FXML private TableColumn<PengirimanDetail, String> noPengirimanColumn;
    @FXML private TableColumn<PengirimanDetail, String> tglPengirimanColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaCustomerColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaProyekColumn;
    @FXML private TableColumn<PengirimanDetail, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PengirimanDetail, String> keteranganPekerjaanColumn;
    @FXML private TableColumn<PengirimanDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> satuanColumn;
    @FXML private TableColumn<PengirimanDetail, Number> qtyColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private DatePicker tglPengirimanMulaiPicker;
    @FXML private DatePicker tglPengirimanAkhirPicker;
    private ObservableList<PengirimanDetail> allPengiriman = FXCollections.observableArrayList();
    private ObservableList<PengirimanDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noPengirimanColumn.setCellValueFactory(cellData -> cellData.getValue().noPengirimanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getPengirimanHead().getPenjualanHead().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getPengirimanHead().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPengirimanHead().getPenjualanHead().lokasiPengerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().namaPekerjaanProperty());
        keteranganPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().keteranganProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        tglPengirimanColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getPengirimanHead().getTglPengiriman())));
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
        
        tglPengirimanMulaiPicker.setConverter(Function.getTglConverter());
        tglPengirimanMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglPengirimanMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglPengirimanAkhirPicker));
        tglPengirimanAkhirPicker.setConverter(Function.getTglConverter());
        tglPengirimanAkhirPicker.setValue(LocalDate.now());
        tglPengirimanAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglPengirimanMulaiPicker));
        
        allPengiriman.addListener((ListChangeListener.Change<? extends PengirimanDetail> change) -> {
            searchPengiriman();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPengiriman();
        });
        filterData.addAll(allPengiriman);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPengiriman();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        pengirimanDetailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPengiriman();
        pengirimanDetailTable.setItems(filterData);
    } 
    @FXML
    private void getPengiriman(){
        Task<List<PengirimanDetail>> task = new Task<List<PengirimanDetail>>() {
            @Override 
            public List<PengirimanDetail> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PengirimanHead> listPengirimanHead = PengirimanHeadDAO.getAllByDateAndStatus(con, 
                            tglPengirimanMulaiPicker.getValue().toString(), tglPengirimanAkhirPicker.getValue().toString(),"true");
                    List<PengirimanDetail> listPengirimanDetail = PengirimanDetailDAO.getAllByDateAndStatus(con, 
                            tglPengirimanMulaiPicker.getValue().toString(), tglPengirimanAkhirPicker.getValue().toString(),"true");
                    List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAll(con);
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(PengirimanDetail p : listPengirimanDetail){
                        for(PengirimanHead pb : listPengirimanHead){
                            if(pb.getNoPengiriman().equals(p.getNoPengiriman()))
                                p.setPengirimanHead(pb);
                        }
                        for(PenjualanDetail d : listPenjualanDetail){
                            if(p.getPengirimanHead().getNoPenjualan().equals(d.getNoPenjualan())&&
                                    p.getNoUrutPenjualan().equals(d.getNoUrut())){
                                p.setPenjualanDetail(d);
                            }
                        }
                        for(PenjualanHead pj : listPenjualanHead){
                            if(p.getPengirimanHead().getNoPenjualan().equals(pj.getNoPenjualan()))
                                p.getPengirimanHead().setPenjualanHead(pj);
                        }
                        for(Customer c : listCustomer){
                            if(p.getPengirimanHead().getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                p.getPengirimanHead().getPenjualanHead().setCustomer(c);
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
                    return listPengirimanDetail;
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
    private void searchPengiriman(){
        try{
            filterData.clear();
            for (PengirimanDetail temp : allPengiriman) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPengiriman())||
                        checkColumn(temp.getSatuan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getPengirimanHead().getTglPengiriman())))||
                        checkColumn(temp.getPengirimanHead().getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getPengirimanHead().getCatatan())||
                        checkColumn(temp.getKodeBarang())||
                        checkColumn(temp.getNamaBarang())||
                        checkColumn(df.format(temp.getQty()))
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
        double totalQty = 0;
        for(PengirimanDetail temp : filterData){
            totalQty = totalQty + temp.getQty();
        }
        totalQtyField.setText(df.format(totalQty));
    }
    private void print(){
        try{
            allPengiriman.sort(Comparator.comparing(PengirimanDetail::getNamaBarang));
            List<PengirimanDetail> listPengirimanDetail = new ArrayList<>();
            for(PengirimanDetail d : allPengiriman){
                String satuan = d.getBarang().getSatuanDasar();
                for(Satuan s : d.getBarang().getAllSatuan()){
                    if(d.getSatuan().equals(s.getKodeSatuan())){
                        d.setQty(d.getQty()/s.getQty());
                        satuan = s.getKodeSatuan();
                    }
                }
                d.setSatuan(satuan);
                listPengirimanDetail.add(d);
            }
            Report report = new Report();
            report.printLaporanBarangDikirim(listPengirimanDetail, tglPengirimanMulaiPicker.getValue().toString(),
                    tglPengirimanAkhirPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
