/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganPoin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class KeuanganPoinDAO {
    public static List<KeuanganPoin> getAllByDateAndCabang(Connection con, String tglMulai, String tglAkhir, String kodeCabang)throws Exception{
        String sql = "select * from tt_keuangan_poin where mid(no_keuangan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<KeuanganPoin> listKeuanganPoin = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            KeuanganPoin k = new KeuanganPoin();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setKodeCabang(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setDeskripsi(rs.getString(5));
            k.setJumlahRp(rs.getDouble(6));
            k.setKodeUser(rs.getString(7));
            listKeuanganPoin.add(k);
        }
        return listKeuanganPoin;
    }
    
    public static Double getSaldoBefore(Connection con, String tanggal, String kodeCabang)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_poin where mid(no_keuangan,8,6) < ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        double saldo = 0;
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static Double getSaldoAfter(Connection con, String tanggal, String kodeCabang)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_poin where mid(no_keuangan,8,6) <= ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        double saldo = 0;
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static KeuanganPoin get(Connection con, String kodeCabang, String kategori, String deskripsi)throws Exception{
        String sql = "select * from tt_keuangan_poin where no_keuangan != '' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!deskripsi.equals("%"))
            sql = sql + " and deskripsi = '"+deskripsi+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        KeuanganPoin k = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            k = new KeuanganPoin();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setKodeCabang(rs.getString(3));
            k.setKategori(rs.getString(4));
            k.setDeskripsi(rs.getString(5));
            k.setJumlahRp(rs.getDouble(6));
            k.setKodeUser(rs.getString(7));
        }
        return k;
    }
    public static void insert(Connection con,KeuanganPoin keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan_poin values(?,?,?,?,?,?,?)");
        ps.setString(1, keu.getNoKeuangan());
        ps.setString(2, keu.getTglKeuangan());
        ps.setString(3, keu.getKodeCabang());
        ps.setString(4, keu.getKategori());
        ps.setString(5, keu.getDeskripsi());
        ps.setDouble(6, keu.getJumlahRp());
        ps.setString(7, keu.getKodeUser());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan_poin where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
