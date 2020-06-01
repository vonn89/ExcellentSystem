/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.PiutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.TerimaPembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Piutang;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import com.excellentsystem.jagobangunpersadafx.Model.TerimaPembayaran;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPelunasanDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPiutangController;
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
    @FXML private TreeTableColumn<Piutang, Number> jumlahPiutangColumn;
    @FXML private TreeTableColumn<Piutang, Number> pembayaranColumn;
    @FXML private TreeTableColumn<Piutang, Number> sisaPiutangColumn;
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
        tglPiutangColumn.setComparator(Function.sortDate(tglLengkap));
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        jumlahPiutangColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahPiutangProperty());
        jumlahPiutangColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        pembayaranColumn.setCellValueFactory(param -> param.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        sisaPiutangColumn.setCellValueFactory(param -> param.getValue().getValue().sisaPiutangProperty());
        sisaPiutangColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        
        ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
        });
        rm.getItems().addAll(refresh);
        piutangTable.setContextMenu(rm);
        piutangTable.setRowFactory(tv -> {
            TreeTableRow<Piutang> row = new TreeTableRow<Piutang>() {
                @Override
                public void updateItem(Piutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        ContextMenu rm = new ContextMenu();
                        MenuItem lihat = new MenuItem("Detail Piutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem lihatPenjualan = new MenuItem("Detail Pelunasan Down Payment");
                        lihatPenjualan.setOnAction((ActionEvent e)->{
                            detailPelunasanDownPayment(item.getKeterangan());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        rm.getItems().add(lihat);
                        if(item.getKategori().equals("Piutang Penjualan"))
                            rm.getItems().add(lihatPenjualan);
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            return row;
        });
    }    
    public void setMainApp(Main mainApp, Stage owner,Stage stage){
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
    public void setPiutang(String tglAkhir, String kategori){
        try(Connection con = Koneksi.getConnection()){
            this.tglAkhir = tglAkhir;
            if(piutangTable.getRoot()!=null)
                piutangTable.getRoot().getChildren().clear();
            listPiutang = new ArrayList<>();
            List<Piutang> allPiutang = PiutangDAO.getAllByDateAndKategoriAndStatus(
                    con, "2000-01-01", tglAkhir, kategori, "%");
            List<TerimaPembayaran> listPembayaran = TerimaPembayaranDAO.getAllByDateAndStatus(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;
            if(kategori.equals("Piutang Penjualan")){
                List<SKLHead> listSKL = SKLHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir, "true");
                for(Piutang pt : allPiutang){
                    pt.setPembayaran(0);
                    pt.setSisaPiutang(pt.getJumlahPiutang());
                    for(SKLHead p : listSKL){
                        if(pt.getKeterangan().equals(p.getNoSKL()))
                            pt.setSklHead(p);
                    }
                    for(TerimaPembayaran p : listPembayaran){
                        if(pt.getNoPiutang().equals(p.getNoPiutang())){
                            pt.setPembayaran(pt.getPembayaran() + p.getJumlahPembayaran());
                            pt.setSisaPiutang(pt.getSisaPiutang()- p.getJumlahPembayaran());
                        }
                    }
                    if(pt.getSisaPiutang()>1){
                        TreeItem<Piutang> parent = new TreeItem<>(pt);
                        saldoAkhir = saldoAkhir + pt.getSisaPiutang();
                        root.getChildren().add(parent);
                    }
                }
            }else{
                for(Piutang pt : allPiutang){
                    pt.setPembayaran(0);
                    pt.setSisaPiutang(pt.getJumlahPiutang());
                    for(TerimaPembayaran p : listPembayaran){
                        if(pt.getNoPiutang().equals(p.getNoPiutang())){
                            pt.setPembayaran(pt.getPembayaran() + p.getJumlahPembayaran());
                            pt.setSisaPiutang(pt.getSisaPiutang() - p.getJumlahPembayaran());
                        }
                    }
                    if(pt.getSisaPiutang()>1){
                        listPiutang.add(pt);
                        root.getChildren().add(new TreeItem<>(pt));
                        saldoAkhir = saldoAkhir + pt.getSisaPiutang();
                    }
                }
            }
            saldoAkhirLabel.setText(rp.format(saldoAkhir));
            piutangTable.setRoot(root);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    private void detailPelunasanDownPayment(String noSKL){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPelunasanDownPayment.fxml");
        DetailPelunasanDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getSKL(noSKL);
    }
    private void showDetailPiutang(Piutang piutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetail(piutang.getNoPiutang());
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
