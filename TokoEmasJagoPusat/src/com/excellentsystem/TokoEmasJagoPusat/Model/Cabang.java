/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.TokoEmasJagoPusat.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Yunaz
 */
public class Cabang {
    //tm_cabang
    private final StringProperty kodeCabang = new SimpleStringProperty();
    private final StringProperty namaCabang = new SimpleStringProperty();
    private final StringProperty ipServer = new SimpleStringProperty();
    
    private final BooleanProperty check = new SimpleBooleanProperty();

    public boolean isCheck() {
        return check.get();
    }

    public void setCheck(boolean value) {
        check.set(value);
    }

    public BooleanProperty checkProperty() {
        return check;
    }

    public String getIpServer() {
        return ipServer.get();
    }

    public void setIpServer(String value) {
        ipServer.set(value);
    }

    public StringProperty ipServerProperty() {
        return ipServer;
    }

    public String getNamaCabang() {
        return namaCabang.get();
    }

    public void setNamaCabang(String value) {
        namaCabang.set(value);
    }

    public StringProperty namaCabangProperty() {
        return namaCabang;
    }

    public String getKodeCabang() {
        return kodeCabang.get();
    }

    public void setKodeCabang(String value) {
        kodeCabang.set(value);
    }

    public StringProperty kodeCabangProperty() {
        return kodeCabang;
    }
    
    
}
