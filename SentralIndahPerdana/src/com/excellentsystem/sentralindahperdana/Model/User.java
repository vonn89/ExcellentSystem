/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yunaz
 */
public class User {

    private final StringProperty username = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<Otoritas> otoritas;
    private List<OtoritasKeuangan> otoritasKeuangan;


    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
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

    public List<Otoritas> getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(List<Otoritas> otoritas) {
        this.otoritas = otoritas;
    }

    public List<OtoritasKeuangan> getOtoritasKeuangan() {
        return otoritasKeuangan;
    }

    public void setOtoritasKeuangan(List<OtoritasKeuangan> otoritasKeuangan) {
        this.otoritasKeuangan = otoritasKeuangan;
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

    
}
