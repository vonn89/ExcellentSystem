/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Piutang {
    
    
    private final StringProperty noPiutang = new SimpleStringProperty();
    private final StringProperty tglPiutang = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty jumlahPiutang = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPiutang = new SimpleDoubleProperty();
    private final StringProperty jatuhTempo = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<TerimaPembayaran> allTerimaPembayaran;
    private SKLHead sklHead;

    public SKLHead getSklHead() {
        return sklHead;
    }

    public void setSklHead(SKLHead sklHead) {
        this.sklHead = sklHead;
    }
    
    public List<TerimaPembayaran> getAllTerimaPembayaran() {
        return allTerimaPembayaran;
    }

    public void setAllTerimaPembayaran(List<TerimaPembayaran> allTerimaPembayaran) {
        this.allTerimaPembayaran = allTerimaPembayaran;
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
    

    public String getTglPiutang() {
        return tglPiutang.get();
    }

    public void setTglPiutang(String value) {
        tglPiutang.set(value);
    }

    public StringProperty tglPiutangProperty() {
        return tglPiutang;
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
    

    public double getJumlahPiutang() {
        return jumlahPiutang.get();
    }

    public void setJumlahPiutang(double value) {
        jumlahPiutang.set(value);
    }

    public DoubleProperty jumlahPiutangProperty() {
        return jumlahPiutang;
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

    public double getSisaPiutang() {
        return sisaPiutang.get();
    }

    public void setSisaPiutang(double value) {
        sisaPiutang.set(value);
    }

    public DoubleProperty sisaPiutangProperty() {
        return sisaPiutang;
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
    

    public String getNoPiutang() {
        return noPiutang.get();
    }

    public void setNoPiutang(String value) {
        noPiutang.set(value);
    }

    public StringProperty noPiutangProperty() {
        return noPiutang;
    }
    
}
