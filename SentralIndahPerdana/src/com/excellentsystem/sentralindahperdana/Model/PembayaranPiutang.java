/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class PembayaranPiutang {

    private final StringProperty noPembayaran = new SimpleStringProperty();
    private final StringProperty tglPembayaran = new SimpleStringProperty();
    private final StringProperty noPiutang = new SimpleStringProperty();
    private final DoubleProperty jumlahPembayaran = new SimpleDoubleProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private Piutang piutang;
    public Piutang getPiutang() {
        return piutang;
    }

    public void setPiutang(Piutang piutang) {
        this.piutang = piutang;
    }
    
    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }

    public double getJumlahPembayaran() {
        return jumlahPembayaran.get();
    }

    public void setJumlahPembayaran(double value) {
        jumlahPembayaran.set(value);
    }

    public DoubleProperty jumlahPembayaranProperty() {
        return jumlahPembayaran;
    }


    public String getNoPiutang() {
        return noPiutang.get();
    }

    public void setNoPiutang(String value) {
        noPiutang.set(value);
    }

    public StringProperty noPiutangProperty() {
        return noPiutang;
    }

    public String getTglPembayaran() {
        return tglPembayaran.get();
    }

    public void setTglPembayaran(String value) {
        tglPembayaran.set(value);
    }

    public StringProperty tglPembayaranProperty() {
        return tglPembayaran;
    }

    public String getNoPembayaran() {
        return noPembayaran.get();
    }

    public void setNoPembayaran(String value) {
        noPembayaran.set(value);
    }

    public StringProperty noPembayaranProperty() {
        return noPembayaran;
    }
    
}
