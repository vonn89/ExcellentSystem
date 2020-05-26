/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
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
 * @author Xtreme
 */
public class PemecahanPropertyController {
  
    @FXML private TableView<PemecahanPropertyDetail> propertyTable;
    @FXML private TableColumn<PemecahanPropertyDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PemecahanPropertyDetail, String> namaPropertyColumn;
    @FXML private TableColumn<PemecahanPropertyDetail, Number> luasTanahColumn;
    @FXML private TableColumn<PemecahanPropertyDetail, Number> nilaiPropertyColumn;
    @FXML private TableColumn<PemecahanPropertyDetail, Number> nilaiPropertyPerMeterColumn;
    @FXML private TableColumn<PemecahanPropertyDetail, Number> hargaJualColumn;
    
    @FXML public TextField kodePropertyField;
    @FXML public TextField namaField;
    @FXML private TextField alamatField;
    @FXML public TextField luasTanahField;
    @FXML public TextField nilaiPropertyField;
    @FXML private TextField nilaiPropertyPerMeterField;
    @FXML public Button saveButton;
    
    @FXML public Label totalPropertyLabel;
    @FXML public Label totalLuasEfektifLabel;
    @FXML public Label totalLuasTersisaLabel;
    @FXML public Label totalJualLabel;
    @FXML public Label nilaiPropertyPerMeterAkhirLabel;
    
    public Property property;
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public ObservableList<PemecahanPropertyDetail> allDetail = FXCollections.observableArrayList();
    public void initialize(){
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));
        
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        luasTanahColumn.setCellValueFactory(cellData ->cellData.getValue().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));
        
        nilaiPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().nilaiPropertyProperty());
        nilaiPropertyColumn.setCellFactory(col -> getTableCell(rp));
        
        nilaiPropertyPerMeterColumn.setCellValueFactory(cellData ->{
            return new SimpleDoubleProperty(cellData.getValue().getNilaiProperty()/cellData.getValue().getLuasTanah());
        });
        nilaiPropertyPerMeterColumn.setCellFactory(col -> getTableCell(rp));
        
        hargaJualColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem add = new MenuItem("Add Property");
        add.setOnAction((ActionEvent event) -> {
            addChildren();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            propertyTable.refresh();
        });
        rowMenu.getItems().addAll(add, refresh);
        propertyTable.setContextMenu(rowMenu);
        propertyTable.setRowFactory((TableView<PemecahanPropertyDetail> tableView) -> {
            final TableRow<PemecahanPropertyDetail> row = new TableRow<PemecahanPropertyDetail>(){
                @Override
                public void updateItem(PemecahanPropertyDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem add = new MenuItem("Add Property");
                        add.setOnAction((ActionEvent event) -> {
                            addChildren();
                        });
                        MenuItem edit = new MenuItem("Edit Property");
                        edit.setOnAction((ActionEvent event) -> {
                            updateDetail(item);
                        });
                        MenuItem delete = new MenuItem("Delete Property");
                        delete.setOnAction((ActionEvent e)->{
                            deleteDetail(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            propertyTable.refresh();
                        });
                        rowMenu.getItems().addAll(add, edit,delete,refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        propertyTable.setItems(allDetail);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*80/100);
        stage.setWidth(mainApp.screenSize.getWidth()*80/100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    private void hitungTotal(){
        int totalProperty = 0;
        double totalLuasEfektif = 0;
        double totalLuasTersisa = Double.parseDouble(luasTanahField.getText().replaceAll(",", ""));
        double nilaiPropertyPerMeter = 0;
        double totalJual = 0;
        for(PemecahanPropertyDetail x : allDetail){
            totalProperty = totalProperty + 1;
            totalLuasEfektif = totalLuasEfektif + x.getLuasTanah();
            totalJual = totalJual + x.getProperty().getHargaJual();
        }
        totalLuasTersisa = totalLuasTersisa - totalLuasEfektif;
        if(totalLuasEfektif!=0)
            nilaiPropertyPerMeter = Double.parseDouble(nilaiPropertyField.getText().replaceAll(",", ""))/totalLuasEfektif;
        for(PemecahanPropertyDetail x : allDetail){
            x.setNilaiProperty(nilaiPropertyPerMeter*x.getLuasTanah());
            x.getProperty().setNilaiProperty(nilaiPropertyPerMeter*x.getLuasTanah());
        }
        propertyTable.refresh();
        totalJualLabel.setText(rp.format(totalJual));
        totalPropertyLabel.setText(qty.format(totalProperty));
        totalLuasEfektifLabel.setText(qty.format(totalLuasEfektif));
        totalLuasTersisaLabel.setText(qty.format(totalLuasTersisa));
        nilaiPropertyPerMeterAkhirLabel.setText(rp.format(nilaiPropertyPerMeter));
    }
    @FXML
    private void addParent(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> status = new ArrayList<>();
        status.add("Available");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    property = row.getItem();
                    kodePropertyField.setText(property.getKodeProperty());
                    namaField.setText(property.getNamaProperty());
                    alamatField.setText(property.getAlamat());
                    luasTanahField.setText(qty.format(property.getLuasTanah()));
                    nilaiPropertyField.setText(rp.format(property.getNilaiProperty()));
                    nilaiPropertyPerMeterField.setText(rp.format(property.getNilaiProperty()/property.getLuasTanah()));
                    allDetail.clear();
                    hitungTotal();
                    mainApp.closeDialog(stage, child);
                }
            });
            return row;
        });
    }
    @FXML
    private void addChildren(){
        if("".equals(kodePropertyField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Property yang akan dipecah, belum dipilih");
        }else{
            try{
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailProperty.fxml");
                DetailPropertyController x = loader.getController();
                x.setMainApp(mainApp, stage, child);
                x.setPemecahanProperty(null);
                x.saveButton.setOnAction((ActionEvent e )->{
                    if("".equals(x.luasTanahField.getText())||x.luasTanahField.getText().equals("0")){
                        mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah masih kosong");
                    }else if(Double.parseDouble(totalLuasTersisaLabel.getText().replaceAll(",", ""))<
                            Double.parseDouble(x.luasTanahField.getText().replaceAll(",", ""))){
                        mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah melebihi yang tersisa");
                    }else{
                        PemecahanPropertyDetail detail = new PemecahanPropertyDetail();
                        detail.setKodeProperty(x.kodePropertyField.getText());
                        detail.setKodeKategori(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                        detail.setNamaProperty(x.namaPropertyField.getText());
                        detail.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                        detail.setNilaiProperty(0);
                        
                        Property p = new Property();
                        p.setKodeProperty(x.kodePropertyField.getText());
                        p.setKodeKategori(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                        p.setNamaProperty(x.namaPropertyField.getText());
                        p.setAlamat(x.alamatField.getText());
                        p.setTipe(x.tipeField.getText());
                        p.setSpesifikasi(x.spesifikasiField.getText());
                        p.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                        p.setLuasBangunan(0);
                        p.setNilaiProperty(0);
                        p.setHargaJual(Double.parseDouble(x.hargaJualField.getText().replaceAll(",", "")));
                        p.setDiskon(0);
                        p.setKeterangan(x.keteranganField.getText());
                        p.setStatus("Available");
                        
                        detail.setProperty(p);
                        allDetail.add(detail);
                        hitungTotal();
                        mainApp.closeDialog(stage,child);
                    }
                });
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
    private void updateDetail(PemecahanPropertyDetail detail){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setPemecahanProperty(detail);
        x.saveButton.setOnAction((ActionEvent e )->{
            if("".equals(x.luasTanahField.getText())||x.luasTanahField.getText().equals("0")){
                mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah masih kosong");
            }else if(Double.parseDouble(totalLuasTersisaLabel.getText().replaceAll(",", ""))+detail.getLuasTanah()<
                    Double.parseDouble(x.luasTanahField.getText().replaceAll(",", ""))){
                mainApp.showMessage(Modality.NONE, "Warning", "Luas tanah melebihi yang tersisa");
            }else{
                detail.setKodeProperty(x.kodePropertyField.getText());
                detail.setKodeKategori(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                detail.setNamaProperty(x.namaPropertyField.getText());
                detail.setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                detail.setNilaiProperty(0);
                
                detail.getProperty().setKodeProperty(x.kodePropertyField.getText());
                detail.getProperty().setKodeKategori(x.kodeKategoriCombo.getSelectionModel().getSelectedItem());
                detail.getProperty().setNamaProperty(x.namaPropertyField.getText());
                detail.getProperty().setAlamat(x.alamatField.getText());
                detail.getProperty().setTipe(x.tipeField.getText());
                detail.getProperty().setSpesifikasi(x.spesifikasiField.getText());
                detail.getProperty().setLuasTanah(Double.parseDouble(x.luasTanahField.getText().replaceAll(",", "")));
                detail.getProperty().setLuasBangunan(0);
                detail.getProperty().setNilaiProperty(0);
                detail.getProperty().setHargaJual(Double.parseDouble(x.hargaJualField.getText().replaceAll(",", "")));
                detail.getProperty().setDiskon(0);
                detail.getProperty().setKeterangan(x.keteranganField.getText());
                detail.getProperty().setStatus("Available");
                
                propertyTable.refresh();
                hitungTotal();
                mainApp.closeDialog(stage,child);
            }

        });
    }
    private void deleteDetail(PemecahanPropertyDetail detail){
        allDetail.remove(detail);
        propertyTable.refresh();
        hitungTotal();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
