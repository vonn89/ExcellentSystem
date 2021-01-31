/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.Model.Helper;

import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import java.util.List;

/**
 *
 * @author excellent
 */
public class DashboardModel {
    private List<PenjualanBarangHead> listPenjualanHead;
    private List<PenjualanBahanHead> listPenjualanCoilHead;
    private List<TopCustomer> listTopCustomer;
    private List<Pegawai> listSales;
    private List<Keuangan> listKeuangan;
    private List<BestSellingItems> listBestSellingItems;
    private List<PendingItems> listPendingItems;

    public List<PendingItems> getListPendingItems() {
        return listPendingItems;
    }

    public void setListPendingItems(List<PendingItems> listPendingItems) {
        this.listPendingItems = listPendingItems;
    }
    
    public List<BestSellingItems> getListBestSellingItems() {
        return listBestSellingItems;
    }

    public void setListBestSellingItems(List<BestSellingItems> listBestSellingItems) {
        this.listBestSellingItems = listBestSellingItems;
    }
    
    public List<TopCustomer> getListTopCustomer() {
        return listTopCustomer;
    }

    public void setListTopCustomer(List<TopCustomer> listTopCustomer) {
        this.listTopCustomer = listTopCustomer;
    }

    public List<Keuangan> getListKeuangan() {
        return listKeuangan;
    }

    public void setListKeuangan(List<Keuangan> listKeuangan) {
        this.listKeuangan = listKeuangan;
    }
    
    
    public List<Pegawai> getListSales() {
        return listSales;
    }

    public void setListSales(List<Pegawai> listSales) {
        this.listSales = listSales;
    }
    
    public List<PenjualanBarangHead> getListPenjualanHead() {
        return listPenjualanHead;
    }

    public void setListPenjualanHead(List<PenjualanBarangHead> listPenjualanHead) {
        this.listPenjualanHead = listPenjualanHead;
    }

    public List<PenjualanBahanHead> getListPenjualanCoilHead() {
        return listPenjualanCoilHead;
    }

    public void setListPenjualanCoilHead(List<PenjualanBahanHead> listPenjualanCoilHead) {
        this.listPenjualanCoilHead = listPenjualanCoilHead;
    }
    
}
