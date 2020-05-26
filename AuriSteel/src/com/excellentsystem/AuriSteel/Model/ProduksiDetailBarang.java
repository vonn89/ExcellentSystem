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
public class ProduksiDetailBarang {
    private final StringProperty kodeProduksi = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty nilai = new SimpleDoubleProperty();
    private ProduksiHead produksiHead;
    private Barang barang;

    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public ProduksiHead getProduksiHead() {
        return produksiHead;
    }

    public void setProduksiHead(ProduksiHead produksiHead) {
        this.produksiHead = produksiHead;
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
    

    public double getNilai() {
        return nilai.get();
    }

    public void setNilai(double value) {
        nilai.set(value);
    }

    public DoubleProperty nilaiProperty() {
        return nilai;
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
    

    public String getKodeProduksi() {
        return kodeProduksi.get();
    }

    public void setKodeProduksi(String value) {
        kodeProduksi.set(value);
    }

    public StringProperty kodeProduksiProperty() {
        return kodeProduksi;
    }
    
}
