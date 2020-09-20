/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class PindahBarangHead {

    
    private final StringProperty noPindah = new SimpleStringProperty();
    private final StringProperty tglPindah = new SimpleStringProperty();
    private final StringProperty gudangAsal = new SimpleStringProperty();
    private final StringProperty gudangTujuan = new SimpleStringProperty();
    private final StringProperty supir = new SimpleStringProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<PindahBarangDetail> listPindahBarangDetail;

    public List<PindahBarangDetail> getListPindahBarangDetail() {
        return listPindahBarangDetail;
    }

    public void setListPindahBarangDetail(List<PindahBarangDetail> listPindahBarangDetail) {
        this.listPindahBarangDetail = listPindahBarangDetail;
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

    public String getSupir() {
        return supir.get();
    }

    public void setSupir(String value) {
        supir.set(value);
    }

    public StringProperty supirProperty() {
        return supir;
    }

    public String getGudangTujuan() {
        return gudangTujuan.get();
    }

    public void setGudangTujuan(String value) {
        gudangTujuan.set(value);
    }

    public StringProperty gudangTujuanProperty() {
        return gudangTujuan;
    }

    public String getGudangAsal() {
        return gudangAsal.get();
    }

    public void setGudangAsal(String value) {
        gudangAsal.set(value);
    }

    public StringProperty gudangAsalProperty() {
        return gudangAsal;
    }

    public String getTglPindah() {
        return tglPindah.get();
    }

    public void setTglPindah(String value) {
        tglPindah.set(value);
    }

    public StringProperty tglPindahProperty() {
        return tglPindah;
    }

    public String getNoPindah() {
        return noPindah.get();
    }

    public void setNoPindah(String value) {
        noPindah.set(value);
    }

    public StringProperty noPindahProperty() {
        return noPindah;
    }
    
}
