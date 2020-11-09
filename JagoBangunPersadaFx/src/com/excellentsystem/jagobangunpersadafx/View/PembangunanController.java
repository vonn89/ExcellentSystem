/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.View;

import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanDetailDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PembangunanHeadDAO;
import com.excellentsystem.jagobangunpersadafx.DAO.PropertyDAO;
import com.excellentsystem.jagobangunpersadafx.Function;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellAkhir;
import static com.excellentsystem.jagobangunpersadafx.Function.getDateCellMulai;
import static com.excellentsystem.jagobangunpersadafx.Function.getTableCell;
import com.excellentsystem.jagobangunpersadafx.Koneksi;
import com.excellentsystem.jagobangunpersadafx.Main;
import static com.excellentsystem.jagobangunpersadafx.Main.qty;
import static com.excellentsystem.jagobangunpersadafx.Main.rp;
import static com.excellentsystem.jagobangunpersadafx.Main.sistem;
import static com.excellentsystem.jagobangunpersadafx.Main.tglLengkap;
import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Otoritas;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanDetail;
import com.excellentsystem.jagobangunpersadafx.Model.PembangunanHead;
import com.excellentsystem.jagobangunpersadafx.Model.Property;
import com.excellentsystem.jagobangunpersadafx.Service.Service;
import com.excellentsystem.jagobangunpersadafx.View.Dialog.DetailPembangunanController;
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
public class PembangunanController  {
    @FXML private TableView<PembangunanHead> pembangunanTable;
    @FXML private TableColumn<PembangunanHead, String> noPembangunanColumn;
    @FXML private TableColumn<PembangunanHead, String> tglPembangunanColumn;
    @FXML private TableColumn<PembangunanHead, String> kategoriColumn;
    @FXML private TableColumn<PembangunanHead, String> keteranganColumn;
    @FXML private TableColumn<PembangunanHead, Number> totalPropertyColumn;
    @FXML private TableColumn<PembangunanHead, Number> totalLuasTanahColumn;
    @FXML private TableColumn<PembangunanHead, Number> totalBiayaColumn;
    @FXML private TableColumn<PembangunanHead, String> metodeColumn;
    @FXML private TableColumn<PembangunanHead, String> tipeKeuanganColumn;
    @FXML private TableColumn<PembangunanHead, String> kodeUserColumn;
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private Label totalPembangunan;
    private ObservableList<PembangunanHead> allPembangunan = FXCollections.observableArrayList();
    private ObservableList<PembangunanHead> filterData = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        noPembangunanColumn.setCellValueFactory(cellData -> cellData.getValue().noPembangunanProperty());
        noPembangunanColumn.setCellFactory(col -> Function.getWrapTableCell(noPembangunanColumn));
        
        tglPembangunanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembangunan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPembangunanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPembangunanColumn));
        tglPembangunanColumn.setComparator(Function.sortDate(tglLengkap));
        
        kategoriColumn.setCellValueFactory(cellData ->cellData.getValue().kategoriProperty());
        kategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kategoriColumn));
        
        keteranganColumn.setCellValueFactory(cellData ->cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));
        
        totalPropertyColumn.setCellValueFactory(cellData ->cellData.getValue().totalPropertyProperty());
        totalPropertyColumn.setCellFactory(col -> getTableCell(qty));
        
        totalLuasTanahColumn.setCellValueFactory(cellData ->cellData.getValue().totalLuasTanahProperty());
        totalLuasTanahColumn.setCellFactory(col -> getTableCell(qty));
        
        totalBiayaColumn.setCellValueFactory(cellData ->cellData.getValue().totalBiayaProperty());
        totalBiayaColumn.setCellFactory(col -> getTableCell(rp));
        
        metodeColumn.setCellValueFactory(cellData ->cellData.getValue().metodeProperty());
        metodeColumn.setCellFactory(col -> Function.getWrapTableCell(metodeColumn));
        
        tipeKeuanganColumn.setCellValueFactory(cellData ->cellData.getValue().tipeKeuanganProperty());
        tipeKeuanganColumn.setCellFactory(col -> Function.getWrapTableCell(tipeKeuanganColumn));
        
        kodeUserColumn.setCellValueFactory(cellData ->cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.now().minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellMulai(tglAkhirPicker));
        
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.now());
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> getDateCellAkhir(tglAwalPicker));
        
        allPembangunan.addListener((ListChangeListener.Change<? extends PembangunanHead> change) -> {
            searchPembangunan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembangunan();
        });
        filterData.addAll(allPembangunan);
        ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("Add New Pembangunan");
        addNew.setOnAction((ActionEvent e)->{
            addNewPembangunan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getPembangunan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Add New Pembangunan")&&o.isStatus())
                rowMenu.getItems().add(addNew);
        }
        rowMenu.getItems().addAll(refresh);
        pembangunanTable.setContextMenu(rowMenu);
        pembangunanTable.setRowFactory(tv -> {
            TableRow<PembangunanHead> row = new TableRow<PembangunanHead>() {
                @Override
                public void updateItem(PembangunanHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{ 
                        ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("Add New Pembangunan");
                        addNew.setOnAction((ActionEvent e)->{
                            addNewPembangunan();
                        });
                        MenuItem batal = new MenuItem("Batal Pembangunan");
                        batal.setOnAction((ActionEvent event) -> {
                            deletePembangunan(item);
                        });
                        MenuItem detail = new MenuItem("Detail Pembangunan");
                        detail.setOnAction((ActionEvent event) -> {
                            detailPembangunan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getPembangunan();
                        });
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Add New Pembangunan")&&o.isStatus())
                                rowMenu.getItems().add(addNew);
                            if(o.getJenis().equals("Detail Pembangunan")&&o.isStatus())
                                rowMenu.getItems().add(detail);
                            if(o.getJenis().equals("Batal Pembangunan")&&o.isStatus())
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
                        if(o.getJenis().equals("Detail Pembangunan")&&o.isStatus())
                            detailPembangunan(row.getItem());
                    }
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getPembangunan();
        pembangunanTable.setItems(filterData);
    }
    @FXML
    private void getPembangunan(){
        Task<List<PembangunanHead>> task = new Task<List<PembangunanHead>>() {
            @Override 
            public List<PembangunanHead> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    List<PembangunanHead> listPembangunan = PembangunanHeadDAO.getAllByDateAndStatus(
                            con, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                    return listPembangunan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allPembangunan.clear();
            allPembangunan.addAll(task.getValue());
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
    private void searchPembangunan() {
        filterData.clear();
        for (PembangunanHead temp : allPembangunan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getNoPembangunan())||
                    checkColumn(temp.getTglPembangunan())||
                    checkColumn(temp.getKategori())||
                    checkColumn(temp.getTipeKeuangan())||
                    checkColumn(temp.getMetode())||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(qty.format(temp.getTotalProperty()))||
                    checkColumn(qty.format(temp.getTotalLuasTanah()))||
                    checkColumn(rp.format(temp.getTotalBiaya()))||
                    checkColumn(temp.getKodeUser()))
                        filterData.add(temp);
            }
        }
        hitungTotal();
    }
    private void hitungTotal(){
        double total = 0;
        for(PembangunanHead pt : filterData){
            total = total + pt.getTotalBiaya();
        }
        totalPembangunan.setText(rp.format(total));
    }
    @FXML
    private void addNewPembangunan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembangunan.fxml");
        DetailPembangunanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.setNewPembangunan();
        x.saveButton.setOnAction((ActionEvent ev)->{
            if("".equals(x.totalBiayaAkhirLabel.getText())||"0".equals(x.totalBiayaAkhirLabel.getText())){
                mainApp.showMessage(Modality.NONE, "Warning", "Total biaya masih kosong");
            }else if(x.kategoriCombo.getSelectionModel().getSelectedItem()==null||
                    "".equals(x.kategoriCombo.getSelectionModel().getSelectedItem())){
                mainApp.showMessage(Modality.NONE, "Warning", "Kategori pembangunan belum dipilih");
            }else if(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem()==null||
                    "".equals(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem())){
                mainApp.showMessage(Modality.NONE, "Warning", "Tipe Keuangan belum dipilih");
            }else{
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            PembangunanHead p = new PembangunanHead();
                            p.setNoPembangunan(PembangunanHeadDAO.getId(con));
                            p.setTglPembangunan(tglSql.format(new Date()));
                            p.setKategori(x.kategoriCombo.getSelectionModel().getSelectedItem());
                            p.setKeterangan(x.keteranganField.getText());
                            p.setTotalProperty(Double.parseDouble(x.totalPropertyLabel.getText().replaceAll(",", "")));
                            p.setTotalLuasTanah(Double.parseDouble(x.totalLuasTanahLabel.getText().replaceAll(",", "")));
                            p.setTotalBiaya(Double.parseDouble(x.totalBiayaAkhirLabel.getText().replaceAll(",", "")));
                            p.setMetode(x.metodeCombo.getSelectionModel().getSelectedItem());
                            p.setTipeKeuangan(x.tipeKeuanganCombo.getSelectionModel().getSelectedItem());
                            p.setKodeUser(sistem.getUser().getUsername());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            List<PembangunanDetail> detail = new ArrayList<>();
                            for(PembangunanDetail d : x.allDetail){
                                d.setNoPembangunan(p.getNoPembangunan());
                                if(d.isStatus())
                                    detail.add(d);
                            }
                            p.setAllDetail(detail);
                            return Service.savePembangunan(con, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent e) -> {
                    mainApp.closeLoading();
                    getPembangunan();
                    if(task.getValue().equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success","Pembangunan berhasil disimpan");
                    }else{
                        mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
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
    private void detailPembangunan(PembangunanHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailPembangunan.fxml");
        DetailPembangunanController x = loader.getController();
        x.setMainApp(mainApp, mainApp.MainStage, stage);
        x.getPembangunan(p.getNoPembangunan());
    }
    private void deletePembangunan(PembangunanHead p){
        MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Batal pembangunan " + p.getNoPembangunan()+ ", anda yakin ?");
        controller.OK.setOnAction((ActionEvent ev) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        p.setAllDetail(PembangunanDetailDAO.getAllByNo(con, p.getNoPembangunan()));
                        Boolean statusTanah = true;
                        for(PembangunanDetail detail : p.getAllDetail()){
                            Property prop = PropertyDAO.get(con, detail.getKodeProperty());
                            if(prop.getStatus().equals("Mapped")||
                                    prop.getStatus().equals("Combined")||
                                    prop.getStatus().equals("Sold"))
                                statusTanah = false;
                        }
                        if(statusTanah){
                            p.setTglBatal(tglSql.format(new Date()));
                            p.setUserBatal(sistem.getUser().getUsername());
                            p.setStatus("false");
                            return Service.batalPembangunan(con, p);
                        }else 
                            return "Pembangunan tidak dapat dibatalkan, karena status tanah sudah terjual/sudah dipecah/sudah digabung";
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((WorkerStateEvent e) -> {
                mainApp.closeLoading();
                getPembangunan();
                if (task.getValue().equals("true")) 
                    mainApp.showMessage(Modality.NONE, "Success", "Data Pembangunan berhasil dibatal");
                else 
                    mainApp.showMessage(Modality.NONE, "Failed", task.getValue());
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    
}
