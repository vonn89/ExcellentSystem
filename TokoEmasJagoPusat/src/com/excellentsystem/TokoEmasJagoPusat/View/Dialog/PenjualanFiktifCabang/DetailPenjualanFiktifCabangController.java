/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang;

import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanFiktifDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifHead;
import java.sql.Connection;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DetailPenjualanFiktifCabangController  {

    @FXML private TableView<PenjualanFiktifDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanFiktifDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PenjualanFiktifDetail, String> namaBarangColumn;
    @FXML private TableColumn<PenjualanFiktifDetail, String> kadarColumn;
    @FXML private TableColumn<PenjualanFiktifDetail, String> kodeInternColumn;
    @FXML private TableColumn<PenjualanFiktifDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanFiktifDetail, Number> hargaColumn;
    
    @FXML public Label noPenjualanLabel;
    @FXML public Label tglPenjualanLabel;
    @FXML public TextField namaField;
    @FXML public TextField alamatField;
    @FXML public TextField noTelpField;
    @FXML public TextField totalPenjualanField;
    
    @FXML private TextField kodeKategoriField;
    @FXML private TextField namaBarangField;
    @FXML private TextField kadarField;
    @FXML private TextField kodeInternField;
    @FXML private TextField beratField;
    @FXML private TextField hargaJualField;
    
    @FXML public Button saveButton;
    
    public ObservableList<PenjualanFiktifDetail> allPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        beratColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getBerat()));
        hargaColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getHargaJual()));
        beratColumn.setCellFactory(col -> Function.getTableCell(gr));
        hargaColumn.setCellFactory(col -> Function.getTableCell(rp));
        
        penjualanDetailTable.setItems(allPenjualanDetail);
        Function.setNumberField(beratField, gr);
        Function.setNumberField(hargaJualField, rp);
        penjualanDetailTable.setRowFactory((TableView<PenjualanFiktifDetail> tableView) -> {
            final TableRow<PenjualanFiktifDetail> row = new TableRow<PenjualanFiktifDetail>(){
                @Override
                public void updateItem(PenjualanFiktifDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        MenuItem hapus = new MenuItem("Hapus barang");
                        hapus.setOnAction((ActionEvent e)->{
                            hapusBarang(item);
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(hapus);
                        setContextMenu(rowMenu);
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
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setDetailPenjualan(PenjualanFiktifHead p){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = KoneksiPusat.getConnection()) {
                    p.setListPenjualanDetail(
                            PenjualanFiktifDetailDAO.getAllByNoPenjualan(con, p.getNoPenjualan()));
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
                noPenjualanLabel.setText(p.getNoPenjualan());
                tglPenjualanLabel.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                namaField.setText(p.getNama());
                alamatField.setText(p.getAlamat());
                noTelpField.setText(p.getNoTelp());
                totalPenjualanField.setText(rp.format(p.getGrandtotal()));
                allPenjualanDetail.addAll(p.getListPenjualanDetail());
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
    private void tambahBarang(){
        if(kodeKategoriField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Kode kategori masih kosong");
        else if(namaBarangField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        else if(beratField.getText().equals("")||beratField.getText().equals("0"))
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
        else if(hargaJualField.getText().equals("")||hargaJualField.getText().equals("0"))
            mainApp.showMessage(Modality.NONE, "Warning", "Harga jual masih kosong");
        else{
            PenjualanFiktifDetail d = new PenjualanFiktifDetail();
            d.setNoPenjualan(noPenjualanLabel.getText());
            d.setKodeKategori(kodeKategoriField.getText());
            d.setNamaBarang(namaBarangField.getText());
            d.setKadar(kadarField.getText());
            d.setKodeIntern(kodeInternField.getText());
            d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
            d.setHargaJual(Double.parseDouble(hargaJualField.getText().replaceAll(",", "")));
            allPenjualanDetail.add(d);
            penjualanDetailTable.refresh();
            hitungTotal();
            kodeKategoriField.setText("");
            namaBarangField.setText("");
            kadarField.setText("");
            kodeInternField.setText("");
            beratField.setText("");
            hargaJualField.setText("");
        }
        
    }
    private void hapusBarang(PenjualanFiktifDetail d){
        allPenjualanDetail.remove(d);
        penjualanDetailTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double total = 0;
        for(PenjualanFiktifDetail d : allPenjualanDetail){
            total = total + d.getHargaJual();
        }
        totalPenjualanField.setText(rp.format(total));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
