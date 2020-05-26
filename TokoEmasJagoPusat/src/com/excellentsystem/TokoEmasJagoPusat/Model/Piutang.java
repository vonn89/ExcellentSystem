/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Piutang {
    //tt_piutang
    private final StringProperty noPiutang = new SimpleStringProperty();
    private final StringProperty tglPiutang = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty tipeKasir = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty noTransaksi = new SimpleStringProperty();
    private final DoubleProperty kurs = new SimpleDoubleProperty();
    private final DoubleProperty jumlahPiutang = new SimpleDoubleProperty();
    private final DoubleProperty terbayar = new SimpleDoubleProperty();
    private final DoubleProperty sisaPiutang = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public double getKurs() {
        return kurs.get();
    }

    public void setKurs(double value) {
        kurs.set(value);
    }

    public DoubleProperty kursProperty() {
        return kurs;
    }

    public String getNoTransaksi() {
        return noTransaksi.get();
    }

    public void setNoTransaksi(String value) {
        noTransaksi.set(value);
    }

    public StringProperty noTransaksiProperty() {
        return noTransaksi;
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

    public String getTipeKasir() {
        return tipeKasir.get();
    }

    public void setTipeKasir(String value) {
        tipeKasir.set(value);
    }

    public StringProperty tipeKasirProperty() {
        return tipeKasir;
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

    public double getSisaPiutang() {
        return sisaPiutang.get();
    }

    public void setSisaPiutang(double value) {
        sisaPiutang.set(value);
    }

    public DoubleProperty sisaPiutangProperty() {
        return sisaPiutang;
    }

    public double getTerbayar() {
        return terbayar.get();
    }

    public void setTerbayar(double value) {
        terbayar.set(value);
    }

    public DoubleProperty terbayarProperty() {
        return terbayar;
    }



    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
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
}
