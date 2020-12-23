/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.KontraktorDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanKontraktorDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanKontraktorHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPembangunanKontraktorController {

    @FXML
    private GridPane gridPane;
    @FXML
    private TableView<PembangunanKontraktorDetail> pembangunanKontraktorDetailTable;
    @FXML
    private TableColumn<PembangunanKontraktorDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PembangunanKontraktorDetail, Number> jumlahRpColumn;

    @FXML
    private TextField noPembangunanField;
    @FXML
    public TextField kontraktorField;
    @FXML
    public TextField propertyField;
    @FXML
    public ComboBox<String> kategoriCombo;
    @FXML
    public TextField totalPembangunanField;
    
    @FXML
    private Button addPropertyButton;
    @FXML
    private Button addKontraktorButton;
    @FXML
    public Button saveButton;

    public PembangunanKontraktorHead pembangunan;
    public ObservableList<PembangunanKontraktorDetail> allDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage owner;
    private Stage stage;

    public void initialize() {
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));

        final ContextMenu rm = new ContextMenu();
        MenuItem add = new MenuItem("Add New Pembangunan Kontraktor");
        add.setOnAction((ActionEvent e) -> {
            addNewPembangunan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            pembangunanKontraktorDetailTable.refresh();
        });
        rm.getItems().add(add);
        rm.getItems().add(refresh);
        pembangunanKontraktorDetailTable.setContextMenu(rm);
        pembangunanKontraktorDetailTable.setRowFactory((TableView<PembangunanKontraktorDetail> tableView) -> {
            final TableRow<PembangunanKontraktorDetail> row = new TableRow<PembangunanKontraktorDetail>() {
                @Override
                public void updateItem(PembangunanKontraktorDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem add = new MenuItem("Add New Pembangunan Kontraktor");
                        add.setOnAction((ActionEvent e) -> {
                            addNewPembangunan();
                        });
                        MenuItem delete = new MenuItem("Delete Pembangunan Kontraktor");
                        delete.setOnAction((ActionEvent e) -> {
                            deletePembangunan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            pembangunanKontraktorDetailTable.refresh();
                        });
                        if(saveButton.isVisible()){
                            rm.getItems().add(add);
                            rm.getItems().add(delete);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
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
        pembangunanKontraktorDetailTable.setItems(allDetail);
        ObservableList<String> x = FXCollections.observableArrayList();
        x.add("PENGURUSAN SERTIFIKAT");
        x.add("INFRASTRUKTUR");
        x.add("PAGAR BUMI");
        x.add("GATE");
        x.add("TAMAN");
        x.add("FASUM");
        x.add("URUGAN");
        x.add("BANGUNAN RUMAH");
        x.add("LAIN - LAIN");
        kategoriCombo.setItems(x);
        kategoriCombo.getSelectionModel().clearSelection();
    }

    public void setNewPembangunan() {
        noPembangunanField.setText("-");
        kontraktorField.setText("");
        propertyField.setText("");
        totalPembangunanField.setText("0");
        kategoriCombo.getSelectionModel().clearSelection();
        allDetail.clear();
        pembangunan = new PembangunanKontraktorHead();
    }

    public void getPembangunan(String noPembangunan) {
        Task<PembangunanKontraktorHead> task = new Task<PembangunanKontraktorHead>() {
            @Override
            public PembangunanKontraktorHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    PembangunanKontraktorHead p = PembangunanKontraktorHeadDAO.get(con, noPembangunan);
                    p.setListPembangunanKontraktorDetail(PembangunanKontraktorDetailDAO.getAllByNoPembangunan(con, noPembangunan));
                    p.setKontraktor(KontraktorDAO.get(con, p.getKodeKontraktor()));
                    p.setProperty(PropertyDAO.get(con, p.getKodeProperty()));
                    return p;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            pembangunan = task.getValue();
            noPembangunanField.setText(pembangunan.getNoPembangunan());
            kontraktorField.setText(pembangunan.getKontraktor().getNamaKontraktor());
            propertyField.setText(pembangunan.getProperty().getNamaProperty());
            kategoriCombo.getSelectionModel().select(pembangunan.getKategori());
            totalPembangunanField.setText(rp.format(pembangunan.getTotalPembangunan()));
            allDetail.clear();
            allDetail.addAll(pembangunan.getListPembangunanKontraktorDetail());
            
            gridPane.getRowConstraints().get(8).setMinHeight(0);
            gridPane.getRowConstraints().get(8).setPrefHeight(0);
            gridPane.getRowConstraints().get(8).setMaxHeight(0);

            saveButton.setVisible(false);
            kategoriCombo.setDisable(true);
            addKontraktorButton.setVisible(false);
            addPropertyButton.setVisible(false);
            
            List<MenuItem> removeMenu = new ArrayList<>();
            for (MenuItem m : pembangunanKontraktorDetailTable.getContextMenu().getItems()) {
                if (m.getText().equals("Add New Pembangunan Kontraktor")) {
                    removeMenu.add(m);
                }
            }
            pembangunanKontraktorDetailTable.getContextMenu().getItems().removeAll(removeMenu);
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void setProperty() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> status = new ArrayList<>();
        status.add("Available");
        status.add("Reserved");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Property p = row.getItem();
                    propertyField.setText(p.getNamaProperty());
                    pembangunan.setProperty(p);
                    pembangunan.setKodeProperty(p.getKodeProperty());
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    }
    @FXML
    private void setKontraktor(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddKontraktor.fxml");
        AddKontraktorController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.kontraktorTable.setRowFactory((TableView<Kontraktor> tableView) -> {
            final TableRow<Kontraktor> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Kontraktor k = row.getItem();
                    kontraktorField.setText(k.getNamaKontraktor());
                    pembangunan.setKontraktor(k);
                    pembangunan.setKodeKontraktor(k.getKodeKontraktor());
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    
    }
    private void addNewPembangunan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPembangunanKontraktor.fxml");
        NewPembangunanKontraktorController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setPembangunan(pembangunan, false);
        x.saveButton.setOnAction((event) -> {
            if(x.keteranganField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Keterangan masih kosong");
            }else if(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", ""))==0){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            }else{
                PembangunanKontraktorDetail d = new PembangunanKontraktorDetail();
                d.setNoPembangunan("");
                d.setNoUrut(0);
                d.setTanggal(tglSql.format(new Date()));
                d.setKeterangan(x.keteranganField.getText());
                d.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                d.setAddendum("");
                d.setKodeUser(sistem.getUser().getUsername());
                d.setStatus("true");
                d.setTglBatal("2000-01-01 00:00:00");
                d.setUserBatal("");
                allDetail.add(d);
                hitungTotal();
                pembangunanKontraktorDetailTable.refresh();
                mainApp.closeDialog(stage, child);
            }
        });
    
    }
    private void deletePembangunan(PembangunanKontraktorDetail d){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus detail pembangunan " + d.getKeterangan()+ ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            mainApp.closeMessage();
            allDetail.remove(d);
            hitungTotal();
            pembangunanKontraktorDetailTable.refresh();
        });
    }
    private void hitungTotal(){
        double total = 0;
        for(PembangunanKontraktorDetail d : allDetail){
            total = total + d.getJumlahRp();
        }
        totalPembangunanField.setText(rp.format(total));
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
