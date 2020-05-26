/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TambahUangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.TambahUangCabangController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
 * @author Excellent
 */
public class DataTambahUangCabangController {

    @FXML private TableView<TambahUangCabang> tambahUangCabangTable;
    @FXML private TableColumn<TambahUangCabang, String> noTambahColumn;
    @FXML private TableColumn<TambahUangCabang, String> tglTambahColumn;
    @FXML private TableColumn<TambahUangCabang, String> kodeCabangColumn;
    @FXML private TableColumn<TambahUangCabang, String> tipeKasirColumn;
    @FXML private TableColumn<TambahUangCabang, Number> jumlahRpColumn;
    @FXML private TableColumn<TambahUangCabang, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    @FXML private ComboBox<String> tipeKasirCombo;
    
    @FXML private Label totalTambahUangLabel;
    private Main mainApp;   
    private ObservableList<TambahUangCabang> allTambahUang = FXCollections.observableArrayList();
    private ObservableList<TambahUangCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noTambahColumn.setCellValueFactory(cellData -> cellData.getValue().noTambahUangProperty());
        tglTambahColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTambah())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglTambahColumn.setComparator(Function.sortDate(tglLengkap));
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        tipeKasirColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKasirProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Tambah Uang Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newTambahUang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTambahUang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        tambahUangCabangTable.setContextMenu(rowMenu);
        tambahUangCabangTable.setRowFactory(table -> {
            TableRow<TambahUangCabang> row = new TableRow<TambahUangCabang>() {
                @Override
                public void updateItem(TambahUangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Tambah Uang Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newTambahUang();
                        });
                        MenuItem detail = new MenuItem("Detail Tambah Uang Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailTambahUang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Tambah Uang Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalTambahUang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getTambahUang();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailTambahUang(row.getItem());
                }
            });
            return row;
        });
        allTambahUang.addListener((ListChangeListener.Change<? extends TambahUangCabang> change) -> {
            searchTambahUang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchTambahUang();
        });
        filterData.addAll(allTambahUang);
        tambahUangCabangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = mainApp;
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            allCabang.clear();
            allCabang.add("Semua");
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.addAll(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            kodeCabangCombo.getSelectionModel().select("Semua");
            
            ObservableList<String> allTipeKasir = FXCollections.observableArrayList();
            allTipeKasir.add("Semua");
            allTipeKasir.add("Kasir");
            allTipeKasir.add("RR");
            tipeKasirCombo.setItems(allTipeKasir);
            tipeKasirCombo.getSelectionModel().select("Semua");
            getTambahUang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getTambahUang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<TambahUangCabang>> task = new Task<List<TambahUangCabang>>() {
                @Override 
                public List<TambahUangCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String kodeCabang = "%";
                        if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                        String tipeKasir = "%";
                        if(!tipeKasirCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            tipeKasir = tipeKasirCombo.getSelectionModel().getSelectedItem();
                        return TambahUangCabangDAO.getAllByDateAndCabangAndTipeKasirAndStatusTerimaAndStatusBatal(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, tipeKasir, "%","false");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allTambahUang.clear();
                allTambahUang.addAll(task.getValue());
                tambahUangCabangTable.refresh();
            });
            task.setOnFailed((e) -> {
                task.getException().printStackTrace();
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchTambahUang() {
        try{
            filterData.clear();
            for (TambahUangCabang a : allTambahUang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoTambahUang())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglTerima())))||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglTambah())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getTipeKasir())||
                        checkColumn(a.getUserTerima())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(rp.format(a.getJumlahRp())))
                        filterData.add(a);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double total = 0;
        for(TambahUangCabang p : filterData){
            total = total + p.getJumlahRp();
        }
        totalTambahUangLabel.setText(rp.format(total));
    }
    private void newTambahUang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TambahUangCabang.fxml");
        TambahUangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.newTambahUang();
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.tipeKasirCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe kasir belum dipilih");
            else if(Double.parseDouble(controller.jumlahRpField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            TambahUangCabang t = new TambahUangCabang();
                            t.setNoTambahUang(TambahUangCabangDAO.getId(conPusat));
                            t.setTglTambah(Function.getSystemDate());
                            t.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            t.setTipeKasir(controller.tipeKasirCombo.getSelectionModel().getSelectedItem());
                            t.setJumlahRp(Double.parseDouble(controller.jumlahRpField.getText().replaceAll(",", "")));
                            t.setKodeUser(user.getKodeUser());
                            t.setStatusTerima("false");
                            t.setTglTerima("2000-01-01 00:00:00");
                            t.setUserTerima("");
                            t.setStatusBatal("false");
                            t.setTglBatal("2000-01-01 00:00:00");
                            t.setUserBatal("");
                            return Service.saveTambahUangCabang(conPusat, t);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTambahUang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Tambah uang cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void detailTambahUang(TambahUangCabang t){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/TambahUangCabang.fxml");
        TambahUangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.detailTambahUang(t);
    }
    private void batalTambahUang(TambahUangCabang s){
        if(s.getStatusTerima().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Tambah uang cabang tidak dapat dibatal, karena sudah pernah diterima");
        }else if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Tambah uang cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal tambah uang cabang "+s.getNoTambahUang()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusBatal("true");
                            s.setTglBatal(Function.getSystemDate());
                            s.setUserBatal(user.getKodeUser());
                            return Service.saveBatalTambahUangCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTambahUang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Tambah uang cabang berhasil dibatal");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
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
