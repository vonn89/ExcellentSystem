/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PenjualanBahanHeadDAO {
    public static List<PenjualanBahanHead> getAllByNoPemesananAndStatus(
            Connection con, String noPemesanan, String status)throws Exception{
        String sql = "select * from tt_penjualan_coil_head where no_penjualan !='' ";
        if(!noPemesanan.equals("%"))
            sql = sql + " and no_pemesanan = '"+noPemesanan+"'";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PenjualanBahanHead> allPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanBahanHead p = new PenjualanBahanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeGudang(rs.getString(5));
            p.setJenisKendaraan(rs.getString(6));
            p.setNoPolisi(rs.getString(7));
            p.setSupir(rs.getString(8));
            p.setPaymentTerm(rs.getString(9));
            p.setKurs(rs.getDouble(10));
            p.setTotalPenjualan(rs.getDouble(11));
            p.setPembayaran(rs.getDouble(12));
            p.setSisaPembayaran(rs.getDouble(13));
            p.setCatatan(rs.getString(14));
            p.setKodeSales(rs.getString(15));
            p.setKodeUser(rs.getString(16));
            p.setTglBatal(rs.getDate(17).toString()+" "+rs.getTime(17).toString());
            p.setUserBatal(rs.getString(18));
            p.setStatus(rs.getString(19));
            allPenjualan.add(p);
        }
        return allPenjualan;
        
    }
    public static List<PenjualanBahanHead> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_coil_head where left(tgl_penjualan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PenjualanBahanHead> allPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanBahanHead p = new PenjualanBahanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeGudang(rs.getString(5));
            p.setJenisKendaraan(rs.getString(6));
            p.setNoPolisi(rs.getString(7));
            p.setSupir(rs.getString(8));
            p.setPaymentTerm(rs.getString(9));
            p.setKurs(rs.getDouble(10));
            p.setTotalPenjualan(rs.getDouble(11));
            p.setPembayaran(rs.getDouble(12));
            p.setSisaPembayaran(rs.getDouble(13));
            p.setCatatan(rs.getString(14));
            p.setKodeSales(rs.getString(15));
            p.setKodeUser(rs.getString(16));
            p.setTglBatal(rs.getDate(17).toString()+" "+rs.getTime(17).toString());
            p.setUserBatal(rs.getString(18));
            p.setStatus(rs.getString(19));
            allPenjualan.add(p);
        }
        return allPenjualan;
        
    }
    public static PenjualanBahanHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_coil_head where no_penjualan = ? ");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanBahanHead p = null;
        while(rs.next()){
            p = new PenjualanBahanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeGudang(rs.getString(5));
            p.setJenisKendaraan(rs.getString(6));
            p.setNoPolisi(rs.getString(7));
            p.setSupir(rs.getString(8));
            p.setPaymentTerm(rs.getString(9));
            p.setKurs(rs.getDouble(10));
            p.setTotalPenjualan(rs.getDouble(11));
            p.setPembayaran(rs.getDouble(12));
            p.setSisaPembayaran(rs.getDouble(13));
            p.setCatatan(rs.getString(14));
            p.setKodeSales(rs.getString(15));
            p.setKodeUser(rs.getString(16));
            p.setTglBatal(rs.getDate(17).toString()+" "+rs.getTime(17).toString());
            p.setUserBatal(rs.getString(18));
            p.setStatus(rs.getString(19));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,3)) "
                + " from tt_penjualan_coil_head where mid(no_penjualan,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PE-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PE-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PenjualanBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_coil_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getNoPemesanan());
        ps.setString(4, p.getKodeCustomer());
        ps.setString(5, p.getKodeGudang());
        ps.setString(6, p.getJenisKendaraan());
        ps.setString(7, p.getNoPolisi());
        ps.setString(8, p.getSupir());
        ps.setString(9, p.getPaymentTerm());
        ps.setDouble(10, p.getKurs());
        ps.setDouble(11, p.getTotalPenjualan());
        ps.setDouble(12, p.getPembayaran());
        ps.setDouble(13, p.getSisaPembayaran());
        ps.setString(14, p.getCatatan());
        ps.setString(15, p.getKodeSales());
        ps.setString(16, p.getKodeUser());
        ps.setString(17, p.getTglBatal());
        ps.setString(18, p.getUserBatal());
        ps.setString(19, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_coil_head set "
                + " tgl_penjualan=?, no_pemesanan=?, kode_customer=?, kode_gudang=?,"
                + " jenis_kendaraan=?, no_polisi=?, supir=?, payment_term=?, "
                + " kurs=?, total_penjualan=?, pembayaran=?, sisa_pembayaran=?, "
                + " catatan=?, kode_sales=?, kode_user=?, tgl_batal=?, user_batal=?, status=? where no_penjualan=?");
        ps.setString(1, p.getTglPenjualan());
        ps.setString(2, p.getNoPemesanan());
        ps.setString(3, p.getKodeCustomer());
        ps.setString(4, p.getKodeGudang());
        ps.setString(5, p.getJenisKendaraan());
        ps.setString(6, p.getNoPolisi());
        ps.setString(7, p.getSupir());
        ps.setString(8, p.getPaymentTerm());
        ps.setDouble(9, p.getKurs());
        ps.setDouble(10, p.getTotalPenjualan());
        ps.setDouble(11, p.getPembayaran());
        ps.setDouble(12, p.getSisaPembayaran());
        ps.setString(13, p.getCatatan());
        ps.setString(14, p.getKodeSales());
        ps.setString(15, p.getKodeUser());
        ps.setString(16, p.getTglBatal());
        ps.setString(17, p.getUserBatal());
        ps.setString(18, p.getStatus());
        ps.setString(19, p.getNoPenjualan());
        ps.executeUpdate();
    }
}
