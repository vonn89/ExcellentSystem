/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.PemesananCoilDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PemesananCoilDetailDAO {
    public static List<PemesananCoilDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pemesanan_coil_detail "
                + " where no_pemesanan in ( select no_pemesanan from tt_pemesanan_coil_head "
                + " where left(tgl_pemesanan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PemesananCoilDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PemesananCoilDetail d = new PemesananCoilDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setKategoriBahan(rs.getString(2));
            d.setNamaBarang(rs.getString(3));
            d.setSpesifikasi(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setQty(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
            d.setTotal(rs.getDouble(8));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PemesananCoilDetail> getAllByNoPemesanan(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_coil_detail where no_pemesanan=?");
        ps.setString(1, noPemesanan);
        ResultSet rs = ps.executeQuery();
        List<PemesananCoilDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PemesananCoilDetail d = new PemesananCoilDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setKategoriBahan(rs.getString(2));
            d.setNamaBarang(rs.getString(3));
            d.setSpesifikasi(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setQty(rs.getDouble(6));
            d.setHarga(rs.getDouble(7));
            d.setTotal(rs.getDouble(8));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PemesananCoilDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_coil_detail values(?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPemesanan());
        ps.setString(2, d.getKategoriBahan());
        ps.setString(3, d.getNamaBarang());
        ps.setString(4, d.getSpesifikasi());
        ps.setString(5, d.getKeterangan());
        ps.setDouble(6, d.getQty());
        ps.setDouble(7, d.getHarga());
        ps.setDouble(8, d.getTotal());
        ps.executeUpdate();
    }
}
