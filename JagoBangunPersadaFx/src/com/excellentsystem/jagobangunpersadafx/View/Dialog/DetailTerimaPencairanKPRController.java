/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
public class DetailTerimaPencairanKPRController {

    @FXML
    private TableView<SKLDetail> SKLDetailtable;
    @FXML
    private TableColumn<SKLDetail, String> tahapColumn;
    @FXML
    private TableColumn<SKLDetail, String> tglPembayaranColumn;
    @FXML
    private TableColumn<SKLDetail, Number> jumlahPembayaranColumn;
    @FXML
    private TextField noKPRField;
    @FXML
    private TextField tglKPRField;
    @FXML
    public TextField namaPropertyField;
    @FXML
    private TextField hargaField;
    @FXML
    private TextField diskonField;
    @FXML
    private TextField addendumField;
    @FXML
    private TextField namaCustomerField;
    @FXML
    private TextField totalDPField;
    @FXML
    public TextField jumlahDiterimaField;
    @FXML
    public TextField keteranganField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    private Button propertyButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SKLHead skl;
    private ObservableList<SKLDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        tahapColumn.setCellFactory(col -> Function.getWrapTableCell(tahapColumn));

        tglPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().tglBayarProperty());
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));

        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));

        Function.setNumberField(jumlahDiterimaField);

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
        SKLDetailtable.setItems(allDetail);
        SKLDetailtable.setSelectionModel(null);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (OtoritasKeuangan k : sistem.getUser().getOtoritasKeuangan()) {
            listKeuangan.add(k.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
    }

    public void setNewKPR() {
        noKPRField.setText("-");
        tglKPRField.setText(tglSql.format(new Date()));
    }

    public void getKPR(Property p) {
        Task<KPR> task = new Task<KPR>() {
            @Override
            public KPR call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    KPR k = KPRDAO.getByKodeProperty(con, p.getKodeProperty());
                    k.setProperty(p);
                    k.setCustomer(CustomerDAO.get(con, k.getKodeCustomer()));
                    k.setSkl(SKLHeadDAO.getByKodeProperty(con, p.getKodeProperty(), "true"));
                    k.getSkl().setAllDetail(SKLDetailDAO.getAllByNoSkl(con, k.getSkl().getNoSKL()));
                    return k;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                setKPR(task.getValue());
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

    public void setKPR(KPR kpr) {
        noKPRField.setText(kpr.getNoKPR());
        tglKPRField.setText(kpr.getTglKPR());
        namaPropertyField.setText(kpr.getProperty().getNamaProperty());
        hargaField.setText(rp.format(kpr.getProperty().getHargaJual()));
        diskonField.setText(rp.format(kpr.getProperty().getDiskon()));
        addendumField.setText(rp.format(kpr.getProperty().getAddendum()));
        namaCustomerField.setText(kpr.getCustomer().getNama());
        allDetail.clear();
        allDetail.addAll(kpr.getSkl().getAllDetail());
        totalDPField.setText(rp.format(kpr.getSkl().getTotalPembayaran()));
        jumlahDiterimaField.setText(rp.format(kpr.getJumlahRp()));
        keteranganField.setText(kpr.getKeterangan());
        keteranganField.setDisable(true);
        tipeKeuanganCombo.setItems(FXCollections.observableArrayList(kpr.getTipeKeuangan()));
        tipeKeuanganCombo.getSelectionModel().selectFirst();
        tipeKeuanganCombo.setDisable(true);
        propertyButton.setVisible(false);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        stage.setHeight(stage.getHeight() - 30);
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
                    Task<SKLHead> task = new Task<SKLHead>() {
                        @Override
                        public SKLHead call() throws Exception {
                            try (Connection con = Koneksi.getConnection()) {
                                SKLHead skl = SKLHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true");
                                if (skl != null) {
                                    skl.setProperty(row.getItem());
                                    List<SKLDetail> sklDetail = SKLDetailDAO.getAllByNoSkl(con, skl.getNoSKL());
                                    Customer cust = CustomerDAO.get(con, skl.getKodeCustomer());
                                    skl.setCustomer(cust);
                                    skl.setAllDetail(sklDetail);
                                    return skl;
                                } else {
                                    return null;
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
                            if (task.getValue() != null) {
                                skl = task.getValue();
                                namaPropertyField.setText(skl.getProperty().getNamaProperty());
                                hargaField.setText(rp.format(skl.getProperty().getHargaJual()));
                                diskonField.setText(rp.format(skl.getProperty().getDiskon()));
                                addendumField.setText(rp.format(skl.getProperty().getAddendum()));
                                totalDPField.setText(rp.format(skl.getTotalPembayaran()));
                                jumlahDiterimaField.setText(rp.format(skl.getProperty().getHargaJual() - skl.getProperty().getDiskon() + skl.getProperty().getAddendum() - skl.getTotalPembayaran()));
                                namaCustomerField.setText(skl.getCustomer().getNama());
                                allDetail.clear();
                                allDetail.addAll(skl.getAllDetail());
                                mainApp.closeDialog(stage, child);
                            } else {
                                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat menerima pencairan KPR, "
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
