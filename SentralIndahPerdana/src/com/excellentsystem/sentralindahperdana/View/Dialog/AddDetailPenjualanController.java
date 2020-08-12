/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddDetailPenjualanController  {

    @FXML public TableView<PenjualanDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanDetail, String> satuanColumn;
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public ObservableList<PenjualanDetail> allPenjualanDetail = FXCollections.observableArrayList();
    public void initialize() {
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getPekerjaan().satuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        penjualanDetailTable.setItems(allPenjualanDetail);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void searchPenjualanDetail(String noPenjualan){
        Task<List<PenjualanDetail>> task = new Task<List<PenjualanDetail>>() {
            @Override 
            public List<PenjualanDetail> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanDetail> listPenjualanDetail = PenjualanDetailDAO.getAllByNoPenjualan(con, noPenjualan);
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    for(PenjualanDetail d : listPenjualanDetail){
                        for(Pekerjaan p : listPekerjaan){
                            if(d.getKodePekerjaan().equals(p.getKodePekerjaan()))
                                d.setPekerjaan(p);
                        }
                    }
                    return listPenjualanDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allPenjualanDetail.clear();
                allPenjualanDetail.addAll(task.get());
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
    public void close(){
        mainApp.closeDialog(owner, stage);
    }   
}
