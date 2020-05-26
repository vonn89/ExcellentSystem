/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Piutang;
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
public class PiutangDAO {
    public static List<Piutang> getAllByDateAndCabangAndTipeKasirAndKategoriAndStatus(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String tipeKasir, String kategori, String status)throws Exception{
        String sql = "select * from tt_piutang where mid(no_piutang,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!tipeKasir.equals("%"))
            sql = sql + " and tipe_kasir = '"+tipeKasir+"' ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<Piutang> allPiutang = new ArrayList<>();
        while(rs.next()){
            Piutang piutang = new Piutang();
            piutang.setNoPiutang(rs.getString(1));
            piutang.setTglPiutang(rs.getString(2));
            piutang.setKodeCabang(rs.getString(3));
            piutang.setTipeKasir(rs.getString(4));
            piutang.setKategori(rs.getString(5));
            piutang.setNoTransaksi(rs.getString(6));
            piutang.setKurs(rs.getDouble(7));
            piutang.setJumlahPiutang(rs.getDouble(8));
            piutang.setTerbayar(rs.getDouble(9));
            piutang.setSisaPiutang(rs.getDouble(10));
            piutang.setStatus(rs.getString(11));
            allPiutang.add(piutang);
        }
        return allPiutang;
    }
    public static Piutang getByNoTransaksi(Connection con, String noTransaksi)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_piutang where no_transaksi = ?");
        ps.setString(1, noTransaksi);
        ResultSet rs = ps.executeQuery();
        Piutang piutang = null;
        while(rs.next()){
            piutang = new Piutang();
            piutang.setNoPiutang(rs.getString(1));
            piutang.setTglPiutang(rs.getString(2));
            piutang.setKodeCabang(rs.getString(3));
            piutang.setTipeKasir(rs.getString(4));
            piutang.setKategori(rs.getString(5));
            piutang.setNoTransaksi(rs.getString(6));
            piutang.setKurs(rs.getDouble(7));
            piutang.setJumlahPiutang(rs.getDouble(8));
            piutang.setTerbayar(rs.getDouble(9));
            piutang.setSisaPiutang(rs.getDouble(10));
            piutang.setStatus(rs.getString(11));
        }
        return piutang;
    }
    public static Piutang get(Connection con, String noPiutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_piutang where no_piutang = ?");
        ps.setString(1, noPiutang);
        ResultSet rs = ps.executeQuery();
        Piutang piutang = null;
        while(rs.next()){
            piutang = new Piutang();
            piutang.setNoPiutang(rs.getString(1));
            piutang.setTglPiutang(rs.getString(2));
            piutang.setKodeCabang(rs.getString(3));
            piutang.setTipeKasir(rs.getString(4));
            piutang.setKategori(rs.getString(5));
            piutang.setNoTransaksi(rs.getString(6));
            piutang.setKurs(rs.getDouble(7));
            piutang.setJumlahPiutang(rs.getDouble(8));
            piutang.setTerbayar(rs.getDouble(9));
            piutang.setSisaPiutang(rs.getDouble(10));
            piutang.setStatus(rs.getString(11));
        }
        return piutang;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_piutang,4)) from tt_piutang "
                + "where mid(no_piutang,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,Piutang piutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_piutang values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, piutang.getNoPiutang());
        ps.setString(2, piutang.getTglPiutang());
        ps.setString(3, piutang.getKodeCabang());
        ps.setString(4, piutang.getTipeKasir());
        ps.setString(5, piutang.getKategori());
        ps.setString(6, piutang.getNoTransaksi());
        ps.setDouble(7, piutang.getKurs());
        ps.setDouble(8, piutang.getJumlahPiutang());
        ps.setDouble(9, piutang.getTerbayar());
        ps.setDouble(10, piutang.getSisaPiutang());
        ps.setString(11, piutang.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con,Piutang piutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_piutang set "
                + " tgl_piutang=?, kode_cabang=?, tipe_kasir=?, kategori=?, no_transaksi=?, "
                + " kurs=?, jumlah_piutang=?, terbayar=?, sisa_piutang=?, status=? "
                + " where no_piutang=?");
        ps.setString(1, piutang.getTglPiutang());
        ps.setString(2, piutang.getKodeCabang());
        ps.setString(3, piutang.getTipeKasir());
        ps.setString(4, piutang.getKategori());
        ps.setString(5, piutang.getNoTransaksi());
        ps.setDouble(6, piutang.getKurs());
        ps.setDouble(7, piutang.getJumlahPiutang());
        ps.setDouble(8, piutang.getTerbayar());
        ps.setDouble(9, piutang.getSisaPiutang());
        ps.setString(10, piutang.getStatus());
        ps.setString(11, piutang.getNoPiutang());
        ps.executeUpdate();
    }
    public static void delete(Connection con,Piutang piutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_piutang where no_piutang=?");
        ps.setString(1, piutang.getNoPiutang());
        ps.executeUpdate();
    }
}
