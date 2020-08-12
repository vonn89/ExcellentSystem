/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianHeadDAO {
    public static List<PembelianHead> getAllByDateAndStatus(
            Connection con, String tglMulai, String tglAkhir, String status)throws Exception{
        List<PembelianHead> allPembelian;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head "
                + "where left(tgl_pembelian,10) between ? and ? and status like ?")) {
            ps.setString(1, tglMulai);
            ps.setString(2, tglAkhir);
            ps.setString(3, status);
            allPembelian = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    PembelianHead head = new PembelianHead();
                    head.setNoPembelian(rs.getString(1));
                    head.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setKodeSupplier(rs.getString(3));
                    head.setTotalPembelian(rs.getDouble(4));
                    head.setPembayaran(rs.getDouble(5));
                    head.setSisaPembayaran(rs.getDouble(6));
                    head.setCatatan(rs.getString(7));
                    head.setKodeUser(rs.getString(8));
                    head.setStatus(rs.getString(9));
                    head.setTglBatal(rs.getDate(10).toString()+" "+rs.getTime(10).toString());
                    head.setUserBatal(rs.getString(11));
                    allPembelian.add(head);
                }
            }
        }
        return allPembelian;
    }
    public static PembelianHead get(Connection con, String noPembelian)throws Exception{
        PembelianHead head;
        try (PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head "
                + "where no_pembelian=? ")) {
            ps.setString(1, noPembelian);
            try (ResultSet rs = ps.executeQuery()) {
                head = null;
                if(rs.next()){
                    head = new PembelianHead();
                    head.setNoPembelian(rs.getString(1));
                    head.setTglPembelian(rs.getDate(2).toString()+" "+rs.getTime(2).toString());
                    head.setKodeSupplier(rs.getString(3));
                    head.setTotalPembelian(rs.getDouble(4));
                    head.setPembayaran(rs.getDouble(5));
                    head.setSisaPembayaran(rs.getDouble(6));
                    head.setCatatan(rs.getString(7));
                    head.setKodeUser(rs.getString(8));
                    head.setStatus(rs.getString(9));
                    head.setTglBatal(rs.getDate(10).toString()+" "+rs.getTime(10).toString());
                    head.setUserBatal(rs.getString(11));
                }
            }
        }
        return head;
    }
    public static String getId(Connection con)throws Exception{
        String noPembelian;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_pembelian,3)) from tt_pembelian_head where mid(no_pembelian,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                noPembelian = "PO-"+dateFormat.format(date)+df.format(1);
                if(rs.next())
                    noPembelian = "PO-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
            }
        }
        return noPembelian;
    }
    public static void insert(Connection con, PembelianHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_head values(?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, head.getNoPembelian());
            ps.setString(2, head.getTglPembelian());
            ps.setString(3, head.getKodeSupplier());
            ps.setDouble(4, head.getTotalPembelian());
            ps.setDouble(5, head.getPembayaran());
            ps.setDouble(6, head.getSisaPembayaran());
            ps.setString(7, head.getCatatan());
            ps.setString(8, head.getKodeUser());
            ps.setString(9, head.getStatus());
            ps.setString(10, head.getTglBatal());
            ps.setString(11, head.getUserBatal());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, PembelianHead head)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tt_pembelian_head set pembayaran=?,"
                + " sisa_pembayaran=?, tgl_batal=?, user_batal=?, status=? where no_pembelian=?")) {
            ps.setDouble(1, head.getPembayaran());
            ps.setDouble(2, head.getSisaPembayaran());
            ps.setString(3, head.getTglBatal());
            ps.setString(4, head.getUserBatal());
            ps.setString(5, head.getStatus());
            ps.setString(6, head.getNoPembelian());
            ps.executeUpdate();
        }
    }
}
