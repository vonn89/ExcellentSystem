/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.LeburRosokCabangDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.LeburRosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailLeburRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.EditBeratJadiController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.LeburRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
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
public class DataLeburRosokCabangController {

    @FXML private TableView<LeburRosokCabang> leburRosokTable;
    @FXML private TableColumn<LeburRosokCabang, String> noLeburColumn;
    @FXML private TableColumn<LeburRosokCabang, String> tglLeburColumn;
    @FXML private TableColumn<LeburRosokCabang, String> kodeCabangColumn;
    @FXML private TableColumn<LeburRosokCabang, Number> totalBeratKotorColumn;
    @FXML private TableColumn<LeburRosokCabang, Number> totalBeratPersenColumn;
    @FXML private TableColumn<LeburRosokCabang, Number> totalNilaiPokokColumn;
    @FXML private TableColumn<LeburRosokCabang, Number> totalBeratJadiColumn;
    @FXML private TableColumn<LeburRosokCabang, String> kodeUserColumn;
    @FXML private TableColumn<LeburRosokCabang, String> statusSelesaiColumn;
    @FXML private TableColumn<LeburRosokCabang, String> tglSelesaiColumn;
    @FXML private TableColumn<LeburRosokCabang, String> userSelesaiColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> statusCombo;
    
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML private Label totalNilaiPokokLabel;
    private Main mainApp;   
    private ObservableList<LeburRosokCabang> allLeburBarang = FXCollections.observableArrayList();
    private ObservableList<LeburRosokCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noLeburColumn.setCellValueFactory(cellData -> cellData.getValue().noLeburProperty());
        tglLeburColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglLebur())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglLeburColumn.setComparator(Function.sortDate(tglLengkap));
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        totalBeratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().beratKotorProperty());
        totalBeratKotorColumn.setCellFactory(col -> getTableCell(gr));
        totalBeratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenProperty());
        totalBeratPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalNilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiPokokProperty());
        totalNilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        totalBeratJadiColumn.setCellValueFactory(cellData -> cellData.getValue().beratJadiProperty());
        totalBeratJadiColumn.setCellFactory(col -> getTableCell(gr));
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
        MenuItem addNew = new MenuItem("New Lebur Rosok Cabang");
        addNew.setOnAction((ActionEvent e) -> {
            newLeburBarang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getLeburBarang();
        });
        rowMenu.getItems().addAll(addNew, refresh);
        leburRosokTable.setContextMenu(rowMenu);
        leburRosokTable.setRowFactory(table -> {
            TableRow<LeburRosokCabang> row = new TableRow<LeburRosokCabang>() {
                @Override
                public void updateItem(LeburRosokCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem addNew = new MenuItem("New Lebur Rosok Cabang");
                        addNew.setOnAction((ActionEvent e) -> {
                            newLeburBarang();
                        });
                        MenuItem detail = new MenuItem("Detail Lebur Rosok Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailLeburBarang(item);
                        });
                        MenuItem selesai = new MenuItem("Terima Lebur Rosok Cabang");
                        selesai.setOnAction((ActionEvent e) -> {
                            selesaiLeburBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Lebur Rosok Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalLeburBarang(item);
                        });
                        MenuItem batalTerima = new MenuItem("Batal Terima Lebur Rosok Cabang");
                        batalTerima.setOnAction((ActionEvent e) -> {
                            batalTerimaLeburBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getLeburBarang();
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
                        detailLeburBarang(row.getItem());
                }
            });
            return row;
        });
        allLeburBarang.addListener((ListChangeListener.Change<? extends LeburRosokCabang> change) -> {
            searchLeburBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchLeburBarang();
        });
        filterData.addAll(allLeburBarang);
        leburRosokTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;

        ObservableList<String> allStatus = FXCollections.observableArrayList();
        allStatus.clear();
        allStatus.add("Semua");
        allStatus.add("Belum Selesai");
        allStatus.add("Selesai");
        statusCombo.setItems(allStatus);
        statusCombo.getSelectionModel().select("Belum Selesai");

        getLeburBarang();
    } 
    @FXML
    private void getLeburBarang(){
        Task<List<LeburRosokCabang>> task = new Task<List<LeburRosokCabang>>() {
            @Override 
            public List<LeburRosokCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    String status = "%";
                    if(statusCombo.getSelectionModel().getSelectedItem().equals("Semua"))
                        status = "%";
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Belum Selesai"))
                        status = "false";
                    else if(statusCombo.getSelectionModel().getSelectedItem().equals("Selesai"))
                        status = "true";
                    return LeburRosokCabangDAO.getAllByDateAndCabangAndStatusSelesaiAndStatusBatal(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", status, "false");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allLeburBarang.clear();
            allLeburBarang.addAll(task.getValue());
            leburRosokTable.refresh();
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
    private void searchLeburBarang() {
        try{
            filterData.clear();
            for (LeburRosokCabang a : allLeburBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoLebur())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglLebur())))||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglSelesai())))||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(rp.format(a.getNilaiPokok()))||
                        checkColumn(gr.format(a.getBeratKotor()))||
                        checkColumn(gr.format(a.getBeratPersen()))||
                        checkColumn(gr.format(a.getBeratJadi()))||
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
        double totalBerat = 0;
        double totalBeratPersen = 0;
        double totalNilaiPokok = 0;
        for(LeburRosokCabang p : filterData){
            totalBerat = totalBerat + p.getBeratKotor();
            totalBeratPersen = totalBeratPersen + p.getBeratPersen();
            totalNilaiPokok = totalNilaiPokok + p.getNilaiPokok();
        }
        totalBeratKotorLabel.setText(gr.format(totalBerat));
        totalBeratPersenLabel.setText(gr.format(totalBeratPersen));
        totalNilaiPokokLabel.setText(rp.format(totalNilaiPokok));
    }
    private void newLeburBarang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/LeburRosokCabang.fxml");
        LeburRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))<=0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat rosok yang akan dilebur kurang dari 0");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))<=0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen rosok yang akan dilebur kurang dari 0");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            LeburRosokCabang l = new LeburRosokCabang();
                            l.setNoLebur(LeburRosokCabangDAO.getId(conPusat));
                            l.setTglLebur(Function.getSystemDate());
                            l.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            l.setBeratKotor(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            l.setBeratPersen(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", "")));
                            l.setNilaiPokok(Double.parseDouble(controller.nilaiPokokField.getText().replaceAll(",", "")));
                            l.setBeratJadi(0);
                            l.setKodeUser(user.getKodeUser());
                            l.setStatusSelesai("false");
                            l.setTglSelesai("2000-01-01 00:00:00");
                            l.setUserSelesai("");
                            l.setStatusBatal("false");
                            l.setTglBatal("2000-01-01 00:00:00");
                            l.setUserBatal("");
                            return Service.saveLeburRosokCabang(conPusat, l);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getLeburBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Lebur barang cabang berhasil disimpan");
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
    private void detailLeburBarang(LeburRosokCabang s){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailLeburRosokCabang.fxml");
        DetailLeburRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setLeburRosokCabang(s);
    }
    private void selesaiLeburBarang(LeburRosokCabang s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Lebur rosok cabang tidak dapat diterima, karena sudah dibatal");
        }else if(s.getStatusSelesai().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Lebur rosok cabang tidak dapat diterima, karena sudah pernah diterima");
        }else{
            Stage child = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,child, "View/Dialog/EditBeratJadi.fxml");
            EditBeratJadiController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, child);
            controller.setBerat(s.getBeratPersen());
            controller.okButton.setOnAction((event) -> {
                mainApp.closeDialog(mainApp.MainStage, child);
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setBeratJadi(Double.parseDouble(controller.beratJadiField.getText().replaceAll(",", "")));
                            s.setStatusSelesai("true");
                            s.setTglSelesai(Function.getSystemDate());
                            s.setUserSelesai(user.getKodeUser());
                            return Service.saveSelesaiLeburRosokCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getLeburBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Selesai lebur rosok cabang berhasil disimpan");
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
    private void batalLeburBarang(LeburRosokCabang s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Lebur rosok cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else if(s.getStatusSelesai().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Lebur rosok cabang tidak dapat dibatal, karena sudah selesai");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal lebur rosok cabang "+s.getNoLebur()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusBatal("true");
                            s.setTglBatal(Function.getSystemDate());
                            s.setUserBatal(user.getKodeUser());
                            return Service.saveBatalLeburRosokCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getLeburBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Lebur rosok cabang berhasil dibatal");
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
    private void batalTerimaLeburBarang(LeburRosokCabang s){
        if(s.getStatusBatal().equals("true")){
            mainApp.showMessage(Modality.NONE, "Warning", "Selesai lebur rosok cabang tidak dapat dibatal, karena sudah dibatal");
        }else if(s.getStatusSelesai().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Selesai lebur rosok cabang tidak dapat dibatal, karena belum diterima");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal selesai lebur rosok cabang "+s.getNoLebur()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            s.setStatusSelesai("false");
                            s.setTglSelesai("2000-01-01 00:00:00");
                            s.setUserSelesai("");
                            return Service.saveBatalSelesaiLeburRosokCabang(conPusat, s);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getLeburBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Selesai lebur rosok cabang berhasil dibatal");
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
