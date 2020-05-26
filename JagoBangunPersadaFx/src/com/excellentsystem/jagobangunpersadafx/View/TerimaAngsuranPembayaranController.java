/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SAPDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SPPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tgl;
import static com.excellentsystem.jagobangunpersadafx.Main.tglBarang;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaAngsuranPembayaranController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.MessageController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
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
public class TerimaAngsuranPembayaranController {
    @FXML private TableView<SAP> SAPTable;
    @FXML private TableColumn<SAP, String> noSAPColumn;
    @FXML private TableColumn<SAP, String> tglSAPColumn;
    @FXML private TableColumn<SAP, Number> TahapAngsuranColumn;
    @FXML private TableColumn<SAP, Number> jumlahAngsuranColumn;
    @FXML private TableColumn<SAP, String> tipeKeuanganAngsuranColumn;
    @FXML private TableColumn<SAP, Number> totalPembayaranColumn;
    @FXML private TableColumn<SAP, String> kodeKategoriColumn;
    @FXML private TableColumn<SAP, String> namaPropertyColumn;
    @FXML private TableColumn<SAP, Number> hargaJualColumn;
    @FXML private TableColumn<SAP, Number> diskonColumn;
    @FXML private TableColumn<SAP, String> namaCustomerColumn;
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private ObservableList<SAP> allSAP = FXCollections.observableArrayList();
    private ObservableList<SAP> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noSAPColumn.setCellValueFactory(cellData -> cellData.getValue().noSAPProperty());
        noSAPColumn.setCellFactory(col -> Function.getWrapTableCell(noSAPColumn));
        
        tglSAPColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSAP())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSAPColumn.setCellFactory(col -> Function.getWrapTableCell(tglSAPColumn));
        
        jumlahAngsuranColumn.setCellValueFactory(cellData ->cellData.getValue().jumlahRpProperty());
        jumlahAngsuranColumn.setCellFactory(col -> getTableCell(rp));
        
        TahapAngsuranColumn.setCellValueFactory(cellData ->cellData.getValue().tahapProperty());
        
        tipeKeuanganAngsuranColumn.setCellValueFactory(cellData ->cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganAngsuranColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganAngsuranColumn));
        
        kodeKategoriColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));
        
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        hargaJualColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        diskonColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));
        
        totalPembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().getSkl().totalPembayaranProperty());
        totalPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        namaCustomerColumn.setCellValueFactory(cellData ->cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Terima Angsuran Pembayaran");
        addNew.setOnAction((ActionEvent e)->{
            addNewTerimaAngsuran();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getSAP();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Terima Angsuran Pembayaran")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().addAll(refresh);
        SAPTable.setContextMenu(rowMenu);
        SAPTable.setRowFactory(tv -> {
            TableRow<SAP> row = new TableRow<SAP>() {
                @Override
                public void updateItem(SAP item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Terima Angsuran Pembayaran");
                        addNew.setOnAction((ActionEvent e)->{
                            addNewTerimaAngsuran();
                        });
                        MenuItem print = new MenuItem("Print Surat Angsuran Pembayaran");
                        print.setOnAction((ActionEvent e)->{
                            printSAP(item);
                        });
                        MenuItem batal = new MenuItem("Batal Terima Angsuran Pembayaran");
                        batal.setOnAction((ActionEvent event) -> {
                            batalTerimaAngsuran(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem detailTerimaAngsuran = new MenuItem("Detail Terima Angsuran Pembayaran");
                        detailTerimaAngsuran.setOnAction((ActionEvent event) -> {
                            detailTerimaAngsuran(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getSAP();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Terima Angsuran Pembayaran")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Terima Angsuran Pembayaran")&&o.isStatus())
                                rowMenu.getItems().add(detailTerimaAngsuran);
                            if(o.getJenis().equals("Detail Customer")&&o.isStatus())
                                rowMenu.getItems().add(detailCustomer);
                            if(o.getJenis().equals("Detail Property")&&o.isStatus())
                                rowMenu.getItems().add(detailProperty);
                            if(o.getJenis().equals("Batal Terima Angsuran Pembayaran")&&o.isStatus())
                                rowMenu.getItems().add(batal);
                            if(o.getJenis().equals("Print Surat Angsuran Pembayaran")&&o.isStatus())
                                rowMenu.getItems().add(print);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Detail Terima Angsuran Pembayaran")&&o.isStatus())
                            detailTerimaAngsuran(row.getItem());
                    }
            });
            return row;
        });
        
        allSAP.addListener((ListChangeListener.Change<? extends SAP> change) -> {
            searchSAP();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchSAP();
        });
        filterData.addAll(allSAP);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getSAP();
        SAPTable.setItems(filterData);
    }
    @FXML
    private void getSAP(){
        Task<List<SAP>> task = new Task<List<SAP>>() {
            @Override 
            public List<SAP> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    statusProperty.add("Sold - Full Paid");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<SAP> listSAP = SAPDAO.getAllByDateAndStatus(con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for(SAP sap : listSAP){
                        sap.setSkl(SKLHeadDAO.get(con, sap.getNoSKL()));
                        for(Customer c : listCustomer){
                            if(sap.getSkl().getKodeCustomer().equals(c.getKodeCustomer()))
                                sap.setCustomer(c);
                        }
                        for(Property pr : listProperty){
                            if(sap.getSkl().getKodeProperty().equals(pr.getKodeProperty()))
                                sap.setProperty(pr);
                        }
                    }
                    return listSAP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allSAP.clear();
                allSAP.addAll(task.getValue());
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
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchSAP() {
        filterData.clear();
        for (SAP temp : allSAP) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getNoSAP())||
                    checkColumn(temp.getTglSAP())||
                    checkColumn(temp.getTipeKeuangan())||
                    checkColumn(qty.format(temp.getTahap()))||
                    checkColumn(rp.format(temp.getJumlahRp()))||
                    checkColumn(rp.format(temp.getSkl().getTotalPembayaran()))||
                    checkColumn(temp.getCustomer().getNama())||
                    checkColumn(temp.getProperty().getKodeKategori())||
                    checkColumn(temp.getProperty().getNamaProperty())||
                    checkColumn(rp.format(temp.getProperty().getHargaJual()))||
                    checkColumn(rp.format(temp.getProperty().getDiskon())))
                        filterData.add(temp);
            }
        }
    }
    private void addNewTerimaAngsuran(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaAngsuranPembayaran.fxml");
        DetailTerimaAngsuranPembayaranController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewSAP();
        x.saveButton.setOnAction((event) -> {
            if(x.sap.getProperty()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            }else if("".equals(x.jumlahAngsuranField.getText())||"0".equals(x.jumlahAngsuranField.getText())){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah Pembayaran masih kosong");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null||
                    "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            x.sap.setJumlahRp(Double.parseDouble(x.jumlahAngsuranField.getText().replaceAll(",", "")));
                            x.sap.setJatuhTempo(tgl.format(tglBarang.parse(x.tglJatuhTempo.getValue().toString())));
                            x.sap.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            x.sap.setKodeUser(sistem.getUser().getUsername());
                            x.sap.setStatus("true");
                            x.sap.setTglBatal("2000-01-01 00:00:00");
                            x.sap.setUserBatal("");
                            return Service.saveTerimaAngsuran(con, x.sap);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        if(task.getValue().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Terima angsuran berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
                            getSAP();
                        }else{
                            mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                        }
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
    }
    private void detailTerimaAngsuran(SAP sap){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaAngsuranPembayaran.fxml");
        DetailTerimaAngsuranPembayaranController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setSAP(sap);
    }
    private void batalTerimaAngsuran(SAP sap){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal terima angsuran " + sap.getNoSAP() + ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        List<SAP> all = SAPDAO.getAllByKodeProperty(con, sap.getKodeProperty(), "true");
                        boolean statusTahap = true;
                        for(SAP s : all){
                            if(s.getTahap()>sap.getTahap())
                                statusTahap = false;
                        }
                        if(SPPHeadDAO.getByKodeProperty(con, sap.getKodeProperty(), "true")!=null){
                            return "Terima angsuran tidak dapat dibatalkan, karena sudah ada pelunasan Angsuran";
                        }else if(statusTahap){
                            sap.setTglBatal(tglSql.format(new Date()));
                            sap.setUserBatal(sistem.getUser().getUsername());
                            sap.setStatus("false");
                            return Service.batalTerimaAngsuran(con, sap);
                        }else 
                            return "Terima angsuran pembayaran tidak dapat dibatalkan, karena sudah ada pembayaran angsuran tahap selanjutnya";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    getSAP();
                    if (task.getValue().equals("true")) 
                        mainApp.showMessage(Modality.NONE, "Success", "Data terima angsuran pembayaran berhasil dibatal");
                    else 
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
                }catch(Exception ex){
                    mainApp.showMessage(Modality.NONE, "Error", ex.toString());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void printSAP(SAP sap){
        try{
            PrintOut print = new PrintOut();
            print.printSuratAngsuranPembayaran(sap);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error",e.toString());
        }
    }
    private void detailCustomer(Customer c){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailCustomer.fxml");
        DetailCustomerController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setCustomer(c);
        x.setViewOnly();
    }
    private void detailProperty(Property p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailProperty.fxml");
        DetailPropertyController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setProperty(p);
        x.viewOnly();
    }
}
