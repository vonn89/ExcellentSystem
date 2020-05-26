/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.ResetStokBarangCabangHead;
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
public class ResetStokBarangCabangHeadDAO {
    
    public static List<ResetStokBarangCabangHead> getAllByDateAndCabangAndGudang(
            Connection con, String tglMulai, String tglAkhir, String cabang, String gudang)throws Exception{
        String sql = "select * from tt_reset_stok_barang_cabang_head where mid(no_reset,5,6) between ? and ? ";
        if(!cabang.equals("%"))
            sql = sql + " and kode_cabang = '"+cabang+"' ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<ResetStokBarangCabangHead> listReset = new ArrayList<>();
        while(rs.next()){
            ResetStokBarangCabangHead r = new ResetStokBarangCabangHead();
            r.setNoReset(rs.getString(1));
            r.setTglReset(rs.getString(2));
            r.setKodeCabang(rs.getString(3));
            r.setKodeGudang(rs.getString(4));
            r.setTotalBerat(rs.getDouble(5));
            r.setTotalBeratPersen(rs.getDouble(6));
            r.setTotalNilai(rs.getDouble(7));
            r.setKodeUser(rs.getString(8));
            listReset.add(r);
        }
        return listReset;
    }
    public static ResetStokBarangCabangHead get(Connection con, String noRetur)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_reset_stok_barang_cabang_head where no_reset = ? ");
        ps.setString(1, noRetur);
        ResultSet rs = ps.executeQuery();
        ResetStokBarangCabangHead r = null;
        while(rs.next()){
            r = new ResetStokBarangCabangHead();
            r.setNoReset(rs.getString(1));
            r.setTglReset(rs.getString(2));
            r.setKodeCabang(rs.getString(3));
            r.setKodeGudang(rs.getString(4));
            r.setTotalBerat(rs.getDouble(5));
            r.setTotalBeratPersen(rs.getDouble(6));
            r.setTotalNilai(rs.getDouble(7));
            r.setKodeUser(rs.getString(8));
        }
        return r;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_reset,4)) from tt_reset_stok_barang_cabang_head "
                + "where mid(no_reset,5,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "RST-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "RST-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, ResetStokBarangCabangHead r)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_reset_stok_barang_cabang_head "
                + "values(?,?,?,?,?,?,?,?)");
        ps.setString(1, r.getNoReset());
        ps.setString(2, r.getTglReset());
        ps.setString(3, r.getKodeCabang());
        ps.setString(4, r.getKodeGudang());
        ps.setDouble(5, r.getTotalBerat());
        ps.setDouble(6, r.getTotalBeratPersen());
        ps.setDouble(7, r.getTotalNilai());
        ps.setString(8, r.getKodeUser());
        ps.executeUpdate();
    }
    public static void update(Connection con, ResetStokBarangCabangHead r)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_reset_stok_barang_cabang_head set "
                + " tgl_reset=?, kode_cabang=?, kode_gudang=?, total_berat=?, "
                + " total_berat_persen=?, total_nilai=? kode_user=? "
                + " where no_reset=?");
        ps.setString(1, r.getTglReset());
        ps.setString(2, r.getKodeCabang());
        ps.setString(3, r.getKodeGudang());
        ps.setDouble(4, r.getTotalBerat());
        ps.setDouble(5, r.getTotalBeratPersen());
        ps.setDouble(6, r.getTotalNilai());
        ps.setString(7, r.getKodeUser());
        ps.setString(8, r.getNoReset());
        ps.executeUpdate();
    }
}
