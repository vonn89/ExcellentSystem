/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class ProduksiHead {
    private final StringProperty kodeProduksi = new SimpleStringProperty();
    private final StringProperty tglProduksi = new SimpleStringProperty();
    private final StringProperty kodeGudang = new SimpleStringProperty();
    private final StringProperty jenisProduksi = new SimpleStringProperty();
    private final DoubleProperty materialCost = new SimpleDoubleProperty();
    private final DoubleProperty biayaProduksi = new SimpleDoubleProperty();
    private final StringProperty catatan = new SimpleStringProperty();
    private final StringProperty kodeUser = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private List<ProduksiDetailBarang> listProduksiDetailBarang;
    private List<ProduksiDetailBahan> listProduksiDetailBahan;
    private List<ProduksiOperator> listProduksiOperator;

    public List<ProduksiOperator> getListProduksiOperator() {
        return listProduksiOperator;
    }

    public void setListProduksiOperator(List<ProduksiOperator> listProduksiOperator) {
        this.listProduksiOperator = listProduksiOperator;
    }

    public double getBiayaProduksi() {
        return biayaProduksi.get();
    }

    public void setBiayaProduksi(double value) {
        biayaProduksi.set(value);
    }

    public DoubleProperty biayaProduksiProperty() {
        return biayaProduksi;
    }

    public String getJenisProduksi() {
        return jenisProduksi.get();
    }

    public void setJenisProduksi(String value) {
        jenisProduksi.set(value);
    }

    public StringProperty jenisProduksiProperty() {
        return jenisProduksi;
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

    

    public List<ProduksiDetailBahan> getListProduksiDetailBahan() {
        return listProduksiDetailBahan;
    }

    public void setListProduksiDetailBahan(List<ProduksiDetailBahan> listProduksiDetailBahan) {
        this.listProduksiDetailBahan = listProduksiDetailBahan;
    }
    
    

    public List<ProduksiDetailBarang> getListProduksiDetailBarang() {
        return listProduksiDetailBarang;
    }

    public void setListProduksiDetailBarang(List<ProduksiDetailBarang> listProduksiDetailBarang) {
        this.listProduksiDetailBarang = listProduksiDetailBarang;
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
    
    

    public double getMaterialCost() {
        return materialCost.get();
    }

    public void setMaterialCost(double value) {
        materialCost.set(value);
    }

    public DoubleProperty materialCostProperty() {
        return materialCost;
    }

    
    

    public String getTglProduksi() {
        return tglProduksi.get();
    }

    public void setTglProduksi(String value) {
        tglProduksi.set(value);
    }

    public StringProperty tglProduksiProperty() {
        return tglProduksi;
    }
    

    public String getKodeProduksi() {
        return kodeProduksi.get();
    }

    public void setKodeProduksi(String value) {
        kodeProduksi.set(value);
    }

    public StringProperty kodeProduksiProperty() {
        return kodeProduksi;
    }
    
}
