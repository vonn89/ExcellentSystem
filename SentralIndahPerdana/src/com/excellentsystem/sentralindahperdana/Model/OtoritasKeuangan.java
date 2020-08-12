/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class OtoritasKeuangan {

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty kodeKeuangan = new SimpleStringProperty();
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

    public String getKodeKeuangan() {
        return kodeKeuangan.get();
    }

    public void setKodeKeuangan(String value) {
        kodeKeuangan.set(value);
    }

    public StringProperty kodeKeuanganProperty() {
        return kodeKeuangan;
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
    
}
