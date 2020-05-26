/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PegawaiDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilDetailDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import java.sql.Connection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPenjualanCoilController {

    @FXML private TableView<PenjualanCoilDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanCoilDetail, String> kodeBahanColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> namaBahanColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanCoilDetail, String> spesifikasiColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> beratKotorColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> beratBersihColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> panjangColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> hargaJualColumn;
    @FXML private TableColumn<PenjualanCoilDetail, Number> subTotalColumn;
    
    @FXML private Label noPenjualanField;
    @FXML private Label tglPenjualanField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    @FXML private TextField namaSalesField;
    
    @FXML private TextField paymentTermField;
    
    @FXML private TextArea catatanField;
    
    @FXML private Label totalQtyField;
    @FXML private Label totalBeratKotorField;
    @FXML private Label totalBeratBersihField;
    @FXML private Label totalPanjangField;
    
    @FXML private TextField totalPenjualanusdField;
    @FXML private TextField kursField;
    @FXML private TextField totalPenjualanField;
    @FXML private TextField ppnField;
    @FXML private TextField grandtotalField;
    
    private PenjualanCoilHead penjualan;
    private ObservableList<PenjualanCoilDetail> allPenjualanCoilDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        spesifikasiColumn.setCellValueFactory(cellData -> cellData.getValue().spesifikasiProperty());
        hargaJualColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHargaJual()/1.1));
        subTotalColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotal()/1.1));
        beratKotorColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBeratKotor()));
        beratBersihColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBeratBersih()));
        panjangColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPanjang()));
        hargaJualColumn.setCellFactory(col -> Function.getTableCell());
        subTotalColumn.setCellFactory(col -> Function.getTableCell());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());
        panjangColumn.setCellFactory(col -> Function.getTableCell());
        penjualanDetailTable.setItems(allPenjualanCoilDetail);
        
        ContextMenu cm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            penjualanDetailTable.refresh();
        });
        cm.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(cm);
        penjualanDetailTable.setRowFactory((TableView<PenjualanCoilDetail> tv) -> {
            final TableRow<PenjualanCoilDetail> row = new TableRow<PenjualanCoilDetail>(){
                @Override
                public void updateItem(PenjualanCoilDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(cm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            penjualanDetailTable.refresh();
                        });
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
    }
    private void hitungTotal(){
        double qty = 0;
        double beratKotor = 0;
        double beratBersih = 0;
        double panjang = 0;
        for(PenjualanCoilDetail d : allPenjualanCoilDetail){
            qty = qty + 1;
            beratKotor = beratKotor + d.getBeratKotor();
            beratBersih = beratBersih + d.getBeratBersih();
            panjang = panjang + d.getPanjang();
        }
        totalQtyField.setText(df.format(qty));
        totalBeratKotorField.setText(df.format(beratKotor));
        totalBeratBersihField.setText(df.format(beratBersih));
        totalPanjangField.setText(df.format(panjang));
    }
    public void setDetailPenjualan(String noPenjualan){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    penjualan = PenjualanCoilHeadDAO.get(con, noPenjualan);
                    penjualan.setCustomer(CustomerDAO.get(con, penjualan.getKodeCustomer()));
                    penjualan.setSales(PegawaiDAO.get(con, penjualan.getKodeSales()));
                    penjualan.setListPenjualanDetail(PenjualanCoilDetailDAO.getAllPenjualanCoilDetail(con, noPenjualan));
                    return "true";
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                noPenjualanField.setText(penjualan.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
                namaField.setText(penjualan.getCustomer().getNama());
                alamatField.setText(penjualan.getCustomer().getAlamat());
                paymentTermField.setText(penjualan.getPaymentTerm());
                namaSalesField.setText(penjualan.getSales().getNama());
                catatanField.setText(penjualan.getCatatan());
                allPenjualanCoilDetail.addAll(penjualan.getListPenjualanDetail());
                totalPenjualanField.setText(df.format(penjualan.getTotalPenjualan()/1.1));
                ppnField.setText(df.format(penjualan.getTotalPenjualan()/1.1*0.1));
                grandtotalField.setText(df.format(penjualan.getTotalPenjualan()));
                totalPenjualanusdField.setText(df.format(penjualan.getTotalPenjualan()/1.1/penjualan.getKurs()));
                kursField.setText(df.format(penjualan.getKurs()));
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
    private void backToDataPenjualan(){
        mainApp.closeDialog(owner, stage);
    }
}
