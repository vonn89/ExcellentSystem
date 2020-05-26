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
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class DetailProyekPropertyController  {

    @FXML private TableView<RAPDetailProperty> propertyTable;
    @FXML private TableColumn<RAPDetailProperty,Boolean> checkColumn;
    @FXML private TableColumn<RAPDetailProperty, String> namaPropertyColumn;
    @FXML private TableColumn<RAPDetailProperty, Number> luasTanahColumn;
    @FXML private TableColumn<RAPDetailProperty, Number> persentaseColumn;
    
    @FXML public Button closeButton;
    @FXML public ComboBox<String> metodeCombo;
    @FXML private Label kategoriPropertyLabel;
    @FXML private ComboBox<String> kategoriPropertyCombo;
    @FXML private CheckBox checkAll;
    @FXML public Label totalPropertyLabel;
    @FXML public Label totalLuasTanahLabel;
    
    private ObservableList<String> allKategori = FXCollections.observableArrayList();
    private ObservableList<String> allMetode = FXCollections.observableArrayList();
    public ObservableList<RAPDetailProperty> allDetail = FXCollections.observableArrayList();
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public void initialize() {
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        luasTanahColumn.setCellValueFactory(cellData ->cellData.getValue().luasTanahProperty());
        luasTanahColumn.setCellFactory(col -> getTableCell(qty));
        
        persentaseColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseProperty());
        persentaseColumn.setCellFactory(col -> getTableCell(qty));
        
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            hitungTotal();
            return propertyTable.getItems().get(param).statusProperty();
        }));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            propertyTable.refresh();
        });
        rm.getItems().add(refresh);
        propertyTable.setContextMenu(rm);
        propertyTable.setRowFactory((TableView<RAPDetailProperty> tableView) -> {
            final TableRow<RAPDetailProperty> row = new TableRow<RAPDetailProperty>(){
                @Override
                public void updateItem(RAPDetailProperty item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem edit = new MenuItem("Edit Persentase");
                        edit.setOnAction((ActionEvent e)->{
                            editPersentase(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            propertyTable.refresh();
                        });
                        if(kategoriPropertyCombo.isVisible())
                            rm.getItems().add(edit);
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    row.getItem().setStatus(!row.getItem().isStatus());
                    hitungTotal();
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
            allMetode.addAll("Rata-rata","Luas Tanah","Manual");
            metodeCombo.getSelectionModel().select("Rata-rata");

            allKategori.addAll(Function.getKategoriProperty());
            allKategori.add("Semua");
            kategoriPropertyCombo.getSelectionModel().clearSelection();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }

    }
    public void setProperty(String metode, List<RAPDetailProperty> listRapDetailProperty){
        if(metode!=null)
            metodeCombo.getSelectionModel().select(metode);
        else
            metodeCombo.getSelectionModel().selectFirst();
        allDetail.clear();
        allDetail.addAll(listRapDetailProperty);
        double totalProp = 0;
        double totalLuas = 0;
        for(RAPDetailProperty d: allDetail){
            totalProp = totalProp + 1;
            totalLuas = totalLuas + d.getLuasTanah();
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
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
            System.out.println("tes");
            Task<List<Property>> task = new Task<List<Property>>() {
                @Override 
                public List<Property> call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        List<String> status = new ArrayList<>();
                        status.add("Available");
                        status.add("Reserved");
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
                    RAPDetailProperty d = new RAPDetailProperty();
                    d.setKodeProperty(p.getKodeProperty());
                    d.setLuasTanah(p.getLuasTanah());
                    d.setPersentase(0);
                    d.setStatus(checkAll.isSelected());
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
    private void editPersentase(RAPDetailProperty d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/Edit.fxml");
        EditController x = loader.getController();
        x.textField.setText(qty.format(d.getPersentase()));
        Function.setNumberField(x.textField);
        x.saveButton.setOnAction((event) -> {
            metodeCombo.getSelectionModel().select("Manual");
            d.setStatus(true);
            d.setPersentase(Double.parseDouble(x.textField.getText().replaceAll(",", "")));
            hitungTotal();
            mainApp.closeDialog(stage, child);
        });
    }
    @FXML
    private void hitungTotal(){
        double totalProp = 0;
        double totalLuas = 0;
        for(RAPDetailProperty d: allDetail){
            if(d.isStatus()){
                totalProp = totalProp + 1;
                totalLuas = totalLuas + d.getLuasTanah();
            }
        }
        totalPropertyLabel.setText(qty.format(totalProp));
        totalLuasTanahLabel.setText(qty.format(totalLuas));
        if(metodeCombo.getSelectionModel().getSelectedItem().equals("Rata-rata")){
            for(RAPDetailProperty d: allDetail){
                if(d.isStatus()){
                    d.setPersentase(100/totalProp);
                }else{
                    d.setPersentase(0);
                }
            }
        }else if(metodeCombo.getSelectionModel().getSelectedItem().equals("Luas Tanah")){
            for(RAPDetailProperty d: allDetail){
                if(d.isStatus()){
                    d.setPersentase(100*d.getLuasTanah()/totalLuas);
                }else{
                    d.setPersentase(0);
                }
            }
        }else{
            for(RAPDetailProperty d: allDetail){
                if(!d.isStatus())
                    d.setPersentase(0);
            }
        }
        propertyTable.refresh();
    }
    @FXML
    private void checkAllHandle(){
        for(RAPDetailProperty d: allDetail){
            d.setStatus(checkAll.isSelected());
        }
        hitungTotal();
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
