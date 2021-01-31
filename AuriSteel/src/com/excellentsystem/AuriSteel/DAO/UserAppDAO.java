/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.UserApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class UserAppDAO {
    public static List<UserApp> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user_app");
        ResultSet rs = ps.executeQuery();
        List<UserApp> allUser = new ArrayList<>();
        while(rs.next()){
            UserApp user = new UserApp();
            user.setKodeUser(rs.getString(1));
            user.setPin(rs.getString(2));
            allUser.add(user);
        }
        return allUser;
    }
    public static void update(Connection con, UserApp user)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_user_app set pin=? where kode_user=?");
        ps.setString(1, user.getPin());
        ps.setString(2, user.getKodeUser());
        ps.executeUpdate();
    }
    public static void insert(Connection con, UserApp user)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_user_app values(?,?)");
        ps.setString(1, user.getKodeUser());
        ps.setString(2, user.getPin());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_user_app where kode_user=?");
        ps.setString(1, kodeUser);
        ps.executeUpdate();
    }
}
