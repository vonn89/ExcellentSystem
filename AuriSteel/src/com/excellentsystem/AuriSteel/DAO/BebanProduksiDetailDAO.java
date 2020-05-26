/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.BebanProduksiDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class BebanProduksiDetailDAO {
    public static List<BebanProduksiDetail> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_beban_produksi_detail "
            + " where no_beban_produksi in (select no_beban_produksi from tt_beban_produksi_head "
            + " where left(tgl_beban_produksi,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<BebanProduksiDetail> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanProduksiDetail b = new BebanProduksiDetail();
            b.setNoBebanProduksi(rs.getString(1));
            b.setNoProduksi(rs.getString(2));
            b.setJumlahRp(rs.getDouble(3));
            b.setStatus(rs.getString(4));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static List<BebanProduksiDetail> getAllByNoBeban(Connection con, String noBeban)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_beban_produksi_detail where no_beban_produksi = ?");
        ps.setString(1, noBeban);
        ResultSet rs = ps.executeQuery();
        List<BebanProduksiDetail> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanProduksiDetail b = new BebanProduksiDetail();
            b.setNoBebanProduksi(rs.getString(1));
            b.setNoProduksi(rs.getString(2));
            b.setJumlahRp(rs.getDouble(3));
            b.setStatus(rs.getString(4));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static List<BebanProduksiDetail> getAllNoProduksiAndStatus(Connection con, String noProduksi, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_beban_produksi_detail where no_produksi = ? and status = ?");
        ps.setString(1, noProduksi);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<BebanProduksiDetail> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanProduksiDetail b = new BebanProduksiDetail();
            b.setNoBebanProduksi(rs.getString(1));
            b.setNoProduksi(rs.getString(2));
            b.setJumlahRp(rs.getDouble(3));
            b.setStatus(rs.getString(4));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static void insert(Connection con, BebanProduksiDetail b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_beban_produksi_detail values (?,?,?,?)");
        ps.setString(1, b.getNoBebanProduksi());
        ps.setString(2, b.getNoProduksi());
        ps.setDouble(3, b.getJumlahRp());
        ps.setString(4, b.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, BebanProduksiDetail b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_beban_produksi_detail set "
                + " jumlah_rp = ?, status = ? where no_beban_produksi = ? and no_produksi = ?");
        ps.setDouble(1, b.getJumlahRp());
        ps.setString(2, b.getStatus());
        ps.setString(3, b.getNoBebanProduksi());
        ps.setString(4, b.getNoProduksi());
        ps.executeUpdate();
    }
}
