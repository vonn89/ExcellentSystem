/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriPekerjaanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriPekerjaan;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPekerjaanController {

    @FXML public TextField kodePekerjaanField;
    @FXML public ComboBox<String> kodeKategoriCombo;
    @FXML public TextField namaPekerjaanField;
    @FXML public TextArea spesifikasiField;
    @FXML public TextField keteranganField;
    @FXML public TextField satuanField;
    @FXML public TextField hargaField;

    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    public void initialize() {
        Function.setNumberField(hargaField);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    List<KategoriPekerjaan> kategori = KategoriPekerjaanDAO.getAll(con);
                    allKategori.clear();
                    for (KategoriPekerjaan temp : kategori) {
                        allKategori.add(temp.getKodeKategori());
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            kodeKategoriCombo.setItems(allKategori);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPekerjaanDetail(Pekerjaan p) {
        kodePekerjaanField.setText("");
        kodeKategoriCombo.getSelectionModel().clearSelection();
        namaPekerjaanField.setText("");
        spesifikasiField.setText("");
        keteranganField.setText("");
        satuanField.setText("");
        hargaField.setText("0");
        if (p != null) {
            kodePekerjaanField.setText(p.getKodePekerjaan());
            kodeKategoriCombo.getSelectionModel().select(p.getKategoriPekerjaan());
            namaPekerjaanField.setText(p.getNamaPekerjaan());
            spesifikasiField.setText(p.getSpesifikasi());
            keteranganField.setText(p.getKeterangan());
            satuanField.setText(p.getSatuan());
            hargaField.setText(df.format(p.getLaba()));
        }
    }
    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

}
