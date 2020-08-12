/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.PembayaranPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranPiutang;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import java.util.Date;
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
public class DetailPiutangController {

    @FXML private TableView<PembayaranPiutang> pembayaranPiutangTable;
    @FXML private TableColumn<PembayaranPiutang, String> noPembayaranColumn;
    @FXML private TableColumn<PembayaranPiutang, String> tglPembayaranColumn;
    @FXML private TableColumn<PembayaranPiutang, Number> jumlahPembayaranColumn;
    @FXML private TableColumn<PembayaranPiutang, String> tipeKeuanganColumn;
    @FXML private TableColumn<PembayaranPiutang, String> catatanColumn;
    
    @FXML private TextField noPiutangField;
    @FXML private TextField tglPiutangField;
    @FXML private TextField kategoriField;
    @FXML private TextField keteranganField;
    @FXML private TextField jumlahPiutangField;
    @FXML private Label terbayarLabel;
    @FXML private Label sisaPiutangLabel;
    private boolean statusBatal = false;
    private String status = "";
    private ObservableList<PembayaranPiutang> allPembayaran = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    public void initialize() {
        noPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().noPembayaranProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        tglPembayaranColumn.setCellValueFactory(cellData -> { 
            try{
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembayaran())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahPembayaranProperty());
        jumlahPembayaranColumn.setCellFactory(col -> Function.getTableCell());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        pembayaranPiutangTable.setRowFactory((TableView<PembayaranPiutang> tableView) -> {
            final TableRow<PembayaranPiutang> row = new TableRow<PembayaranPiutang>(){
                @Override
                public void updateItem(PembayaranPiutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else{
                        try {
                            final ContextMenu rm = new ContextMenu();
                            MenuItem batal = new MenuItem("Batal Pembayaran");
                            batal.setOnAction((ActionEvent e)->{
                                batalPembayaran(item);
                            });
                            Boolean ok = false;
                            for(String s : Function.getTipeKeuanganByUser()){
                                if(item.getTipeKeuangan().equals(s))
                                    ok = true;
                            }
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(status.equals("Piutang")){
                                    if(o.getJenis().equals("Batal Terima Pembayaran Piutang")
                                            &&o.isStatus()&&ok && statusBatal)
                                        rm.getItems().add(batal);
                                }else if(status.equals("Penjualan")){
                                    if(o.getJenis().equals("Batal Terima Pembayaran Penjualan")
                                            &&o.isStatus()&&ok && statusBatal)
                                        rm.getItems().add(batal);
                                }
                            }
                            setContextMenu(rm);
                        } catch (Exception ex) {
                            mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                        }
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
        pembayaranPiutangTable.setItems(allPembayaran);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }   
    private void batalPembayaran(PembayaranPiutang pembayaran){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal pembayaran "+pembayaran.getNoPembayaran()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call()throws Exception {
                    try (Connection con = Koneksi.getConnection()) {
                        Piutang piutang = PiutangDAO.get(con, pembayaran.getNoPiutang());
                        pembayaran.setTglBatal(tglSql.format(new Date()));
                        pembayaran.setUserBatal(sistem.getUser().getUsername());
                        pembayaran.setStatus("false");
                        pembayaran.setPiutang(piutang);
                        return Service.saveBatalPembayaranPiutang(con, pembayaran);
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
    public void setDetail(Piutang piutang, boolean statusBatal){
        Task<List<PembayaranPiutang>> task = new Task<List<PembayaranPiutang>>() {
            @Override 
            public List<PembayaranPiutang> call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    status = "Piutang";
                    return PembayaranPiutangDAO.getAllByNoPiutangAndStatus(con, piutang.getNoPiutang(), "true");
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try{
                mainApp.closeLoading();
                this.statusBatal = statusBatal;
                piutang.setAllPembayaran(task.get());
                noPiutangField.setText(piutang.getNoPiutang());
                tglPiutangField.setText(tglLengkap.format(tglSql.parse(piutang.getTglPiutang())));
                kategoriField.setText(piutang.getKategori());
                keteranganField.setText(piutang.getKeterangan());
                jumlahPiutangField.setText(df.format(piutang.getJumlahPiutang()));
                terbayarLabel.setText(df.format(piutang.getPembayaran()));
                sisaPiutangLabel.setText(df.format(piutang.getSisaPiutang()));
                allPembayaran.clear();
                allPembayaran.addAll(piutang.getAllPembayaran());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((ex) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void setDetailPenjualan(PenjualanHead p,boolean statusBatal){
        Task<Piutang> task = new Task<Piutang>() {
            @Override 
            public Piutang call()throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    status = "Penjualan";
                    Piutang piutang = PiutangDAO.getByKategoriAndKeterangan(con, "Piutang Penjualan", p.getNoPenjualan());
                    List<PembayaranPiutang> listPembayaran = PembayaranPiutangDAO.getAllByNoPiutangAndStatus(con, piutang.getNoPiutang(), "true");
                    piutang.setAllPembayaran(listPembayaran);
                    return piutang;
                }
            }
        };
        task.setOnRunning((ex) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try{
                mainApp.closeLoading();
                this.statusBatal = statusBatal;
                Piutang piutang = task.get();
                noPiutangField.setText(piutang.getNoPiutang());
                tglPiutangField.setText(tglLengkap.format(tglSql.parse(piutang.getTglPiutang())));
                kategoriField.setText(piutang.getKategori());
                keteranganField.setText(piutang.getKeterangan());
                jumlahPiutangField.setText(df.format(piutang.getJumlahPiutang()));
                terbayarLabel.setText(df.format(piutang.getPembayaran()));
                sisaPiutangLabel.setText(df.format(piutang.getSisaPiutang()));
                allPembayaran.clear();
                allPembayaran.addAll(piutang.getAllPembayaran());
            }catch(Exception ex){
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
