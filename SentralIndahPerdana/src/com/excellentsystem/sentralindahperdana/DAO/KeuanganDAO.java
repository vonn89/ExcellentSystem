/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
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
    public static List<Keuangan> getAllByTanggal(Connection con, String tglMulai,String tglAkhir)throws Exception{
        List<Keuangan> allKeuangan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_keuangan where left(tgl_keuangan,10) between ? and ? ")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                allKeuangan = new ArrayList<>();
                while(rs.next()){
                    Keuangan keuangan = new Keuangan();
                    keuangan.setNoKeuangan(rs.getString(1));
                    keuangan.setTglKeuangan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    keuangan.setTipeKeuangan(rs.getString(3));
                    keuangan.setKategori(rs.getString(4));
                    keuangan.setKeterangan(rs.getString(5));
                    keuangan.setJumlahRp(rs.getDouble(6));
                    keuangan.setKodeUser(rs.getString(7));
                    allKeuangan.add(keuangan);
                }
            }
        }
        return allKeuangan;
    }
    public static List<Keuangan> getAllByTipeKeuanganAndTanggal(Connection con, String tipeKeuangan,String tglMulai,String tglAkhir)throws Exception{
        List<Keuangan> allKeuangan;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_keuangan where left(tgl_keuangan,10) between ? and ? and tipe_keuangan like ? order by tgl_keuangan")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, tipeKeuangan);
            try (ResultSet rs = ps.executeQuery()) {
                allKeuangan = new ArrayList<>();
                while(rs.next()){
                    Keuangan keuangan = new Keuangan();
                    keuangan.setNoKeuangan(rs.getString(1));
                    keuangan.setTglKeuangan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    keuangan.setTipeKeuangan(rs.getString(3));
                    keuangan.setKategori(rs.getString(4));
                    keuangan.setKeterangan(rs.getString(5));
                    keuangan.setJumlahRp(rs.getDouble(6));
                    keuangan.setKodeUser(rs.getString(7));
                    allKeuangan.add(keuangan);
                }
            }
        }
        return allKeuangan;
    }
    public static Double getSaldoAkhir(Connection con, String tanggal,String tipeKeuangan)throws Exception{
        double saldoAwal = 0;
        try (PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) from tt_keuangan where left(tgl_keuangan,10) <= ? and tipe_keuangan like ? ")) {
            ps.setString(1, tanggal);
            ps.setString(2, tipeKeuangan);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    saldoAwal = rs.getDouble(1);
            }
        }
        return saldoAwal;
    }
    public static Double getSaldoAwal(Connection con, String tanggal,String tipeKeuangan)throws Exception{
        double saldoAwal = 0;
        try (PreparedStatement ps = con.prepareStatement("select sum(jumlah_rp) from tt_keuangan where left(tgl_keuangan,10) < ? and tipe_keuangan like ? ")) {
            ps.setString(1, tanggal);
            ps.setString(2, tipeKeuangan);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    saldoAwal = rs.getDouble(1);
            }
        }
        return saldoAwal;
    }
    public static String getId(Connection con)throws Exception{
        String noKeuangan;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan where mid(no_keuangan,4,6)=? ")) {
            DateFormat cd = new SimpleDateFormat("yyMMdd");
            DecimalFormat df = new DecimalFormat("0000");
            Date date = new Date();
            ps.setString(1, cd.format(date));
            noKeuangan = "KK-"+cd.format(date)+"-"+df.format(1);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    noKeuangan = "KK-"+cd.format(date)+"-" +df.format(rs.getInt(1)+1);
            }
        }
        return noKeuangan;
    }
    public static void insert(Connection con, Keuangan keuangan)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_keuangan values (?,?,?,?,?,?,?)")) {
            ps.setString(1, keuangan.getNoKeuangan());
            ps.setString(2, keuangan.getTglKeuangan());
            ps.setString(3, keuangan.getTipeKeuangan());
            ps.setString(4, keuangan.getKategori());
            ps.setString(5, keuangan.getKeterangan());
            ps.setDouble(6, keuangan.getJumlahRp());
            ps.setString(7, keuangan.getKodeUser());
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, String noKeuangan)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tt_keuangan where no_keuangan=?")) {
            ps.setString(1, noKeuangan);
            ps.executeUpdate();
        }
    }
    public static void delete(Connection con, String tipeKeuangan, String kategori, String keterangan)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("delete from tt_keuangan "
                + "where tipe_keuangan=? and kategori=? and keterangan=?")) {
            ps.setString(1, tipeKeuangan);
            ps.setString(2, kategori);
            ps.setString(3, keterangan);
            ps.executeUpdate();
        }
    }
}
