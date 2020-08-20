/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellOnlyThisMonth;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailTransaksiKeuanganController {

    @FXML
    private GridPane gridpane;
    @FXML
    private TextField noKeuanganField;
    @FXML
    private TextField tglInputField;
    @FXML
    private TextField userInputField;

    @FXML
    public DatePicker tglKeuanganPicker;
    @FXML
    public TextArea keteranganField;
    @FXML
    public TextField jumlahRpField;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public TextField totalPropertyField;
    @FXML
    public TextField totalImageField;

    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public String metode = "Rata-rata";
    public ObservableList<Keuangan> allDetail = FXCollections.observableArrayList();
    public ObservableList<ImageView> listImage = FXCollections.observableArrayList();

    public void initialize() {
        Function.setNumberField(jumlahRpField);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try {
            this.mainApp = mainApp;
            this.stage = stage;
            this.owner = owner;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            kategoriCombo.setItems(Function.getKategoriTransaksi());
            tipeKeuanganCombo.setItems(Function.getTipeKeuangan());
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    public void setNewKeuangan() {
        noKeuanganField.setText("-");
        tglInputField.setText(tglLengkap.format(new Date()));
        userInputField.setText(Main.sistem.getUser().getUsername());
        tglKeuanganPicker.setConverter(Function.getTglConverter());
        tglKeuanganPicker.setValue(LocalDate.now());
        tglKeuanganPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellOnlyThisMonth(LocalDate.now()));
    }

    public void setDetailKeuangan(Keuangan k) {
        try (Connection con = Koneksi.getConnection()) {
            List<Property> allProperty = PropertyDAO.getAll(con);
            List<Keuangan> allKeuangan = KeuanganDAO.getAllByNoKeuangan(con, k.getNoKeuangan());
            for (Keuangan x : allKeuangan) {
                if (!x.getKodeProperty().equals("") && x.getTipeKeuangan().equals(k.getTipeKeuangan())) {
                    for (Property p : allProperty) {
                        if (p.getKodeProperty().equals(x.getKodeProperty())) {
                            x.setProperty(p);
                        }
                    }
                    x.setChecked(true);
                    allDetail.add(x);
                }
            }
            noKeuanganField.setText(k.getNoKeuangan());
            tglInputField.setText(tglLengkap.format(tglSql.parse(k.getTglInput())));
            userInputField.setText(k.getKodeUser());
            tglKeuanganPicker.setValue(LocalDate.parse(k.getTglKeuangan(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            tglKeuanganPicker.setDisable(true);
            kategoriCombo.getSelectionModel().select(k.getKategori());
            kategoriCombo.setDisable(true);
            keteranganField.setText(k.getDeskripsi());
            keteranganField.setDisable(true);
            jumlahRpField.setText(rp.format(k.getJumlahRp()));
            jumlahRpField.setDisable(true);
            totalImageField.setText(rp.format(k.getTotalImage()));
            tipeKeuanganCombo.getSelectionModel().select(k.getTipeKeuangan());
            tipeKeuanganCombo.setDisable(true);
            totalPropertyField.setText(rp.format(allDetail.size()));
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            gridpane.getRowConstraints().remove(13);
            stage.setHeight(465);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void setDetailProperty() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTransaksiKeuanganProperty.fxml");
        DetailTransaksiKeuanganPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setProperty(metode, allDetail, Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")));
        if (!saveButton.isVisible()) {
            x.setViewOnly();
        }
        x.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            metode = x.metodeCombo.getSelectionModel().getSelectedItem();
            allDetail = x.allDetail;
            double total = 0;
            for (Keuangan d : allDetail) {
                if (d.isChecked()) {
                    total = total + 1;
                }
            }
            totalPropertyField.setText(rp.format(total));
        });
    }

    @FXML
    private void setImage() {
        if (!saveButton.isVisible() && !totalImageField.getText().equals("0")) {
            try {
                StorageOptions storageOptions = StorageOptions.newBuilder().
                        setCredentials(GoogleCredentials.fromStream(Main.class.getResourceAsStream("Resource/credentials.json"))).build();
                Storage storage = storageOptions.getService();
                Bucket bucket = storage.get("jagobangunpersada");
                Page<Blob> blobs = bucket.list();
                for (Blob blob : blobs.iterateAll()) {
                    if (blob.getName().startsWith(noKeuanganField.getText())) {
                        File tempFile = new File(blob.getName());
                        blob.downloadTo(Paths.get(tempFile.getPath()));
                        listImage.add(new ImageView(new Image("file:" + tempFile.getPath())));
                        Files.deleteIfExists(tempFile.toPath()); 
                    }
                }
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTransaksiKeuanganImage.fxml");
        DetailTransaksiKeuanganImageController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setImage(listImage, !saveButton.isVisible());
        x.closeButton.setOnAction((event) -> {
            mainApp.closeDialog(stage, child);
            totalImageField.setText(rp.format(listImage.size()));
        });
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
