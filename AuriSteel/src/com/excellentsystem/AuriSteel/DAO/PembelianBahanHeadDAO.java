/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
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
public class PembelianBahanHeadDAO {
    public static List<PembelianBahanHead> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_head where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
             sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PembelianBahanHead> allPembelian = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PembelianBahanHead p = new PembelianBahanHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeSupplier(rs.getString(4));
            p.setKodeGudang(rs.getString(5));
            p.setPaymentTerm(rs.getString(6));
            p.setTotalPembelian(rs.getDouble(7));
            p.setTotalBebanPembelian(rs.getDouble(8));
            p.setGrandtotal(rs.getDouble(9));
            p.setPembayaran(rs.getDouble(10));
            p.setSisaPembayaran(rs.getDouble(11));
            p.setCatatan(rs.getString(12));
            p.setKodeUser(rs.getString(13));
            p.setTglBatal(rs.getDate(14).toString()+" "+rs.getTime(14).toString());
            p.setUserBatal(rs.getString(15));
            p.setStatus(rs.getString(16));
            allPembelian.add(p);
        }
        return allPembelian;
    }
    public static PembelianBahanHead get(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head where no_pembelian = ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        PembelianBahanHead p =null;
        while(rs.next()){
            p = new PembelianBahanHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeSupplier(rs.getString(4));
            p.setKodeGudang(rs.getString(5));
            p.setPaymentTerm(rs.getString(6));
            p.setTotalPembelian(rs.getDouble(7));
            p.setTotalBebanPembelian(rs.getDouble(8));
            p.setGrandtotal(rs.getDouble(9));
            p.setPembayaran(rs.getDouble(10));
            p.setSisaPembayaran(rs.getDouble(11));
            p.setCatatan(rs.getString(12));
            p.setKodeUser(rs.getString(13));
            p.setTglBatal(rs.getDate(14).toString()+" "+rs.getTime(14).toString());
            p.setUserBatal(rs.getString(15));
            p.setStatus(rs.getString(16));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembelian,3)) from tt_pembelian_head "
                + " where mid(no_pembelian,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PO-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PO-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PembelianBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembelian());
        ps.setString(2, p.getTglPembelian());
        ps.setString(3, p.getNoPemesanan());
        ps.setString(4, p.getKodeSupplier());
        ps.setString(5, p.getKodeGudang());
        ps.setString(6, p.getPaymentTerm());
        ps.setDouble(7, p.getTotalPembelian());
        ps.setDouble(8, p.getTotalBebanPembelian());
        ps.setDouble(9, p.getGrandtotal());
        ps.setDouble(10, p.getPembayaran());
        ps.setDouble(11, p.getSisaPembayaran());
        ps.setString(12, p.getCatatan());
        ps.setString(13, p.getKodeUser());
        ps.setString(14, p.getTglBatal());
        ps.setString(15, p.getUserBatal());
        ps.setString(16, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_head set "
                + " tgl_pembelian=?, no_pemesanan=?, kode_supplier=?, kode_gudang=?, payment_term=?, "
                + " total_pembelian=?, total_beban_pembelian=?, grandtotal=?, "
                + " pembayaran=?, sisa_pembayaran=?, catatan=?, "
                + " kode_user=?, tgl_batal=?, user_batal=?, status=? where no_pembelian=?");
        ps.setString(1, p.getTglPembelian());
        ps.setString(2, p.getNoPemesanan());
        ps.setString(3, p.getKodeSupplier());
        ps.setString(4, p.getKodeGudang());
        ps.setString(5, p.getPaymentTerm());
        ps.setDouble(6, p.getTotalPembelian());
        ps.setDouble(7, p.getTotalBebanPembelian());
        ps.setDouble(8, p.getGrandtotal());
        ps.setDouble(9, p.getPembayaran());
        ps.setDouble(10, p.getSisaPembayaran());
        ps.setString(11, p.getCatatan());
        ps.setString(12, p.getKodeUser());
        ps.setString(13, p.getTglBatal());
        ps.setString(14, p.getUserBatal());
        ps.setString(15, p.getStatus());
        ps.setString(16, p.getNoPembelian());
        ps.executeUpdate();
    }
}
