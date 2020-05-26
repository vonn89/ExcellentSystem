/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KategoriDAO {
    public static List<Kategori> getAll(Connection con)throws Exception{
        List<Kategori> allKategori = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Kategori kategori = new Kategori();
            kategori.setKodeKategori(rs.getString(1));
            kategori.setNamaKategori(rs.getString(2));
            kategori.setPersentaseEmas(rs.getDouble(3));
            kategori.setHargaPersenJual(rs.getDouble(4));
            kategori.setKadar(rs.getString(5));
            allKategori.add(kategori);
        }
        return allKategori;
    }
    public static Kategori get(Connection con, String kodeKategori)throws Exception{
        Kategori k = new Kategori();
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori where kode_kategori = ?");
        ps.setString(1, kodeKategori);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            k = new Kategori();
            k.setKodeKategori(rs.getString(1));
            k.setNamaKategori(rs.getString(2));
            k.setPersentaseEmas(rs.getDouble(3));
            k.setHargaPersenJual(rs.getDouble(4));
            k.setKadar(rs.getString(5));
        }
        return k;
    }
    public static void update(Connection con,Kategori kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori set "
                + " nama_kategori=?, persentase_emas=?, "
                + " harga_persen_jual=?, kadar=?"
                + " where kode_kategori=?");
        ps.setString(1, kategori.getNamaKategori());
        ps.setDouble(2, kategori.getPersentaseEmas());
        ps.setDouble(3, kategori.getHargaPersenJual());
        ps.setString(4, kategori.getKadar());
        ps.setString(5, kategori.getKodeKategori());
        ps.executeUpdate();
    }
    public static void insert(Connection con,Kategori kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori values(?,?,?,?,?)");
        ps.setString(1, kategori.getKodeKategori());
        ps.setString(2, kategori.getNamaKategori());
        ps.setDouble(3, kategori.getPersentaseEmas());
        ps.setDouble(4, kategori.getHargaPersenJual());
        ps.setString(5, kategori.getKadar());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Kategori kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_kategori where kode_kategori = ?");
        ps.setString(1, kategori.getKodeKategori());
        ps.executeUpdate();
    }
}
