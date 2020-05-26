/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Bahan;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailBahanBakuController  {

    @FXML private TextField kodeKategoriField;
    @FXML private TextField noKontrakField;
    @FXML private TextField kodeBahanField;
    @FXML public TextField namaBahanField;
    @FXML public TextArea spesifikasiField;
    @FXML public TextField keteranganField;
    @FXML private TextField beratKotorField;
    @FXML private TextField beratBersihField;
    @FXML private TextField panjangField;
    @FXML private TextField hargaBeliField;
    
    @FXML public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setBahan(Bahan b){
        kodeKategoriField.setText("");
        noKontrakField.setText("");
        kodeBahanField.setText("");
        namaBahanField.setText("");
        spesifikasiField.setText("");
        keteranganField.setText("");
        hargaBeliField.setText("0");
        beratKotorField.setText("0");
        beratBersihField.setText("0");
        panjangField.setText("0");
        if(b!=null && b.getNamaBahan()!=null){
            kodeKategoriField.setText(b.getKodeKategori());
            noKontrakField.setText(b.getNoKontrak());
            kodeBahanField.setText(b.getKodeBahan());
            namaBahanField.setText(b.getNamaBahan());
            spesifikasiField.setText(b.getSpesifikasi());
            keteranganField.setText(b.getKeterangan());
            hargaBeliField.setText(df.format(b.getHargaBeli()));
            beratKotorField.setText(df.format(b.getBeratKotor()));
            beratBersihField.setText(df.format(b.getBeratBersih()));
            panjangField.setText(df.format(b.getPanjang()));
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
}
