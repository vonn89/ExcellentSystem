/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.KategoriKeuanganDAO;
import com.excellentsystem.sentralindahperdana.DAO.KeuanganDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.KategoriKeuangan;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class LaporanKeuanganController  {

    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private Label saldoAwalField;
    @FXML private Label saldoAkhirField;
    @FXML private DatePicker tglMulai;
    @FXML private DatePicker tglAkhir;
    private double saldoAwal; 
    private double saldoAkhir;
    final TreeItem<Keuangan> root = new TreeItem<>();
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    public void initialize() {
        noKeuanganColumn.setCellValueFactory( param -> param.getValue().getValue().noKeuanganProperty());
        tipeKeuanganColumn.setCellValueFactory( param -> param.getValue().getValue().tipeKeuanganProperty());
        deskripsiColumn.setCellValueFactory( param -> param.getValue().getValue().keteranganProperty());
        kodeUserColumn.setCellValueFactory( param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        tglMulai.setConverter(Function.getTglConverter());
        tglMulai.setValue(LocalDate.now().minusMonths(1));
        tglMulai.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhir));
        tglAkhir.setConverter(Function.getTglConverter());
        tglAkhir.setValue(LocalDate.now());
        tglAkhir.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulai));
        
        allKeuangan.addListener((ListChangeListener.Change<? extends Keuangan> change) -> {
            searchKeuangan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKeuangan();
        });
        filterData.addAll(allKeuangan);
        
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
            getKeuangan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(printSummary, printDetail);
        }
        rowMenu.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rowMenu);
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKeuangan();
    }  
    @FXML
    private void getKeuangan(){
        Task<List<Keuangan>> task = new Task<List<Keuangan>>() {
            @Override 
            public List<Keuangan> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<Keuangan> listKeuangan = new ArrayList<>();
                    List<KategoriKeuangan> allKategori = KategoriKeuanganDAO.getAll(con);
                    for(KategoriKeuangan k : allKategori){
                        saldoAwal = saldoAwal + KeuanganDAO.getSaldoAwal(
                            con, tglMulai.getValue().toString(),k.getKodeKeuangan());
                        saldoAkhir = saldoAkhir + KeuanganDAO.getSaldoAkhir(
                            con, tglAkhir.getValue().toString(),k.getKodeKeuangan());
                        listKeuangan.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, k.getKodeKeuangan(),
                            tglMulai.getValue().toString(),tglAkhir.getValue().toString()));
                    }
                    return listKeuangan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allKeuangan.clear();
                allKeuangan.addAll(task.get());
                saldoAwalField.setText(df.format(saldoAwal));
                saldoAkhirField.setText(df.format(saldoAkhir));
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
    private void searchKeuangan() {
        try{
            filterData.clear();
            for (Keuangan temp : allKeuangan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoKeuangan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglKeuangan())))||
                        checkColumn(temp.getTipeKeuangan())||
                        checkColumn(temp.getKategori())||
                        checkColumn(temp.getKeterangan())||
                        checkColumn(df.format(temp.getJumlahRp()))||
                        checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
                }
            }
            setTable();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    private void setTable()throws Exception{
        if(keuanganTable.getRoot()!=null)
            keuanganTable.getRoot().getChildren().clear();
        List<String> kategori = new ArrayList<>();
        for(Keuangan temp: filterData){
            if(!kategori.contains(temp.getKategori()))
                kategori.add(temp.getKategori());
        }
        
        for(String temp : kategori){
            Keuangan head = new Keuangan();
            head.setNoKeuangan(temp);
            double total = 0;
            for(Keuangan keu : filterData){
                if(keu.getKategori().equals(temp))
                    total= total +keu.getJumlahRp();
            }
            head.setJumlahRp(total);
            TreeItem<Keuangan> parent = new TreeItem<>(head);
            for(Keuangan temp2: filterData){
                if(temp.equals(temp2.getKategori())){
                    TreeItem<Keuangan> child = new TreeItem<>(temp2);
                    parent.getChildren().addAll(child);
                }
            }
            root.getChildren().add(parent);
        }
        keuanganTable.setRoot(root);
    }   
    @FXML
    private void print(String jenisLaporan){
        try{
            allKeuangan.sort(Comparator.comparing(Keuangan::getKategori));
            Report report = new Report();
            report.printLaporanKeuangan(allKeuangan, tglMulai.getValue().toString(), tglAkhir.getValue().toString(),
                    jenisLaporan,saldoAwalField.getText(), saldoAkhirField.getText());
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
