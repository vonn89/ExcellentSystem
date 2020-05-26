/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class User {
    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty level = new SimpleStringProperty();
    private List<Otoritas> otoritas = new ArrayList<>();
    private List<OtoritasKeuangan> otoritasKeuangan = new ArrayList<>();

    public List<OtoritasKeuangan> getOtoritasKeuangan() {
        return otoritasKeuangan;
    }

    public void setOtoritasKeuangan(List<OtoritasKeuangan> otoritasKeuangan) {
        this.otoritasKeuangan = otoritasKeuangan;
    }
    
    public List<Otoritas> getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(List<Otoritas> otoritas) {
        this.otoritas = otoritas;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String value) {
        username.set(value);
    }

    public StringProperty usernameProperty() {
        return username;
    }
    
    

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String value) {
        password.set(value);
    }

    public StringProperty passwordProperty() {
        return password;
    }
    

    public String getLevel() {
        return level.get();
    }

    public void setLevel(String value) {
        level.set(value);
    }

    public StringProperty levelProperty() {
        return level;
    }

    
}
