/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Function;
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
            p.setKodeGudang(rs.getString(3));
            p.setJenisProduksi(rs.getString(4));
            p.setMaterialCost(rs.getDouble(5));
            p.setBiayaProduksi(rs.getDouble(6));
            p.setCatatan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setTglBatal(rs.getDate(9).toString()+" "+rs.getTime(9).toString());
            p.setUserBatal(rs.getString(10));
            p.setStatus(rs.getString(11));
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
            p.setKodeGudang(rs.getString(3));
            p.setJenisProduksi(rs.getString(4));
            p.setMaterialCost(rs.getDouble(5));
            p.setBiayaProduksi(rs.getDouble(6));
            p.setCatatan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setTglBatal(rs.getDate(9).toString()+" "+rs.getTime(9).toString());
            p.setUserBatal(rs.getString(10));
            p.setStatus(rs.getString(11));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        Date serverDate = Function.getServerDate(con);
        PreparedStatement ps = con.prepareStatement("select max(right(kode_produksi,3)) from tt_produksi_head "
                + " where mid(kode_produksi,4,4) = ?");
        ps.setString(1, yymm.format(serverDate));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PR-"+yymm.format(serverDate)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PR-"+yymm.format(serverDate)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, ProduksiHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_produksi_head values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getKodeProduksi());
        ps.setString(2, p.getTglProduksi());
        ps.setString(3, p.getKodeGudang());
        ps.setString(4, p.getJenisProduksi());
        ps.setDouble(5, p.getMaterialCost());
        ps.setDouble(6, p.getBiayaProduksi());
        ps.setString(7, p.getCatatan());
        ps.setString(8, p.getKodeUser());
        ps.setString(9, p.getTglBatal());
        ps.setString(10, p.getUserBatal());
        ps.setString(11, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, ProduksiHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_produksi_head set "
                + " tgl_produksi=?, kode_gudang=?, jenis_produksi=?, material_cost=?, beban_produksi=?, "
                + " catatan=?, kode_user=?, tgl_batal=?, user_batal=?, status=? "
                + " where kode_produksi=? ");
        ps.setString(1, p.getTglProduksi());
        ps.setString(2, p.getKodeGudang());
        ps.setString(3, p.getJenisProduksi());
        ps.setDouble(4, p.getMaterialCost());
        ps.setDouble(5, p.getBiayaProduksi());
        ps.setString(6, p.getCatatan());
        ps.setString(7, p.getKodeUser());
        ps.setString(8, p.getTglBatal());
        ps.setString(9, p.getUserBatal());
        ps.setString(10, p.getStatus());
        ps.setString(11, p.getKodeProduksi());
        ps.executeUpdate();
    }
}
