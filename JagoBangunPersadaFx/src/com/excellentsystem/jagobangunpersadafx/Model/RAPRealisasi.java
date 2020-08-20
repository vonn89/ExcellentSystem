/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

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
public class RAPRealisasi {

    private final StringProperty noRap = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty tglRealisasi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty satuan = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final IntegerProperty totalImage = new SimpleIntegerProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private RAPHead rapHead;

    public int getTotalImage() {
        return totalImage.get();
    }

    public void setTotalImage(int value) {
        totalImage.set(value);
    }

    public IntegerProperty totalImageProperty() {
        return totalImage;
    }

    public RAPHead getRapHead() {
        return rapHead;
    }

    public void setRapHead(RAPHead rapHead) {
        this.rapHead = rapHead;
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

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }

    public String getSatuan() {
        return satuan.get();
    }

    public void setSatuan(String value) {
        satuan.set(value);
    }

    public StringProperty satuanProperty() {
        return satuan;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }

    public double getQty() {
        return qty.get();
    }

    public void setQty(double value) {
        qty.set(value);
    }

    public DoubleProperty qtyProperty() {
        return qty;
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

    public String getTglRealisasi() {
        return tglRealisasi.get();
    }

    public void setTglRealisasi(String value) {
        tglRealisasi.set(value);
    }

    public StringProperty tglRealisasiProperty() {
        return tglRealisasi;
    }

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }

    public String getNoRap() {
        return noRap.get();
    }

    public void setNoRap(String value) {
        noRap.set(value);
    }

    public StringProperty noRapProperty() {
        return noRap;
    }
    
}
