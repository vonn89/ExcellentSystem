/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.CustomerDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanCoilHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PenjualanHeadDAO;
import com.excellentsystem.AuriSteel.DAO.PiutangDAO;
import com.excellentsystem.AuriSteel.DAO.TerimaPembayaranDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.Customer;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Model.PenjualanCoilHead;
import com.excellentsystem.AuriSteel.Model.PenjualanHead;
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.TerimaPembayaran;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import com.excellentsystem.AuriSteel.View.Dialog.DetailPiutangController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanCoilRpController;
import com.excellentsystem.AuriSteel.View.Dialog.NewPenjualanController;
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
 * @author yunaz
 */
public class NeracaPiutangController  {
    
    @FXML private TreeTableView<Piutang> piutangTable;
    @FXML private TreeTableColumn<Piutang, String> noPiutangColumn;
    @FXML private TreeTableColumn<Piutang, String> tglPiutangColumn;
    @FXML private TreeTableColumn<Piutang, String> keteranganColumn;
    @FXML private TreeTableColumn<Piutang, String> tipeKeuanganColumn;
    @FXML private TreeTableColumn<Piutang, Number> jumlahPiutangColumn;
    @FXML private TreeTableColumn<Piutang, Number> pembayaranColumn;
    @FXML private TreeTableColumn<Piutang, Number> sisaPiutangColumn;
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
        tglPiutangColumn.setComparator(Function.sortDate(tglLengkap));
        keteranganColumn.setCellValueFactory(param -> param.getValue().getValue().keteranganProperty());
        tipeKeuanganColumn.setCellValueFactory(param -> param.getValue().getValue().tipeKeuanganProperty());
        jumlahPiutangColumn.setCellValueFactory(param -> param.getValue().getValue().jumlahPiutangProperty());
        jumlahPiutangColumn.setCellFactory(col -> Function.getTreeTableCell());
        pembayaranColumn.setCellValueFactory(param -> param.getValue().getValue().pembayaranProperty());
        pembayaranColumn.setCellFactory(col -> Function.getTreeTableCell());
        sisaPiutangColumn.setCellValueFactory(param -> param.getValue().getValue().sisaPiutangProperty());
        sisaPiutangColumn.setCellFactory(col -> Function.getTreeTableCell());
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
                        MenuItem print = new MenuItem("Print Laporan");
                        print.setOnAction((ActionEvent e)->{
                            print();
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem lihat = new MenuItem("Detail Piutang");
                        lihat.setOnAction((ActionEvent e)->{
                            showDetailPiutang(item);
                        });
                        MenuItem lihatPenjualan = new MenuItem("Detail Penjualan");
                        lihatPenjualan.setOnAction((ActionEvent e)->{
                            showDetailPenjualan(item);
                        });
                        MenuItem lihatPenjualanCoil = new MenuItem("Detail Penjualan Coil");
                        lihatPenjualanCoil.setOnAction((ActionEvent e)->{
                            showDetailPenjualanCoil(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Detail Piutang")&&o.isStatus()&&!item.getKategori().equals(""))
                                rm.getItems().add(lihat);
                            if(o.getJenis().equals("Detail Penjualan")&&o.isStatus()&&
                                    item.getKategori().equals("Piutang Penjualan")&&
                                    item.getKeterangan().startsWith("PJ"))
                                rm.getItems().add(lihatPenjualan);
                            if(o.getJenis().equals("Detail Penjualan Coil")&&o.isStatus()&&
                                    item.getKategori().equals("Piutang Penjualan")&&
                                    item.getKeterangan().startsWith("PE"))
                                rm.getItems().add(lihatPenjualanCoil);
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
    public void setPiutang(String tglAkhir, String kategori){
        try(Connection con = Koneksi.getConnection()){
            this.tglAkhir = tglAkhir;
            if(piutangTable.getRoot()!=null)
                piutangTable.getRoot().getChildren().clear();
            listPiutang = new ArrayList<>();
            List<Piutang> allPiutang = PiutangDAO.getAllByDateAndKategoriAndStatus(
                    con, "2000-01-01", tglAkhir, kategori, "%");
            List<TerimaPembayaran> listPembayaran = TerimaPembayaranDAO.getAllByTglTerima(
                    con, "2000-01-01", tglAkhir, "true");
            double saldoAkhir = 0;
            if(kategori.equals("Piutang Penjualan")){
                List<PenjualanHead> listPenjualan = PenjualanHeadDAO.getAllByDateAndStatus(con, "2000-01-01", tglAkhir, "true");
                List<PenjualanCoilHead> listPenjualanCoil = PenjualanCoilHeadDAO.getAllByDateAndStatus(
                        con, "2000-01-01", tglAkhir, "true");
                List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "%");
                List<String> groupByCustomer = new ArrayList<>();
                for(Piutang pt : allPiutang){
                    if(pt.getKeterangan().startsWith("PJ")){
                        for(PenjualanHead p : listPenjualan){
                            if(pt.getKeterangan().equals(p.getNoPenjualan()))
                                pt.setPenjualanHead(p);
                        }
                        for(Customer c : listCustomer){
                            if(pt.getPenjualanHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                pt.getPenjualanHead().setCustomer(c);
                        }
                        if(!groupByCustomer.contains(pt.getPenjualanHead().getCustomer().getNama()))
                            groupByCustomer.add(pt.getPenjualanHead().getCustomer().getNama());
                    }else if(pt.getKeterangan().startsWith("PE")){
                        for(PenjualanCoilHead p : listPenjualanCoil){
                            if(pt.getKeterangan().equals(p.getNoPenjualan()))
                                pt.setPenjualanCoilHead(p);
                        }
                        for(Customer c : listCustomer){
                            if(pt.getPenjualanCoilHead().getKodeCustomer().equals(c.getKodeCustomer()))
                                pt.getPenjualanCoilHead().setCustomer(c);
                        }
                        if(!groupByCustomer.contains(pt.getPenjualanCoilHead().getCustomer().getNama()))
                            groupByCustomer.add(pt.getPenjualanCoilHead().getCustomer().getNama());
                    }
                }
                for(String s : groupByCustomer){
                    Piutang customer = new Piutang();
                    customer.setNoPiutang(s);
                    customer.setKategori("");
                    customer.setKeterangan("");
                    TreeItem<Piutang> parent = new TreeItem<>(customer);
                    double totalPiutangCustomer = 0;
                    double totalPembayaranCustomer = 0;
                    double totalSisaPiutangCustomer = 0;
                    for(Piutang p : allPiutang){
                        if(p.getKeterangan().startsWith("PJ")){
                            if(p.getPenjualanHead().getCustomer().getNama().equals(s)){
                                p.setPembayaran(0);
                                p.setSisaPiutang(p.getJumlahPiutang());
                                for(TerimaPembayaran tp : listPembayaran){
                                    if(tp.getNoPiutang().equals(p.getNoPiutang())){
                                        p.setPembayaran(p.getPembayaran() + tp.getJumlahPembayaran());
                                        p.setSisaPiutang(p.getSisaPiutang() - tp.getJumlahPembayaran());
                                    }
                                }
                                if(p.getSisaPiutang()>1){
                                    listPiutang.add(p);
                                    totalPiutangCustomer = totalPiutangCustomer + p.getJumlahPiutang();
                                    totalPembayaranCustomer = totalPembayaranCustomer + p.getPembayaran();
                                    totalSisaPiutangCustomer = totalSisaPiutangCustomer + p.getSisaPiutang();
                                    parent.getChildren().add(new TreeItem<>(p));
                                }
                            }
                        }else if(p.getKeterangan().startsWith("PE")){
                            if(p.getPenjualanCoilHead().getCustomer().getNama().equals(s)){
                                p.setPembayaran(0);
                                p.setSisaPiutang(p.getJumlahPiutang());
                                for(TerimaPembayaran tp : listPembayaran){
                                    if(tp.getNoPiutang().equals(p.getNoPiutang())){
                                        p.setPembayaran(p.getPembayaran() + tp.getJumlahPembayaran());
                                        p.setSisaPiutang(p.getSisaPiutang() - tp.getJumlahPembayaran());
                                    }
                                }
                                if(p.getSisaPiutang()>1){
                                    listPiutang.add(p);
                                    totalPiutangCustomer = totalPiutangCustomer + p.getJumlahPiutang();
                                    totalPembayaranCustomer = totalPembayaranCustomer + p.getPembayaran();
                                    totalSisaPiutangCustomer = totalSisaPiutangCustomer + p.getSisaPiutang();
                                    parent.getChildren().add(new TreeItem<>(p));
                                }
                            }
                        }
                    }
                    if(totalSisaPiutangCustomer>1){
                        parent.getValue().setJumlahPiutang(totalPiutangCustomer);
                        parent.getValue().setPembayaran(totalPembayaranCustomer);
                        parent.getValue().setSisaPiutang(totalSisaPiutangCustomer);
                        saldoAkhir = saldoAkhir + totalSisaPiutangCustomer;
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
            saldoAkhirLabel.setText(df.format(saldoAkhir));
            piutangTable.setRoot(root);
        }catch(Exception e){
            e.printStackTrace();
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
    private void showDetailPenjualanCoil(Piutang piutang){
        try(Connection con = Koneksi.getConnection()){
            PenjualanCoilHead p = PenjualanCoilHeadDAO.get(con, piutang.getKeterangan());
            if(p.getKurs()!=1){
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoil.fxml");
                NewPenjualanCoilController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPenjualan(p.getNoPenjualan());
            }else{
                Stage child = new Stage();
                FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/NewPenjualanCoilRp.fxml");
                NewPenjualanCoilRpController controller = loader.getController();
                controller.setMainApp(mainApp, stage, child);
                controller.setDetailPenjualan(p.getNoPenjualan());
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void showDetailPiutang(Piutang piutang){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/DetailPiutang.fxml");
        DetailPiutangController x = loader.getController();
        x.setMainApp(mainApp,stage, child);
        x.setDetail(piutang.getNoPiutang());
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
                Sheet sheet = workbook.createSheet("Detail Neraca - Piutang");
                int rc = 0;
                int c = 7;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Transaksi"); 
                sheet.getRow(rc).getCell(1).setCellValue("Tanggal");  
                sheet.getRow(rc).getCell(2).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Tipe Keuangan"); 
                sheet.getRow(rc).getCell(4).setCellValue("Jumlah Piutang"); 
                sheet.getRow(rc).getCell(5).setCellValue("Pembayaran");
                sheet.getRow(rc).getCell(6).setCellValue("Sisa Piutang");
                rc++;
                
                double totalPiutang = 0;
                double totalPembayaran = 0;
                double totalSisaPiutang = 0;
                String kategori = listPiutang.get(0).getKategori();
                if(kategori.equals("Piutang Penjualan")){
                    List<String> groupBy = new ArrayList<>();
                    for(Piutang h : listPiutang){
                        if(h.getPenjualanHead()!=null){
                            if(!groupBy.contains(h.getPenjualanHead().getCustomer().getNama()))
                                groupBy.add(h.getPenjualanHead().getCustomer().getNama());
                        }
                        if(h.getPenjualanCoilHead()!=null){
                            if(!groupBy.contains(h.getPenjualanCoilHead().getCustomer().getNama()))
                                groupBy.add(h.getPenjualanCoilHead().getCustomer().getNama());
                        }
                    }
                    for(String s : groupBy){
                        rc++;
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue(s);
                        rc++;
                        double totalPiutangGroup = 0;
                        double totalPembayaranGroup = 0;
                        double totalSisaPiutangGroup = 0;
                        for(Piutang h : listPiutang){
                            if(h.getPenjualanHead()!=null){
                                if(h.getPenjualanHead().getCustomer().getNama().equals(s)){
                                    createRow(workbook, sheet, rc, c, "Detail");
                                    sheet.getRow(rc).getCell(0).setCellValue(h.getNoPiutang());
                                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglPiutang())));
                                    sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                    sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                    sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahPiutang());
                                    sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                    sheet.getRow(rc).getCell(6).setCellValue(h.getSisaPiutang());
                                    rc++;
                                    totalPiutangGroup = totalPiutangGroup + h.getJumlahPiutang();
                                    totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                    totalSisaPiutangGroup = totalSisaPiutangGroup + h.getSisaPiutang();
                                }
                            }
                            if(h.getPenjualanCoilHead()!=null){
                                if(h.getPenjualanCoilHead().getCustomer().getNama().equals(s)){
                                    createRow(workbook, sheet, rc, c, "Detail");
                                    sheet.getRow(rc).getCell(0).setCellValue(h.getNoPiutang());
                                    sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglPiutang())));
                                    sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                                    sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                                    sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahPiutang());
                                    sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                                    sheet.getRow(rc).getCell(6).setCellValue(h.getSisaPiutang());
                                    rc++;
                                    totalPiutangGroup = totalPiutangGroup + h.getJumlahPiutang();
                                    totalPembayaranGroup = totalPembayaranGroup + h.getPembayaran();
                                    totalSisaPiutangGroup = totalSisaPiutangGroup + h.getSisaPiutang();
                                }
                            }
                        }
                        createRow(workbook, sheet, rc, c, "SubHeader");
                        sheet.getRow(rc).getCell(0).setCellValue("Total "+s);
                        sheet.getRow(rc).getCell(4).setCellValue(totalPiutangGroup);
                        sheet.getRow(rc).getCell(5).setCellValue(totalPembayaranGroup);
                        sheet.getRow(rc).getCell(6).setCellValue(totalSisaPiutangGroup);
                        rc++;
                        totalPiutang = totalPiutang + totalPiutangGroup;
                        totalPembayaran = totalPembayaran + totalPembayaranGroup;
                        totalSisaPiutang = totalSisaPiutang + totalSisaPiutangGroup;
                    }
                }else{
                    for(Piutang h : listPiutang){
                        createRow(workbook, sheet, rc, c, "Detail");
                        sheet.getRow(rc).getCell(0).setCellValue(h.getNoPiutang());
                        sheet.getRow(rc).getCell(1).setCellValue(tglLengkap.format(tglSql.parse(h.getTglPiutang())));
                        sheet.getRow(rc).getCell(2).setCellValue(h.getKeterangan());
                        sheet.getRow(rc).getCell(3).setCellValue(h.getTipeKeuangan());
                        sheet.getRow(rc).getCell(4).setCellValue(h.getJumlahPiutang());
                        sheet.getRow(rc).getCell(5).setCellValue(h.getPembayaran());
                        sheet.getRow(rc).getCell(6).setCellValue(h.getSisaPiutang());
                        rc++;
                        totalPiutang = totalPiutang + h.getJumlahPiutang();
                        totalPembayaran = totalPembayaran + h.getPembayaran();
                        totalSisaPiutang = totalSisaPiutang + h.getSisaPiutang();
                    }
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total");
                sheet.getRow(rc).getCell(4).setCellValue(totalPiutang);
                sheet.getRow(rc).getCell(5).setCellValue(totalPembayaran);
                sheet.getRow(rc).getCell(6).setCellValue(totalSisaPiutang);
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
