/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PembayaranPembelianController  {

    @FXML public TextField noPembelianField;
    @FXML public TextField supplierField;
    @FXML public TextField totalPembelianField;
    @FXML public TextField sudahTerbayarField;
    @FXML public TextField sisaPembayaranField;
    @FXML public TextField jumlahPembayaranRpField;
    @FXML public TextField hargaEmasField;
    @FXML public TextField jumlahPembayaranGrField;
    @FXML public Button saveButton;
    
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        jumlahPembayaranRpField.setOnKeyReleased((event) -> {
            try{
                String string = jumlahPembayaranRpField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        jumlahPembayaranRpField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        jumlahPembayaranRpField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    jumlahPembayaranRpField.setText(rp.format(Double.parseDouble(string.replaceAll(",", ""))));
                jumlahPembayaranRpField.end();
            }catch(Exception e){
                jumlahPembayaranRpField.undo();
            }
            jumlahPembayaranGrField.setText(gr.format(Double.parseDouble(jumlahPembayaranRpField.getText().replaceAll(",", ""))/
                    Double.parseDouble(hargaEmasField.getText().replaceAll(",", ""))));
        });
        jumlahPembayaranGrField.setOnKeyReleased((event) -> {
            try{
                String string = jumlahPembayaranGrField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        jumlahPembayaranGrField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        jumlahPembayaranGrField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    jumlahPembayaranGrField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                jumlahPembayaranGrField.end();
            }catch(Exception e){
                jumlahPembayaranGrField.undo();
            }
            jumlahPembayaranRpField.setText(gr.format(Double.parseDouble(jumlahPembayaranGrField.getText().replaceAll(",", ""))*
                    Double.parseDouble(hargaEmasField.getText().replaceAll(",", ""))));
        });
    }   
    public void setPembayaranPembelian(PembelianHead p){
        noPembelianField.setText(p.getNoPembelian());
        supplierField.setText(p.getSupplier());
        totalPembelianField.setText(gr.format(p.getTotalHargaPersen()));
        sudahTerbayarField.setText(gr.format(p.getHutang().getTerbayar()/p.getHutang().getKurs()));
        sisaPembayaranField.setText(gr.format(p.getHutang().getSisaHutang()/p.getHutang().getKurs()));

        hargaEmasField.setText(rp.format(sistem.getHargaEmas()));
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }    
    
}
