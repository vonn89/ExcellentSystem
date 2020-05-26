/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.SPHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPHead;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailSPBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.SPBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.SelesaiSPBarangCabangController;
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
public class DataSPBarangCabangController  {

    @FXML private TableView<SPHead> spbarangTable;
    @FXML private TableColumn<SPHead, String> noSpColumn;
    @FXML private TableColumn<SPHead, String> tglSpColumn;
    @FXML private TableColumn<SPHead, String> kodeCabangColumn;
    @FXML private TableColumn<SPHead, String> jenisSpColumn;
    @FXML private TableColumn<SPHead, Number> totalQtyColumn;
    @FXML private TableColumn<SPHead, Number> totalBeratColumn;
    @FXML private TableColumn<SPHead, Number> totalBeratPenyusutanColumn;
    @FXML private TableColumn<SPHead, String> kodeUserColumn;
    @FXML private TableColumn<SPHead, String> statusSelesaiColumn;
    @FXML private TableColumn<SPHead, String> tglSelesaiColumn;
    @FXML private TableColumn<SPHead, String> userSelesaiColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> jenisCombo;
    @FXML private ComboBox<String> statusCombo;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    private Main mainApp;   
    private ObservableList<SPHead> allSPBarang = FXCollections.observableArrayList();
    private ObservableList<SPHead> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noSpColumn.setCellValueFactory(cellData -> cellData.getValue().noSPProperty());
        tglSpColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSP())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglSpColumn.setComparator(Function.sortDate(tglLengkap));
        jenisSpColumn.setCellValueFactory(cellData -> cellData.getValue().jenisSPProperty());
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        totalQtyColumn.setCellValueFactory(cellData -> cellData.getValue().totalQtyProperty());
        totalQtyColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratProperty());
        totalBeratColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratPenyusutanColumn.setCellValueFactory(cellData -> cellData.getValue().totalBeratPenyusutanProperty());
        totalBeratPenyusutanColumn.setCellFactory(col -> getTableCell(gr));
        statusSelesaiColumn.setCellValueFactory(cellData -> cellData.getValue().statusSelesaiProperty());
        tglSelesaiColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglSelesai())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglSelesaiColumn.setComparator(Function.sortDate(tglLengkap));
        userSelesaiColumn.setCellValueFactory(cellData -> cellData.getValue().userSelesaiProperty());
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem addNew = new MenuItem("New SP Barang Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newSpBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getSpBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        spbarangTable.setContextMenu(rowMenu);
        spbarangTable.setRowFactory(table -> {
            TableRow<SPHead> row = new TableRow<SPHead>() {
                @Override
                public void updateItem(SPHead item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New SP Barang Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newSpBarang();
                        });
                        MenuItem detail = new MenuItem("Detail SP Barang Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailSpBarang(item);
                        });
                        MenuItem selesai = new MenuItem("Terima SP Barang Cabang");
                        selesai.setOnAction((ActionEvent e) -> {
                            selesaiSpBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal SP Barang Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalSpBarang(item);
                        });
                        MenuItem batalTerima = new MenuItem("Batal Terima SP Barang Cabang");
                        batalTerima.setOnAction((ActionEvent e) -> {
                            batalTerimaSpBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getSpBarang();
                        });
                        rowMenu.getItems().add(addNew);
                        rowMenu.getItems().add(detail);
                        if(item.getStatusBatal().equals("false") && item.getStatusSelesai().equals("false")){
                            rowMenu.getItems().add(selesai);
                            rowMenu.getItems().add(batal);
                        }
                        if(item.getStatusBatal().equals("false") && item.getStatusSelesai().equals("true")){
                            rowMenu.getItems().add(batalTerima);
                        }
                        rowMenu.getItems().add(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailSpBarang(row.getItem());
                }
            });
            return row;
        });
        allSPBarang.addListener((ListChangeListener.Change<? extends SPHead> change) -> {
            searchSpBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchSpBarang();
        });
        filterData.addAll(allSPBarang);
        spbarangTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        ObservableList<String> allJenis = FXCollections.observableArrayList();
        allJenis.clear();
        allJenis.add("Semua");
        allJenis.add("SP");
        allJenis.add("Clear");
        jenisCombo.setItems(allJenis);
        jenisCombo.getSelectionModel().select("Semua");

        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.clear();
        allStatus.add("Semua");
        allStatus.add("Belum Selesai");
        allStatus.add("Selesai");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Belum Selesai");

        getSpBarang();
    } 
    @FXML
    private void getSpBarang(){
        Task<List<SPHead>> task = new Task<List<SPHead>>() {
            @Override 
            public List<SPHead> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String jenis = "%";
                    if(!jenisCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        jenis = jenisCombo.getSelectionModel().getSelectedItem();
                    String status = "%";
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        status = "%";
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Selesai"))
                        status = "false";
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Selesai"))
                        status = "true";
                    return SPHeadDAO.getAllByDateAndCabangAndJenisSpAndStatusSelesaiAndStatusBatal(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", jenis, status, "false");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allSPBarang.clear();
            allSPBarang.addAll(task.getValue());
            spbarangTable.refresh();
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
    private void searchSpBarang() {
        try{
            filterData.clear();
            for (SPHead a : allSPBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoSP())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglSP())))||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglSelesai())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getJenisSP())||
                        checkColumn(rp.format(a.getTotalQty()))||
                        checkColumn(gr.format(a.getTotalBerat()))||
                        checkColumn(gr.format(a.getTotalBeratPenyusutan()))||
                        checkColumn(a.getKodeUser())||
                        checkColumn(a.getStatusSelesai())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglSelesai())))||
                        checkColumn(a.getUserSelesai()))
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
        for(SPHead p : filterData){
            totalQty = totalQty + p.getTotalQty();
            totalBerat = totalBerat + p.getTotalBerat();
        }
        totalQtyLabel.setText(rp.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
    }
    private void newSpBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/SPBarangCabang.fxml");
        SPBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.cabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.jenisSpCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Jenis sp belum dipilih");
            else if(controller.listSPDetail.isEmpty())
                mainApp.showMessage(Modality.NONE, "Warning", "Barang sp masih kosong");
            else{
                SPHead sp = new SPHead();
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            sp.setNoSP(SPHeadDAO.getId(conPusat));
                            sp.setTglSP(Function.getSystemDate());
                            sp.setKodeCabang(controller.cabangCombo.getSelectionModel().getSelectedItem());
                            sp.setJenisSP(controller.jenisSpCombo.getSelectionModel().getSelectedItem());
                            sp.setTotalBeratPenyusutan(0);
                            sp.setTotalNilaiPenyusutan(0);
                            sp.setKodeUser(user.getKodeUser());
                            sp.setStatusSelesai("false");
                            sp.setTglSelesai("2000-01-01 00:00:00");
                            sp.setUserSelesai("");
                            sp.setStatusBatal("false");
                            sp.setTglBatal("2000-01-01 00:00:00");
                            sp.setUserBatal("");
                            
                            int totalQty = 0;
                            double totalBerat = 0;
                            double totalNilaiPokok = 0;
                            int i = 1;
                            for(SPDetail d : controller.listSPDetail){
                                d.setNoSP(sp.getNoSP());
                                d.setNoUrut(i);
                                i++;
                                totalQty = totalQty + d.getQty();
                                totalBerat = totalBerat + d.getBerat();
                                totalNilaiPokok = totalNilaiPokok + d.getNilaiPokok();
                            }
                            sp.setTotalQty(totalQty);
                            sp.setTotalBerat(pembulatan(totalBerat));
                            sp.setTotalNilaiPokok(pembulatan(totalNilaiPokok));
                            sp.setListSPDetail(controller.listSPDetail);
                            return Service.saveSPBarangCabang(conPusat, sp);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getSpBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        controller.listSPDetail.clear();
                        controller.spDetailTable.refresh();
                        controller.getStokBalenanCabang();
                        controller.hitungTotal();
                        mainApp.showMessage(Modality.NONE, "Success", "SP barang cabang berhasil disimpan\n- No SP : "+sp.getNoSP());
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
    private void detailSpBarang(SPHead s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailSPBarangCabang.fxml");
        DetailSPBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setSPBarangCabang(s);
    }
    private void selesaiSpBarang(SPHead s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "SP barang cabang tidak dapat diterima, karena sudah dibatal");
        }else if(s.getStatusSelesai().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "SP barang cabang tidak dapat diterima, karena sudah pernah diterima");
        }else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/SelesaiSPBarangCabang.fxml");
            SelesaiSPBarangCabangController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setSPBarangCabang(s);
            controller.saveButton.setOnAction((event) -> {
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusSelesai("true");
                            s.setTglSelesai(Function.getSystemDate());
                            s.setUserSelesai(user.getKodeUser());
                            s.setListSPDetail(controller.listSPDetail);
                            return Service.saveSelesaiSPBarangCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getSpBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "SP barang cabang berhasil diterima");
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
    private void batalSpBarang(SPHead s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "SP barang cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else if(s.getStatusSelesai().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "SP barang cabang tidak dapat dibatal, karena sudah selesai");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal sp barang cabang "+s.getNoSP()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusBatal("true");
                            s.setTglBatal(Function.getSystemDate());
                            s.setUserBatal(user.getKodeUser());
                            return Service.saveBatalSPBarangCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getSpBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "SP barang cabang berhasil dibatal");
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
    private void batalTerimaSpBarang(SPHead s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Terima SP barang cabang tidak dapat dibatal, karena sudah dibatal");
        }else if(s.getStatusSelesai().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Terima SP barang cabang tidak dapat dibatal, karena belum diterima");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal terima sp barang cabang "+s.getNoSP()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusSelesai("false");
                            s.setTglSelesai("2000-01-01 00:00:00");
                            s.setUserSelesai("");
                            return Service.saveBatalSelesaiSPBarangCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getSpBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Terima SP barang cabang berhasil dibatal");
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
