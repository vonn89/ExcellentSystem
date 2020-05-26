/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.STJDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class STJDetailDAO {
    
    public static List<STJDetail> getAllByDateAndStatus(Connection con, String tglAwal, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stj_detail where no_stj in ("
                + "select no_stj from tt_stj_head where left(tgl_stj,10) between ? and ? and status = ?)");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<STJDetail> allSTJDetail = new ArrayList<>();
        while(rs.next()){
            STJDetail d = new STJDetail();
            d.setNoSTJ(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSTJDetail.add(d);
        }
        return allSTJDetail;
    }
    public static List<STJDetail> getAllByNoSTJ(Connection con,String noSTJ)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stj_detail where no_stj=?");
        ps.setString(1, noSTJ);
        ResultSet rs = ps.executeQuery();
        List<STJDetail> allSTJDetail = new ArrayList<>();
        while(rs.next()){
            STJDetail d = new STJDetail();
            d.setNoSTJ(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSTJDetail.add(d);
        }
        return allSTJDetail;
    }
    public static void insert(Connection con, STJDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stj_detail values(?,?,?,?)");
        ps.setString(1, d.getNoSTJ());
        ps.setString(2, d.getTahap());
        ps.setString(3, d.getTglBayar());
        ps.setDouble(4, d.getJumlahRp());
        ps.executeUpdate();
    }
}
