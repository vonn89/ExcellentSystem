/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class PenjualanCabangController  {

    @FXML private TableView<PenjualanCabangDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanCabangDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<PenjualanCabangDetail, String> kodeJenisColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> beratColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> hargaPersenColumn;
    @FXML private TableColumn<PenjualanCabangDetail, Number> totalColumn;
    
    @FXML private ComboBox<String> jenisCombo;
    @FXML private TextField namaBarangField;
    @FXML private TextField qtyField;
    @FXML private TextField beratField;
    @FXML private TextField hargaField;
    
    @FXML public ComboBox<String> cabangCombo;
    @FXML public Button saveButton;
    
    @FXML private Label hargaEmasLabel;
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    @FXML private Label totalPenjualanLabel;
    
    private List<SubKategori> allSubKategori;
    private ObservableList<String> listSubKategori = FXCollections.observableArrayList();
    public ObservableList<PenjualanCabangDetail> listPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().hargaPersenProperty());
        hargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaPersenProperty());
        totalColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            penjualanDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        penjualanDetailTable.setContextMenu(rowMenu);
        penjualanDetailTable.setRowFactory(table -> {
            TableRow<PenjualanCabangDetail> row = new TableRow<PenjualanCabangDetail>(){
                @Override
                public void updateItem(PenjualanCabangDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            removeBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            penjualanDetailTable.refresh();
                        });
                        rowMenu.getItems().addAll(hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        Function.setNumberField(qtyField, rp);
        Function.setNumberField(beratField, gr);
        Function.setNumberField(hargaField, rp);
        jenisCombo.setOnAction((event) -> {
            getBarang();
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) { 
                beratField.requestFocus();
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hargaField.requestFocus();
                hargaField.selectAll();
            }
        });
        hargaField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setBarang();
        });
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
        penjualanDetailTable.setItems(listPenjualanDetail);
        jenisCombo.setItems(listSubKategori);
        setData();
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listSubKategori.clear();
                    allSubKategori = SubKategoriDAO.getAll(conPusat);
                    List<Kategori> allKategori = KategoriDAO.getAll(conPusat);
                    for(SubKategori s : allSubKategori){
                        for(Kategori k : allKategori){
                            if(s.getKodeKategori().equals(k.getKodeKategori()))
                                s.setKategori(k);
                        }
                        listSubKategori.add(s.getKodeSubKategori());
                    }
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
            ObservableList<String> allCabang = FXCollections.observableArrayList();
            allCabang.clear();
            for(Cabang c : listCabang){
                allCabang.addAll(c.getKodeCabang());
            }
            cabangCombo.setItems(allCabang);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        namaBarangField.setText("");
        qtyField.setText("0");
        beratField.setText("0");
        hargaField.setText("0");
        
        SubKategori subKategori = null;
        for(SubKategori s : allSubKategori){
            if(s.getKodeSubKategori().toUpperCase().equals(jenisCombo.getEditor().getText().toUpperCase()))
                subKategori = s;
        }
        if(subKategori!=null){
            jenisCombo.getSelectionModel().select(subKategori.getKodeSubKategori());
            namaBarangField.setText(subKategori.getNamaSubKategori());
            hargaField.setText(rp.format(subKategori.getKategori().getHargaPersenJual()));
            qtyField.requestFocus();
            qtyField.selectAll();
        }else{
            jenisCombo.getEditor().requestFocus();
            jenisCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void setBarang(){
        if(jenisCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            jenisCombo.requestFocus();
            jenisCombo.getEditor().selectAll();
        }else if(namaBarangField.getText()==null||namaBarangField.getText().equals("")){
            mainApp.showMessage(Modality.NONE, "Warning", "Nama barang masih kosong");
            namaBarangField.requestFocus();
            namaBarangField.selectAll();
        }else if(Double.parseDouble(qtyField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            qtyField.requestFocus();
            qtyField.selectAll();
        }else if(Double.parseDouble(beratField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            beratField.requestFocus();
            beratField.selectAll();
        }else if(Double.parseDouble(hargaField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Harga masih kosong");
            hargaField.requestFocus();
            hargaField.selectAll();
        }else{
            SubKategori subKategori = null;
            for(SubKategori s : allSubKategori){
                if(s.getKodeSubKategori().equalsIgnoreCase(jenisCombo.getSelectionModel().getSelectedItem()))
                    subKategori = s;
            }
            if(subKategori==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Jenis tidak ditemukan");
            }else{
                PenjualanCabangDetail d = new PenjualanCabangDetail();
                d.setKodeKategori(subKategori.getKodeKategori());
                d.setKodeJenis(subKategori.getKodeSubKategori());
                d.setQty(Integer.parseInt(qtyField.getText().replaceAll(",", "")));
                d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
                d.setPersentaseEmas(subKategori.getKategori().getPersentaseEmas());
                d.setBeratPersen(pembulatan(Double.parseDouble(beratField.getText().replaceAll(",", ""))*subKategori.getKategori().getPersentaseEmas()/100));
                d.setHargaPersen(Double.parseDouble(hargaField.getText().replaceAll(",", "")));
                d.setTotalHargaPersen(pembulatan(Double.parseDouble(beratField.getText().replaceAll(",", ""))*Double.parseDouble(hargaField.getText().replaceAll(",", ""))/100));
                d.setTotalNilai(0);
                d.setTotalHargaRp(pembulatan(d.getTotalHargaPersen()*sistem.getHargaEmas()));
                listPenjualanDetail.add(d);
                penjualanDetailTable.refresh();
                hitungTotal();
                
                jenisCombo.getSelectionModel().clearSelection();
                namaBarangField.setText("");
                qtyField.setText("0");
                beratField.setText("0");
                hargaField.setText("0");
                jenisCombo.requestFocus();
                jenisCombo.getEditor().selectAll();
            }
        }
    }
    private void removeBarang(PenjualanCabangDetail d){
        listPenjualanDetail.remove(d);
        penjualanDetailTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double totalQty = 0;
        double totalBerat = 0;
        double totalHargaPersen = 0;
        for(PenjualanCabangDetail d : listPenjualanDetail){
            totalQty = totalQty + d.getQty();
            totalBerat = totalBerat + d.getBerat();
            totalHargaPersen = totalHargaPersen + d.getTotalHargaPersen();
        }
        hargaEmasLabel.setText(rp.format(sistem.getHargaEmas()));
        totalQtyLabel.setText(gr.format(totalQty));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
        totalPenjualanLabel.setText(rp.format(totalHargaPersen*sistem.getHargaEmas()));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
