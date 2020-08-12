/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
import javafx.stage.Modality;
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
    @FXML private TreeTableColumn<Keuangan, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
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
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().tipeKeuanganProperty());
        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem printSummary = new MenuItem("Print Laporan Summary");
        printSummary.setOnAction((ActionEvent event) -> {
            print("Summary");
        });
        MenuItem printDetail = new MenuItem("Print Laporan Detail");
        printDetail.setOnAction((ActionEvent event) -> {
            print("Detail");
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            setTable();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(printSummary, printDetail);
        }
        rowMenu.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rowMenu);
    }    
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight()-80);
            stage.setWidth(mainApp.screenSize.getWidth()-80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
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
        saldoAwalLabel.setText(df.format(saldoAwal));
        saldoAkhirLabel.setText(df.format(saldoAkhir));
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
    private void print(String jenisLaporan){
        try{
            allKeuangan.sort(Comparator.comparing(Keuangan::getKategori));
            List<Keuangan> keuangan = new ArrayList<>();
            for(Keuangan keu : allKeuangan){
                if(LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isAfter(tglAwal.minusDays(1))&&
                    LocalDate.parse(keu.getTglKeuangan().substring(0,10)).isBefore(tglAkhir.plusDays(1))){
                    keuangan.add(keu);
                }
            }
            Report report = new Report();
            report.printLaporanKeuangan(keuangan, tglAwal.toString(), tglAkhir.toString(),
                    jenisLaporan, saldoAwalLabel.getText(), saldoAkhirLabel.getText());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
