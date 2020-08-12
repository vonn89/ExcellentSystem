/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class StokBarangDAO {
    public static void insert(Connection con, StokBarang s)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert tt_stok_barang values(?,?,?,?,?,?)")) {
            ps.setString(1, s.getTanggal());
            ps.setString(2, s.getKodeBarang());
            ps.setDouble(3, s.getStokAwal());
            ps.setDouble(4, s.getStokMasuk());
            ps.setDouble(5, s.getStokKeluar());
            ps.setDouble(6, s.getStokAkhir());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, StokBarang s)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_stok_barang "
                + " set stok_awal=?, stok_masuk=?, stok_keluar=?, stok_akhir=? "
                + " where tanggal=? and kode_barang=? ")) {
            ps.setDouble(1, s.getStokAwal());
            ps.setDouble(2, s.getStokMasuk());
            ps.setDouble(3, s.getStokKeluar());
            ps.setDouble(4, s.getStokAkhir());
            ps.setString(5, s.getTanggal());
            ps.setString(6, s.getKodeBarang());
            ps.executeUpdate();
        }
    }
    public static StokBarang get(Connection con, String tanggal,String kodeBarang)throws Exception{
        StokBarang s;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal <= ? and kode_barang like ? order by tanggal desc")) {
            ps.setString(1, tanggal);
            ps.setString(2, kodeBarang);
            try (ResultSet rs = ps.executeQuery()) {
                s = null;
                if(rs.next()){
                    s = new StokBarang();
                    s.setTanggal(rs.getString(1));
                    s.setKodeBarang(rs.getString(2));
                    s.setStokAwal(rs.getDouble(3));
                    s.setStokMasuk(rs.getDouble(4));
                    s.setStokKeluar(rs.getDouble(5));
                    s.setStokAkhir(rs.getDouble(6));
                }
            }
        }
        return s;
    }
    public static List<StokBarang> getAllByTanggalAndBarang(
            Connection con, String tglMulai, String tglAkhir, String kodeBarang)throws Exception{
        List<StokBarang> stok;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal between ? and ? and kode_barang like ? "
                + "order by tanggal desc")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, kodeBarang);
            try (ResultSet rs = ps.executeQuery()) {
                stok = new ArrayList<>();
                while(rs.next()){
                    StokBarang s = new StokBarang();
                    s.setTanggal(rs.getString(1));
                    s.setKodeBarang(rs.getString(2));
                    s.setStokAwal(rs.getDouble(3));
                    s.setStokMasuk(rs.getDouble(4));
                    s.setStokKeluar(rs.getDouble(5));
                    s.setStokAkhir(rs.getDouble(6));
                    stok.add(s);
                }
            }
        }
        return stok;
    }
    public static List<StokBarang> getAllByTanggal(Connection con, String tanggal)throws Exception{
        List<StokBarang> stok;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang where tanggal<=? order by tanggal desc")) {
            ps.setString(1, tanggal);
            try (ResultSet rs = ps.executeQuery()) {
                stok = new ArrayList<>();
                while(rs.next()){
                    Boolean status = true;
                    for(StokBarang temp :stok){
                        if(temp.getKodeBarang().equals(rs.getString(2)))
                            status= false;
                    }
                    if(status){
                        StokBarang stokBarang = new StokBarang();
                        stokBarang.setTanggal(tanggal);
                        stokBarang.setKodeBarang(rs.getString(2));
                        stokBarang.setStokAwal(rs.getDouble(6));
                        stokBarang.setStokMasuk(0);
                        stokBarang.setStokKeluar(0);
                        stokBarang.setStokAkhir(rs.getDouble(6));
                        if(tanggal.equals(rs.getString(1))){
                            stokBarang.setStokAwal(rs.getDouble(3));
                            stokBarang.setStokMasuk(rs.getDouble(4));
                            stokBarang.setStokKeluar(rs.getDouble(5));
                        }
                        stok.add(stokBarang);
                    }
                }
            }
        }
        return stok;
    }
}
