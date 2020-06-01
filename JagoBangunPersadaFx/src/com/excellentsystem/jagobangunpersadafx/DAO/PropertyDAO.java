/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PropertyDAO {
    public static List<Property> getAll(Connection con)throws Exception{
        String sql = "select * from tm_property";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Property> allProperty = new ArrayList<>();
        while(rs.next()){
            Property p = new Property();
            p.setKodeProperty(rs.getString(1));
            p.setKodeKategori(rs.getString(2));
            p.setNamaProperty(rs.getString(3));
            p.setAlamat(rs.getString(4));
            p.setTipe(rs.getString(5));
            p.setSpesifikasi(rs.getString(6));
            p.setLuasTanah(rs.getDouble(7));
            p.setLuasBangunan(rs.getDouble(8));
            p.setNilaiProperty(rs.getDouble(9));
            p.setHargaJual(rs.getDouble(10));
            p.setDiskon(rs.getDouble(11));
            p.setKeterangan(rs.getString(12));
            p.setStatus(rs.getString(13));
            allProperty.add(p);
        }
        return allProperty;
    }
    public static List<Property> getAllByStatus(Connection con, List<String> status)throws Exception{
        String sql = "select * from tm_property where kode_property!='' ";
        if(!status.isEmpty()){
            sql = sql + " and status in (";
            for(String x : status){
                sql = sql + "'"+x+"',";
            }
            sql = sql + "'') ";
        }
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Property> allProperty = new ArrayList<>();
        while(rs.next()){
            Property p = new Property();
            p.setKodeProperty(rs.getString(1));
            p.setKodeKategori(rs.getString(2));
            p.setNamaProperty(rs.getString(3));
            p.setAlamat(rs.getString(4));
            p.setTipe(rs.getString(5));
            p.setSpesifikasi(rs.getString(6));
            p.setLuasTanah(rs.getDouble(7));
            p.setLuasBangunan(rs.getDouble(8));
            p.setNilaiProperty(rs.getDouble(9));
            p.setHargaJual(rs.getDouble(10));
            p.setDiskon(rs.getDouble(11));
            p.setKeterangan(rs.getString(12));
            p.setStatus(rs.getString(13));
            allProperty.add(p);
        }
        return allProperty;
    }
    public static Property get(Connection con,String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_property where kode_property=?");
        ps.setString(1, kodeProperty);
        ResultSet rs = ps.executeQuery();
        Property p = null;
        while(rs.next()){
            p = new Property();
            p.setKodeProperty(rs.getString(1));
            p.setKodeKategori(rs.getString(2));
            p.setNamaProperty(rs.getString(3));
            p.setAlamat(rs.getString(4));
            p.setTipe(rs.getString(5));
            p.setSpesifikasi(rs.getString(6));
            p.setLuasTanah(rs.getDouble(7));
            p.setLuasBangunan(rs.getDouble(8));
            p.setNilaiProperty(rs.getDouble(9));
            p.setHargaJual(rs.getDouble(10));
            p.setDiskon(rs.getDouble(11));
            p.setKeterangan(rs.getString(12));
            p.setStatus(rs.getString(13));
        }
        return p;
    }
    public static void update(Connection con, Property p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_property set kode_kategori=?, nama_property=?, alamat=?,"
            + " tipe=?, spesifikasi=?, luas_tanah=?, luas_bangunan=?, nilai_property=?, harga_jual=?, diskon=?, keterangan=?, status=? "
            + " where kode_property=?");
        ps.setString(1, p.getKodeKategori());
        ps.setString(2, p.getNamaProperty());
        ps.setString(3, p.getAlamat());
        ps.setString(4, p.getTipe());
        ps.setString(5, p.getSpesifikasi());
        ps.setDouble(6, p.getLuasTanah());
        ps.setDouble(7, p.getLuasBangunan());
        ps.setDouble(8, p.getNilaiProperty());
        ps.setDouble(9, p.getHargaJual());
        ps.setDouble(10, p.getDiskon());
        ps.setString(11, p.getKeterangan());
        ps.setString(12, p.getStatus());
        ps.setString(13, p.getKodeProperty());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Property p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_property values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodeProperty());
        ps.setString(2, p.getKodeKategori());
        ps.setString(3, p.getNamaProperty());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getTipe());
        ps.setString(6, p.getSpesifikasi());
        ps.setDouble(7, p.getLuasTanah());
        ps.setDouble(8, p.getLuasBangunan());
        ps.setDouble(9, p.getNilaiProperty());
        ps.setDouble(10, p.getHargaJual());
        ps.setDouble(11, p.getHargaJual());
        ps.setString(12, p.getKeterangan());
        ps.setString(13, p.getStatus());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(kode_property,4)) from tm_property "
                + " where mid(kode_property,3,2)='"+new SimpleDateFormat("yy").format(new Date())+"' ");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "P-"+new SimpleDateFormat("yy").format(new Date())+new DecimalFormat("0000").format(rs.getInt(1) + 1);
        else 
            return "P-"+new SimpleDateFormat("yy").format(new Date())+"0001";
    }
    public static void delete(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_property set status='Deleted' where kode_property=?");
        ps.setString(1, kodeProperty);
        ps.executeUpdate();
    }
}
