/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyHead;
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
public class PenggabunganPropertyHeadDAO {
    
    public static List<PenggabunganPropertyHead> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penggabungan_property_head "
                + " where left(tgl_penggabungan,10) between ? and ? and status =? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        List<PenggabunganPropertyHead> listPemecahan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenggabunganPropertyHead p = new PenggabunganPropertyHead();
            p.setNoPenggabungan(rs.getString(1));
            p.setTglPenggabungan(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setNilaiTanah(rs.getDouble(7));
            p.setNilaiTanahPerMeter(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
        }
        return listPemecahan;
    }
    public static PenggabunganPropertyHead get(Connection con, String noPemecahan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penggabungan_property_head "
                + " where no_pemecahan = ?");
        ps.setString(1, noPemecahan);
        PenggabunganPropertyHead p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PenggabunganPropertyHead();
            p.setNoPenggabungan(rs.getString(1));
            p.setTglPenggabungan(rs.getString(2));
            p.setKodeProperty(rs.getString(3));
            p.setTotalProperty(rs.getDouble(5));
            p.setTotalLuasTanah(rs.getDouble(6));
            p.setNilaiTanah(rs.getDouble(7));
            p.setNilaiTanahPerMeter(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_penggabungan,3)) from tt_penggabungan_property_head "
                + " where mid(no_penggabungan,4,4)='"+new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "PG-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1) + 1);
        else 
            return "PG-"+new SimpleDateFormat("yyMM").format(new Date())+"001";
    }
    public static void insert(Connection con, PenggabunganPropertyHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penggabungan_property_head values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenggabungan());
        ps.setString(2, p.getTglPenggabungan());
        ps.setString(3, p.getKodeProperty());
        ps.setDouble(4, p.getTotalProperty());
        ps.setDouble(5, p.getTotalLuasTanah());
        ps.setDouble(6, p.getLuasAkhir());
        ps.setDouble(7, p.getNilaiTanah());
        ps.setDouble(8, p.getNilaiTanahPerMeter());
        ps.setString(9, p.getKodeUser());
        ps.setString(10, p.getStatus());
        ps.executeUpdate();
    }
}
