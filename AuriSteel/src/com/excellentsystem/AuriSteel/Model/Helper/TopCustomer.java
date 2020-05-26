/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model.Helper;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class TopCustomer {
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty nama = new SimpleStringProperty();
    private final DoubleProperty totalPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty persentasePenjualan = new SimpleDoubleProperty();

    public double getPersentasePenjualan() {
        return persentasePenjualan.get();
    }

    public void setPersentasePenjualan(double value) {
        persentasePenjualan.set(value);
    }

    public DoubleProperty persentasePenjualanProperty() {
        return persentasePenjualan;
    }

    public double getTotalPenjualan() {
        return totalPenjualan.get();
    }

    public void setTotalPenjualan(double value) {
        totalPenjualan.set(value);
    }

    public DoubleProperty totalPenjualanProperty() {
        return totalPenjualan;
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

    public String getNama() {
        return nama.get();
    }

    public void setNama(String value) {
        nama.set(value);
    }

    public StringProperty namaProperty() {
        return nama;
    }


    
    
     

}
