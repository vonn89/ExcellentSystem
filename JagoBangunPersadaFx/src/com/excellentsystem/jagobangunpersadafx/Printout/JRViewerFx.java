/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.jagobangunpersadafx.Printout;

import com.excellentsystem.jagobangunpersadafx.Main;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperPrint;


/**
 * 
 */

/**
 * @author  Michael Grecol
 *	@project JasperViewerFx
 * @filename JRViewerFx.java
 * @date Mar 23, 2015
 */
public class JRViewerFx  {
    public JRViewerFx(JasperPrint jasperPrint) throws IOException{
        Stage primaryStage = new Stage();
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Printout/FRViewerFx.fxml"));
        BorderPane page = (BorderPane) loader.load();
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JBP Report");
        primaryStage.show();
        Object o = loader.getController();
        if(o instanceof FRViewerFxController){
            FRViewerFxController jrViewerFxController = (FRViewerFxController)   o;
            jrViewerFxController.setJasperPrint(jasperPrint);
            jrViewerFxController.show();
        }
    }

}
