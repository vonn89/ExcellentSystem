/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class UserMesinApp {

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty kodeMesin = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getKodeMesin() {
        return kodeMesin.get();
    }

    public void setKodeMesin(String value) {
        kodeMesin.set(value);
    }

    public StringProperty kodeMesinProperty() {
        return kodeMesin;
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
    
}
