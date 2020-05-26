/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class BarangCabangDAO {
    public static List<BarangCabang> getAllByTglBarcode(Connection con, String tglAwal, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_barang_cabang where left(input_date,10) between ? and ? ");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<BarangCabang> allBarang = new ArrayList<>();
        while(rs.next()){
            BarangCabang b = new BarangCabang();
            b.setKodeBarang(rs.getString(1));
            b.setKodeBarcode(rs.getString(2));
            b.setNamaBarang(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeIntern(rs.getString(6));
            b.setKadar(rs.getString(7));
            b.setBerat(rs.getDouble(8));
            b.setBeratAsli(rs.getDouble(9));
            b.setBeratPersen(rs.getDouble(10));
            b.setNilaiPokok(rs.getDouble(11));
            b.setInputDate(rs.getString(12));
            b.setInputBy(rs.getString(13));
            b.setAsalBarang(rs.getString(14));
            b.setStatus(rs.getString(15));
            allBarang.add(b);
        }
        return allBarang;
    }
    public static List<BarangCabang> getAllByKategoriAndJenisAndStatus(
            Connection con, String kategori, String jenis, String status)throws Exception{
        String sql = "select * from tm_barang_cabang where kode_barcode !='' ";
        if(!kategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kategori+"' ";
        if(!jenis.equals("%"))
            sql = sql + " and kode_jenis = '"+jenis+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<BarangCabang> allBarang = new ArrayList<>();
        while(rs.next()){
            BarangCabang b = new BarangCabang();
            b.setKodeBarang(rs.getString(1));
            b.setKodeBarcode(rs.getString(2));
            b.setNamaBarang(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeIntern(rs.getString(6));
            b.setKadar(rs.getString(7));
            b.setBerat(rs.getDouble(8));
            b.setBeratAsli(rs.getDouble(9));
            b.setBeratPersen(rs.getDouble(10));
            b.setNilaiPokok(rs.getDouble(11));
            b.setInputDate(rs.getString(12));
            b.setInputBy(rs.getString(13));
            b.setAsalBarang(rs.getString(14));
            b.setStatus(rs.getString(15));
            allBarang.add(b);
        }
        return allBarang;
    }
    public static BarangCabang get(Connection con,String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_barang_cabang where kode_barcode = ?");
        ps.setString(1, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        BarangCabang b = null;
        if(rs.next()){
            b = new BarangCabang();
            b.setKodeBarang(rs.getString(1));
            b.setKodeBarcode(rs.getString(2));
            b.setNamaBarang(rs.getString(3));
            b.setKodeKategori(rs.getString(4));
            b.setKodeJenis(rs.getString(5));
            b.setKodeIntern(rs.getString(6));
            b.setKadar(rs.getString(7));
            b.setBerat(rs.getDouble(8));
            b.setBeratAsli(rs.getDouble(9));
            b.setBeratPersen(rs.getDouble(10));
            b.setNilaiPokok(rs.getDouble(11));
            b.setInputDate(rs.getString(12));
            b.setInputBy(rs.getString(13));
            b.setAsalBarang(rs.getString(14));
            b.setStatus(rs.getString(15));
        }
        return b;
    }
    public static void insert(Connection con,BarangCabang b) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_barang_cabang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, b.getKodeBarang());
        ps.setString(2, b.getKodeBarcode());
        ps.setString(3, b.getNamaBarang());
        ps.setString(4, b.getKodeKategori());
        ps.setString(5, b.getKodeJenis());
        ps.setString(6, b.getKodeIntern());
        ps.setString(7, b.getKadar());
        ps.setDouble(8, b.getBerat());
        ps.setDouble(9, b.getBeratAsli());
        ps.setDouble(10, b.getBeratPersen());
        ps.setDouble(11, b.getNilaiPokok());
        ps.setString(12, b.getInputDate());
        ps.setString(13, b.getInputBy());
        ps.setString(14, b.getAsalBarang());
        ps.setString(15, b.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, BarangCabang b) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_barang_cabang set "
                + " nama_barang=?, kode_kategori=?, kode_jenis=?,"
                + " kode_intern=?, kadar=?, berat=?, berat_asli=?, berat_persen=?, nilai_pokok=?, "
                + " input_date=?, input_by=?, asal_barang=?, status=? "
                + " where kode_barcode=? and kode_barang=? ");
        ps.setString(1, b.getNamaBarang());
        ps.setString(2, b.getKodeKategori());
        ps.setString(3, b.getKodeJenis());
        ps.setString(4, b.getKodeIntern());
        ps.setString(5, b.getKadar());
        ps.setDouble(6, b.getBerat());
        ps.setDouble(7, b.getBeratAsli());
        ps.setDouble(8, b.getBeratPersen());
        ps.setDouble(9, b.getNilaiPokok());
        ps.setString(10, b.getInputDate());
        ps.setString(11, b.getInputBy());
        ps.setString(12, b.getAsalBarang());
        ps.setString(13, b.getStatus());
        ps.setString(14, b.getKodeBarcode());
        ps.setString(15, b.getKodeBarang());
        ps.executeUpdate();
    }
}
