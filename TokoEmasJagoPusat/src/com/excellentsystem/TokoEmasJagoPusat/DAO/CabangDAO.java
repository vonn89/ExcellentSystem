/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class CabangDAO {
    public static List<Cabang> getAll(Connection con)throws Exception{
        List<Cabang> allCabang = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_cabang");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            Cabang c = new Cabang();
            c.setKodeCabang(rs.getString(1));
            c.setNamaCabang(rs.getString(2));
            c.setIpServer(rs.getString(3));
            allCabang.add(c);
        }
        return allCabang;
    }
    public static Cabang get(Connection con, String kodeCabang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_cabang where kode_cabang = ?");
        ps.setString(1, kodeCabang);
        ResultSet rs = ps.executeQuery();
        Cabang c = null;
        while(rs.next()){
            c = new Cabang();
            c.setKodeCabang(rs.getString(1));
            c.setNamaCabang(rs.getString(2));
            c.setIpServer(rs.getString(3));
        }
        return c;
    }
    public static void insert(Connection con, Cabang c)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_cabang values (?,?,?)");
        ps.setString(1, c.getKodeCabang());
        ps.setString(2, c.getNamaCabang());
        ps.setString(3, c.getIpServer());
        ps.executeUpdate();
    }
    public static void update(Connection con,Cabang toko)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_cabang set nama_cabang=?, ip_server=? where kode_cabang=?");
        ps.setString(1, toko.getNamaCabang());
        ps.setString(2, toko.getIpServer());
        ps.setString(3, toko.getKodeCabang());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Cabang c)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_cabang where kode_cabang = ?");
        ps.setString(1, c.getKodeCabang());
        ps.executeUpdate();
    }
}
