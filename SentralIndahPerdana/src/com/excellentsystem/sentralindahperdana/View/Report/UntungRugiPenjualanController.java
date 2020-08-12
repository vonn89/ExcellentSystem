/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPiutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPenjualanController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class UntungRugiPenjualanController  {
    
    @FXML private TreeTableView<PenjualanHead> penjualanTable;
    @FXML private TreeTableColumn<PenjualanHead, String> noPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanHead, String> tglPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanHead, String> namaCustomerColumn;
    @FXML private TreeTableColumn<PenjualanHead, String> namaProyekColumn;
    @FXML private TreeTableColumn<PenjualanHead, String> lokasiPengerjaanColumn;
    @FXML private TreeTableColumn<PenjualanHead, Number> totalPenjualanColumn;
    @FXML private TreeTableColumn<PenjualanHead, Number> pembayaranColumn;
    @FXML private TreeTableColumn<PenjualanHead, Number> sisaPembayaranColumn;
    
    @FXML private Label totalPenjualanField;
    @FXML private Label totalPembayaranField;
    @FXML private Label sisaPembayaranField;
    private String tglAwal;
    private String tglAkhir;
    final TreeItem<PenjualanHead> root = new TreeItem<>();
    private ObservableList<PenjualanHead> allPenjualan = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage owner;
    private Stage stage;
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().getCustomer().namaProperty());
        namaProyekColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().lokasiPengerjaanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> Function.getTreeTableCell());
        pembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        sisaPembayaranColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().sisaPembayaranProperty());
        sisaPembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan(tglAwal, tglAkhir);
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().addAll(print);
        }
        rowMenu.getItems().addAll(refresh);
        penjualanTable.setContextMenu(rowMenu);
        penjualanTable.setRowFactory((TreeTableView<PenjualanHead> tableView) -> {
            final TreeTableRow<PenjualanHead> row = new TreeTableRow<PenjualanHead>(){
                @Override
                public void updateItem(PenjualanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Penjualan");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailPenjualan(item);
                        });
                        MenuItem pembayaran = new MenuItem("Detail Pembayaran Penjualan");
                        pembayaran.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent event) -> {
                            print();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPenjualan(tglAwal, tglAkhir);
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rm.getItems().addAll(print);
                            if(o.getJenis().equals("Detail Penjualan")&&o.isStatus()
                                    &&item.getStatus()!=null)
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Detail Pembayaran Penjualan")&&o.isStatus()
                                    &&item.getPembayaran()>0&&item.getStatus()!=null)
                                rm.getItems().add(pembayaran);
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
        try{
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
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void getPenjualan(String tglAwal,String tglAkhir){
        Task<List<PenjualanHead>> task = new Task<List<PenjualanHead>>() {
            @Override 
            public List<PenjualanHead> call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAllByTglPenjualan(con, tglAwal, tglAkhir);
                    List<PenjualanDetail> allDetail = PenjualanDetailDAO.getAllByTglPenjualan(con, tglAwal, tglAkhir);
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    for(PenjualanHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                        List<PenjualanDetail> detail = new ArrayList<>();
                        for(PenjualanDetail d: allDetail){
                            if(p.getNoPenjualan().equals(d.getNoPenjualan()))
                                detail.add(d);
                        }
                        p.setAllDetail(detail);
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                this.tglAwal = tglAwal;
                this.tglAkhir = tglAkhir;
                allPenjualan.clear();
                allPenjualan.addAll(task.get());
                setTable();
                hitungTotal();
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
    private void setTable()throws Exception{
        if(penjualanTable.getRoot()!=null)
            penjualanTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(PenjualanHead temp : allPenjualan){
            if(!groupBy.contains(temp.getCustomer().getNama()))
                groupBy.add(temp.getCustomer().getNama());
        }
        for(String temp : groupBy){
            PenjualanHead head = new PenjualanHead();
            head.setNoPenjualan(temp);
            head.setCustomer(new Customer());
            TreeItem<PenjualanHead> parent = new TreeItem<>(head);
            double totalPenjualan = 0;
            double totalPembayaran = 0;
            double sisaPembayaran = 0;
            for(PenjualanHead pj: allPenjualan){
                if(temp.equals(pj.getCustomer().getNama())){
                    totalPenjualan = totalPenjualan + pj.getTotalPenjualan();
                    totalPembayaran = totalPembayaran + pj.getPembayaran();
                    sisaPembayaran = sisaPembayaran + pj.getSisaPembayaran();
                    parent.getChildren().addAll(new TreeItem<>(pj));
                }
            }
            parent.getValue().setTotalPenjualan(totalPenjualan);
            parent.getValue().setPembayaran(totalPembayaran);
            parent.getValue().setSisaPembayaran(sisaPembayaran);
            root.getChildren().add(parent);
        }
        penjualanTable.setRoot(root);
    }   
    private void hitungTotal(){
        double totalPenjualan=0;
        double totalPembayaran=0;
        double sisaPembayaran=0;
        for(PenjualanHead temp : allPenjualan){
            totalPenjualan = totalPenjualan + temp.getTotalPenjualan();
            totalPembayaran = totalPembayaran + temp.getPembayaran();
            sisaPembayaran = sisaPembayaran + temp.getSisaPembayaran();
        }
        totalPenjualanField.setText(df.format(totalPenjualan));
        totalPembayaranField.setText(df.format(totalPembayaran));
        sisaPembayaranField.setText(df.format(sisaPembayaran));
    }
    private void lihatDetailPenjualan(PenjualanHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPenjualan(p.getNoPenjualan());
    }
    private void showDetailPiutang(PenjualanHead p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setDetailPenjualan(p, false);
    }
    private void print(){
        try{
            List<PenjualanDetail> listPenjualanDetail = new ArrayList<>();
            for(PenjualanHead p : allPenjualan){
                for(PenjualanDetail d : p.getAllDetail()){
                    d.setPenjualanHead(p);
                    listPenjualanDetail.add(d);
                }
            }
            Report report = new Report();
            report.printLaporanPenjualan(listPenjualanDetail, tglAwal, tglAkhir);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
