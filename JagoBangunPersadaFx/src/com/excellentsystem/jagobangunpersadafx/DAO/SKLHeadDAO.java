/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SKLHeadDAO {
    
    public static SKLHead getByKodeProperty(Connection con,String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_head where kode_property=? and status=?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        SKLHead skl = null;
        while(rs.next()){
            skl = new SKLHead();
            skl.setNoSKL(rs.getString(1));
            skl.setTglSKL(rs.getString(2));
            skl.setNoSTJ(rs.getString(3));
            skl.setKodeProperty(rs.getString(4));
            skl.setKodeCustomer(rs.getString(5));
            skl.setTotalPembayaran(rs.getDouble(6));
            skl.setSisaPelunasan(rs.getDouble(7));
            skl.setKodeSales(rs.getString(8));
            skl.setKodeUser(rs.getString(9));
            skl.setStatus(rs.getString(10));
            skl.setTglBatal(rs.getString(11));
            skl.setUserBatal(rs.getString(12));
        }
        return skl;
    }
    public static List<SKLHead> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_head where  "+
                " left(tgl_skl,10) between ? and ? and status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SKLHead> allSKL = new ArrayList<>();
        while(rs.next()){
            SKLHead skl = new SKLHead();
            skl.setNoSKL(rs.getString(1));
            skl.setTglSKL(rs.getString(2));
            skl.setNoSTJ(rs.getString(3));
            skl.setKodeProperty(rs.getString(4));
            skl.setKodeCustomer(rs.getString(5));
            skl.setTotalPembayaran(rs.getDouble(6));
            skl.setSisaPelunasan(rs.getDouble(7));
            skl.setKodeSales(rs.getString(8));
            skl.setKodeUser(rs.getString(9));
            skl.setStatus(rs.getString(10));
            skl.setTglBatal(rs.getString(11));
            skl.setUserBatal(rs.getString(12));
            allSKL.add(skl);
        }
        return allSKL;
    }
    public static SKLHead get(Connection con,String noSKL)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_skl_head where no_skl=? ");
        ps.setString(1, noSKL);
        ResultSet rs = ps.executeQuery();
        SKLHead skl = null;
        while(rs.next()){
            skl = new SKLHead();
            skl.setNoSKL(rs.getString(1));
            skl.setTglSKL(rs.getString(2));
            skl.setNoSTJ(rs.getString(3));
            skl.setKodeProperty(rs.getString(4));
            skl.setKodeCustomer(rs.getString(5));
            skl.setTotalPembayaran(rs.getDouble(6));
            skl.setSisaPelunasan(rs.getDouble(7));
            skl.setKodeSales(rs.getString(8));
            skl.setKodeUser(rs.getString(9));
            skl.setStatus(rs.getString(10));
            skl.setTglBatal(rs.getString(11));
            skl.setUserBatal(rs.getString(12));
        }
        return skl;
    }
    public static String getId(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_skl,3)) from tt_skl_head where right(no_skl,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/SKL/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/SKL/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static void insert(Connection con, SKLHead skl)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_skl_head values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, skl.getNoSKL());
        ps.setString(2, skl.getTglSKL());
        ps.setString(3, skl.getNoSTJ());
        ps.setString(4, skl.getKodeProperty());
        ps.setString(5, skl.getKodeCustomer());
        ps.setDouble(6, skl.getTotalPembayaran());
        ps.setDouble(7, skl.getSisaPelunasan());
        ps.setString(8, skl.getKodeSales());
        ps.setString(9, skl.getKodeUser());
        ps.setString(10, skl.getStatus());
        ps.setString(11, skl.getTglBatal());
        ps.setString(12, skl.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SKLHead skl)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_skl_head set "
                + " tgl_skl=?, no_stj=?, kode_property=?, kode_customer=?, total_pembayaran=?, sisa_pelunasan=?, "
                + " kode_sales=?, kode_user=?, status=?, tgl_batal=?, user_batal=? where no_skl=?");
        ps.setString(1, skl.getTglSKL());
        ps.setString(2, skl.getNoSTJ());
        ps.setString(3, skl.getKodeProperty());
        ps.setString(4, skl.getKodeCustomer());
        ps.setDouble(5, skl.getTotalPembayaran());
        ps.setDouble(6, skl.getSisaPelunasan());
        ps.setString(7, skl.getKodeSales());
        ps.setString(8, skl.getKodeUser());
        ps.setString(9, skl.getStatus());
        ps.setString(10, skl.getTglBatal());
        ps.setString(11, skl.getUserBatal());
        ps.setString(12, skl.getNoSKL());
        ps.executeUpdate();
    }
}
