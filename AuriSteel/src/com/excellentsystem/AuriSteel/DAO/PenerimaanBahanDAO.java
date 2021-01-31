/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenerimaanBahanDAO {
    public static List<PenerimaanBahan> getAllByKategoriAndGudangAndStatus(Connection con, 
            String kategori, String gudang, String status)throws Exception{
        String sql = "select * from tt_penerimaan_bahan where no_penerimaan !='' ";
        if(!kategori.equals("%")) 
            sql = sql + " and kode_kategori = '"+kategori+"' ";
        if(!gudang.equals("%")) 
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        if(!status.equals("%")) 
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PenerimaanBahan> allPemesanan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenerimaanBahan p = new PenerimaanBahan();
            p.setNoPenerimaan(rs.getString(1));
            p.setTglPenerimaan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeGudang(rs.getString(3));
            p.setKodeBahan(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setNamaBahan(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setBeratTimbangan(rs.getDouble(8));
            p.setBeratKotor(rs.getDouble(9));
            p.setBeratBersih(rs.getDouble(10));
            p.setPanjang(rs.getDouble(11));
            p.setKodeUser(rs.getString(12));
            p.setTglBatal(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
            p.setUserBatal(rs.getString(14));
            p.setStatus(rs.getString(15));
            allPemesanan.add(p);
        }
        return allPemesanan;
    }
    public static List<PenerimaanBahan> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penerimaan_bahan where left(tgl_penerimaan,10) between ? and ? ";
        if(!status.equals("%")) 
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PenerimaanBahan> allPemesanan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenerimaanBahan p = new PenerimaanBahan();
            p.setNoPenerimaan(rs.getString(1));
            p.setTglPenerimaan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeGudang(rs.getString(3));
            p.setKodeBahan(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setNamaBahan(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setBeratTimbangan(rs.getDouble(8));
            p.setBeratKotor(rs.getDouble(9));
            p.setBeratBersih(rs.getDouble(10));
            p.setPanjang(rs.getDouble(11));
            p.setKodeUser(rs.getString(12));
            p.setTglBatal(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
            p.setUserBatal(rs.getString(14));
            p.setStatus(rs.getString(15));
            allPemesanan.add(p);
        }
        return allPemesanan;
    }
    public static PenerimaanBahan get(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penerimaan_bahan where no_penerimaan = ? ");
        ps.setString(1, noPemesanan);
        PenerimaanBahan p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PenerimaanBahan();
            p.setNoPenerimaan(rs.getString(1));
            p.setTglPenerimaan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeGudang(rs.getString(3));
            p.setKodeBahan(rs.getString(4));
            p.setKodeKategori(rs.getString(5));
            p.setNamaBahan(rs.getString(6));
            p.setKeterangan(rs.getString(7));
            p.setBeratTimbangan(rs.getDouble(8));
            p.setBeratKotor(rs.getDouble(9));
            p.setBeratBersih(rs.getDouble(10));
            p.setPanjang(rs.getDouble(11));
            p.setKodeUser(rs.getString(12));
            p.setTglBatal(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
            p.setUserBatal(rs.getString(14));
            p.setStatus(rs.getString(15));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penerimaan,3)) from tt_penerimaan_bahan "
                + " where mid(no_penerimaan,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PM-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PM-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PenerimaanBahan p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penerimaan_bahan values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenerimaan());
        ps.setString(2, p.getTglPenerimaan());
        ps.setString(3, p.getKodeGudang());
        ps.setString(4, p.getKodeBahan());
        ps.setString(5, p.getKodeKategori());
        ps.setString(6, p.getNamaBahan());
        ps.setString(7, p.getKeterangan());
        ps.setDouble(8, p.getBeratTimbangan());
        ps.setDouble(9, p.getBeratKotor());
        ps.setDouble(10, p.getBeratBersih());
        ps.setDouble(11, p.getPanjang());
        ps.setString(12, p.getKodeUser());
        ps.setString(13, p.getTglBatal());
        ps.setString(14, p.getUserBatal());
        ps.setString(15, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenerimaanBahan p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penerimaan_bahan set "
                + " tgl_penerimaan=?, kode_gudang=?, kode_bahan=?, kode_kategori=?, "
                + " nama_bahan=?, keterangan=?, berat_timbangan=?, berat_kotor=?, berat_bersih=?, panjang=?, "
                + " kode_user=?, tgl_batal=?, user_batal=?, status=? where no_penerimaan=?");
        ps.setString(1, p.getTglPenerimaan());
        ps.setString(2, p.getKodeGudang());
        ps.setString(3, p.getKodeBahan());
        ps.setString(4, p.getKodeKategori());
        ps.setString(5, p.getNamaBahan());
        ps.setString(6, p.getKeterangan());
        ps.setDouble(7, p.getBeratTimbangan());
        ps.setDouble(8, p.getBeratKotor());
        ps.setDouble(9, p.getBeratBersih());
        ps.setDouble(10, p.getPanjang());
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.setString(14, p.getStatus());
        ps.setString(15, p.getNoPenerimaan());
        ps.executeUpdate();
    }
}
