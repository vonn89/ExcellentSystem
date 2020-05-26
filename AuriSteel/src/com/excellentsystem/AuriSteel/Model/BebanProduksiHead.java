/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class BebanProduksiHead {

    private final StringProperty noBebanProduksi = new SimpleStringProperty();
    private final StringProperty tglBebanProduksi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty totalBebanProduksi = new SimpleDoubleProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<BebanProduksiDetail> listBebanProduksiDetail;

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }

    public List<BebanProduksiDetail> getListBebanProduksiDetail() {
        return listBebanProduksiDetail;
    }

    public void setListBebanProduksiDetail(List<BebanProduksiDetail> listBebanProduksiDetail) {
        this.listBebanProduksiDetail = listBebanProduksiDetail;
    }
    
    public double getTotalBebanProduksi() {
        return totalBebanProduksi.get();
    }

    public void setTotalBebanProduksi(double value) {
        totalBebanProduksi.set(value);
    }

    public DoubleProperty totalBebanProduksiProperty() {
        return totalBebanProduksi;
    }

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getTglBebanProduksi() {
        return tglBebanProduksi.get();
    }

    public void setTglBebanProduksi(String value) {
        tglBebanProduksi.set(value);
    }

    public StringProperty tglBebanProduksiProperty() {
        return tglBebanProduksi;
    }

    public String getNoBebanProduksi() {
        return noBebanProduksi.get();
    }

    public void setNoBebanProduksi(String value) {
        noBebanProduksi.set(value);
    }

    public StringProperty noBebanProduksiProperty() {
        return noBebanProduksi;
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
