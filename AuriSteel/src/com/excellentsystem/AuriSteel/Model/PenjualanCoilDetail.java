/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Xtreme
 */
public class PenjualanCoilDetail {
    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty kodeBahan = new SimpleStringProperty();
    private final StringProperty namaBahan = new SimpleStringProperty();
    private final StringProperty spesifikasi = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty beratKotor = new SimpleDoubleProperty();
    private final DoubleProperty beratBersih = new SimpleDoubleProperty();
    private final DoubleProperty panjang = new SimpleDoubleProperty();
    private final DoubleProperty nilai = new SimpleDoubleProperty();
    private final DoubleProperty hargaJual = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private PenjualanCoilHead penjualanCoilHead;
    private Bahan bahan;

    public Bahan getBahan() {
        return bahan;
    }

    public void setBahan(Bahan bahan) {
        this.bahan = bahan;
    }
    
    public PenjualanCoilHead getPenjualanCoilHead() {
        return penjualanCoilHead;
    }

    public void setPenjualanCoilHead(PenjualanCoilHead penjualanCoilHead) {
        this.penjualanCoilHead = penjualanCoilHead;
    }
    
    public double getPanjang() {
        return panjang.get();
    }

    public void setPanjang(double value) {
        panjang.set(value);
    }

    public DoubleProperty panjangProperty() {
        return panjang;
    }

    public double getBeratBersih() {
        return beratBersih.get();
    }

    public void setBeratBersih(double value) {
        beratBersih.set(value);
    }

    public DoubleProperty beratBersihProperty() {
        return beratBersih;
    }

    public double getBeratKotor() {
        return beratKotor.get();
    }

    public void setBeratKotor(double value) {
        beratKotor.set(value);
    }

    public DoubleProperty beratKotorProperty() {
        return beratKotor;
    }

    public String getSpesifikasi() {
        return spesifikasi.get();
    }

    public void setSpesifikasi(String value) {
        spesifikasi.set(value);
    }

    public StringProperty spesifikasiProperty() {
        return spesifikasi;
    }

    public String getNamaBahan() {
        return namaBahan.get();
    }

    public void setNamaBahan(String value) {
        namaBahan.set(value);
    }

    public StringProperty namaBahanProperty() {
        return namaBahan;
    }

    public String getKodeBahan() {
        return kodeBahan.get();
    }

    public void setKodeBahan(String value) {
        kodeBahan.set(value);
    }

    public StringProperty kodeBahanProperty() {
        return kodeBahan;
    }


    public double getNilai() {
        return nilai.get();
    }

    public void setNilai(double value) {
        nilai.set(value);
    }

    public DoubleProperty nilaiProperty() {
        return nilai;
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


    public String getKeterangan() {
        return keterangan.get();
    }

    public void setKeterangan(String value) {
        keterangan.set(value);
    }

    public StringProperty keteranganProperty() {
        return keterangan;
    }

    public double getHargaJual() {
        return hargaJual.get();
    }

    public void setHargaJual(double value) {
        hargaJual.set(value);
    }

    public DoubleProperty hargaJualProperty() {
        return hargaJual;
    }

    public double getTotal() {
        return total.get();
    }

    public void setTotal(double value) {
        total.set(value);
    }

    public DoubleProperty totalProperty() {
        return total;
    }
    
}
