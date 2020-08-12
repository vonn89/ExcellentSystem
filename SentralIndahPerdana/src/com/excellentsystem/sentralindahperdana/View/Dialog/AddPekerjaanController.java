/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddPekerjaanController  {
    @FXML private TableView<Pekerjaan> pekerjaanTable;
    @FXML private TableColumn<Pekerjaan, String> kodePekerjaanColumn;
    @FXML private TableColumn<Pekerjaan, String> kodeKategoriColumn;
    @FXML private TableColumn<Pekerjaan, String> namaPekerjaanColumn;
    @FXML private TableColumn<Pekerjaan, String> keteranganColumn;
    @FXML private TableColumn<Pekerjaan, String> satuanColumn;
    @FXML private TableColumn<Pekerjaan, Number> hargaColumn;
    
    @FXML private TextField kodePekerjaanField;
    @FXML private TextField namaPekerjaanField;
    @FXML public TextArea keteranganField;
    @FXML public TextField qtyField;
    @FXML public TextField satuanField;
    @FXML public TextField hargaJualField;
    @FXML public TextField totalField;
    @FXML private TextField searchField;
    @FXML public Button addButton;
    public Pekerjaan pekerjaan;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private final ObservableList<Pekerjaan> allPekerjaan = FXCollections.observableArrayList();
    private final ObservableList<Pekerjaan> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriPekerjaanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        hargaColumn.setCellValueFactory(cellData -> cellData.getValue().labaProperty());
        hargaColumn.setCellFactory((c) -> Function.getTableCell());
        hargaJualField.setOnKeyReleased((event) -> {
            try{
                String string = hargaJualField.getText();
                DecimalFormat df = new DecimalFormat("###,##0.######");
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        hargaJualField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        hargaJualField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    hargaJualField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                hargaJualField.end();
            }catch(NumberFormatException e){
                hargaJualField.undo();
            }
            hitungTotal();
        });
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(NumberFormatException e){
                qtyField.undo();
            }
            hitungTotal();
        });
        pekerjaanTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectPekerjaan(newValue);
        });
        allPekerjaan.addListener((ListChangeListener.Change<? extends Pekerjaan> change) -> {
            searchPekerjaan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPekerjaan();
        });
        filterData.addAll(allPekerjaan);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        getPekerjaan();
        pekerjaanTable.setItems(filterData);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void getPekerjaan(){
        Task<List<Pekerjaan>> task = new Task<List<Pekerjaan>>() {
            @Override 
            public List<Pekerjaan> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PekerjaanDAO.getAllByStatus(con,"true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                selectPekerjaan(null);
                allPekerjaan.clear();
                allPekerjaan.addAll(task.get());
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
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchPekerjaan() {
        filterData.clear();
        for (Pekerjaan temp : allPekerjaan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodePekerjaan())||
                    checkColumn(temp.getNamaPekerjaan())||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(temp.getKategoriPekerjaan()))
                    filterData.add(temp);
            }
        }
    }
    @FXML
    private void hitungTotal(){
        if(qtyField.getText().equals(""))
            qtyField.setText("0");
        if(hargaJualField.getText().equals(""))
            hargaJualField.setText("0");
        totalField.setText(df.format(
            Double.parseDouble(qtyField.getText().replaceAll(",", ""))* 
            Double.parseDouble(hargaJualField.getText().replaceAll(",", ""))
        ));
    }
    private void selectPekerjaan(Pekerjaan value){
        pekerjaan = null;
        kodePekerjaanField.setText("");
        namaPekerjaanField.setText("");
        keteranganField.setText("");
        satuanField.setText("");
        hargaJualField.setText("0");
        qtyField.setText("0");
        totalField.setText("0");
        if(value!=null){
            pekerjaan = value;
            kodePekerjaanField.setText(pekerjaan.getKodePekerjaan());
            namaPekerjaanField.setText(pekerjaan.getNamaPekerjaan());
            satuanField.setText(pekerjaan.getSatuan());
            hitungTotal();
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    } 
    
}
