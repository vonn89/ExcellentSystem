/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.RAPRealisasiDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import java.sql.Connection;
import java.util.List;
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
public class DetailRealisasiProyekController  {

    @FXML public TextField keteranganField;
    @FXML public TextField satuanField;
    @FXML public TextField qtyField;
    @FXML public TextField hargaSatuanField;
    @FXML public TextField totalField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    @FXML public Button cancelButton;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(Exception e){
                qtyField.undo();
            }
            hitungTotal();
        });
        hargaSatuanField.setOnKeyReleased((event) -> {
            try{
                String string = hargaSatuanField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        hargaSatuanField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        hargaSatuanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    hargaSatuanField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                hargaSatuanField.end();
            }catch(Exception e){
                hargaSatuanField.undo();
            }
            hitungTotal();
        });
        totalField.setOnKeyReleased((event) -> {
            try{
                String string = totalField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        totalField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        totalField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    totalField.setText(qty.format(Double.parseDouble(string.replaceAll(",", ""))));
                totalField.end();
            }catch(Exception e){
                totalField.undo();
            }
            hitungHargaSatuan();
        });
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            tipeKeuanganCombo.setItems(Function.getAllTipeKeuangan());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void getDetailRealisasi(String noRealisasi, String noUrut){
        Task<RAPRealisasi> task = new Task<RAPRealisasi>() {
            @Override 
            public RAPRealisasi call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    return RAPRealisasiDAO.get(con, noRealisasi, Integer.parseInt(noUrut));
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            setDetailRealisasi(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setDetailRealisasi(RAPRealisasi r){
        keteranganField.setDisable(true);
        satuanField.setDisable(true);
        qtyField.setDisable(true);
        hargaSatuanField.setDisable(true);
        totalField.setDisable(true);
        tipeKeuanganCombo.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        
        keteranganField.setText(r.getKeterangan());
        satuanField.setText(r.getSatuan());
        qtyField.setText(qty.format(r.getQty()));
        hargaSatuanField.setText(rp.format(r.getJumlahRp()/r.getQty()));
        totalField.setText(rp.format(r.getJumlahRp()));
        tipeKeuanganCombo.getSelectionModel().select(r.getTipeKeuangan());
    }
    private void hitungTotal(){
        try{
            double qty = Double.parseDouble(qtyField.getText().replaceAll(",", ""));
            double harga = Double.parseDouble(hargaSatuanField.getText().replaceAll(",", ""));
            totalField.setText(rp.format(qty*harga));
        }catch(Exception e){
            totalField.setText("0");
        }
    }
    private void hitungHargaSatuan(){
        try{
            double qty = Double.parseDouble(qtyField.getText().replaceAll(",", ""));
            double total = Double.parseDouble(totalField.getText().replaceAll(",", ""));
            hargaSatuanField.setText(rp.format(total/qty));
        }catch(Exception e){
            totalField.setText("0");
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
