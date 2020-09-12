/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SPPHead;
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
public class SPPHeadDAO {
    
    public static List<SPPHead> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_spp_head "
                + " where left(tgl_spp,10) between ? and ? and status =? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SPPHead> allSPP = new ArrayList<>();
        while(rs.next()){
            SPPHead spp = new SPPHead();
            spp.setNoSPP(rs.getString(1));
            spp.setTglSPP(rs.getString(2));
            spp.setNoSKL(rs.getString(3));
            spp.setKodeProperty(rs.getString(4));
            spp.setKodeCustomer(rs.getString(5));
            spp.setTotalDP(rs.getDouble(6));
            spp.setTotalAngsuran(rs.getDouble(7));
            spp.setKodeUser(rs.getString(8));
            spp.setStatus(rs.getString(9));
            spp.setTglBatal(rs.getString(10));
            spp.setUserBatal(rs.getString(11));
            allSPP.add(spp);
        }
        return allSPP;
    }
    public static SPPHead getByKodeProperty(Connection con, String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_spp_head where kode_property=? and status=?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        SPPHead spp = null;
        while(rs.next()){
            spp = new SPPHead();
            spp.setNoSPP(rs.getString(1));
            spp.setTglSPP(rs.getString(2));
            spp.setNoSKL(rs.getString(3));
            spp.setKodeProperty(rs.getString(4));
            spp.setKodeCustomer(rs.getString(5));
            spp.setTotalDP(rs.getDouble(6));
            spp.setTotalAngsuran(rs.getDouble(7));
            spp.setKodeUser(rs.getString(8));
            spp.setStatus(rs.getString(9));
            spp.setTglBatal(rs.getString(10));
            spp.setUserBatal(rs.getString(11));
        }
        return spp;
    }
    public static SPPHead get(Connection con, String noSPP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_spp_head where no_spp=? ");
        ps.setString(1, noSPP);
        ResultSet rs = ps.executeQuery();
        SPPHead spp = null;
        while(rs.next()){
            spp = new SPPHead();
            spp.setNoSPP(rs.getString(1));
            spp.setTglSPP(rs.getString(2));
            spp.setNoSKL(rs.getString(3));
            spp.setKodeProperty(rs.getString(4));
            spp.setKodeCustomer(rs.getString(5));
            spp.setTotalDP(rs.getDouble(6));
            spp.setTotalAngsuran(rs.getDouble(7));
            spp.setKodeUser(rs.getString(8));
            spp.setStatus(rs.getString(9));
            spp.setTglBatal(rs.getString(10));
            spp.setUserBatal(rs.getString(11));
        }
        return spp;
    }
    public static String getId(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_spp,3)) from tt_spp_head where right(no_spp,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/SPP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/SPP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static  void insert(Connection con, SPPHead spp)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_spp_head values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, spp.getNoSPP());
        ps.setString(2, spp.getTglSPP());
        ps.setString(3, spp.getNoSKL());
        ps.setString(4, spp.getKodeProperty());
        ps.setString(5, spp.getKodeCustomer());
        ps.setDouble(6, spp.getTotalDP());
        ps.setDouble(7, spp.getTotalAngsuran());
        ps.setString(8, spp.getKodeUser());
        ps.setString(9, spp.getStatus());
        ps.setString(10, spp.getTglBatal());
        ps.setString(11, spp.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SPPHead spp)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_spp_head set "
                + " tgl_spp=?, no_skl=?, kode_property=?, kode_customer=?, total_dp=?, total_angsuran=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_spp=?");
        ps.setString(1, spp.getTglSPP());
        ps.setString(2, spp.getNoSKL());
        ps.setString(3, spp.getKodeProperty());
        ps.setString(4, spp.getKodeCustomer());
        ps.setDouble(5, spp.getTotalDP());
        ps.setDouble(6, spp.getTotalAngsuran());
        ps.setString(7, spp.getKodeUser());
        ps.setString(8, spp.getStatus());
        ps.setString(9, spp.getTglBatal());
        ps.setString(10, spp.getUserBatal());
        ps.setString(11, spp.getNoSPP());
        ps.executeUpdate();
    }
}
