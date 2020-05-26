/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyHead;
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
public class PemecahanPropertyHeadDAO {
    
    public static List<PemecahanPropertyHead> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemecahan_property_head "
                + " where left(tgl_pemecahan,10) between ? and ? and status =? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        List<PemecahanPropertyHead> listPemecahan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PemecahanPropertyHead p = new PemecahanPropertyHead();
            p.setNoPemecahan(rs.getString(1));
            p.setTglPemecahan(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setNamaProperty(rs.getString(4));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setTotalLuasEfektif(rs.getDouble(7));
            p.setTotalLuasTersisa(rs.getDouble(8));
            p.setNilaiProperty(rs.getDouble(9));
            p.setNilaiPropertyPerMeter(rs.getDouble(10));
            p.setKodeUser(rs.getString(11));
            p.setStatus(rs.getString(12));
        }
        return listPemecahan;
    }
    public static PemecahanPropertyHead get(Connection con, String noPemecahan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemecahan_property_head "
                + " where no_pemecahan = ?");
        ps.setString(1, noPemecahan);
        PemecahanPropertyHead p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PemecahanPropertyHead();
            p.setNoPemecahan(rs.getString(1));
            p.setTglPemecahan(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setNamaProperty(rs.getString(4));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setTotalLuasEfektif(rs.getDouble(7));
            p.setTotalLuasTersisa(rs.getDouble(8));
            p.setNilaiProperty(rs.getDouble(9));
            p.setNilaiPropertyPerMeter(rs.getDouble(10));
            p.setKodeUser(rs.getString(11));
            p.setStatus(rs.getString(12));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_pemecahan,3)) from tt_pemecahan_property_head "
                + " where mid(no_pemecahan,4,4)='"+new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "PM-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1) + 1);
         else 
            return "PM-"+new SimpleDateFormat("yyMM").format(new Date())+"001";
    }
    public static void insert(Connection con, PemecahanPropertyHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemecahan_property_head values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPemecahan());
        ps.setString(2, p.getTglPemecahan());
        ps.setString(3, p.getKodeProperty());
        ps.setString(4, p.getNamaProperty());
        ps.setDouble(5, p.getTotalProperty());
        ps.setDouble(6, p.getTotalLuasTanah());
        ps.setDouble(7, p.getTotalLuasEfektif());
        ps.setDouble(8, p.getTotalLuasTersisa());
        ps.setDouble(9, p.getNilaiProperty());
        ps.setDouble(10, p.getNilaiPropertyPerMeter());
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getStatus());
        ps.executeUpdate();
    }
}
