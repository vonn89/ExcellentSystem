/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.StokBarang;
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
    public static StokBarang get(Connection con, String tanggal, String kodeBarang, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang "
                + "where tanggal <= ? and kode_barang = ? and kode_gudang = ? order by tanggal desc limit 1");
        ps.setString(1, tanggal);
        ps.setString(2, kodeBarang);
        ps.setString(3, kodeGudang);
        ResultSet rs = ps.executeQuery();
        StokBarang s = null;
        if(rs.next()){
            s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
        }
        return s;
    }
    public static List<StokBarang> getAllByTanggalAndBarangAndGudang(
            Connection con, String tglMulai, String tglAkhir, String kodeBarang, String kodeGudang)throws Exception{
        String sql = "select * from tt_stok_barang where tanggal between ? and ? ";
        if(!kodeBarang.equals("%"))
            sql = sql + " and kode_barang = '"+kodeBarang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        sql = sql + " order by tanggal desc ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<StokBarang> stok = new ArrayList<>();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
            stok.add(s);
        }
        return stok;
    }
    public static List<StokBarang> getAllByTanggalAndGudang(Connection con, String tanggal, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("SELECT '"+tanggal+"',a.kode_barang,a.kode_gudang,a.stok_awal,a.stok_masuk,a.stok_keluar,a.stok_akhir " +
            "FROM tt_stok_barang a " +
            "JOIN ( " +
            "  SELECT MAX(tanggal) as tanggal, kode_barang as barang " +
            "  FROM tt_stok_barang " +
            "  WHERE kode_gudang = '"+kodeGudang+"' and tanggal <= '"+tanggal+"' " +
            "  GROUP BY barang " +
            ") AS b ON a.kode_barang = b.barang AND a.tanggal = b.tanggal " +
            "where kode_gudang = '"+kodeGudang+"' and a.tanggal <= '"+tanggal+"'");
        ResultSet rs = ps.executeQuery();
        List<StokBarang> stok = new ArrayList<>();
        while(rs.next()){
            StokBarang s = new StokBarang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
            stok.add(s);
        }
        return stok;
    }
    public static void insert(Connection con, StokBarang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert tt_stok_barang values(?,?,?,?,?,?,?)");
        ps.setString(1, s.getTanggal());
        ps.setString(2, s.getKodeBarang());
        ps.setString(3, s.getKodeGudang());
        ps.setDouble(4, s.getStokAwal());
        ps.setDouble(5, s.getStokMasuk());
        ps.setDouble(6, s.getStokKeluar());
        ps.setDouble(7, s.getStokAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con, StokBarang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang "
                + " set stok_awal=?, stok_masuk=?, stok_keluar=?, stok_akhir=? "
                + " where tanggal=? and kode_barang=? and kode_gudang=? ");
        ps.setDouble(1, s.getStokAwal());
        ps.setDouble(2, s.getStokMasuk());
        ps.setDouble(3, s.getStokKeluar());
        ps.setDouble(4, s.getStokAkhir());
        ps.setString(5, s.getTanggal());
        ps.setString(6, s.getKodeBarang());
        ps.setString(7, s.getKodeGudang());
        ps.executeUpdate();
    }
}
