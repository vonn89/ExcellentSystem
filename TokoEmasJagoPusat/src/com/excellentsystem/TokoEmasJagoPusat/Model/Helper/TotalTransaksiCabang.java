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
public class TotalTransaksiCabang {

    private final StringProperty tanggal = new SimpleStringProperty();
    
    private final DoubleProperty penjualan = new SimpleDoubleProperty();
    private final DoubleProperty pembelian = new SimpleDoubleProperty();
    private final DoubleProperty setorPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty mintaUangBankPenjualan = new SimpleDoubleProperty();
    
    private final DoubleProperty pembelianPusat = new SimpleDoubleProperty();
    
    private final DoubleProperty terimaHutang = new SimpleDoubleProperty();
    private final DoubleProperty hutangLunas = new SimpleDoubleProperty();
    private final DoubleProperty hutangBunga = new SimpleDoubleProperty();
    private final DoubleProperty setorRR = new SimpleDoubleProperty();
    private final DoubleProperty mintaUangBankRR = new SimpleDoubleProperty();

    public double getMintaUangBankPenjualan() {
        return mintaUangBankPenjualan.get();
    }

    public void setMintaUangBankPenjualan(double value) {
        mintaUangBankPenjualan.set(value);
    }

    public DoubleProperty mintaUangBankPenjualanProperty() {
        return mintaUangBankPenjualan;
    }

    public double getMintaUangBankRR() {
        return mintaUangBankRR.get();
    }

    public void setMintaUangBankRR(double value) {
        mintaUangBankRR.set(value);
    }

    public DoubleProperty mintaUangBankRRProperty() {
        return mintaUangBankRR;
    }

    public double getSetorRR() {
        return setorRR.get();
    }

    public void setSetorRR(double value) {
        setorRR.set(value);
    }

    public DoubleProperty setorRRProperty() {
        return setorRR;
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

    public double getPembelianPusat() {
        return pembelianPusat.get();
    }

    public void setPembelianPusat(double value) {
        pembelianPusat.set(value);
    }

    public DoubleProperty pembelianPusatProperty() {
        return pembelianPusat;
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

    public double getSetorPenjualan() {
        return setorPenjualan.get();
    }

    public void setSetorPenjualan(double value) {
        setorPenjualan.set(value);
    }

    public DoubleProperty setorPenjualanProperty() {
        return setorPenjualan;
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

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }
    
}
