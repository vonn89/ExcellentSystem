/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import static com.excellentsystem.jagobangunpersadafx.Function.encrypt;
import static com.excellentsystem.jagobangunpersadafx.Main.key;
import com.excellentsystem.jagobangunpersadafx.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class UserDAO {
    public static List<User> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user");
        ResultSet rs = ps.executeQuery();
        List<User> allUser = new ArrayList<>();
        while(rs.next()){
            User user = new User();
            user.setUsername(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setLevel(rs.getString(3));
            allUser.add(user);
        }
        return allUser;
    }
    public static User get(Connection con,String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_user where username=? ");
        ps.setString(1, kodeUser);
        ResultSet rs = ps.executeQuery();
        User user = null;
        while(rs.next()){
            user = new User();
            user.setUsername(rs.getString(1));
            user.setPassword(rs.getString(2));
            user.setLevel(rs.getString(3));
        }
        return user;
    }
    public static void update(Connection con, User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_user set password=?, level=? where username=?");
        ps.setString(1, encrypt(user.getPassword(), key));
        ps.setString(2, user.getLevel());
        ps.setString(3, user.getUsername());
        ps.executeUpdate();
    }
    public static void insert(Connection con, User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_user values(?,?,?)");
        ps.setString(1, user.getUsername());
        ps.setString(2, encrypt(user.getPassword(), key));
        ps.setString(3, user.getLevel());
        ps.executeUpdate();
    }
    public static void delete(Connection con,User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_user where username=?");
        ps.setString(1, user.getUsername());
        ps.executeUpdate();
                
    }
}
