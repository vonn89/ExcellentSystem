/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.DAO;

import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PemesananHead;
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
public class PemesananHeadDAO {
    public static List<PemesananHead> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pemesanan_head where left(tgl_pemesanan,10) between ? and ? ";
        if(!status.equals("%")) 
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PemesananHead> allPemesanan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PemesananHead p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeCustomer(rs.getString(3));
            p.setKodeCustomerInvoice(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setTotalPemesanan(rs.getDouble(6));
            p.setDownPayment(rs.getDouble(7));
            p.setSisaDownPayment(rs.getDouble(8));
            p.setCatatan(rs.getString(9));
            p.setKodeSales(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setTglBatal(rs.getDate(12).toString()+" "+rs.getTime(12).toString());
            p.setUserBatal(rs.getString(13));
            p.setStatus(rs.getString(14));
            allPemesanan.add(p);
        }
        return allPemesanan;
    }
    public static PemesananHead get(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_head where no_pemesanan = ? ");
        ps.setString(1, noPemesanan);
        PemesananHead p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PemesananHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeCustomer(rs.getString(3));
            p.setKodeCustomerInvoice(rs.getString(4));
            p.setPaymentTerm(rs.getString(5));
            p.setTotalPemesanan(rs.getDouble(6));
            p.setDownPayment(rs.getDouble(7));
            p.setSisaDownPayment(rs.getDouble(8));
            p.setCatatan(rs.getString(9));
            p.setKodeSales(rs.getString(10));
            p.setKodeUser(rs.getString(11));
            p.setTglBatal(rs.getDate(12).toString()+" "+rs.getTime(12).toString());
            p.setUserBatal(rs.getString(13));
            p.setStatus(rs.getString(14));
        }
        return p;
    }
    public static String getId(Connection con, Date date)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pemesanan,3)) from tt_pemesanan_head "
                + " where mid(no_pemesanan,4,4) = ?");
        ps.setString(1, yymm.format(date));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PI-"+yymm.format(date)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PI-"+yymm.format(date)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PemesananHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPemesanan());
        ps.setString(2, p.getTglPemesanan());
        ps.setString(3, p.getKodeCustomer());
        ps.setString(4, p.getKodeCustomerInvoice());
        ps.setString(5, p.getPaymentTerm());
        ps.setDouble(6, p.getTotalPemesanan());
        ps.setDouble(7, p.getDownPayment());
        ps.setDouble(8, p.getSisaDownPayment());
        ps.setString(9, p.getCatatan()); 
        ps.setString(10, p.getKodeSales());
        ps.setString(11, p.getKodeUser());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.setString(14, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PemesananHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pemesanan_head set "
                + " tgl_pemesanan=?, kode_customer=?, kode_customer_invoice=?, payment_term=?, "
                + " total_pemesanan=?, down_payment=?, sisa_down_payment=?, "
                + " catatan=?, kode_sales=?, kode_user=?, "
                + " tgl_batal=?, user_batal=?, status=? where no_pemesanan=?");
        ps.setString(1, p.getTglPemesanan());
        ps.setString(2, p.getKodeCustomer());
        ps.setString(3, p.getKodeCustomerInvoice());
        ps.setString(4, p.getPaymentTerm());
        ps.setDouble(5, p.getTotalPemesanan());
        ps.setDouble(6, p.getDownPayment());
        ps.setDouble(7, p.getSisaDownPayment());
        ps.setString(8, p.getCatatan());
        ps.setString(9, p.getKodeSales());
        ps.setString(10, p.getKodeUser());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.setString(13, p.getStatus());
        ps.setString(14, p.getNoPemesanan());
        ps.executeUpdate();
    }
}
