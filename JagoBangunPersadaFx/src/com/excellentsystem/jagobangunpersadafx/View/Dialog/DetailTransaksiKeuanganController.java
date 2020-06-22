/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class DetailTransaksiKeuanganController  {
    
    @FXML public TextField keteranganField;
    @FXML public TextField jumlahRpField;
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public TextField totalPropertyField;
    
    @FXML public Button saveButton;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    private String metode;
    public ObservableList<Keuangan> allDetail = FXCollections.observableArrayList();
    public void initialize(){
        Function.setNumberField(jumlahRpField);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        try{
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            kategoriCombo.setItems(Function.getKategoriTransaksi());
            tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
        }catch(Exception ex){
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }  
    @FXML
    private void setDetailProperty(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTransaksiKeuanganProperty.fxml");
        DetailTransaksiKeuanganPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setProperty(metode, allDetail, Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")));
        if(!saveButton.getText().equals("Save"))
            x.setViewOnly();
        x.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            metode = x.metodeCombo.getSelectionModel().getSelectedItem();
            allDetail = x.allDetail;
            double total = 0;
            for(Keuangan d : allDetail){
                if(d.isStatus())
                    total = total + 1;
            }
            totalPropertyField.setText(rp.format(total));
        });
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }    
}
