/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class TempTransaksiCabang {

    private final StringProperty tanggal = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final IntegerProperty qtyPenjualan = new SimpleIntegerProperty();
    private final DoubleProperty totalPenjualan = new SimpleDoubleProperty();
    private final IntegerProperty qtyPembelian = new SimpleIntegerProperty();
    private final DoubleProperty totalPembelian = new SimpleDoubleProperty();
    private final IntegerProperty qtyTerimaHutang = new SimpleIntegerProperty();
    private final DoubleProperty totalTerimaHutang = new SimpleDoubleProperty();
    private final IntegerProperty qtyHutangLunas = new SimpleIntegerProperty();
    private final DoubleProperty totalHutangLunas = new SimpleDoubleProperty();
    private final DoubleProperty totalHutangBunga = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglAmbil = new SimpleStringProperty();


    public String getTglAmbil() {
        return tglAmbil.get();
    }

    public void setTglAmbil(String value) {
        tglAmbil.set(value);
    }

    public StringProperty tglAmbilProperty() {
        return tglAmbil;
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

    public double getTotalHutangBunga() {
        return totalHutangBunga.get();
    }

    public void setTotalHutangBunga(double value) {
        totalHutangBunga.set(value);
    }

    public DoubleProperty totalHutangBungaProperty() {
        return totalHutangBunga;
    }

    public double getTotalHutangLunas() {
        return totalHutangLunas.get();
    }

    public void setTotalHutangLunas(double value) {
        totalHutangLunas.set(value);
    }

    public DoubleProperty totalHutangLunasProperty() {
        return totalHutangLunas;
    }

    public int getQtyHutangLunas() {
        return qtyHutangLunas.get();
    }

    public void setQtyHutangLunas(int value) {
        qtyHutangLunas.set(value);
    }

    public IntegerProperty qtyHutangLunasProperty() {
        return qtyHutangLunas;
    }

    public double getTotalTerimaHutang() {
        return totalTerimaHutang.get();
    }

    public void setTotalTerimaHutang(double value) {
        totalTerimaHutang.set(value);
    }

    public DoubleProperty totalTerimaHutangProperty() {
        return totalTerimaHutang;
    }

    public int getQtyTerimaHutang() {
        return qtyTerimaHutang.get();
    }

    public void setQtyTerimaHutang(int value) {
        qtyTerimaHutang.set(value);
    }

    public IntegerProperty qtyTerimaHutangProperty() {
        return qtyTerimaHutang;
    }

    public double getTotalPembelian() {
        return totalPembelian.get();
    }

    public void setTotalPembelian(double value) {
        totalPembelian.set(value);
    }

    public DoubleProperty totalPembelianProperty() {
        return totalPembelian;
    }

    public int getQtyPembelian() {
        return qtyPembelian.get();
    }

    public void setQtyPembelian(int value) {
        qtyPembelian.set(value);
    }

    public IntegerProperty qtyPembelianProperty() {
        return qtyPembelian;
    }

    public double getTotalPenjualan() {
        return totalPenjualan.get();
    }

    public void setTotalPenjualan(double value) {
        totalPenjualan.set(value);
    }

    public DoubleProperty totalPenjualanProperty() {
        return totalPenjualan;
    }

    public int getQtyPenjualan() {
        return qtyPenjualan.get();
    }

    public void setQtyPenjualan(int value) {
        qtyPenjualan.set(value);
    }

    public IntegerProperty qtyPenjualanProperty() {
        return qtyPenjualan;
    }

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
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
