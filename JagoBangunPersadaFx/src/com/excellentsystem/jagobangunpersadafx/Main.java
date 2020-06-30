/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx;

import com.excellentsystem.jagobangunpersadafx.DAO.SistemDAO;
import static com.excellentsystem.jagobangunpersadafx.Function.createSecretKey;
import com.excellentsystem.jagobangunpersadafx.Model.Sistem;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.AsetTetapController;
import com.excellentsystem.jagobangunpersadafx.View.DataCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.DataKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.DataPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.DataTukangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DataUserController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.KategoriHutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.KategoriPiutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.KategoriPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.KategoriTransaksiController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.NewTipeKeuanganController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.SplashScreenController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.UbahPasswordController;
import com.excellentsystem.jagobangunpersadafx.View.HutangController;
import com.excellentsystem.jagobangunpersadafx.View.KeuanganController;
import com.excellentsystem.jagobangunpersadafx.View.LoginController;
import com.excellentsystem.jagobangunpersadafx.View.MainController;
import com.excellentsystem.jagobangunpersadafx.View.ModalController;
import com.excellentsystem.jagobangunpersadafx.View.PelunasanAngsuranPembayaranController;
import com.excellentsystem.jagobangunpersadafx.View.PelunasanDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.PembangunanController;
import com.excellentsystem.jagobangunpersadafx.View.PembelianTanahController;
import com.excellentsystem.jagobangunpersadafx.View.PencairanKPRController;
import com.excellentsystem.jagobangunpersadafx.View.PiutangController;
import com.excellentsystem.jagobangunpersadafx.View.RealisasiAnggaranProyekController;
import com.excellentsystem.jagobangunpersadafx.View.RencanaAnggaranProyekController;
import com.excellentsystem.jagobangunpersadafx.View.Report.LaporanNeracaController;
import com.excellentsystem.jagobangunpersadafx.View.Report.LaporanPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Report.LaporanUntungRugiController;
import com.excellentsystem.jagobangunpersadafx.View.Report.LaporanUntungRugiPeriodeController;
import com.excellentsystem.jagobangunpersadafx.View.Report.LaporanUntungRugiPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.TerimaAngsuranPembayaranController;
import com.excellentsystem.jagobangunpersadafx.View.TerimaDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.TerimaTandaJadiController;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    
    public static DecimalFormat qty = new DecimalFormat("###,##0.##");
//    public static DecimalFormat rp = new DecimalFormat("###,##0;(###,##0)");
    public static DecimalFormat rp = new DecimalFormat("###,##0");
    public static DateFormat tglBarang = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat tglSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat tgl = new SimpleDateFormat("dd MMM yyyy");
    public static DateFormat tglLengkap = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
    
    public Stage login;
    public Stage MainStage;
    public Stage loading;
    public Stage popup;
    public Stage message;
    
    public BorderPane mainLayout;
    public Dimension screenSize;
    private MainController mainController;
    
    public static Sistem sistem = null;
    private double x = 0;
    private double y = 0;
    public final String version = "1.1.1";
    public static SecretKeySpec key;
    @Override
    public void start(Stage stage) {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        MainStage = stage;
        MainStage.setTitle("Jago Bangun Persada");
        MainStage.setMaximized(true);
        MainStage.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png"),90,90,true,true));
        ProgressBar progress = new ProgressBar();
        Label updateLabel = new Label();
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                updateMessage("connecting to server...");
                updateProgress(10, 100);
                try (Connection con = Koneksi.getConnection()) {
                    updateProgress(20, 100);
                    Thread.sleep(500);
                    updateProgress(30, 100);
                    Thread.sleep(500);
                    updateProgress(40, 100);
                    Thread.sleep(500);
                    updateMessage("checking for updates...");
                    String password = "password";
                    byte[] salt = "12345678".getBytes();
                    key = createSecretKey(password.toCharArray(), salt, 40000, 128);
                    sistem = SistemDAO.get(con);
                    if(!version.equals(sistem.getVersion())){
                        updateMessage("updating software...");
                        updateProgress(50, 100);
                        String status = Function.downloadUpdateGoogleStorage("Jago Bangun Persada.exe");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information");
                        alert.setContentText(status);
                        alert.showAndWait();
                        System.exit(0);
                    }
                    Service.setPenyusutanAset(con);
                    updateProgress(70, 100);
                    updateMessage("initializing system...");
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Application Failed - \n" +task.getValue());
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
            loader.setLocation(Main.class.getResource("View/Main.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            final Animation animationshow = new Transition() {
                { setCycleDuration(Duration.millis(500)); }
                @Override
                protected void interpolate(double frac) {
                    MainStage.setOpacity(1-frac);
                }
            };
            animationshow.onFinishedProperty().set((EventHandler<ActionEvent>) (ActionEvent actionEvent) -> {
                final Animation animation = new Transition() {
                    { setCycleDuration(Duration.millis(500)); }
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
    public DataKaryawanController showDataKaryawan(){
        FXMLLoader loader = changeStage("View/DataKaryawan.fxml");
        DataKaryawanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Karyawan");
        return controller;
    }
    public DataTukangController showDataTukang(){
        FXMLLoader loader = changeStage("View/DataTukang.fxml");
        DataTukangController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Tukang");
        return controller;
    }
    public DataPropertyController showDataProperty(){
        FXMLLoader loader = changeStage("View/DataProperty.fxml");
        DataPropertyController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Data Property");
        return controller;
    }
    public PembelianTanahController showPembelianTanah(){
        FXMLLoader loader = changeStage("View/PembelianTanah.fxml");
        PembelianTanahController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembelian Tanah");
        return controller;
    }
    public PembangunanController showPembangunan(){
        FXMLLoader loader = changeStage("View/Pembangunan.fxml");
        PembangunanController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pembangunan");
        return controller;
    }
    public RencanaAnggaranProyekController showRencanaAnggaranProyek(){
        FXMLLoader loader = changeStage("View/RencanaAnggaranProyek.fxml");
        RencanaAnggaranProyekController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Rencana Anggaran Proyek");
        return controller;
    }
    public RealisasiAnggaranProyekController showRealisasiAnggaranProyek(){
        FXMLLoader loader = changeStage("View/RealisasiAnggaranProyek.fxml");
        RealisasiAnggaranProyekController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Realisasi Anggaran Proyek");
        return controller;
    }
    public TerimaTandaJadiController showTerimaTandaJadi(){
        FXMLLoader loader = changeStage("View/TerimaTandaJadi.fxml");
        TerimaTandaJadiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Terima Tanda Jadi");
        return controller;
    }
    public TerimaDownPaymentController showTerimaDownPayment(){
        FXMLLoader loader = changeStage("View/TerimaDownPayment.fxml");
        TerimaDownPaymentController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Terima Down Payment");
        return controller;
    }
    public PelunasanDownPaymentController showPelunasanDownPayment(){
        FXMLLoader loader = changeStage("View/PelunasanDownPayment.fxml");
        PelunasanDownPaymentController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pelunasan Down Payment");
        return controller;
    }
    public PencairanKPRController showPencairanKPR(){
        FXMLLoader loader = changeStage("View/PencairanKPR.fxml");
        PencairanKPRController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Terima Pencairan KPR");
        return controller;
    }
    public TerimaAngsuranPembayaranController showTerimaAngsuranPembayaran(){
        FXMLLoader loader = changeStage("View/TerimaAngsuranPembayaran.fxml");
        TerimaAngsuranPembayaranController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Terima Angsuran Pembayaran");
        return controller;
    }
    public PelunasanAngsuranPembayaranController showPelunasanPembayaran(){
        FXMLLoader loader = changeStage("View/PelunasanAngsuranPembayaran.fxml");
        PelunasanAngsuranPembayaranController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Pelunasan Angsuran");
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
    public LaporanUntungRugiController showLaporanUntungRugi(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugi.fxml");
        LaporanUntungRugiController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung-Rugi");
        return controller;
    }
    public LaporanUntungRugiPeriodeController showLaporanUntungRugiPeriode(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugiPeriode.fxml");
        LaporanUntungRugiPeriodeController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Untung-Rugi Periode");
        return controller;
    }
    public LaporanUntungRugiPropertyController showLaporanUntungRugiProperty(){
        FXMLLoader loader = changeStage("View/Report/LaporanUntungRugiProperty.fxml");
        LaporanUntungRugiPropertyController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Property");
        return controller;
    }
    public LaporanPropertyController showLaporanProperty(){
        FXMLLoader loader = changeStage("View/Report/LaporanProperty.fxml");
        LaporanPropertyController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Property");
        return controller;
    }
    public LaporanNeracaController showLaporanNeraca(){
        FXMLLoader loader = changeStage("View/Report/LaporanNeraca.fxml");
        LaporanNeracaController controller = loader.getController();
        controller.setMainApp(this);
        setTitle("Laporan Neraca");
        return controller;
    }
    public UbahPasswordController showUbahPassword(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/UbahPassword.fxml");
        UbahPasswordController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public DataUserController showDataUser(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/DataUser.fxml");
        DataUserController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriHutangController showKategoriHutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/KategoriHutang.fxml");
        KategoriHutangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriPiutangController showKategoriPiutang(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/KategoriPiutang.fxml");
        KategoriPiutangController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriPropertyController showKategoriProperty(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/KategoriProperty.fxml");
        KategoriPropertyController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public NewTipeKeuanganController showTipeKeuangan(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/NewTipeKeuangan.fxml");
        NewTipeKeuanganController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public KategoriTransaksiController showKategoriTransaksi(){
        Stage stage = new Stage();
        FXMLLoader loader = showDialog(MainStage, stage, "View/Dialog/KategoriTransaksi.fxml");
        KategoriTransaksiController controller = loader.getController();
        controller.setMainApp(this, MainStage, stage);
        return controller;
    }
    public void setTitle(String title){
        mainController.setTitle(title);
        if (mainController.menuPane.isVisible()) 
            mainController.showHideMenu();
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
    private Stage splash;
    public void showSplashScreen(ProgressBar progressBar, Label updateLabel){
        try{
            if(splash!=null)
                splash.close();
            splash = new Stage();
            splash.getIcons().add(new Image(Main.class.getResourceAsStream("Resource/icon.png"),90,90,true,true));
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
    public FXMLLoader showPopup(double x1,double y1,String URL) {
        try{
            if(popup!=null)
                popup.close();
            popup = new Stage();
            popup.initModality(Modality.NONE);
            popup.initOwner(MainStage);
            popup.initStyle(StageStyle.TRANSPARENT);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(URL));
            AnchorPane container = (AnchorPane) loader.load();
            
            Scene scene = new Scene(container);
            scene.setFill(Color.TRANSPARENT);
            scene.setOnMousePressed((MouseEvent mouseEvent) -> {
                x = popup.getX() - mouseEvent.getScreenX();
                y = popup.getY() - mouseEvent.getScreenY();
            });
            scene.setOnMouseDragged((MouseEvent mouseEvent) -> {
                popup.setX(mouseEvent.getScreenX() + x);
                popup.setY(mouseEvent.getScreenY() + y);
            });
            
            popup.addEventHandler(MouseEvent.MOUSE_EXITED_TARGET, (MouseEvent e) -> {
                popup.close();
            });
            popup.hide();
            popup.setScene(scene);
            popup.show();
            popup.setX(x1);
            popup.setY(y1);
            
            return loader;
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(MainStage);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" + e);
            alert.showAndWait();
            return null;
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
