/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Report;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SAPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import static com.excellentsystem.jagobangunpersadafx.Function.getTreeTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.Karyawan;
import com.excellentsystem.jagobangunpersadafx.Model.Keuangan;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailKaryawanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembangunanController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembelianTanahController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaPencairanKPRController;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
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
        keuanganTable.setRowFactory(ttv -> {
            TreeTableRow<Keuangan> row = new TreeTableRow<Keuangan>()  {
                @Override
                public void updateItem(Keuangan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    } else {
                        if(item.getNoKeuangan().startsWith("KK")){
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem pembelian = new MenuItem("View Detail Pembelian");
                        pembelian.setOnAction((ActionEvent event) -> {
                            Stage child = new Stage();
                            FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPembelianTanah.fxml");
                            DetailPembelianTanahController x = loader.getController();
                            x.setMainApp(mainApp, stage, child);
                            x.getPembelian(item.getProperty());
                        });
                        MenuItem pembangunan = new MenuItem("Detail Pembangunan");
                        pembangunan.setOnAction((ActionEvent event) -> {
                            Stage child = new Stage();
                            FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailPembangunan.fxml");
                            DetailPembangunanController x = loader.getController();
                            x.setMainApp(mainApp, stage, child);
                            x.getPembangunan(item.getDeskripsi().split(" - ")[1]);
                        });
//                        MenuItem tj = new MenuItem("View Detail Surat Tanda Jadi");
//                        tj.setOnAction((ActionEvent event) -> {
//                            DetailTerimaTandaJadiController x = mainApp.showDetailTerimaTandaJadi();
//                            x.getSTJ(item.getDeskripsi().split(" - ")[1]);
//                            x.from = "Laporan Property";
//                        });
//                        MenuItem dp = new MenuItem("View Detail Surat Down Payment");
//                        dp.setOnAction((ActionEvent event) -> {
//                            DetailTerimaDownPaymentController x = mainApp.showDetailTerimaDownPayment();
//                            x.getSDP(item.getDeskripsi().split(" - ")[1]);
//                            x.from = "Laporan Property";
//                        });
                        MenuItem kpr = new MenuItem("View Detail Terima Pencairan KPR");
                        kpr.setOnAction((ActionEvent event) -> {
                            Stage child = new Stage();
                            FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailTerimaPencairanKPR.fxml");
                            DetailTerimaPencairanKPRController x = loader.getController();
                            x.setMainApp(mainApp, stage, child);
                            x.getKPR(item.getProperty());
                        });
//                        MenuItem sap = new MenuItem("View Detail Surat Angsuran Pembayaran");
//                        sap.setOnAction((ActionEvent event) -> {
//                            DetailTerimaAngsuranPembayaranController x = mainApp.showDetailTerimaAngsuranPembayaran();
//                            x.getSAP(item.getDeskripsi().split(" - ")[1]);
//                            x.from = "Laporan Property";
//                        });
                        if(item.getKategori().equals("Pembelian Tanah")||item.getKategori().equals("Batal Pembelian Tanah"))
                            rowMenu.getItems().add(pembelian);
                        else if(item.getKategori().equals("Pembangunan")||item.getKategori().equals("Batal Pembangunan"))
                            rowMenu.getItems().add(pembangunan);
                        else if(item.getKategori().equals("Terima Tanda Jadi")||item.getKategori().equals("Batal Terima Tanda Jadi")){
//                            rowMenu.getItems().add(tj);
                            MenuItem viewCustomer = new MenuItem("View Customer");
                            viewCustomer.setOnAction((ActionEvent event) -> {
                                try{
                                    Stage child = new Stage();
                                    FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailCustomer.fxml");
                                    DetailCustomerController x = loader.getController();
                                    x.setMainApp(mainApp, stage, child);
                                    try(Connection con = Koneksi.getConnection()){
                                        STJHead stj = STJHeadDAO.get(con, item.getDeskripsi().split(" - ")[1]);
                                        Customer c = CustomerDAO.get(con, stj.getKodeCustomer());
                                        x.setCustomer(c);
                                        x.setViewOnly();
                                    }
                                }catch(Exception e){
                                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                }
                            });
                            rowMenu.getItems().add(viewCustomer);
                            MenuItem viewSales = new MenuItem("View Sales");
                            viewSales.setOnAction((ActionEvent event) -> {
                                try{
                                    Stage child = new Stage();
                                    FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailKaryawan.fxml");
                                    DetailKaryawanController x = loader.getController();
                                    x.setMainApp(mainApp, stage, child);
                                    try(Connection con = Koneksi.getConnection()){
                                        STJHead stj = STJHeadDAO.get(con, item.getDeskripsi().split(" - ")[1]);
                                        Karyawan s = KaryawanDAO.get(con, stj.getKodeSales());
                                        x.setKaryawan(s);
                                        x.setViewOnly();
                                    }
                                }catch(Exception e){
                                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                }
                            });
                            rowMenu.getItems().add(viewSales);
                        }
                        else if(item.getKategori().equals("Terima Down Payment")||item.getKategori().equals("Batal Terima Down Payment")){
//                            rowMenu.getItems().add(dp);
                            MenuItem viewCustomer = new MenuItem("View Customer");
                            viewCustomer.setOnAction((ActionEvent event) -> {
                                try{
                                    Stage child = new Stage();
                                    FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailCustomer.fxml");
                                    DetailCustomerController x = loader.getController();
                                    x.setMainApp(mainApp, stage, child);
                                    try(Connection con = Koneksi.getConnection()){
                                        SDP sdp = SDPDAO.get(con, item.getDeskripsi().split(" - ")[1]);
                                        Customer c = CustomerDAO.get(con, sdp.getKodeCustomer());
                                        x.setCustomer(c);
                                        x.setViewOnly();
                                    }
                                }catch(Exception e){
                                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                }
                            });
                            rowMenu.getItems().add(viewCustomer);
                        }
                        else if(item.getKategori().equals("Terima Pencairan KPR")||item.getKategori().equals("Batal Terima Pencairan KPR")){
                            rowMenu.getItems().add(kpr);
                            MenuItem viewCustomer = new MenuItem("View Customer");
                            viewCustomer.setOnAction((ActionEvent event) -> {
                                try{
                                    Stage child = new Stage();
                                    FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailCustomer.fxml");
                                    DetailCustomerController x = loader.getController();
                                    x.setMainApp(mainApp, stage, child);
                                    try(Connection con = Koneksi.getConnection()){
                                        KPR k = KPRDAO.getByKodeProperty(con, item.getProperty().getKodeProperty());
                                        Customer c = CustomerDAO.get(con, k.getKodeCustomer());
                                        x.setCustomer(c);
                                        x.setViewOnly();
                                    }
                                }catch(Exception e){
                                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                }
                            });
                            rowMenu.getItems().add(viewCustomer);
                        }
                        else if(item.getKategori().equals("Terima Angsuran")||item.getKategori().equals("Batal Terima Angsuran")){
//                            rowMenu.getItems().add(sap);
                            MenuItem viewCustomer = new MenuItem("View Customer");
                            viewCustomer.setOnAction((ActionEvent event) -> {
                                try{
                                    Stage child = new Stage();
                                    FXMLLoader loader = mainApp.showDialog(stage, child,  "View/Dialog/DetailCustomer.fxml");
                                    DetailCustomerController x = loader.getController();
                                    x.setMainApp(mainApp, stage, child);
                                    try(Connection con = Koneksi.getConnection()){
                                        SAP s = SAPDAO.get(con, item.getDeskripsi().split(" - ")[1]);
                                        Customer c = CustomerDAO.get(con, s.getKodeCustomer());
                                        x.setCustomer(c);
                                        x.setViewOnly();
                                    }
                                }catch(Exception e){
                                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                                }
                            });
                            rowMenu.getItems().add(viewCustomer);
                        }
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
    } 
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
}