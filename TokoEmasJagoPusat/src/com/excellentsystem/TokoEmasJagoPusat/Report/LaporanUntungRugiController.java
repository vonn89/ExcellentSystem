/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class LaporanUntungRugiController  {

    @FXML private StackPane stackPane;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> cabangCombo;
    
    private ObservableList<String> cabang = FXCollections.observableArrayList();
    private GridPane gridPane; 
    public void initialize() {
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        cabangCombo.setItems(cabang);
        getCabang();
    }    
    private void getCabang(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                cabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().selectFirst();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void getData(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            Cabang c = CabangDAO.get(conPusat, cabangCombo.getSelectionModel().getSelectedItem());
            try(Connection conCabang = KoneksiCabang.getConnection(c)){
                double penjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double hppPenjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());

                double penjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double hppPenjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());

                double penjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Penjualan Ciok");
                double hppPenjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "HPP Penjualan Ciok");

                double pendapatanKartuMember = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Kartu Member", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double pendapatanServis = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Servis", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanPoin = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Poin", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double pendapatanLainLain = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Lain-lain", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanGaji = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Gaji", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanAdministrasi = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Administrasi", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanOperasional = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Operasional", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanPajak = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Pajak", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanPenyusutan = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Penyusutan", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                double bebanLainLain = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Lain-lain", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());

                double pendapatanBebanPenyesuaian = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Penyesuaian Barang");
                double bebanPenyusutanResetStokBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan Reset Stok Barang");
                double bebanPenyusutanSPBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan SP Barang");
                double pendapatanBebanKursEmas = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                        cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan Beban Kurs Emas");
                
                stackPane.getChildren().clear();

                gridPane = new GridPane();
                gridPane.setStyle("-fx-background-color: white;"
                        + "-fx-effect: dropshadow(three-pass-box, black, 10, 0, 0, 0);");
                StackPane.setMargin(gridPane, new Insets(10, 10, 10, 10));
                gridPane.setMaxWidth(800);
                gridPane.setPadding(new Insets(25));
                gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.LEFT, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 200, Priority.ALWAYS, HPos.RIGHT, true));
                gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 200, Priority.ALWAYS, HPos.RIGHT, true));

                gridPane.getRowConstraints().add(new RowConstraints(40,40,40));
                Label titleLabel = new Label("Laporan Untung Rugi Cabang");
                titleLabel.setStyle("-fx-font-family: serif;"
                        + "-fx-font-size:30;");
                gridPane.add(titleLabel, 0, 0, 3, 1);

                gridPane.getRowConstraints().add(new RowConstraints(25,25,25));
                Label tanggalLabel = new Label("Tanggal  : "+tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+
                        " - "+tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                tanggalLabel.setStyle("-fx-font-family: serif;"
                        + "-fx-font-size:14;");
                gridPane.add(tanggalLabel, 0, 1, 3, 1);

                gridPane.getRowConstraints().add(new RowConstraints(25,25,25));
                Label cabangLabel = new Label("Cabang   : "+cabangCombo.getSelectionModel().getSelectedItem());
                cabangLabel.setStyle("-fx-font-family: serif;"
                        + "-fx-font-size:14;");
                gridPane.add(cabangLabel, 0, 2, 3, 1);

                gridPane.getRowConstraints().add(new RowConstraints(10,10,10));
                Line line = new Line(20, 0, 780, 0);
                gridPane.add(line, 0, 3, 3, 1);

                int row = 35;
                for(int i = 4 ; i<row ; i++){
                    gridPane.getRowConstraints().add(new RowConstraints(25,25,25));
                    if(i%2==0)
                        addBackground(i);
                }
                addBoldText("Penjualan", 0, 4);
                
                addHyperLinkText("Penjualan Umum", 0, 5);
                addNormalText(rp.format(penjualanUmum), 1, 5);

                addHyperLinkText("Penjualan Antar Cabang", 0, 6);
                addNormalText(rp.format(penjualanAntarCabang), 1, 6);
                
                addHyperLinkText("Penjualan Ciok", 0, 7);
                addNormalText(rp.format(penjualanCiok), 1, 7);
                
                addBoldText("Total Penjualan", 0, 8);
                addBoldText(rp.format(penjualanUmum+penjualanAntarCabang+penjualanCiok), 2, 8);
                
                addBoldText("Harga Pokok Penjualan", 0, 10);
                
                addHyperLinkText("Penjualan Umum", 0, 11);
                addNormalText(rp.format(hppPenjualanUmum), 1, 11);

                addHyperLinkText("Penjualan Antar Cabang", 0, 12);
                addNormalText(rp.format(hppPenjualanAntarCabang), 1, 12);
                
                addHyperLinkText("Penjualan Ciok", 0, 13);
                addNormalText(rp.format(hppPenjualanCiok), 1, 13);
                
                addBoldText("Total Harga Pokok Penjualan", 0, 14);
                addBoldText(rp.format(hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok), 2, 14);
                
                addBoldText("Untung Rugi Kotor", 0, 16);
                addBoldText(rp.format(penjualanUmum+penjualanAntarCabang+penjualanCiok+hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok), 2, 16);
                
                addBoldText("Pendapatan / Beban", 0, 18);
                
                addHyperLinkText("Pendapatan Servis", 0, 19);
                addNormalText(rp.format(pendapatanServis), 1, 19);

                addHyperLinkText("Pendapatan Kartu Member", 0, 20);
                addNormalText(rp.format(pendapatanKartuMember), 1, 20);
                
                addHyperLinkText("Pendapatan Lain-lain", 0, 21);
                addNormalText(rp.format(pendapatanLainLain), 1, 21);
                
                addHyperLinkText("Beban Poin", 0, 22);
                addNormalText(rp.format(bebanPoin), 1, 22);
                
                addHyperLinkText("Beban Gaji", 0, 23);
                addNormalText(rp.format(bebanGaji), 1, 23);
                
                addHyperLinkText("Beban Administrasi", 0, 24);
                addNormalText(rp.format(bebanAdministrasi), 1, 24);
                
                addHyperLinkText("Beban Operasional", 0, 25);
                addNormalText(rp.format(bebanOperasional), 1, 25);
                
                addHyperLinkText("Beban Pajak", 0, 26);
                addNormalText(rp.format(bebanPajak), 1, 26);
                
                addHyperLinkText("Beban Penyusutan", 0, 27);
                addNormalText(rp.format(bebanPenyusutan), 1, 27);
                
                addHyperLinkText("Beban Lain-lain", 0, 28);
                addNormalText(rp.format(bebanLainLain), 1, 28);
                
                addHyperLinkText("Pendapatan/Beban Penyesuaian Barang", 0, 29);
                addNormalText(rp.format(pendapatanBebanPenyesuaian), 1, 29);
                
                addHyperLinkText("Beban Penyusutan Reset Stok Barang", 0, 30);
                addNormalText(rp.format(bebanPenyusutanResetStokBarang), 1, 30);
                
                addHyperLinkText("Beban Penyusutan SP Barang", 0, 31);
                addNormalText(rp.format(bebanPenyusutanSPBarang), 1, 31);
                
                addHyperLinkText("Pendapatan/Beban Kurs Emas", 0, 32);
                addNormalText(rp.format(pendapatanBebanKursEmas), 1, 32);
                
                addBoldText("Total Pendapatan-Beban", 0, 33);
                addBoldText(rp.format(
                    pendapatanKartuMember+pendapatanServis+pendapatanLainLain+
                    bebanPoin+bebanGaji+bebanAdministrasi+bebanOperasional+bebanPajak+bebanPenyusutan+bebanLainLain+
                    pendapatanBebanPenyesuaian+bebanPenyusutanResetStokBarang+bebanPenyusutanSPBarang+pendapatanBebanKursEmas), 2, 33);
                
                addBoldText("Untung Rugi Bersih", 0, 35);
                addBoldText(rp.format(
                        penjualanUmum+penjualanAntarCabang+penjualanCiok+
                        hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok+
                        pendapatanKartuMember+pendapatanServis+pendapatanLainLain+
                        bebanPoin+bebanGaji+bebanAdministrasi+bebanOperasional+bebanPajak+bebanPenyusutan+bebanLainLain+
                        pendapatanBebanPenyesuaian+bebanPenyusutanResetStokBarang+bebanPenyusutanSPBarang+pendapatanBebanKursEmas), 2, 35);
                
                stackPane.getChildren().add(gridPane);
            }
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }          
    }
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor5,20%);");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;"
                + "-fx-font-family: serif;");
        gridPane.add(label, column, row);
    }
    private void addBoldText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;"
                + "-fx-font-weight:bold;"
                + "-fx-font-family: serif;");
        gridPane.add(label, column, row);
    }
    private void addHyperLinkText(String text, int column, int row){
        Hyperlink hyperlink = new Hyperlink(text);
        hyperlink.setStyle("-fx-font-size:14;"
                + "-fx-font-family: serif;"
                + "-fx-text-fill:seccolor3;"
                + "-fx-border-color:transparent;");
        hyperlink.setOnAction((e) -> {
//            Stage stage = new Stage();
//            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Report/UntungRugiPenjualan.fxml");
//            UntungRugiPenjualanController x = loader.getController();
//            x.setMainApp(mainApp, mainApp.MainStage, stage);
//            x.getPenjualan(tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
        });
        gridPane.add(hyperlink, column, row);
    }
    
}
