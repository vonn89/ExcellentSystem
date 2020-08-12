/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PekerjaanDAO {
    
    public static List<Pekerjaan> getAllByStatus(Connection con, String status)throws Exception{
        List<Pekerjaan> allPekerjaan;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_pekerjaan where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allPekerjaan = new ArrayList<>();
                while(rs.next()){
                    Pekerjaan b = new Pekerjaan();
                    b.setKodePekerjaan(rs.getString(1));
                    b.setKategoriPekerjaan(rs.getString(2));
                    b.setNamaPekerjaan(rs.getString(3));
                    b.setSpesifikasi(rs.getString(4));
                    b.setKeterangan(rs.getString(5));
                    b.setSatuan(rs.getString(6));
                    b.setLaba(rs.getDouble(7));
                    b.setStatus(rs.getString(8));
                    allPekerjaan.add(b);
                }
            }
        }
        return allPekerjaan;
    }
    public static String getId(Connection con)throws Exception{
        String kodePekerjaan;
        try (PreparedStatement ps = con.prepareStatement("select max(right(kode_pekerjaan,5)) from tm_pekerjaan ")) {
            DecimalFormat df = new DecimalFormat("00000");
            kodePekerjaan = "P-"+df.format(1);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    kodePekerjaan = "P-"+df.format(rs.getInt(1)+1);
            }
        }
        return kodePekerjaan;
    }
    public static void insert(Connection con, Pekerjaan b)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_pekerjaan values(?,?,?,?,?,?,?,?)")) {
            ps.setString(1, b.getKodePekerjaan());
            ps.setString(2, b.getKategoriPekerjaan());
            ps.setString(3, b.getNamaPekerjaan());
            ps.setString(4, b.getSpesifikasi());
            ps.setString(5, b.getKeterangan());
            ps.setString(6, b.getSatuan());
            ps.setDouble(7, b.getLaba());
            ps.setString(8, b.getStatus());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, Pekerjaan b)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_pekerjaan set kategori_pekerjaan=?, nama_pekerjaan=?,"
                + " spesifikasi=?, keterangan=?, satuan=?, laba=?, status=? where kode_pekerjaan=?")) {
            ps.setString(1, b.getKategoriPekerjaan());
            ps.setString(2, b.getNamaPekerjaan());
            ps.setString(3, b.getSpesifikasi());
            ps.setString(4, b.getKeterangan());
            ps.setString(5, b.getSatuan());
            ps.setDouble(6, b.getLaba());
            ps.setString(7, b.getStatus());
            ps.setString(8, b.getKodePekerjaan());
            ps.executeUpdate();
        }
    }
}
