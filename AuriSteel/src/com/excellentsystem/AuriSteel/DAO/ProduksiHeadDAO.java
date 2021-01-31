/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class ProduksiHeadDAO {
    public static List<ProduksiHead> getAllByDateAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_head where left(tgl_produksi,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<ProduksiHead> allProduksi = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            ProduksiHead p = new ProduksiHead();
            p.setKodeProduksi(rs.getString(1));
            p.setTglProduksi(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setTglMulai(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            p.setUserMulai(rs.getString(4));
            p.setTglSelesai(rs.getDate(5).toString()+" "+rs.getTime(5).toString());
            p.setUserSelesai(rs.getString(6));
            p.setKodeGudang(rs.getString(7));
            p.setJenisProduksi(rs.getString(8));
            p.setKodeMesin(rs.getString(9));
            p.setMaterialCost(rs.getDouble(10));
            p.setBiayaProduksi(rs.getDouble(11));
            p.setCatatan(rs.getString(12));
            p.setKodeUser(rs.getString(13));
            p.setTglBatal(rs.getDate(14).toString()+" "+rs.getTime(14).toString());
            p.setUserBatal(rs.getString(15));
            p.setStatus(rs.getString(16));
            allProduksi.add(p);
        }
        return allProduksi;
        
    }
    public static List<ProduksiHead> getAllByTglMulaiAndJenisProduksiAndStatus(
            Connection con, String tglMulai, String tglAkhir, String jenisProduksi, String status)throws Exception{
        String sql = "select * from tt_produksi_head where left(tgl_mulai,10) between ? and ? ";
        if(!jenisProduksi.equals("%"))
            sql = sql + " and jenis_produksi = '"+jenisProduksi+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<ProduksiHead> allProduksi = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            ProduksiHead p = new ProduksiHead();
            p.setKodeProduksi(rs.getString(1));
            p.setTglProduksi(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setTglMulai(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            p.setUserMulai(rs.getString(4));
            p.setTglSelesai(rs.getDate(5).toString()+" "+rs.getTime(5).toString());
            p.setUserSelesai(rs.getString(6));
            p.setKodeGudang(rs.getString(7));
            p.setJenisProduksi(rs.getString(8));
            p.setKodeMesin(rs.getString(9));
            p.setMaterialCost(rs.getDouble(10));
            p.setBiayaProduksi(rs.getDouble(11));
            p.setCatatan(rs.getString(12));
            p.setKodeUser(rs.getString(13));
            p.setTglBatal(rs.getDate(14).toString()+" "+rs.getTime(14).toString());
            p.setUserBatal(rs.getString(15));
            p.setStatus(rs.getString(16));
            allProduksi.add(p);
        }
        return allProduksi;
        
    }
    public static ProduksiHead get(Connection con, String kodeProduksi)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_produksi_head "
                + "where kode_produksi = ?");
        ps.setString(1, kodeProduksi);
        ResultSet rs = ps.executeQuery();
        ProduksiHead p = null;
        while(rs.next()){
            p = new ProduksiHead();
            p.setKodeProduksi(rs.getString(1));
            p.setTglProduksi(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setTglMulai(rs.getDate(3).toString()+" "+rs.getTime(3).toString());
            p.setUserMulai(rs.getString(4));
            p.setTglSelesai(rs.getDate(5).toString()+" "+rs.getTime(5).toString());
            p.setUserSelesai(rs.getString(6));
            p.setKodeGudang(rs.getString(7));
            p.setJenisProduksi(rs.getString(8));
            p.setKodeMesin(rs.getString(9));
            p.setMaterialCost(rs.getDouble(10));
            p.setBiayaProduksi(rs.getDouble(11));
            p.setCatatan(rs.getString(12));
            p.setKodeUser(rs.getString(13));
            p.setTglBatal(rs.getDate(14).toString()+" "+rs.getTime(14).toString());
            p.setUserBatal(rs.getString(15));
            p.setStatus(rs.getString(16));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_produksi,3)) from tt_produksi_head "
                + " where mid(kode_produksi,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PR-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PR-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, ProduksiHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_produksi_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodeProduksi());
        ps.setString(2, p.getTglProduksi());
        ps.setString(3, p.getTglMulai());
        ps.setString(4, p.getUserMulai());
        ps.setString(5, p.getTglSelesai());
        ps.setString(6, p.getUserSelesai());
        ps.setString(7, p.getKodeGudang());
        ps.setString(8, p.getJenisProduksi());
        ps.setString(9, p.getKodeMesin());
        ps.setDouble(10, p.getMaterialCost());
        ps.setDouble(11, p.getBiayaProduksi());
        ps.setString(12, p.getCatatan());
        ps.setString(13, p.getKodeUser());
        ps.setString(14, p.getTglBatal());
        ps.setString(15, p.getUserBatal());
        ps.setString(16, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, ProduksiHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_produksi_head set "
                + " tgl_produksi=?, tgl_mulai=?, user_mulai=?, tgl_selesai=?, user_selesai=?, "
                + " kode_gudang=?, jenis_produksi=?, kode_mesin=?, material_cost=?, beban_produksi=?, "
                + " catatan=?, kode_user=?, tgl_batal=?, user_batal=?, status=? "
                + " where kode_produksi=? ");
        ps.setString(1, p.getTglProduksi());
        ps.setString(2, p.getTglMulai());
        ps.setString(3, p.getUserMulai());
        ps.setString(4, p.getTglSelesai());
        ps.setString(5, p.getUserSelesai());
        ps.setString(6, p.getKodeGudang());
        ps.setString(7, p.getJenisProduksi());
        ps.setString(8, p.getKodeMesin());
        ps.setDouble(9, p.getMaterialCost());
        ps.setDouble(10, p.getBiayaProduksi());
        ps.setString(11, p.getCatatan());
        ps.setString(12, p.getKodeUser());
        ps.setString(13, p.getTglBatal());
        ps.setString(14, p.getUserBatal());
        ps.setString(15, p.getStatus());
        ps.setString(16, p.getKodeProduksi());
        ps.executeUpdate();
    }
}
