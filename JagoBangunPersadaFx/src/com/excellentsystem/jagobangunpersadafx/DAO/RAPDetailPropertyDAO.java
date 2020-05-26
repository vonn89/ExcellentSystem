/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RAPDetailPropertyDAO {
    
    public static List<RAPDetailProperty> getAllByDateAndStatus(Connection con, 
            String tglAwal, String tglAkhir, String statusApproval, String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_rap_detail_property where no_rap in ( "
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
        List<RAPDetailProperty> allRAPDetailProperty = new ArrayList<>();
        while(rs.next()){
            RAPDetailProperty d = new RAPDetailProperty();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeProperty(rs.getString(3));
            d.setLuasTanah(rs.getDouble(4));
            d.setPersentase(rs.getDouble(5));
            allRAPDetailProperty.add(d);
        }
        return allRAPDetailProperty;
    }
    public static List<RAPDetailProperty> getAllByNoRap(Connection con, String noRap)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rap_detail_property where no_rap=?");
        ps.setString(1, noRap);
        ResultSet rs = ps.executeQuery();
        List<RAPDetailProperty> allRAPDetailProperty = new ArrayList<>();
        while(rs.next()){
            RAPDetailProperty d = new RAPDetailProperty();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeProperty(rs.getString(3));
            d.setLuasTanah(rs.getDouble(4));
            d.setPersentase(rs.getDouble(5));
            allRAPDetailProperty.add(d);
        }
        return allRAPDetailProperty;
    }
    public static void insert(Connection con, RAPDetailProperty d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rap_detail_property values(?,?,?,?,?)");
        ps.setString(1, d.getNoRap());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeProperty());
        ps.setDouble(4, d.getLuasTanah());
        ps.setDouble(5, d.getPersentase());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String noRap)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_rap_detail_property where no_rap=?");
        ps.setString(1, noRap);
        ps.executeUpdate();
    }
}
