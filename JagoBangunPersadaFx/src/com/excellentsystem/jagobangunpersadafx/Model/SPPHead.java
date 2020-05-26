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
public class SPPHead {
    
    private final StringProperty noSPP = new SimpleStringProperty();
    private final StringProperty tglSPP = new SimpleStringProperty();
    private final StringProperty noSKL = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final DoubleProperty totalDP = new SimpleDoubleProperty();
    private final DoubleProperty totalAngsuran = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private Customer customer;
    private Property property;
    private List<SPPDetail> allDetail;
    private SKLHead skl;

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

    public List<SPPDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<SPPDetail> allDetail) {
        this.allDetail = allDetail;
    }

    public SKLHead getSkl() {
        return skl;
    }

    public void setSkl(SKLHead skl) {
        this.skl = skl;
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


    public double getTotalAngsuran() {
        return totalAngsuran.get();
    }

    public void setTotalAngsuran(double value) {
        totalAngsuran.set(value);
    }

    public DoubleProperty totalAngsuranProperty() {
        return totalAngsuran;
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


    public String getNoSKL() {
        return noSKL.get();
    }

    public void setNoSKL(String value) {
        noSKL.set(value);
    }

    public StringProperty noSKLProperty() {
        return noSKL;
    }

    public String getTglSPP() {
        return tglSPP.get();
    }

    public void setTglSPP(String value) {
        tglSPP.set(value);
    }

    public StringProperty tglSPPProperty() {
        return tglSPP;
    }

    public String getNoSPP() {
        return noSPP.get();
    }

    public void setNoSPP(String value) {
        noSPP.set(value);
    }

    public StringProperty noSPPProperty() {
        return noSPP;
    }
    
}
