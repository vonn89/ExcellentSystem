/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class NewAddendumController  {

    @FXML
    public TextField noAddendumField;
    @FXML
    public TextField tglAddendumField;
    @FXML
    public TextField propertyField;
    @FXML
    public TextField customerField;
    @FXML
    public TextField hargaPropertyField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField totalBiayaField;
    @FXML
    public TextField perubahanHargaField;
    @FXML
    public Button saveButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        Function.setNumberField(perubahanHargaField);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setNewAddendum(String property, String customer, double hargaJual, String keterangan, double totalBiaya, double perubahanHarga){
        noAddendumField.setText("-");
        tglAddendumField.setText(tglLengkap.format(new Date()));
        propertyField.setText(property);
        customerField.setText(customer);
        hargaPropertyField.setText(rp.format(hargaJual));
        keteranganField.setText(keterangan);
        totalBiayaField.setText(rp.format(totalBiaya));
        perubahanHargaField.setText(rp.format(perubahanHarga));
    }


    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
