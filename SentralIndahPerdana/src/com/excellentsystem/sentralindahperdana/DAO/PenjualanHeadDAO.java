/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
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
public class PenjualanHeadDAO {
    public static List<PenjualanHead> getAllByTglPemesanan(
            Connection con, String tglMulai, String tglAkhir)throws Exception{
        List<PenjualanHead> listPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head "
                + "where left(tgl_pemesanan,10) between ? and ? and status like 'open'")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            listPenjualan = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PenjualanHead head = new PenjualanHead();
                    head.setNoPenjualan(rs.getString(1));
                    head.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setTglPenjualan(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
                    head.setKodeCustomer(rs.getString(4));
                    head.setNamaProyek(rs.getString(5));
                    head.setLokasiPengerjaan(rs.getString(6));
                    head.setTglMulai(rs.getString(7));
                    head.setTglSelesai(rs.getString(8));
                    head.setTotalPenjualan(rs.getDouble(9));
                    head.setPembayaran(rs.getDouble(10));
                    head.setSisaPembayaran(rs.getDouble(11));
                    head.setCatatan(rs.getString(12));
                    head.setKodeUser(rs.getString(13));
                    head.setStatus(rs.getString(14));
                    head.setTglBatal(rs.getDate(15).toString()+" "+rs.getTime(15).toString());
                    head.setUserBatal(rs.getString(16));
                    listPenjualan.add(head);
                }
            }
        }
        return listPenjualan;
    }
    public static List<PenjualanHead> getAllByTglPenjualan(
            Connection con, String tglMulai, String tglAkhir)throws Exception{
        List<PenjualanHead> listPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head "
                + "where left(tgl_penjualan,10) between ? and ? and status like 'close'")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            listPenjualan = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PenjualanHead head = new PenjualanHead();
                    head.setNoPenjualan(rs.getString(1));
                    head.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setTglPenjualan(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
                    head.setKodeCustomer(rs.getString(4));
                    head.setNamaProyek(rs.getString(5));
                    head.setLokasiPengerjaan(rs.getString(6));
                    head.setTglMulai(rs.getString(7));
                    head.setTglSelesai(rs.getString(8));
                    head.setTotalPenjualan(rs.getDouble(9));
                    head.setPembayaran(rs.getDouble(10));
                    head.setSisaPembayaran(rs.getDouble(11));
                    head.setCatatan(rs.getString(12));
                    head.setKodeUser(rs.getString(13));
                    head.setStatus(rs.getString(14));
                    head.setTglBatal(rs.getDate(15).toString()+" "+rs.getTime(15).toString());
                    head.setUserBatal(rs.getString(16));
                    listPenjualan.add(head);
                }
            }
        }
        return listPenjualan;
    }
    public static List<PenjualanHead> getAllByStatus(Connection con, String status)throws Exception{
        List<PenjualanHead> listPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head where status like ?")) {
            ps.setString(1, status);
            listPenjualan = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PenjualanHead head = new PenjualanHead();
                    head.setNoPenjualan(rs.getString(1));
                    head.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setTglPenjualan(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
                    head.setKodeCustomer(rs.getString(4));
                    head.setNamaProyek(rs.getString(5));
                    head.setLokasiPengerjaan(rs.getString(6));
                    head.setTglMulai(rs.getString(7));
                    head.setTglSelesai(rs.getString(8));
                    head.setTotalPenjualan(rs.getDouble(9));
                    head.setPembayaran(rs.getDouble(10));
                    head.setSisaPembayaran(rs.getDouble(11));
                    head.setCatatan(rs.getString(12));
                    head.setKodeUser(rs.getString(13));
                    head.setStatus(rs.getString(14));
                    head.setTglBatal(rs.getDate(15).toString()+" "+rs.getTime(15).toString());
                    head.setUserBatal(rs.getString(16));
                    listPenjualan.add(head);
                }
            }
        }
        return listPenjualan;
    }
    public static List<PenjualanHead> getAll(Connection con)throws Exception{
        List<PenjualanHead> listPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head")) {
            listPenjualan = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PenjualanHead head = new PenjualanHead();
                    head.setNoPenjualan(rs.getString(1));
                    head.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setTglPenjualan(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
                    head.setKodeCustomer(rs.getString(4));
                    head.setNamaProyek(rs.getString(5));
                    head.setLokasiPengerjaan(rs.getString(6));
                    head.setTglMulai(rs.getString(7));
                    head.setTglSelesai(rs.getString(8));
                    head.setTotalPenjualan(rs.getDouble(9));
                    head.setPembayaran(rs.getDouble(10));
                    head.setSisaPembayaran(rs.getDouble(11));
                    head.setCatatan(rs.getString(12));
                    head.setKodeUser(rs.getString(13));
                    head.setStatus(rs.getString(14));
                    head.setTglBatal(rs.getDate(15).toString()+" "+rs.getTime(15).toString());
                    head.setUserBatal(rs.getString(16));
                    listPenjualan.add(head);
                }
            }
        }
        return listPenjualan;
    }
    public static PenjualanHead get(Connection con, String noPenjualan)throws Exception{
        PenjualanHead head;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head "
                + "where no_penjualan=? ")) {
            ps.setString(1, noPenjualan);
            try (ResultSet rs = ps.executeQuery()) {
                head = null;
                if(rs.next()){
                    head = new PenjualanHead();
                    head.setNoPenjualan(rs.getString(1));
                    head.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setTglPenjualan(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
                    head.setKodeCustomer(rs.getString(4));
                    head.setNamaProyek(rs.getString(5));
                    head.setLokasiPengerjaan(rs.getString(6));
                    head.setTglMulai(rs.getString(7));
                    head.setTglSelesai(rs.getString(8));
                    head.setTotalPenjualan(rs.getDouble(9));
                    head.setPembayaran(rs.getDouble(10));
                    head.setSisaPembayaran(rs.getDouble(11));
                    head.setCatatan(rs.getString(12));
                    head.setKodeUser(rs.getString(13));
                    head.setStatus(rs.getString(14));
                    head.setTglBatal(rs.getDate(15).toString()+" "+rs.getTime(15).toString());
                    head.setUserBatal(rs.getString(16));
                }
            }
        }
        return head;
    }
    public static String getId(Connection con)throws Exception{
        String noPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,3)) from tt_penjualan_head "
                + "where mid(no_penjualan,5,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noPenjualan = "INV-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noPenjualan = "INV-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noPenjualan;
    }
    public static void insert(Connection con, PenjualanHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, head.getNoPenjualan());
            ps.setString(2, head.getTglPemesanan());
            ps.setString(3, head.getTglPenjualan());
            ps.setString(4, head.getKodeCustomer());
            ps.setString(5, head.getNamaProyek());
            ps.setString(6, head.getLokasiPengerjaan());
            ps.setString(7, head.getTglMulai());
            ps.setString(8, head.getTglSelesai());
            ps.setDouble(9, head.getTotalPenjualan());
            ps.setDouble(10, head.getPembayaran());
            ps.setDouble(11, head.getSisaPembayaran());
            ps.setString(12, head.getCatatan());
            ps.setString(13, head.getKodeUser());
            ps.setString(14, head.getStatus());
            ps.setString(15, head.getTglBatal());
            ps.setString(16, head.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, PenjualanHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_penjualan_head set tgl_penjualan=?, tgl_mulai=?, tgl_selesai=?,"
                + " pembayaran=?, sisa_pembayaran=?, tgl_batal=?, user_batal=?, status=? where no_penjualan=?")) {
            ps.setString(1, head.getTglPenjualan());
            ps.setString(2, head.getTglMulai());
            ps.setString(3, head.getTglSelesai());
            ps.setDouble(4, head.getPembayaran());
            ps.setDouble(5, head.getSisaPembayaran());
            ps.setString(6, head.getTglBatal());
            ps.setString(7, head.getUserBatal());
            ps.setString(8, head.getStatus());
            ps.setString(9, head.getNoPenjualan());
            ps.executeUpdate();
        }
    }
}
