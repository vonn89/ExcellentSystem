/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import static com.excellentsystem.jagobangunpersadafx.Function.getTreeTableCell;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembangunanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembelianTanahController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailRealisasiProyekController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaAngsuranPembayaranController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaDownPaymentController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaPencairanKPRController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaTandaJadiController;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yunaz
 */
public class DetailKeuanganController  {

    @FXML private TreeTableView<Keuangan> keuanganTable;
    @FXML private TreeTableColumn<Keuangan, String> noKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tglKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodePropertyColumn;
    @FXML private TreeTableColumn<Keuangan, String> kategoriColumn;
    @FXML private TreeTableColumn<Keuangan, String> deskripsiColumn;
    @FXML private TreeTableColumn<Keuangan, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Keuangan, String> kodeUserColumn;
    
    @FXML private Label totalLabel;
    
    private ObservableList<Keuangan> allKeuangan = FXCollections.observableArrayList();
    private Main mainApp;   
    private Stage owner;
    private Stage stage;
    final TreeItem<Keuangan> root = new TreeItem<>();
    public void initialize() {
        noKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().noKeuanganProperty());
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().tipeKeuanganProperty());
        kategoriColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriProperty());
        deskripsiColumn.setCellValueFactory(param -> param.getValue().getValue().deskripsiProperty());
        kodePropertyColumn.setCellValueFactory( x -> {
            if(x.getValue().getValue().getKodeProperty().equals(""))
                return null;
            else
                return new SimpleStringProperty(x.getValue().getValue().getProperty().getNamaProperty());
        });
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        tglKeuanganColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getValue().getTglKeuangan())));
            } catch (Exception ex) {
                return null;
            }
        });
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTreeTableCell(rp));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            setTable();
        });
        rm.getItems().addAll(refresh);
        keuanganTable.setContextMenu(rm);
        keuanganTable.setRowFactory(ttv -> {
            TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>()  {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else {
                        if(item.getNoKeuangan().startsWith("KK")){
                            ContextMenu rowMenu = new ContextMenu();
                            MenuItem detailProperty = new MenuItem("Detail Property");
                            detailProperty.setOnAction((ActionEvent event) -> {
                                detailProperty(item.getProperty());
                            });
                            MenuItem pembelian = new MenuItem("Detail Pembelian");
                            pembelian.setOnAction((ActionEvent event) -> {
                                detailPembelian(item.getProperty());
                            });
                            MenuItem pembangunan = new MenuItem("Detail Pembangunan");
                            pembangunan.setOnAction((ActionEvent event) -> {
                                detailPembangunan(item.getDeskripsi().split(" - ")[1]);
                            });
                            MenuItem realisasi = new MenuItem("Detail Realisasi Proyek");
                            realisasi.setOnAction((ActionEvent event) -> {
                                detailRealisasi(item.getDeskripsi().split(" - ")[1], item.getDeskripsi().split(" - ")[2]);
                            });
                            MenuItem tj = new MenuItem("Detail Terima Tanda Jadi");
                            tj.setOnAction((ActionEvent event) -> {
                                detailTerimaTandaJadi(item.getDeskripsi().split(" - ")[1]);
                            });
                            MenuItem dp = new MenuItem("Detail Terima Down Payment");
                            dp.setOnAction((ActionEvent event) -> {
                                detailTerimaDownPayment(item.getDeskripsi().split(" - ")[1]);
                            });
                            MenuItem kpr = new MenuItem("Detail Terima Pencairan KPR");
                            kpr.setOnAction((ActionEvent event) -> {
                                detailTerimaPencairanKPR(item.getProperty());
                            });
                            MenuItem sap = new MenuItem("Detail Terima Angsuran Pembayaran");
                            sap.setOnAction((ActionEvent event) -> {
                                detailTerimaAngsuranPembayaran(item.getDeskripsi().split(" - ")[1]);
                            });
                            
                            if(item.getProperty()!=null)
                                rowMenu.getItems().add(detailProperty);
                            
                            if(item.getKategori().equals("Pembelian Tanah")||item.getKategori().equals("Batal Pembelian Tanah"))
                                rowMenu.getItems().add(pembelian);
                            else if(item.getKategori().equals("Pembangunan")||item.getKategori().equals("Batal Pembangunan"))
                                rowMenu.getItems().add(pembangunan);
                            else if(item.getKategori().equals("Realisasi Proyek")||item.getKategori().equals("Batal Realisasi Proyek"))
                                rowMenu.getItems().add(realisasi);
                            else if(item.getKategori().equals("Terima Tanda Jadi")||item.getKategori().equals("Batal Terima Tanda Jadi"))
                                rowMenu.getItems().add(tj);
                            else if(item.getKategori().equals("Terima Down Payment")||item.getKategori().equals("Batal Terima Down Payment"))
                                rowMenu.getItems().add(dp);
                            else if(item.getKategori().equals("Terima Pencairan KPR")||item.getKategori().equals("Batal Terima Pencairan KPR"))
                                rowMenu.getItems().add(kpr);
                            else if(item.getKategori().equals("Terima Angsuran")||item.getKategori().equals("Batal Terima Angsuran"))
                                rowMenu.getItems().add(sap);
                            setContextMenu(rowMenu);
                        }
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
        stage.setHeight(mainApp.screenSize.getHeight()*80/100);
        stage.setWidth(mainApp.screenSize.getWidth()*80/100);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setKeuangan(List<Keuangan> temp){
        allKeuangan.clear();
        allKeuangan.addAll(temp);
        setTable();
    }
    private void setTable(){
        if(keuanganTable.getRoot()!=null)
            keuanganTable.getRoot().getChildren().clear();
        for(Keuangan keu : allKeuangan){
            Boolean status = true;
            for(TreeItem<Keuangan> temp : root.getChildren()){
                if(temp.getValue().getNoKeuangan().equals(keu.getNoKeuangan()))
                    status = false;
            }
            if(status){
                Boolean havechild = false;
                for(Keuangan k : allKeuangan){
                    if(keu.getNoKeuangan().equals(k.getNoKeuangan()) && !k.equals(keu))
                        havechild = true;
                }
                if(havechild){
                    Keuangan kk = new Keuangan();
                    kk.setNoKeuangan(keu.getNoKeuangan());
                    kk.setTglKeuangan(keu.getTglKeuangan());
                    kk.setTipeKeuangan(keu.getTipeKeuangan());
                    kk.setKategori(keu.getKategori());
                    kk.setKodeProperty("");
                    kk.setDeskripsi(keu.getDeskripsi());
                    kk.setKodeUser(keu.getKodeUser());
                    TreeItem<Keuangan> child = new TreeItem<>(kk);
                    double total = 0;
                    for(Keuangan k : allKeuangan){
                        if(keu.getNoKeuangan().equals(k.getNoKeuangan())){
                            total = total + k.getJumlahRp();
                            child.getChildren().add(new TreeItem<>(k));
                        }
                    }
                    child.getValue().setJumlahRp(total);
                    root.getChildren().add(child);
                }else{
                    root.getChildren().add(new TreeItem<>(keu));
                }
            }
        }
        keuanganTable.setRoot(root);
        hitungTotal();
    } 
    private void hitungTotal(){
        double total = 0;
        for(Keuangan k : allKeuangan){
            total = total + k.getJumlahRp();
        }
        totalLabel.setText(rp.format(total));
    }
    private void detailProperty(Property p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.setProperty(p);
        x.viewOnly();
    }
    private void detailPembelian(Property p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPembelianTanah.fxml");
        DetailPembelianTanahController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getPembelian(p);
    }
    private void detailPembangunan(String noPembangunan){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPembangunan.fxml");
        DetailPembangunanController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getPembangunan(noPembangunan);
    }
    private void detailRealisasi(String noRealisasi, String noUrut){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailRealisasiProyek.fxml");
        DetailRealisasiProyekController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getDetailRealisasi(noRealisasi, noUrut);
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
    private void detailTerimaAngsuranPembayaran(String noSAP){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTerimaAngsuranPembayaran.fxml");
        DetailTerimaAngsuranPembayaranController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getSAP(noSAP);
    }
    private void detailTerimaPencairanKPR(Property p){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailTerimaPencairanKPR.fxml");
        DetailTerimaPencairanKPRController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        x.getKPR(p);
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}
