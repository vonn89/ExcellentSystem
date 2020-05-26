/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.PelangganFiktif;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PelangganFiktifDAO {
    
    public static List<PelangganFiktif> getAllByCabang(Connection con, String kodeCabang)throws Exception{
        String sql = "select * from tm_pelanggan_fiktif ";
        if(!kodeCabang.equals("%"))
            sql = sql + " where kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PelangganFiktif> allPelanggan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){  
            PelangganFiktif pelanggan = new PelangganFiktif();
            pelanggan.setKodeCabang(rs.getString(1));
            pelanggan.setNama(rs.getString(2));
            pelanggan.setAlamat(rs.getString(3));
            pelanggan.setNoTelp(rs.getString(4));
            allPelanggan.add(pelanggan);
        }
        return allPelanggan;
    }
    public static void insert(Connection con, PelangganFiktif pelanggan)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_pelanggan_fiktif values(?,?,?,?)");
        ps.setString(1, pelanggan.getKodeCabang());
        ps.setString(2, pelanggan.getNama());
        ps.setString(3, pelanggan.getAlamat());
        ps.setString(4, pelanggan.getNoTelp());
        ps.executeUpdate();
    }
    public static void delete(Connection con, PelangganFiktif pelanggan)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_pelanggan_fiktif "
                + "where kode_cabang=? and  nama=? and alamat=? and no_telp=?");
        ps.setString(1, pelanggan.getKodeCabang());
        ps.setString(2, pelanggan.getNama());
        ps.setString(3, pelanggan.getAlamat());
        ps.setString(4, pelanggan.getNoTelp());
        ps.executeUpdate();
    }
    public static void deleteAllFromCabang(Connection con, String kodeCabang)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_pelanggan_fiktif "
                + " where kode_cabang=?");
        ps.setString(1, kodeCabang);
        ps.executeUpdate();
    }
}
