/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Absensi;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AddAbsensiController  {
    
    @FXML public TextField tglAbsensiField;
    @FXML public TextField namaPegawaiField;
    
    @FXML public TextField jamMasukField;
    @FXML public TextField jamPulangField;
    @FXML public TextArea keteranganField;
    @FXML public Button saveButton;
    
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try{
            jamMasukField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        }catch(Exception e){
            jamMasukField.setDisable(true);
        }
        try{
            jamPulangField.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format), format.parse("00:00")));
        }catch(Exception e){
            jamPulangField.setDisable(true);
        }
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setAbsensi(Absensi a){
        try{
            if(a!=null){
                namaPegawaiField.setText(a.getNama());
                tglAbsensiField.setText(tgl.format(tglBarang.parse(a.getTanggal())));
                if(a.getAbsenMasuk()!=0)
                    jamMasukField.setText(new SimpleDateFormat("HH:mm").format(new Date(a.getAbsenMasuk())));
                if(a.getAbsenPulang()!=0)
                    jamPulangField.setText(new SimpleDateFormat("HH:mm").format(new Date(a.getAbsenPulang())));
                keteranganField.setText(a.getKeterangan());
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    } 
}
