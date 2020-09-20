/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.PemesananCoilHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import test.Function;
import static test.Function.yymm;

/**
 *
 * @author Xtreme
 */
public class PemesananCoilHeadDAO {
    public static List<PemesananCoilHead> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_pemesanan_coil_head where left(tgl_pemesanan,10) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<PemesananCoilHead> allPemesanan = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PemesananCoilHead p = new PemesananCoilHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeCustomer(rs.getString(3));
            p.setPaymentTerm(rs.getString(4));
            p.setKurs(rs.getDouble(5));
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
    public static PemesananCoilHead get(Connection con, String noPemesanan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pemesanan_coil_head "
                + "where no_pemesanan = ? ");
        ps.setString(1, noPemesanan);
        PemesananCoilHead p = null;
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            p = new PemesananCoilHead();
            p.setNoPemesanan(rs.getString(1));
            p.setTglPemesanan(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
            p.setKodeCustomer(rs.getString(3));
            p.setPaymentTerm(rs.getString(4));
            p.setKurs(rs.getDouble(5));
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
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pemesanan,3)) "
                + " from tt_pemesanan_coil_head where mid(no_pemesanan,4,4) = ?");
        ps.setString(1, yymm.format(Function.getServerDate(con)));
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "PC-"+yymm.format(Function.getServerDate(con))+new DecimalFormat("000").format(rs.getInt(1)+1);
        else
            return "PC-"+yymm.format(Function.getServerDate(con))+new DecimalFormat("000").format(1);
    }
    public static void insert(Connection con, PemesananCoilHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pemesanan_coil_head "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPemesanan());
        ps.setString(2, p.getTglPemesanan());
        ps.setString(3, p.getKodeCustomer());
        ps.setString(4, p.getPaymentTerm());
        ps.setDouble(5, p.getKurs());
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
    public static void update(Connection con, PemesananCoilHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pemesanan_coil_head set "
                + " tgl_pemesanan=?, kode_customer=?, payment_term=?, "
                + " kurs=?, total_pemesanan=?, down_payment=?, "
                + " sisa_down_payment=?, catatan=?, kode_sales=?, "
                + " kode_user=?, tgl_batal=?, user_batal=?, status=? where no_pemesanan=?");
        ps.setString(1, p.getTglPemesanan());
        ps.setString(2, p.getKodeCustomer());
        ps.setString(3, p.getPaymentTerm());
        ps.setDouble(4, p.getKurs());
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
