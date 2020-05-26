/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.SetoranCabang;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class TerimaSetoranCabangController {

    @FXML public TextField noSetorField;
    @FXML private TextField tglSetorField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField tipeKasirField;
    @FXML private TextField jumlahRpField;
    
    @FXML public CheckBox bayarHutangCheck;
    @FXML public Button saveButton;
    
    public SetoranCabang setoran;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noSetorField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                getSetoran();
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    @FXML
    private void getSetoran(){
        if(noSetorField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "No setoran masih kosong");
        else{
            Task<SetoranCabang> task = new Task<SetoranCabang>() {
                @Override 
                public SetoranCabang call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return SetoranCabangDAO.get(conPusat, noSetorField.getText());
                    }
                }
            };
            task.setOnRunning((eb) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((eb) -> {
                mainApp.closeLoading();
                setoran = task.getValue();
                if(setoran==null){
                    mainApp.showMessage(Modality.NONE, "Warning", "Setoran cabang tidak ditemukan");
                }else{
                    try{
                        noSetorField.setText(setoran.getNoSetor());
                        tglSetorField.setText(tglLengkap.format(tglSql.parse(setoran.getTglSetor())));
                        kodeCabangField.setText(setoran.getKodeCabang());
                        tipeKasirField.setText(setoran.getTipeKasir());
                        jumlahRpField.setText(rp.format(setoran.getJumlahRp()));
                        saveButton.requestFocus();
                    }catch(Exception e){
                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                    }
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void reset(){
        setoran=null;
        noSetorField.setText("");
        tglSetorField.setText("");
        kodeCabangField.setText("");
        tipeKasirField.setText("");
        jumlahRpField.setText("0");
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
