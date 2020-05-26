/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KaryawanDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SDPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.SKLDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
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
public class DetailPelunasanDownPaymentController  {
    @FXML private TableView<SKLDetail> SKLDetailtable;
    @FXML private TableColumn<SKLDetail,String> tahapColumn;
    @FXML private TableColumn<SKLDetail,String> tglPembayaranColumn;
    @FXML private TableColumn<SKLDetail,Number> jumlahPembayaranColumn;
    
    @FXML private TextField noSKLField;
    @FXML private TextField tglSKLField;
    
    @FXML private TextField namaPropertyField;
    @FXML private TextField hargaField;
    @FXML private TextField diskonField;
    @FXML private TextField namaCustomerField;
    
    @FXML private TextField totalPembayaranField;
    @FXML private TextField sisaPelunasanField;
    
    @FXML private Button propertyButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SKLHead skl;
    public ObservableList<SKLDetail> allDetail = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        tahapColumn.setCellFactory(col -> Function.getWrapTableCell(tahapColumn));
        
        tglPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().tglBayarProperty()); 
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        Function.setNumberField(totalPembayaranField);
        Function.setNumberField(hargaField);
    }   
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        SKLDetailtable.setItems(allDetail);
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void setNewSKL(){
        skl = new SKLHead();
        noSKLField.setText("-");
        tglSKLField.setText(tglSql.format(new Date()));
    }
    public void getSKL(String noSKL){
        Task<SKLHead> task = new Task<SKLHead>() {
            @Override 
            public SKLHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    SKLHead skl = SKLHeadDAO.get(con, noSKL);
                    skl.setAllDetail(SKLDetailDAO.getAllByNoSkl(con, noSKL));
                    skl.setProperty(PropertyDAO.get(con, skl.getKodeProperty()));
                    skl.setCustomer(CustomerDAO.get(con, skl.getKodeCustomer()));
                    return skl;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                setSKL(task.getValue());
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
    public void setSKL(SKLHead s){
        try{
            propertyButton.setVisible(false);
            saveButton.setVisible(false);
            cancelButton.setVisible(false);
            
            noSKLField.setText(s.getNoSKL());
            tglSKLField.setText(tglLengkap.format(tglSql.parse(s.getTglSKL())));
            namaPropertyField.setText(s.getProperty().getNamaProperty());
            hargaField.setText(rp.format(s.getProperty().getHargaJual()));
            diskonField.setText(rp.format(s.getProperty().getDiskon()));
            namaCustomerField.setText(s.getCustomer().getNama());
            allDetail.addAll(s.getAllDetail());
            totalPembayaranField.setText(rp.format(s.getTotalPembayaran()));
            sisaPelunasanField.setText(rp.format(s.getSisaPelunasan()));
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
        status.add("Reserved");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    Task<List<SDP>> task = new Task<List<SDP>>() {
                        @Override 
                        public List<SDP> call() throws Exception{
                            try (Connection con = Koneksi.getConnection()) {
                                skl.setStj(STJHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true"));
                                skl.setCustomer(CustomerDAO.get(con, skl.getStj().getKodeCustomer()));
                                skl.setSales(KaryawanDAO.get(con, skl.getStj().getKodeSales()));
                                return SDPDAO.getAllByKodeProperty(con, row.getItem().getKodeProperty(), "true");
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
                            skl.setProperty(p);
                            skl.setKodeProperty(p.getKodeProperty());
                            
                            allDetail.clear();
                            double totalPembayaran = 0;
                            skl.setNoSTJ(skl.getStj().getNoSTJ());
                            SKLDetail d = new SKLDetail();
                            d.setTahap("Tanda Jadi (Booking Fee)");
                            d.setTglBayar(tgl.format(tglSql.parse(skl.getStj().getTglSTJ())));
                            d.setJumlahRp(skl.getStj().getJumlahRp());
                            allDetail.add(d);
                            totalPembayaran = totalPembayaran + skl.getStj().getJumlahRp();

                            List<SDP> allSDP = task.getValue();
                            for(SDP sdp : allSDP){
                                SKLDetail detail = new SKLDetail();
                                detail.setTahap("Down Payment Tahap "+sdp.getTahap());
                                if(allSDP.get(allSDP.size()-1).equals(sdp))
                                    detail.setTahap("Pelunasan Down Payment");
                                detail.setTglBayar(tgl.format(tglSql.parse(sdp.getTglSDP())));
                                detail.setJumlahRp(sdp.getJumlahRp());
                                allDetail.add(detail);
                                totalPembayaran= totalPembayaran +sdp.getJumlahRp();
                            }
                            skl.setTotalPembayaran(totalPembayaran);
                            totalPembayaranField.setText(rp.format(totalPembayaran));
                            skl.setSisaPelunasan(p.getHargaJual()-p.getDiskon()-totalPembayaran);
                            sisaPelunasanField.setText(rp.format(skl.getSisaPelunasan()));
                            namaCustomerField.setText(skl.getCustomer().getNama());
                            skl.setKodeCustomer(skl.getCustomer().getKodeCustomer());
                            skl.setKodeSales(skl.getSales().getKodeKaryawan());
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
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
