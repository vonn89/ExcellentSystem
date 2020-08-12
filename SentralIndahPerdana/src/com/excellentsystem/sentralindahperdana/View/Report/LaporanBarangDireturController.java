/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturHeadDAO;
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
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturHead;
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
public class LaporanBarangDireturController  {

    @FXML private TableView<ReturDetail> returDetailTable;
    @FXML private TableColumn<ReturDetail, String> noReturColumn;
    @FXML private TableColumn<ReturDetail, String> tglReturColumn;
    @FXML private TableColumn<ReturDetail, String> namaCustomerColumn;
    @FXML private TableColumn<ReturDetail, String> namaProyekColumn;
    @FXML private TableColumn<ReturDetail, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<ReturDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<ReturDetail, String> keteranganPekerjaanColumn;
    @FXML private TableColumn<ReturDetail, String> kodeBarangColumn;
    @FXML private TableColumn<ReturDetail, String> namaBarangColumn;
    @FXML private TableColumn<ReturDetail, String> satuanColumn;
    @FXML private TableColumn<ReturDetail, Number> qtyColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyField;
    @FXML private DatePicker tglReturMulaiPicker;
    @FXML private DatePicker tglReturAkhirPicker;
    private ObservableList<ReturDetail> allRetur = FXCollections.observableArrayList();
    private ObservableList<ReturDetail> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noReturColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        tglReturColumn.setCellValueFactory(cellData -> { 
            try {
                return  new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getReturHead().getTglRetur())));
            } catch (Exception ex) {
                return null;
            }
        });
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getReturHead().getPenjualanHead().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getReturHead().getPenjualanHead().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getReturHead().getPenjualanHead().lokasiPengerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().namaPekerjaanProperty());
        keteranganPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getPenjualanDetail().keteranganProperty());
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
        
        tglReturMulaiPicker.setConverter(Function.getTglConverter());
        tglReturMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglReturMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglReturAkhirPicker));
        tglReturAkhirPicker.setConverter(Function.getTglConverter());
        tglReturAkhirPicker.setValue(LocalDate.now());
        tglReturAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglReturMulaiPicker));
        
        allRetur.addListener((ListChangeListener.Change<? extends ReturDetail> change) -> {
            searchRetur();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchRetur();
        });
        filterData.addAll(allRetur);
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getRetur();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        returDetailTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getRetur();
        returDetailTable.setItems(filterData);
    } 
    @FXML
    private void getRetur(){
        Task<List<ReturDetail>> task = new Task<List<ReturDetail>>() {
            @Override 
            public List<ReturDetail> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<ReturHead> listReturHead = ReturHeadDAO.getAllByTanggalAndStatus(con, 
                            tglReturMulaiPicker.getValue().toString(), tglReturAkhirPicker.getValue().toString(),"true");
                    List<ReturDetail> listReturDetail = ReturDetailDAO.getAllByDateAndStatus(con, 
                            tglReturMulaiPicker.getValue().toString(), tglReturAkhirPicker.getValue().toString(),"true");
                    List<PenjualanHead> listPenjualanHead = PenjualanHeadDAO.getAll(con);
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAll(con);
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(ReturDetail p : listReturDetail){
                        for(ReturHead pb : listReturHead){
                            if(pb.getNoRetur().equals(p.getNoRetur()))
                                p.setReturHead(pb);
                        }
                        for(PenjualanDetail d : listPenjualanDetail){
                            if(p.getReturHead().getNoPenjualan().equals(d.getNoPenjualan())&&
                                    p.getNoUrutPenjualan().equals(d.getNoUrut()))
                                p.setPenjualanDetail(d);
                        }
                        for(PenjualanHead pj : listPenjualanHead){
                            if(p.getReturHead().getNoPenjualan().equals(pj.getNoPenjualan()))
                                p.getReturHead().setPenjualanHead(pj);
                        }
                        for(Customer c : listCustomer){
                            if(p.getReturHead().getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                p.getReturHead().getPenjualanHead().setCustomer(c);
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
                    return listReturDetail;
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
    private void searchRetur(){
        try{
            filterData.clear();
            for (ReturDetail temp : allRetur) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRetur())||
                        checkColumn(temp.getSatuan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getReturHead().getTglRetur())))||
                        checkColumn(temp.getReturHead().getPenjualanHead().getCustomer().getNama())||
                        checkColumn(temp.getReturHead().getCatatan())||
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
        for(ReturDetail temp : filterData){
            totalQty = totalQty + temp.getQty();
        }
        totalQtyField.setText(df.format(totalQty));
    }
    private void print(){
        try{
            allRetur.sort(Comparator.comparing(ReturDetail::getNamaBarang));
            List<ReturDetail> listReturDetail = new ArrayList<>();
            for(ReturDetail d : allRetur){
                String satuan = d.getBarang().getSatuanDasar();
                for(Satuan s : d.getBarang().getAllSatuan()){
                    if(d.getSatuan().equals(s.getKodeSatuan())){
                        d.setQty(d.getQty()/s.getQty());
                        satuan = s.getKodeSatuan();
                    }
                }
                d.setSatuan(satuan);
                listReturDetail.add(d);
            }
            Report report = new Report();
            report.printLaporanBarangDiretur(listReturDetail, tglReturMulaiPicker.getValue().toString(),
                    tglReturAkhirPicker.getValue().toString());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
