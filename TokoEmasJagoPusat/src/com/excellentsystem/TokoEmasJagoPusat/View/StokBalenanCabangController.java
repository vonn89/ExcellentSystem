/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RevisiBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.AmbilRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.KartuStokBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PindahRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.RevisiBarangCabangController;
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
public class StokBalenanCabangController {

    @FXML private TableView<StokBarangCabang> stokBarangTable;
    @FXML private TableColumn<StokBarangCabang, String> kodeCabangColumn;
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
    
    @FXML private TextField searchField;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private DatePicker tglPicker;
    private Main mainApp;   
    private final ObservableList<String> allCabang = FXCollections.observableArrayList();
    private final ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokBarangCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
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
        MenuItem ambil = new MenuItem("Ambil Rosok Cabang");
        ambil.setOnAction((ActionEvent e)->{
            ambilRosokCabang(null);
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        for(Otoritas o : user.getOtoritas()){
            if(o.getJenis().equals("Rosok Barang Cabang"))
                rowMenu.getItems().add(ambil);
        }
        rowMenu.getItems().add(refresh);
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
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem revisi = new MenuItem("Revisi Barang Cabang");
                        revisi.setOnAction((ActionEvent e)->{
                            revisiBarangCabang(item);
                        });
                        MenuItem pindah = new MenuItem("Pindah Rosok Cabang");
                        pindah.setOnAction((ActionEvent e)->{
                            pindahRosokCabang(item);
                        });
                        MenuItem ambil = new MenuItem("Ambil Rosok Cabang");
                        ambil.setOnAction((ActionEvent e)->{
                            ambilRosokCabang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(kartu);
                        if(item.getKodeGudang().startsWith("BLN") || item.getKodeGudang().endsWith("SP")){
                            for(Otoritas o : user.getOtoritas()){
                                if(o.getJenis().equals("Revisi Barang Cabang"))
                                    rowMenu.getItems().add(revisi);
                                if(o.getJenis().equals("Rosok Barang Cabang")){
                                    rowMenu.getItems().add(pindah);
                                    rowMenu.getItems().add(ambil);
                                }
                            }
                        }
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

            getBarang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getBarang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
                @Override 
                public List<StokBarangCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String cabang = "%";
                        List<String> listGudang = new ArrayList<>();
                        if(!cabangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                            listGudang.add("BLN-"+cabangCombo.getSelectionModel().getSelectedItem());
                        }else{
                            for(String c : allCabang){
                                if(!c.equals("Semua"))
                                    listGudang.add("BLN-"+c);
                            }
                        }
                        List<StokBarangCabang> listBarang = StokBarangCabangDAO.getAllNonBarcodeByCabangAndListGudangAndKategoriAndJenis(conPusat, 
                            tglPicker.getValue().toString(), cabang, listGudang, "%", "%");
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
    private void kartuStok(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/KartuStokBarangCabang.fxml");
        KartuStokBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setData(s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
    }
    private void revisiBarangCabang(StokBarangCabang s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/RevisiBarangCabang.fxml");
        RevisiBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(s);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Jenis revisi belum dipilih");
            else if(controller.kodeJenisField.getText().equals("") || controller.stokBarangCabang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    controller.kodeJenisRevisiCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis revisi masih kosong");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    Double.parseDouble(controller.qtyRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty revisi masih kosong");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat revisi masih kosong");
            else if((controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok")|| 
                    controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Stok")) && 
                    Double.parseDouble(controller.qtyRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty revisi masih kosong");
            else if((controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Berat")|| 
                    controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Berat")) && 
                    Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat revisi masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            RevisiBarangCabang r = new RevisiBarangCabang();
                            r.setNoRevisi(RevisiBarangCabangDAO.getId(conPusat));
                            r.setTglRevisi(Function.getSystemDate());
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setJenisRevisi(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(controller.stokBarangCabang.getKodeKategori());
                            r.setKodeJenis(controller.kodeJenisField.getText());
                            
                            if(r.getJenisRevisi().equals("Ubah Jenis")){
                                Jenis jenisRevisi = JenisDAO.get(conPusat, controller.kodeJenisField.getText());
                                Kategori kategoriRevisi = KategoriDAO.get(conPusat, jenisRevisi.getKodeKategori());
                                r.setBeratPersen(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                                r.setKodeKategoriRevisi(kategoriRevisi.getKodeKategori());
                                r.setKodeJenisRevisi(controller.kodeJenisRevisiCombo.getSelectionModel().getSelectedItem());
                                r.setQtyRevisi(Integer.parseInt(controller.qtyRevisiField.getText().replaceAll(",", "")));
                                r.setBeratRevisi(Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", "")));
                                r.setBeratPersenRevisi(pembulatan(kategoriRevisi.getPersentaseEmas()*r.getBeratRevisi()/100));
                                r.setNilaiRevisi(pembulatan(controller.stokBarangCabang.getNilaiAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                            }else if(r.getJenisRevisi().equals("Penambahan Stok") || r.getJenisRevisi().equals("Pengurangan Stok")){
                                r.setBeratPersen(0);
                                r.setKodeKategoriRevisi("");
                                r.setKodeJenisRevisi("");
                                r.setQtyRevisi(Integer.parseInt(controller.qtyRevisiField.getText().replaceAll(",", "")));
                                r.setBeratRevisi(0);
                                r.setBeratPersenRevisi(0);
                                r.setNilaiRevisi(0);
                            }else if(r.getJenisRevisi().equals("Penambahan Berat") || r.getJenisRevisi().equals("Pengurangan Berat")){
                                r.setBeratPersen(0);
                                r.setKodeKategoriRevisi("");
                                r.setKodeJenisRevisi("");
                                r.setQtyRevisi(0);
                                r.setBeratRevisi(Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", "")));
                                r.setBeratPersenRevisi(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                                r.setNilaiRevisi(0);
                            }
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRevisiBarangCabang(conPusat, r);
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
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Revisi barang cabang berhasil disimpan");
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
    private void pindahRosokCabang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/PindahRosokCabang.fxml");
        PindahRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setBarang(s);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisField.getText().equals("") || controller.stokBarangCabang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Pindah Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(controller.stokBarangCabang.getKodeKategori());
                            r.setKodeJenis(controller.kodeJenisField.getText());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokBarangCabang.getNilaiAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Pindah rosok cabang berhasil disimpan");
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
    private void ambilRosokCabang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/AmbilRosokCabang.fxml");
        AmbilRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        if(s!=null)
            controller.setBarang(s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            Jenis j = JenisDAO.get(conPusat, controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            Kategori k = KategoriDAO.get(conPusat, j.getKodeKategori());
                            String kodeKategori = k.getKodeKategori();
                            double persentaseEmas = k.getPersentaseEmas();
                            
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Ambil Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(kodeKategori);
                            r.setKodeJenis(controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(persentaseEmas*r.getBerat()/100));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokRosokCabang.getNilaiAkhir()/controller.stokRosokCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
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
                        mainApp.showMessage(Modality.NONE, "Success", "Ambil rosok cabang berhasil disimpan");
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
}
