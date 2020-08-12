/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranHutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailHutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.JatuhTempoController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembayaranController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembelianController;
import java.sql.Connection;
import java.time.LocalDate;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PembelianController  {

    @FXML private TableView<PembelianHead> pembelianTable;
    @FXML private TableColumn<PembelianHead, String> noPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> kodeSupplierColumn;
    @FXML private TableColumn<PembelianHead, String> namaSupplierColumn;
    @FXML private TableColumn<PembelianHead, String> alamatSupplierColumn;
    @FXML private TableColumn<PembelianHead, Number> totalPembelianColumn;
    @FXML private TableColumn<PembelianHead, Number> pembayaranColumn;
    @FXML private TableColumn<PembelianHead, Number> sisaPembayaranColumn;
    @FXML private TableColumn<PembelianHead, String> catatanColumn;
    @FXML private TableColumn<PembelianHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalPembelianField;
    @FXML private Label belumTerbayarField;
    @FXML private Label sudahTerbayarField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<PembelianHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        kodeSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSupplierProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        alamatSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().alamatProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPembelianColumn.setCellValueFactory(cellData ->cellData.getValue().totalPembelianProperty());
        pembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().pembayaranProperty());
        sisaPembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().sisaPembayaranProperty());
        
        totalPembelianColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pembelian");
        addNew.setOnAction((ActionEvent e)->{
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPembelian();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pembelian")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        pembelianTable.setContextMenu(rm);
        pembelianTable.setRowFactory((TableView<PembelianHead> tableView) -> {
            final TableRow<PembelianHead> row = new TableRow<PembelianHead>(){
                @Override
                public void updateItem(PembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pembelian");
                        addNew.setOnAction((ActionEvent e)->{
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Pembelian");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPembelian(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembelian(item);
                        });
                        MenuItem pembayaran = new MenuItem("Lihat Detail Pembayaran Pembelian");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Pembelian");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo Pembelian");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPembelian();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pembelian")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Pembelian")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pembelian")&&o.isStatus())
                                rm.getItems().add(batal);
                            if(o.getJenis().equals("Lihat Detail Pembayaran Pembelian")&&o.isStatus()&&item.getPembayaran()>0)
                                rm.getItems().add(pembayaran);
                            if(o.getJenis().equals("Pembayaran Pembelian")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(bayar);
                            if(o.getJenis().equals("Set Jatuh Tempo Pembelian")&&o.isStatus()&&item.getSisaPembayaran()>0)
                                rm.getItems().add(tempo);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Pembelian")&&o.isStatus())
                                lihatDetailPembelian(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembelian();
        pembelianTable.setItems(filterData);
    }
    @FXML
    private void getPembelian(){
        Task<List<PembelianHead>> task = new Task<List<PembelianHead>>() {
            @Override 
            public List<PembelianHead> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    List<PembelianHead> temp = PembelianHeadDAO.getAllByDateAndStatus(con, 
                                    tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),"true");
                    for(PembelianHead p : temp){
                        for(Supplier s : allSupplier){
                            if(p.getKodeSupplier().equals(s.getKodeSupplier()))
                                p.setSupplier(s);
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
                        checkColumn(temp.getSupplier().getAlamat())||
                        checkColumn(df.format(temp.getTotalPembelian()))||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaPembayaran()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
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
        double terbayar = 0;
        double belumterbayar = 0;
        for(PembelianHead temp : filterData){
            total = total + temp.getTotalPembelian();
            terbayar = terbayar + temp.getPembayaran();
            belumterbayar = belumterbayar + temp.getSisaPembayaran();
        }
        totalPembelianField.setText(df.format(total));
        sudahTerbayarField.setText(df.format(terbayar));
        belumTerbayarField.setText(df.format(belumterbayar));
    }
    private void newPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewPembelian();
    }
    private void lihatDetailPembelian(PembelianHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailPembelian(p.getNoPembelian());
    }
    private void batalPembelian(PembelianHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembelian "+p.getNoPembelian()+", anda yakin ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        p.setTglBatal(tglSql.format(new Date()));
                        p.setUserBatal(sistem.getUser().getUsername());
                        p.setStatus("false");
                        return Service.saveBatalPembelian(con, p);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                try{
                    mainApp.closeLoading();
                    if(task.get().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data pembelian berhasil dibatal");
                        mainApp.showPembelian();
                    }else{
                        mainApp.showMessage(Modality.NONE, "Warning", task.get());
                    }
                }catch(Exception exx){
                    mainApp.showMessage(Modality.NONE, "Error", exx.toString());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void showDetailHutang(PembelianHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetailPembelian(p);
        x.pembayaranHutangTable.setRowFactory((TableView<PembayaranHutang> tableView) -> {
            final TableRow<PembayaranHutang> row = new TableRow<PembayaranHutang>(){
                @Override
                public void updateItem(PembayaranHutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(item, x);
                        });
                        Boolean ok = false;
                        for(String s : Function.getTipeKeuanganByUser()){
                            if(item.getTipeKeuangan().equals(s))
                                ok = true;
                        }
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Batal Pembayaran Pembelian")&&o.isStatus()&&ok)
                                rm.getItems().add(batal);
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    private void batalPembayaran(PembayaranHutang pembayaran, DetailHutangController x){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang hutang = HutangDAO.get(con, pembayaran.getNoHutang());
                        pembayaran.setTglBatal(tglSql.format(new Date()));
                        pembayaran.setUserBatal(sistem.getUser().getUsername());
                        pembayaran.setStatus("false");
                        pembayaran.setHutang(hutang);
                        return Service.saveBatalPembayaranHutang(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                try{
                    mainApp.closeLoading();
                    mainApp.showPembelian();
                    if(task.get().equals("true")){
                        x.close();
                        mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.get());
                    }
                }catch(Exception exc){
                    mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void showPembayaran(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranPembelian(p.getNoPembelian());
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
            if(jumlahBayar>p.getSisaPembayaran())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa pembayaran");
            else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            Hutang h = HutangDAO.getByKategoriAndKeterangan(con, "Hutang Pembelian",p.getNoPembelian());
                            PembayaranHutang pb = new PembayaranHutang();
                            pb.setNoPembayaran(PembayaranHutangDAO.getId(con));
                            pb.setTglPembayaran(tglSql.format(new Date()));
                            pb.setNoHutang(h.getNoHutang());
                            pb.setJumlahPembayaran(Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", "")));
                            pb.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            pb.setCatatan("");
                            pb.setKodeUser(sistem.getUser().getUsername());
                            pb.setTglBatal("2000-01-01 00:00:00");
                            pb.setUserBatal("");
                            pb.setStatus("true");
                            pb.setHutang(h);
                            return Service.savePembayaranHutang(con, pb);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran pembelian berhasil disimpan");
                        getPembelian();
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void setJatuhTempo(PembelianHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/JatuhTempo.fxml");
        JatuhTempoController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            String jatuhTempo = controller.jatuhTempoPicker.getValue().toString();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang hutang = HutangDAO.getByKategoriAndKeterangan(con, "Hutang Pembelian",p.getNoPembelian());
                        hutang.setJatuhTempo(jatuhTempo);
                        return Service.saveJatuhTempoHutang(con, hutang);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                if(task.getValue().equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo pembelian berhasil disimpan");
                    getPembelian();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
