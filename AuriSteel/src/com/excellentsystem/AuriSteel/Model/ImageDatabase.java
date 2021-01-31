/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ASUS
 */
public class ImageDatabase {

    private final StringProperty noTransaksi = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty jenisTransaksi = new SimpleStringProperty();
    private final StringProperty downloadUrl = new SimpleStringProperty();

    public String getDownloadUrl() {
        return downloadUrl.get();
    }

    public void setDownloadUrl(String value) {
        downloadUrl.set(value);
    }

    public StringProperty downloadUrlProperty() {
        return downloadUrl;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi.get();
    }

    public void setJenisTransaksi(String value) {
        jenisTransaksi.set(value);
    }

    public StringProperty jenisTransaksiProperty() {
        return jenisTransaksi;
    }

    public int getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(int value) {
        noUrut.set(value);
    }

    public IntegerProperty noUrutProperty() {
        return noUrut;
    }

    public String getNoTransaksi() {
        return noTransaksi.get();
    }

    public void setNoTransaksi(String value) {
        noTransaksi.set(value);
    }

    public StringProperty noTransaksiProperty() {
        return noTransaksi;
    }
    
}
