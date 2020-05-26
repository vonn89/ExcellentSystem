/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SubKategoriDAO {
    public static SubKategori get(Connection con, String kodeSubKategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_sub_kategori where kode_sub_kategori = ? ");
        ps.setString(1, kodeSubKategori);
        ResultSet rs = ps.executeQuery();
        SubKategori s = null;
        while(rs.next()){
            s = new SubKategori();
            s.setKodeSubKategori(rs.getString(1));
            s.setNamaSubKategori(rs.getString(2));
            s.setKodeKategori(rs.getString(3));
        }
        return s;
    }
    public static List<SubKategori> getAll(Connection con)throws Exception{
        List<SubKategori> allSubKategori = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_sub_kategori");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            SubKategori s = new SubKategori();
            s.setKodeSubKategori(rs.getString(1));
            s.setNamaSubKategori(rs.getString(2));
            s.setKodeKategori(rs.getString(3));
            allSubKategori.add(s);
        }
        return allSubKategori;
    }
    public static void update(Connection con, SubKategori s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_sub_kategori set "
                + " nama_sub_kategori=?, kode_kategori=?"
                + " where kode_sub_kategori=?");
        ps.setString(1, s.getNamaSubKategori());
        ps.setString(2, s.getKodeKategori());
        ps.setString(3, s.getKodeSubKategori());
        ps.executeUpdate();
    }
    public static void insert(Connection con,SubKategori s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_sub_kategori values(?,?,?)");
        ps.setString(1, s.getKodeSubKategori());
        ps.setString(2, s.getNamaSubKategori());
        ps.setString(3, s.getKodeKategori());
        ps.executeUpdate();
    }
    public static void delete(Connection con, SubKategori s)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_sub_kategori where kode_sub_kategori = ?");
        ps.setString(1, s.getKodeSubKategori());
        ps.executeUpdate();
    }
}
