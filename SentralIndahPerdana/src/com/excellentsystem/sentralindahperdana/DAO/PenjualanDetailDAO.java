/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanDetailDAO {
    public static List<PenjualanDetail> getAllByTglPenjualan(Connection con, String tglMulai,String tglAkhir)throws Exception{
        List<PenjualanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail where no_penjualan in "
                + " (select no_penjualan from tt_penjualan_head where left(tgl_penjualan,10) between ? and ? and status like 'close')")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PenjualanDetail detail = new PenjualanDetail();
                    detail.setNoPenjualan(rs.getString(1));
                    detail.setNoUrut(rs.getString(2));
                    detail.setKodePekerjaan(rs.getString(3));
                    detail.setNamaPekerjaan(rs.getString(4));
                    detail.setKeterangan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setHarga(rs.getDouble(7));
                    detail.setTotal(rs.getDouble(8));
                    detail.setWaktuPengerjaan(rs.getDouble(9));
                    detail.setTotalAnggaranBebanMaterial(rs.getDouble(10));
                    detail.setTotalAnggaranBebanPenjualan(rs.getDouble(11));
                    detail.setTotalBebanMaterial(rs.getDouble(12));
                    detail.setTotalBebanPenjualan(rs.getDouble(13));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<PenjualanDetail> getAll(Connection con)throws Exception{
        List<PenjualanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail")) {
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PenjualanDetail detail = new PenjualanDetail();
                    detail.setNoPenjualan(rs.getString(1));
                    detail.setNoUrut(rs.getString(2));
                    detail.setKodePekerjaan(rs.getString(3));
                    detail.setNamaPekerjaan(rs.getString(4));
                    detail.setKeterangan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setHarga(rs.getDouble(7));
                    detail.setTotal(rs.getDouble(8));
                    detail.setWaktuPengerjaan(rs.getDouble(9));
                    detail.setTotalAnggaranBebanMaterial(rs.getDouble(10));
                    detail.setTotalAnggaranBebanPenjualan(rs.getDouble(11));
                    detail.setTotalBebanMaterial(rs.getDouble(12));
                    detail.setTotalBebanPenjualan(rs.getDouble(13));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<PenjualanDetail> getAllByNoPenjualan(Connection con, String noPenjualan)throws Exception{
        List<PenjualanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_detail where no_penjualan=?")) {
            ps.setString(1, noPenjualan);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PenjualanDetail detail = new PenjualanDetail();
                    detail.setNoPenjualan(rs.getString(1));
                    detail.setNoUrut(rs.getString(2));
                    detail.setKodePekerjaan(rs.getString(3));
                    detail.setNamaPekerjaan(rs.getString(4));
                    detail.setKeterangan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setHarga(rs.getDouble(7));
                    detail.setTotal(rs.getDouble(8));
                    detail.setWaktuPengerjaan(rs.getDouble(9));
                    detail.setTotalAnggaranBebanMaterial(rs.getDouble(10));
                    detail.setTotalAnggaranBebanPenjualan(rs.getDouble(11));
                    detail.setTotalBebanMaterial(rs.getDouble(12));
                    detail.setTotalBebanPenjualan(rs.getDouble(13));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static void insert(Connection con, PenjualanDetail detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, detail.getNoPenjualan());
            ps.setString(2, detail.getNoUrut());
            ps.setString(3, detail.getKodePekerjaan());
            ps.setString(4, detail.getNamaPekerjaan());
            ps.setString(5, detail.getKeterangan());
            ps.setDouble(6, detail.getQty());
            ps.setDouble(7, detail.getHarga());
            ps.setDouble(8, detail.getTotal());
            ps.setDouble(9, detail.getWaktuPengerjaan());
            ps.setDouble(10, detail.getTotalAnggaranBebanMaterial());
            ps.setDouble(11, detail.getTotalAnggaranBebanPenjualan());
            ps.setDouble(12, detail.getTotalBebanMaterial());
            ps.setDouble(13, detail.getTotalBebanPenjualan());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, PenjualanDetail detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_penjualan_detail set "
                + " total_anggaran_beban_material=?, "
                + " total_anggaran_beban_penjualan=?, "
                + " total_beban_material=?, "
                + " total_beban_penjualan=? "
                + " where no_penjualan = ? and no_urut = ?")) {
            ps.setDouble(1, detail.getTotalAnggaranBebanMaterial());
            ps.setDouble(2, detail.getTotalAnggaranBebanPenjualan());
            ps.setDouble(3, detail.getTotalBebanMaterial());
            ps.setDouble(4, detail.getTotalBebanPenjualan());
            ps.setString(5, detail.getNoPenjualan());
            ps.setString(6, detail.getNoUrut());
            ps.executeUpdate();
        }
    }
}
