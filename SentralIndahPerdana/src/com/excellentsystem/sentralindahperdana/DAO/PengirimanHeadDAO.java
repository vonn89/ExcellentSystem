/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
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
public class PengirimanHeadDAO {
    
    public static List<PengirimanHead> getAllByNoPenjualanAndStatus(
            Connection con, String noPenjualan, String status)throws Exception{
        List<PengirimanHead> allPengiriman;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_head "
                + "where no_penjualan like ? and status like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(3, status);
            allPengiriman = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PengirimanHead head = new PengirimanHead();
                    head.setNoPengiriman(rs.getString(1));
                    head.setTglPengiriman(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setNoPenjualan(rs.getString(3));
                    head.setJenisKendaraan(rs.getString(4));
                    head.setNoPolisi(rs.getString(5));
                    head.setSupir(rs.getString(6));
                    head.setCatatan(rs.getString(7));
                    head.setKodeUser(rs.getString(8));
                    head.setStatus(rs.getString(9));
                    head.setTglBatal(rs.getDate(10).toString()+" "+rs.getTime(10).toString());
                    head.setUserBatal(rs.getString(11));
                    allPengiriman.add(head);
                }
            }
        }
        return allPengiriman;
    }
    public static List<PengirimanHead> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        List<PengirimanHead> allPengiriman;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_head "
                + "where left(tgl_pengiriman,10) between ? and ? and status like ?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            allPengiriman = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PengirimanHead head = new PengirimanHead();
                    head.setNoPengiriman(rs.getString(1));
                    head.setTglPengiriman(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setNoPenjualan(rs.getString(3));
                    head.setJenisKendaraan(rs.getString(4));
                    head.setNoPolisi(rs.getString(5));
                    head.setSupir(rs.getString(6));
                    head.setCatatan(rs.getString(7));
                    head.setKodeUser(rs.getString(8));
                    head.setStatus(rs.getString(9));
                    head.setTglBatal(rs.getDate(10).toString()+" "+rs.getTime(10).toString());
                    head.setUserBatal(rs.getString(11));
                    allPengiriman.add(head);
                }
            }
        }
        return allPengiriman;
    }
    public static PengirimanHead get(Connection con, String noPengiriman)throws Exception{
        PengirimanHead head;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_head "
                + "where no_pengiriman like ? ")) {
            ps.setString(1, noPengiriman);
            try (ResultSet rs = ps.executeQuery()) {
                head = null;
                if(rs.next()){
                    head = new PengirimanHead();
                    head.setNoPengiriman(rs.getString(1));
                    head.setTglPengiriman(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setNoPenjualan(rs.getString(3));
                    head.setJenisKendaraan(rs.getString(4));
                    head.setNoPolisi(rs.getString(5));
                    head.setSupir(rs.getString(6));
                    head.setCatatan(rs.getString(7));
                    head.setKodeUser(rs.getString(8));
                    head.setStatus(rs.getString(9));
                    head.setTglBatal(rs.getDate(10).toString()+" "+rs.getTime(10).toString());
                    head.setUserBatal(rs.getString(11));
                }
            }
        }
        return head;
    }
    public static String getId(Connection con)throws Exception{
        String noPembelian;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_pengiriman,3)) from tt_pengiriman_head "
                + "where mid(no_pengiriman,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noPembelian = "SJ-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noPembelian = "SJ-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noPembelian;
    }
    public static void insert(Connection con, PengirimanHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_pengiriman_head values(?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, head.getNoPengiriman());
            ps.setString(2, head.getTglPengiriman());
            ps.setString(3, head.getNoPenjualan());
            ps.setString(4, head.getJenisKendaraan());
            ps.setString(5, head.getNoPolisi());
            ps.setString(6, head.getSupir());
            ps.setString(7, head.getCatatan());
            ps.setString(8, head.getKodeUser());
            ps.setString(9, head.getStatus());
            ps.setString(10, head.getTglBatal());
            ps.setString(11, head.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, PengirimanHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_pengiriman_head set"
                + " tgl_batal=?, user_batal=?, status=? where no_pengiriman=?")) {
            ps.setString(1, head.getTglBatal());
            ps.setString(2, head.getUserBatal());
            ps.setString(3, head.getStatus());
            ps.setString(4, head.getNoPengiriman());
            ps.executeUpdate();
        }
    }
}
