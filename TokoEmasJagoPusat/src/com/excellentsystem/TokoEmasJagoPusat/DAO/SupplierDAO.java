/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.DAO;

import com.excellentsystem.TokoEmasJagoPusat.Model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Xtreme
 */
public class SupplierDAO {
    public static List<Supplier> getAll(Connection con)throws Exception{
        List<Supplier> allSupplier = new ArrayList<>();
        PreparedStatement ps = con.prepareStatement("select * from tm_supplier");
        ResultSet rs = ps.executeQuery();
        while(rs.next()){  
            Supplier supplier = new Supplier();
            supplier.setKodeSupplier(rs.getString(1));
            allSupplier.add(supplier);
        }
        return allSupplier;
    }
    public static void insert(Connection con, Supplier supplier)throws Exception{
        PreparedStatement ps = con.prepareStatement("insert into tm_supplier values(?)");
        ps.setString(1, supplier.getKodeSupplier());
        ps.executeUpdate();
    }
    public static void delete(Connection con, Supplier supplier)throws Exception{
        PreparedStatement ps =con.prepareStatement("delete from tm_supplier where kode_supplier=?");
        ps.setString(1, supplier.getKodeSupplier());
        ps.executeUpdate();
    }
}
