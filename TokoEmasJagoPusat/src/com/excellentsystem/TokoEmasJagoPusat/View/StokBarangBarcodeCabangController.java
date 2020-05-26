/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailStokBarcodeCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.KartuStokBarcodeCabangController;
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
 * @author excellent
 */
public class StokBarangBarcodeCabangController {

    @FXML private TableView<StokBarangCabang> stokBarangTable;
    @FXML private TableColumn<StokBarangCabang, String> kodeCabangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeGudangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAkhirColumn;
    
    @FXML private ComboBox<String> cabangCombo;
    @FXML private ComboBox<String> gudangCombo;
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private DatePicker tglPicker;
    private Main mainApp;   
    private final ObservableList<String> allCabang = FXCollections.observableArrayList();
    private final ObservableList<String> allGudang = FXCollections.observableArrayList();
    private final ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarangCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
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
        
        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(
                LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
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
                        MenuItem detail = new MenuItem("Detail Stok Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(detail);
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
        
        allBarang.addListener((ListChangeListener.Change<? extends StokBarangCabang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
        cabangCombo.setItems(allCabang);
        gudangCombo.setItems(allGudang);
    }    
    public void setMainApp(Main mainApp){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = mainApp;
            allCabang.clear();
            allCabang.add("Semua");
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().selectFirst();

            allGudang.clear();
            allGudang.add("Semua");
            allGudang.add("New");
            allGudang.add("SP");
            gudangCombo.getSelectionModel().selectFirst();
            getBarang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getBarang(){
        if(gudangCombo.getSelectionModel().getSelectedItem()!=null && cabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
                @Override 
                public List<StokBarangCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String cabang = "%";
                        List<String> listGudang = new ArrayList<>();
                        if(!cabangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                            cabang = cabangCombo.getSelectionModel().getSelectedItem();
                            if(!gudangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                                listGudang.add(cabang+"-"+gudangCombo.getSelectionModel().getSelectedItem());
                            else{
                                listGudang.add(cabang+"-New");
                                listGudang.add(cabang+"-SP");
                            }
                        }else{
                            if(!gudangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                                for(String c : allCabang){
                                    if(!c.equals("Semua"))
                                        listGudang.add(c+"-"+gudangCombo.getSelectionModel().getSelectedItem());
                                }
                            }else{
                                for(String c : allCabang){
                                    if(!c.equals("Semua")){
                                        listGudang.add(c+"-New");
                                        listGudang.add(c+"-SP");
                                    }
                                }
                            }
                        }
                        List<StokBarangCabang>allBarang = StokBarangCabangDAO.getAllBarcodeByCabangAndListGudangAndKategoriAndJenisAndBarcode(conPusat, 
                            tglPicker.getValue().toString(), cabang, listGudang, "%", "%","%");
                        List<StokBarangCabang> listBarang = new ArrayList<>();
                        for(StokBarangCabang s : allBarang){
                            boolean status = true;
                            for(StokBarangCabang temp : listBarang){
                                if(s.getKodeCabang().equalsIgnoreCase(temp.getKodeCabang())&&
                                        s.getKodeGudang().equalsIgnoreCase(temp.getKodeGudang())&&
                                        s.getKodeJenis().equalsIgnoreCase(temp.getKodeJenis())){
                                    status = false;
                                    temp.setStokAwal(temp.getStokAwal()+s.getStokAwal());
                                    temp.setBeratAwal(temp.getBeratAwal()+s.getBeratAwal());
                                    temp.setStokMasuk(temp.getStokMasuk()+s.getStokMasuk());
                                    temp.setBeratMasuk(temp.getBeratMasuk()+s.getBeratMasuk());
                                    temp.setStokKeluar(temp.getStokKeluar()+s.getStokKeluar());
                                    temp.setBeratKeluar(temp.getBeratKeluar()+s.getBeratKeluar());
                                    temp.setStokAkhir(temp.getStokAkhir()+s.getStokAkhir());
                                    temp.setBeratAkhir(temp.getBeratAkhir()+s.getBeratAkhir());
                                }
                            }
                            if(status){
                                StokBarangCabang stokJenis = new StokBarangCabang();
                                stokJenis.setTanggal(s.getTanggal());
                                stokJenis.setKodeCabang(s.getKodeCabang());
                                stokJenis.setKodeGudang(s.getKodeGudang());
                                stokJenis.setKodeKategori(s.getKodeKategori());
                                stokJenis.setKodeJenis(s.getKodeJenis().toUpperCase());
                                stokJenis.setStokAwal(s.getStokAwal());
                                stokJenis.setBeratAwal(s.getBeratAwal());
                                stokJenis.setStokMasuk(s.getStokMasuk());
                                stokJenis.setBeratMasuk(s.getBeratMasuk());
                                stokJenis.setStokKeluar(s.getStokKeluar());
                                stokJenis.setBeratKeluar(s.getBeratKeluar());
                                stokJenis.setStokAkhir(s.getStokAkhir());
                                stokJenis.setBeratAkhir(s.getBeratAkhir());
                                listBarang.add(stokJenis);
                            }
                        }
                        listBarang.sort(Comparator.comparing(StokBarangCabang::getKodeGudang));
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
        for (StokBarangCabang b : allBarang) {
            if(searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(b);
            else if(checkColumn(b.getKodeKategori())|| 
                    checkColumn(b.getKodeJenis()))
                filterData.add(b);
        }
        hitungTotal();
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        for(StokBarangCabang b : filterData){
            totalQty = totalQty + b.getStokAkhir();
            totalBerat = totalBerat + b.getBeratAkhir();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }
    private void detailBarang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/DetailStokBarcodeCabang.fxml");
        DetailStokBarcodeCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setBarang(s.getTanggal(), s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
    }
    private void kartuStok(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/KartuStokBarcodeCabang.fxml");
        KartuStokBarcodeCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setJenis(s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
    }
}
