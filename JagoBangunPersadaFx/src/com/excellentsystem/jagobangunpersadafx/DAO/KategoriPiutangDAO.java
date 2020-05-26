/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.KategoriPiutang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KategoriPiutangDAO {
    
    public static List<KategoriPiutang> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_piutang");
        ResultSet rs = ps.executeQuery();
        List<KategoriPiutang> allKategori = new ArrayList<>();
        while(rs.next()){
            KategoriPiutang k = new KategoriPiutang();
            k.setKodeKategori(rs.getString(1));
            allKategori.add(k);
        }
        return allKategori;
    }
    public static void insert(Connection con, KategoriPiutang k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_piutang values(?)");
        ps.setString(1, k.getKodeKategori());
        ps.executeUpdate();
    }
    public static void delete(Connection con, KategoriPiutang k)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_kategori_piutang where kode_kategori=?");
        ps.setString(1, k.getKodeKategori());
        ps.executeUpdate();
    }
}
