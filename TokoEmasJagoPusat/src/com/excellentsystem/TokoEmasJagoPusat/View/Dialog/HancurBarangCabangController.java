/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.BarangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Barang;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class HancurBarangCabangController  {

    @FXML private TableView<HancurDetail> hancurDetailTable;
    @FXML private TableColumn<HancurDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<HancurDetail, String> namaBarangColumn;
    @FXML private TableColumn<HancurDetail, String> kodeJenisColumn;
    @FXML private TableColumn<HancurDetail, Number> beratColumn;
    @FXML private TableColumn<HancurDetail, Number> beratAsliColumn;
    @FXML private TableColumn<HancurDetail, Number> persentaseEmasColumn;
    @FXML private TableColumn<HancurDetail, Number> beratPersenColumn;
    @FXML private TableColumn<HancurDetail, String> asalBarangColumn;
    @FXML private TableColumn<HancurDetail, String> tglBarcodeColumn;
    
    @FXML private ComboBox<String> cabangCombo;
    
    @FXML private TextField kodeBarcodeField;
    @FXML private TextField namaBarangField;
    @FXML private TextField kodeJenisField;
    @FXML private TextField beratField;
    @FXML private TextField beratAsliField;
    @FXML private TextField persenRosokField;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    @FXML private Label totalBeratPersenLabel;
    @FXML public Button saveButton;
    
    public Cabang cabang = null;
    private Barang b = null;
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    public ObservableList<HancurDetail> listHancurDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        persentaseEmasColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseRosokProperty());
        persentaseEmasColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenRosokProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getInputDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().asalBarangProperty());
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            hancurDetailTable.refresh();
        });
        menu.getItems().addAll(refresh);
        hancurDetailTable.setContextMenu(menu);
        hancurDetailTable.setRowFactory(table -> {
            TableRow<HancurDetail> row = new TableRow<HancurDetail>(){
                @Override
                public void updateItem(HancurDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            hapusBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            hancurDetailTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().add(hapus);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailBarang(row.getItem());
                    
                }
            });
            return row;
        });
        Function.setNumberField(persenRosokField, gr);
        kodeBarcodeField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                getBarang();
        });
        persenRosokField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
        });
        hancurDetailTable.setItems(listHancurDetail);
        cabangCombo.setItems(allCabang);
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
        setData();
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
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
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void detailBarang(HancurDetail d){
        BarangCabang brg = new BarangCabang();
        brg.setKodeBarang(d.getKodeBarang());
        brg.setKodeBarcode(d.getKodeBarcode());
        brg.setNamaBarang(d.getNamaBarang());
        brg.setKodeKategori(d.getKodeKategori());
        brg.setKodeJenis(d.getKodeJenis());
        brg.setKodeIntern(d.getKodeIntern());
        brg.setKadar(d.getKadar());
        brg.setBerat(d.getBerat());
        brg.setBeratAsli(d.getBeratAsli());
        brg.setBeratPersen(d.getBeratPersen());
        brg.setNilaiPokok(d.getNilaiPokok());
        brg.setInputDate(d.getInputDate());
        brg.setInputBy(d.getInputBy());
        brg.setAsalBarang(d.getAsalBarang());
        brg.setStatus(d.getStatus());
        
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(brg);
    }
    @FXML
    private void getCabang(){
        cabang = null;
        Task<Cabang> task = new Task<Cabang>() {
            @Override 
            public Cabang call() throws Exception{
                try(Connection con = KoneksiPusat.getConnection()){
                    return CabangDAO.get(con, cabangCombo.getSelectionModel().getSelectedItem());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            cabang = task.getValue();
            cabangCombo.setDisable(true);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
        else{
            if(cabang == null)
                getCabang();
            else{
                boolean status = false;
                for(HancurDetail d : listHancurDetail){
                    if(d.getKodeBarcode().equals(kodeBarcodeField.getText()))
                        status = true;
                }
                if(status){
                    mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode sudah diinput");
                    b = null;
                    namaBarangField.setText("");
                    kodeJenisField.setText("");
                    beratField.setText("0");
                    beratAsliField.setText("0");
                    persenRosokField.setText("0");
                    kodeBarcodeField.selectAll();
                }else{
                    Task<Barang> task = new Task<Barang>() {
                        @Override 
                        public Barang call() throws Exception{
                            try(Connection conCabang = KoneksiCabang.getConnection(cabang)){
                                return BarangDAO.get(conCabang, kodeBarcodeField.getText());
                            }
                        }
                    };
                    task.setOnRunning((e) -> {
                        mainApp.showLoadingScreen();
                    });
                    task.setOnSucceeded((e) -> {
                        mainApp.closeLoading();
                        b = task.getValue();
                        if(b==null){
                            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode tidak ditemukan");
                            namaBarangField.setText("");
                            kodeJenisField.setText("");
                            beratField.setText("0");
                            beratAsliField.setText("0");
                            persenRosokField.setText("0");
                            kodeBarcodeField.selectAll();
                        }else if(b.getStatus().equals("false")){
                            mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah terjual/hancur");
                            namaBarangField.setText("");
                            kodeJenisField.setText("");
                            beratField.setText("0");
                            beratAsliField.setText("0");
                            persenRosokField.setText("0");
                            kodeBarcodeField.selectAll();
                        }else{
                            namaBarangField.setText(b.getNamaBarang());
                            kodeJenisField.setText(b.getKodeJenis());
                            beratField.setText(gr.format(b.getBerat()));
                            beratAsliField.setText(gr.format(b.getBeratAsli()));
                            persenRosokField.setText(gr.format(b.getBeratPersen()/b.getBeratAsli()*100));
                            persenRosokField.requestFocus();
                            persenRosokField.selectAll();
                        }
                    });
                    task.setOnFailed((e) -> {
                        task.getException().printStackTrace();
                        mainApp.closeLoading();
                        mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                    });
                    new Thread(task).start();
                }
            }
        }
    }
    @FXML
    private void setBarang(){
        if(b==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode barcode belum di scan");
            kodeBarcodeField.selectAll();
        }else if(b.getStatus().equals("false")){
            mainApp.showMessage(Modality.NONE, "Warning", "Barang sudah terjual/hancur");
            kodeBarcodeField.selectAll();
        }else{
            HancurDetail d = new HancurDetail();
            d.setKodeBarcode(b.getKodeBarcode());
            d.setKodeBarang(b.getKodeBarang());
            d.setNamaBarang(b.getNamaBarang());
            d.setKodeKategori(b.getKodeKategori());
            d.setKodeJenis(b.getKodeJenis());
            d.setKodeIntern(b.getKodeIntern());
            d.setKadar(b.getKadar());
            d.setBerat(b.getBerat());
            d.setBeratAsli(b.getBeratAsli());
            d.setBeratPersen(b.getBeratPersen());
            d.setNilaiPokok(b.getNilaiPokok());
            d.setInputDate(b.getInputDate());
            d.setInputBy(b.getInputBy());
            d.setAsalBarang(b.getAsalBarang());
            d.setStatus(b.getStatus());
            d.setPersentaseRosok(Double.parseDouble(persenRosokField.getText().replaceAll(",", "")));
            d.setBeratPersenRosok(Double.parseDouble(persenRosokField.getText().replaceAll(",", ""))*b.getBeratAsli()/100);
            listHancurDetail.add(d);
            hancurDetailTable.refresh();
            hitungTotal();

            b = null;
            kodeBarcodeField.setText("");
            namaBarangField.setText("");
            kodeJenisField.setText("");
            beratField.setText("0");
            beratAsliField.setText("0");
            persenRosokField.setText("0");
            kodeBarcodeField.requestFocus();
        }
    }
    private void hapusBarang(HancurDetail d){
        listHancurDetail.remove(d);
        hancurDetailTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double beratAsli = 0;
        double beratPersen = 0;
        for(HancurDetail d : listHancurDetail){
            qty = qty + 1;
            berat = berat + d.getBerat();
            beratAsli = beratAsli + d.getBeratAsli();
            beratPersen = beratPersen + d.getBeratPersenRosok();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalBeratAsliLabel.setText(gr.format(beratAsli));
        totalBeratPersenLabel.setText(gr.format(beratPersen));
    }
    @FXML
    private void reset(){
        cabang = null;
        cabangCombo.getSelectionModel().clearSelection();
        cabangCombo.setDisable(false);
        b = null;
        kodeBarcodeField.setText("");
        namaBarangField.setText("");
        kodeJenisField.setText("");
        beratField.setText("0");
        beratAsliField.setText("0");
        persenRosokField.setText("0");
        listHancurDetail.clear();
        hancurDetailTable.refresh();
        hitungTotal();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
