/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
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
public class KeuanganCabangDAO {
    public static List<KeuanganCabang> getAllByDateAndCabangAndKasirAndTipeKeuangan(Connection con, String tglMulai, String tglAkhir, 
            String kodeCabang, String tipeKasir, String tipeKeuangan)throws Exception{
        String sql = "select * from tt_keuangan_cabang where mid(no_keuangan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<KeuanganCabang> listKeuanganCabang = new ArrayList<>();
        while(rs.next()){
            KeuanganCabang keu = new KeuanganCabang();
            keu.setNoKeuangan(rs.getString(1));
            keu.setTglKeuangan(rs.getString(2));
            keu.setKodeCabang(rs.getString(3));
            keu.setTipeKasir(rs.getString(4));
            keu.setTipeKeuangan(rs.getString(5));
            keu.setKategori(rs.getString(6));
            keu.setDeskripsi(rs.getString(7));
            keu.setJumlahRp(rs.getDouble(8));
            keu.setKodeUser(rs.getString(9));
            listKeuanganCabang.add(keu);
        }
        return listKeuanganCabang;
    }
    
    public static Double getSaldoBefore(Connection con, String tanggal, String kodeCabang, String tipeKasir, String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_cabang where mid(no_keuangan,8,6) < ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        double saldo = 0;
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static Double getSaldoAfter(Connection con, String tanggal, String kodeCabang, String tipeKasir, String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_cabang where mid(no_keuangan,8,6) <= ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        double saldo = 0;
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static Double getTotal(Connection con, String tglMulai, String tglAkhir, String kodeCabang, String tipeKasir, String tipeKeuangan)throws Exception{
        String sql = "select sum(jumlah_rp) from tt_keuangan_cabang where mid(no_keuangan,8,6) between ?  and ?";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        double saldo = 0;
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            saldo = saldo + rs.getDouble(1);
        return saldo;
    }
    public static KeuanganCabang get(Connection con, String kodeCabang, String tipeKasir, String tipeKeuangan, String kategori, String deskripsi)throws Exception{
        String sql = "select * from tt_keuangan_cabang where no_keuangan != '' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!tipeKeuangan.equals("%"))
            sql = sql + " and tipe_keuangan = '"+tipeKeuangan+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!deskripsi.equals("%"))
            sql = sql + " and deskripsi = '"+deskripsi+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        KeuanganCabang k = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            k = new KeuanganCabang();
            k.setNoKeuangan(rs.getString(1));
            k.setTglKeuangan(rs.getString(2));
            k.setKodeCabang(rs.getString(3));
            k.setTipeKasir(rs.getString(4));
            k.setTipeKeuangan(rs.getString(5));
            k.setKategori(rs.getString(6));
            k.setDeskripsi(rs.getString(7));
            k.setJumlahRp(rs.getDouble(8));
            k.setKodeUser(rs.getString(9));
        }
        return k;
    }
    public static String getId(Connection con, String kodeCabang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan_cabang "
                + "where mid(no_keuangan,8,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static String getId(Connection con, String kodeCabang,String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_keuangan,4)) from tt_keuangan_cabang "
                + "where mid(no_keuangan,8,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(tanggal)));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = kodeCabang+"-KC-"+tglSystem.format(tglBarang.parse(tanggal))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,KeuanganCabang keu)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_keuangan_cabang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, keu.getNoKeuangan());
        ps.setString(2, keu.getTglKeuangan());
        ps.setString(3, keu.getKodeCabang());
        ps.setString(4, keu.getTipeKasir());
        ps.setString(5, keu.getTipeKeuangan());
        ps.setString(6, keu.getKategori());
        ps.setString(7, keu.getDeskripsi());
        ps.setDouble(8, keu.getJumlahRp());
        ps.setString(9, keu.getKodeUser());
        ps.executeUpdate();
    }
    public static void update(Connection con, KeuanganCabang k) throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_keuangan_cabang set "
                + " tgl_keuangan=?, tipe_kasir=?, deskripsi=?, jumlah_rp=?, kode_user=? "
                + " where no_keuangan=? and kode_cabang=? and tipe_keuangan=? and kategori=?");
        ps.setString(1, k.getTglKeuangan());
        ps.setString(2, k.getTipeKasir());
        ps.setString(3, k.getDeskripsi());
        ps.setDouble(4, k.getJumlahRp());
        ps.setString(5, k.getKodeUser());
        ps.setString(6, k.getNoKeuangan());
        ps.setString(7, k.getKodeCabang());
        ps.setString(8, k.getTipeKeuangan());
        ps.setString(9, k.getKategori());
        ps.executeUpdate();
    }
    public static void deleteAll(Connection con, String noKeuangan) throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_keuangan_cabang where no_keuangan = ?");
        ps.setString(1, noKeuangan);
        ps.executeUpdate();
    }
}
