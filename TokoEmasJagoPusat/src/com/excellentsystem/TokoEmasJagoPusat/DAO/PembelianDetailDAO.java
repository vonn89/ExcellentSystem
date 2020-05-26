/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianDetailDAO {
    public static List<PembelianDetail> getAllByDateAndSupplierAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String supplier, String status)throws Exception{
        String sql = "select * from tt_pembelian_detail where no_pembelian in ("
                + " select no_pembelian from tt_pembelian_head "
                + " where mid(no_pembelian,4,6) between ? and ? ";
        if(!supplier.equals("%"))
            sql = sql + " and supplier = '"+supplier+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> listPembelianDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setPersentaseEmas(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            d.setHargaPersen(rs.getDouble(8));
            d.setTotalHarga(rs.getDouble(9));
            listPembelianDetail.add(d);
        }
        return listPembelianDetail;
    }
    public static List<PembelianDetail> getAllByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_detail where no_pembelian = ? ");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        List<PembelianDetail> listPembelianDetail = new ArrayList<>();
        while(rs.next()){
            PembelianDetail d = new PembelianDetail();
            d.setNoPembelian(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setBerat(rs.getDouble(5));
            d.setPersentaseEmas(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            d.setHargaPersen(rs.getDouble(8));
            d.setTotalHarga(rs.getDouble(9));
            listPembelianDetail.add(d);
        }
        return listPembelianDetail;
    }
    public static void insert(Connection con, PembelianDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_detail values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoPembelian());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeKategori());
        ps.setString(4, detail.getKodeJenis());
        ps.setDouble(5, detail.getBerat());
        ps.setDouble(6, detail.getPersentaseEmas());
        ps.setDouble(7, detail.getBeratPersen());
        ps.setDouble(8, detail.getHargaPersen());
        ps.setDouble(9, detail.getTotalHarga());
        ps.executeUpdate();
    }
}
