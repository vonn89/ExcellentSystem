/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class RevisiBarangCabang {

    private final StringProperty noRevisi = new SimpleStringProperty();
    private final StringProperty tglRevisi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty jenisRevisi = new SimpleStringProperty();
    
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    
    private final StringProperty kodeKategoriRevisi = new SimpleStringProperty();
    private final StringProperty kodeJenisRevisi = new SimpleStringProperty();
    private final IntegerProperty qtyRevisi = new SimpleIntegerProperty();
    private final DoubleProperty beratRevisi = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenRevisi = new SimpleDoubleProperty();
    private final DoubleProperty nilaiRevisi = new SimpleDoubleProperty();

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public double getNilaiRevisi() {
        return nilaiRevisi.get();
    }

    public void setNilaiRevisi(double value) {
        nilaiRevisi.set(value);
    }

    public DoubleProperty nilaiRevisiProperty() {
        return nilaiRevisi;
    }

    public double getBeratPersen() {
        return beratPersen.get();
    }

    public void setBeratPersen(double value) {
        beratPersen.set(value);
    }

    public DoubleProperty beratPersenProperty() {
        return beratPersen;
    }

    public String getKodeKategoriRevisi() {
        return kodeKategoriRevisi.get();
    }

    public void setKodeKategoriRevisi(String value) {
        kodeKategoriRevisi.set(value);
    }

    public StringProperty kodeKategoriRevisiProperty() {
        return kodeKategoriRevisi;
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

    public double getBeratPersenRevisi() {
        return beratPersenRevisi.get();
    }

    public void setBeratPersenRevisi(double value) {
        beratPersenRevisi.set(value);
    }

    public DoubleProperty beratPersenRevisiProperty() {
        return beratPersenRevisi;
    }

    public double getBeratRevisi() {
        return beratRevisi.get();
    }

    public void setBeratRevisi(double value) {
        beratRevisi.set(value);
    }

    public DoubleProperty beratRevisiProperty() {
        return beratRevisi;
    }

    public int getQtyRevisi() {
        return qtyRevisi.get();
    }

    public void setQtyRevisi(int value) {
        qtyRevisi.set(value);
    }

    public IntegerProperty qtyRevisiProperty() {
        return qtyRevisi;
    }

    public String getKodeJenisRevisi() {
        return kodeJenisRevisi.get();
    }

    public void setKodeJenisRevisi(String value) {
        kodeJenisRevisi.set(value);
    }

    public StringProperty kodeJenisRevisiProperty() {
        return kodeJenisRevisi;
    }
    
    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }

    public String getJenisRevisi() {
        return jenisRevisi.get();
    }

    public void setJenisRevisi(String value) {
        jenisRevisi.set(value);
    }

    public StringProperty jenisRevisiProperty() {
        return jenisRevisi;
    }

    public String getTglRevisi() {
        return tglRevisi.get();
    }

    public void setTglRevisi(String value) {
        tglRevisi.set(value);
    }

    public StringProperty tglRevisiProperty() {
        return tglRevisi;
    }

    public String getNoRevisi() {
        return noRevisi.get();
    }

    public void setNoRevisi(String value) {
        noRevisi.set(value);
    }

    public StringProperty noRevisiProperty() {
        return noRevisi;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
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

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
    }

    
}
