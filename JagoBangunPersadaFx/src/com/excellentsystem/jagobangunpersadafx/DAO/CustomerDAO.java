/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.DAO;

import com.excellentsystem.jagobangunpersadafx.Model.Customer;
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
        PreparedStatement ps = con.prepareStatement("select * from tm_customer where status like ?");
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();
        List<Customer> allCustomer = new ArrayList<>();
        while(rs.next()){
            Customer c = new Customer();
            c.setKodeCustomer(rs.getString(1));
            c.setNama(rs.getString(2));
            c.setJenisKelamin(rs.getString(3));
            c.setAlamat(rs.getString(4));
            c.setNoTelp(rs.getString(5));
            c.setNoHandphone(rs.getString(6));;
            c.setEmail(rs.getString(7));
            c.setStatusNikah(rs.getString(8));
            c.setAgama(rs.getString(9));
            c.setPekerjaan(rs.getString(10));
            c.setNoKTP(rs.getString(11));
            c.setNoNPWP(rs.getString(12));
            c.setNoSPTPPH(rs.getString(13));
            c.setStatus(rs.getString(14));
            allCustomer.add(c);
        }
        return allCustomer;
    }
    public static Customer get(Connection con, String kodeCustomer)throws Exception{
        PreparedStatement ps = con.prepareStatement("select * from tm_customer where kode_customer=?");
        ps.setString(1, kodeCustomer);
        ResultSet rs = ps.executeQuery();
        Customer c = null;
        while(rs.next()){
            c = new Customer();
            c.setKodeCustomer(rs.getString(1));
            c.setNama(rs.getString(2));
            c.setJenisKelamin(rs.getString(3));
            c.setAlamat(rs.getString(4));
            c.setNoTelp(rs.getString(5));
            c.setNoHandphone(rs.getString(6));;
            c.setEmail(rs.getString(7));
            c.setStatusNikah(rs.getString(8));
            c.setAgama(rs.getString(9));
            c.setPekerjaan(rs.getString(10));
            c.setNoKTP(rs.getString(11));
            c.setNoNPWP(rs.getString(12));
            c.setNoSPTPPH(rs.getString(13));
            c.setStatus(rs.getString(14));
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
    public static void update(Connection con, Customer c)throws Exception{
        PreparedStatement ps = con.prepareStatement("update tm_customer set nama=?, jenis_kelamin=?, alamat=?, no_telp=?, "
            + " no_handphone=?, email=?, status_nikah=?, agama=?, pekerjaan=?,  no_ktp=?, no_npwp=?, no_spt_pph=?, status=?"
            + " where kode_customer=?");
        ps.setString(1, c.getNama());
        ps.setString(2, c.getJenisKelamin());
        ps.setString(3, c.getAlamat());
        ps.setString(4, c.getNoTelp());
        ps.setString(5, c.getNoHandphone());
        ps.setString(6, c.getEmail());
        ps.setString(7, c.getStatusNikah());
        ps.setString(8, c.getAgama());
        ps.setString(9, c.getPekerjaan());
        ps.setString(10, c.getNoKTP());
        ps.setString(11, c.getNoNPWP());
        ps.setString(12, c.getNoSPTPPH());
        ps.setString(13, c.getStatus());
        ps.setString(14, c.getKodeCustomer());
        ps.executeUpdate();
    }
    public static void insert(Connection con, Customer c)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_customer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        ps.setString(1, c.getKodeCustomer());
        ps.setString(2, c.getNama());
        ps.setString(3, c.getJenisKelamin());
        ps.setString(4, c.getAlamat());
        ps.setString(5, c.getNoTelp());
        ps.setString(6, c.getNoHandphone());
        ps.setString(7, c.getEmail());
        ps.setString(8, c.getStatusNikah());
        ps.setString(9, c.getAgama());
        ps.setString(10, c.getPekerjaan());
        ps.setString(11, c.getNoKTP());
        ps.setString(12, c.getNoNPWP());
        ps.setString(13, c.getNoSPTPPH());
        ps.setString(14, c.getStatus());
        ps.executeUpdate();
    }
}
