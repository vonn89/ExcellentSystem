/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.BatalBarcode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Excellent
 */
public class BatalBarcodeDAO {
    
    public static List<BatalBarcode> getAllByDateAndUser(Connection con, String tglAwal, String tglAkhir, String user)throws Exception{
        String sql = "select * from tt_batal_barcode where left(tgl_batal,10) between ? and ? ";
        if(!user.equals("%"))
            sql = sql + " and user_batal = '"+user+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglAwal);
        ps.setString(2, tglAkhir);
        ResultSet rs = ps.executeQuery();
        List<BatalBarcode> allBarang = new ArrayList<>();
        while(rs.next()){
            BatalBarcode b = new BatalBarcode();
            b.setKodeBarcode(rs.getString(1));
            b.setTglBatal(rs.getString(2));
            b.setUserBatal(rs.getString(3));
            allBarang.add(b);
        }
        return allBarang;
    }
    public static BatalBarcode get(Connection con,String kodeBarcode)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_batal_barcode where kode_barcode = ?");
        ps.setString(1, kodeBarcode);
        ResultSet rs = ps.executeQuery();
        BatalBarcode b = null;
        if(rs.next()){
            b = new BatalBarcode();
            b.setKodeBarcode(rs.getString(1));
            b.setTglBatal(rs.getString(2));
            b.setUserBatal(rs.getString(3));
        }
        return b;
    }
    public static void insert(Connection con, BatalBarcode b) throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_batal_barcode values(?,?,?)");
        ps.setString(1, b.getKodeBarcode());
        ps.setString(2, b.getTglBatal());
        ps.setString(3, b.getUserBatal());
        ps.executeUpdate();
    }
}
