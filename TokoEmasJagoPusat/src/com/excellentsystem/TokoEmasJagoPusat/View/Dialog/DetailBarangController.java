/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailBarangController {

    @FXML public TextField kodeBarcodeField;
    @FXML public TextField kodeBarangField;
    @FXML public TextField namaBarangField;
    @FXML public TextField kodeKategoriField;
    @FXML public TextField kodeJenisField;
    @FXML public TextField kodeInternField;
    @FXML public TextField kadarField;
    @FXML public TextField beratField;
    @FXML public TextField beratAsliField;
    @FXML public TextField tglBarcodeField;
    @FXML public TextField userBarcodeField;
    @FXML public TextField asalBarangField;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setBarang(BarangCabang b){
        try{
            kodeBarcodeField.setText(b.getKodeBarcode());
            kodeBarangField.setText(b.getKodeBarang());
            namaBarangField.setText(b.getNamaBarang());
            kodeKategoriField.setText(b.getKodeKategori());
            kodeJenisField.setText(b.getKodeJenis());
            kodeInternField.setText(b.getKodeIntern());
            kadarField.setText(b.getKadar());
            beratField.setText(gr.format(b.getBerat()));
            beratAsliField.setText(gr.format(b.getBeratAsli()));
            tglBarcodeField.setText(tglLengkap.format(tglSql.parse(b.getInputDate())));
            userBarcodeField.setText(b.getInputBy());
            asalBarangField.setText(b.getAsalBarang());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void setBarangByKodeBarcode(String kodeBarcode){
        Task<BarangCabang> task = new Task<BarangCabang>() {
            @Override 
            public BarangCabang call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return BarangCabangDAO.get(conPusat, kodeBarcode);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            try{
                BarangCabang b = task.getValue();
                kodeBarcodeField.setText(b.getKodeBarcode());
                kodeBarangField.setText(b.getKodeBarang());
                namaBarangField.setText(b.getNamaBarang());
                kodeKategoriField.setText(b.getKodeKategori());
                kodeJenisField.setText(b.getKodeJenis());
                kodeInternField.setText(b.getKodeIntern());
                kadarField.setText(b.getKadar());
                beratField.setText(gr.format(b.getBerat()));
                beratAsliField.setText(gr.format(b.getBeratAsli()));
                tglBarcodeField.setText(tglLengkap.format(tglSql.parse(b.getInputDate())));
                userBarcodeField.setText(b.getInputBy());
                asalBarangField.setText(b.getAsalBarang());
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
