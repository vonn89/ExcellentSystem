/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Sistem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author yunaz
 */
public class SistemDAO {
    public static void update(Connection con, Sistem b)throws Exception{
        PreparedStatement update = con.prepareStatement("update tm_system set "
                + " nama=?, alamat=?, no_telp=?, version=?");
        update.setString(1, b.getNama());
        update.setString(2, b.getAlamat());
        update.setString(3, b.getNoTelp());
        update.setString(4, b.getVersion());
        update.executeUpdate();
    }
    public static Sistem get(Connection con)throws Exception{
        PreparedStatement get = con.prepareStatement("select * from tm_system");
        ResultSet rs = get.executeQuery();
        Sistem s = new Sistem();
        while(rs.next()){
            s.setNama(rs.getString(1));
            s.setAlamat(rs.getString(2));
            s.setNoTelp(rs.getString(3));
            s.setVersion(rs.getString(4));
        }
        return s;
    }
}
