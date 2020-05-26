/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class BarangFiktif {

    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty kadar = new SimpleStringProperty();
    private final StringProperty kodeIntern = new SimpleStringProperty();

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public String getKodeIntern() {
        return kodeIntern.get();
    }

    public void setKodeIntern(String value) {
        kodeIntern.set(value);
    }

    public StringProperty kodeInternProperty() {
        return kodeIntern;
    }

    public String getKadar() {
        return kadar.get();
    }

    public void setKadar(String value) {
        kadar.set(value);
    }

    public StringProperty kadarProperty() {
        return kadar;
    }

    public String getNamaBarang() {
        return namaBarang.get();
    }

    public void setNamaBarang(String value) {
        namaBarang.set(value);
    }

    public StringProperty namaBarangProperty() {
        return namaBarang;
    }

}
