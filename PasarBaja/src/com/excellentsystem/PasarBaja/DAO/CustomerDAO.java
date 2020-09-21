/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.DAO;

import com.excellentsystem.PasarBaja.Model.Customer;
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
public class CustomerDAO {
    public static List<Customer> getAllByStatus(Connection con, String status)throws Exception{
        String sql = "select * from tm_customer ";
        if(!status.equals("%"))
            sql = sql + " where status = '"+status+"' ";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Customer> allCustomer = new ArrayList<>();
        while(rs.next()){
            Customer c = new Customer();
            c.setKodeCustomer(rs.getString(1));
            c.setNama(rs.getString(2));
            c.setAlamat(rs.getString(3));
            c.setKota(rs.getString(4));
            c.setNegara(rs.getString(5));
            c.setKodePos(rs.getString(6));
            c.setEmail(rs.getString(7));
            c.setKontakPerson(rs.getString(8));
            c.setNoTelp(rs.getString(9));
            c.setNoHandphone(rs.getString(10));
            c.setKodeSales(rs.getString(11));
            c.setBank(rs.getString(12));
            c.setAtasNamaRekening(rs.getString(13));
            c.setNoRekening(rs.getString(14));
            c.setLimitHutang(rs.getDouble(15));
            c.setHutang(rs.getDouble(16));
            c.setDeposit(rs.getDouble(17));
            c.setStatus(rs.getString(18));
            allCustomer.add(c);
        }
        return allCustomer;
    }
    public static Customer get(Connection con, String kodeCustomer)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_customer where kode_customer = ?");
        ps.setString(1, kodeCustomer);
        ResultSet rs = ps.executeQuery();
        Customer c = null;
        while(rs.next()){
            c = new Customer();
            c.setKodeCustomer(rs.getString(1));
            c.setNama(rs.getString(2));
            c.setAlamat(rs.getString(3));
            c.setKota(rs.getString(4));
            c.setNegara(rs.getString(5));
            c.setKodePos(rs.getString(6));
            c.setEmail(rs.getString(7));
            c.setKontakPerson(rs.getString(8));
            c.setNoTelp(rs.getString(9));
            c.setNoHandphone(rs.getString(10));
            c.setKodeSales(rs.getString(11));
            c.setBank(rs.getString(12));
            c.setAtasNamaRekening(rs.getString(13));
            c.setNoRekening(rs.getString(14));
            c.setLimitHutang(rs.getDouble(15));
            c.setHutang(rs.getDouble(16));
            c.setDeposit(rs.getDouble(17));
            c.setStatus(rs.getString(18));
        }
        return c;
    }
    public static String getId(Connection con)throws Exception{
        PreparedStatement ps = con.prepareStatement("select max(right(kode_customer,5)) from tm_customer");
        ResultSet rs = ps.executeQuery();
        if(rs.next())
            return "CS-"+new DecimalFormat("00000").format(rs.getInt(1)+1);
        else
            return "CS-"+new DecimalFormat("00000").format(1);
    }
    public static void insert(Connection con, Customer c)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_customer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getKodeCustomer());
        ps.setString(2, c.getNama());
        ps.setString(3, c.getAlamat());
        ps.setString(4, c.getKota());
        ps.setString(5, c.getNegara());
        ps.setString(6, c.getKodePos());
        ps.setString(7, c.getEmail());
        ps.setString(8, c.getKontakPerson());
        ps.setString(9, c.getNoTelp());
        ps.setString(10, c.getNoHandphone());
        ps.setString(11, c.getKodeSales());
        ps.setString(12, c.getBank());
        ps.setString(13, c.getAtasNamaRekening());
        ps.setString(14, c.getNoRekening());
        ps.setDouble(15, c.getLimitHutang());
        ps.setDouble(16, c.getHutang());
        ps.setDouble(17, c.getDeposit());
        ps.setString(18, c.getStatus());
        ps.executeUpdate();
    }
    public static void update(Connection con, Customer c)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_customer set nama=?, alamat=?, kota=?, negara=?, kode_pos=?,"
            + " email=?, kontak_person=?, no_telp=?, no_handphone=?, kode_sales=?, bank=?, atas_nama_rekening=?, no_rekening=?"
            + " ,limit_hutang =?, hutang=?, deposit=?, status=? where kode_customer=?");
        ps.setString(1, c.getNama());
        ps.setString(2, c.getAlamat());
        ps.setString(3, c.getKota());
        ps.setString(4, c.getNegara());
        ps.setString(5, c.getKodePos());
        ps.setString(6, c.getEmail());
        ps.setString(7, c.getKontakPerson());
        ps.setString(8, c.getNoTelp());
        ps.setString(9, c.getNoHandphone());
        ps.setString(10, c.getKodeSales());
        ps.setString(11, c.getBank());
        ps.setString(12, c.getAtasNamaRekening());
        ps.setString(13, c.getNoRekening());
        ps.setDouble(14, c.getLimitHutang());
        ps.setDouble(15, c.getHutang());
        ps.setDouble(16, c.getDeposit());
        ps.setString(17, c.getStatus());
        ps.setString(18, c.getKodeCustomer());
        ps.executeUpdate();
    }
}
