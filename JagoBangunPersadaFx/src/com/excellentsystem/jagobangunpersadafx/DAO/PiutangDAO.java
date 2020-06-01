/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
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
public class PiutangDAO{
    public static List<Piutang> getAllByDateAndKategoriAndStatus(Connection con,String tglMulai,String tglAkhir, String kategori, String status)throws Exception{
        String sql = "select * from tm_piutang where left(tgl_piutang,10) between ? and ?  ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"'";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        else
            sql = sql + " and status != 'false' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Piutang> allPiutang = new ArrayList<>();
        while(rs.next()){
            Piutang p = new Piutang();
            p.setNoPiutang(rs.getString(1));
            p.setTglPiutang(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahPiutang(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPiutang(rs.getDouble(7));
            p.setJatuhTempo(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPiutang.add(p);
        }
        return allPiutang;
    }
    public static List<Piutang> getAllByDate(Connection con,String tglMulai,String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_piutang "
                + " where left(tgl_piutang,10) between '"+tglMulai+"' and '"+tglAkhir+"' and status != 'false'");
        ResultSet rs = ps.executeQuery();
        List<Piutang> allPiutang = new ArrayList<>();
        while(rs.next()){
            Piutang p = new Piutang();
            p.setNoPiutang(rs.getString(1));
            p.setTglPiutang(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahPiutang(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPiutang(rs.getDouble(7));
            p.setJatuhTempo(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPiutang.add(p);
        }
        return allPiutang;
    }
    public static List<Piutang> getAllByKategoriAndStatus(Connection con, String kategori, String status)throws Exception{
        String sql = "select * from tm_piutang where status = ? ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<Piutang> allPiutang = new ArrayList<>();
        while(rs.next()){
            Piutang p = new Piutang();
            p.setNoPiutang(rs.getString(1));
            p.setTglPiutang(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahPiutang(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPiutang(rs.getDouble(7));
            p.setJatuhTempo(rs.getString(8));
            p.setStatus(rs.getString(9));
            allPiutang.add(p);
        }
        return allPiutang;
    }
    public static Piutang getByKategoriAndKeteranganAndStatus(Connection con, String kategori, String keterangan, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_piutang where kategori=? and keterangan=? and status=?");
        ps.setString(1, kategori);
        ps.setString(2, keterangan);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        Piutang p = null;
        if(rs.next()){
            p = new Piutang();
            p.setNoPiutang(rs.getString(1));
            p.setTglPiutang(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahPiutang(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPiutang(rs.getDouble(7));
            p.setJatuhTempo(rs.getString(8));
            p.setStatus(rs.getString(9));
        }
        return p;
    }
    public static Piutang get(Connection con, String noPiutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_piutang where no_piutang=?");
        ps.setString(1, noPiutang);
        ResultSet rs = ps.executeQuery();
        Piutang p = null;
        if(rs.next()){
            p = new Piutang();
            p.setNoPiutang(rs.getString(1));
            p.setTglPiutang(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahPiutang(rs.getDouble(5));
            p.setPembayaran(rs.getDouble(6));
            p.setSisaPiutang(rs.getDouble(7));
            p.setJatuhTempo(rs.getString(8));
            p.setStatus(rs.getString(9));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_piutang,3)) from tm_piutang where mid(no_piutang,4,4) = ?");
        ps.setString(1, new SimpleDateFormat("yyMM").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PT-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PT-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, Piutang p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_piutang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPiutang());
        ps.setString(2, p.getTglPiutang());
        ps.setString(3, p.getKategori());
        ps.setString(4, p.getKeterangan());
        ps.setDouble(5, p.getJumlahPiutang());
        ps.setDouble(6, p.getPembayaran());
        ps.setDouble(7, p.getSisaPiutang());
        ps.setString(8, p.getJatuhTempo());
        ps.setString(9, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, Piutang p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_piutang set "
                + " tgl_piutang=?, kategori=?, keterangan=?, jumlah_piutang=?, "
                + " pembayaran=?, sisa_piutang=?, jatuh_tempo=?, status=? "
                + " where no_piutang=?");
        ps.setString(1, p.getTglPiutang());
        ps.setString(2, p.getKategori());
        ps.setString(3, p.getKeterangan());
        ps.setDouble(4, p.getJumlahPiutang());
        ps.setDouble(5, p.getPembayaran());
        ps.setDouble(6, p.getSisaPiutang());
        ps.setString(7, p.getJatuhTempo());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getNoPiutang());
        ps.executeUpdate();
    }
}
