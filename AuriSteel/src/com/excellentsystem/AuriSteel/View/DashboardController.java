/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.KeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Barang;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Helper.BestSellingItems;
import com.excellentsystem.AuriSteel.Model.Helper.DashboardModel;
import com.excellentsystem.AuriSteel.Model.Helper.PendingItems;
import com.excellentsystem.AuriSteel.Model.Helper.TopCustomer;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.Pegawai;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DashboardController {

    private Boolean scrollingMenu = false;
    @FXML private ScrollPane scrollPane;
    @FXML private GridPane gridPane;
    
    @FXML private Label totalPenjualanLabel;
    @FXML private Label keuntunganKotorLabel;
    @FXML private Label pendapatanBebanLabel;
    @FXML private Label keuntunganBersihLabel;
    
    @FXML private StackedBarChart<String, Double> salesPerformanceChart;
    @FXML private CategoryAxis salesAxis;
    
    @FXML private LineChart<String, Double> omzetPenjualanChart;
    @FXML private CategoryAxis periodeOmzetAxis;
    
    
    @FXML private TableView<TopCustomer> topCustomerTable;
    @FXML private TableColumn<TopCustomer, String> namaCustomerColumn;
    @FXML private TableColumn<TopCustomer, Number> totalColumn;
    @FXML private TableColumn<TopCustomer, Number> persenColumn;
    private ObservableList<TopCustomer> customer = FXCollections.observableArrayList();
    
    @FXML private ComboBox<String> periodeCombo;
    private ObservableList<String> periode = FXCollections.observableArrayList();
    @FXML private CheckBox penjualanCheckBox;
    @FXML private CheckBox penjualanCoilCheckBox;
    
    private Main mainApp;  
    public void initialize() {
        scrollPane.addEventHandler(MouseEvent.DRAG_DETECTED, e -> {
            scrollingMenu = true;
        });
        scrollPane.addEventFilter(MouseEvent.MOUSE_CLICKED, evt -> {
            if (scrollingMenu) {
                scrollingMenu = false;
                evt.consume();
            }
        });
        scrollPane.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            scrollingMenu = false;
        });
        gridPane.setOnScroll((ScrollEvent event) -> {
            double deltaY = event.getDeltaY()*2; // *6 to make the scrolling a bit faster
            double width = scrollPane.getContent().getBoundsInLocal().getHeight();
            double vvalue = scrollPane.getVvalue();
            scrollPane.setVvalue(vvalue + -deltaY/width); // deltaY/width to make the scrolling equally fast regardless of the actual width of the component
        });
        
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalPenjualanProperty());
        totalColumn.setCellFactory(col -> Function.getTableCell());
        persenColumn.setCellValueFactory(cellData -> cellData.getValue().persentasePenjualanProperty());
        persenColumn.setCellFactory(col -> Function.getTableCell());
        
        kodeBarangBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        qtyBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        beratBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        jumlahRpBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenQtyBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenQtyProperty());
        persenQtyBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenBeratBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenBeratProperty());
        persenBeratBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenJumlahRpBestSellingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenJumlahRpProperty());
        persenJumlahRpBestSellingItemsColumn.setCellFactory(col -> Function.getTableCell());
        
        kodeBarangPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        qtyPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
        beratPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
        jumlahRpPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenQtyPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenQtyProperty());
        persenQtyPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenBeratPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenBeratProperty());
        persenBeratPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
        persenJumlahRpPendingItemsColumn.setCellValueFactory(cellData -> cellData.getValue().persenJumlahRpProperty());
        persenJumlahRpPendingItemsColumn.setCellFactory(col -> Function.getTableCell());
    } 
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        periode.clear();
        periode.add("This Month");
        periode.add("Last 6 Months");
        periode.add("Last 12 Months");
        periode.add("This Year");
        periodeCombo.setItems(periode);
        periodeCombo.getSelectionModel().select("This Month");
        topCustomerTable.setItems(customer);
        bestSellingItemsTable.setItems(listBestSellingItems);
        pendingItemsTable.setItems(listPendingItems);
        omzetPenjualanChart.setCursor(Cursor.CROSSHAIR);
        salesPerformanceChart.setCursor(Cursor.CROSSHAIR);
        getData();
    }
    @FXML
    private void getData(){
        Task<DashboardModel> task = new Task<DashboardModel>() {
            @Override 
            public DashboardModel call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    System.out.println("start "+new Date());
                    String tglMulai = "";
                    String tglAkhir = "";
                    if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                        LocalDate startdate = LocalDate.now();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                        tglMulai = startdate.format(format)+"-01";
                        LocalDate enddate = LocalDate.now();
                        tglAkhir = enddate.toString();
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 6 Months")){
                        LocalDate startdate = LocalDate.now().minusMonths(5);
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                        tglMulai = startdate.format(format)+"-01";
                        LocalDate enddate = LocalDate.now();
                        tglAkhir = enddate.toString();
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("Last 12 Months")){
                        LocalDate startdate = LocalDate.now().minusMonths(11);
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM");
                        tglMulai = startdate.format(format)+"-01";
                        LocalDate enddate = LocalDate.now();
                        tglAkhir = enddate.toString();
                    }else if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Year")){
                        LocalDate startdate = LocalDate.now();
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy");
                        tglMulai = startdate.format(format)+"-01-01";
                        LocalDate enddate = LocalDate.now();
                        tglAkhir = enddate.toString();
                    }

                    DashboardModel dashboard = new DashboardModel();
                    dashboard.setListPenjualanHead(new ArrayList<>());
                    dashboard.setListPenjualanCoilHead(new ArrayList<>());
                    dashboard.setListSales(PegawaiDAO.getAllByStatus(con, "%"));
                    System.out.println("getSales "+new Date());
                    
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "%");
                    System.out.println("getCustomer "+new Date());
                    List<TopCustomer> listTopCustomer = new ArrayList<>();
                    for(Customer c : listCustomer){
                        TopCustomer tc = new TopCustomer();
                        tc.setKodeCustomer(c.getKodeCustomer());
                        tc.setNama(c.getNama());
                        tc.setPersentasePenjualan(0);
                        tc.setTotalPenjualan(0);
                        listTopCustomer.add(tc);
                    }
                    dashboard.setListTopCustomer(listTopCustomer);
                    
                    if(penjualanCheckBox.isSelected()){
                        dashboard.setListPenjualanHead(PenjualanBarangHeadDAO.getAllByDateAndStatus(
                                con, tglMulai, tglAkhir, "true"));
                        for(PenjualanBarangHead p : dashboard.getListPenjualanHead()){
                            for(Customer c : listCustomer){
                                if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                    p.setCustomer(c);
                            }
                            for(Pegawai s : dashboard.getListSales()){
                                if(p.getKodeSales().equals(s.getKodePegawai()))
                                    p.setSales(s);
                            }
                        }
                        System.out.println("getPenjualan "+new Date());
                    }
                    if(penjualanCoilCheckBox.isSelected()){
                        dashboard.setListPenjualanCoilHead(PenjualanBahanHeadDAO.getAllByDateAndStatus(
                                con, tglMulai, tglAkhir, "true"));
                        for(PenjualanBahanHead p : dashboard.getListPenjualanCoilHead()){
                            for(Customer c : listCustomer){
                                if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                    p.setCustomer(c);
                            }
                            for(Pegawai s : dashboard.getListSales()){
                                if(p.getKodeSales().equals(s.getKodePegawai()))
                                    p.setSales(s);
                            }
                        }
                        System.out.println("getPenjualanCoil "+new Date());
                    }
                    
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    System.out.println("getBarang "+new Date());
                    List<PenjualanBarangDetail> listPenjualanDetail = PenjualanBarangDetailDAO.getAllByDateAndStatus(con, tglMulai, tglAkhir, "true");
                    System.out.println("getPenjualanDetail "+new Date());
                    List<BestSellingItems> listBestSellingItems = new ArrayList<>();
                    double totalQty = 0;
                    double totalBerat = 0;
                    double totalRp = 0;
                    for(PenjualanBarangDetail p : listPenjualanDetail){
                        double berat = 0;
                        for(Barang b : listBarang){
                            if(b.getKodeBarang().equals(p.getKodeBarang()))
                                berat = p.getQty()*b.getBerat();
                        }
                        boolean status = false;
                        for(BestSellingItems b : listBestSellingItems){
                            if(b.getKodeBarang().equals(p.getKodeBarang())){
                                b.setQty(b.getQty()+p.getQty());
                                b.setBerat(b.getBerat()+berat);
                                b.setJumlahRp(b.getJumlahRp()+p.getTotal());
                                status = true;
                            }
                        }
                        if(status==false){
                            BestSellingItems b = new BestSellingItems();
                            b.setKodeBarang(p.getKodeBarang());
                            b.setQty(p.getQty());
                            b.setBerat(berat);
                            b.setJumlahRp(p.getTotal());
                            b.setPersenQty(0);
                            b.setPersenBerat(0);
                            b.setPersenJumlahRp(0);
                            listBestSellingItems.add(b);
                        }
                        totalQty = totalQty + p.getQty();
                        totalBerat = totalBerat + berat;
                        totalRp = totalRp + p.getTotal();
                    }
                    for(BestSellingItems c : listBestSellingItems){
                        c.setPersenQty(c.getQty()/totalQty*100);
                        c.setPersenBerat(c.getBerat()/totalBerat*100);
                        c.setPersenJumlahRp(c.getJumlahRp()/totalRp*100);
                    }
                    dashboard.setListBestSellingItems(listBestSellingItems);
                    
                    List<PemesananBarangDetail> listPemesananDetail = PemesananBarangDetailDAO.getAllByDateAndStatus(con, 
                            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"))+"-01-01", tglAkhir, "open");
                    System.out.println("getPemesananDetail "+new Date());
                    List<PendingItems> listPendingItems = new ArrayList<>();
                    double totalQtyPendingItems = 0;
                    double totalBeratPendingItems = 0;
                    double totalRpPendingItems = 0;
                    for(PemesananBarangDetail p : listPemesananDetail){
                        double qty = p.getQty()-p.getQtyTerkirim();
                        double berat = 0;
                        for(Barang b : listBarang){
                            if(b.getKodeBarang().equals(p.getKodeBarang()))
                                berat = (p.getQty()-p.getQtyTerkirim())*b.getBerat();
                        }
                        double total = qty * p.getHargaJual();
                        boolean status = false;
                        for(PendingItems b : listPendingItems){
                            if(b.getKodeBarang().equals(p.getKodeBarang())){
                                b.setQty(b.getQty()+qty);
                                b.setBerat(b.getBerat()+berat);
                                b.setJumlahRp(b.getJumlahRp()+total);
                                status = true;
                            }
                        }
                        if(status==false){
                            PendingItems b = new PendingItems();
                            b.setKodeBarang(p.getKodeBarang());
                            b.setQty(qty);
                            b.setBerat(berat);
                            b.setJumlahRp(total);
                            b.setPersenQty(0);
                            b.setPersenBerat(0);
                            b.setPersenJumlahRp(0);
                            listPendingItems.add(b);
                        }
                        totalQtyPendingItems = totalQtyPendingItems + qty;
                        totalBeratPendingItems = totalBeratPendingItems + berat;
                        totalRpPendingItems = totalRpPendingItems + total;
                    }
                    for(PendingItems c : listPendingItems){
                        c.setPersenQty(c.getQty()/totalQtyPendingItems*100);
                        c.setPersenBerat(c.getBerat()/totalBeratPendingItems*100);
                        c.setPersenJumlahRp(c.getJumlahRp()/totalRpPendingItems*100);
                    }
                    dashboard.setListPendingItems(listPendingItems);
                    
                    dashboard.setListKeuangan(KeuanganDAO.getAllByTanggal(con, tglMulai, tglAkhir));
                    System.out.println("getKeuangan "+new Date());
                    
                    return dashboard;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                DashboardModel dashboard = task.getValue();
                setOmzetPenjualan(dashboard.getListKeuangan());
                setSalesPerformance(dashboard.getListPenjualanHead(), dashboard.getListPenjualanCoilHead());
                setTopCustomer(dashboard.getListPenjualanHead(), dashboard.getListPenjualanCoilHead(), dashboard.getListTopCustomer());
                double totalPenjualan = 0;
                double totalHpp = 0;
                double totalPendapatanBeban = 0;
                for(Keuangan k : dashboard.getListKeuangan()){
                    if(k.getTipeKeuangan().equals("Penjualan")){
                        if(k.getKategori().equals("Penjualan") && penjualanCheckBox.isSelected())
                            totalPenjualan = totalPenjualan + k.getJumlahRp();
                        else if(k.getKategori().equals("Penjualan Coil") && penjualanCoilCheckBox.isSelected())
                            totalPenjualan = totalPenjualan + k.getJumlahRp();
                    }
                    if(penjualanCheckBox.isSelected()){
                        if(k.getTipeKeuangan().equals("Retur Penjualan")){
                            totalPenjualan = totalPenjualan - k.getJumlahRp();
                        }
                    }
                    if(k.getTipeKeuangan().equals("HPP")){
                        if(k.getKategori().equals("Penjualan") && penjualanCheckBox.isSelected())
                            totalHpp = totalHpp + k.getJumlahRp();
                        else if(k.getKategori().equals("Penjualan Coil") && penjualanCoilCheckBox.isSelected())
                            totalHpp = totalHpp + k.getJumlahRp();
                        else if(k.getKategori().equals("Retur Penjualan") && penjualanCheckBox.isSelected())
                            totalHpp = totalHpp + k.getJumlahRp();
                    }
                    if(k.getTipeKeuangan().equals("Pendapatan/Beban")){
                        totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                    }
                }
                setLabelHeader(totalPenjualan, totalPenjualan-totalHpp, totalPendapatanBeban, totalPenjualan-totalHpp+totalPendapatanBeban);
            
                listBestSellingItems.clear();
                listBestSellingItems.addAll(dashboard.getListBestSellingItems());
                chooseBestSellingItems();
                
                listPendingItems.clear();
                listPendingItems.addAll(dashboard.getListPendingItems());
                choosePendingItems();
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private void setLabelHeader(double totalPenjualan, double urKotor, double pendapatanBeban, double urBersih){
        String a = new DecimalFormat("###,##0").format(totalPenjualan);
        String b = new DecimalFormat("###,##0").format(urKotor);
        String c = new DecimalFormat("###,##0").format(pendapatanBeban);
        String d = new DecimalFormat("###,##0").format(urBersih);
        if(totalPenjualan/1000000000>=1 || totalPenjualan/1000000000<=-1)
            a = df.format(totalPenjualan/1000000000)+" M";
        if(urKotor/1000000000>=1 || urKotor/1000000000<=-1)
            b = df.format(urKotor/1000000000)+" M";
        if(pendapatanBeban/1000000000>=1 || pendapatanBeban/1000000000<=-1)
            c = df.format(pendapatanBeban/1000000000)+" M";
        if(urBersih/1000000000>=1 || urBersih/1000000000<=-1)
            d = df.format(urBersih/1000000000)+" M";
        
        totalPenjualanLabel.setText(a);
        keuntunganKotorLabel.setText(b);
        pendapatanBebanLabel.setText(c);
        keuntunganBersihLabel.setText(d);
    }
    private void setTopCustomer(List<PenjualanBarangHead> listPenjualan, List<PenjualanBahanHead> listPenjualanCoil, List<TopCustomer> listCustomer)throws Exception{
        customer.clear();
        double grandtotal = 0;
        for(TopCustomer c : listCustomer){
            for(PenjualanBarangHead p : listPenjualan){
                if(c.getKodeCustomer().equals(p.getKodeCustomer())){
                    c.setTotalPenjualan(c.getTotalPenjualan()+p.getTotalPenjualan());
                    grandtotal = grandtotal + p.getTotalPenjualan();
                }
            }
            for(PenjualanBahanHead p : listPenjualanCoil){
                if(c.getKodeCustomer().equals(p.getKodeCustomer())){
                    c.setTotalPenjualan(c.getTotalPenjualan()+p.getTotalPenjualan());
                    grandtotal = grandtotal + p.getTotalPenjualan();
                }
            }
        }
        for(TopCustomer c : listCustomer){
            c.setPersentasePenjualan(c.getTotalPenjualan()/grandtotal*100);
            if(c.getTotalPenjualan()!=0)
                customer.add(c);
        }
        customer.sort(Comparator.comparingDouble(TopCustomer::getTotalPenjualan).reversed());
    }
    private XYChart.Series getXYChartSeriesStore(CategoryAxis categoryAxis, List<Keuangan> listKeuangan, String kategori)throws Exception{
        XYChart.Series series = new XYChart.Series<>();
        series.setName(kategori);  
        for(String s : categoryAxis.getCategories()){
            double x = 0;
            for(Keuangan k : listKeuangan){
                if(k.getTipeKeuangan().equals("Penjualan") && k.getKategori().equals(kategori)){
                    boolean status = false;
                    if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                        if(s.equals(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }else{
                        if(s.equals(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                            status =true;
                    }
                    if(status)
                        x = x + k.getJumlahRp();
                }
            }
            XYChart.Data<String, Double> data = new XYChart.Data<>(s, x);
            data.setNode(
                new HoveredThresholdNode(x)
            );
            if(x!=0)
                series.getData().add(data);
        }
        return series;
    }
    private void setOmzetPenjualan(List<Keuangan> listKeuangan) throws Exception {
        omzetPenjualanChart.getData().clear();
        periodeOmzetAxis.getCategories().clear();
        for(Keuangan k : listKeuangan){
            if(periodeCombo.getSelectionModel().getSelectedItem().equals("This Month")){
                if(!periodeOmzetAxis.getCategories().contains(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                    periodeOmzetAxis.getCategories().add(new SimpleDateFormat("dd MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
            }else{
                if(!periodeOmzetAxis.getCategories().contains(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan()))))
                    periodeOmzetAxis.getCategories().add(new SimpleDateFormat("MMM yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(k.getTglKeuangan())));
            }
        }

        ObservableList<XYChart.Series<String, Double>> dataStorePerformance = FXCollections.observableArrayList();
        XYChart.Series series1 = getXYChartSeriesStore(periodeOmzetAxis, listKeuangan, "Penjualan");
        if(!series1.getData().isEmpty())
            dataStorePerformance.add(series1);

        XYChart.Series series2 = getXYChartSeriesStore(periodeOmzetAxis, listKeuangan, "Penjualan Coil");
        if(!series2.getData().isEmpty())
            dataStorePerformance.add(series2);
        
        omzetPenjualanChart.setData(dataStorePerformance);
    }
    class HoveredThresholdNode extends StackPane {
        
        HoveredThresholdNode(double value) {
            setPrefSize(15, 15);
            final Label label = createDataThresholdLabel(value);
            setOnMouseEntered((MouseEvent mouseEvent) -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited((MouseEvent mouseEvent) -> {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
            });
        }
        private Label createDataThresholdLabel(double value) {
            final Label label = new Label(df.format(value));
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 14; -font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
            return label;
        }
    }
    private void setSalesPerformance(List<PenjualanBarangHead> listPenjualan, List<PenjualanBahanHead> listPenjualanCoil) throws Exception{
        salesPerformanceChart.getData().clear();
        salesAxis.getCategories().clear();
        for(PenjualanBarangHead p : listPenjualan){
            if(!salesAxis.getCategories().contains(p.getSales().getNama())){
                salesAxis.getCategories().add(p.getSales().getNama());
            }
        }
        for(PenjualanBahanHead p : listPenjualanCoil){
            if(!salesAxis.getCategories().contains(p.getSales().getNama()))
                salesAxis.getCategories().add(p.getSales().getNama());
        }
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Penjualan");  
        for(String s : salesAxis.getCategories()){
            double totalPenjualan = 0;
            for(PenjualanBarangHead p : listPenjualan){
                if(s.equals(p.getSales().getNama()))
                    totalPenjualan = totalPenjualan + p.getTotalPenjualan();
            }
            XYChart.Data<String, Double> data = new XYChart.Data<>(s, totalPenjualan);
            data.setNode(
                new HoveredThresholdNode(totalPenjualan)
            );
            if(totalPenjualan!=0)
                series1.getData().add(data);
        }
        if(!series1.getData().isEmpty())
            salesPerformanceChart.getData().add(series1);
        
        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Penjualan Coil");  
        for(String s : salesAxis.getCategories()){
            double totalPenjualan = 0;
            for(PenjualanBahanHead p : listPenjualanCoil){
                if(s.equals(p.getSales().getNama()))
                    totalPenjualan = totalPenjualan + p.getTotalPenjualan();
            }
            XYChart.Data<String, Double> data = new XYChart.Data<>(s, totalPenjualan);
            data.setNode(
                new HoveredThresholdNode(totalPenjualan)
            );
            if(totalPenjualan!=0)
                series2.getData().add(data);
        }
        if(!series2.getData().isEmpty())
            salesPerformanceChart.getData().add(series2);
    }
    
    @FXML private RadioButton qtyBestSellingItemsRadio;
    @FXML private RadioButton beratBestSellingItemsRadio;
    @FXML private RadioButton jumlahRpBestSellingItemsRadio;
    @FXML private TableView<BestSellingItems> bestSellingItemsTable;
    @FXML private TableColumn<BestSellingItems, String> kodeBarangBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> qtyBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> beratBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> jumlahRpBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> persenQtyBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> persenBeratBestSellingItemsColumn;
    @FXML private TableColumn<BestSellingItems, Number> persenJumlahRpBestSellingItemsColumn;
    @FXML private Label totalBestSellingItemsLabel;
    private ObservableList<BestSellingItems> listBestSellingItems = FXCollections.observableArrayList();
    @FXML private void chooseBestSellingItems(){
        if(qtyBestSellingItemsRadio.isSelected()){
            qtyBestSellingItemsColumn.setVisible(true);
            beratBestSellingItemsColumn.setVisible(false);
            jumlahRpBestSellingItemsColumn.setVisible(false);
            persenQtyBestSellingItemsColumn.setVisible(true);
            persenBeratBestSellingItemsColumn.setVisible(false);
            persenJumlahRpBestSellingItemsColumn.setVisible(false);
            listBestSellingItems.sort(Comparator.comparingDouble(BestSellingItems::getQty).reversed());
            double total = 0;
            for(BestSellingItems b : listBestSellingItems){
                total = total + b.getQty();
            }
            totalBestSellingItemsLabel.setText(df.format(total));
        }else if(beratBestSellingItemsRadio.isSelected()){
            qtyBestSellingItemsColumn.setVisible(false);
            beratBestSellingItemsColumn.setVisible(true);
            jumlahRpBestSellingItemsColumn.setVisible(false);
            persenQtyBestSellingItemsColumn.setVisible(false);
            persenBeratBestSellingItemsColumn.setVisible(true);
            persenJumlahRpBestSellingItemsColumn.setVisible(false);
            listBestSellingItems.sort(Comparator.comparingDouble(BestSellingItems::getBerat).reversed());
            double total = 0;
            for(BestSellingItems b : listBestSellingItems){
                total = total + b.getBerat();
            }
            totalBestSellingItemsLabel.setText(df.format(total));
        }else if(jumlahRpBestSellingItemsRadio.isSelected()){
            qtyBestSellingItemsColumn.setVisible(false);
            beratBestSellingItemsColumn.setVisible(false);
            jumlahRpBestSellingItemsColumn.setVisible(true);
            persenQtyBestSellingItemsColumn.setVisible(false);
            persenBeratBestSellingItemsColumn.setVisible(false);
            persenJumlahRpBestSellingItemsColumn.setVisible(true);
            listBestSellingItems.sort(Comparator.comparingDouble(BestSellingItems::getJumlahRp).reversed());
            double total = 0;
            for(BestSellingItems b : listBestSellingItems){
                total = total + b.getJumlahRp();
            }
            totalBestSellingItemsLabel.setText(df.format(total));
        }
    }
    
    @FXML private RadioButton qtyPendingItemsRadio;
    @FXML private RadioButton beratPendingItemsRadio;
    @FXML private RadioButton jumlahRpPendingItemsRadio;
    @FXML private TableView<PendingItems> pendingItemsTable;
    @FXML private TableColumn<PendingItems, String> kodeBarangPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> qtyPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> beratPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> jumlahRpPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> persenQtyPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> persenBeratPendingItemsColumn;
    @FXML private TableColumn<PendingItems, Number> persenJumlahRpPendingItemsColumn;
    @FXML private Label totalPendingItemsLabel;
    private ObservableList<PendingItems> listPendingItems = FXCollections.observableArrayList();
    @FXML private void choosePendingItems(){
        if(qtyPendingItemsRadio.isSelected()){
            qtyPendingItemsColumn.setVisible(true);
            beratPendingItemsColumn.setVisible(false);
            jumlahRpPendingItemsColumn.setVisible(false);
            persenQtyPendingItemsColumn.setVisible(true);
            persenBeratPendingItemsColumn.setVisible(false);
            persenJumlahRpPendingItemsColumn.setVisible(false);
            listPendingItems.sort(Comparator.comparingDouble(PendingItems::getQty).reversed());
            double total = 0;
            for(PendingItems b : listPendingItems){
                total = total + b.getQty();
            }
            totalPendingItemsLabel.setText(df.format(total));
        }else if(beratPendingItemsRadio.isSelected()){
            qtyPendingItemsColumn.setVisible(false);
            beratPendingItemsColumn.setVisible(true);
            jumlahRpPendingItemsColumn.setVisible(false);
            persenQtyPendingItemsColumn.setVisible(false);
            persenBeratPendingItemsColumn.setVisible(true);
            persenJumlahRpPendingItemsColumn.setVisible(false);
            listPendingItems.sort(Comparator.comparingDouble(PendingItems::getBerat).reversed());
            double total = 0;
            for(PendingItems b : listPendingItems){
                total = total + b.getBerat();
            }
            totalPendingItemsLabel.setText(df.format(total));
        }else if(jumlahRpPendingItemsRadio.isSelected()){
            qtyPendingItemsColumn.setVisible(false);
            beratPendingItemsColumn.setVisible(false);
            jumlahRpPendingItemsColumn.setVisible(true);
            persenQtyPendingItemsColumn.setVisible(false);
            persenBeratPendingItemsColumn.setVisible(false);
            persenJumlahRpPendingItemsColumn.setVisible(true);
            listPendingItems.sort(Comparator.comparingDouble(PendingItems::getJumlahRp).reversed());
            double total = 0;
            for(PendingItems b : listPendingItems){
                total = total + b.getJumlahRp();
            }
            totalPendingItemsLabel.setText(df.format(total));
        }
    }
}
