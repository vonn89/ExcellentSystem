/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahHead;
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
public class PindahHeadDAO {
    public static List<PindahHead> getAllByDateAndCabangAndStatusTerimaAndStatusBatal(Connection con, 
            String tglMulai, String tglAkhir, String kodeCabang, String statusTerima, String statusBatal)throws Exception{
        String sql = "select * from tt_pindah_head where mid(no_pindah,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!statusTerima.equals("%"))
            sql = sql + " and status_terima = '"+statusTerima+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PindahHead> allPindah = new ArrayList<>();
        while(rs.next()){
            PindahHead p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            allPindah.add(p);
        }
        return allPindah;
    }
    public static List<PindahHead> getAllByTglTerimaAndCabang(Connection con, String tglMulai, String tglAkhir, String kodeCabang)throws Exception{
        String sql = "select * from tt_pindah_head where left(tgl_terima, 10) between ? and ? "
                + " and status_terima = 'true' and status_batal = 'false'";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<PindahHead> allPindah = new ArrayList<>();
        while(rs.next()){
            PindahHead p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
            allPindah.add(p);
        }
        return allPindah;
    }
    public static PindahHead get(Connection con, String noPindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_pindah_head where no_pindah = ?");
        ps.setString(1, noPindah);
        ResultSet rs = ps.executeQuery();
        PindahHead p = null;
        while(rs.next()){
            p = new PindahHead();
            p.setNoPindah(rs.getString(1));
            p.setTglPindah(rs.getString(2));
            p.setKodeCabang(rs.getString(3));
            p.setKodeGudang(rs.getString(4));
            p.setTotalQty(rs.getInt(5));
            p.setTotalBerat(rs.getDouble(6));
            p.setKodeUser(rs.getString(7));
            p.setStatusTerima(rs.getString(8));
            p.setTglTerima(rs.getString(9));
            p.setUserTerima(rs.getString(10));
            p.setStatusBatal(rs.getString(11));
            p.setTglBatal(rs.getString(12));
            p.setUserBatal(rs.getString(13));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pindah,4)) from tt_pindah_head "
                + "where mid(no_pindah,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PB-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, PindahHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pindah_head values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPindah());
        ps.setString(2, p.getTglPindah());
        ps.setString(3, p.getKodeCabang());
        ps.setString(4, p.getKodeGudang());
        ps.setDouble(5, p.getTotalQty());
        ps.setDouble(6, p.getTotalBerat());
        ps.setString(7, p.getKodeUser());
        ps.setString(8, p.getStatusTerima());
        ps.setString(9, p.getTglTerima());
        ps.setString(10, p.getUserTerima());
        ps.setString(11, p.getStatusBatal());
        ps.setString(12, p.getTglBatal());
        ps.setString(13, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PindahHead p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pindah_head set "
                + "tgl_pindah=?, kode_cabang=?, kode_gudang=?, total_qty=?, total_berat=?, kode_user=?, "
                + "status_terima=?, tgl_terima=?, user_terima=?, status_batal=?, tgl_batal=?, user_batal=? "
                + "where no_pindah=?");
        ps.setString(1, p.getTglPindah());
        ps.setString(2, p.getKodeCabang());
        ps.setString(3, p.getKodeGudang());
        ps.setDouble(4, p.getTotalQty());
        ps.setDouble(5, p.getTotalBerat());
        ps.setString(6, p.getKodeUser());
        ps.setString(7, p.getStatusTerima());
        ps.setString(8, p.getTglTerima());
        ps.setString(9, p.getUserTerima());
        ps.setString(10, p.getStatusBatal());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.setString(13, p.getNoPindah());
        ps.executeUpdate();
    }
}
