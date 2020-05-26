/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCiokPusat;
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
public class PenjualanCiokPusatDAO {
    public static List<PenjualanCiokPusat> getAllByDateAndStatus(Connection con, 
            String tglMulai,String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penjualan_ciok_pusat where mid(no_penjualan,4,6) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenjualanCiokPusat> allPenjualan = new ArrayList<>();
        while(rs.next()){
            PenjualanCiokPusat p = new PenjualanCiokPusat();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setBerat(rs.getDouble(3));
            p.setHargaEmas(rs.getDouble(4));
            p.setNilaiPokok(rs.getDouble(5));
            p.setTotalPenjualan(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
            allPenjualan.add(p);
        }
        return allPenjualan;
    }
    public static PenjualanCiokPusat get(Connection con, String noPenjualan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penjualan_ciok_pusat where no_penjualan =?");
        ps.setString(1, noPenjualan);
        ResultSet rs = ps.executeQuery();
        PenjualanCiokPusat p = null;
        while(rs.next()){
            p = new PenjualanCiokPusat();
            p.setNoPenjualan(rs.getString(1));
            p.setTglPenjualan(rs.getString(2));
            p.setBerat(rs.getDouble(3));
            p.setHargaEmas(rs.getDouble(4));
            p.setNilaiPokok(rs.getDouble(5));
            p.setTotalPenjualan(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatus(rs.getString(8));
            p.setTglBatal(rs.getString(9));
            p.setUserBatal(rs.getString(10));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penjualan,4)) from tt_penjualan_ciok_pusat "
                + "where mid(no_penjualan,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,PenjualanCiokPusat p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penjualan_ciok_pusat values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenjualan());
        ps.setString(2, p.getTglPenjualan());
        ps.setDouble(3, p.getBerat());
        ps.setDouble(4, p.getHargaEmas());
        ps.setDouble(5, p.getNilaiPokok());
        ps.setDouble(6, p.getTotalPenjualan());
        ps.setString(7, p.getKodeUser());
        ps.setString(8, p.getStatus());
        ps.setString(9, p.getTglBatal());
        ps.setString(10, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,PenjualanCiokPusat p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penjualan_ciok_pusat set "
                + "tgl_penjualan=?, berat=?, harga_emas=?, nilai_pokok=?, total_penjualan=?, "
                + "kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + "where no_penjualan=?");
        ps.setString(1, p.getTglPenjualan());
        ps.setDouble(2, p.getBerat());
        ps.setDouble(3, p.getHargaEmas());
        ps.setDouble(4, p.getNilaiPokok());
        ps.setDouble(5, p.getTotalPenjualan());
        ps.setString(6, p.getKodeUser());
        ps.setString(7, p.getStatus());
        ps.setString(8, p.getTglBatal());
        ps.setString(9, p.getUserBatal());
        ps.setString(10, p.getNoPenjualan());
        ps.executeUpdate();
    }
}
