/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class BatalBarcode {

    private final StringProperty kodeBarcode = new SimpleStringProperty();
    private final StringProperty tglBatal = new SimpleStringProperty();
    private final StringProperty userBatal = new SimpleStringProperty();
    private BarangCabang barangCabang;
    private StokBarangCabang stokBarangCabang;

    public StokBarangCabang getStokBarangCabang() {
        return stokBarangCabang;
    }

    public void setStokBarangCabang(StokBarangCabang stokBarangCabang) {
        this.stokBarangCabang = stokBarangCabang;
    }
    
    
    public BarangCabang getBarangCabang() {
        return barangCabang;
    }

    public void setBarangCabang(BarangCabang barangCabang) {
        this.barangCabang = barangCabang;
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

    public String getKodeBarcode() {
        return kodeBarcode.get();
    }

    public void setKodeBarcode(String value) {
        kodeBarcode.set(value);
    }

    public StringProperty kodeBarcodeProperty() {
        return kodeBarcode;
    }
    
}
