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
public class Piutang {

    private final StringProperty noPiutang = new SimpleStringProperty();
    private final StringProperty tglPiutang = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty kategoriKeuangan = new SimpleStringProperty();
    private final DoubleProperty jumlahPiutang = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPiutang = new SimpleDoubleProperty();
    private final StringProperty jatuhTempo = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<PembayaranPiutang> allPembayaran;
    private Customer customer;
    private PenjualanHead penjualan;

    public PenjualanHead getPenjualan() {
        return penjualan;
    }

    public void setPenjualan(PenjualanHead penjualan) {
        this.penjualan = penjualan;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public double getSisaPiutang() {
        return sisaPiutang.get();
    }

    public void setSisaPiutang(double value) {
        sisaPiutang.set(value);
    }

    public DoubleProperty sisaPiutangProperty() {
        return sisaPiutang;
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

    public double getJumlahPiutang() {
        return jumlahPiutang.get();
    }

    public void setJumlahPiutang(double value) {
        jumlahPiutang.set(value);
    }

    public DoubleProperty jumlahPiutangProperty() {
        return jumlahPiutang;
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

    public String getTglPiutang() {
        return tglPiutang.get();
    }

    public void setTglPiutang(String value) {
        tglPiutang.set(value);
    }

    public StringProperty tglPiutangProperty() {
        return tglPiutang;
    }

    public String getNoPiutang() {
        return noPiutang.get();
    }

    public void setNoPiutang(String value) {
        noPiutang.set(value);
    }

    public StringProperty noPiutangProperty() {
        return noPiutang;
    }

    public List<PembayaranPiutang> getAllPembayaran() {
        return allPembayaran;
    }

    public void setAllPembayaran(List<PembayaranPiutang> allPembayaran) {
        this.allPembayaran = allPembayaran;
    }
    
}
