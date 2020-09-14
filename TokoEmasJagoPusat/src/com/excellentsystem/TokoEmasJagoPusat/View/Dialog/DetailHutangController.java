/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.HutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembayaranHutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.Hutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranHutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import java.sql.Connection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailHutangController {

    @FXML public TableView<PembayaranHutang> pembayaranHutangTable;
    @FXML private TableColumn<PembayaranHutang, String> noPembayaranColumn;
    @FXML private TableColumn<PembayaranHutang, String> tglPembayaranColumn;
    @FXML private TableColumn<PembayaranHutang, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<PembayaranHutang, Number> kursColumn;
    @FXML private TableColumn<PembayaranHutang, Number> terbayarGrColumn;
    @FXML private TableColumn<PembayaranHutang, Number> terbayarRpColumn;
    @FXML private TableColumn<PembayaranHutang, String> kodeUserColumn;
    
    @FXML private TextField noHutangField;
    @FXML private TextField tglHutangField;
    @FXML private TextField kategoriField;
    @FXML private TextField keteranganField;
    @FXML private TextField jumlahHutangField;
    @FXML private TextField hargaEmasField;
    @FXML private Label terbayarLabel;
    @FXML private Label sisaHutangLabel;
    
    private ObservableList<PembayaranHutang> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noPembayaranProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembayaran())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahBayarProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell(rp));
        kursColumn.setCellValueFactory(cellData -> cellData.getValue().kursProperty());
        kursColumn.setCellFactory(col -> Function.getTableCell(rp));
        terbayarGrColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getTerbayar()/Double.parseDouble(hargaEmasField.getText().replaceAll(",", "")));
        });
        terbayarGrColumn.setCellFactory(col -> Function.getTableCell(gr));
        terbayarRpColumn.setCellValueFactory(cellData -> cellData.getValue().terbayarProperty());
        terbayarRpColumn.setCellFactory(col -> Function.getTableCell(rp));
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
    }    
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        pembayaranHutangTable.setItems(allPembayaran);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }   
    public void setDetail(Hutang hutang){
        Task<Hutang> task = new Task<Hutang>() {
            @Override 
            public Hutang call() throws Exception{
                try (Connection con = KoneksiPusat.getConnection()) {
                    hutang.setListPembayaranHutang(PembayaranHutangDAO.getAllByDateAndNoHutangAndStatus(
                            con, "2000-01-01", sistem.getTglSystem(), hutang.getNoHutang(), "true"));
                    return hutang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            setHutang(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setDetailPembelian(PembelianHead p){
        Task<Hutang> task = new Task<Hutang>() {
            @Override 
            public Hutang call() throws Exception{
                try (Connection con = KoneksiPusat.getConnection()) {
                    Hutang hutang = HutangDAO.getByNoPembelian(con, p.getNoPembelian());
                    hutang.setListPembayaranHutang(PembayaranHutangDAO.getAllByDateAndNoHutangAndStatus(
                            con, "2000-01-01", sistem.getTglSystem(), hutang.getNoHutang(), "true"));
                    return hutang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            setHutang(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void setHutang(Hutang hutang){
        try{
            noHutangField.setText(hutang.getNoHutang());
            tglHutangField.setText(tglLengkap.format(tglSql.parse(hutang.getTglHutang())));
            kategoriField.setText(hutang.getSupplier());
            keteranganField.setText(hutang.getNoPembelian());
            jumlahHutangField.setText(gr.format(hutang.getJumlahHutang()/hutang.getKurs()));
            hargaEmasField.setText(rp.format(hutang.getKurs()));
            terbayarLabel.setText(gr.format(hutang.getTerbayar()/hutang.getKurs()));
            sisaHutangLabel.setText(gr.format(hutang.getSisaHutang()/hutang.getKurs()));
            allPembayaran.clear();
            allPembayaran.addAll(hutang.getListPembayaranHutang());
        }catch(Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    public void close() {
        mainApp.closeDialog(owner, stage);
    }
    
}
