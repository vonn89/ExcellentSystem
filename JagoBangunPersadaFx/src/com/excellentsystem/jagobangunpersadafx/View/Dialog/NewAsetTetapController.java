/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.AsetTetap;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewAsetTetapController {

    @FXML
    private GridPane penyusutanPane;
    @FXML
    private Label title;
    @FXML
    private Label harga;
    @FXML
    private Label kekurangan;
    @FXML
    public TextField namaField;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField bulanField;
    @FXML
    public TextField tahunField;
    @FXML
    public TextField hargaField;
    @FXML
    public TextField jumlahBayarField;
    @FXML
    public TextField kekuranganField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
        kategoriCombo.setItems(Function.getKategoriAset());
        hargaField.setOnKeyReleased((event) -> {
            try {
                hargaField.setText(rp.format(Double.parseDouble(hargaField.getText().replaceAll(",", ""))));
                hargaField.end();
            } catch (Exception e) {
                hargaField.undo();
            }
            jumlahBayarField.setText(hargaField.getText());
            hitungsisa();
        });
        jumlahBayarField.setOnKeyReleased((event) -> {
            try {
                jumlahBayarField.setText(rp.format(Double.parseDouble(jumlahBayarField.getText().replaceAll(",", ""))));
                jumlahBayarField.end();
            } catch (Exception e) {
                jumlahBayarField.undo();
            }
            hitungsisa();
        });
        Function.setNumberField(bulanField);
        Function.setNumberField(tahunField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setPenjualanAset(AsetTetap aset) {
        title.setText("Penjualan Aset");
        harga.setText("Harga Jual");
        kekurangan.setText("Piutang Aset");
        namaField.setText(aset.getNama());
        kategoriCombo.getSelectionModel().select(aset.getKategori());
        keteranganField.setText(aset.getKeterangan());
        namaField.setDisable(true);
        kategoriCombo.setDisable(true);
        keteranganField.setDisable(true);
        stage.setHeight(370);
        penyusutanPane.setVisible(false);
    }

    private void hitungsisa() {
        kekuranganField.setText(rp.format(Double.parseDouble(hargaField.getText().replaceAll(",", ""))
                - Double.parseDouble(jumlahBayarField.getText().replaceAll(",", ""))));
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
