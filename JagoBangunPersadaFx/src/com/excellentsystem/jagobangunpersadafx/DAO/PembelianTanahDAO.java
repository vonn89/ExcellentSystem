/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PembelianTanah;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianTanahDAO {
    
    public static List<PembelianTanah> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_tanah "
                + " where left(tgl_pembelian,10) between ? and ? and status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembelianTanah> allpembelian = new ArrayList<>();
        while(rs.next()){
            PembelianTanah p = new PembelianTanah();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setLuasTanah(rs.getDouble(5));
            p.setHargaBeli(rs.getDouble(6));
            p.setTipeKeuangan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setStatus(rs.getString(9));
            p.setTglBatal(rs.getString(10));
            p.setUserBatal(rs.getString(11));
            allpembelian.add(p);
        }
        return allpembelian;
    }
    public static PembelianTanah getByKodeProperty(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_tanah where kode_property=?");
        ps.setString(1, kodeProperty);
        ResultSet rs = ps.executeQuery();
        PembelianTanah p = null;
        while(rs.next()){
            p = new PembelianTanah();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setLuasTanah(rs.getDouble(5));
            p.setHargaBeli(rs.getDouble(6));
            p.setTipeKeuangan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setStatus(rs.getString(9));
            p.setTglBatal(rs.getString(10));
            p.setUserBatal(rs.getString(11));
        }
        return p;
    }
    public static PembelianTanah get(Connection con,String nopembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_tanah where no_pembelian=?");
        ps.setString(1, nopembelian);
        ResultSet rs = ps.executeQuery();
        PembelianTanah p = null;
        while(rs.next()){
            p = new PembelianTanah();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setLuasTanah(rs.getDouble(5));
            p.setHargaBeli(rs.getDouble(6));
            p.setTipeKeuangan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setStatus(rs.getString(9));
            p.setTglBatal(rs.getString(10));
            p.setUserBatal(rs.getString(11));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembelian,4)) from tt_pembelian_tanah "
                + " where mid(no_pembelian,4,4)='"+new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "PB-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("0000").format(rs.getInt(1) + 1);
        else 
            return "PB-"+new SimpleDateFormat("yyMM").format(new Date())+"0001";
    }
    public static void insert(Connection con, PembelianTanah p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_tanah values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembelian());
        ps.setString(2, p.getTglPembelian());
        ps.setString(3, p.getKodeProperty());
        ps.setString(4, p.getKeterangan());
        ps.setDouble(5, p.getLuasTanah());
        ps.setDouble(6, p.getHargaBeli());
        ps.setString(7, p.getTipeKeuangan());
        ps.setString(8, p.getKodeUser());
        ps.setString(9, p.getStatus());
        ps.setString(10, p.getTglBatal());
        ps.setString(11, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianTanah p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_tanah set "
                + " tgl_pembelian=?, kode_property=?, keterangan=?, luas_tanah=?, harga_beli=?, "
                + " tipe_keuangan=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_pembelian=?");
        ps.setString(1, p.getTglPembelian());
        ps.setString(2, p.getKodeProperty());
        ps.setString(3, p.getKeterangan());
        ps.setDouble(4, p.getLuasTanah());
        ps.setDouble(5, p.getHargaBeli());
        ps.setString(6, p.getTipeKeuangan());
        ps.setString(7, p.getKodeUser());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getTglBatal());
        ps.setString(10, p.getUserBatal());
        ps.setString(11, p.getNoPembelian());
        ps.executeUpdate();
    }
}
