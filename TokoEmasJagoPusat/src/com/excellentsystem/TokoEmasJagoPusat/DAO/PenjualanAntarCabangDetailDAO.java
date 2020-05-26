/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanAntarCabangDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class PenjualanAntarCabangDetailDAO {
    public static PenjualanAntarCabangDetail get(
            Connection con, String tglMulai, String tglAkhir, String kodeBarcode, String kodeCabang, String cabangTujuan,
            String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_penjualan_antar_cabang_detail "
                + " where no_penjualan in (select no_penjualan from tt_penjualan_antar_cabang_head "
                + " where mid(no_penjualan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!cabangTujuan.equals("%"))
            sql = sql + " and cabang_tujuan = '"+cabangTujuan+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        if(!kodeBarcode.equals("%"))
            sql = sql + " and kode_barcode = '"+kodeBarcode+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        PenjualanAntarCabangDetail d = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            d = new PenjualanAntarCabangDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setKodeBarcode(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setKodeKategori(rs.getString(6));
            d.setKodeJenis(rs.getString(7));
            d.setKodeIntern(rs.getString(8));
            d.setKadar(rs.getString(9));
            d.setBerat(rs.getDouble(10));
            d.setBeratAsli(rs.getDouble(11));
            d.setBeratPersen(rs.getDouble(12));
            d.setNilaiPokok(rs.getDouble(13));
            d.setInputDate(rs.getString(14));
            d.setInputBy(rs.getString(15));
            d.setAsalBarang(rs.getString(16));
            d.setStatus(rs.getString(17));
            d.setHarga(rs.getDouble(18));
            d.setKodeBarcodeBaru(rs.getString(19));
        }
        return d;
    }
    public static List<PenjualanAntarCabangDetail> getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String cabangTujuan,
            String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_penjualan_antar_cabang_detail "
                + " where no_penjualan in (select no_penjualan from tt_penjualan_antar_cabang_head "
                + " where mid(no_penjualan,8,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!cabangTujuan.equals("%"))
            sql = sql + " and cabang_tujuan = '"+cabangTujuan+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<PenjualanAntarCabangDetail> listDetail = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanAntarCabangDetail d = new PenjualanAntarCabangDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setKodeBarcode(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setKodeKategori(rs.getString(6));
            d.setKodeJenis(rs.getString(7));
            d.setKodeIntern(rs.getString(8));
            d.setKadar(rs.getString(9));
            d.setBerat(rs.getDouble(10));
            d.setBeratAsli(rs.getDouble(11));
            d.setBeratPersen(rs.getDouble(12));
            d.setNilaiPokok(rs.getDouble(13));
            d.setInputDate(rs.getString(14));
            d.setInputBy(rs.getString(15));
            d.setAsalBarang(rs.getString(16));
            d.setStatus(rs.getString(17));
            d.setHarga(rs.getDouble(18));
            d.setKodeBarcodeBaru(rs.getString(19));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static List<PenjualanAntarCabangDetail> getAllByNoPenjualan(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_antar_cabang_detail "
                + "where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        List<PenjualanAntarCabangDetail> listDetail = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanAntarCabangDetail d = new PenjualanAntarCabangDetail();
            d.setNoPenjualan(rs.getString(1));
            d.setNoUrut(rs.getInt(2));
            d.setKodeBarang(rs.getString(3));
            d.setKodeBarcode(rs.getString(4));
            d.setNamaBarang(rs.getString(5));
            d.setKodeKategori(rs.getString(6));
            d.setKodeJenis(rs.getString(7));
            d.setKodeIntern(rs.getString(8));
            d.setKadar(rs.getString(9));
            d.setBerat(rs.getDouble(10));
            d.setBeratAsli(rs.getDouble(11));
            d.setBeratPersen(rs.getDouble(12));
            d.setNilaiPokok(rs.getDouble(13));
            d.setInputDate(rs.getString(14));
            d.setInputBy(rs.getString(15));
            d.setAsalBarang(rs.getString(16));
            d.setStatus(rs.getString(17));
            d.setHarga(rs.getDouble(18));
            d.setKodeBarcodeBaru(rs.getString(19));
            listDetail.add(d);
        }
        return listDetail;
    }
    public static void insert(Connection con, PenjualanAntarCabangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_antar_cabang_detail "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, d.getNoPenjualan());
        ps.setInt(2, d.getNoUrut());
        ps.setString(3, d.getKodeBarang());
        ps.setString(4, d.getKodeBarcode());
        ps.setString(5, d.getNamaBarang());
        ps.setString(6, d.getKodeKategori());
        ps.setString(7, d.getKodeJenis());
        ps.setString(8, d.getKodeIntern());
        ps.setString(9, d.getKadar());
        ps.setDouble(10, d.getBerat());
        ps.setDouble(11, d.getBeratAsli());
        ps.setDouble(12, d.getBeratPersen());
        ps.setDouble(13, d.getNilaiPokok());
        ps.setString(14, d.getInputDate());
        ps.setString(15, d.getInputBy());
        ps.setString(16, d.getAsalBarang());
        ps.setString(17, d.getStatus());
        ps.setDouble(18, d.getHarga());
        ps.setString(19, d.getKodeBarcodeBaru());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanAntarCabangDetail d)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_antar_cabang_detail set "
                + " kode_barang = ?, kode_barcode = ?, nama_barang = ?, kode_kategori = ?, kode_jenis = ?, "
                + " kode_intern = ?, kadar = ?, berat = ?, berat_asli = ?, berat_persen = ?, nilai_pokok = ?, "
                + " input_date = ?, input_by = ?, asal_barang = ?, status = ?, harga = ?, kode_barcode_baru = ? "
                + " where no_penjualan = ? and no_urut = ? ");
        ps.setString(1, d.getKodeBarang());
        ps.setString(2, d.getKodeBarcode());
        ps.setString(3, d.getNamaBarang());
        ps.setString(4, d.getKodeKategori());
        ps.setString(5, d.getKodeJenis());
        ps.setString(6, d.getKodeIntern());
        ps.setString(7, d.getKadar());
        ps.setDouble(8, d.getBerat());
        ps.setDouble(9, d.getBeratAsli());
        ps.setDouble(10, d.getBeratPersen());
        ps.setDouble(11, d.getNilaiPokok());
        ps.setString(12, d.getInputDate());
        ps.setString(13, d.getInputBy());
        ps.setString(14, d.getAsalBarang());
        ps.setString(15, d.getStatus());
        ps.setDouble(16, d.getHarga());
        ps.setString(17, d.getKodeBarcodeBaru());
        ps.setString(18, d.getNoPenjualan());
        ps.setInt(19, d.getNoUrut());
        ps.executeUpdate();
    }
}
