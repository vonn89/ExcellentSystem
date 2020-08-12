/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.ReturHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author excellent
 */
public class ReturHeadDAO {
    
    public static void insert(Connection con, ReturHead retur)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_retur_head values(?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, retur.getNoRetur());
            ps.setString(2, retur.getTglRetur());
            ps.setString(3, retur.getNoPenjualan());
            ps.setString(4, retur.getPenerima());
            ps.setString(5, retur.getCatatan());
            ps.setString(6, retur.getKodeUser());
            ps.setString(7, retur.getStatus());
            ps.setString(8, retur.getTglBatal());
            ps.setString(9, retur.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, ReturHead retur)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_retur_head "
                + "set status=?, tgl_batal=?, user_batal=? where no_retur=?")) {
            ps.setString(1, retur.getStatus());
            ps.setString(2, retur.getTglBatal());
            ps.setString(3, retur.getUserBatal());
            ps.setString(4, retur.getNoRetur());
            ps.executeUpdate();
        }
    }
    public static String getId(Connection con)throws Exception{
        String no_retur;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_retur,3)) from tt_retur_head where left(no_retur,7) like ?")) {
            DateFormat d = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            ps.setString(1, "RT-"+d.format(new Date()));
            try (ResultSet rs = ps.executeQuery()) {
                no_retur = "RT-"+d.format(new Date())+df.format(1);
                if(rs.next())
                    no_retur = "RT-"+d.format(new Date())+df.format(rs.getInt(1)+1);
            }
        }
        return no_retur;
    }
    public static ReturHead get(Connection con, String no_retur)throws Exception{
        ReturHead r;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_head where no_retur=?")) {
            ps.setString(1, no_retur);
            try (ResultSet rs = ps.executeQuery()) {
                r = null;
                if(rs.next()){
                    r = new ReturHead();
                    r.setNoRetur(rs.getString(1));
                    r.setTglRetur(rs.getString(2));
                    r.setNoPenjualan(rs.getString(3));
                    r.setPenerima(rs.getString(4));
                    r.setCatatan(rs.getString(5));
                    r.setKodeUser(rs.getString(6));
                    r.setStatus(rs.getString(7));
                    r.setTglBatal(rs.getString(8));
                    r.setUserBatal(rs.getString(9));
                }
            }
        }
        return r;
    }
    public static List<ReturHead> getAllByTanggalAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        List<ReturHead> allRetur;
        try (PreparedStatement ps = con.prepareStatement(
                "select * from tt_retur_head where left(tgl_retur,10) between ? and ? and status like ?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allRetur = new ArrayList<>();
                while(rs.next()){
                    ReturHead r = new ReturHead();
                    r.setNoRetur(rs.getString(1));
                    r.setTglRetur(rs.getString(2));
                    r.setNoPenjualan(rs.getString(3));
                    r.setPenerima(rs.getString(4));
                    r.setCatatan(rs.getString(5));
                    r.setKodeUser(rs.getString(6));
                    r.setStatus(rs.getString(7));
                    r.setTglBatal(rs.getString(8));
                    r.setUserBatal(rs.getString(9));
                    allRetur.add(r);
                }
            }
        }
        return allRetur;
    }
    public static List<ReturHead> getAllByNoPenjualanAndStatus(Connection con, String noPenjualan, String status)throws Exception{
        List<ReturHead> allRetur;
        try (PreparedStatement ps = con.prepareStatement(
                "select * from tt_retur_head where no_penjualan like ?  and status like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                allRetur = new ArrayList<>();
                while(rs.next()){
                    ReturHead r = new ReturHead();
                    r.setNoRetur(rs.getString(1));
                    r.setTglRetur(rs.getString(2));
                    r.setNoPenjualan(rs.getString(3));
                    r.setPenerima(rs.getString(4));
                    r.setCatatan(rs.getString(5));
                    r.setKodeUser(rs.getString(6));
                    r.setStatus(rs.getString(7));
                    r.setTglBatal(rs.getString(8));
                    r.setUserBatal(rs.getString(9));
                    allRetur.add(r);
                }
            }
        }
        return allRetur;
    }
}
