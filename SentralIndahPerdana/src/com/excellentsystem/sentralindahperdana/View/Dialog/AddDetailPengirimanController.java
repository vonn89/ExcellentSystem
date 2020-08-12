/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddDetailPengirimanController  {

    @FXML private TableView<PengirimanDetail> pengirimanDetailTable;
    @FXML private TableColumn<PengirimanDetail, String> kodeBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> namaBarangColumn;
    @FXML private TableColumn<PengirimanDetail, String> satuanColumn;
    @FXML private TableColumn<PengirimanDetail, Number> qtyColumn;
    
    @FXML private TextField kodeBarangField;
    @FXML private TextField namaBarangField;
    @FXML public TextField qtyField;
    @FXML public TextField satuanField;
    @FXML public Button addButton;
    public PengirimanDetail pengirimanDetail;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private final ObservableList<PengirimanDetail> allPengirimanDetail = FXCollections.observableArrayList();
    public void initialize() {
        kodeBarangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        qtyColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyColumn.setCellFactory((c) -> Function.getTableCell());
        qtyField.setOnKeyReleased((event) -> {
            try{
                String string = qtyField.getText();
                if(string.indexOf(".")>0){
                    String string2 = string.substring(string.indexOf(".")+1, string.length());
                    if(string2.contains("."))
                        qtyField.undo();
                    else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                        qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                }else
                    qtyField.setText(df.format(Double.parseDouble(string.replaceAll(",", ""))));
                qtyField.end();
            }catch(NumberFormatException e){
                qtyField.undo();
            }
        });
        pengirimanDetailTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectBarang(newValue);
        });
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        pengirimanDetailTable.setItems(allPengirimanDetail);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void getBarang(PenjualanDetail d){
        Task<List<PengirimanDetail>> task = new Task<List<PengirimanDetail>>() {
            @Override 
            public List<PengirimanDetail> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                        con, d.getNoPenjualan(), d.getNoUrut(), "true");
                    List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                        con, d.getNoPenjualan(), d.getNoUrut(), "true");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    List<PengirimanDetail> listDetail = new ArrayList<>();
                    for(Barang b : listBarang){
                        double qty = 0;
                        double nilai = 0;
                        String satuan = b.getSatuanDasar();
                        for(PengirimanDetail d : listPengiriman){
                            if(b.getKodeBarang().equals(d.getKodeBarang())){
                                nilai = ((nilai*qty) + (d.getNilai()*d.getQty())) / (qty + d.getQty());
                                qty = qty + d.getQty();
                                satuan = d.getSatuan();
                            }
                        }
                        for(ReturDetail d : listRetur){
                            if(b.getKodeBarang().equals(d.getKodeBarang())){
                                nilai = ((nilai*qty) - (d.getNilai()*d.getQty())) / (qty - d.getQty());
                                qty = qty - d.getQty();
                            }
                        }
                        if(qty!=0){
                            PengirimanDetail d = new PengirimanDetail();
                            d.setNoPengiriman("");
                            d.setNoUrutPenjualan("");
                            d.setKodeBarang(b.getKodeBarang());
                            d.setNamaBarang(b.getNamaBarang());
                            d.setQty(qty);
                            d.setSatuan(satuan);
                            d.setNilai(nilai);
                            d.setBarang(b);
                            
                            List<Satuan> allSatuan = new ArrayList<>();
                            for(Satuan s : listSatuan){
                                if(d.getKodeBarang().equals(s.getKodeBarang()))
                                    allSatuan.add(s);
                            }
                            d.getBarang().setAllSatuan(allSatuan);
                            
                            listDetail.add(d);
                        }
                    }
                    return listDetail;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                selectBarang(null);
                allPengirimanDetail.clear();
                allPengirimanDetail.addAll(task.get());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private void selectBarang(PengirimanDetail value){
        pengirimanDetail = null;
        kodeBarangField.setText("");
        namaBarangField.setText("");
        satuanField.setText("");
        qtyField.setText("0");
        if(value!=null){
            pengirimanDetail = value;
            kodeBarangField.setText(value.getKodeBarang());
            namaBarangField.setText(value.getNamaBarang());
            satuanField.setText(value.getSatuan());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    } 
}
