/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class TambahBarangPusatController  {

    
    @FXML private Label title;
    @FXML public ComboBox<String> kodeSubKategoriCombo;
    @FXML public TextField beratField;
    @FXML public TextField hargaPersenField;
    @FXML public TextField totalHargaPersenField;
    @FXML public TextField hargaEmasField;
    @FXML public TextField nilaiPokokField;
    
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    private ObservableList<String> listKodeSubKategori = FXCollections.observableArrayList();
    public void initialize() {
        kodeSubKategoriCombo.setItems(listKodeSubKategori);
        beratField.setOnKeyReleased((event) -> {
            try{
                String string = beratField.getText();
                if(string.contains("-"))
                    beratField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            beratField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratField.end();
            }catch(Exception e){
                beratField.undo();
            }
            hitungTotal();
        });
        hargaPersenField.setOnKeyReleased((event) -> {
            try{
                String string = hargaPersenField.getText();
                if(string.contains("-"))
                    hargaPersenField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            hargaPersenField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            hargaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        hargaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                hargaPersenField.end();
            }catch(Exception e){
                hargaPersenField.undo();
            }
            hitungTotal();
        });
        kodeSubKategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                setSubKategori();
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                hargaPersenField.requestFocus();
                hargaPersenField.selectAll();
            }
        });
        hargaPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                saveButton.requestFocus();
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        hargaEmasField.setText(rp.format(sistem.getHargaEmas()));
        getSubKategori();
    }
    private void getSubKategori(){
        Task<List<SubKategori>> task = new Task<List<SubKategori>>() {
            @Override 
            public List<SubKategori> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return SubKategoriDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            mainApp.closeLoading();
            List<SubKategori> listSubKategori = task.getValue();
            for(SubKategori j : listSubKategori){
                listKodeSubKategori.add(j.getKodeSubKategori());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setData(String string, StokBarang s){
        this.title.setText(string);
        if(s!=null){
            kodeSubKategoriCombo.getSelectionModel().select(s.getKodeSubKategori());
            setSubKategori();
        }
    }
    @FXML 
    private void setSubKategori(){
        boolean status = false;
        for(String j : listKodeSubKategori){
            if(j.equalsIgnoreCase(kodeSubKategoriCombo.getEditor().getText().toUpperCase())){
                kodeSubKategoriCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            beratField.requestFocus();
            beratField.selectAll();
        }else{
            kodeSubKategoriCombo.getEditor().selectAll();
        }
    }
    private void hitungTotal(){
        double berat = Double.parseDouble(beratField.getText().replaceAll(",", ""));
        double hargaPersen = Double.parseDouble(hargaPersenField.getText().replaceAll(",", ""));
        double hargaEmas = Double.parseDouble(hargaEmasField.getText().replaceAll(",", ""));
        totalHargaPersenField.setText(gr.format(berat * hargaPersen / 100));
        nilaiPokokField.setText(rp.format(berat * hargaPersen / 100 * hargaEmas));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
