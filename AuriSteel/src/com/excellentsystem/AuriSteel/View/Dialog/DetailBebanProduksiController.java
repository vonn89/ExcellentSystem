/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BebanProduksiDetailDAO;
import com.excellentsystem.AuriSteel.DAO.BebanProduksiHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.BebanProduksiDetail;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailBebanProduksiController  {

    @FXML private TableView<BebanProduksiDetail> bebanProduksiTable;
    @FXML private TableColumn<BebanProduksiDetail, String> noBebanColumn;
    @FXML private TableColumn<BebanProduksiDetail, String> tglBebanColumn;
    @FXML private TableColumn<BebanProduksiDetail, String> keteranganColumn;
    @FXML private TableColumn<BebanProduksiDetail, Number> jumlahRpColumn;
    
    @FXML private Label totalBebanField;
    public ObservableList<BebanProduksiDetail> allBebanProduksi = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getBebanProduksiHead().noBebanProduksiProperty());
        tglBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getBebanProduksiHead().keteranganProperty());
        tglBebanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getBebanProduksiHead().getTglBebanProduksi())));
            } catch (Exception ex) {
                return null;
            }
        });
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().getBebanProduksiHead().keteranganProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTableCell());
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        bebanProduksiTable.setItems(allBebanProduksi);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void hitungTotal(){
        double total= 0;
        for(BebanProduksiDetail temp : allBebanProduksi){
            total = total + temp.getJumlahRp();
        }
        totalBebanField.setText(df.format(total));
    }
    public void setDetailBebanProduksi(String noProduksi){
        try(Connection con = Koneksi.getConnection()){
            List<BebanProduksiDetail> allBeban = BebanProduksiDetailDAO.getAllNoProduksiAndStatus(con, noProduksi, "true");
            for(BebanProduksiDetail d : allBeban){
                d.setBebanProduksiHead(BebanProduksiHeadDAO.get(con, d.getNoBebanProduksi()));
            }
            allBebanProduksi.clear();
            allBebanProduksi.addAll(allBeban);
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
