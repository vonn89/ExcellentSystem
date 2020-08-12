/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.KategoriBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class KategoriBarangDAO {
    public static void insert(Connection con, KategoriBarang k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_kategori_barang values(?)")) {
            ps.setString(1, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, KategoriBarang k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tm_kategori_barang where kode_kategori=?")) {
            ps.setString(1, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static List<KategoriBarang> getAll(Connection con)throws Exception{
        List<KategoriBarang> allKategoriBarang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_kategori_barang")) {
            try(ResultSet rs = ps.executeQuery()){
                allKategoriBarang = new ArrayList<>();
                while(rs.next()){
                    KategoriBarang k = new KategoriBarang();
                    k.setKodeKategori(rs.getString(1));
                    allKategoriBarang.add(k);
                }
            }
        }
        return allKategoriBarang;
    }
}
