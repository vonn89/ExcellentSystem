/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PembangunanDetail {
    private final StringProperty noPembangunan = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty namaProperty = new SimpleStringProperty();
    private final DoubleProperty luasTanah = new SimpleDoubleProperty();
    private final DoubleProperty biaya = new SimpleDoubleProperty();
    private final BooleanProperty status = new SimpleBooleanProperty();
    private Property property;

    public String getNoPembangunan() {
        return noPembangunan.get();
    }

    public void setNoPembangunan(String value) {
        noPembangunan.set(value);
    }

    public StringProperty noPembangunanProperty() {
        return noPembangunan;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public double getBiaya() {
        return biaya.get();
    }

    public void setBiaya(double value) {
        biaya.set(value);
    }

    public DoubleProperty biayaProperty() {
        return biaya;
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

    
}
