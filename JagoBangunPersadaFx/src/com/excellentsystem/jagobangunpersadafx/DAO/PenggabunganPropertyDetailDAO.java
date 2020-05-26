/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenggabunganPropertyDetailDAO {
    
    public List<PenggabunganPropertyDetail> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penggabungan_property_detail where no_penggabungan in ("
                + " select no_penggabungan from tt_penggabungan_property_head where left(tgl_penggabungan,10) between ? and ? and status = ? )");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ps.setString(3, status);
        ResultSet rs = ps.executeQuery();
        List<PenggabunganPropertyDetail> allPenggabunganPropertyDetail = new ArrayList<>();
        while(rs.next()){
            PenggabunganPropertyDetail p = new PenggabunganPropertyDetail();
            p.setNoPenggabungan(rs.getString(1));
            p.setKodeProperty(rs.getString(2));
            allPenggabunganPropertyDetail.add(p);
        }
        return allPenggabunganPropertyDetail;
    }
    public List<PenggabunganPropertyDetail> getAllByNoPenggabungan(Connection con,String noPenggabungan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penggabungan_property_detail where no_penggabungan=?");
        ps.setString(1, noPenggabungan);
        ResultSet rs = ps.executeQuery();
        List<PenggabunganPropertyDetail> allPenggabunganPropertyDetail = new ArrayList<>();
        while(rs.next()){
            PenggabunganPropertyDetail p = new PenggabunganPropertyDetail();
            p.setNoPenggabungan(rs.getString(1));
            p.setKodeProperty(rs.getString(2));
            allPenggabunganPropertyDetail.add(p);
        }
        return allPenggabunganPropertyDetail;
    }
    public static void insert(Connection con, PenggabunganPropertyDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penggabungan_property_detail values(?,?)");
        ps.setString(1, d.getNoPenggabungan());
        ps.setString(2, d.getKodeProperty());
        ps.executeUpdate();
    }
}
