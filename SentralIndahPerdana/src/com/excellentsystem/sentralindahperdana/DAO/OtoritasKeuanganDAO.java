/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.OtoritasKeuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class OtoritasKeuanganDAO {
    public static void insert(Connection con, OtoritasKeuangan o)throws Exception{
        PreparedStatement insert = con.prepareStatement("insert into tm_otoritas_keuangan values(?,?,?)");
        insert.setString(1, o.getKodeUser());
        insert.setString(2, o.getKodeKeuangan());
        insert.setString(3, String.valueOf(o.isStatus()));
        insert.executeUpdate();
    }
    public static void update(Connection con, OtoritasKeuangan o)throws Exception{
        PreparedStatement update = con.prepareStatement("update tm_otoritas_keuangan set status=? where username=? and kode_keuangan=?");
        update.setString(1, String.valueOf(o.isStatus()));
        update.setString(2, o.getKodeUser());
        update.setString(3, o.getKodeKeuangan());
        update.executeUpdate();
    }
    public static void deleteByKodeUser(Connection con, String username)throws Exception{
        PreparedStatement deleteByKodeUser = con.prepareStatement("delete from tm_otoritas_keuangan where username=?");
        deleteByKodeUser.setString(1, username);
        deleteByKodeUser.executeUpdate();
    }
    public static void deleteByKodeKeuangan(Connection con, String kode_keuangan)throws Exception{
        PreparedStatement deleteByKodeKeuangan = con.prepareStatement("delete from tm_otoritas_keuangan where kode_keuangan=?");
        deleteByKodeKeuangan.setString(1, kode_keuangan);
        deleteByKodeKeuangan.executeUpdate();
    }
    public static List<OtoritasKeuangan> getAll(Connection con)throws Exception{
        PreparedStatement getAll = con.prepareStatement("select * from tm_otoritas_keuangan ");
        ResultSet rs = getAll.executeQuery();
        List<OtoritasKeuangan> allOtoritasKeuangan = new ArrayList<>();
        while(rs.next()){
            OtoritasKeuangan o = new OtoritasKeuangan();
            o.setKodeUser(rs.getString(1));
            o.setKodeKeuangan(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritasKeuangan.add(o);
        }
        return allOtoritasKeuangan;
    }
    public static List<OtoritasKeuangan> getAllByKodeUser(Connection con, String username)throws Exception{
        PreparedStatement getAllByKodeUser = con.prepareStatement("select * from tm_otoritas_keuangan where username=? ");
        getAllByKodeUser.setString(1, username);
        ResultSet rs = getAllByKodeUser.executeQuery();
        List<OtoritasKeuangan> allOtoritasKeuangan = new ArrayList<>();
        while(rs.next()){
            OtoritasKeuangan o = new OtoritasKeuangan();
            o.setKodeUser(rs.getString(1));
            o.setKodeKeuangan(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritasKeuangan.add(o);
        }
        return allOtoritasKeuangan;
    }
}
