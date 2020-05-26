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
public class PemecahanPropertyHead {
    private final StringProperty noPemecahan = new SimpleStringProperty();
    private final StringProperty tglPemecahan = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty namaProperty = new SimpleStringProperty();
    private final DoubleProperty totalProperty = new SimpleDoubleProperty();
    private final DoubleProperty totalLuasTanah = new SimpleDoubleProperty();
    private final DoubleProperty totalLuasEfektif = new SimpleDoubleProperty();
    private final DoubleProperty totalLuasTersisa = new SimpleDoubleProperty();
    private final DoubleProperty nilaiProperty = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPropertyPerMeter = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<PemecahanPropertyDetail> allDetail;
    private Property property;

    public double getNilaiPropertyPerMeter() {
        return nilaiPropertyPerMeter.get();
    }

    public void setNilaiPropertyPerMeter(double value) {
        nilaiPropertyPerMeter.set(value);
    }

    public DoubleProperty nilaiPropertyPerMeterProperty() {
        return nilaiPropertyPerMeter;
    }

    public double getNilaiProperty() {
        return nilaiProperty.get();
    }

    public void setNilaiProperty(double value) {
        nilaiProperty.set(value);
    }

    public DoubleProperty nilaiPropertyProperty() {
        return nilaiProperty;
    }


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    public List<PemecahanPropertyDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PemecahanPropertyDetail> allDetail) {
        this.allDetail = allDetail;
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


    public double getTotalLuasTersisa() {
        return totalLuasTersisa.get();
    }

    public void setTotalLuasTersisa(double value) {
        totalLuasTersisa.set(value);
    }

    public DoubleProperty totalLuasTersisaProperty() {
        return totalLuasTersisa;
    }

    public double getTotalLuasEfektif() {
        return totalLuasEfektif.get();
    }

    public void setTotalLuasEfektif(double value) {
        totalLuasEfektif.set(value);
    }

    public DoubleProperty totalLuasEfektifProperty() {
        return totalLuasEfektif;
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

    public double getTotalProperty() {
        return totalProperty.get();
    }

    public void setTotalProperty(double value) {
        totalProperty.set(value);
    }

    public DoubleProperty totalPropertyProperty() {
        return totalProperty;
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

    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
    }

    public String getTglPemecahan() {
        return tglPemecahan.get();
    }

    public void setTglPemecahan(String value) {
        tglPemecahan.set(value);
    }

    public StringProperty tglPemecahanProperty() {
        return tglPemecahan;
    }

    public String getNoPemecahan() {
        return noPemecahan.get();
    }

    public void setNoPemecahan(String value) {
        noPemecahan.set(value);
    }

    public StringProperty noPemecahanProperty() {
        return noPemecahan;
    }
    
}
