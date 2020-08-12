/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class BebanPenjualanTerbayarController  {

    @FXML private TableView<RencanaAnggaranBebanPenjualan> rencanaAnggaranBebanPenjualanTable;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, String> kategoriColumn;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, Number> jumlahRpColumn;
    @FXML private Label totalAnggaranLabel;
    
    @FXML private TreeTableView<BebanPenjualan> bebanPenjualanTable;
    @FXML private TreeTableColumn<BebanPenjualan, String> kategoriBebanColumn;
    @FXML private TreeTableColumn<BebanPenjualan, Number> jumlahRpBebanColumn;
    @FXML private Label totalBebanPenjualanLabel;
    
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    final TreeItem<BebanPenjualan> root = new TreeItem<>();
    public ObservableList<RencanaAnggaranBebanPenjualan> allRencanaAnggaranBeban = FXCollections.observableArrayList();
    public ObservableList<BebanPenjualan> allBeban = FXCollections.observableArrayList();
    public void initialize() {
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory((c) -> Function.getTableCell());
        
        kategoriBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        jumlahRpBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jumlahRpProperty());
        jumlahRpBebanColumn.setCellFactory((c) -> Function.getTreeTableCell());
        
        rencanaAnggaranBebanPenjualanTable.setItems(allRencanaAnggaranBeban);
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setBebanPenjualan(List<RencanaAnggaranBebanPenjualan> b, String noPenjualan, String noUrut){
        Task<List<BebanPenjualan>> task = new Task<List<BebanPenjualan>>() {
            @Override 
            public List<BebanPenjualan> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                        con, noPenjualan, noUrut, "true");
                    return listBebanPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                allRencanaAnggaranBeban.clear();
                allRencanaAnggaranBeban.addAll(b);
                allBeban.clear();
                allBeban.addAll(task.get());
                setTable();
                hitungTotal();
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
    private void setTable()throws Exception{
        if(bebanPenjualanTable.getRoot()!=null)
            bebanPenjualanTable.getRoot().getChildren().clear();
        List<String> kategori = new ArrayList<>();
        for(BebanPenjualan temp: allBeban){
            if(!kategori.contains(temp.getKategori()))
                kategori.add(temp.getKategori());
        }
        for(String temp : kategori){
            BebanPenjualan b = new BebanPenjualan();
            b.setKeterangan(temp);
            double total = 0;
            TreeItem<BebanPenjualan> parent = new TreeItem<>(b);
            for(BebanPenjualan temp2: allBeban){
                if(temp.equals(temp2.getKategori())){
                    TreeItem<BebanPenjualan> child = new TreeItem<>(temp2);
                    parent.getChildren().addAll(child);
                    total = total + temp2.getJumlahRp();
                }
            }
            parent.getValue().setJumlahRp(total);
            root.getChildren().add(parent);
        }
        bebanPenjualanTable.setRoot(root);
    } 
    private void hitungTotal(){
        double totalAnggaran = 0;
        double totalBeban = 0;
        for(RencanaAnggaranBebanPenjualan d : allRencanaAnggaranBeban){
            totalAnggaran = totalAnggaran + d.getJumlahRp();
        }
        for(BebanPenjualan b : allBeban){
            totalBeban = totalBeban + b.getJumlahRp();
        }
        totalBebanPenjualanLabel.setText(df.format(totalBeban));
        totalAnggaranLabel.setText(df.format(totalAnggaran));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
