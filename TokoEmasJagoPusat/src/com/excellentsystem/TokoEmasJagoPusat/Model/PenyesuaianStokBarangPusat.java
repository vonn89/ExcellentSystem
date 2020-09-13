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
 * @author Excellent
 */
public class PenyesuaianStokBarangPusat {

    private final StringProperty noPenyesuaian = new SimpleStringProperty();
    private final StringProperty tglPenyesuaian = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty jenis = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty hargaPersen = new SimpleDoubleProperty();
    private final DoubleProperty hargaEmas = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
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

    public double getNilaiPokok() {
        return nilaiPokok.get();
    }

    public void setNilaiPokok(double value) {
        nilaiPokok.set(value);
    }

    public DoubleProperty nilaiPokokProperty() {
        return nilaiPokok;
    }

    public double getHargaEmas() {
        return hargaEmas.get();
    }

    public void setHargaEmas(double value) {
        hargaEmas.set(value);
    }

    public DoubleProperty hargaEmasProperty() {
        return hargaEmas;
    }

    public double getHargaPersen() {
        return hargaPersen.get();
    }

    public void setHargaPersen(double value) {
        hargaPersen.set(value);
    }

    public DoubleProperty hargaPersenProperty() {
        return hargaPersen;
    }

    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }

    public String getJenis() {
        return jenis.get();
    }

    public void setJenis(String value) {
        jenis.set(value);
    }

    public StringProperty jenisProperty() {
        return jenis;
    }

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getTglPenyesuaian() {
        return tglPenyesuaian.get();
    }

    public void setTglPenyesuaian(String value) {
        tglPenyesuaian.set(value);
    }

    public StringProperty tglPenyesuaianProperty() {
        return tglPenyesuaian;
    }

    public String getNoPenyesuaian() {
        return noPenyesuaian.get();
    }

    public void setNoPenyesuaian(String value) {
        noPenyesuaian.set(value);
    }

    public StringProperty noPenyesuaianProperty() {
        return noPenyesuaian;
    }
    
}
