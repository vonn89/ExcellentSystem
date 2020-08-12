/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.KategoriPekerjaan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class KategoriPekerjaanDAO {
    public static void insert(Connection con, KategoriPekerjaan k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_kategori_pekerjaan values(?)")) {
            ps.setString(1, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, KategoriPekerjaan k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tm_kategori_pekerjaan where kode_kategori=?")) {
            ps.setString(1, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static List<KategoriPekerjaan> getAll(Connection con)throws Exception{
        List<KategoriPekerjaan> allKategoriPekerjaan;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_kategori_pekerjaan")) {
            try(ResultSet rs = ps.executeQuery()){
                allKategoriPekerjaan = new ArrayList<>();
                while(rs.next()){
                    KategoriPekerjaan k = new KategoriPekerjaan();
                    k.setKodeKategori(rs.getString(1));
                    allKategoriPekerjaan.add(k);
                }
            }
        }
        return allKategoriPekerjaan;
    }
}
