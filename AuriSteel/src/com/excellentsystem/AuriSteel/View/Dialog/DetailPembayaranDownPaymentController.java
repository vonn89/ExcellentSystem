/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import java.sql.Connection;
import java.text.ParseException;
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
public class DetailPembayaranDownPaymentController {

    @FXML public TableView<Piutang> piutangTable;
    @FXML private TableColumn<Piutang, String> noPiutangColumn;
    @FXML private TableColumn<Piutang, String> tglPiutangColumn;
    @FXML private TableColumn<Piutang, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<Piutang, String> tipeKeuanganColumn;
    
    @FXML private TextField noPemesananField;
    @FXML private TextField tglPemesananField;
    @FXML private TextField totalPemesananField;
    @FXML private Label totalDownPaymentLabel;
    @FXML private Label sisaPembayaranLabel;
    
    private ObservableList<Piutang> listPiutang = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPiutangColumn.setCellValueFactory(cellData -> cellData.getValue().noPiutangProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglPiutangColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPiutang())));
            }catch(ParseException ex){
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPiutangProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell());
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        piutangTable.setItems(listPiutang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }     
    public void setDetailPemesananPembelianBahan(PemesananPembelianBahanHead p){
        Task<List<Piutang>> task = new Task<List<Piutang>>() {
            @Override 
            public List<Piutang> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return PiutangDAO.getAllByKategoriAndKeteranganAndStatus(
                            con, "Pembayaran Down Payment", p.getNoPemesanan(), "%");
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try {
                mainApp.closeLoading();
                p.setListPiutang(task.getValue());
                for(Piutang pt : p.getListPiutang()){
                    pt.setPemesananPembelianBahanHead(p);
                }
                noPemesananField.setText(p.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(p.getTglPemesanan())));
                totalPemesananField.setText(df.format(p.getTotalPemesanan()));
                totalDownPaymentLabel.setText(df.format(p.getDownPayment()));
                sisaPembayaranLabel.setText(df.format(p.getTotalPemesanan()-p.getDownPayment()));
                listPiutang.clear();
                listPiutang.addAll(p.getListPiutang());
            } catch (Exception ex) {
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
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
    
}
