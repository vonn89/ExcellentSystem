/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class NeracaKeuanganController  {
    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private Label saldoAwalLabel;
    @FXML private Label saldoAkhirLabel;
    private LocalDate tglAwal;
    private LocalDate tglAkhir;
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    final TreeItem<Keuangan> root = new TreeItem<>();
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());
        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().deskripsiProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglKeuanganColumn.setComparator(Function.sortDate(tglLengkap));
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            setTable();
        });
        rm.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rm);
    }    
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
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
    public void setKeuangan(List<Keuangan> temp, LocalDate tglAwal, LocalDate tglAkhir){
        allKeuangan.clear();
        allKeuangan.addAll(temp);
        this.tglAwal = tglAwal;
        this.tglAkhir = tglAkhir;
        setTable();
    }
    private void setTable(){
        if(keuanganTable.getRoot()!=null)
            keuanganTable.getRoot().getChildren().clear();
        double saldoAwal = 0;
        double saldoAkhir = 0;
        List<String> allKategori = new ArrayList<>();
        for(Keuangan keu : allKeuangan){
            if(!allKategori.contains(keu.getKategori()))
                allKategori.add(keu.getKategori());
            saldoAkhir = saldoAkhir + keu.getJumlahRp();
            if(LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isAfter(tglAwal.minusDays(1))&&
                    LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isBefore(tglAkhir.plusDays(1))){
            }else
                saldoAwal = saldoAwal + keu.getJumlahRp();
        }
        saldoAwalLabel.setText(rp.format(saldoAwal));
        saldoAkhirLabel.setText(rp.format(saldoAkhir));
        for(String s : allKategori){
            Keuangan k = new Keuangan();
            k.setNoKeuangan(s);
            TreeItem<Keuangan> parent = new TreeItem<>(k);
            double total = 0;
            for(Keuangan keu : allKeuangan){
                if(LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isAfter(tglAwal.minusDays(1))&&
                    LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isBefore(tglAkhir.plusDays(1))&&
                    keu.getKategori().equals(s)){
                    parent.getChildren().add(new TreeItem<>(keu));
                    total = total + keu.getJumlahRp();
                }
            }
            parent.getValue().setJumlahRp(total);
            if(total!=0)
                root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    } 
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
