/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.LogHargaEmas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class LogHargaEmasDAO {
    public static List<LogHargaEmas> getAllByTanggal(Connection con, String tglMulai, String tglAkhir)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_log_harga_emas where tanggal between ? and ? ");
        ps.setString(1, tglMulai);
        ps.setString(2, tglAkhir);
        List<LogHargaEmas> listLogHarga = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            LogHargaEmas log = new LogHargaEmas();
            log.setTanggal(rs.getString(1));
            log.setHargaEmas(rs.getDouble(2));
            listLogHarga.add(log);
        }
        return listLogHarga;
    }
    public static void update(Connection con, LogHargaEmas log)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_log_harga_emas set harga_emas=? where tanggal=?");
        ps.setDouble(1, log.getHargaEmas());
        ps.setString(2, log.getTanggal());
        ps.executeUpdate();
    }
    public static void insert(Connection con, LogHargaEmas log)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_log_harga_emas values (?,?)");
        ps.setString(1, log.getTanggal());
        ps.setDouble(2, log.getHargaEmas());
        ps.executeUpdate();
    }
}
