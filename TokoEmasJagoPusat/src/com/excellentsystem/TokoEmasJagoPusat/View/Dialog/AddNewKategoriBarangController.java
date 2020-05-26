/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddNewKategoriBarangController {

    @FXML public TextField kodeKategoriField;
    @FXML public TextField namaKategoriField;
    @FXML public TextField beratPersenField;
    @FXML public TextField hargaPersenJualField;
    @FXML public TextField kadarField;
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(beratPersenField, gr);
        Function.setNumberField(hargaPersenJualField, gr);
        kodeKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                namaKategoriField.requestFocus();
        });
        namaKategoriField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                beratPersenField.requestFocus();
        });
        beratPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                hargaPersenJualField.requestFocus();
        });
        hargaPersenJualField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                kadarField.requestFocus();
        });
        kadarField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setKategoriBarang(Kategori k ){
        kodeKategoriField.setDisable(true);
        kodeKategoriField.setText(k.getKodeKategori());
        namaKategoriField.setText(k.getNamaKategori());
        beratPersenField.setText(rp.format(k.getPersentaseEmas()));
        hargaPersenJualField.setText(rp.format(k.getHargaPersenJual()));
        kadarField.setText(k.getKadar());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
