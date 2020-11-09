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
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.UntungRugi;
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
 * @author Excellent
 */
public class LaporanUntungRugiController  {
    @FXML private StackPane pane; 
    private GridPane gridPane; 
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<Keuangan> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allNilaiTanahDanBangunan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allPendapatan = FXCollections.observableArrayList();
    private ObservableList<Keuangan> allBeban = FXCollections.observableArrayList();
    private ObservableList<String> kategoriTransaksi = FXCollections.observableArrayList();
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
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Combined");
                    statusProperty.add("Mapped");
                    statusProperty.add("Deleted");
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    allPenjualan.clear();
                    allNilaiTanahDanBangunan.clear();
                    allPendapatan.clear();
                    allBeban.clear();
                    allPenjualan.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "PENJUALAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    List<Keuangan> allHPP = KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "HPP",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString());
                    allPendapatan.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "PENDAPATAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    allBeban.addAll(KeuanganDAO.getAllByTipeKeuanganAndDate(con,
                            "BEBAN",tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString()));
                    for(Keuangan k : allPenjualan){
                        if(!k.getKodeProperty().equals("")){
                            for(Property p : listProperty){
                                if(k.getKodeProperty().equals(p.getKodeProperty()))
                                    k.setProperty(p);
                            }
                        }
                    }
                    for(Keuangan k : allHPP){
                        if(!k.getKodeProperty().equals("")){
                            for(Property p : listProperty){
                                if(k.getKodeProperty().equals(p.getKodeProperty()))
                                    k.setProperty(p);
                            }
                        }
                        allNilaiTanahDanBangunan.add(k);
                    }
                    for(Keuangan k : allPendapatan){
                        if(!k.getKodeProperty().equals("")){
                            for(Property p : listProperty){
                                if(k.getKodeProperty().equals(p.getKodeProperty()))
                                    k.setProperty(p);
                            }
                        }
                    }
                    for(Keuangan k : allBeban){
                        if(!k.getKodeProperty().equals("")){
                            for(Property p : listProperty){
                                if(k.getKodeProperty().equals(p.getKodeProperty()))
                                    k.setProperty(p);
                            }
                        }
                    }
                    return null;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                kategoriTransaksi.clear();
                kategoriTransaksi.addAll(Function.getKategoriTransaksi());
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
            addBoldHyperLinkText("Penjualan", 0, 0, allPenjualan);
            addBoldText(rp.format(totalPenjualan), 2, 0);
            
            double totalHPP = 0;
            for(Keuangan k : allNilaiTanahDanBangunan){
                totalHPP = totalHPP + k.getJumlahRp();
            }
            addBoldHyperLinkText("Harga Pokok Penjualan", 0, 2, allNilaiTanahDanBangunan);
            addBoldText(rp.format(totalHPP), 2, 2);
                        
            addBoldText("Untung-Rugi Kotor", 0, 4);
            addBoldText(rp.format(totalPenjualan-totalHPP), 2, 4);
            
            addBoldText("Pendapatan-Beban", 0, 6);
            int i = 7;
            double totalPendapatanBeban = 0;
            for(String s : kategoriTransaksi){
                double pendapatanBeban = 0;
                List<Keuangan> temp = new ArrayList<>();
                for(Keuangan k : allPendapatan){
                    if(k.getKategori().equals(s)){
                        pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                        totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                        temp.add(k);
                    }
                }
                for(Keuangan k : allBeban){
                    if(k.getKategori().equals(s)){
                        pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                        totalPendapatanBeban = totalPendapatanBeban - k.getJumlahRp();
                        temp.add(k);
                    }
                }
                addHyperLinkText(s, 0, i, temp);
                addNormalText(rp.format(pendapatanBeban), 1, i);
                i = i +1;
            }
            addBoldText("Total Pendapatan-Beban", 0, i);
            addBoldText(rp.format(totalPendapatanBeban), 2, i);
            i = i + 2;
            
            addBoldText("Untung-Rugi Bersih", 0, i);
            addBoldText(rp.format(totalPenjualan-totalHPP+totalPendapatanBeban), 2, i);
            
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
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        gridPane.add(label, column, row);
    }
    private void addBoldText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;");
        gridPane.add(label, column, row);
    }
    private void addBoldHyperLinkText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-weight:bold;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/DetailKeuangan.fxml");
            DetailKeuanganController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan);
        });
        gridPane.add(hyperlink, column, row);
    }
    private void addHyperLinkText(String text, int column, int row, List<Keuangan> keuangan){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/DetailKeuangan.fxml");
            DetailKeuanganController x = loader.getController();
            x.setMainApp(mainApp, mainApp.MainStage, stage);
            x.setKeuangan(keuangan);
        });
        gridPane.add(hyperlink, column, row);
    }
    @FXML
    private void print(){
        List<UntungRugi> ur = new ArrayList<>();
        
        ur.add(new UntungRugi("", "", ""));
        double totalPenjualan = 0;
        for(Keuangan k : allPenjualan){
            totalPenjualan = totalPenjualan + k.getJumlahRp();
        }
        ur.add(new UntungRugi(" Penjualan", " "+rp.format(totalPenjualan), ""));

        double totalHppPenjualan = 0;
        for(Keuangan k : allNilaiTanahDanBangunan){
            totalHppPenjualan = totalHppPenjualan + k.getJumlahRp();
        }
        ur.add(new UntungRugi(" Harga Pokok Penjualan", " "+rp.format(totalHppPenjualan), ""));

        ur.add(new UntungRugi("Untung-Rugi Kotor", "", rp.format(
            totalPenjualan-totalHppPenjualan
        )));
        ur.add(new UntungRugi("", "", ""));

        ur.add(new UntungRugi("Pendapatan-Beban", "", ""));
        double totalPendapatanBeban = 0;
        for(String s : kategoriTransaksi){
            double pendapatanBeban = 0;
            for(Keuangan k : allPendapatan){
                if(k.getKategori().equalsIgnoreCase(s)){
                    pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                    totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                }
            }
            for(Keuangan k : allBeban){
                if(k.getKategori().equalsIgnoreCase(s)){
                    pendapatanBeban = pendapatanBeban + k.getJumlahRp();
                    totalPendapatanBeban = totalPendapatanBeban - k.getJumlahRp();
                }
            }
            ur.add(new UntungRugi(" "+s, " "+rp.format(pendapatanBeban), ""));
        }
        ur.add(new UntungRugi("Total Pendapatan-Beban", "", rp.format(totalPendapatanBeban)));
        ur.add(new UntungRugi("", "", ""));
        ur.add(new UntungRugi("Untung-Rugi Bersih", "", rp.format(
            totalPenjualan-totalHppPenjualan+
            totalPendapatanBeban
        )));

        try{
            PrintOut printOut = new PrintOut();
            printOut.printLaporanUntungRugi(ur, 
                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString()
            );
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
}
