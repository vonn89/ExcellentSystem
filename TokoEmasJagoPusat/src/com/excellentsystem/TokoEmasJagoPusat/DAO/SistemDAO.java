/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Sistem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author yunaz
 */
public class SistemDAO {
    public static void update(Connection con, Sistem b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_system set "
                + " version=?, tgl_system=?, tgl_mulai_database=?, harga_emas=? ");
        ps.setString(1, b.getVersion());
        ps.setString(2, b.getTglSystem());
        ps.setString(3, b.getTglMulaiDatabase());
        ps.setDouble(4, b.getHargaEmas());
        ps.executeUpdate();
    }
    public static Sistem get(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_system");
        ResultSet rs = ps.executeQuery();
        Sistem s = new Sistem();
        while(rs.next()){
            s.setVersion(rs.getString(1));
            s.setTglSystem(rs.getString(2));
            s.setTglMulaiDatabase(rs.getString(3));
            s.setHargaEmas(rs.getDouble(4));
        }
        return s;
    }
//    public List<Transaksi> viewTransaksiOpen(Connection con, String jenisTransaksi,String kodeCabang)throws Exception{
//        List<Transaksi> allTransaksi = new ArrayList<>();
//        String sql1 = " select 'Setoran Cabang',no_setor,kode_cabang from tt_setoran_cabang where status='open' ";
//        String sql2 = " select 'Tambah Uang Cabang',no_tambah_uang,kode_cabang from tt_tambah_uang_cabang where status='open' ";
//        String sql3 = " select 'Balenan Cabang',no_ambil_barang,kode_cabang from tt_ambil_barang_head where status='open' ";
//        String sql4 = " select 'Pindah Barang',no_pindah,kode_cabang from tt_pindah_head where status='open' ";
//        String sql5 = " select 'Bayar SP',no_sp,kode_cabang from tt_sp_head where status='close' and bayar='open' ";
//        String sql6 = " select concat('Penjualan Cabang ',kode_cabang),no_penjualan,cabang_tujuan "
//                    + " from tt_penjualan_antar_cabang_head where status_terima='false' and status_batal='false' ";
//        String sqlunion = " union ";
//        if(!"Semua".equals(kodeCabang)){
//            sql1 = sql1 + " and kode_cabang ='"+kodeCabang+"' ";
//            sql2 = sql2 + " and kode_cabang ='"+kodeCabang+"' ";
//            sql3 = sql3 + " and kode_cabang ='"+kodeCabang+"' ";
//            sql4 = sql4 + " and kode_cabang ='"+kodeCabang+"' ";
//            sql5 = sql5 + " and kode_cabang ='"+kodeCabang+"' ";
//            sql6 = sql6 + " and kode_cabang ='"+kodeCabang+"' ";
//        }
//        String sql = sql1 + sqlunion + sql2 + sqlunion + sql3 + sqlunion + sql4 + sqlunion + sql5 + sqlunion + sql6;
//        if(jenisTransaksi.equals("Setoran Cabang"))
//            sql = sql1;
//        else if(jenisTransaksi.equals("Tambah Uang Cabang"))
//            sql = sql2;
//        else if(jenisTransaksi.equals("Balenan Cabang"))
//            sql = sql3;
//        else if(jenisTransaksi.equals("Pindah Barang"))
//            sql = sql4;
//        else if(jenisTransaksi.equals("Bayar SP"))
//            sql = sql5;
//        else if(jenisTransaksi.equals("Penjualan Cabang"))
//            sql = sql6;
//        PreparedStatement ps = con.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            Transaksi transaksi = new Transaksi(rs.getString(1), rs.getString(2), rs.getString(3));
//            allTransaksi.add(transaksi);
//        }
//        return allTransaksi;
//    }
}
