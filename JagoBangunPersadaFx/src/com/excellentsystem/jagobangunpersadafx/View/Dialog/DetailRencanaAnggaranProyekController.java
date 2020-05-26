/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailRencanaAnggaranProyekController  {

    @FXML private TextField noRAPField;
    @FXML private TextField tglRAPField;
    @FXML public ComboBox<String> kategoriCombo;
    @FXML public TextField keteranganField;
    @FXML public TextField estimasiField;
    @FXML public TextField totalAnggaranField;
    @FXML public TextField totalPropertyField;
    
    @FXML public Button saveButton;
    @FXML public Button cancelButton;
    
    public RAPHead rapHead;
    public String status;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        Function.setNumberField(estimasiField);
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        
        ObservableList<String> x = FXCollections.observableArrayList();
        x.add("PENGURUSAN SERTIFIKAT");
        x.add("INFRASTRUKTUR");
        x.add("PAGAR BUMI");
        x.add("GATE");
        x.add("TAMAN");
        x.add("FASUM");
        x.add("URUGAN");
        x.add("BANGUNAN RUMAH");
        x.add("ADDENDUM");
        kategoriCombo.setItems(x);
        kategoriCombo.getSelectionModel().clearSelection();
    }
    public void setNewRAP(){
        status = "new";
        rapHead = new RAPHead();
        rapHead.setListRapDetail(new ArrayList<>());
        rapHead.setListRapPropertyDetail(new ArrayList<>());
        noRAPField.setText("-");
        tglRAPField.setText(tglLengkap.format(new Date()));
    }
    public void setRAP(RAPHead rap){
        Task<RAPHead> task = new Task<RAPHead>() {
            @Override 
            public RAPHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    rap.setListRapDetail(RAPDetailDAO.getAllByNoRap(con, rap.getNoRap()));
                    rap.setListRapPropertyDetail(RAPDetailPropertyDAO.getAllByNoRap(con, rap.getNoRap()));
                    for(RAPDetailProperty d : rap.getListRapPropertyDetail()){
                        d.setStatus(true);
                        d.setProperty(PropertyDAO.get(con, d.getKodeProperty()));
                    }
                    return rap;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                rapHead = rap;
                kategoriCombo.setDisable(true);
                keteranganField.setDisable(true);
                estimasiField.setDisable(true);
                if(sistem.getUser().getLevel().equals("Manager")&& rap.getStatusApproval().equals("On Review")){
                    saveButton.setText("Approved");
                    cancelButton.setText("Rejected");
                }else{
                    saveButton.setText("Approved");
                    cancelButton.setText("Rejected");
                    saveButton.setVisible(false);
                    cancelButton.setVisible(false);
                    stage.setHeight(stage.getHeight()-30);
                }

                noRAPField.setText(rap.getNoRap());
                tglRAPField.setText(tglLengkap.format(tglSql.parse(rap.getTglRap())));
                kategoriCombo.getSelectionModel().select(rap.getKategoriPembangunan());
                keteranganField.setText(rap.getKeterangan());
                estimasiField.setText(rp.format(rap.getPerkiraanWaktu()));
                totalAnggaranField.setText(rp.format(rap.getTotalAnggaran()));
                totalPropertyField.setText(rp.format(rap.getTotalProperty()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
        
    }
    public void setEditRAP(RAPHead rap){
        Task<RAPHead> task = new Task<RAPHead>() {
            @Override 
            public RAPHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    rap.setListRapDetail(RAPDetailDAO.getAllByNoRap(con, rap.getNoRap()));
                    rap.setListRapPropertyDetail(RAPDetailPropertyDAO.getAllByNoRap(con, rap.getNoRap()));
                    for(RAPDetailProperty d : rap.getListRapPropertyDetail()){
                        d.setStatus(true);
                        d.setProperty(PropertyDAO.get(con, d.getKodeProperty()));
                    }
                    return rap;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                status = "edit";
                rapHead = rap;

                noRAPField.setText(rap.getNoRap());
                tglRAPField.setText(tglLengkap.format(tglSql.parse(rap.getTglRap())));
                kategoriCombo.getSelectionModel().select(rap.getKategoriPembangunan());
                keteranganField.setText(rap.getKeterangan());
                estimasiField.setText(rp.format(rap.getPerkiraanWaktu()));
                totalAnggaranField.setText(rp.format(rap.getTotalAnggaran()));
                totalPropertyField.setText(rp.format(rap.getTotalProperty()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
        
    }
    @FXML
    private void setAnggaranDetail(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailAnggaran.fxml");
        DetailAnggaranController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setDetailAnggaran(rapHead.getListRapDetail());
        if(!saveButton.getText().equals("Save"))
            x.setViewOnly();
        x.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            rapHead.setListRapDetail(x.allAnggaranDetail);
            double total = 0;
            for(RAPDetail d : rapHead.getListRapDetail()){
                total = total + d.getTotal();
            }
            totalAnggaranField.setText(rp.format(total));
        });
    }
    @FXML
    private void setDetailProperty(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailProyekProperty.fxml");
        DetailProyekPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setProperty(rapHead.getMetodePembagian(), rapHead.getListRapPropertyDetail());
        if(!saveButton.getText().equals("Save"))
            x.setViewOnly();
        x.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            rapHead.setMetodePembagian(x.metodeCombo.getSelectionModel().getSelectedItem());
            rapHead.setListRapPropertyDetail(x.allDetail);
            double total = 0;
            for(RAPDetailProperty d : rapHead.getListRapPropertyDetail()){
                if(d.isStatus())
                    total = total + 1;
            }
            totalPropertyField.setText(rp.format(total));
        });
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
