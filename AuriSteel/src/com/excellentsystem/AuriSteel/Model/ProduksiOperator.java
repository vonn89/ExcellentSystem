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
    private final StringProperty kodePegawai = new SimpleStringProperty();
    private Pegawai pegawai;

    public Pegawai getPegawai() {
        return pegawai;
    }

    public void setPegawai(Pegawai pegawai) {
        this.pegawai = pegawai;
    }
    
    public String getKodePegawai() {
        return kodePegawai.get();
    }

    public void setKodePegawai(String value) {
        kodePegawai.set(value);
    }

    public StringProperty kodePegawaiProperty() {
        return kodePegawai;
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
