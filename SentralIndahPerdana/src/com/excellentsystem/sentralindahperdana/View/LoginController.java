package com.excellentsystem.sentralindahperdana.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.sentralindahperdana.DAO.OtoritasDAO;
import com.excellentsystem.sentralindahperdana.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.UserDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.User;
import java.sql.Connection;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController {

    @FXML private Label namaPerusahaan;
    @FXML private Label warning;
    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private ImageView logo;
    private Main mainApp;
    private int attempt = 0;
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        namaPerusahaan.setText(sistem.getNama());
        Image img = new Image("file:image/logo.png",250,250,true,true);
        logo.setImage(img);
        warning.setText("");
    }
    @FXML 
    private void handleLoginButton(){
        if("".equals(username.getText())){
            warning.setText("Username belum dipilih");
        }else if(password.getText().equals("")){
            warning.setText("Password masih kosong");
        }else if(attempt>=3){
            System.exit(0);
        }else{
            try(Connection con = Koneksi.getConnection()){
                List<User> allUser = UserDAO.getAllByStatus(con, "true");
                User user = null;
                for(User u: allUser){
                    if(u.getUsername().equals(username.getText()))
                        user = u;
                }
                if(user!=null){
                    if(!user.getPassword().equals(password.getText())){
                        warning.setText("Password masih salah");
                        attempt = attempt +1;
                    }else{
                        user.setOtoritas(OtoritasDAO.getAllByKodeUser(con, user.getUsername()));
                        user.setOtoritasKeuangan(OtoritasKeuanganDAO.getAllByKodeUser(con, user.getUsername()));
                        sistem.setUser(user);
                        mainApp.showMainScene();
                    }
                }else{
                    warning.setText("Username masih salah");
                }
            }catch(Exception e){
                e.printStackTrace();
                warning.setText(e.toString());
            }
        }
    }
   
    
}
