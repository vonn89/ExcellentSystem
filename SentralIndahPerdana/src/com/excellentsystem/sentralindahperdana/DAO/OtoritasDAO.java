/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class OtoritasDAO {
    public static void insert(Connection con, Otoritas o)throws Exception{
        PreparedStatement insert = con.prepareStatement("insert into tm_otoritas values(?,?,?)");
        insert.setString(1, o.getKodeUser());
        insert.setString(2, o.getJenis());
        insert.setString(3, String.valueOf(o.isStatus()));
        insert.executeUpdate();
    }
    public static void update(Connection con, Otoritas o)throws Exception{
        PreparedStatement update = con.prepareStatement("update tm_otoritas set status=? where username=? and jenis=?");
        update.setString(1, String.valueOf(o.isStatus()));
        update.setString(2, o.getKodeUser());
        update.setString(3, o.getJenis());
        update.executeUpdate();
    }
    public static void deleteAllByKodeUser(Connection con, String username)throws Exception{
        PreparedStatement deleteAllByKodeUser = con.prepareStatement("delete from tm_otoritas where username=?");
        deleteAllByKodeUser.setString(1, username);
        deleteAllByKodeUser.executeUpdate();
    }
    public static List<Otoritas> getAll(Connection con)throws Exception{
        PreparedStatement getAll = con.prepareStatement("select * from tm_otoritas ");
        ResultSet rs = getAll.executeQuery();
        List<Otoritas> allOtoritas = new ArrayList<>();
        while(rs.next()){
            Otoritas o = new Otoritas();
            o.setKodeUser(rs.getString(1));
            o.setJenis(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritas.add(o);
        }
        return allOtoritas;
    }
    public static List<Otoritas> getAllByKodeUser(Connection con, String username)throws Exception{
        PreparedStatement getAllByKodeUser = con.prepareStatement("select * from tm_otoritas where username=?");
        getAllByKodeUser.setString(1, username);
        ResultSet rs = getAllByKodeUser.executeQuery();
        List<Otoritas> allOtoritas = new ArrayList<>();
        while(rs.next()){
            Otoritas o = new Otoritas();
            o.setKodeUser(rs.getString(1));
            o.setJenis(rs.getString(2));
            o.setStatus(Boolean.parseBoolean(rs.getString(3)));
            allOtoritas.add(o);
        }
        return allOtoritas;
    }
}
