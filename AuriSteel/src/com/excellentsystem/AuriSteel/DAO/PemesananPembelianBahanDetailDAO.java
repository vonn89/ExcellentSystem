/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PemesananPembelianBahanDetailDAO {
    
    public static List<PemesananPembelianBahanDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pemesanan_pembelian_bahan_detail "
                + " where no_pemesanan in ( select no_pemesanan from tt_pemesanan_pembelian_bahan_head "
                + " where left(tgl_pemesanan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PemesananPembelianBahanDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PemesananPembelianBahanDetail d = new PemesananPembelianBahanDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKategoriBahan(rs.getString(3));
            d.setNamaBahan(rs.getString(4));
            d.setSpesifikasi(rs.getString(5));
            d.setKeterangan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setQtyDiterima(rs.getDouble(8));
            d.setHarga(rs.getDouble(9));
            d.setTotal(rs.getDouble(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PemesananPembelianBahanDetail> getAllByNoPemesanan(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_pembelian_bahan_detail where no_pemesanan=?");
        ps.setString(1, noPemesanan);
        ResultSet rs = ps.executeQuery();
        List<PemesananPembelianBahanDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PemesananPembelianBahanDetail d = new PemesananPembelianBahanDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKategoriBahan(rs.getString(3));
            d.setNamaBahan(rs.getString(4));
            d.setSpesifikasi(rs.getString(5));
            d.setKeterangan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setQtyDiterima(rs.getDouble(8));
            d.setHarga(rs.getDouble(9));
            d.setTotal(rs.getDouble(10));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static PemesananPembelianBahanDetail get(Connection con, String noPemesanan, int noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_pembelian_bahan_detail "
                + " where no_pemesanan=? and no_urut=? ");
        ps.setString(1, noPemesanan);
        ps.setInt(2, noUrut);
        ResultSet rs = ps.executeQuery();
        PemesananPembelianBahanDetail d = null;
        while(rs.next()){
            d = new PemesananPembelianBahanDetail();
            d.setNoPemesanan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKategoriBahan(rs.getString(3));
            d.setNamaBahan(rs.getString(4));
            d.setSpesifikasi(rs.getString(5));
            d.setKeterangan(rs.getString(6));
            d.setQty(rs.getDouble(7));
            d.setQtyDiterima(rs.getDouble(8));
            d.setHarga(rs.getDouble(9));
            d.setTotal(rs.getDouble(10));
        }
        return d;
    }
    public static void insert(Connection con, PemesananPembelianBahanDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_pembelian_bahan_detail values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPemesanan());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKategoriBahan());
        ps.setString(4, d.getNamaBahan());
        ps.setString(5, d.getSpesifikasi());
        ps.setString(6, d.getKeterangan());
        ps.setDouble(7, d.getQty());
        ps.setDouble(8, d.getQtyDiterima());
        ps.setDouble(9, d.getHarga());
        ps.setDouble(10, d.getTotal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PemesananPembelianBahanDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pemesanan_pembelian_bahan_detail set "
                + " kategori_bahan=?, nama_bahan=?, spesifikasi=?, keterangan=?, qty=?, qty_diterima=?, harga=?, total=? "
                + " where no_pemesanan=? and no_urut=? ");
        ps.setString(1, d.getKategoriBahan());
        ps.setString(2, d.getNamaBahan());
        ps.setString(3, d.getSpesifikasi());
        ps.setString(4, d.getKeterangan());
        ps.setDouble(5, d.getQty());
        ps.setDouble(6, d.getQtyDiterima());
        ps.setDouble(7, d.getHarga());
        ps.setDouble(8, d.getTotal());
        ps.setString(9, d.getNoPemesanan());
        ps.setInt(10, d.getNoUrut());
        ps.executeUpdate();
    }
}
