/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddPekerjaanController {

    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField pekerjaanField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField satuanField;
    @FXML
    public TextField volumeField;
    @FXML
    public TextField hargaSatuanField;
    @FXML
    public TextField totalField;
    @FXML
    public Button saveButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        volumeField.setOnKeyReleased((event) -> {
            try {
                String string = volumeField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        volumeField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        volumeField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    volumeField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                volumeField.end();
            } catch (Exception e) {
                volumeField.undo();
            }
            hitungTotal();
        });
        hargaSatuanField.setOnKeyReleased((event) -> {
            try {
                String string = hargaSatuanField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        hargaSatuanField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        hargaSatuanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    hargaSatuanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                hargaSatuanField.end();
            } catch (Exception e) {
                hargaSatuanField.undo();
            }
            hitungTotal();
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void setKategori(List<String> listKategori) {
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        for (String s : listKategori) {
            allKategori.add(s);
        }
        kategoriCombo.setItems(allKategori);
    }

    public void setPekerjaan(RAPDetail d) {
        kategoriCombo.getSelectionModel().select(d.getKategori());
        pekerjaanField.setText(d.getPekerjaan());
        keteranganField.setText(d.getKeterangan());
        satuanField.setText(d.getSatuan());
        volumeField.setText(qty.format(d.getVolume()));
        hargaSatuanField.setText(rp.format(d.getHargaSatuan()));
        totalField.setText(rp.format(d.getTotal()));
    }

    private void hitungTotal() {
        try {
            double volume = Double.parseDouble(volumeField.getText().replaceAll(",", ""));
            double harga = Double.parseDouble(hargaSatuanField.getText().replaceAll(",", ""));
            totalField.setText(rp.format(volume * harga));
        } catch (Exception e) {
            totalField.setText("0");
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
