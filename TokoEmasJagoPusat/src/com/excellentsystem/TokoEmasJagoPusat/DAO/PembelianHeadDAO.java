/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class PembelianHeadDAO {
    public static List<PembelianHead> getAllByDateAndSupplierAndStatus(
            Connection con, String tglMulai,String tglAkhir,String supplier,String status)throws Exception{
        String sql = "select * from tt_pembelian_head where mid(no_pembelian,4,6) between ? and ? ";
        if(!supplier.equals("%"))
            sql = sql + " and supplier = '"+supplier+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<PembelianHead> allPembelianHead = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            PembelianHead head = new PembelianHead();
            head.setNoPembelian(rs.getString(1));
            head.setTglPembelian(rs.getString(2));
            head.setSupplier(rs.getString(3));
            head.setTotalPembelian(rs.getDouble(4));
            head.setHargaEmas(rs.getDouble(5));
            head.setKodeUser(rs.getString(6));
            head.setStatus(rs.getString(7));
            head.setTglBatal(rs.getString(8));
            head.setUserBatal(rs.getString(9));
            allPembelianHead.add(head);
        }
        return allPembelianHead;
    }
    public static PembelianHead get(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pembelian_head where no_pembelian = ? ");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        PembelianHead head = null;
        while(rs.next()){
            head = new PembelianHead();
            head.setNoPembelian(rs.getString(1));
            head.setTglPembelian(rs.getString(2));
            head.setSupplier(rs.getString(3));
            head.setTotalPembelian(rs.getDouble(4));
            head.setHargaEmas(rs.getDouble(5));
            head.setKodeUser(rs.getString(6));
            head.setStatus(rs.getString(7));
            head.setTglBatal(rs.getString(8));
            head.setUserBatal(rs.getString(9));
        }
        return head;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembelian,4)) from tt_pembelian_head "
                + "where mid(no_pembelian,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "BL-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "BL-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con , PembelianHead head)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembelian_head values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, head.getNoPembelian());
        ps.setString(2, head.getTglPembelian());
        ps.setString(3, head.getSupplier());
        ps.setDouble(4, head.getTotalPembelian());
        ps.setDouble(5, head.getHargaEmas());
        ps.setString(6, head.getKodeUser());
        ps.setString(7, head.getStatus());
        ps.setString(8, head.getTglBatal());
        ps.setString(9, head.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PembelianHead head)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembelian_head set "
                + " tgl_pembelian=?, supplier=?, total_pembelian=?, harga_emas=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=?  "
                + " where no_pembelian=?");
        ps.setString(1, head.getTglPembelian());
        ps.setString(2, head.getSupplier());
        ps.setDouble(3, head.getTotalPembelian());
        ps.setDouble(4, head.getHargaEmas());
        ps.setString(5, head.getKodeUser());
        ps.setString(6, head.getStatus());
        ps.setString(7, head.getTglBatal());
        ps.setString(8, head.getUserBatal());
        ps.setString(9, head.getNoPembelian());
        ps.executeUpdate();
    }
}
