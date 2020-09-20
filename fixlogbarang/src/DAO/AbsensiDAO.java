/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Absensi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class AbsensiDAO {
    
    public static List<Absensi> getAllByTanggalAndPegawai(
            Connection con, String tglMulai, String tglAkhir, String nama)throws Exception{
        String sql = "select * from tt_absensi where left(tanggal,10) between ? and ? ";
        if(!nama.equals("%"))
            sql = sql + " and nama = '"+nama+"' ";
        sql = sql + " order by tanggal ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<Absensi> allAbsensi = new ArrayList<>();
        while(rs.next()){
            Absensi l = new Absensi();
            l.setTanggal(rs.getString(1));
            l.setNama(rs.getString(2));
            l.setJamMasuk(rs.getLong(3));
            l.setJamPulang(rs.getLong(4));
            l.setAbsenMasuk(rs.getLong(5));
            l.setAbsenPulang(rs.getLong(6));
            l.setKeterangan(rs.getString(7));
            allAbsensi.add(l);
        }
        return allAbsensi;
    }
    public static Absensi get(Connection con, String tanggal, String nama)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_absensi where tanggal = ? "
                + " and nama = ? ");
        ps.setString(1, tanggal);
        ps.setString(2, nama);
        ResultSet rs = ps.executeQuery();
        Absensi l = null;
        while(rs.next()){
            l = new Absensi();
            l.setTanggal(rs.getString(1));
            l.setNama(rs.getString(2));
            l.setJamMasuk(rs.getLong(3));
            l.setJamPulang(rs.getLong(4));
            l.setAbsenMasuk(rs.getLong(5));
            l.setAbsenPulang(rs.getLong(6));
            l.setKeterangan(rs.getString(7));
        }
        return l;
    }
    public static void update(Connection con, Absensi l)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_absensi set "
                + " jam_masuk=?, jam_pulang=?, absen_masuk=?, absen_pulang=?, keterangan=? "
                + " where tanggal = ? and nama = ? ");
        ps.setLong(1, l.getJamMasuk());
        ps.setLong(2, l.getJamPulang());
        ps.setLong(3, l.getAbsenMasuk());
        ps.setLong(4, l.getAbsenPulang());
        ps.setString(5, l.getKeterangan());
        ps.setString(6, l.getTanggal());
        ps.setString(7, l.getNama());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Absensi l)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert tt_absensi values(?,?,?,?,?,?,?)");
        ps.setString(1, l.getTanggal());
        ps.setString(2, l.getNama());
        ps.setLong(3, l.getJamMasuk());
        ps.setLong(4, l.getJamPulang());
        ps.setLong(5, l.getAbsenMasuk());
        ps.setLong(6, l.getAbsenPulang());
        ps.setString(7, l.getKeterangan());
        ps.executeUpdate();
    }
    public static void delete(Connection con, String nama, String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_absensi "
                + " where nama = ? and tanggal = ? ");
        ps.setString(1, nama);
        ps.setString(2, tanggal);
        ps.executeUpdate();
    }
    public static void deleteAllByDate(Connection con, String nama, String tanggal)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_absensi "
                + " where nama = ? and tanggal = ? ");
        ps.setString(1, nama);
        ps.setString(2, tanggal);
        ps.executeUpdate();
    }
}
