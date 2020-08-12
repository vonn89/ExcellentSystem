/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranHutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
    @FXML private TableColumn<PembayaranHutang, String> tipeKeuanganColumn;
    @FXML private TableColumn<PembayaranHutang, String> catatanColumn;
    
    @FXML private TextField noHutangField;
    @FXML private TextField tglHutangField;
    @FXML private TextField kategoriField;
    @FXML private TextField keteranganField;
    @FXML private TextField jumlahHutangField;
    @FXML private Label terbayarLabel;
    @FXML private Label sisaHutangLabel;
    private ObservableList<PembayaranHutang> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noPembayaranProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembayaran())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        pembayaranHutangTable.setItems(allPembayaran);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }      
    public void setDetail(Hutang h){
        Task<List<PembayaranHutang>> task = new Task<List<PembayaranHutang>>() {
            @Override 
            public List<PembayaranHutang> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return PembayaranHutangDAO.getAllByNoHutangAndStatus(con, h.getNoHutang(), "true");
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try{
                mainApp.closeLoading();
                h.setAllPembayaran(task.get());
                noHutangField.setText(h.getNoHutang());
                tglHutangField.setText(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                kategoriField.setText(h.getKategori());
                keteranganField.setText(h.getKeterangan());
                jumlahHutangField.setText(df.format(h.getJumlahHutang()));
                terbayarLabel.setText(df.format(h.getPembayaran()));
                sisaHutangLabel.setText(df.format(h.getSisaHutang()));
                allPembayaran.clear();
                allPembayaran.addAll(h.getAllPembayaran());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void setDetailPembelian(PembelianHead p){
        Task<Hutang> task = new Task<Hutang>() {
            @Override 
            public Hutang call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    Hutang hutang = HutangDAO.getByKategoriAndKeterangan(con, "Hutang Pembelian", p.getNoPembelian());
                    List<PembayaranHutang> listPembayaran = PembayaranHutangDAO.getAllByNoHutangAndStatus(con, hutang.getNoHutang(), "true");
                    hutang.setAllPembayaran(listPembayaran);
                    return hutang;
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try{
                mainApp.closeLoading();
                Hutang hutang = task.get();
                noHutangField.setText(hutang.getNoHutang());
                tglHutangField.setText(tglLengkap.format(tglSql.parse(hutang.getTglHutang())));
                kategoriField.setText(hutang.getKategori());
                keteranganField.setText(hutang.getKeterangan());
                jumlahHutangField.setText(df.format(hutang.getJumlahHutang()));
                terbayarLabel.setText(df.format(hutang.getPembayaran()));
                sisaHutangLabel.setText(df.format(hutang.getSisaHutang()));
                allPembayaran.clear();
                allPembayaran.addAll(hutang.getAllPembayaran());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    public void close() {
        mainApp.closeDialog(owner, stage);
    }
    
}
