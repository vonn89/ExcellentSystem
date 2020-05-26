/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasPadi.View.Dialog;

import com.excellentsystem.TokoEmasPadi.DAO.SistemDAO;
import com.excellentsystem.TokoEmasPadi.Koneksi;
import com.excellentsystem.TokoEmasPadi.Main;
import static com.excellentsystem.TokoEmasPadi.Main.sistem;
import com.excellentsystem.TokoEmasPadi.Model.Sistem;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PengaturanUmumController {

    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField kotaField;
    @FXML private TextField noTelpField;
    @FXML private TextField emailField;
    @FXML private TextField prefixBarcodeField;
    @FXML private TextField serverField;
    @FXML private TextField printerPenjualanField;
    @FXML private TextField printerGadaiField;
    @FXML private TextField printerBarcodeField;
    private Main mainApp;
    private Stage stage;
    public void setMainApp(Main mainApp, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        setDataPerusahaan();
    }
    @FXML
    private void setDataPerusahaan(){
        try{
            namaField.setText(sistem.getNama());
            alamatField.setText(sistem.getAlamat());
            kotaField.setText(sistem.getKota());
            noTelpField.setText(sistem.getNoTelp());
            emailField.setText(sistem.getWebsite());
            prefixBarcodeField.setText(sistem.getCode());
            BufferedReader in = new BufferedReader(new FileReader("koneksi.txt"));
            String ipServer = in.readLine();
            String printerPenjualan = in.readLine();
            String printerGadai = in.readLine();
            String printerBarcode = in.readLine();
            serverField.setText(ipServer);
            printerPenjualanField.setText(printerPenjualan);
            printerGadaiField.setText(printerGadai);
            printerBarcodeField.setText(printerBarcode);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void cancel(){
        stage.close();
    }
    @FXML
    private void saveDataPerusahaan(){
        try(Connection con = Koneksi.getConnection()){
            Sistem sistem = Main.sistem;
            sistem.setNama(namaField.getText());
            sistem.setAlamat(alamatField.getText());
            sistem.setKota(kotaField.getText());
            sistem.setNoTelp(noTelpField.getText());
            sistem.setWebsite(emailField.getText());
            sistem.setCode(prefixBarcodeField.getText());
            try (BufferedWriter out = new BufferedWriter(new FileWriter("koneksi.txt"))) {
                out.write(serverField.getText());
                out.newLine();
                out.write(printerPenjualanField.getText());
                out.newLine();
                out.write(printerGadaiField.getText());
                out.newLine();
                out.write(printerBarcodeField.getText());
            }
            SistemDAO.update(con, sistem);
            MessageController controller = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Setting baru berhasil disimpan,\nto take effect please restart program");
            controller.OK.setOnAction((ActionEvent event) -> {
                try{
                    mainApp.MainStage.close();
                    mainApp.start(new Stage());
                }catch(Exception e){
                    System.exit(0);
                }
            });
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
