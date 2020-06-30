package com.excellentsystem.jagobangunpersadafx;

import java.sql.Connection;
import java.sql.DriverManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Xtreme
 */
public class Koneksi {
    public static Connection getConnection()throws Exception{
//        String DbName = "jdbc:mysql://localhost:3306/jagobangunpersada?"
//                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
//        Class.forName("com.mysql.jdbc.Driver");
//        return DriverManager.getConnection(DbName,"admin","excellentsystem");
        
        String ipServer = "34.87.153.33";
        String DbName = "jdbc:mysql://"+ipServer+":3306/jagobangunpersada?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName,"root","yunaz051189");
    }
}
