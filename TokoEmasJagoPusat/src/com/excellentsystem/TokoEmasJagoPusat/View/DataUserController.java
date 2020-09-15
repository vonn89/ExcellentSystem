/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.UserDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.decrypt;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.key;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.User;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    @FXML private TableColumn<User, String> usernameColumn;
    
    @FXML private TreeTableView<Otoritas> otoritasTable;
    @FXML private TreeTableColumn<Otoritas, String> jenisColumn;
    @FXML private TreeTableColumn<Otoritas, Boolean> statusColumn;
    
    @FXML private CheckBox checkOtoritas;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private String status = "";
    private final TreeItem<Otoritas> root = new TreeItem<>();
    private ObservableList<User> allUser = FXCollections.observableArrayList();
    private List<Otoritas> otoritas = new ArrayList<>();
    public void initialize() {
        usernameColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        jenisColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jenisProperty());
        
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
        final ContextMenu menu = new ContextMenu();
        MenuItem addNew = new MenuItem("Tambah User Baru");
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
                        MenuItem addNew = new MenuItem("Tambah User Baru");
                        addNew.setOnAction((ActionEvent event) -> {
                            newUser();
                        });
                        MenuItem hapus = new MenuItem("Hapus User");
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
    private void getUser(){
        Task<List<User>> task = new Task<List<User>>() {
            @Override 
            public List<User> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    List<User> allUser = UserDAO.getAll(con);
                    List<Otoritas> allOtoritas = OtoritasDAO.getAll(con);
                    for(User u : allUser){
                        u.setPassword(decrypt(u.getPassword(), key));
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
            
            root.getChildren().add(createTreeItem("Penjualan Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pembelian Supplier",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Retur Pembelian Supplier",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Stok Barang Pusat",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Data Barang Barcode",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Stok Barcode Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Stok Barang Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Barcode Barang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Pindah Barang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Revisi Barang Cabang",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Stok Balenan Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Stok SP Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Terima Balenan Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("SP Barang Cabang",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Stok Rosok & Ciok Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Rosok Barang Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Hancur Barang Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Lebur Rosok Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Penjualan Ciok Cabang",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Keuangan Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Terima Setoran Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Tambah Uang Cabang",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Laporan Barang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan SP",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Laporan Managerial",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Penjualan Fiktif Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Keuangan Poin",
                new ArrayList<>()
            ));
            
            root.getChildren().add(createTreeItem("Harga Emas",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data User",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data Pegawai",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data Kategori Barang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data Cabang",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Data Supplier",
                new ArrayList<>()
            ));
            root.getChildren().add(createTreeItem("Tutup Toko",
                new ArrayList<>()
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
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        status="";
        setTable();
    }
    private void selectUser(User u){
        if(u!=null){
            status="update";
            otoritas.clear();
            otoritas.addAll(u.getOtoritas());
            usernameField.setText(u.getKodeUser());
            passwordField.setText(u.getPassword());
            usernameField.setDisable(true);
            passwordField.setDisable(false);
            saveButton.setDisable(false);
            cancelButton.setDisable(false);
            setTable();
        }
    }    
    public void newUser(){
        status = "new";
        usernameField.setText("");
        passwordField.setText("");
        usernameField.setDisable(false);
        passwordField.setDisable(false);
        saveButton.setDisable(false);
        cancelButton.setDisable(false);

        setTable();
    }
    @FXML
    private void saveUser() {
        if(usernameField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "User belum dipilih");
        }else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        User user = new User();
                        user.setKodeUser(usernameField.getText());
                        user.setPassword(passwordField.getText());
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
                            return Service.saveUpdateUser(con, user);
                        else if(status.equals("new"))
                            return Service.saveNewUser(con, user);
                        else
                            return "false";
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
        }
    }    
    private void deleteUser(User user){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus user "+user.getKodeUser()+" ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
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
