/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RevisiBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailRevisiBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.RevisiBarangCabangController;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
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
public class DataRevisiBarangCabangController {

    @FXML private TableView<RevisiBarangCabang> revisiTable;
    @FXML private TableColumn<RevisiBarangCabang, String> noRevisiColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> tglRevisiColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> kodeCabangColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> kodeGudangColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> keteranganColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> jenisRevisiColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> kodeUserColumn;
    
    @FXML private TableColumn<RevisiBarangCabang, String> kodeJenisColumn;
    @FXML private TableColumn<RevisiBarangCabang, String> kodeJenisRevisiColumn;
    @FXML private TableColumn<RevisiBarangCabang, Number> qtyRevisiColumn;
    @FXML private TableColumn<RevisiBarangCabang, Number> beratRevisiColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    private Main mainApp;   
    private ObservableList<RevisiBarangCabang> allRevisiBarang = FXCollections.observableArrayList();
    private ObservableList<RevisiBarangCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noRevisiColumn.setCellValueFactory(cellData -> cellData.getValue().noRevisiProperty());
        tglRevisiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRevisi())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglRevisiColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        jenisRevisiColumn.setCellValueFactory(cellData -> cellData.getValue().jenisRevisiProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kodeJenisRevisiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisRevisiProperty());
        qtyRevisiColumn.setCellValueFactory(cellData -> cellData.getValue().qtyRevisiProperty());
        qtyRevisiColumn.setCellFactory(col -> getTableCell(rp));
        beratRevisiColumn.setCellValueFactory(cellData -> cellData.getValue().beratRevisiProperty());
        beratRevisiColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Revisi Barang Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newRevisiBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getRevisiBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        revisiTable.setContextMenu(rowMenu);
        revisiTable.setRowFactory(table -> {
            TableRow<RevisiBarangCabang> row = new TableRow<RevisiBarangCabang>() {
                @Override
                public void updateItem(RevisiBarangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Revisi Barang Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newRevisiBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Revisi Barang Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailRevisiBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Revisi Barang Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalRevisiBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getRevisiBarang();
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
                        detailRevisiBarang(row.getItem());
                }
            });
            return row;
        });
        allRevisiBarang.addListener((ListChangeListener.Change<? extends RevisiBarangCabang> change) -> {
            searchRevisiBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchRevisiBarang();
        });
        filterData.addAll(allRevisiBarang);
        revisiTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getRevisiBarang();
    } 
    @FXML
    private void getRevisiBarang(){
        Task<List<RevisiBarangCabang>> task = new Task<List<RevisiBarangCabang>>() {
            @Override 
            public List<RevisiBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return RevisiBarangCabangDAO.getAllByDateAndCabangAndGudangAndStatus(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "%", "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allRevisiBarang.clear();
            allRevisiBarang.addAll(task.getValue());
            revisiTable.refresh();
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
    private void searchRevisiBarang() {
        try{
            filterData.clear();
            for (RevisiBarangCabang a : allRevisiBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoRevisi())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglRevisi())))||
                        checkColumn(a.getKeterangan())||
                        checkColumn(a.getJenisRevisi())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getKodeGudang())||
                        checkColumn(a.getKodeJenis())||
                        checkColumn(a.getKodeJenisRevisi())||
                        checkColumn(rp.format(a.getQtyRevisi()))||
                        checkColumn(gr.format(a.getBeratRevisi())))
                        filterData.add(a);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void newRevisiBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/RevisiBarangCabang.fxml");
        RevisiBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
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
            else if(controller.stokBarangCabang==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Stok barang cabang belum dipilih");
            }else{
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
                    getRevisiBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        controller.kodeJenisRevisiCombo.getSelectionModel().clearSelection();
                        controller.qtyRevisiField.setText("0");
                        controller.beratRevisiField.setText("0");
                        controller.reset();
//                        mainApp.closeDialog(mainApp.MainStage, stage);
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
    private void detailRevisiBarang(RevisiBarangCabang r){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailRevisiBarangCabang.fxml");
        DetailRevisiBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(r);
    }
    private void batalRevisiBarang(RevisiBarangCabang r){
        if(r.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Revisi barang cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal revisi barang cabang "+r.getNoRevisi()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            r.setStatus("false");
                            r.setTglBatal(Function.getSystemDate());
                            r.setUserBatal(user.getKodeUser());
                            return Service.saveBatalRevisiBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRevisiBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Revisi barang cabang berhasil dibatal");
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
