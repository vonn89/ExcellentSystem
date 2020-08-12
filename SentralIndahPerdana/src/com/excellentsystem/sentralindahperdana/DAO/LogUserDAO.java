/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.LogUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class LogUserDAO {
    public static void insert(Connection con, LogUser l)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert tt_log_user values(?,?,?,?)")) {
            ps.setString(1, l.getTanggal());
            ps.setString(2, l.getKodeUser());
            ps.setString(3, l.getKategori());
            ps.setString(4, l.getKeterangan());
            ps.executeUpdate();
        }
    }
    public static List<LogUser> getAllByUser(Connection con, String kode_user)throws Exception{
        List<LogUser> allLogUser;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_log_user where kode_user=?")) {
            ps.setString(1, kode_user);
            try (ResultSet rs = ps.executeQuery()) {
                allLogUser = new ArrayList<>();
                while(rs.next()){
                    LogUser l = new LogUser();
                    l.setTanggal(rs.getString(1));
                    l.setKodeUser(rs.getString(2));
                    l.setKategori(rs.getString(3));
                    l.setKeterangan(rs.getString(4));
                    allLogUser.add(l);
                }
            }
        }
        return allLogUser;
    }
    public static List<LogUser> getAllByTanggal(Connection con, String tglMulai,String tglAkhir)throws Exception{
        List<LogUser> allLogUser;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_log_user where left(tanggal,10) between ? and ?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            try (ResultSet rs = ps.executeQuery()) {
                allLogUser = new ArrayList<>();
                while(rs.next()){
                    LogUser l = new LogUser();
                    l.setTanggal(rs.getString(1));
                    l.setKodeUser(rs.getString(2));
                    l.setKategori(rs.getString(3));
                    l.setKeterangan(rs.getString(4));
                    allLogUser.add(l);
                }
            }
        }
        return allLogUser;
    }
    public static List<LogUser> getAllByTanggalAndUser(Connection con, String tglMulai,String tglAkhir,String kode_user)throws Exception{
        List<LogUser> allLogUser;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_log_user where left(tanggal,10) between ? and ? and kode_user=?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, kode_user);
            try (ResultSet rs = ps.executeQuery()) {
                allLogUser = new ArrayList<>();
                while(rs.next()){
                    LogUser l = new LogUser();
                    l.setTanggal(rs.getString(1));
                    l.setKodeUser(rs.getString(2));
                    l.setKategori(rs.getString(3));
                    l.setKeterangan(rs.getString(4));
                    allLogUser.add(l);
                }
            }
        }
        return allLogUser;
    }
}
