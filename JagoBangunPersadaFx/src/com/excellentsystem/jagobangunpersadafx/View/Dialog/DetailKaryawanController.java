/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class DetailKaryawanController {

    public TextField namaField;
    public ComboBox<String> jabatanCombo;
    public TextArea alamatField;
    public TextField noTelpField;
    public TextField noHandphoneField;
    public ComboBox<String> statusNikahCombo;
    public TextField agamaField;
    public ComboBox<String> identitasCombo;
    public TextField noIdentitasField;
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        ObservableList<String> allJabatan = FXCollections.observableArrayList();
        ObservableList<String> allstatus = FXCollections.observableArrayList();
        ObservableList<String> allIdentitas = FXCollections.observableArrayList();
        allJabatan.add("Manager");
        allJabatan.add("Marketing");
        allJabatan.add("Administrasi");
        allJabatan.add("Pelaksana & Pengawas");
        allJabatan.add("Lain-lain");
        jabatanCombo.setItems(allJabatan);
        allstatus.add("Belum Menikah");
        allstatus.add("Sudah Menikah");
        allstatus.add("Cerai");
        statusNikahCombo.setItems(allstatus);
        allIdentitas.add("KTP");
        allIdentitas.add("SIM");
        allIdentitas.add("Lainnya");
        identitasCombo.setItems(allIdentitas);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setViewOnly(){
        saveButton.setVisible(false);
        namaField.setDisable(true);
        alamatField.setDisable(true);
        noTelpField.setDisable(true);
        noHandphoneField.setDisable(true);
        statusNikahCombo.setDisable(true);
        agamaField.setDisable(true);
        jabatanCombo.setDisable(true);
        identitasCombo.setDisable(true);
        noIdentitasField.setDisable(true);
        stage.setHeight(stage.getHeight()-30);
    }
    public void setKaryawan(Karyawan value){
        namaField.setText("");
        jabatanCombo.getSelectionModel().select("");
        alamatField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        statusNikahCombo.getSelectionModel().select("");
        identitasCombo.getSelectionModel().select("");
        agamaField.setText("");
        noIdentitasField.setText("");
        if(value.getNama()!=null){
            namaField.setText(value.getNama());
            jabatanCombo.getSelectionModel().select(value.getJabatan());
            alamatField.setText(value.getAlamat());
            noTelpField.setText(value.getNoTelp());
            noHandphoneField.setText(value.getNoHandphone());
            statusNikahCombo.getSelectionModel().select(value.getStatusNikah());
            identitasCombo.getSelectionModel().select(value.getIdentitas());
            agamaField.setText(value.getAgama());
            noIdentitasField.setText(value.getNoIdentitas());
            if(!"Manager".equals(Main.sistem.getUser().getLevel()))
                namaField.setDisable(true);
        }
    }
    public void close(){
        mainApp.closeDialog(owner,stage);
    }
}
