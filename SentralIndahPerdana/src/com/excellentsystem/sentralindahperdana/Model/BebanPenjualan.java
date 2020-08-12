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
public class BebanPenjualan {

    private final StringProperty noBebanPenjualan = new SimpleStringProperty();
    private final StringProperty tglBebanPenjualan = new SimpleStringProperty();
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty noUrut = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private PenjualanDetail penjualanDetail;


    public PenjualanDetail getPenjualanDetail() {
        return penjualanDetail;
    }

    public void setPenjualanDetail(PenjualanDetail penjualanDetail) {
        this.penjualanDetail = penjualanDetail;
    }
    
    public String getTglBebanPenjualan() {
        return tglBebanPenjualan.get();
    }

    public void setTglBebanPenjualan(String value) {
        tglBebanPenjualan.set(value);
    }

    public StringProperty tglBebanPenjualanProperty() {
        return tglBebanPenjualan;
    }

    public String getNoBebanPenjualan() {
        return noBebanPenjualan.get();
    }

    public void setNoBebanPenjualan(String value) {
        noBebanPenjualan.set(value);
    }

    public StringProperty noBebanPenjualanProperty() {
        return noBebanPenjualan;
    }

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
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

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
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

    public String getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(String value) {
        noUrut.set(value);
    }

    public StringProperty noUrutProperty() {
        return noUrut;
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
    
}
