/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.SKLManualHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCell;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.SKLManualDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLManualHead;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class SKLManualController  {

   
    @FXML private TableView<SKLManualDetail> SKLManualDetailtable;
    @FXML private TableColumn<SKLManualDetail,String> tahapColumn;
    @FXML private TableColumn<SKLManualDetail,String> tglPembayaranColumn;
    @FXML private TableColumn<SKLManualDetail,Number> jumlahPembayaranColumn;
    
    @FXML private TextField noSKLField;
    @FXML private DatePicker tglSKLPicker;
    
    @FXML private TextField kodeKategoriField;
    @FXML private TextField namaPropertyField;
    @FXML private TextField tipeUnitField;
    @FXML private TextField luasTanahField;
    @FXML private TextField hargaField;
    @FXML private TextField namaCustomerField;
    @FXML private ComboBox<String> jenisKelaminCombo;
    
    @FXML private TextField jumlahPembayaranField;
    @FXML private DatePicker tglPembayaranPicker;
    
    @FXML private TextField totalPembayaranField;
    @FXML private TextField sisaPelunasanField;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    private SKLManualHead skl;
    private ObservableList<SKLManualDetail> allDetail = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        tahapColumn.setCellFactory(col -> Function.getWrapTableCell(tahapColumn));
        
        tglPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().tglBayarProperty()); 
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        Function.setNumberField(luasTanahField);
        Function.setNumberField(hargaField);
        Function.setNumberField(jumlahPembayaranField);
        
        tglSKLPicker.setConverter(Function.getTglConverter());
        tglSKLPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCell());
        
        tglPembayaranPicker.setConverter(Function.getTglConverter());
        tglPembayaranPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCell());
        
        SKLManualDetailtable.setRowFactory((TableView<SKLManualDetail> tableView) -> {
            final TableRow<SKLManualDetail> row = new TableRow<>();
            final ContextMenu rowMenu = new ContextMenu();
            
            MenuItem batal = new MenuItem("Delete");
            batal.setOnAction((ActionEvent event) -> {
                allDetail.remove(row.getItem());
                Collections.sort(allDetail, (SKLManualDetail o1, SKLManualDetail o2) ->{
                    if (o1.getTglBayar() == null || o2.getTglBayar()== null)
                        return 0;
                    try {
                        return tgl.parse(o1.getTglBayar()).compareTo(tgl.parse(o2.getTglBayar()));
                    } catch (ParseException ex) {
                        return 0;
                    }
                });
                for(int i = 0 ; i< allDetail.size() ; i++){
                    int tahap = i;
                    if(tahap==0)
                        allDetail.get(i).setTahap("Tanda Jadi (Booking Fee)");
                    else if(tahap==allDetail.size()-1)
                        allDetail.get(i).setTahap("Pelunasan Down Payment");
                    else
                        allDetail.get(i).setTahap("Down Payment Tahap "+String.valueOf(tahap));
                }
                skl.setAllDetail(allDetail);
                SKLManualDetailtable.refresh();
                hitungTotal();
            });
            
            rowMenu.getItems().addAll(batal);

            // only display context menu for non-null items:
            row.contextMenuProperty().bind(
                    Bindings.when(Bindings.isNotNull(row.itemProperty()))
                            .then(rowMenu)
                            .otherwise((ContextMenu)null));
            return row;
        });
    }   
    private class RemoveButtonCell extends TableCell<SKLManualDetail, Boolean> {
        final Button removeButton = new Button("");
        RemoveButtonCell(){
            Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
            removeButton.setGraphic(new ImageView(imageDecline));
            removeButton.setPrefSize(30, 30);
            removeButton.setTooltip(new Tooltip("Delete"));
            removeButton.setOnAction((ActionEvent t) -> {
                SKLManualDetail temp = (SKLManualDetail) RemoveButtonCell.this.getTableView().getItems().get(RemoveButtonCell.this.getIndex());
                allDetail.remove(temp);
                Collections.sort(allDetail, (SKLManualDetail o1, SKLManualDetail o2) ->{
                    if (o1.getTglBayar() == null || o2.getTglBayar()== null)
                        return 0;
                    try {
                        return tgl.parse(o1.getTglBayar()).compareTo(tgl.parse(o2.getTglBayar()));
                    } catch (ParseException ex) {
                        return 0;
                    }
                });
                for(int i = 0 ; i< allDetail.size() ; i++){
                    int tahap = i;
                    if(tahap==0)
                        allDetail.get(i).setTahap("Tanda Jadi (Booking Fee)");
                    else if(tahap==allDetail.size()-1)
                        allDetail.get(i).setTahap("Pelunasan Down Payment");
                    else
                        allDetail.get(i).setTahap("Down Payment Tahap "+String.valueOf(tahap));
                }
                skl.setAllDetail(allDetail);
                SKLManualDetailtable.refresh();
                hitungTotal();
            });
        }
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty)
                setGraphic(removeButton);
            else
                setGraphic(null);
        }
    } 
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
            SKLManualDetailtable.setItems(allDetail);
            skl = new SKLManualHead();
            tglSKLPicker.setValue(LocalDate.parse(tglBarang.format(new Date()), DateTimeFormatter.ISO_DATE));
            ObservableList<String> alljenis = FXCollections.observableArrayList();
            alljenis.add("Laki-laki");
            alljenis.add("Perempuan");
            jenisKelaminCombo.setItems(alljenis);
            generateNoSKL();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void generateNoSKL(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return SKLManualHeadDAO.getNoSKLManual(con, "JBP");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                noSKLField.setText(task.getValue());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void addDetail(){
        if(tglPembayaranPicker.getValue()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Tgl Pembayaran masih kosong");
        }else if("".equals(jumlahPembayaranField.getText())||"0".equals(jumlahPembayaranField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran masih kosong");
        }else{
            try {
                SKLManualDetail d = new SKLManualDetail();
                d.setTahap("");
                d.setTglBayar(tgl.format(tglBarang.parse(tglPembayaranPicker.getValue().toString())));
                d.setJumlahRp(Double.parseDouble(jumlahPembayaranField.getText().replaceAll(",", "")));
                allDetail.add(d);
                Collections.sort(allDetail, (SKLManualDetail o1, SKLManualDetail o2) ->{
                    if (o1.getTglBayar() == null || o2.getTglBayar()== null)
                        return 0;
                    try {
                        return tgl.parse(o1.getTglBayar()).compareTo(tgl.parse(o2.getTglBayar()));
                    } catch (ParseException ex) {
                        return 0;
                    }
                });
                
                for(int i = 0 ; i< allDetail.size() ; i++){
                    int tahap = i;
                    if(tahap==0)
                        allDetail.get(i).setTahap("Tanda Jadi (Booking Fee)");
                    else if(tahap==allDetail.size()-1)
                        allDetail.get(i).setTahap("Pelunasan Down Payment");
                    else
                        allDetail.get(i).setTahap("Down Payment Tahap "+String.valueOf(tahap));
                }
                skl.setAllDetail(allDetail);
                SKLManualDetailtable.refresh();
                hitungTotal();
            } catch (ParseException ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        }
    }
    private void hitungTotal(){
        double totalPembayaran = 0;
        for(SKLManualDetail d : allDetail){
            totalPembayaran= totalPembayaran + d.getJumlahRp();
        }
        totalPembayaranField.setText(rp.format(totalPembayaran));
        sisaPelunasanField.setText(rp.format(Double.parseDouble(
            hargaField.getText().replaceAll(",", ""))-totalPembayaran));
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML 
    private void save(){
        if("".equals(noSKLField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "No SKl masih kosong");
        }else if(tglSKLPicker.getValue()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "tgl SKL belum dipilih");
        }else if("".equals(kodeKategoriField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Kategori Property masih kosong");
        }else if("".equals(namaPropertyField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama Property masih kosong");
        }else if("".equals(tipeUnitField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Tipe Unit masih kosong");
        }else if("".equals(luasTanahField.getText())||luasTanahField.getText().equals("0")){
            mainApp.showMessage(Modality.NONE, "Warning", "Luas Tanah masih kosong");
        }else if("".equals(hargaField.getText())||luasTanahField.getText().equals("0")){
            mainApp.showMessage(Modality.NONE, "Warning", "Harga masih kosong");
        }else if("".equals(namaCustomerField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama Customer masih kosong");
        }else if(jenisKelaminCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Jenis Kelamin belum dipilih");
        }else if(allDetail.isEmpty()){
            mainApp.showMessage(Modality.NONE, "Warning", "Pembayaran down payment masih kosong");
        }else {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        skl.setNoSKL(noSKLField.getText());
                        skl.setTglSKL(tglSKLPicker.getValue().toString());
                        skl.setKodeKategori(kodeKategoriField.getText());
                        skl.setNamaProperty(namaPropertyField.getText());
                        skl.setTipeUnit(tipeUnitField.getText());
                        skl.setLuasTanah(Double.parseDouble(luasTanahField.getText().replaceAll(",", "")));
                        skl.setHarga(Double.parseDouble(hargaField.getText().replaceAll(",", "")));
                        skl.setNamaCustomer(namaCustomerField.getText());
                        skl.setJenisKelamin(jenisKelaminCombo.getSelectionModel().getSelectedItem());
                        skl.setTotalPembayaran(Double.parseDouble(totalPembayaranField.getText().replaceAll(",", "")));
                        skl.setSisaPelunasan(Double.parseDouble(sisaPelunasanField.getText().replaceAll(",", "")));
                        skl.setKodeUser(sistem.getUser().getUsername());
                        skl.setTglDibuat(tglSql.format(new Date()));
                        skl.setAllDetail(allDetail);
                        return Service.saveSKLManual(con, skl);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    mainApp.showPelunasanDownPayment();
                    if(task.getValue().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "SKL Manual berhasil dibuat");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                    }
                }catch(Exception ex){
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    } 
    
}
