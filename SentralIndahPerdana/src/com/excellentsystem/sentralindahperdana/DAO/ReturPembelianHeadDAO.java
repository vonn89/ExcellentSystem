/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.ReturPembelianHead;
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
 * @author yunaz
 */
public class ReturPembelianHeadDAO {
    public static void insert(Connection con, ReturPembelianHead retur)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_retur_pembelian_head values(?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, retur.getNoRetur());
            ps.setString(2, retur.getTglRetur());
            ps.setString(3, retur.getKodeSupplier());
            ps.setDouble(4, retur.getTotalRetur());
            ps.setString(5, retur.getTipeKeuangan());
            ps.setString(6, retur.getCatatan());
            ps.setString(7, retur.getKodeUser());
            ps.setString(8, retur.getStatus());
            ps.setString(9, retur.getTglBatal());
            ps.setString(10, retur.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, ReturPembelianHead retur)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_retur_pembelian_head set tgl_retur=?, kode_supplier=?, "
                + " total_retur=?, tipe_keuangan=?, catatan=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_retur=?")) {
            ps.setString(1, retur.getTglRetur());
            ps.setString(2, retur.getKodeSupplier());
            ps.setDouble(3, retur.getTotalRetur());
            ps.setString(4, retur.getTipeKeuangan());
            ps.setString(5, retur.getCatatan());
            ps.setString(6, retur.getKodeUser());
            ps.setString(7, retur.getStatus());
            ps.setString(8, retur.getTglBatal());
            ps.setString(9, retur.getUserBatal());
            ps.setString(10, retur.getNoRetur());
            ps.executeUpdate();
        }
    }
    public static String getId(Connection con)throws Exception{
        String no_retur;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_retur,3)) from tt_retur_pembelian_head where left(no_retur,7) like ?")) {
            DateFormat d = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            ps.setString(1, "RB-"+d.format(new Date()));
            try (ResultSet rs = ps.executeQuery()) {
                no_retur = "RB-"+d.format(new Date())+df.format(1);
                if(rs.next())
                    no_retur = "RB-"+d.format(new Date())+df.format(rs.getInt(1)+1);
            }
        }
        return no_retur;
    }
    public static ReturPembelianHead get(Connection con, String no_retur)throws Exception{
        ReturPembelianHead r;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_pembelian_head where no_retur=?")) {
            ps.setString(1, no_retur);
            try (ResultSet rs = ps.executeQuery()) {
                r = null;
                if(rs.next()){
                    r = new ReturPembelianHead();
                    r.setNoRetur(rs.getString(1));
                    r.setTglRetur(rs.getString(2));
                    r.setKodeSupplier(rs.getString(3));
                    r.setTotalRetur(rs.getDouble(4));
                    r.setTipeKeuangan(rs.getString(5));
                    r.setCatatan(rs.getString(6));
                    r.setKodeUser(rs.getString(7));
                    r.setStatus(rs.getString(8));
                    r.setTglBatal(rs.getString(9));
                    r.setUserBatal(rs.getString(10));
                }
            }
        }
        return r;
    }
    public static List<ReturPembelianHead> getAllByTanggalAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        List<ReturPembelianHead> allRetur;
        try (PreparedStatement ps = con.prepareStatement(
                "select * from tt_retur_pembelian_head where left(tgl_retur,10) between ? and ? and status=?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allRetur = new ArrayList<>();
                while(rs.next()){
                    ReturPembelianHead r = new ReturPembelianHead();
                    r.setNoRetur(rs.getString(1));
                    r.setTglRetur(rs.getString(2));
                    r.setKodeSupplier(rs.getString(3));
                    r.setTotalRetur(rs.getDouble(4));
                    r.setTipeKeuangan(rs.getString(5));
                    r.setCatatan(rs.getString(6));
                    r.setKodeUser(rs.getString(7));
                    r.setStatus(rs.getString(8));
                    r.setTglBatal(rs.getString(9));
                    r.setUserBatal(rs.getString(10));
                    allRetur.add(r);
                }
            }
        }
        return allRetur;
    }
}
