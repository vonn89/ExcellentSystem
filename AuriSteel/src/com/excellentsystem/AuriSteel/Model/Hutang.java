/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Hutang {
    
    private final StringProperty noHutang = new SimpleStringProperty();
    private final StringProperty tglHutang = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final DoubleProperty jumlahHutang = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaHutang = new SimpleDoubleProperty();
    private final StringProperty jatuhTempo = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private PembelianBahanHead pembelianBahanHead;
    private PembelianBarangHead pembelianBarangHead;
    private PemesananBarangHead pemesananHead;
    private PemesananBahanHead pemesananBahanHead;
    private List<Pembayaran> listPembayaran;


    public PembelianBarangHead getPembelianBarangHead() {
        return pembelianBarangHead;
    }

    public void setPembelianBarangHead(PembelianBarangHead pembelianBarangHead) {
        this.pembelianBarangHead = pembelianBarangHead;
    }

    public PembelianBahanHead getPembelianBahanHead() {
        return pembelianBahanHead;
    }

    public void setPembelianBahanHead(PembelianBahanHead pembelianBahanHead) {
        this.pembelianBahanHead = pembelianBahanHead;
    }

    public PemesananBahanHead getPemesananBahanHead() {
        return pemesananBahanHead;
    }

    public void setPemesananBahanHead(PemesananBahanHead pemesananBahanHead) {
        this.pemesananBahanHead = pemesananBahanHead;
    }
    
    public PemesananBarangHead getPemesananHead() {
        return pemesananHead;
    }

    public void setPemesananHead(PemesananBarangHead pemesananHead) {
        this.pemesananHead = pemesananHead;
    }

    public List<Pembayaran> getListPembayaran() {
        return listPembayaran;
    }

    public void setListPembayaran(List<Pembayaran> listPembayaran) {
        this.listPembayaran = listPembayaran;
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

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }


    public String getJatuhTempo() {
        return jatuhTempo.get();
    }

    public void setJatuhTempo(String value) {
        jatuhTempo.set(value);
    }

    public StringProperty jatuhTempoProperty() {
        return jatuhTempo;
    }
    

    public String getTglHutang() {
        return tglHutang.get();
    }

    public void setTglHutang(String value) {
        tglHutang.set(value);
    }

    public StringProperty tglHutangProperty() {
        return tglHutang;
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

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public double getJumlahHutang() {
        return jumlahHutang.get();
    }

    public void setJumlahHutang(double value) {
        jumlahHutang.set(value);
    }

    public DoubleProperty jumlahHutangProperty() {
        return jumlahHutang;
    }

    public double getPembayaran() {
        return pembayaran.get();
    }

    public void setPembayaran(double value) {
        pembayaran.set(value);
    }

    public DoubleProperty pembayaranProperty() {
        return pembayaran;
    }

    public double getSisaHutang() {
        return sisaHutang.get();
    }

    public void setSisaHutang(double value) {
        sisaHutang.set(value);
    }

    public DoubleProperty sisaHutangProperty() {
        return sisaHutang;
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
    

    public String getNoHutang() {
        return noHutang.get();
    }

    public void setNoHutang(String value) {
        noHutang.set(value);
    }

    public StringProperty noHutangProperty() {
        return noHutang;
    }
    
}
