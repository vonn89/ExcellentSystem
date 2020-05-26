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
import com.excellentsystem.AuriSteel.Model.Helper.UntungRugi;
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
public class LaporanUntungRugiController  {
    
    private DecimalFormat df = new DecimalFormat("###,##0");
    @FXML private StackPane pane; 
    private GridPane gridPane; 
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<Keuangan> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allRetur = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allHPP = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allPendapatanBeban = FXCollections.observableArrayList();
    private ObservableList<String> kategoriTransaksi = FXCollections.observableArrayList();
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
    public void setTanggal(LocalDate tglAwal,LocalDate tglAkhir){
        tglAwalPicker.setValue(tglAwal);
        tglAkhirPicker.setValue(tglAkhir);
    }
    @FXML
    private void getKeuangan(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    allPenjualan.clear();
                    allRetur.clear();
                    allHPP.clear();
                    allPendapatanBeban.clear();
                    allPenjualan.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Penjualan",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    allHPP.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "HPP",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    allRetur.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Retur Penjualan",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    allPendapatanBeban.addAll(KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Pendapatan/Beban",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    
                    kategoriTransaksi.clear();
                    for(Keuangan k : allPendapatanBeban){
                        if(!kategoriTransaksi.contains(k.getKategori()))
                            kategoriTransaksi.add(k.getKategori());
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
            gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 250, Priority.ALWAYS, HPos.RIGHT, true));
            
            int row = 17 + kategoriTransaksi.size();
            for(int i = 0 ; i<row ; i++){
                gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
                if(i%2==0)
                    addBackground(i);
            }
            
            addBoldText("Penjualan", 0, 0);
            double totalPenjualan = 0;
            double totalPenjualanCoil = 0;
            for(Keuangan k : allPenjualan){
                if(k.getKategori().equals("Penjualan")){
                    totalPenjualan = totalPenjualan + k.getJumlahRp();
                }else if(k.getKategori().equals("Penjualan Coil")){
                    totalPenjualanCoil = totalPenjualanCoil + k.getJumlahRp();
                }
            }
            addHyperLinkPenjualanText("Penjualan", 0, 1);
            addNormalText(df.format(totalPenjualan), 1, 1);
            
            addHyperLinkPenjualanCoilText("Penjualan Coil", 0, 2);
            addNormalText(df.format(totalPenjualanCoil), 1, 2);
            
            double totalRetur = 0;
            for(Keuangan k : allRetur){
                totalRetur = totalRetur - k.getJumlahRp();
            }
            addHyperLinkPendapatanBebanText("Retur Penjualan", 0, 3, allRetur);
            addNormalText(df.format(totalRetur), 1, 3);
            
            addBoldText("Total Penjualan", 0, 4);
            addBoldText(df.format(totalPenjualan+totalPenjualanCoil+totalRetur), 2, 4);
            
            double totalHPP = 0;
            double totalHppPenjualan = 0;
            double totalHppPenjualanCoil = 0;
            double totalHppReturPenjualan = 0;
            List<Keuangan> hppReturPenjualan = new ArrayList<>();
            for(Keuangan k : allHPP){
                totalHPP = totalHPP + k.getJumlahRp();
                if(k.getKategori().equals("Penjualan")){
                    totalHppPenjualan = totalHppPenjualan + k.getJumlahRp();
                }else if(k.getKategori().equals("Penjualan Coil")){
                    totalHppPenjualanCoil = totalHppPenjualanCoil + k.getJumlahRp();
                }else if(k.getKategori().equals("Retur Penjualan")){
                    hppReturPenjualan.add(k);
                    totalHppReturPenjualan = totalHppReturPenjualan + k.getJumlahRp();
                }
            }
            addBoldText("Harga Pokok Penjualan", 0, 6);
            
            addHyperLinkHPPPenjualanText("Penjualan", 0, 7);
            addNormalText(df.format(totalHppPenjualan), 1, 7);
            addHyperLinkHPPPenjualanCoilText("Penjualan Coil", 0, 8);
            addNormalText(df.format(totalHppPenjualanCoil), 1, 8);
            addHyperLinkPendapatanBebanText("Retur Penjualan", 0, 9, hppReturPenjualan);
            addNormalText(df.format(totalHppReturPenjualan), 1, 9);
            
            addBoldText("Total Harga Pokok Penjualan", 0, 10);
            addBoldText(df.format(totalHPP), 2, 10);
            
            addBoldText("Untung-Rugi Kotor", 0, 12);
            addBoldText(df.format(totalPenjualan+totalPenjualanCoil+totalRetur-totalHPP), 2, 12);
            
            int i = 14;
            addBoldText("Pendapatan-Beban", 0, i);
            i = i + 1;
            double totalPendapatanBeban = 0;
            for(String s : kategoriTransaksi){
                double pendapatanBeban = 0;
                List<Keuangan> temp = new ArrayList<>();
                for(Keuangan k : allPendapatanBeban){
                    if(k.getKategori().equalsIgnoreCase(s)){
                        pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                        totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                        temp.add(k);
                    }
                }
                addHyperLinkPendapatanBebanText(s, 0, i, temp);
                addNormalText(df.format(pendapatanBeban), 1, i);
                i = i +1;
            }
            addBoldText("Total Pendapatan-Beban", 0, i);
            addBoldText(df.format(totalPendapatanBeban), 2, i);
            i = i + 2;
            
            addBoldText("Untung-Rugi Bersih", 0, i);
            addBoldText(df.format(totalPenjualan+totalPenjualanCoil+totalRetur-totalHPP+totalPendapatanBeban), 2, i);
            
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
    private void addHyperLinkPenjualanText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiPenjualan.fxml");
            UntungRugiPenjualanController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkHPPPenjualanText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiHPPPenjualan.fxml");
            UntungRugiHPPPenjualanController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkPenjualanCoilText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiPenjualanCoil.fxml");
            UntungRugiPenjualanCoilController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkHPPPenjualanCoilText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiHPPPenjualanCoil.fxml");
            UntungRugiHPPPenjualanCoilController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkPendapatanBebanText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:12;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiPendapatanBeban.fxml");
            UntungRugiPendapatanBebanController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan, tglAwalPicker.getValue(), tglAkhirPicker.getValue());
        });
        gridPane.add(hyperlink, column, row);
    }
    @FXML
    private void print(){
        List<UntungRugi> ur = new ArrayList<>();
        
        double totalPenjualan = 0;
        double totalPenjualanCoil = 0;
        for(Keuangan k : allPenjualan){
            if(k.getKategori().equals("Penjualan")){
                totalPenjualan = totalPenjualan + k.getJumlahRp();
            }else if(k.getKategori().equals("Penjualan Coil")){
                totalPenjualanCoil = totalPenjualanCoil + k.getJumlahRp();
            }
        }
        double totalRetur = 0;
        for(Keuangan k : allRetur){
            totalRetur = totalRetur + k.getJumlahRp();
        }
        ur.add(new UntungRugi("Penjualan", "", ""));
        ur.add(new UntungRugi(" Penjualan", " "+df.format(totalPenjualan), ""));
        ur.add(new UntungRugi(" Penjualan Coil", " "+df.format(totalPenjualanCoil), ""));
        ur.add(new UntungRugi(" Retur Penjualan", " "+df.format(totalRetur), ""));
        ur.add(new UntungRugi("Total Penjualan", "", df.format(totalPenjualan+totalPenjualanCoil-totalRetur)));
        ur.add(new UntungRugi("", "", ""));

        double totalHppPenjualan = 0;
        double totalHppPenjualanCoil = 0;
        double totalHppReturPenjualan = 0;
        for(Keuangan k : allHPP){
            if(k.getKategori().equals("Penjualan")){
                totalHppPenjualan = totalHppPenjualan + k.getJumlahRp();
            }else if(k.getKategori().equals("Penjualan Coil")){
                totalHppPenjualanCoil = totalHppPenjualanCoil + k.getJumlahRp();
            }else if(k.getKategori().equals("Retur Penjualan")){
                totalHppReturPenjualan = totalHppReturPenjualan + k.getJumlahRp();
            }
        }
        ur.add(new UntungRugi("Harga Pokok Penjualan", "", ""));
        ur.add(new UntungRugi(" Penjualan", " "+df.format(totalHppPenjualan), ""));
        ur.add(new UntungRugi(" Penjualan Coil", " "+df.format(totalHppPenjualanCoil), ""));
        ur.add(new UntungRugi(" Retur Penjualan", " "+df.format(totalHppReturPenjualan), ""));
        ur.add(new UntungRugi("Total Harga Pokok Penjualan", "", df.format(totalHppPenjualan+totalHppPenjualanCoil-totalHppReturPenjualan)));
        ur.add(new UntungRugi("", "", ""));

        ur.add(new UntungRugi("Untung-Rugi Kotor", "", df.format(
            (totalPenjualan+totalPenjualanCoil-totalRetur)-
            (totalHppPenjualan+totalHppPenjualanCoil-totalHppReturPenjualan)
        )));
        ur.add(new UntungRugi("", "", ""));

        ur.add(new UntungRugi("Pendapatan-Beban", "", ""));
        double totalPendapatanBeban = 0;
        for(String s : kategoriTransaksi){
            double pendapatanBeban = 0;
            for(Keuangan k : allPendapatanBeban){
                if(k.getKategori().equalsIgnoreCase(s)){
                    pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                    totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                }
            }
            ur.add(new UntungRugi(" "+s, " "+df.format(pendapatanBeban), ""));
        }
        ur.add(new UntungRugi("Total Pendapatan-Beban", "", df.format(totalPendapatanBeban)));
        ur.add(new UntungRugi("", "", ""));
        ur.add(new UntungRugi("Untung-Rugi Bersih", "", df.format(
            (totalPenjualan+totalPenjualanCoil-totalRetur)-
            (totalHppPenjualan+totalHppPenjualanCoil-totalHppReturPenjualan)+
            totalPendapatanBeban
        )));

        try{
            Report report = new Report();
            report.printLaporanUntungRugi(ur, 
                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString()
            );
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
