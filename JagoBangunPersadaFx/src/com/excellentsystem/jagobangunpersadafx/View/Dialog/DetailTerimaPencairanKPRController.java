/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View.Dialog;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
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
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.OtoritasKeuangan;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DetailTerimaPencairanKPRController  {

    @FXML private TableView<SKLDetail> SKLDetailtable;
    @FXML private TableColumn<SKLDetail,String> tahapColumn;
    @FXML private TableColumn<SKLDetail,String> tglPembayaranColumn;
    @FXML private TableColumn<SKLDetail,Number> jumlahPembayaranColumn;
    @FXML private TextField noKPRField;
    @FXML private TextField tglKPRField;
    @FXML public ComboBox<Property> namaPropertyCombo;
    @FXML private TextField hargaField;
    @FXML private TextField diskonField;
    @FXML private TextField namaCustomerField;
    @FXML private TextField totalDPField;
    @FXML public TextField jumlahDiterimaField;
    @FXML public TextField keteranganField;    
    @FXML public ComboBox<String> tipeKeuanganCombo;
    @FXML public Button saveButton;
    @FXML private Button cancelButton;
    
    private Main mainApp;
    private Stage owner;
    private Stage stage;
    public SKLHead skl;
    private String noKPR;
    private ObservableList<SKLDetail> allDetail = FXCollections.observableArrayList();
    private ObservableList<Property> allProperty = FXCollections.observableArrayList();
    public void initialize() {
        tahapColumn.setCellValueFactory(celldata -> celldata.getValue().tahapProperty());
        tahapColumn.setCellFactory(col -> Function.getWrapTableCell(tahapColumn));
        
        tglPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().tglBayarProperty()); 
        tglPembayaranColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembayaranColumn));
        
        jumlahPembayaranColumn.setCellValueFactory(celldata -> celldata.getValue().jumlahRpProperty());
        jumlahPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        Function.setNumberField(jumlahDiterimaField);
        
        namaPropertyCombo.setConverter(new StringConverter<Property>(){
            @Override
            public String toString(Property p){
                return p == null? null : p.getKodeProperty() + " - " + p.getNamaProperty();
            }
            @Override
            public Property fromString(String string){
		Property p = null;
                if (string == null){
                    return p;
		}
		int commaIndex = string.indexOf(" - ");
		if (commaIndex == -1){
                    p = new Property();
                    p.setKodeProperty(string);
		}else{
                    String kodeProperty = string.substring(commaIndex + 2);
                    String namaProperty = string.substring(0, commaIndex);
                    p = new Property();
                    p.setKodeProperty(kodeProperty);
                    p.setNamaProperty(namaProperty);
		}
		return p;
            }
        });
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
        SKLDetailtable.setItems(allDetail);
        namaPropertyCombo.setItems(allProperty);
    }
    public void setNewKPR(){
        Task<List<Property>> task = new Task<List<Property>>() {
            @Override 
            public List<Property> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    noKPR = KPRDAO.getId(con);
                    List<String> statusTanah = new ArrayList<>();
                    statusTanah.add("Sold");
                    List<Property> allProp = PropertyDAO.getAllByStatus(con, statusTanah);
                    List<Property> temp = new ArrayList<>();
                    for(Property p : allProp){
                        if(KPRDAO.getByKodeProperty(con, p.getKodeProperty())==null&&
                                SAPDAO.getAllByKodeProperty(con, p.getKodeProperty(), "true").isEmpty())
                            temp.add(p);
                    }
                    return temp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allProperty.addAll(task.getValue());
                SKLDetailtable.setSelectionModel(null);
                ObservableList<String> listKeuangan = FXCollections.observableArrayList();
                for(OtoritasKeuangan k : sistem.getUser().getOtoritasKeuangan()){
                    listKeuangan.add(k.getKodeKeuangan());
                }
                tipeKeuanganCombo.setItems(listKeuangan);
                noKPRField.setText(noKPR);
                tglKPRField.setText(tglSql.format(new Date()));
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
    public void getKPR(Property p){
        Task<KPR> task = new Task<KPR>() {
            @Override 
            public KPR call() throws Exception{
                try(Connection con = Koneksi.getConnection()){
                    KPR k = KPRDAO.getByKodeProperty(con, p.getKodeProperty());
                    k.setProperty(p);
                    k.setCustomer(CustomerDAO.get(con, k.getKodeCustomer()));
                    k.setSkl(SKLHeadDAO.getByKodeProperty(con, p.getKodeProperty(), "true"));
                    k.getSkl().setAllDetail(SKLDetailDAO.getAllByNoSkl(con, k.getSkl().getNoSKL()));
                    return k;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                setKPR(task.getValue());
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
    public void setKPR(KPR kpr){
        noKPRField.setText(kpr.getNoKPR());
        tglKPRField.setText(kpr.getTglKPR());
        namaPropertyCombo.getSelectionModel().select(kpr.getProperty());
        namaPropertyCombo.setDisable(true);
        hargaField.setText(rp.format(kpr.getProperty().getHargaJual()));
        diskonField.setText(rp.format(kpr.getProperty().getDiskon()));
        namaCustomerField.setText(kpr.getCustomer().getNama());
        allDetail.clear();
        allDetail.addAll(kpr.getSkl().getAllDetail());
        totalDPField.setText(rp.format(kpr.getSkl().getTotalPembayaran()));
        jumlahDiterimaField.setText(rp.format(kpr.getJumlahRp()));
        keteranganField.setText(kpr.getKeterangan());
        keteranganField.setDisable(true);
        tipeKeuanganCombo.setItems(FXCollections.observableArrayList(kpr.getTipeKeuangan()));
        tipeKeuanganCombo.getSelectionModel().selectFirst();
        tipeKeuanganCombo.setDisable(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        stage.setHeight(stage.getHeight()-30);
    }
    @FXML
    private void setProperty(){
        skl = null;
        hargaField.setText("0");
        diskonField.setText("0");
        totalDPField.setText("0");
        jumlahDiterimaField.setText("0");
        namaCustomerField.setText("");
        if(namaPropertyCombo.getSelectionModel().getSelectedItem()!=null){
            Task<SKLHead> task = new Task<SKLHead>() {
                @Override 
                public SKLHead call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        SKLHead temp;
                        temp = SKLHeadDAO.getByKodeProperty(con,
                                namaPropertyCombo.getSelectionModel().getSelectedItem().getKodeProperty(), "true");
                        temp.setProperty(namaPropertyCombo.getSelectionModel().getSelectedItem());
                        List<SKLDetail> sklDetail = SKLDetailDAO.getAllByNoSkl(con, temp.getNoSKL());
                        Customer cust = CustomerDAO.get(con, temp.getKodeCustomer());
                        temp.setCustomer(cust);
                        temp.setAllDetail(sklDetail);
                        return temp;
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    skl = task.getValue();
                    hargaField.setText(rp.format(namaPropertyCombo.getSelectionModel().getSelectedItem().getHargaJual()));
                    diskonField.setText(rp.format(namaPropertyCombo.getSelectionModel().getSelectedItem().getDiskon()));
                    totalDPField.setText(rp.format(skl.getTotalPembayaran()));
                    jumlahDiterimaField.setText(rp.format(skl.getSisaPelunasan()));
                    namaCustomerField.setText(skl.getCustomer().getNama());
                    allDetail.clear();
                    allDetail.addAll(skl.getAllDetail());
                }catch(Exception ex){
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());task.getException().printStackTrace();
                mainApp.closeLoading();
            });
            new Thread(task).start();
        }
    }
    @FXML 
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
