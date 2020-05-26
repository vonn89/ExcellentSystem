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
public class Property {
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaProperty = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty tipe = new SimpleStringProperty();
    private final StringProperty spesifikasi = new SimpleStringProperty();
    private final DoubleProperty luasTanah = new SimpleDoubleProperty();
    private final DoubleProperty luasBangunan = new SimpleDoubleProperty();
    private final DoubleProperty nilaiProperty = new SimpleDoubleProperty();
    private final DoubleProperty hargaJual = new SimpleDoubleProperty();
    private final DoubleProperty diskon = new SimpleDoubleProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public double getNilaiProperty() {
        return nilaiProperty.get();
    }

    public void setNilaiProperty(double value) {
        nilaiProperty.set(value);
    }

    public DoubleProperty nilaiPropertyProperty() {
        return nilaiProperty;
    }

    public double getDiskon() {
        return diskon.get();
    }

    public void setDiskon(double value) {
        diskon.set(value);
    }

    public DoubleProperty diskonProperty() {
        return diskon;
    }

    public String getTipe() {
        return tipe.get();
    }

    public void setTipe(String value) {
        tipe.set(value);
    }

    public StringProperty tipeProperty() {
        return tipe;
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

    public double getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(double value) {
        hargaJual.set(value);
    }

    public DoubleProperty hargaJualProperty() {
        return hargaJual;
    }


    public double getLuasBangunan() {
        return luasBangunan.get();
    }

    public void setLuasBangunan(double value) {
        luasBangunan.set(value);
    }

    public DoubleProperty luasBangunanProperty() {
        return luasBangunan;
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
    

    public String getSpesifikasi() {
        return spesifikasi.get();
    }

    public void setSpesifikasi(String value) {
        spesifikasi.set(value);
    }

    public StringProperty spesifikasiProperty() {
        return spesifikasi;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
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
    
}
