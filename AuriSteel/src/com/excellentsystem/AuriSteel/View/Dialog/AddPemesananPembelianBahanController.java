/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananPembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananPembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddPemesananPembelianBahanController {

    @FXML
    public TableView<PemesananPembelianBahanHead> pemesananHeadTable;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> noPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> tglPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> namaSupplierColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> alamatSupplierColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> noKontrakColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> paymentTermColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> deliveryTermColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, Number> totalPemesananColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> catatanColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanHead, String> kodeUserColumn;

    @FXML
    private TextField searchField;
    private ObservableList<PemesananPembelianBahanHead> allPemesanan = FXCollections.observableArrayList();
    private ObservableList<PemesananPembelianBahanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        noPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().noPemesananProperty());
        noPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(noPemesananColumn));
        
        tglPemesananColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPemesanan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPemesananColumn.setCellFactory(col -> Function.getWrapTableCell(tglPemesananColumn));
        tglPemesananColumn.setComparator(Function.sortDate(tglLengkap));
        
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        namaSupplierColumn.setCellFactory(col -> Function.getWrapTableCell(namaSupplierColumn));
        
        alamatSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().alamatProperty());
        alamatSupplierColumn.setCellFactory(col -> Function.getWrapTableCell(alamatSupplierColumn));
        
        noKontrakColumn.setCellValueFactory(cellData -> cellData.getValue().noKontrakProperty());
        noKontrakColumn.setCellFactory(col -> Function.getWrapTableCell(noKontrakColumn));
        
        paymentTermColumn.setCellValueFactory(cellData -> cellData.getValue().paymentTermProperty());
        paymentTermColumn.setCellFactory(col -> Function.getWrapTableCell(paymentTermColumn));
        
        deliveryTermColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryTermProperty());
        deliveryTermColumn.setCellFactory(col -> Function.getWrapTableCell(deliveryTermColumn));
        
        totalPemesananColumn.setCellValueFactory(cellData -> cellData.getValue().totalPemesananProperty());
        totalPemesananColumn.setCellFactory(col -> Function.getTableCell());
        
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        catatanColumn.setCellFactory(col -> Function.getWrapTableCell(catatanColumn));
        
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        allPemesanan.addListener((ListChangeListener.Change<? extends PemesananPembelianBahanHead> change) -> {
            searchPemesanan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPemesanan();
        });
        filterData.addAll(allPemesanan);
        pemesananHeadTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getPemesanan();
    }

    private void getPemesanan() {
        Task<List<PemesananPembelianBahanHead>> task = new Task<List<PemesananPembelianBahanHead>>() {
            @Override
            public List<PemesananPembelianBahanHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PemesananPembelianBahanHead> allPemesanan = PemesananPembelianBahanHeadDAO.getAllByDateAndStatus(con,
                            "2000-01-01", "2050-01-01", "open");
                    List<PemesananPembelianBahanDetail> allDetail = PemesananPembelianBahanDetailDAO.getAllByDateAndStatus(con,
                            "2000-01-01", "2050-01-01", "open");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    for (PemesananPembelianBahanHead p : allPemesanan) {
                        List<PemesananPembelianBahanDetail> detail = new ArrayList<>();
                        for (PemesananPembelianBahanDetail d : allDetail) {
                            if (d.getNoPemesanan().equals(p.getNoPemesanan())) {
                                detail.add(d);
                            }
                        }
                        p.setListPemesananPembelianBahanDetail(detail);
                        for (Supplier s : allSupplier) {
                            if (p.getKodeSupplier().equals(s.getKodeSupplier())) {
                                p.setSupplier(s);
                            }
                        }
                    }
                    return allPemesanan;
                }
            }

        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allPemesanan.clear();
            allPemesanan.addAll(task.getValue());
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

    private void searchPemesanan() {
        try {
            filterData.clear();
            for (PemesananPembelianBahanHead temp : allPemesanan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPemesanan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPemesanan())))
                            || checkColumn(temp.getKodeSupplier())
                            || checkColumn(temp.getSupplier().getNama())
                            || checkColumn(temp.getSupplier().getAlamat())
                            || checkColumn(temp.getNoKontrak())
                            || checkColumn(temp.getPaymentTerm())
                            || checkColumn(temp.getDeliveryTerm())
                            || checkColumn(df.format(temp.getTotalPemesanan()))
                            || checkColumn(df.format(temp.getDownPayment()))
                            || checkColumn(temp.getCatatan())
                            || checkColumn(temp.getKodeUser())
                            || checkColumn(temp.getTglBatal())
                            || checkColumn(temp.getUserBatal())) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }
}
