/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class PemesananPembelianBahanHeadDAO {
    
    public static List<PemesananPembelianBahanHead> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pemesanan_pembelian_bahan_head where left(tgl_pemesanan,10) between ? and ? ";
        if(!status.equals("%")) 
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PemesananPembelianBahanHead> allPemesanan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PemesananPembelianBahanHead p = new PemesananPembelianBahanHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeSupplier(rs.getString(3));
            p.setNoKontrak(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setDeliveryTerm(rs.getString(6));
            p.setTotalPemesanan(rs.getDouble(7));
            p.setDownPayment(rs.getDouble(8));
            p.setSisaDownPayment(rs.getDouble(9));
            p.setCatatan(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setTglBatal(rs.getDate(12).toString()+" "+rs.getTime(12).toString());
            p.setUserBatal(rs.getString(13));
            p.setStatus(rs.getString(14));
            allPemesanan.add(p);
        }
        return allPemesanan;
    }
    public static PemesananPembelianBahanHead get(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_pembelian_bahan_head where no_pemesanan = ? ");
        ps.setString(1, noPemesanan);
        PemesananPembelianBahanHead p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PemesananPembelianBahanHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeSupplier(rs.getString(3));
            p.setNoKontrak(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setDeliveryTerm(rs.getString(6));
            p.setTotalPemesanan(rs.getDouble(7));
            p.setDownPayment(rs.getDouble(8));
            p.setSisaDownPayment(rs.getDouble(9));
            p.setCatatan(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setTglBatal(rs.getDate(12).toString()+" "+rs.getTime(12).toString());
            p.setUserBatal(rs.getString(13));
            p.setStatus(rs.getString(14));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pemesanan,3)) from tt_pemesanan_pembelian_bahan_head "
                + " where mid(no_pemesanan,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PU-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PU-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PemesananPembelianBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_pembelian_bahan_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPemesanan());
        ps.setString(2, p.getTglPemesanan());
        ps.setString(3, p.getKodeSupplier());
        ps.setString(4, p.getNoKontrak());
        ps.setString(5, p.getPaymentTerm());
        ps.setString(6, p.getDeliveryTerm());
        ps.setDouble(7, p.getTotalPemesanan());
        ps.setDouble(8, p.getDownPayment());
        ps.setDouble(9, p.getSisaDownPayment());
        ps.setString(10, p.getCatatan()); 
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.setString(14, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PemesananPembelianBahanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pemesanan_pembelian_bahan_head set "
                + " tgl_pemesanan=?, kode_supplier=?, no_kontrak=?, payment_term=?, delivery_term=?, "
                + " total_pemesanan=?, down_payment=?, sisa_down_payment=?, "
                + " catatan=?, kode_user=?, tgl_batal=?, user_batal=?, status=? where no_pemesanan=?");
        ps.setString(1, p.getTglPemesanan());
        ps.setString(2, p.getKodeSupplier());
        ps.setString(3, p.getNoKontrak());
        ps.setString(4, p.getPaymentTerm());
        ps.setString(5, p.getDeliveryTerm());
        ps.setDouble(6, p.getTotalPemesanan());
        ps.setDouble(7, p.getDownPayment());
        ps.setDouble(8, p.getSisaDownPayment());
        ps.setString(9, p.getCatatan());
        ps.setString(10, p.getKodeUser());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.setString(13, p.getStatus());
        ps.setString(14, p.getNoPemesanan());
        ps.executeUpdate();
    }
}
