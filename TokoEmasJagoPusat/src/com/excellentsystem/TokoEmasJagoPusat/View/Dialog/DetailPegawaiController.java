/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import com.excellentsystem.TokoEmasJagoPusat.Model.Pegawai;
import java.time.LocalDate;
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
 * @author excellent
 */
public class DetailPegawaiController {

    @FXML public TextField kodePegawaiField;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    @FXML public ComboBox<String> jenisKelaminCombo;
    @FXML public ComboBox<String> jabatanCombo;
    @FXML public DatePicker tglMasukPicker;
    @FXML public Button saveButton;
    private ObservableList<String> allJenisKelamin = FXCollections.observableArrayList();
    private ObservableList<String> allJabatan = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tglMasukPicker.setConverter(Function.getTglConverter());
        tglMasukPicker.setValue(LocalDate.now());
        tglMasukPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(LocalDate.now()));
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        allJenisKelamin.add("Laki-laki");
        allJenisKelamin.add("Perempuan");
        jenisKelaminCombo.setItems(allJenisKelamin);
        
        allJabatan.add("Owner");
        allJabatan.add("Manager");
        allJabatan.add("Admin");
        allJabatan.add("Sales");
        allJabatan.add("Lain-lain");
        jabatanCombo.setItems(allJabatan);
    }
    public void setPegawai(Pegawai p){
        kodePegawaiField.setDisable(true);
        kodePegawaiField.setText(p.getKodePegawai());
        namaField.setText(p.getNama());
        alamatField.setText(p.getAlamat());
        noTelpField.setText(p.getNoTelp());
        jenisKelaminCombo.getSelectionModel().select(p.getJenisKelamin());
        jabatanCombo.getSelectionModel().select(p.getJabatan());
        tglMasukPicker.setValue(LocalDate.parse(p.getTanggalMasuk()));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
