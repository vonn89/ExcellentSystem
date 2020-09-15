/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SubKategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.SupplierDAO;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.ReturPembelianDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.SubKategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.Supplier;
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
public class ReturPembelianSupplierController {

    
    @FXML private TableView<ReturPembelianDetail> returPembelianDetailTable;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<ReturPembelianDetail, String> kodeJenisColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> beratColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> hargaPersenColumn;
    @FXML private TableColumn<ReturPembelianDetail, Number> totalColumn;
    
    @FXML public ComboBox<String> kodeSubKategoriCombo;
    @FXML public TextField beratField;
    @FXML public TextField hargaPersenField;
    @FXML public TextField totalHargaPersenField;
    
    @FXML public ComboBox<String> supplierCombo;
    @FXML public Button saveButton;
    
    @FXML private Label hargaEmasLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalHargaPersenLabel;
    
    private List<SubKategori> allSubKategori;
    private ObservableList<String> listKodeSubKategori = FXCollections.observableArrayList();
    public ObservableList<ReturPembelianDetail> listReturPembelianDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        hargaPersenColumn.setCellValueFactory(cellData -> cellData.getValue().hargaPersenProperty());
        hargaPersenColumn.setCellFactory(col -> getTableCell(gr));
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalHargaProperty());
        totalColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu rowMenu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            returPembelianDetailTable.refresh();
        });
        rowMenu.getItems().addAll(refresh);
        returPembelianDetailTable.setContextMenu(rowMenu);
        returPembelianDetailTable.setRowFactory(table -> {
            TableRow<ReturPembelianDetail> row = new TableRow<ReturPembelianDetail>(){
                @Override
                public void updateItem(ReturPembelianDetail item, boolean empty) {
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
                            returPembelianDetailTable.refresh();
                        });
                        rowMenu.getItems().addAll(hapus, refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
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
            hitungTotalHargaPersen();
        });
        hargaPersenField.setOnKeyReleased((event) -> {
            try{
                String string = hargaPersenField.getText();
                if(string.contains("-"))
                    hargaPersenField.undo();
                else{
                    if(string.indexOf(".")>0){
                        String string2 = string.substring(string.indexOf(".")+1, string.length());
                        if(string2.contains("."))
                            hargaPersenField.undo();
                        else if(!string2.equals("") && Double.parseDouble(string2)!=0)
                            hargaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                    }else
                        hargaPersenField.setText(gr.format(Double.parseDouble(string.replaceAll(",", ""))));
                }
                hargaPersenField.end();
            }catch(Exception e){
                hargaPersenField.undo();
            }
            hitungTotalHargaPersen();
        });
        kodeSubKategoriCombo.setOnAction((event) -> {
            getBarang();
        });
        kodeSubKategoriCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER) 
                getBarang();
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                hargaPersenField.requestFocus();
                hargaPersenField.selectAll();
            }
        });
        hargaPersenField.setOnKeyPressed((KeyEvent keyEvent) -> {
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
        returPembelianDetailTable.setItems(listReturPembelianDetail);
        kodeSubKategoriCombo.setItems(listKodeSubKategori);
        hitungTotal();
        setData();
    }
    private void setData(){
        Task<List<Supplier>> task = new Task<List<Supplier>>() {
            @Override 
            public List<Supplier> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    listKodeSubKategori.clear();
                    allSubKategori = SubKategoriDAO.getAll(conPusat);
                    List<Kategori> allKategori = KategoriDAO.getAll(conPusat);
                    for(SubKategori s : allSubKategori){
                        for(Kategori k : allKategori){
                            if(s.getKodeKategori().equals(k.getKodeKategori()))
                                s.setKategori(k);
                        }
                        listKodeSubKategori.add(s.getKodeSubKategori());
                    }
                    return SupplierDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Supplier> listSupplier = task.getValue();
            ObservableList<String> allSupplier = FXCollections.observableArrayList();
            allSupplier.clear();
            for(Supplier s : listSupplier){
                allSupplier.addAll(s.getKodeSupplier());
            }
            supplierCombo.setItems(allSupplier);
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void getBarang(){
        beratField.setText("0");
        hargaPersenField.setText("0");
        totalHargaPersenField.setText("0");
        
        SubKategori subKategori = null;
        for(SubKategori s : allSubKategori){
            if(s.getKodeSubKategori().toUpperCase().equals(kodeSubKategoriCombo.getEditor().getText().toUpperCase()))
                subKategori = s;
        }
        if(subKategori!=null){
            kodeSubKategoriCombo.getSelectionModel().select(subKategori.getKodeSubKategori());
            hargaPersenField.setText("0");
            beratField.requestFocus();
            beratField.selectAll();
        }else{
            kodeSubKategoriCombo.getEditor().requestFocus();
            kodeSubKategoriCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void setBarang(){
        if(kodeSubKategoriCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            kodeSubKategoriCombo.requestFocus();
            kodeSubKategoriCombo.getEditor().selectAll();
        }else if(Double.parseDouble(beratField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            beratField.requestFocus();
            beratField.selectAll();
        }else if(Double.parseDouble(hargaPersenField.getText().replaceAll(",", ""))==0){
            mainApp.showMessage(Modality.NONE, "Warning", "Harga persen masih kosong");
            hargaPersenField.requestFocus();
            hargaPersenField.selectAll();
        }else{
            SubKategori subKategori = null;
            for(SubKategori s : allSubKategori){
                if(s.getKodeSubKategori().equalsIgnoreCase(kodeSubKategoriCombo.getSelectionModel().getSelectedItem()))
                    subKategori = s;
            }
            if(subKategori==null){
                mainApp.showMessage(Modality.NONE, "Warning", "Jenis tidak ditemukan");
            }else{
                ReturPembelianDetail d = new ReturPembelianDetail();
                d.setKodeKategori(subKategori.getKodeKategori());
                d.setKodeJenis(subKategori.getKodeSubKategori());
                d.setBerat(Double.parseDouble(beratField.getText().replaceAll(",", "")));
                d.setPersentaseEmas(subKategori.getKategori().getPersentaseEmas());
                d.setBeratPersen(pembulatan(Double.parseDouble(beratField.getText().replaceAll(",", ""))*subKategori.getKategori().getPersentaseEmas()/100));
                d.setHargaPersen(Double.parseDouble(hargaPersenField.getText().replaceAll(",", "")));
                d.setTotalHarga(pembulatan(Double.parseDouble(beratField.getText().replaceAll(",", ""))*Double.parseDouble(hargaPersenField.getText().replaceAll(",", ""))/100));
                listReturPembelianDetail.add(d);
                returPembelianDetailTable.refresh();
                hitungTotal();
                
                kodeSubKategoriCombo.getSelectionModel().clearSelection();
                beratField.setText("0");
                hargaPersenField.setText("0");
                totalHargaPersenField.setText("0");

                kodeSubKategoriCombo.requestFocus();
                kodeSubKategoriCombo.getEditor().selectAll();
            }
        }
    }
    private void removeBarang(ReturPembelianDetail d){
        listReturPembelianDetail.remove(d);
        returPembelianDetailTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double totalBerat = 0;
        double totalHargaPersen = 0;
        for(ReturPembelianDetail d : listReturPembelianDetail){
            totalBerat = totalBerat + d.getBerat();
            totalHargaPersen = totalHargaPersen + d.getTotalHarga();
        }
        hargaEmasLabel.setText(rp.format(sistem.getHargaEmas()));
        totalBeratLabel.setText(gr.format(totalBerat));
        totalHargaPersenLabel.setText(gr.format(totalHargaPersen));
    }
    private void hitungTotalHargaPersen(){
        double berat = Double.parseDouble(beratField.getText().replaceAll(",", ""));
        double hargaPersen = Double.parseDouble(hargaPersenField.getText().replaceAll(",", ""));
        totalHargaPersenField.setText(gr.format(berat * hargaPersen / 100));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    
    
}
