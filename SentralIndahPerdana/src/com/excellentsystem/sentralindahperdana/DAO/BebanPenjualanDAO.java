/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
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
public class BebanPenjualanDAO {
    
     public static List<BebanPenjualan> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        List<BebanPenjualan> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_beban_penjualan "
                + " where left(tgl_beban_penjualan,10) between ? and ? and status like ?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    BebanPenjualan detail = new BebanPenjualan();
                    detail.setNoBebanPenjualan(rs.getString(1));
                    detail.setTglBebanPenjualan(rs.getString(2));
                    detail.setNoPenjualan(rs.getString(3));
                    detail.setNoUrut(rs.getString(4));
                    detail.setKategori(rs.getString(5));
                    detail.setKeterangan(rs.getString(6));
                    detail.setJumlahRp(rs.getDouble(7));
                    detail.setTipeKeuangan(rs.getString(8));
                    detail.setKodeUser(rs.getString(9));
                    detail.setStatus(rs.getString(10));
                    detail.setTglBatal(rs.getString(11));
                    detail.setUserBatal(rs.getString(12));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<BebanPenjualan> getAllByNoPenjualanAndNoUrut(Connection con, String noPenjualan, String noUrut, String status)throws Exception{
        List<BebanPenjualan> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_beban_penjualan "
                + " where no_penjualan like ? and no_urut like ? and status like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, noUrut);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    BebanPenjualan detail = new BebanPenjualan();
                    detail.setNoBebanPenjualan(rs.getString(1));
                    detail.setTglBebanPenjualan(rs.getString(2));
                    detail.setNoPenjualan(rs.getString(3));
                    detail.setNoUrut(rs.getString(4));
                    detail.setKategori(rs.getString(5));
                    detail.setKeterangan(rs.getString(6));
                    detail.setJumlahRp(rs.getDouble(7));
                    detail.setTipeKeuangan(rs.getString(8));
                    detail.setKodeUser(rs.getString(9));
                    detail.setStatus(rs.getString(10));
                    detail.setTglBatal(rs.getString(11));
                    detail.setUserBatal(rs.getString(12));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static String getId(Connection con)throws Exception{
        String noPenjualan;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_beban_penjualan,3)) from tt_beban_penjualan "
                + "where mid(no_beban_penjualan,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noPenjualan = "BP-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noPenjualan = "BP-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noPenjualan;
    }
    public static void insert(Connection con, BebanPenjualan detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_beban_penjualan "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, detail.getNoBebanPenjualan());
            ps.setString(2, detail.getTglBebanPenjualan());
            ps.setString(3, detail.getNoPenjualan());
            ps.setString(4, detail.getNoUrut());
            ps.setString(5, detail.getKategori());
            ps.setString(6, detail.getKeterangan());
            ps.setDouble(7, detail.getJumlahRp());
            ps.setString(8, detail.getTipeKeuangan());
            ps.setString(9, detail.getKodeUser());
            ps.setString(10, detail.getStatus());
            ps.setString(11, detail.getTglBatal());
            ps.setString(12, detail.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, BebanPenjualan beban)throws Exception{
        try(PreparedStatement ps = con.prepareStatement("update tt_beban_penjualan "
                + "set status=?, tgl_batal=?, user_batal=? where no_beban_penjualan=?")){
            ps.setString(1, beban.getStatus());
            ps.setString(2, beban.getTglBatal());
            ps.setString(3, beban.getUserBatal());
            ps.setString(4, beban.getNoBebanPenjualan());
            ps.executeUpdate();
        }
    }
}
