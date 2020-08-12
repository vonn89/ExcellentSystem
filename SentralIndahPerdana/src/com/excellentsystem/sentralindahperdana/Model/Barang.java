/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class Barang {

    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty kategoriBarang = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty satuanDasar = new SimpleStringProperty();
    private final DoubleProperty laba = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<Satuan> allSatuan;
    private List<StokBarang> stokAkhir;

    public double getLaba() {
        return laba.get();
    }

    public void setLaba(double value) {
        laba.set(value);
    }

    public DoubleProperty labaProperty() {
        return laba;
    }

    public String getKategoriBarang() {
        return kategoriBarang.get();
    }

    public void setKategoriBarang(String value) {
        kategoriBarang.set(value);
    }

    public StringProperty kategoriBarangProperty() {
        return kategoriBarang;
    }

    public List<StokBarang> getStokAkhir() {
        return stokAkhir;
    }

    public void setStokAkhir(List<StokBarang> stokAkhir) {
        this.stokAkhir = stokAkhir;
    }


    public String getSatuanDasar() {
        return satuanDasar.get();
    }

    public void setSatuanDasar(String value) {
        satuanDasar.set(value);
    }

    public StringProperty satuanDasarProperty() {
        return satuanDasar;
    }

    


    public List<Satuan> getAllSatuan() {
        return allSatuan;
    }

    public void setAllSatuan(List<Satuan> allSatuan) {
        this.allSatuan = allSatuan;
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


    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
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

    public String getKodeBarang() {
        return kodeBarang.get();
    }

    public void setKodeBarang(String value) {
        kodeBarang.set(value);
    }

    public StringProperty kodeBarangProperty() {
        return kodeBarang;
    }
    
}
