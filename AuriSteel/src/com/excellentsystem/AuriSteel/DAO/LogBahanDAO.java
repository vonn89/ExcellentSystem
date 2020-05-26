/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.LogBahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class LogBahanDAO {
    public static LogBahan getLastByBahanAndGudang(Connection con, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_log_bahan "
                + "where kode_bahan = ? and kode_gudang = ? order by tanggal desc limit 1");
        ps.setString(1, kodeBahan);
        ps.setString(2, kodeGudang);
        ResultSet rs = ps.executeQuery();
        LogBahan l = null;
        while(rs.next()){
            l = new LogBahan();
            l.setTanggal(rs.getString(1));
            l.setKodeBahan(rs.getString(2));
            l.setKodeGudang(rs.getString(3));
            l.setKategori(rs.getString(4));
            l.setKeterangan(rs.getString(5));
            l.setStokAwal(rs.getDouble(6));
            l.setNilaiAwal(rs.getDouble(7));
            l.setStokMasuk(rs.getDouble(8));
            l.setNilaiMasuk(rs.getDouble(9));
            l.setStokKeluar(rs.getDouble(10));
            l.setNilaiKeluar(rs.getDouble(11));
            l.setStokAkhir(rs.getDouble(12));
            l.setNilaiAkhir(rs.getDouble(13));
        }
        return l;
    }
    public static LogBahan getLastBeforeDateAndBahanAndGudang(Connection con, String tanggal, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_log_bahan where left(tanggal,10) < ? "
                + " and kode_bahan = ? and kode_gudang = ? order by tanggal desc limit 1");
        ps.setString(1, tanggal);
        ps.setString(2, kodeBahan);
        ps.setString(3, kodeGudang);
        ResultSet rs = ps.executeQuery();
        LogBahan l = null;
        while(rs.next()){
            l = new LogBahan();
            l.setTanggal(rs.getString(1));
            l.setKodeBahan(rs.getString(2));
            l.setKodeGudang(rs.getString(3));
            l.setKategori(rs.getString(4));
            l.setKeterangan(rs.getString(5));
            l.setStokAwal(rs.getDouble(6));
            l.setNilaiAwal(rs.getDouble(7));
            l.setStokMasuk(rs.getDouble(8));
            l.setNilaiMasuk(rs.getDouble(9));
            l.setStokKeluar(rs.getDouble(10));
            l.setNilaiKeluar(rs.getDouble(11));
            l.setStokAkhir(rs.getDouble(12));
            l.setNilaiAkhir(rs.getDouble(13));
        }
        return l;
    }
    public static List<LogBahan> getAllByTanggalAndBahanAndGudang(
            Connection con, String tglMulai, String tglAkhir, String kodeBahan, String kodeGudang)throws Exception{
        String sql = "select * from tt_log_bahan where left(tanggal,10) between ? and ? ";
        if(!kodeBahan.equals("%"))
            sql = sql + " and kode_bahan = '"+kodeBahan+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<LogBahan> allLogBahan = new ArrayList<>();
        while(rs.next()){
            LogBahan l = new LogBahan();
            l.setTanggal(rs.getString(1));
            l.setKodeBahan(rs.getString(2));
            l.setKodeGudang(rs.getString(3));
            l.setKategori(rs.getString(4));
            l.setKeterangan(rs.getString(5));
            l.setStokAwal(rs.getDouble(6));
            l.setNilaiAwal(rs.getDouble(7));
            l.setStokMasuk(rs.getDouble(8));
            l.setNilaiMasuk(rs.getDouble(9));
            l.setStokKeluar(rs.getDouble(10));
            l.setNilaiKeluar(rs.getDouble(11));
            l.setStokAkhir(rs.getDouble(12));
            l.setNilaiAkhir(rs.getDouble(13));
            allLogBahan.add(l);
        }
        return allLogBahan;
    }
    public static void insert(Connection con, LogBahan l)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert tt_log_bahan values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, l.getTanggal());
        ps.setString(2, l.getKodeBahan());
        ps.setString(3, l.getKodeGudang());
        ps.setString(4, l.getKategori());
        ps.setString(5, l.getKeterangan());
        ps.setDouble(6, l.getStokAwal());
        ps.setDouble(7, l.getNilaiAwal());
        ps.setDouble(8, l.getStokMasuk());
        ps.setDouble(9, l.getNilaiMasuk());
        ps.setDouble(10, l.getStokKeluar());
        ps.setDouble(11, l.getNilaiKeluar());
        ps.setDouble(12, l.getStokAkhir());
        ps.setDouble(13, l.getNilaiAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con, LogBahan l)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_log_bahan set "
                + " stok_awal=?, nilai_awal=?, stok_masuk=?, nilai_masuk=?, "
                + " stok_keluar=?, nilai_keluar=?, stok_akhir=?, nilai_akhir=? "
                + " where tanggal = ? and kode_bahan = ? and kode_gudang = ? and kategori = ? and keterangan = ?");
        ps.setDouble(1, l.getStokAwal());
        ps.setDouble(2, l.getNilaiAwal());
        ps.setDouble(3, l.getStokMasuk());
        ps.setDouble(4, l.getNilaiMasuk());
        ps.setDouble(5, l.getStokKeluar());
        ps.setDouble(6, l.getNilaiKeluar());
        ps.setDouble(7, l.getStokAkhir());
        ps.setDouble(8, l.getNilaiAkhir());
        ps.setString(9, l.getTanggal());
        ps.setString(10, l.getKodeBahan());
        ps.setString(11, l.getKodeGudang());
        ps.setString(12, l.getKategori());
        ps.setString(13, l.getKeterangan());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeBahan, String kodeGudang, String kategori, String keterangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_log_bahan "
                + " where kode_bahan = ? and kode_gudang = ? and kategori = ? and keterangan = ?");
        ps.setString(1, kodeBahan);
        ps.setString(2, kodeGudang);
        ps.setString(3, kategori);
        ps.setString(4, keterangan);
        ps.executeUpdate();
    }
}
