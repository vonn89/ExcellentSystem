/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

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
    //tt_hutang
    private final StringProperty noHutang = new SimpleStringProperty();
    private final StringProperty tglHutang = new SimpleStringProperty();
    private final StringProperty supplier = new SimpleStringProperty();
    private final StringProperty noPembelian = new SimpleStringProperty();
    private final DoubleProperty kurs = new SimpleDoubleProperty();
    private final DoubleProperty jumlahHutang = new SimpleDoubleProperty();
    private final DoubleProperty terbayar = new SimpleDoubleProperty();
    private final DoubleProperty sisaHutang = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<PembayaranHutang> listPembayaranHutang;

    public List<PembayaranHutang> getListPembayaranHutang() {
        return listPembayaranHutang;
    }

    public void setListPembayaranHutang(List<PembayaranHutang> listPembayaranHutang) {
        this.listPembayaranHutang = listPembayaranHutang;
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

    public double getJumlahHutang() {
        return jumlahHutang.get();
    }

    public void setJumlahHutang(double value) {
        jumlahHutang.set(value);
    }

    public DoubleProperty jumlahHutangProperty() {
        return jumlahHutang;
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


    public double getSisaHutang() {
        return sisaHutang.get();
    }

    public void setSisaHutang(double value) {
        sisaHutang.set(value);
    }

    public DoubleProperty sisaHutangProperty() {
        return sisaHutang;
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




    public String getNoPembelian() {
        return noPembelian.get();
    }

    public void setNoPembelian(String value) {
        noPembelian.set(value);
    }

    public StringProperty noPembelianProperty() {
        return noPembelian;
    }

    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String value) {
        supplier.set(value);
    }

    public StringProperty supplierProperty() {
        return supplier;
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
}
