/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PembelianTanahDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import com.excellentsystem.jagobangunpersadafx.Model.PembelianTanah;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.Date;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPembelianTanahController  {
    @FXML public TextField noPembelianField;
    @FXML public TextField tglPembelianField;
    @FXML public TextField kodePropertyField;
    @FXML public TextField namaPropertyField;
    @FXML public TextArea alamatField;
    @FXML public TextField luasTanahField;
    @FXML public TextField hargaBeliField;
    @FXML public TextField hargaBeliPerMeterField;
    @FXML public GridPane gridPane;
    @FXML public TextField jumlahBayarField;
    @FXML public TextField hutangField;
    @FXML public TextArea keteranganField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    
    private String noPembelian;
    private String kodeProperty;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        jumlahBayarField.setOnKeyReleased((event) -> {
            try{
                jumlahBayarField.setText(rp.format(Double.parseDouble(jumlahBayarField.getText().replaceAll(",", ""))));
                jumlahBayarField.end();
            }catch(Exception e){
                jumlahBayarField.undo();
            }
            hutangField.setText(rp.format(Double.parseDouble(hargaBeliField.getText().replaceAll(",", ""))-
                    Double.parseDouble(jumlahBayarField.getText().replaceAll(",", ""))));
        });
        luasTanahField.setOnKeyReleased((event) -> {
            try{
                luasTanahField.setText(rp.format(Double.parseDouble(luasTanahField.getText().replaceAll(",", ""))));
                luasTanahField.end();
            }catch(Exception e){
                luasTanahField.undo();
            }
            hitungHargaBeliPerMeter();
        });
        hargaBeliField.setOnKeyReleased((event) -> {
            try{
                hargaBeliField.setText(rp.format(Double.parseDouble(hargaBeliField.getText().replaceAll(",", ""))));
                hargaBeliField.end();
            }catch(Exception e){
                hargaBeliField.undo();
            }
            jumlahBayarField.setText(hargaBeliField.getText());
            hitungHargaBeliPerMeter();
        });
        Function.setNumberField(hargaBeliPerMeterField);
    }    
    public void setMainApp(Main mainApp,Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setNewPembelian(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    noPembelian = PembelianTanahDAO.getId(con);
                    kodeProperty = PropertyDAO.getId(con);
                }
                return null;
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            noPembelianField.setText(noPembelian);
            tglPembelianField.setText(tglLengkap.format(new Date()));
            kodePropertyField.setText(kodeProperty);
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void getPembelian(String noPembelian){
        Task<PembelianTanah> task = new Task<PembelianTanah>() {
            @Override 
            public PembelianTanah call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PembelianTanah pt = PembelianTanahDAO.get(con, noPembelian);
                    pt.setProperty(PropertyDAO.get(con, pt.getKodeProperty()));
                    return pt;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            setPembelian(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void getPembelian(Property p){
        Task<PembelianTanah> task = new Task<PembelianTanah>() {
            @Override 
            public PembelianTanah call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PembelianTanah pt = PembelianTanahDAO.getByKodeProperty(con, p.getKodeProperty());
                    pt.setProperty(p);
                    return pt;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            setPembelian(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void setPembelian(PembelianTanah p){
        noPembelianField.setText(p.getNoPembelian());
        tglPembelianField.setText(p.getTglPembelian());
        kodePropertyField.setText(p.getKodeProperty());
        namaPropertyField.setText(p.getProperty().getNamaProperty());
        namaPropertyField.setDisable(true);
        alamatField.setText(p.getProperty().getAlamat());
        alamatField.setDisable(true);
        luasTanahField.setText(qty.format(p.getLuasTanah()));
        luasTanahField.setDisable(true);
        hargaBeliField.setText(rp.format(p.getHargaBeli()));
        hargaBeliField.setDisable(true);
        hargaBeliPerMeterField.setText(rp.format(p.getHargaBeli()/p.getLuasTanah()));
        keteranganField.setText(p.getKeterangan());
        keteranganField.setDisable(true);
        gridPane.setVisible(false);
        stage.setHeight(stage.getHeight()-150);
    }
    @FXML
    private void hitungHargaBeliPerMeter(){
        if(hargaBeliField.getText().equals(""))
            hargaBeliField.setText("0");
        if(luasTanahField.getText().equals(""))
            luasTanahField.setText("0");
        if(!"0".equals(luasTanahField.getText()))
            hargaBeliPerMeterField.setText(rp.format(
                Double.parseDouble(hargaBeliField.getText().replaceAll(",", ""))/
                Double.parseDouble(luasTanahField.getText().replaceAll(",", ""))));
        else
            hargaBeliPerMeterField.setText("0");
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
