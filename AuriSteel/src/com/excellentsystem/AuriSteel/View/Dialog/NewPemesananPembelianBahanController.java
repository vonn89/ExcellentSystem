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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPemesananPembelianBahanController {

    @FXML
    private TableView<PemesananPembelianBahanDetail> pemesananDetailTable;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> kategoriBahanColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> namaBahanColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> spesifikasiColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, String> keteranganColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> qtyColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> qtyDiterimaColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> hargaColumn;
    @FXML
    private TableColumn<PemesananPembelianBahanDetail, Number> totalColumn;

    @FXML
    private GridPane gridPane;
    @FXML
    public Label noPemesananField;
    @FXML
    private Label tglPemesananField;

    @FXML
    private TextField namaField;
    @FXML
    private TextArea alamatField;

    @FXML
    private Label totalQtyField;
    @FXML
    private Label totalQtyDiterimaField;

    @FXML
    public TextField noKontrakField;
    @FXML
    public ComboBox<String> paymentTermCombo;
    @FXML
    public ComboBox<String> deliveryTermCombo;

    @FXML
    public TextArea catatanField;
    @FXML
    public TextField totalPemesananField;
    @FXML
    public TextField downpaymentField;

    @FXML
    private Button addSupplierButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    public Supplier supplier;
    public ObservableList<PemesananPembelianBahanDetail> allPemesananDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        kategoriBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBahanProperty());
        
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        
        qtyColumn.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getQty()));
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        
        qtyDiterimaColumn.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getQtyDiterima()));
        qtyDiterimaColumn.setCellFactory(col -> Function.getTableCell());
        
        hargaColumn.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getHarga()));
        hargaColumn.setCellFactory(col -> Function.getTableCell());
        
        totalColumn.setCellValueFactory(cellData
                -> new SimpleDoubleProperty(cellData.getValue().getTotal()));
        totalColumn.setCellFactory(col -> Function.getTableCell());
        
        pemesananDetailTable.setItems(allPemesananDetail);

        ContextMenu cm = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang");
        addBarang.setOnAction((ActionEvent e) -> {
            addBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            pemesananDetailTable.refresh();
        });
        cm.getItems().addAll(addBarang, refresh);
        pemesananDetailTable.setContextMenu(cm);
        pemesananDetailTable.setRowFactory((TableView<PemesananPembelianBahanDetail> tv) -> {
            final TableRow<PemesananPembelianBahanDetail> row = new TableRow<PemesananPembelianBahanDetail>() {
                @Override
                public void updateItem(PemesananPembelianBahanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else {
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang");
                        addBarang.setOnAction((ActionEvent e) -> {
                            addBarang();
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e) -> {
                            deleteBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            pemesananDetailTable.refresh();
                        });
                        if (saveButton.isVisible()) {
                            rm.getItems().addAll(addBarang, delete);
                        }
                        rm.getItems().addAll(refresh);
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
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> listPayment = FXCollections.observableArrayList();
        listPayment.add("Cash Before Delivery");
        listPayment.add("Cash On Delivery");
        listPayment.add("Cash");
        listPayment.add("Net 7");
        listPayment.add("Net 14");
        listPayment.add("Net 30");
        paymentTermCombo.setItems(listPayment);
        
        ObservableList<String> listDelivery = FXCollections.observableArrayList();
        listDelivery.add("Franco");
        listDelivery.add("Loco");
        listDelivery.add("CIF");
        listDelivery.add("FOB");
        deliveryTermCombo.setItems(listDelivery);
    }

    @FXML
    private void hitungTotal() {
        double totalQty = 0;
        double totalQtyDiterima = 0;
        double totalPemesanan = 0;
        for (PemesananPembelianBahanDetail temp : allPemesananDetail) {
            totalPemesanan = totalPemesanan + temp.getTotal();
            totalQty = totalQty + temp.getQty();
            totalQtyDiterima = totalQtyDiterima + temp.getQtyDiterima();
        }
        totalQtyField.setText(df.format(totalQty));
        totalQtyDiterimaField.setText(df.format(totalQtyDiterima));
        totalPemesananField.setText(df.format(totalPemesanan));
    }

    public void setNewPemesanan() {
        noPemesananField.setText("");
        tglPemesananField.setText("");
    }

    public void setDetailPemesanan(String noPemesanan) {
        Task<PemesananPembelianBahanHead> task = new Task<PemesananPembelianBahanHead>() {
            @Override
            public PemesananPembelianBahanHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    PemesananPembelianBahanHead pemesanan = PemesananPembelianBahanHeadDAO.get(con, noPemesanan);
                    pemesanan.setSupplier(SupplierDAO.get(con, pemesanan.getKodeSupplier()));
                    pemesanan.setListPemesananPembelianBahanDetail(PemesananPembelianBahanDetailDAO.getAllByNoPemesanan(con, noPemesanan));
                    return pemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try {
                mainApp.closeLoading();
                addSupplierButton.setVisible(false);
                paymentTermCombo.setDisable(true);
                deliveryTermCombo.setDisable(true);
                noKontrakField.setDisable(true);
                catatanField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                List<MenuItem> removeMenu = new ArrayList<>();
                for (MenuItem m : pemesananDetailTable.getContextMenu().getItems()) {
                    if (m.getText().equals("Add Barang")) {
                        removeMenu.add(m);
                    }
                }
                pemesananDetailTable.getContextMenu().getItems().removeAll(removeMenu);

                PemesananPembelianBahanHead pemesanan = task.getValue();
                noPemesananField.setText(pemesanan.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(pemesanan.getTglPemesanan())));
                namaField.setText(pemesanan.getSupplier().getNama());
                alamatField.setText(pemesanan.getSupplier().getAlamat());
                noKontrakField.setText(pemesanan.getNoKontrak());
                paymentTermCombo.getSelectionModel().select(pemesanan.getPaymentTerm());
                deliveryTermCombo.getSelectionModel().select(pemesanan.getDeliveryTerm());
                catatanField.setText(pemesanan.getCatatan());
                allPemesananDetail.addAll(pemesanan.getListPemesananPembelianBahanDetail());
                totalPemesananField.setText(df.format(pemesanan.getTotalPemesanan()));
                downpaymentField.setText(df.format(pemesanan.getDownPayment()));
                hitungTotal();
            } catch (Exception e) {
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }


    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }

    @FXML
    private void addBarang() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddItemKategoriBahan.fxml");
        AddItemKategoriBahanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.namaBarangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.kategoriCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else {
                mainApp.closeDialog(stage, child);
                PemesananPembelianBahanDetail d = null;
                for (PemesananPembelianBahanDetail temp : allPemesananDetail) {
                    if (temp.getKategoriBahan().equals(controller.kategoriCombo.getSelectionModel().getSelectedItem())
                            && temp.getKeterangan().equals(controller.keteranganField.getText())
                            && temp.getHarga()== Double.parseDouble(controller.hargaField.getText().replaceAll(",", ""))) {
                        d = temp;
                    }
                }
                if (d == null) {
                    PemesananPembelianBahanDetail detail = new PemesananPembelianBahanDetail();
                    detail.setKategoriBahan(controller.kategoriCombo.getSelectionModel().getSelectedItem());
                    detail.setNamaBahan(controller.namaBarangField.getText());
                    detail.setSpesifikasi(controller.spesifikasiField.getText());
                    detail.setKeterangan(controller.keteranganField.getText());
                    detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    detail.setQtyDiterima(0);
                    detail.setHarga(Double.parseDouble(controller.hargaField.getText().replaceAll(",", "")));
                    detail.setTotal(Double.parseDouble(controller.totalField.getText().replaceAll(",", "")));
                    allPemesananDetail.add(detail);
                    hitungTotal();
                } else {
                    d.setQty(d.getQty() + Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    d.setTotal(d.getQty() * d.getHarga());
                    pemesananDetailTable.refresh();
                    hitungTotal();
                }
            }
        });
    }

    @FXML
    private void deleteBarang(PemesananPembelianBahanDetail d) {
        if (d.getQtyDiterima()> 0) {
            mainApp.showMessage(Modality.NONE, "Warning", "Qty barang sudah ada yang diterima, tidak dapat di hapus");
        } else {
            allPemesananDetail.remove(d);
            hitungTotal();
            pemesananDetailTable.refresh();
        }
    }

    @FXML
    private void addSupplier() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddSupplier.fxml");
        AddSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.supplierTable.setRowFactory((param) -> {
            TableRow<Supplier> row = new TableRow<Supplier>() {
            };
            row.setOnMouseClicked((MouseEvent evt) -> {
                if (evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount() == 2) {
                    mainApp.closeDialog(stage, child);
                    supplier = row.getItem();
                    namaField.setText(supplier.getNama());
                    alamatField.setText(supplier.getAlamat());
                }
            });
            return row;
        });
    }

}
