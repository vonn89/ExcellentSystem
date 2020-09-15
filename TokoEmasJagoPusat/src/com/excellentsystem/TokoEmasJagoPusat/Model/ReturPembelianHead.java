/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class ReturPembelianHead {
    //tt_retur_pembelian_head
    private final StringProperty noRetur = new SimpleStringProperty();
    private final StringProperty tglRetur = new SimpleStringProperty();
    private final StringProperty supplier = new SimpleStringProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalHargaPersen = new SimpleDoubleProperty();
    private final DoubleProperty hargaEmas = new SimpleDoubleProperty();
    private final DoubleProperty totalRetur = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private List<ReturPembelianDetail> listReturPembelianDetail;
    
    public double getTotalHargaPersen() {
        return totalHargaPersen.get();
    }

    public void setTotalHargaPersen(double value) {
        totalHargaPersen.set(value);
    }

    public DoubleProperty totalHargaPersenProperty() {
        return totalHargaPersen;
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

    public List<ReturPembelianDetail> getListReturPembelianDetail() {
        return listReturPembelianDetail;
    }

    public void setListReturPembelianDetail(List<ReturPembelianDetail> listReturPembelianDetail) {
        this.listReturPembelianDetail = listReturPembelianDetail;
    }
    
    public double getTotalRetur() {
        return totalRetur.get();
    }

    public void setTotalRetur(double value) {
        totalRetur.set(value);
    }

    public DoubleProperty totalReturProperty() {
        return totalRetur;
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

    public double getHargaEmas() {
        return hargaEmas.get();
    }

    public void setHargaEmas(double value) {
        hargaEmas.set(value);
    }

    public DoubleProperty hargaEmasProperty() {
        return hargaEmas;
    }


    public String getSupplier() {
        return supplier.get();
    }

    public void setSupplier(String value) {
        supplier.set(value);
    }

    public StringProperty supplierProperty() {
        return supplier;
    }

    public String getTglRetur() {
        return tglRetur.get();
    }

    public void setTglRetur(String value) {
        tglRetur.set(value);
    }

    public StringProperty tglReturProperty() {
        return tglRetur;
    }

    public String getNoRetur() {
        return noRetur.get();
    }

    public void setNoRetur(String value) {
        noRetur.set(value);
    }

    public StringProperty noReturProperty() {
        return noRetur;
    }
}
