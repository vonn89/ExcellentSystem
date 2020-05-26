/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCiokCabang;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailPenjualanCiokCabangController  {

    @FXML private TextField noPenjualanField;
    @FXML private TextField tglPenjualanField;
    @FXML private TextField kodeUserField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField beratField;
    @FXML private TextField nilaiPokokField;
    @FXML private TextField hargaEmasField;
    @FXML private TextField totalPenjualanField;
    
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
    public void setPenjualanCiok(PenjualanCiokCabang p){
        try{
            noPenjualanField.setText(p.getNoPenjualan());
            tglPenjualanField.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
            kodeUserField.setText(p.getKodeUser());
            kodeCabangField.setText(p.getKodeCabang());
            beratField.setText(gr.format(p.getBerat()));
            nilaiPokokField.setText(rp.format(p.getNilaiPokok()));
            hargaEmasField.setText(gr.format(p.getHargaEmas()));
            totalPenjualanField.setText(gr.format(p.getTotalPenjualan()));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
