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
 * @author Xtreme
 */
public class PenjualanHead {
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty tglPenjualan = new SimpleStringProperty();
    private final StringProperty noPemesanan = new SimpleStringProperty();
    private final StringProperty kodeCustomer = new SimpleStringProperty();
    private final StringProperty kodeCustomerInvoice = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty tujuanKirim = new SimpleStringProperty();
    private final StringProperty supir = new SimpleStringProperty();
    private final StringProperty paymentTerm = new SimpleStringProperty();
    private final DoubleProperty totalPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty totalBebanPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPembayaran = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeSales = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty tglVerifikasi = new SimpleStringProperty();
    private final StringProperty userVerifikasi = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private Customer customer;
    private Pegawai sales;
    private List<PenjualanDetail> listPenjualanDetail;
    private PemesananHead pemesananHead;
    private Customer customerInvoice;

    public String getUserVerifikasi() {
        return userVerifikasi.get();
    }

    public void setUserVerifikasi(String value) {
        userVerifikasi.set(value);
    }

    public StringProperty userVerifikasiProperty() {
        return userVerifikasi;
    }

    public String getTglVerifikasi() {
        return tglVerifikasi.get();
    }

    public void setTglVerifikasi(String value) {
        tglVerifikasi.set(value);
    }

    public StringProperty tglVerifikasiProperty() {
        return tglVerifikasi;
    }

    public double getTotalBebanPenjualan() {
        return totalBebanPenjualan.get();
    }

    public void setTotalBebanPenjualan(double value) {
        totalBebanPenjualan.set(value);
    }

    public DoubleProperty totalBebanPenjualanProperty() {
        return totalBebanPenjualan;
    }


    public String getKodeCustomerInvoice() {
        return kodeCustomerInvoice.get();
    }

    public void setKodeCustomerInvoice(String value) {
        kodeCustomerInvoice.set(value);
    }

    public StringProperty kodeCustomerInvoiceProperty() {
        return kodeCustomerInvoice;
    }

    public Customer getCustomerInvoice() {
        return customerInvoice;
    }

    public void setCustomerInvoice(Customer customerInvoice) {
        this.customerInvoice = customerInvoice;
    }
    
    public String getTujuanKirim() {
        return tujuanKirim.get();
    }

    public void setTujuanKirim(String value) {
        tujuanKirim.set(value);
    }

    public StringProperty tujuanKirimProperty() {
        return tujuanKirim;
    }

    public String getKodeGudang() {
        return kodeGudang.get();
    }

    public void setKodeGudang(String value) {
        kodeGudang.set(value);
    }

    public StringProperty kodeGudangProperty() {
        return kodeGudang;
    }

    public String getSupir() {
        return supir.get();
    }

    public void setSupir(String value) {
        supir.set(value);
    }

    public StringProperty supirProperty() {
        return supir;
    }

    public PemesananHead getPemesananHead() {
        return pemesananHead;
    }

    public void setPemesananHead(PemesananHead pemesananHead) {
        this.pemesananHead = pemesananHead;
    }
    
    public List<PenjualanDetail> getListPenjualanDetail() {
        return listPenjualanDetail;
    }

    public void setListPenjualanDetail(List<PenjualanDetail> listPenjualanDetail) {
        this.listPenjualanDetail = listPenjualanDetail;
    }
    
    public Pegawai getSales() {
        return sales;
    }

    public void setSales(Pegawai sales) {
        this.sales = sales;
    }
    
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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

    public String getPaymentTerm() {
        return paymentTerm.get();
    }

    public void setPaymentTerm(String value) {
        paymentTerm.set(value);
    }

    public StringProperty paymentTermProperty() {
        return paymentTerm;
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
    

    public String getNoPenjualan() {
        return noPenjualan.get();
    }

    public void setNoPenjualan(String value) {
        noPenjualan.set(value);
    }

    public StringProperty noPenjualanProperty() {
        return noPenjualan;
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

    public String getKodeCustomer() {
        return kodeCustomer.get();
    }

    public void setKodeCustomer(String value) {
        kodeCustomer.set(value);
    }

    public StringProperty kodeCustomerProperty() {
        return kodeCustomer;
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

    public double getPembayaran() {
        return pembayaran.get();
    }

    public void setPembayaran(double value) {
        pembayaran.set(value);
    }

    public DoubleProperty pembayaranProperty() {
        return pembayaran;
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

    public String getKodeSales() {
        return kodeSales.get();
    }

    public void setKodeSales(String value) {
        kodeSales.set(value);
    }

    public StringProperty kodeSalesProperty() {
        return kodeSales;
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

    public String getTglBatal() {
        return tglBatal.get();
    }

    public void setTglBatal(String value) {
        tglBatal.set(value);
    }

    public StringProperty tglBatalProperty() {
        return tglBatal;
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
