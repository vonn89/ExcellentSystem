/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class RAPDetailProperty {

    private final StringProperty noRap = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final DoubleProperty luasTanah = new SimpleDoubleProperty();
    private final DoubleProperty persentase = new SimpleDoubleProperty();
    private Property property;
    private final BooleanProperty status = new SimpleBooleanProperty();

    public boolean isStatus() {
        return status.get();
    }

    public void setStatus(boolean value) {
        status.set(value);
    }

    public BooleanProperty statusProperty() {
        return status;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
    
    public double getPersentase() {
        return persentase.get();
    }

    public void setPersentase(double value) {
        persentase.set(value);
    }

    public DoubleProperty persentaseProperty() {
        return persentase;
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


    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
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
