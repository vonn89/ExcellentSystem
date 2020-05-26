/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RAPDetailDAO {
    
    public static List<RAPDetail> getAllByDateAndStatus(Connection con, 
            String tglAwal, String tglAkhir, String statusApproval, String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_rap_detail where no_rap in ( "
                + " select no_rap from tt_rap_head where left(tgl_rap,10) between ? and ? ";
        if(!statusApproval.equals("%"))
            sql = sql + " and status_approval = '"+statusApproval+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<RAPDetail> allRAPDetail = new ArrayList<>();
        while(rs.next()){
            RAPDetail d = new RAPDetail();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKategori(rs.getString(3));
            d.setPekerjaan(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setVolume(rs.getDouble(7));
            d.setHargaSatuan(rs.getDouble(8));
            d.setTotal(rs.getDouble(9));
            allRAPDetail.add(d);
        }
        return allRAPDetail;
    }
    public static List<RAPDetail> getAllByNoRap(Connection con, String noRap)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rap_detail where no_rap=?");
        ps.setString(1, noRap);
        ResultSet rs = ps.executeQuery();
        List<RAPDetail> allRAPDetail = new ArrayList<>();
        while(rs.next()){
            RAPDetail d = new RAPDetail();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKategori(rs.getString(3));
            d.setPekerjaan(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setSatuan(rs.getString(6));
            d.setVolume(rs.getDouble(7));
            d.setHargaSatuan(rs.getDouble(8));
            d.setTotal(rs.getDouble(9));
            allRAPDetail.add(d);
        }
        return allRAPDetail;
    }
    public static void insert(Connection con, RAPDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rap_detail values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoRap());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKategori());
        ps.setString(4, d.getPekerjaan());
        ps.setString(5, d.getKeterangan());
        ps.setString(6, d.getSatuan());
        ps.setDouble(7, d.getVolume());
        ps.setDouble(8, d.getHargaSatuan());
        ps.setDouble(9, d.getTotal());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String noRAP)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rap_detail where no_rap = ?");
        ps.setString(1, noRAP);
        ps.executeUpdate();
    }
}
