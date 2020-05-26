/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PindahBarangHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PindahBarangHeadDAO {
    
    public static List<PindahBarangHead> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pindah_barang_head where left(tgl_pindah,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PindahBarangHead> allPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PindahBarangHead p = new PindahBarangHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setGudangAsal(rs.getString(3));
            p.setGudangTujuan(rs.getString(4));
            p.setSupir(rs.getString(5));
            p.setCatatan(rs.getString(6));
            p.setKodeUser(rs.getString(7));
            p.setTglBatal(rs.getDate(8).toString()+" "+rs.getTime(8).toString());
            p.setUserBatal(rs.getString(9));
            p.setStatus(rs.getString(10));
            allPenjualan.add(p);
        }
        return allPenjualan;
        
    }
    public static PindahBarangHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_barang_head where no_pindah = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PindahBarangHead p = null;
        while(rs.next()){
            p = new PindahBarangHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setGudangAsal(rs.getString(3));
            p.setGudangTujuan(rs.getString(4));
            p.setSupir(rs.getString(5));
            p.setCatatan(rs.getString(6));
            p.setKodeUser(rs.getString(7));
            p.setTglBatal(rs.getDate(8).toString()+" "+rs.getTime(8).toString());
            p.setUserBatal(rs.getString(9));
            p.setStatus(rs.getString(10));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        Date serverDate = Function.getServerDate(con);
        PreparedStatement ps = con.prepareStatement("select max(right(no_pindah,3)) from tt_pindah_barang_head "
                + " where mid(no_pindah,4,4) = ?");
        ps.setString(1, yymm.format(serverDate));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PG-"+yymm.format(serverDate)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PG-"+yymm.format(serverDate)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PindahBarangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_barang_head values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPindah());
        ps.setString(2, p.getTglPindah());
        ps.setString(3, p.getGudangAsal());
        ps.setString(4, p.getGudangTujuan());
        ps.setString(5, p.getSupir());
        ps.setString(6, p.getCatatan());
        ps.setString(7, p.getKodeUser());
        ps.setString(8, p.getTglBatal());
        ps.setString(9, p.getUserBatal());
        ps.setString(10, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PindahBarangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pindah_barang_head set "
                + " tgl_pindah=?, gudang_asal=?, gudang_tujuan=?, supir=?, "
                + " catatan=?, kode_user=?, tgl_batal=?, user_batal=?, status=? "
                + " where no_pindah=?");
        ps.setString(1, p.getTglPindah());
        ps.setString(2, p.getGudangAsal());
        ps.setString(3, p.getGudangTujuan());
        ps.setString(4, p.getSupir());
        ps.setString(5, p.getCatatan());
        ps.setString(6, p.getKodeUser());
        ps.setString(7, p.getTglBatal());
        ps.setString(8, p.getUserBatal());
        ps.setString(9, p.getStatus());
        ps.setString(10, p.getNoPindah());
        ps.executeUpdate();
    }
}
