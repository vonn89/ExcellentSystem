/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.KategoriHutang;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
public class NewHutangController  {
    
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public TextField keteranganField;
    @FXML public TextField jumlahRpField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    @FXML public DatePicker jatuhTempoField;
    private Main mainApp; 
    private Stage stage;
    private Stage owner;
    public void initialize(){
        Function.setNumberField(jumlahRpField);
        jatuhTempoField.setConverter(Function.getTglConverter());
        jatuhTempoField.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for(KategoriKeuangan kk : sistem.getListKategoriKeuangan()){
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        
        ObservableList<String> allKategori = FXCollections.observableArrayList();
        List<KategoriHutang> listKategoriHutang = sistem.getListKategoriHutang();
        for(KategoriHutang k : listKategoriHutang){
            allKategori.add(k.getKodeKategori());
        }
        kategoriCombo.setItems(allKategori);
    }   
    public void close(){
        mainApp.closeDialog(owner, stage);
    }  
}
