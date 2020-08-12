/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.PrintOut;

import com.excellentsystem.sentralindahperdana.Main;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;

public class JRViewerFx  {
    private JasperPrint jasperPrint;
    public void start(){
        try {
            Stage primaryStage = new Stage();
            primaryStage.setMaximized(true);
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png"),150,150,false,true));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PrintOut/FRViewerFx.fxml"));
            BorderPane page = (BorderPane) loader.load();
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Excellent System - Report");
            primaryStage.show();
            FRViewerFxController jrViewerFxController = loader.getController();
            jrViewerFxController.setJasperPrint(jasperPrint);
            jrViewerFxController.show();
        } catch (IOException ex) {
              ex.printStackTrace();
        }
    }
	
    public JRViewerFx(JasperPrint jasperPrint){
        this.jasperPrint = jasperPrint;
        start();
    }

}
