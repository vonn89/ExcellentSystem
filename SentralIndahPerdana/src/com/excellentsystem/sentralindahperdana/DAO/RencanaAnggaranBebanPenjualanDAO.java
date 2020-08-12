/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class RencanaAnggaranBebanPenjualanDAO {
    
    public static List<RencanaAnggaranBebanPenjualan> getAllByNoPenjualanAndNoUrut(Connection con, String noPenjualan, String noUrut)throws Exception{
        List<RencanaAnggaranBebanPenjualan> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_rencana_anggaran_beban_penjualan "
                + " where no_penjualan like ? and no_urut like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, noUrut);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    RencanaAnggaranBebanPenjualan detail = new RencanaAnggaranBebanPenjualan();
                    detail.setNoPenjualan(rs.getString(1));
                    detail.setNoUrut(rs.getString(2));
                    detail.setKategori(rs.getString(3));
                    detail.setJumlahRp(rs.getDouble(4));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static void insert(Connection con, RencanaAnggaranBebanPenjualan detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_rencana_anggaran_beban_penjualan "
                + "values(?,?,?,?)")) {
            ps.setString(1, detail.getNoPenjualan());
            ps.setString(2, detail.getNoUrut());
            ps.setString(3, detail.getKategori());
            ps.setDouble(4, detail.getJumlahRp());
            ps.executeUpdate();
        }
    }
}
