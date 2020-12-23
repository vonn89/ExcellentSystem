/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPembayaranHutangController {

    @FXML
    public TextField noHutangField;
    @FXML
    public TextField totalHutangField;
    @FXML
    public TextField terbayarField;
    @FXML
    public TextField sisaHutangField;
    @FXML
    public TextField jumlahPembayaranField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        Function.setNumberField(jumlahPembayaranField);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
    }

    public void setNewPembayaran(Hutang hutang) {
        noHutangField.setText(hutang.getNoHutang());
        totalHutangField.setText(rp.format(hutang.getJumlahHutang()));
        terbayarField.setText(rp.format(hutang.getPembayaran()));
        sisaHutangField.setText(rp.format(hutang.getSisaHutang()));
    }

    public void setNewPembayaranPembangunanKontraktor(PembangunanKontraktorHead p) {
        noHutangField.setText(p.getNoPembangunan());
        totalHutangField.setText(rp.format(p.getTotalPembangunan()));
        terbayarField.setText(rp.format(p.getPembayaran()));
        sisaHutangField.setText(rp.format(p.getSisaPembayaran()));
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
