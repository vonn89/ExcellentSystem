/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.KartuStokRosokCabangController;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
 * @author excellent
 */
public class StokRosokCiokCabangController {

    @FXML private TableView<StokRosokCabang> stokBarangTable;
    @FXML private TableColumn<StokRosokCabang, String> kodeCabangColumn;
    @FXML private TableColumn<StokRosokCabang, String> kodeGudangColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratAwalColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenAwalColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiAwalColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratMasukColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenMasukColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiMasukColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratKeluarColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenKeluarColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiKeluarColumn;
    
    @FXML private TableColumn<StokRosokCabang, Number> beratAkhirColumn;
    @FXML private TableColumn<StokRosokCabang, Number> beratPersenAkhirColumn;
    @FXML private TableColumn<StokRosokCabang, Number> nilaiAkhirColumn;
    
    @FXML private ComboBox<String> gudangCombo;
    @FXML private TextField searchField;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalNilaiLabel;
    @FXML private DatePicker tglPicker;
    private Main mainApp;   
    private final ObservableList<String> allGudang = FXCollections.observableArrayList();
    private final ObservableList<StokRosokCabang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<StokRosokCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        
        beratAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratAwalProperty());
        beratAwalColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAwalColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAwalProperty());
        beratPersenAwalColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAwalColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAwalProperty());
        nilaiAwalColumn.setCellFactory(col -> getTableCell(rp));
        
        beratMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratMasukProperty());
        beratMasukColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenMasukColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenMasukProperty());
        beratPersenMasukColumn.setCellFactory(col -> getTableCell(gr));
        nilaiMasukColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiMasukProperty());
        nilaiMasukColumn.setCellFactory(col -> getTableCell(rp));
        
        beratKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratKeluarProperty());
        beratKeluarColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenKeluarProperty());
        beratPersenKeluarColumn.setCellFactory(col -> getTableCell(gr));
        nilaiKeluarColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiKeluarProperty());
        nilaiKeluarColumn.setCellFactory(col -> getTableCell(rp));
        
        beratAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratAkhirColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenAkhirProperty());
        beratPersenAkhirColumn.setCellFactory(col -> getTableCell(gr));
        nilaiAkhirColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiAkhirProperty());
        nilaiAkhirColumn.setCellFactory(col -> getTableCell(rp));
        
        tglPicker.setConverter(Function.getTglConverter());
        tglPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglPicker.setDayCellFactory((final DatePicker datePicker) -> Function.getDateCellDisableAfter(LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(refresh);
        stokBarangTable.setContextMenu(rowMenu);
        stokBarangTable.setRowFactory(table -> {
            TableRow<StokRosokCabang> row = new TableRow<StokRosokCabang>() {
                @Override
                public void updateItem(StokRosokCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem kartu = new MenuItem("Kartu Stok Barang");
                        kartu.setOnAction((ActionEvent e)->{
                            kartuStok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getBarang();
                        });
                        rowMenu.getItems().add(kartu);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        kartuStok(row.getItem());
                }
            });
            return row;
        });
        
        allBarang.addListener((ListChangeListener.Change<? extends StokRosokCabang> change) -> {
            searchBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBarang();
        });
        stokBarangTable.setItems(filterData);
        gudangCombo.setItems(allGudang);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        allGudang.clear();
        allGudang.add("Semua");
        allGudang.add("Rosok");
        allGudang.add("Lebur");
        allGudang.add("Ciok");
        gudangCombo.getSelectionModel().selectFirst();
        getBarang();
    } 
    @FXML
    private void getBarang(){
        Task<List<StokRosokCabang>> task = new Task<List<StokRosokCabang>>() {
            @Override 
            public List<StokRosokCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    List<String> listGudang = new ArrayList<>();
                    if(!gudangCombo.getSelectionModel().getSelectedItem().equals("Semua")){
                        List<Cabang> allCabang = CabangDAO.getAll(conPusat);
                        for(Cabang c : allCabang){
                            listGudang.add(c.getKodeCabang()+"-"+gudangCombo.getSelectionModel().getSelectedItem());
                        }
                    }
                    return StokRosokCabangDAO.getAllByCabangAndListGudang(conPusat, tglPicker.getValue().toString(), "%", listGudang);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allBarang.clear();
            allBarang.addAll(task.getValue());
            stokBarangTable.refresh();
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
    private void searchBarang() {
        filterData.clear();
        for (StokRosokCabang b : allBarang) {
            if(searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(b);
            else if(checkColumn(b.getKodeCabang())|| 
                    checkColumn(b.getKodeGudang()))
                filterData.add(b);
        }
        hitungTotal();
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalBeratPersen = 0;
        double totalNilai = 0;
        for(StokRosokCabang b : filterData){
            totalBerat = totalBerat + b.getBeratAkhir();
            totalBeratPersen = totalBeratPersen + b.getBeratPersenAkhir();
            totalNilai = totalNilai + b.getNilaiAkhir();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
        totalNilaiLabel.setText(rp.format(totalNilai));
    }
    private void kartuStok(StokRosokCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/KartuStokRosokCabang.fxml");
        KartuStokRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.setData(s.getKodeCabang(), s.getKodeGudang());
    }
}
