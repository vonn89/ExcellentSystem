/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.TipeKeuangan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class TipeKeuanganDAO {
    
    public static List<TipeKeuangan> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_tipe_keuangan");
        ResultSet rs = ps.executeQuery();
        List<TipeKeuangan> allTipeKeuangan = new ArrayList<>();
        while(rs.next()){
            TipeKeuangan t = new TipeKeuangan();
            t.setKodeKeuangan(rs.getString(1));
            allTipeKeuangan.add(t);
        }
        return allTipeKeuangan;
    }
    public static void insert(Connection con, TipeKeuangan t)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_tipe_keuangan values(?)");
        ps.setString(1, t.getKodeKeuangan());
        ps.executeUpdate();
    }
    public static void delete(Connection con,TipeKeuangan t)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_tipe_keuangan where kode_keuangan=?");
        ps.setString(1, t.getKodeKeuangan());
        ps.executeUpdate();
    }
}
