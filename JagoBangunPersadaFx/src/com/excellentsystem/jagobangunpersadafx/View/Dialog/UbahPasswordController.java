/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.UserDAO;
import static com.excellentsystem.jagobangunpersadafx.Function.decrypt;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.key;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
public class UbahPasswordController {

    @FXML
    public Label username;
    @FXML
    public PasswordField passwordLama;
    @FXML
    public PasswordField passwordBaru;
    @FXML
    public PasswordField ulangiPasswordBaru;
    @FXML
    public Label warning;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void setMainApp(Main main, Stage owner, Stage stage) {
        this.mainApp = main;
        this.owner = owner;
        this.stage = stage;
        username.setText(sistem.getUser().getUsername());
        warning.setText("");
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void save() {
        try {
            if (passwordLama.getText().equals("")) {
                warning.setText("password lama masih kosong");
            } else if (passwordBaru.getText().equals("")) {
                warning.setText("password baru masih kosong");
            } else if (ulangiPasswordBaru.getText().equals("")) {
                warning.setText("ulangi password baru masih kosong");
            } else if (!passwordLama.getText().equals(decrypt(sistem.getUser().getPassword(), key))) {
                warning.setText("password lama salah");
            } else if (!passwordBaru.getText().equals(ulangiPasswordBaru.getText())) {
                warning.setText("password baru tidak sama");
            } else {
                Task<Void> task = new Task<Void>() {
                    @Override
                    public Void call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            sistem.getUser().setPassword(passwordBaru.getText());
                            UserDAO.update(con, sistem.getUser());
                        }
                        return null;
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        mainApp.showMessage(Modality.NONE, "Success", "Password baru berhasil di simpan");
                        close();
                    } catch (Exception ex) {
                        mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

}
