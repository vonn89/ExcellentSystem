/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class ReturPembelianDetailDAO {
    public static List<ReturPembelianDetail> getAllByDateAndSupplierAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String supplier, String status)throws Exception{
        String sql = "select * from tt_retur_pembelian_detail where no_retur in ("
                + " select no_retur from tt_retur_pembelian_head "
                + " where mid(no_retur,4,6) between ? and ? ";
        if(!supplier.equals("%"))
            sql = sql + " and supplier = '"+supplier+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<ReturPembelianDetail> listReturPembelianDetail = new ArrayList<>();
        while(rs.next()){
            ReturPembelianDetail d = new ReturPembelianDetail();
            d.setNoRetur(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setHargaPersen(rs.getDouble(5));
            d.setBerat(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            listReturPembelianDetail.add(d);
        }
        return listReturPembelianDetail;
    }
    public static List<ReturPembelianDetail> getAllByNoRetur(Connection con, String noRetur)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_retur_pembelian_detail where no_retur = ? ");
        ps.setString(1, noRetur);
        ResultSet rs = ps.executeQuery();
        List<ReturPembelianDetail> listReturPembelianDetail = new ArrayList<>();
        while(rs.next()){
            ReturPembelianDetail d = new ReturPembelianDetail();
            d.setNoRetur(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeKategori(rs.getString(3));
            d.setKodeJenis(rs.getString(4));
            d.setHargaPersen(rs.getDouble(5));
            d.setBerat(rs.getDouble(6));
            d.setBeratPersen(rs.getDouble(7));
            listReturPembelianDetail.add(d);
        }
        return listReturPembelianDetail;
    }
    public static void insert(Connection con,ReturPembelianDetail retur)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_retur_pembelian_detail values(?,?,?,?,?,?,?)");
        ps.setString(1, retur.getNoRetur());
        ps.setInt(2, retur.getNoUrut());
        ps.setString(3, retur.getKodeKategori());
        ps.setString(4, retur.getKodeJenis());
        ps.setDouble(5, retur.getHargaPersen());
        ps.setDouble(6, retur.getBerat());
        ps.setDouble(7, retur.getBeratPersen());
        ps.executeUpdate();        
    }
}
