/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class Supplier {
    private final StringProperty kodeSupplier = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final StringProperty alamat = new SimpleStringProperty();
    private final StringProperty kota = new SimpleStringProperty();
    private final StringProperty negara = new SimpleStringProperty();
    private final StringProperty kodePos = new SimpleStringProperty();
    private final StringProperty email = new SimpleStringProperty();
    private final StringProperty kontakPerson = new SimpleStringProperty();
    private final StringProperty noTelp = new SimpleStringProperty();
    private final StringProperty noHandphone = new SimpleStringProperty();
    private final StringProperty bank = new SimpleStringProperty();
    private final StringProperty atasNamaRekening = new SimpleStringProperty();
    private final StringProperty noRekening = new SimpleStringProperty();
    private final DoubleProperty deposit = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final DoubleProperty hutang = new SimpleDoubleProperty();

    public double getHutang() {
        return hutang.get();
    }

    public void setHutang(double value) {
        hutang.set(value);
    }

    public DoubleProperty hutangProperty() {
        return hutang;
    }

    public double getDeposit() {
        return deposit.get();
    }

    public void setDeposit(double value) {
        deposit.set(value);
    }

    public DoubleProperty depositProperty() {
        return deposit;
    }


    public String getKodeSupplier() {
        return kodeSupplier.get();
    }

    public void setKodeSupplier(String value) {
        kodeSupplier.set(value);
    }

    public StringProperty kodeSupplierProperty() {
        return kodeSupplier;
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

    public String getAlamat() {
        return alamat.get();
    }

    public void setAlamat(String value) {
        alamat.set(value);
    }

    public StringProperty alamatProperty() {
        return alamat;
    }

    public String getKota() {
        return kota.get();
    }

    public void setKota(String value) {
        kota.set(value);
    }

    public StringProperty kotaProperty() {
        return kota;
    }

    public String getNegara() {
        return negara.get();
    }

    public void setNegara(String value) {
        negara.set(value);
    }

    public StringProperty negaraProperty() {
        return negara;
    }

    public String getKodePos() {
        return kodePos.get();
    }

    public void setKodePos(String value) {
        kodePos.set(value);
    }

    public StringProperty kodePosProperty() {
        return kodePos;
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

    public String getKontakPerson() {
        return kontakPerson.get();
    }

    public void setKontakPerson(String value) {
        kontakPerson.set(value);
    }

    public StringProperty kontakPersonProperty() {
        return kontakPerson;
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

    public String getBank() {
        return bank.get();
    }

    public void setBank(String value) {
        bank.set(value);
    }

    public StringProperty bankProperty() {
        return bank;
    }

    public String getAtasNamaRekening() {
        return atasNamaRekening.get();
    }

    public void setAtasNamaRekening(String value) {
        atasNamaRekening.set(value);
    }

    public StringProperty atasNamaRekeningProperty() {
        return atasNamaRekening;
    }

    public String getNoRekening() {
        return noRekening.get();
    }

    public void setNoRekening(String value) {
        noRekening.set(value);
    }

    public StringProperty noRekeningProperty() {
        return noRekening;
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
