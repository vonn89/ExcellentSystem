/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PembangunanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembangunanDetailDAO {
    
    public static List<PembangunanDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_detail where no_pembangunan in ("
                + " select no_pembangunan from tt_pembangunan_head where left(tgl_pembangunan,10) between ? and ? and status = ? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembangunanDetail> allPengelolaanTanahDetail = new ArrayList<>();
        while(rs.next()){
            PembangunanDetail p = new PembangunanDetail();
            p.setNoPembangunan(rs.getString(1));
            p.setKodeProperty(rs.getString(2));
            p.setNamaProperty(rs.getString(3));
            p.setLuasTanah(rs.getDouble(4));
            p.setBiaya(rs.getDouble(5));
            allPengelolaanTanahDetail.add(p);
        }
        return allPengelolaanTanahDetail;
    }
    public static List<PembangunanDetail> getAllByNo(Connection con,String noPembangunan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_detail where no_pembangunan=?");
        ps.setString(1, noPembangunan);
        ResultSet rs = ps.executeQuery();
        List<PembangunanDetail> allPengelolaanTanahDetail = new ArrayList<>();
        while(rs.next()){
            PembangunanDetail p = new PembangunanDetail();
            p.setNoPembangunan(rs.getString(1));
            p.setKodeProperty(rs.getString(2));
            p.setNamaProperty(rs.getString(3));
            p.setLuasTanah(rs.getDouble(4));
            p.setBiaya(rs.getDouble(5));
            allPengelolaanTanahDetail.add(p);
        }
        return allPengelolaanTanahDetail;
    }
    public static void insert(Connection con, PembangunanDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembangunan_detail values(?,?,?,?,?)");
        ps.setString(1, d.getNoPembangunan());
        ps.setString(2, d.getKodeProperty());
        ps.setString(3, d.getNamaProperty());
        ps.setDouble(4, d.getLuasTanah());
        ps.setDouble(5, d.getBiaya());
        ps.executeUpdate();
    }
}
