/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranPiutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PiutangDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranPiutang;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailPiutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPenjualanController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
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
 * @author yunaz
 */
public class NeracaPiutangController  {
    
    @FXML private TreeTableView<Piutang> piutangTable;
    @FXML private TreeTableColumn<Piutang, String> noPiutangColumn;
    @FXML private TreeTableColumn<Piutang, String> tglPiutangColumn;
    @FXML private TreeTableColumn<Piutang, String> keteranganColumn;
    @FXML private TreeTableColumn<Piutang, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Piutang, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Piutang, String> kodeUserColumn;
    @FXML private Label saldoAkhirLabel;
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    private List<Piutang> listPiutang;
    private String tglAkhir;
    final TreeItem<Piutang> root = new TreeItem<>();
    public void initialize() {
        noPiutangColumn.setCellValueFactory(param -> param.getValue().getValue().noPiutangProperty());
        tglPiutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglPiutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriKeuanganProperty());
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahPiutangProperty());
        jumlahRpColumn.setCellFactory(col -> Function.getTreeTableCell());
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rowMenu.getItems().add(print);
        }
        rowMenu.getItems().addAll(refresh);
        piutangTable.setContextMenu(rowMenu);
        piutangTable.setRowFactory(tv -> {
            TreeTableRow<Piutang> row = new TreeTableRow<Piutang>() {
                @Override
                public void updateItem(Piutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem lihat = new MenuItem("Detail Piutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem lihatPenjualan = new MenuItem("Detail Penjualan");
                        lihatPenjualan.setOnAction((ActionEvent e)->{
                            showDetailPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rowMenu.getItems().add(print);
                            if(o.getJenis().equals("Detail Piutang")&&o.isStatus()&&!item.getKategori().equals(""))
                                rowMenu.getItems().add(lihat);
                            if(o.getJenis().equals("Detail Penjualan")&&o.isStatus()&&
                                    item.getKategori().equals("Piutang Penjualan"))
                                rowMenu.getItems().add(lihatPenjualan);
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
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
    public void setPiutang(String tglAkhir, String kategori){
        try(Connection con = Koneksi.getConnection()){
            this.tglAkhir = tglAkhir;
            if(piutangTable.getRoot()!=null)
                piutangTable.getRoot().getChildren().clear();
            
            listPiutang = new ArrayList<>();
            List<Piutang> allPiutang = PiutangDAO.getAllByTanggalAndKategori(con, "2000-01-01", tglAkhir, kategori);
            List<PembayaranPiutang> listPembayaran = PembayaranPiutangDAO.getAllByTanggalAndStatus(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;
            
            if(kategori.equals("Piutang Penjualan")){
                List<PenjualanHead> listPenjualan = PenjualanHeadDAO.getAllByStatus(con, "close");
                List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                List<String> groupByCustomer = new ArrayList<>();
                for(Piutang pt : allPiutang){
                    for(PenjualanHead p : listPenjualan){
                        if(pt.getKeterangan().equals(p.getNoPenjualan()))
                            pt.setPenjualan(p);
                    }
                    for(Customer c : listCustomer){
                        if(pt.getPenjualan().getKodeCustomer().equals(c.getKodeCustomer()))
                            pt.setCustomer(c);
                    }
                    if(!groupByCustomer.contains(pt.getCustomer().getNama()))
                        groupByCustomer.add(pt.getCustomer().getNama());
                }
                for(String s : groupByCustomer){
                    Piutang customer = new Piutang();
                    customer.setNoPiutang(s);
                    customer.setKategori("");
                    customer.setKeterangan("");
                    TreeItem<Piutang> parent = new TreeItem<>(customer);
                    double totalCustomer = 0;
                    for(Piutang pt : allPiutang){
                        if(pt.getCustomer().getNama().equals(s)){
                            for(PembayaranPiutang p : listPembayaran){
                                if(pt.getNoPiutang().equals(p.getNoPiutang()))
                                    pt.setJumlahPiutang(pt.getJumlahPiutang() - p.getJumlahPembayaran());
                            }
                            if(pt.getJumlahPiutang()>1){
                                listPiutang.add(pt);
                                totalCustomer = totalCustomer + pt.getJumlahPiutang();
                                parent.getChildren().add(new TreeItem<>(pt));
                            }
                        }
                    }
                    if(totalCustomer>1){
                        parent.getValue().setJumlahPiutang(totalCustomer);
                        saldoAkhir = saldoAkhir + totalCustomer;
                        root.getChildren().add(parent);
                    }
                }
            }else{
                for(Piutang pt : allPiutang){
                    for(PembayaranPiutang p : listPembayaran){
                        if(pt.getNoPiutang().equals(p.getNoPiutang()))
                            pt.setJumlahPiutang(pt.getJumlahPiutang() - p.getJumlahPembayaran());
                    }
                    if(pt.getJumlahPiutang()>1){
                        listPiutang.add(pt);
                        root.getChildren().add(new TreeItem<>(pt));
                        saldoAkhir = saldoAkhir + pt.getJumlahPiutang();
                    }
                }
            }
            saldoAkhirLabel.setText(df.format(saldoAkhir));
            piutangTable.setRoot(root);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    private void showDetailPenjualan(Piutang piutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp,stage, child);
        controller.setDetailPenjualan(piutang.getKeterangan());
    }
    private void showDetailPiutang(Piutang piutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetail(piutang, false);
    }
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanNeracaPiutang(listPiutang, tglAkhir);
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
