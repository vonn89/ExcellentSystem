/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class OtoritasKeuangan {
    private final StringProperty username = new SimpleStringProperty();

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }
    private final StringProperty kodeKeuangan = new SimpleStringProperty();

    public String getKodeKeuangan() {
        return kodeKeuangan.get();
    }

    public void setKodeKeuangan(String value) {
        kodeKeuangan.set(value);
    }

    public StringProperty kodeKeuanganProperty() {
        return kodeKeuangan;
    }
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
    
    
}
