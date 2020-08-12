/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Main;
import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewAsetTetapController {
    @FXML private AnchorPane anchorPane;
    @FXML private AnchorPane penyusutanPane;
    @FXML private Label title;
    @FXML private Label harga;
    @FXML public TextField namaField;
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public TextField keteranganField;
    @FXML public TextField bulanField;
    @FXML public TextField tahunField;
    @FXML public TextField HargaField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    public void initialize(){
        Function.setNumberField(HargaField);
        Function.setNumberField(bulanField);
        Function.setNumberField(tahunField);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            ObservableList<String> allKategori = FXCollections.observableArrayList();
            allKategori.add("Tanah");
            allKategori.add("Bangunan");
            allKategori.add("Kendaraan/Mesin");
            allKategori.add("Peralatan/Perlengkapan");
            allKategori.add("Lain-lain");
            kategoriCombo.setItems(allKategori);
            tipeKeuanganCombo.setItems(Function.getTipeKeuanganByUser());
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    public void setPenjualanAset(AsetTetap aset){
        title.setText("Penjualan Aset");
        harga.setText("Harga Jual");
        namaField.setText(aset.getNama());
        kategoriCombo.getSelectionModel().select(aset.getKategori());
        keteranganField.setText(aset.getKeterangan());
        namaField.setDisable(true);
        keteranganField.setDisable(true);
        kategoriCombo.setDisable(true);
        anchorPane.setPrefHeight(280);
        stage.setHeight(280);
        penyusutanPane.setVisible(false);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }  
}
