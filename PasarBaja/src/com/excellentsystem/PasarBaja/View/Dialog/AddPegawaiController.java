/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.View.Dialog;

import com.excellentsystem.PasarBaja.DAO.PegawaiDAO;
import com.excellentsystem.PasarBaja.DAO.PegawaiDAO;
import com.excellentsystem.PasarBaja.Function;
import com.excellentsystem.PasarBaja.Koneksi;
import com.excellentsystem.PasarBaja.Main;
import static com.excellentsystem.PasarBaja.Main.df;
import com.excellentsystem.PasarBaja.Model.Pegawai;
import com.excellentsystem.PasarBaja.Model.Pegawai;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddPegawaiController  {

    @FXML public TableView<Pegawai> pegawaiTable;
    @FXML private TableColumn<Pegawai, String> kodePegawaiColumn;
    @FXML private TableColumn<Pegawai, String> namaColumn;
    @FXML private TableColumn<Pegawai, String> alamatColumn;
    @FXML private TableColumn<Pegawai, String> kotaColumn;
    @FXML private TableColumn<Pegawai, String> emailColumn;
    @FXML private TableColumn<Pegawai, String> noTelpColumn;
    @FXML private TableColumn<Pegawai, String> noHandphoneColumn;
    @FXML private TableColumn<Pegawai, String> identitasColumn;
    @FXML private TableColumn<Pegawai, String> noIdentitasColumn;
    @FXML private TextField searchField;
    private ObservableList<Pegawai> allPegawai = FXCollections.observableArrayList();
    private ObservableList<Pegawai> filterData = FXCollections.observableArrayList();
    private Main mainApp; 
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodePegawaiColumn.setCellValueFactory(cellData -> cellData.getValue().kodePegawaiProperty());
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        kotaColumn.setCellValueFactory(cellData -> cellData.getValue().kotaProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        noTelpColumn.setCellValueFactory(cellData ->cellData.getValue().noTelpProperty());
        noHandphoneColumn.setCellValueFactory(cellData ->cellData.getValue().noHandphoneProperty());
        identitasColumn.setCellValueFactory(cellData ->cellData.getValue().identitasProperty());
        noIdentitasColumn.setCellValueFactory(cellData ->cellData.getValue().noIdentitasProperty());
        
        allPegawai.addListener((ListChangeListener.Change<? extends Pegawai> change) -> {
            searchPegawai();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPegawai();
        });
        filterData.addAll(allPegawai);
        pegawaiTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getPegawai(String jabatan){
        Task<List<Pegawai>> task = new Task<List<Pegawai>>() {
            @Override 
            public List<Pegawai> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Pegawai> allPegawai = PegawaiDAO.getAllByStatus(con, "true");
                    List<Pegawai> listPegawai = new ArrayList<>();
                    for(Pegawai p : allPegawai){
                        if(p.getJabatan().equals(jabatan))
                            listPegawai.add(p);
                    }
                    return listPegawai;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allPegawai.clear();
            allPegawai.addAll(task.getValue());
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
    private void searchPegawai() {
        filterData.clear();
        for (Pegawai temp : allPegawai) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodePegawai())||
                    checkColumn(temp.getNama())||
                    checkColumn(temp.getAlamat())||
                    checkColumn(temp.getKota())||
                    checkColumn(temp.getEmail())||
                    checkColumn(temp.getNoTelp())||
                    checkColumn(temp.getNoHandphone())||
                    checkColumn(temp.getIdentitas())||
                    checkColumn(temp.getNoIdentitas())
                        )
                    filterData.add(temp);
            }
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
    
}
