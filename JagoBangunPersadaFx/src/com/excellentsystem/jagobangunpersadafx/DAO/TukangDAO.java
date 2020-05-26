/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Tukang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class TukangDAO {
    public static List<Tukang> getAllByStatus(Connection con, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_tukang where status=?");
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<Tukang> allTukang = new ArrayList<>();
        while(rs.next()){
            Tukang t = new Tukang();
            t.setKodeTukang(rs.getString(1));
            t.setNama(rs.getString(2));
            t.setAlamat(rs.getString(3));
            t.setNoTelp(rs.getString(4));
            t.setGajiPerHari(rs.getDouble(5));
            t.setStatus(rs.getString(6));
            allTukang.add(t);
        }
        return allTukang;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_tukang,3)) from tm_tukang");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "T-"+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "T-"+new DecimalFormat("000").format(1);
    }
    public static void update(Connection con, Tukang t)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_tukang set nama=?, alamat=?, no_telp=?, gaji_per_hari=?, status =? "
            + " where kode_tukang=?");
        ps.setString(1, t.getNama());
        ps.setString(2, t.getAlamat());
        ps.setString(3, t.getNoTelp());
        ps.setDouble(4, t.getGajiPerHari());
        ps.setString(5, t.getStatus());
        ps.setString(6, t.getKodeTukang());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Tukang t)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_tukang values(?,?,?,?,?,?)");
        ps.setString(1, t.getKodeTukang());
        ps.setString(2, t.getNama());
        ps.setString(3, t.getAlamat());
        ps.setString(4, t.getNoTelp());
        ps.setDouble(5, t.getGajiPerHari());
        ps.setString(6, t.getStatus());
        ps.executeUpdate();
    }
}
