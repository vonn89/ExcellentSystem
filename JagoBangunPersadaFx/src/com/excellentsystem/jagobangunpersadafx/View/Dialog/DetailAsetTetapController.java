/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.AsetTetap;
import java.text.ParseException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailAsetTetapController {

    @FXML
    private TextField noAsetTetapField;
    @FXML
    private TextField namaField;
    @FXML
    private TextField kategoriField;
    @FXML
    private TextArea keteranganField;
    @FXML
    private TextField tglBeliField;
    @FXML
    private TextField userBeliField;
    @FXML
    private TextField hargaBeliField;
    @FXML
    private TextField masaPakaiField;
    @FXML
    private TextField penyusutanPerBulanFIeld;
    @FXML
    private TextField statusField;
    @FXML
    private TextField tglJualField;
    @FXML
    private TextField userJualField;
    @FXML
    private TextField hargaJualField;
    @FXML
    private TextField penyusutanField;
    @FXML
    private TextField nilaiAkhirField;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setDetail(AsetTetap aset) {
        if (aset != null) {
            try {
                noAsetTetapField.setText(aset.getNoAset());
                namaField.setText(aset.getNama());
                kategoriField.setText(aset.getKategori());
                keteranganField.setText(aset.getKeterangan());
                tglBeliField.setText(tglLengkap.format(tglSql.parse(aset.getTglBeli())));
                userBeliField.setText(aset.getUserBeli());
                hargaBeliField.setText("Rp " + rp.format(aset.getNilaiAwal()));
                masaPakaiField.setText(qty.format(aset.getMasaPakai()) + " Bulan");
                penyusutanPerBulanFIeld.setText("Rp " + rp.format(aset.getNilaiAwal() / aset.getMasaPakai()));
                penyusutanField.setText("Rp " + rp.format(aset.getPenyusutan()));
                nilaiAkhirField.setText("Rp " + rp.format(aset.getNilaiAkhir()));
                if (aset.getStatus().equals("open")) {
                    statusField.setText("Tersedia");
                    tglJualField.setText("-");
                    userJualField.setText("-");
                    hargaJualField.setText("-");
                } else {
                    statusField.setText("Terjual");
                    tglJualField.setText(tglLengkap.format(tglSql.parse(aset.getTglJual())));
                    userJualField.setText(aset.getUserJual());
                    hargaJualField.setText("Rp " + rp.format(aset.getHargaJual()));
                }
            } catch (ParseException ex) {
                close();
            }
        } else {
            close();
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
