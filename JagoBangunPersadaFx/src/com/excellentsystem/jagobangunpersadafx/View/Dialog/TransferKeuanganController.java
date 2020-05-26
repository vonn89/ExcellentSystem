/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class TransferKeuanganController  {

    public ComboBox<String> keCombo;
    public TextField keteranganField;
    public TextField jumlahRpField;
    public ComboBox<String> dariCombo;
    public Button saveButton;
    public Main mainApp;   
    private Stage owner;
    private Stage stage;
    public void initialize(){
        Function.setNumberField(jumlahRpField);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        dariCombo.setItems(Function.getTipeKeuangan());
        keCombo.setItems(Function.getTipeKeuangan());
    }   
    public void closeDialog(){
        mainApp.closeDialog(owner, stage);
    }      
    
}
