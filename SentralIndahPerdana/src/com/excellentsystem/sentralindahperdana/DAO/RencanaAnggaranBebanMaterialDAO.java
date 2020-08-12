/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class RencanaAnggaranBebanMaterialDAO {
    
    public static List<RencanaAnggaranBebanMaterial> getAllByNoPenjualanAndNoUrut(Connection con, String noPenjualan, String noUrut)throws Exception{
        List<RencanaAnggaranBebanMaterial> allDetail;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_rencana_anggaran_beban_material "
                + "where no_penjualan like ? and no_urut like ?")) {
            ps.setString(1, noPenjualan);
            ps.setString(2, noUrut);
            try (ResultSet rs = ps.executeQuery()) {
                allDetail = new ArrayList<>();
                while(rs.next()){
                    RencanaAnggaranBebanMaterial detail = new RencanaAnggaranBebanMaterial();
                    detail.setNoPenjualan(rs.getString(1));
                    detail.setNoUrut(rs.getString(2));
                    detail.setKodeBarang(rs.getString(3));
                    detail.setNamaBarang(rs.getString(4));
                    detail.setSatuan(rs.getString(5));
                    detail.setQty(rs.getDouble(6));
                    detail.setHargaBeli(rs.getDouble(7));
                    detail.setHargaJual(rs.getDouble(8));
                    allDetail.add(detail);
                }
            }
        }
        return allDetail;
    }
    public static void insert(Connection con, RencanaAnggaranBebanMaterial detail)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_rencana_anggaran_beban_material "
                + " values(?,?,?,?,?,?,?,?)")) {
            ps.setString(1, detail.getNoPenjualan());
            ps.setString(2, detail.getNoUrut());
            ps.setString(3, detail.getKodeBarang());
            ps.setString(4, detail.getNamaBarang());
            ps.setString(5, detail.getSatuan());
            ps.setDouble(6, detail.getQty());
            ps.setDouble(7, detail.getHargaBeli());
            ps.setDouble(8, detail.getHargaJual());
            ps.executeUpdate();
        }
    }
}
