/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class ProduksiOperator {

    private final StringProperty kodeProduksi = new SimpleStringProperty();
    private final StringProperty kodeOperator = new SimpleStringProperty();
    private final StringProperty namaOperator = new SimpleStringProperty();
    private Pegawai pegawai;

    public String getKodeOperator() {
        return kodeOperator.get();
    }

    public void setKodeOperator(String value) {
        kodeOperator.set(value);
    }

    public StringProperty kodeOperatorProperty() {
        return kodeOperator;
    }

    public String getNamaOperator() {
        return namaOperator.get();
    }

    public void setNamaOperator(String value) {
        namaOperator.set(value);
    }

    public StringProperty namaOperatorProperty() {
        return namaOperator;
    }

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }
    

    public String getKodeProduksi() {
        return kodeProduksi.get();
    }

    public void setKodeProduksi(String value) {
        kodeProduksi.set(value);
    }

    public StringProperty kodeProduksiProperty() {
        return kodeProduksi;
    }
    
}
