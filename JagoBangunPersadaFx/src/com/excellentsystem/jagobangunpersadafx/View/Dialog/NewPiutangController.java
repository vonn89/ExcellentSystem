/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KategoriPiutangDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.KategoriPiutang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
 * @author Xtreme
 */
public class NewPiutangController {

    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public Button saveButton;
    @FXML
    public DatePicker jatuhTempoField;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        Function.setNumberField(jumlahRpField);
        jatuhTempoField.setConverter(Function.getTglConverter());
        jatuhTempoField.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try {
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            Task<List<KategoriPiutang>> task = new Task<List<KategoriPiutang>>() {
                @Override
                public List<KategoriPiutang> call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return KategoriPiutangDAO.getAll(con);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try {
                    mainApp.closeLoading();
                    for (KategoriPiutang k : task.getValue()) {
                        allKategori.add(k.getKodeKategori());
                    }
                    kategoriCombo.setItems(allKategori);
                    tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
                } catch (Exception ex) {
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
