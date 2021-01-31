/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
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
public class NewPenerimaanBahanController {

    @FXML
    public ComboBox<String> gudangCombo;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField kodeBahanField;
    @FXML
    public TextField beratKotorField;
    @FXML
    public TextField beratBersihField;
    @FXML
    public TextField panjangField;
    @FXML
    public TextField beratTimbanganField;
    @FXML
    public TextField keteranganField;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        Function.setNumberField(beratKotorField);
        Function.setNumberField(beratBersihField);
        Function.setNumberField(panjangField);
        Function.setNumberField(beratTimbanganField);
        
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });

        ObservableList<String> allGudang = FXCollections.observableArrayList();
        List<Gudang> listGudang = sistem.getListGudang();
        for (Gudang k : listGudang) {
            allGudang.add(k.getKodeGudang());
        }
        gudangCombo.setItems(allGudang);
        
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        List<KategoriBahan> listKategoriBahan = sistem.getListKategoriBahan();
        for (KategoriBahan k : listKategoriBahan) {
            allKategori.add(k.getKodeKategori());
        }
        kategoriCombo.setItems(allKategori);
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
