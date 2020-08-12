/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * @author yunaz
 */
public class DetailPembayaranDownPaymentController {

    @FXML private TableView<Hutang> hutangTable;
    @FXML private TableColumn<Hutang, String> noHutangColumn;
    @FXML private TableColumn<Hutang, String> tglHutangColumn;
    @FXML private TableColumn<Hutang, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<Hutang, String> tipeKeuanganColumn;
    
    @FXML private TextField noPemesananField;
    @FXML private TextField tglPemesananField;
    @FXML private TextField totalPemesananField;
    @FXML private Label totalDownPaymentLabel;
    @FXML private Label sisaPembayaranLabel;
    
    private ObservableList<Hutang> listHutang = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noHutangColumn.setCellValueFactory(cellData -> cellData.getValue().noHutangProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriKeuanganProperty());
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahHutangProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        hutangTable.setRowFactory((TableView<Hutang> tableView) -> {
            final TableRow<Hutang> row = new TableRow<Hutang>(){
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem batal = new MenuItem("Batal Pembayaran");
                        batal.setOnAction((ActionEvent e)->{
                            batalPembayaran(item);
                        });
                        boolean status = false;
                        for(String s : Function.getTipeKeuanganByUser()){
                            if(item.getKategoriKeuangan().equals(s))
                                status = true;
                        }
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Batal Terima Pembayaran Down Payment")
                                    &&o.isStatus()&&status)
                                rm.getItems().add(batal);
                        }
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
        hutangTable.setItems(listHutang);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }      
    private void batalPembayaran(Hutang h){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+h.getNoHutang()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        return Service.saveBatalTerimaDownPayment(con, h);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                try{
                    mainApp.closeLoading();
                    if(task.get().equals("true")){
                        mainApp.closeDialog(owner, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Data pembayaran berhasil dibatal");
                        mainApp.showPemesanan();
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.get());
                    }
                }catch(Exception exc){
                    mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    public void setDetailPenjualan(PenjualanHead p){
        Task<List<Hutang>> task = new Task<List<Hutang>>() {
            @Override 
            public List<Hutang> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    return HutangDAO.getAllByKategoriAndKeterangan(
                            con, "Terima Pembayaran Down Payment", p.getNoPenjualan());
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try {
                mainApp.closeLoading();
                p.setAllPembayaran(task.get());
                for(Hutang h : p.getAllPembayaran()){
                    h.setPenjualan(p);
                }
                noPemesananField.setText(p.getNoPenjualan());
                tglPemesananField.setText(tglLengkap.format(tglSql.parse(p.getTglPenjualan())));
                totalPemesananField.setText(df.format(p.getTotalPenjualan()));
                totalDownPaymentLabel.setText(df.format(p.getPembayaran()));
                sisaPembayaranLabel.setText(df.format(p.getSisaPembayaran()));
                listHutang.clear();
                listHutang.addAll(p.getAllPembayaran());
            } catch (Exception ex) {
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close() {
        mainApp.closeDialog(owner, stage);
    }
    
}
