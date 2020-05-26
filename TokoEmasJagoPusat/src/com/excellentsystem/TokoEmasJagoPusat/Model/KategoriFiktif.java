/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class KategoriFiktif {

    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final DoubleProperty hargaJualMin = new SimpleDoubleProperty();
    private final DoubleProperty hargaJualMax = new SimpleDoubleProperty();
    private List<BarangFiktif> listBarang;

    public List<BarangFiktif> getListBarang() {
        return listBarang;
    }

    public void setListBarang(List<BarangFiktif> listBarang) {
        this.listBarang = listBarang;
    }
    
    
    public double getHargaJualMax() {
        return hargaJualMax.get();
    }

    public void setHargaJualMax(double value) {
        hargaJualMax.set(value);
    }

    public DoubleProperty hargaJualMaxProperty() {
        return hargaJualMax;
    }

    public double getHargaJualMin() {
        return hargaJualMin.get();
    }

    public void setHargaJualMin(double value) {
        hargaJualMin.set(value);
    }

    public DoubleProperty hargaJualMinProperty() {
        return hargaJualMin;
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
    
}
