/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Main;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
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
public class AddDetailPemesananPembelianBahanController {

    @FXML
    public TableView<PemesananPembelianBahanDetail> pemesananDetailTable;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> kategoriBahanColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> namaBarangColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> spesifikasiColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> qtyColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> qtyDiterimaColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> hargaColumn;

    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public ObservableList<PemesananPembelianBahanDetail> allPemesananCoilDetail = FXCollections.observableArrayList();

    public void initialize() {
        kategoriBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBahanProperty());
        kategoriBahanColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriBahanColumn));
        
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        namaBarangColumn.setCellFactory(col -> Function.getWrapTableCell(namaBarangColumn));
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        spesifikasiColumn.setCellFactory(col -> Function.getWrapTableCell(spesifikasiColumn));
        
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        
        qtyDiterimaColumn.setCellValueFactory(cellData -> cellData.getValue().qtyDiterimaProperty());
        qtyDiterimaColumn.setCellFactory(col -> Function.getTableCell());
        
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaColumn.setCellFactory(col -> Function.getTableCell());
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

    public void setPemesananDetail(List<PemesananPembelianBahanDetail> listPemesanan) {
        allPemesananCoilDetail.clear();
        allPemesananCoilDetail.addAll(listPemesanan);
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
