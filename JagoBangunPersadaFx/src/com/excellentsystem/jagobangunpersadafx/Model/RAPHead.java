/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class RAPHead {

    private final StringProperty noRap = new SimpleStringProperty();
    private final StringProperty tglRap = new SimpleStringProperty();
    private final StringProperty metodePembagian = new SimpleStringProperty();
    private final IntegerProperty perkiraanWaktu = new SimpleIntegerProperty();
    private final StringProperty tglMulai = new SimpleStringProperty();
    private final DoubleProperty totalProperty = new SimpleDoubleProperty();
    private final DoubleProperty totalAnggaran = new SimpleDoubleProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusApproval = new SimpleStringProperty();
    private final StringProperty tglApproval = new SimpleStringProperty();
    private final StringProperty userApproval = new SimpleStringProperty();
    private final StringProperty statusSelesai = new SimpleStringProperty();
    private final StringProperty tglSelesai = new SimpleStringProperty();
    private final StringProperty userSelesai = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty kategoriPembangunan = new SimpleStringProperty();
    private List<RAPDetail> listRapDetail;
    private List<RAPDetailProperty> listRapPropertyDetail;
    private List<RAPRealisasi> listRapRealisasi;

    public List<RAPRealisasi> getListRapRealisasi() {
        return listRapRealisasi;
    }

    public void setListRapRealisasi(List<RAPRealisasi> listRapRealisasi) {
        this.listRapRealisasi = listRapRealisasi;
    }
    
    public List<RAPDetailProperty> getListRapPropertyDetail() {
        return listRapPropertyDetail;
    }

    public void setListRapPropertyDetail(List<RAPDetailProperty> listRapPropertyDetail) {
        this.listRapPropertyDetail = listRapPropertyDetail;
    }
    
    public List<RAPDetail> getListRapDetail() {
        return listRapDetail;
    }

    public void setListRapDetail(List<RAPDetail> listRapDetail) {
        this.listRapDetail = listRapDetail;
    }
    
    public String getKategoriPembangunan() {
        return kategoriPembangunan.get();
    }

    public void setKategoriPembangunan(String value) {
        kategoriPembangunan.set(value);
    }

    public StringProperty kategoriPembangunanProperty() {
        return kategoriPembangunan;
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

    public String getStatusBatal() {
        return statusBatal.get();
    }

    public void setStatusBatal(String value) {
        statusBatal.set(value);
    }

    public StringProperty statusBatalProperty() {
        return statusBatal;
    }

    public String getUserSelesai() {
        return userSelesai.get();
    }

    public void setUserSelesai(String value) {
        userSelesai.set(value);
    }

    public StringProperty userSelesaiProperty() {
        return userSelesai;
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

    public String getStatusSelesai() {
        return statusSelesai.get();
    }

    public void setStatusSelesai(String value) {
        statusSelesai.set(value);
    }

    public StringProperty statusSelesaiProperty() {
        return statusSelesai;
    }

    public String getUserApproval() {
        return userApproval.get();
    }

    public void setUserApproval(String value) {
        userApproval.set(value);
    }

    public StringProperty userApprovalProperty() {
        return userApproval;
    }

    public String getTglApproval() {
        return tglApproval.get();
    }

    public void setTglApproval(String value) {
        tglApproval.set(value);
    }

    public StringProperty tglApprovalProperty() {
        return tglApproval;
    }

    public String getStatusApproval() {
        return statusApproval.get();
    }

    public void setStatusApproval(String value) {
        statusApproval.set(value);
    }

    public StringProperty statusApprovalProperty() {
        return statusApproval;
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

    public double getTotalAnggaran() {
        return totalAnggaran.get();
    }

    public void setTotalAnggaran(double value) {
        totalAnggaran.set(value);
    }

    public DoubleProperty totalAnggaranProperty() {
        return totalAnggaran;
    }

    public double getTotalProperty() {
        return totalProperty.get();
    }

    public void setTotalProperty(double value) {
        totalProperty.set(value);
    }

    public DoubleProperty totalPropertyProperty() {
        return totalProperty;
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

    public int getPerkiraanWaktu() {
        return perkiraanWaktu.get();
    }

    public void setPerkiraanWaktu(int value) {
        perkiraanWaktu.set(value);
    }

    public IntegerProperty perkiraanWaktuProperty() {
        return perkiraanWaktu;
    }

    public String getMetodePembagian() {
        return metodePembagian.get();
    }

    public void setMetodePembagian(String value) {
        metodePembagian.set(value);
    }

    public StringProperty metodePembagianProperty() {
        return metodePembagian;
    }

    public String getTglRap() {
        return tglRap.get();
    }

    public void setTglRap(String value) {
        tglRap.set(value);
    }

    public StringProperty tglRapProperty() {
        return tglRap;
    }

    public String getNoRap() {
        return noRap.get();
    }

    public void setNoRap(String value) {
        noRap.set(value);
    }

    public StringProperty noRapProperty() {
        return noRap;
    }
    
}
