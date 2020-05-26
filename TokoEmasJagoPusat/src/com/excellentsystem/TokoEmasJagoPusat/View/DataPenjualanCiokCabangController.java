/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCiokCabangDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCiokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailPenjualanCiokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PenjualanCiokCabangController;
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
public class DataPenjualanCiokCabangController {

    @FXML private TableView<PenjualanCiokCabang> penjualanCiokTable;
    @FXML private TableColumn<PenjualanCiokCabang, String> noPenjualanColumn;
    @FXML private TableColumn<PenjualanCiokCabang, String> tglPenjualanColumn;
    @FXML private TableColumn<PenjualanCiokCabang, String> kodeCabangColumn;
    @FXML private TableColumn<PenjualanCiokCabang, Number> beratColumn;
    @FXML private TableColumn<PenjualanCiokCabang, Number> hargaEmasColumn;
    @FXML private TableColumn<PenjualanCiokCabang, Number> nilaiPokokColumn;
    @FXML private TableColumn<PenjualanCiokCabang, Number> totalPenjualanColumn;
    @FXML private TableColumn<PenjualanCiokCabang, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> kodeCabangCombo;
    
    @FXML private Label totalBeratLabel;
    @FXML private Label totalNilaiPokokLabel;
    @FXML private Label totalPenjualanLabel;
    private Main mainApp;   
    private ObservableList<PenjualanCiokCabang> allPenjualanCiok = FXCollections.observableArrayList();
    private ObservableList<PenjualanCiokCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenjualanProperty());
        tglPenjualanColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenjualan())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglPenjualanColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaEmasColumn.setCellValueFactory(cellData -> cellData.getValue().hargaEmasProperty());
        hargaEmasColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        totalPenjualanColumn.setCellValueFactory(cellData -> cellData.getValue().totalPenjualanProperty());
        totalPenjualanColumn.setCellFactory(col -> getTableCell(gr));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New Penjualan Ciok Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newPenjualanCiok();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getPenjualanCiok();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        penjualanCiokTable.setContextMenu(rowMenu);
        penjualanCiokTable.setRowFactory(table -> {
            TableRow<PenjualanCiokCabang> row = new TableRow<PenjualanCiokCabang>() {
                @Override
                public void updateItem(PenjualanCiokCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Penjualan Ciok Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newPenjualanCiok();
                        });
                        MenuItem detail = new MenuItem("Detail Penjualan Ciok Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailPenjualanCiok(item);
                        });
                        MenuItem batal = new MenuItem("Batal Penjualan Ciok Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalPenjualanCiok(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getPenjualanCiok();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
//                        rowMenu.getItems().add(batal);
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailPenjualanCiok(row.getItem());
                }
            });
            return row;
        });
        allPenjualanCiok.addListener((ListChangeListener.Change<? extends PenjualanCiokCabang> change) -> {
            searchPenjualanCiok();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchPenjualanCiok();
        });
        filterData.addAll(allPenjualanCiok);
        penjualanCiokTable.setItems(filterData);
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

            getPenjualanCiok();
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    } 
    @FXML
    private void getPenjualanCiok(){
        Task<List<PenjualanCiokCabang>> task = new Task<List<PenjualanCiokCabang>>() {
            @Override 
            public List<PenjualanCiokCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String kodeCabang = "%";
                    if(!kodeCabangCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        kodeCabang = kodeCabangCombo.getSelectionModel().getSelectedItem();
                    return PenjualanCiokCabangDAO.getAllByDateAndCabangAndStatus(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            kodeCabang, "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allPenjualanCiok.clear();
            allPenjualanCiok.addAll(task.getValue());
            penjualanCiokTable.refresh();
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
    private void searchPenjualanCiok() {
        try{
            filterData.clear();
            for (PenjualanCiokCabang a : allPenjualanCiok) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoPenjualan())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglPenjualan())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(rp.format(a.getNilaiPokok()))||
                        checkColumn(gr.format(a.getBerat()))||
                        checkColumn(rp.format(a.getHargaEmas()))||
                        checkColumn(rp.format(a.getTotalPenjualan()))||
                        checkColumn(a.getKodeUser()))
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
        double totalNilaiPokok = 0;
        double totalPenjualan = 0;
        for(PenjualanCiokCabang p : filterData){
            totalBerat = totalBerat + p.getBerat();
            totalNilaiPokok = totalNilaiPokok + p.getNilaiPokok();
            totalPenjualan = totalPenjualan + p.getTotalPenjualan();
        }
        totalBeratLabel.setText(gr.format(totalBerat));
        totalNilaiPokokLabel.setText(rp.format(totalNilaiPokok));
        totalPenjualanLabel.setText(rp.format(totalPenjualan));
    }
    private void newPenjualanCiok(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PenjualanCiokCabang.fxml");
        PenjualanCiokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))>controller.beratStok)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat ciok yang akan dijual melebihi stok ciok yang ada");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))<=0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat ciok yang akan dijual kurang dari 0");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            PenjualanCiokCabang p = new PenjualanCiokCabang();
                            p.setNoPenjualan(PenjualanCiokCabangDAO.getId(conPusat));
                            p.setTglPenjualan(Function.getSystemDate());
                            p.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            p.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            p.setHargaEmas(Double.parseDouble(controller.hargaEmasField.getText().replaceAll(",", "")));
                            p.setNilaiPokok(Double.parseDouble(controller.nilaiPokokField.getText().replaceAll(",", "")));
                            p.setTotalPenjualan(Double.parseDouble(controller.totalPenjualanField.getText().replaceAll(",", "")));
                            p.setKodeUser(user.getKodeUser());
                            p.setStatus("true");
                            p.setTglBatal("2000-01-01 00:00:00");
                            p.setUserBatal("");
                            return Service.savePenjualanCiokCabang(conPusat, p, controller.bayarHutangCheck.isSelected());
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getPenjualanCiok();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan ciok cabang berhasil disimpan");
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
    private void detailPenjualanCiok(PenjualanCiokCabang s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailPenjualanCiokCabang.fxml");
        DetailPenjualanCiokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setPenjualanCiok(s);
    }
    private void batalPenjualanCiok(PenjualanCiokCabang s){
//        if(s.getStatus().equals("false")){
//            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan ciok cabang tidak dapat dibatal, karena sudah pernah dibatal");
//        }else{
//            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
//                    "Batal penjualan ciok cabang "+s.getNoPenjualan()+" ?");
//            x.OK.setOnAction((ActionEvent ex) -> {
//                mainApp.closeMessage();
//                
//                Task<String> task = new Task<String>() {
//                    @Override 
//                    public String call() throws Exception{
//                        try(Connection conPusat = KoneksiPusat.getConnection()){
//                            s.setStatus("false");
//                            s.setTglBatal(Function.getSystemDate());
//                            s.setUserBatal(user.getKodeUser());
//                            return Service.saveBatalPenjualanCiokCabang(conPusat, s);
//                        }
//                    }
//                };
//                task.setOnRunning((e) -> {
//                    mainApp.showLoadingScreen();
//                });
//                task.setOnSucceeded((e) -> {
//                    mainApp.closeLoading();
//                    getPenjualanCiok();
//                    String status = task.getValue();
//                    if(status.equals("true")){
//                        mainApp.showMessage(Modality.NONE, "Success", "Penjualan ciok cabang berhasil dibatal");
//                    }else
//                        mainApp.showMessage(Modality.NONE, "Failed", status);
//                });
//                task.setOnFailed((e) -> {
//                    mainApp.closeLoading();
//                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
//                });
//                new Thread(task).start();
//            });
//        }
    }
}
