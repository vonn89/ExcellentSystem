/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.KPR;
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
public class KPRDAO {
    
    public static List<KPR> getAllByDate(Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_kpr where  "
                + " left(tgl_kpr,10) between ? and ? and status = ?");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<KPR> allKPR = new ArrayList<>();
        while(rs.next()){
            KPR kpr = new KPR();
            kpr.setNoKPR(rs.getString(1));
            kpr.setTglKPR(rs.getString(2));
            kpr.setNoSKL(rs.getString(3));
            kpr.setKodeCustomer(rs.getString(4));
            kpr.setKodeProperty(rs.getString(5));
            kpr.setJumlahRp(rs.getDouble(6));
            kpr.setKeterangan(rs.getString(7));
            kpr.setTipeKeuangan(rs.getString(8));
            kpr.setKodeUser(rs.getString(9));
            kpr.setStatus(rs.getString(10));
            kpr.setTglBatal(rs.getString(11));
            kpr.setUserBatal(rs.getString(12));
            allKPR.add(kpr);
        }
        return allKPR;
    }
    public static KPR getByKodeProperty(Connection con, String kodeProperty)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_kpr where kode_property=? and status='true'");
        ps.setString(1, kodeProperty);
        ResultSet rs = ps.executeQuery();
        KPR kpr = null;
        while(rs.next()){
            kpr = new KPR();
            kpr.setNoKPR(rs.getString(1));
            kpr.setTglKPR(rs.getString(2));
            kpr.setNoSKL(rs.getString(3));
            kpr.setKodeCustomer(rs.getString(4));
            kpr.setKodeProperty(rs.getString(5));
            kpr.setJumlahRp(rs.getDouble(6));
            kpr.setKeterangan(rs.getString(7));
            kpr.setTipeKeuangan(rs.getString(8));
            kpr.setKodeUser(rs.getString(9));
            kpr.setStatus(rs.getString(10));
            kpr.setTglBatal(rs.getString(11));
            kpr.setUserBatal(rs.getString(12));
        }
        return kpr;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_kpr,3)) from tt_kpr where left(no_kpr,8)=? ");
        ps.setString(1, "KPR-"+new SimpleDateFormat("yyMM").format(new Date()));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "KPR-"+new SimpleDateFormat("yyMM").format(new Date())+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "KPR-"+new SimpleDateFormat("yyMM").format(new Date()+new DecimalFormat("000").format(1));
    }
    public static  void insert(Connection con, KPR kpr)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_kpr values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, kpr.getNoKPR());
        ps.setString(2, kpr.getTglKPR());
        ps.setString(3, kpr.getNoSKL());
        ps.setString(4, kpr.getKodeCustomer());
        ps.setString(5, kpr.getKodeProperty());
        ps.setDouble(6, kpr.getJumlahRp());
        ps.setString(7, kpr.getKeterangan());
        ps.setString(8, kpr.getTipeKeuangan());
        ps.setString(9, kpr.getKodeUser());
        ps.setString(10, kpr.getStatus());
        ps.setString(11, kpr.getTglBatal());
        ps.setString(12, kpr.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,KPR kpr)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_kpr set "
                + " tgl_kpr=?, no_skl=?, kode_customer=?, "
                + " kode_property=?, jumlah_rp=?, keterangan=?, "
                + " tipe_keuangan=?, kode_user=?, status=?, "
                + " tgl_batal=?, user_batal=? where no_kpr=?");
        ps.setString(1, kpr.getTglKPR());
        ps.setString(2, kpr.getNoSKL());
        ps.setString(3, kpr.getKodeCustomer());
        ps.setString(4, kpr.getKodeProperty());
        ps.setDouble(5, kpr.getJumlahRp());
        ps.setString(6, kpr.getKeterangan());
        ps.setString(7, kpr.getTipeKeuangan());
        ps.setString(8, kpr.getKodeUser());
        ps.setString(9, kpr.getStatus());
        ps.setString(10, kpr.getTglBatal());
        ps.setString(11, kpr.getUserBatal());
        ps.setString(12, kpr.getNoKPR());
        ps.executeUpdate();
    }
}
