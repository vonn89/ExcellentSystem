/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PenjualanFiktifDetailDAO {
    
    public static List<PenjualanFiktifDetail> getAllByDateAndCabang(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang)throws Exception{
        String sql = "select * from tt_penjualan_fiktif_detail where no_penjualan in ( select no_penjualan from tt_penjualan_fiktif_head where "
                + " mid(no_penjualan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenjualanFiktifDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanFiktifDetail d = new PenjualanFiktifDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeKategori(rs.getString(2));
            d.setNamaBarang(rs.getString(3));
            d.setKadar(rs.getString(4));
            d.setKodeIntern(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setHargaJual(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PenjualanFiktifDetail> getAllByNoPenjualan(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_fiktif_detail where no_penjualan=?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        List<PenjualanFiktifDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanFiktifDetail d = new PenjualanFiktifDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setKodeKategori(rs.getString(2));
            d.setNamaBarang(rs.getString(3));
            d.setKadar(rs.getString(4));
            d.setKodeIntern(rs.getString(5));
            d.setBerat(rs.getDouble(6));
            d.setHargaJual(rs.getDouble(7));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con , PenjualanFiktifDetail detail )throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_fiktif_detail values(?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPenjualan());
        ps.setString(2, detail.getKodeKategori());
        ps.setString(3, detail.getNamaBarang());
        ps.setString(4, detail.getKadar());
        ps.setString(5, detail.getKodeIntern());
        ps.setDouble(6, detail.getBerat());
        ps.setDouble(7, detail.getHargaJual());
        ps.executeUpdate();
    }
    public static void delete(Connection con , String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_penjualan_fiktif_detail where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        ps.executeUpdate();
    }
}
