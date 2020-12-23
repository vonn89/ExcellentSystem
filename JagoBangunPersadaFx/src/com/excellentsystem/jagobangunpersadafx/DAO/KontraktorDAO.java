/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
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
public class KontraktorDAO {
    public static List<Kontraktor> getAllByStatus(Connection con, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kontraktor where status like ?");
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<Kontraktor> allKontraktor = new ArrayList<>();
        while(rs.next()){
            Kontraktor c = new Kontraktor();
            c.setKodeKontraktor(rs.getString(1));
            c.setNamaKontraktor(rs.getString(2));
            c.setAlamat(rs.getString(3));
            c.setKota(rs.getString(4));
            c.setKontakPerson(rs.getString(5));
            c.setNoTelp(rs.getString(6));
            c.setNoHandphone(rs.getString(7));;
            c.setEmail(rs.getString(8));
            c.setStatus(rs.getString(9));
            allKontraktor.add(c);
        }
        return allKontraktor;
    }
    public static Kontraktor get(Connection con, String kodeKontraktor)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_kontraktor where kode_kontraktor=?");
        ps.setString(1, kodeKontraktor);
        ResultSet rs = ps.executeQuery();
        Kontraktor c = null;
        while(rs.next()){
            c = new Kontraktor();
            c.setKodeKontraktor(rs.getString(1));
            c.setNamaKontraktor(rs.getString(2));
            c.setAlamat(rs.getString(3));
            c.setKota(rs.getString(4));
            c.setKontakPerson(rs.getString(5));
            c.setNoTelp(rs.getString(6));
            c.setNoHandphone(rs.getString(7));;
            c.setEmail(rs.getString(8));
            c.setStatus(rs.getString(9));
        }
        return c;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_kontraktor,5)) from tm_kontraktor");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "KR-"+new DecimalFormat("00000").format(rs.getInt(1)+1);
        else
            return "KR-"+new DecimalFormat("00000").format(1);
    }
    public static void update(Connection con, Kontraktor c)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kontraktor set nama_kontraktor=?, alamat=?, kota=?, "
            + " kontak_person=?, no_telp=?, no_handphone=?, email=?, status=? "
            + " where kode_customer=?");
        ps.setString(1, c.getNamaKontraktor());
        ps.setString(2, c.getAlamat());
        ps.setString(3, c.getKota());
        ps.setString(4, c.getKontakPerson());
        ps.setString(5, c.getNoTelp());
        ps.setString(6, c.getNoHandphone());
        ps.setString(7, c.getEmail());
        ps.setString(8, c.getStatus());
        ps.setString(9, c.getKodeKontraktor());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Kontraktor c)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kontraktor values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getKodeKontraktor());
        ps.setString(2, c.getNamaKontraktor());
        ps.setString(3, c.getAlamat());
        ps.setString(4, c.getKota());
        ps.setString(5, c.getKontakPerson());
        ps.setString(6, c.getNoTelp());
        ps.setString(7, c.getNoHandphone());
        ps.setString(8, c.getEmail());
        ps.setString(9, c.getStatus());
        ps.executeUpdate();
    }
}
