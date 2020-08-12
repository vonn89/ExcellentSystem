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
public class PengirimanDetail {

    private final StringProperty noPengiriman = new SimpleStringProperty();
    private final StringProperty noUrutPenjualan = new SimpleStringProperty();
    private final StringProperty kodeBarang = new SimpleStringProperty();
    private final StringProperty namaBarang = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty nilai = new SimpleDoubleProperty();
    private PenjualanDetail penjualanDetail;
    private Barang barang;
    private PengirimanHead pengirimanHead;

    public PengirimanHead getPengirimanHead() {
        return pengirimanHead;
    }

    public void setPengirimanHead(PengirimanHead pengirimanHead) {
        this.pengirimanHead = pengirimanHead;
    }
    
    public Barang getBarang() {
        return barang;
    }

    public void setBarang(Barang barang) {
        this.barang = barang;
    }
    
    public PenjualanDetail getPenjualanDetail() {
        return penjualanDetail;
    }

    public void setPenjualanDetail(PenjualanDetail penjualanDetail) {
        this.penjualanDetail = penjualanDetail;
    }

    public String getNoUrutPenjualan() {
        return noUrutPenjualan.get();
    }

    public void setNoUrutPenjualan(String value) {
        noUrutPenjualan.set(value);
    }

    public StringProperty noUrutPenjualanProperty() {
        return noUrutPenjualan;
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


    public String getNoPengiriman() {
        return noPengiriman.get();
    }

    public void setNoPengiriman(String value) {
        noPengiriman.set(value);
    }

    public StringProperty noPengirimanProperty() {
        return noPengiriman;
    }
    
}
