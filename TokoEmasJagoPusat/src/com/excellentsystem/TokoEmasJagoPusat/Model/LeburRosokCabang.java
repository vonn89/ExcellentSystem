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
 * @author Xtreme
 */
public class LeburRosokCabang {
    //tt_lebur_rosok_cabang
    private final StringProperty noLebur = new SimpleStringProperty();
    private final StringProperty tglLebur = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final DoubleProperty beratKotor = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty nilaiPokok = new SimpleDoubleProperty();
    private final DoubleProperty beratJadi = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusSelesai = new SimpleStringProperty();
    private final StringProperty tglSelesai = new SimpleStringProperty();
    private final StringProperty userSelesai = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    public String getStatusBatal() {
        return statusBatal.get();
    }

    public void setStatusBatal(String value) {
        statusBatal.set(value);
    }

    public StringProperty statusBatalProperty() {
        return statusBatal;
    }

    public String getUserSelesai() {
        return userSelesai.get();
    }

    public void setUserSelesai(String value) {
        userSelesai.set(value);
    }

    public StringProperty userSelesaiProperty() {
        return userSelesai;
    }

    public String getTglSelesai() {
        return tglSelesai.get();
    }

    public void setTglSelesai(String value) {
        tglSelesai.set(value);
    }

    public StringProperty tglSelesaiProperty() {
        return tglSelesai;
    }

    public String getStatusSelesai() {
        return statusSelesai.get();
    }

    public void setStatusSelesai(String value) {
        statusSelesai.set(value);
    }

    public StringProperty statusSelesaiProperty() {
        return statusSelesai;
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

    public double getBeratKotor() {
        return beratKotor.get();
    }

    public void setBeratKotor(double value) {
        beratKotor.set(value);
    }

    public DoubleProperty beratKotorProperty() {
        return beratKotor;
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

    public String getUserBatal() {
        return userBatal.get();
    }

    public void setUserBatal(String value) {
        userBatal.set(value);
    }

    public StringProperty userBatalProperty() {
        return userBatal;
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

    public double getBeratJadi() {
        return beratJadi.get();
    }

    public void setBeratJadi(double value) {
        beratJadi.set(value);
    }

    public DoubleProperty beratJadiProperty() {
        return beratJadi;
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

    public String getTglLebur() {
        return tglLebur.get();
    }

    public void setTglLebur(String value) {
        tglLebur.set(value);
    }

    public StringProperty tglLeburProperty() {
        return tglLebur;
    }

    public String getNoLebur() {
        return noLebur.get();
    }

    public void setNoLebur(String value) {
        noLebur.set(value);
    }

    public StringProperty noLeburProperty() {
        return noLebur;
    }
}
