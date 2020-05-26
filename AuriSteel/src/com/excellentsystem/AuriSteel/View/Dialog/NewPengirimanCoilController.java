/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.StokBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.PemesananCoilDetail;
import com.excellentsystem.AuriSteel.Model.PemesananCoilHead;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableRow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class NewPengirimanCoilController  {

    
    @FXML private TableView<PenjualanCoilDetail> pengirimanDetailTable;
    @FXML private TableColumn<PenjualanCoilDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> spesifikasiColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> panjangColumn;
    
    @FXML public TextField noPemesananField;
    @FXML private TextField tglPemesananField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML public TextField jenisKendaraanField;
    @FXML public TextField noPolisiField;
    @FXML public TextField namaSupirField;
    @FXML private Label kursLabel;
    @FXML public TextField kursField;
    @FXML public ComboBox<String> gudangCombo;
    @FXML public CheckBox pemesananSelesai;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalBeratKotorField;
    @FXML private Label totalBeratBersihField;
    @FXML private Label totalPanjangField;
    
    @FXML private Label noPengirimanField;
    @FXML private Label tglPengirimanField;
    
    @FXML private Button addPemesananButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public PemesananCoilHead pemesanan;
    public ObservableList<PenjualanCoilDetail> allPenjualanCoilDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        Function.setNumberField(kursField);
        
        ContextMenu cm = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang");
        addBarang.setOnAction((ActionEvent e)->{
            addBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            pengirimanDetailTable.refresh();
        });
        cm.getItems().addAll(addBarang, refresh);
        pengirimanDetailTable.setContextMenu(cm);
        pengirimanDetailTable.setRowFactory((TableView<PenjualanCoilDetail> tv) -> {
            final TableRow<PenjualanCoilDetail> row = new TableRow<PenjualanCoilDetail>(){
                @Override
                public void updateItem(PenjualanCoilDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang");
                        addBarang.setOnAction((ActionEvent e)->{
                            addBarang();
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e)->{
                            allPenjualanCoilDetail.remove(item);
                            hitungTotal();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            pengirimanDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBarang, delete);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
        pengirimanDetailTable.setItems(allPenjualanCoilDetail);
    }
    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> listGudang = FXCollections.observableArrayList();
        for(Gudang g : sistem.getListGudang()){
            listGudang.add(g.getKodeGudang());
        }
        gudangCombo.setItems(listGudang);
    }
    private void setKurs(){
        if(pemesanan.getKurs()==1){
            kursField.setVisible(false);
            kursLabel.setVisible(false);
        }else{
            kursField.setVisible(true);
            kursLabel.setVisible(true);
        }
    }
    private void hitungTotal(){
        double totalQty = 0 ;
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPanjang = 0;
        for(PenjualanCoilDetail d : allPenjualanCoilDetail){
            totalQty = totalQty + 1;
            totalBeratKotor = totalBeratKotor + d.getBeratKotor();
            totalBeratBersih = totalBeratBersih + d.getBeratBersih();
            totalPanjang = totalPanjang + d.getPanjang();
        }
        totalQtyField.setText(df.format(totalQty));
        totalBeratKotorField.setText(df.format(totalBeratKotor));
        totalBeratBersihField.setText(df.format(totalBeratBersih));
        totalPanjangField.setText(df.format(totalPanjang));
    }
    public void setNewPengiriman(){
        noPengirimanField.setText("");
        tglPengirimanField.setText("");
    }
    public void setDetailPengiriman(String noPenjualan){
        Task<PenjualanCoilHead> task = new Task<PenjualanCoilHead>() {
            @Override 
            public PenjualanCoilHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PenjualanCoilHead pengiriman = PenjualanCoilHeadDAO.get(con, noPenjualan);
                    pengiriman.setPemesananCoilHead(PemesananCoilHeadDAO.get(con, pengiriman.getNoPemesanan()));
                    pengiriman.setCustomer(CustomerDAO.get(con, pengiriman.getKodeCustomer()));
                    pengiriman.setListPenjualanDetail(PenjualanCoilDetailDAO.getAllPenjualanCoilDetail(con, noPenjualan));
                    return pengiriman;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addPemesananButton.setVisible(false);
                jenisKendaraanField.setDisable(true);
                noPolisiField.setDisable(true);
                namaSupirField.setDisable(true);
                kursField.setDisable(true);
                gudangCombo.setDisable(true);
                pemesananSelesai.setVisible(false);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                List<MenuItem> removeMenu = new ArrayList<>();
                for(MenuItem m : pengirimanDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Barang"))
                        removeMenu.add(m);
                }
                pengirimanDetailTable.getContextMenu().getItems().removeAll(removeMenu);
                
                PenjualanCoilHead pengiriman = task.getValue();
                noPengirimanField.setText(pengiriman.getNoPenjualan());
                tglPengirimanField.setText(tglLengkap.format(tglSql.parse(pengiriman.getTglPenjualan())));
                noPemesananField.setText(pengiriman.getNoPemesanan());
                if(pengiriman.getPemesananCoilHead()!=null)
                    tglPemesananField.setText(tglLengkap.format(tglSql.parse(pengiriman.getPemesananCoilHead().getTglPemesanan())));
                namaField.setText(pengiriman.getCustomer().getNama());
                alamatField.setText(pengiriman.getCustomer().getAlamat());
                jenisKendaraanField.setText(pengiriman.getJenisKendaraan());
                noPolisiField.setText(pengiriman.getNoPolisi());
                namaSupirField.setText(pengiriman.getSupir());
                kursField.setText(df.format(pengiriman.getKurs()));
                gudangCombo.getSelectionModel().select(pengiriman.getKodeGudang());
                allPenjualanCoilDetail.addAll(pengiriman.getListPenjualanDetail());
                pemesanan = pengiriman.getPemesananCoilHead();
                setKurs();
                hitungTotal();
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void addPemesanan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPemesananCoil.fxml");
        AddPemesananCoilController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.pemesananHeadTable.setRowFactory(table -> {
            TableRow<PemesananCoilHead> row = new TableRow<PemesananCoilHead>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        try{
                            mainApp.closeDialog(stage, child);
                            pemesanan = row.getItem();
                            noPemesananField.setText(pemesanan.getNoPemesanan());
                            tglPemesananField.setText(tglLengkap.format(tglSql.parse(pemesanan.getTglPemesanan())));
                            namaField.setText(pemesanan.getCustomer().getNama());
                            alamatField.setText(pemesanan.getCustomer().getAlamat());
                            allPenjualanCoilDetail.clear();
                            setKurs();
                            hitungTotal();
                        }catch(Exception e){
                            mainApp.showMessage(Modality.NONE, "Error", e.toString());
                        }
                    }
                }
            });
            return row;
        });
    }
    @FXML
    private void changeGudang(){
        allPenjualanCoilDetail.clear();
        hitungTotal();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void addBarang(){
        if(pemesanan==null)
            mainApp.showMessage(Modality.NONE,"Warning", "Pemesanan belum dipilih");
        else if(gudangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPemesananCoil.fxml");
            AddDetailPemesananCoilController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setPemesananDetail(pemesanan.getListPemesananCoilDetail());
            controller.pemesananDetailTable.setRowFactory(table -> {
                TableRow<PemesananCoilDetail> row = new TableRow<PemesananCoilDetail>() {};
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2&&
                        row.getItem()!=null){
                        mainApp.closeDialog(stage, child);
                        addBahan(row.getItem());
                    }
                });
                return row;
            });
        }
    }
    private void addBahan(PemesananCoilDetail d){
        if(gudangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Gudang belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBahan.fxml");
            AddBahanController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.getBahan(gudangCombo.getSelectionModel().getSelectedItem());
            controller.bahanTable.setRowFactory((param) -> {
                TreeTableRow<Bahan> row = new TreeTableRow<Bahan>(){};
                row.setOnMouseClicked((MouseEvent evt) -> {
                    if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                        if(row.getItem().getNamaBahan()!=null){
                            try(Connection con = Koneksi.getConnection()){
                                Bahan b = row.getItem();
                                Boolean status = true;
                                for(PenjualanCoilDetail temp : allPenjualanCoilDetail){
                                    if(temp.getKodeBahan().equals(b.getKodeBahan()))
                                        status=false;
                                }
                                if(status==false){
                                    mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan baku sudah diinput");
                                }else{
                                    if(d.getKategoriBahan().equals(b.getKodeKategori())){
                                        StokBahan s = StokBahanDAO.getStokAkhir(con, 
                                                b.getKodeBahan(), gudangCombo.getSelectionModel().getSelectedItem());
                                        if(s==null){
                                            mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan tidak ditemukan");
                                        }else{
                                            if(s.getStokAkhir()<=0){
                                                mainApp.showMessage(Modality.NONE, "Warning", "Stok bahan sudah habis");
                                            }else{
                                                mainApp.closeDialog(stage,child);
                                                PenjualanCoilDetail detail = new PenjualanCoilDetail();
                                                detail.setBahan(b);
                                                detail.setKodeBahan(b.getKodeBahan());
                                                detail.setNamaBahan(b.getNamaBahan());
                                                detail.setSpesifikasi(b.getSpesifikasi());
                                                detail.setKeterangan(b.getKeterangan());
                                                detail.setBeratKotor(b.getBeratKotor());
                                                detail.setBeratBersih(b.getBeratBersih());
                                                detail.setPanjang(b.getPanjang());
                                                detail.setNilai(b.getHargaBeli());
                                                detail.setHargaJual(d.getHarga());
                                                detail.setTotal(d.getHarga()*b.getBeratBersih());
                                                allPenjualanCoilDetail.add(detail);
                                                hitungTotal();
                                            }
                                        }
                                    }else{
                                        mainApp.showMessage(Modality.NONE, "Warning", "Kode bahan yang diinput berbeda kategori dengan yang dipesan");
                                    }
                                } 
                            }catch(Exception e){
                                mainApp.showMessage(Modality.NONE, "Error", e.toString());
                            }
                        }
                    }
                });
                return row; 
            });
        }
    }
}
