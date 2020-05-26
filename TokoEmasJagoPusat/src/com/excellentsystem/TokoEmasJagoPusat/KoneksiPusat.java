package com.excellentsystem.TokoEmasJagoPusat;

import static com.excellentsystem.TokoEmasJagoPusat.Main.ipServer;
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
public class KoneksiPusat {
    public static Connection getConnection()throws Exception{
        String DbName = "jdbc:mysql://"+ipServer+":3306/tokoemasjagopusat?"
                + "connectTimeout=0&socketTimeout=0&autoReconnect=true";
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(DbName,"jago_admin","jagopusat");
    }
}
