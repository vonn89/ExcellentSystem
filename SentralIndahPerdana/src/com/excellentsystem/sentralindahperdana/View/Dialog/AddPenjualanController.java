/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddPenjualanController {

    @FXML public TableView<PenjualanHead> penjualanHeadTable;
    @FXML private TableColumn<PenjualanHead, String> noPenjualanHeadColumn;
    @FXML private TableColumn<PenjualanHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanHead, String> namaCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> alamatCustomerColumn;
    @FXML private TableColumn<PenjualanHead, String> namaProyekColumn;
    @FXML private TableColumn<PenjualanHead, String> lokasiPengerjaanColumn;
    @FXML private TableColumn<PenjualanHead, String> catatanColumn;
    @FXML private TableColumn<PenjualanHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private String status = "open";
    private ObservableList<PenjualanHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noPenjualanHeadColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        namaCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().namaProperty());
        alamatCustomerColumn.setCellValueFactory(cellData -> cellData.getValue().getCustomer().alamatProperty());
        namaProyekColumn.setCellValueFactory(cellData ->cellData.getValue().namaProyekProperty());
        lokasiPengerjaanColumn.setCellValueFactory(cellData ->cellData.getValue().lokasiPengerjaanProperty());
        catatanColumn.setCellValueFactory(cellData ->cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusMonths(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanHeadTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()-80);
        stage.setWidth(mainApp.screenSize.getWidth()-80);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getPenjualan();
    }
    @FXML
    private void getPenjualan(){
        Task<List<PenjualanHead>> task = new Task<List<PenjualanHead>>() {
            @Override 
            public List<PenjualanHead> call()throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<PenjualanHead> allPenjualan = PenjualanHeadDAO.getAllByTglPenjualan(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    List<Customer> allCustomer = CustomerDAO.getAllByStatus(con, "%");
                    for(PenjualanHead p : allPenjualan){
                        for(Customer c: allCustomer){
                            if(p.getKodeCustomer().equals(c.getKodeCustomer()))
                                p.setCustomer(c);
                        }
                    }
                    return allPenjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            try{
                mainApp.closeLoading();
                allPenjualan.clear();
                allPenjualan.addAll(task.get());
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
    private void searchPenjualan() {
        try{
            filterData.clear();
            for (PenjualanHead temp : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenjualan())))||
                        checkColumn(temp.getKodeCustomer())||
                        checkColumn(temp.getCustomer().getNama())||
                        checkColumn(temp.getCustomer().getAlamat())||
                        checkColumn(temp.getNamaProyek())||
                        checkColumn(temp.getLokasiPengerjaan())||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser())||
                        checkColumn(temp.getTglBatal())||
                        checkColumn(temp.getUserBatal()))
                        filterData.add(temp);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }
}
