/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailTransaksiKeuanganPropertyController  {

    @FXML private TableView<Keuangan> propertyTable;
    @FXML private TableColumn<Keuangan,Boolean> checkColumn;
    @FXML private TableColumn<Keuangan, String> namaPropertyColumn;
    @FXML private TableColumn<Keuangan, Number> luasTanahColumn;
    @FXML private TableColumn<Keuangan, Number> jumlahRpColumn;
    
    @FXML public Button closeButton;
    @FXML public ComboBox<String> metodeCombo;
    @FXML private Label kategoriPropertyLabel;
    @FXML public ComboBox<String> kategoriPropertyCombo;
    @FXML private CheckBox checkAll;
    @FXML public Label totalPropertyLabel;
    @FXML public Label totalLuasTanahLabel;
    @FXML public Label totalTransaksiLabel;
    
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allMetode = FXCollections.observableArrayList();
    public ObservableList<Keuangan> allDetail = FXCollections.observableArrayList();
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        luasTanahColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));
        
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(qty));
        
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            hitungTotal();
            return propertyTable.getItems().get(param).checkedProperty();
        }));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            propertyTable.refresh();
        });
        rm.getItems().add(refresh);
        propertyTable.setContextMenu(rm);
        propertyTable.setRowFactory((TableView<Keuangan> tableView) -> {
            final TableRow<Keuangan> row = new TableRow<Keuangan>(){
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            propertyTable.refresh();
                        });
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(checkColumn.isVisible()){
                        row.getItem().setChecked(!row.getItem().isChecked());
                        hitungTotal();
                    }
                }
            });
            return row;
        });
    }    
    
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);

            propertyTable.setItems(allDetail);
            metodeCombo.setItems(allMetode);
            kategoriPropertyCombo.setItems(allKategori);

            allMetode.clear();
            allMetode.addAll("Rata-rata","Luas Tanah");
            metodeCombo.getSelectionModel().select("Rata-rata");

            allKategori.addAll(Function.getKategoriProperty());
            allKategori.add("Semua");
            kategoriPropertyCombo.getSelectionModel().clearSelection();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }

    }
    public void setProperty(String metode, List<Keuangan> listKeuangan, double totalTransaksi){
        if(metode!=null)
            metodeCombo.getSelectionModel().select(metode);
        else
            metodeCombo.getSelectionModel().selectFirst();
        
        allDetail.clear();
        allDetail.addAll(listKeuangan);
        double totalProp = 0;
        double totalLuas = 0;
        for(Keuangan d: allDetail){
            if(d.isChecked()){
                totalProp = totalProp + 1;
                totalLuas = totalLuas + d.getProperty().getLuasTanah();
            }
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
        totalTransaksiLabel.setText(rp.format(totalTransaksi));
    }
    public void setViewOnly(){
        kategoriPropertyLabel.setVisible(false);
        kategoriPropertyCombo.setVisible(false);
        metodeCombo.setDisable(true);
        checkColumn.setVisible(false);
    }
    @FXML
    private void getProperty(){
        if(kategoriPropertyCombo.isVisible()){
            Task<List<Property>> task = new Task<List<Property>>() {
                @Override 
                public List<Property> call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        List<String> status = new ArrayList<>();
                        status.add("Available");
                        status.add("Reserved");
                        status.add("Sold");
                        return PropertyDAO.getAllByStatus(con,status);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                allDetail.clear();
                for(Property p : task.getValue()){
                    Keuangan d = new Keuangan();
                    d.setKodeProperty(p.getKodeProperty());
                    d.setChecked(checkAll.isSelected());
                    d.setProperty(p);
                    if(p.getKodeKategori().equals(kategoriPropertyCombo.getSelectionModel().getSelectedItem())||
                            kategoriPropertyCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        allDetail.add(d);
                }
                if(!allDetail.isEmpty())
                    hitungTotal();
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void hitungTotal(){
        double totalProp = 0;
        double totalLuas = 0;
        for(Keuangan d: allDetail){
            if(d.isChecked()){
                totalProp = totalProp + 1;
                totalLuas = totalLuas + d.getProperty().getLuasTanah();
            }
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
        
        double totalBiaya = Double.parseDouble(totalTransaksiLabel.getText().replaceAll(",", ""));
        if(metodeCombo.getSelectionModel().getSelectedItem().equals("Rata-rata")){
            for(Keuangan d: allDetail){
                if(d.isChecked())
                    d.setJumlahRp(totalBiaya/totalProp);
                else
                    d.setJumlahRp(0);
            }
        }else if(metodeCombo.getSelectionModel().getSelectedItem().equals("Luas Tanah")){
            for(Keuangan d: allDetail){
                if(d.isChecked())
                    d.setJumlahRp(totalBiaya*d.getProperty().getLuasTanah()/totalLuas);
                else
                    d.setJumlahRp(0);
            }
        }
        propertyTable.refresh();
    }
    @FXML
    private void checkAllHandle(){
        for(Keuangan d: allDetail){
            d.setChecked(checkAll.isSelected());
        }
        hitungTotal();
    }
}
