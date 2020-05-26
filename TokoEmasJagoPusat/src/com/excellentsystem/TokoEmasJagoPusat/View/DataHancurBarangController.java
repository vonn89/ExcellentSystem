/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.HancurHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailHancurBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.HancurBarangCabangController;
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
public class DataHancurBarangController  {

    @FXML private TableView<HancurHead> hancurTable;
    @FXML private TableColumn<HancurHead, String> noHancurColumn;
    @FXML private TableColumn<HancurHead, String> tglHancurColumn;
    @FXML private TableColumn<HancurHead, String> kodeCabangColumn;
    @FXML private TableColumn<HancurHead, Number> totalQtyColumn;
    @FXML private TableColumn<HancurHead, Number> totalBeratColumn;
    @FXML private TableColumn<HancurHead, Number> totalBeratAsliColumn;
    @FXML private TableColumn<HancurHead, Number> totalBeratPersenColumn;
    @FXML private TableColumn<HancurHead, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratPersenLabel;
    
    private Main mainApp;   
    private ObservableList<HancurHead> allHancurBarang = FXCollections.observableArrayList();
    private ObservableList<HancurHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noHancurColumn.setCellValueFactory(cellData -> cellData.getValue().noHancurProperty());
        tglHancurColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglHancur())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglHancurColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratAsliProperty());
        totalBeratAsliColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratPersenProperty());
        totalBeratPersenColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Hancur Barang");
        addNew.setOnAction((ActionEvent e) -> {
            newHancurBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getHancurBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        hancurTable.setContextMenu(rowMenu);
        hancurTable.setRowFactory(table -> {
            TableRow<HancurHead> row = new TableRow<HancurHead>() {
                @Override
                public void updateItem(HancurHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Hancur Barang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newHancurBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Hancur Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailHancurBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getHancurBarang();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailHancurBarang(row.getItem());
                }
            });
            return row;
        });
        allHancurBarang.addListener((ListChangeListener.Change<? extends HancurHead> change) -> {
            searchHancurBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchHancurBarang();
        });
        filterData.addAll(allHancurBarang);
        hancurTable.setItems(filterData);
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
            getHancurBarang();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getHancurBarang(){
        Task<List<HancurHead>> task = new Task<List<HancurHead>>() {
            @Override 
            public List<HancurHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String kodeCabang = "%";
                    if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                    return HancurHeadDAO.getAllByDateAndCabangAndStatus(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), kodeCabang,  "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allHancurBarang.clear();
            allHancurBarang.addAll(task.getValue());
            hancurTable.refresh();
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
    private void searchHancurBarang() {
        try{
            filterData.clear();
            for (HancurHead a : allHancurBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoHancur())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglHancur())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(rp.format(a.getTotalQty()))||
                        checkColumn(gr.format(a.getTotalBerat()))||
                        checkColumn(gr.format(a.getTotalBeratAsli()))||
                        checkColumn(gr.format(a.getTotalBeratPersen())))
                        filterData.add(a);
                }
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        double totalBeratPersen = 0;
        for(HancurHead p : filterData){
            totalQty = totalQty + p.getTotalQty();
            totalBerat = totalBerat + p.getTotalBerat();
            totalBeratPersen = totalBeratPersen + p.getTotalBeratPersen();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
    }
    private void newHancurBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/HancurBarangCabang.fxml");
        HancurBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.cabang==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Cabang belum dipilih");
            }else if(controller.listHancurDetail.isEmpty()){
                mainApp.showMessage(Modality.NONE, "Warning", "Barang masih kosong");
            }else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            try(Connection conCabang = KoneksiCabang.getConnection(controller.cabang)){
                                HancurHead h = new HancurHead();
                                h.setNoHancur(HancurHeadDAO.getId(conPusat));
                                h.setTglHancur(Function.getSystemDate());
                                h.setKodeCabang(controller.cabang.getKodeCabang());
                                h.setKodeUser(user.getKodeUser());
                                h.setStatus("true");
                                h.setTglBatal("2000-01-01 00:00:00");
                                h.setUserBatal("");

                                int totalQty = 0;
                                double totalBerat = 0;
                                double totalBeratAsli = 0;
                                double totalBeratPersen = 0;
                                for(HancurDetail d : controller.listHancurDetail){
                                    d.setNoHancur(h.getNoHancur());
                                    totalQty = totalQty + 1;
                                    totalBerat = totalBerat + d.getBerat();
                                    totalBeratAsli = totalBeratAsli + d.getBeratAsli();
                                    totalBeratPersen = totalBeratPersen + d.getBeratPersenRosok();
                                }
                                h.setTotalQty(totalQty);
                                h.setTotalBerat(totalBerat);
                                h.setTotalBeratAsli(totalBeratAsli);
                                h.setTotalBeratPersen(totalBeratPersen);
                                h.setListHancurDetail(controller.listHancurDetail);
                                return Service.saveHancurBarangCabang(conPusat, conCabang, h);
                            }
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getHancurBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Hancur barang cabang berhasil disimpan");
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
    private void detailHancurBarang(HancurHead h){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailHancurBarangCabang.fxml");
        DetailHancurBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setHancurBarangCabang(h);
    }
}
