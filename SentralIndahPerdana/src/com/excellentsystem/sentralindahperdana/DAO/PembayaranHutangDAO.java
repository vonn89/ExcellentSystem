/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
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
public class PembayaranHutangDAO {
    public static void insert(Connection con, PembayaranHutang p)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_pembayaran_hutang values(?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, p.getNoPembayaran());
            ps.setString(2, p.getTglPembayaran());
            ps.setString(3, p.getNoHutang());
            ps.setDouble(4, p.getJumlahPembayaran());
            ps.setString(5, p.getTipeKeuangan());
            ps.setString(6, p.getKodeUser());
            ps.setString(7, p.getCatatan());
            ps.setString(8, p.getStatus());
            ps.setString(9, p.getTglBatal());
            ps.setString(10, p.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, PembayaranHutang p)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_pembayaran_hutang set tgl_pembayaran=?, no_hutang=?,"
                + " jumlah_pembayaran=?, tipe_keuangan=?, kode_user=?,"
                + " catatan=?, status=?, tgl_batal=?, user_batal=? where no_pembayaran=?")) {
            ps.setString(1, p.getTglPembayaran());
            ps.setString(2, p.getNoHutang());
            ps.setDouble(3, p.getJumlahPembayaran());
            ps.setString(4, p.getTipeKeuangan());
            ps.setString(5, p.getKodeUser());
            ps.setString(6, p.getCatatan());
            ps.setString(7, p.getStatus());
            ps.setString(8, p.getTglBatal());
            ps.setString(9, p.getUserBatal());
            ps.setString(10, p.getNoPembayaran());
            ps.executeUpdate();
        }
    }
    public static String getId(Connection con)throws Exception{
        String no_pembayaran;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_pembayaran,3)) from tt_pembayaran_hutang where left(no_pembayaran,7) like ?")) {
            DateFormat d = new SimpleDateFormat("yyMM");
            ps.setString(1, "PH-"+d.format(new Date()));
            DecimalFormat df = new DecimalFormat("000");
            try (ResultSet rs = ps.executeQuery()) {
                no_pembayaran = "PH-"+d.format(new Date())+df.format(1);
                if(rs.next())
                    no_pembayaran = "PH-"+d.format(new Date())+df.format(rs.getInt(1)+1);
            }
        }
        return no_pembayaran;
    }
    public static List<PembayaranHutang> getAllByTanggalAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        List<PembayaranHutang> allPembayaranHutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembayaran_hutang where left(tgl_pembayaran,10) between ? and ? and status=?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allPembayaranHutang = new ArrayList<>();
                while(rs.next()){
                    PembayaranHutang p = new PembayaranHutang();
                    p.setNoPembayaran(rs.getString(1));
                    p.setTglPembayaran(rs.getString(2));
                    p.setNoHutang(rs.getString(3));
                    p.setJumlahPembayaran(rs.getDouble(4));
                    p.setTipeKeuangan(rs.getString(5));
                    p.setKodeUser(rs.getString(6));
                    p.setCatatan(rs.getString(7));
                    p.setStatus(rs.getString(8));
                    p.setTglBatal(rs.getString(9));
                    p.setUserBatal(rs.getString(10));
                    allPembayaranHutang.add(p);
                }
            }
        }
        return allPembayaranHutang;
    }
    public static List<PembayaranHutang> getAllByNoHutangAndStatus(Connection con, String no_hutang,String status)throws Exception{
        List<PembayaranHutang> allPembayaranHutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembayaran_hutang where no_hutang=? and status=?")) {
            ps.setString(1, no_hutang);
            ps.setString(2, status);
            try (ResultSet rs = ps.executeQuery()) {
                allPembayaranHutang = new ArrayList<>();
                while(rs.next()){
                    PembayaranHutang p = new PembayaranHutang();
                    p.setNoPembayaran(rs.getString(1));
                    p.setTglPembayaran(rs.getString(2));
                    p.setNoHutang(rs.getString(3));
                    p.setJumlahPembayaran(rs.getDouble(4));
                    p.setTipeKeuangan(rs.getString(5));
                    p.setKodeUser(rs.getString(6));
                    p.setCatatan(rs.getString(7));
                    p.setStatus(rs.getString(8));
                    p.setTglBatal(rs.getString(9));
                    p.setUserBatal(rs.getString(10));
                    allPembayaranHutang.add(p);
                }
            }
        }
        return allPembayaranHutang;
    }
}
