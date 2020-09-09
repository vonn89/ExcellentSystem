/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.PembelianHeadDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembelianHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPembelianSupplierController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PembelianSupplierController;
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
public class DataPembelianSupplierController  {

    @FXML private TableView<PembelianHead> pembelianHeadTable;
    @FXML private TableColumn<PembelianHead, String> noPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> tglPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> supplierColumn;
    @FXML private TableColumn<PembelianHead, Number> totalBeratColumn;
    @FXML private TableColumn<PembelianHead, Number> totalHargaPersenColumn;
    @FXML private TableColumn<PembelianHead, Number> hargaEmasColumn;
    @FXML private TableColumn<PembelianHead, Number> totalPembelianColumn;
    @FXML private TableColumn<PembelianHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    @FXML private Label totalPembelianLabel;
    private Main mainApp;   
    private ObservableList<PembelianHead> allPembelian = FXCollections.observableArrayList();
    private ObservableList<PembelianHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().noPembelianProperty());
        tglPembelianColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPembelian())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPembelianColumn.setComparator(Function.sortDate(tglLengkap));
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalHargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalHargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        hargaEmasColumn.setCellValueFactory(cellData -> cellData.getValue().hargaEmasProperty());
        hargaEmasColumn.setCellFactory(col -> getTableCell(rp));
        totalPembelianColumn.setCellValueFactory(cellData -> cellData.getValue().totalPembelianProperty());
        totalPembelianColumn.setCellFactory(col -> getTableCell(rp));
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
        MenuItem addNew = new MenuItem("New Pembelian Supplier");
        addNew.setOnAction((ActionEvent e) -> {
            newPembelian();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPembelian();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        pembelianHeadTable.setContextMenu(rowMenu);
        pembelianHeadTable.setRowFactory(table -> {
            TableRow<PembelianHead> row = new TableRow<PembelianHead>() {
                @Override
                public void updateItem(PembelianHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Pembelian Supplier");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPembelian();
                        });
                        MenuItem detail = new MenuItem("Detail Pembelian Supplier");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPembelianPusat(item);
                        });
                        MenuItem batal = new MenuItem("Batal Pembelian Supplier");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPembelian(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPembelian();
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
                        detailPembelianPusat(row.getItem());
                }
            });
            return row;
        });
        allPembelian.addListener((ListChangeListener.Change<? extends PembelianHead> change) -> {
            searchPembelian();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPembelian();
        });
        filterData.addAll(allPembelian);
        pembelianHeadTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getPembelian();
    } 
    @FXML
    private void getPembelian(){
        Task<List<PembelianHead>> task = new Task<List<PembelianHead>>() {
            @Override 
            public List<PembelianHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return PembelianHeadDAO.getAllByDateAndSupplierAndStatus(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPembelian.clear();
            allPembelian.addAll(task.getValue());
            pembelianHeadTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
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
    private void searchPembelian() {
        try{
            filterData.clear();
            for (PembelianHead a : allPembelian) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
    
                    if(checkColumn(a.getNoPembelian())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglPembelian())))||
                        checkColumn(a.getSupplier())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(rp.format(a.getTotalBerat()))||
                        checkColumn(rp.format(a.getTotalHargaPersen()))||
                        checkColumn(rp.format(a.getHargaEmas()))||
                        checkColumn(rp.format(a.getTotalPembelian())))
                        filterData.add(a);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalHargaPersen = 0;
        double totalPembelian = 0;
        for(PembelianHead p : filterData){
            totalBerat = totalBerat + p.getTotalBerat();
            totalHargaPersen = totalHargaPersen + p.getTotalHargaPersen();
            totalPembelian = totalPembelian + p.getTotalPembelian();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
        totalPembelianLabel.setText(rp.format(totalPembelian));
    }
    private void newPembelian(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PembelianSupplier.fxml");
        PembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.supplierCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Supplier belum dipilih");
            else if(controller.listPembelianDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            PembelianHead p = new PembelianHead();
                            p.setNoPembelian(PembelianHeadDAO.getId(conPusat));
                            p.setTglPembelian(Function.getSystemDate());
                            p.setSupplier(controller.supplierCombo.getSelectionModel().getSelectedItem());
                            int i =1;
                            double totalBerat = 0;
                            double totalHargaPersen = 0;
                            for(PembelianDetail d : controller.listPembelianDetail){
                                d.setNoPembelian(p.getNoPembelian());
                                d.setNoUrut(i);
                                
                                totalBerat = totalBerat + d.getBerat();
                                totalHargaPersen = totalHargaPersen + d.getTotalHarga();
                                i++;
                            }
                            p.setListPembelianDetail(controller.listPembelianDetail);
                            p.setTotalBerat(totalBerat);
                            p.setTotalHargaPersen(totalHargaPersen);
                            p.setHargaEmas(sistem.getHargaEmas());
                            p.setTotalPembelian(Function.pembulatan(totalHargaPersen*sistem.getHargaEmas()));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian supplier berhasil disimpan");
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
    private void detailPembelianPusat(PembelianHead p){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPembelianSupplier.fxml");
        DetailPembelianSupplierController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPembelian(p);
    }
    private void batalPembelian(PembelianHead p){
        if(p.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Pembelian supplier tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal pembelian supplier "+p.getNoPembelian()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            p.setStatus("false");
                            p.setTglBatal(Function.getSystemDate());
                            p.setUserBatal(user.getKodeUser());
                            return Service.saveBatalPembelianSupplier(conPusat, p);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPembelian();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Pembelian supplier berhasil dibatal");
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
