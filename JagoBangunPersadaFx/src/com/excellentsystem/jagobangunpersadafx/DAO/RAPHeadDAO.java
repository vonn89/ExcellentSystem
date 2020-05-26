/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
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
 * @author Excellent
 */
public class RAPHeadDAO {
    
    public static List<RAPHead> getAllByDateAndStatusApprovalAndStatusSelesaiAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, 
            String statusApproval, String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_rap_head where left(tgl_rap,10) between ? and ?";
        if(!statusApproval.equals("%"))
            sql = sql + " and status_approval = '"+statusApproval+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<RAPHead> allrap = new ArrayList<>();
        while(rs.next()){
            RAPHead rap = new RAPHead();
            rap.setNoRap(rs.getString(1));
            rap.setTglRap(rs.getString(2));
            rap.setKategoriPembangunan(rs.getString(3));
            rap.setKeterangan(rs.getString(4));
            rap.setMetodePembagian(rs.getString(5));
            rap.setPerkiraanWaktu(rs.getInt(6));
            rap.setTglMulai(rs.getString(7));
            rap.setTotalProperty(rs.getDouble(8));
            rap.setTotalAnggaran(rs.getDouble(9));
            rap.setKodeUser(rs.getString(10));
            rap.setStatusApproval(rs.getString(11));
            rap.setTglApproval(rs.getString(12));
            rap.setUserApproval(rs.getString(13));
            rap.setStatusSelesai(rs.getString(14));
            rap.setTglSelesai(rs.getString(15));
            rap.setUserSelesai(rs.getString(16));
            rap.setStatusBatal(rs.getString(17));
            rap.setTglBatal(rs.getString(18));
            rap.setUserBatal(rs.getString(19));
            allrap.add(rap);
        }
        return allrap;
    }
    public static RAPHead get(Connection con, String noRap)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rap_head where no_rap = ? ");
        ps.setString(1, noRap);
        ResultSet rs = ps.executeQuery();
        RAPHead rap = null;
        while(rs.next()){
            rap = new RAPHead();
            rap.setNoRap(rs.getString(1));
            rap.setTglRap(rs.getString(2));
            rap.setKategoriPembangunan(rs.getString(3));
            rap.setKeterangan(rs.getString(4));
            rap.setMetodePembagian(rs.getString(5));
            rap.setPerkiraanWaktu(rs.getInt(6));
            rap.setTglMulai(rs.getString(7));
            rap.setTotalProperty(rs.getDouble(8));
            rap.setTotalAnggaran(rs.getDouble(9));
            rap.setKodeUser(rs.getString(10));
            rap.setStatusApproval(rs.getString(11));
            rap.setTglApproval(rs.getString(12));
            rap.setUserApproval(rs.getString(13));
            rap.setStatusSelesai(rs.getString(14));
            rap.setTglSelesai(rs.getString(15));
            rap.setUserSelesai(rs.getString(16));
            rap.setStatusBatal(rs.getString(17));
            rap.setTglBatal(rs.getString(18));
            rap.setUserBatal(rs.getString(19));
        }
        return rap;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_rap,4)) from tt_rap_head where mid(no_rap,5,4)='"+
                new SimpleDateFormat("yyMM").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "RAP-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("0000").format(rs.getInt(1)+1);
        else 
            return "RAP-"+new SimpleDateFormat("yyMM").format(new Date())+"0001";
    }
    public static void insert(Connection con, RAPHead rap)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rap_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, rap.getNoRap());
        ps.setString(2, rap.getTglRap());
        ps.setString(3, rap.getKategoriPembangunan());
        ps.setString(4, rap.getKeterangan());
        ps.setString(5, rap.getMetodePembagian());
        ps.setInt(6, rap.getPerkiraanWaktu());
        ps.setString(7, rap.getTglMulai());
        ps.setDouble(8, rap.getTotalProperty());
        ps.setDouble(9, rap.getTotalAnggaran());
        ps.setString(10, rap.getKodeUser());
        ps.setString(11, rap.getStatusApproval());
        ps.setString(12, rap.getTglApproval());
        ps.setString(13, rap.getUserApproval());
        ps.setString(14, rap.getStatusSelesai());
        ps.setString(15, rap.getTglSelesai());
        ps.setString(16, rap.getUserSelesai());
        ps.setString(17, rap.getStatusBatal());
        ps.setString(18, rap.getTglBatal());
        ps.setString(19, rap.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,RAPHead rap)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_rap_head set "
                + " tgl_rap=?, kategori_pembangunan=?, keterangan=?, metode_pembagian=?, perkiraan_waktu=?, tgl_mulai=?, "
                + " total_property=?, total_anggaran=?, kode_user=?, status_approval=?, tgl_approval=?, user_approval=?, "
                + " status_selesai=?, tgl_selesai=?, user_selesai=?, status_batal=?, tgl_batal=?, user_batal=? "
                + " where no_rap=? ");
        ps.setString(1, rap.getTglRap());
        ps.setString(2, rap.getKategoriPembangunan());
        ps.setString(3, rap.getKeterangan());
        ps.setString(4, rap.getMetodePembagian());
        ps.setInt(5, rap.getPerkiraanWaktu());
        ps.setString(6, rap.getTglMulai());
        ps.setDouble(7, rap.getTotalProperty());
        ps.setDouble(8, rap.getTotalAnggaran());
        ps.setString(9, rap.getKodeUser());
        ps.setString(10, rap.getStatusApproval());
        ps.setString(11, rap.getTglApproval());
        ps.setString(12, rap.getUserApproval());
        ps.setString(13, rap.getStatusSelesai());
        ps.setString(14, rap.getTglSelesai());
        ps.setString(15, rap.getUserSelesai());
        ps.setString(16, rap.getStatusBatal());
        ps.setString(17, rap.getTglBatal());
        ps.setString(18, rap.getUserBatal());
        ps.setString(19, rap.getNoRap());
        ps.executeUpdate();
    }
}
