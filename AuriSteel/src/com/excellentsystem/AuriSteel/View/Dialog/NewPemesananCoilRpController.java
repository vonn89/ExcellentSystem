/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananCoilHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.PemesananCoilDetail;
import com.excellentsystem.AuriSteel.Model.PemesananCoilHead;
import java.sql.Connection;
import java.text.DecimalFormat;
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
public class NewPemesananCoilRpController  {

    
    @FXML private TableView<PemesananCoilDetail> pemesananDetailTable;
    @FXML private TableColumn<PemesananCoilDetail, String> kategoriBahanColumn;
    @FXML private TableColumn<PemesananCoilDetail, String> namaBarangColumn;
    @FXML private TableColumn<PemesananCoilDetail, String> spesifikasiColumn;
    @FXML private TableColumn<PemesananCoilDetail, String> keteranganColumn;
    @FXML private TableColumn<PemesananCoilDetail, Number> qtyColumn;
    @FXML private TableColumn<PemesananCoilDetail, Number> hargaColumn;
    @FXML private TableColumn<PemesananCoilDetail, Number> hargaPPNColumn;
    @FXML private TableColumn<PemesananCoilDetail, Number> totalColumn;
    
    @FXML private Label noPemesananField;
    @FXML private Label tglPemesananField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField namaSalesField;
    
    @FXML public TextField paymentTermField;
    @FXML public TextArea catatanField;
    @FXML private TextField totalPemesananField;
    @FXML private TextField ppnField;
    @FXML public TextField grandtotalField;
    
    @FXML private Button addCustomerButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    @FXML private GridPane gridPane;
    public Customer customer;
    public ObservableList<PemesananCoilDetail> allPemesananCoilDetail = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner; 
    public void initialize() {
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kategoriBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriBahanProperty());
        hargaColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHarga()/1.1));
        hargaColumn.setCellFactory(col -> Function.getTableCell());
        hargaPPNColumn.setCellValueFactory(cellData -> cellData.getValue().hargaProperty());
        hargaPPNColumn.setCellFactory(col -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()/1.1));
        totalColumn.setCellFactory(col -> Function.getTableCell());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> Function.getTableCell());
        pemesananDetailTable.setItems(allPemesananCoilDetail);
        
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
        pemesananDetailTable.setRowFactory((TableView<PemesananCoilDetail> tv) -> {
            final TableRow<PemesananCoilDetail> row = new TableRow<PemesananCoilDetail>(){
                @Override
                public void updateItem(PemesananCoilDetail item, boolean empty) {
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
                            allPemesananCoilDetail.remove(item);
                            hitungTotal();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            pemesananDetailTable.refresh();
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
    }
    @FXML
    private void hitungTotal(){
        double totalPemesanan = 0;
        for(PemesananCoilDetail temp : allPemesananCoilDetail){
            totalPemesanan = totalPemesanan + temp.getTotal();
        }
        totalPemesananField.setText(df.format(totalPemesanan/1.1));
        ppnField.setText(df.format(totalPemesanan/1.1*0.1));
        grandtotalField.setText(new DecimalFormat("###,##0").format(totalPemesanan));
    }
    public void setNewPemesanan(){
        noPemesananField.setText("");
        tglPemesananField.setText("");
    }
    public void setDetailPemesanan(String noPemesanan){
        Task<PemesananCoilHead> task = new Task<PemesananCoilHead>() {
            @Override 
            public PemesananCoilHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    PemesananCoilHead pemesanan = PemesananCoilHeadDAO.get(con, noPemesanan);
                    pemesanan.setCustomer(CustomerDAO.get(con, pemesanan.getKodeCustomer()));
                    pemesanan.setSales(PegawaiDAO.get(con, pemesanan.getKodeSales()));
                    pemesanan.setListPemesananCoilDetail(PemesananCoilDetailDAO.getAllByNoPemesanan(con, noPemesanan));
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
                paymentTermField.setDisable(true);
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
                
                PemesananCoilHead pemesanan = task.getValue();
                noPemesananField.setText(pemesanan.getNoPemesanan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(pemesanan.getTglPemesanan())));
                namaField.setText(pemesanan.getCustomer().getNama());
                alamatField.setText(pemesanan.getCustomer().getAlamat());
                paymentTermField.setText(pemesanan.getPaymentTerm());
                namaSalesField.setText(pemesanan.getSales().getNama());
                catatanField.setText(pemesanan.getCatatan());
                allPemesananCoilDetail.addAll(pemesanan.getListPemesananCoilDetail());
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
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddItemPemesananCoil.fxml");
        AddItemPemesananCoilController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.namaBarangField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.kategoriCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else{
                mainApp.closeDialog(stage,child);
                PemesananCoilDetail detail = new PemesananCoilDetail();
                detail.setKategoriBahan(controller.kategoriCombo.getSelectionModel().getSelectedItem()); 
                detail.setNamaBarang(controller.namaBarangField.getText());
                detail.setKeterangan(controller.keteranganField.getText());
                detail.setSpesifikasi(controller.spesifikasiField.getText());    
                detail.setQty(Double.parseDouble(controller.qtyField.getText().replaceAll(",", "")));
                detail.setHarga(Double.parseDouble(controller.hargaField.getText().replaceAll(",", ""))); 
                detail.setTotal(Double.parseDouble(controller.totalField.getText().replaceAll(",", "")));
                allPemesananCoilDetail.add(detail);
                hitungTotal();
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
                    namaField.setText(customer.getNama());
                    alamatField.setText(customer.getAlamat());
                    namaSalesField.setText(customer.getSales().getNama());
                }
            });
            return row; 
        });
    }
}
