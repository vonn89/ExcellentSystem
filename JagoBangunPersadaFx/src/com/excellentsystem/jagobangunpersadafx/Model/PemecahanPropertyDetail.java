/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PemecahanPropertyDetail {
    private final StringProperty noPemecahan = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaProperty = new SimpleStringProperty();
    private final DoubleProperty luasTanah = new SimpleDoubleProperty();
    private final DoubleProperty nilaiProperty = new SimpleDoubleProperty();
    private Property property;

    public double getNilaiProperty() {
        return nilaiProperty.get();
    }

    public void setNilaiProperty(double value) {
        nilaiProperty.set(value);
    }

    public DoubleProperty nilaiPropertyProperty() {
        return nilaiProperty;
    }


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    


    public double getLuasTanah() {
        return luasTanah.get();
    }

    public void setLuasTanah(double value) {
        luasTanah.set(value);
    }

    public DoubleProperty luasTanahProperty() {
        return luasTanah;
    }
    
    public String getNamaProperty() {
        return namaProperty.get();
    }

    public void setNamaProperty(String value) {
        namaProperty.set(value);
    }

    public StringProperty namaPropertyProperty() {
        return namaProperty;
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

    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
    }
    public String getNoPemecahan() {
        return noPemecahan.get();
    }

    public void setNoPemecahan(String value) {
        noPemecahan.set(value);
    }

    public StringProperty noPemecahanProperty() {
        return noPemecahan;
    }
    
}
