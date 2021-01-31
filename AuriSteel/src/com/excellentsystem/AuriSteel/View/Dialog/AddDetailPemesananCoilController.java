/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.PemesananBahanDetail;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddDetailPemesananCoilController {

    @FXML
    public TableView<PemesananBahanDetail> pemesananDetailTable;
    @FXML
    private TableColumn<PemesananBahanDetail, String> kategoriBahanColumn;
    @FXML
    private TableColumn<PemesananBahanDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PemesananBahanDetail, String> spesifikasiColumn;
    @FXML
    private TableColumn<PemesananBahanDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PemesananBahanDetail, Number> qtyColumn;

    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public ObservableList<PemesananBahanDetail> allPemesananCoilDetail = FXCollections.observableArrayList();

    public void initialize() {
        kategoriBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBahanProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        pemesananDetailTable.setItems(allPemesananCoilDetail);
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    public void setPemesananDetail(List<PemesananBahanDetail> listPemesanan) {
        allPemesananCoilDetail.clear();
        allPemesananCoilDetail.addAll(listPemesanan);
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
