/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model.Helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class TransaksiCabang {

    private final StringProperty transaksi = new SimpleStringProperty();
    private final StringProperty noTransaksi = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
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

    public String getTransaksi() {
        return transaksi.get();
    }

    public void setTransaksi(String value) {
        transaksi.set(value);
    }

    public StringProperty transaksiProperty() {
        return transaksi;
    }
    
}
