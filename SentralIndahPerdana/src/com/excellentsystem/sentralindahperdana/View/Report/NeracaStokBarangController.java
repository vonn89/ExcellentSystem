/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.LogBarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.StokBarangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.LogBarang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NeracaStokBarangController {
    
    @FXML private TableView<StokBarang> barangTable;
    @FXML private TableColumn<StokBarang, String> kategoriBarangColumn;
    @FXML private TableColumn<StokBarang, String> kodeBarangColumn;
    @FXML private TableColumn<StokBarang, String> namaBarangColumn;
    @FXML private TableColumn<StokBarang, String> satuanColumn;
    @FXML private TableColumn<StokBarang, Number> nilaiColumn;
    @FXML private TableColumn<StokBarang, Number> mutasiAkhirColumn;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalNilaiField;
    
    private ObservableList<StokBarang> allBarang = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        kategoriBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kategoriBarangProperty());
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getBarang().satuanDasarProperty());
        nilaiColumn.setCellValueFactory(cellData -> {
            double nilai = 0;
            if(cellData.getValue().getLogBarang().getStokAkhir()!=0)
                nilai = cellData.getValue().getLogBarang().getNilaiAkhir()/
                    cellData.getValue().getLogBarang().getStokAkhir();
            return new SimpleDoubleProperty(nilai);
        });
        nilaiColumn.setCellFactory(col -> Function.getTableCell());
        mutasiAkhirColumn.setCellValueFactory( cellData -> cellData.getValue().stokAkhirProperty());
        mutasiAkhirColumn.setCellFactory(col -> Function.getTableCell());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory((TableView<StokBarang> tableView) -> {
            final TableRow<StokBarang> row = new TableRow<StokBarang>(){
                @Override
                public void updateItem(StokBarang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem kartu = new MenuItem("Lihat Kartu Stok");
                        kartu.setOnAction((ActionEvent e)->{
                            showKartuStok(item);
                        });
                        MenuItem logBarang = new MenuItem("Lihat Log Barang");
                        logBarang.setOnAction((ActionEvent e)->{
                            showLogBarang(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rm.getItems().addAll(print);
                        }
                        rm.getItems().addAll(kartu,logBarang,refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        showKartuStok(row.getItem());
                }
            });
            return row;
        });
        barangTable.setItems(allBarang);
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
    public void getBarang(LocalDate tglAkhir){
        Task<List<StokBarang>> task = new Task<List<StokBarang>>() {
            @Override 
            public List<StokBarang> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<StokBarang> temp = StokBarangDAO.getAllByTanggal(con, tglAkhir.toString());
                    List<LogBarang> listLogBarang = LogBarangDAO.getAllByDate(con, tglAkhir.toString());
                    for(StokBarang s : temp){
                        for(Barang b : listBarang){
                            if(b.getKodeBarang().equals(s.getKodeBarang()))
                                s.setBarang(b);
                        }
                        for(LogBarang l : listLogBarang){
                            if(s.getKodeBarang().equals(l.getKodeBarang())){
                                s.setLogBarang(l);
                            }
                        }
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allBarang.clear();
                allBarang.addAll(task.get());
                hitungTotal();
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
    private void hitungTotal(){
        double totalQty =0;
        double totalHPP = 0;
        for(StokBarang temp : allBarang){
            totalQty = totalQty + temp.getStokAkhir();
            totalHPP = totalHPP + temp.getLogBarang().getNilaiAkhir();
        }
        totalQtyField.setText(df.format(totalQty));
        totalNilaiField.setText(df.format(totalHPP));
    }
    private void showLogBarang(StokBarang b){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Report/LogBarang.fxml");
        LogBarangController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setBarang(b.getBarang());
    }
    private void showKartuStok(StokBarang b){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Report/KartuStokBarang.fxml");
        KartuStokBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.getBarang(b);
    }
    private void print(){
        try{
            List<StokBarang> listStokBarang = new ArrayList<>();
            for(StokBarang d : allBarang){
                listStokBarang.add(d);
            }
            Report report = new Report();
            report.printLaporanBarang(listStokBarang);
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
