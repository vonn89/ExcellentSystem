/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RosokCabangDAO;
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
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.AmbilRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailRosokCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.PindahRosokCabangController;
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
public class DataRosokCabangController {

    @FXML private TableView<RosokCabang> rosokTable;
    @FXML private TableColumn<RosokCabang, String> noRosokColumn;
    @FXML private TableColumn<RosokCabang, String> tglRosokColumn;
    @FXML private TableColumn<RosokCabang, String> kategoriColumn;
    @FXML private TableColumn<RosokCabang, String> keteranganColumn;
    @FXML private TableColumn<RosokCabang, String> kodeCabangColumn;
    @FXML private TableColumn<RosokCabang, String> kodeGudangColumn;
    @FXML private TableColumn<RosokCabang, String> kodeKategoriColumn;
    @FXML private TableColumn<RosokCabang, String> kodeJenisColumn;
    @FXML private TableColumn<RosokCabang, Number> qtyColumn;
    @FXML private TableColumn<RosokCabang, Number> beratColumn;
    @FXML private TableColumn<RosokCabang, Number> persentaseEmasColumn;
    @FXML private TableColumn<RosokCabang, Number> beratPersenColumn;
    @FXML private TableColumn<RosokCabang, Number> nilaiPokokColumn;
    @FXML private TableColumn<RosokCabang, String> kodeUserColumn;
    
    @FXML private TextField searchField;
    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    
    private Main mainApp;   
    private ObservableList<RosokCabang> allRosokBarang = FXCollections.observableArrayList();
    private ObservableList<RosokCabang> filterData = FXCollections.observableArrayList();
    public void initialize() {
        noRosokColumn.setCellValueFactory(cellData -> cellData.getValue().noRosokProperty());
        tglRosokColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglRosok())));
            } catch (ParseException ex) {
                return null;
            }
        });
        tglRosokColumn.setComparator(Function.sortDate(tglLengkap));
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        kodeCabangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeCabangProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(gr));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        persentaseEmasColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseEmasProperty());
        persentaseEmasColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenRosokProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        nilaiPokokColumn.setCellValueFactory(cellData -> cellData.getValue().nilaiPokokProperty());
        nilaiPokokColumn.setCellFactory(col -> getTableCell(rp));
        
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()).minusMonths(1));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem pindah = new MenuItem("Pindah Rosok Cabang");
        pindah.setOnAction((ActionEvent e) -> {
            pindahRosokCabang();
        });
        MenuItem ambil = new MenuItem("Ambil Rosok Cabang");
        ambil.setOnAction((ActionEvent e) -> {
            ambilRosokCabang();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getRosokBarang();
        });
        rowMenu.getItems().addAll(pindah, ambil, refresh);
        rosokTable.setContextMenu(rowMenu);
        rosokTable.setRowFactory(table -> {
            TableRow<RosokCabang> row = new TableRow<RosokCabang>() {
                @Override
                public void updateItem(RosokCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem pindah = new MenuItem("Pindah Rosok Cabang");
                        pindah.setOnAction((ActionEvent e) -> {
                            pindahRosokCabang();
                        });
                        MenuItem ambil = new MenuItem("Ambil Rosok Cabang");
                        ambil.setOnAction((ActionEvent e) -> {
                            ambilRosokCabang();
                        });
                        MenuItem detail = new MenuItem("Detail Rosok Cabang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailRosokBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Rosok Cabang");
                        batal.setOnAction((ActionEvent e) -> {
                            batalRosokBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e) -> {
                            getRosokBarang();
                        });
                        rowMenu.getItems().add(pindah);
                        rowMenu.getItems().add(ambil);
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
                        detailRosokBarang(row.getItem());
                }
            });
            return row;
        });
        allRosokBarang.addListener((ListChangeListener.Change<? extends RosokCabang> change) -> {
            searchRosokBarang();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchRosokBarang();
        });
        filterData.addAll(allRosokBarang);
        rosokTable.setItems(filterData);
    }    
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        getRosokBarang();
    } 
    @FXML
    private void getRosokBarang(){
        Task<List<RosokCabang>> task = new Task<List<RosokCabang>>() {
            @Override 
            public List<RosokCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return RosokCabangDAO.getAllByDateAndKategoriAndCabangAndStatus(
                        conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "%", "%", "true");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allRosokBarang.clear();
            allRosokBarang.addAll(task.getValue());
            rosokTable.refresh();
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
    private void searchRosokBarang() {
        try{
            filterData.clear();
            for (RosokCabang a : allRosokBarang) {
                if (searchField.getText() == null || searchField.getText().equals(""))
                    filterData.add(a);
                else{
                    if(checkColumn(a.getNoRosok())||
                        checkColumn(tglLengkap.format(tglSql.parse(a.getTglRosok())))||
                        checkColumn(a.getKategori())||
                        checkColumn(a.getKeterangan())||
                        checkColumn(a.getKodeCabang())||
                        checkColumn(a.getKodeKategori())||
                        checkColumn(a.getKodeJenis())||
                        checkColumn(a.getKodeUser())||
                        checkColumn(gr.format(a.getQty()))||
                        checkColumn(gr.format(a.getBerat()))||
                        checkColumn(gr.format(a.getPersentaseEmas()))||
                        checkColumn(gr.format(a.getBeratPersen()))||
                        checkColumn(rp.format(a.getNilaiPokok())))
                        filterData.add(a);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void pindahRosokCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/PindahRosokCabang.fxml");
        PindahRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisField.getText().equals("") || controller.stokBarangCabang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Pindah Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(controller.stokBarangCabang.getKodeKategori());
                            r.setKodeJenis(controller.kodeJenisField.getText());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokBarangCabang.getNilaiAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRosokBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        controller.reset();
//                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Pindah rosok cabang berhasil disimpan");
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
    private void ambilRosokCabang(){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/AmbilRosokCabang.fxml");
        AmbilRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            Jenis j = JenisDAO.get(conPusat, controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            Kategori k = KategoriDAO.get(conPusat, j.getKodeKategori());
                            String kodeKategori = k.getKodeKategori();
                            double persentaseEmas = k.getPersentaseEmas();
                            
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Ambil Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(kodeKategori);
                            r.setKodeJenis(controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(persentaseEmas*r.getBerat()/100));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokRosokCabang.getNilaiAkhir()/controller.stokRosokCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRosokBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(mainApp.MainStage, stage);
                        mainApp.showMessage(Modality.NONE, "Success", "Ambil rosok cabang berhasil disimpan");
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
    private void detailRosokBarang(RosokCabang r){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailRosokCabang.fxml");
        DetailRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(r);
    }
    private void batalRosokBarang(RosokCabang r){
        if(r.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Rosok barang cabang tidak dapat dibatal, karena sudah pernah dibatal");
        }else{
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Batal rosok barang cabang "+r.getNoRosok()+" ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                mainApp.closeMessage();
                
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            r.setStatus("false");
                            r.setTglBatal(Function.getSystemDate());
                            r.setUserBatal(user.getKodeUser());
                            return Service.saveBatalRosokBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getRosokBarang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.showMessage(Modality.NONE, "Success", "Rosok barang cabang berhasil dibatal");
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
