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
public class ReturHead {

    private final StringProperty noRetur = new SimpleStringProperty();
    private final StringProperty tglRetur = new SimpleStringProperty();
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty penerima = new SimpleStringProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private PenjualanHead penjualanHead;
    private List<ReturDetail> allDetail;

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public List<ReturDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<ReturDetail> allDetail) {
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

    public String getPenerima() {
        return penerima.get();
    }

    public void setPenerima(String value) {
        penerima.set(value);
    }

    public StringProperty penerimaProperty() {
        return penerima;
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


    public String getTglRetur() {
        return tglRetur.get();
    }

    public void setTglRetur(String value) {
        tglRetur.set(value);
    }

    public StringProperty tglReturProperty() {
        return tglRetur;
    }

    public String getNoRetur() {
        return noRetur.get();
    }

    public void setNoRetur(String value) {
        noRetur.set(value);
    }

    public StringProperty noReturProperty() {
        return noRetur;
    }
    
}
