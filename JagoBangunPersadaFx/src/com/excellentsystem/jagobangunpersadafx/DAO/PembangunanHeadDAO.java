/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PembangunanHead;
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
public class PembangunanHeadDAO {
    
    public static List<PembangunanHead> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_head where "
                + " left(tgl_pembangunan,10) between ? and ? and status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembangunanHead> allpengelolaan = new ArrayList<>();
        while(rs.next()){
            PembangunanHead p = new PembangunanHead();
            p.setNoPembangunan(rs.getString(1));
            p.setTglPembangunan(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setTotalBiaya(rs.getDouble(7));
            p.setMetode(rs.getString(8));
            p.setTipeKeuangan(rs.getString(9));
            p.setKodeUser(rs.getString(10));
            p.setStatus(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            allpengelolaan.add(p);
        }
        return allpengelolaan;
    }
    public static PembangunanHead get(Connection con,String noPengelolaan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_head where no_pembangunan=? ");
        ps.setString(1, noPengelolaan);
        ResultSet rs = ps.executeQuery();
        PembangunanHead p = null;
        while(rs.next()){
            p = new PembangunanHead();
            p.setNoPembangunan(rs.getString(1));
            p.setTglPembangunan(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setTotalBiaya(rs.getDouble(7));
            p.setMetode(rs.getString(8));
            p.setTipeKeuangan(rs.getString(9));
            p.setKodeUser(rs.getString(10));
            p.setStatus(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembangunan,4)) from tt_pembangunan_head "
                + " where mid(no_pembangunan,4,4)='"+new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "BG-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("0000").format(rs.getInt(1)+1);
         else 
            return "BG-"+new SimpleDateFormat("yyMM").format(new Date())+"0001";
    }
    public static String getIdByDate(Connection con, Date date)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembangunan,4)) from tt_pembangunan_head "
                + " where mid(no_pembangunan,4,4)='"+new SimpleDateFormat("yyMM").format(date)+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "BG-"+new SimpleDateFormat("yyMM").format(date)+new DecimalFormat("0000").format(rs.getInt(1)+1);
         else 
            return "BG-"+new SimpleDateFormat("yyMM").format(date)+"0001";
    }
    public static void insert(Connection con, PembangunanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembangunan_head values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembangunan());
        ps.setString(2, p.getTglPembangunan());
        ps.setString(3, p.getKategori());
        ps.setString(4, p.getKeterangan());
        ps.setDouble(5, p.getTotalProperty());
        ps.setDouble(6, p.getTotalLuasTanah());
        ps.setDouble(7, p.getTotalBiaya());
        ps.setString(8, p.getMetode());
        ps.setString(9, p.getTipeKeuangan());
        ps.setString(10, p.getKodeUser());
        ps.setString(11, p.getStatus());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,PembangunanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembangunan_head set "
                + " tgl_pembangunan = ?, kategori = ?, keterangan = ?, total_property = ?, "
                + " total_luas_tanah = ?, total_biaya = ?, metode = ?, "
                + " tipe_keuangan = ?, kode_user = ?, status = ?, "
                + " tgl_batal=?, user_batal=? where no_pembangunan=?");
        ps.setString(1, p.getTglPembangunan());
        ps.setString(2, p.getKategori());
        ps.setString(3, p.getKeterangan());
        ps.setDouble(4, p.getTotalProperty());
        ps.setDouble(5, p.getTotalLuasTanah());
        ps.setDouble(6, p.getTotalBiaya());
        ps.setString(7, p.getMetode());
        ps.setString(8, p.getTipeKeuangan());
        ps.setString(9, p.getKodeUser());
        ps.setString(10, p.getStatus());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.setString(13, p.getNoPembangunan());
        ps.executeUpdate();
    }
}
