/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.PembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianDetailDAO {
    public static List<PembelianDetail> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_detail "
            + " where no_pembelian in (select no_pembelian from tt_pembelian_head "
            + " where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setKodeBahan(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setNoKontrak(rs.getString(4));
            d.setNamaBahan(rs.getString(5));
            d.setSpesifikasi(rs.getString(6));
            d.setKeterangan(rs.getString(7));
            d.setBeratKotor(rs.getDouble(8));
            d.setBeratBersih(rs.getDouble(9));
            d.setPanjang(rs.getDouble(10));
            d.setTotal(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PembelianDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail where no_pembelian=?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setKodeBahan(rs.getString(2));
            d.setKodeKategori(rs.getString(3));
            d.setNoKontrak(rs.getString(4));
            d.setNamaBahan(rs.getString(5));
            d.setSpesifikasi(rs.getString(6));
            d.setKeterangan(rs.getString(7));
            d.setBeratKotor(rs.getDouble(8));
            d.setBeratBersih(rs.getDouble(9));
            d.setPanjang(rs.getDouble(10));
            d.setTotal(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PembelianDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_detail values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPembelian());
        ps.setString(2, d.getKodeBahan());
        ps.setString(3, d.getKodeKategori());
        ps.setString(4, d.getNoKontrak());
        ps.setString(5, d.getNamaBahan());
        ps.setString(6, d.getSpesifikasi());
        ps.setString(7, d.getKeterangan());
        ps.setDouble(8, d.getBeratKotor());
        ps.setDouble(9, d.getBeratBersih());
        ps.setDouble(10, d.getPanjang());
        ps.setDouble(11, d.getTotal());
        ps.executeUpdate();
    }
}
