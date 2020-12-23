/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.HutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import com.excellentsystem.jagobangunpersadafx.Model.Pembayaran;
import java.sql.Connection;
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

    @FXML
    public TableView<Pembayaran> pembayaranHutangTable;
    @FXML
    private TableColumn<Pembayaran, String> noPembayaranColumn;
    @FXML
    private TableColumn<Pembayaran, String> tglPembayaranColumn;
    @FXML
    private TableColumn<Pembayaran, Number> jumlahPembayaranColumn;
    @FXML
    private TableColumn<Pembayaran, String> tipeKeuanganColumn;
    @FXML
    private TableColumn<Pembayaran, String> catatanColumn;

    @FXML
    private TextField noHutangField;
    @FXML
    private TextField tglHutangField;
    @FXML
    private TextField kategoriField;
    @FXML
    private TextField keteranganField;
    @FXML
    private TextField jumlahHutangField;
    @FXML
    private Label terbayarLabel;
    @FXML
    private Label sisaHutangLabel;

    private ObservableList<Pembayaran> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noPembayaranProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembayaran())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell(rp));
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

    public void setDetail(Hutang hutang) {
        Task<Hutang> task = new Task<Hutang>() {
            @Override
            public Hutang call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    hutang.setAllPembayaran(PembayaranDAO.getAllByNoHutangAndStatus(con, hutang.getNoHutang(), "true"));
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
    public void setDetailPembangunanKontraktor(PembangunanKontraktorHead p) {
        Task<Hutang> task = new Task<Hutang>() {
            @Override
            public Hutang call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    Hutang hutang = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Hutang Pembangunan Kontraktor", p.getNoPembangunan(), "%");
                    hutang.setAllPembayaran(PembayaranDAO.getAllByNoHutangAndStatus(con, hutang.getNoHutang(), "true"));
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

    private void setHutang(Hutang hutang) {
        try {
            noHutangField.setText(hutang.getNoHutang());
            tglHutangField.setText(tglLengkap.format(tglSql.parse(hutang.getTglHutang())));
            kategoriField.setText(hutang.getKategori());
            keteranganField.setText(hutang.getKeterangan());
            jumlahHutangField.setText("Rp " + rp.format(hutang.getJumlahHutang()));
            terbayarLabel.setText(rp.format(hutang.getPembayaran()));
            sisaHutangLabel.setText(rp.format(hutang.getSisaHutang()));
            allPembayaran.clear();
            allPembayaran.addAll(hutang.getAllPembayaran());
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
