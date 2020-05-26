/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class AmbilRosokCabangController  {

    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public TextField beratRosokField;
    @FXML public TextField beratPersenRosokField;
    
    @FXML public TextField keteranganField;
    @FXML public ComboBox<String> kodeGudangCombo;
    @FXML public ComboBox<String> kodeJenisCombo;
    @FXML public TextField qtyField;
    @FXML public TextField beratField;
    @FXML public TextField persentaseEmasField;
    @FXML public TextField beratPersenField;
    
    @FXML public Button saveButton;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public StokRosokCabang stokRosokCabang;
    private List<Cabang> listCabang;
    private ObservableList<String> listGudang = FXCollections.observableArrayList();
    private ObservableList<String> listKodeJenis = FXCollections.observableArrayList();
    public void initialize() {
        Function.setNumberField(qtyField, rp);
        Function.setNumberField(beratField, gr);
        Function.setNumberField(persentaseEmasField, gr);
        persentaseEmasField.setOnKeyReleased((event) -> {
            try{
                String string = persentaseEmasField.getText();
                if(string.contains("-"))
                    persentaseEmasField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            persentaseEmasField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        persentaseEmasField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                persentaseEmasField.end();
            }catch(Exception e){
                persentaseEmasField.undo();
            }
            beratPersenField.setText(gr.format(Double.parseDouble(beratField.getText().replaceAll(",", "")) * 
                    Double.parseDouble(persentaseEmasField.getText().replaceAll(",", "")) /100));
        });
        keteranganField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                kodeGudangCombo.requestFocus();
        });
        kodeGudangCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                kodeJenisCombo.requestFocus();
        });
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                setJenis();
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                beratField.requestFocus();
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                persentaseEmasField.requestFocus();
                persentaseEmasField.selectAll();
            }
        });
        persentaseEmasField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                saveButton.requestFocus();
            }
        });
        kodeJenisCombo.setItems(listKodeJenis);
        kodeGudangCombo.setItems(listGudang);
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        setData();
    }
    private void setData(){
        Task<List<Jenis>> task = new Task<List<Jenis>>() {
            @Override 
            public List<Jenis> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listCabang = CabangDAO.getAll(conPusat);
                    return JenisDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ec) -> {
            mainApp.closeLoading();
            List<Jenis> listJenis = task.getValue();
            for(Jenis j : listJenis){
                listKodeJenis.add(j.getKodeJenis());
            }
            
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            getRosokAndSetGudang();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void getRosokAndSetGudang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<StokRosokCabang> task = new Task<StokRosokCabang>() {
                @Override 
                public StokRosokCabang call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return StokRosokCabangDAO.get(conPusat, sistem.getTglSystem(), kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                                kodeCabangCombo.getSelectionModel().getSelectedItem()+"-Rosok");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((ec) -> {
                mainApp.closeLoading();
                StokRosokCabang s = task.getValue();
                if(s!=null)
                    setRosok(s);
                else{
                    s = new StokRosokCabang();
                    s.setTanggal(sistem.getTglSystem());
                    s.setKodeCabang(kodeCabangCombo.getSelectionModel().getSelectedItem());
                    s.setKodeGudang(kodeCabangCombo.getSelectionModel().getSelectedItem()+"-Rosok");
                    s.setBeratAwal(0);
                    s.setBeratPersenAwal(0);
                    s.setNilaiAwal(0);
                    s.setBeratMasuk(0);
                    s.setBeratPersenMasuk(0);
                    s.setNilaiMasuk(0);
                    s.setBeratKeluar(0);
                    s.setBeratPersenKeluar(0);
                    s.setNilaiKeluar(0);
                    s.setBeratAkhir(0);
                    s.setBeratPersenAkhir(0);
                    s.setNilaiAkhir(0);
                    setRosok(s);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    public void setRosok(StokRosokCabang s){
        stokRosokCabang = s;
        
        kodeCabangCombo.getSelectionModel().select(s.getKodeCabang());
        beratRosokField.setText(gr.format(s.getBeratAkhir()));
        beratPersenRosokField.setText(gr.format(s.getBeratPersenAkhir()));
            
        listGudang.clear();
        listGudang.add(kodeCabangCombo.getSelectionModel().getSelectedItem()+"-SP");
        listGudang.add("BLN-"+kodeCabangCombo.getSelectionModel().getSelectedItem());
        kodeGudangCombo.getSelectionModel().clearSelection();
        keteranganField.requestFocus();
    }
    public void setBarang(String kodeCabang, String kodeGudang, String kodeJenis){
        kodeCabangCombo.getSelectionModel().select(kodeCabang);
        getRosokAndSetGudang();
        kodeGudangCombo.getSelectionModel().select(kodeGudang);
        kodeJenisCombo.getSelectionModel().select(kodeJenis);
        setJenis();
    }
    @FXML 
    private void setJenis(){
        boolean status = false;
        for(String j : listKodeJenis){
            if(j.equalsIgnoreCase(kodeJenisCombo.getEditor().getText().toUpperCase())){
                kodeJenisCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            qtyField.requestFocus();
            qtyField.selectAll();
        }else{
            kodeJenisCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
