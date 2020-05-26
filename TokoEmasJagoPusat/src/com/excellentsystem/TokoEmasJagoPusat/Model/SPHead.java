/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class SPHead {
    //tt_sp_head
    private final StringProperty noSP = new SimpleStringProperty();
    private final StringProperty tglSP = new SimpleStringProperty();
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty jenisSP = new SimpleStringProperty();
    private final IntegerProperty totalQty = new SimpleIntegerProperty();
    private final DoubleProperty totalBerat = new SimpleDoubleProperty();
    private final DoubleProperty totalNilaiPokok = new SimpleDoubleProperty();
    private final DoubleProperty totalBeratPenyusutan = new SimpleDoubleProperty();
    private final DoubleProperty totalNilaiPenyusutan = new SimpleDoubleProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty statusSelesai = new SimpleStringProperty();
    private final StringProperty tglSelesai = new SimpleStringProperty();
    private final StringProperty userSelesai = new SimpleStringProperty();
    private final StringProperty statusBatal = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    
    private List<SPDetail> listSPDetail;

    public List<SPDetail> getListSPDetail() {
        return listSPDetail;
    }

    public void setListSPDetail(List<SPDetail> listSPDetail) {
        this.listSPDetail = listSPDetail;
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

    public String getStatusSelesai() {
        return statusSelesai.get();
    }

    public void setStatusSelesai(String value) {
        statusSelesai.set(value);
    }

    public StringProperty statusSelesaiProperty() {
        return statusSelesai;
    }

    public double getTotalNilaiPenyusutan() {
        return totalNilaiPenyusutan.get();
    }

    public void setTotalNilaiPenyusutan(double value) {
        totalNilaiPenyusutan.set(value);
    }

    public DoubleProperty totalNilaiPenyusutanProperty() {
        return totalNilaiPenyusutan;
    }

    public double getTotalBeratPenyusutan() {
        return totalBeratPenyusutan.get();
    }

    public void setTotalBeratPenyusutan(double value) {
        totalBeratPenyusutan.set(value);
    }

    public DoubleProperty totalBeratPenyusutanProperty() {
        return totalBeratPenyusutan;
    }

    public double getTotalNilaiPokok() {
        return totalNilaiPokok.get();
    }

    public void setTotalNilaiPokok(double value) {
        totalNilaiPokok.set(value);
    }

    public DoubleProperty totalNilaiPokokProperty() {
        return totalNilaiPokok;
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


    public String getKodeUser() {
        return kodeUser.get();
    }

    public void setKodeUser(String value) {
        kodeUser.set(value);
    }

    public StringProperty kodeUserProperty() {
        return kodeUser;
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

    public int getTotalQty() {
        return totalQty.get();
    }

    public void setTotalQty(int value) {
        totalQty.set(value);
    }

    public IntegerProperty totalQtyProperty() {
        return totalQty;
    }

    public String getJenisSP() {
        return jenisSP.get();
    }

    public void setJenisSP(String value) {
        jenisSP.set(value);
    }

    public StringProperty jenisSPProperty() {
        return jenisSP;
    }

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
    }

    public String getTglSP() {
        return tglSP.get();
    }

    public void setTglSP(String value) {
        tglSP.set(value);
    }

    public StringProperty tglSPProperty() {
        return tglSP;
    }

    public String getNoSP() {
        return noSP.get();
    }

    public void setNoSP(String value) {
        noSP.set(value);
    }

    public StringProperty noSPProperty() {
        return noSP;
    }
}
