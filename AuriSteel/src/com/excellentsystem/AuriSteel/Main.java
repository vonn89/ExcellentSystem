/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel;

import com.excellentsystem.AuriSteel.DAO.GudangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriBahanDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriHutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriKeuanganDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriPiutangDAO;
import com.excellentsystem.AuriSteel.DAO.KategoriTransaksiDAO;
import com.excellentsystem.AuriSteel.DAO.OtoritasDAO;
import com.excellentsystem.AuriSteel.DAO.SistemDAO;
import com.excellentsystem.AuriSteel.DAO.UserDAO;
import static com.excellentsystem.AuriSteel.Function.createSecretKey;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Sistem;
import com.excellentsystem.AuriSteel.Model.User;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.AsetTetapController;
import com.excellentsystem.AuriSteel.View.DashboardController;
import com.excellentsystem.AuriSteel.View.DataAbsensiController;
import com.excellentsystem.AuriSteel.View.DataBahanController;
import com.excellentsystem.AuriSteel.View.DataBarangController;
import com.excellentsystem.AuriSteel.View.DataCustomerController;
import com.excellentsystem.AuriSteel.View.DataPegawaiController;
import com.excellentsystem.AuriSteel.View.DataSupplierController;
import com.excellentsystem.AuriSteel.View.DataUserController;
import com.excellentsystem.AuriSteel.View.Dialog.GudangController;
import com.excellentsystem.AuriSteel.View.Dialog.KategoriBahanController;
import com.excellentsystem.AuriSteel.View.Dialog.KategoriHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.KategoriKeuanganController;
import com.excellentsystem.AuriSteel.View.Dialog.KategoriPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.KategoriTransaksiController;
import com.excellentsystem.AuriSteel.View.Dialog.MessageController;
import com.excellentsystem.AuriSteel.View.Dialog.SplashScreenController;
import com.excellentsystem.AuriSteel.View.Dialog.UbahPasswordController;
import com.excellentsystem.AuriSteel.View.HutangController;
import com.excellentsystem.AuriSteel.View.KeuanganController;
import com.excellentsystem.AuriSteel.View.LoginController;
import com.excellentsystem.AuriSteel.View.MainAppController;
import com.excellentsystem.AuriSteel.View.ModalController;
import com.excellentsystem.AuriSteel.View.PembelianBarangController;
import com.excellentsystem.AuriSteel.View.PembelianController;
import com.excellentsystem.AuriSteel.View.PemesananCoilController;
import com.excellentsystem.AuriSteel.View.PemesananController;
import com.excellentsystem.AuriSteel.View.PengirimanBarangController;
import com.excellentsystem.AuriSteel.View.PengirimanCoilController;
import com.excellentsystem.AuriSteel.View.PenjualanCoilController;
import com.excellentsystem.AuriSteel.View.PenjualanController;
import com.excellentsystem.AuriSteel.View.PermintaanBarangController;
import com.excellentsystem.AuriSteel.View.PindahBahanController;
import com.excellentsystem.AuriSteel.View.PindahBarangController;
import com.excellentsystem.AuriSteel.View.PiutangController;
import com.excellentsystem.AuriSteel.View.ProduksiBarangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanBahanController;
import com.excellentsystem.AuriSteel.View.Report.LaporanBahanDibeliController;
import com.excellentsystem.AuriSteel.View.Report.LaporanBarangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanBarangDiproduksiController;
import com.excellentsystem.AuriSteel.View.Report.LaporanBarangTerjualController;
import com.excellentsystem.AuriSteel.View.Report.LaporanCoilTerjualController;
import com.excellentsystem.AuriSteel.View.Report.LaporanHutangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanKeuanganController;
import com.excellentsystem.AuriSteel.View.Report.LaporanNeracaController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPembelianController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPenjualanController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPenyesuaianStokBahanController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPenyesuaianStokBarangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanPiutangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanProduksiBarangController;
import com.excellentsystem.AuriSteel.View.Report.LaporanUntungRugiController;
import com.excellentsystem.AuriSteel.View.Report.LaporanUntungRugiPeriodeController;
import com.excellentsystem.AuriSteel.View.Report.LaporanUntungRugiSummaryController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Xtreme
 */
public class Main extends Application {

    public static DecimalFormat df = new DecimalFormat("###,##0.##;(###,##0.##)");
//    public static DecimalFormat df = new DecimalFormat("###,##0.##");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tgl = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    public static DateFormat yymm = new SimpleDateFormat("yyMM");
    public static DateFormat yymmdd = new SimpleDateFormat("yyMMdd");
    
    public Stage MainStage;
    public Stage loading;
    public Stage message;
    
    public BorderPane mainLayout;
    public Dimension screenSize;
    private MainAppController mainAppController;
    
    public static Sistem sistem;
    private double x = 0;
    private double y = 0;
    public final String version = "2.2.8";
    public static SecretKeySpec key;
    @Override
    public void start(Stage stage)  {
        MainStage = stage;
        MainStage.setTitle("Auri Steel Metalindo");
        MainStage.setMaximized(true);
        MainStage.getIcons().add(new Image(getClass().getResourceAsStream("Resource/icon.png")));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            Font.loadFont(getClass().getResourceAsStream("Resource/Lato-Regular.ttf"), 12);
//            Font.loadFont(getClass().getResourceAsStream("Resource/Lato-Bold.ttf"), 12);
//            Font.loadFont(getClass().getResourceAsStream("Resource/BodonBk.ttf"), 12);
        ProgressBar progress = new ProgressBar();
        Label updateLabel = new Label();
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                updateMessage("connecting to server...");
                updateProgress(10, 100);
                try (Connection con = Koneksi.getConnection()) {
                    updateProgress(20, 100);
                    String password = "password";
                    byte[] salt = "12345678".getBytes();
                    key = createSecretKey(password.toCharArray(), salt, 40000, 128);
                    updateProgress(30, 100);
                    updateMessage("initializing system...");
                    sistem = SistemDAO.getSystem(con);
                    sistem.setListKategoriKeuangan(KategoriKeuanganDAO.getAll(con));
                    sistem.setListKategoriTransaksi(KategoriTransaksiDAO.getAllByStatus(con, "true"));
                    sistem.setListGudang(GudangDAO.getAll(con));
                    sistem.setListKategoriBahan(KategoriBahanDAO.getAllByStatus(con, "true"));
                    sistem.setListKategoriHutang(KategoriHutangDAO.getAll(con));
                    sistem.setListKategoriPiutang(KategoriPiutangDAO.getAll(con));
                    List<User> listUser = UserDAO.getAll(con);
                    List<Otoritas> listOtoritas = OtoritasDAO.getAll(con);
                    for(User u : listUser){
                        List<Otoritas> otoritas = new ArrayList<>();
                        for(Otoritas o : listOtoritas){
                            if(u.getKodeUser().equals(o.getKodeUser()))
                                otoritas.add(o);
                        }
                        u.setOtoritas(otoritas);
                    }
                    sistem.setListUser(listUser);
                    updateProgress(40, 100);
                    updateMessage("checking for updates...");
                    if(!version.equals(sistem.getVersion())){
                        updateMessage("updating software...");
                        updateProgress(50, 100);
                        return Function.downloadUpdateGoogleStorage("Auri Steel.exe");
                    }
//                Service.createAbsensi(con, 01, 2020);
////                if(Function.getServerDate(con).getDate()==1){
                    Service.setPenyusutanAset(con);
////                }
                    updateProgress(70, 100);
                    Thread.sleep(500);
                    updateProgress(80, 100);
                    Thread.sleep(500);
                    updateProgress(90, 100);
                    Thread.sleep(500);
                    updateProgress(100, 100);
                    return "true";
                }
            }
        };
        task.setOnRunning((e) -> {
            showSplashScreen(progress, updateLabel);
        });
        task.setOnSucceeded((e) -> {
            splash.close();
            if(task.getValue().equals("true")){
                showLoginScene();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText(task.getValue());
                alert.showAndWait();
                System.exit(0);
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +task.getException());
            alert.showAndWait();
            System.exit(0);
            splash.close();
        });
        progress.progressProperty().bind(task.progressProperty());
        updateLabel.textProperty().bind(task.messageProperty());
        new Thread(task).start();
    }
    public void showLoginScene() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Login.fxml"));
            AnchorPane container = (AnchorPane) loader.load();
            
            Scene scene = new Scene(container);
            MainStage.hide();
            MainStage.setScene(scene);
            MainStage.show();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    public void showMainScene(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/MainApp.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            
            final Animation animationshow = new Transition() {
                { setCycleDuration(Duration.millis(1000)); }
                @Override
                protected void interpolate(double frac) {
                    MainStage.setOpacity(1-frac);
                }
            };
            animationshow.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
                final Animation animation = new Transition() {
                    { setCycleDuration(Duration.millis(1000)); }
                    @Override
                    protected void interpolate(double frac) {
                        MainStage.setOpacity(frac);
                    }
                };
                animation.play();
                MainStage.hide();
                MainStage.setScene(scene);
                mainAppController = loader.getController();
                
                mainAppController.setMainApp(this);
                if(sistem.getUser().getLevel().equals("Gudang"))
                    showPengirimanBarang();
                if(sistem.getUser().getLevel().equals("Produksi"))
                    showPermintaanBarang();
                if(sistem.getUser().getLevel().equals("Produksi"))
                    showPengirimanBarang();
                if(sistem.getUser().getLevel().equals("Penjualan"))
                    showPenjualan();
                if(sistem.getUser().getLevel().equals("Pembelian"))
                    showPembelian();
                if(sistem.getUser().getLevel().equals("Keuangan"))
                    showKeuangan();
                if(sistem.getUser().getLevel().equals("Manager"))
                    showDashboard();
                
                MainStage.show();
            });
            animationshow.play();
            
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    public DashboardController showDashboard(){
        FXMLLoader loader = changeStage("View/Dashboard.fxml");
        DashboardController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Dashboard");
        return controller;
    }
    public DataCustomerController showDataCustomer(){
        FXMLLoader loader = changeStage("View/DataCustomer.fxml");
        DataCustomerController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Customer");
        return controller;
    }
    public DataSupplierController showDataSupplier(){
        FXMLLoader loader = changeStage("View/DataSupplier.fxml");
        DataSupplierController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Supplier");
        return controller;
    }
    public DataPegawaiController showDataPegawai(){
        FXMLLoader loader = changeStage("View/DataPegawai.fxml");
        DataPegawaiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Pegawai");
        return controller;
    }
    public DataBahanController showDataBahan(){
        FXMLLoader loader = changeStage("View/DataBahan.fxml");
        DataBahanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Bahan");
        return controller;
    }
    public DataBarangController showDataBarang(){
        FXMLLoader loader = changeStage("View/DataBarang.fxml");
        DataBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Barang");
        return controller;
    }
    public DataUserController showDataUser(){
        FXMLLoader loader = changeStage("View/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data User");
        return controller;
    }
    public DataAbsensiController showDataAbsensi(){
        FXMLLoader loader = changeStage("View/DataAbsensi.fxml");
        DataAbsensiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Absensi");
        return controller;
    }
    public PemesananController showPemesanan(){
        FXMLLoader loader = changeStage("View/Pemesanan.fxml");
        PemesananController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pemesanan");
        return controller;
    }
    public PemesananCoilController showPemesananCoil(){
        FXMLLoader loader = changeStage("View/PemesananCoil.fxml");
        PemesananCoilController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pemesanan Coil");
        return controller;
    }
    public PenjualanController showPenjualan(){
        FXMLLoader loader = changeStage("View/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Penjualan");
        return controller;
    }
    public PenjualanCoilController showPenjualanCoil(){
        FXMLLoader loader = changeStage("View/PenjualanCoil.fxml");
        PenjualanCoilController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Penjualan Coil");
        return controller;
    }
    public PembelianController showPembelian(){
        FXMLLoader loader = changeStage("View/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembelian");
        return controller;
    }
    public PembelianBarangController showPembelianBarang(){
        FXMLLoader loader = changeStage("View/PembelianBarang.fxml");
        PembelianBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembelian Barang");
        return controller;
    }
    public PermintaanBarangController showPermintaanBarang(){
        FXMLLoader loader = changeStage("View/PermintaanBarang.fxml");
        PermintaanBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Permintaan Barang");
        return controller;
    }
    public ProduksiBarangController showProduksiBarang(){
        FXMLLoader loader = changeStage("View/ProduksiBarang.fxml");
        ProduksiBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Produksi Barang");
        return controller;
    }
    public PengirimanBarangController showPengirimanBarang(){
        FXMLLoader loader = changeStage("View/PengirimanBarang.fxml");
        PengirimanBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pengiriman Barang");
        return controller;
    }
    public PengirimanCoilController showPengirimanCoil(){
        FXMLLoader loader = changeStage("View/PengirimanCoil.fxml");
        PengirimanCoilController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pengiriman Coil");
        return controller;
    }
    public PindahBahanController showPindahBahan(){
        FXMLLoader loader = changeStage("View/PindahBahan.fxml");
        PindahBahanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pindah Bahan");
        return controller;
    }
    public PindahBarangController showPindahBarang(){
        FXMLLoader loader = changeStage("View/PindahBarang.fxml");
        PindahBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pindah Barang");
        return controller;
    }
    public KeuanganController showKeuangan(){
        FXMLLoader loader = changeStage("View/Keuangan.fxml");
        KeuanganController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Keuangan");
        return controller;
    }
    public HutangController showHutang(){
        FXMLLoader loader = changeStage("View/Hutang.fxml");
        HutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Hutang");
        return controller;
    }
    public PiutangController showPiutang(){
        FXMLLoader loader = changeStage("View/Piutang.fxml");
        PiutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Piutang");
        return controller;
    }
    public ModalController showModal(){
        FXMLLoader loader = changeStage("View/Modal.fxml");
        ModalController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Modal");
        return controller;
    }
    public AsetTetapController showAsetTetap(){
        FXMLLoader loader = changeStage("View/AsetTetap.fxml");
        AsetTetapController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Aset Tetap");
        return controller;
    }
    public LaporanBarangController showLaporanBarang(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarang.fxml");
        LaporanBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang");
        return controller;
    }
    public LaporanBahanController showLaporanBahan(){
        FXMLLoader loader = changeStage("View/Report/LaporanBahan.fxml");
        LaporanBahanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Bahan");
        return controller;
    }
    public LaporanProduksiBarangController showLaporanProduksiBarang(){
        FXMLLoader loader = changeStage("View/Report/LaporanProduksiBarang.fxml");
        LaporanProduksiBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Produksi Barang");
        return controller;
    }
    public LaporanBarangDiproduksiController showLaporanBarangDiproduksi(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangDiproduksi.fxml");
        LaporanBarangDiproduksiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Diproduksi");
        return controller;
    }
    public LaporanPenyesuaianStokBahanController showLaporanPenyesuaianStokBahan(){
        FXMLLoader loader = changeStage("View/Report/LaporanPenyesuaianStokBahan.fxml");
        LaporanPenyesuaianStokBahanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Penyesuaian Stok Bahan");
        return controller;
    }
    public LaporanPenyesuaianStokBarangController showLaporanPenyesuaianStokBarang(){
        FXMLLoader loader = changeStage("View/Report/LaporanPenyesuaianStokBarang.fxml");
        LaporanPenyesuaianStokBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Penyesuaian Stok Barang");
        return controller;
    }
    public LaporanPenjualanController showLaporanPenjualan(){
        FXMLLoader loader = changeStage("View/Report/LaporanPenjualan.fxml");
        LaporanPenjualanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Penjualan");
        return controller;
    }
    public LaporanBarangTerjualController showLaporanBarangTerjual(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangTerjual.fxml");
        LaporanBarangTerjualController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Terjual");
        return controller;
    }
    public LaporanPenjualanCoilController showLaporanPenjualanCoil(){
        FXMLLoader loader = changeStage("View/Report/LaporanPenjualanCoil.fxml");
        LaporanPenjualanCoilController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Penjualan Coil");
        return controller;
    }
    public LaporanCoilTerjualController showLaporanCoilTerjual(){
        FXMLLoader loader = changeStage("View/Report/LaporanCoilTerjual.fxml");
        LaporanCoilTerjualController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Coil Terjual");
        return controller;
    }
    public LaporanPembelianController showLaporanPembelian(){
        FXMLLoader loader = changeStage("View/Report/LaporanPembelian.fxml");
        LaporanPembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Pembelian");
        return controller;
    }
    public LaporanBahanDibeliController showLaporanBahanDibeli(){
        FXMLLoader loader = changeStage("View/Report/LaporanBahanDibeli.fxml");
        LaporanBahanDibeliController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Bahan Dibeli");
        return controller;
    }
    public LaporanKeuanganController showLaporanKeuangan(){
        FXMLLoader loader = changeStage("View/Report/LaporanKeuangan.fxml");
        LaporanKeuanganController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Keuangan");
        return controller;
    }
    public LaporanHutangController showLaporanHutang(){
        FXMLLoader loader = changeStage("View/Report/LaporanHutang.fxml");
        LaporanHutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Hutang");
        return controller;
    }
    public LaporanPiutangController showLaporanPiutang(){
        FXMLLoader loader = changeStage("View/Report/LaporanPiutang.fxml");
        LaporanPiutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Piutang");
        return controller;
    }
    public LaporanUntungRugiController showLaporanUntungRugi(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugi.fxml");
        LaporanUntungRugiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung Rugi");
        return controller;
    }
    public LaporanUntungRugiPeriodeController showLaporanUntungRugiPeriode(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugiPeriode.fxml");
        LaporanUntungRugiPeriodeController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung Rugi Periode");
        return controller;
    }
    public LaporanUntungRugiSummaryController showLaporanUntungRugiSummary(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugiSummary.fxml");
        LaporanUntungRugiSummaryController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung Rugi Summary");
        return controller;
    }
    public LaporanNeracaController showLaporanNeraca(){
        FXMLLoader loader = changeStage("View/Report/LaporanNeraca.fxml");
        LaporanNeracaController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Neraca");
        return controller;
    }
    public KategoriBahanController showKategoriBahan(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriBahan.fxml");
        KategoriBahanController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public GudangController showGudang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/Gudang.fxml");
        GudangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriHutangController showKategoriHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriHutang.fxml");
        KategoriHutangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriPiutangController showKategoriPiutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriPiutang.fxml");
        KategoriPiutangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriTransaksiController showKategoriTransaksi(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriTransaksi.fxml");
        KategoriTransaksiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriKeuanganController showKategoriKeuangan(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriKeuangan.fxml");
        KategoriKeuanganController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public UbahPasswordController showUbahPassword(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/UbahPassword.fxml");
        UbahPasswordController controller = loader.getController();
        controller.setMainApp(this, MainStage ,stage);
        return controller;
    }
    
    public void setTitle(String title){
        mainAppController.setTitle(title);
        if (mainAppController.vbox.isVisible()) 
            mainAppController.showHideMenu();
    }
    public void showLoadingScreen(){
        try{
            if(loading!=null)
                loading.close();
            loading = new Stage();
            loading.initModality(Modality.WINDOW_MODAL);
            loading.initOwner(MainStage);
            loading.initStyle(StageStyle.TRANSPARENT);
            loading.setOnCloseRequest((event) -> {
                event.consume();
            });
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/LoadingScreen.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            loading.setOpacity(0.7);
            loading.hide();
            loading.setScene(scene);
            loading.show();
            
            loading.setHeight(MainStage.getHeight());
            loading.setWidth(MainStage.getWidth());
            loading.setX((MainStage.getWidth() - loading.getWidth()) / 2);
            loading.setY((MainStage.getHeight() - loading.getHeight()) / 2);
        }catch(Exception e){
            showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    public void closeLoading(){
        loading.close();
    }
    public FXMLLoader changeStage(String URL){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();
            BorderPane border = (BorderPane) mainLayout.getCenter();
            border.setCenter(container);
            return loader;
        }catch(Exception e){
            e.printStackTrace();
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }
    private Stage splash;
    public void showSplashScreen(ProgressBar progressBar, Label updateLabel){
        try{
            if(splash!=null)
                splash.close();
            splash = new Stage();
            splash.getIcons().add(new Image(getClass().getResourceAsStream("Resource/icon.png")));
            splash.initModality(Modality.WINDOW_MODAL);
            splash.initOwner(MainStage);
            splash.initStyle(StageStyle.TRANSPARENT);
            splash.setOnCloseRequest((event) -> {
                event.consume();
            });
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/SplashScreen.fxml"));
            AnchorPane container = (AnchorPane) loader.load();
            SplashScreenController controller = loader.getController();
            progressBar.setPrefWidth(475);
            updateLabel.setStyle("-fx-text-fill: white");
            controller.setSplashScreen(progressBar, updateLabel);

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            splash.hide();
            splash.setScene(scene);
            splash.show();
            
            splash.setHeight(screenSize.getHeight());
            splash.setWidth(screenSize.getWidth());
            splash.setX((screenSize.getWidth() - splash.getWidth()) / 2);
            splash.setY((screenSize.getHeight() - splash.getHeight()) / 2);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    public void closeDialog(Stage owner,Stage dialog){
        dialog.close();
        owner.getScene().getRoot().setEffect(new ColorAdjust(0,0,0,0));
    }
    public FXMLLoader showDialog(Stage owner, Stage dialog, String URL){
        try{
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(owner);
            dialog.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
                        
            scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = dialog.getX() - mouseEvent.getScreenX();
                y = dialog.getY() - mouseEvent.getScreenY();
            });
            scene.setOnMouseDragged((MouseEvent mouseEvent) -> {
                dialog.setX(mouseEvent.getScreenX() + x);
                dialog.setY(mouseEvent.getScreenY() + y);
            });
            owner.getScene().getRoot().setEffect(new ColorAdjust(0, 0, -0.5, -0.5));
            dialog.hide();
            dialog.setScene(scene);
            dialog.show();
            //set dialog on center parent
            dialog.setX((screenSize.getWidth() - dialog.getWidth()) / 2);
            dialog.setY((screenSize.getHeight() - dialog.getHeight()) / 2);
            return loader;
        }catch(IOException e){
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
    }
    public MessageController showMessage(Modality modal,String type,String content){
        try{
            if(message!=null)
                message.close();
            message = new Stage();
            message.initModality(modal);
            message.initOwner(MainStage);
            message.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/Message.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            message.setX(MainStage.getWidth()-450);
            message.setY(MainStage.getHeight()-150);
            final Animation popup = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curPos = (MainStage.getHeight()-150) * (1-frac);
                    container.setTranslateY(curPos);
                }
            };
            popup.play();
            message.hide();
            message.setScene(scene);
            message.show();
            MessageController controller = loader.getController();
            controller.setMainApp(this,type,content);
            return controller;
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            return null;
        }
    }
    public void closeMessage(){
        message.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
