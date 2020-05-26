/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View;

import com.excellentsystem.TokoEmasJagoPusat.DAO.BarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
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
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.PrintOut.PrintOut;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.CekStokBarangCabangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.DetailBarangController;
import com.excellentsystem.TokoEmasJagoPusat.View.Dialog.MessageController;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class BarcodeBarangController  {
  
    @FXML private TableView<BarangCabang> barangTable;
    @FXML private TableColumn<BarangCabang,Boolean> checkColumn;
    @FXML private TableColumn<BarangCabang, String> kodeBarcodeColumn;
    @FXML private TableColumn<BarangCabang, String> namaBarangColumn;
    @FXML private TableColumn<BarangCabang, String> kodeKategoriColumn;
    @FXML private TableColumn<BarangCabang, String> kodeJenisColumn;
    @FXML private TableColumn<BarangCabang, String> kadarColumn;
    @FXML private TableColumn<BarangCabang, String> kodeInternColumn;
    @FXML private TableColumn<BarangCabang, Number> beratColumn;
    @FXML private TableColumn<BarangCabang, Number> beratAsliColumn;
    @FXML private TableColumn<BarangCabang, String> userBarcodeColumn;
    @FXML private TableColumn<BarangCabang, String> tglBarcodeColumn;
    
    @FXML private CheckBox checkAll;
    @FXML private ComboBox<String> kodeCabangCombo;
    @FXML private ComboBox<String> kodeGudangCombo;
    
    @FXML private VBox vbox;
    @FXML private ComboBox<String> jenisBarangCombo;
    @FXML private Label warningJenisBarang;
    @FXML private TextField qtyJenisField;
    @FXML private TextField beratJenisField;
    
    @FXML private Label kodeJenisLabel;
    @FXML private ComboBox<String> kodeJenisCombo;
    @FXML private Label warningKodeJenis;
    @FXML private TextField namaBarangField;
    @FXML private Label warningNamaBarang;
    @FXML private TextField beratField;
    @FXML private Label warningBerat;
    @FXML private TextField beratAsliField;
    @FXML private Label warningBeratAsli;
    @FXML private TextField kadarField;
    @FXML private Label warningKadar;
    @FXML private TextField kodeInternField;
    @FXML private Label warningKodeIntern;
    
    @FXML private Button saveButton;
    @FXML private Label totalQty;
    @FXML private Label totalBerat;
    @FXML private Label totalBeratAsli;
    
    @FXML private TextField minBarcodeField;
    @FXML private TextField maxBarcodeField;
    
    private Main mainApp;   
    private List<Jenis> listJenis;
    private final ObservableList<BarangCabang> allBarang = FXCollections.observableArrayList();
    private final ObservableList<String> allCabang = FXCollections.observableArrayList(); 
    private final ObservableList<String> allGudang = FXCollections.observableArrayList(); 
    private final ObservableList<String> allJenisBarang = FXCollections.observableArrayList(); 
    private final ObservableList<String> allKodeJenis = FXCollections.observableArrayList(); 
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        kadarColumn.setCellValueFactory(cellData -> cellData.getValue().kadarProperty());
        kodeInternColumn.setCellValueFactory(cellData -> cellData.getValue().kodeInternProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData ->cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        userBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().inputByProperty());
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getInputDate())));
            } catch (ParseException ex) {
                return null;
            }
        });
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().checkedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer param) -> {
            return barangTable.getItems().get(param).checkedProperty();
        }));
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem cetak = new MenuItem("Cetak Barcode");
        cetak.setOnAction((ActionEvent e)->{
            cetakBarcode();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            getBarang();
        });
        rowMenu.getItems().addAll(cetak, refresh);
        barangTable.setContextMenu(rowMenu);
        barangTable.setRowFactory(table -> {
            TableRow<BarangCabang> row = new TableRow<BarangCabang>(){
                @Override
                public void updateItem(BarangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rowMenu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent e) -> {
                            detailBarang(item);
                        });
                        MenuItem batal = new MenuItem("Batal Barcode");
                        batal.setOnAction((ActionEvent e) -> {
                            batalBarcode(item);
                        });
                        MenuItem cetak = new MenuItem("Cetak Barcode");
                        cetak.setOnAction((ActionEvent e)->{
                            cetakBarcode();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            getBarang();
                        });
                        rowMenu.getItems().addAll(cetak, detail, batal, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem().isChecked())
                        row.getItem().setChecked(false);
                    else
                        row.getItem().setChecked(true);
                }
            });
            return row;
        });
        beratField.setOnKeyReleased((event) -> {
            try{
                String string = beratField.getText();
                if(string.contains("-"))
                    beratField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            beratField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        beratField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratField.end();
            }catch(Exception e){
                beratField.undo();
            }
            warningBerat();
        });
        beratAsliField.setOnKeyReleased((event) -> {
            try{
                String string = beratAsliField.getText();
                if(string.contains("-"))
                    beratAsliField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            beratAsliField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            beratAsliField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        beratAsliField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                beratAsliField.end();
            }catch(Exception e){
                beratAsliField.undo();
            }
            warningBerat();
        });
        jenisBarangCombo.setOnAction((event) -> {
            setJenisBarang();
        });
        jenisBarangCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setJenisBarang();
        });
        kodeJenisCombo.setOnAction((event) -> {
            setKodeJenis();
        });
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setKodeJenis();
        });
        namaBarangField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                beratField.selectAll();
                beratField.requestFocus();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                beratAsliField.selectAll();
                beratAsliField.requestFocus();
            }
        });
        beratAsliField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kadarField.selectAll();
                kadarField.requestFocus();
            }
        });
        kadarField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                kodeInternField.selectAll();
                kodeInternField.requestFocus();
            }
        });
        kodeInternField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
        saveButton.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                saveButton.fire();
        });
        barangTable.setItems(allBarang);
        kodeCabangCombo.setItems(allCabang);
        kodeGudangCombo.setItems(allGudang);
        jenisBarangCombo.setItems(allJenisBarang);
        kodeJenisCombo.setItems(allKodeJenis);
    }  
    public void setMainApp(Main mainApp){
        this.mainApp = mainApp;
        for(Node n : vbox.getChildren()){
            n.managedProperty().bind(n.visibleProperty());
        }
        setBarcodeBarang();
    }
    private void setBarcodeBarang(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listJenis = JenisDAO.getAll(conPusat);
                    return CabangDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            allCabang.clear();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            allGudang.clear();
            allGudang.add("New");
            allGudang.add("SP");
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void getBarangAndJenisBarang(){
        if(kodeGudangCombo.getSelectionModel().getSelectedItem()!=null){
            if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("SP")){
                kodeJenisLabel.setVisible(false);
                kodeJenisCombo.setVisible(false);
                warningKodeJenis.setVisible(false);
            }else{
                kodeJenisLabel.setVisible(true);
                kodeJenisCombo.setVisible(true);
                warningKodeJenis.setVisible(true);
            }
            if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null){
                getJenisBarang();
                getBarang();
            }
        }
    }
    private void getJenisBarang(){
        Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
            @Override 
            public List<StokBarangCabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                            conPusat, sistem.getTglSystem(), kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                            kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem(), "%", "%");
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            allJenisBarang.clear();
            List<StokBarangCabang> listStokDalam = task.getValue();
            for(StokBarangCabang s : listStokDalam){
                if(s.getStokAkhir()>0)
                    allJenisBarang.add(s.getKodeJenis());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()!=null && kodeGudangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<BarangCabang>> task = new Task<List<BarangCabang>>() {
                @Override 
                public List<BarangCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        List<StokBarangCabang> listStokBarang = StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(conPusat, 
                                sistem.getTglSystem(), kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                                kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem(), "%", "%", "%");
                        List<BarangCabang> listBarang = new ArrayList<>();
                        for (StokBarangCabang s : listStokBarang) {
                            if(s.getStokAkhir()>0){
                                boolean status = true;
                                if(!minBarcodeField.getText().equals(""))
                                    status = Integer.parseInt(s.getKodeBarcode())>=Integer.parseInt(minBarcodeField.getText());
                                if(!maxBarcodeField.getText().equals(""))
                                    status = Integer.parseInt(s.getKodeBarcode())<=Integer.parseInt(maxBarcodeField.getText());
                                if(status){
                                    BarangCabang b = BarangCabangDAO.get(conPusat, s.getKodeBarcode());
                                    if(b.getInputBy().equalsIgnoreCase(user.getKodeUser()))
                                        listBarang.add(b);
                                }
                            }
                        }
                        return listBarang;
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
                for (BarangCabang b : allBarang) {
                    b.setChecked(checkAll.isSelected());
                    b.checkedProperty().addListener((obs, oldValue, newValue) -> hitungTotal());
                }
                hitungTotal();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    @FXML
    private void setJenisBarang(){
        cancel();
        boolean status = false;
        for(String j : allJenisBarang){
            if(j.equalsIgnoreCase(jenisBarangCombo.getEditor().getText().toUpperCase())){
                jenisBarangCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            getQtyAndBeratJenis();
            warningJenisBarang.setText("");
            if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("New") && jenisBarangCombo.getSelectionModel().getSelectedItem()!=null){
                getKodeJenis();
                kodeJenisCombo.requestFocus();
                kodeJenisCombo.getEditor().selectAll();
            }else{
                namaBarangField.requestFocus();
                namaBarangField.selectAll();
            }
        }else{
            qtyJenisField.setText("0");
            beratJenisField.setText("0");
            warningJenisBarang.setText("* Belum dipilih / masih salah");
            jenisBarangCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void cekStokBarangCabang(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
        else if(kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
        else{
            Stage stage = new Stage();
            FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/CekStokBarangCabang.fxml");
            CekStokBarangCabangController controller = loader.getController();
            controller.setMainApp(mainApp, mainApp.MainStage, stage);
            controller.setData(sistem.getTglSystem(), kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                    kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem());
            
            final ContextMenu rowMenu = new ContextMenu();
            MenuItem refresh = new MenuItem("Refresh");
            refresh.setOnAction((ActionEvent event) -> {
                getBarang();
            });
            rowMenu.getItems().addAll(refresh);
            controller.stokBarangTable.setContextMenu(rowMenu);
            controller.stokBarangTable.setRowFactory(table -> {
                TableRow<StokBarangCabang> row = new TableRow<StokBarangCabang>() {
                    @Override
                    public void updateItem(StokBarangCabang item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setContextMenu(null);
                        }else{
                            final ContextMenu rowMenu = new ContextMenu();
                            MenuItem pilih = new MenuItem("Pilih Barang");
                            pilih.setOnAction((ActionEvent e) -> {
                                jenisBarangCombo.getSelectionModel().select(item.getKodeJenis());
                                getQtyAndBeratJenis();
                                warningJenisBarang.setText("");
                                if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("New") && jenisBarangCombo.getSelectionModel().getSelectedItem()!=null){
                                    getKodeJenis();
                                    kodeJenisCombo.requestFocus();
                                    kodeJenisCombo.getEditor().selectAll();
                                }else{
                                    namaBarangField.requestFocus();
                                    namaBarangField.selectAll();
                                }
                                mainApp.closeDialog(mainApp.MainStage ,stage);
                            });
                            MenuItem refresh = new MenuItem("Refresh");
                            refresh.setOnAction((ActionEvent e) -> {
                                getBarang();
                            });
                            rowMenu.getItems().add(pilih);
                            rowMenu.getItems().add(refresh);
                            setContextMenu(rowMenu);
                        }
                    }
                };
                row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                        if(row.getItem()!=null){
                            jenisBarangCombo.getSelectionModel().select(row.getItem().getKodeJenis());
                            getQtyAndBeratJenis();
                            warningJenisBarang.setText("");
                            if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("New") && jenisBarangCombo.getSelectionModel().getSelectedItem()!=null){
                                getKodeJenis();
                                kodeJenisCombo.requestFocus();
                                kodeJenisCombo.getEditor().selectAll();
                            }else{
                                namaBarangField.requestFocus();
                                namaBarangField.selectAll();
                            }
                            mainApp.closeDialog(mainApp.MainStage ,stage);
                        }
                    }
                });
                return row;
            });
        }
    }
    private void getQtyAndBeratJenis(){
        Task<StokBarangCabang> task = new Task<StokBarangCabang>() {
            @Override 
            public StokBarangCabang call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return StokBarangCabangDAO.getNonBarcode(conPusat, sistem.getTglSystem(), 
                            kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem(), 
                            jenisBarangCombo.getSelectionModel().getSelectedItem());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            StokBarangCabang s = task.getValue();
            qtyJenisField.setText(rp.format(s.getStokAkhir()));
            beratJenisField.setText(gr.format(s.getBeratAkhir()));
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getKodeJenis(){
        allKodeJenis.clear();
        for(Jenis j : listJenis){
            if(jenisBarangCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(j.getKodeSubKategori()))
                allKodeJenis.add(j.getKodeJenis());
        }
    }
    @FXML
    private void setKodeJenis(){
        cancel();
        boolean status = false;
        for(String j : allKodeJenis){
            if(j.equalsIgnoreCase(kodeJenisCombo.getEditor().getText().toUpperCase())){
                kodeJenisCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            warningKodeJenis.setText("");
            namaBarangField.requestFocus();
            namaBarangField.selectAll();
        }else{
            warningKodeJenis.setText("* Belum dipilih / masih salah");
            kodeJenisCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void save(){
        if(kodeCabangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
        }else if(kodeGudangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
        }else if(jenisBarangCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
        }else if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("New")&&kodeJenisCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Jenis barang belum dipilih");
        }else if(namaBarangField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
        }else if(kadarField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Kadar masih kosong");
        }else if(kodeInternField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode intern masih kosong");
        }else if(beratField.getText().equals("")||beratField.getText().equals("0")){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
        }else if(beratAsliField.getText().equals("")||beratAsliField.getText().equals("0")){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli masih kosong");
        }else if(Integer.parseInt(qtyJenisField.getText())<1){
            mainApp.showMessage(Modality.NONE, "Warning", "Qty barang sudah habis / tidak ada");
        }else if(Double.parseDouble(beratField.getText().replaceAll(",", "")) < Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli tidak boleh melebihi berat jual");
        }else if(Double.parseDouble(beratJenisField.getText().replaceAll(",", "")) < Double.parseDouble(beratAsliField.getText().replaceAll(",", ""))){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat asli melebihi stok berat yang tersisa");
        }else{
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        BarangCabang b = new BarangCabang();
                        b.setNamaBarang(namaBarangField.getText());
                        if(kodeGudangCombo.getSelectionModel().getSelectedItem().equals("New")){
                            String kodeKategori = "";
                            for(Jenis j : listJenis){
                                if(kodeJenisCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(j.getKodeJenis()))
                                    kodeKategori = j.getKodeKategori();
                            }
                            b.setKodeKategori(kodeKategori);
                            b.setKodeJenis(kodeJenisCombo.getSelectionModel().getSelectedItem());
                        }else{
                            String kodeKategori = "";
                            for(Jenis j : listJenis){
                                if(jenisBarangCombo.getSelectionModel().getSelectedItem().equalsIgnoreCase(j.getKodeJenis()))
                                    kodeKategori = j.getKodeKategori();
                            }
                            b.setKodeKategori(kodeKategori);
                            b.setKodeJenis(jenisBarangCombo.getSelectionModel().getSelectedItem());
                        }
                        b.setKodeIntern(kodeInternField.getText());
                        b.setKadar(kadarField.getText());
                        b.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
                        b.setBeratAsli(Double.parseDouble(beratAsliField.getText().replaceAll(",", "")));
                        b.setInputDate(Function.getSystemDate());
                        b.setInputBy(user.getKodeUser());
                        b.setAsalBarang(kodeGudangCombo.getSelectionModel().getSelectedItem());
                        b.setStatus("true");
                        return Service.saveBarcodeBarang(conPusat, b, kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                                kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem(),
                                jenisBarangCombo.getSelectionModel().getSelectedItem());
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                String status = task.getValue();
                if(status.equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Barcode barang berhasil disimpan");
                    getBarang();
                    getQtyAndBeratJenis();
                    beratField.setText("0");
                    beratAsliField.setText("0");
                    warningBerat();
                    namaBarangField.selectAll();
                    namaBarangField.requestFocus();
                }else{
                    mainApp.showMessage(Modality.NONE, "Failed", status);
                }
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }
    }
    private void detailBarang(BarangCabang b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage ,stage, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, stage);
        controller.setBarang(b);
    }
    private void cetakBarcode(){
        List<BarangCabang> barang = new ArrayList<>();
        for(BarangCabang b : allBarang){
            if(b.isChecked())
                barang.add(b);
        }
        if(barang.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
        else{        
            MessageController x = mainApp.showMessage(Modality.APPLICATION_MODAL, "Confirmation",
                    "Cetak "+totalQty.getText()+" barcode ?");
            x.OK.setOnAction((ActionEvent ex) -> {
                try{ 
                    mainApp.closeMessage();
                    PrintOut.printBarcode(barang);
                }catch(Exception e){
                    mainApp.showMessage(Modality.NONE, "Error", e.toString());
                }
            });
        }
    }
    private void batalBarcode(BarangCabang b){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return Service.saveBatalBarcodeBarang(conPusat, b, kodeCabangCombo.getSelectionModel().getSelectedItem(), 
                            kodeCabangCombo.getSelectionModel().getSelectedItem()+"-"+kodeGudangCombo.getSelectionModel().getSelectedItem());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            String status = task.getValue();
            if(status.equals("true")){
                mainApp.showMessage(Modality.NONE, "Success", "Batal barcode barang berhasil disimpan");
                getBarang();
            }else{
                mainApp.showMessage(Modality.NONE, "Failed", status);
            }
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    
    @FXML
    private void checkAll(){
        for(BarangCabang b: allBarang){
            b.setChecked(checkAll.isSelected());
        }
        barangTable.refresh();
    }
    @FXML
    private void warningNama(){
        if(namaBarangField.getText().equals(""))
            warningNamaBarang.setText("* Masih kosong");
        else if(namaBarangField.getText().length()>12)
            warningNamaBarang.setText("* Max.12 karakter");
        else
            warningNamaBarang.setText("");
    }
    private void warningBerat(){
        if(beratField.getText().equals(""))
            warningBerat.setText("* Masih kosong");
        else if(beratField.getText().equals("0"))
            warningBerat.setText("* Masih kosong");
        else if(Double.parseDouble(beratField.getText().replaceAll(",", "")) < Double.parseDouble(beratAsliField.getText().replaceAll(",", "")))
            warningBerat.setText("* Berat jual tidak boleh kurang berat asli");
        else
            warningBerat.setText("");
        
        if(beratAsliField.getText().equals(""))
            warningBeratAsli.setText("* Masih kosong");
        else if(beratAsliField.getText().equals("0"))
            warningBeratAsli.setText("* Masih kosong");
        else if(Double.parseDouble(beratField.getText().replaceAll(",", "")) < Double.parseDouble(beratAsliField.getText().replaceAll(",", "")))
            warningBeratAsli.setText("* Berat asli tidak boleh melebihi berat jual");
        else if(Double.parseDouble(beratJenisField.getText().replaceAll(",", "")) < Double.parseDouble(beratAsliField.getText().replaceAll(",", "")))
            warningBeratAsli.setText("* Berat asli melebihi stok berat yang tersisa");
        else
            warningBeratAsli.setText("");
    }
    @FXML
    private void warningKadar(){
        if(kadarField.getText().equals(""))
            warningKadar.setText("* Masih kosong");
        else if(kadarField.getText().length()>8)
            warningKadar.setText("* Max.8 karakter");
        else
            warningKadar.setText("");
    }
    @FXML
    private void warningKodeIntern(){
        if(kodeInternField.getText().equals(""))
            warningKodeIntern.setText("* Masih kosong");
        else if(kodeInternField.getText().length()>8)
            warningKodeIntern.setText("* Max.8 karakter");
        else
            warningKodeIntern.setText("");
    }
    private void hitungTotal(){
        double beratAsli = 0;
        double berat = 0;
        int qty = 0;
        for(BarangCabang b : allBarang){
            if(b.isChecked()){
                beratAsli = beratAsli + b.getBeratAsli();
                berat = berat + b.getBerat();
                qty = qty + 1;
            }  
        }
        totalBerat.setText(gr.format(berat));
        totalQty.setText(rp.format(qty));
        totalBeratAsli.setText(gr.format(beratAsli));
    }
    @FXML
    private void cancel(){
        namaBarangField.setText("");
        warningNama();
        beratField.setText("0");
        beratAsliField.setText("0");
        warningBerat();
        kadarField.setText("");
        warningKadar();
        kodeInternField.setText("");
        warningKodeIntern();
    }
}
