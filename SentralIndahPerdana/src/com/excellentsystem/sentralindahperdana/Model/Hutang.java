/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class Hutang {

    private final StringProperty noHutang = new SimpleStringProperty();
    private final StringProperty tglHutang = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty kategoriKeuangan = new SimpleStringProperty();
    private final DoubleProperty jumlahHutang = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaHutang = new SimpleDoubleProperty();
    private final StringProperty jatuhTempo = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<PembayaranHutang> allPembayaran;
    private PembelianHead pembelian;
    private PenjualanHead penjualan;
    private Supplier supplier;
    private Customer customer;

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public PenjualanHead getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(PenjualanHead penjualan) {
        this.penjualan = penjualan;
    }
    
    public PembelianHead getPembelian() {
        return pembelian;
    }

    public void setPembelian(PembelianHead pembelian) {
        this.pembelian = pembelian;
    }
    
    
    public String getKategoriKeuangan() {
        return kategoriKeuangan.get();
    }

    public void setKategoriKeuangan(String value) {
        kategoriKeuangan.set(value);
    }

    public StringProperty kategoriKeuanganProperty() {
        return kategoriKeuangan;
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

    public String getJatuhTempo() {
        return jatuhTempo.get();
    }

    public void setJatuhTempo(String value) {
        jatuhTempo.set(value);
    }

    public StringProperty jatuhTempoProperty() {
        return jatuhTempo;
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

    public double getPembayaran() {
        return pembayaran.get();
    }

    public void setPembayaran(double value) {
        pembayaran.set(value);
    }

    public DoubleProperty pembayaranProperty() {
        return pembayaran;
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

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
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

    public String getTglHutang() {
        return tglHutang.get();
    }

    public void setTglHutang(String value) {
        tglHutang.set(value);
    }

    public StringProperty tglHutangProperty() {
        return tglHutang;
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

    public List<PembayaranHutang> getAllPembayaran() {
        return allPembayaran;
    }

    public void setAllPembayaran(List<PembayaranHutang> allPembayaran) {
        this.allPembayaran = allPembayaran;
    }
    
}
