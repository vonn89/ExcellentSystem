/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailKontraktorController {

    @FXML
    public TextField namaField;
    @FXML
    public TextArea alamatField;
    @FXML
    public TextField kotaField;
    @FXML
    public TextField kontakPersonField;
    @FXML
    public TextField noTelpField;
    @FXML
    public TextField noHandphoneField;
    @FXML
    public TextField emailField;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setViewOnly() {
        saveButton.setVisible(false);
        namaField.setDisable(true);
        alamatField.setDisable(true);
        noTelpField.setDisable(true);
        noHandphoneField.setDisable(true);
        emailField.setDisable(true);
        kotaField.setDisable(true);
        kontakPersonField.setDisable(true);
        stage.setHeight(stage.getHeight() - 30);
    }

    public void setKontraktor(Kontraktor k) {
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        emailField.setText("");
        kotaField.setText("");
        kontakPersonField.setText("");
        if (k.getNamaKontraktor() != null) {
            namaField.setText(k.getNamaKontraktor());
            alamatField.setText(k.getAlamat());
            noTelpField.setText(k.getNoTelp());
            noHandphoneField.setText(k.getNoHandphone());
            emailField.setText(k.getEmail());
            kotaField.setText(k.getKota());
            kontakPersonField.setText(k.getKontakPerson());
            if (!"Manager".equals(Main.sistem.getUser().getLevel())) {
                namaField.setDisable(true);
            }
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
