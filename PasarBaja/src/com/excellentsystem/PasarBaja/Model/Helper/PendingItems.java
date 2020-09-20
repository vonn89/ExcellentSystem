/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.PasarBaja.Model.Helper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class PendingItems {

    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final DoubleProperty persenQty = new SimpleDoubleProperty();
    private final DoubleProperty persenBerat = new SimpleDoubleProperty();
    private final DoubleProperty persenJumlahRp = new SimpleDoubleProperty();

    public double getPersenJumlahRp() {
        return persenJumlahRp.get();
    }

    public void setPersenJumlahRp(double value) {
        persenJumlahRp.set(value);
    }

    public DoubleProperty persenJumlahRpProperty() {
        return persenJumlahRp;
    }

    public double getPersenBerat() {
        return persenBerat.get();
    }

    public void setPersenBerat(double value) {
        persenBerat.set(value);
    }

    public DoubleProperty persenBeratProperty() {
        return persenBerat;
    }

    public double getPersenQty() {
        return persenQty.get();
    }

    public void setPersenQty(double value) {
        persenQty.set(value);
    }

    public DoubleProperty persenQtyProperty() {
        return persenQty;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
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

    public double getQty() {
        return qty.get();
    }

    public void setQty(double value) {
        qty.set(value);
    }

    public DoubleProperty qtyProperty() {
        return qty;
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
