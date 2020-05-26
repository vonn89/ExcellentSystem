/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPHead;
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
public class SPHeadDAO {
    public static List<SPHead> getAllByDateAndCabangAndJenisSpAndStatusSelesaiAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String jenisSP, 
            String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_sp_head where mid(no_sp,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!jenisSP.equals("%"))
            sql = sql + " and jenis_sp = '"+jenisSP+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<SPHead> allSp = new ArrayList<>();
        while(rs.next()){
            SPHead sp = new SPHead();
            sp.setNoSP(rs.getString(1));
            sp.setTglSP(rs.getString(2));
            sp.setKodeCabang(rs.getString(3));
            sp.setJenisSP(rs.getString(4));
            sp.setTotalQty(rs.getInt(5));
            sp.setTotalBerat(rs.getDouble(6));
            sp.setTotalNilaiPokok(rs.getDouble(7));
            sp.setTotalBeratPenyusutan(rs.getDouble(8));
            sp.setTotalNilaiPenyusutan(rs.getDouble(9));
            sp.setKodeUser(rs.getString(10));
            sp.setStatusSelesai(rs.getString(11));
            sp.setTglSelesai(rs.getString(12));
            sp.setUserSelesai(rs.getString(13));
            sp.setStatusBatal(rs.getString(14));
            sp.setTglBatal(rs.getString(15));
            sp.setUserBatal(rs.getString(16));
            allSp.add(sp);
        }
        return allSp;
    }
    public static SPHead get(Connection con, String noSp)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sp_head "
                + "where no_sp = ?");
        ps.setString(1, noSp);
        ResultSet rs = ps.executeQuery();
        SPHead sp = null;
        while(rs.next()){
            sp = new SPHead();
            sp.setNoSP(rs.getString(1));
            sp.setTglSP(rs.getString(2));
            sp.setKodeCabang(rs.getString(3));
            sp.setJenisSP(rs.getString(4));
            sp.setTotalQty(rs.getInt(5));
            sp.setTotalBerat(rs.getDouble(6));
            sp.setTotalNilaiPokok(rs.getDouble(7));
            sp.setTotalBeratPenyusutan(rs.getDouble(8));
            sp.setTotalNilaiPenyusutan(rs.getDouble(9));
            sp.setKodeUser(rs.getString(10));
            sp.setStatusSelesai(rs.getString(11));
            sp.setTglSelesai(rs.getString(12));
            sp.setUserSelesai(rs.getString(13));
            sp.setStatusBatal(rs.getString(14));
            sp.setTglBatal(rs.getString(15));
            sp.setUserBatal(rs.getString(16));
        }
        return sp;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_sp,4)) from tt_sp_head "
                + "where mid(no_sp,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "SP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "SP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,SPHead sp)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_sp_head "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, sp.getNoSP());
        ps.setString(2, sp.getTglSP());
        ps.setString(3, sp.getKodeCabang());
        ps.setString(4, sp.getJenisSP());
        ps.setInt(5, sp.getTotalQty());
        ps.setDouble(6, sp.getTotalBerat());
        ps.setDouble(7, sp.getTotalNilaiPokok());
        ps.setDouble(8, sp.getTotalBeratPenyusutan());
        ps.setDouble(9, sp.getTotalNilaiPenyusutan());
        ps.setString(10, sp.getKodeUser());
        ps.setString(11, sp.getStatusSelesai());
        ps.setString(12, sp.getTglSelesai());
        ps.setString(13, sp.getUserSelesai());
        ps.setString(14, sp.getStatusBatal());
        ps.setString(15, sp.getTglBatal());
        ps.setString(16, sp.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,SPHead sp)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_sp_head set "
            + " tgl_sp=?, kode_cabang=?, jenis_sp=?, total_qty=?, "
            + " total_berat=?, total_nilai_pokok=?, "
            + " total_berat_penyusutan=?, total_nilai_penyusutan=?, "
            + " kode_user=?, status_selesai=?, tgl_selesai=?, user_selesai=?, "
            + " status_batal=?, tgl_batal=?, user_batal=? where no_sp=?");
        ps.setString(1, sp.getTglSP());
        ps.setString(2, sp.getKodeCabang());
        ps.setString(3, sp.getJenisSP());
        ps.setInt(4, sp.getTotalQty());
        ps.setDouble(5, sp.getTotalBerat());
        ps.setDouble(6, sp.getTotalNilaiPokok());
        ps.setDouble(7, sp.getTotalBeratPenyusutan());
        ps.setDouble(8, sp.getTotalNilaiPenyusutan());
        ps.setString(9, sp.getKodeUser());
        ps.setString(10, sp.getStatusSelesai());
        ps.setString(11, sp.getTglSelesai());
        ps.setString(12, sp.getUserSelesai());
        ps.setString(13, sp.getStatusBatal());
        ps.setString(14, sp.getTglBatal());
        ps.setString(15, sp.getUserBatal());
        ps.setString(16, sp.getNoSP());
        ps.executeUpdate();
    }
}
