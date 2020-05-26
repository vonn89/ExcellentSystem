/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.BarangFiktif;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class BarangFiktifDAO {
    
    public static List<BarangFiktif> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_barang_fiktif");
        ResultSet rs = ps.executeQuery();
        List<BarangFiktif> allBarang = new ArrayList<>();
        while(rs.next()){
            BarangFiktif barang = new BarangFiktif();
            barang.setKodeKategori(rs.getString(1));
            barang.setNamaBarang(rs.getString(2));
            barang.setKadar(rs.getString(3));
            barang.setKodeIntern(rs.getString(4));
            allBarang.add(barang);
        }
        return allBarang;
    }
    public static void insert(Connection con,BarangFiktif b) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_barang_fiktif values(?,?,?,?)");
        ps.setString(1, b.getKodeKategori());
        ps.setString(2, b.getNamaBarang());
        ps.setString(3, b.getKadar());
        ps.setString(4, b.getKodeIntern());
        ps.executeUpdate();
    }
    public static void delete(Connection con, BarangFiktif b) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_barang_fiktif where kode_kategori = ? and "
                + " nama_barang = ? and kadar = ? and kode_intern = ?");
        ps.setString(1, b.getKodeKategori());
        ps.setString(2, b.getNamaBarang());
        ps.setString(3, b.getKadar());
        ps.setString(4, b.getKodeIntern());
        ps.executeUpdate();
    }
    public static void deleteAllByKategori(Connection con, String kodeKategori) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_barang_fiktif where kode_kategori = ?");
        ps.setString(1, kodeKategori);
        ps.executeUpdate();
    }
}
