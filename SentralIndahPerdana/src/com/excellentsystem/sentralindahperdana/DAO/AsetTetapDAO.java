/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.AsetTetap;
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
public class AsetTetapDAO {
    public static List<AsetTetap> getAllByStatus(Connection con, String status)throws Exception{
        List<AsetTetap> allAsetTetap;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_aset_tetap where status like ? ")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allAsetTetap = new ArrayList<>();
                while(rs.next()){
                    AsetTetap aset = new AsetTetap();
                    aset.setNoAset(rs.getString(1));
                    aset.setNama(rs.getString(2));
                    aset.setKategori(rs.getString(3));
                    aset.setKeterangan(rs.getString(4));
                    aset.setMasaPakai(rs.getInt(5));
                    aset.setNilaiAwal(rs.getDouble(6));
                    aset.setPenyusutan(rs.getDouble(7));
                    aset.setNilaiAkhir(rs.getDouble(8));
                    aset.setHargaJual(rs.getDouble(9));
                    aset.setStatus(rs.getString(10));
                    aset.setTglJual(rs.getDate(11).toString()+" "+rs.getTime(11).toString());
                    aset.setUserJual(rs.getString(12));
                    aset.setTglBeli(rs.getDate(13).toString()+" "+rs.getTime(13).toString());
                    aset.setUserBeli(rs.getString(14));
                    allAsetTetap.add(aset);
                }
            }
        }
        return allAsetTetap;
    }
    public static String getId(Connection con)throws Exception{
        String noAsetTetap;
        try (PreparedStatement ps = con.prepareStatement("select max(right(no_aset_tetap,3)) from tm_aset_tetap where mid(no_aset_tetap,4,4) = ?")) {
            DateFormat dateFormat = new SimpleDateFormat("yyMM");
            DecimalFormat df = new DecimalFormat("000");
            Date date = new Date();
            ps.setString(1, dateFormat.format(date));
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    noAsetTetap = "AT-"+dateFormat.format(date)+df.format(rs.getInt(1)+1);
                else
                    noAsetTetap = "AT-"+dateFormat.format(date)+df.format(1);
            }
        }
        return noAsetTetap;
    }
    public static void insert(Connection con, AsetTetap aset)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_aset_tetap values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, aset.getNoAset());
            ps.setString(2, aset.getNama());
            ps.setString(3, aset.getKategori());
            ps.setString(4, aset.getKeterangan());
            ps.setInt(5, aset.getMasaPakai());
            ps.setDouble(6, aset.getNilaiAwal());
            ps.setDouble(7, aset.getPenyusutan());
            ps.setDouble(8, aset.getNilaiAkhir());
            ps.setDouble(9, aset.getHargaJual());
            ps.setString(10, aset.getStatus());
            ps.setString(11, aset.getTglJual());
            ps.setString(12, aset.getUserJual());
            ps.setString(13, aset.getTglBeli());
            ps.setString(14, aset.getUserBeli());
            ps.executeUpdate();
        }
    }
    public static void update(Connection con, AsetTetap aset)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_aset_tetap set nama=?, kategori=?, keterangan=?, masa_pakai=?,"
                + " nilai_awal=?, penyusutan=?, nilai_akhir=?, harga_jual=?, status=?, tgl_jual=?, user_jual=?, tgl_beli=?,"
                + " user_beli=? where no_aset_tetap=? ")) {
            ps.setString(1, aset.getNama());
            ps.setString(2, aset.getKategori());
            ps.setString(3, aset.getKeterangan());
            ps.setInt(4, aset.getMasaPakai());
            ps.setDouble(5, aset.getNilaiAwal());
            ps.setDouble(6, aset.getPenyusutan());
            ps.setDouble(7, aset.getNilaiAkhir());
            ps.setDouble(8, aset.getHargaJual());
            ps.setString(9, aset.getStatus());
            ps.setString(10, aset.getTglJual());
            ps.setString(11, aset.getUserJual());
            ps.setString(12, aset.getTglBeli());
            ps.setString(13, aset.getUserBeli());
            ps.setString(14, aset.getNoAset());
            ps.executeUpdate();
        }
    }
}
