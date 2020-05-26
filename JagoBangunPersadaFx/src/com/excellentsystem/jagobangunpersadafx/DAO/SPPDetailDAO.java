/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SPPDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SPPDetailDAO {
    
    public static List<SPPDetail> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_spp_detail where no_spp in ("
                + "select no_spp from tt_spp_head where left(tgl_spp,10) between ? and ? and status= ?)");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SPPDetail> allSPPDetail = new ArrayList<>();
        while(rs.next()){
            SPPDetail d = new SPPDetail();
            d.setNoSPP(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSPPDetail.add(d);
        }
        return allSPPDetail;
    }
    public static List<SPPDetail> getAllByNoSPP(Connection con,String noSPP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_spp_detail where no_spp=?");
        ps.setString(1, noSPP);
        ResultSet rs = ps.executeQuery();
        List<SPPDetail> allSPPDetail = new ArrayList<>();
        while(rs.next()){
            SPPDetail d = new SPPDetail();
            d.setNoSPP(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSPPDetail.add(d);
        }
        return allSPPDetail;
    }
    public static void insert(Connection con, SPPDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_spp_detail values(?,?,?,?)");
        ps.setString(1, d.getNoSPP());
        ps.setString(2, d.getTahap());
        ps.setString(3, d.getTglBayar());
        ps.setDouble(4, d.getJumlahRp());
        ps.executeUpdate();
    }
}
