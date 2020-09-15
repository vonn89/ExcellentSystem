/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat;

import com.excellentsystem.TokoEmasJagoPusat.DAO.OtoritasDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.UserDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.createSecretKey;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.Sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.User;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanBatalBarcodeController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanNeracaCabangController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanOmzetController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanPembelianController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanPerformaBarcodeController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanTransaksiCabangController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanUntungRugiCabangController;
import com.excellentsystem.TokoEmasJagoPusat.Report.LaporanUntungRugiController;
import com.excellentsystem.TokoEmasJagoPusat.View.BarcodeBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DashboardController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataBarangBarcodeController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataHancurBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataKategoriBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataLeburRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataPegawaiController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataPembelianSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataPenjualanCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataPenjualanCiokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataPindahBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataReturPembelianSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataRevisiBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataSPBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataTambahUangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataTerimaBalenanCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataTerimaSetoranCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.DataUserController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DataCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DataSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PengaturanUmumController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.TutupTokoController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.UbahPasswordController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.WarningController;
import com.excellentsystem.TokoEmasJagoPusat.View.LoginController;
import com.excellentsystem.TokoEmasJagoPusat.View.MainController;
import com.excellentsystem.TokoEmasJagoPusat.View.PenjualanFiktifCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokBalenanCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokBarangBarcodeCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokBarangPusatController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokRosokCiokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.StokSPCabangController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
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
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
 * @author excellent
 */
public class Main extends Application{
    
    public Stage MainStage;
    public Stage message;
    public Stage loading;
    
    public Dimension screenSize;
    public BorderPane mainLayout;
    public MainController mainController;
    
    private double x = 0;
    private double y = 0;
    
    public static DecimalFormat gr = new DecimalFormat("###,##0.##");
    public static DecimalFormat rp = new DecimalFormat("###,##0");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSystem = new SimpleDateFormat("yyMMdd");
    public static DateFormat tglNormal = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    
    public static Sistem sistem;
    public static User user;
    public static List<User> allUser;
    public static String ipServer;
    
    public static long timeout = 0;
    public static final String version = "2.0.10";
    public static SecretKeySpec key;
    public static final String printerBarcode = "SATO CG408";
    private void getSistem()throws Exception{
        try(Connection conPusat = KoneksiPusat.getConnection()){
            sistem = SistemDAO.get(conPusat);
            allUser = UserDAO.getAll(conPusat);
            List<Otoritas> listOtoritas = OtoritasDAO.getAll(conPusat);
            for(User u : allUser){
                List<Otoritas> otoritas = new ArrayList<>();
                for(Otoritas o : listOtoritas){
                    if(o.getKodeUser().equals(u.getKodeUser()))
                        otoritas.add(o);
                }
                u.setOtoritas(otoritas);
            }
        }
    }
    
    @Override
    public void start(Stage stage) {
        try{
            String password = "password";
            byte[] salt = "12345678".getBytes();
            key = createSecretKey(password.toCharArray(), salt, 40000, 128);
            
            ipServer = new BufferedReader(new FileReader("koneksipusat")).readLine();
            getSistem();
            MainStage = stage;
            MainStage.setTitle("Toko Emas Jago Pusat");
            MainStage.setMaximized(true);
            MainStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            showLoginScene();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            System.exit(0);
        }
        
    }
    public void showLoginScene() {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Login.fxml"));
            AnchorPane container = (AnchorPane) loader.load();
            
            Scene scene = new Scene(container);
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
                MainStage.show();
            });
            animationshow.play();
            LoginController controller = loader.getController();
            controller.setMainApp(this);
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            e.printStackTrace();
        }
    }
    public void showMainScene(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Main.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            
            MainStage.hide();
            MainStage.setScene(scene);
            MainStage.show();
            
            mainController = loader.getController();
            mainController.setMainApp(this);
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
    public DataUserController showDataUser(){
        FXMLLoader loader = changeStage("View/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data User");
        return controller;
    }
    public DataPegawaiController showDataPegawai(){
        FXMLLoader loader = changeStage("View/DataPegawai.fxml");
        DataPegawaiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Pegawai");
        return controller;
    }
    public DataKategoriBarangController showDataKategoriBarang(){
        FXMLLoader loader = changeStage("View/DataKategoriBarang.fxml");
        DataKategoriBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Kategori Barang");
        return controller;
    }
    public PenjualanFiktifCabangController showPenjualanFiktifCabang(){
        FXMLLoader loader = changeStage("View/PenjualanFiktifCabang.fxml");
        PenjualanFiktifCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Penjualan Fiktif Cabang");
        return controller;
    }
    public DataBarangBarcodeController showDataBarangBarcode(){
        FXMLLoader loader = changeStage("View/DataBarangBarcode.fxml");
        DataBarangBarcodeController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Barang Barcode");
        return controller;
    }
    public StokBarangCabangController showStokBarangCabang(){
        FXMLLoader loader = changeStage("View/StokBarangCabang.fxml");
        StokBarangCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Barang Cabang");
        return controller;
    }
    public StokBarangBarcodeCabangController showStokBarangBarcodeCabang(){
        FXMLLoader loader = changeStage("View/StokBarangBarcodeCabang.fxml");
        StokBarangBarcodeCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Barcode Cabang");
        return controller;
    }
    public StokRosokCiokCabangController showStokRosokCabang(){
        FXMLLoader loader = changeStage("View/StokRosokCiokCabang.fxml");
        StokRosokCiokCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Rosok & Ciok Cabang");
        return controller;
    }
    public BarcodeBarangController showBarcodeBarang(){
        FXMLLoader loader = changeStage("View/BarcodeBarang.fxml");
        BarcodeBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Barcode Barang");
        return controller;
    }
    public DataPindahBarangController showDataPindahBarang(){
        FXMLLoader loader = changeStage("View/DataPindahBarang.fxml");
        DataPindahBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Pindah Barang");
        return controller;
    }
    public StokBalenanCabangController showStokBalenanCabang(){
        FXMLLoader loader = changeStage("View/StokBalenanCabang.fxml");
        StokBalenanCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Balenan Cabang");
        return controller;
    }
    public StokSPCabangController showStokSPCabang(){
        FXMLLoader loader = changeStage("View/StokSPCabang.fxml");
        StokSPCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok SP Cabang");
        return controller;
    }
    public DataTerimaBalenanCabangController showDataTerimaBalenanCabang(){
        FXMLLoader loader = changeStage("View/DataTerimaBalenanCabang.fxml");
        DataTerimaBalenanCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Terima Balenan Cabang");
        return controller;
    }
    public DataSPBarangCabangController showDataSpBarangCabang(){
        FXMLLoader loader = changeStage("View/DataSPBarangCabang.fxml");
        DataSPBarangCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data SP Barang Cabang");
        return controller;
    }
    public DataRevisiBarangCabangController showDataRevisiBarangCabang(){
        FXMLLoader loader = changeStage("View/DataRevisiBarangCabang.fxml");
        DataRevisiBarangCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Revisi Barang Cabang");
        return controller;
    }
    public DataRosokCabangController showDataRosokCabang(){
        FXMLLoader loader = changeStage("View/DataRosokCabang.fxml");
        DataRosokCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Rosok Barang Cabang");
        return controller;
    }
    public DataLeburRosokCabangController showDataLeburRosokCabang(){
        FXMLLoader loader = changeStage("View/DataLeburRosokCabang.fxml");
        DataLeburRosokCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Lebur Rosok Cabang");
        return controller;
    }
    public DataPenjualanCiokCabangController showDataPenjualanCiokCabang(){
        FXMLLoader loader = changeStage("View/DataPenjualanCiokCabang.fxml");
        DataPenjualanCiokCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Penjualan Ciok Cabang");
        return controller;
    }
    public DataHancurBarangController showDataHancurBarang(){
        FXMLLoader loader = changeStage("View/DataHancurBarang.fxml");
        DataHancurBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Hancur Barang Cabang");
        return controller;
    }
    public DataTerimaSetoranCabangController showDataTerimaSetoranCabang(){
        FXMLLoader loader = changeStage("View/DataTerimaSetoranCabang.fxml");
        DataTerimaSetoranCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Terima Setoran Cabang");
        return controller;
    }
    public DataTambahUangCabangController showDataTambahUangCabang(){
        FXMLLoader loader = changeStage("View/DataTambahUangCabang.fxml");
        DataTambahUangCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Tambah Uang Cabang");
        return controller;
    }
    public DataPenjualanCabangController showDataPenjualanCabang(){
        FXMLLoader loader = changeStage("View/DataPenjualanCabang.fxml");
        DataPenjualanCabangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Penjualan Cabang");
        return controller;
    }
    public DataPembelianSupplierController showDataPembelianSupplier(){
        FXMLLoader loader = changeStage("View/DataPembelianSupplier.fxml");
        DataPembelianSupplierController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Pembelian Supplier");
        return controller;
    }
    public DataReturPembelianSupplierController showDataReturPembelianSupplier(){
        FXMLLoader loader = changeStage("View/DataReturPembelianSupplier.fxml");
        DataReturPembelianSupplierController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Retur Pembelian Supplier");
        return controller;
    }
    public StokBarangPusatController showStokBarangPusat(){
        FXMLLoader loader = changeStage("View/StokBarangPusat.fxml");
        StokBarangPusatController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Stok Barang Pusat");
        return controller;
    }
    public void showLaporanOmzet(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanOmzet.fxml", "Laporan Omzet");
        LaporanOmzetController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPembelianUmumCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPembelian.fxml", "Laporan Pembelian Umum Cabang");
        LaporanPembelianController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanBatalBarcode(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanBatalBarcode.fxml", "Laporan Batal Barcode");
        LaporanBatalBarcodeController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanPerformaBarcode(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanPerformaBarcode.fxml", "Laporan Performa Barcode");
        LaporanPerformaBarcodeController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanNeracaCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanNeracaCabang.fxml", "Laporan Neraca Cabang");
        LaporanNeracaCabangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanUntungRugiCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanUntungRugiCabang.fxml", "Laporan Untung Rugi Cabang");
        LaporanUntungRugiCabangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public void showLaporanUntungRugiCabang2(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanUntungRugi.fxml", "Laporan Untung Rugi Cabang");
        LaporanUntungRugiController controller = loader.getController();
//        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
//            if (event.getCode() == KeyCode.LEFT) 
//                controller.prevPage();
//            if (event.getCode() == KeyCode.RIGHT) 
//                controller.nextPage();
//        });
    }
    public void showLaporanTransaksiCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showLaporan(stage, "Report/LaporanTransaksiCabang.fxml", "Laporan Transaksi Cabang");
        LaporanTransaksiCabangController controller = loader.getController();
        stage.addEventFilter(KeyEvent.KEY_RELEASED, event->{
            if (event.getCode() == KeyCode.LEFT) 
                controller.prevPage();
            if (event.getCode() == KeyCode.RIGHT) 
                controller.nextPage();
        });
    }
    public DataCabangController showDataCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/DataCabang.fxml");
        DataCabangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public DataSupplierController showDataSupplier(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/DataSupplier.fxml");
        DataSupplierController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public PengaturanUmumController showPengaturanUmum(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/PengaturanUmum.fxml");
        PengaturanUmumController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public TutupTokoController showTutupToko(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/TutupToko.fxml");
        TutupTokoController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public UbahPasswordController showUbahPassword(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/UbahPassword.fxml");
        UbahPasswordController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    private void setTitle(String title){
        mainController.setTitle(title);
        if (mainController.menuPane.getPrefWidth()!=0) 
            mainController.showHideMenu();
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
    private FXMLLoader showLaporan(Stage stage, String URL, String title){
        try{
            stage.setMaximized(true);
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            BorderPane page = (BorderPane) loader.load();
            
            Scene scene = new Scene(page);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
            
            return loader;
        }catch(Exception e){
            showMessage(Modality.NONE, "Error", e.toString());
            return null;
        }
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
        }
    }
    public void closeLoading(){
        loading.close();
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
    public void closeDialog(Stage owner,Stage dialog){
        dialog.close();
        owner.getScene().getRoot().setEffect(new ColorAdjust(0,0,0,0));
    }
    public Stage warning;
    public void showWarning(String title, String text){
        try{
            if(warning!=null)
                warning.close();
            warning = new Stage();
            warning.initModality(Modality.APPLICATION_MODAL);
            warning.initOwner(MainStage);
            warning.initStyle(StageStyle.TRANSPARENT);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/Dialog/Warning.fxml"));
            AnchorPane container = (AnchorPane) loader.load();

            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            
            warning.hide();
            warning.setScene(scene);
            warning.show();
            //set dialog on center parent
            warning.setX((screenSize.getWidth() - warning.getWidth()) / 2);
            warning.setY((screenSize.getHeight() - warning.getHeight()) / 2);
            warning.setOnCloseRequest((event) -> {
                closeWarning();
            });
            WarningController controller = loader.getController();
            controller.setMainApp(this, title, text);
        }catch(IOException e){
            showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void closeWarning(){
        warning.close();
        warning=null;
        MainStage.requestFocus();
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
            
            message.setX(screenSize.getWidth()-410);
            message.setY(screenSize.getHeight()-160);
            
            final Animation animation = new Transition() {
                { setCycleDuration(Duration.millis(250)); }
                @Override
                protected void interpolate(double frac) {
                    final double curPos = (screenSize.getHeight()-160) * (1-frac);
                    container.setTranslateY(curPos);
                }
            };
            animation.play();
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
