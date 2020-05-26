/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Sistem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Xtreme
 */
public class SistemDAO {
    public static Sistem get(Connection con)throws Exception{
        Sistem s = null ;
        PreparedStatement ps = con.prepareStatement("select * from tm_system");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            s = new Sistem();
            s.setNamaPt(rs.getString(1));
            s.setAlamat(rs.getString(2));
            s.setKota(rs.getString(3));
            s.setNoTelp(rs.getString(4));
            s.setEmail(rs.getString(5));
            s.setLogo(rs.getString(6));
            s.setNamaDirektur(rs.getString(7));
            s.setVersion(rs.getString(8));
        }
        return s;
    }
    public void update(Connection con,Sistem s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_system set "
                + "nama_pt=?, alamat=?, kota=?, email=?, no_telp=?, logo=?, nama_direktur=?, version=? ");
        ps.setString(1, s.getNamaPt());
        ps.setString(2, s.getAlamat());
        ps.setString(3, s.getKota());
        ps.setString(4, s.getEmail());
        ps.setString(5, s.getNoTelp());
        ps.setString(6, s.getLogo());
        ps.setString(7, s.getNamaDirektur());
        ps.setString(8, s.getVersion());
        ps.executeUpdate();
    }
}
