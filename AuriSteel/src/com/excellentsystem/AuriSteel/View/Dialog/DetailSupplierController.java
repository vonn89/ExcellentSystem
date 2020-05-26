/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.Supplier;
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
public class DetailSupplierController  {
    @FXML public TextField kodeSupplierField;
    @FXML public TextField namaField;
    @FXML public TextArea alamatField;
    @FXML public TextField kotaField;
    @FXML public TextField negaraField;
    @FXML public TextField kodePosField;
    @FXML public TextField emailField;
    @FXML public TextField kontakPersonField;
    @FXML public TextField noTelpField;
    @FXML public TextField noHandphoneField;
    @FXML public TextField bankField;
    @FXML public TextField atasNamaRekeningField;
    @FXML public TextField noRekeningField;
    @FXML public Button saveButton;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setSupplierDetail(Supplier supplier){
        kodeSupplierField.setText("");
        namaField.setText("");
        alamatField.setText("");
        kotaField.setText("");
        negaraField.setText("");
        kodePosField.setText("");
        emailField.setText("");
        kontakPersonField.setText("");
        noTelpField.setText("");
        noHandphoneField.setText("");
        bankField.setText("");
        atasNamaRekeningField.setText("");
        noRekeningField.setText("");
        if (supplier != null) {
            kodeSupplierField.setText(supplier.getKodeSupplier());
            namaField.setText(supplier.getNama());
            alamatField.setText(supplier.getAlamat());
            kotaField.setText(supplier.getKota());
            negaraField.setText(supplier.getNegara());
            kodePosField.setText(supplier.getKodePos());
            emailField.setText(supplier.getEmail());
            kontakPersonField.setText(supplier.getKontakPerson());
            noTelpField.setText(supplier.getNoTelp());
            noHandphoneField.setText(supplier.getNoHandphone());
            bankField.setText(supplier.getBank());
            atasNamaRekeningField.setText(supplier.getAtasNamaRekening());
            noRekeningField.setText(supplier.getNoRekening());
        } 
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
