/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class TransferKeuanganController  {

    @FXML public ComboBox<String> keCombo;
    @FXML public TextField keteranganField;
    @FXML public TextField jumlahRpField;
    @FXML public ComboBox<String> dariCombo;
    @FXML public Button saveButton;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize(){
        Function.setNumberField(jumlahRpField);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            dariCombo.setItems(Function.getTipeKeuanganByUser());
            keCombo.setItems(Function.getTipeKeuanganByUser());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }      
    
}
