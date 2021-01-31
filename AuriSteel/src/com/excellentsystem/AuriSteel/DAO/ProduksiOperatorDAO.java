/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.ProduksiOperator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class ProduksiOperatorDAO {
    public static List<ProduksiOperator> getAllByDateAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_operator "
                + " where kode_produksi in (select kode_produksi from tt_produksi_head"
                + " where left(tgl_produksi,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        System.out.println(sql);
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<ProduksiOperator> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiOperator d = new ProduksiOperator();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeOperator(rs.getString(2));
            d.setNamaOperator(rs.getString(3));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<ProduksiOperator> getAllByTglMulaiAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_operator "
                + " where kode_produksi in (select kode_produksi from tt_produksi_head"
                + " where left(tgl_mulai,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<ProduksiOperator> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiOperator d = new ProduksiOperator();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeOperator(rs.getString(2));
            d.setNamaOperator(rs.getString(3));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<ProduksiOperator> get(Connection con, String kodeProduksi)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_produksi_operator "
                + " where kode_produksi = ?");
        ps.setString(1, kodeProduksi);
        ResultSet rs = ps.executeQuery();
        List<ProduksiOperator> allDetail = new ArrayList<>();
        while(rs.next()){
            ProduksiOperator d = new ProduksiOperator();
            d.setKodeProduksi(rs.getString(1));
            d.setKodeOperator(rs.getString(2));
            d.setNamaOperator(rs.getString(3));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, ProduksiOperator d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_produksi_operator values(?,?,?)");
        ps.setString(1, d.getKodeProduksi());
        ps.setString(2, d.getKodeOperator());
        ps.setString(3, d.getNamaOperator());
        ps.executeUpdate();
    }
}
