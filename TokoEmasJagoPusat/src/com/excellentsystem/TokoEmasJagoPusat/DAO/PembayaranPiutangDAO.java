/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranPiutang;
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
public class PembayaranPiutangDAO {
    public static List<PembayaranPiutang> getAllByDateAndNoPiutangAndStatus(
            Connection con, String tglMulai, String tglAkhir, String noPiutang, String status)throws Exception{
        String sql = "select * from tt_pembayaran_piutang where mid(no_pembayaran,4,6) between ? and ? ";
        if(!noPiutang.equals("%"))
            sql = sql + " and no_piutang = '"+noPiutang+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<PembayaranPiutang> allPembayaranPiutang = new ArrayList<>();
        while(rs.next()){
            PembayaranPiutang bayar = new PembayaranPiutang();
            bayar.setNoPembayaran(rs.getString(1));
            bayar.setTglPembayaran(rs.getString(2));
            bayar.setNoPiutang(rs.getString(3));
            bayar.setJumlahBayar(rs.getDouble(4));
            bayar.setKurs(rs.getDouble(5));
            bayar.setTerbayar(rs.getDouble(6));
            bayar.setKodeUser(rs.getString(7));
            bayar.setStatus(rs.getString(8));
            bayar.setTglBatal(rs.getString(9));
            bayar.setUserBatal(rs.getString(10));
            allPembayaranPiutang.add(bayar);
        }
        return allPembayaranPiutang;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_pembayaran,4)) from tt_pembayaran_piutang "
                + "where mid(no_pembayaran,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "PP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "PP-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void update(Connection con, PembayaranPiutang pembayaran)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_pembayaran_piutang set "
                + " tgl_pembayaran=?, no_piutang=?, jumlah_bayar=?, "
                + " kurs=?, terbayar=?, kode_user=?, "
                + " status=?, tgl_batal=?, user_batal=?"
                + " where no_pembayaran = ?");
        ps.setString(1, pembayaran.getTglPembayaran());
        ps.setString(2, pembayaran.getNoPiutang());
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
    public static void insert(Connection con, PembayaranPiutang pembayaran)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_pembayaran_piutang values(?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, pembayaran.getNoPembayaran());
        ps.setString(2, pembayaran.getTglPembayaran());
        ps.setString(3, pembayaran.getNoPiutang());
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
