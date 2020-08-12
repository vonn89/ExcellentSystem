/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriPiutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriPiutang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class NewPiutangController  {

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
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        Task<List<KategoriPiutang>> task = new Task<List<KategoriPiutang>>() {
            @Override 
            public List<KategoriPiutang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    return KategoriPiutangDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                ObservableList<String> allKategori = FXCollections.observableArrayList();
                mainApp.closeLoading();
                for(KategoriPiutang k : task.getValue()){
                    allKategori.add(k.getKodeKategori());
                }
                kategoriCombo.setItems(allKategori);
                tipeKeuanganCombo.setItems(Function.getTipeKeuanganByUser());
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }   
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }  
}
