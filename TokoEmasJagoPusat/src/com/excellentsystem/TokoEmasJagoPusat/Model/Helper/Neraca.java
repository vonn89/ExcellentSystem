/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model.Helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class Neraca {

    private final StringProperty aktiva = new SimpleStringProperty();
    private final StringProperty passiva = new SimpleStringProperty();
    private final StringProperty jumlahAktiva = new SimpleStringProperty();
    private final StringProperty jumlahPassiva = new SimpleStringProperty();

    public Neraca(String aktiva, String jumlahAktiva, String passiva, String jumlahPassiva) {
        this.aktiva.set(aktiva);
        this.jumlahAktiva.set(jumlahAktiva);
        this.passiva.set(passiva);
        this.jumlahPassiva.set(jumlahPassiva);
    }

    public String getJumlahPassiva() {
        return jumlahPassiva.get();
    }

    public void setJumlahPassiva(String value) {
        jumlahPassiva.set(value);
    }

    public StringProperty jumlahPassivaProperty() {
        return jumlahPassiva;
    }

    public String getJumlahAktiva() {
        return jumlahAktiva.get();
    }

    public void setJumlahAktiva(String value) {
        jumlahAktiva.set(value);
    }

    public StringProperty jumlahAktivaProperty() {
        return jumlahAktiva;
    }
    
    public String getPassiva() {
        return passiva.get();
    }

    public void setPassiva(String value) {
        passiva.set(value);
    }

    public StringProperty passivaProperty() {
        return passiva;
    }

    public String getAktiva() {
        return aktiva.get();
    }

    public void setAktiva(String value) {
        aktiva.set(value);
    }

    public StringProperty aktivaProperty() {
        return aktiva;
    }
    
}
