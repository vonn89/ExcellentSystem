/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenyesuaianStokBarangPusat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class PenyesuaianStokBarangPusatDAO {
    
    public static List<PenyesuaianStokBarangPusat> getAllByDateAndStatus(Connection con, 
            String tglMulai, String tglAkhir, String status)throws Exception{
        String sql = "select * from tt_penyesuaian_stok_barang_pusat where mid(no_penyesuaian,4,6) between ? and ? ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PenyesuaianStokBarangPusat> allPindah = new ArrayList<>();
        while(rs.next()){
            PenyesuaianStokBarangPusat p = new PenyesuaianStokBarangPusat();
            p.setNoPenyesuaian(rs.getString(1));
            p.setTglPenyesuaian(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setJenis(rs.getString(4));
            p.setBerat(rs.getDouble(5));
            p.setHargaPersen(rs.getDouble(6));
            p.setHargaEmas(rs.getDouble(7));
            p.setNilaiPokok(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
            p.setTglBatal(rs.getString(11));
            p.setUserBatal(rs.getString(12));
            allPindah.add(p);
        }
        return allPindah;
    }
    public static PenyesuaianStokBarangPusat get(Connection con, String noPindah)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_penyesuaian_stok_barang_pusat where no_penyesuaian = ?");
        ps.setString(1, noPindah);
        ResultSet rs = ps.executeQuery();
        PenyesuaianStokBarangPusat p = null;
        while(rs.next()){
            p = new PenyesuaianStokBarangPusat();
            p.setNoPenyesuaian(rs.getString(1));
            p.setTglPenyesuaian(rs.getString(2));
            p.setKategori(rs.getString(3));
            p.setJenis(rs.getString(4));
            p.setBerat(rs.getDouble(5));
            p.setHargaPersen(rs.getDouble(6));
            p.setHargaEmas(rs.getDouble(7));
            p.setNilaiPokok(rs.getDouble(8));
            p.setKodeUser(rs.getString(9));
            p.setStatus(rs.getString(10));
            p.setTglBatal(rs.getString(11));
            p.setUserBatal(rs.getString(12));
        }
        return p;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_penyesuaian,4)) from tt_penyesuaian_stok_barang_pusat "
                + "where mid(no_penyesuaian,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PS-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PS-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con, PenyesuaianStokBarangPusat p)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_penyesuaian_stok_barang_pusat values(?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, p.getNoPenyesuaian());
        ps.setString(2, p.getTglPenyesuaian());
        ps.setString(3, p.getKategori());
        ps.setString(4, p.getJenis());
        ps.setDouble(5, p.getBerat());
        ps.setDouble(6, p.getHargaPersen());
        ps.setDouble(7, p.getHargaEmas());
        ps.setDouble(8, p.getNilaiPokok());
        ps.setString(9, p.getKodeUser());
        ps.setString(10, p.getStatus());
        ps.setString(11, p.getTglBatal());
        ps.setString(12, p.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con, PenyesuaianStokBarangPusat p)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_penyesuaian_stok_barang_pusat set "
                + "tgl_penyesuaian=?, kategori=?, jenis=?, berat=?, harga_persen=?, harga_emas=?, "
                + "nilai_pokok=?, kode_user=?, status=?, tgl_batal=?, user_batal=? "
                + "where no_penyesuaian=?");
        ps.setString(1, p.getTglPenyesuaian());
        ps.setString(2, p.getKategori());
        ps.setString(3, p.getJenis());
        ps.setDouble(4, p.getBerat());
        ps.setDouble(5, p.getHargaPersen());
        ps.setDouble(6, p.getHargaEmas());
        ps.setDouble(7, p.getNilaiPokok());
        ps.setString(8, p.getKodeUser());
        ps.setString(9, p.getStatus());
        ps.setString(10, p.getTglBatal());
        ps.setString(11, p.getUserBatal());
        ps.setString(12, p.getNoPenyesuaian());
        ps.executeUpdate();
    }
}
