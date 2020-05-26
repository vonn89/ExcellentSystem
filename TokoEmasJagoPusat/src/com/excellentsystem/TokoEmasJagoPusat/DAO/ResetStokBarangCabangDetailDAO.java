/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.ResetStokBarangCabangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class ResetStokBarangCabangDetailDAO {
    
    public static List<ResetStokBarangCabangDetail> getAllByDateAndCabangAndGudang(Connection con, 
            String tglMulai, String tglAkhir, String cabang, String gudang)throws Exception{
        String sql = "select * from tt_reset_stok_barang_cabang_detail where no_reset in ("
                + " select no_reset from tt_reset_stok_barang_cabang_head "
                + " where mid(no_reset,5,6) between ? and ? ";
        if(!cabang.equals("%"))
            sql = sql + " and kode_cabang = '"+cabang+"' ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<ResetStokBarangCabangDetail> listResetStokBarangCabangDetail = new ArrayList<>();
        while(rs.next()){
            ResetStokBarangCabangDetail d = new ResetStokBarangCabangDetail();
            d.setNoReset(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setPersentaseEmas(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            d.setNilaiPokok(rs.getDouble(8));
            listResetStokBarangCabangDetail.add(d);
        }
        return listResetStokBarangCabangDetail;
    }
    public static List<ResetStokBarangCabangDetail> getAllByNoReset(Connection con, String noReset)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_reset_stok_barang_cabang_detail where no_reset = ? ");
        ps.setString(1, noReset);
        ResultSet rs = ps.executeQuery();
        List<ResetStokBarangCabangDetail> listResetStokBarangCabangDetail = new ArrayList<>();
        while(rs.next()){
            ResetStokBarangCabangDetail d = new ResetStokBarangCabangDetail();
            d.setNoReset(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setPersentaseEmas(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            d.setNilaiPokok(rs.getDouble(8));
            listResetStokBarangCabangDetail.add(d);
        }
        return listResetStokBarangCabangDetail;
    }
    public static void insert(Connection con,ResetStokBarangCabangDetail retur)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_reset_stok_barang_cabang_detail "
                + " values(?,?,?,?,?,?,?,?)");
        ps.setString(1, retur.getNoReset());
        ps.setInt(2, retur.getNoUrut());
        ps.setString(3, retur.getKodeKategori());
        ps.setString(4, retur.getKodeJenis());
        ps.setDouble(5, retur.getBerat());
        ps.setDouble(6, retur.getPersentaseEmas());
        ps.setDouble(7, retur.getBeratPersen());
        ps.setDouble(8, retur.getNilaiPokok());
        ps.executeUpdate();        
    }
}
