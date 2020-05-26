/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
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
public class KeuanganDAO {
    public static Double getSaldoAwalByDateAndTipeKeuangan(Connection con, String tanggal, String tipeKeuangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) " +
            " from tt_keuangan where left(tgl_keuangan,10) < ? and tipe_keuangan = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, tipeKeuangan);
        ResultSet rs = ps.executeQuery();
        double saldoAwal = 0;
        if(rs.next())
            saldoAwal = rs.getDouble(1);
        return saldoAwal;
    }
    public static Double getSaldoAkhirByDateAndTipeKeuangan(Connection con, String tanggal, String tipeKeuangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) " +
            " from tt_keuangan where left(tgl_keuangan,10) <= ?  and tipe_keuangan = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, tipeKeuangan);
        ResultSet rs = ps.executeQuery();
        double saldoAkhir = 0;
        if(rs.next())
            saldoAkhir = rs.getDouble(1);
        return saldoAkhir;
    }
    public static Keuangan get(Connection con,String tipeKeuangan, String kategori, String kodeProperty, String deskripsi)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan where tipe_keuangan=? and kategori=? and kode_property=? and deskripsi=?");
        ps.setString(1, tipeKeuangan);
        ps.setString(2, kategori);
        ps.setString(3, kodeProperty);
        ps.setString(4, deskripsi);
        ResultSet rs = ps.executeQuery();
        Keuangan k = null;
        while(rs.next()){
            k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setTipeKeuangan(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setKodeProperty(rs.getString(5));
            k.setDeskripsi(rs.getString(6));
            k.setJumlahRp(rs.getDouble(7));
            k.setKodeUser(rs.getString(8));
        }
        return k;
    }
    public static List<Keuangan> getAllByKodeProperty(Connection con,String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan where kode_property=?");
        ps.setString(1, kodeProperty);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setTipeKeuangan(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setKodeProperty(rs.getString(5));
            k.setDeskripsi(rs.getString(6));
            k.setJumlahRp(rs.getDouble(7));
            k.setKodeUser(rs.getString(8));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static List<Keuangan> getAllByTipeKeuanganAndDate(
            Connection con, String tipeKeuangan, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_keuangan "
                + " where left(tgl_keuangan,10) between ? and ? and tipe_keuangan = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, tipeKeuangan);
        ResultSet rs = ps.executeQuery();
        List<Keuangan> allKeuangan = new ArrayList<>();
        while(rs.next()){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setTipeKeuangan(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setKodeProperty(rs.getString(5));
            k.setDeskripsi(rs.getString(6));
            k.setJumlahRp(rs.getDouble(7));
            k.setKodeUser(rs.getString(8));
            allKeuangan.add(k);
        }
        return allKeuangan;
    }
    public static void updateDeskripsi(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan set "
                + " deskripsi=? "
                + " where no_keuangan=?");
        ps.setString(1, k.getDeskripsi());
        ps.setString(2, k.getNoKeuangan());
        ps.executeUpdate();
    }
    public static void update(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan set "
                + " tgl_keuangan = ?, deskripsi = ?, jumlah_rp = ?, kode_user = ? "
                + " where no_keuangan = ? and tipe_keuangan = ? and kategori = ? and kode_property = ?");
        ps.setString(1, k.getTglKeuangan());
        ps.setString(2, k.getDeskripsi());
        ps.setDouble(3, k.getJumlahRp());
        ps.setString(4, k.getKodeUser());
        ps.setString(5, k.getNoKeuangan());
        ps.setString(6, k.getTipeKeuangan());
        ps.setString(7, k.getKategori());
        ps.setString(8, k.getKodeProperty());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan values (?,?,?,?,?,?,?,?)");
        ps.setString(1, k.getNoKeuangan());
        ps.setString(2, k.getTglKeuangan());
        ps.setString(3, k.getTipeKeuangan());
        ps.setString(4, k.getKategori());
        ps.setString(5, k.getKodeProperty());
        ps.setString(6, k.getDeskripsi());
        ps.setDouble(7, k.getJumlahRp());
        ps.setString(8, k.getKodeUser());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Keuangan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan "
                + " where no_keuangan=? and tgl_keuangan=? and tipe_keuangan=? and kategori=? and "
                + " kode_property=? and deskripsi=? and jumlah_rp=? and kode_user=?");
        ps.setString(1, k.getNoKeuangan());
        ps.setString(2, k.getTglKeuangan());
        ps.setString(3, k.getTipeKeuangan());
        ps.setString(4, k.getKategori());
        ps.setString(5, k.getKodeProperty());
        ps.setString(6, k.getDeskripsi());
        ps.setDouble(7, k.getJumlahRp());
        ps.setString(8, k.getKodeUser());
        ps.executeUpdate();
    }
    public static void deleteAllByNoKeuangan(Connection con, String noKeuangan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan where no_keuangan=?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan "
                + " where mid(no_keuangan,4,6)='"+new SimpleDateFormat("yyMMdd").format(new Date())+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "KK-"+new SimpleDateFormat("yyMMdd").format(new Date())+"-" + new DecimalFormat("0000").format(rs.getInt(1) + 1);
        else 
            return "KK-"+new SimpleDateFormat("yyMMdd").format(new Date())+"-0001";
    }
    public static String getIdByDate(Connection con, Date date)throws Exception{
        PreparedStatement ps =  con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan "
                + " where mid(no_keuangan,4,6)='"+new SimpleDateFormat("yyMMdd").format(date)+"'");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) 
            return "KK-"+new SimpleDateFormat("yyMMdd").format(date)+"-" + new DecimalFormat("0000").format(rs.getInt(1) + 1);
        else 
            return "KK-"+new SimpleDateFormat("yyMMdd").format(date)+"-0001";
    }
}
