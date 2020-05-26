/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.KategoriFiktif;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class KategoriFiktifDAO {
    
    public static List<KategoriFiktif> getAll(Connection con)throws Exception{
        List<KategoriFiktif> allKategori = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_fiktif");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            KategoriFiktif kategori = new KategoriFiktif();
            kategori.setKodeKategori(rs.getString(1));
            kategori.setHargaJualMin(rs.getDouble(2));
            kategori.setHargaJualMax(rs.getDouble(3));
            allKategori.add(kategori);
        }
        return allKategori;
    }
    public static void update(Connection con,KategoriFiktif kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori_fiktif set "
                + " harga_jual_min=?, harga_jual_max=? "
                + " where kode_kategori=?");
        ps.setDouble(1, kategori.getHargaJualMin());
        ps.setDouble(2, kategori.getHargaJualMax());
        ps.setString(3, kategori.getKodeKategori());
        ps.executeUpdate();
    }
    public static void insert(Connection con,KategoriFiktif kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_fiktif values(?,?,?)");
        ps.setString(1, kategori.getKodeKategori());
        ps.setDouble(2, kategori.getHargaJualMin());
        ps.setDouble(3, kategori.getHargaJualMax());
        ps.executeUpdate();
    }
    public static void delete(Connection con, KategoriFiktif kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_kategori_fiktif where kode_kategori = ?");
        ps.setString(1, kategori.getKodeKategori());
        ps.executeUpdate();
    }
}
