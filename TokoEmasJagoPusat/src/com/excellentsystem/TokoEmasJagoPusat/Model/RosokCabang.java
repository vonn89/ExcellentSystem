/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class RosokCabang {

    private final StringProperty noRosok = new SimpleStringProperty();
    private final StringProperty tglRosok = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenRosok = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public double getBeratPersenRosok() {
        return beratPersenRosok.get();
    }

    public void setBeratPersenRosok(double value) {
        beratPersenRosok.set(value);
    }

    public DoubleProperty beratPersenRosokProperty() {
        return beratPersenRosok;
    }
    
    public double getNilaiPokok() {
        return nilaiPokok.get();
    }

    public void setNilaiPokok(double value) {
        nilaiPokok.set(value);
    }

    public DoubleProperty nilaiPokokProperty() {
        return nilaiPokok;
    }

    public double getBeratPersen() {
        return beratPersen.get();
    }

    public void setBeratPersen(double value) {
        beratPersen.set(value);
    }

    public DoubleProperty beratPersenProperty() {
        return beratPersen;
    }

    public double getPersentaseEmas() {
        return persentaseEmas.get();
    }

    public void setPersentaseEmas(double value) {
        persentaseEmas.set(value);
    }

    public DoubleProperty persentaseEmasProperty() {
        return persentaseEmas;
    }

    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }

    public int getQty() {
        return qty.get();
    }

    public void setQty(int value) {
        qty.set(value);
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
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

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
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

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
    }

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
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

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
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

    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public String getTglRosok() {
        return tglRosok.get();
    }

    public void setTglRosok(String value) {
        tglRosok.set(value);
    }

    public StringProperty tglRosokProperty() {
        return tglRosok;
    }

    public String getNoRosok() {
        return noRosok.get();
    }

    public void setNoRosok(String value) {
        noRosok.set(value);
    }

    public StringProperty noRosokProperty() {
        return noRosok;
    }
    
}
