/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PelangganFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.Day;
import com.excellentsystem.TokoEmasJagoPusat.Model.KategoriFiktif;
import com.excellentsystem.TokoEmasJagoPusat.Model.PelangganFiktif;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddNewPenjualanFiktifCabangController  {

    @FXML public ComboBox<String> kodeCabangCombo;
    @FXML public ComboBox<String> bulanCombo;
    @FXML public ComboBox<String> tahunCombo;
    
    @FXML private TextField totalHariLabel;
    @FXML public TextField totalPenjualanField;
    @FXML public TextField minPenjualanField;
    @FXML public TextField maksPenjualanField;
    
    @FXML private TextField totalPelangganField;
    @FXML private TextField totalBarangField;
    
    @FXML public Button saveButton;
    
    private DateTimeFormatter month = DateTimeFormatter.ofPattern("MMMM");
    private DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
    private ObservableList<String> allBulan = FXCollections.observableArrayList();
    private ObservableList<String> allTahun = FXCollections.observableArrayList();
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    public List<Day> listDay = new ArrayList<>();
    public List<PelangganFiktif> listPelangganFiktif = new ArrayList<>();
    public List<KategoriFiktif> listKategoriFiktif = new ArrayList<>();
    public List<BarangFiktif> listBarangFiktif = new ArrayList<>();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        Function.setNumberField(totalPenjualanField, rp);
        Function.setNumberField(minPenjualanField, rp);
        Function.setNumberField(maksPenjualanField, rp);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        kodeCabangCombo.setItems(allCabang);
        bulanCombo.setItems(allBulan);
        tahunCombo.setItems(allTahun);
        setData();
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con = KoneksiPusat.getConnection()){
                    listBarangFiktif.clear();
                    listBarangFiktif.addAll(BarangFiktifDAO.getAll(con));
                    listKategoriFiktif.clear();
                    listKategoriFiktif.addAll(KategoriFiktifDAO.getAll(con));
                    return CabangDAO.getAll(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            allCabang.clear();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            allBulan.clear();
            LocalDate date = LocalDate.now();
            for(int i = 0 ; i < 12; i++){
                allBulan.add(date.format(month));
                date = date.plusMonths(1);
            }
            allTahun.clear();
            LocalDate d = date.minusYears(6);
            for(int i = 0 ; i < 10; i++){
                allTahun.add(d.format(year));
                d = d.plusYears(1);
            }
            hitung();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void hitung(){
        double hari = 0;
        for(Day d : listDay){
            if(d.isStatus())
                hari = hari + 1;
        }
        totalHariLabel.setText(rp.format(hari)+" Hari");
        totalBarangField.setText(rp.format(listBarangFiktif.size())+" Barang");
        totalPelangganField.setText(rp.format(listPelangganFiktif.size())+" Pelanggan");
    }
    @FXML
    private void changePeriode(){
        if(bulanCombo.getSelectionModel().getSelectedItem()!=null&&
                tahunCombo.getSelectionModel().getSelectedItem()!=null){
            listDay.clear();
            LocalDate start = LocalDate.parse("01 "+bulanCombo.getSelectionModel().getSelectedItem()+
                    " "+tahunCombo.getSelectionModel().getSelectedItem(), DateTimeFormatter.ofPattern("dd MMMM yyyy"));
            LocalDate date = start;
            for(int i = 1; i <= 31 ; i++){
                Day day = new Day();
                day.setTanggal(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
                day.setHari(date.format(DateTimeFormatter.ofPattern("EEEE")));
                day.setStatus(true);
                listDay.add(day);
                date = date.plusDays(1);

                if(date.getMonthValue()!=start.getMonthValue())
                    break;
            }
            hitung();
        }
    }
    @FXML
    private void changeCabang(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                Thread.sleep(timeout);
                try(Connection con  = KoneksiPusat.getConnection()){
                    listPelangganFiktif = PelangganFiktifDAO.getAllByCabang(
                            con, kodeCabangCombo.getSelectionModel().getSelectedItem());
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            hitung();
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setHariKerja(){
        if(bulanCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Periode bulan belum dipilih");
        else if(tahunCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Periode tahun belum dipilih");
        else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PenjualanFiktifCabang/SetHariKerja.fxml");
            SetHariKerjaController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setPeriode(bulanCombo.getSelectionModel().getSelectedItem()+
                        " "+tahunCombo.getSelectionModel().getSelectedItem(), listDay);
            child.setOnHiding((event) -> {
                mainApp.closeDialog(stage, child);
                hitung();
            });
        }
    }
    @FXML
    private void dataPelanggan(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PenjualanFiktifCabang/DataPelangganFiktif.fxml");
            DataPelangganFiktifController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setPelanggan(kodeCabangCombo.getSelectionModel().getSelectedItem(), listPelangganFiktif);
            child.setOnHiding((event) -> {
                listPelangganFiktif.clear();
                listPelangganFiktif.addAll(controller.allPelanggan);
                mainApp.closeDialog(stage, child);
                hitung();
            });
        }else{
            mainApp.showMessage(Modality.NONE, "Warning", "Cabang belum dipilih");
        }
    }
    @FXML
    private void dataBarang(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/PenjualanFiktifCabang/DataBarangFiktif.fxml");
        DataBarangFiktifController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(listBarangFiktif, listKategoriFiktif);
        child.setOnHiding((event) -> {
            listBarangFiktif.clear();
            listBarangFiktif.addAll(controller.listBarang);
            listKategoriFiktif.clear();
            listKategoriFiktif.addAll(controller.allKategori);
            mainApp.closeDialog(stage, child);
            hitung();
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
