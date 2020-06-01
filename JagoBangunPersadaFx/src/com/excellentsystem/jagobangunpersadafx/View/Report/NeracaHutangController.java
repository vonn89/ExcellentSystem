/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.HutangDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembayaranDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Hutang;
import com.excellentsystem.jagobangunpersadafx.Model.Pembayaran;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailHutangController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaTandaJadiController;
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
 * @author excellent
 */
public class NeracaHutangController  {

    @FXML private TreeTableView<Hutang> hutangTable;
    @FXML private TreeTableColumn<Hutang, String> noHutangColumn;
    @FXML private TreeTableColumn<Hutang, String> tglHutangColumn;
    @FXML private TreeTableColumn<Hutang, String> keteranganColumn;
    @FXML private TreeTableColumn<Hutang, Number> jumlahHutangColumn;
    @FXML private TreeTableColumn<Hutang, Number> pembayaranColumn;
    @FXML private TreeTableColumn<Hutang, Number> sisaHutangColumn;
    
    @FXML private Label totalHutangLabel;
    
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    private List<Hutang> listHutang;
    private String tglAkhir;
    final TreeItem<Hutang> root = new TreeItem<>();
    public void initialize() {
        noHutangColumn.setCellValueFactory(param -> param.getValue().getValue().noHutangProperty());
        tglHutangColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglHutang())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglHutangColumn.setComparator(Function.sortDate(tglLengkap));
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        jumlahHutangColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahHutangProperty());
        jumlahHutangColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        pembayaranColumn.setCellValueFactory(param -> param.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        sisaHutangColumn.setCellValueFactory(param -> param.getValue().getValue().sisaHutangProperty());
        sisaHutangColumn.setCellFactory(col -> Function.getTreeTableCell(rp));
        ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            
        });
        rm.getItems().addAll(refresh);
        hutangTable.setContextMenu(rm);
        hutangTable.setRowFactory(tv -> {
            TreeTableRow<Hutang> row = new TreeTableRow<Hutang>() {
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        ContextMenu rm = new ContextMenu();
                        MenuItem lihat = new MenuItem("Detail Hutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem stj = new MenuItem("Detail Terima Tanda Jadi");
                        stj.setOnAction((ActionEvent e)->{
                            detailTerimaTandaJadi(item.getKeterangan());
                        });
                        MenuItem sdp = new MenuItem("Detail Terima Down Payment");
                        sdp.setOnAction((ActionEvent e)->{
                            detailTerimaDownPayment(item.getKeterangan());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            
                        });
                        rm.getItems().add(lihat);
                        if(item.getKategori().equals("Terima Tanda Jadi"))
                            rm.getItems().add(stj);
                        if(item.getKategori().equals("Terima Down Payment"))
                            rm.getItems().add(sdp);
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
    public void setHutang(String tglAkhir, String kategori){
        try(Connection con = Koneksi.getConnection()){
            this.tglAkhir = tglAkhir;
            if(hutangTable.getRoot()!=null)
                hutangTable.getRoot().getChildren().clear();
            listHutang = new ArrayList<>();
            List<Hutang> allHutang = HutangDAO.getAllByTanggalAndKategoriAndStatus(
                    con, "2000-01-01", tglAkhir, kategori, "%");
            List<Pembayaran> listPembayaran = PembayaranDAO.getAllByDateAndStatus(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;
            if(kategori.equals("Terima Tanda Jadi")){
                List<STJHead> listSTJ = STJHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir,"true");
                for(Hutang h : allHutang){
                    h.setPembayaran(0);
                    h.setSisaHutang(h.getJumlahHutang());
                    for(STJHead stj : listSTJ){
                        if(stj.getNoSTJ().equals(h.getKeterangan()))
                            h.setStj(stj);
                    }
                    for(Pembayaran p : listPembayaran){
                        if(h.getNoHutang().equals(p.getNoHutang())){
                            h.setPembayaran(h.getPembayaran() + p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang()- p.getJumlahPembayaran());
                        }
                    }
                    if(h.getSisaHutang()>1){
                        TreeItem<Hutang> parent = new TreeItem<>(h);
                        saldoAkhir = saldoAkhir + h.getSisaHutang();
                        root.getChildren().add(parent);
                    }
                }
            }else if(kategori.equals("Terima Down Payment")){
                List<SDP> listSDP = SDPDAO.getAllByDateAndStatus(
                        con, "2000-01-01", tglAkhir, "%");
                List<Property> listProperty = PropertyDAO.getAll(con);
                List<String> groupByProperty = new ArrayList<>();
                for(Hutang h : allHutang){
                    for(SDP s : listSDP){
                        if(h.getKeterangan().equals(s.getNoSDP()))
                            h.setSdp(s);
                    }
                    for(Property p : listProperty){
                        if(h.getSdp().getKodeProperty().equals(p.getKodeProperty()))
                            h.getSdp().setProperty(p);
                    }
                    if(!groupByProperty.contains(h.getSdp().getProperty().getNamaProperty()))
                        groupByProperty.add(h.getSdp().getProperty().getNamaProperty());
                }
                for(String s : groupByProperty){
                    Hutang prop = new Hutang();
                    prop.setNoHutang(s);
                    prop.setKategori("");
                    prop.setKeterangan("");
                    TreeItem<Hutang> parent = new TreeItem<>(prop);
                    double totalHutangProperty = 0;
                    double totalPembayaranProperty = 0;
                    double totalSisaHutangProperty = 0;
                    for(Hutang h : allHutang){
                        if(h.getSdp().getProperty().getNamaProperty().equals(s)){
                            h.setPembayaran(0);
                            h.setSisaHutang(h.getJumlahHutang());
                            for(Pembayaran p : listPembayaran){
                                if(h.getNoHutang().equals(p.getNoHutang())){
                                    h.setPembayaran(h.getPembayaran() + p.getJumlahPembayaran());
                                    h.setSisaHutang(h.getSisaHutang()- p.getJumlahPembayaran());
                                }
                            }
                            if(h.getSisaHutang()>1){
                                listHutang.add(h);
                                totalHutangProperty = totalHutangProperty + h.getJumlahHutang();
                                totalPembayaranProperty = totalPembayaranProperty + h.getPembayaran();
                                totalSisaHutangProperty = totalSisaHutangProperty + h.getSisaHutang();
                                parent.getChildren().add(new TreeItem<>(h));
                            }
                        }
                    }
                    if(totalSisaHutangProperty>1){
                        parent.getValue().setJumlahHutang(totalHutangProperty);
                        parent.getValue().setPembayaran(totalPembayaranProperty);
                        parent.getValue().setSisaHutang(totalSisaHutangProperty);
                        saldoAkhir = saldoAkhir + totalSisaHutangProperty;
                        root.getChildren().add(parent);
                    }
                }
            }else{
                for(Hutang h : allHutang){
                    h.setPembayaran(0);
                    h.setSisaHutang(h.getJumlahHutang());
                    for(Pembayaran p : listPembayaran){
                        if(h.getNoHutang().equals(p.getNoHutang())){
                            h.setPembayaran(h.getPembayaran() + p.getJumlahPembayaran());
                            h.setSisaHutang(h.getSisaHutang()- p.getJumlahPembayaran());
                        }
                    }
                    if(h.getSisaHutang()>1){
                        listHutang.add(h);
                        root.getChildren().add(new TreeItem<>(h));
                        saldoAkhir = saldoAkhir + h.getSisaHutang();
                    }
                }
            }
            totalHutangLabel.setText(rp.format(saldoAkhir));
            hutangTable.setRoot(root);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    private void showDetailHutang(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetail(hutang);
    }
    private void detailTerimaTandaJadi(String noSTJ){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTerimaTandaJadi.fxml");
        DetailTerimaTandaJadiController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getSTJ(noSTJ);
    }
    private void detailTerimaDownPayment(String noSDP){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTerimaDownPayment.fxml");
        DetailTerimaDownPaymentController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getSDP(noSDP);
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
