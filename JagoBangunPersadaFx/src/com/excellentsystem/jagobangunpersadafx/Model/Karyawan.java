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
public class Karyawan {
    private final StringProperty kodeKaryawan = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty jabatan = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty noHandphone = new SimpleStringProperty();
    private final StringProperty statusNikah = new SimpleStringProperty();
    private final StringProperty agama = new SimpleStringProperty();
    private final StringProperty identitas = new SimpleStringProperty();
    private final StringProperty noIdentitas = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getKodeKaryawan() {
        return kodeKaryawan.get();
    }

    public void setKodeKaryawan(String value) {
        kodeKaryawan.set(value);
    }

    public StringProperty kodeKaryawanProperty() {
        return kodeKaryawan;
    }

    public String getNoIdentitas() {
        return noIdentitas.get();
    }

    public void setNoIdentitas(String value) {
        noIdentitas.set(value);
    }

    public StringProperty noIdentitasProperty() {
        return noIdentitas;
    }

    public String getIdentitas() {
        return identitas.get();
    }

    public void setIdentitas(String value) {
        identitas.set(value);
    }

    public StringProperty identitasProperty() {
        return identitas;
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

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
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
    

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getJabatan() {
        return jabatan.get();
    }

    public void setJabatan(String value) {
        jabatan.set(value);
    }

    public StringProperty jabatanProperty() {
        return jabatan;
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
    
    
}
