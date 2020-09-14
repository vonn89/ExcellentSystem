/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PembayaranHutang {
    //tt_pembayaran_hutang
    private final StringProperty noPembayaran = new SimpleStringProperty();
    private final StringProperty tglPembayaran = new SimpleStringProperty();
    private final StringProperty noHutang = new SimpleStringProperty();
    private final DoubleProperty jumlahBayar = new SimpleDoubleProperty();
    private final DoubleProperty kurs = new SimpleDoubleProperty();
    private final DoubleProperty terbayar = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    
    public double getTerbayar() {
        return terbayar.get();
    }

    public void setTerbayar(double value) {
        terbayar.set(value);
    }

    public DoubleProperty terbayarProperty() {
        return terbayar;
    }


    public double getKurs() {
        return kurs.get();
    }

    public void setKurs(double value) {
        kurs.set(value);
    }

    public DoubleProperty kursProperty() {
        return kurs;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
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

    public double getJumlahBayar() {
        return jumlahBayar.get();
    }

    public void setJumlahBayar(double value) {
        jumlahBayar.set(value);
    }

    public DoubleProperty jumlahBayarProperty() {
        return jumlahBayar;
    }


    public String getNoHutang() {
        return noHutang.get();
    }

    public void setNoHutang(String value) {
        noHutang.set(value);
    }

    public StringProperty noHutangProperty() {
        return noHutang;
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
