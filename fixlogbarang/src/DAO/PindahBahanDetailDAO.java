/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.PindahBahanDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PindahBahanDetailDAO {
    
    public static List<PindahBahanDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pindah_bahan_detail "
            + " where no_pindah in (select no_pindah from tt_pindah_bahan_head "
            + " where left(tgl_pindah,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PindahBahanDetail> allDetail = new ArrayList<>();
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PindahBahanDetail d = new PindahBahanDetail();
            d.setNoPindah(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBahan(rs.getString(3));
            d.setKodeKategori(rs.getString(4));
            d.setNoKontrak(rs.getString(5));
            d.setNamaBahan(rs.getString(6));
            d.setSpesifikasi(rs.getString(7));
            d.setKeterangan(rs.getString(8));
            d.setBeratKotor(rs.getDouble(9));
            d.setBeratBersih(rs.getDouble(10));
            d.setPanjang(rs.getDouble(11));
            d.setNilai(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PindahBahanDetail> getAllPindahBahanDetail(Connection con, String noPindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_bahan_detail where no_pindah=?");
        ps.setString(1, noPindah);
        ResultSet rs = ps.executeQuery();
        List<PindahBahanDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PindahBahanDetail d = new PindahBahanDetail();
            d.setNoPindah(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBahan(rs.getString(3));
            d.setKodeKategori(rs.getString(4));
            d.setNoKontrak(rs.getString(5));
            d.setNamaBahan(rs.getString(6));
            d.setSpesifikasi(rs.getString(7));
            d.setKeterangan(rs.getString(8));
            d.setBeratKotor(rs.getDouble(9));
            d.setBeratBersih(rs.getDouble(10));
            d.setPanjang(rs.getDouble(11));
            d.setNilai(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PindahBahanDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_bahan_detail values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPindah());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeBahan());
        ps.setString(4, d.getKodeKategori());
        ps.setString(5, d.getNoKontrak());
        ps.setString(6, d.getNamaBahan());
        ps.setString(7, d.getSpesifikasi());
        ps.setString(8, d.getKeterangan());
        ps.setDouble(9, d.getBeratKotor());
        ps.setDouble(10, d.getBeratBersih());
        ps.setDouble(11, d.getPanjang());
        ps.setDouble(12, d.getNilai());
        ps.executeUpdate();
    }
}
