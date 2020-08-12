/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class RencanaAnggaranPenjualanController {

    @FXML private Label namaPekerjaanLabel;
    @FXML private Label volumeLabel;
    @FXML private Label satuanLabel;
    @FXML public TextField estimasiWaktuPengerjaanField;
    @FXML private TextField totalAnggaranBelanjaField;
    @FXML private TextField totalAnggaranBebanPenjualanField;
    
    @FXML private TextField totalAnggaranPenjualanField;
    @FXML private TextField nilaiPenjualanField;
    @FXML private TextField perkiraanKeuntunganKotorField;
    @FXML public TextField perkiraanKeuntunganPersenField;
    @FXML public Button saveButton;
    public PenjualanDetail penjualanDetail;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        estimasiWaktuPengerjaanField.setOnKeyReleased((event) -> {
            try{
                estimasiWaktuPengerjaanField.setText(df.format(Double.parseDouble(estimasiWaktuPengerjaanField.getText().replaceAll(",", ""))));
                estimasiWaktuPengerjaanField.end();
            }catch(Exception e){
                estimasiWaktuPengerjaanField.undo();
            }
        });
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setPekerjaan(PenjualanDetail d){
        penjualanDetail = d;
        namaPekerjaanLabel.setText(penjualanDetail.getNamaPekerjaan());
        volumeLabel.setText(df.format(penjualanDetail.getQty()));
        satuanLabel.setText(penjualanDetail.getPekerjaan().getSatuan());
        estimasiWaktuPengerjaanField.setText(df.format(penjualanDetail.getWaktuPengerjaan()));
        hitungTotal();
    }
    public void setViewOnly(){
        estimasiWaktuPengerjaanField.setDisable(true);
        saveButton.setVisible(false);
    }
    @FXML
    private void showRencanaAnggaranBelanja(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/RencanaAnggaranBelanja.fxml");
        RencanaAnggaranBelanjaController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setAnggaranBelanja(penjualanDetail.getRencanaAnggaranBelanja());
        controller.addItemButton.setVisible(saveButton.isVisible());
        controller.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            penjualanDetail.setRencanaAnggaranBelanja(controller.allMaterial);
            hitungTotal();
        });
    }
    @FXML
    private void showRencanaAnggaranBebanPenjualan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/RencanaAnggaranBebanPenjualan.fxml");
        RencanaAnggaranBebanPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setAnggaranBeban(penjualanDetail.getRencanaAnggaranBebanPenjualan());
        controller.hbox.setVisible(saveButton.isVisible());
        controller.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            penjualanDetail.setRencanaAnggaranBebanPenjualan(controller.allBeban);
            hitungTotal();
        });
    }
    private void hitungTotal(){
        double totalRAB = 0;
        for(RencanaAnggaranBebanMaterial b : penjualanDetail.getRencanaAnggaranBelanja()){
            totalRAB = totalRAB + b.getHargaJual()*b.getQty();
        }
        double totalBeban = 0;
        for(RencanaAnggaranBebanPenjualan b : penjualanDetail.getRencanaAnggaranBebanPenjualan()){
            totalBeban = totalBeban + b.getJumlahRp();
        }
        totalAnggaranBelanjaField.setText(df.format(totalRAB));
        totalAnggaranBebanPenjualanField.setText(df.format(totalBeban));
        totalAnggaranPenjualanField.setText(df.format(totalRAB+totalBeban));
        nilaiPenjualanField.setText(df.format(penjualanDetail.getTotal()));
        perkiraanKeuntunganKotorField.setText(df.format(penjualanDetail.getTotal()-totalRAB-totalBeban));
        perkiraanKeuntunganPersenField.setText(df.format(
            (penjualanDetail.getTotal()-totalRAB-totalBeban)/penjualanDetail.getTotal()*100)+" %");
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
