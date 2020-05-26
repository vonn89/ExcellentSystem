/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Tukang;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailTukangController  {
    public TextField namaField;
    public TextArea alamatField;
    public TextField noTelpField;
    public TextField gajiPerHariField;
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        Function.setNumberField(gajiPerHariField);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setTukang(Tukang value){
        namaField.setText("");
        alamatField.setText("");
        noTelpField.setText("");
        gajiPerHariField.setText("0");
        if(value.getNama()!=null){
            namaField.setText(value.getNama());
            alamatField.setText(value.getAlamat());
            noTelpField.setText(value.getNoTelp());
            gajiPerHariField.setText(rp.format(value.getGajiPerHari()));
            if(!sistem.getUser().getLevel().equals("Manager"))
                namaField.setDisable(true);
        }
    }
    public void close(){
        mainApp.closeDialog(owner,stage);
    }
}
