/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.KeuanganDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.UntungRugi;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
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
public class LaporanUntungRugiController  {
    @FXML private StackPane pane; 
    private GridPane gridPane; 
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<Keuangan> allPenjualan = FXCollections.observableArrayList();
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
        
        final ContextMenu rowMenu = new ContextMenu();
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
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        pane.setOnContextMenuRequested((e) -> {
            rowMenu.show(pane, e.getScreenX(), e.getScreenY());
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
                    allPenjualan.clear();
                    allHPP.clear();
                    allPendapatanBeban.clear();
                    List<Keuangan> allUntungRugi = KeuanganDAO.getAllByTipeKeuanganAndTanggal(con,
                            "Untung/Rugi",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString());
                    for(Keuangan k : allUntungRugi){
                        if(k.getKategori().equals("Penjualan"))
                            allPenjualan.add(k);
                        else if(k.getKategori().equals("HPP"))
                            allHPP.add(k);
                        else
                            allPendapatanBeban.add(k);
                    }
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
            task.getException().printStackTrace();
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
            
            int row = 10 + kategoriTransaksi.size();
            for(int i = 0 ; i<row ; i++){
                gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
                if(i%2==0)
                    addBackground(i);
            }
            
            double totalPenjualan = 0;
            for(Keuangan k : allPenjualan){
                totalPenjualan = totalPenjualan + k.getJumlahRp();
            }
            addHyperLinkPenjualanText("Penjualan", 0, 0, allPenjualan);
            addBoldText(df.format(totalPenjualan), 2, 0);
            
            double totalHPP = 0;
            for(Keuangan k : allHPP){
                totalHPP = totalHPP + k.getJumlahRp();
            }
            addHyperLinkHPPPenjualanText("Harga Pokok Penjualan", 0, 2, allHPP);
            addBoldText(df.format(totalHPP), 2, 2);
            
            addBoldText("Untung-Rugi Kotor", 0, 4);
            addBoldText(df.format(totalPenjualan+totalHPP), 2, 4);
            
            int i = 6;
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
            addBoldText(df.format(totalPenjualan+totalHPP+totalPendapatanBeban), 2, i);
            
            gridPane.setPadding(new Insets(10));
            pane.getChildren().add(gridPane);
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    @FXML
    private void print(){
        List<UntungRugi> ur = new ArrayList<>();
        
        double totalPenjualan = 0;
        for(Keuangan k : allPenjualan){
            totalPenjualan = totalPenjualan + k.getJumlahRp();
        }
        ur.add(new UntungRugi("Penjualan", "", df.format(totalPenjualan)));
        ur.add(new UntungRugi("", "", ""));

        double totalHPP = 0;
        for(Keuangan k : allHPP){
            totalHPP = totalHPP + k.getJumlahRp();
        }
        ur.add(new UntungRugi("Harga Pokok Penjualan", "", df.format(totalHPP)));
        ur.add(new UntungRugi("", "", ""));

        ur.add(new UntungRugi("Untung-Rugi Kotor", "", df.format(totalPenjualan+totalHPP)));
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
            ur.add(new UntungRugi(" "+s, df.format(pendapatanBeban), ""));
        }
        ur.add(new UntungRugi("Total Pendapatan-Beban", "", df.format(totalPendapatanBeban)));
        ur.add(new UntungRugi("", "", ""));
        ur.add(new UntungRugi("Untung-Rugi Bersih", "", df.format(totalPenjualan+totalHPP+totalPendapatanBeban)));

        try{
            Report report = new Report();
            report.printLaporanUntungRugi(ur, 
                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString()
            );
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor4,90%);");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;");
        gridPane.add(label, column, row);
    }
    private void addBoldText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;"
                + "-fx-font-weight:bold;");
        gridPane.add(label, column, row);
    }
    
    private void addHyperLinkPenjualanText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:14;"
                + "-fx-font-weight:bold;"
                + "-fx-text-fill:seccolor1;"
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
    private void addHyperLinkHPPPenjualanText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:14;"
                + "-fx-text-fill:seccolor1;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiHPP.fxml");
            UntungRugiHPPController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan, tglAwalPicker.getValue(), tglAkhirPicker.getValue());
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkPendapatanBebanText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:14;"
                + "-fx-text-fill:seccolor1;"
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
}
