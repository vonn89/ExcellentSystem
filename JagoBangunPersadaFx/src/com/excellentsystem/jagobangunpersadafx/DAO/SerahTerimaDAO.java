/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.SerahTerima;
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
public class SerahTerimaDAO {
    
    public static SerahTerima getByKodeProperty(Connection con,String kodeProperty, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_serah_terima where kode_property=? and status=?");
        ps.setString(1, kodeProperty);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        SerahTerima st = null;
        while(rs.next()){
            st = new SerahTerima();
            st.setNoSerahTerima(rs.getString(1));
            st.setTglSerahTerima(rs.getString(2));
            st.setKodeProperty(rs.getString(3));
            st.setKodeCustomer(rs.getString(4));
            st.setHarga(rs.getDouble(5));
            st.setDiskon(rs.getDouble(6));
            st.setTotalDP(rs.getDouble(7));
            st.setTotalKPR(rs.getDouble(8));
            st.setNoHGB(rs.getString(9));
            st.setNoIMB(rs.getString(10));
            st.setKelengkapan(rs.getString(11));
            st.setBonus(rs.getString(12));
            st.setKodeUser(rs.getString(13));
            st.setStatus(rs.getString(14));
            st.setTglBatal(rs.getString(15));
            st.setUserBatal(rs.getString(16));
        }
        return st;
    }
    public static List<SerahTerima> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_serah_terima where  "+
                " left(tgl_serah_terima,10) between ? and ? and status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<SerahTerima> allST = new ArrayList<>();
        while(rs.next()){
            SerahTerima st = new SerahTerima();
            st.setNoSerahTerima(rs.getString(1));
            st.setTglSerahTerima(rs.getString(2));
            st.setKodeProperty(rs.getString(3));
            st.setKodeCustomer(rs.getString(4));
            st.setHarga(rs.getDouble(5));
            st.setDiskon(rs.getDouble(6));
            st.setTotalDP(rs.getDouble(7));
            st.setTotalKPR(rs.getDouble(8));
            st.setNoHGB(rs.getString(9));
            st.setNoIMB(rs.getString(10));
            st.setKelengkapan(rs.getString(11));
            st.setBonus(rs.getString(12));
            st.setKodeUser(rs.getString(13));
            st.setStatus(rs.getString(14));
            st.setTglBatal(rs.getString(15));
            st.setUserBatal(rs.getString(16));
            allST.add(st);
        }
        return allST;
    }
    public static SerahTerima get(Connection con,String noSKL)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_serah_terima where no_serah_terima=? ");
        ps.setString(1, noSKL);
        ResultSet rs = ps.executeQuery();
        SerahTerima st = null;
        while(rs.next()){
            st = new SerahTerima();
            st.setNoSerahTerima(rs.getString(1));
            st.setTglSerahTerima(rs.getString(2));
            st.setKodeProperty(rs.getString(3));
            st.setKodeCustomer(rs.getString(4));
            st.setHarga(rs.getDouble(5));
            st.setDiskon(rs.getDouble(6));
            st.setTotalDP(rs.getDouble(7));
            st.setTotalKPR(rs.getDouble(8));
            st.setNoHGB(rs.getString(9));
            st.setNoIMB(rs.getString(10));
            st.setKelengkapan(rs.getString(11));
            st.setBonus(rs.getString(12));
            st.setKodeUser(rs.getString(13));
            st.setStatus(rs.getString(14));
            st.setTglBatal(rs.getString(15));
            st.setUserBatal(rs.getString(16));
        }
        return st;
    }
    public static String getId(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(left(no_serah_terima,3)) from tt_serah_terima where right(no_serah_terima,4)=? ");
        ps.setString(1, new SimpleDateFormat("yyyy").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return new DecimalFormat("000").format(rs.getInt(1)+1)+"/BAST/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
        else
            return new DecimalFormat("000").format(1)+"/BAST/"+new SimpleDateFormat("MM").format(new Date())+"/"+kodeProperty+"/"+new SimpleDateFormat("yyyy").format(new Date());
    }
    public static void insert(Connection con, SerahTerima st)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_serah_terima values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, st.getNoSerahTerima());
        ps.setString(2, st.getTglSerahTerima());
        ps.setString(3, st.getKodeProperty());
        ps.setString(4, st.getKodeCustomer());
        ps.setDouble(5, st.getHarga());
        ps.setDouble(6, st.getDiskon());
        ps.setDouble(7, st.getTotalDP());
        ps.setDouble(8, st.getTotalKPR());
        ps.setString(9, st.getNoHGB());
        ps.setString(10, st.getNoIMB());
        ps.setString(11, st.getKelengkapan());
        ps.setString(12, st.getBonus());
        ps.setString(13, st.getKodeUser());
        ps.setString(14, st.getStatus());
        ps.setString(15, st.getTglBatal());
        ps.setString(16, st.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SerahTerima st)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_serah_terima set "
                + " tgl_serah_terima=?, kode_property=?, kode_customer=?, "
                + " harga=?, diskon=?, total_dp=?, total_kpr=?, "
                + " no_hgb=?, no_imb=?, kelengkapan=?, bonus=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? where no_serah_terima=?");
        ps.setString(1, st.getTglSerahTerima());
        ps.setString(2, st.getKodeProperty());
        ps.setString(3, st.getKodeCustomer());
        ps.setDouble(4, st.getHarga());
        ps.setDouble(5, st.getDiskon());
        ps.setDouble(6, st.getTotalDP());
        ps.setDouble(7, st.getTotalKPR());
        ps.setString(8, st.getNoHGB());
        ps.setString(9, st.getNoIMB());
        ps.setString(10, st.getKelengkapan());
        ps.setString(11, st.getBonus());
        ps.setString(12, st.getKodeUser());
        ps.setString(13, st.getStatus());
        ps.setString(14, st.getTglBatal());
        ps.setString(15, st.getUserBatal());
        ps.setString(16, st.getNoSerahTerima());
        ps.executeUpdate();
    }
}
