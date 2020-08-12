/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PenjualanHead {

    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty tglPemesanan = new SimpleStringProperty();
    private final StringProperty tglPenjualan = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty namaProyek = new SimpleStringProperty();
    private final StringProperty lokasiPengerjaan = new SimpleStringProperty();
    private final StringProperty tglMulai = new SimpleStringProperty();
    private final StringProperty tglSelesai = new SimpleStringProperty();
    private final DoubleProperty totalPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPembayaran = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private Customer customer;
    private List<PenjualanDetail> allDetail;
    private List<Hutang> allPembayaran;

    public String getTglPemesanan() {
        return tglPemesanan.get();
    }

    public void setTglPemesanan(String value) {
        tglPemesanan.set(value);
    }

    public StringProperty tglPemesananProperty() {
        return tglPemesanan;
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

    public String getTglMulai() {
        return tglMulai.get();
    }

    public void setTglMulai(String value) {
        tglMulai.set(value);
    }

    public StringProperty tglMulaiProperty() {
        return tglMulai;
    }

    public List<Hutang> getAllPembayaran() {
        return allPembayaran;
    }

    public void setAllPembayaran(List<Hutang> allPembayaran) {
        this.allPembayaran = allPembayaran;
    }
    
    public List<PenjualanDetail> getAllDetail() {
        return allDetail;
    }

    public void setAllDetail(List<PenjualanDetail> allDetail) {
        this.allDetail = allDetail;
    }
    
    public String getTglPenjualan() {
        return tglPenjualan.get();
    }

    public void setTglPenjualan(String value) {
        tglPenjualan.set(value);
    }

    public StringProperty tglPenjualanProperty() {
        return tglPenjualan;
    }

    public String getNoPenjualan() {
        return noPenjualan.get();
    }

    public void setNoPenjualan(String value) {
        noPenjualan.set(value);
    }

    public StringProperty noPenjualanProperty() {
        return noPenjualan;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public String getCatatan() {
        return catatan.get();
    }

    public void setCatatan(String value) {
        catatan.set(value);
    }

    public StringProperty catatanProperty() {
        return catatan;
    }

    public double getSisaPembayaran() {
        return sisaPembayaran.get();
    }

    public void setSisaPembayaran(double value) {
        sisaPembayaran.set(value);
    }

    public DoubleProperty sisaPembayaranProperty() {
        return sisaPembayaran;
    }

    public double getPembayaran() {
        return pembayaran.get();
    }

    public void setPembayaran(double value) {
        pembayaran.set(value);
    }

    public DoubleProperty pembayaranProperty() {
        return pembayaran;
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


    public String getLokasiPengerjaan() {
        return lokasiPengerjaan.get();
    }

    public void setLokasiPengerjaan(String value) {
        lokasiPengerjaan.set(value);
    }

    public StringProperty lokasiPengerjaanProperty() {
        return lokasiPengerjaan;
    }

    public String getNamaProyek() {
        return namaProyek.get();
    }

    public void setNamaProyek(String value) {
        namaProyek.set(value);
    }

    public StringProperty namaProyekProperty() {
        return namaProyek;
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

    
}
