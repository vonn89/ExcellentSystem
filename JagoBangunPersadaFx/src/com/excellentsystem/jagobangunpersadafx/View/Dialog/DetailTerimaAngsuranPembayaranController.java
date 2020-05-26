/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SAPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class DetailTerimaAngsuranPembayaranController {

    @FXML private TableView<SAP> SAPtable;
    @FXML private TableColumn<SAP,Number> tahapColumn;
    @FXML private TableColumn<SAP,String> tglPembayaranColumn;
    @FXML private TableColumn<SAP,Number> jumlahPembayaranColumn;
    @FXML private TextField noSAPField;
    @FXML private TextField tglSAPField;
    @FXML private TextField namaPropertyField;
    @FXML private TextField hargaField;
    @FXML private TextField diskonField;
    @FXML private TextField namaCustomerField;
    @FXML private TextField tahapField;
    @FXML public TextField jumlahAngsuranField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML private Label tahapNextLabel;
    @FXML public DatePicker tglJatuhTempo;
    @FXML private Button propertyButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label abaikanLabel;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SAP sap;
    private ObservableList<SAP> allSAP = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        
        tglPembayaranColumn.setCellValueFactory(cellData ->{
            try {
                return new SimpleStringProperty(tgl.format(tglSql.parse(cellData.getValue().getTglSAP())));
            } catch (Exception ex) {
                return new SimpleStringProperty("-");
            }
        });
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        tglPembayaranColumn.setComparator(Function.sortDate(tgl));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        Function.setNumberField(jumlahAngsuranField);
        
        tglJatuhTempo.setConverter(Function.getTglConverter());
        tglJatuhTempo.setValue(LocalDate.now());
        tglJatuhTempo.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }   
    public void setMainApp(Main mainApp, Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for(OtoritasKeuangan k : sistem.getUser().getOtoritasKeuangan()){
            listKeuangan.add(k.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
        SAPtable.setItems(allSAP);
    }
    public void setNewSAP(){
        sap = new SAP();
        noSAPField.setText("-");
        tglSAPField.setText(tglSql.format(new Date()));
    }
    public void getSAP(String noSAP){
        Task<SAP> task = new Task<SAP>() {
            @Override 
            public SAP call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    SAP sap = SAPDAO.get(con, noSAP);
                    sap.setProperty(PropertyDAO.get(con, sap.getKodeProperty()));
                    sap.setCustomer(CustomerDAO.get(con, sap.getKodeCustomer()));
                    return sap;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                setSAP(task.getValue());
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
    public void setSAP(SAP temp){
        Task<List<SAP>> task = new Task<List<SAP>>() {
            @Override 
            public List<SAP> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return SAPDAO.getAllByKodeProperty(con, temp.getKodeProperty(), "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                propertyButton.setVisible(false);
                abaikanLabel.setVisible(false);
                jumlahAngsuranField.setDisable(true);
                tipeKeuanganCombo.setDisable(true);
                tglJatuhTempo.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                
                allSAP.addAll(task.getValue());
                sap = temp;
                noSAPField.setText(sap.getNoSAP());
                tglSAPField.setText(tglLengkap.format(tglSql.parse(sap.getTglSAP())));
                namaPropertyField.setText(sap.getProperty().getNamaProperty());
                hargaField.setText(rp.format(sap.getProperty().getHargaJual()));
                diskonField.setText(rp.format(sap.getProperty().getDiskon()));
                namaCustomerField.setText(sap.getCustomer().getNama());
                tahapField.setText(String.valueOf(sap.getTahap()));
                tahapNextLabel.setText("Tagihan Down Payment Tahap ke-"+String.valueOf(sap.getTahap()+1));
                jumlahAngsuranField.setText(rp.format(sap.getJumlahRp()));
                tipeKeuanganCombo.setItems(FXCollections.observableArrayList(sap.getTipeKeuangan()));
                tipeKeuanganCombo.getSelectionModel().selectFirst();
                tglJatuhTempo.setValue(LocalDate.parse(sap.getJatuhTempo(),DateTimeFormatter.ofPattern("dd MMM yyyy")));
                stage.setHeight(stage.getHeight()-50);
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
                    Task<Void> task = new Task<Void>() {
                        @Override 
                        public Void call() throws Exception{
                            try (Connection con = Koneksi.getConnection()) {
                                sap.setSkl(SKLHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true"));
                                sap.getSkl().setAllDetail(SKLDetailDAO.getAllByNoSkl(con, sap.getSkl().getNoSKL()));
                                sap.setCustomer(CustomerDAO.get(con, sap.getSkl().getKodeCustomer()));
                                allSAP.addAll(SAPDAO.getAllByKodeProperty(con, row.getItem().getKodeProperty(), "true"));
                            }
                            return null;
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
                            
                            sap.setProperty(p);
                            sap.setKodeProperty(p.getKodeProperty());
                            sap.setNoSKL(sap.getSkl().getNoSKL());
                            
                            namaCustomerField.setText(sap.getCustomer().getNama());
                            sap.setKodeCustomer(sap.getCustomer().getKodeCustomer());

                            int tahap = 1;
                            for(SAP s : allSAP){
                                if(s.getTahap()>=tahap)
                                    tahap = s.getTahap()+1;
                            }
                            tahapField.setText(String.valueOf(tahap));
                            tahapNextLabel.setText("Tagihan Down Payment Tahap ke-"+String.valueOf(tahap+1));
                            sap.setTahap(tahap);
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
