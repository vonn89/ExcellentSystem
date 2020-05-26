/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailTerimaBalenanCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.TerimaBalenanCabangController;
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
public class DataTerimaBalenanCabangController {

    @FXML private TableView<AmbilBarangHead> ambilBarangTable;
    @FXML private TableColumn<AmbilBarangHead, String> noAmbilColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglTerimaColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglAmbilColumn;
    @FXML private TableColumn<AmbilBarangHead, String> kodeCabangColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglPembelianMulaiColumn;
    @FXML private TableColumn<AmbilBarangHead, String> tglPembelianAkhirColumn;
    @FXML private TableColumn<AmbilBarangHead, String> userTerimaColumn;
    @FXML private TableColumn<AmbilBarangHead, String> kodeUserColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalQtyColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratKotorColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratBersihColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalBeratPersenColumn;
    @FXML private TableColumn<AmbilBarangHead, Number> totalPembelianColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalPembelianLabel;
    private Main mainApp;   
    private ObservableList<AmbilBarangHead> allAmbilBarang = FXCollections.observableArrayList();
    private ObservableList<AmbilBarangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noAmbilColumn.setCellValueFactory(cellData -> cellData.getValue().noAmbilBarangProperty());
        tglTerimaColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglTerima())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglTerimaColumn.setComparator(Function.sortDate(tglLengkap));
        tglAmbilColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglAmbilBarang())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglAmbilColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        tglPembelianMulaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglPembelianMulai())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianMulaiColumn.setComparator(Function.sortDate(tglNormal));
        tglPembelianAkhirColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTglPembelianAkhir())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianAkhirColumn.setComparator(Function.sortDate(tglNormal));
        userTerimaColumn.setCellValueFactory(cellData -> cellData.getValue().userTerimaProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratKotorProperty());
        totalBeratKotorColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratBersihProperty());
        totalBeratBersihColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratPersenProperty());
        totalBeratPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> getTableCell(rp));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Terima Balenan Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newTerimaBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getTerimaBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        ambilBarangTable.setContextMenu(rowMenu);
        ambilBarangTable.setRowFactory(table -> {
            TableRow<AmbilBarangHead> row = new TableRow<AmbilBarangHead>() {
                @Override
                public void updateItem(AmbilBarangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Terima Balenan Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newTerimaBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Terima Balenan Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailTerimaBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Balenan Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalTerimaBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getTerimaBarang();
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
                        detailTerimaBarang(row.getItem());
                }
            });
            return row;
        });
        allAmbilBarang.addListener((ListChangeListener.Change<? extends AmbilBarangHead> change) -> {
            searchTerimaBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchTerimaBarang();
        });
        filterData.addAll(allAmbilBarang);
        ambilBarangTable.setItems(filterData);
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
            getTerimaBarang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getTerimaBarang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<AmbilBarangHead>> task = new Task<List<AmbilBarangHead>>() {
                @Override 
                public List<AmbilBarangHead> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String kodeCabang = "%";
                        if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                        return AmbilBarangHeadDAO.getAllByDateAndCabangAndStatusTerimaAndStatusBatal(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, "true", "false");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allAmbilBarang.clear();
                allAmbilBarang.addAll(task.getValue());
                ambilBarangTable.refresh();
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
    private void searchTerimaBarang() {
        try{
            filterData.clear();
            for (AmbilBarangHead a : allAmbilBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoAmbilBarang())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglTerima())))||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglAmbilBarang())))||
                        checkColumn(tglNormal.format(tglBarang.parse(a.getTglPembelianMulai())))||
                        checkColumn(tglNormal.format(tglBarang.parse(a.getTglPembelianAkhir())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getUserTerima())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(rp.format(a.getTotalQty()))||
                        checkColumn(gr.format(a.getTotalBeratKotor()))||
                        checkColumn(gr.format(a.getTotalBeratBersih()))||
                        checkColumn(gr.format(a.getTotalBeratPersen()))||
                        checkColumn(rp.format(a.getTotalPembelian())))
                        filterData.add(a);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalBeratPersen = 0;
        double totalPembelian = 0;
        for(AmbilBarangHead p : filterData){
            totalQty = totalQty + p.getTotalQty();
            totalBeratKotor = totalBeratKotor + p.getTotalBeratKotor();
            totalBeratBersih = totalBeratBersih + p.getTotalBeratBersih();
            totalBeratPersen = totalBeratPersen + p.getTotalBeratPersen();
            totalPembelian = totalPembelian + p.getTotalPembelian();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratKotorLabel.setText(gr.format(totalBeratKotor));
        totalBeratBersihLabel.setText(gr.format(totalBeratBersih));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
        totalPembelianLabel.setText(rp.format(totalPembelian));
    }
    private void newTerimaBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/TerimaBalenanCabang.fxml");
        TerimaBalenanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.ambilBarangHead==null)
                mainApp.showMessage(Modality.NONE, "Warning", "No ambil barang yang dimasukan masih kosong atau salah");
            else if(controller.ambilBarangHead.getStatusTerima().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena no ambil sudah pernah diterima");
            else if(controller.ambilBarangHead.getStatusBatal().equals("true"))
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena no ambil sudah dibatalkan");
            else if(controller.ambilBarangHead.getStatusTerima().equals("false") && 
                    controller.ambilBarangHead.getStatusBatal().equals("false")){
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            return Service.saveTerimaBalenanCabang(conPusat, controller.ambilBarangHead);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTerimaBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Terima balenan cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }else 
                mainApp.showMessage(Modality.NONE, "Warning", "Tidak dapat diterima, karena status terima salah");
        });
    }
    private void detailTerimaBarang(AmbilBarangHead a){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailTerimaBalenanCabang.fxml");
        DetailTerimaBalenanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setAmbilBarang(a);
    }
    private void batalTerimaBarang(AmbilBarangHead a){
        if(a.getStatusTerima().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Terima balenan cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal terima balenan cabang "+a.getNoAmbilBarang()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            a.setStatusTerima("false");
                            a.setTglTerima("2000-01-01 00:00:00");
                            a.setUserTerima("");
                            return Service.saveBatalTerimaBalenanCabang(conPusat, a);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getTerimaBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Terima balenan cabang berhasil dibatal");
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
