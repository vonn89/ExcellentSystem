/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.HutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KontraktorDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanKontraktorDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanKontraktorHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SerahTerimaDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.Kontraktor;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanKontraktorHead;
import com.excellentsystem.jagobangunpersadafx.Model.Pembayaran;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Model.SerahTerima;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailHutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembangunanKontraktorController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewPembangunanKontraktorController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewPembayaranHutangController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
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
public class PembangunanKontraktorController {

    @FXML
    private TableView<PembangunanKontraktorHead> pembangunanKontraktorTable;
    @FXML
    private TableColumn<PembangunanKontraktorHead, String> noPembangunanColumn;
    @FXML
    private TableColumn<PembangunanKontraktorHead, String> kontraktorColumn;
    @FXML
    private TableColumn<PembangunanKontraktorHead, String> propertyColumn;
    @FXML
    private TableColumn<PembangunanKontraktorHead, Number> totalPembangunanColumn;
    @FXML
    private TableColumn<PembangunanKontraktorHead, Number> pembayaranColumn;
    @FXML
    private TableColumn<PembangunanKontraktorHead, Number> sisaPembayaranColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label totalPembangunanLabel;
    @FXML
    private Label totalPembayaranLabel;
    @FXML
    private Label totalSisaPembayaranLabel;
    private ObservableList<PembangunanKontraktorHead> allPembangunan = FXCollections.observableArrayList();
    private ObservableList<PembangunanKontraktorHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;

    public void initialize() {
        noPembangunanColumn.setCellValueFactory(cellData -> cellData.getValue().noPembangunanProperty());
        noPembangunanColumn.setCellFactory(col -> Function.getWrapTableCell(noPembangunanColumn));

        kontraktorColumn.setCellValueFactory(cellData -> cellData.getValue().getKontraktor().namaKontraktorProperty());
        kontraktorColumn.setCellFactory(col -> Function.getWrapTableCell(kontraktorColumn));

        propertyColumn.setCellValueFactory(cellData -> cellData.getValue().getProperty().namaPropertyProperty());
        propertyColumn.setCellFactory(col -> Function.getWrapTableCell(propertyColumn));

        totalPembangunanColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembangunanProperty());
        totalPembangunanColumn.setCellFactory(col -> getTableCell(rp));

        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> getTableCell(rp));

        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> getTableCell(rp));

        allPembangunan.addListener((ListChangeListener.Change<? extends PembangunanKontraktorHead> change) -> {
            searchPembangunan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPembangunan();
                });
        filterData.addAll(allPembangunan);
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pembangunan Kontraktor");
        addNew.setOnAction((ActionEvent e) -> {
            addNewPembangunan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getPembangunan();
        });
        for (Otoritas o : sistem.getUser().getOtoritas()) {
            if (o.getJenis().equals("Add New Pembangunan Kontraktor") && o.isStatus()) {
                rowMenu.getItems().add(addNew);
            }
        }
        rowMenu.getItems().addAll(refresh);
        pembangunanKontraktorTable.setContextMenu(rowMenu);
        pembangunanKontraktorTable.setRowFactory(tv -> {
            TableRow<PembangunanKontraktorHead> row = new TableRow<PembangunanKontraktorHead>() {
                @Override
                public void updateItem(PembangunanKontraktorHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else {
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pembangunan Kontraktor");
                        addNew.setOnAction((ActionEvent e) -> {
                            addNewPembangunan();
                        });
                        MenuItem revisi = new MenuItem("Add Revisi Pembangunan Kontraktor");
                        revisi.setOnAction((ActionEvent e) -> {
                            addRevisi(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembangunan Kontraktor");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePembangunan(item);
                        });
                        MenuItem bayar = new MenuItem("Pembayaran Pembangunan Kontraktor");
                        bayar.setOnAction((ActionEvent event) -> {
                            PembayaranPembangunan(item);
                        });
                        MenuItem detail = new MenuItem("Detail Pembangunan Kontraktor");
                        detail.setOnAction((ActionEvent event) -> {
                            detailPembangunan(item);
                        });
                        MenuItem detailBayar = new MenuItem("Detail Pembayaran Pembangunan Kontraktor");
                        detailBayar.setOnAction((ActionEvent event) -> {
                            detailPembayaranPembangunan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembangunan();
                        });
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Add New Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(addNew);
                            }
                            if (o.getJenis().equals("Add Revisi Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(revisi);
                            }
                            if (o.getJenis().equals("Detail Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(detail);
                            }
                            if (o.getJenis().equals("Pembayaran Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(bayar);
                            }
                            if (o.getJenis().equals("Detail Pembayaran Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(detailBayar);
                            }
                            if (o.getJenis().equals("Batal Pembangunan Kontraktor") && o.isStatus()) {
                                rowMenu.getItems().add(batal);
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
                        if (o.getJenis().equals("Detail Pembangunan Kontraktor") && o.isStatus()) {
                            detailPembangunan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
    }

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getPembangunan();
        pembangunanKontraktorTable.setItems(filterData);
    }

    @FXML
    private void getPembangunan() {
        Task<List<PembangunanKontraktorHead>> task = new Task<List<PembangunanKontraktorHead>>() {
            @Override
            public List<PembangunanKontraktorHead> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<String> listStatus = new ArrayList<>();
                    listStatus.add("Reserved");
                    listStatus.add("Available");
                    listStatus.add("Sold");
                    listStatus.add("Combined");
                    listStatus.add("Mapped");
                    List<PembangunanKontraktorHead> listPembangunan = new ArrayList<>();
                    List<PembangunanKontraktorHead> allPembangunan = PembangunanKontraktorHeadDAO.getAll(con);
                    List<PembangunanKontraktorDetail> allPembangunanDetail = PembangunanKontraktorDetailDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    List<Kontraktor> listKontraktor = KontraktorDAO.getAllByStatus(con, "%");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, listStatus);
                    for (PembangunanKontraktorHead p : allPembangunan) {
                        for (Kontraktor k : listKontraktor) {
                            if (p.getKodeKontraktor().equals(k.getKodeKontraktor())) {
                                p.setKontraktor(k);
                            }
                        }
                        for (Property pr : listProperty) {
                            if (p.getKodeProperty().equals(pr.getKodeProperty())) {
                                p.setProperty(pr);
                            }
                        }
                        p.setListPembangunanKontraktorDetail(new ArrayList<>());
                        for (PembangunanKontraktorDetail d : allPembangunanDetail) {
                            if (p.getNoPembangunan().equals(d.getNoPembangunan())) {
                                p.getListPembangunanKontraktorDetail().add(d);
                            }
                        }
                        if (!p.getListPembangunanKontraktorDetail().isEmpty()) {
                            listPembangunan.add(p);
                        }
                    }
                    return listPembangunan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPembangunan.clear();
            allPembangunan.addAll(task.getValue());
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

    private void searchPembangunan() {
        filterData.clear();
        for (PembangunanKontraktorHead temp : allPembangunan) {
            if (searchField.getText() == null || searchField.getText().equals("")) {
                filterData.add(temp);
            } else {
                if (checkColumn(temp.getNoPembangunan())
                        || checkColumn(temp.getKontraktor().getNamaKontraktor())
                        || checkColumn(temp.getProperty().getNamaProperty())) {
                    filterData.add(temp);
                }
            }
        }
        hitungTotal();
    }

    private void hitungTotal() {
        double totalPembangunan = 0;
        double totalPembayaran = 0;
        double totalSisaPembayaran = 0;
        for (PembangunanKontraktorHead pt : filterData) {
            totalPembangunan = totalPembangunan + pt.getTotalPembangunan();
            totalPembayaran = totalPembayaran + pt.getPembayaran();
            totalSisaPembayaran = totalSisaPembayaran + pt.getSisaPembayaran();
        }
        totalPembangunanLabel.setText(rp.format(totalPembangunan));
        totalPembayaranLabel.setText(rp.format(totalPembayaran));
        totalSisaPembayaranLabel.setText(rp.format(totalSisaPembayaran));
    }

    @FXML
    private void addNewPembangunan() {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembangunanKontraktor.fxml");
        DetailPembangunanKontraktorController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewPembangunan();
        x.saveButton.setOnAction((ActionEvent ev) -> {
            if ("".equals(x.kontraktorField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kontraktor belum dipilih");
            } else if ("".equals(x.propertyField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            } else if ("".equals(x.totalPembangunanField.getText()) || "0".equals(x.totalPembangunanField.getText())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Total pembangunan masih kosong");
            } else if (x.kategoriCombo.getSelectionModel().getSelectedItem() == null
                    || "".equals(x.kategoriCombo.getSelectionModel().getSelectedItem())) {
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori pembangunan belum dipilih");
            } else if (x.allDetail.isEmpty()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Detail pembangunan masih kosong");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            PembangunanKontraktorHead p = new PembangunanKontraktorHead();
                            p.setNoPembangunan(PembangunanKontraktorHeadDAO.getId(con));
                            p.setKodeKontraktor(x.pembangunan.getKodeKontraktor());
                            p.setKodeProperty(x.pembangunan.getKodeProperty());
                            p.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            p.setTotalPembangunan(Double.parseDouble(x.totalPembangunanField.getText().replaceAll(",", "")));
                            p.setPembayaran(0);
                            p.setSisaPembayaran(Double.parseDouble(x.totalPembangunanField.getText().replaceAll(",", "")));
                            int noUrut = 1;
                            for (PembangunanKontraktorDetail d : x.allDetail) {
                                d.setNoPembangunan(p.getNoPembangunan());
                                d.setNoUrut(noUrut);
                                noUrut++;
                            }
                            p.setListPembangunanKontraktorDetail(x.allDetail);
                            return Service.savePembangunanKontraktor(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembangunan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembangunan berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            }
        });
    }

    private void addRevisi(PembangunanKontraktorHead p) {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/NewPembangunanKontraktor.fxml");
        NewPembangunanKontraktorController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, child);
        x.setPembangunan(p, true);
        x.saveButton.setOnAction((event) -> {
            if (x.keteranganField.getText().equals("")) {
                mainApp.showMessage(Modality.NONE, "Warning", "Keterangan masih kosong");
            } else if (Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")) == 0) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            } else if (p.getSisaPembayaran()+ Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")) < 0) {
                mainApp.showMessage(Modality.NONE, "Warning", "Revisi pembangunan tidak boleh kurang dari pembayaran");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            if (p.getAddendum() != null) {
                                double pembayaran = 0;
                                STJHead stj = STJHeadDAO.getByKodeProperty(con, p.getKodeProperty(), "true");
                                if (stj != null) {
                                    pembayaran = pembayaran + stj.getJumlahRp();
                                }
                                List<SDP> listSDP = SDPDAO.getAllByKodeProperty(con, p.getKodeProperty(), "true");
                                for (SDP s : listSDP) {
                                    pembayaran = pembayaran + s.getJumlahRp();
                                }
                                KPR kpr = KPRDAO.getByKodeProperty(con, p.getKodeProperty());
                                if (kpr != null) {
                                    pembayaran = pembayaran + kpr.getJumlahRp();
                                }
                                Property prop = PropertyDAO.get(con, p.getKodeProperty());
                                double hargaJual = prop.getHargaJual()-prop.getDiskon()+prop.getAddendum()+p.getAddendum().getPerubahanHargaProperty();
                                if(hargaJual - pembayaran<0){
                                    return "Addendum tidak dapat dibuat, karena pembayaran customer sudah melebih nilai jual";
                                }
                                SerahTerima st = SerahTerimaDAO.getByKodeProperty(con, p.getKodeProperty(), "true");
                                 if(st!=null){
                                    return "Revisi tidak dapat dibuat, karena sudah serah terima";
                                 }
                            }
                            p.setListPembangunanKontraktorDetail(PembangunanKontraktorDetailDAO.getAllByNoPembangunan(con, p.getNoPembangunan()));

                            PembangunanKontraktorDetail d = new PembangunanKontraktorDetail();
                            d.setNoPembangunan(p.getNoPembangunan());
                            d.setNoUrut(p.getListPembangunanKontraktorDetail().size() + 1);
                            d.setTanggal(tglSql.format(new Date()));
                            d.setKeterangan(x.keteranganField.getText());
                            d.setJumlahRp(Double.parseDouble(x.jumlahRpField.getText().replaceAll(",", "")));
                            d.setAddendum("");
                            d.setKodeUser(sistem.getUser().getUsername());
                            d.setStatus("true");
                            d.setTglBatal("2000-01-01 00:00:00");
                            d.setUserBatal("");

                            return Service.saveRevisiPembangunanKontraktor(con, p, d);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembangunan();
                    if (task.getValue().equals("true")) {
                        mainApp.closeDialog(mainApp.MainStage, child);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembangunan berhasil disimpan");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();

            }
        });
    }

    private void detailPembangunan(PembangunanKontraktorHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembangunanKontraktor.fxml");
        DetailPembangunanKontraktorController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.getPembangunan(p.getNoPembangunan());
    }

    private void PembayaranPembangunan(PembangunanKontraktorHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewPembayaranHutang.fxml");
        NewPembayaranHutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewPembayaranPembangunanKontraktor(p);
        x.saveButton.setOnAction((ActionEvent event) -> {
            if ("0".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))
                    || "".equals(x.jumlahPembayaranField.getText().replaceAll(",", ""))) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah pembayaran masih kosong");
            } else if (Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", "")) > p.getSisaPembayaran()) {
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah yang dibayar melebihi sisa hutang");
            } else if (x.tipeKeuanganCombo.getSelectionModel().getSelectedItem() == null) {
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            } else {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            Hutang h = HutangDAO.getByKategoriAndKeteranganAndStatus(con, "Hutang Pembangunan Kontraktor", p.getNoPembangunan(), "open");

                            Pembayaran pembayaran = new Pembayaran();
                            pembayaran.setNoPembayaran(PembayaranDAO.getId(con));
                            pembayaran.setTglPembayaran(tglSql.format(new Date()));
                            pembayaran.setNoHutang(h.getNoHutang());
                            pembayaran.setJumlahPembayaran(Double.parseDouble(x.jumlahPembayaranField.getText().replaceAll(",", "")));
                            pembayaran.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            pembayaran.setCatatan("");
                            pembayaran.setKodeUser(sistem.getUser().getUsername());
                            pembayaran.setTglBatal("2000-01-01 00:00:00");
                            pembayaran.setUserBatal("");
                            pembayaran.setStatus("true");
                            pembayaran.setHutang(h);
                            return Service.saveNewPembayaranPembangunanKontraktor(con, p, pembayaran);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try {
                        mainApp.closeLoading();
                        getPembangunan();
                        if (task.getValue().equals("true")) {
                            mainApp.showMessage(Modality.NONE, "Success", "Pembayaran kontraktor berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                        } else {
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
        });
    }

    private void detailPembayaranPembangunan(PembangunanKontraktorHead p) {
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setDetailPembangunanKontraktor(p);
        x.pembayaranHutangTable.setRowFactory((TableView<Pembayaran> tableView) -> {
            final TableRow<Pembayaran> row = new TableRow<Pembayaran>() {
                @Override
                public void updateItem(Pembayaran item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran Pembangunan Kontraktor");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembayaran(item, stage);
                        });
                        boolean statusTipeKeuangan = false;
                        List<String> listTipeKeuangan = Function.getTipeKeuangan();
                        for (String s : listTipeKeuangan) {
                            if (item.getTipeKeuangan().equals(s)) {
                                statusTipeKeuangan = true;
                            }
                        }
                        for (Otoritas o : sistem.getUser().getOtoritas()) {
                            if (o.getJenis().equals("Batal Pembayaran Pembangunan Kontraktor") && o.isStatus() && statusTipeKeuangan) {
                                rm.getItems().add(batal);
                            }
                        }
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }

    private void batalPembayaran(Pembayaran pembayaran, Stage stage) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembayaran " + pembayaran.getNoPembayaran() + " ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Hutang hutang = HutangDAO.get(con, pembayaran.getNoHutang());
                        pembayaran.setTglBatal(tglSql.format(Function.getServerDate(con)));
                        pembayaran.setUserBatal(sistem.getUser().getUsername());
                        pembayaran.setStatus("false");
                        pembayaran.setHutang(hutang);
                        return Service.batalPembayaranPembangunanKontraktor(con, pembayaran);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getPembangunan();
                if (task.getValue().equals("true")) {
                    mainApp.closeDialog(mainApp.MainStage, stage);
                    mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                task.getException().printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }

    private void deletePembangunan(PembangunanKontraktorHead p) {
        if (p.getPembayaran() != 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat dibatalkan, karena sudah ada pembayaran");
        } else {
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                    "Batal pembangunan " + p.getNoPembangunan() + ", anda yakin ?");
            controller.OK.setOnAction((ActionEvent ev) -> {
                Task<String> task = new Task<String>() {
                    @Override
                    public String call() throws Exception {
                        try (Connection con = Koneksi.getConnection()) {
                            p.setListPembangunanKontraktorDetail(PembangunanKontraktorDetailDAO.getAllByNoPembangunan(con, p.getNoPembangunan()));
                            Boolean statusTanah = true;
                            Property prop = PropertyDAO.get(con, p.getKodeProperty());
                            if (prop.getStatus().equals("Mapped")
                                    || prop.getStatus().equals("Combined")
                                    || prop.getStatus().equals("Sold")) {
                                statusTanah = false;
                            }
                            if (statusTanah) {
                                for (PembangunanKontraktorDetail d : p.getListPembangunanKontraktorDetail()) {
                                    d.setTglBatal(tglSql.format(new Date()));
                                    d.setUserBatal(sistem.getUser().getUsername());
                                    d.setStatus("false");
                                }
                                return Service.batalPembangunanKontraktor(con, p);
                            } else {
                                return "Pembangunan tidak dapat dibatalkan, karena status tanah sudah terjual/sudah dipecah/sudah digabung";
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembangunan();
                    if (task.getValue().equals("true")) {
                        mainApp.showMessage(Modality.NONE, "Success", "Data Pembangunan berhasil dibatal");
                    } else {
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                });
                task.setOnFailed((e) -> {
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    mainApp.closeLoading();
                });
                new Thread(task).start();
            });
        }
    }

}
