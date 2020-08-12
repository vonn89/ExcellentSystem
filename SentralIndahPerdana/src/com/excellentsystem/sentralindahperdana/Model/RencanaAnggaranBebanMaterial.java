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
public class RencanaAnggaranBebanMaterial {

    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty noUrut = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty hargaBeli = new SimpleDoubleProperty();
    private final DoubleProperty hargaJual = new SimpleDoubleProperty();
    private Barang barang;

    public double getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(double value) {
        hargaJual.set(value);
    }

    public DoubleProperty hargaJualProperty() {
        return hargaJual;
    }

    public double getHargaBeli() {
        return hargaBeli.get();
    }

    public void setHargaBeli(double value) {
        hargaBeli.set(value);
    }

    public DoubleProperty hargaBeliProperty() {
        return hargaBeli;
    }

    public String getNoPenjualan() {
        return noPenjualan.get();
    }

    public void setNoPenjualan(String value) {
        noPenjualan.set(value);
    }

    public StringProperty noPenjualanProperty() {
        return noPenjualan;
    }

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    


    public double getQty() {
        return qty.get();
    }

    public void setQty(double value) {
        qty.set(value);
    }

    public DoubleProperty qtyProperty() {
        return qty;
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

    public String getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(String value) {
        noUrut.set(value);
    }

    public StringProperty noUrutProperty() {
        return noUrut;
    }

    
}
