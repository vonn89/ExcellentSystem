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
public class PenggabunganPropertyHead {
    private final StringProperty noPenggabungan = new SimpleStringProperty();
    private final StringProperty tglPenggabungan = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final DoubleProperty totalProperty = new SimpleDoubleProperty();
    private final DoubleProperty totalLuasTanah = new SimpleDoubleProperty();
    private final DoubleProperty nilaiTanah = new SimpleDoubleProperty();
    private final DoubleProperty nilaiTanahPerMeter = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty luasAkhir = new SimpleDoubleProperty();
    private List<PenggabunganPropertyDetail> allDetail;
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    public Double getLuasAkhir() {
        return luasAkhir.get();
    }

    public void setLuasAkhir(Double value) {
        luasAkhir.set(value);
    }

    public DoubleProperty luasAkhirProperty() {
        return luasAkhir;
    }

    public List<PenggabunganPropertyDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PenggabunganPropertyDetail> allDetail) {
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

    public double getNilaiTanahPerMeter() {
        return nilaiTanahPerMeter.get();
    }

    public void setNilaiTanahPerMeter(double value) {
        nilaiTanahPerMeter.set(value);
    }

    public DoubleProperty nilaiTanahPerMeterProperty() {
        return nilaiTanahPerMeter;
    }

    public double getNilaiTanah() {
        return nilaiTanah.get();
    }

    public void setNilaiTanah(double value) {
        nilaiTanah.set(value);
    }

    public DoubleProperty nilaiTanahProperty() {
        return nilaiTanah;
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


    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
    }

    public String getTglPenggabungan() {
        return tglPenggabungan.get();
    }

    public void setTglPenggabungan(String value) {
        tglPenggabungan.set(value);
    }

    public StringProperty tglPenggabunganProperty() {
        return tglPenggabungan;
    }

    public String getNoPenggabungan() {
        return noPenggabungan.get();
    }

    public void setNoPenggabungan(String value) {
        noPenggabungan.set(value);
    }

    public StringProperty noPenggabunganProperty() {
        return noPenggabungan;
    }
    
}
