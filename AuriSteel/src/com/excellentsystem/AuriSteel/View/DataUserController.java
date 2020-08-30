/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.OtoritasDAO;
import com.excellentsystem.AuriSteel.DAO.UserDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.key;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.User;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
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
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataUserController  {

    
    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> kodeUserColumn;
    @FXML private TableColumn<User, String> namaColumn;
    @FXML private TableColumn<User, String> levelColumn;
    
    @FXML private TreeTableView<Otoritas> otoritasTable;
    @FXML private TreeTableColumn<Otoritas, String> jenisColumn;
    @FXML private TreeTableColumn<Otoritas, Boolean> statusColumn;

    @FXML private CheckBox checkOtoritas;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField namaField;
    @FXML private ComboBox<String> levelCombo;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private final TreeItem<Otoritas> root = new TreeItem<>();
    private ObservableList<User> allUser = FXCollections.observableArrayList();
    private List<Otoritas> otoritas = new ArrayList<>();
    private Main mainApp;  
    private String status;
    public void initialize() {
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        namaColumn.setCellFactory(col -> Function.getWrapTableCell(namaColumn));
        
        levelColumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        levelColumn.setCellFactory(col -> Function.getWrapTableCell(levelColumn));
        
        jenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jenisProperty());
        jenisColumn.setCellFactory(col -> Function.getWrapTreeTableCell(jenisColumn));
        
        statusColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<Otoritas, Boolean> param) -> {
            SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(param.getValue().getValue().isStatus());
            booleanProp.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                param.getValue().getValue().setStatus(newValue);
                for(TreeItem<Otoritas> child : param.getValue().getChildren()){
                    child.getValue().setStatus(newValue);
                }
                otoritasTable.refresh();
            });
            return booleanProp;
        });
        statusColumn.setCellFactory((TreeTableColumn<Otoritas,Boolean> p) -> {
            CheckBoxTreeTableCell<Otoritas,Boolean> cell = new CheckBoxTreeTableCell<>();
            cell.setAlignment(Pos.CENTER);
            return cell;
        });
        
        userTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selectUser(newValue));
        
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New User");
        addNew.setOnAction((ActionEvent event) -> {
            newUser();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getUser();
        });
        rm.getItems().addAll(addNew, refresh);
        userTable.setContextMenu(rm);
        userTable.setRowFactory(table -> {
            TableRow<User> row = new TableRow<User>(){
                @Override
                public void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    }else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New User");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Hapus User");
                        hapus.setOnAction((ActionEvent event) -> {
                            delete(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getUser();
                        });
                        rm.getItems().addAll(addNew, hapus, refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        usernameField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        userTable.setItems(allUser);
        ObservableList<String> allLevel = FXCollections.observableArrayList();
        allLevel.clear();
        allLevel.add("Pembelian");
        allLevel.add("Penjualan");
        allLevel.add("Keuangan");
        allLevel.add("Produksi");
        allLevel.add("Gudang");
        allLevel.add("Manager");
        levelCombo.setItems(allLevel);
        getUser();
    }
    @FXML
    private void checkOtoritas(){
        for(TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()){
            head.getValue().setStatus(checkOtoritas.isSelected());
            for(TreeItem<Otoritas> child : head.getChildren()){
                child.getValue().setStatus(checkOtoritas.isSelected());
            }
        }
        otoritasTable.refresh();
    }
    @FXML
    private void getUser(){
        Task<List<User>> task = new Task<List<User>>() {
            @Override 
            public List<User> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<User> allUser = UserDAO.getAll(con);
                    List<Otoritas> allOtoritas = OtoritasDAO.getAll(con);
                    for(User u : allUser){
                        u.setPassword(Function.decrypt(u.getPassword(), key));
                        List<Otoritas> otoritas = new ArrayList<>();
                        for(Otoritas o : allOtoritas){
                            if(u.getKodeUser().equals(o.getKodeUser()))
                                otoritas.add(o);
                        }
                        u.setOtoritas(otoritas);
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
    private TreeItem<Otoritas> createTreeItem(String head, List<String> child){
        Otoritas temp = new Otoritas();
        temp.setJenis(head);
        temp.setStatus(false);
        for(Otoritas o : otoritas){
            if(o.getJenis().equals(temp.getJenis()))
                temp.setStatus(o.isStatus());
        }
        TreeItem<Otoritas> parent = new TreeItem<>(temp);
        for(String s : child){
            Otoritas temp2 = new Otoritas();
            temp2.setJenis(s);
            temp2.setStatus(false);
            for(Otoritas o : otoritas){
                if(o.getJenis().equals(temp2.getJenis()))
                    temp2.setStatus(o.isStatus());
            }
            parent.getChildren().addAll(new TreeItem<>(temp2));
        }
        return parent;
    }
    private void setTable(){
        try{
            if(otoritasTable.getRoot()!=null)
                otoritasTable.getRoot().getChildren().clear();
            
            root.getChildren().add(createTreeItem("Dashboard",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data Customer", 
                Arrays.asList(
                    "Add New Customer", 
                    "Edit Customer", 
                    "Delete Customer"
                )
            ));
            root.getChildren().add(createTreeItem("Data Supplier",
                Arrays.asList(
                    "Add New Supplier",
                    "Edit Supplier",
                    "Delete Supplier"
                )
            ));
            root.getChildren().add(createTreeItem("Data Pegawai",
                Arrays.asList(
                    "Add New Pegawai",
                    "Edit Pegawai",
                    "Delete Pegawai"
                )
            ));
            root.getChildren().add(createTreeItem("Data Bahan",
                Arrays.asList(
                    "Edit Bahan"
                )
            ));
            root.getChildren().add(createTreeItem("Data Barang",
                Arrays.asList(
                    "Add New Barang",
                    "Edit Barang",
                    "Delete Barang"
                )
            ));
            root.getChildren().add(createTreeItem("Data Absensi",
                Arrays.asList(
                    "Edit Absensi"
                )
            ));
            root.getChildren().add(createTreeItem("Laporan Absensi",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pemesanan",
                Arrays.asList(
                    "Add New Pemesanan",
                    "Detail Pemesanan",
                    "Edit Pemesanan",
                    "Batal Pemesanan",
                    "Approve Pemesanan",
                    "Pemesanan Selesai",
                    "Terima Pembayaran DP",
                    "Detail Terima Pembayaran DP",
                    "Batal Terima Pembayaran DP",
                    "Print Order Confirmation"
                )
            ));
            root.getChildren().add(createTreeItem("Pemesanan Coil",
                Arrays.asList(
                    "Add New Pemesanan Coil",
                    "Detail Pemesanan Coil",
                    "Batal Pemesanan Coil",
                    "Pemesanan Coil Selesai",
                    "Terima Pembayaran DP Coil",
                    "Detail Terima Pembayaran DP Coil",
                    "Batal Terima Pembayaran DP Coil",
                    "Print Order Confirmation Coil"
                )
            ));
            root.getChildren().add(createTreeItem("Penjualan",
                Arrays.asList(
                    "Detail Penjualan",
                    "Detail Pembayaran Penjualan",
                    "Terima Pembayaran",
                    "Batal Terima Pembayaran",
                    "Set Jatuh Tempo Penjualan",
                    "Print Invoice"
                )
            ));
            root.getChildren().add(createTreeItem("Penjualan Coil",
                Arrays.asList(                    
                    "Detail Penjualan Coil",
                    "Detail Pembayaran Penjualan Coil",
                    "Terima Pembayaran Coil",
                    "Batal Terima Pembayaran Coil",
                    "Set Jatuh Tempo Penjualan Coil",
                    "Print Invoice Coil"
                )
            ));
            root.getChildren().add(createTreeItem("Pembelian",
                Arrays.asList(                    
                    "Add New Pembelian",
                    "Detail Pembelian",
                    "Batal Pembelian",
                    "Detail Pembayaran Pembelian",
                    "Pembayaran Pembelian",
                    "Batal Pembayaran Pembelian",
                    "Set Jatuh Tempo Pembelian"
                )
            ));
            root.getChildren().add(createTreeItem("Pembelian Barang",
                Arrays.asList(                    
                    "Add New Pembelian Barang",
                    "Detail Pembelian Barang",
                    "Batal Pembelian Barang",
                    "Detail Pembayaran Pembelian Barang",
                    "Pembayaran Pembelian Barang",
                    "Batal Pembayaran Pembelian Barang",
                    "Set Jatuh Tempo Pembelian Barang"
                )
            ));
            root.getChildren().add(createTreeItem("Permintaan Barang",
                Arrays.asList(                    
                    "Print SPK"
                )
            ));
            root.getChildren().add(createTreeItem("Produksi Barang",
                Arrays.asList(          
                    "Add New Produksi",
                    "Detail Produksi",
                    "Batal Produksi"
                )
            ));
            root.getChildren().add(createTreeItem("Pengiriman Barang",
                Arrays.asList(                    
                    "Add New Pengiriman",
                    "Detail Pengiriman",
                    "Verifikasi Pengiriman",
                    "Batal Pengiriman",
                    "Print Surat Jalan"
                )
            ));
            root.getChildren().add(createTreeItem("Pengiriman Coil",
                Arrays.asList(                    
                    "Add New Pengiriman Coil",
                    "Detail Pengiriman Coil",
                    "Batal Pengiriman Coil",
                    "Print Surat Jalan Coil"
                )
            ));
            root.getChildren().add(createTreeItem("Pindah Bahan",
                Arrays.asList(                    
                    "Add New Pindah Bahan",
                    "Detail Pindah Bahan",
                    "Batal Pindah Bahan",
                    "Print Surat Jalan Pindah Bahan"
                )
            ));
            root.getChildren().add(createTreeItem("Pindah Barang",
                Arrays.asList(                    
                    "Add New Pindah Barang",
                    "Detail Pindah Barang",
                    "Batal Pindah Barang",
                    "Print Surat Jalan Pindah Barang"
                )
            ));
            root.getChildren().add(createTreeItem("Keuangan",
                Arrays.asList(                    
                    "Add New Transaksi",    
                    "Detail Transaksi",
                    "Transfer Keuangan",
                    "Batal Transaksi"
                )
            ));
            root.getChildren().add(createTreeItem("Hutang",
                Arrays.asList(                    
                    "Add New Hutang",
                    "Detail Hutang",
                    "Pembayaran Hutang",
                    "Batal Pembayaran Hutang",
                    "Set Jatuh Tempo Hutang"
                )
            ));
            root.getChildren().add(createTreeItem("Piutang",
                Arrays.asList(                    
                    "Add New Piutang",
                    "Detail Piutang",
                    "Terima Pembayaran Piutang",
                    "Batal Terima Pembayaran Piutang",
                    "Set Jatuh Tempo Piutang"
                )
            ));
            root.getChildren().add(createTreeItem("Modal",
                Arrays.asList(                    
                    "Tambah Modal",
                    "Ambil Modal"
                )
            ));
            root.getChildren().add(createTreeItem("Aset Tetap",
                Arrays.asList(                    
                    "Pembelian Aset Tetap",
                    "Penjualan Aset Tetap",
                    "Detail Aset Tetap"
                )
            ));
            root.getChildren().add(createTreeItem("Laporan Barang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Penjualan",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Pembelian",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Keuangan",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Managerial",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pengaturan Umum",
                Arrays.asList(                    
                    "Data User",        
                    "Data Gudang",
                    "Print Laporan",
                    "Export Excel",
                    "Penyesuaian Stok Bahan",
                    "Penyesuaian Stok Barang",
                    "Kategori Bahan",
                    "Kategori Barang",
                    "Kategori Hutang",
                    "Kategori Piutang",
                    "Kategori Keuangan",
                    "Kategori Transaksi"
                )
            ));
            
            otoritasTable.setRoot(root);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void reset(){
        otoritas.clear();
        usernameField.setText("");
        passwordField.setText("");
        namaField.setText("");
        levelCombo.getSelectionModel().clearSelection();
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        namaField.setDisable(true);
        levelCombo.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status="";
        setTable();
    }
    public void selectUser(User u){
        if(u!=null){
            status="update";
            otoritas.clear();
            otoritas.addAll(u.getOtoritas());
            usernameField.setText(u.getKodeUser());
            passwordField.setText(u.getPassword());
            namaField.setText(u.getNama());
            levelCombo.getSelectionModel().select(u.getLevel());
            usernameField.setDisable(true);
            passwordField.setDisable(false);
            namaField.setDisable(false);
            levelCombo.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            setTable();
        }
    }
    public void newUser(){
        status = "new";
        usernameField.setText("");
        passwordField.setText("");
        namaField.setText("");
        levelCombo.getSelectionModel().clearSelection();
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        namaField.setDisable(false);
        levelCombo.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        setTable();
    }
    public void saveUser(){
        if(usernameField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "User masih kosong");
        }else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        User user = new User();
                        user.setKodeUser(usernameField.getText());
                        user.setPassword(passwordField.getText());
                        user.setNama(namaField.getText());
                        user.setLevel(levelCombo.getSelectionModel().getSelectedItem());
                        user.setStatus("true");
                        List<Otoritas> listOtoritas = new ArrayList<>();
                        for(TreeItem<Otoritas> head : otoritasTable.getRoot().getChildren()){
                            Otoritas o = head.getValue();
                            o.setKodeUser(usernameField.getText());
                            listOtoritas.add(o);
                            for(TreeItem<Otoritas> child : head.getChildren()){
                                Otoritas o2 = child.getValue();
                                o2.setKodeUser(usernameField.getText());
                                listOtoritas.add(o2);
                            }
                        }
                        user.setOtoritas(listOtoritas);
                        
                        if(status.equals("update"))
                            return Service.updateUser(con, user);
                        else if(status.equals("new"))
                            return Service.newUser(con, user);
                        else
                            return "false";
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                mainApp.closeLoading();
                getUser();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
                    reset();
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }
    public void delete(User user){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete user "+user.getKodeUser()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection con = Koneksi.getConnection()){
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
                if(message.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil dihapus");
                }else
                    mainApp.showMessage(Modality.NONE, "Failed", message);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    
}
