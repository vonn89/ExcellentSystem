/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokBarangCabangDAO {
    public static List<StokBarangCabang> getAllBarcodeByDateAndCabangAndGudangAndKategoriAndJenisAndBarcode(
            Connection con, String tglAwal, String tglAkhir, String kodeCabang, String kodeGudang, 
            String kodeKategori, String kodeJenis, String barcode)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal between ? and ? and kode_barcode!='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        if(!barcode.equals("%"))
            sql = sql + " and kode_barcode = '"+barcode+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static List<StokBarangCabang> getAllNonBarcodeByDateAndCabangAndGudangAndKategoriAndJenis(
            Connection con, String tglAwal, String tglAkhir, String kodeCabang, String kodeGudang, String kodeKategori, String kodeJenis)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal between ? and ? and kode_barcode='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static List<StokBarangCabang> getAllBarcodeByCabangAndListGudangAndKategoriAndJenisAndBarcode(
            Connection con, String tanggal, String kodeCabang, List<String> listGudang, String kodeKategori, String kodeJenis, String barcode)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal=? and kode_barcode!='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!listGudang.isEmpty()){
            String gudang = "";
            for(String g : listGudang){
                gudang = gudang + "'"+g+"'";
                if(listGudang.indexOf(g)+1<listGudang.size())
                    gudang = gudang + ",";
            }
            sql = sql + " and kode_gudang in ("+gudang+") ";
        }
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        if(!barcode.equals("%"))
            sql = sql + " and kode_barcode = '"+barcode+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static List<StokBarangCabang> getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(
            Connection con, String tanggal, String kodeCabang, String kodeGudang, String kodeKategori, String kodeJenis, String barcode)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal=? and kode_barcode!='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        if(!barcode.equals("%"))
            sql = sql + " and kode_barcode = '"+barcode+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static List<StokBarangCabang> getAllNonBarcodeByCabangAndListGudangAndKategoriAndJenis(
            Connection con, String tanggal, String kodeCabang, List<String> listGudang, String kodeKategori, String kodeJenis)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal=? and kode_barcode='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!listGudang.isEmpty()){
            String gudang = "";
            for(String g : listGudang){
                gudang = gudang + "'"+g+"'";
                if(listGudang.indexOf(g)+1<listGudang.size())
                    gudang = gudang + ",";
            }
            sql = sql + " and kode_gudang in ("+gudang+") ";
        }
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static List<StokBarangCabang> getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
            Connection con, String tanggal, String kodeCabang, String kodeGudang, String kodeKategori, String kodeJenis)throws Exception{
        String sql = "select * from tt_stok_barang_cabang where tanggal=? and kode_barcode='' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        if(!kodeKategori.equals("%"))
            sql = sql + " and kode_kategori = '"+kodeKategori+"' ";
        if(!kodeJenis.equals("%"))
            sql = sql + " and kode_jenis = '"+kodeJenis+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        List<StokBarangCabang> allStokBarangCabang = new ArrayList<>();
        ResultSet rs =ps.executeQuery();
        while(rs.next()){
            StokBarangCabang s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));

            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));

            allStokBarangCabang.add(s);
        }
        return allStokBarangCabang;
    }
    public static StokBarangCabang getNonBarcode(Connection con, String tanggal, String kodeGudang, String kodeJenis)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang_cabang "
                + "where tanggal = ? and kode_gudang = ? and kode_jenis = ? and kode_barcode=''");
        ps.setString(1, tanggal);
        ps.setString(2, kodeGudang);
        ps.setString(3, kodeJenis);
        ResultSet rs = ps.executeQuery();
        StokBarangCabang s = null;
        if(rs.next()){
            s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));
                
            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
        }
        return s;
    }
    public static StokBarangCabang getBatalBarcode(Connection con, String tanggal, String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang_cabang "
                + "where tanggal = ? and kode_barcode = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        StokBarangCabang s = null;
        if(rs.next()){
            s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));

            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
        }
        return s;
    }
    public static StokBarangCabang getBarcode(Connection con, String tanggal, String kodeGudang, String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stok_barang_cabang "
                + "where tanggal = ? and kode_gudang = ? and kode_barcode = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, kodeGudang);
        ps.setString(3, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        StokBarangCabang s = null;
        if(rs.next()){
            s = new StokBarangCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeBarang(rs.getString(2));
            s.setKodeBarcode(rs.getString(3));
            s.setKodeCabang(rs.getString(4));
            s.setKodeGudang(rs.getString(5));
            s.setKodeKategori(rs.getString(6));
            s.setKodeJenis(rs.getString(7));
            
            s.setStokAwal(rs.getInt(8));
            s.setBeratAwal(rs.getDouble(9));
            s.setBeratPersenAwal(rs.getDouble(10));
            s.setNilaiAwal(rs.getDouble(11));

            s.setStokMasuk(rs.getInt(12));
            s.setBeratMasuk(rs.getDouble(13));
            s.setBeratPersenMasuk(rs.getDouble(14));
            s.setNilaiMasuk(rs.getDouble(15));

            s.setStokKeluar(rs.getInt(16));
            s.setBeratKeluar(rs.getDouble(17));
            s.setBeratPersenKeluar(rs.getDouble(18));
            s.setNilaiKeluar(rs.getDouble(19));

            s.setStokAkhir(rs.getInt(20));
            s.setBeratAkhir(rs.getDouble(21));
            s.setBeratPersenAkhir(rs.getDouble(22));
            s.setNilaiAkhir(rs.getDouble(23));
        }
        return s;
    }
    public static void insertStokAwal(Connection con, String date, String nextDate)throws Exception{
        con.prepareStatement("insert into tt_stok_barang_cabang "
            + " select '"+nextDate+"',kode_barang,kode_barcode,kode_cabang,kode_gudang,kode_kategori,kode_jenis,"
            + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir,"
            + " 0,0,0,0,"
            + " 0,0,0,0,"
            + " stok_akhir,berat_akhir,berat_persen_akhir,nilai_akhir "
            + " from tt_stok_barang_cabang where tanggal = '"+date+"' and stok_akhir!=0 ").executeUpdate();
    }
    public static void insert(Connection con,StokBarangCabang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_barang_cabang values("
                + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, stok.getTanggal());
        ps.setString(2, stok.getKodeBarang());
        ps.setString(3, stok.getKodeBarcode());
        ps.setString(4, stok.getKodeCabang());
        ps.setString(5, stok.getKodeGudang());
        ps.setString(6, stok.getKodeKategori());
        ps.setString(7, stok.getKodeJenis());
        
        ps.setInt(8, stok.getStokAwal());
        ps.setDouble(9, stok.getBeratAwal());
        ps.setDouble(10, stok.getBeratPersenAwal());
        ps.setDouble(11, stok.getNilaiAwal());
        
        ps.setInt(12, stok.getStokMasuk());
        ps.setDouble(13, stok.getBeratMasuk());
        ps.setDouble(14, stok.getBeratPersenMasuk());
        ps.setDouble(15, stok.getNilaiMasuk());
        
        ps.setInt(16, stok.getStokKeluar());
        ps.setDouble(17, stok.getBeratKeluar());
        ps.setDouble(18, stok.getBeratPersenKeluar());
        ps.setDouble(19, stok.getNilaiKeluar());
        
        ps.setInt(20, stok.getStokAkhir());
        ps.setDouble(21, stok.getBeratAkhir());
        ps.setDouble(22, stok.getBeratPersenAkhir());
        ps.setDouble(23, stok.getNilaiAkhir());
        ps.executeUpdate();      
    }
    public static void update(Connection con,StokBarangCabang stok)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_barang_cabang set "
            + " stok_awal=?, berat_awal=?, berat_persen_awal=?, nilai_awal=?, "
            + " stok_masuk=?, berat_masuk=?, berat_persen_masuk=?, nilai_masuk=?, "
            + " stok_keluar=?, berat_keluar=?, berat_persen_keluar=?, nilai_keluar=?, "
            + " stok_akhir=?, berat_akhir=?, berat_persen_akhir=?, nilai_akhir=? "
            + " where tanggal=? and kode_barang=? and kode_barcode=? and "
            + " kode_cabang=? and kode_gudang=?  and kode_kategori=? and kode_jenis=? ");
        ps.setInt(1, stok.getStokAwal());
        ps.setDouble(2, stok.getBeratAwal());
        ps.setDouble(3, stok.getBeratPersenAwal());
        ps.setDouble(4, stok.getNilaiAwal());
        
        ps.setInt(5, stok.getStokMasuk());
        ps.setDouble(6, stok.getBeratMasuk());
        ps.setDouble(7, stok.getBeratPersenMasuk());
        ps.setDouble(8, stok.getNilaiMasuk());
        
        ps.setInt(9, stok.getStokKeluar());
        ps.setDouble(10, stok.getBeratKeluar());
        ps.setDouble(11, stok.getBeratPersenKeluar());
        ps.setDouble(12, stok.getNilaiKeluar());
        
        ps.setInt(13, stok.getStokAkhir());
        ps.setDouble(14, stok.getBeratAkhir());
        ps.setDouble(15, stok.getBeratPersenAkhir());
        ps.setDouble(16, stok.getNilaiAkhir());
        
        ps.setString(17, stok.getTanggal());
        ps.setString(18, stok.getKodeBarang());
        ps.setString(19, stok.getKodeBarcode());
        ps.setString(20, stok.getKodeCabang());
        ps.setString(21, stok.getKodeGudang());
        ps.setString(22, stok.getKodeKategori());
        ps.setString(23, stok.getKodeJenis());
        ps.executeUpdate();
    }
}
