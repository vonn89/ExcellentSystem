/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class RAPDetail {

    private final StringProperty noRap = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty pekerjaan = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final DoubleProperty volume = new SimpleDoubleProperty();
    private final DoubleProperty hargaSatuan = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private RAPHead rapHead;

    public RAPHead getRapHead() {
        return rapHead;
    }

    public void setRapHead(RAPHead rapHead) {
        this.rapHead = rapHead;
    }
    
    public double getTotal() {
        return total.get();
    }

    public void setTotal(double value) {
        total.set(value);
    }

    public DoubleProperty totalProperty() {
        return total;
    }

    public double getHargaSatuan() {
        return hargaSatuan.get();
    }

    public void setHargaSatuan(double value) {
        hargaSatuan.set(value);
    }

    public DoubleProperty hargaSatuanProperty() {
        return hargaSatuan;
    }

    public double getVolume() {
        return volume.get();
    }

    public void setVolume(double value) {
        volume.set(value);
    }

    public DoubleProperty volumeProperty() {
        return volume;
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

    public String getPekerjaan() {
        return pekerjaan.get();
    }

    public void setPekerjaan(String value) {
        pekerjaan.set(value);
    }

    public StringProperty pekerjaanProperty() {
        return pekerjaan;
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

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }

    public String getNoRap() {
        return noRap.get();
    }

    public void setNoRap(String value) {
        noRap.set(value);
    }

    public StringProperty noRapProperty() {
        return noRap;
    }
    
}
