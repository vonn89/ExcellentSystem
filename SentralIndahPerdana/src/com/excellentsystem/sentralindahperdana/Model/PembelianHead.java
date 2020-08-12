/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class PembelianHead {

    private final StringProperty noPembelian = new SimpleStringProperty();
    private final StringProperty tglPembelian = new SimpleStringProperty();
    private final StringProperty kodeSupplier = new SimpleStringProperty();
    private final DoubleProperty totalPembelian = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPembayaran = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<PembelianDetail> allDetail;
    private Supplier supplier;
    private Hutang hutang;


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

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public double getSisaPembayaran() {
        return sisaPembayaran.get();
    }

    public void setSisaPembayaran(double value) {
        sisaPembayaran.set(value);
    }

    public DoubleProperty sisaPembayaranProperty() {
        return sisaPembayaran;
    }

    public double getPembayaran() {
        return pembayaran.get();
    }

    public void setPembayaran(double value) {
        pembayaran.set(value);
    }

    public DoubleProperty pembayaranProperty() {
        return pembayaran;
    }

    public double getTotalPembelian() {
        return totalPembelian.get();
    }

    public void setTotalPembelian(double value) {
        totalPembelian.set(value);
    }

    public DoubleProperty totalPembelianProperty() {
        return totalPembelian;
    }

    public String getKodeSupplier() {
        return kodeSupplier.get();
    }

    public void setKodeSupplier(String value) {
        kodeSupplier.set(value);
    }

    public StringProperty kodeSupplierProperty() {
        return kodeSupplier;
    }

    public String getTglPembelian() {
        return tglPembelian.get();
    }

    public void setTglPembelian(String value) {
        tglPembelian.set(value);
    }

    public StringProperty tglPembelianProperty() {
        return tglPembelian;
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

    public List<PembelianDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PembelianDetail> allDetail) {
        this.allDetail = allDetail;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Hutang getHutang() {
        return hutang;
    }

    public void setHutang(Hutang hutang) {
        this.hutang = hutang;
    }
    
}
