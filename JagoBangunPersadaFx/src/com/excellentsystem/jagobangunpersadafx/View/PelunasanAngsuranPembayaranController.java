/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SPPDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SPPHeadDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Customer;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Model.SPPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.SPPHead;
import com.excellentsystem.jagobangunpersadafx.Printout.PrintOut;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPelunasanAngsuranController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
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
public class PelunasanAngsuranPembayaranController {
    
    @FXML private TableView<SPPHead> SPPHeadTable;
    @FXML private TableColumn<SPPHead, String> noSPPHeadColumn;
    @FXML private TableColumn<SPPHead, String> tglSPPHeadColumn;
    @FXML private TableColumn<SPPHead, Number> totalDPColumn;
    @FXML private TableColumn<SPPHead, Number> totalAngsuranColumn;
    @FXML private TableColumn<SPPHead, String> kodeKategoriColumn;
    @FXML private TableColumn<SPPHead, String> namaPropertyColumn;
    @FXML private TableColumn<SPPHead, Number> hargaJualColumn;
    @FXML private TableColumn<SPPHead, Number> diskonColumn;
    @FXML private TableColumn<SPPHead, String> namaCustomerColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    private Main mainApp;   
    private ObservableList<SPPHead> allSPPHead = FXCollections.observableArrayList();
    private ObservableList<SPPHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noSPPHeadColumn.setCellValueFactory(cellData -> cellData.getValue().noSPPProperty());
        noSPPHeadColumn.setCellFactory(col -> Function.getWrapTableCell(noSPPHeadColumn));
        
        tglSPPHeadColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSPP())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglSPPHeadColumn.setCellFactory(col -> Function.getWrapTableCell(tglSPPHeadColumn));
        tglSPPHeadColumn.setComparator(Function.sortDate(tglLengkap));
        
        totalDPColumn.setCellValueFactory(cellData ->cellData.getValue().totalDPProperty());
        totalDPColumn.setCellFactory(col -> getTableCell(rp));
        
        totalAngsuranColumn.setCellValueFactory(cellData ->cellData.getValue().totalAngsuranProperty());
        totalAngsuranColumn.setCellFactory(col -> getTableCell(rp));
        
        kodeKategoriColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));
        
        namaPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().namaPropertyProperty());
        namaPropertyColumn.setCellFactory(col -> Function.getWrapTableCell(namaPropertyColumn));
        
        hargaJualColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().hargaJualProperty());
        hargaJualColumn.setCellFactory(col -> getTableCell(rp));
        
        diskonColumn.setCellValueFactory(cellData ->cellData.getValue().getProperty().diskonProperty());
        diskonColumn.setCellFactory(col -> getTableCell(rp));
        
        namaCustomerColumn.setCellValueFactory(cellData ->cellData.getValue().getCustomer().namaProperty());
        namaCustomerColumn.setCellFactory(col -> Function.getWrapTableCell(namaCustomerColumn));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pelunasan Angsuran");
        addNew.setOnAction((ActionEvent e)->{
            addNewPelunasan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getSPP();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pelunasan Angsuran")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().addAll(refresh);
        SPPHeadTable.setContextMenu(rowMenu);
        SPPHeadTable.setRowFactory(tv -> {
            TableRow<SPPHead> row = new TableRow<SPPHead>() {
                @Override
                public void updateItem(SPPHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pelunasan Angsuran");
                        addNew.setOnAction((ActionEvent e)->{
                            addNewPelunasan();
                        });
                        MenuItem print = new MenuItem("Print Surat Pelunasan Pembayaran");
                        print.setOnAction((ActionEvent e)->{
                            printSPP(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pelunasan Angsuran");
                        batal.setOnAction((ActionEvent event) -> {
                            batalPelunasan(item);
                        });
                        MenuItem detailPelunasan = new MenuItem("Detail Pelunasan Pembayaran");
                        detailPelunasan.setOnAction((ActionEvent event) -> {
                            detailPelunasan(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getSPP();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pelunasan Angsuran")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Pelunasan Angsuran")&&o.isStatus())
                                rowMenu.getItems().add(detailPelunasan);
                            if(o.getJenis().equals("Detail Property")&&o.isStatus())
                                rowMenu.getItems().add(detailProperty);
                            if(o.getJenis().equals("Detail Customer")&&o.isStatus())
                                rowMenu.getItems().add(detailCustomer);
                            if(o.getJenis().equals("Batal Pelunasan Angsuran")&&o.isStatus())
                                rowMenu.getItems().add(batal);
                            if(o.getJenis().equals("Print Surat Pelunasan Pembayaran")&&o.isStatus())
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
                        if(o.getJenis().equals("Detail Pelunasan Angsuran")&&o.isStatus())
                            detailPelunasan(row.getItem());
                    }
            });
            return row;
        });
        allSPPHead.addListener((ListChangeListener.Change<? extends SPPHead> change) -> {
            search();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            search();
        });
        filterData.addAll(allSPPHead);
    }    
    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        SPPHeadTable.setItems(filterData);
        getSPP();
    }
    @FXML
    private void getSPP(){
        Task<List<SPPHead>> task = new Task<List<SPPHead>>() {
            @Override 
            public List<SPPHead> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    statusProperty.add("Sold - Full Paid");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<SPPDetail> listSPPDetail = SPPDetailDAO.getAllByDateAndStatus(con, tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString(), "true");
                    List<SPPHead> listSPP = SPPHeadDAO.getAllByDateAndStatus(con, tglAwalPicker.getValue().toString(),tglAkhirPicker.getValue().toString(), "true");
                    for(SPPHead spp : listSPP){
                        List<SPPDetail> detail = new ArrayList<>();
                        for(SPPDetail d : listSPPDetail){
                            if(spp.getNoSPP().equals(d.getNoSPP()))
                                detail.add(d);
                        }
                        spp.setAllDetail(detail);
                        for(Customer c : listCustomer){
                            if(spp.getKodeCustomer().equals(c.getKodeCustomer()))
                                spp.setCustomer(c);
                        }
                        for(Property p : listProperty){
                            if(spp.getKodeProperty().equals(p.getKodeProperty()))
                                spp.setProperty(p);
                        }
                    }
                    return listSPP;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allSPPHead.clear();
                allSPPHead.addAll(task.getValue());
            }catch(Exception ex){
                mainApp.showMessage(Modality.NONE, "Error", ex.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null)
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        return false;
    }
    private void search() {
        filterData.clear();
        for (SPPHead temp : allSPPHead) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getNoSPP())||
                    checkColumn(temp.getTglSPP())||
                    checkColumn(rp.format(temp.getTotalDP()))||
                    checkColumn(rp.format(temp.getTotalAngsuran()))||
                    checkColumn(temp.getCustomer().getNama())||
                    checkColumn(temp.getProperty().getKodeKategori())||
                    checkColumn(temp.getProperty().getNamaProperty())||
                    checkColumn(rp.format(temp.getProperty().getHargaJual()))||
                    checkColumn(rp.format(temp.getProperty().getDiskon())))
                        filterData.add(temp);
            }
        }
    }
    @FXML
    private void addNewPelunasan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanAngsuran.fxml");
        DetailPelunasanAngsuranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setNewSPP();
        controller.saveButton.setOnAction((event) -> {
            if(controller.spp.getProperty()==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            }else if(controller.spp.getTotalDP()+controller.spp.getTotalAngsuran()<controller.spp.getProperty().getHargaJual()-controller.spp.getProperty().getDiskon()){
                mainApp.showMessage(Modality.NONE, "Warning", "Total yang dibayar masih kurang");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            controller.spp.setKodeUser(sistem.getUser().getUsername());
                            controller.spp.setStatus("true");
                            controller.spp.setTglBatal("2000-01-01 00:00:00");
                            controller.spp.setUserBatal("");
                            controller.spp.setAllDetail(controller.allDetail);
                            return Service.savePelunasanAngsuran(con, controller.spp);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getSPP();
                        if(task.getValue().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Pelunasan angsuran berhasil disimpan");
                            mainApp.closeDialog(mainApp.MainStage, stage);
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
    private void detailPelunasan(SPPHead s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPelunasanAngsuran.fxml");
        DetailPelunasanAngsuranController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setSPP(s);
    }
    private void batalPelunasan(SPPHead spp){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pelunasan angsuran " + spp.getNoSPP()+ ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        spp.setTglBatal(tglSql.format(new Date()));
                        spp.setUserBatal(sistem.getUser().getUsername());
                        spp.setStatus("false");
                        return Service.batalPelunasanAngsuran(con, spp);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    getSPP();
                    if (task.getValue().equals("true")) 
                        mainApp.showMessage(Modality.NONE, "Success", "Data pelunasan angsuran berhasil dibatal");
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
    private void printSPP(SPPHead spp){
        try{
            PrintOut print = new PrintOut();
            print.printSuratPelunasanAngsuran(spp);
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
