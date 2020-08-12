/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Piutang;
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
public class PiutangDAO {
    public static List<Piutang> getAllByDate(Connection con, String tglMulai,String tglAkhir)throws Exception{
        List<Piutang> allPiutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_piutang where left(tgl_piutang,10) between ? and ? and status!='false'")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                allPiutang = new ArrayList<>();
                while(rs.next()){
                    Piutang piutang = new Piutang();
                    piutang.setNoPiutang(rs.getString(1));
                    piutang.setTglPiutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    piutang.setKategori(rs.getString(3));
                    piutang.setKeterangan(rs.getString(4));
                    piutang.setKategoriKeuangan(rs.getString(5));
                    piutang.setJumlahPiutang(rs.getDouble(6));
                    piutang.setPembayaran(rs.getDouble(7));
                    piutang.setSisaPiutang(rs.getDouble(8));
                    piutang.setJatuhTempo(rs.getString(9));
                    piutang.setKodeUser(rs.getString(10));
                    piutang.setStatus(rs.getString(11));
                    allPiutang.add(piutang);
                }
            }
        }
        return allPiutang;
    }
    public static List<Piutang> getAllByStatus(Connection con, String status)throws Exception{
        List<Piutang> allPiutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_piutang where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allPiutang = new ArrayList<>();
                while(rs.next()){
                    Piutang piutang = new Piutang();
                    piutang.setNoPiutang(rs.getString(1));
                    piutang.setTglPiutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    piutang.setKategori(rs.getString(3));
                    piutang.setKeterangan(rs.getString(4));
                    piutang.setKategoriKeuangan(rs.getString(5));
                    piutang.setJumlahPiutang(rs.getDouble(6));
                    piutang.setPembayaran(rs.getDouble(7));
                    piutang.setSisaPiutang(rs.getDouble(8));
                    piutang.setJatuhTempo(rs.getString(9));
                    piutang.setKodeUser(rs.getString(10));
                    piutang.setStatus(rs.getString(11));
                    allPiutang.add(piutang);
                }
            }
        }
        return allPiutang;
    }
    public static List<Piutang> getAllByTanggalAndKategori(Connection con, String tglMulai,String tglAkhir, String kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_piutang where left(tgl_piutang,10) between ? and ? "
                + " and kategori like ? and status!='false'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, kategori);
        ResultSet rs = ps.executeQuery();
        List<Piutang> listPiutang = new ArrayList<>();
        while(rs.next()){
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(rs.getString(1));
            piutang.setTglPiutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            piutang.setKategori(rs.getString(3));
            piutang.setKeterangan(rs.getString(4));
            piutang.setKategoriKeuangan(rs.getString(5));
            piutang.setJumlahPiutang(rs.getDouble(6));
            piutang.setPembayaran(rs.getDouble(7));
            piutang.setSisaPiutang(rs.getDouble(8));
            piutang.setJatuhTempo(rs.getString(9));
            piutang.setKodeUser(rs.getString(10));
            piutang.setStatus(rs.getString(11));
            listPiutang.add(piutang);
        }
        return listPiutang;
    }
    public static Piutang getByKategoriAndKeterangan(Connection con, String kategori, String keterangan)throws Exception{
        Piutang piutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_piutang where kategori=? and keterangan=? and status!='false'")) {
            ps.setString(1, kategori);
            ps.setString(2, keterangan);
            try (ResultSet rs = ps.executeQuery()) {
                piutang = null;
                if(rs.next()){
                    piutang = new Piutang();
                    piutang.setNoPiutang(rs.getString(1));
                    piutang.setTglPiutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    piutang.setKategori(rs.getString(3));
                    piutang.setKeterangan(rs.getString(4));
                    piutang.setKategoriKeuangan(rs.getString(5));
                    piutang.setJumlahPiutang(rs.getDouble(6));
                    piutang.setPembayaran(rs.getDouble(7));
                    piutang.setSisaPiutang(rs.getDouble(8));
                    piutang.setJatuhTempo(rs.getString(9));
                    piutang.setKodeUser(rs.getString(10));
                    piutang.setStatus(rs.getString(11));
                }
            }
        }
        return piutang;
    }
    public static Piutang get(Connection con, String noPiutang)throws Exception{
        Piutang piutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_piutang where no_piutang=?")) {
            ps.setString(1, noPiutang);
            try (ResultSet rs = ps.executeQuery()) {
                piutang = null;
                if(rs.next()){
                    piutang = new Piutang();
                    piutang.setNoPiutang(rs.getString(1));
                    piutang.setTglPiutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    piutang.setKategori(rs.getString(3));
                    piutang.setKeterangan(rs.getString(4));
                    piutang.setKategoriKeuangan(rs.getString(5));
                    piutang.setJumlahPiutang(rs.getDouble(6));
                    piutang.setPembayaran(rs.getDouble(7));
                    piutang.setSisaPiutang(rs.getDouble(8));
                    piutang.setJatuhTempo(rs.getString(9));
                    piutang.setKodeUser(rs.getString(10));
                    piutang.setStatus(rs.getString(11));
                }
            }
        }
        return piutang;
    }
    public static String getId(Connection con)throws Exception{
        String noPiutang;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_piutang,3)) from tm_piutang where mid(no_piutang,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noPiutang = "PT-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noPiutang = "PT-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noPiutang;
    }
    public static void insert(Connection con, Piutang piutang)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_piutang values(?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, piutang.getNoPiutang());
            ps.setString(2, piutang.getTglPiutang());
            ps.setString(3, piutang.getKategori());
            ps.setString(4, piutang.getKeterangan());
            ps.setString(5, piutang.getKategoriKeuangan());
            ps.setDouble(6, piutang.getJumlahPiutang());
            ps.setDouble(7, piutang.getPembayaran());
            ps.setDouble(8, piutang.getSisaPiutang());
            ps.setString(9, piutang.getJatuhTempo());
            ps.setString(10, piutang.getKodeUser());
            ps.setString(11, piutang.getStatus());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, Piutang piutang)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_piutang set pembayaran=?, sisa_piutang=?,jatuh_tempo=?, status=? where no_piutang=?")) {
            ps.setDouble(1, piutang.getPembayaran());
            ps.setDouble(2, piutang.getSisaPiutang());
            ps.setString(3, piutang.getJatuhTempo());
            ps.setString(4, piutang.getStatus());
            ps.setString(5, piutang.getNoPiutang());
            ps.executeUpdate();
        }
    }
}
