package com.excellentsystem.sentralindahperdana;

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
        String ipServer = "localhost";
//        String ipServer = "153.92.9.24";
        String DbName = "jdbc:mysql://"+ipServer+":3306/excellentsystem_sentralindahperdana?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName,"excellen_admin","excellentsystem");
    }
}
