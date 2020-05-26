/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Main.yymmdd;
import com.excellentsystem.AuriSteel.Model.PenyesuaianStokBarang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PenyesuaianStokBarangDAO {
    public static PenyesuaianStokBarang get(Connection con, String noPenyesuaian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penyesuaian_stok_barang "
                + "where no_penyesuaian = ?");
        ps.setString(1, noPenyesuaian);
        PenyesuaianStokBarang p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PenyesuaianStokBarang();
            p.setNoPenyesuaian(rs.getString(1));
            p.setTglPenyesuaian(rs.getString(2));
            p.setKodeBarang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setQty(rs.getDouble(5));
            p.setNilai(rs.getDouble(6));
            p.setCatatan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setStatus(rs.getString(9));
        }
        return p;
    }
    public static List<PenyesuaianStokBarang> getAllByDateAndBarangAndGudang(
            Connection con, String tglMulai, String tglAkhir, String barang, String gudang)throws Exception{
        String sql = "select * from tt_penyesuaian_stok_barang where left(tgl_penyesuaian,10) between ? and ? ";
        if(!barang.equals("%"))
            sql = sql + " and kode_barang = '"+barang+"' ";
        if(!gudang.equals("%"))
            sql = sql + " and kode_gudang = '"+gudang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PenyesuaianStokBarang> listPenyesuaianStok = new ArrayList<>();
        while(rs.next()){
            PenyesuaianStokBarang p = new PenyesuaianStokBarang();
            p.setNoPenyesuaian(rs.getString(1));
            p.setTglPenyesuaian(rs.getString(2));
            p.setKodeBarang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setQty(rs.getDouble(5));
            p.setNilai(rs.getDouble(6));
            p.setCatatan(rs.getString(7));
            p.setKodeUser(rs.getString(8));
            p.setStatus(rs.getString(9));
            listPenyesuaianStok.add(p);
        }
        return listPenyesuaianStok;
    }
    public static void insert(Connection con, PenyesuaianStokBarang p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penyesuaian_stok_barang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenyesuaian());
        ps.setString(2, p.getTglPenyesuaian());
        ps.setString(3, p.getKodeBarang());
        ps.setString(4, p.getKodeGudang());
        ps.setDouble(5, p.getQty());
        ps.setDouble(6, p.getNilai());
        ps.setString(7, p.getCatatan());
        ps.setString(8, p.getKodeUser());
        ps.setString(9, p.getStatus());
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        Date serverDate = Function.getServerDate(con);
        PreparedStatement ps = con.prepareStatement("select max(right(no_penyesuaian,3)) from tt_penyesuaian_stok_barang "
                + " where mid(no_penyesuaian,4,6) = ?");
        ps.setString(1, yymmdd.format(serverDate));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "SR-"+yymmdd.format(serverDate)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "SR-"+yymmdd.format(serverDate)+new DecimalFormat("000").format(1);
    }
}
