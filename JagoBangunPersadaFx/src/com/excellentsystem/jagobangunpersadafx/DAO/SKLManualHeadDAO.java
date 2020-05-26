/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SKLManualHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Xtreme
 */
public class SKLManualHeadDAO {
    
    public static String getNoSKLManual(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_skl,3)) from tt_skl_head where right(no_skl,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("00").format(rs.getInt(1)+1)+"/SKL/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("00").format(1)+"/SKL/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static  void insertSKL(Connection con, SKLManualHead skl)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_skl_manual_head values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, skl.getNoSKL());
        ps.setString(2, skl.getTglSKL());
        ps.setString(3, skl.getKodeKategori());
        ps.setString(4, skl.getNamaProperty());
        ps.setString(5, skl.getTipeUnit());
        ps.setDouble(6, skl.getLuasTanah());
        ps.setDouble(7, skl.getHarga());
        ps.setString(8, skl.getNamaCustomer());
        ps.setString(9, skl.getJenisKelamin());
        ps.setDouble(10, skl.getTotalPembayaran());
        ps.setDouble(11, skl.getSisaPelunasan());
        ps.setString(12, skl.getKodeUser());
        ps.setString(13, skl.getTglDibuat());
        ps.executeUpdate();
    }
}
