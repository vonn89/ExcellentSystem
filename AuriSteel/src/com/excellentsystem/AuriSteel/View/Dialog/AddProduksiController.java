/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.ProduksiHeadDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
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
 * @author Xtreme
 */
public class AddProduksiController  {

    @FXML public TableView<ProduksiHead> produksiHeadTable;
    @FXML private TableColumn<ProduksiHead, String> noProduksiColumn;
    @FXML private TableColumn<ProduksiHead, String> tglProduksiColumn;
    @FXML private TableColumn<ProduksiHead, String> kodeGudangColumn;
    @FXML private TableColumn<ProduksiHead, String> jenisProduksiColumn;
    @FXML private TableColumn<ProduksiHead, Number> materialCostColumn;
    @FXML private TableColumn<ProduksiHead, Number> totalBebanProduksiColumn;
    @FXML private TableColumn<ProduksiHead, String> catatanColumn;
    @FXML private TableColumn<ProduksiHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglMulaiPicker;
    @FXML private DatePicker tglAkhirPicker;
    private ObservableList<ProduksiHead> allProduksi = FXCollections.observableArrayList();
    private ObservableList<ProduksiHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public void initialize() {
        noProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().kodeProduksiProperty());
        tglProduksiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglProduksi())));
            } catch (Exception ex) {
                return null;
            }
        });
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        jenisProduksiColumn.setCellValueFactory(cellData -> cellData.getValue().jenisProduksiProperty());
        materialCostColumn.setCellValueFactory(cellData ->cellData.getValue().materialCostProperty());
        materialCostColumn.setCellFactory(col -> Function.getTableCell());
        totalBebanProduksiColumn.setCellValueFactory(cellData ->cellData.getValue().biayaProduksiProperty());
        totalBebanProduksiColumn.setCellFactory(col -> Function.getTableCell());
        catatanColumn.setCellValueFactory(cellData ->cellData.getValue().catatanProperty());
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        
        tglMulaiPicker.setConverter(Function.getTglConverter());
        tglMulaiPicker.setValue(LocalDate.now().minusWeeks(1));
        tglMulaiPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellMulai(tglAkhirPicker));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellAkhir(tglMulaiPicker));
        allProduksi.addListener((ListChangeListener.Change<? extends ProduksiHead> change) -> {
            searchProduksi();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchProduksi();
        });
        filterData.addAll(allProduksi);
        produksiHeadTable.setItems(filterData);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        getProduksi();
    }
    @FXML
    private void getProduksi(){
        Task<List<ProduksiHead>> task = new Task<List<ProduksiHead>>() {
            @Override 
            public List<ProduksiHead> call()throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return ProduksiHeadDAO.getAllByDateAndJenisProduksiAndStatus(con, 
                            tglMulaiPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),"%", "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allProduksi.clear();
            allProduksi.addAll(task.getValue());
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
    private void searchProduksi() {
        try{
            filterData.clear();
            for (ProduksiHead temp : allProduksi) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(temp);
                else{
                    if(checkColumn(temp.getKodeProduksi())||
                        checkColumn(tglLengkap.format(tglSql.parse(temp.getTglProduksi())))||
                        checkColumn(temp.getKodeGudang())||
                        checkColumn(temp.getJenisProduksi())||
                        checkColumn(df.format(temp.getBiayaProduksi()))||
                        checkColumn(df.format(temp.getMaterialCost()))||
                        checkColumn(temp.getCatatan())||
                        checkColumn(temp.getKodeUser()))
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
