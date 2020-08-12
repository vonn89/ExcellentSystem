/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailAsetTetapController  {

    @FXML private TextField noAsetTetapField;
    @FXML private TextField namaField;
    @FXML private TextField kategoriField;
    @FXML private TextArea keteranganField;
    @FXML private TextField tglBeliField;
    @FXML private TextField userBeliField;
    @FXML private TextField hargaBeliField;
    @FXML private TextField masaPakaiField;
    @FXML private TextField penyusutanPerBulanField;
    @FXML private TextField statusField;
    @FXML private TextField tglJualField;
    @FXML private TextField userJualField;
    @FXML private TextField hargaJualField;
    @FXML private TextField penyusutanField;
    @FXML private TextField nilaiAkhirField;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }   
    public void setDetail(AsetTetap aset){
        try{
            noAsetTetapField.setText(aset.getNoAset());
            namaField.setText(aset.getNama());
            kategoriField.setText(aset.getKategori());
            keteranganField.setText(aset.getKeterangan());
            tglBeliField.setText(tglLengkap.format(tglSql.parse(aset.getTglBeli())));
            userBeliField.setText(aset.getUserBeli());
            hargaBeliField.setText("Rp "+df.format(aset.getNilaiAwal()));
            masaPakaiField.setText(df.format(aset.getMasaPakai())+" Bulan");
            penyusutanPerBulanField.setText("Rp "+df.format(aset.getNilaiAwal()/aset.getMasaPakai()));
            penyusutanField.setText("Rp "+df.format(aset.getPenyusutan()));
            nilaiAkhirField.setText("Rp "+df.format(aset.getNilaiAkhir()));
            if(aset.getStatus().equals("open")){
                statusField.setText("Tersedia");
                tglJualField.setText("-");
                userJualField.setText("-");
                hargaJualField.setText("-");
            }else{
                statusField.setText("Terjual");
                tglJualField.setText(tglLengkap.format(tglSql.parse(aset.getTglBeli())));
                userJualField.setText(aset.getUserJual());
                hargaJualField.setText("Rp "+df.format(aset.getHargaJual()));
            }
        }catch(Exception ex){
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
