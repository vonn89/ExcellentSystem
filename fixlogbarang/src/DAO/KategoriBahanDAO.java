/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.KategoriBahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yunaz
 */
public class KategoriBahanDAO {
    public static List<KategoriBahan> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_kategori_bahan";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"'";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<KategoriBahan> allKategoriBahan = new ArrayList<>();
        while(rs.next()){
            KategoriBahan k = new KategoriBahan();
            k.setKodeKategori(rs.getString(1));
            k.setStatus(rs.getString(2));
            allKategoriBahan.add(k);
        }
        return allKategoriBahan;
    }
    public static KategoriBahan get(Connection con, String kodeKategori)throws Exception{
        KategoriBahan k = null;
        PreparedStatement ps = con.prepareStatement("select * from tm_kategori_bahan where kode_kategori=? ");
        ps.setString(1, kodeKategori);
        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            k = new KategoriBahan();
            k.setKodeKategori(rs.getString(1));
            k.setStatus(rs.getString(2));
        }
        return k;
    }
    public static void insert(Connection con, KategoriBahan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_kategori_bahan values(?,?)");
        ps.setString(1, k.getKodeKategori());
        ps.setString(2, k.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, KategoriBahan k)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_kategori_bahan set status=? where kode_kategori=?");
        ps.setString(1, k.getStatus());
        ps.setString(2, k.getKodeKategori());
        ps.executeUpdate();
    }
}
