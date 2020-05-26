/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class ResetStokBarangCabangHead {

    private final StringProperty noReset = new SimpleStringProperty();
    private final StringProperty tglReset = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratPersen = new SimpleDoubleProperty();
    private final DoubleProperty totalNilai = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public double getTotalNilai() {
        return totalNilai.get();
    }

    public void setTotalNilai(double value) {
        totalNilai.set(value);
    }

    public DoubleProperty totalNilaiProperty() {
        return totalNilai;
    }

    public double getTotalBeratPersen() {
        return totalBeratPersen.get();
    }

    public void setTotalBeratPersen(double value) {
        totalBeratPersen.set(value);
    }

    public DoubleProperty totalBeratPersenProperty() {
        return totalBeratPersen;
    }

    public double getTotalBerat() {
        return totalBerat.get();
    }

    public void setTotalBerat(double value) {
        totalBerat.set(value);
    }

    public DoubleProperty totalBeratProperty() {
        return totalBerat;
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

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
    }

    public String getTglReset() {
        return tglReset.get();
    }

    public void setTglReset(String value) {
        tglReset.set(value);
    }

    public StringProperty tglResetProperty() {
        return tglReset;
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
