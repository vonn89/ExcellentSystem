/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class KategoriTransaksiDAO {
    public static void insert(Connection con, KategoriTransaksi k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_kategori_transaksi values(?,?)")) {
            ps.setString(1, k.getKodeKategori());
            ps.setString(2, k.getJenisTransaksi());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, KategoriTransaksi k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_kategori_transaksi set jenis_transaksi=? where kode_kategori=?")) {
            ps.setString(1, k.getJenisTransaksi());
            ps.setString(2, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, KategoriTransaksi k)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tm_kategori_transaksi where kode_kategori=?")) {
            ps.setString(1, k.getKodeKategori());
            ps.executeUpdate();
        }
    }
    public static KategoriTransaksi get(Connection con, String kodeKategori)throws Exception{
        KategoriTransaksi k = null;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_kategori_transaksi where kode_kategori=?")) {
            ps.setString(1, kodeKategori);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    k = new KategoriTransaksi();
                    k.setKodeKategori(rs.getString(1));
                    k.setJenisTransaksi(rs.getString(2));
                }
            }
        }
        return k;
    }
    public static List<KategoriTransaksi> getAll(Connection con)throws Exception{
        List<KategoriTransaksi> allKategoriTransaksi;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_kategori_transaksi")) {
            try(ResultSet rs = ps.executeQuery()){
                allKategoriTransaksi = new ArrayList<>();
                while(rs.next()){
                    KategoriTransaksi k = new KategoriTransaksi();
                    k.setKodeKategori(rs.getString(1));
                    k.setJenisTransaksi(rs.getString(2));
                    allKategoriTransaksi.add(k);
                }
            }
        }
        return allKategoriTransaksi;
    }
}
