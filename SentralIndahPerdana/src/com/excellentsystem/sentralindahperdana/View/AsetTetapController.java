/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.AsetTetapDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailAsetTetapController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewAsetTetapController;
import java.sql.Connection;
import java.text.ParseException;
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
 * @author Xtreme
 */
public class AsetTetapController {
    
    @FXML private TableView<AsetTetap> asetTetapTable;
    @FXML private TableColumn<AsetTetap, String> noAsetTetapColumn;
    @FXML private TableColumn<AsetTetap, String> namaColumn;
    @FXML private TableColumn<AsetTetap, String> kategoriColumn;
    @FXML private TableColumn<AsetTetap, String> keteranganColumn;
    @FXML private TableColumn<AsetTetap, String> masaPakaiColumn;
    @FXML private TableColumn<AsetTetap, Number> nilaiAwalColumn;
    @FXML private TableColumn<AsetTetap, Number> penyusutanColumn;
    @FXML private TableColumn<AsetTetap, Number> nilaiAkhirColumn;
    @FXML private TableColumn<AsetTetap, Number> hargaJualColumn;
    @FXML private TableColumn<AsetTetap, String> tglBeliColumn;
    @FXML private TableColumn<AsetTetap, String> userBeliColumn;
    @FXML private TableColumn<AsetTetap, String> tglJualColumn;
    @FXML private TableColumn<AsetTetap, String> userJualColumn;
    @FXML private TextField searchField;
    @FXML private Label totalAsetTetapField;
    @FXML private Label totalPenyusutanField;
    @FXML private ComboBox<String> groupByCombo;
    private final ObservableList<AsetTetap> allAsetTetap = FXCollections.observableArrayList();
    private final ObservableList<AsetTetap> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    public void initialize() {
        noAsetTetapColumn.setCellValueFactory(cellData -> cellData.getValue().noAsetProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        userBeliColumn.setCellValueFactory(cellData -> cellData.getValue().userBeliProperty());
        userJualColumn.setCellValueFactory(cellData -> cellData.getValue().userJualProperty());
        masaPakaiColumn.setCellValueFactory(cellData -> {
            String masaPakai = "-";
            if (cellData.getValue().getMasaPakai() != 0) 
                masaPakai = String.valueOf(cellData.getValue().getMasaPakai()) + " Bulan";
            return new SimpleStringProperty(masaPakai);
        });
        tglBeliColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglBeli())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglJualColumn.setCellValueFactory(cellData -> {
            try {
                String tglJual = "-";
                if (cellData.getValue().getStatus().equals("close")) 
                    tglJual = tglLengkap.format(tglSql.parse(cellData.getValue().getTglJual()));
                return new SimpleStringProperty(tglJual);
            } catch (ParseException ex) {
                return null;
            }
        });
        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> Function.getTableCell());
        penyusutanColumn.setCellValueFactory(cellData -> cellData.getValue().penyusutanProperty());
        penyusutanColumn.setCellFactory(col -> Function.getTableCell());
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> Function.getTableCell());
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rm = new ContextMenu();
        MenuItem beli = new MenuItem("Pembelian Aset Tetap");
        beli.setOnAction((ActionEvent e)->{
            showBeliAsetTetap();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getAsetTetap();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Pembelian Aset Tetap")&&o.isStatus())
                rm.getItems().add(beli);
        }
        rm.getItems().addAll(refresh);
        asetTetapTable.setContextMenu(rm);
        asetTetapTable.setRowFactory((TableView<AsetTetap> tableView) -> {
            final TableRow<AsetTetap> row = new TableRow<AsetTetap>(){
                @Override
                public void updateItem(AsetTetap item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem beli = new MenuItem("Pembelian Aset Tetap");
                        beli.setOnAction((ActionEvent e)->{
                            showBeliAsetTetap();
                        });
                        MenuItem jual = new MenuItem("Penjualan Aset Tetap");
                        jual.setOnAction((ActionEvent e)->{
                            showJualAsetTetap(item);
                        });
                        MenuItem lihat = new MenuItem("Lihat Detail Aset Tetap");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailAsetTetap(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getAsetTetap();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Pembelian Aset Tetap")&&o.isStatus())
                                rm.getItems().add(beli);
                            if(o.getJenis().equals("Penjualan Aset Tetap")&&o.isStatus()&&item.getStatus().equals("open"))
                                rm.getItems().add(jual);
                            if(o.getJenis().equals("Lihat Detail Aset Tetap")&&o.isStatus())
                                rm.getItems().add(lihat);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2&&row.getItem()!=null){    
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Lihat Detail Aset Tetap")&&o.isStatus())
                            showDetailAsetTetap(row.getItem());
                    }
                }
            });
            return row;
        });
        
        allAsetTetap.addListener((ListChangeListener.Change<? extends AsetTetap> change) -> {
            searchAsetTetap();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchAsetTetap();
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        asetTetapTable.setItems(filterData);
        ObservableList<String> groupBy = FXCollections.observableArrayList();
        groupBy.add("Tersedia");
        groupBy.add("Terjual");
        groupBy.add("Semua");
        groupByCombo.setItems(groupBy);
        groupByCombo.getSelectionModel().selectFirst();
        getAsetTetap();
    }
    @FXML
    private void getAsetTetap() {
        Task<List<AsetTetap>> task = new Task<List<AsetTetap>>() {
            @Override 
            public List<AsetTetap> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    String status = "%";
                    if (groupByCombo.getSelectionModel().getSelectedItem().equals("Tersedia")) 
                        status = "open";
                    else if (groupByCombo.getSelectionModel().getSelectedItem().equals("Terjual")) 
                        status = "close";
                    return AsetTetapDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allAsetTetap.clear();
            allAsetTetap.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column) {
        if (column != null) 
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) 
                return true;
        return false;
    }
    private void searchAsetTetap() {
        try {
            filterData.clear();
            for (AsetTetap temp : allAsetTetap) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoAset()) || 
                            checkColumn(temp.getNama()) || 
                            checkColumn(temp.getKategori()) || 
                            checkColumn(temp.getKeterangan()) || 
                            checkColumn(String.valueOf(temp.getMasaPakai())) || 
                            checkColumn(df.format(temp.getNilaiAwal())) || 
                            checkColumn(df.format(temp.getPenyusutan())) || 
                            checkColumn(df.format(temp.getNilaiAkhir())) || 
                            checkColumn(df.format(temp.getHargaJual())) || 
                            checkColumn(temp.getUserBeli()) || 
                            checkColumn(temp.getUserJual()) || 
                            checkColumn(tglLengkap.format(tglSql.parse(temp.getTglBeli()))) || 
                            checkColumn(tglLengkap.format(tglSql.parse(temp.getTglJual())))) {
                        filterData.add(temp);
                    }
                }
            }
            hitungTotal();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal() {
        double totalAsetTetap = 0;
        double totalPenyusutan = 0;
        for (AsetTetap temp : filterData) {
            totalAsetTetap = totalAsetTetap + temp.getNilaiAkhir();
            totalPenyusutan = totalPenyusutan + temp.getPenyusutan();
        }
        totalAsetTetapField.setText(df.format(totalAsetTetap));
        totalPenyusutanField.setText(df.format(totalPenyusutan));
    }
    private void showBeliAsetTetap() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewAsetTetap.fxml");
        NewAsetTetapController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if("0".equals(x.HargaField.getText().replaceAll(",", ""))||"".equals(x.HargaField.getText().replaceAll(",", ""))) 
                mainApp.showMessage(Modality.NONE, "Warning", "Harga beli masih kosong");
            else if(x.kategoriCombo.getSelectionModel().getSelectedItem()==null) 
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null) 
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            int masaPakai = (Integer.parseInt(x.tahunField.getText()) * 12) + Integer.parseInt(x.bulanField.getText());
                            AsetTetap asetTetap = new AsetTetap();
                            asetTetap.setNoAset(AsetTetapDAO.getId(con));
                            asetTetap.setNama(x.namaField.getText());
                            asetTetap.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            asetTetap.setKeterangan(x.keteranganField.getText());
                            asetTetap.setMasaPakai(masaPakai);
                            asetTetap.setNilaiAwal(Double.parseDouble(x.HargaField.getText().replaceAll(",", "")));
                            asetTetap.setPenyusutan(0);
                            asetTetap.setNilaiAkhir(Double.parseDouble(x.HargaField.getText().replaceAll(",", "")));
                            asetTetap.setHargaJual(0);
                            asetTetap.setStatus("open");
                            asetTetap.setTglJual("2000-01-01 00:00:00");
                            asetTetap.setUserJual("");
                            asetTetap.setTglBeli(tglSql.format(new Date()));
                            asetTetap.setUserBeli(sistem.getUser().getUsername());
                            return Service.savePembelianAsetTetap(con, asetTetap,x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
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
                        getAsetTetap();
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian Aset Tetap berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void showJualAsetTetap(AsetTetap aset) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewAsetTetap.fxml");
        NewAsetTetapController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setPenjualanAset(aset);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.HargaField.getText().replaceAll(",", ""))||"".equals(x.HargaField.getText().replaceAll(",", ""))) 
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual masih kosong");
            else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) 
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        Thread.sleep(timeout);
                        try(Connection con = Koneksi.getConnection()){
                            aset.setHargaJual(Double.parseDouble(x.HargaField.getText().replaceAll(",", "")));
                            aset.setStatus("close");
                            aset.setTglJual(tglSql.format(new Date()));
                            aset.setUserJual(sistem.getUser().getUsername());
                            return Service.savePenjualanAsetTetap(con, aset,
                                x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getAsetTetap();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan aset tetap berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void showDetailAsetTetap(AsetTetap aset) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailAsetTetap.fxml");
        DetailAsetTetapController x = loader.getController();
        x.setMainApp(mainApp,mainApp.MainStage, stage);
        x.setDetail(aset);
    }
}
