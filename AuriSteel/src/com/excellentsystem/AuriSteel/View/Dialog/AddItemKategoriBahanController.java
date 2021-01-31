/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import java.text.DecimalFormat;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddItemKategoriBahanController {

    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField namaBarangField;
    @FXML
    public TextArea spesifikasiField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField qtyField;
    @FXML
    public TextField hargaField;
    @FXML
    public TextField totalField;
    @FXML
    public Button addButton;
    private ObservableList<String> kategori = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        hargaField.setOnKeyReleased((event) -> {
            try {
                String string = hargaField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        hargaField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        hargaField.setText(new DecimalFormat("###,##0.######").format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    hargaField.setText(new DecimalFormat("###,##0.######").format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                hargaField.end();
            } catch (Exception e) {
                hargaField.undo();
            }
            hitungHarga();
        });
        qtyField.setOnKeyReleased((event) -> {
            try {
                String string = qtyField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        qtyField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        qtyField.setText(new DecimalFormat("###,##0.###").format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    qtyField.setText(new DecimalFormat("###,##0.###").format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                qtyField.end();
            } catch (Exception e) {
                qtyField.undo();
            }
            hitungHarga();
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        kategoriCombo.setItems(kategori);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
        List<KategoriBahan> listKategori = sistem.getListKategoriBahan();
        kategori.clear();
        for (KategoriBahan k : listKategori) {
            kategori.add(k.getKodeKategori());
        }
        kategoriCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void hitungHarga() {
        if (qtyField.getText().equals("")) {
            qtyField.setText("0");
        }
        if (hargaField.getText().equals("")) {
            hargaField.setText("0");
        }
        double total = Double.parseDouble(hargaField.getText().replaceAll(",", ""))
                * Double.parseDouble(qtyField.getText().replaceAll(",", ""));
        totalField.setText(df.format(total));
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
