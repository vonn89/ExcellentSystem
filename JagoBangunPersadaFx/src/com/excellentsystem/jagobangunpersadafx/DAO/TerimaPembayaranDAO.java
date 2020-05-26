/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.TerimaPembayaran;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class TerimaPembayaranDAO {
    public static List<TerimaPembayaran> getAllByDateAndStatus(Connection con, String tglAwal, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_terima_pembayaran where left(tgl_terima,10) between ? and ? and status=?");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<TerimaPembayaran> allTerimaPembayaran = new ArrayList<>();
        while(rs.next()){
            TerimaPembayaran t = new TerimaPembayaran();
            t.setNoTerimaPembayaran(rs.getString(1));
            t.setTglTerima(rs.getString(2));
            t.setNoPiutang(rs.getString(3));
            t.setJumlahPembayaran(rs.getDouble(4));
            t.setTipeKeuangan(rs.getString(5));
            t.setCatatan(rs.getString(6));
            t.setKodeUser(rs.getString(7));
            t.setStatus(rs.getString(8));
            t.setTglBatal(rs.getString(9));
            t.setUserBatal(rs.getString(10));
            allTerimaPembayaran.add(t);
        }
        return allTerimaPembayaran;
    }
    
    public static List<TerimaPembayaran> getAllByNoPiutang(Connection con, String noPiutang, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_terima_pembayaran where no_piutang=? and status=?");
        ps.setString(1, noPiutang);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<TerimaPembayaran> allTerimaPembayaran = new ArrayList<>();
        while(rs.next()){
            TerimaPembayaran t = new TerimaPembayaran();
            t.setNoTerimaPembayaran(rs.getString(1));
            t.setTglTerima(rs.getString(2));
            t.setNoPiutang(rs.getString(3));
            t.setJumlahPembayaran(rs.getDouble(4));
            t.setTipeKeuangan(rs.getString(5));
            t.setCatatan(rs.getString(6));
            t.setKodeUser(rs.getString(7));
            t.setStatus(rs.getString(8));
            t.setTglBatal(rs.getString(9));
            t.setUserBatal(rs.getString(10));
            allTerimaPembayaran.add(t);
        }
        return allTerimaPembayaran;
    }
    
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_terima_pembayaran,3)) from tt_terima_pembayaran"
                + " where mid(no_terima_pembayaran,4,4) = ?");
        ps.setString(1, new SimpleDateFormat("yyMM").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "TP-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "TP-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, TerimaPembayaran t)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_terima_pembayaran values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, t.getNoTerimaPembayaran());
        ps.setString(2, t.getTglTerima());
        ps.setString(3, t.getNoPiutang());
        ps.setDouble(4, t.getJumlahPembayaran());
        ps.setString(5, t.getTipeKeuangan());
        ps.setString(6, t.getCatatan());
        ps.setString(7, t.getKodeUser());
        ps.setString(8, t.getStatus());
        ps.setString(9, t.getTglBatal());
        ps.setString(10, t.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, TerimaPembayaran t)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_terima_pembayaran set "
                + " tgl_terima=?, no_piutang=?, jumlah_pembayaran=?, tipe_keuangan=?, catatan=?, kode_user=?, "
                + " status=?, tgl_batal=?, user_batal=? where no_terima_pembayaran=?");
        ps.setString(1, t.getTglTerima());
        ps.setString(2, t.getNoPiutang());
        ps.setDouble(3, t.getJumlahPembayaran());
        ps.setString(4, t.getTipeKeuangan());
        ps.setString(5, t.getCatatan());
        ps.setString(6, t.getKodeUser());
        ps.setString(7, t.getStatus());
        ps.setString(8, t.getTglBatal());
        ps.setString(9, t.getUserBatal());
        ps.setString(10, t.getNoTerimaPembayaran());
        ps.executeUpdate();
    }
}
