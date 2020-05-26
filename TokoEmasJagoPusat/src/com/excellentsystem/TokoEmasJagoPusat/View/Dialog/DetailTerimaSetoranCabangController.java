/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.SetoranCabang;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailTerimaSetoranCabangController {

    @FXML private TextField noSetorField;
    @FXML private TextField tglSetorField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField tipeKasirField;
    @FXML private TextField jumlahRpField;
    @FXML private TextField userSetorField;
    @FXML private TextField tglTerimaField;
    @FXML private TextField userTerimaField;
        
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setSetor(SetoranCabang s){
        try{
            noSetorField.setText(s.getNoSetor());
            tglSetorField.setText(tglLengkap.format(tglSql.parse(s.getTglSetor())));
            kodeCabangField.setText(s.getKodeCabang());
            tipeKasirField.setText(s.getTipeKasir());
            jumlahRpField.setText(rp.format(s.getJumlahRp()));
            userSetorField.setText(s.getKodeUser());
            tglTerimaField.setText(tglLengkap.format(tglSql.parse(s.getTglTerima())));
            userTerimaField.setText(s.getUserTerima());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
