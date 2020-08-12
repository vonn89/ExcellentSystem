/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana;

import com.excellentsystem.sentralindahperdana.DAO.SistemDAO;
import com.excellentsystem.sentralindahperdana.Model.Sistem;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.AsetTetapController;
import com.excellentsystem.sentralindahperdana.View.BebanPenjualanController;
import com.excellentsystem.sentralindahperdana.View.DataBarangController;
import com.excellentsystem.sentralindahperdana.View.DataCustomerController;
import com.excellentsystem.sentralindahperdana.View.DataPegawaiController;
import com.excellentsystem.sentralindahperdana.View.DataPekerjaanController;
import com.excellentsystem.sentralindahperdana.View.DataSupplierController;
import com.excellentsystem.sentralindahperdana.View.DataUserController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriBarangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriHutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriKeuanganController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriPekerjaanController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriPiutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.KategoriTransaksiController;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.PengaturanUmumController;
import com.excellentsystem.sentralindahperdana.View.Dialog.UbahPasswordController;
import com.excellentsystem.sentralindahperdana.View.HutangController;
import com.excellentsystem.sentralindahperdana.View.KeuanganController;
import com.excellentsystem.sentralindahperdana.View.LoginController;
import com.excellentsystem.sentralindahperdana.View.MainController;
import com.excellentsystem.sentralindahperdana.View.ModalController;
import com.excellentsystem.sentralindahperdana.View.PembelianController;
import com.excellentsystem.sentralindahperdana.View.PemesananController;
import com.excellentsystem.sentralindahperdana.View.PengirimanBarangController;
import com.excellentsystem.sentralindahperdana.View.PenjualanController;
import com.excellentsystem.sentralindahperdana.View.PiutangController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangDibeliController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangDikirimController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangDireturBeliController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangDireturController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBarangTerjualController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanBebanPenjualanTerbayarController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanHutangController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanKeuanganController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanNeracaController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanPembelianController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanPenjualanController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanPiutangController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanProyekController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanReturPembelianController;
import com.excellentsystem.sentralindahperdana.View.Report.LaporanUntungRugiController;
import com.excellentsystem.sentralindahperdana.View.ReturController;
import com.excellentsystem.sentralindahperdana.View.ReturPembelianController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
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
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author excellent
 */
public class Main extends Application{
    
    public static DecimalFormat df = new DecimalFormat("###,##0.##");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tglNormal = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    
    public Stage MainStage;
    public Stage message;
    public Stage loading;
    
    public Dimension screenSize;
    public BorderPane mainLayout;
    public MainController mainController;
    
    private double x = 0;
    private double y = 0;
    public static Sistem sistem;
    public static long timeout = 1000;
    private final String version = "1.0";
    private String downloadUpdate(){
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;
        String status = "";
        try{
            client.connect("ftp.es-excellentsystem.com");
            boolean login = client.login("admin@es-excellentsystem.com", "yunaz051189");
            if (login) {
                client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE); 
                client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                String filename = "Sentral Indah Perdana.exe";
                fos = new FileOutputStream(filename);
                boolean file = client.retrieveFile("/" + filename, fos);
                if(file)
                    status = "Update Success - please restart application";
                else
                    status = "Update Failed - please try again";
                client.logout();
            }else{
                status = "Fail connect to server";
            }
        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
            System.exit(0);
        }finally{
            try{
                if(fos!= null) 
                    fos.close();
                client.disconnect();
            }catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Application error - \n" +e);
                alert.showAndWait();
                System.exit(0);
            }
        }
        return status;
    }
    @Override
    public void start(Stage stage) {
        try(Connection con = Koneksi.getConnection()){
            MainStage = stage;
            MainStage.setTitle("Sentral Indah Perdana");
            MainStage.setMaximized(true);
            MainStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png")));
            screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            sistem = SistemDAO.get(con);
            if(!version.equals(sistem.getVersion())){
                String status = downloadUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText(status);
                alert.showAndWait();
                System.exit(0);
            }
            Service.setPenyusutanAset(con);
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
            
            mainController = loader.getController();
            mainController.setMainApp(this);
            showPenjualan();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
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
    public DataBarangController showDataBarang(){
        FXMLLoader loader = changeStage("View/DataBarang.fxml");
        DataBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Barang");
        return controller;
    }
    public DataPekerjaanController showDataPekerjaan(){
        FXMLLoader loader = changeStage("View/DataPekerjaan.fxml");
        DataPekerjaanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Pekerjaan");
        return controller;
    }
    public DataUserController showDataUser(){
        FXMLLoader loader = changeStage("View/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data User");
        return controller;
    }
    public KeuanganController showKeuangan(){
        FXMLLoader loader = changeStage("View/Keuangan.fxml");
        KeuanganController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Keuangan");
        return controller;
    }
    public HutangController showHutang(){
        FXMLLoader loader = changeStage("View/Hutang.fxml");
        HutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Hutang");
        return controller;
    }
    public PiutangController showPiutang(){
        FXMLLoader loader = changeStage("View/Piutang.fxml");
        PiutangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Piutang");
        return controller;
    }
    public ModalController showModal(){
        FXMLLoader loader = changeStage("View/Modal.fxml");
        ModalController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Modal");
        return controller;
    }
    public AsetTetapController showAsetTetap(){
        FXMLLoader loader = changeStage("View/AsetTetap.fxml");
        AsetTetapController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Aset Tetap");
        return controller;
    }
    public PemesananController showPemesanan(){
        FXMLLoader loader = changeStage("View/Pemesanan.fxml");
        PemesananController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pemesanan");
        return controller;
    }
    public PenjualanController showPenjualan(){
        FXMLLoader loader = changeStage("View/Penjualan.fxml");
        PenjualanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Penjualan");
        return controller;
    }
    public PengirimanBarangController showPengirimanBarang(){
        FXMLLoader loader = changeStage("View/PengirimanBarang.fxml");
        PengirimanBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pengiriman Barang");
        return controller;
    }
    public ReturController showRetur(){
        FXMLLoader loader = changeStage("View/Retur.fxml");
        ReturController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Retur Barang");
        return controller;
    }
    public BebanPenjualanController showBebanPenjualan(){
        FXMLLoader loader = changeStage("View/BebanPenjualan.fxml");
        BebanPenjualanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Beban Penjualan");
        return controller;
    }
    public PembelianController showPembelian(){
        FXMLLoader loader = changeStage("View/Pembelian.fxml");
        PembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembelian");
        return controller;
    }
    public ReturPembelianController showReturPembelian(){
        FXMLLoader loader = changeStage("View/ReturPembelian.fxml");
        ReturPembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Retur Pembelian");
        return controller;
    }
    public LaporanBarangController showLaporanBarang(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarang.fxml");
        LaporanBarangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang");
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
        setTitle("Laporan Item Terjual");
        return controller;
    }
    public LaporanBarangDikirimController showLaporanBarangDikirim(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangDikirim.fxml");
        LaporanBarangDikirimController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Dikirim");
        return controller;
    }
    public LaporanBarangDireturController showLaporanBarangDiretur(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangDiretur.fxml");
        LaporanBarangDireturController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Diretur");
        return controller;
    }
    public LaporanBebanPenjualanTerbayarController showLaporanBebanPenjualanTerbayar(){
        FXMLLoader loader = changeStage("View/Report/LaporanBebanPenjualanTerbayar.fxml");
        LaporanBebanPenjualanTerbayarController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Beban Penjualan Terbayar");
        return controller;
    }
    public LaporanPembelianController showLaporanPembelian(){
        FXMLLoader loader = changeStage("View/Report/LaporanPembelian.fxml");
        LaporanPembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Pembelian");
        return controller;
    }
    public LaporanBarangDibeliController showLaporanBarangDibeli(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangDibeli.fxml");
        LaporanBarangDibeliController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Dibeli");
        return controller;
    }
    public LaporanReturPembelianController showLaporanReturPembelian(){
        FXMLLoader loader = changeStage("View/Report/LaporanReturPembelian.fxml");
        LaporanReturPembelianController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Retur Pembelian");
        return controller;
    }
    public LaporanBarangDireturBeliController showLaporanBarangDireturBeli(){
        FXMLLoader loader = changeStage("View/Report/LaporanBarangDireturBeli.fxml");
        LaporanBarangDireturBeliController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Barang Diretur Beli");
        return controller;
    }
    public LaporanProyekController showLaporanProyek(){
        FXMLLoader loader = changeStage("View/Report/LaporanProyek.fxml");
        LaporanProyekController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Proyek");
        return controller;
    }
    public LaporanUntungRugiController showLaporanUntungRugi(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugi.fxml");
        LaporanUntungRugiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung-Rugi");
        return controller;
    }
    public LaporanNeracaController showLaporanNeraca(){
        FXMLLoader loader = changeStage("View/Report/LaporanNeraca.fxml");
        LaporanNeracaController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Neraca");
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
    public PengaturanUmumController showPengaturanUmum(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/PengaturanUmum.fxml");
        PengaturanUmumController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriBarangController showKategoriBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriBarang.fxml");
        KategoriBarangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriPekerjaanController showKategoriPekerjaan(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage ,stage, "View/Dialog/KategoriPekerjaan.fxml");
        KategoriPekerjaanController controller = loader.getController();
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
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();
            ScrollPane scroll = (ScrollPane) mainLayout.getCenter();
            scroll.setContent(container);
            return loader;
        }catch(Exception e){
            e.printStackTrace();
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
