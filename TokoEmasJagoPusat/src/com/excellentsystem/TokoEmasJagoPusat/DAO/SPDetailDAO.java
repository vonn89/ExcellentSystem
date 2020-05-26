/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SPDetailDAO {
    public static List<SPDetail> getAllByDateAndCabangAndJenisSpAndStatusSelesaiAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, String jenisSP, 
            String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_sp_detail where no_sp in ( "
                + " select no_sp from tt_sp_head where mid(no_sp,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!jenisSP.equals("%"))
            sql = sql + " and jenis_sp = '"+jenisSP+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        sql = sql + " )";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<SPDetail> listSpDetail = new ArrayList<>();
        while(rs.next()){
            SPDetail sp = new SPDetail();
            sp.setNoSP(rs.getString(1));
            sp.setNoUrut(rs.getInt(2));
            sp.setKodeKategoriBalenan(rs.getString(3));
            sp.setKodeJenisBalenan(rs.getString(4));
            sp.setKodeKategori(rs.getString(5));
            sp.setKodeJenis(rs.getString(6));
            sp.setQty(rs.getInt(7));
            sp.setBerat(rs.getDouble(8));
            sp.setBeratPersenBalenan(rs.getDouble(9));
            sp.setBeratPersen(rs.getDouble(10));
            sp.setNilaiPokok(rs.getDouble(11));
            sp.setBeratPenyusutan(rs.getDouble(12));
            sp.setNilaiPenyusutan(rs.getDouble(13));
            listSpDetail.add(sp);
        }
        return listSpDetail;
    }
    public static List<SPDetail> getAllByNoSP(Connection con, String noSP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_sp_detail where no_sp=?");
        ps.setString(1, noSP);
        ResultSet rs = ps.executeQuery();
        List<SPDetail> listSpDetail = new ArrayList<>();
        while(rs.next()){
            SPDetail sp = new SPDetail();
            sp.setNoSP(rs.getString(1));
            sp.setNoUrut(rs.getInt(2));
            sp.setKodeKategoriBalenan(rs.getString(3));
            sp.setKodeJenisBalenan(rs.getString(4));
            sp.setKodeKategori(rs.getString(5));
            sp.setKodeJenis(rs.getString(6));
            sp.setQty(rs.getInt(7));
            sp.setBerat(rs.getDouble(8));
            sp.setBeratPersenBalenan(rs.getDouble(9));
            sp.setBeratPersen(rs.getDouble(10));
            sp.setNilaiPokok(rs.getDouble(11));
            sp.setBeratPenyusutan(rs.getDouble(12));
            sp.setNilaiPenyusutan(rs.getDouble(13));
            listSpDetail.add(sp);
        }
        return listSpDetail;
    }
    public static void insert(Connection con,SPDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_sp_detail values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, detail.getNoSP());
        ps.setInt(2, detail.getNoUrut());
        ps.setString(3, detail.getKodeKategoriBalenan());
        ps.setString(4, detail.getKodeJenisBalenan());
        ps.setString(5, detail.getKodeKategori());
        ps.setString(6, detail.getKodeJenis());
        ps.setInt(7, detail.getQty());
        ps.setDouble(8, detail.getBerat());
        ps.setDouble(9, detail.getBeratPersenBalenan());
        ps.setDouble(10, detail.getBeratPersen());
        ps.setDouble(11, detail.getNilaiPokok());
        ps.setDouble(12, detail.getBeratPenyusutan());
        ps.setDouble(13, detail.getNilaiPenyusutan());
        ps.executeUpdate();
    }
    public static void update(Connection con, SPDetail detail)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_sp_detail set "
                + " kode_kategori_balenan=?, kode_jenis_balenan=?, kode_kategori=?, kode_jenis=?, "
                + " qty=?, berat=?, berat_persen_balenan=?, berat_persen=?, nilai_pokok=?, berat_penyusutan=?, nilai_penyusutan=? "
                + " where no_sp=? and no_urut=? ");
        ps.setString(1, detail.getKodeKategoriBalenan());
        ps.setString(2, detail.getKodeJenisBalenan());
        ps.setString(3, detail.getKodeKategori());
        ps.setString(4, detail.getKodeJenis());
        ps.setInt(5, detail.getQty());
        ps.setDouble(6, detail.getBerat());
        ps.setDouble(7, detail.getBeratPersenBalenan());
        ps.setDouble(8, detail.getBeratPersen());
        ps.setDouble(9, detail.getNilaiPokok());
        ps.setDouble(10, detail.getBeratPenyusutan());
        ps.setDouble(11, detail.getNilaiPenyusutan());
        ps.setString(12, detail.getNoSP());
        ps.setInt(13, detail.getNoUrut());
        ps.executeUpdate();
    }
}
