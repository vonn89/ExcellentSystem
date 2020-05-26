/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
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
public class STJHeadDAO {
    
    public static List<STJHead> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stj_head "
                + " where left(tgl_stj,10) between ? and ? and status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<STJHead> allSTJ = new ArrayList<>();
        while(rs.next()){
            STJHead stj = new STJHead();
            stj.setNoSTJ(rs.getString(1));
            stj.setTglSTJ(rs.getString(2));
            stj.setKodeProperty(rs.getString(3));
            stj.setKodeCustomer(rs.getString(4));
            stj.setJumlahRp(rs.getDouble(5));
            stj.setKeterangan(rs.getString(6));
            stj.setTipeKeuangan(rs.getString(7));
            stj.setKodeSales(rs.getString(8));
            stj.setKodeUser(rs.getString(9));
            stj.setStatus(rs.getString(10));
            stj.setTglBatal(rs.getString(11));
            stj.setUserBatal(rs.getString(12));
            allSTJ.add(stj);
        }
        return allSTJ;
    }
    public static STJHead getByKodeProperty(Connection con,String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stj_head where kode_property=? and status=?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        STJHead stj = null;
        while(rs.next()){
            stj = new STJHead();
            stj.setNoSTJ(rs.getString(1));
            stj.setTglSTJ(rs.getString(2));
            stj.setKodeProperty(rs.getString(3));
            stj.setKodeCustomer(rs.getString(4));
            stj.setJumlahRp(rs.getDouble(5));
            stj.setKeterangan(rs.getString(6));
            stj.setTipeKeuangan(rs.getString(7));
            stj.setKodeSales(rs.getString(8));
            stj.setKodeUser(rs.getString(9));
            stj.setStatus(rs.getString(10));
            stj.setTglBatal(rs.getString(11));
            stj.setUserBatal(rs.getString(12));
        }
        return stj;
    }
    public static STJHead get(Connection con, String noSTJ)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_stj_head where no_stj=? ");
        ps.setString(1, noSTJ);
        ResultSet rs = ps.executeQuery();
        STJHead stj = null;
        while(rs.next()){
            stj = new STJHead();
            stj.setNoSTJ(rs.getString(1));
            stj.setTglSTJ(rs.getString(2));
            stj.setKodeProperty(rs.getString(3));
            stj.setKodeCustomer(rs.getString(4));
            stj.setJumlahRp(rs.getDouble(5));
            stj.setKeterangan(rs.getString(6));
            stj.setTipeKeuangan(rs.getString(7));
            stj.setKodeSales(rs.getString(8));
            stj.setKodeUser(rs.getString(9));
            stj.setStatus(rs.getString(10));
            stj.setTglBatal(rs.getString(11));
            stj.setUserBatal(rs.getString(12));
        }
        return stj;
    }
    public static String getId(Connection con,String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_stj,3)) from tt_stj_head where right(no_stj,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/STJ/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/STJ/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        
    }
    public static  void insert(Connection con, STJHead stj)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_stj_head values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, stj.getNoSTJ());
        ps.setString(2, stj.getTglSTJ());
        ps.setString(3, stj.getKodeProperty());
        ps.setString(4, stj.getKodeCustomer());
        ps.setDouble(5, stj.getJumlahRp());
        ps.setString(6, stj.getKeterangan());
        ps.setString(7, stj.getTipeKeuangan());
        ps.setString(8, stj.getKodeSales());
        ps.setString(9, stj.getKodeUser());
        ps.setString(10, stj.getStatus());
        ps.setString(11, stj.getTglBatal());
        ps.setString(12, stj.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,STJHead stj)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_stj_head set "
                + " tgl_stj=?, kode_property=?, kode_customer=?, jumlah_rp=?, keterangan=?, tipe_keuangan=?, "
                + " kode_sales=?, kode_user=?, status=?, tgl_batal=?, user_batal=? where no_stj=?");
        ps.setString(1, stj.getTglSTJ());
        ps.setString(2, stj.getKodeProperty());
        ps.setString(3, stj.getKodeCustomer());
        ps.setDouble(4, stj.getJumlahRp());
        ps.setString(5, stj.getKeterangan());
        ps.setString(6, stj.getTipeKeuangan());
        ps.setString(7, stj.getKodeSales());
        ps.setString(8, stj.getKodeUser());
        ps.setString(9, stj.getStatus());
        ps.setString(10, stj.getTglBatal());
        ps.setString(11, stj.getUserBatal());
        ps.setString(12, stj.getNoSTJ());
        ps.executeUpdate();
    }
}
