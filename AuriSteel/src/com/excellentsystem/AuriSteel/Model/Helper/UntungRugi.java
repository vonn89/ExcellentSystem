/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model.Helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class UntungRugi {

    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty jumlahDetail = new SimpleStringProperty();
    private final StringProperty jumlahTotal = new SimpleStringProperty();

    public UntungRugi(String keterangan, String jumlahDetail, String jumlahTotal) {
        this.keterangan.set(keterangan);
        this.jumlahDetail.set(jumlahDetail);
        this.jumlahTotal.set(jumlahTotal);
    }

    public String getJumlahTotal() {
        return jumlahTotal.get();
    }

    public void setJumlahTotal(String value) {
        jumlahTotal.set(value);
    }

    public StringProperty jumlahTotalProperty() {
        return jumlahTotal;
    }

    public String getJumlahDetail() {
        return jumlahDetail.get();
    }

    public void setJumlahDetail(String value) {
        jumlahDetail.set(value);
    }

    public StringProperty jumlahDetailProperty() {
        return jumlahDetail;
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


}
