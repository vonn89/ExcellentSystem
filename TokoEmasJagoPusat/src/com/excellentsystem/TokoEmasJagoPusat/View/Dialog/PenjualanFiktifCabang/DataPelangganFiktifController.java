/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanFiktifCabang;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PelangganFiktifDAO;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.timeout;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PelangganFiktif;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class DataPelangganFiktifController  {

    @FXML public TableView<PelangganFiktif> pelangganTable;
    @FXML private TableColumn<PelangganFiktif, String> namaColumn;
    @FXML private TableColumn<PelangganFiktif, String> alamatColumn;
    @FXML private TableColumn<PelangganFiktif, String> noTelpColumn;
    
    @FXML private TextField namaField;
    @FXML private TextField alamatField;
    @FXML private TextField noTelpField;
    @FXML private Label totalPelangganLabel;
    private String kodeCabang = "";
    
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public ObservableList<PelangganFiktif> allPelanggan = FXCollections.observableArrayList();
    public void initialize() {
        namaColumn.setCellValueFactory(cellData -> cellData.getValue().namaProperty());
        alamatColumn.setCellValueFactory(cellData -> cellData.getValue().alamatProperty());
        noTelpColumn.setCellValueFactory(cellData -> cellData.getValue().noTelpProperty());
        ContextMenu menu = new ContextMenu();
        MenuItem impor = new MenuItem("Import Pelanggan Dari Database Cabang");
        impor.setOnAction((ActionEvent) -> {
            importPelanggan();
        });
        MenuItem hapusSemua = new MenuItem("Hapus Semua Pelanggan");
        hapusSemua.setOnAction((ActionEvent) -> {
            deleteAllPelanggan();
        });
        menu.getItems().addAll(hapusSemua, impor);
        pelangganTable.setContextMenu(menu);
        pelangganTable.setRowFactory(t -> {
            TableRow<PelangganFiktif> row = new TableRow<PelangganFiktif>(){
                @Override
                public void updateItem(PelangganFiktif item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        MenuItem hapus = new MenuItem("Hapus Pelanggan");
                        hapus.setOnAction((ActionEvent) -> {
                            deletePelanggan(item);
                        });
                        MenuItem hapusSemua = new MenuItem("Hapus Semua Pelanggan");
                        hapusSemua.setOnAction((ActionEvent) -> {
                            deleteAllPelanggan();
                        });
                        MenuItem impor = new MenuItem("Import Pelanggan Dari Database Cabang");
                        impor.setOnAction((ActionEvent) -> {
                            importPelanggan();
                        });
                        ContextMenu rowMenu = new ContextMenu();
                        rowMenu.getItems().addAll(hapus,hapusSemua, impor);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        pelangganTable.setItems(allPelanggan);
    }    
    public void setMainApp(Main main,Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
    }
    public void setPelanggan(String c, List<PelangganFiktif> listPelanggan){
        this.kodeCabang = c;
        allPelanggan.clear();
        allPelanggan.addAll(listPelanggan);
        totalPelangganLabel.setText("Total Pelanggan : "+rp.format(allPelanggan.size()));
    }
    private void deletePelanggan(PelangganFiktif k){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus pelanggan "+k.getNama()+" ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        PelangganFiktifDAO.delete(con, k);
                        return "true";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPelanggan.remove(k);
                pelangganTable.refresh();
                totalPelangganLabel.setText("Total Pelanggan : "+rp.format(allPelanggan.size()));
                String msg = task.getValue();
                if(msg.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Pelanggan berhasil dihapus");
                else
                    mainApp.showMessage(Modality.NONE, "Failed", msg);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    private void deleteAllPelanggan(){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Hapus semua pelanggan ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        PelangganFiktifDAO.deleteAllFromCabang(con, kodeCabang);
                        return "true";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPelanggan.clear();
                pelangganTable.refresh();
                totalPelangganLabel.setText("Total Pelanggan : "+rp.format(allPelanggan.size()));
                String msg = task.getValue();
                if(msg.equals("true"))
                    mainApp.showMessage(Modality.NONE, "Success", "Pelanggan berhasil dihapus");
                else
                    mainApp.showMessage(Modality.NONE, "Failed", msg);
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    @FXML
    private void savePelanggan(){
        if(namaField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Nama masih kosong");
        else if(alamatField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Alamat masih kosong");
        else{
            Task<PelangganFiktif> task = new Task<PelangganFiktif>() {
                @Override 
                public PelangganFiktif call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection con = KoneksiPusat.getConnection()){
                        PelangganFiktif p = new PelangganFiktif();
                        p.setKodeCabang(kodeCabang);
                        p.setNama(namaField.getText());
                        p.setAlamat(alamatField.getText());
                        p.setNoTelp(noTelpField.getText());
                        PelangganFiktifDAO.insert(con, p);
                        return p;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPelanggan.add(task.getValue());
                pelangganTable.refresh();
                totalPelangganLabel.setText("Total Pelanggan : "+rp.format(allPelanggan.size()));
                namaField.setText("");
                alamatField.setText("");
                noTelpField.setText("");
                mainApp.showMessage(Modality.NONE, "Success", "Pelanggan berhasil disimpan");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void importPelanggan(){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Import pelanggan akan menghapus semua data pelanggan fiktif untuk cabang "+kodeCabang+",\nanda yakin ?");
        controller.OK.setOnAction((ActionEvent ex) -> {
            Task<List<PelangganFiktif>> task = new Task<List<PelangganFiktif>>() {
                @Override 
                public List<PelangganFiktif> call() throws Exception{
                    Thread.sleep(timeout);
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        PelangganFiktifDAO.deleteAllFromCabang(conPusat, kodeCabang);
                        List<PelangganFiktif> listPelanggan = new ArrayList<>();
                        Cabang cabang = null;
                        List<Cabang> listCabang = CabangDAO.getAll(conPusat);
                        for(Cabang c : listCabang){
                            if(c.getKodeCabang().equals(kodeCabang))
                                cabang = c;
                        }
                        try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                            PreparedStatement ps = conCabang.prepareStatement("select nama,alamat,no_telp "
                                    + "from tt_penjualan_head_"+kodeCabang+" group by nama, alamat");
                            ResultSet rs = ps.executeQuery();
                            while(rs.next()){
                                PelangganFiktif p = new PelangganFiktif();
                                p.setKodeCabang(kodeCabang);
                                p.setNama(rs.getString(1));
                                p.setAlamat(rs.getString(2));
                                p.setNoTelp(rs.getString(3));
                                PelangganFiktifDAO.insert(conPusat, p);
                                listPelanggan.add(p);
                            }
                        }
                        return listPelanggan;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPelanggan.clear();
                allPelanggan.addAll(task.getValue());
                pelangganTable.refresh();
                totalPelangganLabel.setText("Total Pelanggan : "+rp.format(allPelanggan.size()));
                mainApp.showMessage(Modality.NONE, "Success", "Pelanggan berhasil diimport dari cabang");
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        });
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
