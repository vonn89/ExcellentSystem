/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifHead;
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
public class PenjualanFiktifHeadDAO {
    
    public static List<PenjualanFiktifHead> getAllByDateAndCabang(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang)throws Exception{
        String sql = "select * from tt_penjualan_fiktif_head where mid(no_penjualan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenjualanFiktifHead> allPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanFiktifHead penjualan = new PenjualanFiktifHead();
            penjualan.setNoPenjualan(rs.getString(1));
            penjualan.setTglPenjualan(rs.getString(2));
            penjualan.setKodeCabang(rs.getString(3));
            penjualan.setNama(rs.getString(4));
            penjualan.setAlamat(rs.getString(5));
            penjualan.setNoTelp(rs.getString(6));
            penjualan.setGrandtotal(rs.getDouble(7));
            allPenjualan.add(penjualan);
        }
        return allPenjualan;
    }
    public static PenjualanFiktifHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_fiktif_head where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanFiktifHead penjualan = null;
        while(rs.next()){
            penjualan = new PenjualanFiktifHead();
            penjualan.setNoPenjualan(rs.getString(1));
            penjualan.setTglPenjualan(rs.getString(2));
            penjualan.setKodeCabang(rs.getString(3));
            penjualan.setNama(rs.getString(4));
            penjualan.setAlamat(rs.getString(5));
            penjualan.setNoTelp(rs.getString(6));
            penjualan.setGrandtotal(rs.getDouble(7));
        }
        return penjualan;
    }
    public static String getId(Connection con, String kodeCabang, String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,4)) from tt_penjualan_fiktif_head "
                + "where mid(no_penjualan,8,6)=? and kode_cabang = ?");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        ps.setString(2, kodeCabang);
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = kodeCabang+"-PJ-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = kodeCabang+"-PJ-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,PenjualanFiktifHead penjualan)throws Exception{
        PreparedStatement ps =  con.prepareStatement("insert into tt_penjualan_fiktif_head values(?,?,?,?,?,?,?)");
        ps.setString(1, penjualan.getNoPenjualan());
        ps.setString(2, penjualan.getTglPenjualan());
        ps.setString(3, penjualan.getKodeCabang());
        ps.setString(4, penjualan.getNama());
        ps.setString(5, penjualan.getAlamat());
        ps.setString(6, penjualan.getNoTelp());
        ps.setDouble(7, penjualan.getGrandtotal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanFiktifHead penjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_fiktif_head set "
                + "tgl_penjualan=?, kode_cabang=?, nama=?, alamat=?, no_telp=?, "
                + "grandtotal=? where no_penjualan=?");
        ps.setString(1, penjualan.getTglPenjualan());
        ps.setString(2, penjualan.getKodeCabang());
        ps.setString(3, penjualan.getNama());
        ps.setString(4, penjualan.getAlamat());
        ps.setString(5, penjualan.getNoTelp());
        ps.setDouble(6, penjualan.getGrandtotal());
        ps.setString(7, penjualan.getNoPenjualan());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_penjualan_fiktif_head where no_penjualan=?");
        ps.setString(1, noPenjualan);
        ps.executeUpdate();
    }
}
