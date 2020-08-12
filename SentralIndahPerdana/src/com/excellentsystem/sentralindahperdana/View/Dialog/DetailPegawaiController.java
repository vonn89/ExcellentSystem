/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Main;
import com.excellentsystem.sentralindahperdana.Model.Pegawai;
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
public class DetailPegawaiController  {
    @FXML public TextField kodePegawaiField;
    @FXML public TextField namaField;
    @FXML public ComboBox<String> jabatanCombo;
    @FXML public TextArea alamatField;
    @FXML public TextField kotaField;
    @FXML public TextField identitasField;
    @FXML public TextField noIdentitasField;
    @FXML public TextField emailField;
    @FXML public TextField noTelpField;
    @FXML public TextField noHandphoneField;
    @FXML public Button saveButton;
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        ObservableList<String> allJabatan = FXCollections.observableArrayList();
        allJabatan.clear();
        allJabatan.add("Administasi");
        allJabatan.add("Pengawas");
        allJabatan.add("Marketing");
        allJabatan.add("Manager");
        jabatanCombo.setItems(allJabatan);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setPegawaiDetail(Pegawai pegawai){
        kodePegawaiField.setText("");
        namaField.setText("");
        jabatanCombo.getSelectionModel().select("");
        alamatField.setText("");
        kotaField.setText("");
        identitasField.setText("");
        noIdentitasField.setText("");
        emailField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        if (pegawai != null) {
            kodePegawaiField.setText(pegawai.getKodePegawai());
            namaField.setText(pegawai.getNama());
            jabatanCombo.getSelectionModel().select(pegawai.getJabatan());
            alamatField.setText(pegawai.getAlamat());
            kotaField.setText(pegawai.getKota());
            identitasField.setText(pegawai.getIdentitas());
            noIdentitasField.setText(pegawai.getNoIdentitas());
            emailField.setText(pegawai.getEmail());
            noTelpField.setText(pegawai.getNoTelp());
            noHandphoneField.setText(pegawai.getNoHandphone());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
