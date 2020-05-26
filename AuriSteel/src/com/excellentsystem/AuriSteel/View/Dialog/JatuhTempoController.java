/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class JatuhTempoController {

    @FXML public DatePicker jatuhTempoPicker;
    @FXML public Button saveButton;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        jatuhTempoPicker.setConverter(Function.getTglConverter());
        jatuhTempoPicker.setValue(LocalDate.now());
        jatuhTempoPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }    
    public void setMainApp(Main mainApp,Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void cancel(){
        mainApp.closeDialog(owner, stage);
    }
}
