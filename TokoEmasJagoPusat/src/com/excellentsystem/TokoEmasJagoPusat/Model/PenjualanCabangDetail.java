/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

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
public class PenjualanCabangDetail {
    //tt_penjualan_cabang_detail
    private final StringProperty noPenjualanCabang = new SimpleStringProperty();
    private final IntegerProperty noUrut = new SimpleIntegerProperty();
    private final StringProperty kodeKategori = new SimpleStringProperty();
    private final StringProperty kodeJenis = new SimpleStringProperty();
    private final IntegerProperty qty = new SimpleIntegerProperty();
    private final DoubleProperty berat = new SimpleDoubleProperty();
    private final DoubleProperty persentaseEmas = new SimpleDoubleProperty();
    private final DoubleProperty beratPersen = new SimpleDoubleProperty();
    private final DoubleProperty hargaPersen = new SimpleDoubleProperty();
    private final DoubleProperty totalHargaPersen = new SimpleDoubleProperty();
    private final DoubleProperty totalNilai = new SimpleDoubleProperty();
    private final DoubleProperty totalHargaRp = new SimpleDoubleProperty();

    public double getTotalNilai() {
        return totalNilai.get();
    }

    public void setTotalNilai(double value) {
        totalNilai.set(value);
    }

    public DoubleProperty totalNilaiProperty() {
        return totalNilai;
    }

    public double getTotalHargaRp() {
        return totalHargaRp.get();
    }

    public void setTotalHargaRp(double value) {
        totalHargaRp.set(value);
    }

    public DoubleProperty totalHargaRpProperty() {
        return totalHargaRp;
    }

    public double getTotalHargaPersen() {
        return totalHargaPersen.get();
    }

    public void setTotalHargaPersen(double value) {
        totalHargaPersen.set(value);
    }

    public DoubleProperty totalHargaPersenProperty() {
        return totalHargaPersen;
    }

    public double getHargaPersen() {
        return hargaPersen.get();
    }

    public void setHargaPersen(double value) {
        hargaPersen.set(value);
    }

    public DoubleProperty hargaPersenProperty() {
        return hargaPersen;
    }

    public double getPersentaseEmas() {
        return persentaseEmas.get();
    }

    public void setPersentaseEmas(double value) {
        persentaseEmas.set(value);
    }

    public DoubleProperty persentaseEmasProperty() {
        return persentaseEmas;
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

    public double getBeratPersen() {
        return beratPersen.get();
    }

    public void setBeratPersen(double value) {
        beratPersen.set(value);
    }

    public DoubleProperty beratPersenProperty() {
        return beratPersen;
    }
    
    public double getBerat() {
        return berat.get();
    }

    public void setBerat(double value) {
        berat.set(value);
    }

    public DoubleProperty beratProperty() {
        return berat;
    }


    public int getQty() {
        return qty.get();
    }

    public void setQty(int value) {
        qty.set(value);
    }

    public IntegerProperty qtyProperty() {
        return qty;
    }

    public String getKodeKategori() {
        return kodeKategori.get();
    }

    public void setKodeKategori(String value) {
        kodeKategori.set(value);
    }

    public StringProperty kodeKategoriProperty() {
        return kodeKategori;
    }

    public String getKodeJenis() {
        return kodeJenis.get();
    }

    public void setKodeJenis(String value) {
        kodeJenis.set(value);
    }

    public StringProperty kodeJenisProperty() {
        return kodeJenis;
    }

    public String getNoPenjualanCabang() {
        return noPenjualanCabang.get();
    }

    public void setNoPenjualanCabang(String value) {
        noPenjualanCabang.set(value);
    }

    public StringProperty noPenjualanCabangProperty() {
        return noPenjualanCabang;
    }
}
