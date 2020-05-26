/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class JenisDAO {
    public static List<Jenis> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_jenis ");
        List<Jenis> allJenis = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Jenis jenis = new Jenis();
            jenis.setKodeJenis(rs.getString(1));
            jenis.setNamaJenis(rs.getString(2));
            jenis.setKodeKategori(rs.getString(3));
            jenis.setKodeSubKategori(rs.getString(4));
            allJenis.add(jenis);
        }
        return allJenis;
    }
    public static Jenis get(Connection con, String kodeJenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_jenis where kode_jenis = ?");
        ps.setString(1, kodeJenis);
        ResultSet rs = ps.executeQuery();
        Jenis jenis = null;
        while(rs.next()){
            jenis = new Jenis();
            jenis.setKodeJenis(rs.getString(1));
            jenis.setNamaJenis(rs.getString(2));
            jenis.setKodeKategori(rs.getString(3));
            jenis.setKodeSubKategori(rs.getString(4));
        }
        return jenis;
    }
    public static void update(Connection con,Jenis jenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_jenis set "
                + " nama_jenis=?, kode_kategori=?, kode_sub_kategori=? "
                + " where kode_jenis=?");
        ps.setString(1, jenis.getNamaJenis());
        ps.setString(2, jenis.getKodeKategori());
        ps.setString(3, jenis.getKodeSubKategori());
        ps.setString(4, jenis.getKodeJenis());
        ps.executeUpdate();
    }
    public static void insert(Connection con,Jenis jenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_jenis values(?,?,?,?)");
        ps.setString(1, jenis.getKodeJenis());
        ps.setString(2, jenis.getNamaJenis());
        ps.setString(3, jenis.getKodeKategori());
        ps.setString(4, jenis.getKodeSubKategori());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Jenis jenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_jenis where kode_jenis = ?");
        ps.setString(1, jenis.getKodeJenis());
        ps.executeUpdate();
    }
}
