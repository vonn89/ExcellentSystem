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
public class SKLManualHead {
    private final StringProperty noSKL = new SimpleStringProperty();
    private final StringProperty tglSKL = new SimpleStringProperty();
    private final DoubleProperty totalPembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPelunasan = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private List<SKLManualDetail> allDetail;
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty namaProperty = new SimpleStringProperty();
    private final StringProperty tipeUnit = new SimpleStringProperty();
    private final DoubleProperty luasTanah = new SimpleDoubleProperty();
    private final DoubleProperty harga = new SimpleDoubleProperty();
    private final StringProperty namaCustomer = new SimpleStringProperty();
    private final StringProperty jenisKelamin = new SimpleStringProperty();
    private final StringProperty tglDibuat = new SimpleStringProperty();

    public String getTglDibuat() {
        return tglDibuat.get();
    }

    public void setTglDibuat(String value) {
        tglDibuat.set(value);
    }

    public StringProperty tglDibuatProperty() {
        return tglDibuat;
    }

    public String getJenisKelamin() {
        return jenisKelamin.get();
    }

    public void setJenisKelamin(String value) {
        jenisKelamin.set(value);
    }

    public StringProperty jenisKelaminProperty() {
        return jenisKelamin;
    }

    public String getNamaCustomer() {
        return namaCustomer.get();
    }

    public void setNamaCustomer(String value) {
        namaCustomer.set(value);
    }

    public StringProperty namaCustomerProperty() {
        return namaCustomer;
    }

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double value) {
        harga.set(value);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }

    public double getLuasTanah() {
        return luasTanah.get();
    }

    public void setLuasTanah(double value) {
        luasTanah.set(value);
    }

    public DoubleProperty luasTanahProperty() {
        return luasTanah;
    }

    public String getTipeUnit() {
        return tipeUnit.get();
    }

    public void setTipeUnit(String value) {
        tipeUnit.set(value);
    }

    public StringProperty tipeUnitProperty() {
        return tipeUnit;
    }

    public String getNamaProperty() {
        return namaProperty.get();
    }

    public void setNamaProperty(String value) {
        namaProperty.set(value);
    }

    public StringProperty namaPropertyProperty() {
        return namaProperty;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public List<SKLManualDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<SKLManualDetail> allDetail) {
        this.allDetail = allDetail;
    }

    public double getTotalPembayaran() {
        return totalPembayaran.get();
    }

    public void setTotalPembayaran(double value) {
        totalPembayaran.set(value);
    }

    public DoubleProperty totalPembayaranProperty() {
        return totalPembayaran;
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

    public double getSisaPelunasan() {
        return sisaPelunasan.get();
    }

    public void setSisaPelunasan(double value) {
        sisaPelunasan.set(value);
    }

    public DoubleProperty sisaPelunasanProperty() {
        return sisaPelunasan;
    }


    public String getTglSKL() {
        return tglSKL.get();
    }

    public void setTglSKL(String value) {
        tglSKL.set(value);
    }

    public StringProperty tglSKLProperty() {
        return tglSKL;
    }

    public String getNoSKL() {
        return noSKL.get();
    }

    public void setNoSKL(String value) {
        noSKL.set(value);
    }

    public StringProperty noSKLProperty() {
        return noSKL;
    }
    
}
