/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KategoriCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.OmzetCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.KategoriCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Keuangan;
import static java.lang.Math.abs;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
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
 * @author excellent
 */
public class DashboardController {

    @FXML private StackPane kategoriBarangLoading;
    @FXML private StackPane omzetCabangLoading;
    @FXML private StackPane performaCabangLoading;
    @FXML private ComboBox<String> periodeCombo;
    private List<Cabang> allCabang = new ArrayList<>();
    private List<Keuangan> allKeuanganCabang = new ArrayList<>();
    private ObservableList<String> periode = FXCollections.observableArrayList();
    private String tglAwal = sistem.getTglSystem();
    private String tglAkhir = sistem.getTglSystem();
    
    private Main mainApp;  
    public void initialize() {
        ContextMenu omzetMenu = new ContextMenu();
        MenuItem refreshOmzet = new MenuItem("Refresh");
        refreshOmzet.setOnAction((ActionEvent) -> {
            setStorePerformanceTable();
        });
        omzetMenu.getItems().addAll(refreshOmzet);
        kategoriTable.setContextMenu(omzetMenu);
        kategoriTable.setRowFactory(t -> {
            TableRow<KategoriCabang> row = new TableRow<KategoriCabang>(){
                @Override
                public void updateItem(KategoriCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(omzetMenu);
                    }else{
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            setStorePerformanceTable();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        //Data Kategori Barang
        kategoriTable.setItems(allKategori);
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTableCell(rp));
        hargaJualColumn.setCellValueFactory(cellData -> cellData.getValue().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> Function.getTableCell(rp));
        ContextMenu kategoriMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            setDataKategoriBarang();
        });
        kategoriMenu.getItems().addAll(refresh);
        kategoriTable.setContextMenu(kategoriMenu);
        kategoriTable.setRowFactory(t -> {
            TableRow<KategoriCabang> row = new TableRow<KategoriCabang>(){
                @Override
                public void updateItem(KategoriCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(kategoriMenu);
                    }else{
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            setDataKategoriBarang();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        
        
//        Timeline t = new Timeline(
//            new KeyFrame(Duration.minutes(5), (ActionEvent actionEvent) -> {
//                if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day") && 
//                        mainApp.mainController.title.getText().equals("Dashboard") && mainApp.MainStage.isFocused()){
//                    System.out.println(new Date()+" setDashboard");
//                    getCabangAndData();
//                }
//            })
//        );
//        t.setCycleCount(Animation.INDEFINITE);
//        t.play();
    }    
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        periode.clear();
        periode.add("This Day");
        periode.add("This Month");
        periode.add("Last 6 Months");
        periode.add("Last 12 Months");
        periode.add("This Year");
        periodeCombo.setItems(periode);
        periodeCombo.getSelectionModel().selectFirst();
        getCabangAndData();
    }
    @FXML
    private void getCabangAndData(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String status = "";
                    allKeuanganCabang.clear();
                    allOmzetCabang.clear();
                    allCabang.clear();
                    allCabang.addAll(CabangDAO.getAll(conPusat));
                    for(Cabang c : allCabang){
                        OmzetCabang o = new OmzetCabang();
                        o.setKodeCabang(c.getKodeCabang());
                        try(Connection conCabang = KoneksiCabang.getQuickConnection(c)){
                            List<Keuangan> listKeuanganCabang = KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(
                                    conCabang, tglAwal, tglAkhir, "%", "Kas", "%", "%");
                            allKeuanganCabang.addAll(listKeuanganCabang);
                            
                            for(Keuangan k : listKeuanganCabang){
                                if(k.getKategori().equals("Penjualan Umum"))
                                    o.setPenjualan(o.getPenjualan()+k.getJumlahRp());
                                if(k.getKategori().equals("Pembelian Umum"))
                                    o.setPembelian(o.getPembelian()+k.getJumlahRp()*-1);
                                if(k.getKategori().equals("Terima Hutang"))
                                    o.setTerimaHutang(o.getTerimaHutang()+k.getJumlahRp()*-1);
                                if(k.getKategori().equals("Hutang Lunas"))
                                    o.setHutangLunas(o.getHutangLunas()+k.getJumlahRp());
                                if(k.getKategori().equals("Hutang Bunga"))
                                    o.setHutangBunga(o.getHutangBunga()+k.getJumlahRp());
                            }
                            o.setSaldoAkhirKasPenjualan(KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Kas", tglAkhir));
                            o.setSaldoAkhirKasRR(KeuanganDAO.getSaldoBefore(conCabang, "RR", "Kas", tglAkhir));
                            
                            status = status + c.getKodeCabang()+" = online\n";
                        }catch(Exception e){
                            status = status + c.getKodeCabang()+" = offline\n";
                        }
                        allOmzetCabang.add(o);
                        System.out.println(c.getKodeCabang()+new Date());
                    }
                    return status;
                }
            }
        };
        task.setOnRunning((e) -> {
            omzetCabangLoading.setVisible(true);
            performaCabangLoading.setVisible(true);
        });
        task.setOnSucceeded((e) -> {
            ObservableList<String> listCabang = FXCollections.observableArrayList();
            for(Cabang c : allCabang){
                listCabang.add(c.getKodeCabang());
            }
            cabangCombo.setItems(listCabang);
            cabangCombo.getSelectionModel().selectFirst();
            
            ObservableList<String> listKategori = FXCollections.observableArrayList();
            listKategori.add("Penjualan Umum");
            listKategori.add("Pembelian Umum");
            listKategori.add("Terima Hutang");
            listKategori.add("Hutang Lunas");
            listKategori.add("Hutang Bunga");
            kategoriCombo.setItems(listKategori);
            kategoriCombo.getSelectionModel().selectFirst();
            setStorePerformanceTable();
            omzetCabangLoading.setVisible(false);
            performaCabangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            omzetCabangLoading.setVisible(true);
            performaCabangLoading.setVisible(true);
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void changePeriode(){
        LocalDate localDate = LocalDate.parse(sistem.getTglSystem(), DateTimeFormatter.ISO_DATE);
        tglAwal = localDate.toString();
        tglAkhir = localDate.toString();
        DateTimeFormatter yyyyMM = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");
        if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
            tglAwal = tglAkhir;
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
            tglAwal = localDate.format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 6 Months")){
            tglAwal = localDate.minusMonths(5).format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 12 Months")){
            tglAwal = localDate.minusMonths(11).format(yyyyMM)+"-01";
        }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Year")){
            tglAwal = localDate.format(yyyy)+"-01-01";
        }
        getCabangAndData();
    }
    
    
    //Store Performance
    @FXML private RadioButton countStorePerformanceRadio;
    @FXML private RadioButton totalStorePerformanceRadio;
    @FXML private LineChart<String, Double> storePerformanceChart;
    @FXML private CategoryAxis periodeStorePerformanceAxis;
    @FXML private ComboBox<String> kategoriCombo;
    private XYChart.Series getXYChartSeriesStore(CategoryAxis categoryAxis, List<Keuangan> listKeuangan, String kodeCabang, String kategori)throws Exception{
        XYChart.Series series = new XYChart.Series<>();
        series.setName(kodeCabang);  
        for(String s : categoryAxis.getCategories()){
            double x = 0;
            for(Keuangan k : listKeuangan){
                if(k.getNoKeuangan().startsWith(kodeCabang) && k.getKategori().equals(kategori)){
                    boolean status = false;
                    if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
                        if(s.equals(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                        if(s.equals(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }else{
                        if(s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }
                    if(status){
                        if(totalStorePerformanceRadio.isSelected())
                            x = x + abs(k.getJumlahRp());
                        else if(countStorePerformanceRadio.isSelected()){
                            if(!kategori.equals("Hutang Bunga"))
                                x = x + 1;
                            else
                                x = x + 0;
                        }
                    }
                }
            }
            series.getData().add(new XYChart.Data<>(s, x));
        }
        return series;
    }
    @FXML
    private void setStorePerformance(){
        try{
            periodeStorePerformanceAxis.getCategories().clear();
            Collections.sort(allKeuanganCabang, (o1, o2) -> {
                int sComp = ((Keuangan) o1).getTglKeuangan().compareTo(((Keuangan) o2).getTglKeuangan());
                if (sComp != 0) 
                    return sComp;
                return ((Keuangan) o1).getNoKeuangan().compareTo(((Keuangan) o2).getNoKeuangan());
            });
            for(Keuangan k : allKeuanganCabang){
                if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Day")){
                    if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                        periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("HH:00").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                    if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                        periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                }else{
                    if(!periodeStorePerformanceAxis.getCategories().contains(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                        periodeStorePerformanceAxis.getCategories().add(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
                }
            }
            ObservableList<Series<String, Double>> dataStorePerformance = FXCollections.observableArrayList();
            for(Cabang c : allCabang){
                XYChart.Series series = getXYChartSeriesStore(periodeStorePerformanceAxis, allKeuanganCabang, c.getKodeCabang(), kategoriCombo.getSelectionModel().getSelectedItem());
                if(!series.getData().isEmpty())
                    dataStorePerformance.add(series);
            }

            storePerformanceChart.setData(dataStorePerformance);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
    //Store Performance Table
    @FXML private StackPane pane;
    private GridPane gridPane;
    private ObservableList<OmzetCabang> allOmzetCabang = FXCollections.observableArrayList();
    private void addBackground(int row){
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor5,20%);");
        gridPane.add(x, 0, row, GridPane.REMAINING, 1);
    }
    private void addHeaderText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: white;");
        gridPane.add(label, column, row);
    }
    private void addNormalText(String text,int column, int row){
        Label label = new Label(text);
        label.setStyle("-fx-font-size:14;");
        gridPane.add(label, column, row);
    }
    private void setStorePerformanceTable(){
        pane.getChildren().clear();
        gridPane = new GridPane();

        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, 100, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 100, Double.MAX_VALUE, Priority.ALWAYS, HPos.CENTER, true));

        int totalRows = 1 + allCabang.size();
        for(int i = 0 ; i<totalRows ; i++){
            gridPane.getRowConstraints().add(new RowConstraints(30,30,30));
            if(i%2==0)
                addBackground(i);
        }
        AnchorPane x = new AnchorPane();
        x.setStyle("-fx-background-color:derive(seccolor1,20%);");
        gridPane.add(x, 0, 0, GridPane.REMAINING, 1);
        
        AnchorPane y = new AnchorPane();
        y.setStyle("-fx-background-color:derive(seccolor3,20%);");
        gridPane.add(y, 0, 0, 1, GridPane.REMAINING);
        
        addHeaderText("Penjualan", 1, 0);
        addHeaderText("Pembelian", 2, 0);
        addHeaderText("Terima Hutang", 3, 0);
        addHeaderText("Hutang Lunas", 4, 0);
        addHeaderText("Hutang Bunga", 5, 0);
        addHeaderText("Saldo Akhir Penjualan", 6, 0);
        addHeaderText("Saldo Akhir RR", 7, 0);
        int row = 1;
        for(OmzetCabang o : allOmzetCabang){
            addHeaderText(o.getKodeCabang(), 0, row);
            addNormalText(rp.format(o.getPenjualan()), 1, row);
            addNormalText(rp.format(o.getPembelian()), 2, row);
            addNormalText(rp.format(o.getTerimaHutang()), 3, row);
            addNormalText(rp.format(o.getHutangLunas()), 4, row);
            addNormalText(rp.format(o.getHutangBunga()), 5, row);
            addNormalText(rp.format(o.getSaldoAkhirKasPenjualan()), 6, row);
            addNormalText(rp.format(o.getSaldoAkhirKasRR()), 7, row);
            row++;
        }
        gridPane.setPadding(new Insets(10));
        pane.getChildren().add(gridPane);
    }
    
    
    
    
    //Data Kategori Barang
    @FXML private TableView<KategoriCabang> kategoriTable;
    @FXML private TableColumn<KategoriCabang, String> kodeKategoriColumn;
    @FXML private TableColumn<KategoriCabang, Number> hargaBeliColumn;
    @FXML private TableColumn<KategoriCabang, Number> hargaJualColumn;
    private ObservableList<KategoriCabang> allKategori = FXCollections.observableArrayList();
    @FXML private ComboBox<String> cabangCombo;
    @FXML
    private void setDataKategoriBarang(){
        Task<List<KategoriCabang>> task = new Task<List<KategoriCabang>>() {
            @Override 
            public List<KategoriCabang> call() throws Exception{
                Cabang cabang = null;
                for(Cabang c : allCabang){
                    if(c.getKodeCabang().equals(cabangCombo.getSelectionModel().getSelectedItem()))
                        cabang = c;
                }
                if(cabang!=null){
                    try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                        return KategoriCabangDAO.getAll(conCabang);
                    }catch(Exception e){
                        return new ArrayList<>();
                    }
                }else{
                    return new ArrayList<>();
                }
            }
        };
        task.setOnRunning((event) -> {
            kategoriBarangLoading.setVisible(true);
        });
        task.setOnSucceeded((e) -> {
            allKategori.clear();
            allKategori.addAll(task.getValue());
            kategoriTable.refresh();
            kategoriBarangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            allKategori.clear();
            kategoriTable.refresh();
            kategoriBarangLoading.setVisible(false);
        });
        new Thread(task).start();
    }
}
