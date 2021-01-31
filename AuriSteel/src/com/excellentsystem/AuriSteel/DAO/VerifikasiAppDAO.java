/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.User;
import com.excellentsystem.AuriSteel.Model.VerifikasiApp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class VerifikasiAppDAO {
    
    public static List<VerifikasiApp> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_verifikasi_app");
        ResultSet rs = ps.executeQuery();
        List<VerifikasiApp> allVerifikasiApp = new ArrayList<>();
        while(rs.next()){
            VerifikasiApp o = new VerifikasiApp();
            o.setKodeUser(rs.getString(1));
            o.setJenis(rs.getString(2));
            o.setStatus(rs.getString(3));
            allVerifikasiApp.add(o);
        }
        return allVerifikasiApp;
    }
    public static List<VerifikasiApp> getAllByKodeUser(Connection con, String kodeUser)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_verifikasi_app where kode_user=?");
        ps.setString(1, kodeUser);
        ResultSet rs = ps.executeQuery();
        List<VerifikasiApp> allVerifikasiApp = new ArrayList<>();
        while(rs.next()){
            VerifikasiApp o = new VerifikasiApp();
            o.setKodeUser(rs.getString(1));
            o.setJenis(rs.getString(2));
            o.setStatus(rs.getString(3));
            allVerifikasiApp.add(o);
        }
        return allVerifikasiApp;
    }
    public static void insert(Connection con, VerifikasiApp otoritas)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_verifikasi_app values(?,?,?)");
        ps.setString(1, otoritas.getKodeUser());
        ps.setString(2, otoritas.getJenis());
        ps.setString(3, otoritas.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, VerifikasiApp otoritas)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_verifikasi_app set status=? where kode_user=? and jenis=?");
        ps.setString(1, otoritas.getStatus());
        ps.setString(2, otoritas.getKodeUser());
        ps.setString(3, otoritas.getJenis());
        ps.executeUpdate();
    }
    public static void delete(Connection con, User user)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_verifikasi_app where kode_user=?");
        ps.setString(1, user.getKodeUser());
        ps.executeUpdate();
    }
}
