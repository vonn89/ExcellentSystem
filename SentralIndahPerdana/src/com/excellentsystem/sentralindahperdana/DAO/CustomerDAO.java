/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.DAO;

import com.excellentsystem.sentralindahperdana.Model.Customer;
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
    public static Customer get(Connection con, String kodeCustomer)throws Exception{
        Customer customer;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_customer where kode_customer like ?")) {
            ps.setString(1, kodeCustomer);
            try (ResultSet rs = ps.executeQuery()) {
                customer = null;
                if(rs.next()){
                    customer = new Customer();
                    customer.setKodeCustomer(rs.getString(1));
                    customer.setNama(rs.getString(2));
                    customer.setAlamat(rs.getString(3));
                    customer.setKota(rs.getString(4));
                    customer.setNegara(rs.getString(5));
                    customer.setKodePos(rs.getString(6));
                    customer.setEmail(rs.getString(7));
                    customer.setKontakPerson(rs.getString(8));
                    customer.setNoTelp(rs.getString(9));
                    customer.setNoHandphone(rs.getString(10));
                    customer.setBank(rs.getString(11));
                    customer.setAtasNamaRekening(rs.getString(12));
                    customer.setNoRekening(rs.getString(13));
                    customer.setDeposit(rs.getDouble(14));
                    customer.setHutang(rs.getDouble(15));
                    customer.setLimitHutang(rs.getDouble(16));
                    customer.setStatus(rs.getString(17));
                }
            }
        }
        return customer;
    }
    public static List<Customer> getAllByStatus(Connection con, String status)throws Exception{
        List<Customer> allCustomer;
        try (PreparedStatement ps = con.prepareStatement("select * from tm_customer where status like ?")) {
            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                allCustomer = new ArrayList<>();
                while(rs.next()){
                    Customer customer = new Customer();
                    customer.setKodeCustomer(rs.getString(1));
                    customer.setNama(rs.getString(2));
                    customer.setAlamat(rs.getString(3));
                    customer.setKota(rs.getString(4));
                    customer.setNegara(rs.getString(5));
                    customer.setKodePos(rs.getString(6));
                    customer.setEmail(rs.getString(7));
                    customer.setKontakPerson(rs.getString(8));
                    customer.setNoTelp(rs.getString(9));
                    customer.setNoHandphone(rs.getString(10));
                    customer.setBank(rs.getString(11));
                    customer.setAtasNamaRekening(rs.getString(12));
                    customer.setNoRekening(rs.getString(13));
                    customer.setDeposit(rs.getDouble(14));
                    customer.setHutang(rs.getDouble(15));
                    customer.setLimitHutang(rs.getDouble(16));
                    customer.setStatus(rs.getString(17));
                    allCustomer.add(customer);
                }
            }
        }
        return allCustomer;
    }
    public static String getId(Connection con)throws Exception{
        String kodeCustomer;
        try (PreparedStatement ps = con.prepareStatement("select max(right(kode_customer,5)) from tm_customer")) {
            DecimalFormat df = new DecimalFormat("00000");
            kodeCustomer = "CS-"+df.format(1);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    kodeCustomer = "CS-"+df.format(rs.getInt(1)+1);
            }
        }
        return kodeCustomer;
    }
    public static void update(Connection con, Customer customer)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("update tm_customer set nama=?, alamat=?, kota=?, negara=?, kode_pos=?,"
                + " email=?, kontak_person=?, no_telp=?, no_handphone=?, bank=?, atas_nama_rekening=?, no_rekening=?"
                + " ,limit_hutang =?, hutang=?, deposit=?, status=? where kode_customer=?")) {
            ps.setString(1, customer.getNama());
            ps.setString(2, customer.getAlamat());
            ps.setString(3, customer.getKota());
            ps.setString(4, customer.getNegara());
            ps.setString(5, customer.getKodePos());
            ps.setString(6, customer.getEmail());
            ps.setString(7, customer.getKontakPerson());
            ps.setString(8, customer.getNoTelp());
            ps.setString(9, customer.getNoHandphone());
            ps.setString(10, customer.getBank());
            ps.setString(11, customer.getAtasNamaRekening());
            ps.setString(12, customer.getNoRekening());
            ps.setDouble(13, customer.getLimitHutang());
            ps.setDouble(14, customer.getHutang());
            ps.setDouble(15, customer.getDeposit());
            ps.setString(16, customer.getStatus());
            ps.setString(17, customer.getKodeCustomer());
            ps.executeUpdate();
        }
    }
    public static void insert(Connection con, Customer customer)throws Exception{
        try (PreparedStatement ps = con.prepareStatement("insert into tm_customer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, customer.getKodeCustomer());
            ps.setString(2, customer.getNama());
            ps.setString(3, customer.getAlamat());
            ps.setString(4, customer.getKota());
            ps.setString(5, customer.getNegara());
            ps.setString(6, customer.getKodePos());
            ps.setString(7, customer.getEmail());
            ps.setString(8, customer.getKontakPerson());
            ps.setString(9, customer.getNoTelp());
            ps.setString(10, customer.getNoHandphone());
            ps.setString(11, customer.getBank());
            ps.setString(12, customer.getAtasNamaRekening());
            ps.setString(13, customer.getNoRekening());
            ps.setDouble(14, customer.getDeposit());
            ps.setDouble(15, customer.getHutang());
            ps.setDouble(16, customer.getLimitHutang());
            ps.setString(17, customer.getStatus());
            ps.executeUpdate();
        }
    }
}
