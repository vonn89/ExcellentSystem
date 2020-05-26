/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class StokBahan {
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeBahan = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final DoubleProperty stokAwal = new SimpleDoubleProperty();
    private final DoubleProperty stokMasuk = new SimpleDoubleProperty();
    private final DoubleProperty stokKeluar = new SimpleDoubleProperty();
    private final DoubleProperty stokAkhir = new SimpleDoubleProperty();
    private Bahan bahan;
    private KategoriBahan kategoriBahan;
    private final DoubleProperty nilaiAkhir = new SimpleDoubleProperty();

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }

    public double getNilaiAkhir() {
        return nilaiAkhir.get();
    }

    public void setNilaiAkhir(double value) {
        nilaiAkhir.set(value);
    }

    public DoubleProperty nilaiAkhirProperty() {
        return nilaiAkhir;
    }

    public KategoriBahan getKategoriBahan() {
        return kategoriBahan;
    }

    public void setKategoriBahan(KategoriBahan kategoriBahan) {
        this.kategoriBahan = kategoriBahan;
    }
    
    public Bahan getBahan() {
        return bahan;
    }

    public void setBahan(Bahan bahan) {
        this.bahan = bahan;
    }
    

    public String getKodeBahan() {
        return kodeBahan.get();
    }

    public void setKodeBahan(String value) {
        kodeBahan.set(value);
    }

    public StringProperty kodeBahanProperty() {
        return kodeBahan;
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

    public double getStokAkhir() {
        return stokAkhir.get();
    }

    public void setStokAkhir(double value) {
        stokAkhir.set(value);
    }

    public DoubleProperty stokAkhirProperty() {
        return stokAkhir;
    }
    public double getStokKeluar() {
        return stokKeluar.get();
    }

    public void setStokKeluar(double value) {
        stokKeluar.set(value);
    }

    public DoubleProperty stokKeluarProperty() {
        return stokKeluar;
    }
    public double getStokMasuk() {
        return stokMasuk.get();
    }

    public void setStokMasuk(double value) {
        stokMasuk.set(value);
    }

    public DoubleProperty stokMasukProperty() {
        return stokMasuk;
    }
    public double getStokAwal() {
        return stokAwal.get();
    }

    public void setStokAwal(double value) {
        stokAwal.set(value);
    }

    public DoubleProperty stokAwalProperty() {
        return stokAwal;
    }

     
    
}
