/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Addendum;
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
 * @author Excellent
 */
public class AddendumDAO {
    
    public static List<Addendum> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_addendum "
                + " where left(tgl_addendum,10) between ? and ? and status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<Addendum> allAddendum = new ArrayList<>();
        while(rs.next()){
            Addendum a = new Addendum();
            a.setNoAddendum(rs.getString(1));
            a.setTglAddendum(rs.getString(2));
            a.setKodeProperty(rs.getString(3));
            a.setKodeCustomer(rs.getString(4));
            a.setKeterangan(rs.getString(5));
            a.setPerubahanHargaProperty(rs.getDouble(6));
            a.setKodeUser(rs.getString(7));
            a.setStatus(rs.getString(8));
            a.setTglBatal(rs.getString(9));
            a.setUserBatal(rs.getString(10));
            allAddendum.add(a);
        }
        return allAddendum;
    }
    public static Addendum getByKodeProperty(Connection con,String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_addendum where kode_property=? and status=?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        Addendum a = null;
        while(rs.next()){
            a = new Addendum();
            a.setNoAddendum(rs.getString(1));
            a.setTglAddendum(rs.getString(2));
            a.setKodeProperty(rs.getString(3));
            a.setKodeCustomer(rs.getString(4));
            a.setKeterangan(rs.getString(5));
            a.setPerubahanHargaProperty(rs.getDouble(6));
            a.setKodeUser(rs.getString(7));
            a.setStatus(rs.getString(8));
            a.setTglBatal(rs.getString(9));
            a.setUserBatal(rs.getString(10));
        }
        return a;
    }
    public static Addendum get(Connection con, String noAddendum)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_addendum where no_addendum=? ");
        ps.setString(1, noAddendum);
        ResultSet rs = ps.executeQuery();
        Addendum a = null;
        while(rs.next()){
            a = new Addendum();
            a.setNoAddendum(rs.getString(1));
            a.setTglAddendum(rs.getString(2));
            a.setKodeProperty(rs.getString(3));
            a.setKodeCustomer(rs.getString(4));
            a.setKeterangan(rs.getString(5));
            a.setPerubahanHargaProperty(rs.getDouble(6));
            a.setKodeUser(rs.getString(7));
            a.setStatus(rs.getString(8));
            a.setTglBatal(rs.getString(9));
            a.setUserBatal(rs.getString(10));
        }
        return a;
    }
    public static String getId(Connection con,String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_addendum,3)) from tt_addendum where right(no_addendum,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/ADD/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/ADD/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        
    }
    public static  void insert(Connection con, Addendum a)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_addendum values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, a.getNoAddendum());
        ps.setString(2, a.getTglAddendum());
        ps.setString(3, a.getKodeProperty());
        ps.setString(4, a.getKodeCustomer());
        ps.setString(5, a.getKeterangan());
        ps.setDouble(6, a.getPerubahanHargaProperty());
        ps.setString(7, a.getKodeUser());
        ps.setString(8, a.getStatus());
        ps.setString(9, a.getTglBatal());
        ps.setString(10, a.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,Addendum a)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_addendum set "
                + " tgl_addendum=?, kode_property=?, kode_customer=?, keterangan=?, perubahan_harga_property=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? where no_addendum=?");
        ps.setString(1, a.getTglAddendum());
        ps.setString(2, a.getKodeProperty());
        ps.setString(3, a.getKodeCustomer());
        ps.setString(4, a.getKeterangan());
        ps.setDouble(5, a.getPerubahanHargaProperty());
        ps.setString(6, a.getKodeUser());
        ps.setString(7, a.getStatus());
        ps.setString(8, a.getTglBatal());
        ps.setString(9, a.getUserBatal());
        ps.setString(10, a.getNoAddendum());
        ps.executeUpdate();
    }
}
