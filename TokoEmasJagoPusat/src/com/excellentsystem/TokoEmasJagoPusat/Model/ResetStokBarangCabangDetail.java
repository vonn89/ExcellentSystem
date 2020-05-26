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
public class ResetStokBarangCabangDetail {

    private final StringProperty noReset = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();

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

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }

    public String getNoReset() {
        return noReset.get();
    }

    public void setNoReset(String value) {
        noReset.set(value);
    }

    public StringProperty noResetProperty() {
        return noReset;
    }
    
}
