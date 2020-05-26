/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailStokBarcodeCabangController  {

    @FXML private TableView<StokBarangCabang> stokBarangTable;
    @FXML private TableColumn<StokBarangCabang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarangCabang, String> namaBarangColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAkhirColumn;
    
    @FXML private Label tanggalLabel;
    @FXML private Label kodeCabangLabel;
    @FXML private Label kodeGudangLabel;
    @FXML private Label kodeJenisLabel;
    @FXML private TextField searchField;
    
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarangCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().namaBarangProperty());
        
        stokAwalColumn.setCellValueFactory(cellData -> cellData.getValue().stokAwalProperty());
        stokAwalColumn.setCellFactory(col -> getTableCell(rp));
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        
        stokMasukColumn.setCellValueFactory(cellData -> cellData.getValue().stokMasukProperty());
        stokMasukColumn.setCellFactory(col -> getTableCell(rp));
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        
        stokKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().stokKeluarProperty());
        stokKeluarColumn.setCellFactory(col -> getTableCell(rp));
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        
        stokAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        stokAkhirColumn.setCellFactory(col -> getTableCell(rp));
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            stokBarangTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokBarangCabang> row = new TableRow<StokBarangCabang>() {
                @Override
                public void updateItem(StokBarangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            stokBarangTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(kartu);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends StokBarangCabang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
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
    public void setBarang(String tanggal, String kodeCabang, String kodeGudang, String kodeJenis){
        Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
            @Override 
            public List<StokBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<StokBarangCabang> listStokBarang = StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(
                            conPusat, tanggal, kodeCabang, kodeGudang, "%", kodeJenis, "%");
                    for(StokBarangCabang s : listStokBarang){
                        s.setBarangCabang(BarangCabangDAO.get(conPusat, s.getKodeBarcode()));
                    }
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
                tanggalLabel.setText(tglNormal.format(tglBarang.parse(tanggal)));
                kodeCabangLabel.setText(kodeCabang);
                kodeGudangLabel.setText(kodeGudang);
                kodeJenisLabel.setText(kodeJenis);
                allBarang.clear();
                allBarang.addAll(task.getValue());
                stokBarangTable.refresh();
            }catch(Exception ex){
                ex.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchBarang() {
        try{
            filterData.clear();
            for (StokBarangCabang b : allBarang) {
                if(searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(b);
                else if(checkColumn(b.getBarangCabang().getKodeKategori())|| 
                        checkColumn(b.getBarangCabang().getKodeBarcode())|| 
                        checkColumn(b.getBarangCabang().getKodeBarang())|| 
                        checkColumn(tglLengkap.format(tglSql.parse(b.getBarangCabang().getInputDate())))|| 
                        checkColumn(b.getBarangCabang().getInputBy())|| 
                        checkColumn(b.getBarangCabang().getKadar())|| 
                        checkColumn(b.getBarangCabang().getKodeIntern())|| 
                        checkColumn(b.getBarangCabang().getNamaBarang())|| 
                        checkColumn(b.getBarangCabang().getAsalBarang())|| 
                        checkColumn(b.getBarangCabang().getKodeJenis()))
                    filterData.add(b);
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void detailBarang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s.getBarangCabang());
    }
    private void kartuStok(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/KartuStokBarcodeCabang.fxml");
        KartuStokBarcodeCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarcode(s.getKodeCabang(), s.getKodeGudang(), s.getKodeBarcode());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
