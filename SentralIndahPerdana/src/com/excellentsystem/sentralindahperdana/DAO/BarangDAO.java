/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class BarangDAO {
    public static List<Barang> getAllByStatus(Connection con, String status)throws Exception{
        List<Barang> allBarang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_barang where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allBarang = new ArrayList<>();
                while(rs.next()){
                    Barang b = new Barang();
                    b.setKodeBarang(rs.getString(1));
                    b.setKategoriBarang(rs.getString(2));
                    b.setNamaBarang(rs.getString(3));
                    b.setKeterangan(rs.getString(4));
                    b.setSatuanDasar(rs.getString(5));
                    b.setLaba(rs.getDouble(6));
                    b.setStatus(rs.getString(7));
                    allBarang.add(b);
                }
            }
        }
        return allBarang;
    }
    public static String getId(Connection con)throws Exception{
        String kodeBarang;
        try (PreparedStatement ps = con.prepareStatement("select max(right(kode_barang,5)) from tm_barang ")) {
            DecimalFormat df = new DecimalFormat("00000");
            kodeBarang = "B-"+df.format(1);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    kodeBarang = "B-"+df.format(rs.getInt(1)+1);
            }
        }
        return kodeBarang;
    }
    public static void insert(Connection con, Barang b)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_barang values(?,?,?,?,?,?,?)")) {
            ps.setString(1, b.getKodeBarang());
            ps.setString(2, b.getKategoriBarang());
            ps.setString(3, b.getNamaBarang());
            ps.setString(4, b.getKeterangan());
            ps.setString(5, b.getSatuanDasar());
            ps.setDouble(6, b.getLaba());
            ps.setString(7, b.getStatus());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, Barang b)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_barang set kategori_barang=?, nama_barang=?, keterangan=?, "
                + " satuan_dasar=?, laba=?, status=? where kode_barang=?")) {
            ps.setString(1, b.getKategoriBarang());
            ps.setString(2, b.getNamaBarang());
            ps.setString(3, b.getKeterangan());
            ps.setString(4, b.getSatuanDasar());
            ps.setDouble(5, b.getLaba());
            ps.setString(6, b.getStatus());
            ps.setString(7, b.getKodeBarang());
            ps.executeUpdate();
        }
    }
}
