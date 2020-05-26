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
 * @author Yunaz
 */
public class SubKategori {

    //tm_sub_kategori
    private final StringProperty kodeSubKategori = new SimpleStringProperty();
    private final StringProperty namaSubKategori = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private Kategori kategori;

    public Kategori getKategori() {
        return kategori;
    }

    public void setKategori(Kategori kategori) {
        this.kategori = kategori;
    }
    
    
    
    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public String getNamaSubKategori() {
        return namaSubKategori.get();
    }

    public void setNamaSubKategori(String value) {
        namaSubKategori.set(value);
    }

    public StringProperty namaSubKategoriProperty() {
        return namaSubKategori;
    }

    public String getKodeSubKategori() {
        return kodeSubKategori.get();
    }

    public void setKodeSubKategori(String value) {
        kodeSubKategori.set(value);
    }

    public StringProperty kodeSubKategoriProperty() {
        return kodeSubKategori;
    }
    
    
}
