/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class UserDAO {
    public static void insert(Connection con, User u)throws Exception{
        try(PreparedStatement insert = con.prepareStatement("insert into tm_user values(?,?,?)")){
            insert.setString(1, u.getUsername());
            insert.setString(2, u.getPassword());
            insert.setString(3, u.getStatus());
            insert.executeUpdate();
        }
    }
    public static void update(Connection con, User k)throws Exception{
        try(PreparedStatement update = con.prepareStatement("update tm_user set password=?, status=? where username=?")){
            update.setString(1, k.getPassword());
            update.setString(2, k.getStatus());
            update.setString(3, k.getUsername());
            update.executeUpdate();
        }
    }
    public static void delete(Connection con, User u)throws Exception{
        try(PreparedStatement ps = con.prepareStatement("delete from tm_user where username=?")){
            ps.setString(1, u.getUsername());
            ps.executeUpdate();
        }
    }
    public static List<User> getAllByStatus(Connection con, String status)throws Exception{
        List<User> allUser = new ArrayList<>();
        try(PreparedStatement getAllByStatus = con.prepareStatement("select * from tm_user where status =?")){
            getAllByStatus.setString(1, status);
            try(ResultSet rs = getAllByStatus.executeQuery()){
                while(rs.next()){
                    User u = new User();
                    u.setUsername(rs.getString(1));
                    u.setPassword(rs.getString(2));
                    u.setStatus(rs.getString(3));
                    allUser.add(u);
                }
            }
        }
        return allUser;
    }
}
