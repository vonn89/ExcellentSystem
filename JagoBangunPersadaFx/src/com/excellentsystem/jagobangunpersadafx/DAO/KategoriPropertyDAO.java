/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.KategoriProperty;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KategoriPropertyDAO {
    
    public static List<KategoriProperty> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_property ");
        ResultSet rs = ps.executeQuery();
        List<KategoriProperty> allKategoriProperty = new ArrayList<>();
        while(rs.next()){
            KategoriProperty k = new KategoriProperty();
            k.setKodeKategori(rs.getString(1));
            allKategoriProperty.add(k);
        }
        return allKategoriProperty;
    }
    public static void insert(Connection con, KategoriProperty k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_property values(?)");
        ps.setString(1, k.getKodeKategori());
        ps.executeUpdate();
    }
    public static void delete(Connection con, KategoriProperty k)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_kategori_property where kode_kategori=?");
        ps.setString(1, k.getKodeKategori());
        ps.executeUpdate();
    }
}
