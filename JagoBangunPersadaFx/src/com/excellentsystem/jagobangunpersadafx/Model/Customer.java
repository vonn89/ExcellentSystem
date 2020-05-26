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
public class Customer {
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty jenisKelamin = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty noHandphone = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty statusNikah = new SimpleStringProperty();
    private final StringProperty agama = new SimpleStringProperty();
    private final StringProperty pekerjaan = new SimpleStringProperty();
    private final StringProperty noKTP = new SimpleStringProperty();
    private final StringProperty noNPWP = new SimpleStringProperty();
    private final StringProperty noSPTPPH = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();


    public String getKodeCustomer() {
        return kodeCustomer.get();
    }

    public void setKodeCustomer(String value) {
        kodeCustomer.set(value);
    }

    public StringProperty kodeCustomerProperty() {
        return kodeCustomer;
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

    public String getJenisKelamin() {
        return jenisKelamin.get();
    }

    public void setJenisKelamin(String value) {
        jenisKelamin.set(value);
    }

    public StringProperty jenisKelaminProperty() {
        return jenisKelamin;
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

    public String getNoHandphone() {
        return noHandphone.get();
    }

    public void setNoHandphone(String value) {
        noHandphone.set(value);
    }

    public StringProperty noHandphoneProperty() {
        return noHandphone;
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

    public String getStatusNikah() {
        return statusNikah.get();
    }

    public void setStatusNikah(String value) {
        statusNikah.set(value);
    }

    public StringProperty statusNikahProperty() {
        return statusNikah;
    }
    public String getAgama() {
        return agama.get();
    }

    public void setAgama(String value) {
        agama.set(value);
    }

    public StringProperty agamaProperty() {
        return agama;
    }

    public String getPekerjaan() {
        return pekerjaan.get();
    }

    public void setPekerjaan(String value) {
        pekerjaan.set(value);
    }

    public StringProperty pekerjaanProperty() {
        return pekerjaan;
    }

    public String getNoKTP() {
        return noKTP.get();
    }

    public void setNoKTP(String value) {
        noKTP.set(value);
    }

    public StringProperty noKTPProperty() {
        return noKTP;
    }

    public String getNoNPWP() {
        return noNPWP.get();
    }

    public void setNoNPWP(String value) {
        noNPWP.set(value);
    }

    public StringProperty noNPWPProperty() {
        return noNPWP;
    }

    public String getNoSPTPPH() {
        return noSPTPPH.get();
    }

    public void setNoSPTPPH(String value) {
        noSPTPPH.set(value);
    }

    public StringProperty noSPTPPHProperty() {
        return noSPTPPH;
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
    
}
