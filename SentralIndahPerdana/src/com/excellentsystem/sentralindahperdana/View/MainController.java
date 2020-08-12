package com.excellentsystem.sentralindahperdana.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Notification;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class MainController  {

    @FXML private Label title;
    
    @FXML public VBox menuPane;
    
    @FXML private VBox userVbox;
    @FXML private TitledPane loginButton;
    @FXML private MenuButton logoutButton;
    @FXML private MenuButton ubahPasswordButton;
    
    @FXML private TitledPane masterPane;
    @FXML private VBox masterVbox;
    @FXML private MenuButton menuDataCustomer;
    @FXML private MenuButton menuDataSupplier;
    @FXML private MenuButton menuDataPegawai;
    @FXML private MenuButton menuDataBarang;
    @FXML private MenuButton menuDataPekerjaan;
    
    @FXML private TitledPane penjualanPane;
    @FXML private VBox penjualanVbox;
    @FXML private MenuButton menuPemesanan;
    @FXML private MenuButton menuPenjualan;
    @FXML private MenuButton menuPengirimanBarang;
    @FXML private MenuButton menuReturBarang;
    @FXML private MenuButton menuBebanPenjualan;
    
    @FXML private TitledPane pembelianPane;
    @FXML private VBox pembelianVbox;
    @FXML private MenuButton menuPembelian;
    @FXML private MenuButton menuReturPembelian;
    
    @FXML private TitledPane keuanganPane;
    @FXML private VBox keuanganVbox;
    @FXML private MenuButton menuDataKeuangan;
    @FXML private MenuButton menuDataHutang;
    @FXML private MenuButton menuDataPiutang;
    @FXML private MenuButton menuDataModal;
    @FXML private MenuButton menuDataAsetTetap;
    
    @FXML private TitledPane laporanPane;
    @FXML private VBox laporanVbox;
    @FXML private MenuButton menuLaporanBarang;
    @FXML private MenuButton menuLaporanPenjualan;
    @FXML private MenuButton menuLaporanPembelian;
    @FXML private MenuButton menuLaporanKeuangan;
    @FXML private MenuButton menuLaporanManagerial;
    
    @FXML private TitledPane pengaturanPane;
    @FXML private VBox pengaturanVbox;
    @FXML private MenuButton menuDataUser;
    @FXML private MenuButton menuKategoriBarang;
    @FXML private MenuButton menuKategoriPekerjaan;
    @FXML private MenuButton menuKategoriKeuangan;
    @FXML private MenuButton menuKategoriTransaksi;
    @FXML private MenuButton menuKategoriHutang;
    @FXML private MenuButton menuKategoriPiutang;
    
    
    @FXML private ScrollPane scrollPane;
    @FXML private Button notifButton;
    @FXML private ListView<Notification> listView;
    private ObservableList<Notification> allNotif = FXCollections.observableArrayList();
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        try{
            this.mainApp = mainApp;
            menuPane.setPrefWidth(0);
            scrollPane.setPrefWidth(0);
            for(Node n : masterVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : penjualanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pembelianVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : keuanganVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : laporanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pengaturanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : userVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            title.setText(sistem.getNama());
            setUser();
            getAllNotif();
            notifButton.setText(String.valueOf(allNotif.size()));
            listView.setItems(allNotif);
            listView.setCellFactory((ListView<Notification> param) -> {
                ListCell<Notification> cell = new ListCell<Notification>() {
                    @Override
                    protected void updateItem(Notification item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) 
                            setText(item.getJenisTransaksi()+" - "+item.getNoTransaksi()+"\n"
                                    +item.getNama()+"\n"
                                    + "Rp "+df.format(item.getJumlahRp()));
                    }
                };
                return cell;
            });
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }    
    public void getAllNotif()throws Exception{
        allNotif.clear();
        try (Connection con = Koneksi.getConnection()) {
            List<Piutang> allPiutang = PiutangDAO.getAllByStatus(con, "open");
            for(Piutang p : allPiutang){
                if(!p.getJatuhTempo().equals("2000-01-01")){
                    if(tglBarang.parse(p.getJatuhTempo()).before(new Date())||tglBarang.parse(p.getJatuhTempo()).equals(new Date())){
                        Notification notif = new Notification();
                        notif.setNama(p.getKeterangan());
                        notif.setJenisTransaksi(p.getKategori());
                        notif.setNoTransaksi(p.getNoPiutang());
                        notif.setJumlahRp(p.getSisaPiutang());
                        allNotif.add(notif);
                    }
                }
            }
            List<Hutang> allHutang = HutangDAO.getAllByStatus(con, "open");
            for(Hutang h : allHutang){
                if(!h.getJatuhTempo().equals("2000-01-01")){
                    if(tglBarang.parse(h.getJatuhTempo()).before(new Date())||tglBarang.parse(h.getJatuhTempo()).equals(new Date())){
                        Notification notif = new Notification();
                        notif.setNama(h.getKeterangan());
                        notif.setJenisTransaksi(h.getKategori());
                        notif.setNoTransaksi(h.getKategori());
                        notif.setJumlahRp(h.getSisaHutang());
                        allNotif.add(notif);
                    }
                }
            }
        }
    }
    public void setUser() {
        ScrollPane scroll = (ScrollPane) mainApp.mainLayout.getCenter();
        scroll.setContent(null);
        if(sistem.getUser()==null){
            loginButton.setText("Login");
            logoutButton.setVisible(false);
            ubahPasswordButton.setVisible(false);
            mainApp.showLoginScene();
        }else{
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText(sistem.getUser().getUsername());
            for(Otoritas o : sistem.getUser().getOtoritas()){
                if(o.getJenis().equals("Data Customer")){
                    menuDataCustomer.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Supplier")){
                    menuDataSupplier.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Pegawai")){
                    menuDataPegawai.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Barang")){
                    menuDataBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Pekerjaan")){
                    menuDataPekerjaan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pemesanan")){
                    menuPemesanan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan")){
                    menuPenjualan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pengiriman Barang")){
                    menuPengirimanBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Retur Barang")){
                    menuReturBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Beban Penjualan")){
                    menuBebanPenjualan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian")){
                    menuPembelian.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Retur Pembelian")){
                    menuReturPembelian.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Keuangan")){
                    menuDataKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Hutang")){
                    menuDataHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Piutang")){
                    menuDataPiutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Modal")){
                    menuDataModal.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Aset Tetap")){
                    menuDataAsetTetap.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Barang")){
                    menuLaporanBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Penjualan")){
                    menuLaporanPenjualan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Pembelian")){
                    menuLaporanPembelian.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Keuangan")){
                    menuLaporanKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Managerial")){
                    menuLaporanManagerial.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data User")){
                    menuDataUser.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Barang")){
                    menuKategoriBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Pekerjaan")){
                    menuKategoriPekerjaan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Keuangan")){
                    menuKategoriKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Transaksi")){
                    menuKategoriTransaksi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Hutang")){
                    menuKategoriHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Piutang")){
                    menuKategoriPiutang.setVisible(o.isStatus());
                }
            }
        }
        
    }
    @FXML
    public void showHideMenu(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curWidth = 220 * (1.0 - frac) ;
                menuPane.setPrefWidth(curWidth);
                masterPane.setExpanded(false);
                penjualanPane.setExpanded(false);
                pembelianPane.setExpanded(false);
                keuanganPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 220 * frac ;
              menuPane.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (menuPane.getPrefWidth()!=0) 
                hideSidebar.play();
            else 
                showSidebar.play();
        }
    }
    @FXML
    public void showHideNotif(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curWidth = 250 * (1.0 - frac) ;
                scrollPane.setPrefWidth(curWidth);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 250 * frac ;
              scrollPane.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (scrollPane.getPrefWidth()!=0) 
                hideSidebar.play();
            else 
                showSidebar.play();
        }
    }
    public void setTitle(String x){
        title.setText(x);
    }
    @FXML
    private void logout(){
        mainApp.showLoginScene();
    }
    @FXML
    private void exit(){
        System.exit(0);
    }
    @FXML
    private void showUbahPassword(){
        mainApp.showUbahPassword();
    }      
    @FXML
    private void showDataCustomer(){
        mainApp.showDataCustomer();
    }
    @FXML
    private void showDataSupplier(){
        mainApp.showDataSupplier();
    }
    @FXML
    private void showDataPegawai(){
        mainApp.showDataPegawai();
    }
    @FXML
    private void showDataUser(){
        mainApp.showDataUser();
    }
    @FXML
    private void showPengaturanUmum(){
        mainApp.showPengaturanUmum();
    }
    @FXML
    private void showKategoriBarang(){
        mainApp.showKategoriBarang();
    }
    @FXML
    private void showKategoriPekerjaan(){
        mainApp.showKategoriPekerjaan();
    }
    @FXML
    private void showKategoriHutang(){
        mainApp.showKategoriHutang();
    }
    @FXML
    private void showKategoriPiutang(){
        mainApp.showKategoriPiutang();
    }
    @FXML
    private void showKategoriKeuangan(){
        mainApp.showKategoriKeuangan();
    }
    @FXML
    private void showKategoriTransaksi(){
        mainApp.showKategoriTransaksi();
    }
    @FXML
    private void showDataBarang(){
        mainApp.showDataBarang();
    }
    @FXML
    private void showDataPekerjaan(){
        mainApp.showDataPekerjaan();
    }
    @FXML
    private void showKeuangan(){
        mainApp.showKeuangan();
    }
    @FXML
    private void showHutang(){
        mainApp.showHutang();
    }
    @FXML
    private void showPiutang(){
        mainApp.showPiutang();
    }
    @FXML
    private void showModal(){
        mainApp.showModal();
    }
    @FXML
    private void showAsetTetap(){
        mainApp.showAsetTetap();
    }
    @FXML
    private void showPengirimanBarang(){
        mainApp.showPengirimanBarang();
    }
    @FXML
    private void showPemesanan(){
        mainApp.showPemesanan();
    }
    @FXML
    private void showPenjualan(){
        mainApp.showPenjualan();
    }
    @FXML
    private void showPembelian(){
        mainApp.showPembelian();
    }
    @FXML
    private void showReturBarang(){
        mainApp.showRetur();
    }
    @FXML
    private void showBebanPenjualan(){
        mainApp.showBebanPenjualan();
    }
    @FXML
    private void showReturPembelian(){
        mainApp.showReturPembelian();
    }
    @FXML
    private void showLaporanBarang(){
        mainApp.showLaporanBarang();
    }
    @FXML
    private void showLaporanPenjualan(){
        mainApp.showLaporanPenjualan();
    }
    @FXML
    private void showLaporanBarangTerjual(){
        mainApp.showLaporanBarangTerjual();
    }
    @FXML
    private void showLaporanBarangDikirim(){
        mainApp.showLaporanBarangDikirim();
    }
    @FXML
    private void showLaporanBarangDiretur(){
        mainApp.showLaporanBarangDiretur();
    }
    @FXML
    private void showLaporanBebanPenjualanTerbayar(){
        mainApp.showLaporanBebanPenjualanTerbayar();
    }
    @FXML
    private void showLaporanPembelian(){
        mainApp.showLaporanPembelian();
    }
    @FXML
    private void showLaporanBarangDibeli(){
        mainApp.showLaporanBarangDibeli();
    }
    @FXML
    private void showLaporanReturPembelian(){
        mainApp.showLaporanReturPembelian();
    }
    @FXML
    private void showLaporanBarangDireturBeli(){
        mainApp.showLaporanBarangDireturBeli();
    }
    @FXML
    private void showLaporanProyek(){
        mainApp.showLaporanProyek();
    }
    @FXML
    private void showLaporanUntungRugi(){
        mainApp.showLaporanUntungRugi();
    }
    @FXML
    private void showLaporanNeraca(){
        mainApp.showLaporanNeraca();
    }
    @FXML
    private void showLaporanKeuangan(){
        mainApp.showLaporanKeuangan();
    }
    @FXML
    private void showLaporanHutang(){
        mainApp.showLaporanHutang();
    }
    @FXML
    private void showLaporanPiutang(){
        mainApp.showLaporanPiutang();
    }
}
