/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranHutang;
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
public class PembayaranHutangDAO {
    public static List<PembayaranHutang> getAllByDateAndNoHutangAndStatus(
            Connection con, String tglMulai, String tglAkhir, String noHutang, String status)throws Exception{
        String sql = "select * from tt_pembayaran_hutang where mid(no_pembayaran,4,6) between ? and ? ";
        if(!noHutang.equals("%"))
            sql = sql + " and no_hutang = '"+noHutang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PembayaranHutang> allPembayaranHutang = new ArrayList<>();
        while(rs.next()){
            PembayaranHutang bayar = new PembayaranHutang();
            bayar.setNoPembayaran(rs.getString(1));
            bayar.setTglPembayaran(rs.getString(2));
            bayar.setNoHutang(rs.getString(3));
            bayar.setJumlahBayar(rs.getDouble(4));
            bayar.setKurs(rs.getDouble(5));
            bayar.setTerbayar(rs.getDouble(6));
            bayar.setKodeUser(rs.getString(7));
            bayar.setStatus(rs.getString(8));
            bayar.setTglBatal(rs.getString(9));
            bayar.setUserBatal(rs.getString(10));
            allPembayaranHutang.add(bayar);
        }
        return allPembayaranHutang;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembayaran,4)) from tt_pembayaran_hutang "
                + "where mid(no_pembayaran,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PH-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PH-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void update(Connection con, PembayaranHutang pembayaran)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembayaran_hutang set "
                + " tgl_pembayaran=?, no_hutang=?, jumlah_bayar=?, "
                + " kurs=?, terbayar=?, kode_user=?, "
                + " status=?, tgl_batal=?, user_batal=?"
                + " where no_pembayaran = ?");
        ps.setString(1, pembayaran.getTglPembayaran());
        ps.setString(2, pembayaran.getNoHutang());
        ps.setDouble(3, pembayaran.getJumlahBayar());
        ps.setDouble(4, pembayaran.getKurs());
        ps.setDouble(5, pembayaran.getTerbayar());
        ps.setString(6, pembayaran.getKodeUser());
        ps.setString(7, pembayaran.getStatus());
        ps.setString(8, pembayaran.getTglBatal());
        ps.setString(9, pembayaran.getUserBatal());
        ps.setString(10, pembayaran.getNoPembayaran());
        ps.executeUpdate();
    }
    public static void insert(Connection con, PembayaranHutang pembayaran)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembayaran_hutang values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pembayaran.getNoPembayaran());
        ps.setString(2, pembayaran.getTglPembayaran());
        ps.setString(3, pembayaran.getNoHutang());
        ps.setDouble(4, pembayaran.getJumlahBayar());
        ps.setDouble(5, pembayaran.getKurs());
        ps.setDouble(6, pembayaran.getTerbayar());
        ps.setString(7, pembayaran.getKodeUser());
        ps.setString(8, pembayaran.getStatus());
        ps.setString(9, pembayaran.getTglBatal());
        ps.setString(10, pembayaran.getUserBatal());
        ps.executeUpdate();
    }
}
