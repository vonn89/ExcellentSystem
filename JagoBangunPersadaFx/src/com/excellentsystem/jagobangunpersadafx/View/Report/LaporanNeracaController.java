/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.KeuanganDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Neraca;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class LaporanNeracaController  {

    @FXML private StackPane pane; 
    private GridPane gridPane; 
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalAktivaLabel;
    @FXML private Label totalPassivaLabel;
    private ObservableList<Keuangan> keuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> piutang = FXCollections.observableArrayList();
    private ObservableList<Keuangan> asetLancar = FXCollections.observableArrayList();
    private ObservableList<Keuangan> asetTetap = FXCollections.observableArrayList();
    private ObservableList<Keuangan> hutang = FXCollections.observableArrayList();
    private ObservableList<Keuangan> modal = FXCollections.observableArrayList();
    private ObservableList<Keuangan> untungRugi = FXCollections.observableArrayList();
    private List<String> kategoriKeuangan = new ArrayList<>();
    private List<String> kategoriPiutang = new ArrayList<>();
    private List<String> kategoriAsetLancar = new ArrayList<>();
    private List<String> kategoriAsetTetap = new ArrayList<>();
    private List<String> kategoriHutang = new ArrayList<>();
    private Main mainApp;   
    public void initialize() {
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        rm.getItems().addAll(print);
        rm.getItems().addAll(refresh);
        pane.setOnContextMenuRequested((e) -> {
            rm.show(pane, e.getScreenX(), e.getScreenY());
        });
        pane.setOnMouseClicked((event) -> {
            rm.hide();
        });
    }   
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKeuangan();
    }  
    @FXML
    private void getKeuangan(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Property> listProperty = PropertyDAO.getAll(con);
                    keuangan.clear();
                    kategoriAsetLancar = Function.getKategoriProperty();
                    kategoriKeuangan = Function.getAllTipeKeuangan();
                    for(String s : kategoriKeuangan){
                        keuangan.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, s, "", tglAkhirPicker.getValue().toString()));
                    }
                    
                    piutang.clear();
                    piutang.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "PIUTANG", "", tglAkhirPicker.getValue().toString()));
                    for(Keuangan k : piutang){
                        if(!kategoriPiutang.contains(k.getKategori()))
                            kategoriPiutang.add(k.getKategori());
                    }
                    
                    asetLancar.clear();
                    asetLancar.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "ASET LANCAR", "", tglAkhirPicker.getValue().toString()));
                    for(Keuangan k : asetLancar){
                        for(Property p : listProperty){
                            if(k.getKodeProperty().equals(p.getKodeProperty())){
                                k.setProperty(p);
                            }
                        }
                    }
                    
                    asetTetap.clear();
                    asetTetap.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "ASET TETAP", "", tglAkhirPicker.getValue().toString()));
                    for(Keuangan k : asetTetap){
                        if(!kategoriAsetTetap.contains(k.getKategori()))
                            kategoriAsetTetap.add(k.getKategori());
                    }
                    
                    hutang.clear();
                    hutang.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "HUTANG", "", tglAkhirPicker.getValue().toString()));
                    for(Keuangan k : hutang){
                        if(!kategoriHutang.contains(k.getKategori()))
                            kategoriHutang.add(k.getKategori());
                    }
                    
                    modal.clear();
                    modal.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "MODAL", "", tglAkhirPicker.getValue().toString()));
                    double urditahan = KeuanganDAO.getSaldoAwalByDateAndTipeKeuangan(con, tglAwalPicker.getValue().toString(), "PENJUALAN")-
                                KeuanganDAO.getSaldoAwalByDateAndTipeKeuangan(con, tglAwalPicker.getValue().toString(), "HPP")+
                                KeuanganDAO.getSaldoAwalByDateAndTipeKeuangan(con, tglAwalPicker.getValue().toString(), "PENDAPATAN")-
                                KeuanganDAO.getSaldoAwalByDateAndTipeKeuangan(con, tglAwalPicker.getValue().toString(), "BEBAN");
                    
                    Keuangan keuurditahan = new Keuangan();
                    keuurditahan.setNoKeuangan("");
                    keuurditahan.setTglKeuangan(tglAwalPicker.getValue().toString());
                    keuurditahan.setTipeKeuangan("MODAL");
                    keuurditahan.setKodeProperty("");
                    keuurditahan.setKategori("Untung-Rugi Ditahan");
                    keuurditahan.setDeskripsi("Untung-Rugi Ditahan");
                    keuurditahan.setJumlahRp(urditahan);
                    keuurditahan.setKodeUser("System");
                    keuurditahan.setTglInput(tglAwalPicker.getValue().toString());
                    keuurditahan.setStatus("true");
                    keuurditahan.setTglBatal("2000-01-01 00:00:00");
                    keuurditahan.setUserBatal("");
                    modal.add(keuurditahan);
                    
                    untungRugi.clear();
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "PENJUALAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "HPP",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "PENDAPATAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "BEBAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                setGridPane();
                mainApp.closeLoading();
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
//    public void setGridPane(){
//        try(Connection con = Koneksi.getConnection()){
//            kategoriKeuangan = Function.getAllTipeKeuangan();
//            kategoriPiutang = Function.getKategoriPiutang();
//            kategoriHutang = Function.getKategoriHutang();
//            kategoriAsetLancar = Function.getKategoriProperty();
//            kategoriAsetTetap = Function.getKategoriAset();
//            List<Property> listProperty = PropertyDAO.getAll(con);
//            kategoriPiutang.add("Piutang Penjualan");
//            kategoriHutang.add("Terima Tanda Jadi");
//            kategoriHutang.add("Terima Down Payment");
//            
//            keuangan.clear();
//            keuangan.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con, "%", "2000-01-01", tglAkhirPicker.getValue().toString()));
//            
//            pane.getChildren().clear();
//            gridPane = new GridPane();
//            
//            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
//            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
//            gridPane.getColumnConstraints().add(new ColumnConstraints(50, 50, 50, Priority.ALWAYS, HPos.RIGHT, true));
//            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
//            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
//            
//            int row = 14 + kategoriKeuangan.size() + kategoriPiutang.size() + kategoriAsetLancar.size() + kategoriAsetTetap.size();
//            for(int i = 0 ; i<row ; i++){
//                gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
//                if(i%2==0)
//                    addBackground(i);
//            }
//            
//            addHeaderText("Aktiva", 0, 0);
//            int rowAktiva = 2;
//            
//            addBoldText("Kas/Bank", 0, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            double totalKasBank = 0;
//            for(String s : kategoriKeuangan){
//                double jumlahRp = 0;
//                List<Keuangan> listKeuangan = new ArrayList<>();
//                for(Keuangan k : keuangan){
//                    if(k.getTipeKeuangan().equals(s)){
//                        listKeuangan.add(k);
//                        jumlahRp = jumlahRp + k.getJumlahRp();
//                    }
//                }
//                addHyperLinkKeuanganText(s, 0, rowAktiva, listKeuangan);
//                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
//                totalKasBank = totalKasBank + jumlahRp;
//                rowAktiva = rowAktiva + 1;
//            }
//            addBoldText("Total Kas/Bank", 0, rowAktiva);
//            addBoldText(rp.format(totalKasBank), 1, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            
//            rowAktiva = rowAktiva + 1;
//            addBoldText("Piutang", 0, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            double totalPiutang = 0;
//            for(String s : kategoriPiutang){
//                double jumlahRp = 0;
//                for(Keuangan k : keuangan){
//                    if(k.getTipeKeuangan().equals("PIUTANG") && k.getKategori().equals(s))
//                        jumlahRp = jumlahRp + k.getJumlahRp();
//                }
//                addHyperLinkPiutangText(s, 0, rowAktiva);
//                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
//                totalPiutang = totalPiutang + jumlahRp;
//                rowAktiva = rowAktiva + 1;
//            }
//            addBoldText("Total Piutang", 0, rowAktiva);
//            addBoldText(rp.format(totalPiutang), 1, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            
//            rowAktiva = rowAktiva + 1;
//            addBoldText("Aset Lancar", 0, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            double totalAsetLancar = 0;
//            for(String s : kategoriAsetLancar){
//                double jumlahRp = 0;
//                for(Keuangan k : keuangan){
//                    if(k.getTipeKeuangan().equals("ASET LANCAR")){
//                        for(Property p : listProperty){
//                            if(k.getKodeProperty().equals(p.getKodeProperty())){
//                                k.setProperty(p);
//                            }
//                        }
//                        if(k.getProperty()!=null){
//                            if(k.getProperty().getKodeKategori().equals(s)){
//                                jumlahRp = jumlahRp + k.getJumlahRp();
//                            }
//                        }
//                    }
//                }
//                addNormalText(s, 0, rowAktiva);
//                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
//                totalAsetLancar = totalAsetLancar + jumlahRp;
//                rowAktiva = rowAktiva + 1;
//            }
//            addBoldText("Total Aset Lancar", 0, rowAktiva);
//            addBoldText(rp.format(totalAsetLancar), 1, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            
//            rowAktiva = rowAktiva + 1;
//            addBoldText("Aset Tetap", 0, rowAktiva);
//            rowAktiva = rowAktiva + 1;
//            double totalAsetTetap = 0;
//            for(String s : kategoriAsetTetap){
//                double jumlahRp = 0;
//                List<Keuangan> listKeuangan = new ArrayList<>();
//                for(Keuangan k : keuangan){
//                    if(k.getTipeKeuangan().equals("ASET TETAP") && k.getKategori().equals(s)){
//                        listKeuangan.add(k);
//                        jumlahRp = jumlahRp + k.getJumlahRp();
//                    }
//                }
//                addHyperLinkAsetTetapText(s, 0, rowAktiva, listKeuangan);
//                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
//                totalAsetTetap = totalAsetTetap + jumlahRp;
//                rowAktiva = rowAktiva + 1;
//            }
//            addBoldText("Total Aset Tetap", 0, rowAktiva);
//            addBoldText(rp.format(totalAsetTetap), 1, rowAktiva);
//            
//            addHeaderText("Passiva", 3, 0);
//            int rowPassiva = 2;
//            
//            addBoldText("Hutang", 3, rowPassiva);
//            rowPassiva = rowPassiva + 1;
//            double totalHutang = 0;
//            for(String s : kategoriHutang){
//                double jumlahRp = 0;
//                for(Keuangan k : keuangan){
//                    if(k.getTipeKeuangan().equals("HUTANG") && k.getKategori().equalsIgnoreCase(s)){
//                        System.out.println(k.getKategori()+"  -  "+k.getDeskripsi()+" -  "+rp.format(k.getJumlahRp()));
//                        jumlahRp = jumlahRp + k.getJumlahRp();
//                    }
//                }
//                addHyperLinkHutangText(s, 3, rowPassiva);
//                addNormalText(rp.format(jumlahRp), 4, rowPassiva);
//                totalHutang = totalHutang + jumlahRp;
//                rowPassiva = rowPassiva + 1;
//            }
//            addBoldText("Total Hutang", 3, rowPassiva);
//            addBoldText(rp.format(totalHutang), 4, rowPassiva);
//            rowPassiva = rowPassiva + 1;
//            
//            rowPassiva = rowPassiva + 1;
//            double totalModal = 0;
//            double totalUr = 0;
//            for(Keuangan k : keuangan){
//                if(k.getTipeKeuangan().equals("MODAL"))
//                    totalModal = totalModal + k.getJumlahRp();
//                if(tglAwalPicker.getValue().isBefore(LocalDate.parse(k.getTglKeuangan().substring(0, 10), DateTimeFormatter.ISO_DATE))||
//                        tglAwalPicker.getValue().isEqual(LocalDate.parse(k.getTglKeuangan().substring(0, 10), DateTimeFormatter.ISO_DATE))){
//                    if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("BEBAN"))
//                        totalUr = totalUr - k.getJumlahRp();
//                    else if(k.getTipeKeuangan().equals("PENJUALAN")||k.getTipeKeuangan().equals("PENDAPATAN"))
//                        totalUr = totalUr + k.getJumlahRp();
//                }else{
//                    if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("BEBAN"))
//                        totalModal = totalModal - k.getJumlahRp();
//                    else if(k.getTipeKeuangan().equals("PENJUALAN")||k.getTipeKeuangan().equals("PENDAPATAN"))
//                        totalModal = totalModal + k.getJumlahRp();
//                }
//            }
//            addBoldText("Modal", 3, rowPassiva);
//            addBoldText(rp.format(totalModal), 4, rowPassiva);
//            rowPassiva = rowPassiva + 1;
//            
//            rowPassiva = rowPassiva + 1;
//            addBoldText("Untung-Rugi", 3, rowPassiva);
//            addBoldText(rp.format(totalUr), 4, rowPassiva);
//            
//            totalAktivaLabel.setText(rp.format(totalKasBank+totalPiutang+totalAsetLancar+totalAsetTetap));
//            totalPassivaLabel.setText(rp.format(totalHutang+totalModal+totalUr));
//            
//            gridPane.setHgap(5);
//            gridPane.setVgap(5);
//            gridPane.setPadding(new Insets(15));
//            pane.getChildren().add(gridPane);
//        } catch (Exception ex) {
//            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
//        }
//    }
    public void setGridPane(){
        try {
            pane.getChildren().clear();
            gridPane = new GridPane();
            
            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(50, 50, 50, Priority.ALWAYS, HPos.RIGHT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
            
            int row = 14 + kategoriKeuangan.size() + kategoriPiutang.size() + kategoriAsetLancar.size() + kategoriAsetTetap.size();
            for(int i = 0 ; i<row ; i++){
                gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
                if(i%2==0)
                    addBackground(i);
            }
            
            addHeaderText("Aktiva", 0, 0);
            int rowAktiva = 2;
            
            addBoldText("Kas/Bank", 0, rowAktiva);
            rowAktiva = rowAktiva + 1;
            double totalKasBank = 0;
            for(String s : kategoriKeuangan){
                double jumlahRp = 0;
                List<Keuangan> listKeuangan = new ArrayList<>();
                for(Keuangan k : keuangan){
                    if(k.getTipeKeuangan().equals(s)){
                        listKeuangan.add(k);
                        jumlahRp = jumlahRp + k.getJumlahRp();
                    }
                }
                addHyperLinkKeuanganText(s, 0, rowAktiva, listKeuangan);
                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
                totalKasBank = totalKasBank + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Kas/Bank", 0, rowAktiva);
            addBoldText(rp.format(totalKasBank), 1, rowAktiva);
            rowAktiva = rowAktiva + 1;
            
            rowAktiva = rowAktiva + 1;
            addBoldText("Piutang", 0, rowAktiva);
            rowAktiva = rowAktiva + 1;
            double totalPiutang = 0;
            for(String s : kategoriPiutang){
                double jumlahRp = 0;
                for(Keuangan k : piutang){
                    if(k.getKategori().equals(s))
                        jumlahRp = jumlahRp + k.getJumlahRp();
                }
                addHyperLinkPiutangText(s, 0, rowAktiva);
                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
                totalPiutang = totalPiutang + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Piutang", 0, rowAktiva);
            addBoldText(rp.format(totalPiutang), 1, rowAktiva);
            rowAktiva = rowAktiva + 1;
            
            rowAktiva = rowAktiva + 1;
            addBoldText("Aset Lancar", 0, rowAktiva);
            rowAktiva = rowAktiva + 1;
            double totalAsetLancar = 0;
            for(String s : kategoriAsetLancar){
                double jumlahRp = 0;
                for(Keuangan k : asetLancar){
                    if(k.getProperty()!=null){
                        if(k.getProperty().getKodeKategori().equals(s)){
                            jumlahRp = jumlahRp + k.getJumlahRp();
                        }
                    }
                }
                addNormalText(s, 0, rowAktiva);
                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
                totalAsetLancar = totalAsetLancar + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Aset Lancar", 0, rowAktiva);
            addBoldText(rp.format(totalAsetLancar), 1, rowAktiva);
            rowAktiva = rowAktiva + 1;
            
            rowAktiva = rowAktiva + 1;
            addBoldText("Aset Tetap", 0, rowAktiva);
            rowAktiva = rowAktiva + 1;
            double totalAsetTetap = 0;
            for(String s : kategoriAsetTetap){
                double jumlahRp = 0;
                List<Keuangan> listKeuangan = new ArrayList<>();
                for(Keuangan k : asetTetap){
                    if(k.getKategori().equals(s)){
                        listKeuangan.add(k);
                        jumlahRp = jumlahRp + k.getJumlahRp();
                    }
                }
                addHyperLinkAsetTetapText(s, 0, rowAktiva, listKeuangan);
                addNormalText(rp.format(jumlahRp), 1, rowAktiva);
                totalAsetTetap = totalAsetTetap + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Aset Tetap", 0, rowAktiva);
            addBoldText(rp.format(totalAsetTetap), 1, rowAktiva);
            
            addHeaderText("Passiva", 3, 0);
            int rowPassiva = 2;
            
            addBoldText("Hutang", 3, rowPassiva);
            rowPassiva = rowPassiva + 1;
            double totalHutang = 0;
            for(String s : kategoriHutang){
                double jumlahRp = 0;
                for(Keuangan k : hutang){
                    if(k.getKategori().equals(s))
                        jumlahRp = jumlahRp + k.getJumlahRp();
                }
                addHyperLinkHutangText(s, 3, rowPassiva);
                addNormalText(rp.format(jumlahRp), 4, rowPassiva);
                totalHutang = totalHutang + jumlahRp;
                rowPassiva = rowPassiva + 1;
            }
            addBoldText("Total Hutang", 3, rowPassiva);
            addBoldText(rp.format(totalHutang), 4, rowPassiva);
            rowPassiva = rowPassiva + 1;
            
            rowPassiva = rowPassiva + 1;
            double totalModal = 0;
            for(Keuangan k : modal){
                if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("BEBAN"))
                    totalModal = totalModal - k.getJumlahRp();
                else
                    totalModal = totalModal + k.getJumlahRp();
            }
            addBoldText("Modal", 3, rowPassiva);
            addBoldText(rp.format(totalModal), 4, rowPassiva);
            rowPassiva = rowPassiva + 1;
            
            rowPassiva = rowPassiva + 1;
            double totalUr = 0;
            for(Keuangan k : untungRugi){
                if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("BEBAN"))
                    totalUr = totalUr - k.getJumlahRp();
                else
                    totalUr = totalUr + k.getJumlahRp();
            }
            addBoldText("Untung-Rugi", 3, rowPassiva);
            addBoldText(rp.format(totalUr), 4, rowPassiva);
            
            totalAktivaLabel.setText(rp.format(totalKasBank+totalPiutang+totalAsetLancar+totalAsetTetap));
            totalPassivaLabel.setText(rp.format(totalHutang+totalModal+totalUr));
            
            gridPane.setHgap(5);
            gridPane.setVgap(5);
            gridPane.setPadding(new Insets(15));
            pane.getChildren().add(gridPane);
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:seccolor5;");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addHeaderText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:20;"
                + "-fx-font-family: Georgia;");
        gridPane.add(label, column, row);
    }
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        gridPane.add(label, column, row);
    }
    private void addBoldText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;");
        gridPane.add(label, column, row);
    }
    private void addHyperLinkKeuanganText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaKeuangan.fxml");
            NeracaKeuanganController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan, tglAwalPicker.getValue(), tglAkhirPicker.getValue());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkPiutangText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaPiutang.fxml");
            NeracaPiutangController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setPiutang(tglAkhirPicker.getValue().toString(), text);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkHutangText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaHutang.fxml");
            NeracaHutangController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setHutang(tglAkhirPicker.getValue().toString(), text);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkAsetTetapText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaAsetTetap.fxml");
            NeracaAsetTetapController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkAsetLancarText(String text, int column, int row, List<Keuangan> keuangan){
//        Hyperlink hyperlink = new Hyperlink(text);
//        hyperlink.setStyle("-fx-font-size:12;"
//                + "-fx-text-fill:seccolor3;"
//                + "-fx-border-color:transparent;");
//        hyperlink.setOnAction((e) -> {
//            Stage stage = new Stage();
//            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaAsetLancar.fxml");
//            NeracaAsetLancarController x = loader.getController();
//            x.setMainApp(mainApp, mainApp.MainStage, stage);
//            x.setKeuangan(keuangan);
//        });
//        gridPane.add(hyperlink, column, row);
    }
    @FXML
    private void print(){
        List<Neraca> listNeraca = new ArrayList<>();
        
        listNeraca.add(new Neraca("Kas/Bank", "", "", ""));
        
        double totalKasBank = 0;
        for(String kk : kategoriKeuangan){
            double jumlahRp = 0;
            for(Keuangan k : keuangan){
                if(k.getTipeKeuangan().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+kk, " "+rp.format(jumlahRp), "", ""));
            
            totalKasBank = totalKasBank + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Kas/Bank", rp.format(totalKasBank), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Piutang", "", "", ""));
        
        double totalPiutang = 0;
        for(String kk : kategoriPiutang){
            double jumlahRp = 0;
            for(Keuangan k : piutang){
                if(k.getKategori().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+kk, " "+rp.format(jumlahRp), "", ""));
            
            totalPiutang = totalPiutang + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Piutang", rp.format(totalPiutang), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Aset Lancar", "", "", ""));
        
        double totalAsetLancar = 0;
        for(String s : kategoriAsetLancar){
            double jumlahRp = 0;
            for(Keuangan k : asetLancar){
                if(k.getKategori().equals(s))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+s, " "+rp.format(jumlahRp), "", ""));
            totalAsetLancar = totalAsetLancar + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Aset Lancar", rp.format(totalAsetLancar), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Aset Tetap", "", "", ""));
        
        double totalAset = 0;
        for(String kk : kategoriAsetTetap){
            double jumlahRp = 0;
            for(Keuangan k : asetTetap){
                if(k.getKategori().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+kk, " "+rp.format(jumlahRp), "", ""));
            
            totalAset = totalAset + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Aset Tetap", rp.format(totalAset), "", ""));
        
        int j = 0;
        if(listNeraca.get(j)!=null)
            listNeraca.get(j).setPassiva("Hutang");
        else
            listNeraca.add(new Neraca("", "", "Hutang", ""));
        j++;
        
        double totalHutang = 0;
        for(String kk : kategoriHutang){
            double jumlahRp = 0;
            for(Keuangan k : hutang){
                if(k.getKategori().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            
            if(listNeraca.get(j)!=null){
                listNeraca.get(j).setPassiva(" "+kk);
                listNeraca.get(j).setJumlahPassiva(" "+rp.format(jumlahRp));
            }else
                listNeraca.add(new Neraca("", "", " "+kk, ""+rp.format(jumlahRp)));
            
            totalHutang = totalHutang + jumlahRp;
            j++;
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Total Hutang");
            listNeraca.get(j).setJumlahPassiva(rp.format(totalHutang));
        }else
            listNeraca.add(new Neraca("", "", "Total Hutang", rp.format(totalHutang)));
        j = j + 2;
        
        double totalModal = 0;
        for(Keuangan k : modal){
            totalModal = totalModal + k.getJumlahRp();
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Modal");
            listNeraca.get(j).setJumlahPassiva(rp.format(totalModal));
        }else
            listNeraca.add(new Neraca("", "", "Modal", rp.format(totalModal)));
        j = j + 2;
        
        double totalUr = 0;
        for(Keuangan k : untungRugi){
            if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("BEBAN"))
                totalUr = totalUr - k.getJumlahRp();
            else
                totalUr = totalUr + k.getJumlahRp();
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Untung/Rugi");
            listNeraca.get(j).setJumlahPassiva(rp.format(totalUr));
        }else
            listNeraca.add(new Neraca("", "", "Untung/Rugi", rp.format(totalUr)));
        
        
        try{
            PrintOut printOut = new PrintOut();
            printOut.printLaporanNeraca(listNeraca, tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())),
                totalAktivaLabel.getText(), totalPassivaLabel.getText()
            );
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
