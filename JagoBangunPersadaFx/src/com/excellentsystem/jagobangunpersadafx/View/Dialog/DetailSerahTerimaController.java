/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Model.SerahTerima;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailSerahTerimaController {

    @FXML
    private TextField noSerahTerimaField;
    @FXML
    private TextField tglSerahTerimaField;
    @FXML
    private TextField namaPropertyField;
    @FXML
    private TextField hargaField;
    @FXML
    private TextField diskonField;
    @FXML
    private TextField addendumField;
    @FXML
    private TextField namaCustomerField;

    @FXML
    public TextField noHGBField;
    @FXML
    public TextField noIMBField;
    @FXML
    public TextArea kelengkapanField;
    @FXML
    public TextArea bonusField;

    @FXML
    private TextField totalDPField;
    @FXML
    private TextField totalKPRField;
    @FXML
    private Button propertyButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SerahTerima serahTerima;

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

    public void setNewSerahTerima() {
        serahTerima = new SerahTerima();
        noSerahTerimaField.setText("-");
        tglSerahTerimaField.setText(tglSql.format(new Date()));
    }

    public void setSerahTerima(SerahTerima s) {
        try {
            noHGBField.setDisable(true);
            noIMBField.setDisable(true);
            kelengkapanField.setDisable(true);
            bonusField.setDisable(true);
            propertyButton.setVisible(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);

            noSerahTerimaField.setText(s.getNoSerahTerima());
            tglSerahTerimaField.setText(tglLengkap.format(tglSql.parse(s.getTglSerahTerima())));
            namaPropertyField.setText(s.getProperty().getNamaProperty());
            hargaField.setText(rp.format(s.getHarga()));
            diskonField.setText(rp.format(s.getDiskon()));
            addendumField.setText(rp.format(s.getAddendum()));
            namaCustomerField.setText(s.getCustomer().getNama());

            noHGBField.setText(s.getNoHGB());
            noIMBField.setText(s.getNoIMB());
            kelengkapanField.setText(s.getKelengkapan());
            bonusField.setText(s.getBonus());

            totalDPField.setText(rp.format(s.getTotalDP()));
            totalKPRField.setText(rp.format(s.getTotalKPR()));
            stage.setHeight(stage.getHeight() - 20);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void setProperty() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> status = new ArrayList<>();
        status.add("Reserved");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Task<String> task = new Task<String>() {
                        @Override
                        public String call() throws Exception {
                            try (Connection con = Koneksi.getConnection()) {
                                if (SKLHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true") != null) {
                                    serahTerima.setProperty(row.getItem());
                                    STJHead stj = STJHeadDAO.getByKodeProperty(con, serahTerima.getProperty().getKodeProperty(), "true");
                                    double totalDP = stj.getJumlahRp();
                                    List<SDP> listSDP = SDPDAO.getAllByKodeProperty(con, serahTerima.getProperty().getKodeProperty(), "true");
                                    for (SDP sdp : listSDP) {
                                        totalDP = totalDP + sdp.getJumlahRp();
                                    }
                                    serahTerima.setTotalDP(totalDP);
                                    KPR kpr = KPRDAO.getByKodeProperty(con, serahTerima.getProperty().getKodeProperty());
                                    if (kpr != null) {
                                        serahTerima.setTotalKPR(kpr.getJumlahRp());
                                    }
                                    Customer c = CustomerDAO.get(con, stj.getKodeCustomer());
                                    serahTerima.setCustomer(c);
                                    return "true";
                                } else {
                                    return "false";
                                }
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((WorkerStateEvent e) -> {
                        try {
                            mainApp.closeLoading();
                            if (task.getValue().equals("true")) {
                                namaPropertyField.setText(serahTerima.getProperty().getNamaProperty());
                                namaCustomerField.setText(serahTerima.getCustomer().getNama());
                                hargaField.setText(rp.format(serahTerima.getProperty().getHargaJual()));
                                diskonField.setText(rp.format(serahTerima.getProperty().getDiskon()));
                                addendumField.setText(rp.format(serahTerima.getProperty().getAddendum()));
                                totalDPField.setText(rp.format(serahTerima.getTotalDP()));
                                totalKPRField.setText(rp.format(serahTerima.getTotalKPR()));
                                mainApp.closeDialog(stage, child);
                            } else {
                                mainApp.showMessage(Modality.NONE, "Warning", "Serah terima tidak dapat dilakukan, "
                                        + "karena surat keterangan lunas belum dibuat");
                            }
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
            });
            return row;
        });
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
