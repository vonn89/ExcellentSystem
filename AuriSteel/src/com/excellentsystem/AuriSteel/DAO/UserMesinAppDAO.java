/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.User;
import com.excellentsystem.AuriSteel.Model.UserMesinApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class UserMesinAppDAO {
    public static List<UserMesinApp> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user_mesin_app");
        ResultSet rs = ps.executeQuery();
        List<UserMesinApp> allUserMesinApp = new ArrayList<>();
        while(rs.next()){
            UserMesinApp o = new UserMesinApp();
            o.setKodeUser(rs.getString(1));
            o.setKodeMesin(rs.getString(2));
            o.setStatus(rs.getString(3));
            allUserMesinApp.add(o);
        }
        return allUserMesinApp;
    }
    public static List<UserMesinApp> getAllByKodeUser(Connection con, String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user_mesin_app where kode_user=?");
        ps.setString(1, kodeUser);
        ResultSet rs = ps.executeQuery();
        List<UserMesinApp> allUserMesinApp = new ArrayList<>();
        while(rs.next()){
            UserMesinApp o = new UserMesinApp();
            o.setKodeUser(rs.getString(1));
            o.setKodeMesin(rs.getString(2));
            o.setStatus(rs.getString(3));
            allUserMesinApp.add(o);
        }
        return allUserMesinApp;
    }
    public static void insert(Connection con, UserMesinApp otoritas)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_user_mesin_app values(?,?,?)");
        ps.setString(1, otoritas.getKodeUser());
        ps.setString(2, otoritas.getKodeMesin());
        ps.setString(3, otoritas.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, UserMesinApp otoritas)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_user_mesin_app set status=? where kode_user=? and kode_mesin=?");
        ps.setString(1, otoritas.getStatus());
        ps.setString(2, otoritas.getKodeUser());
        ps.setString(3, otoritas.getKodeMesin());
        ps.executeUpdate();
    }
    public static void delete(Connection con, User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_user_mesin_app where kode_user=?");
        ps.setString(1, user.getKodeUser());
        ps.executeUpdate();
    }
}
