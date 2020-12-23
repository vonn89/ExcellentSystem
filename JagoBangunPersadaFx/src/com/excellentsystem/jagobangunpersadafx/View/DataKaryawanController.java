/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
public class DataKaryawanController {

    @FXML
    private TableView<Karyawan> karyawanTable;
    @FXML
    private TableColumn<Karyawan, String> kodeKaryawanColumn;
    @FXML
    private TableColumn<Karyawan, String> namaColumn;
    @FXML
    private TableColumn<Karyawan, String> jabatanColumn;
    @FXML
    private TableColumn<Karyawan, String> alamatColumn;
    @FXML
    private TableColumn<Karyawan, String> noTelpColumn;
    @FXML
    private TableColumn<Karyawan, String> noHandphoneColumn;
    @FXML
    private TableColumn<Karyawan, String> statusNikahColumn;
    @FXML
    private TableColumn<Karyawan, String> agamaColumn;
    @FXML
    private TableColumn<Karyawan, String> identitasColumn;
    @FXML
    private TableColumn<Karyawan, String> noIdentitasColumn;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<Karyawan> allKaryawan = FXCollections.observableArrayList();
    private ObservableList<Karyawan> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeKaryawanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKaryawanProperty());
        kodeKaryawanColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKaryawanColumn));

        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));

        jabatanColumn.setCellValueFactory(cellData -> cellData.getValue().jabatanProperty());
        jabatanColumn.setCellFactory(col -> Function.getWrapTableCell(jabatanColumn));

        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));

        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));

        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));

        statusNikahColumn.setCellValueFactory(cellData -> cellData.getValue().statusNikahProperty());
        statusNikahColumn.setCellFactory(col -> Function.getWrapTableCell(statusNikahColumn));

        agamaColumn.setCellValueFactory(cellData -> cellData.getValue().agamaProperty());
        agamaColumn.setCellFactory(col -> Function.getWrapTableCell(agamaColumn));

        identitasColumn.setCellValueFactory(cellData -> cellData.getValue().identitasProperty());
        identitasColumn.setCellFactory(col -> Function.getWrapTableCell(identitasColumn));

        noIdentitasColumn.setCellValueFactory(cellData -> cellData.getValue().noIdentitasProperty());
        noIdentitasColumn.setCellFactory(col -> Function.getWrapTableCell(noIdentitasColumn));

        allKaryawan.addListener((ListChangeListener.Change<? extends Karyawan> change) -> {
            searchKaryawan();
        });
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Karyawan");
        addNew.setOnAction((ActionEvent e) -> {
            newKaryawan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getKaryawan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Karyawan") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
        }
        rm.getItems().addAll(refresh);
        karyawanTable.setContextMenu(rm);
        karyawanTable.setRowFactory((TableView<Karyawan> tableView) -> {
            final TableRow<Karyawan> row = new TableRow<Karyawan>() {
                @Override
                public void updateItem(Karyawan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Karyawan");
                        addNew.setOnAction((ActionEvent e) -> {
                            newKaryawan();
                        });
                        MenuItem edit = new MenuItem("Detail Karyawan");
                        edit.setOnAction((ActionEvent e) -> {
                            updateKaryawan(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Karyawan");
                        hapus.setOnAction((ActionEvent e) -> {
                            deleteKaryawan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getKaryawan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Karyawan") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Karyawan") && o.isStatus()) {
                                rm.getItems().add(edit);
                            }
                            if (o.getJenis().equals("Delete Karyawan") && o.isStatus()) {
                                rm.getItems().add(hapus);
                            }
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Karyawan") && o.isStatus()) {
                                updateKaryawan(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchKaryawan();
                });
        filterData.addAll(allKaryawan);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKaryawan();
        karyawanTable.setItems(filterData);
    }

    private void getKaryawan() {
        Task<List<Karyawan>> task = new Task<List<Karyawan>>() {
            @Override
            public List<Karyawan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return KaryawanDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKaryawan.clear();
            allKaryawan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchKaryawan() {
        filterData.clear();
        for (Karyawan temp : allKaryawan) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodeKaryawan())
                        || checkColumn(temp.getNama())
                        || checkColumn(temp.getJabatan())
                        || checkColumn(temp.getAlamat())
                        || checkColumn(temp.getNoTelp())
                        || checkColumn(temp.getNoHandphone())
                        || checkColumn(temp.getStatusNikah())
                        || checkColumn(temp.getIdentitas())
                        || checkColumn(temp.getNoIdentitas())
                        || checkColumn(temp.getAgama())) {
                    filterData.add(temp);
                }
            }
        }
    }

    private void newKaryawan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKaryawan.fxml");
        DetailKaryawanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Karyawan k = new Karyawan();
                            k.setKodeKaryawan(KaryawanDAO.getId(con));
                            k.setAgama(x.agamaField.getText());
                            k.setAlamat(x.alamatField.getText());
                            k.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                            k.setJabatan(x.jabatanCombo.getSelectionModel().getSelectedItem());
                            k.setNama(x.namaField.getText());
                            k.setNoHandphone(x.noHandphoneField.getText());
                            k.setNoIdentitas(x.noIdentitasField.getText());
                            k.setNoTelp(x.noTelpField.getText());
                            k.setStatus("true");
                            k.setStatusNikah(x.statusNikahCombo.getSelectionModel().getSelectedItem());
                            return Service.newKaryawan(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    try {
                        mainApp.closeLoading();
                        getKaryawan();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Data karyawan berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                        } else {
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
                    } catch (Exception ex) {
                        mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }

    private void updateKaryawan(Karyawan k) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKaryawan.fxml");
        DetailKaryawanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKaryawan(k);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            k.setAgama(x.agamaField.getText());
                            k.setAlamat(x.alamatField.getText());
                            k.setIdentitas(x.identitasCombo.getSelectionModel().getSelectedItem());
                            k.setJabatan(x.jabatanCombo.getSelectionModel().getSelectedItem());
                            k.setNama(x.namaField.getText());
                            k.setNoHandphone(x.noHandphoneField.getText());
                            k.setNoIdentitas(x.noIdentitasField.getText());
                            k.setNoTelp(x.noTelpField.getText());
                            k.setStatus("true");
                            k.setStatusNikah(x.statusNikahCombo.getSelectionModel().getSelectedItem());
                            return Service.updateKaryawan(con, k);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    try {
                        mainApp.closeLoading();
                        getKaryawan();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Data karyawan berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                        } else {
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
                    } catch (Exception ex) {
                        mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }

    private void deleteKaryawan(Karyawan k) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete karyawan " + k.getKodeKaryawan() + "-" + k.getNama() + " ?");
        controller.OK.setOnAction((ActionEvent ec) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        boolean status = true;
                        for (STJHead head : STJHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglBarang.format(new Date()), "true")) {
                            if (head.getKodeSales().equals(k.getKodeKaryawan())) {
                                status = false;
                            }
                        }
                        if (status) {
                            k.setStatus("false");
                            return Service.updateKaryawan(con, k);
                        } else {
                            return "tidak dapat dihapus, karena sudah ada penjualan dengan sales " + k.getNama();
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKaryawan();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data karyawan berhasil dihapus");
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
}
