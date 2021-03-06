/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BarangDAO;
import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.LogBarangDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Gudang;
import com.excellentsystem.AuriSteel.Model.LogBarang;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class NewPengirimanController  {

    @FXML private TableView<PenjualanBarangDetail> pengirimanDetailTable;
    @FXML private TableColumn<PenjualanBarangDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PenjualanBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanBarangDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanBarangDetail, String> catatanInternColumn;
    @FXML private TableColumn<PenjualanBarangDetail, String> satuanColumn;
    @FXML private TableColumn<PenjualanBarangDetail, Number> qtyColumn;
    
    @FXML public TextField noPemesananField;
    @FXML private TextField tglPemesananField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField namaInvoiceField;
    @FXML public TextArea alamatKirimField;
    @FXML public TextField namaSupirField;
    @FXML public ComboBox<String> gudangCombo;
    
    @FXML private Label noPengirimanField;
    @FXML private Label tglPengirimanField;
    @FXML private Label totalQtyField;
    @FXML private Label totalTonaseField;
    
    @FXML private Button addPemesananButton;
    @FXML private Button cancelButton;
    @FXML public Button saveButton;
    
    public PemesananBarangHead pemesanan;
    public ObservableList<PenjualanBarangDetail> allPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanInternProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        pengirimanDetailTable.setItems(allPenjualanDetail);
        
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
        pengirimanDetailTable.setRowFactory((TableView<PenjualanBarangDetail> tv) -> {
            final TableRow<PenjualanBarangDetail> row = new TableRow<PenjualanBarangDetail>(){
                @Override
                public void updateItem(PenjualanBarangDetail item, boolean empty) {
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
                            allPenjualanDetail.remove(item);
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
    private void hitungTotal(){
        double total = 0;
        double totalTonase = 0;
        for(PenjualanBarangDetail d : allPenjualanDetail){
            total = total + d.getQty();
            totalTonase = totalTonase + d.getQty()*d.getBarang().getBerat();
        }
        totalQtyField.setText(df.format(total));
        totalTonaseField.setText(df.format(totalTonase));
    }
    public void setNewPengiriman(){
        noPengirimanField.setText("");
        tglPengirimanField.setText("");
    }
    public void setDetailPengiriman(String noPenjualan){
        Task<PenjualanBarangHead> task = new Task<PenjualanBarangHead>() {
            @Override 
            public PenjualanBarangHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PenjualanBarangHead pengiriman = PenjualanBarangHeadDAO.get(con, noPenjualan);
                    pengiriman.setPemesananBarangHead(PemesananBarangHeadDAO.get(con, pengiriman.getNoPemesanan()));
                    pengiriman.getPemesananBarangHead().setCustomerInvoice(CustomerDAO.get(con, pengiriman.getPemesananBarangHead().getKodeCustomerInvoice()));
                    pengiriman.setCustomer(CustomerDAO.get(con, pengiriman.getKodeCustomer()));
                    pengiriman.setListPenjualanBarangDetail(PenjualanBarangDetailDAO.getAllPenjualanDetail(con, noPenjualan));
                    for(PenjualanBarangDetail d : pengiriman.getListPenjualanBarangDetail()){
                        d.setBarang(BarangDAO.get(con, d.getKodeBarang()));
                    }
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
                alamatKirimField.setDisable(true);
                namaSupirField.setDisable(true);
                gudangCombo.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                List<MenuItem> removeMenu = new ArrayList<>();
                for(MenuItem m : pengirimanDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Barang"))
                        removeMenu.add(m);
                }
                pengirimanDetailTable.getContextMenu().getItems().removeAll(removeMenu);
                
                PenjualanBarangHead pengiriman = task.getValue();
                noPengirimanField.setText(pengiriman.getNoPenjualan());
                tglPengirimanField.setText(tglLengkap.format(tglSql.parse(pengiriman.getTglPengiriman())));
                noPemesananField.setText(pengiriman.getNoPemesanan());
                if(pengiriman.getPemesananBarangHead()!=null)
                    tglPemesananField.setText(tglLengkap.format(tglSql.parse(pengiriman.getPemesananBarangHead().getTglPemesanan())));
                namaField.setText(pengiriman.getCustomer().getNama());
                namaInvoiceField.setText(pengiriman.getPemesananBarangHead().getCustomerInvoice().getNama());
                alamatField.setText(pengiriman.getCustomer().getAlamat());
                alamatKirimField.setText(pengiriman.getTujuanKirim());
                namaSupirField.setText(pengiriman.getSupir());
                gudangCombo.getSelectionModel().select(pengiriman.getKodeGudang());
                allPenjualanDetail.addAll(pengiriman.getListPenjualanBarangDetail());
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
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPemesanan.fxml");
        AddPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.pemesananHeadTable.setRowFactory(table -> {
            TableRow<PemesananBarangHead> row = new TableRow<PemesananBarangHead>() {};
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
                            namaInvoiceField.setText(pemesanan.getCustomerInvoice().getNama());
                            alamatField.setText(pemesanan.getCustomer().getAlamat());
                            allPenjualanDetail.clear();
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
        allPenjualanDetail.clear();
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
            mainApp.showMessage(Modality.NONE,"Warning", "Gudang belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddDetailPemesanan.fxml");
            AddDetailPemesananController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setPemesananDetail(pemesanan.getListPemesananBarangDetail());
            controller.addButton.setOnAction((ActionEvent event) -> {
                if(controller.pemesananDetail!=null){
                    mainApp.closeDialog(stage, child);
                    double qty = 0;
                    for(PemesananBarangDetail d : controller.allPemesananDetail){
                        if(d.getKodeBarang().equals(controller.pemesananDetail.getKodeBarang())&&
                                d.getKeterangan().equals(controller.pemesananDetail.getKeterangan())){
                            qty = qty + (d.getQty()-d.getQtyTerkirim());
                        }
                    }
                    PenjualanBarangDetail d = null;
                    for(PenjualanBarangDetail temp : allPenjualanDetail){
                        if(temp.getNoPemesanan().equals(controller.pemesananDetail.getNoPemesanan())&&
                                temp.getNoUrut()==controller.pemesananDetail.getNoUrut()){
                            qty = qty-temp.getQty();
                            d = temp;
                        }   
                    }
                    if(qty>=Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))){
                        if(d==null){
                            try(Connection con = Koneksi.getConnection()){
                                LogBarang log = LogBarangDAO.getLastByBarangAndGudang(con, controller.pemesananDetail.getKodeBarang(), gudangCombo.getSelectionModel().getSelectedItem());
                                PenjualanBarangDetail detail = new PenjualanBarangDetail();
                                detail.setNoPemesanan(controller.pemesananDetail.getNoPemesanan());
                                detail.setNoUrut(controller.pemesananDetail.getNoUrut());
                                detail.setKodeBarang(controller.pemesananDetail.getKodeBarang());
                                detail.setNamaBarang(controller.pemesananDetail.getNamaBarang());
                                detail.setKeterangan(controller.keteranganField.getText());
                                detail.setCatatanIntern(controller.pemesananDetail.getCatatanIntern());
                                detail.setSatuan(controller.pemesananDetail.getSatuan());
                                detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                                if(log.getStokAkhir()!=0)
                                    detail.setNilai(log.getNilaiAkhir()/log.getStokAkhir());
                                else
                                    detail.setNilai(0);
                                detail.setHargaJual(controller.pemesananDetail.getHargaJual());
                                detail.setTotal(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))*
                                        controller.pemesananDetail.getHargaJual());
                                detail.setBarang(BarangDAO.get(con, controller.pemesananDetail.getKodeBarang()));
                                allPenjualanDetail.add(detail);                        
                            }catch(Exception e){
                                mainApp.showMessage(Modality.NONE, "Error", e.toString());
                            }
                        }else{
                            d.setQty(d.getQty()+Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                            d.setTotal(d.getQty()*d.getHargaJual());
                            pengirimanDetailTable.refresh();
                        }
                        hitungTotal();
                    }else{
                        mainApp.showMessage(Modality.NONE, "Warning", "Barang yang akan dikirim melebihi pemesanan");
                    }
                }
            });
        }
    }
}
