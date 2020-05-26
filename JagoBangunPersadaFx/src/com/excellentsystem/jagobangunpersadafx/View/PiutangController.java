/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.PiutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TerimaPembayaranDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
import com.excellentsystem.jagobangunpersadafx.Model.TerimaPembayaran;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPelunasanDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPiutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewPiutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewTerimaPembayaranPiutangController;
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
public class PiutangController  {

    @FXML private TableView<Piutang> piutangTable;
    @FXML private TableColumn<Piutang, String> noPiutangColumn;
    @FXML private TableColumn<Piutang, String> tglPiutangColumn;
    @FXML private TableColumn<Piutang, String> kategoriColumn;
    @FXML private TableColumn<Piutang, String> keteranganColumn;
    @FXML private TableColumn<Piutang, Number> jumlahPiutangColumn;
    @FXML private TableColumn<Piutang, Number> pembayaranColumn;
    @FXML private TableColumn<Piutang, Number> sisaPiutangColumn;
    @FXML private TableColumn<Piutang, String> jatuhTempoColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPiutangField;
    @FXML private Label totalPembayaranField;
    @FXML private ComboBox<String> groupByCombo;
    
    private ObservableList<Piutang> allPiutang = FXCollections.observableArrayList();
    private ObservableList<Piutang> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().noPiutangProperty());
        noPiutangColumn.setCellFactory(col -> Function.getWrapTableCell(noPiutangColumn));
        
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        tglPiutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPiutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPiutangColumn.setCellFactory(col -> Function.getWrapTableCell(tglPiutangColumn));
        tglPiutangColumn.setComparator(Function.sortDate(tglLengkap));
        
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
        
        jumlahPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPiutangProperty());
        jumlahPiutangColumn.setCellFactory(col -> getTableCell(rp));
        
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        sisaPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPiutangProperty());
        sisaPiutangColumn.setCellFactory(col -> getTableCell(rp));
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Piutang");
        addNew.setOnAction((ActionEvent e)->{
            showNewPiutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPiutang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Piutang")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().add(refresh);
        piutangTable.setContextMenu(rowMenu);
        piutangTable.setRowFactory(tv -> {
            TableRow<Piutang> row = new TableRow<Piutang>() {
                @Override
                public void updateItem(Piutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Piutang");
                        addNew.setOnAction((ActionEvent e)->{
                            showNewPiutang();
                        });
                        MenuItem detail = new MenuItem("Detail Piutang");
                        detail.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem skl = new MenuItem("Detail Pelunasan Down Payment");
                        skl.setOnAction((ActionEvent event) -> {
                            detailPelunasanDownPayment(item.getKeterangan());
                        });
                        MenuItem bayar = new MenuItem("Terima Pembayaran Piutang");
                        bayar.setOnAction((ActionEvent e)->{
                            showTerimaPembayaran(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPiutang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Piutang")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                        }
                        rowMenu.getItems().add(detail);
                        if(item.getKategori().equals("Piutang Penjualan")){
                            rowMenu.getItems().add(skl);
                        }else{
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Terima Pembayaran Piutang")&&o.isStatus()&&
                                        item.getStatus().equals("open")&&
                                    !item.getKategori().equals("Terima Pembayaran Down Payment"))
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
        allPiutang.addListener((ListChangeListener.Change<? extends Piutang> change) -> {
            searchPiutang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPiutang();
        });
        filterData.addAll(allPiutang);
    }
     public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.clear();
        groupBy.add("Belum Lunas");
        groupBy.add("Lunas");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().select("Belum Lunas");
        getPiutang();
        piutangTable.setItems(filterData);
    } 
     @FXML
    private void getPiutang(){
        Task<List<Piutang>> task = new Task<List<Piutang>>() {
            @Override 
            public List<Piutang>call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    String statusHutang = "";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas"))
                        statusHutang = "close";
                    else if(groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas"))
                        statusHutang = "open";
                    List<Piutang> listPiutang = PiutangDAO.getAllByKategoriAndStatus(con, "%", statusHutang);
                    return listPiutang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allPiutang.clear();
                allPiutang.addAll(task.getValue());
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
    private void searchPiutang(){
        try{
            filterData.clear();
            for (Piutang temp : allPiutang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPiutang())||
                        checkColumn(rp.format(temp.getJumlahPiutang()))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPiutang())))||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(rp.format(temp.getPembayaran()))||
                        checkColumn(rp.format(temp.getSisaPiutang()))||
                        checkColumn(tgl.format(tglBarang.parse(temp.getJatuhTempo()))))
                            filterData.add(temp);
                    else{
                        Boolean status = false;
                        for(TerimaPembayaran terimaPembayaran : temp.getAllTerimaPembayaran()){
                            if(checkColumn(terimaPembayaran.getNoTerimaPembayaran())||
                                checkColumn(terimaPembayaran.getTipeKeuangan())||
                                checkColumn(tglLengkap.format(tglSql.parse(terimaPembayaran.getTglTerima())))||
                                checkColumn(rp.format(terimaPembayaran.getJumlahPembayaran()))||
                                checkColumn(terimaPembayaran.getCatatan()))
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
    private void hitungTotal(){
        double belumDibayar = 0;
        double sudahDibayar = 0;
        for(Piutang temp : filterData){
            belumDibayar = belumDibayar + temp.getSisaPiutang();
            sudahDibayar = sudahDibayar + temp.getPembayaran();
        }
        totalPiutangField.setText(rp.format(belumDibayar));
        totalPembayaranField.setText(rp.format(sudahDibayar));
    }
    @FXML
    private void showNewPiutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPiutang.fxml");
        NewPiutangController x = loader.getController();
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
                            Piutang p = new Piutang();
                            p.setNoPiutang(PiutangDAO.getId(con));
                            p.setTglPiutang(tglSql.format(new Date()));
                            p.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            p.setKeterangan(x.keteranganField.getText());
                            p.setJumlahPiutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            p.setPembayaran(0);
                            p.setSisaPiutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            p.setJatuhTempo(jatuhTempo);
                            p.setStatus("open");
                            return Service.saveNewPiutang(con, p, 
                                    x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getPiutang();
                        if(task.getValue().equals("true")){
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Data piutang berhasil disimpan");
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
    private void showDetailPiutang(Piutang piutang){
        try{
            List<String> listKategoriHutang = Function.getKategoriHutang();
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
            DetailPiutangController x = loader.getController();
            x.setMainApp(mainApp,mainApp.MainStage, stage);
            x.setDetail(piutang.getNoPiutang());
            x.pembayaranPiutangTable.setRowFactory((TableView<TerimaPembayaran> tableView) -> {
                final TableRow<TerimaPembayaran> row = new TableRow<TerimaPembayaran>(){
                    @Override
                    public void updateItem(TerimaPembayaran item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setContextMenu(null);
                        } else{
                            final ContextMenu rm = new ContextMenu();
                            MenuItem batal = new MenuItem("Batal Terima Pembayaran Piutang");
                            batal.setOnAction((ActionEvent e)->{
                                batalPembayaran(item, stage);
                            });
                            boolean status = false;
                            List<String> listTipeKeuangan = Function.getTipeKeuangan();
                            for(String s : listTipeKeuangan){
                                if(item.getTipeKeuangan().equals(s))
                                    status = true;
                            }
                            boolean statusKategori = false;
                            for(String s : listKategoriHutang){
                                if(piutang.getKategori().equals(s))
                                    statusKategori = true;
                            }
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Batal Terima Pembayaran Piutang")&&o.isStatus()&&status&&statusKategori)
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
    private void batalPembayaran(TerimaPembayaran pembayaran, Stage stage){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoTerimaPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Piutang piutang = PiutangDAO.get(con, pembayaran.getNoPiutang());
                        pembayaran.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setUserBatal(sistem.getUser().getUsername());
                        pembayaran.setStatus("false");
                        pembayaran.setPiutang(piutang);
                        return Service.batalTerimaPembayaranPiutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPiutang();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void showTerimaPembayaran(Piutang p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewTerimaPembayaranPiutang.fxml");
        NewTerimaPembayaranPiutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewTerimaPembayaran(p);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))||
                    "".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran masih kosong");
            }else if(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", ""))>p.getSisaPiutang()){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran melebihi sisa piutang");
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
                            TerimaPembayaran terimaPembayaran = new TerimaPembayaran();
                            terimaPembayaran.setNoTerimaPembayaran(TerimaPembayaranDAO.getId(con));
                            terimaPembayaran.setTglTerima(tglSql.format(new Date()));
                            terimaPembayaran.setNoPiutang(p.getNoPiutang());
                            terimaPembayaran.setJumlahPembayaran(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", "")));
                            terimaPembayaran.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            terimaPembayaran.setCatatan("");
                            terimaPembayaran.setKodeUser(sistem.getUser().getUsername());
                            terimaPembayaran.setTglBatal("2000-01-01 00:00:00");
                            terimaPembayaran.setUserBatal("");
                            terimaPembayaran.setStatus("true");
                            terimaPembayaran.setPiutang(p);
                            return Service.saveNewTerimaPembayaranPiutang(con, terimaPembayaran, jatuhTempo);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getPiutang();
                        if(task.getValue().equals("true")){
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Terima Pembayaran Piutang berhasil disimpan");
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
    private void detailPelunasanDownPayment(String noSKL){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanDownPayment.fxml");
        DetailPelunasanDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.getSKL(noSKL);
    }
}
