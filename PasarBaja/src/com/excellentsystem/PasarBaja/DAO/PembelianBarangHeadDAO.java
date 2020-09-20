/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.DAO;

import static com.excellentsystem.PasarBaja.Main.yymm;
import com.excellentsystem.PasarBaja.Model.PembelianBarangHead;
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
public class PembelianBarangHeadDAO {
    public static List<PembelianBarangHead> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pembelian_barang_head where left(tgl_pembelian,10) between ? and ? ";
        if(!status.equals("%"))
             sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PembelianBarangHead> allPembelian = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PembelianBarangHead p = new PembelianBarangHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeSupplier(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setTotalPembelian(rs.getDouble(6));
            p.setTotalBebanPembelian(rs.getDouble(7));
            p.setGrandtotal(rs.getDouble(8));
            p.setPembayaran(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setCatatan(rs.getString(11));
            p.setKodeUser(rs.getString(12));
            p.setTglBatal(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
            p.setUserBatal(rs.getString(14));
            p.setStatus(rs.getString(15));
            allPembelian.add(p);
        }
        return allPembelian;
    }
    public static PembelianBarangHead get(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_barang_head where no_pembelian = ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        PembelianBarangHead p =null;
        while(rs.next()){
            p = new PembelianBarangHead();
            p.setNoPembelian(rs.getString(1));
            p.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeSupplier(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setTotalPembelian(rs.getDouble(6));
            p.setTotalBebanPembelian(rs.getDouble(7));
            p.setGrandtotal(rs.getDouble(8));
            p.setPembayaran(rs.getDouble(9));
            p.setSisaPembayaran(rs.getDouble(10));
            p.setCatatan(rs.getString(11));
            p.setKodeUser(rs.getString(12));
            p.setTglBatal(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
            p.setUserBatal(rs.getString(14));
            p.setStatus(rs.getString(15));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembelian,3)) from tt_pembelian_barang_head "
                + " where mid(no_pembelian,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PB-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PB-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PembelianBarangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_barang_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPembelian());
        ps.setString(2, p.getTglPembelian());
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
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianBarangHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_barang_head set "
                + " tgl_pembelian=?, kode_supplier=?, kode_gudang=?, payment_term=?, "
                + " total_pembelian=?, total_beban_pembelian=?, grandtotal=?, "
                + " pembayaran=?, sisa_pembayaran=?, catatan=?, "
                + " kode_user=?, tgl_batal=?, user_batal=?, status=? where no_pembelian=?");
        ps.setString(1, p.getTglPembelian());
        ps.setString(2, p.getKodeSupplier());
        ps.setString(3, p.getKodeGudang());
        ps.setString(4, p.getPaymentTerm());
        ps.setDouble(5, p.getTotalPembelian());
        ps.setDouble(6, p.getTotalBebanPembelian());
        ps.setDouble(7, p.getGrandtotal());
        ps.setDouble(8, p.getPembayaran());
        ps.setDouble(9, p.getSisaPembayaran());
        ps.setString(10, p.getCatatan());
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.setString(14, p.getStatus());
        ps.setString(15, p.getNoPembelian());
        ps.executeUpdate();
    }
}
