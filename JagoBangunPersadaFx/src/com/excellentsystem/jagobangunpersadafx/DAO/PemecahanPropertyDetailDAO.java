/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PemecahanPropertyDetailDAO {
    
    public static List<PemecahanPropertyDetail> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemecahan_property_detail where no_pemecahan in ("
                + "select no_pemecahan from tt_pemecahan_property_head where left(tgl_pemecahan,10) between ? and ? and status =? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PemecahanPropertyDetail> allPemecahanPropertyDetail = new ArrayList<>();
        while(rs.next()){
            PemecahanPropertyDetail d = new PemecahanPropertyDetail();
            d.setNoPemecahan(rs.getString(1));
            d.setKodeProperty(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaProperty(rs.getString(4));
            d.setLuasTanah(rs.getDouble(5));
            d.setNilaiProperty(rs.getDouble(6));
            allPemecahanPropertyDetail.add(d);
        }
        return allPemecahanPropertyDetail;
    }
    public static List<PemecahanPropertyDetail> getAllByNoPemecahan(Connection con, String noPemecahan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemecahan_property_detail where no_pemecahan=?");
        ps.setString(1, noPemecahan);
        ResultSet rs = ps.executeQuery();
        List<PemecahanPropertyDetail> allPemecahanPropertyDetail = new ArrayList<>();
        while(rs.next()){
            PemecahanPropertyDetail d = new PemecahanPropertyDetail();
            d.setNoPemecahan(rs.getString(1));
            d.setKodeProperty(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setNamaProperty(rs.getString(4));
            d.setLuasTanah(rs.getDouble(5));
            d.setNilaiProperty(rs.getDouble(6));
            allPemecahanPropertyDetail.add(d);
        }
        return allPemecahanPropertyDetail;
    }
    public static void insert(Connection con, PemecahanPropertyDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemecahan_property_detail values(?,?,?,?,?,?)");
        ps.setString(1, d.getNoPemecahan());
        ps.setString(2, d.getKodeProperty());
        ps.setString(3, d.getKodeKategori());
        ps.setString(4, d.getNamaProperty());
        ps.setDouble(5, d.getLuasTanah());
        ps.setDouble(6, d.getNilaiProperty());
        ps.executeUpdate();
    }
}
