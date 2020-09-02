/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.KeuanganDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tgl;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Helper.Neraca;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.sql.Connection;
import java.text.DecimalFormat;
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

    private DecimalFormat df = new DecimalFormat("###,##0");
    @FXML private StackPane pane; 
    private GridPane gridPane; 
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalAktivaLabel;
    @FXML private Label totalPassivaLabel;
    private ObservableList<Keuangan> keuangan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> piutang = FXCollections.observableArrayList();
    private ObservableList<Keuangan> stokBahan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> stokBarang = FXCollections.observableArrayList();
    private ObservableList<Keuangan> asetTetap = FXCollections.observableArrayList();
    private ObservableList<Keuangan> hutang = FXCollections.observableArrayList();
    private ObservableList<Keuangan> modal = FXCollections.observableArrayList();
    private ObservableList<Keuangan> untungRugi = FXCollections.observableArrayList();
    private List<String> kategoriKeuangan = new ArrayList<>();
    private List<String> kategoriPiutang = new ArrayList<>();
    private List<String> kategoriAsetTetap = new ArrayList<>();
    private List<String> kategoriHutang = new ArrayList<>();
    private Main mainApp;   
    public void initialize() {
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglAwalPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getKeuangan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
        }
        rm.getItems().addAll(refresh);
        pane.setOnContextMenuRequested((e) -> {
            rm.show(pane, e.getScreenX(), e.getScreenY());
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
                    keuangan.clear();
                    piutang.clear();
                    stokBahan.clear();
                    stokBarang.clear();
                    asetTetap.clear();
                    hutang.clear();
                    modal.clear();
                    untungRugi.clear();
                    List<Keuangan> allKeuangan = KeuanganDAO.getAllByTanggal(con, "", tglAkhirPicker.getValue().toString());
                    for(Keuangan k : allKeuangan){
                        if(k.getTipeKeuangan().equalsIgnoreCase("Piutang"))
                            piutang.add(k);
                        else if(k.getTipeKeuangan().equalsIgnoreCase("Stok Bahan"))
                            stokBahan.add(k);
                        else if(k.getTipeKeuangan().equalsIgnoreCase("Stok Barang"))
                            stokBarang.add(k);
                        else if(k.getTipeKeuangan().equalsIgnoreCase("Aset Tetap"))
                            asetTetap.add(k);
                        else if(k.getTipeKeuangan().equalsIgnoreCase("Hutang"))
                            hutang.add(k);
                        else if(k.getTipeKeuangan().equalsIgnoreCase("Modal"))
                            modal.add(k);
                        else if(!k.getTipeKeuangan().equalsIgnoreCase("Penjualan")&&
                                !k.getTipeKeuangan().equalsIgnoreCase("Retur Penjualan")&&
                                !k.getTipeKeuangan().equalsIgnoreCase("HPP")&&
                                !k.getTipeKeuangan().equalsIgnoreCase("Pendapatan/Beban")){
                            keuangan.add(k);
                        }
                    }
                    double urDitahan = 
                        KeuanganDAO.getSaldoAwal(con, tglAwalPicker.getValue().toString(), "Penjualan")-
                        KeuanganDAO.getSaldoAwal(con, tglAwalPicker.getValue().toString(), "Retur Penjualan")-
                        KeuanganDAO.getSaldoAwal(con, tglAwalPicker.getValue().toString(), "HPP")+
                        KeuanganDAO.getSaldoAwal(con, tglAwalPicker.getValue().toString(), "Pendapatan/Beban");
                    Keuangan keuurditahan = new Keuangan();
                    keuurditahan.setNoKeuangan("");
                    keuurditahan.setTglKeuangan(tglAwalPicker.getValue().toString());
                    keuurditahan.setTipeKeuangan("MODAL");
                    keuurditahan.setKategori("Untung-Rugi Ditahan");
                    keuurditahan.setDeskripsi("Untung-Rugi Ditahan");
                    keuurditahan.setJumlahRp(urDitahan);
                    keuurditahan.setKodeUser("System");
                    modal.add(keuurditahan);
                    
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Penjualan",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Retur Penjualan",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "HPP",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    untungRugi.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Pendapatan/Beban",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    
                    for(Keuangan k : keuangan){
                        if(!kategoriKeuangan.contains(k.getTipeKeuangan()))
                            kategoriKeuangan.add(k.getTipeKeuangan());
                    }
                    for(Keuangan k : piutang){
                        if(!kategoriPiutang.contains(k.getKategori()))
                            kategoriPiutang.add(k.getKategori());
                    }
                    for(Keuangan k : asetTetap){
                        if(!kategoriAsetTetap.contains(k.getKategori()))
                            kategoriAsetTetap.add(k.getKategori());
                    }
                    for(Keuangan k : hutang){
                        if(!kategoriHutang.contains(k.getKategori()))
                            kategoriHutang.add(k.getKategori());
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            setGridPane();
            mainApp.closeLoading();
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    } 
    public void setGridPane(){
        try {
            pane.getChildren().clear();
            gridPane = new GridPane();
            
            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(50, 50, 50, Priority.ALWAYS, HPos.RIGHT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
            
            int row = 15 + kategoriKeuangan.size() + kategoriPiutang.size() + kategoriAsetTetap.size();
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
            for(String kk : kategoriKeuangan){
                double jumlahRp = 0;
                List<Keuangan> listKeuangan = new ArrayList<>();
                for(Keuangan k : keuangan){
                    if(k.getTipeKeuangan().equals(kk)){
                        listKeuangan.add(k);
                        jumlahRp = jumlahRp + k.getJumlahRp();
                    }
                }
                addHyperLinkText(kk, 0, rowAktiva, listKeuangan);
                addNormalText(df.format(jumlahRp), 1, rowAktiva);
                totalKasBank = totalKasBank + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Kas/Bank", 0, rowAktiva);
            addBoldText(df.format(totalKasBank), 1, rowAktiva);
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
                addNormalText(df.format(jumlahRp), 1, rowAktiva);
                totalPiutang = totalPiutang + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Piutang", 0, rowAktiva);
            addBoldText(df.format(totalPiutang), 1, rowAktiva);
            rowAktiva = rowAktiva + 1;
            
            double totalAsetLancar = 0;
            rowAktiva = rowAktiva + 1;
            addBoldText("Stok Persediaan Bahan Baku & Barang", 0, rowAktiva);
            rowAktiva = rowAktiva + 1;
            
            double stokBahanSMG = 0;
            double stokBahanSBY = 0;
            for(Keuangan k : stokBahan){
                if(k.getKategori().equals("SMG"))
                    stokBahanSMG = stokBahanSMG + k.getJumlahRp();
                if(k.getKategori().equals("SBY"))
                    stokBahanSBY = stokBahanSBY + k.getJumlahRp();
            }
            addHyperLinkStokBahanText("Stok Persedian Bahan Baku - SMG","SMG", 0, rowAktiva);
            addNormalText(df.format(stokBahanSMG), 1, rowAktiva);
            totalAsetLancar = totalAsetLancar + stokBahanSMG;
            rowAktiva = rowAktiva + 1;
            
            addHyperLinkStokBahanText("Stok Persedian Bahan Baku - SBY","SBY", 0, rowAktiva);
            addNormalText(df.format(stokBahanSBY), 1, rowAktiva);
            totalAsetLancar = totalAsetLancar + stokBahanSBY;
            rowAktiva = rowAktiva + 1;
            
            double stokBarangSMG = 0;
            double stokBarangSBY = 0;
            for(Keuangan k : stokBarang){
                if(k.getKategori().equals("SMG"))
                    stokBarangSMG = stokBarangSMG + k.getJumlahRp();
                if(k.getKategori().equals("SBY"))
                    stokBarangSBY = stokBarangSBY + k.getJumlahRp();
            }
            addHyperLinkStokBarangText("Stok Persedian Barang - SMG","SMG", 0, rowAktiva);
            addNormalText(df.format(stokBarangSMG), 1, rowAktiva);
            totalAsetLancar = totalAsetLancar + stokBarangSMG;
            rowAktiva = rowAktiva + 1;
            
            addHyperLinkStokBarangText("Stok Persedian Barang - SBY","SBY", 0, rowAktiva);
            addNormalText(df.format(stokBarangSBY), 1, rowAktiva);
            totalAsetLancar = totalAsetLancar + stokBarangSBY;
            rowAktiva = rowAktiva + 1;
            
            addBoldText("Total Stok Persediaan Bahan Baku & Barang", 0, rowAktiva);
            addBoldText(df.format(totalAsetLancar), 1, rowAktiva);
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
                addNormalText(df.format(jumlahRp), 1, rowAktiva);
                totalAsetTetap = totalAsetTetap + jumlahRp;
                rowAktiva = rowAktiva + 1;
            }
            addBoldText("Total Aset Tetap", 0, rowAktiva);
            addBoldText(df.format(totalAsetTetap), 1, rowAktiva);
            
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
                addNormalText(df.format(jumlahRp), 4, rowPassiva);
                totalHutang = totalHutang + jumlahRp;
                rowPassiva = rowPassiva + 1;
            }
            addBoldText("Total Hutang", 3, rowPassiva);
            addBoldText(df.format(totalHutang), 4, rowPassiva);
            rowPassiva = rowPassiva + 1;
            
            rowPassiva = rowPassiva + 1;
            double totalModal = 0;
            for(Keuangan k : modal){
                totalModal = totalModal + k.getJumlahRp();
            }
            addBoldHyperLinkText("Modal", 3, rowPassiva, modal);
            addBoldText(df.format(totalModal), 4, rowPassiva);
            rowPassiva = rowPassiva + 1;
            
            rowPassiva = rowPassiva + 1;
            double totalUr = 0;
            for(Keuangan k : untungRugi){
                if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("Retur Penjualan"))
                    totalUr = totalUr - k.getJumlahRp();
                else
                    totalUr = totalUr + k.getJumlahRp();
            }
            addBoldURHyperLinkText("Untung-Rugi", 3, rowPassiva, untungRugi);
            addBoldText(df.format(totalUr), 4, rowPassiva);
            
            totalAktivaLabel.setText(df.format(totalKasBank+totalPiutang+totalAsetLancar+totalAsetTetap));
            totalPassivaLabel.setText(df.format(totalHutang+totalModal+totalUr));
            
            gridPane.setPadding(new Insets(10));
            pane.getChildren().add(gridPane);
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor5,20%);");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addHeaderText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;"
                + "-fx-font-family: Georgia;");
        gridPane.add(label, column, row);
    }
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:12;");
        gridPane.add(label, column, row);
    }
    private void addBoldText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:12;"
                + "-fx-font-weight:bold;");
        gridPane.add(label, column, row);
    }
    private void addBoldHyperLinkText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-font-weight:bold;"
                + "-fx-border-color:transparent;"
        );
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaKeuangan.fxml");
            NeracaKeuanganController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan, tglAwalPicker.getValue(), tglAkhirPicker.getValue());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
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
    private void addHyperLinkStokBahanText(String text, String gudang, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaStokBahan.fxml");
            NeracaStokBahanController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getBahan(tglAkhirPicker.getValue(),gudang);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkStokBarangText(String text, String gudang, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/NeracaStokBarang.fxml");
            NeracaStokBarangController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getBarang(tglAkhirPicker.getValue(), gudang);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addBoldURHyperLinkText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-font-weight:bold;"
                + "-fx-border-color:transparent;"
        );
        hyperlink.setOnAction((e) -> {
            LaporanUntungRugiController controller = mainApp.showLaporanUntungRugi();
            controller.setTanggal(tglAwalPicker.getValue(), tglAkhirPicker.getValue());
        });
        gridPane.add(hyperlink, column, row);
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
            listNeraca.add(new Neraca(" "+kk, " "+df.format(jumlahRp), "", ""));
            
            totalKasBank = totalKasBank + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Kas/Bank", df.format(totalKasBank), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Piutang", "", "", ""));
        
        double totalPiutang = 0;
        for(String kk : kategoriPiutang){
            double jumlahRp = 0;
            for(Keuangan k : piutang){
                if(k.getKategori().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+kk, " "+df.format(jumlahRp), "", ""));
            
            totalPiutang = totalPiutang + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Piutang", df.format(totalPiutang), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Stok Persediaan", "", "", ""));
        
        double totalBahan = 0;
        for(Keuangan k : stokBahan){
            totalBahan = totalBahan + k.getJumlahRp();
        }
        listNeraca.add(new Neraca(" Stok Persediaan Bahan Baku", " "+df.format(totalBahan), "", ""));
        
        double totalBarang = 0;
        for(Keuangan k : stokBarang){
            totalBarang = totalBarang + k.getJumlahRp();
        }
        listNeraca.add(new Neraca(" Stok Persediaan Barang", " "+df.format(totalBarang), "", ""));
        
        listNeraca.add(new Neraca("Total Stok Persediaan", df.format(totalBahan + totalBarang), "", ""));
        
        listNeraca.add(new Neraca("", "", "", ""));
        
        listNeraca.add(new Neraca("Aset Tetap", "", "", ""));
        
        double totalAset = 0;
        for(String kk : kategoriAsetTetap){
            double jumlahRp = 0;
            for(Keuangan k : asetTetap){
                if(k.getKategori().equals(kk))
                    jumlahRp = jumlahRp + k.getJumlahRp();
            }
            listNeraca.add(new Neraca(" "+kk, " "+df.format(jumlahRp), "", ""));
            
            totalAset = totalAset + jumlahRp;
        }
        listNeraca.add(new Neraca("Total Aset Tetap", df.format(totalAset), "", ""));
        
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
                listNeraca.get(j).setJumlahPassiva(" "+df.format(jumlahRp));
            }else
                listNeraca.add(new Neraca("", "", " "+kk, ""+df.format(jumlahRp)));
            
            totalHutang = totalHutang + jumlahRp;
            j++;
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Total Hutang");
            listNeraca.get(j).setJumlahPassiva(df.format(totalHutang));
        }else
            listNeraca.add(new Neraca("", "", "Total Hutang", df.format(totalHutang)));
        j = j + 2;
        
        double totalModal = 0;
        for(Keuangan k : modal){
            totalModal = totalModal + k.getJumlahRp();
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Modal");
            listNeraca.get(j).setJumlahPassiva(df.format(totalModal));
        }else
            listNeraca.add(new Neraca("", "", "Modal", df.format(totalModal)));
        j = j + 2;
        
        double totalUr = 0;
        for(Keuangan k : untungRugi){
            if(k.getTipeKeuangan().equals("HPP")||k.getTipeKeuangan().equals("Retur Penjualan"))
                totalUr = totalUr - k.getJumlahRp();
            else
                totalUr = totalUr + k.getJumlahRp();
        }
        if(listNeraca.get(j)!=null){
            listNeraca.get(j).setPassiva("Untung/Rugi");
            listNeraca.get(j).setJumlahPassiva(df.format(totalUr));
        }else
            listNeraca.add(new Neraca("", "", "Untung/Rugi", df.format(totalUr)));
        
        
        try{
            Report report = new Report();
            report.printLaporanNeraca(listNeraca, 
                tglAwalPicker.getValue().toString(), tgl.format(tglBarang.parse(tglAkhirPicker.getValue().toString())),
                totalAktivaLabel.getText(), totalPassivaLabel.getText()
            );
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
