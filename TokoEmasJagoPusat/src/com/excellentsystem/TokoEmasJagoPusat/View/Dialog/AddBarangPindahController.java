/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PindahDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class AddBarangPindahController {

    @FXML private TableView<StokBarangCabang> barangTable;
    @FXML private TableColumn<StokBarangCabang,Boolean> checkColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> namaBarangColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeKategoriColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeJenisColumn;
    @FXML private TableColumn<StokBarangCabang, String> kodeInternColumn;
    @FXML private TableColumn<StokBarangCabang, String> kadarColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratAsliColumn;
    @FXML private TableColumn<StokBarangCabang, String> tglBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> userBarcodeColumn;
    @FXML private TableColumn<StokBarangCabang, String> asalBarangColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private TextField searchField;
    @FXML public Button addButton;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    
    @FXML private ComboBox<String> jenisCombo;
    @FXML private ComboBox<String> userCombo;
    
    private ObservableList<String> listJenis = FXCollections.observableArrayList();
    private ObservableList<String> listUser = FXCollections.observableArrayList();
    private ObservableList<StokBarangCabang> allBarang = FXCollections.observableArrayList();
    public ObservableList<StokBarangCabang> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getKodeJenis().toUpperCase());
        });
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().kodeInternProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().kadarProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().asalBarangProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getBarangCabang().getInputDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().inputByProperty());
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().getBarangCabang().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer i) -> {
            return barangTable.getItems().get(i).getBarangCabang().checkedProperty();
        }));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            barangTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<StokBarangCabang> row = new TableRow<StokBarangCabang>() {
                @Override
                public void updateItem(StokBarangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            barangTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null){
                        if(row.getItem().getBarangCabang().isChecked())
                            row.getItem().getBarangCabang().setChecked(false);
                        else
                            row.getItem().getBarangCabang().setChecked(true);
                        hitungTotal();
                    }
                }
            });
            return row;
        });
        allBarang.addListener((ListChangeListener.Change<? extends StokBarangCabang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        filterData.addAll(allBarang);
        barangTable.setItems(filterData);
    }    
    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        jenisCombo.setItems(listJenis);
        userCombo.setItems(listUser);
    }
    public void getBarang(String cabang, String gudang, List<PindahDetail> listPindahDetail){
        Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
            @Override 
            public List<StokBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<StokBarangCabang> listStok = new ArrayList<>();
                    List<StokBarangCabang> allStokBarang = StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(conPusat,
                            sistem.getTglSystem(), cabang, cabang+"-"+gudang, "%", "%", "%");
                    for(StokBarangCabang s : allStokBarang){
                        if(s.getStokAkhir()>0){
                            boolean statusInput = false;
                            for(PindahDetail d : listPindahDetail){
                                if(s.getKodeBarcode().equals(d.getKodeBarcode()))
                                    statusInput = true;
                            }
                            if(!statusInput){
                                BarangCabang b = BarangCabangDAO.get(conPusat, s.getKodeBarcode());
                                b.setChecked(checkAll.isSelected());
                                s.setBarangCabang(b);
                                listStok.add(s);
                            }
                        }
                    }
                    
                    List<String> allJenis = new ArrayList<>();
                    List<String> allUser = new ArrayList<>();
                    for(StokBarangCabang s : listStok){
                        if(!allJenis.contains(s.getKodeJenis().toUpperCase()))
                            allJenis.add(s.getKodeJenis().toUpperCase());
                        if(!allUser.contains(s.getBarangCabang().getInputBy()))
                            allUser.add(s.getBarangCabang().getInputBy());
                    }
                    listJenis.clear();
                    listJenis.add("Semua");
                    listJenis.addAll(allJenis);
                    listUser.clear();
                    listUser.add("Semua");
                    listUser.addAll(allUser);
                    return listStok;
                    
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            jenisCombo.getSelectionModel().select("Semua");
            userCombo.getSelectionModel().select("Semua");
            allBarang.clear();
            allBarang.addAll(task.getValue());
            barangTable.refresh();
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void checkAllHandle(){
        for(StokBarangCabang b: filterData){
            b.getBarangCabang().setChecked(checkAll.isSelected());
        }
        barangTable.refresh();
        hitungTotal();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    @FXML
    private void searchBarang() {
        try{
            filterData.clear();
            for (StokBarangCabang b : allBarang) {
                boolean status = false;
                if (searchField.getText() == null || searchField.getText().equals(""))
                    status = true;
                else{
                    if(checkColumn(b.getKodeKategori())||
                        checkColumn(b.getKodeCabang())||
                        checkColumn(b.getKodeGudang())||
                        checkColumn(b.getKodeBarcode())||
                        checkColumn(b.getKodeBarang())||
                        checkColumn(b.getBarangCabang().getNamaBarang())||
                        checkColumn(b.getKodeJenis())||
                        checkColumn(b.getBarangCabang().getKodeIntern())||
                        checkColumn(b.getBarangCabang().getKadar())||
                        checkColumn(b.getBarangCabang().getAsalBarang())||
                        checkColumn(b.getBarangCabang().getInputBy())||
                        checkColumn(tglLengkap.format(tglSql.parse(b.getBarangCabang().getInputDate())))||
                        checkColumn(gr.format(b.getBarangCabang().getBerat()))||
                        checkColumn(gr.format(b.getBarangCabang().getBeratAsli())))
                        status = true;
                }
                boolean statusJenis = false;
                if(jenisCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                    statusJenis = true;
                else{
                    if(b.getKodeJenis().equalsIgnoreCase(jenisCombo.getSelectionModel().getSelectedItem()))
                        statusJenis = true;
                }
                   
                boolean statusUser = false;
                if(userCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                    statusUser = true;
                else{
                    if(b.getBarangCabang().getInputBy().equals(userCombo.getSelectionModel().getSelectedItem()))
                        statusUser = true;
                }  
                
                if(status && statusJenis && statusUser)
                    filterData.add(b);
            }
            hitungTotal();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void hitungTotal(){
        int totalQty = 0;
        double totalBerat = 0;
        double totalBeratAsli = 0;
        for(StokBarangCabang b : filterData){
            if(b.getBarangCabang().isChecked()){
                totalQty = totalQty + 1;
                totalBerat = totalBerat + b.getBarangCabang().getBerat();
                totalBeratAsli = totalBeratAsli + b.getBarangCabang().getBeratAsli();
            }
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratAsliLabel.setText(gr.format(totalBeratAsli));
    }
    private void detailBarang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s.getBarangCabang());
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
