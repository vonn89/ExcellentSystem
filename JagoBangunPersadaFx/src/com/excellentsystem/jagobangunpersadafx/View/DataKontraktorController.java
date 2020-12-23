/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.KontraktorDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanKontraktorHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKontraktorController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
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
public class DataKontraktorController {

    @FXML
    private TableView<Kontraktor> kontraktorTable;
    @FXML
    private TableColumn<Kontraktor, String> kodeKontraktorColumn;
    @FXML
    private TableColumn<Kontraktor, String> namaColumn;
    @FXML
    private TableColumn<Kontraktor, String> alamatColumn;
    @FXML
    private TableColumn<Kontraktor, String> kotaColumn;
    @FXML
    private TableColumn<Kontraktor, String> kontakPersonColumn;
    @FXML
    private TableColumn<Kontraktor, String> noTelpColumn;
    @FXML
    private TableColumn<Kontraktor, String> noHandphoneColumn;
    @FXML
    private TableColumn<Kontraktor, String> emailColumn;
    @FXML
    private TextField searchField;
    private Main mainApp;
    private ObservableList<Kontraktor> allKontraktor = FXCollections.observableArrayList();
    private ObservableList<Kontraktor> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeKontraktorColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKontraktorProperty());
        kodeKontraktorColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKontraktorColumn));

        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaKontraktorProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));

        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));

        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        kotaColumn.setCellFactory(col -> Function.getWrapTableCell(kotaColumn));

        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));

        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));

        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));

        kontakPersonColumn.setCellValueFactory(cellData -> cellData.getValue().kontakPersonProperty());
        kontakPersonColumn.setCellFactory(col -> Function.getWrapTableCell(kontakPersonColumn));

        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Kontraktor");
        addNew.setOnAction((ActionEvent e) -> {
            newKontraktor();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getKontraktor();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Kontraktor") && o.isStatus()) {
                rm.getItems().add(addNew);
            }
        }
        rm.getItems().add(refresh);
        kontraktorTable.setContextMenu(rm);
        kontraktorTable.setRowFactory((TableView<Kontraktor> tableView) -> {
            final TableRow<Kontraktor> row = new TableRow<Kontraktor>() {
                @Override
                public void updateItem(Kontraktor item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Kontraktor");
                        addNew.setOnAction((ActionEvent e) -> {
                            newKontraktor();
                        });
                        MenuItem edit = new MenuItem("Detail Kontraktor");
                        edit.setOnAction((ActionEvent e) -> {
                            updateKontraktor(item);
                        });
                        MenuItem hapus = new MenuItem("Delete Kontraktor");
                        hapus.setOnAction((ActionEvent e) -> {
                            deleteKontraktor(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getKontraktor();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Kontraktor") && o.isStatus()) {
                                rm.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Detail Kontraktor") && o.isStatus()) {
                                rm.getItems().add(edit);
                            }
                            if (o.getJenis().equals("Delete Kontraktor") && o.isStatus()) {
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
                            if (o.getJenis().equals("Detail Kontraktor") && o.isStatus()) {
                                updateKontraktor(row.getItem());
                            }
                        }
                    }
                }
            });
            return row;
        });
        allKontraktor.addListener((ListChangeListener.Change<? extends Kontraktor> change) -> {
            searchKontraktor();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchKontraktor();
                });
        filterData.addAll(allKontraktor);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKontraktor();
        kontraktorTable.setItems(filterData);
    }

    private void getKontraktor() {
        Task<List<Kontraktor>> task = new Task<List<Kontraktor>>() {
            @Override
            public List<Kontraktor> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return KontraktorDAO.getAllByStatus(con, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allKontraktor.clear();
            allKontraktor.addAll(task.getValue());
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

    private void searchKontraktor() {
        filterData.clear();
        for (Kontraktor temp : allKontraktor) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getKodeKontraktor())
                        || checkColumn(temp.getNamaKontraktor())
                        || checkColumn(temp.getAlamat())
                        || checkColumn(temp.getKota())
                        || checkColumn(temp.getKontakPerson())
                        || checkColumn(temp.getEmail())
                        || checkColumn(temp.getNoTelp())
                        || checkColumn(temp.getNoHandphone())
                        || checkColumn(temp.getStatus())) {
                    filterData.add(temp);
                }
            }
        }
    }

    private void newKontraktor() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKontraktor.fxml");
        DetailKontraktorController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Kontraktor c = new Kontraktor();
                            c.setKodeKontraktor(KontraktorDAO.getId(con));
                            c.setNamaKontraktor(x.namaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setKota(x.kotaField.getText());
                            c.setKontakPerson(x.kontakPersonField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setStatus("true");
                            return Service.newKontraktor(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKontraktor();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data kontraktor berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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

    private void updateKontraktor(Kontraktor c) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailKontraktor.fxml");
        DetailKontraktorController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKontraktor(c);
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if (x.namaField.getText().equals("") || x.namaField.getText() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "nama masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            c.setNamaKontraktor(x.namaField.getText());
                            c.setAlamat(x.alamatField.getText());
                            c.setKota(x.kotaField.getText());
                            c.setKontakPerson(x.kontakPersonField.getText());
                            c.setEmail(x.emailField.getText());
                            c.setNoHandphone(x.noHandphoneField.getText());
                            c.setNoTelp(x.noTelpField.getText());
                            c.setStatus("true");
                            return Service.updateKontraktor(con, c);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getKontraktor();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data kontraktor berhasil disimpan");
                        mainApp.closeDialog(mainApp.MainStage, stage);
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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

    private void deleteKontraktor(Kontraktor c) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete kontraktor " + c.getKodeKontraktor() + "-" + c.getNamaKontraktor() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Boolean status = true;
                        for (PembangunanKontraktorHead head : PembangunanKontraktorHeadDAO.getAll(con)) {
                            if (head.getKodeKontraktor().equals(c.getKodeKontraktor())) {
                                status = false;
                            }
                        }
                        if (status) {
                            c.setStatus("false");
                            return Service.updateKontraktor(con, c);
                        } else {
                            return "tidak dapat dihapus, karena masih ada pembangunan menggunakan kontraktor " + c.getNamaKontraktor();
                        }
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getKontraktor();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data kontraktor berhasil dihapus");
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
