/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.HutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Pembayaran;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailHutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaTandaJadiController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewHutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewPembayaranHutangController;
import java.sql.Connection;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class HutangController  {

    @FXML private TableView<Hutang> hutangTable;
    @FXML private TableColumn<Hutang, String> noHutangColumn;
    @FXML private TableColumn<Hutang, String> tglHutangColumn;
    @FXML private TableColumn<Hutang, String> tipeHutangColumn;
    @FXML private TableColumn<Hutang, String> keteranganColumn;
    @FXML private TableColumn<Hutang, Number> jumlahHutangColumn;
    @FXML private TableColumn<Hutang, Number> pembayaranColumn;
    @FXML private TableColumn<Hutang, Number> sisaHutangColumn;
    @FXML private TableColumn<Hutang, String> jatuhTempoColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalHutangField;
    @FXML private Label totalPembayaranField;
    @FXML private ComboBox<String> groupByCombo;
    
    private ObservableList<Hutang> allHutang = FXCollections.observableArrayList();
    private ObservableList<Hutang> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        noHutangColumn.setCellFactory(col -> Function.getWrapTableCell(noHutangColumn));
        
        tipeHutangColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        tipeHutangColumn.setCellFactory(col -> Function.getWrapTableCell(tipeHutangColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglHutangColumn.setCellFactory(col -> Function.getWrapTableCell(tglHutangColumn));
        tglHutangColumn.setComparator(Function.sortDate(tglLengkap));
        
        jatuhTempoColumn.setCellValueFactory(cellData -> { 
            try {
                if(cellData.getValue().getJatuhTempo().equals("2000-01-01"))
                    return null;
                else
                    return new SimpleStringProperty(tgl.format(tglBarang.parse(cellData.getValue().getJatuhTempo())));
            } catch (Exception ex) {
                return null;
            }
        });
        jatuhTempoColumn.setCellFactory(col -> Function.getWrapTableCell(jatuhTempoColumn));
        
        jumlahHutangColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahHutangProperty());
        jumlahHutangColumn.setCellFactory(col -> getTableCell(rp));
        
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        sisaHutangColumn.setCellValueFactory(cellData -> cellData.getValue().sisaHutangProperty());
        sisaHutangColumn.setCellFactory(col -> getTableCell(rp));
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Hutang");
        addNew.setOnAction((ActionEvent e)->{
            showNewHutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getHutang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Hutang")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().add(refresh);
        hutangTable.setContextMenu(rowMenu);
        hutangTable.setRowFactory(tv -> {
            TableRow<Hutang> row = new TableRow<Hutang>() {
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Hutang");
                        addNew.setOnAction((ActionEvent e)->{
                            showNewHutang();
                        });
                        MenuItem detail = new MenuItem("Detail Hutang");
                        detail.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem tj = new MenuItem("Detail Terima Tanda Jadi");
                        tj.setOnAction((ActionEvent event) -> {
                            detailTerimaTandaJadi(item.getKeterangan());
                        });
                        MenuItem dp = new MenuItem("Detail Terima Down Payment");
                        dp.setOnAction((ActionEvent event) -> {
                            detailTerimaDownPayment(item.getKeterangan());
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Hutang");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getHutang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Hutang")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                        }
                        rowMenu.getItems().add(detail);
                        if(item.getKategori().equals("Terima Tanda Jadi")){
                            rowMenu.getItems().add(tj);
                        }else if(item.getKategori().equals("Terima Down Payment")){
                            rowMenu.getItems().add(dp);
                        }else{
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Pembayaran Hutang")&&o.isStatus()&&
                                        item.getStatus().equals("open"))
                                    rowMenu.getItems().add(bayar);
                            }
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allHutang.addListener((ListChangeListener.Change<? extends Hutang> change) -> {
            searchHutang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHutang();
        });
        filterData.addAll(allHutang);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Belum Lunas");
        groupBy.add("Lunas");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Belum Lunas");
        getHutang();
        hutangTable.setItems(filterData);
    } 
    @FXML
    private void getHutang(){
        Task<List<Hutang>> task = new Task<List<Hutang>>() {
            @Override 
            public List<Hutang> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    String status = "%";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas"))
                        status = "open";
                    else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas"))
                        status = "close";
                    return HutangDAO.getAllByKategoriAndStatus(con, "%" , status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allHutang.clear();
                allHutang.addAll(task.getValue());
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
    private void searchHutang(){
        try{
            filterData.clear();
            for (Hutang temp : allHutang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoHutang())||
                        checkColumn(rp.format(temp.getJumlahHutang()))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglHutang())))||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(rp.format(temp.getPembayaran()))||
                        checkColumn(rp.format(temp.getSisaHutang()))||
                        checkColumn(tgl.format(tglBarang.parse(temp.getJatuhTempo()))))
                        filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double belumTerbayar = 0;
        double sudahTerbayar = 0;
        for(Hutang temp : filterData){
            belumTerbayar = belumTerbayar + temp.getSisaHutang();
            sudahTerbayar = sudahTerbayar + temp.getPembayaran();
        }
        totalHutangField.setText(rp.format(belumTerbayar));
        totalPembayaranField.setText(rp.format(sudahTerbayar));
    }
    @FXML
    private void showNewHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewHutang.fxml");
        NewHutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))||
                    "".equals(x.jumlahRpField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            }else if(x.kategoriCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String jatuhTempo = "2000-01-01";
                            if(x.jatuhTempoField.getValue()!=null)
                                jatuhTempo= x.jatuhTempoField.getValue().toString();
                            Hutang h = new Hutang();
                            h.setNoHutang(HutangDAO.getId(con));
                            h.setTglHutang(tglSql.format(new Date()));
                            h.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            h.setKeterangan(x.keteranganField.getText());
                            h.setJumlahHutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            h.setPembayaran(0);
                            h.setSisaHutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            h.setJatuhTempo(jatuhTempo);
                            h.setStatus("open");
                            return Service.saveNewHutang(con, h, x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getHutang();
                        if(task.getValue().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Data hutang berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                        }else
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
        });
    }
    private void showDetailHutang(Hutang hutang){
        try{
            List<String> listKategoriHutang = Function.getKategoriHutang();
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
            DetailHutangController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setDetail(hutang);
            x.pembayaranHutangTable.setRowFactory((TableView<Pembayaran> tableView) -> {
                final TableRow<Pembayaran> row = new TableRow<Pembayaran>(){
                    @Override
                    public void updateItem(Pembayaran item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setContextMenu(null);
                        } else{
                            final ContextMenu rm = new ContextMenu();
                            MenuItem batal = new MenuItem("Batal Pembayaran Hutang");
                            batal.setOnAction((ActionEvent e)->{
                                batalPembayaran(item, stage);
                            });
                            boolean statusTipeKeuangan = false;
                            List<String> listTipeKeuangan = Function.getTipeKeuangan();
                            for(String s : listTipeKeuangan){
                                if(item.getTipeKeuangan().equals(s))
                                    statusTipeKeuangan = true;
                            }
                            boolean statusKategori = false;
                            for(String s : listKategoriHutang){
                                if(hutang.getKategori().equals(s))
                                    statusKategori = true;
                            }
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Batal Pembayaran Hutang")&&o.isStatus()&&statusTipeKeuangan&&statusKategori)
                                    rm.getItems().add(batal);
                            }
                            setContextMenu(rm);
                        }
                    }
                };
                return row;
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void batalPembayaran(Pembayaran pembayaran, Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang hutang = HutangDAO.get(con, pembayaran.getNoHutang());
                        pembayaran.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setUserBatal(sistem.getUser().getUsername());
                        pembayaran.setStatus("false");
                        pembayaran.setHutang(hutang);
                        return Service.batalPembayaranHutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getHutang();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void showPembayaran(Hutang h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaranHutang.fxml");
        NewPembayaranHutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewPembayaran(h);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))||
                    "".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah pembayaran masih kosong");
            }else if(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", ""))>h.getSisaHutang()){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi sisa hutang");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String jatuhTempo = "2000-01-01";
                            if(x.jatuhTempoField.getValue()!=null)
                                jatuhTempo= x.jatuhTempoField.getValue().toString();
                            Pembayaran pembayaran = new Pembayaran();
                            pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                            pembayaran.setTglPembayaran(tglSql.format(new Date()));
                            pembayaran.setNoHutang(h.getNoHutang());
                            pembayaran.setJumlahPembayaran(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", "")));
                            pembayaran.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            pembayaran.setCatatan("");
                            pembayaran.setKodeUser(sistem.getUser().getUsername());
                            pembayaran.setTglBatal("2000-01-01 00:00:00");
                            pembayaran.setUserBatal("");
                            pembayaran.setStatus("true");
                            pembayaran.setHutang(h);
                            return Service.saveNewPembayaranHutang(con, pembayaran, jatuhTempo);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getHutang();
                        if(task.getValue().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Pembayaran hutang berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                        }else{
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
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
        });
    }
    private void detailTerimaTandaJadi(String noSTJ){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaTandaJadi.fxml");
        DetailTerimaTandaJadiController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.getSTJ(noSTJ);
    }
    private void detailTerimaDownPayment(String noSDP){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaDownPayment.fxml");
        DetailTerimaDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.getSDP(noSDP);
    }
}
