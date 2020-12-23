/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.AddendumDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Addendum;
import java.sql.Connection;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailAddendumController {

    @FXML
    public TextField noAddendumField;
    @FXML
    public TextField tglAddendumField;
    @FXML
    public TextField propertyField;
    @FXML
    public TextField customerField;
    @FXML
    public TextArea keteranganField;
    @FXML
    public TextField perubahanHargaField;

    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void getAddendum(String noAddendum) {
        Task<Addendum> task = new Task<Addendum>() {
            @Override
            public Addendum call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    Addendum a = AddendumDAO.get(con, noAddendum);
                    a.setProperty(PropertyDAO.get(con, a.getKodeProperty()));
                    a.setCustomer(CustomerDAO.get(con, a.getKodeCustomer()));
                    return a;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            setAddendum(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }

    public void setAddendum(Addendum a) {
        try {
            noAddendumField.setText(a.getNoAddendum());
            tglAddendumField.setText(tglLengkap.format(tglSql.parse(a.getTglAddendum())));
            propertyField.setText(a.getProperty().getNamaProperty());
            customerField.setText(a.getCustomer().getNama());
            keteranganField.setText(a.getKeterangan());
            perubahanHargaField.setText(qty.format(a.getPerubahanHargaProperty()));
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
