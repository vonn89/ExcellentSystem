/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class KartuStokRosokCabangController  {

    @FXML private TableView<StokRosokCabang> stokBarangTable;
    @FXML private TableColumn<StokRosokCabang, String> tanggalColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratAwalColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenAwalColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiAwalColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratMasukColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenMasukColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiMasukColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenKeluarColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiKeluarColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratAkhirColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenAkhirColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiAkhirColumn;
    
    @FXML private Label kodeCabangLabel;
    @FXML private Label kodeGudangLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokRosokCabang> allBarang = FXCollections.observableArrayList();
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglNormal));
        
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAwalProperty());
        beratPersenAwalColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> getTableCell(rp));
        
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratPersenMasukColumn.setCellFactory(col -> getTableCell(gr));
        nilaiMasukColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiMasukProperty());
        nilaiMasukColumn.setCellFactory(col -> getTableCell(rp));
        
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratPersenKeluarColumn.setCellFactory(col -> getTableCell(gr));
        nilaiKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiKeluarProperty());
        nilaiKeluarColumn.setCellFactory(col -> getTableCell(rp));
        
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAkhirProperty());
        beratPersenAkhirColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> getTableCell(rp));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokRosokCabang> row = new TableRow<StokRosokCabang>() {
                @Override
                public void updateItem(StokRosokCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        stokBarangTable.setItems(allBarang);
    }    
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setData(String kodeCabang, String kodeGudang){
        kodeCabangLabel.setText(kodeCabang);
        kodeGudangLabel.setText(kodeGudang);
        getBarang();
    }
    @FXML
    private void getBarang(){
        Task<List<StokRosokCabang>> task = new Task<List<StokRosokCabang>>() {
            @Override 
            public List<StokRosokCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String kodeCabang = kodeCabangLabel.getText();
                    String kodeGudang = kodeGudangLabel.getText();
                    List<StokRosokCabang> listStokBarang = StokRosokCabangDAO.getAllByDateAndCabangAndGudang(conPusat,
                        tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, kodeGudang);
                    listStokBarang.sort(Comparator.comparing(StokRosokCabang::getTanggal));
                    return listStokBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allBarang.clear();
                allBarang.addAll(task.getValue());
                stokBarangTable.refresh();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
