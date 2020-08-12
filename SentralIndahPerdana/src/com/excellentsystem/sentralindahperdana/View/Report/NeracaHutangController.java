/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.HutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembayaranHutangDAO;
import com.excellentsystem.sentralindahperdana.DAO.PembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.PembayaranHutang;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.View.Dialog.DetailHutangController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewPembelianController;
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
 * @author excellent
 */
public class NeracaHutangController  {

    @FXML private TreeTableView<Hutang> hutangTable;
    @FXML private TreeTableColumn<Hutang, String> noHutangColumn;
    @FXML private TreeTableColumn<Hutang, String> tglHutangColumn;
    @FXML private TreeTableColumn<Hutang, String> keteranganColumn;
    @FXML private TreeTableColumn<Hutang, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Hutang, Number> jumlahRpColumn;
    @FXML private TreeTableColumn<Hutang, String> kodeUserColumn;
    @FXML private Label saldoAkhirLabel;
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
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().kategoriKeuanganProperty());
        jumlahRpColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahHutangProperty());
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
        hutangTable.setContextMenu(rowMenu);
        hutangTable.setRowFactory(tv -> {
            TreeTableRow<Hutang> row = new TreeTableRow<Hutang>() {
                @Override
                public void updateItem(Hutang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    } else{
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem lihat = new MenuItem("Detail Hutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailHutang(item);
                        });
                        MenuItem lihatPembelian = new MenuItem("Detail Pembelian");
                        lihatPembelian.setOnAction((ActionEvent e)->{
                            showDetailPembelian(item);
                        });
                        MenuItem lihatPemesanan = new MenuItem("Detail Pemesanan");
                        lihatPemesanan.setOnAction((ActionEvent e)->{
                            showDetailPemesanan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rowMenu.getItems().add(print);
                            if(o.getJenis().equals("Detail Hutang")&&o.isStatus()&&!item.getKategori().equals(""))
                                rowMenu.getItems().add(lihat);
                            if(o.getJenis().equals("Detail Pembelian")&&o.isStatus()&&item.getKategori().equals("Hutang Pembelian"))
                                rowMenu.getItems().add(lihatPembelian);
                            if(o.getJenis().equals("Detail Pemesanan")&&o.isStatus()&&
                                    item.getKategori().equals("Terima Pembayaran Down Payment"))
                                rowMenu.getItems().add(lihatPemesanan);
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
    public void setHutang(String tglAkhir, String kategori){
        try(Connection con = Koneksi.getConnection()){
            this.tglAkhir = tglAkhir;
            if(hutangTable.getRoot()!=null)
                hutangTable.getRoot().getChildren().clear();
            
            listHutang = new ArrayList<>();
            List<Hutang> allHutang = HutangDAO.getAllByTanggalAndKategori(con, "2000-01-01", tglAkhir, kategori);
            List<PembayaranHutang> listPembayaran = PembayaranHutangDAO.getAllByTanggalAndStatus(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;

            if(kategori.equals("Hutang Pembelian")){
                List<PembelianHead> listPembelian = PembelianHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir, "true");
                List<Supplier> listSupplier = SupplierDAO.getAllByStatus(con, "true");
                List<String> groupBySupplier = new ArrayList<>();
                for(Hutang h : allHutang){
                    for(PembelianHead pb : listPembelian){
                        if(pb.getNoPembelian().equals(h.getKeterangan()))
                            h.setPembelian(pb);
                    }
                    for(Supplier s : listSupplier){
                        if(h.getPembelian().getKodeSupplier().equals(s.getKodeSupplier()))
                            h.setSupplier(s);
                    }
                    if(!groupBySupplier.contains(h.getSupplier().getNama()))
                        groupBySupplier.add(h.getSupplier().getNama());
                    
                }
                for(String s : groupBySupplier){
                    Hutang supplier = new Hutang();
                    supplier.setNoHutang(s);
                    supplier.setKategori("");
                    supplier.setKeterangan("");
                    TreeItem<Hutang> parent = new TreeItem<>(supplier);
                    double totalSupplier = 0;
                    for(Hutang h : allHutang){
                        if(h.getSupplier().getNama().equals(s)){
                            for(PembayaranHutang p : listPembayaran){
                                if(h.getNoHutang().equals(p.getNoHutang()))
                                    h.setJumlahHutang(h.getJumlahHutang() - p.getJumlahPembayaran());
                            }
                            if(h.getJumlahHutang()>1){
                                listHutang.add(h);
                                totalSupplier = totalSupplier + h.getJumlahHutang();
                                parent.getChildren().add(new TreeItem<>(h));
                            }
                        }
                    }
                    if(totalSupplier>1){
                        parent.getValue().setJumlahHutang(totalSupplier);
                        saldoAkhir = saldoAkhir + totalSupplier;
                        root.getChildren().add(parent);
                    }
                }
            }else if(kategori.equals("Terima Pembayaran Down Payment")){
                List<PenjualanHead> listPemesanan = PenjualanHeadDAO.getAll(con);
                List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                List<String> groupByCustomer = new ArrayList<>();
                for(Hutang h : allHutang){
                    for(PenjualanHead p : listPemesanan){
                        if(h.getKeterangan().equals(p.getNoPenjualan()))
                            h.setPenjualan(p);
                    }
                    for(Customer c : listCustomer){
                        if(h.getPenjualan().getKodeCustomer().equals(c.getKodeCustomer()))
                            h.setCustomer(c);
                    }
                    if(!groupByCustomer.contains(h.getCustomer().getNama()))
                        groupByCustomer.add(h.getCustomer().getNama());
                }
                for(String s : groupByCustomer){
                    Hutang customer = new Hutang();
                    customer.setNoHutang(s);
                    customer.setKategori("");
                    customer.setKeterangan("");
                    TreeItem<Hutang> parent = new TreeItem<>(customer);
                    double totalCustomer = 0;

                    for(Hutang h : allHutang){
                        if(h.getCustomer().getNama().equals(s)){
                            for(PembayaranHutang p : listPembayaran){
                                if(h.getNoHutang().equals(p.getNoHutang()))
                                    h.setJumlahHutang(h.getJumlahHutang()-p.getJumlahPembayaran());
                            }
                            if(h.getJumlahHutang()>1){
                                listHutang.add(h);
                                totalCustomer = totalCustomer + h.getJumlahHutang();
                                parent.getChildren().add(new TreeItem<>(h));
                            }
                        }
                    }
                    if(totalCustomer>1){
                        parent.getValue().setJumlahHutang(totalCustomer);
                        saldoAkhir = saldoAkhir + totalCustomer;
                        root.getChildren().add(parent);
                    }
                }
            }else{
                for(Hutang h : allHutang){
                    for(PembayaranHutang p : listPembayaran){
                        if(h.getNoHutang().equals(p.getNoHutang()))
                            h.setJumlahHutang(h.getJumlahHutang() - p.getJumlahPembayaran());
                    }
                    if(h.getJumlahHutang()>1){
                        listHutang.add(h);
                        root.getChildren().add(new TreeItem<>(h));
                        saldoAkhir = saldoAkhir + h.getJumlahHutang();
                    }
                }
            }
            saldoAkhirLabel.setText(df.format(saldoAkhir));
            hutangTable.setRoot(root);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    private void showDetailPembelian(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,stage, child);
        controller.setDetailPembelian(hutang.getKeterangan());
        controller.saveButton.setVisible(false);
    }
    private void showDetailPemesanan(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualan.fxml");
        NewPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPenjualan(hutang.getKeterangan());
        controller.saveButton.setVisible(false);
    }
    private void showDetailHutang(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailHutang.fxml");
        DetailHutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetail(hutang);
    }
    private void print(){
        try{
            Report report = new Report();
            report.printLaporanNeracaHutang(listHutang, tglAkhir);
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
