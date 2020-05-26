/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SAP;
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
public class SAPDAO {
    
    public static SAP get(Connection con,String noSAP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sap where no_sap=?");
        ps.setString(1, noSAP);
        ResultSet rs = ps.executeQuery();
        SAP sap = null;
        while(rs.next()){
            sap = new SAP();
            sap.setNoSAP(rs.getString(1));
            sap.setTglSAP(rs.getString(2));
            sap.setNoSKL(rs.getString(3));
            sap.setKodeProperty(rs.getString(4));
            sap.setKodeCustomer(rs.getString(5));
            sap.setTahap(rs.getInt(6));
            sap.setJumlahRp(rs.getDouble(7));
            sap.setTipeKeuangan(rs.getString(8));
            sap.setJatuhTempo(rs.getString(9));
            sap.setKodeUser(rs.getString(10));
            sap.setStatus(rs.getString(11));
            sap.setTglBatal(rs.getString(12));
            sap.setUserBatal(rs.getString(13));
        }
        return sap;
    }
    public static List<SAP> getAllByKodeProperty(Connection con, String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sap where kode_property=? and status = ?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<SAP> allSAP = new ArrayList<>();
        while(rs.next()){
            SAP sap = new SAP();
            sap.setNoSAP(rs.getString(1));
            sap.setTglSAP(rs.getString(2));
            sap.setNoSKL(rs.getString(3));
            sap.setKodeProperty(rs.getString(4));
            sap.setKodeCustomer(rs.getString(5));
            sap.setTahap(rs.getInt(6));
            sap.setJumlahRp(rs.getDouble(7));
            sap.setTipeKeuangan(rs.getString(8));
            sap.setJatuhTempo(rs.getString(9));
            sap.setKodeUser(rs.getString(10));
            sap.setStatus(rs.getString(11));
            sap.setTglBatal(rs.getString(12));
            sap.setUserBatal(rs.getString(13));
            allSAP.add(sap);
        }
        return allSAP;
    }
    public static List<SAP> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sap "
                + " where left(tgl_sap,10) between ? and ? and status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SAP> allSAP = new ArrayList<>();
        while(rs.next()){
            SAP sap = new SAP();
            sap.setNoSAP(rs.getString(1));
            sap.setTglSAP(rs.getString(2));
            sap.setNoSKL(rs.getString(3));
            sap.setKodeProperty(rs.getString(4));
            sap.setKodeCustomer(rs.getString(5));
            sap.setTahap(rs.getInt(6));
            sap.setJumlahRp(rs.getDouble(7));
            sap.setTipeKeuangan(rs.getString(8));
            sap.setJatuhTempo(rs.getString(9));
            sap.setKodeUser(rs.getString(10));
            sap.setStatus(rs.getString(11));
            sap.setTglBatal(rs.getString(12));
            sap.setUserBatal(rs.getString(13));
            allSAP.add(sap);
        }
        return allSAP;
    }
    public static String getId(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_sap,3)) from tt_sap where right(no_sap,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/SAP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/SAP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static void insert(Connection con, SAP sap)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_sap values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, sap.getNoSAP());
        ps.setString(2, sap.getTglSAP());
        ps.setString(3, sap.getNoSKL());
        ps.setString(4, sap.getKodeProperty());
        ps.setString(5, sap.getKodeCustomer());
        ps.setInt(6, sap.getTahap());
        ps.setDouble(7, sap.getJumlahRp());
        ps.setString(8, sap.getTipeKeuangan());
        ps.setString(9, sap.getJatuhTempo());
        ps.setString(10, sap.getKodeUser());
        ps.setString(11, sap.getStatus());
        ps.setString(12, sap.getTglBatal());
        ps.setString(13, sap.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SAP sap)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_sap set "
                + " tgl_sap=?, no_skl=?, kode_property=?, kode_customer=?, tahap=?, jumlah_rp=?, "
                + " tipe_keuangan=?, jatuh_tempo=?, kode_user=?, status=?, tgl_batal=?, user_batal=? where no_sap=?");
        ps.setString(1, sap.getTglSAP());
        ps.setString(2, sap.getNoSKL());
        ps.setString(3, sap.getKodeProperty());
        ps.setString(4, sap.getKodeCustomer());
        ps.setInt(5, sap.getTahap());
        ps.setDouble(6, sap.getJumlahRp());
        ps.setString(7, sap.getTipeKeuangan());
        ps.setString(8, sap.getJatuhTempo());
        ps.setString(9, sap.getKodeUser());
        ps.setString(10, sap.getStatus());
        ps.setString(11, sap.getTglBatal());
        ps.setString(12, sap.getUserBatal());
        ps.setString(13, sap.getNoSAP());
        ps.executeUpdate();
    }
}
