/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLManualDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SKLManualDetailDAO {
    
    public List<SKLDetail> getAllSKLDetailByNoSKL(Connection con,String noSKL)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_manual_detail where no_skl=?");
        ps.setString(1, noSKL);
        ResultSet rs = ps.executeQuery();
        List<SKLDetail> allSKLDetail = new ArrayList<>();
        while(rs.next()){
            SKLDetail d = new SKLDetail();
            d.setNoSKL(rs.getString(1));
            d.setTahap(rs.getString(2));
            d.setTglBayar(rs.getString(3));
            d.setJumlahRp(rs.getDouble(4));
            allSKLDetail.add(d);
        }
        return allSKLDetail;
    }
    public static void insertSKLDetail(Connection con, SKLManualDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_skl_manual_detail values(?,?,?,?)");
        ps.setString(1, d.getNoSKL());
        ps.setString(2, d.getTahap());
        ps.setString(3, d.getTglBayar());
        ps.setDouble(4, d.getJumlahRp());
        ps.executeUpdate();
    }
}
