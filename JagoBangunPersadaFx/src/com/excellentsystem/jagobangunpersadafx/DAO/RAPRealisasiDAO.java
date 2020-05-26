/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class RAPRealisasiDAO {
    
    public static List<RAPRealisasi> getAllByDateAndStatus(Connection con, 
            String tglAwal, String tglAkhir, String statusApproval, String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_rap_realisasi where no_rap in ( "
                + " select no_rap from tt_rap_head where left(tgl_rap,10) between ? and ? ";
        if(!statusApproval.equals("%"))
            sql = sql + " and status_approval = '"+statusApproval+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<RAPRealisasi> allRAPRealisasi = new ArrayList<>();
        while(rs.next()){
            RAPRealisasi d = new RAPRealisasi();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setTglRealisasi(rs.getString(3));
            d.setKeterangan(rs.getString(4));
            d.setSatuan(rs.getString(5));
            d.setQty(rs.getDouble(6));
            d.setJumlahRp(rs.getDouble(7));
            d.setTipeKeuangan(rs.getString(8));
            d.setKodeUser(rs.getString(9));
            d.setStatus(rs.getString(10));
            d.setTglBatal(rs.getString(11));
            d.setUserBatal(rs.getString(12));
            allRAPRealisasi.add(d);
        }
        return allRAPRealisasi;
    }
    public static List<RAPRealisasi> getAllByNoRapAndStatus(Connection con, String noRap, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rap_realisasi where no_rap = ? and status like ?");
        ps.setString(1, noRap);
        ps.setString(2, status);
        ResultSet rs = ps.executeQuery();
        List<RAPRealisasi> allRAPRealisasi = new ArrayList<>();
        while(rs.next()){
            RAPRealisasi d = new RAPRealisasi();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setTglRealisasi(rs.getString(3));
            d.setKeterangan(rs.getString(4));
            d.setSatuan(rs.getString(5));
            d.setQty(rs.getDouble(6));
            d.setJumlahRp(rs.getDouble(7));
            d.setTipeKeuangan(rs.getString(8));
            d.setKodeUser(rs.getString(9));
            d.setStatus(rs.getString(10));
            d.setTglBatal(rs.getString(11));
            d.setUserBatal(rs.getString(12));
            allRAPRealisasi.add(d);
        }
        return allRAPRealisasi;
    }
    public static RAPRealisasi get(Connection con, String noRap, int noUrut)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rap_realisasi where no_rap=? and no_urut=?");
        ps.setString(1, noRap);
        ps.setInt(2, noUrut);
        ResultSet rs = ps.executeQuery();
        RAPRealisasi d = null;
        while(rs.next()){
            d = new RAPRealisasi();
            d.setNoRap(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setTglRealisasi(rs.getString(3));
            d.setKeterangan(rs.getString(4));
            d.setSatuan(rs.getString(5));
            d.setQty(rs.getDouble(6));
            d.setJumlahRp(rs.getDouble(7));
            d.setTipeKeuangan(rs.getString(8));
            d.setKodeUser(rs.getString(9));
            d.setStatus(rs.getString(10));
            d.setTglBatal(rs.getString(11));
            d.setUserBatal(rs.getString(12));
        }
        return d;
    }
    public static void insert(Connection con, RAPRealisasi d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rap_realisasi values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoRap());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getTglRealisasi());
        ps.setString(4, d.getKeterangan());
        ps.setString(5, d.getSatuan());
        ps.setDouble(6, d.getQty());
        ps.setDouble(7, d.getJumlahRp());
        ps.setString(8, d.getTipeKeuangan());
        ps.setString(9, d.getKodeUser());
        ps.setString(10, d.getStatus());
        ps.setString(11, d.getTglBatal());
        ps.setString(12, d.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, RAPRealisasi d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_rap_realisasi set "
                + " tgl_realisasi=?, keterangan=?, satuan=?, qty=?, jumlah_rp=?, "
                + " tipe_keuangan=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_rap=? and no_urut=?");
        ps.setString(1, d.getTglRealisasi());
        ps.setString(2, d.getKeterangan());
        ps.setString(3, d.getSatuan());
        ps.setDouble(4, d.getQty());
        ps.setDouble(5, d.getJumlahRp());
        ps.setString(6, d.getTipeKeuangan());
        ps.setString(7, d.getKodeUser());
        ps.setString(8, d.getStatus());
        ps.setString(9, d.getTglBatal());
        ps.setString(10, d.getUserBatal());
        ps.setString(11, d.getNoRap());
        ps.setInt(12, d.getNoUrut());
        ps.executeUpdate();
    }
}
