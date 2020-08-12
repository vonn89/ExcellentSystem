/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.View;

import com.excellentsystem.sentralindahperdana.DAO.ReturPembelianHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.SupplierDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Otoritas;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianHead;
import com.excellentsystem.sentralindahperdana.Model.Supplier;
import com.excellentsystem.sentralindahperdana.Service.Service;
import com.excellentsystem.sentralindahperdana.View.Dialog.MessageController;
import com.excellentsystem.sentralindahperdana.View.Dialog.NewReturPembelianController;
import java.sql.Connection;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
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
public class ReturPembelianController  {

    @FXML private TableView<ReturPembelianHead> returPembelianTable;
    @FXML private TableColumn<ReturPembelianHead, String> noReturPembelianColumn;
    @FXML private TableColumn<ReturPembelianHead, String> tglReturPembelianColumn;
    @FXML private TableColumn<ReturPembelianHead, String> kodeSupplierColumn;
    @FXML private TableColumn<ReturPembelianHead, String> namaSupplierColumn;
    @FXML private TableColumn<ReturPembelianHead, String> alamatSupplierColumn;
    @FXML private TableColumn<ReturPembelianHead, Number> totalReturColumn;
    @FXML private TableColumn<ReturPembelianHead, String> tipeKeuanganColumn;
    @FXML private TableColumn<ReturPembelianHead, String> catatanColumn;
    @FXML private TableColumn<ReturPembelianHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private Label totalReturPembelianField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<ReturPembelianHead> allReturPembelian = FXCollections.observableArrayList();
    private ObservableList<ReturPembelianHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noReturPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noReturProperty());
        kodeSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().kodeSupplierProperty());
        namaSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().namaProperty());
        alamatSupplierColumn.setCellValueFactory(cellData -> cellData.getValue().getSupplier().alamatProperty());
        tipeKeuanganColumn.setCellValueFactory(cellData -> cellData.getValue().tipeKeuanganProperty());
        catatanColumn.setCellValueFactory(cellData -> cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        tglReturPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRetur())));
            } catch (Exception ex) {
                return null;
            }
        });
        totalReturColumn.setCellValueFactory(cellData -> cellData.getValue().totalReturProperty());
        totalReturColumn.setCellFactory(col -> Function.getTableCell());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        
        final ContextMenu rm = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Retur Pembelian");
        addNew.setOnAction((ActionEvent e)->{
            newReturPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getReturPembelian();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Retur Pembelian")&&o.isStatus())
                rm.getItems().add(addNew);
        }
        rm.getItems().addAll(refresh);
        returPembelianTable.setContextMenu(rm);
        returPembelianTable.setRowFactory((TableView<ReturPembelianHead> tableView) -> {
            final TableRow<ReturPembelianHead> row = new TableRow<ReturPembelianHead>(){
                @Override
                public void updateItem(ReturPembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Retur Pembelian");
                        addNew.setOnAction((ActionEvent e)->{
                            newReturPembelian();
                        });
                        MenuItem detail = new MenuItem("Lihat Detail Retur Pembelian");
                        detail.setOnAction((ActionEvent e)->{
                            lihatDetailReturPembelian(item);
                        });
                        MenuItem batal = new MenuItem("Batal Retur Pembelian");
                        batal.setOnAction((ActionEvent e)->{
                            batalReturPembelian(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getReturPembelian();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Retur Pembelian")&&o.isStatus())
                                rm.getItems().add(addNew);
                            if(o.getJenis().equals("Lihat Detail Retur Pembelian")&&o.isStatus())
                                rm.getItems().add(detail);
                            if(o.getJenis().equals("Batal Retur Pembelian")&&o.isStatus())
                                rm.getItems().add(batal);
                        }
                        rm.getItems().add(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2)
                    if(row.getItem()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Lihat Detail Retur Pembelian")&&o.isStatus())
                                lihatDetailReturPembelian(row.getItem());
                        }
                    }
            });
            return row;
        });
        allReturPembelian.addListener((ListChangeListener.Change<? extends ReturPembelianHead> change) -> {
            searchReturPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchReturPembelian();
        });
        filterData.addAll(allReturPembelian);
    }  
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        getReturPembelian();
        returPembelianTable.setItems(filterData);
    }
    @FXML
    private void getReturPembelian(){
        Task<List<ReturPembelianHead>> task = new Task<List<ReturPembelianHead>>() {
            @Override 
            public List<ReturPembelianHead> call() throws Exception{
                try (Connection con = Koneksi.getConnection()){
                    List<ReturPembelianHead> listRetur = ReturPembelianHeadDAO.getAllByTanggalAndStatus(
                        con, tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    List<Supplier> allSupplier = SupplierDAO.getAllByStatus(con, "%");
                    for(ReturPembelianHead r : listRetur){
                        for(Supplier s : allSupplier){
                            if(s.getKodeSupplier().equals(r.getKodeSupplier()))
                                r.setSupplier(s);
                        }
                    }
                    return listRetur;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            try{
                mainApp.closeLoading();
                allReturPembelian.clear();
                allReturPembelian.addAll(task.get());
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
    private void searchReturPembelian() {
        try{
            filterData.clear();
            for (ReturPembelianHead temp : allReturPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoRetur())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglRetur())))||
                        checkColumn(temp.getSupplier().getKodeSupplier())||
                        checkColumn(temp.getSupplier().getNama())||
                        checkColumn(temp.getSupplier().getAlamat())||
                        checkColumn(df.format(temp.getTotalRetur()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser())||
                        checkColumn(temp.getTipeKeuangan()))
                            filterData.add(temp);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double total = 0;
        for(ReturPembelianHead temp : filterData){
            total = total + temp.getTotalRetur();
        }
        totalReturPembelianField.setText(df.format(total));
    }
    private void newReturPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewReturPembelian.fxml");
        NewReturPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setNewReturPembelian();
    }
    private void lihatDetailReturPembelian(ReturPembelianHead r){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/NewReturPembelian.fxml");
        NewReturPembelianController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setDetailReturPembelian(r.getNoRetur());
    }
    private void batalReturPembelian(ReturPembelianHead r){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
            "Batal retur pembelian "+r.getNoRetur()+" ?");
        controller.OK.setOnAction((ActionEvent e) -> {
            mainApp.closeMessage();
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        r.setTglBatal(tglSql.format(new Date()));
                        r.setUserBatal(sistem.getUser().getUsername());
                        r.setStatus("false");
                        return Service.saveBatalReturPembelian(con, r);
                    }
                }
            };
            task.setOnRunning((ex) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent ex) -> {
                try{
                    mainApp.closeLoading();
                    if(task.get().equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Data retur pembelian berhasil dibatal");
                        getReturPembelian();
                    }else{
                        mainApp.showMessage(Modality.NONE, "Error", task.get());
                    }
                }catch(Exception exc){
                    mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                }
            });
            task.setOnFailed((ex) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
}
