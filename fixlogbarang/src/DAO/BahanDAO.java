/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DAO;

import Model.Bahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class BahanDAO {
    public static List<Bahan> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_bahan ";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Bahan> allBahan = new ArrayList<>();
        while(rs.next()){
            Bahan b = new Bahan();
            b.setKodeBahan(rs.getString(1));
            b.setKodeKategori(rs.getString(2));
            b.setNoKontrak(rs.getString(3));
            b.setNamaBahan(rs.getString(4));
            b.setSpesifikasi(rs.getString(5));
            b.setKeterangan(rs.getString(6));
            b.setBeratKotor(rs.getDouble(7));
            b.setBeratBersih(rs.getDouble(8));
            b.setPanjang(rs.getDouble(9));
            b.setHargaBeli(rs.getDouble(10));
            b.setStatus(rs.getString(11));
            allBahan.add(b);
        }
        return allBahan;
    }
    public static Bahan get(Connection con,String kodeBahan)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_bahan where kode_bahan=?");
        ps.setString(1, kodeBahan);
        ResultSet rs = ps.executeQuery();
        Bahan b = null;
        while(rs.next()){
            b = new Bahan();
            b.setKodeBahan(rs.getString(1));
            b.setKodeKategori(rs.getString(2));
            b.setNoKontrak(rs.getString(3));
            b.setNamaBahan(rs.getString(4));
            b.setSpesifikasi(rs.getString(5));
            b.setKeterangan(rs.getString(6));
            b.setBeratKotor(rs.getDouble(7));
            b.setBeratBersih(rs.getDouble(8));
            b.setPanjang(rs.getDouble(9));
            b.setHargaBeli(rs.getDouble(10));
            b.setStatus(rs.getString(11));
        }
        return b;
    }
    public static void insert(Connection con,Bahan b)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_bahan values(?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, b.getKodeBahan());
        ps.setString(2, b.getKodeKategori());
        ps.setString(3, b.getKodeKategori());
        ps.setString(4, b.getNamaBahan());
        ps.setString(5, b.getSpesifikasi());
        ps.setString(6, b.getKeterangan());
        ps.setDouble(7, b.getBeratKotor());
        ps.setDouble(8, b.getBeratBersih());
        ps.setDouble(9, b.getPanjang());
        ps.setDouble(10, b.getHargaBeli());
        ps.setString(11, b.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con,Bahan b)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_bahan set "
                + " kode_kategori=?, no_kontrak=?, nama_bahan=?, spesifikasi=?, keterangan=?, "
                + " berat_kotor=?, berat_bersih=?, panjang=?, harga_beli=?, status=? "
                + " where kode_bahan=? ");
        ps.setString(1, b.getKodeKategori());
        ps.setString(2, b.getNoKontrak());
        ps.setString(3, b.getNamaBahan());
        ps.setString(4, b.getSpesifikasi());
        ps.setString(5, b.getKeterangan());
        ps.setDouble(6, b.getBeratKotor());
        ps.setDouble(7, b.getBeratBersih());
        ps.setDouble(8, b.getPanjang());
        ps.setDouble(9, b.getHargaBeli());
        ps.setString(10, b.getStatus());
        ps.setString(11, b.getKodeBahan());
        ps.executeUpdate();
    }
    public static void delete(Connection con,String kodeBahan)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tm_bahan where kode_bahan=?");
        ps.setString(1, kodeBahan);
        ps.executeUpdate();
    }
}
