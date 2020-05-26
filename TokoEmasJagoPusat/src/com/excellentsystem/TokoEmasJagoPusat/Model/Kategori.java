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
public class Kategori {
    //tm_kategori
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaKategori = new SimpleStringProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty hargaPersenJual = new SimpleDoubleProperty();
    private final StringProperty kadar = new SimpleStringProperty();

    public double getPersentaseEmas() {
        return persentaseEmas.get();
    }

    public void setPersentaseEmas(double value) {
        persentaseEmas.set(value);
    }

    public DoubleProperty persentaseEmasProperty() {
        return persentaseEmas;
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

    public double getHargaPersenJual() {
        return hargaPersenJual.get();
    }

    public void setHargaPersenJual(double value) {
        hargaPersenJual.set(value);
    }

    public DoubleProperty hargaPersenJualProperty() {
        return hargaPersenJual;
    }


    public String getNamaKategori() {
        return namaKategori.get();
    }

    public void setNamaKategori(String value) {
        namaKategori.set(value);
    }

    public StringProperty namaKategoriProperty() {
        return namaKategori;
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
}
