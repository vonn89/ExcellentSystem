/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class LeburRosokCabangController {
    
    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public TextField beratField;
    @FXML public TextField beratPersenField;
    @FXML public TextField nilaiPokokField;
    
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;    

    public void initialize() {
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        getCabang();
    }
    private void getCabang(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return CabangDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setCabang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<StokRosokCabang> task = new Task<StokRosokCabang>() {
                @Override 
                public StokRosokCabang call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return StokRosokCabangDAO.get(conPusat, sistem.getTglSystem(), 
                                kodeCabangCombo.getSelectionModel().getSelectedItem(), kodeCabangCombo.getSelectionModel().getSelectedItem()+"-Rosok");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((ec) -> {
                mainApp.closeLoading();
                StokRosokCabang s = task.getValue();
                if(s!=null){
                    beratField.setText(gr.format(s.getBeratAkhir()));
                    beratPersenField.setText(gr.format(s.getBeratPersenAkhir()));
                    nilaiPokokField.setText(rp.format(s.getNilaiAkhir()));
                }else{
                    kodeCabangCombo.getSelectionModel().clearSelection();
                    beratField.setText("0");
                    beratPersenField.setText("0");
                    nilaiPokokField.setText("0");
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
