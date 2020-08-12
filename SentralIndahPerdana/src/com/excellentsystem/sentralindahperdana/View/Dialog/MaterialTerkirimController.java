/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
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
public class MaterialTerkirimController  {

    @FXML private TableView<RencanaAnggaranBebanMaterial> rabTable;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> kodeBarangRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> namaBarangRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> satuanRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> qtyRABColumn;
    
    @FXML private TableView<PengirimanDetail> barangTerkirimTable;
    @FXML private TableColumn<PengirimanDetail, String> kodeBarangTerkirimColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaBarangTerkirimColumn;
    @FXML private TableColumn<PengirimanDetail, String> satuanTerkirimColumn;
    @FXML private TableColumn<PengirimanDetail, Number> qtyTerkirimColumn;
    
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private ObservableList<RencanaAnggaranBebanMaterial> allRAB = FXCollections.observableArrayList();
    private ObservableList<PengirimanDetail> allBarangTerkirim = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangRABColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangRABColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanRABColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        qtyRABColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyRABColumn.setCellFactory((c) -> Function.getTableCell());
        
        kodeBarangTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanTerkirimColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        qtyTerkirimColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyTerkirimColumn.setCellFactory((c) -> Function.getTableCell());
        
        rabTable.setItems(allRAB);
        barangTerkirimTable.setItems(allBarangTerkirim);
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setBarang(List<RencanaAnggaranBebanMaterial> listRab, String noPenjualan, String noUrut){
        Task<List<PengirimanDetail>> task = new Task<List<PengirimanDetail>>() {
            @Override 
            public List<PengirimanDetail> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<PengirimanDetail> listPengiriman = 
                        PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                        con, noPenjualan, noUrut, "true");
                    List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                        con, noPenjualan, noUrut, "true");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    List<PengirimanDetail> listDetail = new ArrayList<>();
                    for(Barang b : listBarang){
                        double qty = 0;
                        String satuan = b.getSatuanDasar();
                        for(PengirimanDetail d : listPengiriman){
                            if(b.getKodeBarang().equals(d.getKodeBarang())){
                                qty = qty + d.getQty();
                                satuan = d.getSatuan();
                            }
                        }
                        for(ReturDetail d : listRetur){
                            if(b.getKodeBarang().equals(d.getKodeBarang()))
                                qty = qty - d.getQty();
                        }
                        if(qty!=0){
                            PengirimanDetail d = new PengirimanDetail();
                            d.setNoPengiriman("");
                            d.setNoUrutPenjualan("");
                            d.setKodeBarang(b.getKodeBarang());
                            d.setNamaBarang(b.getNamaBarang());
                            d.setQty(qty);
                            d.setSatuan(satuan);
                            d.setNilai(0);
                            d.setBarang(b);
                            
                            List<Satuan> allSatuan = new ArrayList<>();
                            for(Satuan s : listSatuan){
                                if(d.getKodeBarang().equals(s.getKodeBarang()))
                                    allSatuan.add(s);
                            }
                            d.getBarang().setAllSatuan(allSatuan);
                            
                            listDetail.add(d);
                        }
                    }
                    return listDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                allRAB.clear();
                allRAB.addAll(listRab);
                allBarangTerkirim.clear();
                allBarangTerkirim.addAll(task.get());
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
