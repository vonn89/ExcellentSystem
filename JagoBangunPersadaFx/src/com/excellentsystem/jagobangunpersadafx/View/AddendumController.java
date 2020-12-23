/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.AddendumDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Addendum;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailAddendumController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.DatePicker;
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
public class AddendumController {

    @FXML
    private TableView<Addendum> AddendumTable;
    @FXML
    private TableColumn<Addendum, String> noAddendumColumn;
    @FXML
    private TableColumn<Addendum, String> tglAddendumColumn;
    @FXML
    private TableColumn<Addendum, String> kodeKategoriColumn;
    @FXML
    private TableColumn<Addendum, String> namaPropertyColumn;
    @FXML
    private TableColumn<Addendum, String> namaCustomerColumn;
    @FXML
    private TableColumn<Addendum, Number> hargaJualColumn;
    @FXML
    private TableColumn<Addendum, Number> diskonColumn;
    @FXML
    private TableColumn<Addendum, Number> addendumColumn;

    @FXML
    private TextField searchField;
    @FXML
    private DatePicker tglAwalPicker;
    @FXML
    private DatePicker tglAkhirPicker;

    private Main mainApp;
    private ObservableList<Addendum> allAddendum = FXCollections.observableArrayList();
    private ObservableList<Addendum> filterData = FXCollections.observableArrayList();

    public void initialize() {
        noAddendumColumn.setCellValueFactory(cellData -> cellData.getValue().noAddendumProperty());
        noAddendumColumn.setCellFactory(col -> Function.getWrapTableCell(noAddendumColumn));

        tglAddendumColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglAddendum())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglAddendumColumn.setCellFactory(col -> Function.getWrapTableCell(tglAddendumColumn));
        tglAddendumColumn.setComparator(Function.sortDate(tglLengkap));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaPropertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));

        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));

        diskonColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));

        addendumColumn.setCellValueFactory(cellData -> cellData.getValue().perubahanHargaPropertyProperty());
        addendumColumn.setCellFactory(col -> getTableCell(rp));

        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));

        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));

        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));

        ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getAddendum();
        });
        rowMenu.getItems().addAll(refresh);
        AddendumTable.setContextMenu(rowMenu);
        AddendumTable.setRowFactory(tv -> {
            TableRow<Addendum> row = new TableRow<Addendum>() {
                @Override
                public void updateItem(Addendum item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem detailPelunasan = new MenuItem("Detail Addendum");
                        detailPelunasan.setOnAction((ActionEvent event) -> {
                            detailAddendum(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getAddendum();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Detail Addendum") && o.isStatus()) {
                                rowMenu.getItems().add(detailPelunasan);
                            }
                            if (o.getJenis().equals("Detail Property") && o.isStatus()) {
                                rowMenu.getItems().add(detailProperty);
                            }
                            if (o.getJenis().equals("Detail Customer") && o.isStatus()) {
                                rowMenu.getItems().add(detailCustomer);
                            }
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    for (Otoritas o : sistem.getUser().getOtoritas()) {
                        if (o.getJenis().equals("Detail Addendum") && o.isStatus()) {
                            detailAddendum(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
        allAddendum.addListener((ListChangeListener.Change<? extends Addendum> change) -> {
            search();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    search();
                });
        filterData.addAll(allAddendum);
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        AddendumTable.setItems(filterData);
        getAddendum();
    }

    @FXML
    private void getAddendum() {
        Task<List<Addendum>> task = new Task<List<Addendum>>() {
            @Override
            public List<Addendum> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<Addendum> listAddendum = AddendumDAO.getAllByDateAndStatus(con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for (Addendum spp : listAddendum) {
                        for (Customer c : listCustomer) {
                            if (spp.getKodeCustomer().equals(c.getKodeCustomer())) {
                                spp.setCustomer(c);
                            }
                        }
                        for (Property p : listProperty) {
                            if (spp.getKodeProperty().equals(p.getKodeProperty())) {
                                spp.setProperty(p);
                            }
                        }
                    }
                    return listAddendum;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try {
                mainApp.closeLoading();
                allAddendum.clear();
                allAddendum.addAll(task.getValue());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
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

    private void search() {
        filterData.clear();
        for (Addendum temp : allAddendum) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoAddendum())
                        || checkColumn(temp.getTglAddendum())
                        || checkColumn(temp.getCustomer().getNama())
                        || checkColumn(temp.getProperty().getKodeKategori())
                        || checkColumn(temp.getProperty().getNamaProperty())
                        || checkColumn(rp.format(temp.getProperty().getHargaJual()))
                        || checkColumn(rp.format(temp.getPerubahanHargaProperty()))
                        || checkColumn(rp.format(temp.getProperty().getDiskon()))) {
                    filterData.add(temp);
                }
            }
        }
    }


    private void detailAddendum(Addendum a) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailAddendum.fxml");
        DetailAddendumController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setAddendum(a);
    }


    private void detailCustomer(Customer c) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomer(c);
        x.setViewOnly();
    }

    private void detailProperty(Property p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setProperty(p);
        x.viewOnly();
    }
}
