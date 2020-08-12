/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.KategoriKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.OtoritasDAO;
import com.excellentsystem.sentralindahperdana.DAO.OtoritasKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.UserDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.timeout;
import com.excellentsystem.sentralindahperdana.Model.KategoriKeuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.OtoritasKeuangan;
import com.excellentsystem.sentralindahperdana.Model.User;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataUserController  {

    @FXML private TableView<User> userTable;
    @FXML private TableColumn<User, String> usernameColumn;
    @FXML private TableColumn removeColumn;
    @FXML private TableView<Otoritas> otoritasTable;
    @FXML private TableColumn<Otoritas, String> jenisOtoritasColumn;
    @FXML private TableColumn<Otoritas, Boolean> statusOtoritasColumn;
    @FXML private TableView<OtoritasKeuangan> otoritasKeuanganTable;
    @FXML private TableColumn<OtoritasKeuangan, String> jenisOtoritasKeuanganColumn;
    @FXML private TableColumn<OtoritasKeuangan, Boolean> statusOtoritasKeuanganColumn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private CheckBox checkOtoritas;
    @FXML private CheckBox checkOtoritasKeuangan;
    private Main mainApp;
    private User user;
    private String status = "";
    private final ObservableList<User> allUser = FXCollections.observableArrayList();
    private final ObservableList<Otoritas> otoritas = FXCollections.observableArrayList();
    private final ObservableList<OtoritasKeuangan> otoritasKeuangan = FXCollections.observableArrayList();
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<User, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
                    btn.setGraphic(new ImageView(imageDecline));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        User u = (User)getTableView().getItems().get( getIndex() );
                        deleteUser(u);
                    });
                    setGraphic( btn );
                }
            }
        };
    }
    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        removeColumn.setCellValueFactory( new PropertyValueFactory<>(""));
        removeColumn.setCellFactory(getButtonCell());
        
        jenisOtoritasColumn.setCellValueFactory(cellData -> cellData.getValue().jenisProperty());
        statusOtoritasColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusOtoritasColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
                (Integer param) -> otoritasTable.getItems().get(param).statusProperty()));
        jenisOtoritasKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKeuanganProperty());
        statusOtoritasKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusOtoritasKeuanganColumn.setCellFactory(CheckBoxTableCell.forTableColumn(
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
            TableRow<User> row = new TableRow<User>(){
                @Override
                public void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New User");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Delete User");
                        hapus.setOnAction((ActionEvent event) -> {
                            deleteUser(item);
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
            if (keyEvent.getCode() == KeyCode.ENTER)  
                passwordField.requestFocus();
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        userTable.setItems(allUser);
        otoritasTable.setItems(otoritas);
        otoritasKeuanganTable.setItems(otoritasKeuangan);
        getUser();
    }    
    private void getUser(){
        Task<List<User>> task = new Task<List<User>>() {
            @Override 
            public List<User> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    List<User> allUser = UserDAO.getAllByStatus(con, "true");
                    List<Otoritas> allOtoritas = OtoritasDAO.getAll(con);
                    List<OtoritasKeuangan> allOtoritasKeuangan = OtoritasKeuanganDAO.getAll(con);
                    for(User u : allUser){
                        List<Otoritas> otoritas = new ArrayList<>();
                        for(Otoritas o : allOtoritas){
                            if(u.getUsername().equals(o.getKodeUser()))
                                otoritas.add(o);
                        }
                        List<OtoritasKeuangan> otoritasKeuangan = new ArrayList<>();
                        for(OtoritasKeuangan ok: allOtoritasKeuangan){
                            if(u.getUsername().equals(ok.getKodeUser()))
                                otoritasKeuangan.add(ok);
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
            otoritasKeuangan.clear();
            otoritas.clear();
            usernameField.setText("");
            passwordField.setText("");
            usernameField.setDisable(true);
            user=null;
            status="";
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void checkOtoritas(){
        for(Otoritas o : otoritas){
            o.setStatus(checkOtoritas.isSelected());
        }
        otoritasTable.refresh();
    }
    @FXML
    private void checkOtoritasKeuangan(){
        for(OtoritasKeuangan o : otoritasKeuangan){
            o.setStatus(checkOtoritasKeuangan.isSelected());
        }
        otoritasKeuanganTable.refresh();
    }
    private void selectUser(User value){
        user=null;
        usernameField.setDisable(true);
        status="";
        otoritas.clear();
        otoritasKeuangan.clear();
        if(value!=null){
            status="update";
            user = value;
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            otoritas.addAll(user.getOtoritas());
            otoritasKeuangan.addAll(user.getOtoritasKeuangan());
        }
    }    
    private void newUser(){
        Task<List<KategoriKeuangan>> task = new Task<List<KategoriKeuangan>>() {
            @Override 
            public List<KategoriKeuangan> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = Koneksi.getConnection()){
                    return KategoriKeuanganDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();

            status = "new";
            user = new User();
            usernameField.setDisable(false);
            usernameField.setText("");
            passwordField.setText("");

            List<String> allOtoritas = new ArrayList<>();
            allOtoritas.add("Data Customer");
            allOtoritas.add("Add New Customer");
            allOtoritas.add("Edit Customer");
            allOtoritas.add("Delete Customer");
            allOtoritas.add("Set Limit Hutang Customer");
            
            allOtoritas.add("Data Supplier");
            allOtoritas.add("Add New Supplier");
            allOtoritas.add("Edit Supplier");
            allOtoritas.add("Delete Supplier");
            
            allOtoritas.add("Data Pegawai");
            allOtoritas.add("Add New Pegawai");
            allOtoritas.add("Edit Pegawai");
            allOtoritas.add("Delete Pegawai");
            
            allOtoritas.add("Data Barang");
            allOtoritas.add("Add New Barang");
            allOtoritas.add("Edit Barang");
            allOtoritas.add("Delete Barang");
            
            allOtoritas.add("Data Pekerjaan");
            allOtoritas.add("Add New Pekerjaan");
            allOtoritas.add("Edit Pekerjaan");
            allOtoritas.add("Delete Pekerjaan");
            
            allOtoritas.add("Pemesanan");
            allOtoritas.add("Add New Pemesanan");
            allOtoritas.add("Lihat Detail Pemesanan");
            allOtoritas.add("Batal Pemesanan");
            allOtoritas.add("Proyek Selesai");
            allOtoritas.add("Terima Pembayaran Down Payment");
            allOtoritas.add("Lihat Detail Terima Pembayaran Down Payment");
            allOtoritas.add("Batal Terima Pembayaran Down Payment");
            allOtoritas.add("Print Order Confirmation");
            
            allOtoritas.add("Pengiriman Barang");
            allOtoritas.add("Add New Pengiriman");
            allOtoritas.add("Lihat Detail Pengiriman");
            allOtoritas.add("Batal Pengiriman");
            allOtoritas.add("Print Surat Jalan");
            
            allOtoritas.add("Retur Barang");
            allOtoritas.add("Add New Retur");
            allOtoritas.add("Lihat Detail Retur");
            allOtoritas.add("Batal Retur");
            
            allOtoritas.add("Beban Penjualan");
            allOtoritas.add("Add New Beban Penjualan");
            allOtoritas.add("Lihat Detail Beban Penjualan");
            allOtoritas.add("Batal Beban Penjualan");
            
            allOtoritas.add("Penjualan");
            allOtoritas.add("Lihat Detail Penjualan");
            allOtoritas.add("Batal Proyek Selesai");
            allOtoritas.add("Terima Pembayaran Penjualan");
            allOtoritas.add("Lihat Detail Terima Pembayaran Penjualan");
            allOtoritas.add("Batal Terima Pembayaran Penjualan");
            allOtoritas.add("Print Invoice");
            allOtoritas.add("Set Jatuh Tempo Penjualan");
            
            allOtoritas.add("Pembelian");
            allOtoritas.add("Add New Pembelian");
            allOtoritas.add("Lihat Detail Pembelian");
            allOtoritas.add("Batal Pembelian");
            allOtoritas.add("Pembayaran Pembelian");
            allOtoritas.add("Lihat Detail Pembayaran Pembelian");
            allOtoritas.add("Batal Pembayaran Pembelian");
            allOtoritas.add("Set Jatuh Tempo Pembelian");
            
            allOtoritas.add("Retur Pembelian");
            allOtoritas.add("Add New Retur Pembelian");
            allOtoritas.add("Lihat Detail Retur Pembelian");
            allOtoritas.add("Batal Retur Pembelian");
            
            allOtoritas.add("Data Keuangan");
            allOtoritas.add("Add New Transaksi");
            allOtoritas.add("Transfer Keuangan");
            allOtoritas.add("Batal Transaksi");
            
            allOtoritas.add("Data Hutang");
            allOtoritas.add("Add New Hutang");
            allOtoritas.add("Lihat Detail Hutang");
            allOtoritas.add("Pembayaran Hutang");
            allOtoritas.add("Batal Pembayaran Hutang");
            allOtoritas.add("Set Jatuh Tempo Hutang");
            
            allOtoritas.add("Data Piutang");
            allOtoritas.add("Add New Piutang");
            allOtoritas.add("Lihat Detail Piutang");
            allOtoritas.add("Pembayaran Piutang");
            allOtoritas.add("Batal Pembayaran Piutang");
            allOtoritas.add("Set Jatuh Tempo Piutang");
            
            allOtoritas.add("Data Modal");
            allOtoritas.add("Tambah Modal");
            allOtoritas.add("Ambil Modal");
            
            allOtoritas.add("Data Aset Tetap");
            allOtoritas.add("Pembelian Aset Tetap");
            allOtoritas.add("Penjualan Aset Tetap");
            allOtoritas.add("Lihat Detail Aset Tetap");
            
            allOtoritas.add("Laporan Barang");
            allOtoritas.add("Laporan Penjualan");
            allOtoritas.add("Laporan Pembelian");
            allOtoritas.add("Laporan Keuangan");
            allOtoritas.add("Laporan Managerial");
            allOtoritas.add("Print Laporan");
            
            allOtoritas.add("Data User");
            allOtoritas.add("Kategori Barang");
            allOtoritas.add("Kategori Pekerjaan");
            allOtoritas.add("Kategori Keuangan");
            allOtoritas.add("Kategori Transaksi");
            allOtoritas.add("Kategori Hutang");
            allOtoritas.add("Kategori Piutang");
            List<Otoritas> tempOtoritas = new ArrayList<>();
            for(String jns : allOtoritas){
                Otoritas temp = new Otoritas();
                temp.setJenis(jns);
                temp.setStatus(checkOtoritas.isSelected());
                tempOtoritas.add(temp);
            }
            otoritas.clear();
            otoritas.addAll(tempOtoritas);

            List<OtoritasKeuangan> ok = new ArrayList<>();
            for(KategoriKeuangan k : task.getValue()){
                OtoritasKeuangan temp = new OtoritasKeuangan();
                temp.setKodeUser(user.getUsername());
                temp.setKodeKeuangan(k.getKodeKeuangan());
                temp.setStatus(checkOtoritasKeuangan.isSelected());
                ok.add(temp);
            }
            otoritasKeuangan.clear();
            otoritasKeuangan.addAll(ok);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }    
    @FXML
    private void saveUser() {
        if(status.equals("update")){
            if(user==null || usernameField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "User belum dipilih");
            }else{
                MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                        "Update user "+user.getUsername()+" ?");
                controller.OK.setOnAction((ActionEvent evt) -> {
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                user.setPassword(passwordField.getText());
                                for(Otoritas temp : otoritas){
                                    temp.setKodeUser(user.getUsername());
                                }
                                user.setOtoritas(otoritas);
                                for(OtoritasKeuangan temp : otoritasKeuangan){
                                    temp.setKodeUser(user.getUsername());
                                }
                                user.setOtoritasKeuangan(otoritasKeuangan);
                                return Service.saveUpdateUser(con, user);
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
                            mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
                        }else{
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
        }else if(status.equals("new")){
            if(user==null || usernameField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "username masih kosong");
            }else{
                MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                        "Save new user "+usernameField.getText()+" ?");
                controller.OK.setOnAction((ActionEvent ev) -> {
                    Task<String> task = new Task<String>() {
                        @Override 
                        public String call() throws Exception{
                            Thread.sleep(timeout);
                            try(Connection con = Koneksi.getConnection()){
                                String statusUser = "true";
                                List<User> allUser = UserDAO.getAllByStatus(con, "%");
                                for(User u : allUser){
                                    if(u.getUsername().equals(usernameField.getText()))
                                        statusUser = "Username sudah pernah terdaftar";
                                }
                                if("true".equals(statusUser)){
                                    user.setUsername(usernameField.getText());
                                    user.setPassword(passwordField.getText());
                                    user.setStatus("true");
                                    
                                    for(Otoritas o : otoritas){
                                        o.setKodeUser(user.getUsername());
                                    }
                                    user.setOtoritas(otoritas);
                                    
                                    for(OtoritasKeuangan o : otoritasKeuangan){
                                        o.setKodeUser(user.getUsername());
                                    }
                                    user.setOtoritasKeuangan(otoritasKeuangan);
                                    
                                    return Service.saveNewUser(con, user);
                                }else
                                    return statusUser;
                                
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
                            mainApp.showMessage(Modality.NONE, "Success", "Data user berhasil disimpan");
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
    }    
    private void deleteUser(User user){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Delete user "+user.getUsername()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
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
