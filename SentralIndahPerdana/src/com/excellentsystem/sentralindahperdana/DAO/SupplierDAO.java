/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Supplier;
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
public class SupplierDAO {
    public static List<Supplier> getAllByStatus(Connection con, String status)throws Exception{
        List<Supplier> allSupplier;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_supplier where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allSupplier = new ArrayList<>();
                while(rs.next()){
                    Supplier supplier = new Supplier();
                    supplier.setKodeSupplier(rs.getString(1));
                    supplier.setNama(rs.getString(2));
                    supplier.setAlamat(rs.getString(3));
                    supplier.setKota(rs.getString(4));
                    supplier.setNegara(rs.getString(5));
                    supplier.setKodePos(rs.getString(6));
                    supplier.setEmail(rs.getString(7));
                    supplier.setKontakPerson(rs.getString(8));
                    supplier.setNoTelp(rs.getString(9));
                    supplier.setNoHandphone(rs.getString(10));
                    supplier.setBank(rs.getString(11));
                    supplier.setAtasNamaRekening(rs.getString(12));
                    supplier.setNoRekening(rs.getString(13));
                    supplier.setHutang(rs.getDouble(14));
                    supplier.setDeposit(rs.getDouble(15));
                    supplier.setStatus(rs.getString(16));
                    allSupplier.add(supplier);
                }
            }
        }
        return allSupplier;
    }
    public static Supplier get(Connection con, String kodeSupplier)throws Exception{
        Supplier supplier;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_supplier where kode_supplier=? and status='true'")) {
            ps.setString(1, kodeSupplier);
            try (ResultSet rs = ps.executeQuery()) {
                supplier = null;
                while(rs.next()){
                    supplier = new Supplier();
                    supplier.setKodeSupplier(rs.getString(1));
                    supplier.setNama(rs.getString(2));
                    supplier.setAlamat(rs.getString(3));
                    supplier.setKota(rs.getString(4));
                    supplier.setNegara(rs.getString(5));
                    supplier.setKodePos(rs.getString(6));
                    supplier.setEmail(rs.getString(7));
                    supplier.setKontakPerson(rs.getString(8));
                    supplier.setNoTelp(rs.getString(9));
                    supplier.setNoHandphone(rs.getString(10));
                    supplier.setBank(rs.getString(11));
                    supplier.setAtasNamaRekening(rs.getString(12));
                    supplier.setNoRekening(rs.getString(13));
                    supplier.setHutang(rs.getDouble(14));
                    supplier.setDeposit(rs.getDouble(15));
                    supplier.setStatus(rs.getString(16));
                }
            }
        }
        return supplier;
    }
    public static String getId(Connection con)throws Exception{
        String kodeSupplier;
        try (PreparedStatement ps = con.prepareStatement("select max(right(kode_supplier,4)) from tm_supplier")) {
            DecimalFormat df = new DecimalFormat("0000");
            try (ResultSet rs = ps.executeQuery()) {
                kodeSupplier = "SP-"+df.format(1);
                if(rs.next())
                    kodeSupplier = "SP-"+df.format(rs.getInt(1)+1);
            }
        }
        return kodeSupplier;
    }
    public static void update(Connection con, Supplier supplier)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_supplier set nama=?, alamat=?, kota=?, negara=?, kode_pos=?,"
                + " email=?, kontak_person=?, no_telp=?, no_handphone=?, bank=?, atas_nama_rekening=?, no_rekening=?, hutang=?, deposit=?, status=?"
                + " where kode_supplier=?")) {
            ps.setString(1, supplier.getNama());
            ps.setString(2, supplier.getAlamat());
            ps.setString(3, supplier.getKota());
            ps.setString(4, supplier.getNegara());
            ps.setString(5, supplier.getKodePos());
            ps.setString(6, supplier.getEmail());
            ps.setString(7, supplier.getKontakPerson());
            ps.setString(8, supplier.getNoTelp());
            ps.setString(9, supplier.getNoHandphone());
            ps.setString(10, supplier.getBank());
            ps.setString(11, supplier.getAtasNamaRekening());
            ps.setString(12, supplier.getNoRekening());
            ps.setDouble(13, supplier.getHutang());
            ps.setDouble(14, supplier.getDeposit());
            ps.setString(15, supplier.getStatus());
            ps.setString(16, supplier.getKodeSupplier());
            ps.executeUpdate();
        }
    }
    public static void insert(Connection con, Supplier supplier)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_supplier values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, supplier.getKodeSupplier());
            ps.setString(2, supplier.getNama());
            ps.setString(3, supplier.getAlamat());
            ps.setString(4, supplier.getKota());
            ps.setString(5, supplier.getNegara());
            ps.setString(6, supplier.getKodePos());
            ps.setString(7, supplier.getEmail());
            ps.setString(8, supplier.getKontakPerson());
            ps.setString(9, supplier.getNoTelp());
            ps.setString(10, supplier.getNoHandphone());
            ps.setString(11, supplier.getBank());
            ps.setString(12, supplier.getAtasNamaRekening());
            ps.setString(13, supplier.getNoRekening());
            ps.setDouble(14, supplier.getHutang());
            ps.setDouble(15, supplier.getDeposit());
            ps.setString(16, supplier.getStatus());
            ps.executeUpdate();
        }
    }
}
