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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class PenjualanCiokCabangController {

    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public TextField beratField;
    @FXML public TextField nilaiPokokField;
    @FXML public TextField hargaEmasField;
    @FXML public TextField totalPenjualanField;
    
    @FXML public CheckBox bayarHutangCheck;
    @FXML public Button saveButton;
    public double nilaiStok;
    public double beratStok;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
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
            hitung();
        });
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = main;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            hargaEmasField.setText(rp.format(sistem.getHargaEmas()));
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            allCabang.clear();
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.addAll(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            kodeCabangCombo.getSelectionModel().clearSelection();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getstokCiok(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<StokRosokCabang> task = new Task<StokRosokCabang>() {
                @Override 
                public StokRosokCabang call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return StokRosokCabangDAO.get(conPusat, sistem.getTglSystem(), 
                                kodeCabangCombo.getSelectionModel().getSelectedItem(), kodeCabangCombo.getSelectionModel().getSelectedItem()+"-Ciok");
                    }
                }
            };
            task.setOnRunning((eb) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((eb) -> {
                mainApp.closeLoading();
                StokRosokCabang s = task.getValue();
                if(s==null){
                    beratStok = 0;
                    nilaiStok = 0;

                    beratField.setText(gr.format(0));
                    nilaiPokokField.setText(rp.format(0));
                    nilaiPokokField.setText(rp.format(0));
                    totalPenjualanField.setText(rp.format(0));
                }else{
                    beratStok = s.getBeratAkhir();
                    nilaiStok = s.getNilaiAkhir();

                    beratField.setText(gr.format(s.getBeratAkhir()));
                    nilaiPokokField.setText(rp.format(s.getNilaiAkhir()));
                    hitung();
                }
                saveButton.requestFocus();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void hitung(){
        double berat = Double.parseDouble(beratField.getText().replaceAll(",", ""));
        double hargaEmas = Double.parseDouble(hargaEmasField.getText().replaceAll(",", ""));
        nilaiPokokField.setText(rp.format(berat / beratStok * nilaiStok));
        totalPenjualanField.setText(rp.format(berat * hargaEmas));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
