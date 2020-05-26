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
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailRevisiBarangCabangController  {

    @FXML private TextField noRevisiField;
    @FXML private TextField tglRevisiField;
    @FXML private TextField jenisRevisiField;
    @FXML private TextField keteranganField;
    @FXML private TextField kodeUserField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField kodeGudangField;
    
    @FXML private TextField kodeJenisField;
    @FXML private TextField kodeJenisRevisiField;
    @FXML private TextField qtyRevisiField;
    @FXML private TextField beratRevisiField;
    
    @FXML private VBox vbox;
    @FXML private HBox kodeJenisHbox;
    @FXML private HBox qtyHbox;
    @FXML private HBox beratHbox;
    
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
        for(Node n : vbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
    }
    public void setBarang(RevisiBarangCabang r){
        try{
            noRevisiField.setText(r.getNoRevisi());
            tglRevisiField.setText(tglLengkap.format(tglSql.parse(r.getTglRevisi())));
            keteranganField.setText(r.getKeterangan());
            jenisRevisiField.setText(r.getJenisRevisi());
            kodeUserField.setText(r.getKodeUser());
            kodeCabangField.setText(r.getKodeCabang());
            kodeGudangField.setText(r.getKodeGudang());

            kodeJenisField.setText(r.getKodeJenis());
            kodeJenisRevisiField.setText(r.getKodeJenisRevisi());
            qtyRevisiField.setText(rp.format(r.getQtyRevisi()));
            beratRevisiField.setText(gr.format(r.getBeratRevisi()));
            setJenisRevisi();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void setJenisRevisi(){
        if(jenisRevisiField.getText().equals("Ubah Jenis")){
            kodeJenisHbox.setVisible(true);
            qtyHbox.setVisible(true);
            beratHbox.setVisible(true);
            stage.setHeight(470);
        }else if(jenisRevisiField.getText().equals("Penambahan Stok") || jenisRevisiField.getText().equals("Pengurangan Stok")){
            kodeJenisHbox.setVisible(false);
            qtyHbox.setVisible(true);
            beratHbox.setVisible(false);
            stage.setHeight(405);
        }else if(jenisRevisiField.getText().equals("Penambahan Berat") || jenisRevisiField.getText().equals("Pengurangan Berat")){
            kodeJenisHbox.setVisible(false);
            qtyHbox.setVisible(false);
            beratHbox.setVisible(true);
            stage.setHeight(405);
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
