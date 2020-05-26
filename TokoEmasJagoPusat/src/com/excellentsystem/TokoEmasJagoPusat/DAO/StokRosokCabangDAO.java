/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class StokRosokCabangDAO {
    public static List<StokRosokCabang> getAllByDateAndCabangAndGudang(
            Connection con, String tglAwal, String tglAkhir, String kodeCabang, String kodeGudang)throws Exception{
        String sql = "select * from tt_stok_rosok_cabang where tanggal between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<StokRosokCabang> listStokRosokCabang = new ArrayList<>();
        while(rs.next()){
            StokRosokCabang s = new StokRosokCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeCabang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            
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
            listStokRosokCabang.add(s);
        }
        return listStokRosokCabang;
    }
    public static List<StokRosokCabang> getAllByCabangAndListGudang(Connection con, String tanggal, String kodeCabang, List<String> listGudang)throws Exception{
        String sql = "select * from tt_stok_rosok_cabang where tanggal = ? ";
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
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        List<StokRosokCabang> listStokRosokCabang = new ArrayList<>();
        while(rs.next()){
            StokRosokCabang s = new StokRosokCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeCabang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            
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
            listStokRosokCabang.add(s);
        }
        return listStokRosokCabang;
    }
    public static List<StokRosokCabang> getAllByCabangAndGudang(Connection con, String tanggal, String kodeCabang, String kodeGudang)throws Exception{
        String sql = "select * from tt_stok_rosok_cabang where tanggal = ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        List<StokRosokCabang> listStokRosokCabang = new ArrayList<>();
        while(rs.next()){
            StokRosokCabang s = new StokRosokCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeCabang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            
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
            listStokRosokCabang.add(s);
        }
        return listStokRosokCabang;
    }
    public static StokRosokCabang get(Connection con, String tanggal, String kodeCabang, String kodeGudang)throws Exception{
        String sql  = "select * from tt_stok_rosok_cabang where tanggal = ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeGudang.equals("%"))
            sql = sql + " and kode_gudang = '"+kodeGudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tanggal);
        ResultSet rs = ps.executeQuery();
        StokRosokCabang s = null;
        while(rs.next()){
            s = new StokRosokCabang();
            s.setTanggal(rs.getString(1));
            s.setKodeCabang(rs.getString(2));
            s.setKodeGudang(rs.getString(3));
            
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
        con.prepareStatement("insert into tt_stok_rosok_cabang "
            + " select '"+nextDate+"',kode_cabang,kode_gudang,"
            + " berat_akhir,berat_persen_akhir,nilai_akhir,"
            + " 0,0,0,"
            + " 0,0,0,"
            + " berat_akhir,berat_persen_akhir,nilai_akhir "
            + " from tt_stok_rosok_cabang where tanggal = '"+date+"' and berat_akhir!=0 and berat_persen_akhir!=0 and nilai_akhir!=0").executeUpdate();
    }
    public static void insert(Connection con, StokRosokCabang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stok_rosok_cabang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, s.getTanggal());
        ps.setString(2, s.getKodeCabang());
        ps.setString(3, s.getKodeGudang());
        
        ps.setDouble(4, s.getBeratAwal());
        ps.setDouble(5, s.getBeratPersenAwal());
        ps.setDouble(6, s.getNilaiAwal());
        
        ps.setDouble(7, s.getBeratMasuk());
        ps.setDouble(8, s.getBeratPersenMasuk());
        ps.setDouble(9, s.getNilaiMasuk());
        
        ps.setDouble(10, s.getBeratKeluar());
        ps.setDouble(11, s.getBeratPersenKeluar());
        ps.setDouble(12, s.getNilaiKeluar());
        
        ps.setDouble(13, s.getBeratAkhir());
        ps.setDouble(14, s.getBeratPersenAkhir());
        ps.setDouble(15, s.getNilaiAkhir());
        ps.executeUpdate();
    }
    public static void update(Connection con,StokRosokCabang s)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stok_rosok_cabang set "
            + "berat_awal=?, berat_persen_awal=?, nilai_awal=?, "
            + "berat_masuk=?, berat_persen_masuk=?, nilai_masuk=?, "
            + "berat_keluar=?, berat_persen_keluar=?, nilai_keluar=?, "
            + "berat_akhir=?, berat_persen_akhir=?, nilai_akhir=? "
            + "where tanggal=? and kode_cabang=? and kode_gudang=?");
        ps.setDouble(1, s.getBeratAwal());
        ps.setDouble(2, s.getBeratPersenAwal());
        ps.setDouble(3, s.getNilaiAwal());
        
        ps.setDouble(4, s.getBeratMasuk());
        ps.setDouble(5, s.getBeratPersenMasuk());
        ps.setDouble(6, s.getNilaiMasuk());
        
        ps.setDouble(7, s.getBeratKeluar());
        ps.setDouble(8, s.getBeratPersenKeluar());
        ps.setDouble(9, s.getNilaiKeluar());
        
        ps.setDouble(10, s.getBeratAkhir());
        ps.setDouble(11, s.getBeratPersenAkhir());
        ps.setDouble(12, s.getNilaiAkhir());
        
        ps.setString(13, s.getTanggal());
        ps.setString(14, s.getKodeCabang());
        ps.setString(15, s.getKodeGudang());
        ps.executeUpdate();
    }
}
