/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.TempTransaksiCabang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class TempTransaksiCabangDAO {
    public static List<TempTransaksiCabang> getAllByDateAndCabangAndSales(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String kodeSales)throws Exception{
        String sql = "select * from temp_transaksi_cabang where tanggal between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!kodeSales.equals("%"))
            sql = sql + " and kode_sales = '"+kodeSales+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<TempTransaksiCabang> allTransaksiCabang = new ArrayList<>();
        while(rs.next()){
            TempTransaksiCabang temp = new TempTransaksiCabang();
            temp.setTanggal(rs.getString(1));
            temp.setKodeCabang(rs.getString(2));
            temp.setKodeSales(rs.getString(3));
            temp.setQtyPenjualan(rs.getInt(4));
            temp.setTotalPenjualan(rs.getDouble(5));
            temp.setQtyPembelian(rs.getInt(6));
            temp.setTotalPembelian(rs.getDouble(7));
            temp.setQtyTerimaHutang(rs.getInt(8));
            temp.setTotalTerimaHutang(rs.getDouble(9));
            temp.setQtyHutangLunas(rs.getInt(10));
            temp.setTotalHutangLunas(rs.getDouble(11));
            temp.setTotalHutangBunga(rs.getDouble(12));
            temp.setStatus(rs.getString(13));
            temp.setTglAmbil(rs.getString(14));
            allTransaksiCabang.add(temp);
        }
        return allTransaksiCabang;
    }
    public static TempTransaksiCabang get(Connection con, String tanggal, String kodeCabang, String kodeSales)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from temp_transaksi_cabang where tanggal = ?  and kode_cabang = ? and kode_sales = ?");
        ps.setString(1, tanggal);
        ps.setString(2, kodeCabang);
        ps.setString(3, kodeSales);
        ResultSet rs = ps.executeQuery();
        TempTransaksiCabang temp = null;
        while(rs.next()){
            temp = new TempTransaksiCabang();
            temp.setTanggal(rs.getString(1));
            temp.setKodeCabang(rs.getString(2));
            temp.setKodeSales(rs.getString(3));
            temp.setQtyPenjualan(rs.getInt(4));
            temp.setTotalPenjualan(rs.getDouble(5));
            temp.setQtyPembelian(rs.getInt(6));
            temp.setTotalPembelian(rs.getDouble(7));
            temp.setQtyTerimaHutang(rs.getInt(8));
            temp.setTotalTerimaHutang(rs.getDouble(9));
            temp.setQtyHutangLunas(rs.getInt(10));
            temp.setTotalHutangLunas(rs.getDouble(11));
            temp.setTotalHutangBunga(rs.getDouble(12));
            temp.setStatus(rs.getString(13));
            temp.setTglAmbil(rs.getString(14));
        }
        return temp;
    }
    public static void insert(Connection con, TempTransaksiCabang temp)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into temp_transaksi_cabang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, temp.getTanggal());
        ps.setString(2, temp.getKodeCabang());
        ps.setString(3, temp.getKodeSales());
        ps.setInt(4, temp.getQtyPenjualan());
        ps.setDouble(5, temp.getTotalPenjualan());
        ps.setInt(6, temp.getQtyPembelian());
        ps.setDouble(7, temp.getTotalPembelian());
        ps.setInt(8, temp.getQtyTerimaHutang());
        ps.setDouble(9, temp.getTotalTerimaHutang());
        ps.setInt(10, temp.getQtyHutangLunas());
        ps.setDouble(11, temp.getTotalHutangLunas());
        ps.setDouble(12, temp.getTotalHutangBunga());
        ps.setString(13, temp.getStatus());
        ps.setString(14, temp.getTglAmbil());
        ps.executeUpdate();
    }
    public static void update(Connection con, TempTransaksiCabang temp)throws Exception{
        PreparedStatement ps = con.prepareStatement("update temp_transaksi_cabang set "
                + " qty_penjualan=?, total_penjualan=?, qty_pembelian=?, total_pembelian=?, "
                + " qty_terima_hutang=?, total_terima_hutang=?, qty_hutang_lunas=?, total_hutang_lunas=?, total_hutang_bunga=? "
                + " where tanggal=? and kode_cabang=? and kode_sales=?");
        ps.setInt(1, temp.getQtyPenjualan());
        ps.setDouble(2, temp.getTotalPenjualan());
        ps.setInt(3, temp.getQtyPembelian());
        ps.setDouble(4, temp.getTotalPembelian());
        ps.setInt(5, temp.getQtyTerimaHutang());
        ps.setDouble(6, temp.getTotalTerimaHutang());
        ps.setInt(7, temp.getQtyHutangLunas());
        ps.setDouble(8, temp.getTotalHutangLunas());
        ps.setDouble(9, temp.getTotalHutangBunga());
        ps.setString(10, temp.getStatus());
        ps.setString(11, temp.getTglAmbil());
        ps.setString(12, temp.getTanggal());
        ps.setString(13, temp.getKodeCabang());
        ps.setString(14, temp.getKodeSales());
        ps.executeUpdate();
    }
    public static void deleteByTanggalAndCabang(Connection con, String tglAwal, String tglAkhir, String kodeCabang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from temp_transaksi_cabang where tanggal between ? and ? and kode_cabang like ?");
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ps.setString(3, kodeCabang);
        ps.executeUpdate();
    }
}
