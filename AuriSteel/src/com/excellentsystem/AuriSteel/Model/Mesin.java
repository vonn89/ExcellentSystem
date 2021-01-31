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
public class Mesin {

    private final StringProperty kodeMesin = new SimpleStringProperty();

    public String getKodeMesin() {
        return kodeMesin.get();
    }

    public void setKodeMesin(String value) {
        kodeMesin.set(value);
    }

    public StringProperty kodeMesinProperty() {
        return kodeMesin;
    }
    
}
