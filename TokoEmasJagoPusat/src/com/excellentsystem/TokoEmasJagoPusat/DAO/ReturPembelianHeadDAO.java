/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excellent
 */
public class ReturPembelianHeadDAO {
    public static List<ReturPembelianHead> getAllByDateAndSupplierAndStatus(
            Connection con, String tglMulai, String tglAkhir, String supplier, String status)throws Exception{
        String sql = "select * from tt_retur_pembelian_head where mid(no_retur,4,6) between ? and ? ";
        if(!supplier.equals("%"))
            sql = sql + " and supplier = '"+supplier+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<ReturPembelianHead> listReturPembelian = new ArrayList<>();
        while(rs.next()){
            ReturPembelianHead r = new ReturPembelianHead();
            r.setNoRetur(rs.getString(1));
            r.setTglRetur(rs.getString(2));
            r.setSupplier(rs.getString(3));
            r.setTotalRetur(rs.getDouble(4));
            r.setHargaEmas(rs.getDouble(5));
            r.setKodeUser(rs.getString(6));
            r.setStatus(rs.getString(7));
            r.setTglBatal(rs.getString(8));
            r.setUserBatal(rs.getString(9));
            listReturPembelian.add(r);
        }
        return listReturPembelian;
    }
    public static ReturPembelianHead get(Connection con, String noRetur)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_retur_pembelian_head where no_retur = ? ");
        ps.setString(1, noRetur);
        ResultSet rs = ps.executeQuery();
        ReturPembelianHead r = null;
        while(rs.next()){
            r = new ReturPembelianHead();
            r.setNoRetur(rs.getString(1));
            r.setTglRetur(rs.getString(2));
            r.setSupplier(rs.getString(3));
            r.setTotalRetur(rs.getDouble(4));
            r.setHargaEmas(rs.getDouble(5));
            r.setKodeUser(rs.getString(6));
            r.setStatus(rs.getString(7));
            r.setTglBatal(rs.getString(8));
            r.setUserBatal(rs.getString(9));
        }
        return r;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_retur,4)) from tt_retur_pembelian_head "
                + "where mid(no_retur,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "RB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "RB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, ReturPembelianHead r)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_retur_pembelian_head "
                + "values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, r.getNoRetur());
        ps.setString(2, r.getTglRetur());
        ps.setString(3, r.getSupplier());
        ps.setDouble(4, r.getTotalRetur());
        ps.setDouble(5, r.getHargaEmas());
        ps.setString(6, r.getKodeUser());
        ps.setString(7, r.getStatus());
        ps.setString(8, r.getTglBatal());
        ps.setString(9, r.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, ReturPembelianHead r)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_retur_pembelian_head set "
                + " tgl_retur=?, supplier=?, total_retur=?, harga_emas=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? where no_retur=?");
        ps.setString(1, r.getTglRetur());
        ps.setString(2, r.getSupplier());
        ps.setDouble(3, r.getTotalRetur());
        ps.setDouble(4, r.getHargaEmas());
        ps.setString(5, r.getKodeUser());
        ps.setString(6, r.getStatus());
        ps.setString(7, r.getTglBatal());
        ps.setString(8, r.getUserBatal());
        ps.setString(9, r.getNoRetur());
        ps.executeUpdate();
    }
}
