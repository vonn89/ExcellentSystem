/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Hutang;
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
public class HutangDAO {
    public static List<Hutang> getAllByDateAndSupplierAndStatus(
            Connection con, String tglMulai, String tglAkhir, String kodeSupplier, String status)throws Exception{
        String sql = "select * from tt_hutang where mid(no_hutang,4,6) between ? and ? ";
        if(!kodeSupplier.equals("%"))
            sql = sql + " and supplier = '"+kodeSupplier+"' ";
        if(!status.equals("%"))
            sql = sql + " and status = '"+status+"' ";
        sql = sql + " order by no_hutang";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        ResultSet rs = ps.executeQuery();
        List<Hutang> allHutang = new ArrayList<>();
        while(rs.next()){
            Hutang hutang = new Hutang();
            hutang.setNoHutang(rs.getString(1));
            hutang.setTglHutang(rs.getString(2));
            hutang.setSupplier(rs.getString(3));
            hutang.setNoPembelian(rs.getString(4));
            hutang.setKurs(rs.getDouble(5));
            hutang.setJumlahHutang(rs.getDouble(6));
            hutang.setTerbayar(rs.getDouble(7));
            hutang.setSisaHutang(rs.getDouble(8));
            hutang.setStatus(rs.getString(9));
            allHutang.add(hutang);
        }
        return allHutang;
    }
    public static Hutang getByNoPembelian(Connection con, String noPembelian)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_hutang where no_pembelian =  ?");
        ps.setString(1, noPembelian);
        ResultSet rs = ps.executeQuery();
        Hutang hutang = null;
        while(rs.next()){
            hutang = new Hutang();
            hutang.setNoHutang(rs.getString(1));
            hutang.setTglHutang(rs.getString(2));
            hutang.setSupplier(rs.getString(3));
            hutang.setNoPembelian(rs.getString(4));
            hutang.setKurs(rs.getDouble(5));
            hutang.setJumlahHutang(rs.getDouble(6));
            hutang.setTerbayar(rs.getDouble(7));
            hutang.setSisaHutang(rs.getDouble(8));
            hutang.setStatus(rs.getString(9));
        }
        return hutang;
    }
    public static Hutang get(Connection con, String noHutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tt_hutang where no_hutang = ?");
        ps.setString(1, noHutang);
        ResultSet rs = ps.executeQuery();
        Hutang hutang = null;
        while(rs.next()){
            hutang = new Hutang();
            hutang.setNoHutang(rs.getString(1));
            hutang.setTglHutang(rs.getString(2));
            hutang.setSupplier(rs.getString(3));
            hutang.setNoPembelian(rs.getString(4));
            hutang.setKurs(rs.getDouble(5));
            hutang.setJumlahHutang(rs.getDouble(6));
            hutang.setTerbayar(rs.getDouble(7));
            hutang.setSisaHutang(rs.getDouble(8));
            hutang.setStatus(rs.getString(9));
        }
        return hutang;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_hutang,4)) from tt_hutang "
                + "where mid(no_hutang,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "HT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "HT-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,Hutang hutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_hutang values(?,?,?,?,?,?,?,?,?)");
        ps.setString(1, hutang.getNoHutang());
        ps.setString(2, hutang.getTglHutang());
        ps.setString(3, hutang.getSupplier());
        ps.setString(4, hutang.getNoPembelian());
        ps.setDouble(5, hutang.getKurs());
        ps.setDouble(6, hutang.getJumlahHutang());
        ps.setDouble(7, hutang.getTerbayar());
        ps.setDouble(8, hutang.getSisaHutang());
        ps.setString(9, hutang.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con,Hutang hutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_hutang set "
                + " tgl_hutang=?, supplier=?, no_pembelian=?, "
                + " kurs=?, jumlah_hutang=?, terbayar=?,"
                + " sisa_hutang=?, status=? "
                + " where no_hutang=?");
        ps.setString(1, hutang.getTglHutang());
        ps.setString(2, hutang.getSupplier());
        ps.setString(3, hutang.getNoPembelian());
        ps.setDouble(4, hutang.getKurs());
        ps.setDouble(5, hutang.getJumlahHutang());
        ps.setDouble(6, hutang.getTerbayar());
        ps.setDouble(7, hutang.getSisaHutang());
        ps.setString(8, hutang.getStatus());
        ps.setString(9, hutang.getNoHutang());
        ps.executeUpdate();
    }
    public static void delete(Connection con,Hutang hutang)throws Exception{
        PreparedStatement ps = con.prepareStatement("delete from tt_hutang where no_hutang=?");
        ps.setString(1, hutang.getNoHutang());
        ps.executeUpdate();
    }
}
