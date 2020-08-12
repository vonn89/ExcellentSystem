/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class ReturDetailDAO {
    
    public static void insert(Connection con, ReturDetail d)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_retur_detail values(?,?,?,?,?,?,?)")) {
            ps.setString(1, d.getNoRetur());
            ps.setString(2, d.getNoUrutPenjualan());
            ps.setString(3, d.getKodeBarang());
            ps.setString(4, d.getNamaBarang());
            ps.setString(5, d.getSatuan());
            ps.setDouble(6, d.getQty());
            ps.setDouble(7, d.getNilai());
            ps.executeUpdate();
        }
    }
    public static List<ReturDetail> getAllByNoRetur(Connection con, String no_retur)throws Exception{
        List<ReturDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_detail where no_retur like ?")) {
            ps.setString(1, no_retur);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    ReturDetail d = new ReturDetail();
                    d.setNoRetur(rs.getString(1));
                    d.setNoUrutPenjualan(rs.getString(2));
                    d.setKodeBarang(rs.getString(3));
                    d.setNamaBarang(rs.getString(4));
                    d.setSatuan(rs.getString(5));
                    d.setQty(rs.getDouble(6));
                    d.setNilai(rs.getDouble(7));
                    allDetail.add(d);
                }
            }
        }
        return allDetail;
    }
    public static List<ReturDetail> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        List<ReturDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_detail where no_retur in "
                + "(select no_retur from tt_retur_head where left(tgl_retur,10) between ? and ? and status like ?)")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    ReturDetail d = new ReturDetail();
                    d.setNoRetur(rs.getString(1));
                    d.setNoUrutPenjualan(rs.getString(2));
                    d.setKodeBarang(rs.getString(3));
                    d.setNamaBarang(rs.getString(4));
                    d.setSatuan(rs.getString(5));
                    d.setQty(rs.getDouble(6));
                    d.setNilai(rs.getDouble(7));
                    allDetail.add(d);
                }
            }
        }
        return allDetail;
    }
    public static List<ReturDetail> getAllByNoPenjualanAndNoUrutAndStatus(Connection con, String noPenjualan,String noUrut,String status)throws Exception{
        List<ReturDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_detail b,tt_retur_head a "
                + " where a.no_retur=b.no_retur and a.no_penjualan like ? and b.no_urut_penjualan like ? and a.status like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, noUrut);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    ReturDetail d = new ReturDetail();
                    d.setNoRetur(rs.getString(1));
                    d.setNoUrutPenjualan(rs.getString(2));
                    d.setKodeBarang(rs.getString(3));
                    d.setNamaBarang(rs.getString(4));
                    d.setSatuan(rs.getString(5));
                    d.setQty(rs.getDouble(6));
                    d.setNilai(rs.getDouble(7));
                    allDetail.add(d);
                }
            }
        }
        return allDetail;
    }
}
