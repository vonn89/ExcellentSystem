/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianDetailDAO {
    public static List<PembelianDetail> getAllByDateAndStatus(
            Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        List<PembelianDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail where no_pembelian in "
                + "( select no_pembelian from tt_pembelian_head where left(tgl_pembelian,10) between ? and ? and status like ? )")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PembelianDetail detail = new PembelianDetail();
                    detail.setNoPembelian(rs.getString(1));
                    detail.setKodeBarang(rs.getString(2));
                    detail.setNamaBarang(rs.getString(3));
                    detail.setSatuan(rs.getString(4));
                    detail.setQty(rs.getDouble(5));
                    detail.setHarga(rs.getDouble(6));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static List<PembelianDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        List<PembelianDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail where no_pembelian=?")) {
            ps.setString(1, noPembelian);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    PembelianDetail detail = new PembelianDetail();
                    detail.setNoPembelian(rs.getString(1));
                    detail.setKodeBarang(rs.getString(2));
                    detail.setNamaBarang(rs.getString(3));
                    detail.setSatuan(rs.getString(4));
                    detail.setQty(rs.getDouble(5));
                    detail.setHarga(rs.getDouble(6));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static void insert(Connection con, PembelianDetail detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_detail values(?,?,?,?,?,?)")) {
            ps.setString(1, detail.getNoPembelian());
            ps.setString(2, detail.getKodeBarang());
            ps.setString(3, detail.getNamaBarang());
            ps.setString(4, detail.getSatuan());
            ps.setDouble(5, detail.getQty());
            ps.setDouble(6, detail.getHarga());
            ps.executeUpdate();
        }
    }
}
