/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanHead;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPembangunanController {

    @FXML
    private TableView<PembangunanDetail> propertyTable;
    @FXML
    private TableColumn<PembangunanDetail, Boolean> checkColumn;
    @FXML
    private TableColumn<PembangunanDetail, String> namaPropertyColumn;
    @FXML
    private TableColumn<PembangunanDetail, Number> luasTanahColumn;
    @FXML
    private TableColumn<PembangunanDetail, Number> biayaColumn;

    @FXML
    private HBox hbox;
    @FXML
    private GridPane gridPane;
    @FXML
    public TextField keteranganField;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    private Label totalBiayaLabel;
    @FXML
    private TextField totalBiayaField;
    @FXML
    public ComboBox<String> metodeCombo;
    @FXML
    private ComboBox<String> kategoriPropertyCombo;
    @FXML
    private CheckBox checkAll;

    @FXML
    public Label totalPropertyLabel;
    @FXML
    public Label totalLuasTanahLabel;
    @FXML
    public Label totalBiayaAkhirLabel;
    @FXML
    public Button saveButton;

    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allMetode = FXCollections.observableArrayList();
    private ObservableList<String> allKeuangan = FXCollections.observableArrayList();
    public ObservableList<PembangunanDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        luasTanahColumn.setCellValueFactory(cellData -> cellData.getValue().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));

        biayaColumn.setCellValueFactory(cellData -> cellData.getValue().biayaProperty());
        biayaColumn.setCellFactory(col -> getTableCell(rp));

        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            hitungTotal();
            return propertyTable.getItems().get(param).statusProperty();
        }));

        totalBiayaField.setOnKeyReleased((event) -> {
            try {
                totalBiayaField.setText(rp.format(Double.parseDouble(totalBiayaField.getText().replaceAll(",", ""))));
                totalBiayaField.end();
            } catch (Exception e) {
                totalBiayaField.undo();
            }
            hitungTotal();
        });

        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getProperty();
        });
        rm.getItems().add(refresh);
        propertyTable.setContextMenu(rm);
        propertyTable.setRowFactory((TableView<PembangunanDetail> tableView) -> {
            final TableRow<PembangunanDetail> row = new TableRow<PembangunanDetail>() {
                @Override
                public void updateItem(PembangunanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem edit = new MenuItem("Edit Biaya");
                        edit.setOnAction((ActionEvent e) -> {
                            updateBiaya(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getProperty();
                        });
                        if (saveButton.isVisible()) {
                            rm.getItems().add(edit);
                            rm.getItems().add(refresh);
                        }
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    row.getItem().setStatus(!row.getItem().isStatus());
                    hitungTotal();
                }
            });
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
        propertyTable.setItems(allDetail);
        metodeCombo.setItems(allMetode);
        tipeKeuanganCombo.setItems(allKeuangan);
        kategoriPropertyCombo.setItems(allKategori);
    }

    public void setNewPembangunan() {
        try {
            allKeuangan.clear();
            allKeuangan.addAll(Function.getTipeKeuangan());

            allMetode.clear();
            allMetode.addAll("Rata-rata", "Luas Tanah", "Manual");
            metodeCombo.getSelectionModel().select("Rata-rata");

            allKategori.addAll(Function.getKategoriProperty());
            allKategori.add("Semua");
            kategoriPropertyCombo.getSelectionModel().select("Semua");

            ObservableList<String> x = FXCollections.observableArrayList();
            x.add("PENGURUSAN SERTIFIKAT");
            x.add("INFRASTRUKTUR");
            x.add("PAGAR BUMI");
            x.add("GATE");
            x.add("TAMAN");
            x.add("FASUM");
            x.add("URUGAN");
            x.add("BANGUNAN RUMAH");
            x.add("ADDENDUM");
            x.add("LAIN - LAIN");
            kategoriCombo.setItems(x);
            kategoriCombo.getSelectionModel().clearSelection();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void getPembangunan(String noPembangunan) {
        Task<PembangunanHead> task = new Task<PembangunanHead>() {
            @Override
            public PembangunanHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    PembangunanHead p = PembangunanHeadDAO.get(con, noPembangunan);
                    p.setAllDetail(PembangunanDetailDAO.getAllByNo(con, noPembangunan));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            tipeKeuanganCombo.setDisable(true);
            kategoriCombo.setDisable(true);
            checkColumn.setVisible(false);
            saveButton.setVisible(false);
            keteranganField.setDisable(true);
            hbox.setVisible(false);
            gridPane.getRowConstraints().get(6).setMinHeight(0);
            gridPane.getRowConstraints().get(6).setPrefHeight(0);
            gridPane.getRowConstraints().get(6).setMaxHeight(0);

            PembangunanHead p = task.getValue();
            kategoriCombo.getSelectionModel().select(p.getKategori());
            keteranganField.setText(p.getKeterangan());
            totalPropertyLabel.setText(qty.format(p.getTotalProperty()));
            totalLuasTanahLabel.setText(qty.format(p.getTotalLuasTanah()));
            totalBiayaAkhirLabel.setText(rp.format(p.getTotalBiaya()));
            tipeKeuanganCombo.getSelectionModel().select(p.getTipeKeuangan());
            allDetail.addAll(p.getAllDetail());

            List<MenuItem> removeMenu = new ArrayList<>();
            for (MenuItem m : propertyTable.getContextMenu().getItems()) {
                if (m.getText().equals("Refresh")) {
                    removeMenu.add(m);
                }
            }
            propertyTable.getContextMenu().getItems().removeAll(removeMenu);
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    @FXML
    private void getProperty() {
        Task<List<Property>> task = new Task<List<Property>>() {
            @Override
            public List<Property> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<String> status = new ArrayList<>();
                    status.add("Available");
                    status.add("Reserved");
                    return PropertyDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allDetail.clear();
            for (Property p : task.getValue()) {
                PembangunanDetail d = new PembangunanDetail();
                d.setKodeProperty(p.getKodeProperty());
                d.setNamaProperty(p.getNamaProperty());
                d.setLuasTanah(p.getLuasTanah());
                d.setBiaya(0);
                d.setStatus(checkAll.isSelected());
                if (p.getKodeKategori().equals(kategoriPropertyCombo.getSelectionModel().getSelectedItem())
                        || kategoriPropertyCombo.getSelectionModel().getSelectedItem().equals("Semua")) {
                    allDetail.add(d);
                }
            }
            if (!allDetail.isEmpty()) {
                hitungTotal();
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    private void updateBiaya(PembangunanDetail d) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Edit.fxml");
        EditController x = loader.getController();
        x.textField.setText(rp.format(d.getBiaya()));
        Function.setNumberField(x.textField);
        x.saveButton.setOnAction((event) -> {
            metodeCombo.getSelectionModel().select("Manual");
            d.setStatus(true);
            d.setBiaya(Double.parseDouble(x.textField.getText().replaceAll(",", "")));
            hitungTotal();
            mainApp.closeDialog(stage, child);
        });
    }

    @FXML
    private void hitungTotal() {
        totalBiayaLabel.setVisible(true);
        totalBiayaField.setVisible(true);
        double totalProp = 0;
        double totalLuas = 0;
        for (PembangunanDetail d : allDetail) {
            if (d.isStatus()) {
                totalProp = totalProp + 1;
                totalLuas = totalLuas + d.getLuasTanah();
            }
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
        if (totalBiayaField.getText().equals("")) {
            totalBiayaField.setText("0");
        }
        double totalBiaya = Double.parseDouble(totalBiayaField.getText().replaceAll(",", ""));
        if (metodeCombo.getSelectionModel().getSelectedItem().equals("Rata-rata")) {
            for (PembangunanDetail d : allDetail) {
                if (d.isStatus()) {
                    d.setBiaya(totalBiaya / totalProp);
                } else {
                    d.setBiaya(0);
                }
            }
        } else if (metodeCombo.getSelectionModel().getSelectedItem().equals("Luas Tanah")) {
            for (PembangunanDetail d : allDetail) {
                if (d.isStatus()) {
                    d.setBiaya(totalBiaya * d.getLuasTanah() / totalLuas);
                } else {
                    d.setBiaya(0);
                }
            }
        } else {
            totalBiayaLabel.setVisible(false);
            totalBiayaField.setVisible(false);
            for (PembangunanDetail d : allDetail) {
                if (!d.isStatus()) {
                    d.setBiaya(0);
                }
            }
        }
        propertyTable.refresh();
        double totalBiayaAkhir = 0;
        for (PembangunanDetail d : allDetail) {
            totalBiayaAkhir = totalBiayaAkhir + d.getBiaya();
        }
        totalBiayaAkhirLabel.setText(rp.format(totalBiayaAkhir));
        propertyTable.refresh();
    }

    @FXML
    private void checkAllHandle() {
        for (PembangunanDetail d : allDetail) {
            d.setStatus(checkAll.isSelected());
        }
        hitungTotal();
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
