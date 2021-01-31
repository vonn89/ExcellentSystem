/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Model.ImageDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ImageDatabaseDAO {
    
    public static List<ImageDatabase> getAllByNoTransaksi(
            Connection con, String noTransaksi)throws Exception{
        String sql = "select * from tm_image_database where no_transaksi = ? ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, noTransaksi);
        ResultSet rs = ps.executeQuery();
        List<ImageDatabase> allDetail = new ArrayList<>();
        while(rs.next()){
            ImageDatabase d = new ImageDatabase();
            d.setNoTransaksi(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setJenisTransaksi(rs.getString(3));
            d.setDownloadUrl(rs.getString(4));
            allDetail.add(d);
        }
        return allDetail;
    }
    public static void insert(Connection con, ImageDatabase d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_image_database values(?,?,?,?)");
        ps.setString(1, d.getNoTransaksi());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getJenisTransaksi());
        ps.setString(4, d.getDownloadUrl());
        ps.executeUpdate();
    }
}
