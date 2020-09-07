/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model.Helper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class OmzetCabang {

    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final DoubleProperty penjualan = new SimpleDoubleProperty();
    private final DoubleProperty pembelian = new SimpleDoubleProperty();
    private final DoubleProperty terimaHutang = new SimpleDoubleProperty();
    private final DoubleProperty hutangLunas = new SimpleDoubleProperty();
    private final DoubleProperty hutangBunga = new SimpleDoubleProperty();
    private final DoubleProperty saldoAkhirKasPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty saldoAkhirKasRR = new SimpleDoubleProperty();

    public double getSaldoAkhirKasRR() {
        return saldoAkhirKasRR.get();
    }

    public void setSaldoAkhirKasRR(double value) {
        saldoAkhirKasRR.set(value);
    }

    public DoubleProperty saldoAkhirKasRRProperty() {
        return saldoAkhirKasRR;
    }

    public double getSaldoAkhirKasPenjualan() {
        return saldoAkhirKasPenjualan.get();
    }

    public void setSaldoAkhirKasPenjualan(double value) {
        saldoAkhirKasPenjualan.set(value);
    }

    public DoubleProperty saldoAkhirKasPenjualanProperty() {
        return saldoAkhirKasPenjualan;
    }


    public double getHutangBunga() {
        return hutangBunga.get();
    }

    public void setHutangBunga(double value) {
        hutangBunga.set(value);
    }

    public DoubleProperty hutangBungaProperty() {
        return hutangBunga;
    }

    public double getHutangLunas() {
        return hutangLunas.get();
    }

    public void setHutangLunas(double value) {
        hutangLunas.set(value);
    }

    public DoubleProperty hutangLunasProperty() {
        return hutangLunas;
    }

    public double getTerimaHutang() {
        return terimaHutang.get();
    }

    public void setTerimaHutang(double value) {
        terimaHutang.set(value);
    }

    public DoubleProperty terimaHutangProperty() {
        return terimaHutang;
    }

    public double getPembelian() {
        return pembelian.get();
    }

    public void setPembelian(double value) {
        pembelian.set(value);
    }

    public DoubleProperty pembelianProperty() {
        return pembelian;
    }

    public double getPenjualan() {
        return penjualan.get();
    }

    public void setPenjualan(double value) {
        penjualan.set(value);
    }

    public DoubleProperty penjualanProperty() {
        return penjualan;
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
    
}
