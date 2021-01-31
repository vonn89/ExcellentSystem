/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.Mesin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class MesinDAO {
    
    public static List<Mesin> getAll(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_mesin");
        ResultSet rs = ps.executeQuery();
        List<Mesin> allMesin = new ArrayList<>();
        while(rs.next()){
            Mesin k = new Mesin();
            k.setKodeMesin(rs.getString(1));
            allMesin.add(k);
        }
        return allMesin;
    }
    public static void insert(Connection con, Mesin k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_mesin values(?)");
        ps.setString(1, k.getKodeMesin());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Mesin g)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_mesin where kode_gudang = ?");
        ps.setString(1, g.getKodeMesin());
        ps.executeUpdate();
    }
}
