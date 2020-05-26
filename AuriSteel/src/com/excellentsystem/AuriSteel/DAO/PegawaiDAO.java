/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.Pegawai;
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
public class PegawaiDAO {
    public static List<Pegawai> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_pegawai";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Pegawai> allPegawai = new ArrayList<>();
        while(rs.next()){
            Pegawai pegawai = new Pegawai();
            pegawai.setKodePegawai(rs.getString(1));
            pegawai.setNama(rs.getString(2));
            pegawai.setJabatan(rs.getString(3));
            pegawai.setAlamat(rs.getString(4));
            pegawai.setKota(rs.getString(5));
            pegawai.setIdentitas(rs.getString(6));
            pegawai.setNoIdentitas(rs.getString(7));
            pegawai.setEmail(rs.getString(8));
            pegawai.setNoTelp(rs.getString(9));
            pegawai.setNoHandphone(rs.getString(10));
            pegawai.setStatus(rs.getString(11));
            allPegawai.add(pegawai);
        }
        return allPegawai;
    }
    public static Pegawai get(Connection con, String kodePegawai)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_pegawai where kode_pegawai=?");
        ps.setString(1, kodePegawai);
        ResultSet rs = ps.executeQuery();
        Pegawai pegawai = null;
        while(rs.next()){
            pegawai = new Pegawai();
            pegawai.setKodePegawai(rs.getString(1));
            pegawai.setNama(rs.getString(2));
            pegawai.setJabatan(rs.getString(3));
            pegawai.setAlamat(rs.getString(4));
            pegawai.setKota(rs.getString(5));
            pegawai.setIdentitas(rs.getString(6));
            pegawai.setNoIdentitas(rs.getString(7));
            pegawai.setEmail(rs.getString(8));
            pegawai.setNoTelp(rs.getString(9));
            pegawai.setNoHandphone(rs.getString(10));
            pegawai.setStatus(rs.getString(11));
        }
        return pegawai;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_pegawai,4)) from tm_pegawai");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "SL-"+new DecimalFormat("0000").format(rs.getInt(1)+1);
        else
            return "SL-"+new DecimalFormat("0000").format(1);
    }
    public static void insert(Connection con, Pegawai pegawai)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_pegawai values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pegawai.getKodePegawai());
        ps.setString(2, pegawai.getNama());
        ps.setString(3, pegawai.getJabatan());
        ps.setString(4, pegawai.getAlamat());
        ps.setString(5, pegawai.getKota());
        ps.setString(6, pegawai.getIdentitas());
        ps.setString(7, pegawai.getNoIdentitas());
        ps.setString(8, pegawai.getEmail());
        ps.setString(9, pegawai.getNoTelp());
        ps.setString(10, pegawai.getNoHandphone());
        ps.setString(11, pegawai.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, Pegawai pegawai)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_pegawai set nama=?, jabatan=?, alamat=?, kota=?, identitas=?,"
                + " no_identitas=?, email=?, no_telp=?, no_handphone=?,status =? where kode_pegawai=?");
        ps.setString(1, pegawai.getNama());
        ps.setString(2, pegawai.getJabatan());
        ps.setString(3, pegawai.getAlamat());
        ps.setString(4, pegawai.getKota());
        ps.setString(5, pegawai.getIdentitas());
        ps.setString(6, pegawai.getNoIdentitas());
        ps.setString(7, pegawai.getEmail());
        ps.setString(8, pegawai.getNoTelp());
        ps.setString(9, pegawai.getNoHandphone());
        ps.setString(10, pegawai.getStatus());
        ps.setString(11, pegawai.getKodePegawai());
        ps.executeUpdate();
    }
}
