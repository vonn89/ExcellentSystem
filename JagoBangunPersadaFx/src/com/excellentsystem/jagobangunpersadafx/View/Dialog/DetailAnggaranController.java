/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTreeTableCell;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class DetailAnggaranController {

    @FXML
    private TreeTableView<RAPDetail> anggaranDetailTable;
    @FXML
    private TreeTableColumn<RAPDetail, String> pekerjaanColumn;
    @FXML
    private TreeTableColumn<RAPDetail, String> keteranganColumn;
    @FXML
    private TreeTableColumn<RAPDetail, String> satuanColumn;
    @FXML
    private TreeTableColumn<RAPDetail, Number> volumeColumn;
    @FXML
    private TreeTableColumn<RAPDetail, Number> hargaSatuanColumn;
    @FXML
    private TreeTableColumn<RAPDetail, Number> totalColumn;
    @FXML
    private Label totalAnggaranLabel;
    @FXML
    public Button closeButton;
    private final TreeItem<RAPDetail> root = new TreeItem<>();
    public ObservableList<RAPDetail> allAnggaranDetail = FXCollections.observableArrayList();
    private String status = "";
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        pekerjaanColumn.setCellValueFactory(param -> param.getValue().getValue().pekerjaanProperty());
        pekerjaanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(pekerjaanColumn));

        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTreeTableCell(keteranganColumn));

        satuanColumn.setCellValueFactory(param -> param.getValue().getValue().satuanProperty());
        satuanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(satuanColumn));

        volumeColumn.setCellValueFactory(param -> param.getValue().getValue().volumeProperty());
        volumeColumn.setCellFactory(col -> getTreeTableCell(qty));

        hargaSatuanColumn.setCellValueFactory(param -> param.getValue().getValue().hargaSatuanProperty());
        hargaSatuanColumn.setCellFactory(col -> getTreeTableCell(rp));

        totalColumn.setCellValueFactory(param -> param.getValue().getValue().totalProperty());
        totalColumn.setCellFactory(col -> getTreeTableCell(rp));

        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pekerjaan");
        addNew.setOnAction(e -> {
            addNew();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction(e -> {
            setTable();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        anggaranDetailTable.setContextMenu(rowMenu);
        anggaranDetailTable.setRowFactory(ttv -> {
            TreeTableRow<RAPDetail> row = new TreeTableRow<RAPDetail>() {
                @Override
                public void updateItem(RAPDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pekerjaan");
                        addNew.setOnAction(e -> {
                            addNew();
                        });
                        MenuItem edit = new MenuItem("Edit Pekerjaan");
                        edit.setOnAction((e) -> {
                            edit(item);
                        });
                        MenuItem delete = new MenuItem("Delete Pekerjaan");
                        delete.setOnAction(e -> {
                            delete(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction(e -> {
                            setTable();
                        });
                        if (!status.equals("View")) {
                            rowMenu.getItems().addAll(addNew);
                            if (item.getPekerjaan() != null) {
                                rowMenu.getItems().addAll(edit, delete);
                            }
                        }
                        rowMenu.getItems().addAll(refresh);
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
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 80 / 100);
        stage.setWidth(mainApp.screenSize.getWidth() * 80 / 100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void setDetailAnggaran(List<RAPDetail> listRapDetail) {
        allAnggaranDetail.clear();
        allAnggaranDetail.addAll(listRapDetail);
        setTable();
    }

    public void setViewOnly() {
        status = "View";
        List<MenuItem> removeMenu = new ArrayList<>();
        for (MenuItem m : anggaranDetailTable.getContextMenu().getItems()) {
            if (m.getText().equals("Add New Pekerjaan")) {
                removeMenu.add(m);
            }
        }
        anggaranDetailTable.getContextMenu().getItems().removeAll(removeMenu);
    }

    private void setTable() {
        if (anggaranDetailTable.getRoot() != null) {
            anggaranDetailTable.getRoot().getChildren().clear();
        }
        List<String> groupBy = new ArrayList<>();
        for (RAPDetail d : allAnggaranDetail) {
            if (!groupBy.contains(d.getKategori())) {
                groupBy.add(d.getKategori());
            }
        }
        for (String t : groupBy) {
            RAPDetail head = new RAPDetail();
            head.setPekerjaan(t);
            TreeItem<RAPDetail> parent = new TreeItem<>(head);
            double total = 0;
            for (RAPDetail d : allAnggaranDetail) {
                if (t.equals(d.getKategori())) {
                    total = total + d.getTotal();
                    parent.getChildren().addAll(new TreeItem<>(d));
                }
            }
            head.setTotal(total);
            root.getChildren().add(parent);
        }
        anggaranDetailTable.setRoot(root);
        anggaranDetailTable.refresh();
        hitungTotal();
    }

    private void hitungTotal() {
        double total = 0;
        for (RAPDetail d : allAnggaranDetail) {
            total = total + d.getTotal();
        }
        totalAnggaranLabel.setText(rp.format(total));
    }

    private void addNew() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPekerjaan.fxml");
        AddPekerjaanController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> groupBy = new ArrayList<>();
        for (RAPDetail d : allAnggaranDetail) {
            if (!groupBy.contains(d.getKategori())) {
                groupBy.add(d.getKategori());
            }
        }
        x.setKategori(groupBy);
        x.saveButton.setOnAction((event) -> {
            RAPDetail d = new RAPDetail();
            d.setKategori(x.kategoriCombo.getEditor().getText());
            d.setPekerjaan(x.pekerjaanField.getText());
            d.setKeterangan(x.keteranganField.getText());
            d.setSatuan(x.satuanField.getText());
            d.setVolume(Double.parseDouble(x.volumeField.getText().replaceAll(",", "")));
            d.setHargaSatuan(Double.parseDouble(x.hargaSatuanField.getText().replaceAll(",", "")));
            d.setTotal(Double.parseDouble(x.totalField.getText().replaceAll(",", "")));
            allAnggaranDetail.add(d);
            setTable();
            mainApp.closeDialog(stage, child);
        });
    }

    private void edit(RAPDetail detail) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPekerjaan.fxml");
        AddPekerjaanController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> groupBy = new ArrayList<>();
        for (RAPDetail d : allAnggaranDetail) {
            if (!groupBy.contains(d.getKategori())) {
                groupBy.add(d.getKategori());
            }
        }
        x.setKategori(groupBy);
        x.setPekerjaan(detail);
        x.saveButton.setOnAction((event) -> {
            detail.setKategori(x.kategoriCombo.getEditor().getText());
            detail.setPekerjaan(x.pekerjaanField.getText());
            detail.setKeterangan(x.keteranganField.getText());
            detail.setSatuan(x.satuanField.getText());
            detail.setVolume(Double.parseDouble(x.volumeField.getText().replaceAll(",", "")));
            detail.setHargaSatuan(Double.parseDouble(x.hargaSatuanField.getText().replaceAll(",", "")));
            detail.setTotal(Double.parseDouble(x.totalField.getText().replaceAll(",", "")));
            setTable();
            mainApp.closeDialog(stage, child);
        });
    }

    private void delete(RAPDetail d) {
        allAnggaranDetail.remove(d);
        setTable();
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
