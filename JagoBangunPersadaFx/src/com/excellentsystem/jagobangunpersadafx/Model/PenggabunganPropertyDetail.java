/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PenggabunganPropertyDetail {
    private final StringProperty noPenggabungan = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
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
