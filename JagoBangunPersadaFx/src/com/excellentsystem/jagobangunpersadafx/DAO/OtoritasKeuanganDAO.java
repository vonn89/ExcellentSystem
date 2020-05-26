/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class OtoritasKeuanganDAO {
    public static List<OtoritasKeuangan> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_otoritas_keuangan");
        ResultSet rs = ps.executeQuery();
        List<OtoritasKeuangan> allOtoritasKeuangan = new ArrayList<>();
        while(rs.next()){
            OtoritasKeuangan o = new OtoritasKeuangan();
            o.setUsername(rs.getString(1));
            o.setKodeKeuangan(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritasKeuangan.add(o);
        }
        return allOtoritasKeuangan;
    }
    public static List<OtoritasKeuangan> getAllByUsername(Connection con,String username)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_otoritas_keuangan where username=?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        List<OtoritasKeuangan> allOtoritasKeuangan = new ArrayList<>();
        while(rs.next()){
            OtoritasKeuangan o = new OtoritasKeuangan();
            o.setUsername(rs.getString(1));
            o.setKodeKeuangan(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritasKeuangan.add(o);
        }
        return allOtoritasKeuangan;
    }
    public static void insert(Connection con, OtoritasKeuangan o)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_otoritas_keuangan values(?,?,?)");
        ps.setString(1, o.getUsername());
        ps.setString(2, o.getKodeKeuangan());
        ps.setString(3, String.valueOf(o.isStatus()));
        ps.executeUpdate();
    }
    public static void update(Connection con, OtoritasKeuangan o)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_otoritas_keuangan set status=? where username=? and kode_keuangan=?");
        ps.setString(1, String.valueOf(o.isStatus()));
        ps.setString(2, o.getUsername());
        ps.setString(3, o.getKodeKeuangan());
        ps.executeUpdate();
    }
    public static void deleteAllByUsername(Connection con, String username)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_otoritas_keuangan where username=?");
        ps.setString(1, username);
        ps.executeUpdate();
    }
    public static void deleteAllByKodeKeuangan(Connection con, String keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_otoritas_keuangan where kode_keuangan=?");
        ps.setString(1, keu);
        ps.executeUpdate();
    }
}
