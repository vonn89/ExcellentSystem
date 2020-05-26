/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataBarangBarcodeController {

    @FXML private TableView<StokBarangCabang> barangTable;
    @FXML private TableColumn<StokBarangCabang,Boolean> checkColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeCabangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeGudangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> namaBarangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeKategoriColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeJenisColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeInternColumn;
    @FXML private TableColumn<StokBarangCabang, String> kadarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAsliColumn;
    @FXML private TableColumn<StokBarangCabang, String> tglBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> userBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> asalBarangColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> cabangCombo;
    @FXML private ComboBox<String> gudangCombo;
    @FXML private ComboBox<String> kategoriCombo;
    @FXML private ComboBox<String> jenisCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    
    private List<Jenis> listJenis;
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    private ObservableList<String> allGudang = FXCollections.observableArrayList();
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allJenis = FXCollections.observableArrayList();
    private ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    private ObservableList<StokBarangCabang> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getKodeJenis().toUpperCase());
        });
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().asalBarangProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getBarangCabang().getInputDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().inputByProperty());
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer i) -> {
            return barangTable.getItems().get(i).getBarangCabang().checkedProperty();
        }));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Cetak Barcode");
        cetak.setOnAction((ActionEvent e)->{
            cetakBarcode();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(cetak,refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
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
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e)->{
                            cetakBarcode();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(cetak);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().getBarangCabang().isChecked())
                            row.getItem().getBarangCabang().setChecked(false);
                        else
                            row.getItem().getBarangCabang().setChecked(true);
                        hitungTotal();
                    }
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
        filterData.addAll(allBarang);
        barangTable.setItems(filterData);
        cabangCombo.setItems(allCabang);
        gudangCombo.setItems(allGudang);
        kategoriCombo.setItems(allKategori);
        jenisCombo.setItems(allJenis);
    }      
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        setData();
    } 
    private void setData(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<Kategori> listKategori = KategoriDAO.getAll(conPusat);
                    allKategori.clear();
                    allKategori.add("Semua");
                    for(Kategori k : listKategori){
                        allKategori.add(k.getKodeKategori());
                    }
                    listJenis = JenisDAO.getAll(conPusat);
                    List<Cabang> listCabang = CabangDAO.getAll(conPusat);
                    allCabang.clear();
                    allCabang.add("Semua");
                    for(Cabang c : listCabang){
                        allCabang.add(c.getKodeCabang());
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allGudang.clear();
            allGudang.add("Semua");
            allGudang.add("New");
            allGudang.add("SP");
            gudangCombo.getSelectionModel().select("Semua");
            cabangCombo.getSelectionModel().select("Semua");
            kategoriCombo.getSelectionModel().select("Semua");
            barangTable.refresh();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setJenis(){
        allJenis.clear();
        allJenis.add("Semua");
        for(Jenis j : listJenis){
            if(kategoriCombo.getSelectionModel().getSelectedItem().equals("Semua")||
                    kategoriCombo.getSelectionModel().getSelectedItem().equals(j.getKodeKategori()))
                allJenis.add(j.getKodeJenis());
        }
        jenisCombo.getSelectionModel().select("Semua");
    }
    @FXML
    private void getBarang(){
        Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
            @Override 
            public List<StokBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String cabang = "%";
                    List<String> listGudang = new ArrayList<>();
                    String kategori = "%";
                    String jenis = "%";
                    if(!cabangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                        cabang = cabangCombo.getSelectionModel().getSelectedItem();
                        if(!gudangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                            listGudang.add(cabang+"-"+gudangCombo.getSelectionModel().getSelectedItem());
                        }else{
                            listGudang.add(cabang+"-New");
                            listGudang.add(cabang+"-SP");
                        }
                    }else{
                        if(!gudangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                            for(String c : allCabang){
                                if(!c.equals("Semua")){
                                    listGudang.add(c+"-"+gudangCombo.getSelectionModel().getSelectedItem());
                                }
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
                    if(!kategoriCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        kategori = kategoriCombo.getSelectionModel().getSelectedItem();
                    if(!jenisCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        jenis = jenisCombo.getSelectionModel().getSelectedItem();
                    List<StokBarangCabang> listStok = new ArrayList<>();
                    List<StokBarangCabang> allStokBarang = StokBarangCabangDAO.getAllBarcodeByCabangAndListGudangAndKategoriAndJenisAndBarcode(conPusat,
                            sistem.getTglSystem(), cabang, listGudang, kategori, jenis, "%");
                    for(StokBarangCabang s : allStokBarang){
                        if(s.getStokAkhir()>0){
                            BarangCabang b = BarangCabangDAO.get(conPusat, s.getKodeBarcode());
                            b.setChecked(checkAll.isSelected());
                            s.setBarangCabang(b);
                            listStok.add(s);
                        }
                    }
                    return listStok;
                    
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
            barangTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAllHandle(){
        for(StokBarangCabang b: allBarang){
            b.getBarangCabang().setChecked(checkAll.isSelected());
        }
        barangTable.refresh();
        hitungTotal();
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
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(b);
                else{
                    if(checkColumn(b.getKodeKategori())||
                        checkColumn(b.getKodeCabang())||
                        checkColumn(b.getKodeGudang())||
                        checkColumn(b.getKodeBarcode())||
                        checkColumn(b.getKodeBarang())||
                        checkColumn(b.getBarangCabang().getNamaBarang())||
                        checkColumn(b.getKodeJenis())||
                        checkColumn(b.getBarangCabang().getKodeIntern())||
                        checkColumn(b.getBarangCabang().getKadar())||
                        checkColumn(b.getBarangCabang().getAsalBarang())||
                        checkColumn(b.getBarangCabang().getInputBy())||
                        checkColumn(tglLengkap.format(tglSql.parse(b.getBarangCabang().getInputDate())))||
                        checkColumn(gr.format(b.getBarangCabang().getBerat()))||
                        checkColumn(gr.format(b.getBarangCabang().getBeratAsli())))
                        filterData.add(b);
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
        double totalBeratAsli = 0;
        for(StokBarangCabang b : filterData){
            if(b.getBarangCabang().isChecked()){
                totalQty = totalQty + 1;
                totalBerat = totalBerat + b.getBarangCabang().getBerat();
                totalBeratAsli = totalBeratAsli + b.getBarangCabang().getBeratAsli();
            }
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratAsliLabel.setText(gr.format(totalBeratAsli));
    }
    private void detailBarang(StokBarangCabang b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(b.getBarangCabang());
    }
    private void cetakBarcode(){
        List<BarangCabang> barang = new ArrayList<>();
        for(StokBarangCabang b : filterData){
            if(b.getBarangCabang().isChecked())
                barang.add(b.getBarangCabang());
        }
        if(barang.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        else{        
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Cetak "+totalQtyLabel.getText()+" barcode ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                try{ 
                    mainApp.closeMessage();
                    PrintOut.printBarcode(barang);
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }
}
