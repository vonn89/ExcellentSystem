/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.DAO;

import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Main.yymm;
import com.excellentsystem.AuriSteel.Model.PenjualanHead;
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
public class PenjualanHeadDAO {
    public static List<PenjualanHead> getAllByNoPemesananAndStatus(Connection con, String noPemesanan, String status)throws Exception{
        String sql = "select * from tt_penjualan_head where no_penjualan !='' ";
        if(!noPemesanan.equals("%"))
            sql = sql + " and no_pemesanan = '"+noPemesanan+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        List<PenjualanHead> allPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanHead p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeCustomerInvoice(rs.getString(5));
            p.setKodeGudang(rs.getString(6));
            p.setTujuanKirim(rs.getString(7));
            p.setJenisPengiriman(rs.getString(8));
            p.setSupir(rs.getString(9));
            p.setPaymentTerm(rs.getString(10));
            p.setTotalBebanPenjualan(rs.getDouble(11));
            p.setTotalPenjualan(rs.getDouble(12));
            p.setPembayaran(rs.getDouble(13));
            p.setSisaPembayaran(rs.getDouble(14));
            p.setCatatan(rs.getString(15));
            p.setKodeSales(rs.getString(16));
            p.setKodeUser(rs.getString(17));
            p.setTglBatal(rs.getDate(18).toString()+" "+rs.getTime(18).toString());
            p.setUserBatal(rs.getString(19));
            p.setStatus(rs.getString(20));
            allPenjualan.add(p);
        }
        return allPenjualan;
        
    }
    public static List<PenjualanHead> getAllByDateAndStatus(Connection con, String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_head where left(tgl_penjualan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PenjualanHead> allPenjualan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PenjualanHead p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeCustomerInvoice(rs.getString(5));
            p.setKodeGudang(rs.getString(6));
            p.setTujuanKirim(rs.getString(7));
            p.setJenisPengiriman(rs.getString(8));
            p.setSupir(rs.getString(9));
            p.setPaymentTerm(rs.getString(10));
            p.setTotalBebanPenjualan(rs.getDouble(11));
            p.setTotalPenjualan(rs.getDouble(12));
            p.setPembayaran(rs.getDouble(13));
            p.setSisaPembayaran(rs.getDouble(14));
            p.setCatatan(rs.getString(15));
            p.setKodeSales(rs.getString(16));
            p.setKodeUser(rs.getString(17));
            p.setTglBatal(rs.getDate(18).toString()+" "+rs.getTime(18).toString());
            p.setUserBatal(rs.getString(19));
            p.setStatus(rs.getString(20));
            allPenjualan.add(p);
        }
        return allPenjualan;
        
    }
    public static PenjualanHead get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_head where no_penjualan = ?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanHead p = null;
        while(rs.next()){
            p = new PenjualanHead();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setNoPemesanan(rs.getString(3));
            p.setKodeCustomer(rs.getString(4));
            p.setKodeCustomerInvoice(rs.getString(5));
            p.setKodeGudang(rs.getString(6));
            p.setTujuanKirim(rs.getString(7));
            p.setJenisPengiriman(rs.getString(8));
            p.setSupir(rs.getString(9));
            p.setPaymentTerm(rs.getString(10));
            p.setTotalBebanPenjualan(rs.getDouble(11));
            p.setTotalPenjualan(rs.getDouble(12));
            p.setPembayaran(rs.getDouble(13));
            p.setSisaPembayaran(rs.getDouble(14));
            p.setCatatan(rs.getString(15));
            p.setKodeSales(rs.getString(16));
            p.setKodeUser(rs.getString(17));
            p.setTglBatal(rs.getDate(18).toString()+" "+rs.getTime(18).toString());
            p.setUserBatal(rs.getString(19));
            p.setStatus(rs.getString(20));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        Date serverDate = Function.getServerDate(con);
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,3)) from tt_penjualan_head "
                + " where mid(no_penjualan,4,4) = ?");
        ps.setString(1, yymm.format(serverDate));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PJ-"+yymm.format(serverDate)+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PJ-"+yymm.format(serverDate)+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PenjualanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_head values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setString(3, p.getNoPemesanan());
        ps.setString(4, p.getKodeCustomer());
        ps.setString(5, p.getKodeCustomerInvoice());
        ps.setString(6, p.getKodeGudang());
        ps.setString(7, p.getTujuanKirim());
        ps.setString(8, p.getJenisPengiriman());
        ps.setString(9, p.getSupir());
        ps.setString(10, p.getPaymentTerm());
        ps.setDouble(11, p.getTotalBebanPenjualan());
        ps.setDouble(12, p.getTotalPenjualan());
        ps.setDouble(13, p.getPembayaran());
        ps.setDouble(14, p.getSisaPembayaran());
        ps.setString(15, p.getCatatan());
        ps.setString(16, p.getKodeSales());
        ps.setString(17, p.getKodeUser());
        ps.setString(18, p.getTglBatal());
        ps.setString(19, p.getUserBatal());
        ps.setString(20, p.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenjualanHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_head set "
                + " tgl_penjualan=?, no_pemesanan=?, kode_customer=?, kode_customer_invoice=?, kode_gudang=?, "
                + " tujuan_kirim=?, jenis_pengiriman=?, supir=?, "
                + " payment_term=?, total_beban_penjualan=?, total_penjualan=?, pembayaran=?, sisa_pembayaran=?, "
                + " catatan=?, kode_sales=?, kode_user=?, tgl_batal=?, user_batal=?, status=? "
                + " where no_penjualan=?");
        ps.setString(1, p.getTglPenjualan());
        ps.setString(2, p.getNoPemesanan());
        ps.setString(3, p.getKodeCustomer());
        ps.setString(4, p.getKodeCustomerInvoice());
        ps.setString(5, p.getKodeGudang());
        ps.setString(6, p.getTujuanKirim());
        ps.setString(7, p.getJenisPengiriman());
        ps.setString(8, p.getSupir());
        ps.setString(9, p.getPaymentTerm());
        ps.setDouble(10, p.getTotalBebanPenjualan());
        ps.setDouble(11, p.getTotalPenjualan());
        ps.setDouble(12, p.getPembayaran());
        ps.setDouble(13, p.getSisaPembayaran());
        ps.setString(14, p.getCatatan());
        ps.setString(15, p.getKodeSales());
        ps.setString(16, p.getKodeUser());
        ps.setString(17, p.getTglBatal());
        ps.setString(18, p.getUserBatal());
        ps.setString(19, p.getStatus());
        ps.setString(20, p.getNoPenjualan());
        ps.executeUpdate();
    }
}
