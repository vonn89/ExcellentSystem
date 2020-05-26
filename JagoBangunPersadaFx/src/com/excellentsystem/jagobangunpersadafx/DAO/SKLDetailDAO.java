/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SKLDetailDAO {
    public static List<SKLDetail> getAllByDateAndStatus(Connection con, String tglAwal, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_detail where no_skl in ( "
                + " select no_skl from tt_skl_head where left(tgl_skl,10) between ? and ? and status = ? )");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SKLDetail> allSKLDetail = new ArrayList<>();
        while(rs.next()){
            SKLDetail d = new SKLDetail();
            d.setNoSKL(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSKLDetail.add(d);
        }
        return allSKLDetail;
    }
    public static List<SKLDetail> getAllByNoSkl(Connection con, String noSKL)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_detail where no_skl=?");
        ps.setString(1, noSKL);
        ResultSet rs = ps.executeQuery();
        List<SKLDetail> allSKLDetail = new ArrayList<>();
        while(rs.next()){
            SKLDetail d = new SKLDetail();
            d.setNoSKL(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSKLDetail.add(d);
        }
        return allSKLDetail;
    }
    public static void insert(Connection con, SKLDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_skl_detail values(?,?,?,?)");
        ps.setString(1, d.getNoSKL());
        ps.setString(2, d.getTahap());
        ps.setString(3, d.getTglBayar());
        ps.setDouble(4, d.getJumlahRp());
        ps.executeUpdate();
    }
}
