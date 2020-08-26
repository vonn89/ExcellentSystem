/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokBarangDAO {
    public static List<StokBarang> getAllByDateAndKategoriAndSubKategori(
            Connection con, String tglAwal, String tglAkhir, String kodeKategori, String kodeSubKategori)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal between ? and ? ";
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeSubKategori.equals("%"))
            sql = sql + " and kode_sub_kategori = '"+kodeSubKategori+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        List<StokBarang> listStokBarang = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeKategori(rs.getString(2));
            s.setKodeSubKategori(rs.getString(3));
            
            s.setBeratAwal(rs.getDouble(4));
            s.setBeratPersenAwal(rs.getDouble(5));
            s.setNilaiAwal(rs.getDouble(6));
            
            s.setBeratMasuk(rs.getDouble(7));
            s.setBeratPersenMasuk(rs.getDouble(8));
            s.setNilaiMasuk(rs.getDouble(9));
            
            s.setBeratKeluar(rs.getDouble(10));
            s.setBeratPersenKeluar(rs.getDouble(11));
            s.setNilaiKeluar(rs.getDouble(12));
            
            s.setBeratAkhir(rs.getDouble(13));
            s.setBeratPersenAkhir(rs.getDouble(14));
            s.setNilaiAkhir(rs.getDouble(15));
            listStokBarang.add(s);
        }
        return listStokBarang;
    }
    public static List<StokBarang> getAllByTanggalAndKategori(Connection con, String tanggal, String kategori)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal = ? ";
        if(!kategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kategori+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        List<StokBarang> listStokBarang = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeKategori(rs.getString(2));
            s.setKodeSubKategori(rs.getString(3));
            
            s.setBeratAwal(rs.getDouble(4));
            s.setBeratPersenAwal(rs.getDouble(5));
            s.setNilaiAwal(rs.getDouble(6));
            
            s.setBeratMasuk(rs.getDouble(7));
            s.setBeratPersenMasuk(rs.getDouble(8));
            s.setNilaiMasuk(rs.getDouble(9));
            
            s.setBeratKeluar(rs.getDouble(10));
            s.setBeratPersenKeluar(rs.getDouble(11));
            s.setNilaiKeluar(rs.getDouble(12));
            
            s.setBeratAkhir(rs.getDouble(13));
            s.setBeratPersenAkhir(rs.getDouble(14));
            s.setNilaiAkhir(rs.getDouble(15));
            listStokBarang.add(s);
        }
        return listStokBarang;
    }
    public static StokBarang get(Connection con, String tanggal, String kodeSubKategori)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal = ? and kode_sub_kategori = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ps.setString(2, kodeSubKategori);
        StokBarang s = null;
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeKategori(rs.getString(2));
            s.setKodeSubKategori(rs.getString(3));
            
            s.setBeratAwal(rs.getDouble(4));
            s.setBeratPersenAwal(rs.getDouble(5));
            s.setNilaiAwal(rs.getDouble(6));
            
            s.setBeratMasuk(rs.getDouble(7));
            s.setBeratPersenMasuk(rs.getDouble(8));
            s.setNilaiMasuk(rs.getDouble(9));
            
            s.setBeratKeluar(rs.getDouble(10));
            s.setBeratPersenKeluar(rs.getDouble(11));
            s.setNilaiKeluar(rs.getDouble(12));
            
            s.setBeratAkhir(rs.getDouble(13));
            s.setBeratPersenAkhir(rs.getDouble(14));
            s.setNilaiAkhir(rs.getDouble(15));
        }
        return s;
    }
    public static void insertStokAwal(Connection con, String date, String nextDate)throws Exception{
        con.prepareStatement("insert into tt_stok_barang "
            + " select '"+nextDate+"',kode_kategori,kode_sub_kategori,"
            + " berat_akhir,berat_persen_akhir,nilai_akhir,"
            + " 0,0,0,"
            + " 0,0,0,"
            + " berat_akhir,berat_persen_akhir,nilai_akhir "
            + " from tt_stok_barang where tanggal = '"+date+"' and berat_akhir!=0  and berat_persen_akhir!=0 and nilai_akhir!=0").executeUpdate();
    }
    public static void insert(Connection con, StokBarang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_barang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, stok.getTanggal());
        ps.setString(2, stok.getKodeKategori());
        ps.setString(3, stok.getKodeSubKategori());
        
        ps.setDouble(4, stok.getBeratAwal());
        ps.setDouble(5, stok.getBeratPersenAwal());
        ps.setDouble(6, stok.getNilaiAwal());
        
        ps.setDouble(7, stok.getBeratMasuk());
        ps.setDouble(8, stok.getBeratPersenMasuk());
        ps.setDouble(9, stok.getNilaiMasuk());
        
        ps.setDouble(10, stok.getBeratKeluar());
        ps.setDouble(11, stok.getBeratPersenKeluar());
        ps.setDouble(12, stok.getNilaiKeluar());
        
        ps.setDouble(13, stok.getBeratAkhir());
        ps.setDouble(14, stok.getBeratPersenAkhir());
        ps.setDouble(15, stok.getNilaiAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con, StokBarang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang set "
                + " berat_awal=?, berat_persen_awal=?, nilai_awal=?, "
                + " berat_masuk=?, berat_persen_masuk=?, nilai_masuk=?, "
                + " berat_keluar=?, berat_persen_keluar=?, nilai_keluar=?, "
                + " berat_akhir=?, berat_persen_akhir=?, nilai_akhir=? "
                + " where tanggal=? and kode_kategori=? and kode_sub_kategori=?");
        ps.setDouble(1, stok.getBeratAwal());
        ps.setDouble(2, stok.getBeratPersenAwal());
        ps.setDouble(3, stok.getNilaiAwal());
        
        ps.setDouble(4, stok.getBeratMasuk());
        ps.setDouble(5, stok.getBeratPersenMasuk());
        ps.setDouble(6, stok.getNilaiMasuk());
        
        ps.setDouble(7, stok.getBeratKeluar());
        ps.setDouble(8, stok.getBeratPersenKeluar());
        ps.setDouble(9, stok.getNilaiKeluar());
        
        ps.setDouble(10, stok.getBeratAkhir());
        ps.setDouble(11, stok.getBeratPersenAkhir());
        ps.setDouble(12, stok.getNilaiAkhir());
        
        ps.setString(13, stok.getTanggal());
        ps.setString(14, stok.getKodeKategori());
        ps.setString(15, stok.getKodeSubKategori());
        ps.executeUpdate();
    }
}
