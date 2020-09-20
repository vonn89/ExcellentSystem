/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.BebanPembelian;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class BebanPembelianDAO {
    public static List<BebanPembelian> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_beban_pembelian "
            + " where no_pembelian in (select no_pembelian from tt_pembelian_head "
            + " where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<BebanPembelian> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanPembelian b = new BebanPembelian();
            b.setNoPembelian(rs.getString(1));
            b.setKeterangan(rs.getString(2));
            b.setJumlahRp(rs.getDouble(3));
            b.setStatus(rs.getString(4));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static List<BebanPembelian> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_beban_pembelian where no_pembelian = ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<BebanPembelian> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanPembelian b = new BebanPembelian();
            b.setNoPembelian(rs.getString(1));
            b.setKeterangan(rs.getString(2));
            b.setJumlahRp(rs.getDouble(3));
            b.setStatus(rs.getString(4));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static void insert(Connection con, BebanPembelian b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_beban_pembelian values (?,?,?,?)");
        ps.setString(1, b.getNoPembelian());
        ps.setString(2, b.getKeterangan());
        ps.setDouble(3, b.getJumlahRp());
        ps.setString(4, b.getStatus());
        ps.executeUpdate();
    }
}
