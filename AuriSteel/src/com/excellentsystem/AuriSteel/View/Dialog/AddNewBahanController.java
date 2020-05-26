/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.KategoriBahan;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddNewBahanController {

    @FXML public ComboBox<String> kodeKategoriCombo;
    @FXML public TextField kodeBahanField;
    @FXML public TextField noKontrakField;
    @FXML public TextField namaBahanField;
    @FXML public TextArea spesifikasiField;
    @FXML public TextField keteranganField;
    @FXML public TextField beratKotorField;
    @FXML public TextField beratBersihField;
    @FXML public TextField panjangField;
    @FXML public TextField hargaBeliField;
    @FXML public Button addButton;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    public void initialize(){
        hargaBeliField.setOnKeyReleased((event) -> {
            try{
                hargaBeliField.setText(df.format(Double.parseDouble(hargaBeliField.getText().replaceAll(",", ""))));
                hargaBeliField.end();
            }catch(Exception e){
                hargaBeliField.undo();
            }
        });
        beratKotorField.setOnKeyReleased((event) -> {
            try{
                String string = beratKotorField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        beratKotorField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        beratKotorField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    beratKotorField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                beratKotorField.end();
            }catch(Exception e){
                beratKotorField.undo();
            }
        });
        beratBersihField.setOnKeyReleased((event) -> {
            try{
                String string = beratBersihField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        beratBersihField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        beratBersihField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    beratBersihField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                beratBersihField.end();
            }catch(Exception e){
                beratBersihField.undo();
            }
        });
        panjangField.setOnKeyReleased((event) -> {
            try{
                String string = panjangField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        panjangField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        panjangField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    panjangField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                panjangField.end();
            }catch(Exception e){
                panjangField.undo();
            }
        });
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
        allKategori.clear();
        List<KategoriBahan> listKategori = sistem.getListKategoriBahan();
        for(KategoriBahan k : listKategori){
            allKategori.add(k.getKodeKategori());
        }
        kodeKategoriCombo.setItems(allKategori);
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    } 
    
}
