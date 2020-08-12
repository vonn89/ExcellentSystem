/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author excellent
 */
public class PenjualanDetail {

    private final StringProperty noPenjualan = new SimpleStringProperty();
    private final StringProperty noUrut = new SimpleStringProperty();
    private final StringProperty kodePekerjaan = new SimpleStringProperty();
    private final StringProperty namaPekerjaan = new SimpleStringProperty();
    private final StringProperty keterangan = new SimpleStringProperty();
    private final DoubleProperty qty = new SimpleDoubleProperty();
    private final DoubleProperty harga = new SimpleDoubleProperty();
    private final DoubleProperty total = new SimpleDoubleProperty();
    private final DoubleProperty waktuPengerjaan = new SimpleDoubleProperty();
    private final DoubleProperty totalAnggaranBebanMaterial = new SimpleDoubleProperty();
    private final DoubleProperty totalAnggaranBebanPenjualan = new SimpleDoubleProperty();
    private final DoubleProperty totalBebanMaterial = new SimpleDoubleProperty();
    private final DoubleProperty totalBebanPenjualan = new SimpleDoubleProperty();
    private Pekerjaan pekerjaan;
    private List<RencanaAnggaranBebanMaterial> rencanaAnggaranBelanja;
    private List<RencanaAnggaranBebanPenjualan> rencanaAnggaranBebanPenjualan;
    private List<BebanPenjualan> bebanPenjualan;
    private List<BebanMaterial> bebanMaterial;
    private PenjualanHead penjualanHead;

    public double getTotalBebanPenjualan() {
        return totalBebanPenjualan.get();
    }

    public void setTotalBebanPenjualan(double value) {
        totalBebanPenjualan.set(value);
    }

    public DoubleProperty totalBebanPenjualanProperty() {
        return totalBebanPenjualan;
    }

    public double getTotalBebanMaterial() {
        return totalBebanMaterial.get();
    }

    public void setTotalBebanMaterial(double value) {
        totalBebanMaterial.set(value);
    }

    public DoubleProperty totalBebanMaterialProperty() {
        return totalBebanMaterial;
    }

    public double getTotalAnggaranBebanPenjualan() {
        return totalAnggaranBebanPenjualan.get();
    }

    public void setTotalAnggaranBebanPenjualan(double value) {
        totalAnggaranBebanPenjualan.set(value);
    }

    public DoubleProperty totalAnggaranBebanPenjualanProperty() {
        return totalAnggaranBebanPenjualan;
    }

    public double getTotalAnggaranBebanMaterial() {
        return totalAnggaranBebanMaterial.get();
    }

    public void setTotalAnggaranBebanMaterial(double value) {
        totalAnggaranBebanMaterial.set(value);
    }

    public DoubleProperty totalAnggaranBebanMaterialProperty() {
        return totalAnggaranBebanMaterial;
    }

    public List<BebanPenjualan> getBebanPenjualan() {
        return bebanPenjualan;
    }

    public void setBebanPenjualan(List<BebanPenjualan> bebanPenjualan) {
        this.bebanPenjualan = bebanPenjualan;
    }

    public List<BebanMaterial> getBebanMaterial() {
        return bebanMaterial;
    }

    public void setBebanMaterial(List<BebanMaterial> bebanMaterial) {
        this.bebanMaterial = bebanMaterial;
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

    public PenjualanHead getPenjualanHead() {
        return penjualanHead;
    }

    public void setPenjualanHead(PenjualanHead penjualanHead) {
        this.penjualanHead = penjualanHead;
    }
    
    public List<RencanaAnggaranBebanMaterial> getRencanaAnggaranBelanja() {
        return rencanaAnggaranBelanja;
    }

    public void setRencanaAnggaranBelanja(List<RencanaAnggaranBebanMaterial> rencanaAnggaranBelanja) {
        this.rencanaAnggaranBelanja = rencanaAnggaranBelanja;
    }

    public List<RencanaAnggaranBebanPenjualan> getRencanaAnggaranBebanPenjualan() {
        return rencanaAnggaranBebanPenjualan;
    }

    public void setRencanaAnggaranBebanPenjualan(List<RencanaAnggaranBebanPenjualan> rencanaAnggaranBebanPenjualan) {
        this.rencanaAnggaranBebanPenjualan = rencanaAnggaranBebanPenjualan;
    }

    
    public double getWaktuPengerjaan() {
        return waktuPengerjaan.get();
    }

    public void setWaktuPengerjaan(double value) {
        waktuPengerjaan.set(value);
    }

    public DoubleProperty waktuPengerjaanProperty() {
        return waktuPengerjaan;
    }

    
    public Pekerjaan getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(Pekerjaan pekerjaan) {
        this.pekerjaan = pekerjaan;
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

    public String getNamaPekerjaan() {
        return namaPekerjaan.get();
    }

    public void setNamaPekerjaan(String value) {
        namaPekerjaan.set(value);
    }

    public StringProperty namaPekerjaanProperty() {
        return namaPekerjaan;
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

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double value) {
        harga.set(value);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }

    public double getQty() {
        return qty.get();
    }

    public void setQty(double value) {
        qty.set(value);
    }

    public DoubleProperty qtyProperty() {
        return qty;
    }

    public String getKodePekerjaan() {
        return kodePekerjaan.get();
    }

    public void setKodePekerjaan(String value) {
        kodePekerjaan.set(value);
    }

    public StringProperty kodePekerjaanProperty() {
        return kodePekerjaan;
    }

    public String getNoUrut() {
        return noUrut.get();
    }

    public void setNoUrut(String value) {
        noUrut.set(value);
    }

    public StringProperty noUrutProperty() {
        return noUrut;
    }

    
}
