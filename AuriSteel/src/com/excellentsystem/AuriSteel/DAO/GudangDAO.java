/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.Gudang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class GudangDAO {
    public static List<Gudang> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_gudang");
        ResultSet rs = ps.executeQuery();
        List<Gudang> allGudang = new ArrayList<>();
        while(rs.next()){
            Gudang k = new Gudang();
            k.setKodeGudang(rs.getString(1));
            allGudang.add(k);
        }
        return allGudang;
    }
    public static void insert(Connection con, Gudang k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_gudang values(?)");
        ps.setString(1, k.getKodeGudang());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Gudang g)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_gudang where kode_gudang = ?");
        ps.setString(1, g.getKodeGudang());
        ps.executeUpdate();
    }
}
