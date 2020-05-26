/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanCabangDetailDAO {
    public static List<PenjualanCabangDetail> getAllByDateAndCabangAndStatus(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang,String status)throws Exception{
        String sql = "select * from tt_penjualan_cabang_detail where "
                + "no_penjualan_cabang in ( select no_penjualan_cabang from tt_penjualan_cabang_head where "
                + "mid(no_penjualan_cabang,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenjualanCabangDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanCabangDetail d = new PenjualanCabangDetail();
            d.setNoPenjualanCabang(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setQty(rs.getInt(5));
            d.setBerat(rs.getDouble(6));
            d.setPersentaseEmas(rs.getDouble(7));
            d.setBeratPersen(rs.getDouble(8));
            d.setHargaPersen(rs.getDouble(9));
            d.setTotalHargaPersen(rs.getDouble(10));
            d.setTotalNilai(rs.getDouble(11));
            d.setTotalHargaRp(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static List<PenjualanCabangDetail> getAllByNoPenjualan(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_cabang_detail where no_penjualan_cabang=?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        List<PenjualanCabangDetail> allDetail = new ArrayList<>();
        while(rs.next()){
            PenjualanCabangDetail d = new PenjualanCabangDetail();
            d.setNoPenjualanCabang(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setQty(rs.getInt(5));
            d.setBerat(rs.getDouble(6));
            d.setPersentaseEmas(rs.getDouble(7));
            d.setBeratPersen(rs.getDouble(8));
            d.setHargaPersen(rs.getDouble(9));
            d.setTotalHargaPersen(rs.getDouble(10));
            d.setTotalNilai(rs.getDouble(11));
            d.setTotalHargaRp(rs.getDouble(12));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con , PenjualanCabangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_cabang_detail "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPenjualanCabang());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeKategori());
        ps.setString(4, d.getKodeJenis());
        ps.setInt(5, d.getQty());
        ps.setDouble(6, d.getBerat());
        ps.setDouble(7, d.getPersentaseEmas());
        ps.setDouble(8, d.getBeratPersen());
        ps.setDouble(9, d.getHargaPersen());
        ps.setDouble(10, d.getTotalHargaPersen());
        ps.setDouble(11, d.getTotalNilai());
        ps.setDouble(12, d.getTotalHargaRp());
        ps.executeUpdate();
    }
}
