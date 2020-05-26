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
public class Pegawai {

    private final StringProperty kodePegawai = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty jenisKelamin = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty jabatan = new SimpleStringProperty();
    private final StringProperty tanggalMasuk = new SimpleStringProperty();
    private final StringProperty tanggalKeluar = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getTanggalKeluar() {
        return tanggalKeluar.get();
    }

    public void setTanggalKeluar(String value) {
        tanggalKeluar.set(value);
    }

    public StringProperty tanggalKeluarProperty() {
        return tanggalKeluar;
    }

    public String getTanggalMasuk() {
        return tanggalMasuk.get();
    }

    public void setTanggalMasuk(String value) {
        tanggalMasuk.set(value);
    }

    public StringProperty tanggalMasukProperty() {
        return tanggalMasuk;
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

    public String getJenisKelamin() {
        return jenisKelamin.get();
    }

    public void setJenisKelamin(String value) {
        jenisKelamin.set(value);
    }

    public StringProperty jenisKelaminProperty() {
        return jenisKelamin;
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

    public String getKodePegawai() {
        return kodePegawai.get();
    }

    public void setKodePegawai(String value) {
        kodePegawai.set(value);
    }

    public StringProperty kodePegawaiProperty() {
        return kodePegawai;
    }
    
}
