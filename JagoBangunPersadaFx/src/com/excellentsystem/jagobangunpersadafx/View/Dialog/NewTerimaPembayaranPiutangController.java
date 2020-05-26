/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
import java.time.LocalDate;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewTerimaPembayaranPiutangController  {

    public TextField noPiutangField;
    public TextField totalPiutangField;
    public TextField terbayarField;
    public TextField sisaPiutangField;
    public TextField jumlahPembayaranField;
    public ComboBox<String> tipeKeuanganCombo;
    public Button saveButton;
    public DatePicker jatuhTempoField;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    public void initialize(){
        Function.setNumberField(jumlahPembayaranField);
        jatuhTempoField.setConverter(Function.getTglConverter());
        jatuhTempoField.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
    }   
    public void setNewTerimaPembayaran(Piutang piutang){
        noPiutangField.setText(piutang.getNoPiutang());
        totalPiutangField.setText(rp.format(piutang.getJumlahPiutang()));
        terbayarField.setText(rp.format(piutang.getPembayaran()));
        sisaPiutangField.setText(rp.format(piutang.getSisaPiutang()));
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }    
    
}
