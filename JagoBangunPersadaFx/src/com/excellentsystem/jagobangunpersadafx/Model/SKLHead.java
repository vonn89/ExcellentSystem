/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class SKLHead {
    private final StringProperty noSKL = new SimpleStringProperty();
    private final StringProperty tglSKL = new SimpleStringProperty();
    private final StringProperty noSTJ = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final DoubleProperty totalPembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPelunasan = new SimpleDoubleProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private STJHead stj;
    private Customer customer;
    private Property property;
    private Karyawan sales;
    private List<SKLDetail> allDetail;

    public double getTotalPembayaran() {
        return totalPembayaran.get();
    }

    public void setTotalPembayaran(double value) {
        totalPembayaran.set(value);
    }

    public DoubleProperty totalPembayaranProperty() {
        return totalPembayaran;
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

    public List<SKLDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<SKLDetail> allDetail) {
        this.allDetail = allDetail;
    }
    
    public STJHead getStj() {
        return stj;
    }

    public void setStj(STJHead stj) {
        this.stj = stj;
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

    public double getSisaPelunasan() {
        return sisaPelunasan.get();
    }

    public void setSisaPelunasan(double value) {
        sisaPelunasan.set(value);
    }

    public DoubleProperty sisaPelunasanProperty() {
        return sisaPelunasan;
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

    public String getTglSKL() {
        return tglSKL.get();
    }

    public void setTglSKL(String value) {
        tglSKL.set(value);
    }

    public StringProperty tglSKLProperty() {
        return tglSKL;
    }

    public String getNoSKL() {
        return noSKL.get();
    }

    public void setNoSKL(String value) {
        noSKL.set(value);
    }

    public StringProperty noSKLProperty() {
        return noSKL;
    }
    
}
