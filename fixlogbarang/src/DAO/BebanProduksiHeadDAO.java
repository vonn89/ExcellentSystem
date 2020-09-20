/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.BebanProduksiHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import test.Function;
import static test.Function.yymm;

/**
 *
 * @author Xtreme
 */
public class BebanProduksiHeadDAO {
    public static List<BebanProduksiHead> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_beban_produksi_head "
            + " where left(tgl_beban_produksi,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<BebanProduksiHead> allBeban = new ArrayList<>();
        while(rs.next()){
            BebanProduksiHead b = new BebanProduksiHead();
            b.setNoBebanProduksi(rs.getString(1));
            b.setTglBebanProduksi(rs.getString(2));
            b.setKeterangan(rs.getString(3));
            b.setTotalBebanProduksi(rs.getDouble(4));
            b.setTipeKeuangan(rs.getString(5));
            b.setKodeUser(rs.getString(6));
            b.setTglBatal(rs.getString(7));
            b.setUserBatal(rs.getString(8));
            b.setStatus(rs.getString(9));
            allBeban.add(b);
        }
        return allBeban;
    }
    public static BebanProduksiHead get(Connection con, String noBeban)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_beban_produksi_head where no_beban_produksi = ?");
        ps.setString(1, noBeban);
        ResultSet rs = ps.executeQuery();
        BebanProduksiHead b = null;
        while(rs.next()){
            b = new BebanProduksiHead();
            b.setNoBebanProduksi(rs.getString(1));
            b.setTglBebanProduksi(rs.getString(2));
            b.setKeterangan(rs.getString(3));
            b.setTotalBebanProduksi(rs.getDouble(4));
            b.setTipeKeuangan(rs.getString(5));
            b.setKodeUser(rs.getString(6));
            b.setTglBatal(rs.getString(7));
            b.setUserBatal(rs.getString(8));
            b.setStatus(rs.getString(9));
        }
        return b;
    }
    public static String getId(Connection con)throws Exception{
        Date serverDate = Function.getServerDate(con);
        PreparedStatement ps = con.prepareStatement("select max(right(no_beban_produksi,3)) from tt_beban_produksi_head "
                + " where mid(no_beban_produksi,4,4) = ?");
        ps.setString(1, yymm.format(serverDate));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "BP-"+yymm.format(serverDate)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "BP-"+yymm.format(serverDate)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, BebanProduksiHead b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_beban_produksi_head values (?,?,?,?,?,?,?,?,?)");
        ps.setString(1, b.getNoBebanProduksi());
        ps.setString(2, b.getTglBebanProduksi());
        ps.setString(3, b.getKeterangan());
        ps.setDouble(4, b.getTotalBebanProduksi());
        ps.setString(5, b.getTipeKeuangan());
        ps.setString(6, b.getKodeUser());
        ps.setString(7, b.getTglBatal());
        ps.setString(8, b.getUserBatal());
        ps.setString(9, b.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, BebanProduksiHead b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_beban_produksi_head set "
                + " tgl_beban_produksi = ?, keterangan = ?, total_beban_produksi = ?, tipe_keuangan = ?, "
                + " kode_user = ?, tgl_batal = ?, user_batal = ?, status = ? where no_beban_produksi = ?");
        ps.setString(1, b.getTglBebanProduksi());
        ps.setString(2, b.getKeterangan());
        ps.setDouble(3, b.getTotalBebanProduksi());
        ps.setString(4, b.getTipeKeuangan());
        ps.setString(5, b.getKodeUser());
        ps.setString(6, b.getTglBatal());
        ps.setString(7, b.getUserBatal());
        ps.setString(8, b.getStatus());
        ps.setString(9, b.getNoBebanProduksi());
        ps.executeUpdate();
    }
}
