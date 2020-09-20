/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.PenjualanCoilDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanCoilDetailDAO {
    public static List<PenjualanCoilDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_coil_detail "
            + " where no_penjualan in (select no_penjualan from tt_penjualan_coil_head "
            + " where left(tgl_penjualan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PenjualanCoilDetail> allDetail = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanCoilDetail d = new PenjualanCoilDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBahan(rs.getString(2));
            d.setNamaBahan(rs.getString(3));
            d.setSpesifikasi(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setBeratKotor(rs.getDouble(6));
            d.setBeratBersih(rs.getDouble(7));
            d.setPanjang(rs.getDouble(8));
            d.setNilai(rs.getDouble(9));
            d.setHargaJual(rs.getDouble(10));
            d.setTotal(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PenjualanCoilDetail> getAllPenjualanCoilDetail(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_coil_detail where no_penjualan=?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        List<PenjualanCoilDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanCoilDetail d = new PenjualanCoilDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeBahan(rs.getString(2));
            d.setNamaBahan(rs.getString(3));
            d.setSpesifikasi(rs.getString(4));
            d.setKeterangan(rs.getString(5));
            d.setBeratKotor(rs.getDouble(6));
            d.setBeratBersih(rs.getDouble(7));
            d.setPanjang(rs.getDouble(8));
            d.setNilai(rs.getDouble(9));
            d.setHargaJual(rs.getDouble(10));
            d.setTotal(rs.getDouble(11));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, PenjualanCoilDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_coil_detail values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPenjualan());
        ps.setString(2, d.getKodeBahan());
        ps.setString(3, d.getNamaBahan());
        ps.setString(4, d.getSpesifikasi());
        ps.setString(5, d.getKeterangan());
        ps.setDouble(6, d.getBeratKotor());
        ps.setDouble(7, d.getBeratBersih());
        ps.setDouble(8, d.getPanjang());
        ps.setDouble(9, d.getNilai());
        ps.setDouble(10, d.getHargaJual());
        ps.setDouble(11, d.getTotal());
        ps.executeUpdate();
    }
}
