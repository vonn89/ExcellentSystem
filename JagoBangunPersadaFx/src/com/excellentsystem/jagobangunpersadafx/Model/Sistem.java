/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Sistem {
    private final StringProperty namaPt = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty kota = new SimpleStringProperty();
    private final StringProperty logo = new SimpleStringProperty();
    private final StringProperty namaDirektur = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private User user;
    private final StringProperty version = new SimpleStringProperty();

    public String getVersion() {
        return version.get();
    }

    public void setVersion(String value) {
        version.set(value);
    }

    public StringProperty versionProperty() {
        return version;
    }

    public String getNamaDirektur() {
        return namaDirektur.get();
    }

    public void setNamaDirektur(String value) {
        namaDirektur.set(value);
    }

    public StringProperty namaDirekturProperty() {
        return namaDirektur;
    }


    public String getEmail() {
        return email.get();
    }

    public void setEmail(String value) {
        email.set(value);
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getKota() {
        return kota.get();
    }

    public void setKota(String value) {
        kota.set(value);
    }

    public StringProperty kotaProperty() {
        return kota;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    

    public String getNamaPt() {
        return namaPt.get();
    }

    public void setNamaPt(String value) {
        namaPt.set(value);
    }

    public StringProperty namaPtProperty() {
        return namaPt;
    }

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
    }


    public String getLogo() {
        return logo.get();
    }

    public void setLogo(String value) {
        logo.set(value);
    }

    public StringProperty logoProperty() {
        return logo;
    }
    
}
