/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PengirimanDetailDAO {
    
    public static List<PengirimanDetail> getAllByNoPenjualanAndNoUrutAndStatus(
            Connection con, String noPenjualan,String noUrut, String status)throws Exception{
        List<PengirimanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_detail where no_pengiriman in "
                + "( select no_pengiriman from tt_pengiriman_head "
                + " where no_penjualan like ?  and status like ? ) and no_urut_penjualan like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, status);
            ps.setString(3, noUrut);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PengirimanDetail detail = new PengirimanDetail();
                    detail.setNoPengiriman(rs.getString(1));
                    detail.setNoUrutPenjualan(rs.getString(2));
                    detail.setKodeBarang(rs.getString(3));
                    detail.setNamaBarang(rs.getString(4));
                    detail.setSatuan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setNilai(rs.getDouble(7));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<PengirimanDetail> getAllByDateAndStatus(
            Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        List<PengirimanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_detail where no_pengiriman in "
                + "( select no_pengiriman from tt_pengiriman_head "
                + " where left(tgl_pengiriman,10) between ? and ? and status like ? )")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PengirimanDetail detail = new PengirimanDetail();
                    detail.setNoPengiriman(rs.getString(1));
                    detail.setNoUrutPenjualan(rs.getString(2));
                    detail.setKodeBarang(rs.getString(3));
                    detail.setNamaBarang(rs.getString(4));
                    detail.setSatuan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setNilai(rs.getDouble(7));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<PengirimanDetail> getAllByNoPengiriman(Connection con, String noPengiriman)throws Exception{
        List<PengirimanDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pengiriman_detail where no_pengiriman=?")) {
            ps.setString(1, noPengiriman);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PengirimanDetail detail = new PengirimanDetail();
                    detail.setNoPengiriman(rs.getString(1));
                    detail.setNoUrutPenjualan(rs.getString(2));
                    detail.setKodeBarang(rs.getString(3));
                    detail.setNamaBarang(rs.getString(4));
                    detail.setSatuan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setNilai(rs.getDouble(7));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static void insert(Connection con, PengirimanDetail detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_pengiriman_detail values(?,?,?,?,?,?,?)")) {
            ps.setString(1, detail.getNoPengiriman());
            ps.setString(2, detail.getNoUrutPenjualan());
            ps.setString(3, detail.getKodeBarang());
            ps.setString(4, detail.getNamaBarang());
            ps.setString(5, detail.getSatuan());
            ps.setDouble(6, detail.getQty());
            ps.setDouble(7, detail.getNilai());
            ps.executeUpdate();
        }
    }
}
