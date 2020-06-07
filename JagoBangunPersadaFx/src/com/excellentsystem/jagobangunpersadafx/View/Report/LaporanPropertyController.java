/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembelianTanahDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PemecahanPropertyDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PemecahanPropertyHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPDetailPropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.RAPRealisasiDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanHead;
import com.excellentsystem.jagobangunpersadafx.Model.PembelianTanah;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PemecahanPropertyHead;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetailProperty;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class LaporanPropertyController {

    @FXML private ComboBox<String> kategoriCombo;
    @FXML private GridPane mainGridPane; 
    @FXML private ScrollPane scrollPane; 
    @FXML private StackPane stackPane; 
    private GridPane gridPane; 
    
    private List<PembangunanHead> allPembangunan;
    private List<RAPHead> allRap;
    private List<PembelianTanah> allPembelian;
    private List<PemecahanPropertyHead> allPemecahanProperty;
    private List<Property> listProperty;
    private int rows = 0;
    private int columns = 0;
    private Main mainApp;   
    public void initialize() {
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getData();
        });
//        rm.getItems().addAll(print);
        rm.getItems().addAll(refresh);
        stackPane.setOnContextMenuRequested((e) -> {
            rm.show(stackPane, e.getScreenX(), e.getScreenY());
        });
        stackPane.setOnMouseClicked((event) -> {
            rm.hide();
        });
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getKategoriProperty();
    }  
    private void getKategoriProperty(){
        try{
            kategoriCombo.setItems(Function.getKategoriProperty());
            kategoriCombo.getSelectionModel().selectFirst();
            getData();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getData(){
        Task<Void> task = new Task<Void>() {
            @Override 
            public Void call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    listProperty = new ArrayList<>();
                    List<Property> allProperty = PropertyDAO.getAll(con);
                    for(Property p : allProperty){
                        if(p.getKodeKategori().equals(kategoriCombo.getSelectionModel().getSelectedItem()) && 
                                (!p.getStatus().equals("Mapped") && !p.getStatus().equals("Combined")))
                            listProperty.add(p);
                    }
                    allPembelian = PembelianTanahDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    allPemecahanProperty = PemecahanPropertyHeadDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    List<PemecahanPropertyDetail> allPemecahanPropertyDetail = PemecahanPropertyDetailDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    for(PemecahanPropertyHead p : allPemecahanProperty){
                        List<PemecahanPropertyDetail> listDetail = new ArrayList<>();
                        for(PemecahanPropertyDetail d : allPemecahanPropertyDetail){
                            if(p.getNoPemecahan().equals(d.getNoPemecahan()))
                                listDetail.add(d);
                        }
                        p.setAllDetail(listDetail);
                    }
                    allPembangunan = PembangunanHeadDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    List<PembangunanDetail> listPembangunanDetail = PembangunanDetailDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "true");
                    for(PembangunanHead p : allPembangunan){
                        List<PembangunanDetail> listDetail = new ArrayList<>();
                        for(PembangunanDetail d : listPembangunanDetail){
                            if(d.getNoPembangunan().equals(p.getNoPembangunan())){
                                listDetail.add(d);
                            }
                        }
                        p.setAllDetail(listDetail);
                    }
                    allRap = RAPHeadDAO.getAllByDateAndStatusApprovalAndStatusSelesaiAndStatusBatal(con, "2000-01-01", "2100-01-01", "%", "%", "false");
                    List<RAPRealisasi> allRealisasi = RAPRealisasiDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "%", "%", "false");
                    List<RAPDetailProperty> allRapDetailProperty = RAPDetailPropertyDAO.getAllByDateAndStatus(con, "2000-01-01", "2100-01-01", "%", "%", "false");
                    for(RAPHead r : allRap){
                        List<RAPDetailProperty> listRapDetailProperty = new ArrayList<>();
                        for(RAPDetailProperty d : allRapDetailProperty){
                            if(r.getNoRap().equals(d.getNoRap()))
                                listRapDetailProperty.add(d);
                        }
                        r.setListRapPropertyDetail(listRapDetailProperty);
                        List<RAPRealisasi> listRapRealisasi = new ArrayList<>();
                        for(RAPRealisasi d : allRealisasi){
                            if(r.getNoRap().equals(d.getNoRap()))
                                listRapRealisasi.add(d);
                        }
                        r.setListRapRealisasi(listRapRealisasi);
                    }
                    rows = 13;
                    columns = 2;
                    for(Property p : listProperty){
                        columns = columns+1;
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
            stackPane.getChildren().clear();
            gridPane = new GridPane();
            gridPane.setHgap(1);
            gridPane.setVgap(1);
                        
            setTopHeader();
            setLeftHeader();
            
            AnchorPane ap = new AnchorPane();
            ap.setStyle("-fx-background-color: seccolor6");
            ap.setMaxSize(200, 41);
            ap.setMouseTransparent(true);
            Label l = new Label("");
            l.setPrefSize(200, 30);
            l.setStyle("-fx-background-color: seccolor3;"
                    + "-fx-text-fill: seccolor6;"
                    + "-fx-font-weight: bold;");
            AnchorPane.setBottomAnchor(l, 1.0);
            ap.getChildren().add(l);
            GridPane.setHalignment(ap, HPos.LEFT);
            GridPane.setValignment(ap, VPos.TOP);
            GridPane.setMargin(ap, new Insets(1, 0, 1, 12));
            mainGridPane.add(ap, 0, 1);
            int i =1;
            for(Property p : listProperty){
                setDetail(p, i);
                i++;
            }
            setTotal(i);
            
            stackPane.getChildren().add(gridPane);
        } catch (Exception ex) {
            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
        }
    }
    private void setLeftHeader(){
        for(int i = 0 ; i < rows ; i++){
            gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
        }
        
        ScrollPane sp = new ScrollPane();
        sp.setMouseTransparent(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setPadding(new Insets(6, 5, 6, 5));
        
        StackPane st = new StackPane();
        st.setMouseTransparent(true);
        st.setStyle("-fx-background-color: seccolor6;");
        st.setPadding(new Insets(0, 0, 0, 2));
        
        GridPane gp = new GridPane();
        gp.setHgap(1);
        gp.setVgap(1);
        gp.setMaxWidth(200);
        gp.getColumnConstraints().add(new ColumnConstraints(200, 200, 200, Priority.ALWAYS, HPos.LEFT, true));
        for(int i = 0 ; i < rows ; i++){
            gp.getRowConstraints().add(new RowConstraints(30,30,30));
        }
        int i = 1;
        addText(gp, "PEMBELIAN TANAH", 0, i, "seccolor3", "seccolor6");
        i++;
        gp.getRowConstraints().add(i, new RowConstraints(3,3,3));
        gridPane.getRowConstraints().add(i, new RowConstraints(3,3,3));
        addText(gp, "", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "PENGURUSAN SERTIFIKAT", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "INFRASTRUKTUR", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "PAGAR BUMI", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "GATE", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "TAMAN", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "FASUM", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "URUGAN", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "BANGUNAN RUMAH", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "ADDENDUM", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "LAIN - LAIN", 0, i, "seccolor3", "seccolor6");
        i++;
        gp.getRowConstraints().add(i, new RowConstraints(3,3,3));
        gridPane.getRowConstraints().add(i, new RowConstraints(3,3,3));
        addText(gp, "", 0, i, "seccolor3", "seccolor6");
        i++;
        addText(gp, "TOTAL", 0, i, "seccolor3", "seccolor6");
        i++;
        StackPane.setMargin(gp, new Insets(5, 1, 5, 5));
        st.getChildren().add(gp);
        sp.setContent(st);
        mainGridPane.add(sp, 0, 1);
        
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            sp.setVvalue(scrollPane.getVvalue());
        });
    }
    private void setTopHeader() throws ParseException{
        gridPane.getColumnConstraints().add(new ColumnConstraints(200, 200, 200, Priority.ALWAYS, HPos.LEFT, true));
        for(int i = 1 ; i < columns ; i++){
            gridPane.getColumnConstraints().add(new ColumnConstraints(150, 150, Double.MAX_VALUE, Priority.ALWAYS, HPos.RIGHT, true));
        }
        ScrollPane sp = new ScrollPane();
        sp.setMouseTransparent(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setFitToWidth(true);
        sp.setPadding(new Insets(6, 6, 1, 5));
        
        StackPane st = new StackPane();
        st.setMouseTransparent(true);
        st.setStyle("-fx-background-color: seccolor6;");
        st.setPadding(new Insets(0, 0, 0, 1));
        
        GridPane gp = new GridPane();
        gp.setHgap(1);
        gp.setVgap(1);
        gp.setMinHeight(30);
        gp.setPrefHeight(30);
        gp.setMaxHeight(30);
        gp.getColumnConstraints().add(new ColumnConstraints(200, 200, 200, Priority.ALWAYS, HPos.LEFT, true));
        for(int i = 1 ; i < columns ; i++){
            gp.getColumnConstraints().add(new ColumnConstraints(150, 150, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        }
        gp.getRowConstraints().add(new RowConstraints(30,30,30));
        int i =1;
        for(Property p : listProperty){
            addText(gp, p.getNamaProperty(), i, 0, "seccolor1", "seccolor6");
            i++;
        }
        addText(gp, "Total "+kategoriCombo.getSelectionModel().getSelectedItem(), i, 0, "seccolor1", "seccolor6");
        
        StackPane.setMargin(gp, new Insets(5 , 5 , 1, 5));
        st.getChildren().add(gp);
        sp.setContent(st);
        mainGridPane.add(sp, 0, 1);
        
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) -> {
            sp.setHvalue(scrollPane.getHvalue());
        });
    }
    private void setDetail(Property p, int col) throws ParseException{
        int i = 1;
        
        double nilaiTanah = 0;
        for(PembelianTanah pt : allPembelian){
            if(pt.getKodeProperty().equals(p.getKodeProperty())){
                nilaiTanah = nilaiTanah + pt.getHargaBeli();
            }
        }
        for(PemecahanPropertyHead pp : allPemecahanProperty){
            if(pp.getKodeProperty().equals(p.getKodeProperty())){
                nilaiTanah = nilaiTanah - pp.getNilaiProperty();
            }
            for(PemecahanPropertyDetail d : pp.getAllDetail()){
                if(d.getKodeProperty().equals(p.getKodeProperty())){
                    nilaiTanah = nilaiTanah + d.getNilaiProperty();
                }
            }
        }
        addText(gridPane, rp.format(nilaiTanah), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, "", col, i, "seccolor1", "seccolor6");
        i++;
        double pengurusanSertifikat = 0;
        double infrastruktur = 0;
        double pagarBumi = 0;
        double gate = 0;
        double taman = 0;
        double fasum = 0;
        double urugan = 0;
        double bangunanRumah = 0;
        double addendum = 0;
        double lainlain = 0;
        for(PembangunanHead pb : allPembangunan){
            if(pb.getKategori().equals("PENGURUSAN SERTIFIKAT")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("INFRASTRUKTUR")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        infrastruktur = infrastruktur + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("PAGAR BUMI")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pagarBumi = pagarBumi + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("GATE")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        gate = gate + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("TAMAN")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        taman = taman + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("FASUM")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        fasum = fasum + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("URUGAN")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        urugan = urugan + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("BANGUNAN RUMAH")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        bangunanRumah = bangunanRumah + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("ADDENDUM")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        addendum = addendum + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("LAIN-LAIN")){
                for(PembangunanDetail d : pb.getAllDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        lainlain = lainlain + d.getBiaya();
                }
            }
        }
        for(RAPHead r : allRap){
            double total = 0;
            for(RAPRealisasi rr : r.getListRapRealisasi()){
                if(rr.getStatus().equals("true"))
                    total = total + rr.getJumlahRp();
            }
            if(r.getKategoriPembangunan().equals("PENGURUSAN SERTIFIKAT")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("INFRASTRUKTUR")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("PAGAR BUMI")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("GATE")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("TAMAN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("FASUM")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("URUGAN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("BANGUNAN RUMAH")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("ADDENDUM")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("LAIN-LAIN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
        }
        addText(gridPane, rp.format(pengurusanSertifikat), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(infrastruktur), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(pagarBumi), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(gate), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(taman), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(fasum), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(urugan), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(bangunanRumah), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(addendum), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, rp.format(lainlain), col, i, "seccolor5", "seccolor3");
        i++;
        addText(gridPane, "", col, i, "seccolor1", "seccolor6");
        i++;
        addText(gridPane, rp.format(nilaiTanah+pengurusanSertifikat+infrastruktur+pagarBumi+gate+taman+fasum+urugan+bangunanRumah+addendum+lainlain), col, i, "seccolor5", "seccolor3");
        i++;

    }
    private void setTotal(int col) throws ParseException{
        int i = 1;
        
        double nilaiTanah = 0;
        for(PembelianTanah pt : allPembelian){
            for(Property p : listProperty){
                if(pt.getKodeProperty().equals(p.getKodeProperty())){
                    nilaiTanah = nilaiTanah + pt.getHargaBeli();
                }
            }
        }
        for(PemecahanPropertyHead pp : allPemecahanProperty){
            for(Property p : listProperty){
                if(pp.getKodeProperty().equals(p.getKodeProperty())){
                    nilaiTanah = nilaiTanah - pp.getNilaiProperty();
                }
            }
            for(PemecahanPropertyDetail d : pp.getAllDetail()){
                for(Property p : listProperty){
                    if(d.getKodeProperty().equals(p.getKodeProperty())){
                        nilaiTanah = nilaiTanah + d.getNilaiProperty();
                    }
                }
            }
        }
        addText(gridPane, rp.format(nilaiTanah), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, "", col, i, "seccolor1", "seccolor6");
        i++;
        double pengurusanSertifikat = 0;
        double infrastruktur = 0;
        double pagarBumi = 0;
        double gate = 0;
        double taman = 0;
        double fasum = 0;
        double urugan = 0;
        double bangunanRumah = 0;
        double addendum = 0;
        double lainlain = 0;
        for(PembangunanHead pb : allPembangunan){
            if(pb.getKategori().equals("PENGURUSAN SERTIFIKAT")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("INFRASTRUKTUR")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        infrastruktur = infrastruktur + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("PAGAR BUMI")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pagarBumi = pagarBumi + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("GATE")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        gate = gate + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("TAMAN")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        taman = taman + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("FASUM")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        fasum = fasum + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("URUGAN")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        urugan = urugan + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("BANGUNAN RUMAH")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        bangunanRumah = bangunanRumah + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("ADDENDUM")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        addendum = addendum + d.getBiaya();
                }
            }
            if(pb.getKategori().equals("LAIN-LAIN")){
                for(PembangunanDetail d : pb.getAllDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        lainlain = lainlain + d.getBiaya();
                }
            }
        }
        for(RAPHead r : allRap){
            double total = 0;
            for(RAPRealisasi rr : r.getListRapRealisasi()){
                if(rr.getStatus().equals("true"))
                    total = total + rr.getJumlahRp();
            }
            if(r.getKategoriPembangunan().equals("PENGURUSAN SERTIFIKAT")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("INFRASTRUKTUR")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("PAGAR BUMI")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("GATE")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("TAMAN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("FASUM")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("URUGAN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("BANGUNAN RUMAH")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("ADDENDUM")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
            if(r.getKategoriPembangunan().equals("LAIN-LAIN")){
                for(RAPDetailProperty d : r.getListRapPropertyDetail()){for(Property p : listProperty)
                    if(d.getKodeProperty().equals(p.getKodeProperty()))
                        pengurusanSertifikat = pengurusanSertifikat + (total*d.getPersentase()/100);
                }
            }
        }
        addText(gridPane, rp.format(pengurusanSertifikat), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(infrastruktur), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(pagarBumi), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(gate), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(taman), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(fasum), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(urugan), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(bangunanRumah), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(addendum), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, rp.format(lainlain), col, i, "seccolor3", "seccolor6");
        i++;
        addText(gridPane, "", col, i, "seccolor1", "seccolor6");
        i++;
        addText(gridPane, rp.format(nilaiTanah+pengurusanSertifikat+infrastruktur+pagarBumi+gate+taman+fasum+urugan+bangunanRumah+addendum+lainlain), col, i, "seccolor3", "seccolor6");
        i++;

    }
    private void addText(GridPane gridPane, String text, int column, int row, String backgroundColor, String textColor){
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:"+backgroundColor+";");
        gridPane.add(anchorPane, column, row, 1, 1);
        
        Label label = new Label(text);
        label.setStyle("-fx-font-weight:bold;"
                +"-fx-text-fill: "+textColor+";");
        label.setPadding(new Insets(0, 5, 0, 5));
        gridPane.add(label, column, row);
    }
    private void print(){}
}
