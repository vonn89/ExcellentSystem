/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewKeuanganController {

    @FXML
    private TableView<Keuangan> propertyTable;
    @FXML
    private TableColumn<Keuangan, Boolean> checkColumn;
    @FXML
    private TableColumn<Keuangan, String> namaPropertyColumn;
    @FXML
    private TableColumn<Keuangan, Number> luasTanahColumn;
    @FXML
    private TableColumn<Keuangan, Number> biayaColumn;

    @FXML
    private Label totalPropertyLabel;
    @FXML
    private Label totalLuasTanahLabel;
    @FXML
    private Label totalBiayaAkhirLabel;
    @FXML
    public TextField keteranganField;
    @FXML
    public TextField jumlahRpField;

    @FXML
    public GridPane gridPane;

    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    private ComboBox<String> metodeCombo;
    @FXML
    private ComboBox<String> kategoriPropertyCombo;

    @FXML
    private CheckBox checkAll;

    @FXML
    private Button propertyButton;
    @FXML
    public Button saveButton;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    private ObservableList<String> allKategoriProperty = FXCollections.observableArrayList();
    public ObservableList<Keuangan> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        luasTanahColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));
        biayaColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        biayaColumn.setCellFactory(col -> getTableCell(rp));

        checkColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            hitungTotal();
            return propertyTable.getItems().get(param).checkedProperty();
        }));
        jumlahRpField.setOnKeyReleased((event) -> {
            try {
                jumlahRpField.setText(rp.format(Double.parseDouble(jumlahRpField.getText().replaceAll(",", ""))));
                jumlahRpField.end();
            } catch (Exception e) {
                jumlahRpField.undo();
            }
            hitungTotal();
        });
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            propertyTable.refresh();
        });
        rm.getItems().add(refresh);
        propertyTable.setContextMenu(rm);
        propertyTable.setRowFactory((TableView<Keuangan> tableView) -> {
            final TableRow<Keuangan> row = new TableRow<Keuangan>() {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
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
                    row.getItem().setChecked(!row.getItem().isChecked());
                    hitungTotal();
                }
            });
            return row;
        });
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
            propertyTable.setItems(allDetail);
            kategoriCombo.setItems(Function.getKategoriTransaksi());
            tipeKeuanganCombo.setItems(Function.getTipeKeuangan());

            ObservableList<String> allMetode = FXCollections.observableArrayList();
            allMetode.addAll("Rata-rata", "Luas Tanah", "Manual");
            metodeCombo.setItems(allMetode);
            metodeCombo.getSelectionModel().select("Rata-rata");

            allKategoriProperty.addAll(Function.getKategoriProperty());
            allKategoriProperty.add("Semua");
            kategoriPropertyCombo.setItems(allKategoriProperty);
            kategoriPropertyCombo.getSelectionModel().select("Semua");
            showDetailProperty();
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    @FXML
    private void getProperty() {
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override
            public List<Keuangan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Keuangan> temp = new ArrayList<>();
                    List<String> status = new ArrayList<>();
                    status.add("Available");
                    status.add("Reserved");
                    status.add("Sold");
                    List<Property> allProperty = PropertyDAO.getAllByStatus(con, status);
                    for (Property p : allProperty) {
                        Keuangan k = new Keuangan();
                        k.setKodeProperty(p.getKodeProperty());
                        k.setProperty(p);
                        k.setChecked(checkAll.isSelected());
                        if (p.getKodeKategori().equals(kategoriPropertyCombo.getSelectionModel().getSelectedItem())
                                || kategoriPropertyCombo.getSelectionModel().getSelectedItem().equals("Semua")) {
                            temp.add(k);
                        }
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allDetail.clear();
                allDetail.addAll(task.getValue());
                if (!allDetail.isEmpty()) {
                    hitungTotal();
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

    private void updateBiaya(Keuangan k) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Edit.fxml");
        EditController x = loader.getController();
        x.textField.setText(rp.format(k.getJumlahRp()));
        Function.setNumberField(x.textField);
        x.saveButton.setOnAction((event) -> {
            metodeCombo.getSelectionModel().select("Manual");
            k.setJumlahRp(Double.parseDouble(x.textField.getText().replaceAll(",", "")));
            hitungTotal();
            k.setChecked(true);
            mainApp.closeDialog(stage, child);
        });
    }

    @FXML
    private void hitungTotal() {
        jumlahRpField.setVisible(true);
        double totalProp = 0;
        double totalLuas = 0;
        for (Keuangan k : allDetail) {
            if (k.isChecked()) {
                totalProp = totalProp + 1;
                totalLuas = totalLuas + k.getProperty().getLuasTanah();
            }
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
        if (jumlahRpField.getText().equals("")) {
            jumlahRpField.setText("0");
        }
        double totalBiaya = Double.parseDouble(jumlahRpField.getText().replaceAll(",", ""));
        if (metodeCombo.getSelectionModel().getSelectedItem().equals("Rata-rata")) {
            for (Keuangan d : allDetail) {
                if (d.isChecked()) {
                    d.setJumlahRp(totalBiaya / totalProp);
                } else {
                    d.setJumlahRp(0);
                }
            }
        } else if (metodeCombo.getSelectionModel().getSelectedItem().equals("Luas Tanah")) {
            for (Keuangan d : allDetail) {
                if (d.isChecked()) {
                    d.setJumlahRp(totalBiaya * d.getProperty().getLuasTanah() / totalLuas);
                } else {
                    d.setJumlahRp(0);
                }
            }
        } else {
            jumlahRpField.setVisible(false);
            for (Keuangan d : allDetail) {
                if (d.isChecked()) {
                    d.setJumlahRp(d.getJumlahRp() / d.getProperty().getLuasTanah());
                } else {
                    d.setJumlahRp(0);
                }
            }
        }
        propertyTable.refresh();
        double totalBiayaAkhir = 0;
        for (Keuangan d : allDetail) {
            totalBiayaAkhir = totalBiayaAkhir + d.getJumlahRp();
        }
        totalBiayaAkhirLabel.setText(rp.format(totalBiayaAkhir));
    }

    @FXML
    private void checkAllHandle() {
        for (Keuangan d : allDetail) {
            d.setChecked(checkAll.isSelected());
        }
        propertyTable.refresh();
        hitungTotal();
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void showDetailProperty() {
        final Animation hideSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                final double h = 300 * (1.0 - frac);
                gridPane.setPrefHeight(h);
                stage.setHeight(600 - (300 * frac));
            }
        };
        hideSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            gridPane.setVisible(false);
            allDetail.clear();
            checkAll.setSelected(false);
            propertyButton.setStyle("-fx-background-color: seccolor1;"
                    + "-fx-border-color:transparent;"
                    + "-fx-text-fill:white");
        });
        final Animation showSidebar = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            @Override
            protected void interpolate(double frac) {
                final double h = 300 * frac;
                gridPane.setPrefHeight(h);
                stage.setHeight(300 + h);
            }
        };
        showSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            gridPane.setVisible(true);
            checkAll.setSelected(true);
            getProperty();
            propertyButton.setStyle("-fx-background-color: seccolor5;"
                    + "-fx-border-color:seccolor1;"
                    + "-fx-text-fill:seccolor1");
        });
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (gridPane.isVisible()) {
                hideSidebar.play();
            } else {
                gridPane.setVisible(true);
                showSidebar.play();
            }
        }
    }
}
