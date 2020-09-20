/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.DAO;

import com.excellentsystem.PasarBaja.Model.PembelianBarangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianBarangDetailDAO {
    public static List<PembelianBarangDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_barang_detail "
            + " where no_pembelian in (select no_pembelian from tt_pembelian_barang_head "
            + " where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PembelianBarangDetail> allDetail = new ArrayList<>();
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PembelianBarangDetail d = new PembelianBarangDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setNilai(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            d.setTotal(rs.getDouble(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PembelianBarangDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_barang_detail where no_pembelian=?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<PembelianBarangDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianBarangDetail d = new PembelianBarangDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setNilai(rs.getDouble(8));
            d.setHargaBeli(rs.getDouble(9));
            d.setTotal(rs.getDouble(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PembelianBarangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_barang_detail values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPembelian());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeBarang());
        ps.setString(4, d.getNamaBarang());
        ps.setString(5, d.getKeterangan());
        ps.setString(6, d.getSatuan());
        ps.setDouble(7, d.getQty());
        ps.setDouble(8, d.getNilai());
        ps.setDouble(9, d.getHargaBeli());
        ps.setDouble(10, d.getTotal());
        ps.executeUpdate();
    }
}
