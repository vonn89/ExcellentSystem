/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang;

import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.Day;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class SetHariKerjaController  {

    @FXML private TableView<Day> dayTable;
    @FXML private TableColumn<Day, Boolean> statusColumn;
    @FXML private TableColumn<Day, String> tanggalColumn;
    @FXML private TableColumn<Day, String> hariColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private Label periodeLabel;
    @FXML private Label totalHariLabel;
    private ObservableList<Day> allDay = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        tanggalColumn.setCellValueFactory(cellData -> cellData.getValue().tanggalProperty());
        hariColumn.setCellValueFactory(cellData -> cellData.getValue().hariProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        statusColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            hitungTotal();
            return dayTable.getItems().get(v).statusProperty();
        }));
        dayTable.setRowFactory((TableView<Day> tableView) -> {
            final TableRow<Day> row = new TableRow<Day>(){};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().isStatus())
                            row.getItem().setStatus(false);
                        else
                            row.getItem().setStatus(true);
                    }
                }
            });
            return row;
        });
        dayTable.setItems(allDay);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setPeriode(String periode, List<Day> listDay){
        periodeLabel.setText(periode);
        allDay.clear();
        allDay.addAll(listDay);
        hitungTotal();
    }
    private void hitungTotal(){
        double qty = 0;
        for(Day d : dayTable.getItems()){
            if(d.isStatus())
                qty = qty + 1;
        }
        totalHariLabel.setText("Total Hari Kerja : "+rp.format(qty));
    }
    @FXML
    private void checkAllHandle(){
        for(Day d: dayTable.getItems()){
            d.setStatus(checkAll.isSelected());
        }
        dayTable.refresh();
        hitungTotal();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
