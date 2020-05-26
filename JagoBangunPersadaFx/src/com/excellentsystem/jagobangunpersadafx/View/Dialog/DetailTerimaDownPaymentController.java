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
import com.excellentsystem.jagobangunpersadafx.DAO.STJDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.STJHeadDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
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
public class DetailTerimaDownPaymentController  {

    @FXML private TableView<SDP> SDPtable;
    @FXML private TableColumn<SDP,Number> tahapColumn;
    @FXML private TableColumn<SDP,String> tglPembayaranColumn;
    @FXML private TableColumn<SDP,Number> jumlahPembayaranColumn;
    
    @FXML private TextField noSDPField;
    @FXML private TextField tglSDPField;
    @FXML private TextField namaPropertyField;
    @FXML private TextField hargaField;
    @FXML private TextField diskonField;
    @FXML private TextField namaCustomerField;
    @FXML private TextField tahapField;
    @FXML public TextField jumlahDPField;
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML private Label tahapNextLabel;
    @FXML public TextField jumlahTagihanField;
    @FXML public DatePicker tglJatuhTempo;
    
    @FXML private Button propertyButton;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Label abaikanLabel;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SDP sdp;
    private ObservableList<SDP> allSDP = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        
        tglPembayaranColumn.setCellValueFactory(cellData ->{
            try {
                return new SimpleStringProperty(tgl.format(tglSql.parse(cellData.getValue().getTglSDP())));
            } catch (Exception ex) {
                return new SimpleStringProperty("-");
            }
        });
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        tglPembayaranColumn.setComparator(Function.sortDate(tgl));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        Function.setNumberField(jumlahDPField);
        Function.setNumberField(jumlahTagihanField);
        
        tglJatuhTempo.setConverter(Function.getTglConverter());
        tglJatuhTempo.setValue(LocalDate.now());
        tglJatuhTempo.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableBefore(LocalDate.now()));
    }   
    public void setMainApp(Main mainApp,Stage owner, Stage stage){
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        SDPtable.setItems(allSDP);
        ObservableList<String> listKeuangan = FXCollections.observableArrayList();
        for(OtoritasKeuangan k : sistem.getUser().getOtoritasKeuangan()){
            listKeuangan.add(k.getKodeKeuangan());
        }
        tipeKeuanganCombo.setItems(listKeuangan);
    }
    public void setNewSDP(){
        sdp = new SDP();
        noSDPField.setText("-");
        tglSDPField.setText(tglSql.format(new Date()));
    }
    public void getSDP(String noSDP){
        Task<SDP> task = new Task<SDP>() {
            @Override 
            public SDP call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    SDP sdp = SDPDAO.get(con, noSDP);
                    sdp.setProperty(PropertyDAO.get(con, sdp.getKodeProperty()));
                    sdp.setCustomer(CustomerDAO.get(con, sdp.getKodeCustomer()));
                    return sdp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                setSDP(task.getValue());
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
    public void setSDP(SDP temp){
        Task<List<SDP>> task = new Task<List<SDP>>() {
            @Override 
            public List<SDP> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return SDPDAO.getAllByKodeProperty(con, temp.getKodeProperty(), "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                sdp = temp;
                allSDP.addAll(task.getValue());
                allSDP.sort(Comparator.comparing(SDP::getTahap));
                noSDPField.setText(sdp.getNoSDP());
                tglSDPField.setText(tglLengkap.format(tglSql.parse(sdp.getTglSDP())));
                namaPropertyField.setText(sdp.getProperty().getNamaProperty());
                namaCustomerField.setText(sdp.getCustomer().getNama());
                hargaField.setText(rp.format(sdp.getProperty().getHargaJual()));
                diskonField.setText(rp.format(sdp.getProperty().getDiskon()));
                propertyButton.setVisible(false);
                tahapField.setText(String.valueOf(sdp.getTahap()));
                tahapNextLabel.setText("Tagihan Down Payment Tahap ke-"+String.valueOf(sdp.getTahap()+1));
                jumlahDPField.setText(rp.format(sdp.getJumlahRp()));
                jumlahDPField.setDisable(true);
                tipeKeuanganCombo.setItems(FXCollections.observableArrayList(sdp.getTipeKeuangan()));
                tipeKeuanganCombo.getSelectionModel().selectFirst();
                tipeKeuanganCombo.setDisable(true);
                tglJatuhTempo.setValue(LocalDate.parse(sdp.getJatuhTempo(),DateTimeFormatter.ofPattern("dd MMM yyyy")));
                tglJatuhTempo.setDisable(true);
                jumlahTagihanField.setText(rp.format(sdp.getJumlahTagihan()));
                jumlahTagihanField.setDisable(true);
                saveButton.setVisible(false);
                cancelButton.setVisible(false);
                abaikanLabel.setVisible(false);
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
        status.add("Reserved");
        x.getProperty(status);
        x.propertyTable.setRowFactory((TableView<Property> tableView) -> {
            final TableRow<Property> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    Task<Void> task = new Task<Void>() {
                        @Override 
                        public Void call() throws Exception{
                            try (Connection con = Koneksi.getConnection()) {
                                STJHead stj = STJHeadDAO.getByKodeProperty(con, row.getItem().getKodeProperty(), "true");
                                stj.setAllDetail(STJDetailDAO.getAllByNoSTJ(con, stj.getNoSTJ()));
                                sdp.setStj(stj);
                                sdp.setCustomer(CustomerDAO.get(con, stj.getKodeCustomer()));
                                sdp.setSales(KaryawanDAO.get(con, stj.getKodeSales()));
                                allSDP.clear();
                                allSDP.addAll(SDPDAO.getAllByKodeProperty(con, row.getItem().getKodeProperty(), "true"));
                                allSDP.sort(Comparator.comparing(SDP::getTahap));
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
                            sdp.setProperty(p);
                            sdp.setKodeProperty(p.getKodeProperty());
                            sdp.setNoSTJ(sdp.getStj().getNoSTJ());
                            namaCustomerField.setText(sdp.getCustomer().getNama());
                            sdp.setKodeCustomer(sdp.getCustomer().getKodeCustomer());
                            sdp.setKodeSales(sdp.getSales().getKodeKaryawan());
                            int tahap = 1;
                            for(SDP s : allSDP){
                                if(s.getTahap()>=tahap)
                                    tahap = s.getTahap()+1;
                            }
                            tahapField.setText(String.valueOf(tahap));
                            tahapNextLabel.setText("Tagihan Down Payment Tahap ke-"+String.valueOf(tahap+1));
                            sdp.setTahap(tahap);
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
