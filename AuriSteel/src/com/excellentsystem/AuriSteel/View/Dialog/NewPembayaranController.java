/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.KategoriKeuangan;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.PembelianHead;
import com.excellentsystem.AuriSteel.Model.PemesananCoilHead;
import com.excellentsystem.AuriSteel.Model.PemesananHead;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.Model.PenjualanHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import java.sql.Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPembayaranController  {

    @FXML public Label title;
    @FXML public Label noTransaksiLabel;
    @FXML public Label totalTransaksiLabel;
    @FXML public Label sudahTerbayarLabel;
    
    @FXML public TextField noTransaksiField;
    @FXML public TextField totalTransaksiField;
    @FXML public TextField sudahTerbayarField;
    @FXML public TextField sisaPembayaranField;
    @FXML public TextField jumlahPembayaranField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
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
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for(KategoriKeuangan kk : sistem.getListKategoriKeuangan()){
            listKeuangan.add(kk.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        jumlahPembayaranField.setOnKeyReleased((event) -> {
            try{
                String string = jumlahPembayaranField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        jumlahPembayaranField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        jumlahPembayaranField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    jumlahPembayaranField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                jumlahPembayaranField.end();
            }catch(Exception e){
                jumlahPembayaranField.undo();
            }
        });
    }   
    public void setTerimaPembayaranDownPayment(PemesananHead p){
        title.setText("Terima Pembayaran DP");
        noTransaksiLabel.setText("No Pemesanan");
        totalTransaksiLabel.setText("Total Pemesanan");
        sudahTerbayarLabel.setText("Down Payment");
        noTransaksiField.setText(p.getNoPemesanan());
        totalTransaksiField.setText(df.format(p.getTotalPemesanan()));
        sudahTerbayarField.setText(df.format(p.getDownPayment()));
        sisaPembayaranField.setText(df.format(p.getTotalPemesanan()-p.getDownPayment()));
    }
    public void setTerimaPembayaranDownPaymentCoil(PemesananCoilHead p){
        title.setText("Terima Pembayaran DP");
        noTransaksiLabel.setText("No Pemesanan");
        totalTransaksiLabel.setText("Total Pemesanan");
        sudahTerbayarLabel.setText("Down Payment");
        noTransaksiField.setText(p.getNoPemesanan());
        totalTransaksiField.setText(df.format(p.getTotalPemesanan()));
        sudahTerbayarField.setText(df.format(p.getDownPayment()));
        sisaPembayaranField.setText(df.format(p.getTotalPemesanan()-p.getDownPayment()));
    }
    public void setPembayaranPenjualanCoil(String noPenjualan){
        Task<PenjualanCoilHead> task = new Task<PenjualanCoilHead>() {
            @Override 
            public PenjualanCoilHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PenjualanCoilHeadDAO.get(con, noPenjualan);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                PenjualanCoilHead p = task.getValue();
                title.setText("Pembayaran Penjualan");
                noTransaksiLabel.setText("No Penjualan");
                totalTransaksiLabel.setText("Total Penjualan");
                noTransaksiField.setText(p.getNoPenjualan());
                totalTransaksiField.setText(df.format(p.getTotalPenjualan()));
                sudahTerbayarField.setText(df.format(p.getPembayaran()));
                sisaPembayaranField.setText(df.format(p.getSisaPembayaran()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPembayaranPenjualan(String noPenjualan){
        Task<PenjualanHead> task = new Task<PenjualanHead>() {
            @Override 
            public PenjualanHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PenjualanHeadDAO.get(con, noPenjualan);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                PenjualanHead p = task.getValue();
                title.setText("Pembayaran Penjualan");
                noTransaksiLabel.setText("No Penjualan");
                totalTransaksiLabel.setText("Total Penjualan");
                noTransaksiField.setText(p.getNoPenjualan());
                totalTransaksiField.setText(df.format(p.getTotalPenjualan()));
                sudahTerbayarField.setText(df.format(p.getPembayaran()));
                sisaPembayaranField.setText(df.format(p.getSisaPembayaran()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPembayaranPembelian(String noPembelian){
        Task<PembelianHead> task = new Task<PembelianHead>() {
            @Override 
            public PembelianHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PembelianHeadDAO.get(con, noPembelian);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                PembelianHead p = task.getValue();
                title.setText("Pembayaran Pembelian");
                noTransaksiLabel.setText("No Pembelian");
                totalTransaksiLabel.setText("Total Pembelian");
                noTransaksiField.setText(p.getNoPembelian());
                totalTransaksiField.setText(df.format(p.getGrandtotal()));
                sudahTerbayarField.setText(df.format(p.getPembayaran()));
                sisaPembayaranField.setText(df.format(p.getSisaPembayaran()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPembayaranPembelianBarang(String noPembelian){
        Task<PembelianBarangHead> task = new Task<PembelianBarangHead>() {
            @Override 
            public PembelianBarangHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PembelianBarangHeadDAO.get(con, noPembelian);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                PembelianBarangHead p = task.getValue();
                title.setText("Pembayaran Pembelian");
                noTransaksiLabel.setText("No Pembelian");
                totalTransaksiLabel.setText("Total Pembelian");
                noTransaksiField.setText(p.getNoPembelian());
                totalTransaksiField.setText(df.format(p.getGrandtotal()));
                sudahTerbayarField.setText(df.format(p.getPembayaran()));
                sisaPembayaranField.setText(df.format(p.getSisaPembayaran()));
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    public void setPembayaranHutang(Hutang h){
        title.setText("Pembayaran Hutang");
        noTransaksiLabel.setText("No Hutang");
        totalTransaksiLabel.setText("Total Hutang");
        noTransaksiField.setText(h.getNoHutang());
        totalTransaksiField.setText(df.format(h.getJumlahHutang()));
        sudahTerbayarField.setText(df.format(h.getPembayaran()));
        sisaPembayaranField.setText(df.format(h.getSisaHutang()));
    }
    public void setPembayaranPiutang(Piutang p){
        title.setText("Pembayaran Piutang");
        noTransaksiLabel.setText("No Piutang");
        totalTransaksiLabel.setText("Total Piutang");
        noTransaksiField.setText(p.getNoPiutang());
        totalTransaksiField.setText(df.format(p.getJumlahPiutang()));
        sudahTerbayarField.setText(df.format(p.getPembayaran()));
        sisaPembayaranField.setText(df.format(p.getSisaPiutang()));
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }    
    
}
