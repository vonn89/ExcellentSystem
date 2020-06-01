/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class MainController {

    @FXML public VBox menuPane;
    @FXML private Label title;
    
    @FXML private VBox userVbox;
    @FXML private TitledPane loginButton;
    @FXML private MenuButton logoutButton;
    @FXML private MenuButton ubahPasswordButton;
    
    @FXML private MenuButton dataProperty;
    @FXML private MenuButton dataCustomer;
    @FXML private MenuButton dataKaryawan;
    @FXML private MenuButton dataTukang;
    
    @FXML private MenuButton pembelianTanah;
    @FXML private MenuButton pembangunan;
    
    @FXML private MenuButton rencanaAnggaranProyek;
    @FXML private MenuButton realisasiAnggaranProyek;
    
    @FXML private MenuButton terimaTandaJadi;
    @FXML private MenuButton terimaDownPayment;
    @FXML private MenuButton pelunasanDownPayment;
    @FXML private MenuButton terimaPencairanKPR;
    @FXML private MenuButton terimaAngsuran;
    @FXML private MenuButton pelunasanAngsuran;
    
    @FXML private MenuButton dataKeuangan;
    @FXML private MenuButton dataHutang;
    @FXML private MenuButton dataPiutang;
    @FXML private MenuButton dataModal;
    @FXML private MenuButton dataAsetTetap;
    
    @FXML private MenuButton dataUser;
    @FXML private MenuButton kategoriHutang;
    @FXML private MenuButton kateogriPiutang;
    @FXML private MenuButton kategoriTransaksi;
    @FXML private MenuButton kategoriProperty;
    @FXML private MenuButton kategoriKeuangan;
    
    @FXML private MenuButton laporanProperty;
    @FXML private MenuButton laporanUntungRugi;
    @FXML private MenuButton laporanUntungRugiPeriode;
    @FXML private MenuButton laporanNeraca;
    
    @FXML private VBox masterVbox;
    @FXML private VBox pembelianVbox;
    @FXML private VBox proyekVbox;
    @FXML private VBox penjualanVbox;
    @FXML private VBox keuanganVbox;
    @FXML private VBox laporanVbox;
    @FXML private VBox settingsVbox;
    
    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        menuPane.setPrefWidth(0);
        for(Node n : masterVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : pembelianVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : proyekVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : penjualanVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : keuanganVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : laporanVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : settingsVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        for(Node n : userVbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        setUser();
    }    
    public void setUser() {
        title.setText("JAGO BANGUN PERSADA");
        if(sistem.getUser()==null){
            loginButton.setText("Login");
            logoutButton.setVisible(false);
            ubahPasswordButton.setVisible(false);
            mainApp.showLoginScene();
            
            dataProperty.setVisible(false);
            dataCustomer.setVisible(false);
            dataKaryawan.setVisible(false);
            dataTukang.setVisible(false);

            pembelianTanah.setVisible(false);
            pembangunan.setVisible(false);
            
            rencanaAnggaranProyek.setVisible(false);
            realisasiAnggaranProyek.setVisible(false);

            terimaTandaJadi.setVisible(false);
            terimaDownPayment.setVisible(false);
            pelunasanDownPayment.setVisible(false);
            terimaPencairanKPR.setVisible(false);
            terimaAngsuran.setVisible(false);
            pelunasanAngsuran.setVisible(false);

            dataKeuangan.setVisible(false);
            dataHutang.setVisible(false);
            dataPiutang.setVisible(false);
            dataModal.setVisible(false);
            dataAsetTetap.setVisible(false);

            dataUser.setVisible(false);
            kategoriHutang.setVisible(false);
            kateogriPiutang.setVisible(false);
            kategoriTransaksi.setVisible(false);
            kategoriProperty.setVisible(false);
            kategoriKeuangan.setVisible(false);
            
            laporanProperty.setVisible(false);
            laporanUntungRugi.setVisible(false);
            laporanUntungRugiPeriode.setVisible(false);
            laporanNeraca.setVisible(false);
        }else{
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText(sistem.getUser().getUsername());
            for(Otoritas o : sistem.getUser().getOtoritas()){
                if(o.getJenis().equals("Data Property")){
                    dataProperty.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Customer")){
                    dataCustomer.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Karyawan")){
                    dataKaryawan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Tukang")){
                    dataTukang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian Tanah")){
                    pembelianTanah.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembangunan")){
                    pembangunan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Rencana Anggaran Proyek")){
                    rencanaAnggaranProyek.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Realisasi Anggaran Proyek")){
                    realisasiAnggaranProyek.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Tanda Jadi")){
                    terimaTandaJadi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Down Payment")){
                    terimaDownPayment.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pelunasan Down Payment")){
                    pelunasanDownPayment.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Pencairan KPR")){
                    terimaPencairanKPR.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Angsuran Pembayaran")){
                    terimaAngsuran.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pelunasan Angsuran")){
                    pelunasanAngsuran.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Keuangan")){
                    dataKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Hutang")){
                    dataHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Piutang")){
                    dataPiutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Modal")){
                    dataModal.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Aset Tetap")){
                    dataAsetTetap.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data User")){
                    dataUser.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Hutang")){
                    kategoriHutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Piutang")){
                    kateogriPiutang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Transaksi")){
                    kategoriTransaksi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Property")){
                    kategoriProperty.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Kategori Keuangan")){
                    kategoriKeuangan.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Property")){
                    laporanProperty.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Untung Rugi")){
                    laporanUntungRugi.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Untung Rugi Periode")){
                    laporanUntungRugiPeriode.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Neraca")){
                    laporanNeraca.setVisible(o.isStatus());
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
//                dashboardPane.setExpanded(false);
//                pusatPane.setExpanded(false);
//                barangCabangPane.setExpanded(false);
//                spCabangPane.setExpanded(false);
//                rosokDanCiokCabangPane.setExpanded(false);
//                keuanganCabangPane.setExpanded(false);
//                laporanPane.setExpanded(false);
//                pengaturanPane.setExpanded(false);
//                administrasiToolPane.setExpanded(false);
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
//    @FXML
//    public void showMenu() {
//          final Animation hideSidebar = new Transition() {
//            { setCycleDuration(Duration.millis(230)); }
//            @Override
//            protected void interpolate(double frac) {
//              final double curWidth = 230 * (1.0 - frac);
//              stackPane.setPrefWidth(curWidth);
//              stackPane.setTranslateX(-230 + curWidth);
//            }
//          };
//          hideSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
//              stackPane.setVisible(false);
//          });
//          final Animation showSidebar = new Transition() {
//            { setCycleDuration(Duration.millis(230)); }
//            @Override
//            protected void interpolate(double frac) {
//              final double curWidth = 230 * frac;
//              stackPane.setPrefWidth(curWidth);
//              stackPane.setTranslateX(-230 + curWidth);
//            }
//          };
//          showSidebar.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
//              stackPane.setVisible(true);
//          });
//          if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
//            if (stackPane.isVisible()) {
//              hideSidebar.play();
//            } else {
//              stackPane.setVisible(true);
//              showSidebar.play();
//            }
//          }
//    }
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
    private void showDataKaryawan(){
        mainApp.showDataKaryawan();
    } 
    @FXML
    private void showDataTukang(){
        mainApp.showDataTukang();
    }  
    @FXML
    private void showDataProperty(){
        mainApp.showDataProperty();
    }
    @FXML
    private void showPembelianTanah(){
        mainApp.showPembelianTanah();
    }
    @FXML
    private void showPembangunan(){
        mainApp.showPembangunan();
    }
    @FXML
    private void showRencanaAnggaranProyek(){
        mainApp.showRencanaAnggaranProyek();
    }
    @FXML
    private void showRealisasiAnggaranProyek(){
        mainApp.showRealisasiAnggaranProyek();
    }
    @FXML
    private void showTerimaTandaJadi(){
        mainApp.showTerimaTandaJadi();
    }
    @FXML
    private void showTerimaDownPayment(){
        mainApp.showTerimaDownPayment();
    }
    @FXML
    private void showPelunasanDownPayment(){
        mainApp.showPelunasanDownPayment();
    }
    @FXML
    private void showPencairanKPR(){
        mainApp.showPencairanKPR();
    }
    @FXML
    private void showTerimaAngsuran(){
        mainApp.showTerimaAngsuranPembayaran();
    }
    @FXML
    private void showPelunasanAngsuran(){
        mainApp.showPelunasanPembayaran();
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
    private void showLaporanUntungRugi(){
        mainApp.showLaporanUntungRugi();
    }
    @FXML
    private void showLaporanUntungRugiPeriode(){
        mainApp.showLaporanUntungRugiPeriode();
    }
    @FXML
    private void showLaporanUntungRugiProperty(){
        mainApp.showLaporanUntungRugiProperty();
    }
    @FXML
    private void showLaporanNeraca(){
        mainApp.showLaporanNeraca();
    }
    @FXML
    private void showDataUser(){
        mainApp.showDataUser();
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
    private void showKategoriProperty(){
        mainApp.showKategoriProperty();
    }
    @FXML
    private void showTipeKeuangan(){
        mainApp.showTipeKeuangan();
    }
    @FXML
    private void showKategoriTransaksi(){
        mainApp.showKategoriTransaksi();
    }
}
