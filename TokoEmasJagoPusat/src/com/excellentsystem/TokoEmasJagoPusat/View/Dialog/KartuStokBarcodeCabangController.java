/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class KartuStokBarcodeCabangController  {

    @FXML private TableView<StokBarangCabang> stokBarangTable;
    @FXML private TableColumn<StokBarangCabang, String> tanggalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAwalColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratMasukColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> stokAkhirColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAkhirColumn;
    
    @FXML private Label kodeCabangLabel;
    @FXML private Label kodeGudangLabel;
    @FXML private Label kodeBarangLabel;
    @FXML private Label kodeBarangValueLabel;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    private final ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglNormal.format(tglBarang.parse(cellData.getValue().getTanggal())));
            } catch (Exception ex) {
                return null;
            }
        });
        tanggalColumn.setComparator(Function.sortDate(tglNormal));
        
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
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        if(kodeBarangLabel.getText().equals("Kode Jenis")){
                            rowMenu.getItems().add(detail);
                        }
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
    public void setBarcode(String kodeCabang, String kodeGudang, String kodeBarcode){
        kodeCabangLabel.setText(kodeCabang);
        kodeGudangLabel.setText(kodeGudang);
        kodeBarangLabel.setText("Kode Barcode");
        kodeBarangValueLabel.setText(kodeBarcode);
        getBarang();
    }
    public void setJenis(String kodeCabang, String kodeGudang, String kodeJenis){
        kodeCabangLabel.setText(kodeCabang);
        kodeGudangLabel.setText(kodeGudang);
        kodeBarangLabel.setText("Kode Jenis");
        kodeBarangValueLabel.setText(kodeJenis);
        getBarang();
    }
    @FXML
    private void getBarang(){
        Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
            @Override 
            public List<StokBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String kodeCabang = kodeCabangLabel.getText();
                    String kodeGudang = kodeGudangLabel.getText();
                    String kodeBarcode = "%";
                    String kodeJenis = "%";
                    if(kodeBarangLabel.getText().equals("Kode Barcode")){
                        kodeBarcode = kodeBarangValueLabel.getText();
                        List<StokBarangCabang> listStokBarang = StokBarangCabangDAO.getAllBarcodeByDateAndCabangAndGudangAndKategoriAndJenisAndBarcode(conPusat,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, kodeGudang, "%", kodeJenis, kodeBarcode);
                        listStokBarang.sort(Comparator.comparing(StokBarangCabang::getTanggal));
                        return listStokBarang;
                    }else if(kodeBarangLabel.getText().equals("Kode Jenis")){
                        kodeJenis = kodeBarangValueLabel.getText();
                        List<StokBarangCabang> listStokBarang = StokBarangCabangDAO.getAllBarcodeByDateAndCabangAndGudangAndKategoriAndJenisAndBarcode(conPusat,
                            tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, kodeGudang, "%", kodeJenis, kodeBarcode);
                    
                        List<StokBarangCabang> listBarang = new ArrayList<>();
                        for(StokBarangCabang s : listStokBarang){
                            boolean status = true;
                            for(StokBarangCabang temp : listBarang){
                                if(s.getKodeJenis().equalsIgnoreCase(temp.getKodeJenis())&&
                                        s.getTanggal().equals(temp.getTanggal())){
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
                                stokJenis.setKodeJenis(s.getKodeJenis());
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
                        listBarang.sort(Comparator.comparing(StokBarangCabang::getTanggal));
                        return listBarang;
                    }else
                        return null;
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
    private void detailBarang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailStokBarcodeCabang.fxml");
        DetailStokBarcodeCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s.getTanggal(), s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
