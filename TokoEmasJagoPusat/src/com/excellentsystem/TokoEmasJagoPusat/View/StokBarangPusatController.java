/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.KartuStokBarangPusatController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.TambahBarangPusatController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
public class StokBarangPusatController  {

    @FXML private TableView<StokBarang> stokBarangTable;
    @FXML private TableColumn<StokBarang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarang, Number> beratPersenAwalColumn;
    @FXML private TableColumn<StokBarang, Number> nilaiAwalColumn;
    @FXML private TableColumn<StokBarang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarang, Number> beratPersenMasukColumn;
    @FXML private TableColumn<StokBarang, Number> nilaiMasukColumn;
    @FXML private TableColumn<StokBarang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> beratPersenKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> nilaiKeluarColumn;
    @FXML private TableColumn<StokBarang, Number> beratAkhirColumn;
    @FXML private TableColumn<StokBarang, Number> beratPersenAkhirColumn;
    @FXML private TableColumn<StokBarang, Number> nilaiAkhirColumn;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> kategoriCombo;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalNilaiLabel;
    @FXML private DatePicker tglPicker;
    private Main mainApp;   
    private final ObservableList<String> allKategori = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSubKategoriProperty());
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAwalProperty());
        beratPersenAwalColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> getTableCell(rp));
        
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenMasukProperty());
        beratPersenMasukColumn.setCellFactory(col -> getTableCell(gr));
        nilaiMasukColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiMasukProperty());
        nilaiMasukColumn.setCellFactory(col -> getTableCell(rp));
        
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenKeluarProperty());
        beratPersenKeluarColumn.setCellFactory(col -> getTableCell(gr));
        nilaiKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiKeluarProperty());
        nilaiKeluarColumn.setCellFactory(col -> getTableCell(rp));
        
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAkhirProperty());
        beratPersenAkhirColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> getTableCell(rp));
        
        
        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(
                LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem tambah = new MenuItem("Tambah Barang Pusat");
        tambah.setOnAction((ActionEvent e)->{
            tambahBarangPusat(null);
        });
        MenuItem ambil = new MenuItem("Ambil Barang Pusat");
        ambil.setOnAction((ActionEvent e)->{
            ambilBarangPusat(null);
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().add(tambah);
        rowMenu.getItems().add(ambil);
        rowMenu.getItems().add(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokBarang> row = new TableRow<StokBarang>() {
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem tambah = new MenuItem("Tambah Barang Pusat");
                        tambah.setOnAction((ActionEvent e)->{
                            tambahBarangPusat(item);
                        });
                        MenuItem ambil = new MenuItem("Ambil Barang Pusat");
                        ambil.setOnAction((ActionEvent e)->{
                            ambilBarangPusat(item);
                        });
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(tambah);
                        rowMenu.getItems().add(ambil);
                        rowMenu.getItems().add(kartu);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        kartuStok(row.getItem());
                }
            });
            return row;
        });
        
        allBarang.addListener((ListChangeListener.Change<? extends StokBarang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        Task<List<String>> task = new Task<List<String>>() {
            @Override 
            public List<String> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<Kategori> listKategori = KategoriDAO.getAll(conPusat);
                    List<String> allKategori = new ArrayList<>();
                    allKategori.add("Semua");
                    for(Kategori k : listKategori){
                        allKategori.add(k.getKodeKategori());
                    }
                    return allKategori;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allKategori.clear();
            allKategori.addAll(task.getValue());
            kategoriCombo.setItems(allKategori);
            kategoriCombo.getSelectionModel().selectFirst();
            getBarang();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    } 
    @FXML
    private void getBarang(){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String kategori = "%";
                    if(!kategoriCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        kategori = kategoriCombo.getSelectionModel().getSelectedItem();
                    System.out.println(kategori);
                    List<StokBarang> listBarang = StokBarangDAO.getAllByTanggalAndKategori(conPusat, tglPicker.getValue().toString(), kategori);
                    listBarang.sort(Comparator.comparing(StokBarang::getKodeSubKategori));
                    return listBarang;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBarang.clear();
            allBarang.addAll(task.getValue());
            stokBarangTable.refresh();
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
        filterData.clear();
        for (StokBarang b : allBarang) {
            if(searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(b);
            else if(checkColumn(b.getKodeKategori())|| 
                    checkColumn(b.getKodeSubKategori()))
                filterData.add(b);
        }
        hitungTotal();
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalBeratPersen = 0;
        double totalNilai = 0;
        for(StokBarang b : filterData){
            totalBerat = totalBerat + b.getBeratAkhir();
            totalBeratPersen = totalBeratPersen + b.getBeratPersenAkhir();
            totalNilai = totalNilai + b.getNilaiAkhir();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
        totalNilaiLabel.setText(rp.format(totalNilai));
    }
    private void kartuStok(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/KartuStokBarangPusat.fxml");
        KartuStokBarangPusatController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setData(s.getKodeSubKategori());
    }
    private void tambahBarangPusat(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/TambahBarangPusat.fxml");
        TambahBarangPusatController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setData("Tambah Barang Pusat", s);
        controller.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>(){
                @Override 
                public String call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        SubKategori subKategori = SubKategoriDAO.get(conPusat, controller.kodeSubKategoriCombo.getSelectionModel().getSelectedItem());
                        Kategori kategori = KategoriDAO.get(conPusat, subKategori.getKodeKategori());
                        return Service.saveTambahBarang(conPusat, kategori.getKodeKategori(), controller.kodeSubKategoriCombo.getSelectionModel().getSelectedItem(),
                                Double.parseDouble(controller.beratField.getText().replaceAll(",", "")), 
                                Function.pembulatan(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))* kategori.getPersentaseEmas()/100),
                                Double.parseDouble(controller.nilaiPokokField.getText().replaceAll(",", "")));
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getBarang();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, child);
                    mainApp.showMessage(Modality.NONE, "Success", "Tambah barang berhasil disimpan");
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
    private void ambilBarangPusat(StokBarang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/TambahBarangPusat.fxml");
        TambahBarangPusatController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setData("Ambil Barang Pusat", s);
        controller.saveButton.setOnAction((event) -> {
            Task<String> task = new Task<String>(){
                @Override 
                public String call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        SubKategori subKategori = SubKategoriDAO.get(conPusat, controller.kodeSubKategoriCombo.getSelectionModel().getSelectedItem());
                        Kategori kategori = KategoriDAO.get(conPusat, subKategori.getKodeKategori());
                        return Service.saveAmbilBarang(conPusat, kategori.getKodeKategori(), controller.kodeSubKategoriCombo.getSelectionModel().getSelectedItem(),
                                Double.parseDouble(controller.beratField.getText().replaceAll(",", "")), 
                                Function.pembulatan(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))* kategori.getPersentaseEmas()/100),
                                Double.parseDouble(controller.nilaiPokokField.getText().replaceAll(",", "")));
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getBarang();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.closeDialog(mainApp.MainStage, child);
                    mainApp.showMessage(Modality.NONE, "Success", "Ambil barang berhasil disimpan");
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
