/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
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
public class RosokCabangDAO {
    public static List<RosokCabang> getAllByDateAndKategoriAndCabangAndStatus(Connection con, String tglMulai, String tglAkhir,
            String kategori, String kodeCabang, String status)throws Exception{
        String sql = "select * from tt_rosok_cabang where mid(no_rosok,4,6) between ? and ? ";
        if(!kategori.equals("%"))
            sql = sql + " and kategori = '"+kategori+"' ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<RosokCabang> listAmbilRosok = new ArrayList<>();
        while(rs.next()){
            RosokCabang ar = new RosokCabang();
            ar.setNoRosok(rs.getString(1));
            ar.setTglRosok(rs.getString(2));
            ar.setKategori(rs.getString(3));
            ar.setKeterangan(rs.getString(4));
            ar.setKodeCabang(rs.getString(5));
            ar.setKodeGudang(rs.getString(6));
            ar.setKodeKategori(rs.getString(7));
            ar.setKodeJenis(rs.getString(8));
            ar.setQty(rs.getInt(9));
            ar.setBerat(rs.getDouble(10));
            ar.setBeratPersen(rs.getDouble(11));
            ar.setPersentaseEmas(rs.getDouble(12));
            ar.setBeratPersenRosok(rs.getDouble(13));
            ar.setNilaiPokok(rs.getDouble(14));
            ar.setKodeUser(rs.getString(15));
            ar.setStatus(rs.getString(16));
            ar.setTglBatal(rs.getString(17));
            ar.setUserBatal(rs.getString(18));
            listAmbilRosok.add(ar);
        }
        return listAmbilRosok;
    }
    public static List<RosokCabang> getAllByNoRosok(Connection con, String noSP)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_rosok_cabang where no_rosok = ?");
        ps.setString(1, noSP);
        ResultSet rs = ps.executeQuery();
        List<RosokCabang> listRosok = new ArrayList<>();
        while(rs.next()){
            RosokCabang ar = new RosokCabang();
            ar.setNoRosok(rs.getString(1));
            ar.setTglRosok(rs.getString(2));
            ar.setKategori(rs.getString(3));
            ar.setKeterangan(rs.getString(4));
            ar.setKodeCabang(rs.getString(5));
            ar.setKodeGudang(rs.getString(6));
            ar.setKodeKategori(rs.getString(7));
            ar.setKodeJenis(rs.getString(8));
            ar.setQty(rs.getInt(9));
            ar.setBerat(rs.getDouble(10));
            ar.setBeratPersen(rs.getDouble(11));
            ar.setPersentaseEmas(rs.getDouble(12));
            ar.setBeratPersenRosok(rs.getDouble(13));
            ar.setNilaiPokok(rs.getDouble(14));
            ar.setKodeUser(rs.getString(15));
            ar.setStatus(rs.getString(16));
            ar.setTglBatal(rs.getString(17));
            ar.setUserBatal(rs.getString(18));
            listRosok.add(ar);
        }
        return listRosok;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_rosok,4)) from tt_rosok_cabang "
                + "where mid(no_rosok,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noAmbil;
        if(rs.next())
            noAmbil = "RO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noAmbil = "RO-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noAmbil;
    }
    public static void insert(Connection con, RosokCabang r)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_rosok_cabang values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, r.getNoRosok());
        ps.setString(2, r.getTglRosok());
        ps.setString(3, r.getKategori());
        ps.setString(4, r.getKeterangan());
        ps.setString(5, r.getKodeCabang());
        ps.setString(6, r.getKodeGudang());
        ps.setString(7, r.getKodeKategori());
        ps.setString(8, r.getKodeJenis());
        ps.setInt(9, r.getQty());
        ps.setDouble(10, r.getBerat());
        ps.setDouble(11, r.getBeratPersen());
        ps.setDouble(12, r.getPersentaseEmas());
        ps.setDouble(13, r.getBeratPersenRosok());
        ps.setDouble(14, r.getNilaiPokok());
        ps.setString(15, r.getKodeUser());
        ps.setString(16, r.getStatus());
        ps.setString(17, r.getTglBatal());
        ps.setString(18, r.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, RosokCabang r)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_rosok_cabang set "
                + " tgl_rosok=?, kategori=?, keterangan=?, kode_cabang=?, kode_gudang=?, kode_kategori=?, kode_jenis=?, "
                + " qty=?, berat=?, berat_persen=?, persentase_emas=?, berat_persen_rosok=?, nilai_pokok=?, "
                + " kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + " where no_rosok = ?");
        ps.setString(1, r.getTglRosok());
        ps.setString(2, r.getKategori());
        ps.setString(3, r.getKeterangan());
        ps.setString(4, r.getKodeCabang());
        ps.setString(5, r.getKodeGudang());
        ps.setString(6, r.getKodeKategori());
        ps.setString(7, r.getKodeJenis());
        ps.setDouble(8, r.getQty());
        ps.setDouble(9, r.getBerat());
        ps.setDouble(10, r.getBeratPersen());
        ps.setDouble(11, r.getPersentaseEmas());
        ps.setDouble(12, r.getBeratPersenRosok());
        ps.setDouble(13, r.getNilaiPokok());
        ps.setString(14, r.getKodeUser());
        ps.setString(15, r.getStatus());
        ps.setString(16, r.getTglBatal());
        ps.setString(17, r.getUserBatal());
        ps.setString(18, r.getNoRosok());
        ps.executeUpdate();
    }
    
}
