/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPropertyController {

    @FXML
    public TextField kodePropertyField;
    @FXML
    public ComboBox<String> kodeKategoriCombo;
    @FXML
    public TextField namaPropertyField;
    @FXML
    public TextField tipeField;
    @FXML
    public TextArea alamatField;
    @FXML
    public TextArea spesifikasiField;
    @FXML
    public TextField luasTanahField;
    @FXML
    public TextField luasBangunanField;
    @FXML
    public TextField nilaiPropertyField;
    @FXML
    public TextField nilaiPropertyPerMeterField;
    @FXML
    public TextField hargaJualField;
    @FXML
    public TextField hargaJualPerMeterField;
    @FXML
    public TextField diskonField;
    @FXML
    public TextField addendumField;
    @FXML
    public TextArea keteranganField;
    @FXML
    public TextField statusField;
    @FXML
    public Button saveButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        luasTanahField.setOnKeyReleased((event) -> {
            try {
                String string = luasTanahField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        luasTanahField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        luasTanahField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    luasTanahField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                luasTanahField.end();
            } catch (Exception e) {
                luasTanahField.undo();
            }
            hitungNilaiPropertyPerMeter();
            hitungHargaJualPerMeter();
        });
        luasBangunanField.setOnKeyReleased((event) -> {
            try {
                String string = luasBangunanField.getText();
                if (string.indexOf(".") > 0) {
                    String string2 = string.substring(string.indexOf(".") + 1, string.length());
                    if (string2.contains(".")) {
                        luasBangunanField.undo();
                    } else if (!string2.equals("") && Double.parseDouble(string2) != 0) {
                        luasBangunanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }
                } else {
                    luasBangunanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                luasBangunanField.end();
            } catch (Exception e) {
                luasBangunanField.undo();
            }
        });
        nilaiPropertyField.setOnKeyReleased((event) -> {
            try {
                nilaiPropertyField.setText(rp.format(Double.parseDouble(nilaiPropertyField.getText().replaceAll(",", ""))));
                nilaiPropertyField.end();
            } catch (Exception e) {
                nilaiPropertyField.undo();
            }
            hitungNilaiPropertyPerMeter();
        });
        hargaJualField.setOnKeyReleased((event) -> {
            try {
                hargaJualField.setText(rp.format(Double.parseDouble(hargaJualField.getText().replaceAll(",", ""))));
                hargaJualField.end();
            } catch (Exception e) {
                hargaJualField.undo();
            }
            hitungHargaJualPerMeter();
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try {
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            kodeKategoriCombo.setItems(Function.getKategoriProperty());
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void setProperty(Property p) {
        kodePropertyField.setText(p.getKodeProperty());
        kodeKategoriCombo.getSelectionModel().select(p.getKodeKategori());
        namaPropertyField.setText(p.getNamaProperty());
        tipeField.setText(p.getTipe());
        alamatField.setText(p.getAlamat());
        spesifikasiField.setText(p.getSpesifikasi());
        luasTanahField.setText(qty.format(p.getLuasTanah()));
        luasBangunanField.setText(qty.format(p.getLuasBangunan()));
        if (!"Manager".equals(sistem.getUser().getLevel())) {
            nilaiPropertyField.setText("-");
            nilaiPropertyPerMeterField.setText("-");
        } else {
            nilaiPropertyField.setText(rp.format(p.getNilaiProperty()));
            nilaiPropertyPerMeterField.setText(rp.format(p.getNilaiProperty() / p.getLuasTanah()));
        }
        hargaJualField.setText(rp.format(p.getHargaJual()));
        hargaJualPerMeterField.setText(rp.format(p.getHargaJual() / p.getLuasTanah()));
        diskonField.setText(rp.format(p.getDiskon()));
        addendumField.setText(rp.format(p.getAddendum()));
        keteranganField.setText(p.getKeterangan());
        statusField.setText(p.getStatus());
        boolean status = true;
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Update Property") && o.isStatus()) {
                status = false;
            }
        }
        if (p.getStatus().equals("Sold") || status) {
            viewOnly();
        }
    }

    public void viewOnly() {
        kodeKategoriCombo.setDisable(true);
        namaPropertyField.setDisable(true);
        alamatField.setDisable(true);
        tipeField.setDisable(true);
        spesifikasiField.setDisable(true);
        luasTanahField.setDisable(true);
        luasBangunanField.setDisable(true);
        hargaJualField.setDisable(true);
        diskonField.setDisable(true);
        keteranganField.setDisable(true);
        saveButton.setVisible(false);
        stage.setHeight(stage.getHeight() - 20);
    }

    public void setPemecahanProperty(PemecahanPropertyDetail d) {
        luasTanahField.setDisable(false);
        luasBangunanField.setDisable(true);
        nilaiPropertyField.setDisable(true);
        diskonField.setDisable(true);
        addendumField.setDisable(true);
        kodePropertyField.setText("");
        kodeKategoriCombo.getSelectionModel().clearSelection();
        namaPropertyField.setText("");
        alamatField.setText("");
        spesifikasiField.setText("");
        luasTanahField.setText("0");
        hargaJualField.setText("0");
        diskonField.setText("0");
        addendumField.setText("0");
        keteranganField.setText("");
        if (d != null) {
            kodeKategoriCombo.getSelectionModel().select(d.getKodeKategori());
            namaPropertyField.setText(d.getNamaProperty());
            alamatField.setText(d.getProperty().getAlamat());
            tipeField.setText(d.getProperty().getTipe());
            spesifikasiField.setText(d.getProperty().getSpesifikasi());
            luasTanahField.setText(qty.format(d.getLuasTanah()));
            hargaJualField.setText(rp.format(d.getProperty().getHargaJual()));
            diskonField.setText(rp.format(d.getProperty().getDiskon()));
            addendumField.setText(rp.format(d.getProperty().getAddendum()));
            keteranganField.setText(d.getProperty().getKeterangan());
        }
    }

    public void setPenggabunganProperty(List<PenggabunganPropertyDetail> allDetail) {
        luasTanahField.setDisable(true);
        nilaiPropertyField.setDisable(true);
        luasBangunanField.setDisable(true);
        diskonField.setDisable(true);
        addendumField.setDisable(true);
        kodePropertyField.setText("");
        kodeKategoriCombo.getSelectionModel().clearSelection();
        namaPropertyField.setText("");
        alamatField.setText("");
        spesifikasiField.setText("");
        luasTanahField.setText("0");
        hargaJualField.setText("0");
        diskonField.setText("0");
        addendumField.setText("0");
        keteranganField.setText("");
        if (allDetail != null) {
            double totalLuasTanah = 0;
            double totalNilaiProperty = 0;
            double totalJual = 0;
            for (PenggabunganPropertyDetail d : allDetail) {
                totalLuasTanah = totalLuasTanah + d.getProperty().getLuasTanah();
                totalNilaiProperty = totalNilaiProperty + d.getProperty().getNilaiProperty();
                totalJual = totalJual + d.getProperty().getHargaJual();
            }
            luasTanahField.setText(rp.format(totalLuasTanah));
            nilaiPropertyField.setText(rp.format(totalNilaiProperty));
            nilaiPropertyPerMeterField.setText(rp.format(totalNilaiProperty / totalLuasTanah));
            hargaJualField.setText(rp.format(totalJual));
            hargaJualPerMeterField.setText(rp.format(totalJual / totalLuasTanah));
            diskonField.setText(rp.format(0));
            addendumField.setText(rp.format(0));
        }
    }

    @FXML
    private void hitungNilaiPropertyPerMeter() {
        if (nilaiPropertyField.getText().equals("")) {
            nilaiPropertyField.setText("0");
        }
        if (luasTanahField.getText().equals("")) {
            luasTanahField.setText("0");
        }
        if (!"0".equals(luasTanahField.getText())) {
            nilaiPropertyPerMeterField.setText(rp.format(
                    Double.parseDouble(nilaiPropertyField.getText().replaceAll(",", ""))
                    / Double.parseDouble(luasTanahField.getText().replaceAll(",", ""))));
        } else {
            nilaiPropertyPerMeterField.setText("0");
        }
    }

    @FXML
    private void hitungHargaJualPerMeter() {
        if (hargaJualField.getText().equals("")) {
            hargaJualField.setText("0");
        }
        if (luasTanahField.getText().equals("")) {
            luasTanahField.setText("0");
        }
        if (!"0".equals(luasTanahField.getText())) {
            hargaJualPerMeterField.setText(rp.format(Double.parseDouble(hargaJualField.getText().replaceAll(",", ""))
                    / Double.parseDouble(luasTanahField.getText().replaceAll(",", ""))));
        } else {
            hargaJualPerMeterField.setText("0");
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
