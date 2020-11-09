/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class SerahTerima {

    private final StringProperty noSerahTerima = new SimpleStringProperty();
    private final StringProperty tglSerahTerima = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final DoubleProperty harga = new SimpleDoubleProperty();
    private final DoubleProperty diskon = new SimpleDoubleProperty();
    private final DoubleProperty totalDP = new SimpleDoubleProperty();
    private final DoubleProperty totalKPR = new SimpleDoubleProperty();
    private final StringProperty noHGB = new SimpleStringProperty();
    private final StringProperty noIMB = new SimpleStringProperty();
    private final StringProperty kelengkapan = new SimpleStringProperty();
    private final StringProperty bonus = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();

    private Customer customer;
    private Property property;
    private Karyawan sales;

    public double getTotalKPR() {
        return totalKPR.get();
    }

    public void setTotalKPR(double value) {
        totalKPR.set(value);
    }

    public DoubleProperty totalKPRProperty() {
        return totalKPR;
    }

    public double getTotalDP() {
        return totalDP.get();
    }

    public void setTotalDP(double value) {
        totalDP.set(value);
    }

    public DoubleProperty totalDPProperty() {
        return totalDP;
    }

    public double getDiskon() {
        return diskon.get();
    }

    public void setDiskon(double value) {
        diskon.set(value);
    }

    public DoubleProperty diskonProperty() {
        return diskon;
    }

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double value) {
        harga.set(value);
    }

    public DoubleProperty hargaProperty() {
        return harga;
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


    public String getBonus() {
        return bonus.get();
    }

    public void setBonus(String value) {
        bonus.set(value);
    }

    public StringProperty bonusProperty() {
        return bonus;
    }

    public String getKelengkapan() {
        return kelengkapan.get();
    }

    public void setKelengkapan(String value) {
        kelengkapan.set(value);
    }

    public StringProperty kelengkapanProperty() {
        return kelengkapan;
    }

    public String getNoIMB() {
        return noIMB.get();
    }

    public void setNoIMB(String value) {
        noIMB.set(value);
    }

    public StringProperty noIMBProperty() {
        return noIMB;
    }

    public String getNoHGB() {
        return noHGB.get();
    }

    public void setNoHGB(String value) {
        noHGB.set(value);
    }

    public StringProperty noHGBProperty() {
        return noHGB;
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


    public String getTglSerahTerima() {
        return tglSerahTerima.get();
    }

    public void setTglSerahTerima(String value) {
        tglSerahTerima.set(value);
    }

    public StringProperty tglSerahTerimaProperty() {
        return tglSerahTerima;
    }

    public String getNoSerahTerima() {
        return noSerahTerima.get();
    }

    public void setNoSerahTerima(String value) {
        noSerahTerima.set(value);
    }

    public StringProperty noSerahTerimaProperty() {
        return noSerahTerima;
    }
    
}
