/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.Model.Helper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Notification {
    private final StringProperty noTransaksi = new SimpleStringProperty();
    private final StringProperty jenisTransaksi = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty nama = new SimpleStringProperty();



    public String getNoTransaksi() {
        return noTransaksi.get();
    }

    public void setNoTransaksi(String value) {
        noTransaksi.set(value);
    }

    public StringProperty noTransaksiProperty() {
        return noTransaksi;
    }

    

    public String getJenisTransaksi() {
        return jenisTransaksi.get();
    }

    public void setJenisTransaksi(String value) {
        jenisTransaksi.set(value);
    }

    public StringProperty jenisTransaksiProperty() {
        return jenisTransaksi;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }
    
}
