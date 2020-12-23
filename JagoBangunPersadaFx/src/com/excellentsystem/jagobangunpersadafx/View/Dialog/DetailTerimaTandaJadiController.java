/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.STJDetail;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailTerimaTandaJadiController {

    @FXML
    private VBox vbox;
    @FXML
    private HBox salesHbox;
    @FXML
    private HBox pembayaranHbox;
    @FXML
    private HBox buttonHbox;

    @FXML
    private TableView<STJDetail> detailSTJtable;
    @FXML
    private TableColumn<STJDetail, String> tglPembayaranColumn;
    @FXML
    private TableColumn<STJDetail, Number> jumlahPembayaranColumn;

    @FXML
    private TextField noSTJField;
    @FXML
    private TextField tglSTJField;
    @FXML
    public TextField namaCustomerField;
    @FXML
    private TextField alamatCustomerField;
    @FXML
    public TextField kodePropertyField;
    @FXML
    private TextField namaPropertyField;
    @FXML
    private TextField tipeUnitField;
    @FXML
    private TextField luasTanahField;
    @FXML
    private TextField hargaField;
    @FXML
    public TextField diskonField;
    @FXML
    public TextField jumlahPembayaranField;

    @FXML
    public ComboBox<String> tipeKeuanganCombo;
    @FXML
    private DatePicker tglPembayaranPicker;
    @FXML
    private TextField jumlahDPField;
    @FXML
    public TextArea catatanField;
    @FXML
    public TextField salesField;

    @FXML
    private Button customerButton;
    @FXML
    private Button propertyButton;
    @FXML
    private Button addButton;
    @FXML
    private Button salesButton;
    @FXML
    public Button saveButton;
    @FXML
    private Button cancelButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public STJHead stj;
    public ObservableList<STJDetail> allDetail = FXCollections.observableArrayList();

    public void initialize() {
        tglPembayaranColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(cellData.getValue().getTglBayar());
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));

        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));

        tglPembayaranPicker.setConverter(Function.getTglConverter());
        tglPembayaranPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
        Function.setNumberField(diskonField);
        Function.setNumberField(jumlahDPField);
        Function.setNumberField(jumlahPembayaranField);
        jumlahDPField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addPlan();
            }
        });

        detailSTJtable.setRowFactory((TableView<STJDetail> tableView) -> {
            final TableRow<STJDetail> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem batal = new MenuItem("Delete");
            batal.setOnAction((ActionEvent event) -> {
                allDetail.remove(row.getItem());
                Collections.sort(allDetail, (STJDetail o1, STJDetail o2) -> {
                    if (o1.getTglBayar() == null || o2.getTglBayar() == null) {
                        return 0;
                    }
                    try {
                        return tgl.parse(o1.getTglBayar()).compareTo(tgl.parse(o2.getTglBayar()));
                    } catch (ParseException ex) {
                        return 0;
                    }
                });
                for (int i = 0; i < allDetail.size(); i++) {
                    int tahap = i + 1;
                    allDetail.get(i).setTahap(String.valueOf(tahap));
                }
                stj.setAllDetail(allDetail);
                detailSTJtable.refresh();
            });
            if (saveButton.isVisible()) {
                rowMenu.getItems().addAll(batal);
            }
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu) null));
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
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        detailSTJtable.setItems(allDetail);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for (OtoritasKeuangan k : sistem.getUser().getOtoritasKeuangan()) {
            listKeuangan.add(k.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        for (Node n : vbox.getChildren()) {
            n.managedProperty().bind(n.visibleProperty());
        }
        for (Node n : pembayaranHbox.getChildren()) {
            n.managedProperty().bind(n.visibleProperty());
        }
        for (Node n : salesHbox.getChildren()) {
            n.managedProperty().bind(n.visibleProperty());
        }
        for (Node n : buttonHbox.getChildren()) {
            n.managedProperty().bind(n.visibleProperty());
        }
    }

    public void setNewSTJ() {
        stj = new STJHead();
        noSTJField.setText("-");
        tglSTJField.setText(tglLengkap.format(new Date()));
    }

    public void getSTJ(String noSTJ) {
        Task<STJHead> task = new Task<STJHead>() {
            @Override
            public STJHead call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    STJHead temp = STJHeadDAO.get(con, noSTJ);
                    temp.setAllDetail(STJDetailDAO.getAllByNoSTJ(con, noSTJ));
                    temp.setCustomer(CustomerDAO.get(con, temp.getKodeCustomer()));
                    temp.setProperty(PropertyDAO.get(con, temp.getKodeProperty()));
                    temp.setSales(KaryawanDAO.get(con, temp.getKodeSales()));
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            setSTJ(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    public void setSTJ(STJHead temp) {
        try {
            stj = temp;
            jumlahPembayaranField.setDisable(true);
            diskonField.setDisable(true);
            tipeKeuanganCombo.setDisable(true);
            tglPembayaranPicker.setVisible(false);
            jumlahDPField.setVisible(false);
            addButton.setVisible(false);
            catatanField.setEditable(false);
            customerButton.setVisible(false);
            propertyButton.setVisible(false);
            salesButton.setVisible(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);

            noSTJField.setText(stj.getNoSTJ());
            tglSTJField.setText(tglLengkap.format(tglSql.parse(stj.getTglSTJ())));
            namaCustomerField.setText(stj.getCustomer().getNama());
            alamatCustomerField.setText(stj.getCustomer().getAlamat());
            kodePropertyField.setText(stj.getProperty().getKodeProperty());
            namaPropertyField.setText(stj.getProperty().getNamaProperty());
            tipeUnitField.setText(stj.getProperty().getTipe());
            luasTanahField.setText(qty.format(stj.getProperty().getLuasTanah()));
            hargaField.setText(rp.format(stj.getProperty().getHargaJual()));
            diskonField.setText(rp.format(stj.getProperty().getDiskon()));
            jumlahPembayaranField.setText(rp.format(stj.getJumlahRp()));
            tipeKeuanganCombo.setItems(FXCollections.observableArrayList(stj.getTipeKeuangan()));
            tipeKeuanganCombo.getSelectionModel().selectFirst();
            catatanField.setText(stj.getKeterangan());
            salesField.setText(stj.getSales().getNama());
            allDetail.addAll(stj.getAllDetail());

            detailSTJtable.refresh();
        } catch (ParseException ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }

    @FXML
    private void setCustomer() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddCustomer.fxml");
        AddCustomerController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.customerTable.setRowFactory((TableView<Customer> tableView) -> {
            final TableRow<Customer> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Customer c = row.getItem();
                    namaCustomerField.setText(c.getNama());
                    alamatCustomerField.setText(c.getAlamat());
                    stj.setCustomer(c);
                    stj.setKodeCustomer(c.getKodeCustomer());
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    }

    @FXML
    private void setSales() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddSales.fxml");
        AddSalesController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.karyawanTable.setRowFactory((TableView<Karyawan> tableView) -> {
            final TableRow<Karyawan> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Karyawan k = row.getItem();
                    salesField.setText(k.getNama());
                    stj.setSales(k);
                    stj.setKodeSales(k.getKodeKaryawan());
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    }

    @FXML
    private void setProperty() {
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> status = new ArrayList<>();
        status.add("Available");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
                    Property p = row.getItem();
                    kodePropertyField.setText(p.getKodeProperty());
                    namaPropertyField.setText(p.getNamaProperty());
                    tipeUnitField.setText(p.getTipe());
                    luasTanahField.setText(qty.format(p.getLuasTanah()));
                    hargaField.setText(rp.format(p.getHargaJual()));
                    diskonField.setText(rp.format(p.getDiskon()));
                    stj.setProperty(p);
                    stj.setKodeProperty(p.getKodeProperty());
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    }

    @FXML
    private void addPlan() {
        if (tglPembayaranPicker.getValue() == null) {
            mainApp.showMessage(Modality.NONE, "Warning", "Tgl Pembayaran DP masih kosong");
        } else if ("".equals(jumlahDPField.getText()) || "0".equals(jumlahDPField.getText())) {
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran DP masih kosong");
        } else {
            try {
                STJDetail detail = new STJDetail();
                detail.setNoSTJ(noSTJField.getText());
                detail.setTahap("");
                detail.setTglBayar(tgl.format(tglBarang.parse(tglPembayaranPicker.getValue().toString())));
                detail.setJumlahRp(Double.parseDouble(jumlahDPField.getText().replaceAll(",", "")));
                allDetail.add(detail);
                Collections.sort(allDetail, (STJDetail o1, STJDetail o2) -> {
                    if (o1.getTglBayar() == null || o2.getTglBayar() == null) {
                        return 0;
                    }
                    try {
                        return tgl.parse(o1.getTglBayar()).compareTo(tgl.parse(o2.getTglBayar()));
                    } catch (ParseException ex) {
                        return 0;
                    }
                });
                for (int i = 0; i < allDetail.size(); i++) {
                    int tahap = i + 1;
                    allDetail.get(i).setTahap(String.valueOf(tahap));
                }
                stj.setAllDetail(allDetail);
                detailSTJtable.refresh();
            } catch (ParseException ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        }
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
