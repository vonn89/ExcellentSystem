/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.PindahBarangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PindahBarangDetailDAO {
    
    public static List<PindahBarangDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pindah_barang_detail "
            + " where no_pindah in (select no_pindah from tt_pindah_barang_head "
            + " where left(tgl_pindah,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PindahBarangDetail> allDetail = new ArrayList<>();
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PindahBarangDetail d = new PindahBarangDetail();
            d.setNoPindah(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setNilai(rs.getDouble(8));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PindahBarangDetail> getAllPindahBarangDetail(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_barang_detail where no_pindah=?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        List<PindahBarangDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PindahBarangDetail d = new PindahBarangDetail();
            d.setNoPindah(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setNamaBarang(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setNilai(rs.getDouble(8));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PindahBarangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_barang_detail values(?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPindah());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeBarang());
        ps.setString(4, d.getNamaBarang());
        ps.setString(5, d.getKeterangan());
        ps.setString(6, d.getSatuan());
        ps.setDouble(7, d.getQty());
        ps.setDouble(8, d.getNilai());
        ps.executeUpdate();
    }
}
