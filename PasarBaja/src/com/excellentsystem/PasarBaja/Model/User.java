/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.Model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class User {

    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty password = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty level = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<Otoritas> otoritas;

    public List<Otoritas> getOtoritas() {
        return otoritas;
    }

    public void setOtoritas(List<Otoritas> otoritas) {
        this.otoritas = otoritas;
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

    public String getLevel() {
        return level.get();
    }

    public void setLevel(String value) {
        level.set(value);
    }

    public StringProperty levelProperty() {
        return level;
    }

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
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
