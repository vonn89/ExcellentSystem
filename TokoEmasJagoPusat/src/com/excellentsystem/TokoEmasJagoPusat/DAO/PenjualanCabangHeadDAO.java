/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanCabangHeadDAO {
    public static List<PenjualanCabangHead> getAllByDateAndCabangAndStatus(Connection con, 
            String tglMulai,String tglAkhir,String kodeCabang, String status)throws Exception{
        String sql = "select * from tt_penjualan_cabang_head where mid(no_penjualan_cabang,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenjualanCabangHead> allPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanCabangHead p = new PenjualanCabangHead();
            p.setNoPenjualanCabang(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setTotalQty(rs.getInt(4));
            p.setTotalBerat(rs.getDouble(5));
            p.setTotalHargaPersen(rs.getDouble(6));
            p.setHargaEmas(rs.getDouble(7));
            p.setTotalPenjualan(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
            p.setTglBatal(rs.getString(11));
            p.setUserBatal(rs.getString(12));
            allPenjualan.add(p);
        }
        return allPenjualan;
    }
    public static PenjualanCabangHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_cabang_head where no_penjualan_cabang = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanCabangHead p = null;
        while(rs.next()){
            p = new PenjualanCabangHead();
            p.setNoPenjualanCabang(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setTotalQty(rs.getInt(4));
            p.setTotalBerat(rs.getDouble(5));
            p.setTotalHargaPersen(rs.getDouble(6));
            p.setHargaEmas(rs.getDouble(7));
            p.setTotalPenjualan(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
            p.setTglBatal(rs.getString(11));
            p.setUserBatal(rs.getString(12));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan_cabang,4)) from tt_penjualan_cabang_head "
                + "where mid(no_penjualan_cabang,8,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PST-PC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PST-PC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,PenjualanCabangHead p)throws Exception{
        PreparedStatement ps =  con.prepareStatement("insert into tt_penjualan_cabang_head values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualanCabang());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getKodeCabang());
        ps.setInt(4, p.getTotalQty());
        ps.setDouble(5, p.getTotalBerat());
        ps.setDouble(6, p.getTotalHargaPersen());
        ps.setDouble(7, p.getHargaEmas());
        ps.setDouble(8, p.getTotalPenjualan());
        ps.setString(9, p.getKodeUser());
        ps.setString(10, p.getStatus());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanCabangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_cabang_head set "
                + "tgl_penjualan=?, kode_cabang=?, total_qty=?, total_berat=?, total_harga_persen=?, "
                + "harga_emas=?, total_penjualan=?, kode_user=?, status=?,tgl_batal=?,user_batal=? "
                + "where no_penjualan_cabang=?");
        ps.setString(1, p.getTglPenjualan());
        ps.setString(2, p.getKodeCabang());
        ps.setInt(3, p.getTotalQty());
        ps.setDouble(4, p.getTotalBerat());
        ps.setDouble(5, p.getTotalHargaPersen());
        ps.setDouble(6, p.getHargaEmas());
        ps.setDouble(7, p.getTotalPenjualan());
        ps.setString(8, p.getKodeUser());
        ps.setString(9, p.getStatus());
        ps.setString(10, p.getTglBatal());
        ps.setString(11, p.getUserBatal());
        ps.setString(12, p.getNoPenjualanCabang());
        ps.executeUpdate();
    }
}
