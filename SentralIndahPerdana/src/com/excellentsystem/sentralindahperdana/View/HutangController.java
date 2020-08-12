/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranHutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailHutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.JatuhTempoController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewHutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembayaranController;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
    @FXML private TableColumn<Hutang, String> kategoriKeuanganColumn;
    @FXML private TableColumn<Hutang, Number> jumlahHutangColumn;
    @FXML private TableColumn<Hutang, Number> pembayaranColumn;
    @FXML private TableColumn<Hutang, Number> sisaHutangColumn;
    @FXML private TableColumn<Hutang, String> jatuhTempoColumn;
    
    @FXML private TextField searchField;
    @FXML private Label belumTerbayarField;
    @FXML private Label sudahTerbayarField;
    @FXML private ComboBox<String> groupByCombo;
    private ObservableList<Hutang> allHutang = FXCollections.observableArrayList();
    private ObservableList<Hutang> filterData = FXCollections.observableArrayList();
    private Main mainApp; 
    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        tipeHutangColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kategoriKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriKeuanganProperty());
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        jatuhTempoColumn.setCellValueFactory(cellData -> { 
            try {
                if(cellData.getValue().getJatuhTempo().equals("2000-01-01"))
                    return null;
                else
                    return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getJatuhTempo())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahHutangColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahHutangProperty());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        sisaHutangColumn.setCellValueFactory(cellData -> cellData.getValue().sisaHutangProperty());
        jumlahHutangColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaHutangColumn.setCellFactory(col -> Function.getTableCell());
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Hutang");
        addNew.setOnAction((ActionEvent e)->{
            showNewHutang();
        });
        MenuItem katHutang = new MenuItem("Add New Kategori Hutang");
        katHutang.setOnAction((ActionEvent e)->{
            mainApp.showKategoriHutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getHutang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Hutang")&&o.isStatus())
                rowMenu.getItems().add(addNew);
            if(o.getJenis().equals("Kategori Hutang")&&o.isStatus())
                rowMenu.getItems().add(katHutang);
        }
        rowMenu.getItems().addAll(refresh);
        hutangTable.setContextMenu(rowMenu);
        hutangTable.setRowFactory(tv -> {
            TableRow<Hutang> row = new TableRow<Hutang>() {
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Hutang");
                        addNew.setOnAction((ActionEvent e)->{
                            showNewHutang();
                        });
                        MenuItem katHutang = new MenuItem("Add New Kategori Hutang");
                        katHutang.setOnAction((ActionEvent e)->{
                            mainApp.showKategoriHutang();
                        });
                        MenuItem lihat = new MenuItem("Lihat Detail Hutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Hutang");
                        bayar.setOnAction((ActionEvent e)->{
                            showPembayaran(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo Hutang");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getHutang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Hutang")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Kategori Hutang")&&o.isStatus())
                                rowMenu.getItems().add(katHutang);
                            if(o.getJenis().equals("Lihat Detail Hutang")&&o.isStatus())
                                rowMenu.getItems().add(lihat);
                            if(o.getJenis().equals("Pembayaran Hutang")&&o.isStatus()&&
                                !item.getKategori().equals("Terima Pembayaran Down Payment")&&
                                item.getStatus().equals("open"))
                                rowMenu.getItems().add(bayar);
                            if(o.getJenis().equals("Set Jatuh Tempo Hutang")&&o.isStatus()&&
                                    item.getStatus().equals("open"))
                                rowMenu.getItems().add(tempo);
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Hutang")&&o.isStatus())
                                showDetailHutang(row.getItem());
                        }
                    }
                }
            });
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
                try (Connection con = Koneksi.getConnection()) {
                    String status = "close";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("Belum Lunas"))
                        status = "open";
                    return HutangDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            try{
                allHutang.clear();
                allHutang.addAll(task.get());
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
                        checkColumn(df.format(temp.getJumlahHutang()))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglHutang())))||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaHutang()))||
                        checkColumn(tglNormal.format(tglBarang.parse(temp.getJatuhTempo()))))
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
        belumTerbayarField.setText(df.format(belumTerbayar));
        sudahTerbayarField.setText(df.format(sudahTerbayar));
    }
    private void showDetailHutang(Hutang hutang){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetail(hutang);
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
                            if(o.getJenis().equals("Batal Pembayaran Hutang")&&o.isStatus()&&ok)
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
                    mainApp.showHutang();
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
    private void showNewHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewHutang.fxml");
        NewHutangController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))||"".equals(x.jumlahRpField.getText().replaceAll(",", "")))
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            else if(x.kategoriCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
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
                            h.setKategoriKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            h.setJumlahHutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            h.setPembayaran(0);
                            h.setSisaHutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            h.setJatuhTempo(jatuhTempo);
                            h.setKodeUser(sistem.getUser().getUsername());
                            h.setStatus("open");
                            return Service.saveNewHutang(con, h);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getHutang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data hutang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void showPembayaran(Hutang h) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
        NewPembayaranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembayaranHutang(h);
        controller.saveButton.setOnAction((event) -> {
            double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
            if(jumlahBayar>h.getSisaHutang())
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa hutang");
            else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
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
                    getHutang();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembayaran hutang berhasil disimpan");
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
    private void setJatuhTempo(Hutang hutang) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/JatuhTempo.fxml");
        JatuhTempoController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            String jatuhTempo = controller.jatuhTempoPicker.getValue().toString();
            Task<Void> task = new Task<Void>() {
                @Override 
                public Void call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        hutang.setJatuhTempo(jatuhTempo);
                        Service.saveJatuhTempoHutang(con, hutang);
                        return null;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                mainApp.closeDialog(mainApp.MainStage, stage);
                mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo hutang berhasil disimpan");
                getHutang();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
