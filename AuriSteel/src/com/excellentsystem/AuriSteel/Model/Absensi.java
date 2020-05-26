/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class Absensi {

    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final LongProperty jamMasuk = new SimpleLongProperty();
    private final LongProperty jamPulang = new SimpleLongProperty();
    private final LongProperty AbsenMasuk = new SimpleLongProperty();
    private final LongProperty absenPulang = new SimpleLongProperty();
    private final StringProperty keterangan = new SimpleStringProperty();

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }
    
    public long getAbsenPulang() {
        return absenPulang.get();
    }

    public void setAbsenPulang(long value) {
        absenPulang.set(value);
    }

    public LongProperty absenPulangProperty() {
        return absenPulang;
    }

    public long getAbsenMasuk() {
        return AbsenMasuk.get();
    }

    public void setAbsenMasuk(long value) {
        AbsenMasuk.set(value);
    }

    public LongProperty AbsenMasukProperty() {
        return AbsenMasuk;
    }

    public long getJamPulang() {
        return jamPulang.get();
    }

    public void setJamPulang(long value) {
        jamPulang.set(value);
    }

    public LongProperty jamPulangProperty() {
        return jamPulang;
    }

    public long getJamMasuk() {
        return jamMasuk.get();
    }

    public void setJamMasuk(long value) {
        jamMasuk.set(value);
    }

    public LongProperty jamMasukProperty() {
        return jamMasuk;
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
    
    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }

}
