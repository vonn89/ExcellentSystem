/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Excellent
 */
public class PembangunanKontraktorHead {

    private final StringProperty noPembangunan = new SimpleStringProperty();
    private final StringProperty kodeKontraktor = new SimpleStringProperty();
    private final StringProperty kodeProperty = new SimpleStringProperty();
    private final StringProperty kategori = new SimpleStringProperty();
    private final DoubleProperty totalPembangunan = new SimpleDoubleProperty();
    private final DoubleProperty pembayaran = new SimpleDoubleProperty();
    private final DoubleProperty sisaPembayaran = new SimpleDoubleProperty();
    
    private Kontraktor kontraktor;
    private Property property;
    private List<PembangunanKontraktorDetail> listPembangunanKontraktorDetail;
    private Addendum addendum;

    public Addendum getAddendum() {
        return addendum;
    }

    public void setAddendum(Addendum addendum) {
        this.addendum = addendum;
    }
    
    
    public String getKategori() {
        return kategori.get();
    }

    public void setKategori(String value) {
        kategori.set(value);
    }

    public StringProperty kategoriProperty() {
        return kategori;
    }

    public Kontraktor getKontraktor() {
        return kontraktor;
    }

    public void setKontraktor(Kontraktor kontraktor) {
        this.kontraktor = kontraktor;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public List<PembangunanKontraktorDetail> getListPembangunanKontraktorDetail() {
        return listPembangunanKontraktorDetail;
    }

    public void setListPembangunanKontraktorDetail(List<PembangunanKontraktorDetail> listPembangunanKontraktorDetail) {
        this.listPembangunanKontraktorDetail = listPembangunanKontraktorDetail;
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

    public double getTotalPembangunan() {
        return totalPembangunan.get();
    }

    public void setTotalPembangunan(double value) {
        totalPembangunan.set(value);
    }

    public DoubleProperty totalPembangunanProperty() {
        return totalPembangunan;
    }

    public String getKodeProperty() {
        return kodeProperty.get();
    }

    public void setKodeProperty(String value) {
        kodeProperty.set(value);
    }

    public StringProperty kodePropertyProperty() {
        return kodeProperty;
    }

    public String getKodeKontraktor() {
        return kodeKontraktor.get();
    }

    public void setKodeKontraktor(String value) {
        kodeKontraktor.set(value);
    }

    public StringProperty kodeKontraktorProperty() {
        return kodeKontraktor;
    }

    public String getNoPembangunan() {
        return noPembangunan.get();
    }

    public void setNoPembangunan(String value) {
        noPembangunan.set(value);
    }

    public StringProperty noPembangunanProperty() {
        return noPembangunan;
    }
    
}
