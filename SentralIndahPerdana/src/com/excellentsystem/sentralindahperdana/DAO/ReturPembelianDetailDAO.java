/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.ReturPembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class ReturPembelianDetailDAO {
    public static void insert(Connection con, ReturPembelianDetail d)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_retur_pembelian_detail values(?,?,?,?,?,?)")) {
            ps.setString(1, d.getNoRetur());
            ps.setString(2, d.getKodeBarang());
            ps.setString(3, d.getNamaBarang());
            ps.setString(4, d.getSatuan());
            ps.setDouble(5, d.getQty());
            ps.setDouble(6, d.getHarga());
            ps.executeUpdate();
        }
    }
    public static List<ReturPembelianDetail> getAllByNoRetur(Connection con, String no_retur)throws Exception{
        List<ReturPembelianDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_pembelian_detail where no_retur like ?")) {
            ps.setString(1, no_retur);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    ReturPembelianDetail d = new ReturPembelianDetail();
                    d.setNoRetur(rs.getString(1));
                    d.setKodeBarang(rs.getString(2));
                    d.setNamaBarang(rs.getString(3));
                    d.setSatuan(rs.getString(4));
                    d.setQty(rs.getDouble(5));
                    d.setHarga(rs.getDouble(6));
                    allDetail.add(d);
                }
            }
        }
        return allDetail;
    }
    public static List<ReturPembelianDetail> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir,String status)throws Exception{
        List<ReturPembelianDetail> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_retur_pembelian_detail where no_retur in "
                + "(select no_retur from tt_retur_pembelian_head where left(tgl_retur,10) between ? and ? and status like ?)")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    ReturPembelianDetail d = new ReturPembelianDetail();
                    d.setNoRetur(rs.getString(1));
                    d.setKodeBarang(rs.getString(2));
                    d.setNamaBarang(rs.getString(3));
                    d.setSatuan(rs.getString(4));
                    d.setQty(rs.getDouble(5));
                    d.setHarga(rs.getDouble(6));
                    allDetail.add(d);
                }
            }
        }
        return allDetail;
    }
}
