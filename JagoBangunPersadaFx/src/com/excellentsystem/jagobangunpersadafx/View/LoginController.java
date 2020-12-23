/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.UserDAO;
import static com.excellentsystem.jagobangunpersadafx.Function.decrypt;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.key;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LoginController {

    @FXML
    private Label versionLabel;
    @FXML
    private Label warning;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private CheckBox rememberMeCheck;
    private Main mainApp;
    private int attempt = 0;
    private final List<User> allUser = new ArrayList<>();

    @FXML
    private void handleLoginButton() {
        if ("".equals(username.getText())) {
            warning.setText("Username masih kosong");
        } else if (password.getText().equals("")) {
            warning.setText("Password masih kosong");
        } else if (attempt >= 3) {
            System.exit(0);
        } else {
            try {
                User user = null;
                for (User u : allUser) {
                    if (u.getUsername().equals(username.getText()) && password.getText().equals(decrypt(u.getPassword(), key))) {
                        user = u;
                    }
                }
                if (user != null) {
                    try {
                        if (rememberMeCheck.isSelected()) {
                            try (FileWriter fw = new FileWriter(new File("password"), false)) {
                                fw.write(user.getUsername());
                                fw.write(System.lineSeparator());
                                fw.write(user.getPassword());
                                fw.write(System.lineSeparator());
                                fw.write(String.valueOf(rememberMeCheck.isSelected()));
                            }
                        } else {
                            try (FileWriter fw = new FileWriter(new File("password"), false)) {
                                fw.write("");
                            }
                        }
                        sistem.setUser(user);
                        mainApp.showMainScene();
                    } catch (Exception e) {
                        mainApp.showMessage(Modality.NONE, "Error", e.toString());
                    }
                } else {
                    warning.setText("Username/Password masih salah");
                    attempt = attempt + 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }

    public void setMainApp(Main mainApp) {
        try (Connection con = Koneksi.getConnection()) {
            this.mainApp = mainApp;
            versionLabel.setText("Ver. " + mainApp.version);

            BufferedReader text = new BufferedReader(new FileReader("password"));
            String user = text.readLine();
            if (user != null) {
                username.setText(user);
                password.setText(decrypt(text.readLine(), key));
                rememberMeCheck.setSelected(Boolean.valueOf(text.readLine()));
            }

            allUser.addAll(UserDAO.getAll(con));
            List<Otoritas> listOtoritas = OtoritasDAO.getAll(con);
            List<OtoritasKeuangan> listOtoritasKeuangan = OtoritasKeuanganDAO.getAll(con);
            for (User u : allUser) {
                List<Otoritas> otoritas = new ArrayList<>();
                for (Otoritas o : listOtoritas) {
                    if (u.getUsername().equals(o.getUsername())) {
                        otoritas.add(o);
                    }
                }
                u.setOtoritas(otoritas);
                List<OtoritasKeuangan> otoritasKeuangan = new ArrayList<>();
                for (OtoritasKeuangan o : listOtoritasKeuangan) {
                    if (u.getUsername().equals(o.getUsername())) {
                        otoritasKeuangan.add(o);
                    }
                }
                u.setOtoritasKeuangan(otoritasKeuangan);
            }
            warning.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            warning.setText(ex.toString());
        }
    }

}
