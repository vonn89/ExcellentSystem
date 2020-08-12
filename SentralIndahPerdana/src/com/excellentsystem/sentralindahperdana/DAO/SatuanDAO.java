/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Satuan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class SatuanDAO {
    public static void insert(Connection con, Satuan s)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_satuan values(?,?,?)")) {
            ps.setString(1, s.getKodeBarang());
            ps.setString(2, s.getKodeSatuan());
            ps.setDouble(3, s.getQty());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, Satuan s)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_satuan set qty=? where kode_satuan=? and kode_barang=?")) {
            ps.setDouble(1, s.getQty());
            ps.setString(2, s.getKodeSatuan());
            ps.setString(3, s.getKodeBarang());
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, Satuan b)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tm_satuan where kode_satuan=? and kode_barang=?")) {
            ps.setString(1, b.getKodeSatuan());
            ps.setString(2, b.getKodeBarang());
            ps.executeUpdate();
        }
    }
    public static void deleteAllByKodeBarang(Connection con, String kode_barang)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tm_satuan where kode_barang=?")) {
            ps.setString(1, kode_barang);
            ps.executeUpdate();
        }
    }
    public static Satuan get(Connection con, String kode_barang,String kode_satuan)throws Exception{
        Satuan s;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_satuan where kode_barang=? and kode_satuan=?")) {
            ps.setString(1, kode_barang);
            ps.setString(2, kode_satuan);
            try (ResultSet rs = ps.executeQuery()) {
                s = null;
                while(rs.next()){
                    s = new Satuan();
                    s.setKodeBarang(rs.getString(1));
                    s.setKodeSatuan(rs.getString(2));
                    s.setQty(rs.getDouble(3));
                }
            }
        }
        return s;
    }
    public static List<Satuan> getAll(Connection con)throws Exception{
        List<Satuan> allSatuan;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_satuan ")) {
            try(ResultSet rs = ps.executeQuery()){
                allSatuan = new ArrayList<>();
                while(rs.next()){
                    Satuan s = new Satuan();
                    s.setKodeBarang(rs.getString(1));
                    s.setKodeSatuan(rs.getString(2));
                    s.setQty(rs.getDouble(3));
                    allSatuan.add(s);
                }
            }
        }
        return allSatuan;
    }
    public static List<Satuan> getAllByKodeBarang(Connection con, String kode_barang)throws Exception{
        List<Satuan> allSatuan;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_satuan where kode_barang=?")) {
            ps.setString(1, kode_barang);
            try (ResultSet rs = ps.executeQuery()) {
                allSatuan = new ArrayList<>();
                while(rs.next()){
                    Satuan s = new Satuan();
                    s.setKodeBarang(rs.getString(1));
                    s.setKodeSatuan(rs.getString(2));
                    s.setQty(rs.getDouble(3));
                    allSatuan.add(s);
                }
            }
        }
        return allSatuan;
    }
}
