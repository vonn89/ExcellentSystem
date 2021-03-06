/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.HutangDAO;
import com.excellentsystem.AuriSteel.DAO.PembayaranDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PembelianBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBahanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PemesananBarangHeadDAO;
import com.excellentsystem.AuriSteel.DAO.SupplierDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.Pembayaran;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PembelianBarangHead;
import com.excellentsystem.AuriSteel.Model.PemesananBahanHead;
import com.excellentsystem.AuriSteel.Model.PemesananBarangHead;
import com.excellentsystem.AuriSteel.Model.Supplier;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailHutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPembelianController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananCoilRpController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPemesananController;
import java.io.File;
import java.io.FileOutputStream;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
    @FXML private TreeTableColumn<Hutang, Number> jumlahHutangColumn;
    @FXML private TreeTableColumn<Hutang, Number> pembayaranColumn;
    @FXML private TreeTableColumn<Hutang, Number> sisaHutangColumn;
    @FXML private TreeTableColumn<Hutang, String> kodeUserColumn;
    
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
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().tipeKeuanganProperty());
        jumlahHutangColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahHutangProperty());
        jumlahHutangColumn.setCellFactory(col -> Function.getTreeTableCell());
        pembayaranColumn.setCellValueFactory(param -> param.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        sisaHutangColumn.setCellValueFactory(param -> param.getValue().getValue().sisaHutangProperty());
        sisaHutangColumn.setCellFactory(col -> Function.getTreeTableCell());
        kodeUserColumn.setCellValueFactory(param -> param.getValue().getValue().kodeUserProperty());
        ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent e)->{
            print();
        });
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().addAll(export);
        }
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
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
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
                        MenuItem lihatPemesananCoil = new MenuItem("Detail Pemesanan Coil");
                        lihatPemesananCoil.setOnAction((ActionEvent e)->{
                            showDetailPemesananCoil(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Hutang")&&o.isStatus()&&!item.getKategori().equals(""))
                                rm.getItems().add(lihat);
                            if(o.getJenis().equals("Detail Pembelian")&&o.isStatus()&&item.getKategori().equals("Hutang Pembelian"))
                                rm.getItems().add(lihatPembelian);
                            if(o.getJenis().equals("Detail Pemesanan")&&o.isStatus()&&
                                    item.getKategori().equals("Terima Pembayaran Down Payment")&&
                                    item.getKeterangan().startsWith("PI-"))
                                rm.getItems().add(lihatPemesanan);
                            if(o.getJenis().equals("Detail Pemesanan Coil")&&o.isStatus()&&
                                    item.getKategori().equals("Terima Pembayaran Down Payment")&&
                                    item.getKeterangan().startsWith("PC-"))
                                rm.getItems().add(lihatPemesananCoil);
                            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                                rm.getItems().addAll(print);
                            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                rm.getItems().addAll(export);
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
            List<Pembayaran> listPembayaran = PembayaranDAO.getAllByTglBayar(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;
            if(kategori.equals("Hutang Pembelian")){
                List<PembelianBahanHead> listPembelian = PembelianBahanHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir,"true");
                List<PembelianBarangHead> listPembelianBarang = PembelianBarangHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir, "true");
                List<Supplier> listSupplier = SupplierDAO.getAllByStatus(con, "%");
                List<String> groupBySupplier = new ArrayList<>();
                for(Hutang h : allHutang){
                    for(PembelianBahanHead pb : listPembelian){
                        if(pb.getNoPembelian().equals(h.getKeterangan()))
                            h.setPembelianBahanHead(pb);
                    }
                    for(PembelianBarangHead pb : listPembelianBarang){
                        if(pb.getNoPembelian().equals(h.getKeterangan()))
                            h.setPembelianBarangHead(pb);
                    }
                    if(h.getPembelianBahanHead()!=null){
                        for(Supplier s : listSupplier){
                            if(h.getPembelianBahanHead().getKodeSupplier().equals(s.getKodeSupplier()))
                                h.getPembelianBahanHead().setSupplier(s);
                        }
                        if(!groupBySupplier.contains(h.getPembelianBahanHead().getSupplier().getNama()))
                            groupBySupplier.add(h.getPembelianBahanHead().getSupplier().getNama());
                    }
                    if(h.getPembelianBarangHead()!=null){
                        for(Supplier s : listSupplier){
                            if(h.getPembelianBarangHead().getKodeSupplier().equals(s.getKodeSupplier()))
                                h.getPembelianBarangHead().setSupplier(s);
                        }
                        if(!groupBySupplier.contains(h.getPembelianBarangHead().getSupplier().getNama()))
                            groupBySupplier.add(h.getPembelianBarangHead().getSupplier().getNama());
                    }
                }
                for(String s : groupBySupplier){
                    Hutang supplier = new Hutang();
                    supplier.setNoHutang(s);
                    supplier.setKategori("");
                    supplier.setKeterangan("");
                    TreeItem<Hutang> parent = new TreeItem<>(supplier);
                    double totalHutangSupplier = 0;
                    double totalPembayaranSupplier = 0;
                    double totalSisaHutangSupplier = 0;
                    for(Hutang h : allHutang){
                        if(h.getPembelianBahanHead()!=null){
                            if(h.getPembelianBahanHead().getSupplier().getNama().equals(s)){
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
                                    totalHutangSupplier = totalHutangSupplier + h.getJumlahHutang();
                                    totalPembayaranSupplier = totalPembayaranSupplier + h.getPembayaran();
                                    totalSisaHutangSupplier = totalSisaHutangSupplier + h.getSisaHutang();
                                    parent.getChildren().add(new TreeItem<>(h));
                                }
                            }
                        }
                        if(h.getPembelianBarangHead()!=null){
                            if(h.getPembelianBarangHead().getSupplier().getNama().equals(s)){
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
                                    totalHutangSupplier = totalHutangSupplier + h.getJumlahHutang();
                                    totalPembayaranSupplier = totalPembayaranSupplier + h.getPembayaran();
                                    totalSisaHutangSupplier = totalSisaHutangSupplier + h.getSisaHutang();
                                    parent.getChildren().add(new TreeItem<>(h));
                                }
                            }
                        }
                    }
                    if(totalSisaHutangSupplier>1){
                        parent.getValue().setJumlahHutang(totalHutangSupplier);
                        parent.getValue().setPembayaran(totalPembayaranSupplier);
                        parent.getValue().setSisaHutang(totalSisaHutangSupplier);
                        saldoAkhir = saldoAkhir + totalSisaHutangSupplier;
                        root.getChildren().add(parent);
                    }
                }
            }else if(kategori.equals("Terima Pembayaran Down Payment")){
                List<PemesananBarangHead> listPemesanan = PemesananBarangHeadDAO.getAllByDateAndStatus(
                        con, "2000-01-01", tglAkhir, "%");
                List<PemesananBahanHead> listPemesananCoil = PemesananBahanHeadDAO.getAllByDateAndStatus(
                        con, "2000-01-01", tglAkhir, "%");
                List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "%");
                List<String> groupByCustomer = new ArrayList<>();
                for(Hutang h : allHutang){
                    if(h.getKeterangan().startsWith("PI")){
                        for(PemesananBarangHead p : listPemesanan){
                            if(h.getKeterangan().equals(p.getNoPemesanan()))
                                h.setPemesananHead(p);
                        }
                        for(Customer c : listCustomer){
                            if(h.getPemesananHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                h.getPemesananHead().setCustomer(c);
                        }
                        if(!groupByCustomer.contains(h.getPemesananHead().getCustomer().getNama()))
                            groupByCustomer.add(h.getPemesananHead().getCustomer().getNama());
                    }else if(h.getKeterangan().startsWith("PC")){
                        for(PemesananBahanHead p : listPemesananCoil){
                            if(h.getKeterangan().equals(p.getNoPemesanan()))
                                h.setPemesananBahanHead(p);
                        }
                        for(Customer c : listCustomer){
                            if(h.getPemesananBahanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                h.getPemesananBahanHead().setCustomer(c);
                        }
                        if(!groupByCustomer.contains(h.getPemesananBahanHead().getCustomer().getNama()))
                            groupByCustomer.add(h.getPemesananBahanHead().getCustomer().getNama());
                    }
                }
                for(String s : groupByCustomer){
                    Hutang customer = new Hutang();
                    customer.setNoHutang(s);
                    customer.setKategori("");
                    customer.setKeterangan("");
                    TreeItem<Hutang> parent = new TreeItem<>(customer);
                    double totalHutangCustomer = 0;
                    double totalPembayaranCustomer = 0;
                    double totalSisaHutangCustomer = 0;
                    for(Hutang h : allHutang){
                        if(h.getKeterangan().startsWith("PI")){
                            if(h.getPemesananHead().getCustomer().getNama().equals(s)){
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
                                    totalHutangCustomer = totalHutangCustomer + h.getJumlahHutang();
                                    totalPembayaranCustomer = totalPembayaranCustomer + h.getPembayaran();
                                    totalSisaHutangCustomer = totalSisaHutangCustomer + h.getSisaHutang();
                                    parent.getChildren().add(new TreeItem<>(h));
                                }
                            }
                        }else if(h.getKeterangan().startsWith("PC")){
                            if(h.getPemesananBahanHead().getCustomer().getNama().equals(s)){
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
                                    totalHutangCustomer = totalHutangCustomer + h.getJumlahHutang();
                                    totalPembayaranCustomer = totalPembayaranCustomer + h.getPembayaran();
                                    totalSisaHutangCustomer = totalSisaHutangCustomer + h.getSisaHutang();
                                    parent.getChildren().add(new TreeItem<>(h));
                                }
                            }
                        }
                    }
                    if(totalSisaHutangCustomer>1){
                        parent.getValue().setJumlahHutang(totalHutangCustomer);
                        parent.getValue().setPembayaran(totalPembayaranCustomer);
                        parent.getValue().setSisaHutang(totalSisaHutangCustomer);
                        saldoAkhir = saldoAkhir + totalSisaHutangCustomer;
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
            totalHutangLabel.setText(df.format(saldoAkhir));
            hutangTable.setRoot(root);
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    private void showDetailPembelian(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPembelian.fxml");
        NewPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,stage, child);
        controller.setDetailPembelian(hutang.getKeterangan());
    }
    private void showDetailPemesanan(Hutang hutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPemesanan.fxml");
        NewPemesananController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setDetailPemesanan(hutang.getKeterangan());
    }
    private void showDetailPemesananCoil(Hutang hutang){
        try(Connection con = Koneksi.getConnection()){
            PemesananBahanHead p = PemesananBahanHeadDAO.get(con, hutang.getKeterangan());
            if(p.getKurs()!=1){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPemesananCoil.fxml");
                NewPemesananCoilController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPemesanan(p.getNoPemesanan());
            }else{
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPemesananCoilRp.fxml");
                NewPemesananCoilRpController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPemesanan(p.getNoPemesanan());
            }
        }catch(Exception e){
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
    private void exportExcel(){
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Sheet sheet = workbook.createSheet("Detail Neraca - Hutang");
                int rc = 0;
                int c = 7;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Transaksi"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tanggal");  
                sheet.getRow(rc).getCell(2).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Tipe Keuangan"); 
                sheet.getRow(rc).getCell(4).setCellValue("Jumlah Hutang"); 
                sheet.getRow(rc).getCell(5).setCellValue("Pembayaran");
                sheet.getRow(rc).getCell(6).setCellValue("Sisa Hutang");
                rc++;
                
                double totalHutang = 0;
                double totalPembayaran = 0;
                double totalSisaHutang = 0;
                String kategori = listHutang.get(0).getKategori();
                if(kategori.equals("Hutang Pembelian")){
                    List<String> groupBy = new ArrayList<>();
                    for(Hutang h : listHutang){
                        if(h.getPembelianBahanHead()!=null)
                            if(!groupBy.contains(h.getPembelianBahanHead().getSupplier().getNama()))
                                groupBy.add(h.getPembelianBahanHead().getSupplier().getNama());
                        if(h.getPembelianBarangHead()!=null)
                            if(!groupBy.contains(h.getPembelianBarangHead().getSupplier().getNama()))
                                groupBy.add(h.getPembelianBarangHead().getSupplier().getNama());
                    }
                    for(String s : groupBy){
                        rc++;
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue(s);
                        rc++;
                        double totalHutangGroup = 0;
                        double totalPembayaranGroup = 0;
                        double totalSisaHutangGroup = 0;
                        for(Hutang h : listHutang){
                            if(h.getPembelianBahanHead()!=null && h.getPembelianBahanHead().getSupplier().getNama().equals(s)){
                                createRow(workbook, sheet, rc, c, "Detail");
                                sheet.getRow(rc).getCell(0).setCellValue(h.getNoHutang());
                                sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                                sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahHutang());
                                sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                sheet.getRow(rc).getCell(6).setCellValue(h.getSisaHutang());
                                rc++;
                                totalHutangGroup = totalHutangGroup + h.getJumlahHutang();
                                totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                totalSisaHutangGroup = totalSisaHutangGroup + h.getSisaHutang();
                            }
                            if(h.getPembelianBarangHead()!=null && h.getPembelianBarangHead().getSupplier().getNama().equals(s)){
                                createRow(workbook, sheet, rc, c, "Detail");
                                sheet.getRow(rc).getCell(0).setCellValue(h.getNoHutang());
                                sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                                sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahHutang());
                                sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                sheet.getRow(rc).getCell(6).setCellValue(h.getSisaHutang());
                                rc++;
                                totalHutangGroup = totalHutangGroup + h.getJumlahHutang();
                                totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                totalSisaHutangGroup = totalSisaHutangGroup + h.getSisaHutang();
                            }
                        }
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue("Total "+s);
                        sheet.getRow(rc).getCell(4).setCellValue(totalHutangGroup);
                        sheet.getRow(rc).getCell(5).setCellValue(totalPembayaranGroup);
                        sheet.getRow(rc).getCell(6).setCellValue(totalSisaHutangGroup);
                        rc++;
                        totalHutang = totalHutang + totalHutangGroup;
                        totalPembayaran = totalPembayaran + totalPembayaranGroup;
                        totalSisaHutang = totalSisaHutang + totalSisaHutangGroup;
                    }
                }else if(kategori.equals("Terima Pembayaran Down Payment")){
                    List<String> groupBy = new ArrayList<>();
                    for(Hutang h : listHutang){
                        if(h.getPemesananHead()!=null)
                            if(!groupBy.contains(h.getPemesananHead().getCustomer().getNama()))
                                groupBy.add(h.getPemesananHead().getCustomer().getNama());
                        if(h.getPemesananBahanHead()!=null)
                            if(!groupBy.contains(h.getPemesananBahanHead().getCustomer().getNama()))
                                groupBy.add(h.getPemesananBahanHead().getCustomer().getNama());
                    }
                    for(String s : groupBy){
                        rc++;
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue(s);
                        rc++;
                        double totalHutangGroup = 0;
                        double totalPembayaranGroup = 0;
                        double totalSisaHutangGroup = 0;
                        for(Hutang h : listHutang){
                            if(h.getPemesananHead()!=null && h.getPemesananHead().getCustomer().getNama().equals(s)){
                                createRow(workbook, sheet, rc, c, "Detail");
                                sheet.getRow(rc).getCell(0).setCellValue(h.getNoHutang());
                                sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                                sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahHutang());
                                sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                sheet.getRow(rc).getCell(6).setCellValue(h.getSisaHutang());
                                rc++;
                                totalHutangGroup = totalHutangGroup + h.getJumlahHutang();
                                totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                totalSisaHutangGroup = totalSisaHutangGroup + h.getSisaHutang();
                            }
                            if(h.getPemesananBahanHead()!=null && h.getPemesananBahanHead().getCustomer().getNama().equals(s)){
                                createRow(workbook, sheet, rc, c, "Detail");
                                sheet.getRow(rc).getCell(0).setCellValue(h.getNoHutang());
                                sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                                sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahHutang());
                                sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                sheet.getRow(rc).getCell(6).setCellValue(h.getSisaHutang());
                                rc++;
                                totalHutangGroup = totalHutangGroup + h.getJumlahHutang();
                                totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                totalSisaHutangGroup = totalSisaHutangGroup + h.getSisaHutang();
                            }
                        }
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue("Total "+s);
                        sheet.getRow(rc).getCell(4).setCellValue(totalHutangGroup);
                        sheet.getRow(rc).getCell(5).setCellValue(totalPembayaranGroup);
                        sheet.getRow(rc).getCell(6).setCellValue(totalSisaHutangGroup);
                        rc++;
                        totalHutang = totalHutang + totalHutangGroup;
                        totalPembayaran = totalPembayaran + totalPembayaranGroup;
                        totalSisaHutang = totalSisaHutang + totalSisaHutangGroup;
                    }
                }else{
                    for(Hutang h : listHutang){
                        createRow(workbook, sheet, rc, c, "Detail");
                        sheet.getRow(rc).getCell(0).setCellValue(h.getNoHutang());
                        sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglHutang())));
                        sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                        sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                        sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahHutang());
                        sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                        sheet.getRow(rc).getCell(6).setCellValue(h.getSisaHutang());
                        rc++;
                        totalHutang = totalHutang + h.getJumlahHutang();
                        totalPembayaran = totalPembayaran + h.getPembayaran();
                        totalSisaHutang = totalSisaHutang + h.getSisaHutang();
                    }
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(4).setCellValue(totalHutang);
                sheet.getRow(rc).getCell(5).setCellValue(totalPembayaran);
                sheet.getRow(rc).getCell(6).setCellValue(totalSisaHutang);
                rc++;
                for(int i=0 ; i<c ; i++){ sheet.autoSizeColumn(i);}
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
    
}
