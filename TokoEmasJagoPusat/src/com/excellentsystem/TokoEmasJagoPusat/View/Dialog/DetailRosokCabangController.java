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
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailRosokCabangController {

    @FXML private TextField noRosokField;
    @FXML private TextField tglRosokField;
    @FXML private TextField kategoriField;
    @FXML private TextField keteranganField;
    @FXML private TextField kodeUserField;
    @FXML private TextField kodeCabangField;
    
    @FXML private Label kodeGudangLabel;
    @FXML private TextField kodeGudangField;
    
    @FXML private TextField kodeJenisField;
    @FXML private TextField qtyField;
    @FXML private TextField beratField;
    @FXML private TextField persentaseEmasField;
    @FXML private TextField beratPersenField;
    
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
    public void setBarang(RosokCabang r){
        try{
            noRosokField.setText(r.getNoRosok());
            tglRosokField.setText(tglLengkap.format(tglSql.parse(r.getTglRosok())));
            kategoriField.setText(r.getKategori());
            keteranganField.setText(r.getKeterangan());
            kodeUserField.setText(r.getKodeUser());
            kodeCabangField.setText(r.getKodeCabang());
            
            if(r.getKategori().equals("Pindah Rosok Cabang"))
                kodeGudangLabel.setText("Gudang Asal");
            else if(r.getKategori().equals("Ambil Rosok Cabang"))
                kodeGudangLabel.setText("Gudang Tujuan");
            kodeGudangField.setText(r.getKodeGudang());

            kodeJenisField.setText(r.getKodeJenis());
            qtyField.setText(rp.format(r.getQty()));
            beratField.setText(gr.format(r.getBerat()));
            persentaseEmasField.setText(gr.format(r.getPersentaseEmas()));
            beratPersenField.setText(gr.format(r.getBeratPersenRosok()));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
