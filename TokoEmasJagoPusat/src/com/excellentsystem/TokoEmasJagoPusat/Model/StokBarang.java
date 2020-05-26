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
public class StokBarang { 
    //tt_stok_barang
    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeSubKategori = new SimpleStringProperty();
    
    private final DoubleProperty beratAwal = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenAwal = new SimpleDoubleProperty();
    private final DoubleProperty nilaiAwal = new SimpleDoubleProperty();
    
    private final DoubleProperty beratMasuk = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenMasuk = new SimpleDoubleProperty();
    private final DoubleProperty nilaiMasuk = new SimpleDoubleProperty();
    
    private final DoubleProperty beratKeluar = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenKeluar = new SimpleDoubleProperty();
    private final DoubleProperty nilaiKeluar = new SimpleDoubleProperty();
    
    private final DoubleProperty beratAkhir = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenAkhir = new SimpleDoubleProperty();
    private final DoubleProperty nilaiAkhir = new SimpleDoubleProperty();

    public double getNilaiAkhir() {
        return nilaiAkhir.get();
    }

    public void setNilaiAkhir(double value) {
        nilaiAkhir.set(value);
    }

    public DoubleProperty nilaiAkhirProperty() {
        return nilaiAkhir;
    }

    public double getBeratPersenAkhir() {
        return beratPersenAkhir.get();
    }

    public void setBeratPersenAkhir(double value) {
        beratPersenAkhir.set(value);
    }

    public DoubleProperty beratPersenAkhirProperty() {
        return beratPersenAkhir;
    }

    public double getNilaiKeluar() {
        return nilaiKeluar.get();
    }

    public void setNilaiKeluar(double value) {
        nilaiKeluar.set(value);
    }

    public DoubleProperty nilaiKeluarProperty() {
        return nilaiKeluar;
    }

    public double getBeratPersenKeluar() {
        return beratPersenKeluar.get();
    }

    public void setBeratPersenKeluar(double value) {
        beratPersenKeluar.set(value);
    }

    public DoubleProperty beratPersenKeluarProperty() {
        return beratPersenKeluar;
    }

    public double getNilaiMasuk() {
        return nilaiMasuk.get();
    }

    public void setNilaiMasuk(double value) {
        nilaiMasuk.set(value);
    }

    public DoubleProperty nilaiMasukProperty() {
        return nilaiMasuk;
    }

    public double getBeratPersenMasuk() {
        return beratPersenMasuk.get();
    }

    public void setBeratPersenMasuk(double value) {
        beratPersenMasuk.set(value);
    }

    public DoubleProperty beratPersenMasukProperty() {
        return beratPersenMasuk;
    }

    public double getNilaiAwal() {
        return nilaiAwal.get();
    }

    public void setNilaiAwal(double value) {
        nilaiAwal.set(value);
    }

    public DoubleProperty nilaiAwalProperty() {
        return nilaiAwal;
    }

    public double getBeratPersenAwal() {
        return beratPersenAwal.get();
    }

    public void setBeratPersenAwal(double value) {
        beratPersenAwal.set(value);
    }

    public DoubleProperty beratPersenAwalProperty() {
        return beratPersenAwal;
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

    public double getBeratAkhir() {
        return beratAkhir.get();
    }

    public void setBeratAkhir(double value) {
        beratAkhir.set(value);
    }

    public DoubleProperty beratAkhirProperty() {
        return beratAkhir;
    }

    public double getBeratKeluar() {
        return beratKeluar.get();
    }

    public void setBeratKeluar(double value) {
        beratKeluar.set(value);
    }

    public DoubleProperty beratKeluarProperty() {
        return beratKeluar;
    }

    public double getBeratMasuk() {
        return beratMasuk.get();
    }

    public void setBeratMasuk(double value) {
        beratMasuk.set(value);
    }

    public DoubleProperty beratMasukProperty() {
        return beratMasuk;
    }

    public double getBeratAwal() {
        return beratAwal.get();
    }

    public void setBeratAwal(double value) {
        beratAwal.set(value);
    }

    public DoubleProperty beratAwalProperty() {
        return beratAwal;
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
