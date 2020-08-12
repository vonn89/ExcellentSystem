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
 * @author excellent
 */
public class Pekerjaan {

    private final StringProperty kodePekerjaan = new SimpleStringProperty();
    private final StringProperty kategoriPekerjaan = new SimpleStringProperty();
    private final StringProperty namaPekerjaan = new SimpleStringProperty();
    private final StringProperty spesifikasi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty laba = new SimpleDoubleProperty();

    public double getLaba() {
        return laba.get();
    }

    public void setLaba(double value) {
        laba.set(value);
    }

    public DoubleProperty labaProperty() {
        return laba;
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


    public String getSatuan() {
        return satuan.get();
    }

    public void setSatuan(String value) {
        satuan.set(value);
    }

    public StringProperty satuanProperty() {
        return satuan;
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

    public String getSpesifikasi() {
        return spesifikasi.get();
    }

    public void setSpesifikasi(String value) {
        spesifikasi.set(value);
    }

    public StringProperty spesifikasiProperty() {
        return spesifikasi;
    }

    public String getNamaPekerjaan() {
        return namaPekerjaan.get();
    }

    public void setNamaPekerjaan(String value) {
        namaPekerjaan.set(value);
    }

    public StringProperty namaPekerjaanProperty() {
        return namaPekerjaan;
    }

    public String getKategoriPekerjaan() {
        return kategoriPekerjaan.get();
    }

    public void setKategoriPekerjaan(String value) {
        kategoriPekerjaan.set(value);
    }

    public StringProperty kategoriPekerjaanProperty() {
        return kategoriPekerjaan;
    }

    public String getKodePekerjaan() {
        return kodePekerjaan.get();
    }

    public void setKodePekerjaan(String value) {
        kodePekerjaan.set(value);
    }

    public StringProperty kodePekerjaanProperty() {
        return kodePekerjaan;
    }
    
}
