/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.Barang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class BarangDAO {
    public static List<Barang> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_barang ";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Barang> allBarang = new ArrayList<>();
        while(rs.next()){
            Barang b = new Barang();
            b.setKodeBarang(rs.getString(1));
            b.setNamaBarang(rs.getString(2));
            b.setSpesifikasi(rs.getString(3));
            b.setSatuan(rs.getString(4));
            b.setBerat(rs.getDouble(5));
            b.setHargaJual(rs.getDouble(6));
            b.setStatus(rs.getString(7));
            allBarang.add(b);
        }
        return allBarang;
    }
    public static Barang get(Connection con, String kodeBarang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_barang where kode_barang=?");
        ps.setString(1, kodeBarang);
        ResultSet rs = ps.executeQuery();
        Barang b = null;
        while(rs.next()){
            b = new Barang();
            b.setKodeBarang(rs.getString(1));
            b.setNamaBarang(rs.getString(2));
            b.setSpesifikasi(rs.getString(3));
            b.setSatuan(rs.getString(4));
            b.setBerat(rs.getDouble(5));
            b.setHargaJual(rs.getDouble(6));
            b.setStatus(rs.getString(7));
        }
        return b;
    }
    public static void insert(Connection con, Barang b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_barang values(?,?,?,?,?,?,?)");
        ps.setString(1, b.getKodeBarang());
        ps.setString(2, b.getNamaBarang());
        ps.setString(3, b.getSpesifikasi());
        ps.setString(4, b.getSatuan());
        ps.setDouble(5, b.getBerat());
        ps.setDouble(6, b.getHargaJual());
        ps.setString(7, b.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, Barang b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_barang set "
                + " nama_barang=?, spesifikasi=?, satuan=?, berat=?, harga_jual=?, status=? "
                + " where kode_barang=? ");
        ps.setString(1, b.getNamaBarang());
        ps.setString(2, b.getSpesifikasi());
        ps.setString(3, b.getSatuan());
        ps.setDouble(4, b.getBerat());
        ps.setDouble(5, b.getHargaJual());
        ps.setString(6, b.getStatus());
        ps.setString(7, b.getKodeBarang());
        ps.executeUpdate();
    }
}
