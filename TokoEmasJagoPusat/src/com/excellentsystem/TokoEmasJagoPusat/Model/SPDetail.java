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
 * @author Xtreme
 */
public class SPDetail {
    //tt_sp_detail
    private final StringProperty noSP = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeKategoriBalenan = new SimpleStringProperty();
    private final StringProperty kodeJenisBalenan = new SimpleStringProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty beratPersenBalenan = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final DoubleProperty beratPenyusutan = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPenyusutan = new SimpleDoubleProperty();

    public double getBeratPersen() {
        return beratPersen.get();
    }

    public void setBeratPersen(double value) {
        beratPersen.set(value);
    }

    public DoubleProperty beratPersenProperty() {
        return beratPersen;
    }

    public double getBeratPersenBalenan() {
        return beratPersenBalenan.get();
    }

    public void setBeratPersenBalenan(double value) {
        beratPersenBalenan.set(value);
    }

    public DoubleProperty beratPersenBalenanProperty() {
        return beratPersenBalenan;
    }

    public String getKodeJenisBalenan() {
        return kodeJenisBalenan.get();
    }

    public void setKodeJenisBalenan(String value) {
        kodeJenisBalenan.set(value);
    }

    public StringProperty kodeJenisBalenanProperty() {
        return kodeJenisBalenan;
    }

    public String getKodeKategoriBalenan() {
        return kodeKategoriBalenan.get();
    }

    public void setKodeKategoriBalenan(String value) {
        kodeKategoriBalenan.set(value);
    }

    public StringProperty kodeKategoriBalenanProperty() {
        return kodeKategoriBalenan;
    }

    public double getNilaiPenyusutan() {
        return nilaiPenyusutan.get();
    }

    public void setNilaiPenyusutan(double value) {
        nilaiPenyusutan.set(value);
    }

    public DoubleProperty nilaiPenyusutanProperty() {
        return nilaiPenyusutan;
    }


    public double getBeratPenyusutan() {
        return beratPenyusutan.get();
    }

    public void setBeratPenyusutan(double value) {
        beratPenyusutan.set(value);
    }

    public DoubleProperty beratPenyusutanProperty() {
        return beratPenyusutan;
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


    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
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

    public String getNoSP() {
        return noSP.get();
    }

    public void setNoSP(String value) {
        noSP.set(value);
    }

    public StringProperty noSPProperty() {
        return noSP;
    }
}
