/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PengirimanHead {

    private final StringProperty noPengiriman = new SimpleStringProperty();
    private final StringProperty tglPengiriman = new SimpleStringProperty();
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty jenisKendaraan = new SimpleStringProperty();
    private final StringProperty noPolisi = new SimpleStringProperty();
    private final StringProperty supir = new SimpleStringProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private PenjualanHead penjualanHead;
    private List<PengirimanDetail> allDetail;

    public List<PengirimanDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PengirimanDetail> allDetail) {
        this.allDetail = allDetail;
    }

    
    public PenjualanHead getPenjualanHead() {
        return penjualanHead;
    }

    public void setPenjualanHead(PenjualanHead penjualanHead) {
        this.penjualanHead = penjualanHead;
    }

    public String getNoPenjualan() {
        return noPenjualan.get();
    }

    public void setNoPenjualan(String value) {
        noPenjualan.set(value);
    }

    public StringProperty noPenjualanProperty() {
        return noPenjualan;
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

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public String getSupir() {
        return supir.get();
    }

    public void setSupir(String value) {
        supir.set(value);
    }

    public StringProperty supirProperty() {
        return supir;
    }

    public String getNoPolisi() {
        return noPolisi.get();
    }

    public void setNoPolisi(String value) {
        noPolisi.set(value);
    }

    public StringProperty noPolisiProperty() {
        return noPolisi;
    }

    public String getJenisKendaraan() {
        return jenisKendaraan.get();
    }

    public void setJenisKendaraan(String value) {
        jenisKendaraan.set(value);
    }

    public StringProperty jenisKendaraanProperty() {
        return jenisKendaraan;
    }

    public String getTglPengiriman() {
        return tglPengiriman.get();
    }

    public void setTglPengiriman(String value) {
        tglPengiriman.set(value);
    }

    public StringProperty tglPengirimanProperty() {
        return tglPengiriman;
    }

    public String getNoPengiriman() {
        return noPengiriman.get();
    }

    public void setNoPengiriman(String value) {
        noPengiriman.set(value);
    }

    public StringProperty noPengirimanProperty() {
        return noPengiriman;
    }
    
}
