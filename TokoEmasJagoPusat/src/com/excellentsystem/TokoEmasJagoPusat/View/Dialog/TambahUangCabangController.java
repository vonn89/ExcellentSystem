/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TambahUangCabang;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class TambahUangCabangController  {

    @FXML private TextField noTambahUangField;
    @FXML private TextField tglTambahField;
    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public ComboBox<String> tipeKasirCombo;
    @FXML public TextField jumlahRpField;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(jumlahRpField, rp);
        tipeKasirCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                jumlahRpField.requestFocus();
                jumlahRpField.selectAll();
            }
        });
        jumlahRpField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            allCabang.clear();
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.addAll(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            
            ObservableList<String> allTipeKasir = FXCollections.observableArrayList();
            allTipeKasir.add("Kasir");
            allTipeKasir.add("RR");
            tipeKasirCombo.setItems(allTipeKasir);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void newTambahUang(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return TambahUangCabangDAO.getId(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            noTambahUangField.setText(task.getValue());
            tglTambahField.setText(Function.getSystemDate());
            
            saveButton.setVisible(true);
            cancelButton.setVisible(true);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML private GridPane gridPane;
    public void detailTambahUang(TambahUangCabang t){
        noTambahUangField.setText(t.getNoTambahUang());
        tglTambahField.setText(t.getTglTambah());
        kodeCabangCombo.getSelectionModel().select(t.getKodeCabang());
        tipeKasirCombo.getSelectionModel().select(t.getTipeKasir());
        jumlahRpField.setText(rp.format(t.getJumlahRp()));

        gridPane.getRowConstraints().get(7).setMinHeight(0);
        gridPane.getRowConstraints().get(7).setPrefHeight(0);
        gridPane.getRowConstraints().get(7).setMaxHeight(0);
        
        kodeCabangCombo.setDisable(true);
        tipeKasirCombo.setDisable(true);
        jumlahRpField.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }    
    
}
