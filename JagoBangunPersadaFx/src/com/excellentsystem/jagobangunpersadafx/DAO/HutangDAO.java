/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
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
public class HutangDAO {
    public static List<Hutang> getAllByTanggalAndKategoriAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String kategori, String status)throws Exception{
        String sql = "select * from tm_hutang where left(tgl_hutang,10) between ? and ?  ";
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
        List<Hutang> listHutang = new ArrayList<>();
        while(rs.next()){
            Hutang h = new Hutang();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKategori(rs.getString(3));
            h.setKeterangan(rs.getString(4));
            h.setJumlahHutang(rs.getDouble(5));
            h.setPembayaran(rs.getDouble(6));
            h.setSisaHutang(rs.getDouble(7));
            h.setJatuhTempo(rs.getString(8));
            h.setStatus(rs.getString(9));
            listHutang.add(h);
        }
        return listHutang;
    }
    public static List<Hutang> getAllByDate(Connection con,String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang "
                + " where left(tgl_hutang,10) between ? and ? and status!='false'");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Hutang> allHutang = new ArrayList<>();
        while(rs.next()){
            Hutang h = new Hutang();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKategori(rs.getString(3));
            h.setKeterangan(rs.getString(4));
            h.setJumlahHutang(rs.getDouble(5));
            h.setPembayaran(rs.getDouble(6));
            h.setSisaHutang(rs.getDouble(7));
            h.setJatuhTempo(rs.getString(8));
            h.setStatus(rs.getString(9));
            allHutang.add(h);
        }
        return allHutang;
    }
    public static List<Hutang> getAllByKategoriAndStatus(Connection con, String kategori, String status)throws Exception{
        String sql = "select * from tm_hutang where status = ? ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<Hutang> allHutang = new ArrayList<>();
        while(rs.next()){
            Hutang h = new Hutang();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKategori(rs.getString(3));
            h.setKeterangan(rs.getString(4));
            h.setJumlahHutang(rs.getDouble(5));
            h.setPembayaran(rs.getDouble(6));
            h.setSisaHutang(rs.getDouble(7));
            h.setJatuhTempo(rs.getString(8));
            h.setStatus(rs.getString(9));
            allHutang.add(h);
        }
        return allHutang;
    }
    public static Hutang getByKategoriAndKeteranganAndStatus(Connection con, String kategori, String keterangan, String status)throws Exception{
        String sql = "select * from tm_hutang where status!='' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"'";
        if(!keterangan.equals("%"))
            sql = sql + " and keterangan = '"+keterangan+"'";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        Hutang h = null;
        if(rs.next()){
            h = new Hutang();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKategori(rs.getString(3));
            h.setKeterangan(rs.getString(4));
            h.setJumlahHutang(rs.getDouble(5));
            h.setPembayaran(rs.getDouble(6));
            h.setSisaHutang(rs.getDouble(7));
            h.setJatuhTempo(rs.getString(8));
            h.setStatus(rs.getString(9));
        }
        return h;
    }
    public static Hutang get(Connection con, String noHutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_hutang where no_hutang=?");
        ps.setString(1, noHutang);
        ResultSet rs = ps.executeQuery();
        Hutang h = null;
        if(rs.next()){
            h = new Hutang();
            h.setNoHutang(rs.getString(1));
            h.setTglHutang(rs.getString(2));
            h.setKategori(rs.getString(3));
            h.setKeterangan(rs.getString(4));
            h.setJumlahHutang(rs.getDouble(5));
            h.setPembayaran(rs.getDouble(6));
            h.setSisaHutang(rs.getDouble(7));
            h.setJatuhTempo(rs.getString(8));
            h.setStatus(rs.getString(9));
        }
        return h;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_hutang,3)) from tm_hutang where mid(no_hutang,4,4) = ?");
        ps.setString(1, new SimpleDateFormat("yyMM").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "HT-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "HT-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(1);
    }
    public static String getIdByDate(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_hutang,3)) from tm_hutang where mid(no_hutang,4,4) = ?");
        ps.setString(1, new SimpleDateFormat("yyMM").format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "HT-"+new SimpleDateFormat("yyMM").format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "HT-"+new SimpleDateFormat("yyMM").format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, Hutang h)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_hutang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, h.getNoHutang());
        ps.setString(2, h.getTglHutang());
        ps.setString(3, h.getKategori());
        ps.setString(4, h.getKeterangan());
        ps.setDouble(5, h.getJumlahHutang());
        ps.setDouble(6, h.getPembayaran());
        ps.setDouble(7, h.getSisaHutang());
        ps.setString(8, h.getJatuhTempo());
        ps.setString(9, h.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con,Hutang h)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_hutang set "
                + " tgl_hutang=?, kategori=?, keterangan=?, "
                + " jumlah_hutang=?, pembayaran=?, sisa_hutang=?, "
                + " jatuh_tempo=?, status=? where no_hutang=? ");
        ps.setString(1, h.getTglHutang());
        ps.setString(2, h.getKategori());
        ps.setString(3, h.getKeterangan());
        ps.setDouble(4, h.getJumlahHutang());
        ps.setDouble(5, h.getPembayaran());
        ps.setDouble(6, h.getSisaHutang());
        ps.setString(7, h.getJatuhTempo());
        ps.setString(8, h.getStatus());
        ps.setString(9, h.getNoHutang());
        ps.executeUpdate();
    }
}
