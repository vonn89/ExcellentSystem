/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Addendum;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import java.sql.Connection;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPembangunanKontraktorController {

    @FXML
    private GridPane gridPane;
    @FXML
    public TextField kontraktorField;
    @FXML
    public TextField propertyField;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public CheckBox addendumCheck;
    @FXML
    public Button addendumButton;
    @FXML
    public Button saveButton;

    private PembangunanKontraktorHead pembangunan;
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        Function.setNumberField(jumlahRpField);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setPembangunan(PembangunanKontraktorHead p, boolean addButton) {
        pembangunan = p;
        if (p.getKontraktor() != null) {
            kontraktorField.setText(p.getKontraktor().getNamaKontraktor());
        }
        if (p.getProperty() != null) {
            propertyField.setText(p.getProperty().getNamaProperty());
        }
        if (!addButton) {
            gridPane.getRowConstraints().get(7).setMinHeight(0);
            gridPane.getRowConstraints().get(7).setPrefHeight(0);
            gridPane.getRowConstraints().get(7).setMaxHeight(0);
            stage.setHeight(stage.getHeight() - 30);
        }
        addendumButton.setVisible(addButton);
        addendumCheck.setVisible(addButton);
    }

    @FXML
    private void setAddendum() {
        if (keteranganField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Keterangan Pembangunan masih kosong");
        } else if (jumlahRpField.getText().equals("0")) {
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Rp masih kosong");
        } else {
            if (pembangunan.getAddendum() == null) {
                try (Connection con = Koneksi.getConnection()) {
                    STJHead stj = STJHeadDAO.getByKodeProperty(con, pembangunan.getKodeProperty(), "true");
                    if (stj == null) {
                        mainApp.showMessage(Modality.NONE, "Warning", "Addendum tidak dapat dibuat, karena surat tanda jadi tidak ditemukan");
                    } else {
                        Customer c = CustomerDAO.get(con, stj.getKodeCustomer());

                        Stage child = new Stage();
                        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewAddendum.fxml");
                        NewAddendumController x = loader.getController();
                        x.setMainApp(mainApp, stage, child);
                        x.setNewAddendum(pembangunan.getProperty().getNamaProperty(), c.getNama(), pembangunan.getProperty().getHargaJual(),
                                keteranganField.getText(), Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")), 0);
                        x.saveButton.setOnAction((event) -> {
                            Addendum a = new Addendum();
                            a.setNoAddendum("");
                            a.setTglAddendum(tglSql.format(new Date()));
                            a.setKodeProperty(pembangunan.getKodeProperty());
                            a.setKodeCustomer(c.getKodeCustomer());
                            a.setKeterangan(x.keteranganField.getText());
                            a.setPerubahanHargaProperty(Double.parseDouble(x.perubahanHargaField.getText().replaceAll(",", "")));
                            a.setKodeUser(sistem.getUser().getUsername());
                            a.setStatus("true");
                            a.setTglBatal("2000-01-01 00:00:00");
                            a.setUserBatal("");
                            a.setCustomer(c);
                            a.setProperty(pembangunan.getProperty());
                            pembangunan.setAddendum(a);

                            mainApp.closeDialog(stage, child);
                            addendumCheck.setSelected(true);
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            } else {
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewAddendum.fxml");
                NewAddendumController x = loader.getController();
                x.setMainApp(mainApp, stage, child);
                x.setNewAddendum(pembangunan.getAddendum().getProperty().getNamaProperty(), pembangunan.getAddendum().getCustomer().getNama(),
                        pembangunan.getAddendum().getProperty().getHargaJual(), pembangunan.getAddendum().getKeterangan(),
                        Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")), pembangunan.getAddendum().getPerubahanHargaProperty());
                x.saveButton.setOnAction((event) -> {
                    pembangunan.getAddendum().setKeterangan(x.keteranganField.getText());
                    pembangunan.getAddendum().setPerubahanHargaProperty(Double.parseDouble(x.perubahanHargaField.getText().replaceAll(",", "")));

                    mainApp.closeDialog(stage, child);
                    addendumCheck.setSelected(true);
                });
            }
        }
        if (pembangunan.getAddendum() == null) {
            addendumCheck.setSelected(false);
        } else {
            addendumCheck.setSelected(true);
        }
    }

    @FXML
    private void addendumToggle() {
        if (addendumCheck.isSelected()) {
            setAddendum();
        } else {
            pembangunan.setAddendum(null);
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
