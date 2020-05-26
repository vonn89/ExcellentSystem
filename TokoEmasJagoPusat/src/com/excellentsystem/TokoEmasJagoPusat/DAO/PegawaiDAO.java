/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Pegawai;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PegawaiDAO {
    
    public static List<Pegawai> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_pegawai ";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"' ";
        List<Pegawai> allPegawai = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){  
            Pegawai p = new Pegawai();
            p.setKodePegawai(rs.getString(1));
            p.setNama(rs.getString(2));
            p.setJenisKelamin(rs.getString(3));
            p.setAlamat(rs.getString(4));
            p.setNoTelp(rs.getString(5));
            p.setJabatan(rs.getString(6));
            p.setTanggalMasuk(rs.getString(7));
            p.setTanggalKeluar(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPegawai.add(p);
        }
        return allPegawai;
    }
    public static void insert(Connection con, Pegawai p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_pegawai values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodePegawai());
        ps.setString(2, p.getNama());
        ps.setString(3, p.getJenisKelamin());
        ps.setString(4, p.getAlamat());
        ps.setString(5, p.getNoTelp());
        ps.setString(6, p.getJabatan());
        ps.setString(7, p.getTanggalMasuk());
        ps.setString(8, p.getTanggalKeluar());
        ps.setString(9, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, Pegawai p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_pegawai set "
                + "nama=?, jenis_kelamin=?, alamat=?, no_telp=?, jabatan=?,"
                + "tanggal_masuk=?, tanggal_keluar=?, status=? where kode_pegawai=?");
        ps.setString(1, p.getNama());
        ps.setString(2, p.getJenisKelamin());
        ps.setString(3, p.getAlamat());
        ps.setString(4, p.getNoTelp());
        ps.setString(5, p.getJabatan());
        ps.setString(6, p.getTanggalMasuk());
        ps.setString(7, p.getTanggalKeluar());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getKodePegawai());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Pegawai pegawai)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_pegawai where kode_pegawai=?");
        ps.setString(1, pegawai.getKodePegawai());
        ps.executeUpdate();
    }
}
