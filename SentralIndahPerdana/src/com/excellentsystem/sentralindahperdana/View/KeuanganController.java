/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.KategoriTransaksiDAO;
import com.excellentsystem.sentralindahperdana.DAO.KeuanganDAO;
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
import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewKeuanganController;
import com.excellentsystem.sentralindahperdana.View.Dialog.TransferKeuanganController;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class KeuanganController  {
    
    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label saldoAkhirField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> TipeKeuanganCombo;
    private double saldoAkhir = 0;
    private double saldoAwal = 0;
    private List<KategoriTransaksi> allKategori;
    final TreeItem<Keuangan> root = new TreeItem<>();
    private final ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private final ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noKeuanganColumn.setCellValueFactory( param -> param.getValue().getValue().noKeuanganProperty());
        kategoriColumn.setCellValueFactory( param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory( param -> param.getValue().getValue().keteranganProperty());
        kodeUserColumn.setCellValueFactory( param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(new SimpleDateFormat("hh:mm").format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Transaksi");
        addNew.setOnAction((ActionEvent event) -> {
            showNewKeuangan();
        });
        MenuItem katTransaksi = new MenuItem("Add New Kategori Transaksi");
        katTransaksi.setOnAction((ActionEvent event) -> {
            mainApp.showKategoriTransaksi();
        });
        MenuItem katKeu = new MenuItem("Add New Kategori Keuangan");
        katKeu.setOnAction((ActionEvent event) -> {
            mainApp.showKategoriKeuangan();
        });
        MenuItem transfer = new MenuItem("Transfer Keuangan");
        transfer.setOnAction((ActionEvent event) -> {
            showTransfer();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Transaksi")&&o.isStatus())
                rowMenu.getItems().add(addNew);
            if(o.getJenis().equals("Transfer Keuangan")&&o.isStatus())
                rowMenu.getItems().add(transfer);
            if(o.getJenis().equals("Kategori Transaksi")&&o.isStatus())
                rowMenu.getItems().add(katTransaksi);
            if(o.getJenis().equals("Kategori Keuangan")&&o.isStatus())
                rowMenu.getItems().add(katKeu);
        }
        rowMenu.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rowMenu);
        keuanganTable.setRowFactory(ttv -> {
            TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>() {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        if(item.getNoKeuangan().startsWith("KK")){
                            ContextMenu rowMenu = new ContextMenu();
                            MenuItem addNew = new MenuItem("Add New Transaksi");
                            addNew.setOnAction((ActionEvent event) -> {
                                showNewKeuangan();
                            });
                            MenuItem katTransaksi = new MenuItem("Add New Kategori Transaksi");
                            katTransaksi.setOnAction((ActionEvent event) -> {
                                mainApp.showKategoriTransaksi();
                            });
                            MenuItem katKeu = new MenuItem("Add New Kategori Keuangan");
                            katKeu.setOnAction((ActionEvent event) -> {
                                mainApp.showKategoriKeuangan();
                            });
                            MenuItem transfer = new MenuItem("Transfer Keuangan");
                            transfer.setOnAction((ActionEvent event) -> {
                                showTransfer();
                            });
                            MenuItem batal = new MenuItem("Batal Transaksi");
                            batal.setOnAction(e -> {
                                batal(item);
                            });
                            MenuItem refresh = new MenuItem("Refresh");
                            refresh.setOnAction((ActionEvent event) -> {
                                getKeuangan();
                            });
                            Boolean status = false;
                            for(KategoriTransaksi k : allKategori){
                                if(item.getKategori().equals(k.getKodeKategori()))
                                    status = true;
                            }
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Add New Transaksi")&&o.isStatus())
                                    rowMenu.getItems().add(addNew);
                                if(o.getJenis().equals("Kategori Transaksi")&&o.isStatus())
                                    rowMenu.getItems().add(katTransaksi);
                                if(o.getJenis().equals("Kategori Keuangan")&&o.isStatus())
                                    rowMenu.getItems().add(katKeu);
                                if(o.getJenis().equals("Transfer Keuangan")&&o.isStatus())
                                    rowMenu.getItems().add(transfer);
                                if(o.getJenis().equals("Batal Transaksi")&&o.isStatus()&&
                                        status&&item.getTglKeuangan().startsWith(tglBarang.format(new Date())))
                                    rowMenu.getItems().add(batal);
                            }
                            rowMenu.getItems().add(refresh);
                            setContextMenu(rowMenu);
                        }
                    }
                }
            };
            return row;
        });
        allKeuangan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKeuangan();
        });
        filterData.addAll(allKeuangan);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        Task<List<KategoriTransaksi>> task = new Task<List<KategoriTransaksi>>() {
            @Override 
            public List<KategoriTransaksi> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    return KategoriTransaksiDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allKategori = task.getValue();
                TipeKeuanganCombo.setItems(Function.getTipeKeuanganByUser());
                TipeKeuanganCombo.getSelectionModel().selectFirst();
                getKeuangan();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }  
    @FXML
    private void getKeuangan(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    saldoAwal = KeuanganDAO.getSaldoAwal(con, tglMulaiPicker.getValue().toString(),TipeKeuanganCombo.getSelectionModel().getSelectedItem());
                    saldoAkhir = KeuanganDAO.getSaldoAkhir(con, tglAkhirPicker.getValue().toString(),TipeKeuanganCombo.getSelectionModel().getSelectedItem());
                    return KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, 
                                TipeKeuanganCombo.getSelectionModel().getSelectedItem(),
                                tglMulaiPicker.getValue().toString(),
                                tglAkhirPicker.getValue().toString());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                saldoAkhirField.setText(df.format(saldoAkhir));
                allKeuangan.clear();
                allKeuangan.addAll(task.get());
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
    private void searchKeuangan() {
        try{
            filterData.clear();
            for (Keuangan temp : allKeuangan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoKeuangan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))||
                        checkColumn(temp.getTipeKeuangan())||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(df.format(temp.getJumlahRp()))||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
            setTable();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void setTable()throws Exception{
        if(keuanganTable.getRoot()!=null)
            keuanganTable.getRoot().getChildren().clear();
        List<String> tanggal = new ArrayList<>();
        for(Keuangan temp: filterData){
            if(!tanggal.contains(temp.getTglKeuangan().substring(0, 10)))
                tanggal.add(temp.getTglKeuangan().substring(0,10));
        }
        for(String temp : tanggal){
            Keuangan tglKeu = new Keuangan();
            tglKeu.setNoKeuangan(tglNormal.format(tglBarang.parse(temp)));
            tglKeu.setJumlahRp(saldoAwal);
            TreeItem<Keuangan> parent = new TreeItem<>(tglKeu);
            for(Keuangan temp2: filterData){
                if(temp.equals(temp2.getTglKeuangan().substring(0,10))){
                    TreeItem<Keuangan> child = new TreeItem<>(temp2);
                    parent.getChildren().addAll(child);
                    saldoAwal = saldoAwal + temp2.getJumlahRp();
                }
            }
            root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    } 
    private void showTransfer(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TransferKeuangan.fxml");
        TransferKeuanganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.dariCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode keuangan asal transfer belum dipilih");
            else if(x.keCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode keuangan tujuan transfer belum dipilih");
            else if(x.dariCombo.getSelectionModel().getSelectedItem().equals(x.keCombo.getSelectionModel().getSelectedItem()))
                mainApp.showMessage(Modality.NONE, "Warning", "Asal dan tujuan transfer tidak boleh sama");
            else if(x.jumlahRpField.getText().equals("0"))
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp yang akan ditransfer masih kosong");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            return Service.saveTransferKeuangan(
                                con, x.dariCombo.getSelectionModel().getSelectedItem(),
                                x.keCombo.getSelectionModel().getSelectedItem(),x.keteranganField.getText(),
                                Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKeuangan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Transfer Keuangan berhasil disimpan");
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
    private void showNewKeuangan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewKeuangan.fxml");
        NewKeuanganController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev)->{
            if("0".equals(x.jumlahRpField.getText().replaceAll(",", ""))||"".equals(x.jumlahRpField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
            }else if(x.kategoriCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe Keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            Double jumlahRp = Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", ""));
                            for(KategoriTransaksi k : allKategori){
                                if(k.getKodeKategori().equals(x.kategoriCombo.getSelectionModel().getSelectedItem())&&
                                        k.getJenisTransaksi().equals("K"))
                                    jumlahRp = jumlahRp*-1;
                            }
                            Keuangan k = new Keuangan();
                            k.setNoKeuangan(KeuanganDAO.getId(con));
                            k.setTglKeuangan(tglSql.format(new Date()));
                            k.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            k.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            k.setKeterangan(x.keteranganField.getText());
                            k.setJumlahRp(jumlahRp);
                            k.setKodeUser(sistem.getUser().getUsername());
                            return Service.saveTransaksiKeuangan(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKeuangan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Transaksi Keuangan berhasil disimpan");
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
    private void batal(Keuangan keu){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal transaksi keuangan "+keu.getNoKeuangan()+"-"+keu.getKategori()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        return Service.batalTransaksiKeuangan(con, keu);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKeuangan();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Batal transaksi berhasil disimpan");
                }else
                    mainApp.showMessage(Modality.NONE, "Error", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
