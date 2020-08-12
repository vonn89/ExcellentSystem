/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.PembayaranPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranPiutang;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPiutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.JatuhTempoController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembayaranController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPiutangController;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
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
 * @author yunaz
 */
public class PiutangController {

    @FXML private TableView<Piutang> piutangTable;
    @FXML private TableColumn<Piutang, String> noPiutangColumn;
    @FXML private TableColumn<Piutang, String> tglPiutangColumn;
    @FXML private TableColumn<Piutang, String> tipePiutangColumn;
    @FXML private TableColumn<Piutang, String> keteranganColumn;
    @FXML private TableColumn<Piutang, String> kategoriKeuanganColumn;
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
        tipePiutangColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kategoriKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriKeuanganProperty());
        tglPiutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPiutang())));
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
        jumlahPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPiutangProperty());
        jumlahPiutangColumn.setCellFactory(col -> Function.getTableCell());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTableCell());
        sisaPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPiutangProperty());
        sisaPiutangColumn.setCellFactory(col -> Function.getTableCell());
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Piutang");
        addNew.setOnAction((ActionEvent e)->{
            showNewPiutang();
        });
        MenuItem katPiutang = new MenuItem("Add New Kategori Piutang");
        katPiutang.setOnAction((ActionEvent e)->{
            mainApp.showKategoriPiutang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPiutang();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Piutang")&&o.isStatus())
                rowMenu.getItems().add(addNew);
            if(o.getJenis().equals("Kategori Piutang")&&o.isStatus())
                rowMenu.getItems().add(katPiutang);
        }
        rowMenu.getItems().addAll(refresh);
        piutangTable.setContextMenu(rowMenu);
        piutangTable.setRowFactory(tv -> {
            TableRow<Piutang> row = new TableRow<Piutang>() {
                @Override
                public void updateItem(Piutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Piutang");
                        addNew.setOnAction((ActionEvent e)->{
                            showNewPiutang();
                        });
                        MenuItem katPiutang = new MenuItem("Add New Kategori Piutang");
                        katPiutang.setOnAction((ActionEvent e)->{
                            mainApp.showKategoriPiutang();
                        });
                        MenuItem lihat = new MenuItem("Lihat Detail Piutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Piutang");
                        bayar.setOnAction((ActionEvent e)->{
                            pembayaranPiutang(item);
                        });
                        MenuItem tempo = new MenuItem("Set Jatuh Tempo Piutang");
                        tempo.setOnAction((ActionEvent e)->{
                            setJatuhTempo(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPiutang();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Piutang")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Kategori Piutang")&&o.isStatus())
                                rowMenu.getItems().add(katPiutang);
                            if(o.getJenis().equals("Lihat Detail Piutang")&&o.isStatus())
                                rowMenu.getItems().add(lihat);
                            if(o.getJenis().equals("Pembayaran Piutang")&&o.isStatus()&&item.getStatus().equals("open"))
                                rowMenu.getItems().add(bayar);
                            if(o.getJenis().equals("Set Jatuh Tempo Piutang")&&o.isStatus()&&item.getStatus().equals("open"))
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
                            if(o.getJenis().equals("Lihat Detail Piutang")&&o.isStatus())
                                showDetailPiutang(row.getItem());
                        }
                    }
                }
            });
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
            public List<Piutang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    String status = "open";
                    if(groupByCombo.getSelectionModel().getSelectedItem().equals("Lunas"))
                        status = "close";
                    return PiutangDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPiutang.clear();
            allPiutang.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null)
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
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
                        checkColumn(df.format(temp.getJumlahPiutang()))||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPiutang())))||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(df.format(temp.getPembayaran()))||
                        checkColumn(df.format(temp.getSisaPiutang()))||
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
        for(Piutang temp : filterData){
            belumTerbayar = belumTerbayar + temp.getSisaPiutang();
            sudahTerbayar = sudahTerbayar + temp.getPembayaran();
        }
        totalPiutangField.setText(df.format(belumTerbayar));
        totalPembayaranField.setText(df.format(sudahTerbayar));
    }
    private void showDetailPiutang(Piutang piutang){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetail(piutang,true);
    }
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
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            String jatuhTempo = "2000-01-01";
                            if(x.jatuhTempoField.getValue()!=null)
                                jatuhTempo= x.jatuhTempoField.getValue().toString();
                            Piutang p = new Piutang();
                            p.setNoPiutang(PiutangDAO.getId(con));
                            p.setTglPiutang(tglSql.format(new Date()));
                            p.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            p.setKeterangan(x.keteranganField.getText());
                            p.setKategoriKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            p.setJumlahPiutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            p.setPembayaran(0);
                            p.setSisaPiutang(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            p.setJatuhTempo(jatuhTempo);
                            p.setKodeUser(sistem.getUser().getUsername());
                            p.setStatus("open");
                            return Service.saveNewPiutang(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data piutang berhasil disimpan");
                        getPiutang();
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
    private void pembayaranPiutang(Piutang p) {
        if (p != null) {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaran.fxml");
            NewPembayaranController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setPembayaranPiutang(p);
            controller.saveButton.setOnAction((event) -> {
                double jumlahBayar = Double.parseDouble(controller.jumlahPembayaranField.getText().replaceAll(",", ""));
                if(jumlahBayar>p.getSisaPiutang())
                    mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi dari sisa piutang");
                else if(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null)
                    mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
                else{
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                PembayaranPiutang pb = new PembayaranPiutang();
                                pb.setNoPembayaran(PembayaranPiutangDAO.getId(con));
                                pb.setTglPembayaran(tglSql.format(new Date()));
                                pb.setNoPiutang(p.getNoPiutang());
                                pb.setJumlahPembayaran(jumlahBayar);
                                pb.setTipeKeuangan(controller.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                                pb.setCatatan("");
                                pb.setKodeUser(sistem.getUser().getUsername());
                                pb.setTglBatal("2000-01-01 00:00:00");
                                pb.setUserBatal("");
                                pb.setStatus("true");
                                pb.setPiutang(p);
                                return Service.savePembayaranPiutang(con, pb);
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
                            mainApp.showMessage(Modality.NONE, "Success", "Pembayaran piutang berhasil disimpan");
                            getPiutang();
                        }else
                            mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                    });
                    task.setOnFailed((e) -> {
                        mainApp.closeLoading();
                        mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    });
                    new Thread(task).start();
                }
            });
        } else {
            mainApp.showMessage(Modality.NONE, "Warning", "Piutang belum dipilih");
        }
    }
    private void setJatuhTempo(Piutang piutang) {
        if (piutang != null) {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/JatuhTempo.fxml");
            JatuhTempoController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.saveButton.setOnAction((event) -> {
                String jatuhTempo = controller.jatuhTempoPicker.getValue().toString();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            piutang.setJatuhTempo(jatuhTempo);
                            return Service.saveJatuhTempoPiutang(con, piutang);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Jatuh tempo piutang berhasil disimpan");
                        getPiutang();
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            });
        } else {
            mainApp.showMessage(Modality.NONE, "Warning", "Piutang belum dipilih");
        }
    }
}
