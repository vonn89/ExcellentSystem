/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Main;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddNewSubKategoriBarangController {

    @FXML public TextField kodeSubKategoriField;
    @FXML public TextField namaSubKategoriField;
    @FXML public ComboBox<String> kategoriBarangCombo;
    @FXML public Button SaveButton;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeSubKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                namaSubKategoriField.requestFocus();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        kategoriBarangCombo.setItems(allKategori);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setKategori(List<Kategori> listKategori){
        allKategori.clear();
        for(Kategori k : listKategori){
            allKategori.add(k.getKodeKategori());
        }
    }
    public void setSubKategoriBarang(SubKategori s){
        kodeSubKategoriField.setDisable(true);
        kategoriBarangCombo.setDisable(true);
        kodeSubKategoriField.setText(s.getKodeSubKategori());
        namaSubKategoriField.setText(s.getNamaSubKategori());
        kategoriBarangCombo.getSelectionModel().select(s.getKodeKategori());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
