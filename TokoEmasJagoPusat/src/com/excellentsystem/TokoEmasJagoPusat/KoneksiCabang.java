/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat;

import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Xtreme
 */
public class KoneksiCabang {
    public static Connection getConnection(Cabang c)throws Exception{
        String DbName = "jdbc:mysql://"+c.getIpServer()+":3306/tokoemasjago_"+c.getKodeCabang()+"?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.setLoginTimeout(1000);
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
    public static Connection getQuickConnection(Cabang c)throws Exception{
        String DbName = "jdbc:mysql://"+c.getIpServer()+":3306/tokoemasjago_"+c.getKodeCabang()+"?"
                + "connectTimeout=1000&socketTimeout=1000";
        Class.forName("com.mysql.jdbc.Driver");
        DriverManager.setLoginTimeout(1000);
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
}
