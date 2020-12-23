/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembangunanKontraktorDetailDAO {
    
    public static List<PembangunanKontraktorDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_kontraktor_detail "
                + " where left(tanggal,10) between ? and ? and status = ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PembangunanKontraktorDetail> allPengelolaanTanahDetail = new ArrayList<>();
        while(rs.next()){
            PembangunanKontraktorDetail p = new PembangunanKontraktorDetail();
            p.setNoPembangunan(rs.getString(1));
            p.setNoUrut(rs.getInt(2));
            p.setTanggal(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahRp(rs.getDouble(5));
            p.setAddendum(rs.getString(6));
            p.setKodeUser(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
            allPengelolaanTanahDetail.add(p);
        }
        return allPengelolaanTanahDetail;
    }
    public static List<PembangunanKontraktorDetail> getAllByNoPembangunan(Connection con,String noPembangunan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembangunan_kontraktor_detail where no_pembangunan=?");
        ps.setString(1, noPembangunan);
        ResultSet rs = ps.executeQuery();
        List<PembangunanKontraktorDetail> allPengelolaanTanahDetail = new ArrayList<>();
        while(rs.next()){
            PembangunanKontraktorDetail p = new PembangunanKontraktorDetail();
            p.setNoPembangunan(rs.getString(1));
            p.setNoUrut(rs.getInt(2));
            p.setTanggal(rs.getString(3));
            p.setKeterangan(rs.getString(4));
            p.setJumlahRp(rs.getDouble(5));
            p.setAddendum(rs.getString(6));
            p.setKodeUser(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
            allPengelolaanTanahDetail.add(p);
        }
        return allPengelolaanTanahDetail;
    }
    public static void insert(Connection con, PembangunanKontraktorDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembangunan_kontraktor_detail values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPembangunan());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getTanggal());
        ps.setString(4, d.getKeterangan());
        ps.setDouble(5, d.getJumlahRp());
        ps.setString(6, d.getAddendum());
        ps.setString(7, d.getKodeUser());
        ps.setString(8, d.getStatus());
        ps.setString(9, d.getTglBatal());
        ps.setString(10, d.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembangunanKontraktorDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembangunan_kontraktor_detail set "
                + " tanggal=?, keterangan=?, jumlah_rp=?, addendum=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_pembangunan=? and no_urut=? ");
        ps.setString(1, d.getTanggal());
        ps.setString(2, d.getKeterangan());
        ps.setDouble(3, d.getJumlahRp());
        ps.setString(4, d.getAddendum());
        ps.setString(5, d.getKodeUser());
        ps.setString(6, d.getStatus());
        ps.setString(7, d.getTglBatal());
        ps.setString(8, d.getUserBatal());
        ps.setString(9, d.getNoPembangunan());
        ps.setInt(10, d.getNoUrut());
        ps.executeUpdate();
    }
}
