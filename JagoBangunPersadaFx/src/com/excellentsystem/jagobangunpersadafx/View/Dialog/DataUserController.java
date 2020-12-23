/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.UserDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.key;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.User;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataUserController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> levelColumn;

    @FXML
    private TreeTableView<Otoritas> otoritasTable;
    @FXML
    private TreeTableColumn<Otoritas, String> jenisColumn;
    @FXML
    private TreeTableColumn<Otoritas, Boolean> statusColumn;

    @FXML
    private TableView<OtoritasKeuangan> otoritasKeuanganTable;
    @FXML
    private TableColumn<OtoritasKeuangan, String> tipeKeuanganColumn;
    @FXML
    private TableColumn<OtoritasKeuangan, Boolean> statusKeuanganColumn;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> levelCombo;

    @FXML
    private CheckBox checkOtoritas;
    @FXML
    private CheckBox checkOtoritasKeuangan;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;

    private Main mainApp;
    private Stage owner;
    private Stage stage;

    private final TreeItem<Otoritas> root = new TreeItem<>();
    private ObservableList<User> allUser = FXCollections.observableArrayList();
    private List<Otoritas> otoritas = new ArrayList<>();
    private ObservableList<OtoritasKeuangan> otoritaskeuangan = FXCollections.observableArrayList();
    private String status;

    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        usernameColumn.setCellFactory(col -> Function.getWrapTableCell(usernameColumn));

        levelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        levelColumn.setCellFactory(col -> Function.getWrapTableCell(levelColumn));

        jenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jenisProperty());
        jenisColumn.setCellFactory(col -> Function.getWrapTreeTableCell(jenisColumn));

        statusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Otoritas, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().getValue().setStatus(newValue);
                for (TreeItem<Otoritas> child : param.getValue().getChildren()) {
                    child.getValue().setStatus(newValue);
                }
                otoritasTable.refresh();
            });
            return booleanProp;
        });
        statusColumn.setCellFactory((TreeTableColumn<Otoritas, Boolean> p) -> {
            CheckBoxTreeTableCell<Otoritas, Boolean> cell = new CheckBoxTreeTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });

        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKeuanganProperty());
        tipeKeuanganColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganColumn));

        statusKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusKeuanganColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer param) -> otoritasKeuanganTable.getItems().get(param).statusProperty()));

        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectUser(newValue));

        final ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New User");
        addNew.setOnAction((ActionEvent event) -> {
            newUser();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getUser();
        });
        menu.getItems().addAll(addNew, refresh);
        userTable.setContextMenu(menu);
        userTable.setRowFactory(table -> {
            TableRow<User> row = new TableRow<User>() {
                @Override
                public void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New User");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Delete User");
                        hapus.setOnAction((ActionEvent event) -> {
                            delete(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getUser();
                        });
                        rowMenu.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                passwordField.requestFocus();
            }
        });

    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        try {
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight() * 80 / 100);
            stage.setWidth(mainApp.screenSize.getWidth() * 80 / 100);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            userTable.setItems(allUser);
            otoritasKeuanganTable.setItems(otoritaskeuangan);
            ObservableList<String> allLevel = FXCollections.observableArrayList();
            allLevel.clear();
            allLevel.add("Administrasi");
            allLevel.add("Marketing");
            allLevel.add("Manager");
            levelCombo.setItems(allLevel);
            getUser();
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void checkOtoritas() {
        for (TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()) {
            head.getValue().setStatus(checkOtoritas.isSelected());
            for (TreeItem<Otoritas> child : head.getChildren()) {
                child.getValue().setStatus(checkOtoritas.isSelected());
            }
        }
        otoritasTable.refresh();
    }

    @FXML
    private void checkOtoritasKeuangan() {
        for (OtoritasKeuangan o : otoritaskeuangan) {
            o.setStatus(checkOtoritasKeuangan.isSelected());
        }
        otoritasKeuanganTable.refresh();
    }

    @FXML
    private void getUser() {
        Task<List<User>> task = new Task<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<User> allUser = UserDAO.getAll(con);
                    List<Otoritas> allOtoritas = OtoritasDAO.getAll(con);
                    List<OtoritasKeuangan> allOtoritasKeuangan = OtoritasKeuanganDAO.getAll(con);
                    for (User u : allUser) {
                        u.setPassword(Function.decrypt(u.getPassword(), key));
                        List<Otoritas> otoritas = new ArrayList<>();
                        for (Otoritas o : allOtoritas) {
                            if (u.getUsername().equals(o.getUsername())) {
                                otoritas.add(o);
                            }
                        }
                        List<OtoritasKeuangan> otoritasKeuangan = new ArrayList<>();
                        for (OtoritasKeuangan ok : allOtoritasKeuangan) {
                            if (u.getUsername().equals(ok.getUsername())) {
                                otoritasKeuangan.add(ok);
                            }
                        }
                        u.setOtoritas(otoritas);
                        u.setOtoritasKeuangan(otoritasKeuangan);
                    }
                    return allUser;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allUser.clear();
            allUser.addAll(task.getValue());
            reset();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }

    private TreeItem<Otoritas> createTreeItem(String head, List<String> child) {
        Otoritas temp = new Otoritas();
        temp.setJenis(head);
        temp.setStatus(false);
        for (Otoritas o : otoritas) {
            if (o.getJenis().equals(temp.getJenis())) {
                temp.setStatus(o.isStatus());
            }
        }
        TreeItem<Otoritas> parent = new TreeItem<>(temp);
        for (String s : child) {
            Otoritas temp2 = new Otoritas();
            temp2.setJenis(s);
            temp2.setStatus(false);
            for (Otoritas o : otoritas) {
                if (o.getJenis().equals(temp2.getJenis())) {
                    temp2.setStatus(o.isStatus());
                }
            }
            parent.getChildren().addAll(new TreeItem<>(temp2));
        }
        return parent;
    }

    private void setTable() {
        try {
            if (otoritasTable.getRoot() != null) {
                otoritasTable.getRoot().getChildren().clear();
            }

            root.getChildren().add(createTreeItem("Data Property",
                    Arrays.asList(
                            "Update Property",
                            "Detail Property",
                            "Pemecahan Property",
                            "Penggabungan Property"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Customer",
                    Arrays.asList(
                            "Add New Customer",
                            "Detail Customer",
                            "Delete Customer"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Kontraktor",
                    Arrays.asList(
                            "Add New Kontraktor",
                            "Detail Kontraktor",
                            "Delete Kontraktor"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Karyawan",
                    Arrays.asList(
                            "Add New Karyawan",
                            "Detail Karyawan",
                            "Delete Karyawan"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Tukang",
                    Arrays.asList(
                            "Add New Tukang",
                            "Detail Tukang",
                            "Delete Tukang"
                    )
            ));
            root.getChildren().add(createTreeItem("Pembelian Tanah",
                    Arrays.asList(
                            "Add New Pembelian Tanah",
                            "Detail Pembelian Tanah",
                            "Batal Pembelian Tanah"
                    )
            ));
            root.getChildren().add(createTreeItem("Pembangunan",
                    Arrays.asList(
                            "Add New Pembangunan",
                            "Detail Pembangunan",
                            "Batal Pembangunan"
                    )
            ));
            root.getChildren().add(createTreeItem("Pembangunan Kontraktor",
                    Arrays.asList(
                            "Add New Pembangunan Kontraktor",
                            "Add Revisi Pembangunan Kontraktor",
                            "Detail Pembangunan Kontraktor",
                            "Pembayaran Pembangunan Kontraktor",
                            "Detail Pembayaran Pembangunan Kontraktor",
                            "Batal Pembayaran Pembangunan Kontraktor",
                            "Batal Pembangunan Kontraktor"
                    )
            ));
            root.getChildren().add(createTreeItem("Rencana Anggaran Proyek",
                    Arrays.asList(
                            "Add New Rencana Anggaran Proyek",
                            "Detail Rencana Anggaran Proyek",
                            "Edit Rencana Anggaran Proyek",
                            "Hapus Rencana Anggaran Proyek",
                            "Print Rencana Anggaran Proyek"
                    )
            ));
            root.getChildren().add(createTreeItem("Realisasi Anggaran Proyek",
                    Arrays.asList(
                            "Add New Realisasi",
                            "Detail Realisasi",
                            "Batal Realisasi",
                            "Print Realisasi Anggaran Proyek"
                    )
            ));
            root.getChildren().add(createTreeItem("Terima Tanda Jadi",
                    Arrays.asList(
                            "Add New Terima Tanda Jadi",
                            "Detail Terima Tanda Jadi",
                            "Batal Terima Tanda Jadi",
                            "Print Surat Tanda Jadi"
                    )
            ));
            root.getChildren().add(createTreeItem("Terima Down Payment",
                    Arrays.asList(
                            "Add New Terima Down Payment",
                            "Detail Terima Down Payment",
                            "Batal Terima Down Payment",
                            "Print Surat Down Payment"
                    )
            ));
            root.getChildren().add(createTreeItem("Pelunasan Down Payment",
                    Arrays.asList(
                            "Add New Pelunasan Down Payment",
                            "Detail Pelunasan Down Payment",
                            "Batal Pelunasan Down Payment",
                            "Print Surat Keterangan Lunas",
                            "Buat Surat Keterangan Lunas Manual"
                    )
            ));
            root.getChildren().add(createTreeItem("Terima Pencairan KPR",
                    Arrays.asList(
                            "Add New Terima Pencairan KPR",
                            "Detail Terima Pencairan KPR",
                            "Batal Terima Pencairan KPR"
                    )
            ));
            root.getChildren().add(createTreeItem("Addendum",
                    Arrays.asList(
                            "Detail Addendum"
                    )
            ));
            root.getChildren().add(createTreeItem("Serah Terima",
                    Arrays.asList(
                            "Add New Serah Terima",
                            "Detail Serah Terima",
                            "Batal Serah Terima",
                            "Print Surat Serah Terima"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Keuangan",
                    Arrays.asList(
                            "Add New Keuangan",
                            "Detail Transaksi Keuangan",
                            "Transfer Keuangan",
                            "Edit Keterangan",
                            "Batal Keuangan"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Hutang",
                    Arrays.asList(
                            "Add New Hutang",
                            "Detail Hutang",
                            "Pembayaran Hutang",
                            "Batal Pembayaran Hutang"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Piutang",
                    Arrays.asList(
                            "Add New Piutang",
                            "Detail Piutang",
                            "Terima Pembayaran Piutang",
                            "Batal Terima Pembayaran Piutang"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Modal",
                    Arrays.asList(
                            "Tambah Modal",
                            "Ambil Modal"
                    )
            ));
            root.getChildren().add(createTreeItem("Data Aset Tetap",
                    Arrays.asList(
                            "Pembelian Aset Tetap",
                            "Penjualan Aset Tetap",
                            "Detail Aset Tetap"
                    )
            ));
            root.getChildren().add(createTreeItem("Laporan Property",
                    new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Kategori Property",
                    new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Untung-Rugi",
                    new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Untung-Rugi Periode",
                    new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Neraca",
                    new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pengaturan Umum",
                    Arrays.asList(
                            "Data User",
                            "Kategori Property",
                            "Kategori Hutang",
                            "Kategori Piutang",
                            "Kategori Keuangan",
                            "Kategori Transaksi"
                    )
            ));

            otoritasTable.setRoot(root);
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    @FXML
    private void reset() {
        otoritaskeuangan.clear();
        otoritas.clear();
        usernameField.setText("");
        passwordField.setText("");
        levelCombo.getSelectionModel().clearSelection();
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        levelCombo.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status = "";
        setTable();
    }

    public void selectUser(User u) {
        if (u != null) {
            status = "update";
            otoritas.clear();
            otoritas.addAll(u.getOtoritas());
            otoritaskeuangan.clear();
            otoritaskeuangan.addAll(u.getOtoritasKeuangan());
            usernameField.setText(u.getUsername());
            passwordField.setText(u.getPassword());
            levelCombo.getSelectionModel().select(u.getLevel());
            usernameField.setDisable(true);
            passwordField.setDisable(false);
            levelCombo.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            setTable();
        }
    }

    public void newUser() {
        try {
            status = "new";
            usernameField.setText("");
            passwordField.setText("");
            levelCombo.getSelectionModel().clearSelection();
            usernameField.setDisable(false);
            passwordField.setDisable(false);
            levelCombo.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);

            setTable();
            otoritaskeuangan.clear();
            for (String k : Function.getAllTipeKeuangan()) {
                OtoritasKeuangan temp = new OtoritasKeuangan();
                temp.setKodeKeuangan(k);
                temp.setStatus(checkOtoritasKeuangan.isSelected());
                otoritaskeuangan.add(temp);
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }

    public void saveUser() {
        if (usernameField.getText().equals("")) {
            mainApp.showMessage(Modality.NONE, "Warning", "User masih kosong");
        } else {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        User user = new User();
                        user.setUsername(usernameField.getText());
                        user.setPassword(passwordField.getText());
                        user.setLevel(levelCombo.getSelectionModel().getSelectedItem());
                        List<Otoritas> listOtoritas = new ArrayList<>();
                        for (TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()) {
                            Otoritas o = head.getValue();
                            o.setUsername(usernameField.getText());
                            listOtoritas.add(o);
                            for (TreeItem<Otoritas> child : head.getChildren()) {
                                Otoritas o2 = child.getValue();
                                o2.setUsername(usernameField.getText());
                                listOtoritas.add(o2);
                            }
                        }
                        user.setOtoritas(listOtoritas);
                        for (OtoritasKeuangan temp : otoritaskeuangan) {
                            temp.setUsername(user.getUsername());
                        }
                        user.setOtoritasKeuangan(otoritaskeuangan);

                        if (status.equals("update")) {
                            return Service.updateUser(con, user);
                        } else if (status.equals("new")) {
                            return Service.newUser(con, user);
                        } else {
                            return "false";
                        }
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getUser();
                if (task.getValue().equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
                    reset();
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }

    public void delete(User user) {
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete user " + user.getUsername() + " ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override
                public String call() throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.deleteUser(con, user);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getUser();
                String message = task.getValue();
                if (message.equals("true")) {
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil dihapus");
                } else {
                    mainApp.showMessage(Modality.NONE, "Failed", message);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }

    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
}
