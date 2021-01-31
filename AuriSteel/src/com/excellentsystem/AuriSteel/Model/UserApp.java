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
public class UserApp {

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty pin = new SimpleStringProperty();

    public String getPin() {
        return pin.get();
    }

    public void setPin(String value) {
        pin.set(value);
    }

    public StringProperty pinProperty() {
        return pin;
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
