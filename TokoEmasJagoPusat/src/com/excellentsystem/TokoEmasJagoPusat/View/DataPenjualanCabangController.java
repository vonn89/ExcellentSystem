/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCabangHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPenjualanCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanCabangController;
import java.sql.Connection;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
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
 * @author Excellent
 */
public class DataPenjualanCabangController  {

    @FXML private TableView<PenjualanCabangHead> penjualanCabangHeadTable;
    @FXML private TableColumn<PenjualanCabangHead, String> noPenjualanCabangColumn;
    @FXML private TableColumn<PenjualanCabangHead, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanCabangHead, String> kodeCabangColumn;
    @FXML private TableColumn<PenjualanCabangHead, Number> totalQtyColumn;
    @FXML private TableColumn<PenjualanCabangHead, Number> totalBeratColumn;
    @FXML private TableColumn<PenjualanCabangHead, Number> totalHargaPersenColumn;
    @FXML private TableColumn<PenjualanCabangHead, Number> hargaEmasColumn;
    @FXML private TableColumn<PenjualanCabangHead, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanCabangHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    @FXML private Label totalPenjualanLabel;
    private Main mainApp;   
    private ObservableList<PenjualanCabangHead> allPenjualan = FXCollections.observableArrayList();
    private ObservableList<PenjualanCabangHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanCabangColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanCabangProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalHargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaEmasColumn.setCellValueFactory(cellData -> cellData.getValue().hargaEmasProperty());
        hargaEmasColumn.setCellFactory(col -> getTableCell(rp));
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(rp));
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Penjualan Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newPenjualan();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualan();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        penjualanCabangHeadTable.setContextMenu(rowMenu);
        penjualanCabangHeadTable.setRowFactory(table -> {
            TableRow<PenjualanCabangHead> row = new TableRow<PenjualanCabangHead>() {
                @Override
                public void updateItem(PenjualanCabangHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Penjualan Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenjualan();
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualanCabang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Penjualan Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPenjualan(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualan();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPenjualanCabang(row.getItem());
                }
            });
            return row;
        });
        allPenjualan.addListener((ListChangeListener.Change<? extends PenjualanCabangHead> change) -> {
            searchPenjualan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualan();
        });
        filterData.addAll(allPenjualan);
        penjualanCabangHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            this.mainApp = mainApp;
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            allCabang.clear();
            allCabang.add("Semua");
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                allCabang.addAll(c.getKodeCabang());
            }
            kodeCabangCombo.setItems(allCabang);
            kodeCabangCombo.getSelectionModel().select("Semua");
            getPenjualan();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getPenjualan(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<PenjualanCabangHead>> task = new Task<List<PenjualanCabangHead>>() {
                @Override 
                public List<PenjualanCabangHead> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        String kodeCabang = "%";
                        if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                            kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                        return PenjualanCabangHeadDAO.getAllByDateAndCabangAndStatus(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang, "true");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                allPenjualan.clear();
                allPenjualan.addAll(task.getValue());
                penjualanCabangHeadTable.refresh();
            });
            task.setOnFailed((e) -> {
                task.getException().printStackTrace();
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
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
            for (PenjualanCabangHead a : allPenjualan) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
    
                    if(checkColumn(a.getNoPenjualanCabang())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglPenjualan())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(rp.format(a.getTotalQty()))||
                        checkColumn(rp.format(a.getTotalBerat()))||
                        checkColumn(rp.format(a.getTotalHargaPersen()))||
                        checkColumn(rp.format(a.getHargaEmas()))||
                        checkColumn(rp.format(a.getTotalPenjualan())))
                        filterData.add(a);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalQty = 0;
        double totalBerat = 0;
        double totalHargaPersen = 0;
        double totalPenjualan = 0;
        for(PenjualanCabangHead p : filterData){
            totalQty = totalQty + p.getTotalQty();
            totalBerat = totalBerat + p.getTotalBerat();
            totalHargaPersen = totalHargaPersen + p.getTotalHargaPersen();
            totalPenjualan = totalPenjualan + p.getTotalPenjualan();
        }
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    private void newPenjualan(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PenjualanCabang.fxml");
        PenjualanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.cabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.listPenjualanDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            PenjualanCabangHead p = new PenjualanCabangHead();
                            p.setNoPenjualanCabang(PenjualanCabangHeadDAO.getId(conPusat));
                            p.setTglPenjualan(Function.getSystemDate());
                            p.setKodeCabang(controller.cabangCombo.getSelectionModel().getSelectedItem());
                            int i =1;
                            int totalQty = 0;
                            double totalBerat = 0;
                            double totalHargaPersen = 0;
                            for(PenjualanCabangDetail d : controller.listPenjualanDetail){
                                d.setNoPenjualanCabang(p.getNoPenjualanCabang());
                                d.setNoUrut(i);
                                
                                totalQty = totalQty + d.getQty();
                                totalBerat = totalBerat + d.getBerat();
                                totalHargaPersen = totalHargaPersen + d.getTotalHargaPersen();
                                i++;
                            }
                            p.setListDetail(controller.listPenjualanDetail);
                            p.setTotalQty(totalQty);
                            p.setTotalBerat(totalBerat);
                            p.setTotalHargaPersen(totalHargaPersen);
                            p.setHargaEmas(sistem.getHargaEmas());
                            p.setTotalPenjualan(Function.pembulatan(totalHargaPersen*sistem.getHargaEmas()));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePenjualanCabang(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void detailPenjualanCabang(PenjualanCabangHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPenjualanCabang.fxml");
        DetailPenjualanCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPenjualanCabang(p);
    }
    private void batalPenjualan(PenjualanCabangHead p){
        if(p.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan cabang tidak dapat dibatal, karena sudah pernah diterima");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal penjualan cabang "+p.getNoPenjualanCabang()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            p.setStatus("false");
                            p.setTglBatal(Function.getSystemDate());
                            p.setUserBatal(user.getKodeUser());
                            return Service.saveBatalPenjualanCabang(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualan();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan cabang berhasil dibatal");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            });
        }
    }
    
}
