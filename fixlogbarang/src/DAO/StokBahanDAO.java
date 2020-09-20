/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.StokBahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokBahanDAO {
    public static List<StokBahan> getAllByDateAndGudang(Connection con, String tanggal, String gudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("SELECT '"+tanggal+"',a.kode_bahan,a.kode_gudang,a.stok_awal,a.stok_masuk,a.stok_keluar,a.stok_akhir " +
            " FROM tt_stok_bahan a " +
            " JOIN ( " +
            "  SELECT MAX(tanggal) as tanggal, kode_bahan as barang " +
            "  FROM tt_stok_bahan " +
            "  WHERE kode_gudang = '"+gudang+"' and tanggal <= '"+tanggal+"' " +
            "  GROUP BY barang " +
            " ) AS b ON a.kode_bahan = b.barang AND a.tanggal = b.tanggal " +
            " where kode_gudang = '"+gudang+"' and a.tanggal <= '"+tanggal+"'");
        ResultSet rs = ps.executeQuery();
        List<StokBahan> stok = new ArrayList<>();
        while(rs.next()){
            StokBahan s = new StokBahan();
            s.setTanggal(rs.getString(1));
            s.setKodeBahan(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
            stok.add(s);
        }
        return stok;
    }
    public static List<StokBahan> getAllByTanggalAndBahanAndGudang(
            Connection con, String tglMulai, String tglAkhir, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_bahan "
                + "where tanggal between ? and ?  and kode_bahan = ?  and kode_gudang = ?  order by tanggal desc");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, kodeBahan);
        ps.setString(4, kodeGudang);
        ResultSet rs = ps.executeQuery();
        List<StokBahan> stok = new ArrayList<>();
        while(rs.next()){
            StokBahan s = new StokBahan();
            s.setTanggal(rs.getString(1));
            s.setKodeBahan(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
            stok.add(s);
        }
        return stok;
    }
    public static StokBahan get(Connection con, String tanggal, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_bahan "
                + " where tanggal <= ? and kode_bahan=? and kode_gudang=? order by tanggal desc limit 1");
        ps.setString(1, tanggal);
        ps.setString(2, kodeBahan);
        ps.setString(3, kodeGudang);
        StokBahan s = null;
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            s = new StokBahan();
            s.setTanggal(rs.getString(1));
            s.setKodeBahan(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
        }
        return s;
    }
    public static StokBahan getStokAkhir(Connection con, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_bahan "
                + " where kode_bahan=? and kode_gudang=? order by tanggal desc limit 1");
        ps.setString(1, kodeBahan);
        ps.setString(2, kodeGudang);
        StokBahan s = null;
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            s = new StokBahan();
            s.setTanggal(rs.getString(1));
            s.setKodeBahan(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            s.setStokAwal(rs.getDouble(4));
            s.setStokMasuk(rs.getDouble(5));
            s.setStokKeluar(rs.getDouble(6));
            s.setStokAkhir(rs.getDouble(7));
        }
        return s;
    }
    public static void insert(Connection con, StokBahan s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_bahan values(?,?,?,?,?,?,?)");
        ps.setString(1, s.getTanggal());
        ps.setString(2, s.getKodeBahan());
        ps.setString(3, s.getKodeGudang());
        ps.setDouble(4, s.getStokAwal());
        ps.setDouble(5, s.getStokMasuk());
        ps.setDouble(6, s.getStokKeluar());
        ps.setDouble(7, s.getStokAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con, StokBahan s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_bahan set "
                + " stok_awal=?, stok_masuk=?, stok_keluar=?, stok_akhir=? "
                + " where tanggal=? and kode_bahan=? and kode_gudang=? ");
        ps.setDouble(1, s.getStokAwal());
        ps.setDouble(2, s.getStokMasuk());
        ps.setDouble(3, s.getStokKeluar());
        ps.setDouble(4, s.getStokAkhir());
        ps.setString(5, s.getTanggal());
        ps.setString(6, s.getKodeBahan());
        ps.setString(7, s.getKodeGudang());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String kodeBahan, String kodeGudang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_stok_bahan where kode_bahan=? and kode_gudang=?");
        ps.setString(1, kodeBahan);
        ps.setString(2, kodeGudang);
        ps.executeUpdate();
    }
}
