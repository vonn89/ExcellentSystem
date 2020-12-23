/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
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
public class DetailCustomerController {

    public TextField namaField;
    public ComboBox<String> jenisKelaminCombo;
    public TextArea alamatField;
    public TextField noTelpField;
    public TextField noHandphoneField;
    public TextField emailField;
    public ComboBox<String> statusNikahCombo;
    public TextField agamaField;
    public TextField pekerjaanField;
    public TextField noKTPField;
    public TextField noNPWPField;
    public TextField noSPTPPHField;
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        ObservableList<String> alljenis = FXCollections.observableArrayList();
        ObservableList<String> allstatus = FXCollections.observableArrayList();
        alljenis.add("Laki-laki");
        alljenis.add("Perempuan");
        jenisKelaminCombo.setItems(alljenis);
        allstatus.add("Belum Menikah");
        allstatus.add("Sudah Menikah");
        allstatus.add("Cerai");
        statusNikahCombo.setItems(allstatus);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setViewOnly() {
        saveButton.setVisible(false);
        namaField.setDisable(true);
        jenisKelaminCombo.setDisable(true);
        alamatField.setDisable(true);
        noTelpField.setDisable(true);
        noHandphoneField.setDisable(true);
        emailField.setDisable(true);
        statusNikahCombo.setDisable(true);
        agamaField.setDisable(true);
        pekerjaanField.setDisable(true);
        noKTPField.setDisable(true);
        noNPWPField.setDisable(true);
        noSPTPPHField.setDisable(true);
        stage.setHeight(stage.getHeight() - 30);
    }

    public void setCustomer(Customer cust) {
        namaField.setText("");
        jenisKelaminCombo.getSelectionModel().select("");
        alamatField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        statusNikahCombo.getSelectionModel().select("");
        emailField.setText("");
        agamaField.setText("");
        pekerjaanField.setText("");
        noKTPField.setText("");
        noNPWPField.setText("");
        noSPTPPHField.setText("");
        if (cust.getNama() != null) {
            namaField.setText(cust.getNama());
            jenisKelaminCombo.getSelectionModel().select(cust.getJenisKelamin());
            alamatField.setText(cust.getAlamat());
            noTelpField.setText(cust.getNoTelp());
            noHandphoneField.setText(cust.getNoHandphone());
            statusNikahCombo.getSelectionModel().select(cust.getStatusNikah());
            emailField.setText(cust.getEmail());
            agamaField.setText(cust.getAgama());
            pekerjaanField.setText(cust.getPekerjaan());
            noKTPField.setText(cust.getNoKTP());
            noNPWPField.setText(cust.getNoNPWP());
            noSPTPPHField.setText(cust.getNoSPTPPH());
            if (!"Manager".equals(Main.sistem.getUser().getLevel())) {
                namaField.setDisable(true);
            }
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
