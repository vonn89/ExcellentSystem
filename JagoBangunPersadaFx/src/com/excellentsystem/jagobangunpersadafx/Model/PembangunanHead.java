/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PembangunanHead {
    private final StringProperty noPembangunan = new SimpleStringProperty();
    private final StringProperty tglPembangunan = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty totalProperty = new SimpleDoubleProperty();
    private final DoubleProperty totalLuasTanah = new SimpleDoubleProperty();
    private final DoubleProperty totalBiaya = new SimpleDoubleProperty();
    private final StringProperty metode = new SimpleStringProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<PembangunanDetail> allDetail;

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getTglPembangunan() {
        return tglPembangunan.get();
    }

    public void setTglPembangunan(String value) {
        tglPembangunan.set(value);
    }

    public StringProperty tglPembangunanProperty() {
        return tglPembangunan;
    }

    public String getNoPembangunan() {
        return noPembangunan.get();
    }

    public void setNoPembangunan(String value) {
        noPembangunan.set(value);
    }

    public StringProperty noPembangunanProperty() {
        return noPembangunan;
    }

    public String getMetode() {
        return metode.get();
    }

    public void setMetode(String value) {
        metode.set(value);
    }

    public StringProperty metodeProperty() {
        return metode;
    }

    public double getTotalProperty() {
        return totalProperty.get();
    }

    public void setTotalProperty(double value) {
        totalProperty.set(value);
    }

    public DoubleProperty totalPropertyProperty() {
        return totalProperty;
    }

    public List<PembangunanDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PembangunanDetail> allDetail) {
        this.allDetail = allDetail;
    }
    
    public double getTotalBiaya() {
        return totalBiaya.get();
    }

    public void setTotalBiaya(double value) {
        totalBiaya.set(value);
    }

    public DoubleProperty totalBiayaProperty() {
        return totalBiaya;
    }

    public double getTotalLuasTanah() {
        return totalLuasTanah.get();
    }

    public void setTotalLuasTanah(double value) {
        totalLuasTanah.set(value);
    }

    public DoubleProperty totalLuasTanahProperty() {
        return totalLuasTanah;
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
