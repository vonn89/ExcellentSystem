/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.PenggabunganPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class PenggabunganPropertyController {

    @FXML
    private TableView<PenggabunganPropertyDetail> propertyTable;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, String> namaPropertyColumn;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, Number> luasTanahColumn;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, Number> nilaiPropertyColumn;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, Number> nilaiPropertyPerMeterColumn;
    @FXML
    private TableColumn<PenggabunganPropertyDetail, Number> hargaJualColumn;

    @FXML
    public Label totalPropertyLabel;
    @FXML
    public Label totalLuasTanahLabel;
    @FXML
    public Label totalNilaiPropertyLabel;
    @FXML
    private Label nilaiPropertyPerMeterLabel;
    @FXML
    public Label totalJualLabel;
    @FXML
    public Button saveButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public ObservableList<PenggabunganPropertyDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        luasTanahColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));

        nilaiPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().nilaiPropertyProperty());
        nilaiPropertyColumn.setCellFactory(col -> getTableCell(rp));

        nilaiPropertyPerMeterColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getProperty().getNilaiProperty()
                    / cellData.getValue().getProperty().getLuasTanah());
        });
        nilaiPropertyPerMeterColumn.setCellFactory(col -> getTableCell(rp));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        final ContextMenu rowMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add Property");
        add.setOnAction((ActionEvent e) -> {
            addChildren();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            propertyTable.refresh();
        });
        rowMenu.getItems().addAll(add, refresh);
        propertyTable.setContextMenu(rowMenu);
        propertyTable.setRowFactory((TableView<PenggabunganPropertyDetail> tableView) -> {
            final TableRow<PenggabunganPropertyDetail> row = new TableRow<PenggabunganPropertyDetail>() {
                @Override
                public void updateItem(PenggabunganPropertyDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem add = new MenuItem("Add Property");
                        add.setOnAction((ActionEvent e) -> {
                            addChildren();
                        });
                        MenuItem delete = new MenuItem("Delete Property");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteDetail(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            propertyTable.refresh();
                        });
                        rowMenu.getItems().addAll(add, delete, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        propertyTable.setItems(allDetail);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 80 / 100);
        stage.setWidth(mainApp.screenSize.getWidth() * 80 / 100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    private void deleteDetail(PenggabunganPropertyDetail detail) {
        allDetail.remove(detail);
        propertyTable.refresh();
        hitungTotal();
    }

    private void hitungTotal() {
        int totalProperty = 0;
        double totalLuasTanah = 0;
        double totalNilaiProperty = 0;
        double nilaiPropertyPerMeter = 0;
        double totalJual = 0;
        for (PenggabunganPropertyDetail x : allDetail) {
            totalProperty = totalProperty + 1;
            totalLuasTanah = totalLuasTanah + x.getProperty().getLuasTanah();
            totalNilaiProperty = totalNilaiProperty + x.getProperty().getNilaiProperty();
            totalJual = totalJual + x.getProperty().getHargaJual();
        }
        if (totalLuasTanah != 0) {
            nilaiPropertyPerMeter = totalNilaiProperty / totalLuasTanah;
        }
        propertyTable.refresh();
        totalJualLabel.setText(rp.format(totalJual));
        totalPropertyLabel.setText(qty.format(totalProperty));
        totalLuasTanahLabel.setText(qty.format(totalLuasTanah));
        totalNilaiPropertyLabel.setText(rp.format(totalNilaiProperty));
        nilaiPropertyPerMeterLabel.setText(rp.format(nilaiPropertyPerMeter));
    }

    @FXML
    private void addChildren() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> statusProp = new ArrayList<>();
        statusProp.add("Available");
        x.getProperty(statusProp);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Property p = row.getItem();
                    Boolean status = true;
                    for (PenggabunganPropertyDetail a : allDetail) {
                        if (a.getKodeProperty().equals(p.getKodeProperty())) {
                            status = false;
                        }
                    }
                    if (status) {
                        PenggabunganPropertyDetail detail = new PenggabunganPropertyDetail();
                        detail.setKodeProperty(p.getKodeProperty());
                        detail.setProperty(p);
                        allDetail.add(detail);
                        hitungTotal();
                        mainApp.closeDialog(stage, child);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Warning", "Kode property sudah pernah diinput");
                    }
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
