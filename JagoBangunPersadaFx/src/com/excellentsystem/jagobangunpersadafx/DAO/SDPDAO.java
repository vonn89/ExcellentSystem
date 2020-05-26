/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SDP;
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
public class SDPDAO {
    
    public static List<SDP> getAllByKodeProperty(Connection con, String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sdp where kode_property=? and status=? ");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<SDP> allSDP = new ArrayList<>();
        while(rs.next()){
            SDP sdp = new SDP();
            sdp.setNoSDP(rs.getString(1));
            sdp.setTglSDP(rs.getString(2));
            sdp.setNoSTJ(rs.getString(3));
            sdp.setKodeProperty(rs.getString(4));
            sdp.setKodeCustomer(rs.getString(5));
            sdp.setTahap(rs.getInt(6));
            sdp.setJumlahRp(rs.getDouble(7));
            sdp.setTipeKeuangan(rs.getString(8));
            sdp.setKodeSales(rs.getString(9));
            sdp.setJatuhTempo(rs.getString(10));
            sdp.setJumlahTagihan(rs.getDouble(11));
            sdp.setKodeUser(rs.getString(12));
            sdp.setStatus(rs.getString(13));
            sdp.setTglBatal(rs.getString(14));
            sdp.setUserBatal(rs.getString(15));
            allSDP.add(sdp);
        }
        return allSDP;
    }
    public static List<SDP> getAllByDateAndStatus(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sdp "
                + " where left(tgl_sdp,10) between ? and ? and status=? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SDP> allSDP = new ArrayList<>();
        while(rs.next()){
            SDP sdp = new SDP();
            sdp.setNoSDP(rs.getString(1));
            sdp.setTglSDP(rs.getString(2));
            sdp.setNoSTJ(rs.getString(3));
            sdp.setKodeProperty(rs.getString(4));
            sdp.setKodeCustomer(rs.getString(5));
            sdp.setTahap(rs.getInt(6));
            sdp.setJumlahRp(rs.getDouble(7));
            sdp.setTipeKeuangan(rs.getString(8));
            sdp.setKodeSales(rs.getString(9));
            sdp.setJatuhTempo(rs.getString(10));
            sdp.setJumlahTagihan(rs.getDouble(11));
            sdp.setKodeUser(rs.getString(12));
            sdp.setStatus(rs.getString(13));
            sdp.setTglBatal(rs.getString(14));
            sdp.setUserBatal(rs.getString(15));
            allSDP.add(sdp);
        }
        return allSDP;
    }
    public static SDP get(Connection con,String noSDP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sdp where no_sdp=? ");
        ps.setString(1, noSDP);
        ResultSet rs = ps.executeQuery();
        SDP sdp = null;
        while(rs.next()){
            sdp = new SDP();
            sdp.setNoSDP(rs.getString(1));
            sdp.setTglSDP(rs.getString(2));
            sdp.setNoSTJ(rs.getString(3));
            sdp.setKodeProperty(rs.getString(4));
            sdp.setKodeCustomer(rs.getString(5));
            sdp.setTahap(rs.getInt(6));
            sdp.setJumlahRp(rs.getDouble(7));
            sdp.setTipeKeuangan(rs.getString(8));
            sdp.setKodeSales(rs.getString(9));
            sdp.setJatuhTempo(rs.getString(10));
            sdp.setJumlahTagihan(rs.getDouble(11));
            sdp.setKodeUser(rs.getString(12));
            sdp.setStatus(rs.getString(13));
            sdp.setTglBatal(rs.getString(14));
            sdp.setUserBatal(rs.getString(15));
        }
        return sdp;
    }
    public static String getId(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_sdp,3)) from tt_sdp where right(no_sdp,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/SDP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/SDP/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static void insert(Connection con, SDP sdp)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_sdp values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, sdp.getNoSDP());
        ps.setString(2, sdp.getTglSDP());
        ps.setString(3, sdp.getNoSTJ());
        ps.setString(4, sdp.getKodeProperty());
        ps.setString(5, sdp.getKodeCustomer());
        ps.setInt(6, sdp.getTahap());
        ps.setDouble(7, sdp.getJumlahRp());
        ps.setString(8, sdp.getTipeKeuangan());
        ps.setString(9, sdp.getKodeSales());
        ps.setString(10, sdp.getJatuhTempo());
        ps.setDouble(11, sdp.getJumlahTagihan());
        ps.setString(12, sdp.getKodeUser());
        ps.setString(13, sdp.getStatus());
        ps.setString(14, sdp.getTglBatal());
        ps.setString(15, sdp.getUserBatal());
        ps.executeUpdate();
    }
    public static  void update(Connection con,SDP sdp)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_sdp set "
                + " tgl_sdp=?, no_stj=?, kode_property=?, kode_customer=?, tahap=?, jumlah_rp=?, tipe_keuangan=?, "
                + " kode_sales=?, jatuh_tempo=?, jumlah_tagihan=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_sdp=?");
        ps.setString(1, sdp.getTglSDP());
        ps.setString(2, sdp.getNoSTJ());
        ps.setString(3, sdp.getKodeProperty());
        ps.setString(4, sdp.getKodeCustomer());
        ps.setInt(5, sdp.getTahap());
        ps.setDouble(6, sdp.getJumlahRp());
        ps.setString(7, sdp.getTipeKeuangan());
        ps.setString(8, sdp.getKodeSales());
        ps.setString(9, sdp.getJatuhTempo());
        ps.setDouble(10, sdp.getJumlahTagihan());
        ps.setString(11, sdp.getKodeUser());
        ps.setString(12, sdp.getStatus());
        ps.setString(13, sdp.getTglBatal());
        ps.setString(14, sdp.getUserBatal());
        ps.setString(15, sdp.getNoSDP());
        ps.executeUpdate();
    }
}
