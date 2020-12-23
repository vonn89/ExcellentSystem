/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
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
public class PembangunanKontraktorHeadDAO {
    
    public static List<PembangunanKontraktorHead> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_kontraktor_head");
        ResultSet rs = ps.executeQuery();
        List<PembangunanKontraktorHead> allpengelolaan = new ArrayList<>();
        while(rs.next()){
            PembangunanKontraktorHead p = new PembangunanKontraktorHead();
            p.setNoPembangunan(rs.getString(1));
            p.setKodeKontraktor(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setKategori(rs.getString(4));
            p.setTotalPembangunan(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPembayaran(rs.getDouble(7));
            allpengelolaan.add(p);
        }
        return allpengelolaan;
    }
    public static PembangunanKontraktorHead get(Connection con,String noPembangunan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_kontraktor_head where no_pembangunan=? ");
        ps.setString(1, noPembangunan);
        ResultSet rs = ps.executeQuery();
        PembangunanKontraktorHead p = null;
        while(rs.next()){
            p = new PembangunanKontraktorHead();
            p.setNoPembangunan(rs.getString(1));
            p.setKodeKontraktor(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setKategori(rs.getString(4));
            p.setTotalPembangunan(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPembayaran(rs.getDouble(7));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembangunan,4)) from tt_pembangunan_kontraktor_head "
                + " where mid(no_pembangunan,4,4)='"+new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "BK-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("0000").format(rs.getInt(1)+1);
         else 
            return "BK-"+new SimpleDateFormat("yyMM").format(new Date())+"0001";
    }
    public static String getIdByDate(Connection con, Date date)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pembangunan,4)) from tt_pembangunan_kontraktor_head "
                + " where mid(no_pembangunan,4,4)='"+new SimpleDateFormat("yyMM").format(date)+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "BK-"+new SimpleDateFormat("yyMM").format(date)+new DecimalFormat("0000").format(rs.getInt(1)+1);
         else 
            return "BK-"+new SimpleDateFormat("yyMM").format(date)+"0001";
    }
    public static void insert(Connection con, PembangunanKontraktorHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembangunan_kontraktor_head values(?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembangunan());
        ps.setString(2, p.getKodeKontraktor());
        ps.setString(3, p.getKodeProperty());
        ps.setString(4, p.getKategori());
        ps.setDouble(5, p.getTotalPembangunan());
        ps.setDouble(6, p.getPembayaran());
        ps.setDouble(7, p.getSisaPembayaran());
        ps.executeUpdate();
    }
    public static void update(Connection con,PembangunanKontraktorHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembangunan_kontraktor_head set "
                + " kode_kontraktor = ?, kode_property = ?, kategori = ?, total_pembangunan = ?, pembayaran = ?, "
                + " sisa_pembayaran = ? where no_pembangunan=?");
        ps.setString(1, p.getKodeKontraktor());
        ps.setString(2, p.getKodeProperty());
        ps.setString(3, p.getKategori());
        ps.setDouble(4, p.getTotalPembangunan());
        ps.setDouble(5, p.getPembayaran());
        ps.setDouble(6, p.getSisaPembayaran());
        ps.setString(7, p.getNoPembangunan());
        ps.executeUpdate();
    }
}
