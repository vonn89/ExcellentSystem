/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPemesananController  {

    
    @FXML private TableView<PemesananBarangDetail> pemesananDetailTable;
    @FXML private TableColumn<PemesananBarangDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> namaBarangColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> keteranganColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> catatanInternColumn;
    @FXML private TableColumn<PemesananBarangDetail, String> satuanColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> qtyColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> qtyTerkirimColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> hargaJualColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> hargaPPNColumn;
    @FXML private TableColumn<PemesananBarangDetail, Number> totalColumn;
    
    @FXML private GridPane gridPane;
    @FXML public Label noPemesananField;
    @FXML private Label tglPemesananField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField namaSalesField;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalQtyTerkirimField;
    
    @FXML public ComboBox<String> paymentTermCombo;
    @FXML public TextArea catatanField;
    @FXML public TextField totalPemesananField;
    @FXML public TextField ppnField;
    @FXML public TextField grandtotalField;
    
    @FXML private Button addCustomerButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    public Customer customer;
    public Customer customerInvoice;
    public ObservableList<PemesananBarangDetail> allPemesananDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner; 
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().satuanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        catatanInternColumn.setCellValueFactory(cellData -> cellData.getValue().catatanInternProperty());
        hargaJualColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getHargaJual()/1.1));
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        hargaPPNColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getHargaJual()));
        hargaPPNColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getHargaJual()/1.1*cellData.getValue().getQty()));
        totalColumn.setCellFactory(col -> Function.getTableCell());
        qtyColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getQty()));
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        qtyTerkirimColumn.setCellValueFactory(cellData -> 
            new SimpleDoubleProperty(cellData.getValue().getQtyTerkirim()));
        qtyTerkirimColumn.setCellFactory(col -> Function.getTableCell());
        pemesananDetailTable.setItems(allPemesananDetail);
        
        ContextMenu cm = new ContextMenu();
        MenuItem addBarang = new MenuItem("Add Barang");
        addBarang.setOnAction((ActionEvent e)->{
            addBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            pemesananDetailTable.refresh();
        });
        cm.getItems().addAll(addBarang, refresh);
        pemesananDetailTable.setContextMenu(cm);
        pemesananDetailTable.setRowFactory((TableView<PemesananBarangDetail> tv) -> {
            final TableRow<PemesananBarangDetail> row = new TableRow<PemesananBarangDetail>(){
                @Override
                public void updateItem(PemesananBarangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addBarang = new MenuItem("Add Barang");
                        addBarang.setOnAction((ActionEvent e)->{
                            addBarang();
                        });
                        MenuItem edit = new MenuItem("Edit Barang");
                        edit.setOnAction((ActionEvent e)->{
                            editBarang(item);
                        });
                        MenuItem delete = new MenuItem("Delete Barang");
                        delete.setOnAction((ActionEvent e)->{
                            deleteBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            pemesananDetailTable.refresh();
                        });
                        if(saveButton.isVisible())
                            rm.getItems().addAll(addBarang, edit, delete);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
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
        ObservableList<String> listPayment = FXCollections.observableArrayList();
        listPayment.add("Cash Before Delivery");
        listPayment.add("Cash On Delivery");
        listPayment.add("Cash");
        listPayment.add("Net 7");
        listPayment.add("Net 14");
        listPayment.add("Net 30");
        paymentTermCombo.setItems(listPayment);
    }
    @FXML
    private void hitungTotal(){
        double totalQty = 0;
        double totalQtyTerkirim = 0;
        double totalPemesanan = 0;
        for(PemesananBarangDetail temp : allPemesananDetail){
            totalPemesanan = totalPemesanan + temp.getTotal();
            totalQty = totalQty + temp.getQty();
            totalQtyTerkirim = totalQtyTerkirim + temp.getQtyTerkirim();
        }
        totalQtyField.setText(df.format(totalQty));
        totalQtyTerkirimField.setText(df.format(totalQtyTerkirim));
        totalPemesananField.setText(df.format(totalPemesanan/1.1));
        ppnField.setText(df.format(totalPemesanan/1.1*0.1));
        grandtotalField.setText(df.format(totalPemesanan));
    }
    public void setNewPemesanan(){
        noPemesananField.setText("");
        tglPemesananField.setText("");
    }
    public void setDetailPemesanan(String noPemesanan){
        Task<PemesananBarangHead> task = new Task<PemesananBarangHead>() {
            @Override 
            public PemesananBarangHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, noPemesanan);
                    pemesanan.setCustomer(CustomerDAO.get(con, pemesanan.getKodeCustomer()));
                    pemesanan.setCustomerInvoice(CustomerDAO.get(con, pemesanan.getKodeCustomerInvoice()));
                    pemesanan.setSales(PegawaiDAO.get(con, pemesanan.getKodeSales()));
                    pemesanan.setListPemesananBarangDetail(PemesananBarangDetailDAO.getAllByNoPemesanan(con, noPemesanan));
                    return pemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addCustomerButton.setVisible(false);
                paymentTermCombo.setDisable(true);
                catatanField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                AnchorPane.setBottomAnchor(gridPane, 0.0);
                List<MenuItem> removeMenu = new ArrayList<>();
                for(MenuItem m : pemesananDetailTable.getContextMenu().getItems()){
                    if(m.getText().equals("Add Barang"))
                        removeMenu.add(m);
                }
                pemesananDetailTable.getContextMenu().getItems().removeAll(removeMenu);
                
                PemesananBarangHead pemesanan = task.getValue();
                customerInvoice = pemesanan.getCustomerInvoice();
                noPemesananField.setText(pemesanan.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(pemesanan.getTglPemesanan())));
                namaField.setText(pemesanan.getCustomer().getNama());
                alamatField.setText(pemesanan.getCustomer().getAlamat());
                paymentTermCombo.getSelectionModel().select(pemesanan.getPaymentTerm());
                namaSalesField.setText(pemesanan.getSales().getNama());
                catatanField.setText(pemesanan.getCatatan());
                allPemesananDetail.addAll(pemesanan.getListPemesananBarangDetail());
                totalPemesananField.setText(df.format(pemesanan.getTotalPemesanan()/1.1));
                ppnField.setText(df.format(pemesanan.getTotalPemesanan()/1.1*0.1));
                grandtotalField.setText(df.format(pemesanan.getTotalPemesanan()));
                hitungTotal();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void editPemesanan(String noPemesanan){
        Task<PemesananBarangHead> task = new Task<PemesananBarangHead>() {
            @Override 
            public PemesananBarangHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PemesananBarangHead pemesanan = PemesananBarangHeadDAO.get(con, noPemesanan);
                    pemesanan.setCustomer(CustomerDAO.get(con, pemesanan.getKodeCustomer()));
                    pemesanan.setCustomerInvoice(CustomerDAO.get(con, pemesanan.getKodeCustomerInvoice()));
                    pemesanan.setSales(PegawaiDAO.get(con, pemesanan.getKodeSales()));
                    pemesanan.setListPemesananBarangDetail(PemesananBarangDetailDAO.getAllByNoPemesanan(con, noPemesanan));
                    customer = pemesanan.getCustomer();
                    customerInvoice = pemesanan.getCustomerInvoice();
                    return pemesanan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                
                PemesananBarangHead pemesanan = task.getValue();
                customerInvoice = pemesanan.getCustomerInvoice();
                noPemesananField.setText(pemesanan.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(pemesanan.getTglPemesanan())));
                namaField.setText(pemesanan.getCustomer().getNama());
                alamatField.setText(pemesanan.getCustomer().getAlamat());
                paymentTermCombo.getSelectionModel().select(pemesanan.getPaymentTerm());
                namaSalesField.setText(pemesanan.getSales().getNama());
                catatanField.setText(pemesanan.getCatatan());
                allPemesananDetail.addAll(pemesanan.getListPemesananBarangDetail());
                totalPemesananField.setText(df.format(pemesanan.getTotalPemesanan()/1.1));
                ppnField.setText(df.format(pemesanan.getTotalPemesanan()/1.1*0.1));
                grandtotalField.setText(df.format(pemesanan.getTotalPemesanan()));
                hitungTotal();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void addBarang(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddBarangPemesanan.fxml");
        AddBarangPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.barang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else if(!sistem.getUser().getLevel().equals("Manager")&&
                controller.barang.getHargaJual()>Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual tidak boleh di bawah batas harga");
            else{
                mainApp.closeDialog(stage,child);
                PemesananBarangDetail d = null;
                for(PemesananBarangDetail temp : allPemesananDetail){
                    if(temp.getKodeBarang().equals(controller.barang.getKodeBarang())&&
                        temp.getKeterangan().equals(controller.keteranganField.getText())&&
                        temp.getHargaJual()==Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")))
                            d = temp;
                }
                if(d==null){
                    PemesananBarangDetail detail = new PemesananBarangDetail();
                    detail.setKodeBarang(controller.barang.getKodeBarang());
                    detail.setNamaBarang(controller.barang.getNamaBarang());
                    detail.setKeterangan(controller.keteranganField.getText());
                    detail.setCatatanIntern(controller.catatanInternField.getText());
                    detail.setSatuan(controller.barang.getSatuan());
                    detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    detail.setQtyTerkirim(0);
                    detail.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                    detail.setTotal(Double.parseDouble(controller.totalField.getText().replaceAll(",", "")));
                    allPemesananDetail.add(detail);
                    hitungTotal();
                }else{
                    d.setQty(d.getQty()+Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                    d.setTotal(d.getQty()*d.getHargaJual());
                    pemesananDetailTable.refresh();
                    hitungTotal();
                }
            }
        });
    }
    @FXML 
    private void deleteBarang(PemesananBarangDetail d){
        if(d.getQtyTerkirim()>0){
            mainApp.showMessage(Modality.NONE, "Warning", "Qty barang sudah ada yeng terkirim, tidak dapat di hapus");
        }else{
            allPemesananDetail.remove(d);
            hitungTotal();
            pemesananDetailTable.refresh();
        }
    }
    @FXML
    private void editBarang(PemesananBarangDetail detail){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/EditBarangPemesanan.fxml");
        EditBarangPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.editBarang(detail);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.barang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih atau kode barang masih kosong");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(detail.getQtyTerkirim()>0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty barang sudah ada yeng terkirim, tidak dapat di ubah");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else if(!sistem.getUser().getLevel().equals("Manager")&&
                controller.barang.getHargaJual()>Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")))
                mainApp.showMessage(Modality.NONE, "Warning", "Harga jual tidak boleh di bawah batas harga");
            else{
                mainApp.closeDialog(stage,child);
                detail.setKodeBarang(controller.barang.getKodeBarang());
                detail.setNamaBarang(controller.barang.getNamaBarang());
                detail.setKeterangan(controller.keteranganField.getText());
                detail.setCatatanIntern(controller.catatanInternField.getText());
                detail.setSatuan(controller.barang.getSatuan());
                detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                detail.setHargaJual(Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", "")));
                detail.setTotal(Double.parseDouble(controller.totalField.getText().replaceAll(",", "")));
                hitungTotal();
                pemesananDetailTable.refresh();
            }
        });
    }
    @FXML
    private void addCustomer(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddCustomer.fxml");
        AddCustomerController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.customerTable.setRowFactory((param) -> {
            TableRow<Customer> row = new TableRow<Customer>(){};
            row.setOnMouseClicked((MouseEvent evt) -> {
                if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                    mainApp.closeDialog(stage, child);
                    customer = row.getItem();
                    customerInvoice = row.getItem();
                    namaField.setText(customer.getNama());
                    alamatField.setText(customer.getAlamat());
                    namaSalesField.setText(customer.getSales().getNama());
                }
            });
            return row; 
        });
    }
    @FXML
    private void namaInvoice(){
        if(customerInvoice==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NamaInvoice.fxml");
            NamaInvoiceController controller = loader.getController();
            controller.setMainApp(mainApp, stage, child);
            controller.setCustomer(customerInvoice);
            if(saveButton.isVisible()){
                child.setOnHiding((event) -> {
                    customerInvoice = controller.customer;
                });
            }else{
                controller.setViewOnly();
            }
        }
    }
}
