/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KaryawanDAO {
    public static List<Karyawan> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_karyawan where kode_karyawan != '' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Karyawan> allKaryawan = new ArrayList<>();
        while(rs.next()){
            Karyawan k = new Karyawan();
            k.setKodeKaryawan(rs.getString(1));
            k.setNama(rs.getString(2));
            k.setJabatan(rs.getString(3));
            k.setAlamat(rs.getString(4));
            k.setNoTelp(rs.getString(5));
            k.setNoHandphone(rs.getString(6));
            k.setStatusNikah(rs.getString(7));
            k.setAgama(rs.getString(8));
            k.setIdentitas(rs.getString(9));
            k.setNoIdentitas(rs.getString(10));
            k.setStatus(rs.getString(11));
            allKaryawan.add(k);
        }
        return allKaryawan;
    }
    public static Karyawan get(Connection con, String kodeKaryawan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_karyawan where kode_karyawan = ?");
        ps.setString(1, kodeKaryawan);
        ResultSet rs = ps.executeQuery();
        Karyawan k = null;
        while(rs.next()){
            k = new Karyawan();
            k.setKodeKaryawan(rs.getString(1));
            k.setNama(rs.getString(2));
            k.setJabatan(rs.getString(3));
            k.setAlamat(rs.getString(4));
            k.setNoTelp(rs.getString(5));
            k.setNoHandphone(rs.getString(6));
            k.setStatusNikah(rs.getString(7));
            k.setAgama(rs.getString(8));
            k.setIdentitas(rs.getString(9));
            k.setNoIdentitas(rs.getString(10));
            k.setStatus(rs.getString(11));
        }
        return k;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_karyawan,3)) from tm_karyawan");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "K-"+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "K-"+new DecimalFormat("000").format(1);
    }
    public static void update(Connection con,Karyawan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_karyawan set "
                + " nama=?, jabatan=?, alamat=?, no_telp=?, "
                + " no_handphone=?, status_nikah=?, agama=?, "
                + " identitas=?, no_identitas=?, status=? "
                + " where kode_karyawan=?");
        ps.setString(1, k.getNama());
        ps.setString(2, k.getJabatan());
        ps.setString(3, k.getAlamat());
        ps.setString(4, k.getNoTelp());
        ps.setString(5, k.getNoHandphone());
        ps.setString(6, k.getStatusNikah());
        ps.setString(7, k.getAgama());
        ps.setString(8, k.getIdentitas());
        ps.setString(9, k.getNoIdentitas());
        ps.setString(10, k.getStatus());
        ps.setString(11, k.getKodeKaryawan());
        ps.executeUpdate();
    }
    public static void insert(Connection con,Karyawan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_karyawan values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, k.getKodeKaryawan());
        ps.setString(2, k.getNama());
        ps.setString(3, k.getJabatan());
        ps.setString(4, k.getAlamat());
        ps.setString(5, k.getNoTelp());
        ps.setString(6, k.getNoHandphone());
        ps.setString(7, k.getStatusNikah());
        ps.setString(8, k.getAgama());
        ps.setString(9, k.getIdentitas());
        ps.setString(10, k.getNoIdentitas());
        ps.setString(11, k.getStatus());
        ps.executeUpdate();
    }
}
