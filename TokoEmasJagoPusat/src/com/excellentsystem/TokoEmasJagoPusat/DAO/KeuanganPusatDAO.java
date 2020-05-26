/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganPusat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KeuanganPusatDAO {
    public static List<KeuanganPusat> getAllByDateAndTipeKeuangan(Connection con, String tglMulai, String tglAkhir, String tipeKeuangan)throws Exception{
        String sql = "select * from tt_keuangan_pusat where mid(no_keuangan,4,6) between ? and ? ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<KeuanganPusat> listKeuanganPusat = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            KeuanganPusat keu = new KeuanganPusat();
            keu.setNoKeuangan(rs.getString(1));
            keu.setTglKeuangan(rs.getString(2));
            keu.setTipeKeuangan(rs.getString(3));
            keu.setKategori(rs.getString(4));
            keu.setDeskripsi(rs.getString(5));
            keu.setJumlahRp(rs.getDouble(6));
            keu.setKodeUser(rs.getString(7));
            listKeuanganPusat.add(keu);
        }
        return listKeuanganPusat;
    }
    public static Double getSaldoBefore(Connection con, String tanggal, String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_pusat where mid(no_keuangan,4,6) < ? ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        double saldo = 0;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static Double getSaldoAfter(Connection con, String tanggal, String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_pusat where mid(no_keuangan,4,6) <= ? ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        double saldo = 0;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static KeuanganPusat get(Connection con, String tipeKeuangan, String kategori, String deskripsi)throws Exception{
        String sql = "select * from tt_keuangan_pusat where no_keuangan != '' ";
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!deskripsi.equals("%"))
            sql = sql + " and deskripsi = '"+deskripsi+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        KeuanganPusat k = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            k = new KeuanganPusat();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setTipeKeuangan(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setDeskripsi(rs.getString(5));
            k.setJumlahRp(rs.getDouble(6));
            k.setKodeUser(rs.getString(7));
        }
        return k;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan_pusat "
                + "where mid(no_keuangan,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "KP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "KP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,KeuanganPusat keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan_pusat values(?,?,?,?,?,?,?)");
        ps.setString(1, keu.getNoKeuangan());
        ps.setString(2, keu.getTglKeuangan());
        ps.setString(3, keu.getTipeKeuangan());
        ps.setString(4, keu.getKategori());
        ps.setString(5, keu.getDeskripsi());
        ps.setDouble(6, keu.getJumlahRp());
        ps.setString(7, keu.getKodeUser());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan_pusat where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
