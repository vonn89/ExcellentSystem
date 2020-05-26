/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Main;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import java.util.ArrayList;
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
public class AddNewJenisBarangController {

    @FXML public TextField kodeJenisField;
    @FXML public TextField namaJenisField;
    @FXML public ComboBox<String> kategoriBarangCombo;
    @FXML public ComboBox<String> subKategoriBarangCombo;
    @FXML public Button SaveButton;
    
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allSubKategori = FXCollections.observableArrayList();
    private List<SubKategori> listSubKategori = new ArrayList<>();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeJenisField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                namaJenisField.requestFocus();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        kategoriBarangCombo.setItems(allKategori);
        subKategoriBarangCombo.setItems(allSubKategori);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setKategori(List<Kategori> x, List<SubKategori> y){
        allKategori.clear();
        for(Kategori k : x){
            allKategori.add(k.getKodeKategori());
        }
        listSubKategori.clear();
        listSubKategori.addAll(y);
    }
    @FXML
    private void setSubKategori(){
        allSubKategori.clear();
        for(SubKategori s : listSubKategori){
            if(s.getKodeKategori().equals(kategoriBarangCombo.getSelectionModel().getSelectedItem()))
                allSubKategori.add(s.getKodeSubKategori());
        }
    }
    public void setJenis(Jenis j){
        kodeJenisField.setDisable(true);
        kategoriBarangCombo.setDisable(true);
        kodeJenisField.setText(j.getKodeJenis());
        namaJenisField.setText(j.getNamaJenis());
        kategoriBarangCombo.getSelectionModel().select(j.getKodeKategori());
        subKategoriBarangCombo.getSelectionModel().select(j.getKodeSubKategori());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
