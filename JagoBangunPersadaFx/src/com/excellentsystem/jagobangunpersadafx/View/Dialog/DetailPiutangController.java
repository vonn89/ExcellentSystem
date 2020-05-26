/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PiutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TerimaPembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
import com.excellentsystem.jagobangunpersadafx.Model.TerimaPembayaran;
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
public class DetailPiutangController {

    @FXML public TableView<TerimaPembayaran> pembayaranPiutangTable;
    @FXML private TableColumn<TerimaPembayaran, String> noPembayaranColumn;
    @FXML private TableColumn<TerimaPembayaran, String> tglPembayaranColumn;
    @FXML private TableColumn<TerimaPembayaran, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<TerimaPembayaran, String> tipeKeuanganColumn;
    @FXML private TableColumn<TerimaPembayaran, String> catatanColumn;
    
    @FXML private TextField noPiutangField;
    @FXML private TextField tglPiutangField;
    @FXML private TextField kategoriField;
    @FXML private TextField keteranganField;
    @FXML private TextField jumlahPiutangField;
    @FXML private Label terbayarLabel;
    @FXML private Label sisaPiutangLabel;
    private ObservableList<TerimaPembayaran> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noTerimaPembayaranProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell(rp));
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        
    }    
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        pembayaranPiutangTable.setItems(allPembayaran);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }   
    public void setDetail(String noPiutang){
        Task<Piutang> task = new Task<Piutang>() {
            @Override 
            public Piutang call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    Piutang p = PiutangDAO.get(con, noPiutang);
                    p.setAllTerimaPembayaran(TerimaPembayaranDAO.getAllByNoPiutang(con, p.getNoPiutang(), "true"));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                Piutang p = task.getValue();
                noPiutangField.setText(p.getNoPiutang());
                tglPiutangField.setText(tglLengkap.format(tglSql.parse(p.getTglPiutang())));
                kategoriField.setText(p.getKategori());
                keteranganField.setText(p.getKeterangan());
                jumlahPiutangField.setText("Rp "+rp.format(p.getJumlahPiutang()));
                terbayarLabel.setText(rp.format(p.getPembayaran()));
                sisaPiutangLabel.setText(rp.format(p.getSisaPiutang()));
                allPembayaran.clear();
                allPembayaran.addAll(p.getAllTerimaPembayaran());
            }catch(Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void close() {
        mainApp.closeDialog(owner, stage);
    }
    
}
