/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

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
public class SDP {
    private final StringProperty noSDP = new SimpleStringProperty();
    private final StringProperty tglSDP = new SimpleStringProperty();
    private final StringProperty noSTJ = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final IntegerProperty tahap = new SimpleIntegerProperty();
    private final DoubleProperty jumlahRp = new SimpleDoubleProperty();
    private final StringProperty tipeKeuangan = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty jatuhTempo = new SimpleStringProperty();
    private final DoubleProperty jumlahTagihan = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private STJHead stj;
    private Customer customer;
    private Property property;
    private Karyawan sales;

    public STJHead getStj() {
        return stj;
    }

    public void setStj(STJHead stj) {
        this.stj = stj;
    }

    public int getTahap() {
        return tahap.get();
    }

    public void setTahap(int value) {
        tahap.set(value);
    }

    public IntegerProperty tahapProperty() {
        return tahap;
    }

    public double getJumlahTagihan() {
        return jumlahTagihan.get();
    }

    public void setJumlahTagihan(double value) {
        jumlahTagihan.set(value);
    }

    public DoubleProperty jumlahTagihanProperty() {
        return jumlahTagihan;
    }

    public String getJatuhTempo() {
        return jatuhTempo.get();
    }

    public void setJatuhTempo(String value) {
        jatuhTempo.set(value);
    }

    public StringProperty jatuhTempoProperty() {
        return jatuhTempo;
    }

    public String getTipeKeuangan() {
        return tipeKeuangan.get();
    }

    public void setTipeKeuangan(String value) {
        tipeKeuangan.set(value);
    }

    public StringProperty tipeKeuanganProperty() {
        return tipeKeuangan;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Karyawan getSales() {
        return sales;
    }

    public void setSales(Karyawan sales) {
        this.sales = sales;
    }
    
    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public double getJumlahRp() {
        return jumlahRp.get();
    }

    public void setJumlahRp(double value) {
        jumlahRp.set(value);
    }

    public DoubleProperty jumlahRpProperty() {
        return jumlahRp;
    }


    public String getKodeCustomer() {
        return kodeCustomer.get();
    }

    public void setKodeCustomer(String value) {
        kodeCustomer.set(value);
    }

    public StringProperty kodeCustomerProperty() {
        return kodeCustomer;
    }

    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
    }


    public String getNoSTJ() {
        return noSTJ.get();
    }

    public void setNoSTJ(String value) {
        noSTJ.set(value);
    }

    public StringProperty noSTJProperty() {
        return noSTJ;
    }

    public String getTglSDP() {
        return tglSDP.get();
    }

    public void setTglSDP(String value) {
        tglSDP.set(value);
    }

    public StringProperty tglSDPProperty() {
        return tglSDP;
    }

    public String getNoSDP() {
        return noSDP.get();
    }

    public void setNoSDP(String value) {
        noSDP.set(value);
    }

    public StringProperty noSDPProperty() {
        return noSDP;
    }
    
}
