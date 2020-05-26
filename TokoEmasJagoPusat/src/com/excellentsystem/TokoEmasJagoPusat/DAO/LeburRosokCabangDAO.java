/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSystem;
import com.excellentsystem.TokoEmasJagoPusat.Model.LeburRosokCabang;
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
public class LeburRosokCabangDAO {
    public static List<LeburRosokCabang> getAllByDateAndCabangAndStatusSelesaiAndStatusBatal(
            Connection con, String tglMulai, String tglAkhir, String kodeCabang, 
            String statusSelesai, String statusBatal)throws Exception{
        String sql = "select * from tt_lebur_rosok_cabang "
                + " where mid(no_lebur,4,6) between ? and ? ";
        if(!kodeCabang.equals("%"))
            sql = sql + " and kode_cabang = '"+kodeCabang+"' ";
        if(!statusSelesai.equals("%"))
            sql = sql + " and status_selesai = '"+statusSelesai+"' ";
        if(!statusBatal.equals("%"))
            sql = sql + " and status_batal = '"+statusBatal+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, tglSystem.format(tglBarang.parse(tglMulai)));
        ps.setString(2, tglSystem.format(tglBarang.parse(tglAkhir)));
        List<LeburRosokCabang> listLeburRosok = new ArrayList<>();
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            LeburRosokCabang lebur = new LeburRosokCabang();
            lebur.setNoLebur(rs.getString(1));
            lebur.setTglLebur(rs.getString(2));
            lebur.setKodeCabang(rs.getString(3));
            lebur.setBeratKotor(rs.getDouble(4));
            lebur.setBeratPersen(rs.getDouble(5));
            lebur.setNilaiPokok(rs.getDouble(6));
            lebur.setBeratJadi(rs.getDouble(7));
            lebur.setKodeUser(rs.getString(8));
            lebur.setStatusSelesai(rs.getString(9));
            lebur.setTglSelesai(rs.getString(10));
            lebur.setUserSelesai(rs.getString(11));
            lebur.setStatusBatal(rs.getString(12));
            lebur.setTglBatal(rs.getString(13));
            lebur.setUserBatal(rs.getString(14));
            listLeburRosok.add(lebur);
        }
        return listLeburRosok;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(no_lebur,4)) from tt_lebur_rosok_cabang "
                + "where mid(no_lebur,4,6)=? ");
        DecimalFormat df = new DecimalFormat("0000");
        ps.setString(1, tglSystem.format(tglBarang.parse(sistem.getTglSystem())));
        ResultSet rs = ps.executeQuery();
        String noHancur;
        if(rs.next())
            noHancur = "LR-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(rs.getInt(1)+1);
        else
            noHancur = "LR-"+tglSystem.format(tglBarang.parse(sistem.getTglSystem()))+"-"+df.format(1);
        return noHancur;
    }
    public static void insert(Connection con,LeburRosokCabang leburRosok)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tt_lebur_rosok_cabang "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, leburRosok.getNoLebur());
        ps.setString(2, leburRosok.getTglLebur());
        ps.setString(3, leburRosok.getKodeCabang());
        ps.setDouble(4, leburRosok.getBeratKotor());
        ps.setDouble(5, leburRosok.getBeratPersen());
        ps.setDouble(6, leburRosok.getNilaiPokok());
        ps.setDouble(7, leburRosok.getBeratJadi());
        ps.setString(8, leburRosok.getKodeUser());
        ps.setString(9, leburRosok.getStatusSelesai());
        ps.setString(10, leburRosok.getTglSelesai());
        ps.setString(11, leburRosok.getUserSelesai());
        ps.setString(12, leburRosok.getStatusBatal());
        ps.setString(13, leburRosok.getTglBatal());
        ps.setString(14, leburRosok.getUserBatal());
        ps.executeUpdate();
    }
    public static void update(Connection con,LeburRosokCabang leburRosok)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tt_lebur_rosok_cabang set "
                + " tgl_lebur=?, kode_cabang=?, berat_kotor=?, berat_persen=?, "
                + " nilai_pokok=?, berat_jadi=?, kode_user=?, status_selesai=?, "
                + " tgl_selesai=?, user_selesai=?, status_batal=?, tgl_batal=?, user_batal=? "
                + " where no_lebur = ?");
        ps.setString(1, leburRosok.getTglLebur());
        ps.setString(2, leburRosok.getKodeCabang());
        ps.setDouble(3, leburRosok.getBeratKotor());
        ps.setDouble(4, leburRosok.getBeratPersen());
        ps.setDouble(5, leburRosok.getNilaiPokok());
        ps.setDouble(6, leburRosok.getBeratJadi());
        ps.setString(7, leburRosok.getKodeUser());
        ps.setString(8, leburRosok.getStatusSelesai());
        ps.setString(9, leburRosok.getTglSelesai());
        ps.setString(10, leburRosok.getUserSelesai());
        ps.setString(11, leburRosok.getStatusBatal());
        ps.setString(12, leburRosok.getTglBatal());
        ps.setString(13, leburRosok.getUserBatal());
        ps.setString(14, leburRosok.getNoLebur());
        ps.executeUpdate();
    }
}
