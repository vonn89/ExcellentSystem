/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PelangganFiktif {

    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();

    public String getNoTelp() {
        return noTelp.get();
    }

    public void setNoTelp(String value) {
        noTelp.set(value);
    }

    public StringProperty noTelpProperty() {
        return noTelp;
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

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
    }
    
}
