/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KontraktorDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddKontraktorController {

    @FXML
    public TableView<Kontraktor> kontraktorTable;
    @FXML
    private TableColumn<Kontraktor, String> kodeKontraktorColumn;
    @FXML
    private TableColumn<Kontraktor, String> namaKontraktorColumn;
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
    private Stage owner;
    private Stage stage;
    private ObservableList<Kontraktor> allKontraktor = FXCollections.observableArrayList();
    private ObservableList<Kontraktor> filterData = FXCollections.observableArrayList();

    public void initialize() {
        kodeKontraktorColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKontraktorProperty());
        kodeKontraktorColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKontraktorColumn));

        namaKontraktorColumn.setCellValueFactory(cellData -> cellData.getValue().namaKontraktorProperty());
        namaKontraktorColumn.setCellFactory(col -> Function.getWrapTableCell(namaKontraktorColumn));

        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        alamatColumn.setCellFactory(col -> Function.getWrapTableCell(alamatColumn));

        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        kotaColumn.setCellFactory(col -> Function.getWrapTableCell(kotaColumn));

        kontakPersonColumn.setCellValueFactory(cellData -> cellData.getValue().kontakPersonProperty());
        kontakPersonColumn.setCellFactory(col -> Function.getWrapTableCell(kontakPersonColumn));
        
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        noTelpColumn.setCellFactory(col -> Function.getWrapTableCell(noTelpColumn));

        noHandphoneColumn.setCellValueFactory(cellData -> cellData.getValue().noHandphoneProperty());
        noHandphoneColumn.setCellFactory(col -> Function.getWrapTableCell(noHandphoneColumn));

        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        emailColumn.setCellFactory(col -> Function.getWrapTableCell(emailColumn));

        allKontraktor.addListener((ListChangeListener.Change<? extends Kontraktor> change) -> {
            searchKontraktor();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchKontraktor();
                });
        filterData.addAll(allKontraktor);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        kontraktorTable.setItems(filterData);
        getKontraktor();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 80 / 100);
        stage.setWidth(mainApp.screenSize.getWidth() * 80 / 100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }

    public void getKontraktor() {
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
            try {
                mainApp.closeLoading();
                allKontraktor.clear();
                allKontraktor.addAll(task.getValue());
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
                        || checkColumn(temp.getKota())
                        || checkColumn(temp.getAlamat())
                        || checkColumn(temp.getNoTelp())
                        || checkColumn(temp.getNoHandphone())
                        || checkColumn(temp.getEmail())
                        || checkColumn(temp.getKontakPerson())) {
                    filterData.add(temp);
                }
            }
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
