/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class ProduksiDetailBarangDAO {
    public static List<ProduksiDetailBarang> getAllByDateAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_detail_barang "
                + " where kode_produksi in (select kode_produksi from tt_produksi_head"
                + " where left(tgl_produksi,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<ProduksiDetailBarang> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiDetailBarang d = new ProduksiDetailBarang();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeBarang(rs.getString(2));
            d.setQty(rs.getDouble(3));
            d.setNilai(rs.getDouble(4));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<ProduksiDetailBarang> getAllByTglMulaiAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_detail_barang "
                + " where kode_produksi in (select kode_produksi from tt_produksi_head"
                + " where left(tgl_mulai,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<ProduksiDetailBarang> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiDetailBarang d = new ProduksiDetailBarang();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeBarang(rs.getString(2));
            d.setQty(rs.getDouble(3));
            d.setNilai(rs.getDouble(4));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<ProduksiDetailBarang> get(Connection con, String kodeProduksi)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_produksi_detail_barang "
                + " where kode_produksi = ?");
        ps.setString(1, kodeProduksi);
        ResultSet rs = ps.executeQuery();
        List<ProduksiDetailBarang> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiDetailBarang d = new ProduksiDetailBarang();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeBarang(rs.getString(2));
            d.setQty(rs.getDouble(3));
            d.setNilai(rs.getDouble(4));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, ProduksiDetailBarang d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_produksi_detail_barang values(?,?,?,?)");
        ps.setString(1, d.getKodeProduksi());
        ps.setString(2, d.getKodeBarang());
        ps.setDouble(3, d.getQty());
        ps.setDouble(4, d.getNilai());
        ps.executeUpdate();
    }
    public static void update(Connection con, ProduksiDetailBarang d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_produksi_detail_barang set "
                + " qty = ? , nilai = ? where kode_produksi = ? and kode_barang = ?");
        ps.setDouble(1, d.getQty());
        ps.setDouble(2, d.getNilai());
        ps.setString(3, d.getKodeProduksi());
        ps.setString(4, d.getKodeBarang());
        ps.executeUpdate();
    }
}
