/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Helper.Notification;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Piutang;
import java.sql.Connection;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 *
 * @author Xtreme
 */
public class MainAppController {

    
    @FXML public VBox vbox;
    @FXML private Accordion accordion;
    @FXML private Label title;
    
    @FXML private TitledPane loginButton;
    @FXML private MenuButton logoutButton;
    @FXML private MenuButton ubahPasswordButton;
    
    @FXML private TitledPane dashboardPane;
    
    @FXML private TitledPane masterPane;
    @FXML private VBox masterVbox;
    @FXML private MenuButton menuDataCustomer;
    @FXML private MenuButton menuDataSupplier;
    @FXML private MenuButton menuDataPegawai;
    @FXML private MenuButton menuDataBahan;
    @FXML private MenuButton menuDataBarang;
    
    @FXML private TitledPane absensiPane;
    @FXML private VBox absensiVbox;
    @FXML private MenuButton menuInputAbsensi;
    @FXML private MenuButton menuLaporanAbsensi;
    
    @FXML private TitledPane penjualanPane;
    @FXML private VBox penjualanVbox;
    @FXML private MenuButton menuPemesanan;
    @FXML private MenuButton menuPenjualan;
    @FXML private MenuButton menuPemesananCoil;
    @FXML private MenuButton menuPenjualanCoil;
    
    @FXML private TitledPane pembelianPane;
    @FXML private VBox pembelianVbox;
    @FXML private MenuButton menuPembelianBarang;
    @FXML private MenuButton menuPembelian;
    
    @FXML private TitledPane barangPane;
    @FXML private VBox barangVbox;
    @FXML private MenuButton menuPermintaanBarang;
    @FXML private MenuButton menuProduksiBarang;
    @FXML private MenuButton menuPengirimanBarang;
    @FXML private MenuButton menuPengirimanCoil;
    @FXML private MenuButton menuPindahBarang;
    @FXML private MenuButton menuPindahBahan;
    
    @FXML private TitledPane keuanganPane;
    @FXML private VBox keuanganVbox;
    @FXML private MenuButton menuKeuangan;
    @FXML private MenuButton menuHutang;
    @FXML private MenuButton menuPiutang;
    @FXML private MenuButton menuModal;
    @FXML private MenuButton menuAsetTetap;
    
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
    @FXML private MenuButton menuDataGudang;
    @FXML private MenuButton menuKategoriBahan;
    @FXML private MenuButton menuKategoriHutang;
    @FXML private MenuButton menuKategoriPiutang;
    @FXML private MenuButton menuKategoriKeuangan;
    @FXML private MenuButton menuKategoriTransaksi;
    
    private Main mainApp;
    private ObservableList<Notification> allNotif = FXCollections.observableArrayList();
    public void setMainApp(Main mainApp) {
        try{
            this.mainApp = mainApp;
            vbox.setPrefWidth(0);
            vbox.setVisible(false);
            for(Node n : vbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : masterVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : absensiVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : penjualanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pembelianVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : barangVbox.getChildren()){
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
            title.setText("AURI STEEL METALINDO");
            setUser();
            getAllNotif();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    public void setUser() {
        dashboardPane.setVisible(false);

        menuDataCustomer.setVisible(false);
        menuDataSupplier.setVisible(false);
        menuDataPegawai.setVisible(false);
        menuDataBahan.setVisible(false);
        menuDataBarang.setVisible(false);

        menuInputAbsensi.setVisible(false);
        menuLaporanAbsensi.setVisible(false);
        
        menuPemesanan.setVisible(false);
        menuPenjualan.setVisible(false);
        menuPemesananCoil.setVisible(false);
        menuPenjualanCoil.setVisible(false);

        menuPembelian.setVisible(false);
        menuPembelianBarang.setVisible(false);

        menuPermintaanBarang.setVisible(false);
        menuProduksiBarang.setVisible(false);
        menuPengirimanBarang.setVisible(false);
        menuPengirimanCoil.setVisible(false);
        menuPindahBahan.setVisible(false);
        menuPindahBarang.setVisible(false);

        menuKeuangan.setVisible(false);
        menuHutang.setVisible(false);
        menuPiutang.setVisible(false);
        menuModal.setVisible(false);
        menuAsetTetap.setVisible(false);

        menuLaporanBarang.setVisible(false);
        menuLaporanPenjualan.setVisible(false);
        menuLaporanPembelian.setVisible(false);
        menuLaporanKeuangan.setVisible(false);
        menuLaporanManagerial.setVisible(false);

        menuDataUser.setVisible(false);
        menuDataGudang.setVisible(false);
        menuKategoriBahan.setVisible(false);
        menuKategoriHutang.setVisible(false);
        menuKategoriPiutang.setVisible(false);
        menuKategoriKeuangan.setVisible(false);
        menuKategoriTransaksi.setVisible(false);
        if(sistem.getUser()==null){
            mainApp.showLoginScene();
        }else{
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText("User : "+sistem.getUser().getKodeUser());
            for(Otoritas o : sistem.getUser().getOtoritas()){
                if(o.getJenis().equals("Dashboard")){
                    if(o.isStatus())
                        dashboardPane.setVisible(true);
                    else
                        accordion.getPanes().remove(dashboardPane);
                }else if(o.getJenis().equals("Data Customer")){
                    menuDataCustomer.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Supplier")){
                    menuDataSupplier.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Pegawai")){
                    menuDataPegawai.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Bahan")){
                    menuDataBahan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Barang")){
                    menuDataBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Absensi")){
                    menuInputAbsensi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Absensi")){
                    menuLaporanAbsensi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pemesanan")){
                    menuPemesanan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan")){
                    menuPenjualan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pemesanan Coil")){
                    menuPemesananCoil.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan Coil")){
                    menuPenjualanCoil.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian")){
                    menuPembelian.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian Barang")){
                    menuPembelianBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Permintaan Barang")){
                    menuPermintaanBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Produksi Barang")){
                    menuProduksiBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pengiriman Barang")){
                    menuPengirimanBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pengiriman Coil")){
                    menuPengirimanCoil.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pindah Bahan")){
                    menuPindahBahan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pindah Barang")){
                    menuPindahBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Keuangan")){
                    menuKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Hutang")){
                    menuHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Piutang")){
                    menuPiutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Modal")){
                    menuModal.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Aset Tetap")){
                    menuAsetTetap.setVisible(o.isStatus());
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
                }else if(o.getJenis().equals("Data Gudang")){
                    menuDataGudang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Bahan")){
                    menuKategoriBahan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Hutang")){
                    menuKategoriHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Piutang")){
                    menuKategoriPiutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Keuangan")){
                    menuKategoriKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Transaksi")){
                    menuKategoriTransaksi.setVisible(o.isStatus());
                }
            }
            if(menuDataCustomer.isVisible()==false &&
                    menuDataSupplier.isVisible()==false &&
                    menuDataPegawai.isVisible()==false &&
                    menuDataBahan.isVisible()==false &&
                    menuDataBarang.isVisible()==false ){
                accordion.getPanes().remove(masterPane);
            }
            if(menuInputAbsensi.isVisible()==false &&
                    menuLaporanAbsensi.isVisible()==false ){
                accordion.getPanes().remove(absensiPane);
            }
            if(menuPemesanan.isVisible()==false &&
                    menuPenjualan.isVisible()==false &&
                    menuPemesananCoil.isVisible()==false &&
                    menuPenjualanCoil.isVisible()==false){
                accordion.getPanes().remove(penjualanPane);
            }
            if(menuPembelian.isVisible()==false &&
                    menuPembelianBarang.isVisible()==false){
                accordion.getPanes().remove(pembelianPane);
            }
            if(menuPermintaanBarang.isVisible()==false &&
                    menuProduksiBarang.isVisible()==false &&
                    menuPengirimanBarang.isVisible()==false &&
                    menuPengirimanCoil.isVisible()==false &&
                    menuPindahBahan.isVisible()==false &&
                    menuPindahBarang.isVisible()==false){
                accordion.getPanes().remove(barangPane);
            }
            if(menuKeuangan.isVisible()==false &&
                    menuHutang.isVisible()==false &&
                    menuPiutang.isVisible()==false &&
                    menuModal.isVisible()==false &&
                    menuAsetTetap.isVisible()==false){
                accordion.getPanes().remove(keuanganPane);
            }
            if(menuLaporanBarang.isVisible()==false &&
                    menuLaporanPenjualan.isVisible()==false &&
                    menuLaporanPembelian.isVisible()==false &&
                    menuLaporanKeuangan.isVisible()==false &&
                    menuLaporanManagerial.isVisible()==false){
                accordion.getPanes().remove(laporanPane);
            }
            if(menuDataUser.isVisible()==false &&
                    menuDataGudang.isVisible()==false &&
                    menuKategoriBahan.isVisible()==false &&
                    menuKategoriHutang.isVisible()==false &&
                    menuKategoriPiutang.isVisible()==false&&
                    menuKategoriKeuangan.isVisible()==false&&
                    menuKategoriTransaksi.isVisible()==false){
                accordion.getPanes().remove(pengaturanPane);
            }
        }
        
    }
    public void getAllNotif()throws Exception{
        allNotif.clear();
        try (Connection con = Koneksi.getConnection()) {
            List<Piutang> allPiutang = PiutangDAO.getAllByStatus(con, "open");
            for(Piutang p : allPiutang){
                if(!p.getJatuhTempo().equals("2000-01-01")){
                    if(tglBarang.parse(p.getJatuhTempo()).before(Function.getServerDate(con))||tglBarang.parse(p.getJatuhTempo()).equals(Function.getServerDate(con))){
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
                    if(tglBarang.parse(h.getJatuhTempo()).before(Function.getServerDate(con))||tglBarang.parse(h.getJatuhTempo()).equals(Function.getServerDate(con))){
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
    @FXML
    public void showHideMenu(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(10)); }
            @Override
            protected void interpolate(double frac) {
                final double curWidth = 220 * (1.0 - frac);
                vbox.setPrefWidth(curWidth);
                masterPane.setExpanded(false);
                absensiPane.setExpanded(false);
                penjualanPane.setExpanded(false);
                pembelianPane.setExpanded(false);
                barangPane.setExpanded(false);
                keuanganPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
                loginButton.setExpanded(false);
            }
        };
        hideSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
            vbox.setVisible(false);
        });
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(10)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 220 * frac;
              vbox.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (vbox.isVisible()) 
                hideSidebar.play();
            else {
                vbox.setVisible(true);
                showSidebar.play();
            }
        }
    }
    public void setTitle(String x){
        title.setText(x);
    }
    @FXML
    public void menuLogout() {
        mainApp.showLoginScene();
    }
    @FXML
    public void menuExit() {
        System.exit(0);
    }
    @FXML
    public void menushowUbahPassword() {
        mainApp.showUbahPassword();
    }
    @FXML
    public void menuDashboard() {
        mainApp.showDashboard();
    }
    @FXML
    public void menuDataCustomer() {
        mainApp.showDataCustomer();
    }
    @FXML
    public void menuDataSupplier() {
        mainApp.showDataSupplier();
    }
    @FXML
    public void menuDataPegawai() {
        mainApp.showDataPegawai();
    }
    @FXML
    public void menuDataBahan() {
        mainApp.showDataBahan();
    }
    @FXML
    public void menuDataBarang() {
        mainApp.showDataBarang();
    }
    @FXML
    public void menuDataPaketBarang() {
    }
    @FXML
    public void menuInputAbsensi() {
        mainApp.showDataAbsensi();
    }
    @FXML
    public void menuLaporanAbsensi() {
    }
    @FXML
    public void menuDataUser() {
        mainApp.showDataUser();
    }
    @FXML
    private void menuDataGudang(){
        mainApp.showGudang();
    }
    @FXML
    private void showKategoriBahan(){
        mainApp.showKategoriBahan();
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
    public void menuPenjualan() {
        mainApp.showPenjualan();
    }
    @FXML
    public void menuPenjualanCoil() {
        mainApp.showPenjualanCoil();
    }
    @FXML
    public void menuPemesanan() {
        mainApp.showPemesanan();
    }
    @FXML
    public void menuPemesananCoil() {
        mainApp.showPemesananCoil();
    }
    @FXML
    public void menuPembelian() {
        mainApp.showPembelian();
    }
    @FXML
    public void menuPembelianBarang() {
        mainApp.showPembelianBarang();
    }
    @FXML
    public void menuPermintaanBarang() {
        mainApp.showPermintaanBarang();
    }
    @FXML
    public void menuProduksiBarang() {
        mainApp.showProduksiBarang();
    }
    @FXML
    public void menuPengirimanBarang() {
        mainApp.showPengirimanBarang();
    }
    @FXML
    public void menuPengirimanCoil() {
        mainApp.showPengirimanCoil();
    }
    @FXML
    public void menuPindahBarang() {
        mainApp.showPindahBarang();
    }
    @FXML
    public void menuPindahBahan() {
        mainApp.showPindahBahan();
    }
    @FXML
    public void menuKeuangan() {
        mainApp.showKeuangan();
    }
    @FXML
    public void menuHutang() {
        mainApp.showHutang();
    }
    @FXML
    public void menuPiutang() {
        mainApp.showPiutang();
    }
    @FXML
    public void menuModal() {
        mainApp.showModal();
    }
    @FXML
    public void menuAsetTetap() {
        mainApp.showAsetTetap();
    }
    @FXML
    public void menuLaporanBarang() {
        mainApp.showLaporanBarang();
    }
    @FXML
    public void menuLaporanBahan() {
        mainApp.showLaporanBahan();
    }
    @FXML
    public void menuLaporanProduksiBarang() {
        mainApp.showLaporanProduksiBarang();
    }
    @FXML
    public void menuLaporanBarangDiproduksi() {
        mainApp.showLaporanBarangDiproduksi();
    }
    @FXML
    public void menuLaporanPenyesuaianStokBahan() {
        mainApp.showLaporanPenyesuaianStokBahan();
    }
    @FXML
    public void menuLaporanPenyesuaianStokBarang() {
        mainApp.showLaporanPenyesuaianStokBarang();
    }

    @FXML
    public void menuLaporanPenjualan() {
        mainApp.showLaporanPenjualan();
    }
    @FXML
    public void menuLaporanBarangTerjual() {
        mainApp.showLaporanBarangTerjual();
    }
    @FXML
    public void menuLaporanPenjualanCoil() {
        mainApp.showLaporanPenjualanCoil();
    }
    @FXML
    public void menuLaporanCoilTerjual() {
        mainApp.showLaporanCoilTerjual();
    }



    @FXML
    public void menuLaporanPembelian() {
        mainApp.showLaporanPembelian();
    }
    @FXML
    public void menuLaporanBahanDibeli() {
        mainApp.showLaporanBahanDibeli();
    }

    @FXML
    public void menuLaporanKeuangan() {
        mainApp.showLaporanKeuangan();
    }

    @FXML
    public void menuLaporanHutang() {
        mainApp.showLaporanHutang();
    }

    @FXML
    public void menuLaporanPiutang() {
        mainApp.showLaporanPiutang();
    }

    @FXML
    public void menuLaporanUntungRugiPerpetual() {
        mainApp.showLaporanUntungRugi();
    }
    @FXML
    public void menuLaporanUntungRugiPeriode() {
        mainApp.showLaporanUntungRugiPeriode();
    }


    @FXML
    public void menuLaporanNeracaPerpetual() {
        mainApp.showLaporanNeraca();
    }
    @FXML
    public void menuLaporanUntungRugiSummary() {
        mainApp.showLaporanUntungRugiSummary();
    }
}
