/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class RevisiBarangCabangDAO {
    
    public static List<RevisiBarangCabang> getAllByDateAndCabangAndGudangAndStatus(
            Connection con, String tglMulai, String tglAkhir, String cabang, String gudang, String status)throws Exception{
        String sql = "select * from tt_revisi_barang_cabang where mid(no_revisi,5,6) between ? and ? ";
        if(!cabang.equals("%"))
            sql = sql + " and kode_cabang = '"+cabang+"' ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<RevisiBarangCabang> listRevisi = new ArrayList<>();
        while(rs.next()){
            RevisiBarangCabang r = new RevisiBarangCabang();
            r.setNoRevisi(rs.getString(1));
            r.setTglRevisi(rs.getString(2));
            r.setKeterangan(rs.getString(3));
            r.setJenisRevisi(rs.getString(4));
            r.setKodeCabang(rs.getString(5));
            r.setKodeGudang(rs.getString(6));
            
            r.setKodeKategori(rs.getString(7));
            r.setKodeJenis(rs.getString(8));
            r.setBeratPersen(rs.getDouble(9));
            r.setKodeKategoriRevisi(rs.getString(10));
            r.setKodeJenisRevisi(rs.getString(11));
            r.setQtyRevisi(rs.getInt(12));
            r.setBeratRevisi(rs.getDouble(13));
            r.setBeratPersenRevisi(rs.getDouble(14));
            r.setNilaiRevisi(rs.getDouble(15));
            
            r.setKodeUser(rs.getString(16));
            r.setStatus(rs.getString(17));
            r.setTglBatal(rs.getString(18));
            r.setUserBatal(rs.getString(19));
            listRevisi.add(r);
        }
        return listRevisi;
    }
    public static RevisiBarangCabang get(Connection con, String noRetur)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_revisi_barang_cabang where no_revisi = ? ");
        ps.setString(1, noRetur);
        ResultSet rs = ps.executeQuery();
        RevisiBarangCabang r = null;
        while(rs.next()){
            r = new RevisiBarangCabang();
            r.setNoRevisi(rs.getString(1));
            r.setTglRevisi(rs.getString(2));
            r.setKeterangan(rs.getString(3));
            r.setJenisRevisi(rs.getString(4));
            r.setKodeCabang(rs.getString(5));
            r.setKodeGudang(rs.getString(6));
            
            r.setKodeKategori(rs.getString(7));
            r.setKodeJenis(rs.getString(8));
            r.setBeratPersen(rs.getDouble(9));
            r.setKodeKategoriRevisi(rs.getString(10));
            r.setKodeJenisRevisi(rs.getString(11));
            r.setQtyRevisi(rs.getInt(12));
            r.setBeratRevisi(rs.getDouble(13));
            r.setBeratPersenRevisi(rs.getDouble(14));
            r.setNilaiRevisi(rs.getDouble(15));
            
            r.setKodeUser(rs.getString(16));
            r.setStatus(rs.getString(17));
            r.setTglBatal(rs.getString(18));
            r.setUserBatal(rs.getString(19));
        }
        return r;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_revisi,4)) from tt_revisi_barang_cabang "
                + "where mid(no_revisi,5,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "REV-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "REV-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, RevisiBarangCabang r)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_revisi_barang_cabang "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, r.getNoRevisi());
        ps.setString(2, r.getTglRevisi());
        ps.setString(3, r.getKeterangan());
        ps.setString(4, r.getJenisRevisi());
        ps.setString(5, r.getKodeCabang());
        ps.setString(6, r.getKodeGudang());
        
        ps.setString(7, r.getKodeKategori());
        ps.setString(8, r.getKodeJenis());
        ps.setDouble(9, r.getBeratPersen());
        ps.setString(10, r.getKodeKategoriRevisi());
        ps.setString(11, r.getKodeJenisRevisi());
        ps.setDouble(12, r.getQtyRevisi());
        ps.setDouble(13, r.getBeratRevisi());
        ps.setDouble(14, r.getBeratPersenRevisi());
        ps.setDouble(15, r.getNilaiRevisi());
        
        ps.setString(16, r.getKodeUser());
        ps.setString(17, r.getStatus());
        ps.setString(18, r.getTglBatal());
        ps.setString(19, r.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, RevisiBarangCabang r)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_revisi_barang_cabang set "
                + " tgl_revisi=?, keterangan=?, jenis_revisi=?, kode_cabang=?, kode_gudang=?, "
                + " kode_kategori=?, kode_jenis=?, berat_persen=?, kode_kategori_revisi=?, kode_jenis_revisi=?, "
                + " qty_revisi=?, berat_revisi=?, berat_persen_revisi=?, nilai_revisi=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? where no_revisi=?");
        ps.setString(1, r.getTglRevisi());
        ps.setString(2, r.getKeterangan());
        ps.setString(3, r.getJenisRevisi());
        ps.setString(4, r.getKodeCabang());
        ps.setString(5, r.getKodeGudang());
        
        ps.setString(6, r.getKodeKategori());
        ps.setString(7, r.getKodeJenis());
        ps.setDouble(8, r.getBeratPersen());
        ps.setString(9, r.getKodeKategoriRevisi());
        ps.setString(10, r.getKodeJenisRevisi());
        ps.setDouble(11, r.getQtyRevisi());
        ps.setDouble(12, r.getBeratRevisi());
        ps.setDouble(13, r.getBeratPersenRevisi());
        ps.setDouble(14, r.getNilaiRevisi());
        
        ps.setString(15, r.getKodeUser());
        ps.setString(16, r.getStatus());
        ps.setString(17, r.getTglBatal());
        ps.setString(18, r.getUserBatal());
        ps.setString(19, r.getNoRevisi());
        ps.executeUpdate();
    }
}
