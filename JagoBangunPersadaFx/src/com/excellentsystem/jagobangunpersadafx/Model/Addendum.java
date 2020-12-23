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
public class Addendum {

    private final StringProperty noAddendum = new SimpleStringProperty();
    private final StringProperty tglAddendum = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty perubahanHargaProperty = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    
    private Property property;
    private Customer customer;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public double getPerubahanHargaProperty() {
        return perubahanHargaProperty.get();
    }

    public void setPerubahanHargaProperty(double value) {
        perubahanHargaProperty.set(value);
    }

    public DoubleProperty perubahanHargaPropertyProperty() {
        return perubahanHargaProperty;
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


    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
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

    public String getTglAddendum() {
        return tglAddendum.get();
    }

    public void setTglAddendum(String value) {
        tglAddendum.set(value);
    }

    public StringProperty tglAddendumProperty() {
        return tglAddendum;
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

    public String getNoAddendum() {
        return noAddendum.get();
    }

    public void setNoAddendum(String value) {
        noAddendum.set(value);
    }

    public StringProperty noAddendumProperty() {
        return noAddendum;
    }

}
