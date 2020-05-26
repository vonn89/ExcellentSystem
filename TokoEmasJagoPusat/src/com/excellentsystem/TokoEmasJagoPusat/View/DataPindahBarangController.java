/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPindahBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PindahBarangController;
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
public class DataPindahBarangController  {

    @FXML private TableView<PindahHead> pindahBarangTable;
    @FXML private TableColumn<PindahHead, String> noPindahColumn;
    @FXML private TableColumn<PindahHead, String> tglPindahColumn;
    @FXML private TableColumn<PindahHead, String> kodeCabangColumn;
    @FXML private TableColumn<PindahHead, Number> totalQtyColumn;
    @FXML private TableColumn<PindahHead, Number> totalBeratColumn;
    @FXML private TableColumn<PindahHead, String> userPindahColumn;
    @FXML private TableColumn<PindahHead, String> tglTerimaColumn;
    @FXML private TableColumn<PindahHead, String> salesTerimaColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    @FXML private ComboBox<String> statusCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    private Main mainApp;   
    private ObservableList<PindahHead> allPindahBarang = FXCollections.observableArrayList();
    private ObservableList<PindahHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPindahColumn.setCellValueFactory(cellData -> cellData.getValue().noPindahProperty());
        tglPindahColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPindah())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPindahColumn.setComparator(Function.sortDate(tglLengkap));
        tglTerimaColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglTerimaColumn.setComparator(Function.sortDate(tglLengkap));
        userPindahColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        salesTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().userTerimaProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Pindah Barang");
        addNew.setOnAction((ActionEvent e) -> {
            newPindahBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPindahBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pindahBarangTable.setContextMenu(rowMenu);
        pindahBarangTable.setRowFactory(table -> {
            TableRow<PindahHead> row = new TableRow<PindahHead>() {
                @Override
                public void updateItem(PindahHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pindah Barang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPindahBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Pindah Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPindahBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pindah Barang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPindahBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPindahBarang();
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
                        detailPindahBarang(row.getItem());
                }
            });
            return row;
        });
        allPindahBarang.addListener((ListChangeListener.Change<? extends PindahHead> change) -> {
            searchPindahBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPindahBarang();
        });
        filterData.addAll(allPindahBarang);
        pindahBarangTable.setItems(filterData);
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
            
            ObservableList<String> allStatus = FXCollections.observableArrayList();
            allStatus.clear();
            allStatus.add("Semua");
            allStatus.add("Belum Diterima");
            allStatus.add("Sudah Diterima");
            statusCombo.setItems(allStatus);
            statusCombo.getSelectionModel().select("Semua");
            getPindahBarang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getPindahBarang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null && statusCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<PindahHead>> task = new Task<List<PindahHead>>() {
                @Override 
                public List<PindahHead> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String kodeCabang = "%";
                        if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                        String statusTerima = "%";
                        if(statusCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            statusTerima = "%";
                        else if(statusCombo.getSelectionModel().getSelectedItem().equals("Sudah Diterima"))
                            statusTerima = "true";
                        else if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Diterima"))
                            statusTerima = "false";
                        return PindahHeadDAO.getAllByDateAndCabangAndStatusTerimaAndStatusBatal(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, statusTerima, "false");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPindahBarang.clear();
                allPindahBarang.addAll(task.getValue());
                pindahBarangTable.refresh();
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
    private void searchPindahBarang() {
        try{
            filterData.clear();
            for (PindahHead t : allPindahBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(t);
                else{
                    if(checkColumn(t.getNoPindah())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglPindah())))||
                        checkColumn(t.getKodeUser())||
                        checkColumn(tglLengkap.format(tglSql.parse(t.getTglTerima())))||
                        checkColumn(t.getUserTerima())||
                        checkColumn(t.getKodeCabang())||
                        checkColumn(rp.format(t.getTotalQty()))||
                        checkColumn(gr.format(t.getTotalBerat())))
                        filterData.add(t);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        for(PindahHead p : filterData){
            totalQty = totalQty + p.getTotalQty();
            totalBerat = totalBerat + p.getTotalBerat();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }
    private void newPindahBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PindahBarang.fxml");
        PindahBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.cabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.gudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.listPindahDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Data pindah barang masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            PindahHead p = new PindahHead();
                            p.setNoPindah(PindahHeadDAO.getId(conPusat));
                            p.setTglPindah(Function.getSystemDate());
                            p.setKodeCabang(controller.cabangCombo.getSelectionModel().getSelectedItem());
                            p.setKodeGudang(controller.cabangCombo.getSelectionModel().getSelectedItem()+"-"+controller.gudangCombo.getSelectionModel().getSelectedItem());
                            p.setTotalQty(Integer.parseInt(controller.totalQtyLabel.getText().replaceAll(",", "")));
                            p.setTotalBerat(Double.parseDouble(controller.totalBeratLabel.getText().replaceAll(",", "")));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatusTerima("false");
                            p.setTglTerima("2000-01-01 00:00:00");
                            p.setUserTerima("");
                            p.setStatusBatal("false");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            p.setListPindahDetail(controller.listPindahDetail);
                            return Service.savePindahBarang(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPindahBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pindah barang berhasil disimpan");
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
    private void detailPindahBarang(PindahHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPindahBarang.fxml");
        DetailPindahBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPindahBarang(p);
    }
    private void batalPindahBarang(PindahHead p){
        if(p.getStatusTerima().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Pindah barang tidak dapat dibatal, karena sudah diterima cabang");
        }else if(p.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Pindah barang tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal pindah barang "+p.getNoPindah()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            p.setStatusBatal("true");
                            p.setTglBatal(Function.getSystemDate());
                            p.setUserBatal(user.getKodeUser());
                            return Service.saveBatalPindahBarang(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPindahBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Pindah barang berhasil dibatal");
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
