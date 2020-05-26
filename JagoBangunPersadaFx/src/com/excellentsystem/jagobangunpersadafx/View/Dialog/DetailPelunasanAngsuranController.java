/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SAPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import com.excellentsystem.jagobangunpersadafx.Model.SPPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SPPHead;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailPelunasanAngsuranController {
    
    @FXML private TableView<SPPDetail> SPPDetailtable;
    @FXML private TableColumn<SPPDetail,String> tahapColumn;
    @FXML private TableColumn<SPPDetail,String> tglPembayaranColumn;
    @FXML private TableColumn<SPPDetail,Number> jumlahPembayaranColumn;
    
    @FXML private TextField noSPPField;
    @FXML private TextField tglSPPField;
    @FXML private TextField namaPropertyField;
    @FXML private TextField hargaField;
    @FXML private TextField diskonField;
    @FXML private TextField namaCustomerField;
    @FXML private TextField totalDPField;
    @FXML private TextField totalAngsuranField;
    @FXML private Button propertyButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SPPHead spp;
    public ObservableList<SPPDetail> allDetail = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        tahapColumn.setCellFactory(col -> Function.getWrapTableCell(tahapColumn));
        
        tglPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().tglBayarProperty()); 
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
    }   
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        SPPDetailtable.setItems(allDetail);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setNewSPP(){
        spp = new SPPHead();
        noSPPField.setText("-");
        tglSPPField.setText(tglSql.format(new Date()));
    }
    public void setSPP(SPPHead s){
        try{
            propertyButton.setVisible(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            
            noSPPField.setText(s.getNoSPP());
            tglSPPField.setText(tglLengkap.format(tglSql.parse(s.getTglSPP())));
            namaPropertyField.setText(s.getProperty().getNamaProperty());
            hargaField.setText(rp.format(s.getProperty().getHargaJual()));
            diskonField.setText(rp.format(s.getProperty().getDiskon()));
            namaCustomerField.setText(s.getCustomer().getNama());
            allDetail.addAll(s.getAllDetail());
            totalDPField.setText(rp.format(s.getTotalDP()));
            totalAngsuranField.setText(rp.format(s.getTotalAngsuran()));
            stage.setHeight(stage.getHeight()-20);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void setProperty(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddProperty.fxml");
        AddPropertyController x = loader.getController();
        x.setMainApp(mainApp, stage, child);
        List<String> status = new ArrayList<>();
        status.add("Sold");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    Task<List<SAP>> task = new Task<List<SAP>>() {
                        @Override 
                        public List<SAP> call() throws Exception{
                            try (Connection con = Koneksi.getConnection()) {
                                spp.setSkl(SKLHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true"));
                                spp.setCustomer(CustomerDAO.get(con, spp.getSkl().getKodeCustomer()));
                                return SAPDAO.getAllByKodeProperty(con, row.getItem().getKodeProperty(), "true");
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((WorkerStateEvent e) -> {
                        try{
                            mainApp.closeLoading();
                            Property p = row.getItem();
                            namaPropertyField.setText(p.getNamaProperty());
                            hargaField.setText(rp.format(p.getHargaJual()));
                            diskonField.setText(rp.format(p.getDiskon()));
                            spp.setProperty(p);
                            spp.setKodeProperty(p.getKodeProperty());
                            spp.setNoSKL(spp.getSkl().getNoSKL());
                            allDetail.clear();
                            SPPDetail d = new SPPDetail();
                            d.setNoSPP(noSPPField.getText());
                            d.setTahap("Total Pembayaran Down Payment");
                            d.setTglBayar(tgl.format(tglSql.parse(spp.getSkl().getTglSKL())));
                            d.setJumlahRp(spp.getSkl().getTotalPembayaran());
                            allDetail.add(d);
                            spp.setTotalDP(spp.getSkl().getTotalPembayaran());
                            totalDPField.setText(rp.format(spp.getSkl().getTotalPembayaran()));
                            double totalPembayaran = 0;
                            List<SAP> allSAP = task.getValue();
                            for(SAP sap : allSAP){
                                SPPDetail detail = new SPPDetail();
                                detail.setNoSPP(noSPPField.getText());
                                detail.setTahap("Angsuran Tahap "+sap.getTahap());
                                if(allSAP.get(allSAP.size()-1).equals(sap))
                                    detail.setTahap("Pelunasan Angsuran");
                                detail.setTglBayar(tgl.format(tglSql.parse(sap.getTglSAP())));
                                detail.setJumlahRp(sap.getJumlahRp());
                                allDetail.add(detail);
                                totalPembayaran= totalPembayaran +sap.getJumlahRp();
                            }
                            spp.setTotalAngsuran(totalPembayaran);
                            totalAngsuranField.setText(rp.format(spp.getTotalAngsuran()));
                            namaCustomerField.setText(spp.getCustomer().getNama());
                            spp.setKodeCustomer(spp.getCustomer().getKodeCustomer());
                            mainApp.closeDialog(stage, child);
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
            });
            return row;
        });
    }
//    @FXML
//    private void showPreview(){
//        Task<Void> task = new Task<Void>() {
//            @Override 
//            public Void call() throws Exception{
//                spp.setAllDetail(allDetail);
//                JasperDesign jasperDesign = JRXmlLoader.load(Main.class.getResourceAsStream("Printout/SPP.jrxml"));
//                Map parameters = new HashMap<>();
//                DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
//                parameters.put("tglSPP",tgl.format(tglSql.parse(spp.getTglSPP())));
//                JasperDesign subreport = JRXmlLoader.load(Main.class.getResourceAsStream("Printout/SPPDetail.jrxml"));
//                JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport);
//                parameters.put("SubReportParam", jasperSubReport);
//                List<SPPHead> allspp = new ArrayList<>();
//                allspp.add(spp);
//                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allspp);
//                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//                jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
//                return null;
//            }
//        };
//        task.setOnRunning((e) -> {
//            mainApp.showLoadingScreen();
//        });
//        task.setOnSucceeded((WorkerStateEvent e) -> {
//            try{
//                mainApp.closeLoading();
//                maxpage.setText(String.valueOf(jasperPrint.getPages().size()));
//                if(Integer.parseInt(page.getText())>Integer.parseInt(maxpage.getText()))
//                    page.setText("1");
//                imageView.setFitHeight(jasperPrint.getPageHeight() * zoomFactor);
//                imageView.setFitWidth(jasperPrint.getPageWidth() * zoomFactor);
//                BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, Integer.parseInt(page.getText())-1, 2);
//                WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
//                imageView.setImage(SwingFXUtils.toFXImage(image, fxImage));
//            }catch(Exception ex){
//                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
//            }
//        });
//        task.setOnFailed((e) -> {
//            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
//            mainApp.closeLoading();
//        });
//        new Thread(task).start();
//    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
