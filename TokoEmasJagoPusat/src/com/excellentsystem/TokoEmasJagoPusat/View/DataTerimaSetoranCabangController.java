/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SetoranCabangDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.SetoranCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailTerimaSetoranCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.TerimaSetoranCabangController;
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
public class DataTerimaSetoranCabangController {

    @FXML private TableView<SetoranCabang> terimaSetoranTable;
    @FXML private TableColumn<SetoranCabang, String> noSetorColumn;
    @FXML private TableColumn<SetoranCabang, String> tglTerimaColumn;
    @FXML private TableColumn<SetoranCabang, String> userTerimaColumn;
    @FXML private TableColumn<SetoranCabang, String> tglSetorColumn;
    @FXML private TableColumn<SetoranCabang, String> userSetorColumn;
    @FXML private TableColumn<SetoranCabang, String> kodeCabangColumn;
    @FXML private TableColumn<SetoranCabang, String> tipeKasirColumn;
    @FXML private TableColumn<SetoranCabang, Number> jumlahRpColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    @FXML private ComboBox<String> tipeKasirCombo;
    
    @FXML private Label totalSetorLabel;
    private Main mainApp;   
    private ObservableList<SetoranCabang> allSetoran = FXCollections.observableArrayList();
    private ObservableList<SetoranCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noSetorColumn.setCellValueFactory(cellData -> cellData.getValue().noSetorProperty());
        tglTerimaColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglTerimaColumn.setComparator(Function.sortDate(tglLengkap));
        userTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().userTerimaProperty());
        tglSetorColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSetor())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglSetorColumn.setComparator(Function.sortDate(tglLengkap));
        userSetorColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
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
        MenuItem addNew = new MenuItem("New Terima Setoran Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newTerimaSetoran();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTerimaSetoran();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        terimaSetoranTable.setContextMenu(rowMenu);
        terimaSetoranTable.setRowFactory(table -> {
            TableRow<SetoranCabang> row = new TableRow<SetoranCabang>() {
                @Override
                public void updateItem(SetoranCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Terima Setoran Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newTerimaSetoran();
                        });
                        MenuItem detail = new MenuItem("Detail Terima Setoran Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailTerimaSetoran(item);
                        });
//                        MenuItem batal = new MenuItem("Batal Terima Setoran Cabang");
//                        batal.setOnAction((ActionEvent e) -> {
//                            batalTerimaSetoran(item);
//                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getTerimaSetoran();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
//                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailTerimaSetoran(row.getItem());
                }
            });
            return row;
        });
        allSetoran.addListener((ListChangeListener.Change<? extends SetoranCabang> change) -> {
            searchTerimaSetoran();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchTerimaSetoran();
        });
        filterData.addAll(allSetoran);
        terimaSetoranTable.setItems(filterData);
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
            getTerimaSetoran();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getTerimaSetoran(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<SetoranCabang>> task = new Task<List<SetoranCabang>>() {
                @Override 
                public List<SetoranCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String kodeCabang = "%";
                        if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                        String tipeKasir = "%";
                        if(!tipeKasirCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            tipeKasir = tipeKasirCombo.getSelectionModel().getSelectedItem();
                        return SetoranCabangDAO.getAllByTglTerimaAndCabangAndTipeKasir(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, tipeKasir);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allSetoran.clear();
                allSetoran.addAll(task.getValue());
                terimaSetoranTable.refresh();
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
    private void searchTerimaSetoran() {
        try{
            filterData.clear();
            for (SetoranCabang a : allSetoran) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoSetor())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglTerima())))||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglSetor())))||
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
        double totalSetor = 0;
        for(SetoranCabang p : filterData){
            totalSetor = totalSetor + p.getJumlahRp();
        }
        totalSetorLabel.setText(rp.format(totalSetor));
    }
    private void newTerimaSetoran(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TerimaSetoranCabang.fxml");
        TerimaSetoranCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.setoran==null)
                mainApp.showMessage(Modality.NONE, "Warning", "No setor belum di masukan");
            else if(controller.setoran.getStatusTerima().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena no setor sudah pernah diterima");
            else if(controller.setoran.getStatusBatal().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena no setor sudah dibatalkan");
            else if(controller.setoran.getStatusTerima().equals("false") && 
                    controller.setoran.getStatusBatal().equals("false")){
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            controller.setoran.setStatusTerima("true");
                            controller.setoran.setTglTerima(Function.getSystemDate());
                            controller.setoran.setUserTerima(user.getKodeUser());
                            return Service.saveTerimaSetoranCabang(conPusat, controller.setoran, controller.bayarHutangCheck.isSelected());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTerimaSetoran();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Terima setoran cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }else 
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena status setor salah");
        });
    }
    private void detailTerimaSetoran(SetoranCabang s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailTerimaSetoranCabang.fxml");
        DetailTerimaSetoranCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setSetor(s);
    }
    private void batalTerimaSetoran(SetoranCabang s){
//        if(s.getStatusTerima().equals("false")){
//            mainApp.showMessage(Modality.NONE, "Warning", "Terima setoran cabang tidak dapat dibatal, karena sudah pernah dibatal / belum pernah diterima");
//        }else{
//            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
//                    "Batal terima setoran cabang "+s.getNoSetor()+" ?");
//            x.OK.setOnAction((ActionEvent ex) -> {
//                mainApp.closeMessage();
//                
//                Task<String> task = new Task<String>() {
//                    @Override 
//                    public String call() throws Exception{
//                        try(Connection conPusat = KoneksiPusat.getConnection()){
//                            s.setStatusTerima("false");
//                            s.setTglTerima("2000-01-01 00:00:00");
//                            s.setUserTerima("");
//                            return Service.saveBatalTerimaSetoranCabang(conPusat, s);
//                        }
//                    }
//                };
//                task.setOnRunning((e) -> {
//                    mainApp.showLoadingScreen();
//                });
//                task.setOnSucceeded((e) -> {
//                    mainApp.closeLoading();
//                    getTerimaSetoran();
//                    String status = task.getValue();
//                    if(status.equals("true")){
//                        mainApp.showMessage(Modality.NONE, "Success", "Terima setoran cabang berhasil dibatal");
//                    }else
//                        mainApp.showMessage(Modality.NONE, "Failed", status);
//                });
//                task.setOnFailed((e) -> {
//                    mainApp.closeLoading();
//                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
//                });
//                new Thread(task).start();
//            });
//        }
    }
}
