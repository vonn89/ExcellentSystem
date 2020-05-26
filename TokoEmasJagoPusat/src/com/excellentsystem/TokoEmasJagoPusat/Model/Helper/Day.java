/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model.Helper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class Day {

    private final StringProperty hari = new SimpleStringProperty();
    private final StringProperty tanggal = new SimpleStringProperty();
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

    public String getTanggal() {
        return tanggal.get();
    }

    public void setTanggal(String value) {
        tanggal.set(value);
    }

    public StringProperty tanggalProperty() {
        return tanggal;
    }

    public String getHari() {
        return hari.get();
    }

    public void setHari(String value) {
        hari.set(value);
    }

    public StringProperty hariProperty() {
        return hari;
    }
    
}
