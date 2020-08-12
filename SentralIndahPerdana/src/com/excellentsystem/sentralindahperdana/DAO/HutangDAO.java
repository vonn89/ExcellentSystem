/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Hutang;
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
public class HutangDAO {
    public static List<Hutang> getAllByTanggal(Connection con, String tglMulai,String tglAkhir)throws Exception{
        List<Hutang> allHutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_hutang "
                + "where left(tgl_hutang,10) between ? and ? and status!='false'")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                allHutang = new ArrayList<>();
                while(rs.next()){
                    Hutang hutang = new Hutang();
                    hutang.setNoHutang(rs.getString(1));
                    hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    hutang.setKategori(rs.getString(3));
                    hutang.setKeterangan(rs.getString(4));
                    hutang.setKategoriKeuangan(rs.getString(5));
                    hutang.setJumlahHutang(rs.getDouble(6));
                    hutang.setPembayaran(rs.getDouble(7));
                    hutang.setSisaHutang(rs.getDouble(8));
                    hutang.setJatuhTempo(rs.getString(9));
                    hutang.setKodeUser(rs.getString(10));
                    hutang.setStatus(rs.getString(11));
                    allHutang.add(hutang);
                }
            }
        }
        return allHutang;
    }
    public static List<Hutang> getAllByStatus(Connection con, String status)throws Exception{
        List<Hutang> allHutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_hutang where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allHutang = new ArrayList<>();
                while(rs.next()){
                    Hutang hutang = new Hutang();
                    hutang.setNoHutang(rs.getString(1));
                    hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    hutang.setKategori(rs.getString(3));
                    hutang.setKeterangan(rs.getString(4));
                    hutang.setKategoriKeuangan(rs.getString(5));
                    hutang.setJumlahHutang(rs.getDouble(6));
                    hutang.setPembayaran(rs.getDouble(7));
                    hutang.setSisaHutang(rs.getDouble(8));
                    hutang.setJatuhTempo(rs.getString(9));
                    hutang.setKodeUser(rs.getString(10));
                    hutang.setStatus(rs.getString(11));
                    allHutang.add(hutang);
                }
            }
        }
        return allHutang;
    }
    public static List<Hutang> getAllByTanggalAndKategori(Connection con, String tglMulai, String tglAkhir, String kategori)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang "
                + "where left(tgl_hutang,10) between ? and ? and kategori like ? and status!='false'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, kategori);
        ResultSet rs = ps.executeQuery();
        List<Hutang> listHutang = new ArrayList<>();
        while(rs.next()){
            Hutang hutang = new Hutang();
            hutang.setNoHutang(rs.getString(1));
            hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            hutang.setKategori(rs.getString(3));
            hutang.setKeterangan(rs.getString(4));
            hutang.setKategoriKeuangan(rs.getString(5));
            hutang.setJumlahHutang(rs.getDouble(6));
            hutang.setPembayaran(rs.getDouble(7));
            hutang.setSisaHutang(rs.getDouble(8));
            hutang.setJatuhTempo(rs.getString(9));
            hutang.setKodeUser(rs.getString(10));
            hutang.setStatus(rs.getString(11));
            listHutang.add(hutang);
        }
        return listHutang;
    }
    public static List<Hutang> getAllByKategoriAndKeterangan(Connection con, String kategori, String keterangan)throws Exception{
        List<Hutang> allHutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_hutang "
                + "where kategori like ? and keterangan like ? and status !='false'")) {
            ps.setString(1, kategori);
            ps.setString(2, keterangan);
            try (ResultSet rs = ps.executeQuery()) {
                allHutang = new ArrayList<>();
                while(rs.next()){
                    Hutang hutang = new Hutang();
                    hutang.setNoHutang(rs.getString(1));
                    hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    hutang.setKategori(rs.getString(3));
                    hutang.setKeterangan(rs.getString(4));
                    hutang.setKategoriKeuangan(rs.getString(5));
                    hutang.setJumlahHutang(rs.getDouble(6));
                    hutang.setPembayaran(rs.getDouble(7));
                    hutang.setSisaHutang(rs.getDouble(8));
                    hutang.setJatuhTempo(rs.getString(9));
                    hutang.setKodeUser(rs.getString(10));
                    hutang.setStatus(rs.getString(11));
                    allHutang.add(hutang);
                }
            }
        }
        return allHutang;
    }
    public static Hutang getByKategoriAndKeterangan(Connection con, String kategori, String keterangan)throws Exception{
        Hutang hutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_hutang "
                + "where kategori like ? and keterangan like ? and status!='false'")) {
            ps.setString(1, kategori);
            ps.setString(2, keterangan);
            try (ResultSet rs = ps.executeQuery()) {
                hutang = null;
                if(rs.next()){
                    hutang = new Hutang();
                    hutang.setNoHutang(rs.getString(1));
                    hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    hutang.setKategori(rs.getString(3));
                    hutang.setKeterangan(rs.getString(4));
                    hutang.setKategoriKeuangan(rs.getString(5));
                    hutang.setJumlahHutang(rs.getDouble(6));
                    hutang.setPembayaran(rs.getDouble(7));
                    hutang.setSisaHutang(rs.getDouble(8));
                    hutang.setJatuhTempo(rs.getString(9));
                    hutang.setKodeUser(rs.getString(10));
                    hutang.setStatus(rs.getString(11));
                }
            }
        }
        return hutang;
    }
    public static Hutang get(Connection con, String noHutang)throws Exception{
        Hutang hutang;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_hutang where no_hutang=?")) {
            ps.setString(1, noHutang);
            try (ResultSet rs = ps.executeQuery()) {
                hutang = null;
                if(rs.next()){
                    hutang = new Hutang();
                    hutang.setNoHutang(rs.getString(1));
                    hutang.setTglHutang(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    hutang.setKategori(rs.getString(3));
                    hutang.setKeterangan(rs.getString(4));
                    hutang.setKategoriKeuangan(rs.getString(5));
                    hutang.setJumlahHutang(rs.getDouble(6));
                    hutang.setPembayaran(rs.getDouble(7));
                    hutang.setSisaHutang(rs.getDouble(8));
                    hutang.setJatuhTempo(rs.getString(9));
                    hutang.setKodeUser(rs.getString(10));
                    hutang.setStatus(rs.getString(11));
                }
            }
        }
        return hutang;
    }
    public static String getId(Connection con)throws Exception{
        String noHutang;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_hutang,3)) from tm_hutang where mid(no_hutang,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noHutang = "HT-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noHutang = "HT-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noHutang;
    }
    public static void insert(Connection con, Hutang hutang)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_hutang values(?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, hutang.getNoHutang());
            ps.setString(2, hutang.getTglHutang());
            ps.setString(3, hutang.getKategori());
            ps.setString(4, hutang.getKeterangan());
            ps.setString(5, hutang.getKategoriKeuangan());
            ps.setDouble(6, hutang.getJumlahHutang());
            ps.setDouble(7, hutang.getPembayaran());
            ps.setDouble(8, hutang.getSisaHutang());
            ps.setString(9, hutang.getJatuhTempo());
            ps.setString(10, hutang.getKodeUser());
            ps.setString(11, hutang.getStatus());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, Hutang hutang)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_hutang set pembayaran=?, sisa_hutang=?,jatuh_tempo=?, status=? where no_hutang=?")) {
            ps.setDouble(1, hutang.getPembayaran());
            ps.setDouble(2, hutang.getSisaHutang());
            ps.setString(3, hutang.getJatuhTempo());
            ps.setString(4, hutang.getStatus());
            ps.setString(5, hutang.getNoHutang());
            ps.executeUpdate();
        }
    }
}
