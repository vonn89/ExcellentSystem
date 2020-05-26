/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Bahan {
   
    private final StringProperty kodeBahan = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty noKontrak = new SimpleStringProperty();
    private final StringProperty namaBahan = new SimpleStringProperty();
    private final StringProperty spesifikasi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty beratKotor = new SimpleDoubleProperty();
    private final DoubleProperty beratBersih = new SimpleDoubleProperty();
    private final DoubleProperty panjang = new SimpleDoubleProperty();
    private final DoubleProperty hargaBeli = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private StokBahan stokBahan;
    private KategoriBahan kategoriBahan;

    public KategoriBahan getKategoriBahan() {
        return kategoriBahan;
    }

    public void setKategoriBahan(KategoriBahan kategoriBahan) {
        this.kategoriBahan = kategoriBahan;
    }
    
    
    public String getNoKontrak() {
        return noKontrak.get();
    }

    public void setNoKontrak(String value) {
        noKontrak.set(value);
    }

    public StringProperty noKontrakProperty() {
        return noKontrak;
    }

    public double getHargaBeli() {
        return hargaBeli.get();
    }

    public void setHargaBeli(double value) {
        hargaBeli.set(value);
    }

    public DoubleProperty hargaBeliProperty() {
        return hargaBeli;
    }


    public StokBahan getStokBahan() {
        return stokBahan;
    }

    public void setStokBahan(StokBahan stokBahan) {
        this.stokBahan = stokBahan;
    }
    
    
    public String getKodeBahan() {
        return kodeBahan.get();
    }

    public void setKodeBahan(String value) {
        kodeBahan.set(value);
    }

    public StringProperty kodeBahanProperty() {
        return kodeBahan;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }
    

    public String getNamaBahan() {
        return namaBahan.get();
    }

    public void setNamaBahan(String value) {
        namaBahan.set(value);
    }

    public StringProperty namaBahanProperty() {
        return namaBahan;
    }

    public String getSpesifikasi() {
        return spesifikasi.get();
    }

    public void setSpesifikasi(String value) {
        spesifikasi.set(value);
    }

    public StringProperty spesifikasiProperty() {
        return spesifikasi;
    }

    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }
    public double getBeratKotor() {
        return beratKotor.get();
    }

    public void setBeratKotor(double value) {
        beratKotor.set(value);
    }

    public DoubleProperty beratKotorProperty() {
        return beratKotor;
    }

    public double getBeratBersih() {
        return beratBersih.get();
    }

    public void setBeratBersih(double value) {
        beratBersih.set(value);
    }

    public DoubleProperty beratBersihProperty() {
        return beratBersih;
    }
    

    public double getPanjang() {
        return panjang.get();
    }

    public void setPanjang(double value) {
        panjang.set(value);
    }

    public DoubleProperty panjangProperty() {
        return panjang;
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
