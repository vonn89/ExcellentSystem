/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class UbahPasswordController  {

    @FXML private Label username;
    @FXML private PasswordField passwordLama;
    @FXML private PasswordField passwordBaru;
    @FXML private PasswordField ulangiPasswordBaru;
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        username.setText(sistem.getUser().getUsername());
    }
    @FXML
    private void save(){
        if(passwordLama.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning","password lama masih kosong");
        else if(passwordBaru.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning","password baru masih kosong");
        else if(ulangiPasswordBaru.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning","ulangi password baru masih kosong");
        else if(!passwordLama.getText().equals(sistem.getUser().getPassword()))
            mainApp.showMessage(Modality.NONE, "Warning","password lama salah");
        else if(!passwordBaru.getText().equals(ulangiPasswordBaru.getText()))
            mainApp.showMessage(Modality.NONE, "Warning","password baru tidak sama");
        else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = Koneksi.getConnection()){
                        sistem.getUser().setPassword(passwordBaru.getText());
                        return Service.saveUpdatePassword(con, sistem.getUser());
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Password baru berhasil di simpan");
                    close();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", status);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
