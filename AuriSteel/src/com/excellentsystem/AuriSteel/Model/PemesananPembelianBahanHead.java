/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class PemesananPembelianBahanHead {

    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final StringProperty tglPemesanan = new SimpleStringProperty();
    private final StringProperty kodeSupplier = new SimpleStringProperty();
    private final StringProperty noKontrak = new SimpleStringProperty();
    private final StringProperty paymentTerm = new SimpleStringProperty();
    private final StringProperty deliveryTerm = new SimpleStringProperty();
    private final DoubleProperty totalPemesanan = new SimpleDoubleProperty();
    private final DoubleProperty downPayment = new SimpleDoubleProperty();
    private final DoubleProperty sisaDownPayment = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private Supplier supplier;
    private List<PemesananPembelianBahanDetail> listPemesananPembelianBahanDetail;
    private List<Piutang> listPiutang;

    public List<Piutang> getListPiutang() {
        return listPiutang;
    }

    public void setListPiutang(List<Piutang> listPiutang) {
        this.listPiutang = listPiutang;
    }
    
    
    public String getDeliveryTerm() {
        return deliveryTerm.get();
    }

    public void setDeliveryTerm(String value) {
        deliveryTerm.set(value);
    }

    public StringProperty deliveryTermProperty() {
        return deliveryTerm;
    }

    public List<PemesananPembelianBahanDetail> getListPemesananPembelianBahanDetail() {
        return listPemesananPembelianBahanDetail;
    }

    public void setListPemesananPembelianBahanDetail(List<PemesananPembelianBahanDetail> listPemesananPembelianBahanDetail) {
        this.listPemesananPembelianBahanDetail = listPemesananPembelianBahanDetail;
    }
    
    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
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

    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
    }

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public double getSisaDownPayment() {
        return sisaDownPayment.get();
    }

    public void setSisaDownPayment(double value) {
        sisaDownPayment.set(value);
    }

    public DoubleProperty sisaDownPaymentProperty() {
        return sisaDownPayment;
    }

    public double getDownPayment() {
        return downPayment.get();
    }

    public void setDownPayment(double value) {
        downPayment.set(value);
    }

    public DoubleProperty downPaymentProperty() {
        return downPayment;
    }

    public double getTotalPemesanan() {
        return totalPemesanan.get();
    }

    public void setTotalPemesanan(double value) {
        totalPemesanan.set(value);
    }

    public DoubleProperty totalPemesananProperty() {
        return totalPemesanan;
    }

    public String getPaymentTerm() {
        return paymentTerm.get();
    }

    public void setPaymentTerm(String value) {
        paymentTerm.set(value);
    }

    public StringProperty paymentTermProperty() {
        return paymentTerm;
    }

    public String getNoKontrak() {
        return noKontrak.get();
    }

    public void setNoKontrak(String value) {
        noKontrak.set(value);
    }

    public StringProperty noKontrakProperty() {
        return noKontrak;
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

    public String getTglPemesanan() {
        return tglPemesanan.get();
    }

    public void setTglPemesanan(String value) {
        tglPemesanan.set(value);
    }

    public StringProperty tglPemesananProperty() {
        return tglPemesanan;
    }

    public String getNoPemesanan() {
        return noPemesanan.get();
    }

    public void setNoPemesanan(String value) {
        noPemesanan.set(value);
    }

    public StringProperty noPemesananProperty() {
        return noPemesanan;
    }
    
}
