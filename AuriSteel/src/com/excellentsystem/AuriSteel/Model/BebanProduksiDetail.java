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
 * @author ASUS
 */
public class BebanProduksiDetail {

    private final StringProperty noBebanProduksi = new SimpleStringProperty();
    private final StringProperty noProduksi = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private ProduksiHead produksiHead;
    private BebanProduksiHead bebanProduksiHead;

    public BebanProduksiHead getBebanProduksiHead() {
        return bebanProduksiHead;
    }

    public void setBebanProduksiHead(BebanProduksiHead bebanProduksiHead) {
        this.bebanProduksiHead = bebanProduksiHead;
    }
    
    public ProduksiHead getProduksiHead() {
        return produksiHead;
    }

    public void setProduksiHead(ProduksiHead produksiHead) {
        this.produksiHead = produksiHead;
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

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public String getNoProduksi() {
        return noProduksi.get();
    }

    public void setNoProduksi(String value) {
        noProduksi.set(value);
    }

    public StringProperty noProduksiProperty() {
        return noProduksi;
    }

    public String getNoBebanProduksi() {
        return noBebanProduksi.get();
    }

    public void setNoBebanProduksi(String value) {
        noBebanProduksi.set(value);
    }

    public StringProperty noBebanProduksiProperty() {
        return noBebanProduksi;
    }
    
}
