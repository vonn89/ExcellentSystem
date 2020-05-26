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
import com.excellentsystem.TokoEmasJagoPusat.Model.LeburRosokCabang;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailLeburRosokCabangController  {

    @FXML private GridPane gridPane;
    @FXML private TextField noLeburField;
    @FXML private TextField tglLeburField;
    @FXML private TextField userLeburField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField beratKotorField;
    @FXML private TextField beratPersenField;
    @FXML private TextField nilaiPokokField;
    
    @FXML private Label beratJadiLabel;
    @FXML private Label tglSelesaiLabel;
    @FXML private Label userSelesaiLabel;
    @FXML private TextField beratJadiField;
    @FXML private TextField tglSelesaiField;
    @FXML private TextField userSelesaiField;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setLeburRosokCabang(LeburRosokCabang l){
        try{
            noLeburField.setText(l.getNoLebur());
            tglLeburField.setText(tglLengkap.format(tglSql.parse(l.getTglLebur())));
            userLeburField.setText(l.getKodeUser());
            kodeCabangField.setText(l.getKodeCabang());
            beratKotorField.setText(gr.format(l.getBeratKotor()));
            beratPersenField.setText(gr.format(l.getBeratPersen()));
            nilaiPokokField.setText(rp.format(l.getNilaiPokok()));
            if(l.getStatusSelesai().equals("false")){
                gridPane.getRowConstraints().get(10).setMinHeight(0);
                gridPane.getRowConstraints().get(10).setPrefHeight(0);
                gridPane.getRowConstraints().get(10).setMaxHeight(0);
                gridPane.getRowConstraints().get(11).setMinHeight(0);
                gridPane.getRowConstraints().get(11).setPrefHeight(0);
                gridPane.getRowConstraints().get(11).setMaxHeight(0);
                gridPane.getRowConstraints().get(12).setMinHeight(0);
                gridPane.getRowConstraints().get(12).setPrefHeight(0);
                gridPane.getRowConstraints().get(12).setMaxHeight(0);
                
                stage.setHeight(325);
                beratJadiLabel.setVisible(false);
                tglSelesaiLabel.setVisible(false);
                userSelesaiLabel.setVisible(false);
                beratJadiField.setVisible(false);
                tglSelesaiField.setVisible(false);
                userSelesaiField.setVisible(false);
            }else{
                beratJadiField.setText(gr.format(l.getBeratJadi()));
                tglSelesaiField.setText(tglLengkap.format(tglSql.parse(l.getTglSelesai())));
                userSelesaiField.setText(l.getUserSelesai());
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
