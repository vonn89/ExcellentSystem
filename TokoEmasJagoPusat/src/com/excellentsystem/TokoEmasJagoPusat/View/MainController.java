package com.excellentsystem.TokoEmasJagoPusat.View;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.excellentsystem.TokoEmasJagoPusat.DAO.AmbilBarangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanAntarCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PermintaanBarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PindahHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SetoranCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SistemDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TambahUangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.AmbilBarangHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.TransaksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Otoritas;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanAntarCabangHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.PermintaanBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.SetoranCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TambahUangCabang;
import com.excellentsystem.TokoEmasJagoPusat.PrintOut.PrintOut;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class MainController  {

    @FXML private StackPane permintaanBarangLoading;
    
    @FXML private StackPane stackPane;
    @FXML private TableView<TransaksiCabang> transaksiCabangTable;
    @FXML private TableColumn<TransaksiCabang, String> transaksiColumn;
    @FXML private TableColumn<TransaksiCabang, String> noTransaksiColumn;
    @FXML private TableColumn<TransaksiCabang, String> kodeCabangColumn;
    private ObservableList<TransaksiCabang> allTransaksiCabang = FXCollections.observableArrayList();
    
    
    @FXML public Label title;
    @FXML public Label tglSystemLabel;
    @FXML public Label hargaEmasLabel;
    
    @FXML public VBox menuPane;
    
    @FXML private VBox sementaraVbox;
    
    @FXML private VBox userVbox;
    @FXML private TitledPane loginButton;
    @FXML private MenuButton logoutButton;
    @FXML private MenuButton ubahPasswordButton;
    
    @FXML private TitledPane dashboardPane;
    
    @FXML private Label pusatLabel;
    @FXML private TitledPane penjualanCabangPane;
    @FXML private TitledPane pembelianSupplierPane;
    @FXML private TitledPane returPembelianSupplierPane;
    @FXML private TitledPane stokBarangPane;
    
    @FXML private Accordion accordion1;
    @FXML private TitledPane barangCabangPane;
    @FXML private VBox barangCabangVbox;
    @FXML private MenuButton menuDataBarangBarcode;
    @FXML private MenuButton menuStokBarcodeCabang;
    @FXML private MenuButton menuStokBarangCabang;
    @FXML private MenuButton menuBarcodeBarang;
    @FXML private MenuButton menuPindahBarang;
    @FXML private MenuButton menuRevisiBarangCabang;
    
    @FXML private TitledPane spCabangPane;
    @FXML private VBox spCabangVbox;
    @FXML private MenuButton menuStokBalenanCabang;
    @FXML private MenuButton menuStokSpCabang;
    @FXML private MenuButton menuTerimaBalenanCabang;
    @FXML private MenuButton menuSpBarangCabang;
    
    @FXML private TitledPane rosokDanCiokCabangPane;
    @FXML private VBox rosokDanCiokCabangVbox;
    @FXML private MenuButton menuStokRosokCiokCabang;
    @FXML private MenuButton menuRosokBarangCabang;
    @FXML private MenuButton menuHancurBarangCabang;
    @FXML private MenuButton menuLeburRosokCabang;
    @FXML private MenuButton menuPenjualanCiokCabang;
    
    @FXML private TitledPane keuanganCabangPane;
    @FXML private VBox keuanganCabangVbox;
    @FXML private MenuButton menuKeuanganCabang;
    @FXML private MenuButton menuTerimaSetoranCabang;
    @FXML private MenuButton menuTambahUangCabang;
    
    @FXML private TitledPane laporanPane;
    @FXML private VBox laporanVbox;
    @FXML private MenuButton menuLaporanBarang;
    @FXML private MenuButton menuLaporanSP;
    @FXML private MenuButton menuLaporanManagerial;
    
    @FXML private Accordion accordion2;
    @FXML private TitledPane administrasiToolPane;
    @FXML private VBox administrasiToolVbox;
    @FXML private MenuButton menuPenjualanFiktifCabang;
    @FXML private MenuButton menuKeuanganPoin;
    
    @FXML private TitledPane pengaturanPane;
    @FXML private VBox pengaturanVbox;
    @FXML private MenuButton menuPengaturanUmum;
    @FXML private MenuButton menuDataUser;
    @FXML private MenuButton menuDataPegawai;
    @FXML private MenuButton menuDataKategoriBarang;
    @FXML private MenuButton menuDataCabang;
    @FXML private MenuButton menuDataSupplier;
    @FXML private MenuButton menuTutupToko;
    
    @FXML private Button notifButton;
//    @FXML private ListView<Notification> listView;
//    private ObservableList<Notification> allNotif = FXCollections.observableArrayList();
    private Timeline timeline;
    private Main mainApp;
    public void initialize(){
        transaksiColumn.setCellValueFactory(cellData -> cellData.getValue().transaksiProperty());
        noTransaksiColumn.setCellValueFactory(cellData -> cellData.getValue().noTransaksiProperty());
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent) -> {
            setTransaksiCabangTable();
        });
        rowMenu.getItems().addAll(refresh);
        transaksiCabangTable.setContextMenu(rowMenu);
        transaksiCabangTable.setRowFactory(t -> {
            TableRow<TransaksiCabang> row = new TableRow<TransaksiCabang>(){
                @Override
                public void updateItem(TransaksiCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent) -> {
                            setTransaksiCabangTable();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        
        ContextMenu permintaanBarangRowMenu = new ContextMenu();
        MenuItem refreshPermintaanBarang = new MenuItem("Refresh");
        refreshPermintaanBarang.setOnAction((ActionEvent) -> {
            setPermintaanBarang();
        });
        permintaanBarangRowMenu.getItems().add(refreshPermintaanBarang);
        vbox.setOnContextMenuRequested((e) -> {
            permintaanBarangRowMenu.show(vbox, e.getScreenX(), e.getScreenY());
        });
        warningLabel.setOnContextMenuRequested((e) -> {
            permintaanBarangRowMenu.show(vbox, e.getScreenX(), e.getScreenY());
        });
        
    }
    public void setMainApp(Main mainApp) {
        try{
            this.mainApp = mainApp;
            menuPane.setPrefWidth(0);
            stackPane.setPrefWidth(0);
            for(Node n : sementaraVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : barangCabangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : spCabangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : rosokDanCiokCabangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : keuanganCabangVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : laporanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : administrasiToolVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : pengaturanVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            for(Node n : userVbox.getChildren()){
                n.managedProperty().bind(n.visibleProperty());
            }
            title.setText("Toko Emas Jago");
            checkTglSystem();
            setUser();
            
            timeline = new Timeline(new KeyFrame(Duration.seconds(10), (ActionEvent actionEvent) -> {
                if(mainApp.MainStage.isFocused())
                    checkTglSystem();
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
            
            transaksiCabangTable.setItems(allTransaksiCabang);
            setTransaksiCabangTable();
            setPermintaanBarang();
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    public void setUser() {
        dashboardPane.setVisible(false);
        penjualanCabangPane.setVisible(false);
        pembelianSupplierPane.setVisible(false);
        returPembelianSupplierPane.setVisible(false);
        stokBarangPane.setVisible(false);
        
        menuDataBarangBarcode.setVisible(false);
        menuStokBarcodeCabang.setVisible(false);
        menuStokBarangCabang.setVisible(false);
        menuBarcodeBarang.setVisible(false);
        menuPindahBarang.setVisible(false);
        menuRevisiBarangCabang.setVisible(false);
        
        menuStokBalenanCabang.setVisible(false);
        menuStokSpCabang.setVisible(false);
        menuTerimaBalenanCabang.setVisible(false);
        menuSpBarangCabang.setVisible(false);

        menuStokRosokCiokCabang.setVisible(false);
        menuRosokBarangCabang.setVisible(false);
        menuHancurBarangCabang.setVisible(false);
        menuLeburRosokCabang.setVisible(false);
        menuPenjualanCiokCabang.setVisible(false);

        menuKeuanganCabang.setVisible(false);
        menuTerimaSetoranCabang.setVisible(false);
        menuTambahUangCabang.setVisible(false);

        menuLaporanBarang.setVisible(false);
        menuLaporanSP.setVisible(false);
        menuLaporanManagerial.setVisible(false);

        menuPenjualanFiktifCabang.setVisible(false);
        menuKeuanganPoin.setVisible(false);

        menuPengaturanUmum.setVisible(false);
        menuDataUser.setVisible(false);
        menuDataPegawai.setVisible(false);
        menuDataKategoriBarang.setVisible(false);
        menuDataCabang.setVisible(false);
        menuDataSupplier.setVisible(false);
        menuTutupToko.setVisible(false);
        
        if(user!=null){
            logoutButton.setVisible(true);
            ubahPasswordButton.setVisible(true);
            loginButton.setText(Main.user.getKodeUser());
            for(Otoritas o : Main.user.getOtoritas()){
                if(o.getJenis().equals("Dashboard")){
                    dashboardPane.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan Cabang")){
                    penjualanCabangPane.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pembelian Supplier")){
                    pembelianSupplierPane.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Retur Pembelian Supplier")){
                    returPembelianSupplierPane.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok Barang Pusat")){
                    stokBarangPane.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Data Barang Barcode")){
                    menuDataBarangBarcode.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok Barcode Cabang")){
                    menuStokBarcodeCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok Barang Cabang")){
                    menuStokBarangCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Barcode Barang")){
                    menuBarcodeBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Pindah Barang")){
                    menuPindahBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Revisi Barang Cabang")){
                    menuRevisiBarangCabang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Stok Balenan Cabang")){
                    menuStokBalenanCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Stok SP Cabang")){
                    menuStokSpCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Balenan Cabang")){
                    menuTerimaBalenanCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("SP Barang Cabang")){
                    menuSpBarangCabang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Stok Rosok & Ciok Cabang")){
                    menuStokRosokCiokCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Rosok Barang Cabang")){
                    menuRosokBarangCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Hancur Barang Cabang")){
                    menuHancurBarangCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Lebur Rosok Cabang")){
                    menuLeburRosokCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Penjualan Ciok Cabang")){
                    menuPenjualanCiokCabang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Keuangan Cabang")){
                    menuKeuanganCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Terima Setoran Cabang")){
                    menuTerimaSetoranCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Tambah Uang Cabang")){
                    menuTambahUangCabang.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Laporan Barang")){
                    menuLaporanBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan SP")){
                    menuLaporanSP.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Laporan Managerial")){
                    menuLaporanManagerial.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Penjualan Fiktif Cabang")){
                    menuPenjualanFiktifCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Keuangan Poin")){
                    menuKeuanganPoin.setVisible(o.isStatus());
                    
                }else if(o.getJenis().equals("Harga Emas")){
                    menuPengaturanUmum.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data User")){
                    menuDataUser.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Pegawai")){
                    menuDataPegawai.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Kategori Barang")){
                    menuDataKategoriBarang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Cabang")){
                    menuDataCabang.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Data Supplier")){
                    menuDataSupplier.setVisible(o.isStatus());
                }else if(o.getJenis().equals("Tutup Toko")){
                    menuTutupToko.setVisible(o.isStatus());
                }
            }
            if(penjualanCabangPane.isVisible()==false &&
                    barangCabangPane.isVisible()==false &&
                    pembelianSupplierPane.isVisible()==false  &&
                    returPembelianSupplierPane.isVisible()==false ){
                pusatLabel.setVisible(false);
            }
            
            if(menuDataBarangBarcode.isVisible()==false &&
                    menuStokBarcodeCabang.isVisible()==false &&
                    menuStokBarangCabang.isVisible()==false &&
                    menuBarcodeBarang.isVisible()==false &&
                    menuRevisiBarangCabang.isVisible()==false &&
                    menuPindahBarang.isVisible()==false ){
                accordion1.getPanes().remove(barangCabangPane);
            }
            if(menuStokBalenanCabang.isVisible()==false &&
                    menuStokSpCabang.isVisible()==false &&
                    menuTerimaBalenanCabang.isVisible()==false &&
                    menuSpBarangCabang.isVisible()==false ){
                accordion1.getPanes().remove(spCabangPane);
            }
            if(menuStokRosokCiokCabang.isVisible()==false &&
                    menuRosokBarangCabang.isVisible()==false &&
                    menuHancurBarangCabang.isVisible()==false &&
                    menuPenjualanCiokCabang.isVisible()==false &&
                    menuLeburRosokCabang.isVisible()==false ){
                accordion1.getPanes().remove(rosokDanCiokCabangPane);
            }
            if(menuKeuanganCabang.isVisible()==false &&
                    menuTerimaSetoranCabang.isVisible()==false &&
                    menuTambahUangCabang.isVisible()==false){
                accordion1.getPanes().remove(keuanganCabangPane);
            }
            if(menuLaporanBarang.isVisible()==false &&
                    menuLaporanSP.isVisible()==false &&
                    menuLaporanManagerial.isVisible()==false){
                accordion1.getPanes().remove(laporanPane);
            }
            if(menuPenjualanFiktifCabang.isVisible()==false &&
                    menuKeuanganPoin.isVisible()==false){
                accordion2.getPanes().remove(administrasiToolPane);
            }
            if(menuPengaturanUmum.isVisible()==false &&
                    menuDataUser.isVisible()==false &&
                    menuDataPegawai.isVisible()==false &&
                    menuDataKategoriBarang.isVisible()==false &&
                    menuDataCabang.isVisible()==false &&
                    menuDataSupplier.isVisible()==false &&
                    menuTutupToko.isVisible()==false){
                accordion2.getPanes().remove(pengaturanPane);
            }
            if(dashboardPane.isVisible())
                showDashboard();
        }
        
    }
    @FXML
    private void startOrPauseCheckTanggal(){
        if(timeline.getStatus().equals(Animation.Status.RUNNING)){
            timeline.pause();
            tglSystemLabel.setTextFill(Paint.valueOf("RED"));
        }else if(timeline.getStatus().equals(Animation.Status.PAUSED)){
            timeline.play();
            tglSystemLabel.setTextFill(Paint.valueOf("WHITE"));
        }
    }
    public void checkTglSystem(){
        try(Connection con = KoneksiPusat.getConnection()){
            sistem = SistemDAO.get(con);
            tglSystemLabel.setText(tglNormal.format(tglBarang.parse(sistem.getTglSystem())));
            hargaEmasLabel.setText(rp.format(sistem.getHargaEmas()));
            if(!sistem.getTglSystem().equals(tglBarang.format(new Date()))){
                if(mainApp.warning==null)
                    mainApp.showWarning("Warning!!", "Tanggal sistem dan tanggal komputer berbeda");
            }
        }catch(Exception e){
            mainApp.showWarning("Error", e.toString());
        }
    }
    @FXML
    public void showHideMenu(){
        final Animation hideSidebar = new Transition() { 
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
                final double curWidth = 240 * (1.0 - frac) ;
                menuPane.setPrefWidth(curWidth);
                barangCabangPane.setExpanded(false);
                spCabangPane.setExpanded(false);
                rosokDanCiokCabangPane.setExpanded(false);
                keuanganCabangPane.setExpanded(false);
                laporanPane.setExpanded(false);
                pengaturanPane.setExpanded(false);
                administrasiToolPane.setExpanded(false);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 240 * frac ;
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
                final double curWidth = 400 * (1.0 - frac) ;
                stackPane.setPrefWidth(curWidth);
            }
        };
        final Animation showSidebar = new Transition() {
            { setCycleDuration(Duration.millis(250)); }
            @Override
            protected void interpolate(double frac) {
              final double curWidth = 400 * frac ;
              stackPane.setPrefWidth(curWidth);
            }
        };
        if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED) {
            if (stackPane.getPrefWidth()!=0) 
                hideSidebar.play();
            else 
                showSidebar.play();
        }
    }
    public void setTransaksiCabangTable(){
        try(Connection con = KoneksiPusat.getConnection()){
            allTransaksiCabang.clear();
            String tglMulai = "2000-01-01";
            String tglAkhir = sistem.getTglSystem();
            List<SetoranCabang> listSetoranCabang = SetoranCabangDAO.getAllByDateAndCabangAndTipeKasirAndStatusTerimaAndStatusBatal(
                    con, tglMulai, tglAkhir, "%", "%", "false", "false");
            List<TambahUangCabang> listTambahUangCabang = TambahUangCabangDAO.getAllByDateAndCabangAndTipeKasirAndStatusTerimaAndStatusBatal(
                    con, tglMulai, tglAkhir, "%", "%", "false", "false");
            List<AmbilBarangHead> listAmbilBarangCabang = AmbilBarangHeadDAO.getAllByDateAndCabangAndStatusTerimaAndStatusBatal(
                    con, tglMulai, tglAkhir, "%", "false", "false");
            List<PindahHead> listPindahCabang = PindahHeadDAO.getAllByDateAndCabangAndStatusTerimaAndStatusBatal(
                    con, tglMulai, tglAkhir, "%", "false", "false");
            List<PenjualanAntarCabangHead> listPenjualanAntarCabang = PenjualanAntarCabangHeadDAO.getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                    con, tglMulai, tglAkhir, "%", "%", "false", "false");
            for(SetoranCabang s : listSetoranCabang){
                TransaksiCabang t = new TransaksiCabang();
                t.setTransaksi("Setoran Cabang");
                t.setNoTransaksi(s.getNoSetor());
                t.setKodeCabang(s.getKodeCabang());
                allTransaksiCabang.add(t);
            }
            for(TambahUangCabang s : listTambahUangCabang){
                TransaksiCabang t = new TransaksiCabang();
                t.setTransaksi("Tambah Uang Cabang");
                t.setNoTransaksi(s.getNoTambahUang());
                t.setKodeCabang(s.getKodeCabang());
                allTransaksiCabang.add(t);
            }
            for(AmbilBarangHead s : listAmbilBarangCabang){
                TransaksiCabang t = new TransaksiCabang();
                t.setTransaksi("Ambil Balenan Cabang");
                t.setNoTransaksi(s.getNoAmbilBarang());
                t.setKodeCabang(s.getKodeCabang());
                allTransaksiCabang.add(t);
            }
            for(PindahHead s : listPindahCabang){
                TransaksiCabang t = new TransaksiCabang();
                t.setTransaksi("Pindah Barang Cabang");
                t.setNoTransaksi(s.getNoPindah());
                t.setKodeCabang(s.getKodeCabang());
                allTransaksiCabang.add(t);
            }
            for(PenjualanAntarCabangHead s : listPenjualanAntarCabang){
                TransaksiCabang t = new TransaksiCabang();
                t.setTransaksi("Penjualan Antar Cabang");
                t.setNoTransaksi(s.getNoPenjualan());
                t.setKodeCabang(s.getKodeCabang());
                allTransaksiCabang.add(t);
            }
        }catch(Exception e){
            
        }
    }
    //PermintaanBarang
    @FXML private Label warningLabel;
    @FXML private VBox vbox;
    private void setPermintaanBarang(){
        Task<List<PermintaanBarang>> task = new Task<List<PermintaanBarang>>() {
            @Override 
            public List<PermintaanBarang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PermintaanBarangDAO.getAllByCabangAndLimit(conPusat, "%", 10);
                }
            }
        };
        task.setOnRunning((event) -> {
            permintaanBarangLoading.setVisible(true);
        });
        task.setOnSucceeded((ev) -> {
            try{
                List<PermintaanBarang> listPermintaan = task.getValue();
                vbox.getChildren().clear();
                for(PermintaanBarang p : listPermintaan){
                    ImageView img = null;
                    if(p.getStatus().equals("true"))
                        img = new ImageView(new Image(Main.class.getResourceAsStream("Resource/check.png"), 15, 15, true, true));
                    
                    Label tanggalAndSales = new Label(tglLengkap.format(tglSql.parse(p.getTanggal()))+" - "+p.getKodeCabang()+" ("+p.getKodeSales()+")", img);
                    tanggalAndSales.setContentDisplay(ContentDisplay.RIGHT);
                    tanggalAndSales.setStyle("-fx-font-size : 12; "
                            + " -fx-font-weight : bold; "
                            + " -fx-text-fill: seccolor1;");
                    
                    Button printButton = new Button("print");
                    printButton.setStyle("-fx-background-radius:3;-fx-padding:3 5 3 5;");
                    printButton.setOnAction((event) -> {
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            p.setStatus("true");
                            PermintaanBarangDAO.update(conPusat, p);
                            List<PermintaanBarang> print = new ArrayList<>();
                            print.add(p);
                            PrintOut printOut = new PrintOut();
                            printOut.printPermintaanBarang(print);
                            setPermintaanBarang();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    });
                    
                    Label keterangan = new Label(p.getKeterangan());
                    keterangan.setStyle("-fx-font-size : 13;");
                    keterangan.setWrapText(true);
                    
                    vbox.getChildren().add(tanggalAndSales);
                    vbox.getChildren().add(keterangan);
                    vbox.getChildren().add(printButton);
                    vbox.getChildren().add(new Separator(Orientation.HORIZONTAL));
                }
                warningLabel.setVisible(false);
            }catch(ParseException e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            permintaanBarangLoading.setVisible(false);
        });
        task.setOnFailed((e) -> {
            warningLabel.setVisible(true);
            permintaanBarangLoading.setVisible(false);
        });
        new Thread(task).start();
    }
    public void setTitle(String x){
        title.setText(x);
    }
    @FXML
    private void showDashboard(){
        mainApp.showDashboard();
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
    private void showDataUser(){
        mainApp.showDataUser();
    }
    @FXML
    private void showDataPegawai(){
        mainApp.showDataPegawai();
    }
    @FXML
    private void showDataKategoriBarang(){
        mainApp.showDataKategoriBarang();
    }
    @FXML
    private void showDataCabang(){
        mainApp.showDataCabang();
    }
    @FXML
    private void showDataSupplier(){
        mainApp.showDataSupplier();
    }
    @FXML
    private void showPengaturanUmum(){
        mainApp.showPengaturanUmum();
    }
    @FXML
    private void showTutupToko(){
        mainApp.showTutupToko();
    }
    
    @FXML
    private void showStokBarangPusat(){
        mainApp.showStokBarangPusat();
    }
    @FXML
    private void showDataPenjualanCabang(){
        mainApp.showDataPenjualanCabang();
    }
    @FXML
    private void showDataPembelianSupplier(){
        mainApp.showDataPembelianSupplier();
    }
    @FXML
    private void showDataReturPembelianSupplier(){
        mainApp.showDataReturPembelianSupplier();
    }
    //Barang
    @FXML
    private void showDataBarangBarcode(){
        mainApp.showDataBarangBarcode();
    }
    @FXML
    private void showStokBarangCabang(){
        mainApp.showStokBarangCabang();
    }
    @FXML
    private void showStokBarangBarcodeCabang(){
        mainApp.showStokBarangBarcodeCabang();
    }
    @FXML
    private void showBarcodeBarang(){
        mainApp.showBarcodeBarang();
    }
    @FXML
    private void showDataPindahBarang(){
        mainApp.showDataPindahBarang();
    }
    //SP
    @FXML
    private void showStokBalenanCabang(){
        mainApp.showStokBalenanCabang();
    }
    @FXML
    private void showStokSPCabang(){
        mainApp.showStokSPCabang();
    }
    @FXML
    private void showDataTerimaBalenanCabang(){
        mainApp.showDataTerimaBalenanCabang();
    }
    @FXML
    private void showDataSpBarangCabang(){
        mainApp.showDataSpBarangCabang();
    }
    @FXML
    private void showDataRevisiBarangCabang(){
        mainApp.showDataRevisiBarangCabang();
    }
    //Rosok dan Ciok
    @FXML
    private void showStokRosokCabang(){
        mainApp.showStokRosokCabang();
    }
    @FXML
    private void showDataRosokCabang(){
        mainApp.showDataRosokCabang();
    }
    @FXML
    private void showDataHancurBarang(){
        mainApp.showDataHancurBarang();
    }
    @FXML
    private void showDataLeburRosokCabang(){
        mainApp.showDataLeburRosokCabang();
    }
    @FXML
    private void showDataPenjualanCiokCabang(){
        mainApp.showDataPenjualanCiokCabang();
    }
    //Keuangan
    @FXML
    private void showDataTerimaSetoranCabang(){
        mainApp.showDataTerimaSetoranCabang();
    }
    @FXML
    private void showDataTambahUangCabang(){
        mainApp.showDataTambahUangCabang();
    }
    //Laporan
    @FXML
    private void showLaporanOmzet(){
        mainApp.showLaporanOmzet();
    }
    @FXML
    private void showLaporanPembelianUmumCabang(){
        mainApp.showLaporanPembelianUmumCabang();
    }
    @FXML
    private void showLaporanBatalBarcode(){
        mainApp.showLaporanBatalBarcode();
    }
    @FXML
    private void showLaporanPerformaBarcode(){
        mainApp.showLaporanPerformaBarcode();
    }
    @FXML
    private void showLaporanNeracaCabang(){
        mainApp.showLaporanNeracaCabang();
    }
    @FXML
    private void showLaporanUntungRugiCabang(){
        mainApp.showLaporanUntungRugiCabang();
    }
    @FXML
    private void showLaporanUntungRugiCabang2(){
        mainApp.showLaporanUntungRugiCabang2();
    }
    @FXML
    private void showLaporanTransaksiCabang(){
        mainApp.showLaporanTransaksiCabang();
    }
    
    @FXML
    private void showPenjualanFiktifCabang(){
        mainApp.showPenjualanFiktifCabang();
    }
    
}
