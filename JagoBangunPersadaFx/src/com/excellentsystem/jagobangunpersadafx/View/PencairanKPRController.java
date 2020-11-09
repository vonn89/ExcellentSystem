/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.CustomerDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.KPRDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.SKLHeadDAO;
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
import com.excellentsystem.jagobangunpersadafx.Model.KPR;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailCustomerController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPropertyController;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailTerimaPencairanKPRController;
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
public class PencairanKPRController  {

    @FXML private TableView<KPR> KPRTable;
    @FXML private TableColumn<KPR, String> noKPRColumn;
    @FXML private TableColumn<KPR, String> tglKPRColumn;
    @FXML private TableColumn<KPR, Number> jumlahRpColumn;
    @FXML private TableColumn<KPR, String> keteranganColumn;
    @FXML private TableColumn<KPR, String> tipeKeuanganColumn;
    
    @FXML private TableColumn<KPR, Number> totalPembayaranColumn;
    
    @FXML private TableColumn<KPR, String> kodeKategoriColumn;
    @FXML private TableColumn<KPR, String> namaPropertyColumn;
    @FXML private TableColumn<KPR, Number> hargaJualColumn;
    @FXML private TableColumn<KPR, Number> diskonColumn;
    
    @FXML private TableColumn<KPR, String> namaCustomerColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    private Main mainApp;   
    private ObservableList<KPR> allKPR = FXCollections.observableArrayList();
    private ObservableList<KPR> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noKPRColumn.setCellValueFactory(cellData -> cellData.getValue().noKPRProperty());
        noKPRColumn.setCellFactory(col -> Function.getWrapTableCell(noKPRColumn));
        
        tglKPRColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglKPR())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglKPRColumn.setCellFactory(col -> Function.getWrapTableCell(tglKPRColumn));
        tglKPRColumn.setComparator(Function.sortDate(tglLengkap));
        
        jumlahRpColumn.setCellValueFactory(cellData ->cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory(col -> getTableCell(rp));
        
        totalPembayaranColumn.setCellValueFactory(cellData ->cellData.getValue().getSkl().totalPembayaranProperty());
        totalPembayaranColumn.setCellFactory(col -> getTableCell(rp));
        
        keteranganColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        tipeKeuanganColumn.setCellValueFactory(cellData ->cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganColumn));
        
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
        
        allKPR.addListener((ListChangeListener.Change<? extends KPR> change) -> {
            searchKPR();
        });
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Terima Pencairan KPR");
        addNew.setOnAction((ActionEvent e)->{
            addNewTerimaPencairanKPR();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getKPR();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Terima Pencairan KPR")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().addAll(refresh);
        KPRTable.setContextMenu(rowMenu);
        KPRTable.setRowFactory(tv -> {
            TableRow<KPR> row = new TableRow<KPR>() {
                @Override
                public void updateItem(KPR item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Terima Pencairan KPR");
                        addNew.setOnAction((ActionEvent e)->{
                            addNewTerimaPencairanKPR();
                        });
                        MenuItem batal = new MenuItem("Batal Terima Pencairan KPR");
                        batal.setOnAction((ActionEvent event) -> {
                            batalTerimaPencairanKPR(item);
                        });
                        MenuItem detailCustomer = new MenuItem("Detail Customer");
                        detailCustomer.setOnAction((ActionEvent event) -> {
                            detailCustomer(item.getCustomer());
                        });
                        MenuItem detailProperty = new MenuItem("Detail Property");
                        detailProperty.setOnAction((ActionEvent event) -> {
                            detailProperty(item.getProperty());
                        });
                        MenuItem detailKPR = new MenuItem("Detail Terima Pencairan KPR");
                        detailKPR.setOnAction((ActionEvent event) -> {
                            detailTerimaPencairanKPR(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getKPR();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Terima Pencairan KPR")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Terima Pencairan KPR")&&o.isStatus())
                                rowMenu.getItems().add(detailKPR);
                            if(o.getJenis().equals("Detail Customer")&&o.isStatus())
                                rowMenu.getItems().add(detailCustomer);
                            if(o.getJenis().equals("Detail Property")&&o.isStatus())
                                rowMenu.getItems().add(detailProperty);
                            if(o.getJenis().equals("Batal Terima Pencairan KPR")&&o.isStatus())
                                rowMenu.getItems().add(batal);
                        }
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2)
                    for(Otoritas o : sistem.getUser().getOtoritas()){
                        if(o.getJenis().equals("Detail Terima Pencairan KPR")&&o.isStatus())
                            detailTerimaPencairanKPR(row.getItem());
                    }
            });
            return row;
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchKPR();
        });
        filterData.addAll(allKPR);
    }    
    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getKPR();
        KPRTable.setItems(filterData);
    }
    @FXML
    private void getKPR(){
        Task<List<KPR>> task = new Task<List<KPR>>() {
            @Override 
            public List<KPR> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<Customer> listCustomer = CustomerDAO.getAllByStatus(con, "true");
                    List<String> statusProperty = new ArrayList<>();
                    statusProperty.add("Available");
                    statusProperty.add("Reserved");
                    statusProperty.add("Sold");
                    List<Property> listProperty = PropertyDAO.getAllByStatus(con, statusProperty);
                    List<KPR> listKPR = KPRDAO.getAllByDate(con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    for(KPR kpr : listKPR){
                        kpr.setSkl(SKLHeadDAO.get(con, kpr.getNoSKL()));
                        kpr.getSkl().setAllDetail(SKLDetailDAO.getAllByNoSkl(con, kpr.getNoSKL()));
                        for(Customer c : listCustomer){
                            if(kpr.getKodeCustomer().equals(c.getKodeCustomer()))
                                kpr.setCustomer(c);
                        }
                        for(Property p : listProperty){
                            if(kpr.getKodeProperty().equals(p.getKodeProperty()))
                                kpr.setProperty(p);
                        }                        
                    }
                    return listKPR;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allKPR.clear();
                allKPR.addAll(task.getValue());
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
    private void searchKPR() {
        filterData.clear();
        for (KPR temp : allKPR) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getNoKPR())||
                    checkColumn(temp.getTglKPR())||
                    checkColumn(temp.getTipeKeuangan())||
                    checkColumn(rp.format(temp.getJumlahRp()))||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(rp.format(temp.getSkl().getTotalPembayaran()))||
                    checkColumn(temp.getCustomer().getNama())||
                    checkColumn(temp.getProperty().getKodeKategori())||
                    checkColumn(temp.getProperty().getNamaProperty())||
                    checkColumn(rp.format(temp.getProperty().getHargaJual()))||
                    checkColumn(rp.format(temp.getProperty().getDiskon()))
                )
                    filterData.add(temp);
            }
        }
    }
    private void addNewTerimaPencairanKPR(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaPencairanKPR.fxml");
        DetailTerimaPencairanKPRController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewKPR();
        x.saveButton.setOnAction((ActionEvent ev)->{
            if(x.namaPropertyField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Property belum dipilih");
            }else if(x.jumlahDiterimaField.getText().equals("")){
                mainApp.showMessage(Modality.NONE, "Warning", "Jumlah di terima masih kosong");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null||
                    "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            KPR kpr = new KPR();
                            kpr.setNoKPR(KPRDAO.getId(con));
                            kpr.setTglKPR(tglSql.format(new Date()));
                            kpr.setNoSKL(x.skl.getNoSKL());
                            kpr.setKodeCustomer(x.skl.getKodeCustomer());
                            kpr.setKodeProperty(x.skl.getKodeProperty());
                            kpr.setJumlahRp(Double.parseDouble(x.jumlahDiterimaField.getText().replaceAll(",", "")));
                            kpr.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            kpr.setKeterangan(x.keteranganField.getText());
                            kpr.setKodeUser(sistem.getUser().getUsername());
                            kpr.setStatus("true");
                            kpr.setTglBatal("2000-01-01 00:00:00");
                            kpr.setUserBatal("");
                            kpr.setCustomer(x.skl.getCustomer());
                            kpr.setProperty(x.skl.getProperty());
                            kpr.setSkl(x.skl);
                            return Service.saveTerimaPencairanKPR(con, kpr);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    try{
                        mainApp.closeLoading();
                        getKPR();
                        if(task.getValue().equals("true")){
                            mainApp.showMessage(Modality.NONE, "Success", "Terima Pencairan KPR berhasil disimpan");
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
    private void detailTerimaPencairanKPR(KPR kpr){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailTerimaPencairanKPR.fxml");
        DetailTerimaPencairanKPRController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setKPR(kpr);
    }
    private void batalTerimaPencairanKPR(KPR kpr){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal terima pencairan KPR " + kpr.getNoKPR()+ ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        kpr.setTglBatal(tglSql.format(new Date()));
                        kpr.setUserBatal(sistem.getUser().getUsername());
                        kpr.setStatus("false");
                        return Service.batalTerimaPencairanKPR(con, kpr);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                try{
                    mainApp.closeLoading();
                    getKPR();
                    if (task.getValue().equals("true")) 
                        mainApp.showMessage(Modality.NONE, "Success", "Data terima pencairan KPR berhasil dibatal");
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
