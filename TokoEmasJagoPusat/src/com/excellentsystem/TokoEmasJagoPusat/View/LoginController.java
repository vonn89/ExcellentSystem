package com.excellentsystem.TokoEmasJagoPusat.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.decrypt;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.allUser;
import static com.excellentsystem.TokoEmasJagoPusat.Main.key;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.version;
import com.excellentsystem.TokoEmasJagoPusat.Model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController {

    @FXML private Label warningLabel;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox rememberMeCheck;
    @FXML private Label versionLabel;
    private Main mainApp;
    private int attempt = 0;
    public void initialize(){
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
        passwordField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                handleLoginButton();
        });
    }
    public void setMainApp(Main mainApp){
        try{
            this.mainApp = mainApp;
            versionLabel.setText("Ver. "+version);
            warningLabel.setText("");
            usernameField.requestFocus();
            BufferedReader text = new BufferedReader(new FileReader("password"));
            String user = text.readLine();
            if(user!=null){
                usernameField.setText(user);
                passwordField.setText(decrypt(text.readLine(), key));
                rememberMeCheck.setSelected(Boolean.valueOf(text.readLine()));
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void handleLoginButton(){
        if("".equals(usernameField.getText())){
            warningLabel.setText("Username masih kosong");
        }else if(passwordField.getText().equals("")){
            warningLabel.setText("Password masih kosong");
        }else if(attempt>=3){
            System.exit(0);
        }else{
            try{
                User user = null;
                for(User u : allUser){
                    if(u.getKodeUser().equals(usernameField.getText()))
                        user = u;
                }
                if(user==null){
                    warningLabel.setText("Username tidak ditemukan");
                    attempt = attempt +1;
                }else if(!passwordField.getText().equals(decrypt(user.getPassword(), key))){
                    warningLabel.setText("Password masih salah");
                    attempt = attempt +1;
                }else{
                    if(rememberMeCheck.isSelected()){
                        try (FileWriter fw = new FileWriter(new File("password"), false)) {
                            fw.write(user.getKodeUser());
                            fw.write(System.lineSeparator());
                            fw.write(user.getPassword());
                            fw.write(System.lineSeparator());
                            fw.write(String.valueOf(rememberMeCheck.isSelected()));
                        }
                    }else{
                        try (FileWriter fw = new FileWriter(new File("password"), false)) {
                            fw.write("");
                        }
                    }
                    Main.user = user;
                    if(!version.equals(sistem.getVersion()))
                        checkUpdate();
                    else
                        mainApp.showMainScene();
                }
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
   @FXML private GridPane gridPane;
    private void checkUpdate(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                Thread.sleep(1000);
                return Function.downloadUpdateGoogleStorage("Toko Emas Jago Pusat.exe");
            }
        };
        task.setOnRunning((e) -> {
            gridPane.setVisible(true);
                mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
            gridPane.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setContentText(task.getValue());
            alert.showAndWait();
            System.exit(0);
        });
        task.setOnFailed((e) -> {
                mainApp.closeLoading();
            gridPane.setVisible(false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Update gagal silakan coba kembali \n"
                        + task.getException().toString());
            alert.showAndWait();
            System.exit(0);
        });
        new Thread(task).start();
    }
    
}
