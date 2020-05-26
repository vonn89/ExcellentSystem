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
public class SKLDetail {
    
    private final StringProperty noSKL = new SimpleStringProperty();
    private final StringProperty tahap = new SimpleStringProperty();
    private final StringProperty tglBayar = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getTglBayar() {
        return tglBayar.get();
    }

    public void setTglBayar(String value) {
        tglBayar.set(value);
    }

    public StringProperty tglBayarProperty() {
        return tglBayar;
    }

    public String getTahap() {
        return tahap.get();
    }

    public void setTahap(String value) {
        tahap.set(value);
    }

    public StringProperty tahapProperty() {
        return tahap;
    }

    public String getNoSKL() {
        return noSKL.get();
    }

    public void setNoSKL(String value) {
        noSKL.set(value);
    }

    public StringProperty noSKLProperty() {
        return noSKL;
    }
}
